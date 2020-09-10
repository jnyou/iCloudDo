/*
 * Copyright (c) 2001, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.pept.protocol;

import com.sun.corba.se.pept.broker.Broker;
import com.sun.corba.se.pept.transport.ContactInfoList;

/**
 * <p>The presentation block interacts with the PEPt architecture
 * via the <code>ClientDelegate</code>.</p>
 *
 * @author Harold Carr
 */
public interface ClientDelegate
{
    /**
     * The {@link Broker Broker} associated
     * with an invocation.
     *
     * @return {@link Broker Broker}
     */
    public Broker getBroker();

    /**
     * Get the
     * {@link ContactInfoList ContactInfoList}
     * which represents they encoding/protocol/transport combinations that
     * may be used to contact the service.
     *
     * @return
     * {@link ContactInfoList ContactInfoList}
     */
    public ContactInfoList getContactInfoList();
}

// End of file.
