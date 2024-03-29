package org.jacorb.notification.filter;

/*
 *        JacORB - a free Java ORB
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

/**
 * @author Alphonse Bendt
 * @version $Id: ParseException.java,v 1.6 2011-05-10 15:40:38 nick.cross Exp $
 */

public class ParseException extends Exception 
{
    private static final long serialVersionUID = 1L;
    
    private final Exception nested_;
    private final String expression_;
    
    public ParseException(String expression, Exception e) 
    {
        super();

        nested_ = e;
        expression_ = expression;
    }

    public String getMessage() 
    {
        return "parse " + expression_ + " caused:\n" + nested_.getMessage();
    }
}
