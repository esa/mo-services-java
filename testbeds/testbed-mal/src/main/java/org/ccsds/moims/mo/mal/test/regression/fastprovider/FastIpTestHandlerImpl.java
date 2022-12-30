/* ----------------------------------------------------------------------------
 * Copyright (C) 2016      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
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
package org.ccsds.moims.mo.mal.test.regression.fastprovider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.malprototype.iptest.body.RequestMultiResponse;
import org.ccsds.moims.mo.malprototype.iptest.provider.IPTestInheritanceSkeleton;
import org.ccsds.moims.mo.malprototype.iptest.provider.InvokeInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.InvokeMultiInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.ProgressInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.ProgressMultiInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.TestInvokeEmptyBodyInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.TestProgressEmptyBodyInteraction;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestDefinition;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestResult;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishUpdate;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class FastIpTestHandlerImpl extends IPTestInheritanceSkeleton {

    public void testSubmit(IPTestDefinition iptd, MALInteraction mali) throws MALInteractionException, MALException {
        if (null == iptd) {
            throw new MALInteractionException(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, null));
        }
    }

    public String request(IPTestDefinition iptd, MALInteraction mali) throws MALInteractionException, MALException {
        if (null == iptd) {
            throw new MALInteractionException(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, null));
        }

        return "";
    }

    public void invoke(IPTestDefinition iptd, InvokeInteraction ri) throws MALInteractionException, MALException {
        try {
            LoggingBase.logMessage("invoke called");
            Thread.sleep(1000);

            if (null == iptd) {
                ri.sendError(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, null));
                return;
            }

            LoggingBase.logMessage("   returning ack");
            ri.sendAcknowledgement("");

            Thread.sleep(1000);
            LoggingBase.logMessage("   returning response");
            ri.sendResponse("");

            Thread.sleep(1000);
            LoggingBase.logMessage("   returned response");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void progress(IPTestDefinition iptd, ProgressInteraction qi) throws MALInteractionException, MALException {
        try {
            LoggingBase.logMessage("progress called");
            Thread.sleep(1000);

            if (null == iptd) {
                qi.sendError(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, null));
                return;
            }

            LoggingBase.logMessage("   returning ack");
            qi.sendAcknowledgement("");

            Thread.sleep(1000);
            LoggingBase.logMessage("   returning update 1");
            qi.sendUpdate(1);

            Thread.sleep(1000);
            LoggingBase.logMessage("   returning update 2");
            qi.sendUpdate(2);

            Thread.sleep(1000);
            LoggingBase.logMessage("   returning response");
            qi.sendResponse("");

            Thread.sleep(1000);
            LoggingBase.logMessage("   returned response");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void send(IPTestDefinition iptd, MALInteraction mali) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IPTestResult getResult(Element elmnt, MALInteraction mali) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void publishUpdates(TestPublishUpdate tpu, MALInteraction mali) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void publishRegister(TestPublishRegister tpr, MALInteraction mali) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void publishDeregister(TestPublishDeregister tpd, MALInteraction mali) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void testMultipleNotify(TestPublishUpdate tpu, MALInteraction mali) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendMulti(IPTestDefinition iptd, Element elmnt, MALInteraction mali) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void submitMulti(IPTestDefinition iptd, Element elmnt, MALInteraction mali) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RequestMultiResponse requestMulti(IPTestDefinition iptd, Element elmnt, MALInteraction mali) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void invokeMulti(IPTestDefinition iptd, Element elmnt, InvokeMultiInteraction imi) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void progressMulti(IPTestDefinition iptd, Element elmnt, ProgressMultiInteraction pmi) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void testRequestEmptyBody(IPTestDefinition iptd, MALInteraction mali) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void testInvokeEmptyBody(IPTestDefinition iptd, TestInvokeEmptyBodyInteraction tiebi) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void testProgressEmptyBody(IPTestDefinition iptd, TestProgressEmptyBodyInteraction tpebi) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
