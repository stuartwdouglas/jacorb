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
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alphonse Bendt
 * @version $Id: JDK15TypeCodeCache.java,v 1.1 2011-09-23 11:29:15 nick.cross Exp $
 */
public class JDK15TypeCodeCache extends AbstractTypeCodeCache
{
    protected Map newCache()
    {
        return new ConcurrentHashMap();
    }

    protected void put(String repositoryID, Pair[] entries)
    {
        ((ConcurrentHashMap)cache).putIfAbsent(repositoryID, entries);
    }
}
