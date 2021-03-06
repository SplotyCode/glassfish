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

package com.sun.enterprise.tools.verifier.tests.web;

import java.util.*;
import com.sun.enterprise.deployment.*;
import com.sun.enterprise.tools.verifier.*;
import com.sun.enterprise.tools.verifier.tests.*;
import org.glassfish.web.deployment.descriptor.ErrorPageDescriptor;
import org.glassfish.web.deployment.descriptor.WebBundleDescriptorImpl;


/** Error code element contains an HTTP error code within web application test.
 *  i.e. 404
 */
public class ErrorCode extends WebTest implements WebCheck { 

    
    /**
     * Determine is Error Code is valid.
     *
     * @param errorCodeTypeInteger  error code type
     *
     * @return <code>boolean</code> true if error code is valid, false otherwise
     */
    public static boolean isValidErrorCode(Integer errorCodeTypeInteger) {
	int errorCodeType = errorCodeTypeInteger.intValue();
	if ((errorCodeType == ErrorCodeTypes.CONTINUE) ||
	    (errorCodeType == ErrorCodeTypes.SWITCHING_PROTOCOLS) ||
	    (errorCodeType == ErrorCodeTypes.OK) ||
	    (errorCodeType == ErrorCodeTypes.CREATED) ||
	    (errorCodeType == ErrorCodeTypes.ACCEPTED) ||
	    (errorCodeType == ErrorCodeTypes.NON_AUTHORITATIVE_INFORMATION) ||
	    (errorCodeType == ErrorCodeTypes.NO_CONTENT) ||
	    (errorCodeType == ErrorCodeTypes.RESET_CONTENT) ||
	    (errorCodeType == ErrorCodeTypes.PARTIAL_CONTENT) ||
	    (errorCodeType == ErrorCodeTypes.MULTIPLE_CHOICES) ||
	    (errorCodeType == ErrorCodeTypes.MOVED_PERMANENTLY) ||
	    (errorCodeType == ErrorCodeTypes.FOUND) ||
	    (errorCodeType == ErrorCodeTypes.SEE_OTHER) ||
	    (errorCodeType == ErrorCodeTypes.NOT_MODIFIED) ||
	    (errorCodeType == ErrorCodeTypes.USE_PROXY) ||
	    (errorCodeType == ErrorCodeTypes.UNUSED) ||
	    (errorCodeType == ErrorCodeTypes.TEMPORARY_REDIRECT) ||
	    (errorCodeType == ErrorCodeTypes.BAD_REQUEST) ||
	    (errorCodeType == ErrorCodeTypes.UNAUTHORIZED) ||
	    (errorCodeType == ErrorCodeTypes.PAYMENT_REQUIRED) ||
	    (errorCodeType == ErrorCodeTypes.FORBIDDEN) ||
	    (errorCodeType == ErrorCodeTypes.NOT_FOUND) ||
	    (errorCodeType == ErrorCodeTypes.METHOD_NOT_ALLOWED) ||
	    (errorCodeType == ErrorCodeTypes.NOT_ACCEPTABLE) ||
	    (errorCodeType == ErrorCodeTypes.PROXY_AUTHENTICATION_REQUIRED) ||
	    (errorCodeType == ErrorCodeTypes.REQUEST_TIMEOUT) ||
	    (errorCodeType == ErrorCodeTypes.CONFLICT) ||
	    (errorCodeType == ErrorCodeTypes.GONE) ||
	    (errorCodeType == ErrorCodeTypes.LENGTH_REQUIRED) ||
	    (errorCodeType == ErrorCodeTypes.PRECONDITION_FAILED) ||
	    (errorCodeType == ErrorCodeTypes.REQUEST_ENTITY_TOO_LARGE) ||
	    (errorCodeType == ErrorCodeTypes.REQUEST_URI_TOO_LONG) ||
	    (errorCodeType == ErrorCodeTypes.UNSUPPORTED_MEDIA_TYPE) ||
	    (errorCodeType == ErrorCodeTypes.REQUESTED_RANGE_NOT_SATISFIABLE) ||
	    (errorCodeType == ErrorCodeTypes.EXPECTATION_FAILED) ||
	    (errorCodeType == ErrorCodeTypes.INTERNAL_SERVER_ERROR) ||
	    (errorCodeType == ErrorCodeTypes.NOT_IMPLEMENTED) ||
	    (errorCodeType == ErrorCodeTypes.BAD_GATEWAY) ||
	    (errorCodeType == ErrorCodeTypes.SERVICE_UNAVAILABLE) ||
	    (errorCodeType == ErrorCodeTypes.GATEWAY_TIMEOUT) ||
	    (errorCodeType == ErrorCodeTypes.HTTP_VERSION_NOT_SUPPORTED)) {
	    return true;
	} else {
	    return false;
	}
 
    }

