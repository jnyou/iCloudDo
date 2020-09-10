/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.pept.transport;

import java.util.Iterator;

/**
 * <p> <code>ContactInfoList</code> contains one or more
 * {@link ContactInfo ContactInfo}.
 *
 * @author Harold Carr
 */
public interface ContactInfoList
{
    /**
     * Used to get a
     * {@link ContactInfoListIterator
     * ContactInfoListIterator} to retrieve individual
     * {@link ContactInfo ContactInfo}
     * from the list.
     *
     * @return A
     * {@link ContactInfoListIterator
     * ContactInfoListIterator}.
     */
    public Iterator iterator();
}

// End of file.
