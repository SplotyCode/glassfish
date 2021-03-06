/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

 package com.sun.enterprise.deployment.interfaces;
 
 import java.util.Set;
 
 public interface MailResourceIntf { 
	
    public String getName();
	
	public String getDescription();
	
	public boolean isEnabled();
	
	public Set getProperties();
	
    public int getType();
    
    public String getResType();
    
    public String getFactoryClass() ;

    public String getStoreProtocol();
      
    public String getStoreProtocolClass();
    
    public String getTransportProtocol();
    
    public String getTransportProtocolClass();
    
    public String getMailHost();
    
    public String getUsername();
    
    public String getMailFrom();

    public boolean isDebug();
 }
