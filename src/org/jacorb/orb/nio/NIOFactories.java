/*
 *        JacORB - a free Java ORB
 *
 *   Copyright (C) 2011 Gerald Brose
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

package org.jacorb.orb.nio;

import org.omg.ETF.Connection;


/**
 * @author Ciju John
 * @version $Id: NIOFactories.java,v 1.3 2011-11-25 11:58:10 nick.cross Exp $
 *
 * This class is identical to the iiop.IIOPFactories except for the static
 *  initialization bit.
 */
public class NIOFactories
        extends org.jacorb.orb.iiop.IIOPFactories
{
   /**
    * Return the correct type of connection
    */
    protected Connection create_connection_internal ()
    {
       return new ClientNIOConnection();
    }
}
