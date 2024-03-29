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

package org.jacorb.notification.util;

import org.jacorb.config.Configuration;
import org.slf4j.Logger;

/**
 * @author Alphonse Bendt
 * @version $Id: LogUtil.java,v 1.8 2011-09-27 14:06:17 nick.cross Exp $
 */
public class LogUtil
{
    public static Logger getLogger(Configuration config, String name)
    {
        try
        {
            return ((org.jacorb.config.Configuration) config).getLogger(name);
        } catch (ClassCastException e)
        {
            return getLogger(name);
        }
    }

    public static Logger getLogger(String name)
    {
        return org.slf4j.LoggerFactory.getLogger(name);
    }
}