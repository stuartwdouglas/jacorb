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

package org.jacorb.notification.servant;

import org.jacorb.notification.IContainer;

/**
 * Internal Interface provided to Proxies that gives them Information about their Parent Admin.
 * 
 * @author Alphonse Bendt
 * @version $Id: IAdmin.java,v 1.4 2011-05-10 15:40:39 nick.cross Exp $
 */
public interface IAdmin extends IContainer
{
    /**
     * @return the id the Proxy should use.
     */
    int getProxyID();
    
    /**
     * @return a boolean value that indicates if the id may be used to look up the Proxy via Admins get_proxy* methods.
     */
    boolean isIDPublic();
    
    String getAdminMBean();
}
