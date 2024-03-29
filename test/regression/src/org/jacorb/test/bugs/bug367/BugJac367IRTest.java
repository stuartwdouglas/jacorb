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
 *   Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.jacorb.test.bugs.bug367;

import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jacorb.test.common.TestUtils;
import org.jacorb.test.ir.AbstractIRServerTestCase;
import org.jacorb.test.ir.IFRServerSetup;

/**
 * @author Alphonse Bendt
 * @version $Id: BugJac367IRTest.java,v 1.4 2011-05-10 15:40:41 nick.cross Exp $
 */
public class BugJac367IRTest extends AbstractIRServerTestCase
{
    public BugJac367IRTest(String name, IFRServerSetup setup)
    {
        super(name, setup);
    }

    public static Test suite()
    {
        if (TestUtils.isJ2ME())
        {
            return new TestSuite();
        }

        Properties props = new Properties();
        props.setProperty("jacorb.ir.patch_pragma_prefix", "on");

        return AbstractIRServerTestCase.suite("irjac367.idl", BugJac367IRTest.class, new String[] {"-i2jpackage", "ir:org.jacorb.test.ir"}, props);
    }

    public void testServerStart() throws Exception
    {
        assertFalse(repository._non_existent());
        assertNotNull(repository.lookup_id("IDL:org.jacorb.test/ir/StringAliasList:1.0"));
    }
}
