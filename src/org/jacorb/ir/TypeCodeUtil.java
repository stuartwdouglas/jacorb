package org.jacorb.ir;

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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.jacorb.orb.TypeCode;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.ORB;

/**
 * @author Gerald Brose, FU Berlin
 * @version $Id: TypeCodeUtil.java,v 1.21 2011-05-10 15:40:37 nick.cross Exp $
 */

public class TypeCodeUtil
{
    private static final Map cache = new HashMap();

    static
    {
        cache.put( "java.lang.String",  ORB.init ().get_primitive_tc (TCKind.tk_string));
        cache.put( "org.omg.CORBA.String", ORB.init ().get_primitive_tc (TCKind.tk_string));
        cache.put( "java.lang.Void", ORB.init ().get_primitive_tc (TCKind.tk_void ));
        cache.put( "java.lang.Long",ORB.init ().get_primitive_tc (TCKind.tk_longlong ));
        cache.put( "java.lang.Integer",ORB.init ().get_primitive_tc (TCKind.tk_long ));
        cache.put( "java.lang.Short",ORB.init ().get_primitive_tc (TCKind.tk_short ));
        cache.put( "java.lang.Float",ORB.init ().get_primitive_tc (TCKind.tk_float ));
        cache.put( "java.lang.Double",ORB.init ().get_primitive_tc (TCKind.tk_double ));
        cache.put( "java.lang.Boolean",ORB.init ().get_primitive_tc (TCKind.tk_boolean ));
        cache.put( "java.lang.Byte" ,ORB.init ().get_primitive_tc (TCKind.tk_octet ));
        cache.put( "org.omg.CORBA.Any", ORB.init ().get_primitive_tc (TCKind.tk_any));
        cache.put( "java.lang.Character",ORB.init ().get_primitive_tc (TCKind.tk_char));
        cache.put( "org.omg.CORBA.TypeCode",ORB.init ().get_primitive_tc (TCKind.tk_TypeCode));
        // Principal deprecated and removed.
        cache.put( "org.omg.CORBA.Object", ORB.init ().get_primitive_tc (TCKind.tk_objref));
    }


    /**
     * get a TypeCode for Class c. An object o of this class is needed
     * in order to get at nested types, as e.g. in array of arrays of arrays
     */

    public static org.omg.CORBA.TypeCode getTypeCode( Class c,
                                                      java.lang.Object o,
                                                      Logger logger )
        throws ClassNotFoundException
    {
        return getTypeCode( c, null, o, null, logger );
    }

    /**
     * get a TypeCode for Class c. An object o of this class is needed
     * in order to get at nested types, as e.g. in array of arrays of arrays
     */

