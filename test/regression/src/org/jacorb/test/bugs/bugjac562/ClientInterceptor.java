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

package org.jacorb.test.bugs.bugjac562;

import org.omg.PortableInterceptor.ClientRequestInfo;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.ForwardRequest;

/**
 * @author Alphonse Bendt
 * @version $Id: ClientInterceptor.java,v 1.2 2011-05-10 15:40:42 nick.cross Exp $
 */
public class ClientInterceptor
    extends org.omg.CORBA.LocalObject
    implements ClientRequestInterceptor
{
    public void receive_exception(ClientRequestInfo ri) throws ForwardRequest
    {
    }

    public void receive_other(ClientRequestInfo ri) throws ForwardRequest
    {
    }

    public void receive_reply(ClientRequestInfo ri)
    {
    }

    public void send_poll(ClientRequestInfo ri)
    {
    }

    public void send_request(ClientRequestInfo ri) throws ForwardRequest
    {
        throw new RuntimeException();
    }

    public void destroy()
    {
    }

    public String name()
    {
        return "MyName";
    }
}
