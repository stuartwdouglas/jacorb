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

package org.jacorb.orb.typecode;

import java.util.Map;
import org.jacorb.orb.CDRInputStream;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;

/**
 * @author Alphonse Bendt
 * @version $Id: ExceptTypeCodeReader.java,v 1.2 2011-09-26 15:19:38 nick.cross Exp $
 */
public class ExceptTypeCodeReader extends ComplexTypeCodeReader
{
    protected TypeCode doReadTypeCodeInternal(CDRInputStream in,
            Map recursiveTCMap, Map repeatedTCMap, Integer startPosition,
            int kind, String repositoryID)
    {
        final String name = validateName (in.read_string());
        final int member_count = in.read_long();

        StructMember[] members = new StructMember[member_count];
        for( int i = 0; i < member_count; i++)
        {
            members[i] = new StructMember
            (
                in.read_string(),
                in.read_TypeCode(recursiveTCMap, repeatedTCMap),
                null
            );
        }
        return orb.create_exception_tc (repositoryID, name, members, false);
    }
}