    public static org.omg.CORBA.TypeCode getTypeCode( Class c,
                                                      ClassLoader classLoader,
                                                      java.lang.Object o,
                                                      String idlName,
                                                      Logger logger )
        throws ClassNotFoundException
    {
        String typeName = c.getName();

        if (logger.isDebugEnabled())
        {
            logger.debug("TypeCodes.getTypeCode for class : " +
                         typeName + " idlName: " + idlName);
        }

        ClassLoader loader;
        if( classLoader != null )
            loader = classLoader;
        else
            loader = c.getClassLoader(); // important for ir

        // debug:
        //System.out.println("- TypeCodes.getTypeCode for class : " + c.getName() + " and primitive " + c.isPrimitive() + " and idl name " + idlName);

        org.omg.CORBA.TypeCode _tc =
            (org.omg.CORBA.TypeCode)cache.get( typeName  );
        if( _tc != null )
        {
            //System.out.println("[ cached TypeCode ]");
            return _tc;
        }

        if( idlName != null )
        {
            _tc = (org.omg.CORBA.TypeCode)cache.get( idlName );
            if( _tc != null )
            {
                //System.out.println("[ cached TypeCode ]");
                return _tc;
            }
        }

        if( c.isPrimitive() )
        {
            if( typeName.equals("void"))
                return ORB.init ().get_primitive_tc (TCKind.tk_void );
            if( typeName.equals("int"))
                return ORB.init ().get_primitive_tc (TCKind.tk_long );
            if( typeName.equals("long"))
                return ORB.init ().get_primitive_tc (TCKind.tk_longlong );
            if( typeName.equals("short"))
                return ORB.init ().get_primitive_tc (TCKind.tk_short );
            if( typeName.equals("float"))
                return ORB.init ().get_primitive_tc (TCKind.tk_float );
            if( typeName.equals("double"))
                return ORB.init ().get_primitive_tc (TCKind.tk_double );
            if( typeName.equals("boolean"))
                return ORB.init ().get_primitive_tc (TCKind.tk_boolean );
            if( typeName.equals("byte") )
                return ORB.init ().get_primitive_tc (TCKind.tk_octet );
            if( typeName.equals("char") )
                return ORB.init ().get_primitive_tc (TCKind.tk_char );
            if( typeName.equals("wchar") )
                return ORB.init ().get_primitive_tc (TCKind.tk_wchar );
            else
            {
                System.err.println("- TypeCode.getTypeCode, primitive class not found " +
                                   typeName );
                return null;
            }
        }

        /* else */

        Class tcClass = null;
        Class idlEntity = null;
        try
        {
            tcClass = Class.forName("org.omg.CORBA.TypeCode", true, loader );
            idlEntity = Class.forName("org.omg.CORBA.portable.IDLEntity", true, loader);
        }
        catch ( ClassNotFoundException ce )
        {
            logger.error("Can't load org.jacorb base classes!", ce);
            throw ce;
        }
        int field_size = 0;

        if ( tcClass.isAssignableFrom(c))
        {
            return ORB.init ().get_primitive_tc (TCKind.tk_TypeCode );
        }
        else
        {
            if( idlName != null && idlName.length() > 0 )
            {
                try
                {
                     if( idlName.equals( "java.lang.String"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_string);
                     else if( idlName.equals( "org.omg.CORBA.Boolean"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_boolean);
                     else if( idlName.equals( "org.omg.CORBA.Byte"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_octet);
                     else if( idlName.equals( "org.omg.CORBA.Short"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_short);
                     else if( idlName.equals( "org.omg.CORBA.Long"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_longlong);
                     else if( idlName.equals( "org.omg.CORBA.Int"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_long);
                     else if( idlName.equals( "org.omg.CORBA.String"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_string);
                     else if( idlName.equals( "org.omg.CORBA.Char"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_char);
                     else if( idlName.equals( "org.omg.CORBA.Float"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_float);
                     else if( idlName.equals( "org.omg.CORBA.Double"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_double);
                     else if( idlName.equals( "org.omg.CORBA.Any"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_any);
                     else if( idlName.equals( "org.omg.CORBA.Object"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_objref);
                     else if( idlName.equals( "org.omg.CORBA.TypeCode"))
                        return ORB.init ().get_primitive_tc (TCKind.tk_TypeCode);

                    Class type = Class.forName( idlName + "Helper", true, loader);

                    return (org.omg.CORBA.TypeCode)type.getDeclaredMethod(
                                                    "type",
                                                    (Class[]) null).invoke( null, (Object[]) null );
                }
                catch( ClassNotFoundException cnfe )
                {
                    logger.debug("Caught Exception", cnfe );
                    throw new RuntimeException("Could not create TypeCode for: " +
                                               c.getName() + ", no helper class for " + idlName );
                }
                catch( Exception e )
                {
                    logger.error("Caught Exception", e );
                }
            }

            if( idlEntity.isAssignableFrom(c))
            {
                try
                {
                    Class resultHelperClass = Class.forName( c.getName()+ "Helper", true, loader);

                    return (org.omg.CORBA.TypeCode)
                        resultHelperClass.getDeclaredMethod(
                                                    "type",
                                                    (Class[]) null).invoke( null, (Object[]) null );
                }
                catch( Exception cnfe )
                {
                    logger.error("Caught Exception", cnfe);
                    throw new RuntimeException("Could not create TypeCode for: " +
                                               c.getName() );
                }
            }
            else
            {
                throw new RuntimeException("Could not create TypeCode for: " +
                                           c.getName() + ", not an IDLEntity" );
            }
        }
    }
}
