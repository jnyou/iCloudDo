/*
 * Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.corba.se.impl.dynamicany;

import org.omg.CORBA.Any;
import org.omg.CORBA.LocalObject;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TCKind;

import org.omg.DynamicAny.*;
import org.omg.DynamicAny.DynAnyFactoryPackage.*;

import com.sun.corba.se.spi.orb.ORB ;
import com.sun.corba.se.spi.logging.CORBALogDomains ;
import com.sun.corba.se.impl.logging.ORBUtilSystemException ;

public class DynAnyFactoryImpl
    extends LocalObject
    implements DynAnyFactory
{
    //
    // Instance variables
    //

    private ORB orb;

    //
    // Constructors
    //

    private DynAnyFactoryImpl() {
        this.orb = null;
    }

    public DynAnyFactoryImpl(ORB orb) {
        this.orb = orb;
    }

    //
    // DynAnyFactory interface methods
    //

    // Returns the most derived DynAny type based on the Anys TypeCode.
    public DynAny create_dyn_any (Any any)
        throws InconsistentTypeCode
    {
        return DynAnyUtil.createMostDerivedDynAny(any, orb, true);
    }

    // Returns the most derived DynAny type based on the TypeCode.
    public DynAny create_dyn_any_from_type_code (TypeCode type)
        throws InconsistentTypeCode
    {
        return DynAnyUtil.createMostDerivedDynAny(type, orb);
    }

    // Needed for org.omg.CORBA.Object

    private String[] __ids = { "IDL:omg.org/DynamicAny/DynAnyFactory:1.0" };

    public String[] _ids() {
        return (String[]) __ids.clone();
    }
}
