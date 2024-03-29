package org.jacorb.test.orb;

/*
 *        JacORB  - a free Java ORB
 *
 *   Copyright (C) 1997-2011 Gerald Brose / The JacORB Team.
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Library General Public
 *   License as published by the Free Software Foundation; either
 *   version 2 of the License, or (at your option) any later version.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *   Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this library; if not, write to the Free
 *   Software Foundation, 51 Franklin Street, Fifth Floor, Boston,
 *   MA 02110-1301, USA.
 */

import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jacorb.orb.iiop.IIOPAddress;
import org.jacorb.orb.iiop.IIOPProfile;
import org.jacorb.test.IIOPAddressServer;
import org.jacorb.test.IIOPAddressServerHelper;
import org.jacorb.test.Sample;
import org.jacorb.test.common.ClientServerSetup;
import org.jacorb.test.common.ClientServerTestCase;
import org.jacorb.test.common.CommonSetup;
import org.jacorb.test.common.JacORBTestSuite;
import org.jacorb.test.common.TestUtils;
import org.omg.CORBA.portable.Delegate;
import org.omg.IOP.TAG_INTERNET_IOP;
import org.omg.IOP.TaggedProfile;

/**
 * This test is similar to AlternateIIOPAddressTest, but it uses the
 * special IORInfoExt functions to set up the IORs.
 *
 * @jacorb-since 2.2
 * @author Marc Heide
 * @version $Id: AlternateProfileTest.java,v 1.14 2011-05-10 15:40:43 nick.cross Exp $
 */
public class AlternateProfileTest extends ClientServerTestCase
{
    protected IIOPAddressServer server = null;

    private static final String CORRECT_HOST = "127.0.0.1";
    private static final String WRONG_HOST   = "10.0.1.223"; //"194.138.122.114"
    private static final String WRONG_HOST_2 = "10.0.1.123"; //"147.54.135.239"

    private static final int CORRECT_PORT = 50000;
    private static final int WRONG_PORT   = 50001;

    public AlternateProfileTest(String name, ClientServerSetup setup)
    {
        super(name, setup);
    }

    protected void setUp() throws Exception
    {
        server = IIOPAddressServerHelper.narrow(setup.getServerObject());
    }

    protected void tearDown() throws Exception
    {
        // server.clearSocketAddress();
        server.setIORAddress (CORRECT_HOST, CORRECT_PORT);
        server.clearAlternateAddresses();
        server = null;
    }

    public static Test suite()
    {
        TestSuite suite = new JacORBTestSuite
        (
            "Test TAG_ALTERNATE_IIOP_ADDRESS/IORInfoExt",
            AlternateProfileTest.class
        );

        Properties client_props = new Properties();
        client_props.setProperty ("jacorb.retries", "0");
        client_props.setProperty ("jacorb.retry_interval", "50");

        // This was originally two seconds but if the reg tests are run using
        // the IMR we get TIMEOUT instead of TRANSIENT. Therefore this has been
        // set to a much larger value.
        client_props.setProperty
            ("jacorb.connection.client.pending_reply_timeout", "120000");
        client_props.setProperty
            ("jacorb.connection.client.connect_timeout","5000");

        Properties server_props = new Properties();
        server_props.setProperty
            ("org.omg.PortableInterceptor.ORBInitializerClass.IIOPProfileORBInitializer",
             "org.jacorb.test.orb.IIOPProfileORBInitializer");
        server_props.setProperty ("OAPort", Integer.toString(CORRECT_PORT));

        // If security is not disabled it will not use the above host/port
        // combinations.
        client_props.setProperty(CommonSetup.JACORB_REGRESSION_DISABLE_SECURITY, "true");
        server_props.setProperty(CommonSetup.JACORB_REGRESSION_DISABLE_SECURITY, "true");

        client_props.setProperty(CommonSetup.JACORB_REGRESSION_DISABLE_IMR, "true");
        server_props.setProperty(CommonSetup.JACORB_REGRESSION_DISABLE_IMR, "true");

        ClientServerSetup setup =
         new ClientServerSetup (suite,
                                IIOPAddressServerImpl.class.getName(),
                                client_props,
                                server_props);

        TestUtils.addToSuite(suite, setup, AlternateProfileTest.class);

        return setup;
    }

    public void test_ping()
    {
        Sample s = server.getObject();
        testNumberOfIIOPProfiles(1, s);
        int result = s.ping (17);
        assertEquals (18, result);
    }

    public void test_primary_ok()
    {
        server.setIORAddress( CORRECT_HOST, CORRECT_PORT );
        Sample s = server.getObject();
        testNumberOfIIOPProfiles(1, s);
        int result = s.ping (77);
        assertEquals (78, result);
    }

    public void test_primary_wrong_host()
    {
        server.setIORAddress( WRONG_HOST, CORRECT_PORT );
        Sample s = server.getObject();
        testNumberOfIIOPProfiles(1, s);
        try
        {
            s.ping (123);
            fail ("TRANSIENT or TIMEOUT exception expected");
        }
        catch (org.omg.CORBA.TRANSIENT ex)
        {
            // ok - unable to resolve the address
        }
        catch (org.omg.CORBA.TIMEOUT ex)
        {
            // ok - client connection timeout configured.
        }
    }

