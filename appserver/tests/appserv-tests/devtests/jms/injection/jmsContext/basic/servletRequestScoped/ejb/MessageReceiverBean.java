/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.test.jms.injection.ejb;

import jakarta.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import jakarta.jms.*;

/**
 *
 * @author LILIZHAO
 */
@Stateless(mappedName="MessageReceiverBean/remote")
public class MessageReceiverBean implements MessageReceiverRemote {
    @Resource(mappedName = "jms/jms_unit_test_Queue")
    private Queue queue;

    @Inject
    @JMSConnectionFactory("jms/jms_unit_test_QCF")
    @JMSSessionMode(JMSContext.AUTO_ACKNOWLEDGE)
    private JMSContext jmsContext;

    @Override
    public boolean checkMessage(String[] texts) {
        try {
            JMSConsumer consumer = jmsContext.createConsumer(queue);
            boolean[] found = new boolean[texts.length];
            for (int i=0; i<texts.length; i++) {
                Message msg = consumer.receive(30000L);
                if (msg instanceof TextMessage) {
                    String content = ((TextMessage) msg).getText();
                    for (int j=0; j<texts.length; j++) {
                        if (!found[j] && texts[i].equals(content))
                            found[j] = true;
                            break;
                    }
                }
            }
            boolean result = true;
            for (int i=0; i<found.length; i++)
                result = result & found[i];
            return result;
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }
}
