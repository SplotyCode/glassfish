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

package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.beans.Preferred;
import test.beans.SecureInterceptor;
import test.beans.TestBean;
import test.beans.TransactionInterceptor;

@WebServlet(name = "mytest", urlPatterns = { "/myurl" })
public class InterceptorsTestServlet extends HttpServlet {
    @Inject
    @Preferred
    TestBean tb;
    
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        PrintWriter writer = res.getWriter();
        writer.write("Hello from Servlet 3.0.");
        String msg = "";
        
        tb.m1();
        if (!TransactionInterceptor.aroundInvokeCalled)
            msg +="TransactionInterceptor aroundInvoke method not called";
        if (!SecureInterceptor.aroundInvokeCalled)
            msg +="SecureInterceptor aroundInvoke method not called";
        if ((TransactionInterceptor.aroundInvokeInvocationCount != 1))
            msg +="TransactionInterceptor aroundInvoke invocation count is " +
                    "not expected 1 but" + 
                    TransactionInterceptor.aroundInvokeInvocationCount;
        tb.m2();
        if ((SecureInterceptor.aroundInvokeInvocationCount != 2))
            msg +="SecureInterceptor aroundInvoke invocation count is " +
                    "not expected 2 but" + 
                    SecureInterceptor.aroundInvokeInvocationCount;

        writer.write(msg + "\n");
    }

}