    /** Error code element contains an HTTP error code within web application test.
     *  i.e. 404
     *
     * @param descriptor the Web deployment descriptor 
     *
     * @return <code>Result</code> the results for this assertion
     */
    public Result check(WebBundleDescriptor descriptor) {

	Result result = getInitializedResult();
	ComponentNameConstructor compName = getVerifierContext().getComponentNameConstructor();

	if (((WebBundleDescriptorImpl)descriptor).getErrorPageDescriptors().hasMoreElements()) {
	    boolean oneFailed = false;
	    boolean foundIt = false;
            int oneErrorCode = 0;
            int oneNA = 0;
	    // get the errorpage's in this .war
	    for (Enumeration e = ((WebBundleDescriptorImpl)descriptor).getErrorPageDescriptors() ; e.hasMoreElements() ;) {
		foundIt = false;
                oneErrorCode++;
		ErrorPageDescriptor errorpage = (ErrorPageDescriptor) e.nextElement();
                String exceptionType = errorpage.getExceptionType();
                if (!((exceptionType != null) && (exceptionType.length() > 0))) {
		    Integer errorCode = new Integer( errorpage.getErrorCode() );
		    if (isValidErrorCode(errorCode)) {
			foundIt = true;
		    } else {
			foundIt = false;
		    }
   
		    if (foundIt) {
			result.addGoodDetails(smh.getLocalString
					   ("tests.componentNameConstructor",
					    "For [ {0} ]",
					    new Object[] {compName.toString()}));
			result.addGoodDetails(smh.getLocalString
					      (getClass().getName() + ".passed",
					       "Error code [ {0} ] contains valid HTTP error code within web application [ {1} ]",
					       new Object[] {errorCode.toString(), descriptor.getName()}));
		    } else {
			if (!oneFailed) {
			    oneFailed = true;
			}
			result.addErrorDetails(smh.getLocalString
					   ("tests.componentNameConstructor",
					    "For [ {0} ]",
					    new Object[] {compName.toString()}));
			result.addErrorDetails(smh.getLocalString
					       (getClass().getName() + ".failed",
						"Error: error-code [ {0} ] does not contain valid HTTP error code within web application [ {1} ]",
						new Object[] {errorCode.toString(), descriptor.getName()}));
		    }
                } else {
                    // maybe ErrorCode is not used 'cause we are using Exception
                    // if that is the case, then test is N/A,
		    result.addNaDetails(smh.getLocalString
					   ("tests.componentNameConstructor",
					    "For [ {0} ]",
					    new Object[] {compName.toString()}));
                    result.addNaDetails(smh.getLocalString
					(getClass().getName() + ".notApplicable1",
					 "Not Applicable: Error-code is [ {0} ], using [ {1} ] instead within web application [ {2} ]",
					 new Object[] {new Integer(errorpage.getErrorCode()), exceptionType, descriptor.getName()}));
                    oneNA++;
                }
	    }
	    if (oneFailed) {
		result.setStatus(Result.FAILED);
            } else if (oneNA == oneErrorCode) {
                result.setStatus(Result.NOT_APPLICABLE);
	    } else {
		result.setStatus(Result.PASSED);
	    }
	} else {
	    result.addNaDetails(smh.getLocalString
					   ("tests.componentNameConstructor",
					    "For [ {0} ]",
					    new Object[] {compName.toString()}));
	    result.notApplicable(smh.getLocalString
				 (getClass().getName() + ".notApplicable",
				  "There are no error-code elements within the web archive [ {0} ]",
				  new Object[] {descriptor.getName()}));
	}

	return result;
    }
}
