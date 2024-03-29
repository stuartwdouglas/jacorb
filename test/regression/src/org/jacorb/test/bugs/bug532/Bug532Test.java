package org.jacorb.test.bugs.bug532;

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

import org.jacorb.test.common.ORBTestCase;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

/**
 * Test for bug 532, CDROutputStream.create_input_stream completely fails
 * to take account of deferred writes, so fails when byte[]s larger than
 * 4000 bytes are being sent. We saw that bug appearing in JBoss when we
 * could not insert into an Any a byte[] of size larger than 4000 bytes.
 *
 * @author <a href="mailto:dimitris@jboss.org">Dimitris Andreadis</a>
 * @version $Id: Bug532Test.java,v 1.4 2011-05-13 09:24:32 nick.cross Exp $
 */
public class Bug532Test extends ORBTestCase
{
    public void testLargeByteArrayToAnyInsertion()
    {
        ORB orb = org.omg.CORBA.ORB.init(new String[]{}, null);

        byte[] bytes = new byte[4001];
        Any any = orb.create_any();
        ByteSequenceHelper.insert(any, bytes);

        orb.shutdown(true);
    }
}
