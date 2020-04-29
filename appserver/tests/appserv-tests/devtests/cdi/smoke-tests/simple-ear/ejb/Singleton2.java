/*
 * Copyright (c) 2010, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.acme;

import javax.ejb.*;
import jakarta.annotation.*;


@Singleton
@DependsOn("cdi-full-ear-ejb.jar#Singleton3")
public class Singleton2 {

    @PostConstruct
    public void init() {
        System.out.println("In SingletonBean2::init()");
    }
    
    @PreDestroy
    public void destroy() {
        System.out.println("In SingletonBean2::destroy()");
    }



}
