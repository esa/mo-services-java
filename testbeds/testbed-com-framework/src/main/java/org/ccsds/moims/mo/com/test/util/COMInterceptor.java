/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Test bed
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.com.test.util;

import org.ccsds.moims.mo.mal.DeliveryTimedoutException;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;
import org.ccsds.moims.mo.mal.transport.MALTransmitMultipleErrorException;
import org.ccsds.moims.mo.testbed.transport.TestEndPoint;
import org.ccsds.moims.mo.testbed.transport.TestEndPointSendInterceptor;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class COMInterceptor implements TestEndPointSendInterceptor {

    @Override
    public void sendMessage(TestEndPoint ep, MALMessage msg) throws MALTransmitErrorException, MALException {
        LoggingBase.logMessage("COMInterceptor:sendMsg header" + msg.getHeader());

        // Only intercept following operations on activity test - send, tesSubmit, request, invoke, progress
        if (msg.getHeader().getService().getValue() == 4 && msg.getHeader().getOperation().getValue() >= 200
                && msg.getHeader().getOperation().getValue() <= 204 && msg.getBody().getElementCount() == 1) {
            LoggingBase.logMessage("COMInterceptor:sendMsg BODY " + msg.getBody().getElementCount());
            LoggingBase.logMessage("COMInterceptor:sendMsg BODY " + msg.getBody().getClass().toString());
            StringList myStr = (StringList) ((MALMessageBody) msg.getBody()).getBodyElement(0, null);
            LoggingBase.logMessage("COMInterceptor:sendMsg BODY string list = " + msg.getHeader().getTransactionId());

            if (myStr.contains("RELEASE_ERROR")) {
                throw new MALTransmitErrorException(msg.getHeader(),
                        new DeliveryTimedoutException(msg),
                        msg.getQoSProperties());
            }
        }
    }

    @Override
    public void sendMessages(TestEndPoint ep, MALMessage[] messages)
            throws MALTransmitMultipleErrorException, MALException {
    }
}