    public void test_primary_wrong_port()
    {
        server.setIORAddress( CORRECT_HOST, WRONG_PORT );
        Sample s = server.getObject();
        testNumberOfIIOPProfiles(1, s);

        try
        {
            s.ping (4);
            fail ("TRANSIENT or TIMEOUT  exception expected");
        }
        catch (org.omg.CORBA.TRANSIENT ex)
        {
            // ok - unable to resolve the address
        }
        catch (org.omg.CORBA.TIMEOUT ex)
        {
            // ok - client connection timeout configured.
        }
    }

    public void test_alternate_ok()
    {
        server.setIORAddress( WRONG_HOST, CORRECT_PORT );
        server.addAlternateAddress( CORRECT_HOST, CORRECT_PORT );
        Sample s = server.getObject();
//         ORB _myOrb = _setup.getClientOrb();
//         String iorStr = _myOrb.object_to_string(s);
//         System.out.println(iorStr);

        testNumberOfIIOPProfiles(2, s);
        testHostAndPortInIIOPProfile(s, 0, WRONG_HOST, CORRECT_PORT);
        testHostAndPortInIIOPProfile(s, 1, CORRECT_HOST, CORRECT_PORT);

        int result = s.ping (99);
        assertEquals (100, result);
    }

    public void test_alternate_ok_2()
    {
        server.setIORAddress( WRONG_HOST, CORRECT_PORT );
        server.addAlternateAddress( WRONG_HOST_2, CORRECT_PORT );
        server.addAlternateAddress( CORRECT_HOST, CORRECT_PORT );
        Sample s = server.getObject();
        testNumberOfIIOPProfiles(3, s);
        testHostAndPortInIIOPProfile(s, 0, WRONG_HOST, CORRECT_PORT);
        testHostAndPortInIIOPProfile(s, 1, WRONG_HOST_2, CORRECT_PORT);
        testHostAndPortInIIOPProfile(s, 2, CORRECT_HOST, CORRECT_PORT);
        int result = s.ping (187);
        assertEquals (188, result);
    }

    public void test_alternate_wrong()
    {
        server.setIORAddress( CORRECT_HOST, WRONG_PORT );
        server.addAlternateAddress( WRONG_HOST, CORRECT_PORT );
        server.addAlternateAddress( WRONG_HOST_2, WRONG_PORT );
        server.addAlternateAddress( WRONG_HOST_2, CORRECT_PORT );
        Sample s = server.getObject();
        testNumberOfIIOPProfiles(4, s);
        testHostAndPortInIIOPProfile(s, 0, CORRECT_HOST, WRONG_PORT);
        testHostAndPortInIIOPProfile(s, 1, WRONG_HOST, CORRECT_PORT);
        testHostAndPortInIIOPProfile(s, 2, WRONG_HOST_2, WRONG_PORT);
        testHostAndPortInIIOPProfile(s, 3, WRONG_HOST_2, CORRECT_PORT);
        try
        {
            s.ping (33);
            fail ("TRANSIENT or TIMEOUT  exception expected");
        }
        catch (org.omg.CORBA.TRANSIENT ex)
        {
            // ok - unable to resolve the address
        }
        catch (org.omg.CORBA.TIMEOUT ex)
        {
            // ok - client connection timeout configured.
        }
    }

    /**
     * Tests the number of IOP profiles in given IOR.
     * @param numberExpected
     * @param obj
     */
    public void testNumberOfIIOPProfiles( int numberExpected, org.omg.CORBA.Object obj )
    {
       // try to get ORB delegate to object
       org.jacorb.orb.Delegate jacOrbDelegate = null;
       Delegate localObj = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate();
       jacOrbDelegate = (org.jacorb.orb.Delegate)localObj;

       org.omg.IOP.IOR ior = jacOrbDelegate.getIOR();

       TaggedProfile[] profiles = ior.profiles;
       int nrOfIOPProf = 0;
       for (int i = 0; i < profiles.length; i++)
       {
           if (profiles[i].tag == TAG_INTERNET_IOP.value)
           {
              nrOfIOPProf++;
           }
       }
       assertEquals(numberExpected, nrOfIOPProf);
    }

    /**
     * Tests if given host and port equal values in given IOR.
     * Since several IOP profiles may be coded in IOR, an position must be specified.
     * Position must be: 0 <= pos < max_number_of_profiles
     * @param obj
     * @param pos
     * @param host
     * @param port
     */
    public void testHostAndPortInIIOPProfile(org.omg.CORBA.Object obj, int pos, String host, int port)
    {
       // try to get ORB delegate to object
       org.jacorb.orb.Delegate jacOrbDelegate = null;
       Delegate localObj = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate();
       jacOrbDelegate = (org.jacorb.orb.Delegate)localObj;

       //       ParsedIOR pior = jacOrbDelegate.getParsedIOR();
       org.omg.IOP.IOR ior = jacOrbDelegate.getIOR();

       TaggedProfile[] profiles = ior.profiles;
       int cnt = pos;
       boolean found = false;
       for (int i = 0; i < profiles.length; i++)
       {
           if (profiles[i].tag == TAG_INTERNET_IOP.value)
           {
               if( cnt == 0 )
              {
                  IIOPProfile prof = new IIOPProfile(profiles[i].profile_data);
                  assertEquals(((IIOPAddress)prof.getAddress()).getIP(), host);
                  assertEquals(((IIOPAddress)prof.getAddress()).getPort(), port);
                  found = true;
                  break;
              }
               cnt--;
               continue;
           }
       }
       assertTrue(found);
    }

    public static void main(String args[])
    {
      junit.textui.TestRunner.run(suite());
    }
}
