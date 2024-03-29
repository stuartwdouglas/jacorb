package org.jacorb.test.notification.bugs;

/*
 *        JacORB - a free Java ORB
 *
 *   Copyright (C) 1999-2011 Gerald Brose / The JacORB Team.
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
 *
 */

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * PackageTest.java
 * 
 * @author Alphonse Bendt
 * @version $Id: PackageTest.java,v 1.5 2011-05-10 15:40:42 nick.cross Exp $
 */

public class PackageTest extends TestCase
{
    public static Test suite() throws Exception
    {
        TestSuite _suite = new TestSuite("Tests in Package org.jacorb.test.notification.bugs");

        _suite.addTest(MultipleDeliveryBugTest.suite());
        _suite.addTest(DeadlockBugTest.suite());

        return _suite;
    }

    public static void main(String[] args) throws Exception
    {
        junit.textui.TestRunner.run(suite());
    }
}
