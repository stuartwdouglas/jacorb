package org.jacorb.test.orb.etf.wiop;

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
 *   Software Foundation, 51 Franklin Street, Fifth Floor, Boston,
 *   MA 02110-1301, USA.
 */

import org.omg.ETF.*;
import org.omg.GIOP.Version;
import org.omg.IOP.*;

/**
 * See {@link org.jacorb.test.orb.etf.wiop.WIOPFactories WIOPFactories} for
 * a description of WIOP.
 * 
 * @author <a href="mailto:spiegel@gnu.org">Andre Spiegel</a>
 * @version $Id: WIOPProfile.java,v 1.3 2011-05-10 15:40:43 nick.cross Exp $
 */
public class WIOPProfile extends _ProfileLocalBase
{
    private int tag = 0;
    private Profile delegate = null;
    
    public WIOPProfile (Profile delegate, int tag)
    {
        this.delegate = delegate;
        this.tag = tag;
    }

    public Profile getDelegate()
    {
        return delegate;
    }

    public void marshal (TaggedProfileHolder tagged_profile,
                         TaggedComponentSeqHolder components)
    {
        delegate.marshal (tagged_profile, components);
        tagged_profile.value.tag = this.tag();
    }

    public int hash()
    {
        return delegate.hash();
    }

    public Profile copy()
    {
        return new WIOPProfile (delegate.copy(), tag);
    }

    public boolean is_match (Profile prof)
    {
        return delegate.is_match (prof);
    }

    public Version version()
    {
        return delegate.version();
    }

    public void set_object_key(byte[] key)
    {
        delegate.set_object_key (key);
    }

    public byte[] get_object_key()
    {
        return delegate.get_object_key();
    }

    public int tag()
    {
        return this.tag;
    }

}
