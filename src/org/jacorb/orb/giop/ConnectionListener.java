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
package org.jacorb.orb.giop;

/**
 * ConnectionListener.java
 *
 *
 * Created: Thu Oct  4 15:56:02 2002
 *
 * @author Nicolas Noffke
 * @version $Id: ConnectionListener.java,v 1.8 2011-05-10 15:40:40 nick.cross Exp $
 */

public interface ConnectionListener 
{   
    public void connectionClosed();
    public void streamClosed();
}// ConnectionListener
