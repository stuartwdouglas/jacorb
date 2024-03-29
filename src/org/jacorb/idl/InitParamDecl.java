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

package org.jacorb.idl;

/**
 * @author Gerald Brose
 * @version $Id: InitParamDecl.java,v 1.8 2011-05-10 15:40:36 nick.cross Exp $
 */

import java.io.PrintWriter;

public class InitParamDecl
        extends ParamDecl
{
    public InitParamDecl( int num )
    {
        super( num );
    }

    public void setPackage( String s )
    {
        s = parser.pack_replace( s );
        if( pack_name.length() > 0 )
            pack_name = s + "." + pack_name;
        else
            pack_name = s;
        paramTypeSpec.setPackage( s );
    }

    public void parse()
    {
        while( paramTypeSpec.typeSpec() instanceof ScopedName )
        {
            TypeSpec ts = ( (ScopedName)paramTypeSpec.typeSpec() ).resolvedTypeSpec();

            if( ts != null )
                paramTypeSpec = ts;
        }
    }

    public void print( PrintWriter ps )
    {
        ps.print( paramTypeSpec.toString() + " " + simple_declarator );
    }

    public String printWriteStatement( String ps )
    {
        return printWriteStatement( simple_declarator.toString(), ps );
    }

    public String printWriteStatement( String name, String ps )
    {
        return paramTypeSpec.typeSpec().printWriteStatement( name, ps );
    }

    public String printReadExpression( String ps )
    {
        return paramTypeSpec.typeSpec().printReadExpression( ps );
    }
}
