/*
 *        JacORB  - a free Java ORB
 *
 *   Copyright (C) 1997-2006 The JacORB project.
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
 *   Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.jacorb.test.bugs.bugjac462;

import java.util.Properties;
import org.jacorb.orb.CDRInputStream;
import org.jacorb.orb.CDROutputStream;
import org.jacorb.test.common.ORBTestCase;
import org.omg.CORBA.TypeCode;

/**
 * @author Alphonse Bendt
 * @version $Id: BugJac462Test.java,v 1.2 2011-09-26 13:54:04 nick.cross Exp $
 */
public class BugJac462Test extends ORBTestCase
{
    protected void patchORBProperties(String testName, Properties props) throws Exception
    {
        props.setProperty("jacorb.cacheTypecodes", "on");
        props.setProperty("jacorb.compactTypecodes", "off");
    }

    public void testBug462() throws Exception
    {
        CDROutputStream out1 = (CDROutputStream) orb.create_output_stream();
        out1.write_TypeCode(ComplexAHelper.type());

        TypeCode tc = readTypeCode(out1);

        assertTrue(tc.equivalent(ComplexAHelper.type()));

        CDROutputStream out2 = (CDROutputStream) orb.create_output_stream();
        out2.write_TypeCode(ComplexBHelper.type());

        tc = readTypeCode(out2);

        assertTrue(tc.equivalent(ComplexBHelper.type()));

        CDROutputStream out3 = (CDROutputStream) orb.create_output_stream();
        out3.write_TypeCode(ComplexCHelper.type());

        tc = readTypeCode(out3);

        assertTrue(tc.equivalent(ComplexCHelper.type()));
    }

    private TypeCode readTypeCode(CDROutputStream out)
    {
        CDRInputStream in = (CDRInputStream) out.create_input_stream();
        TypeCode tc = in.read_TypeCode();
        return tc;
    }

    public void testMultipleReadsAreAnsweredFromCache()
    {
        CDROutputStream out = (CDROutputStream) orb.create_output_stream();
        out.write_TypeCode(ComplexAHelper.type());

        TypeCode tc1 = readTypeCode(out);
        TypeCode tc2 = readTypeCode(out);

        assertSame(tc1, tc2);
    }
}
