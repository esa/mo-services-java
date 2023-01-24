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

import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.test.patterns.PatternTest;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.structures.IPTestDefinition;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class FastProviderScenario {

    private IPTestStub testService = null;

    public boolean initialiseConsumer() throws Exception {
        final java.util.Properties sysProps = System.getProperties();

        sysProps.setProperty("org.ccsds.moims.mo.mal.transport.protocol.fast",
                "org.ccsds.moims.mo.mal.test.regression.fastprovider.fasttransport.FastTransportFactoryImpl");

        System.setProperties(sysProps);

        MALProvider testServiceProvider = LocalMALInstance.instance().getProviderManager().createProvider("FastProvider",
                "fast",
                IPTestHelper.IPTEST_SERVICE,
                new Blob("".getBytes()),
                new FastIpTestHandlerImpl(),
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1),
                System.getProperties(),
                true,
                null);

        MALConsumer testConsumer = LocalMALInstance.instance().getConsumerManager().createConsumer((String) null,
                testServiceProvider.getURI(),
                testServiceProvider.getURI(),
                IPTestHelper.IPTEST_SERVICE,
                new Blob("".getBytes()),
                new IdentifierList(),
                new Identifier(""),
                SessionType.LIVE,
                new Identifier(""),
                QoSLevel.ASSURED,
                System.getProperties(),
                new UInteger(0));

        testService = new IPTestStub(testConsumer);

        return true;
    }

    public boolean normalSubmitCompletesWithoutAnError() throws Exception {
        LoggingBase.logMessage("Calling SUBMIT correctly");
        testService.testSubmit(new IPTestDefinition());

        return true;
    }

    public boolean errorSubmitCompletesWithAnError() throws Exception {
        try {
            LoggingBase.logMessage("Calling SUBMIT incorrectly");
            testService.testSubmit(null);
            LoggingBase.logMessage("ERROR: Calling SUBMIT incorrectly did not raise an error");
            return false;
        } catch (MALInteractionException ex) {
            LoggingBase.logMessage("Received ACK ERROR correctly");
        }

        return true;
    }

    public boolean normalRequestCompletesWithoutAnError() throws Exception {
        LoggingBase.logMessage("Calling REQUEST correctly");
        testService.request(new IPTestDefinition());

        return true;
    }

    public boolean errorRequestCompletesWithAnError() throws Exception {
        try {
            LoggingBase.logMessage("Calling REQUEST incorrectly");
            testService.request(null);
            LoggingBase.logMessage("ERROR: Calling REQUEST incorrectly did not raise an error");
            return false;
        } catch (MALInteractionException ex) {
            LoggingBase.logMessage("Received ACK ERROR correctly");
        }

        return true;
    }

    public boolean normalInvokeCompletesWithoutAnError() throws Exception {
        PatternTest.ResponseListener monitor = new PatternTest.ResponseListener("FastProvider", 1);

        LoggingBase.logMessage("Calling INVOKE");
        testService.invoke(new IPTestDefinition(), monitor);
        LoggingBase.logMessage("Received ACK");

        boolean retVal = false;

        try {
            LoggingBase.logMessage("FastProvider.waiting for responses");
            retVal = monitor.getCond().waitFor(10000);
        } catch (InterruptedException ex) {
            // do nothing, we are expecting this
        }

        boolean ackNotReceived = null == monitor.invokeAckReceivedMsgHeader;
        boolean responseReceived = null != monitor.invokeResponseReceivedMsgHeader;

        LoggingBase.logMessage("FastProvider.waiting(" + retVal + ")");
        LoggingBase.logMessage("FastProvider.checkCorrectNumberOfReceivedMessages(" + monitor.checkCorrectNumberOfReceivedMessages() + ")");
        LoggingBase.logMessage("FastProvider.ackNotReceived(" + ackNotReceived + ")");
        LoggingBase.logMessage("FastProvider.responseReceived(" + responseReceived + ")");

        return retVal && monitor.checkCorrectNumberOfReceivedMessages() && ackNotReceived && responseReceived;
    }

    public boolean errorInvokeCompletesWithAnError() throws Exception {
        PatternTest.ResponseListener monitor = new PatternTest.ResponseListener("FastProvider", 0);

        boolean retVal = false;

        try {
            LoggingBase.logMessage("Calling INVOKE incorrectly");
            testService.invoke(null, monitor);
            LoggingBase.logMessage("ERROR: Calling INVOKE incorrectly did not raise an error");
        } catch (MALInteractionException ex) {
            LoggingBase.logMessage("Received ACK ERROR correctly");
            retVal = true;
        }

        LoggingBase.logMessage("FastProvider.waiting(" + retVal + ")");

        if (retVal) {
            try {
                LoggingBase.logMessage("FastProvider.waiting for responses");
                retVal = !monitor.getCond().waitFor(1000);
            } catch (InterruptedException ex) {
                retVal = false;
            }
        }

        boolean ackNotReceived = (null == monitor.invokeAckReceivedMsgHeader) && (null == monitor.invokeAckErrorReceivedMsgHeader);
        boolean responseNotReceived = (null == monitor.invokeResponseReceivedMsgHeader) && (null == monitor.invokeResponseErrorReceivedMsgHeader);

        LoggingBase.logMessage("FastProvider.waiting(" + retVal + ")");
        LoggingBase.logMessage("FastProvider.checkCorrectNumberOfReceivedMessages(" + monitor.checkCorrectNumberOfReceivedMessages() + ")");
        LoggingBase.logMessage("FastProvider.ackNotReceived(" + ackNotReceived + ")");
        LoggingBase.logMessage("FastProvider.responseNotReceived(" + responseNotReceived + ")");

        return retVal && monitor.checkCorrectNumberOfReceivedMessages() && ackNotReceived && responseNotReceived;
    }

    public boolean normalProgressCompletesWithoutAnError() throws Exception {
        PatternTest.ResponseListener monitor = new PatternTest.ResponseListener("FastProvider", 3);

        LoggingBase.logMessage("Calling PROGRESS");
        testService.progress(new IPTestDefinition(), monitor);
        LoggingBase.logMessage("Received ACK");

        boolean retVal = false;

        try {
            LoggingBase.logMessage("FastProvider.waiting for responses");
            retVal = monitor.getCond().waitFor(10000);
        } catch (InterruptedException ex) {
            // do nothing, we are expecting this
        }

        boolean ackNotReceived = null == monitor.progressAckReceivedMsgHeader;
        boolean update1Received = null != monitor.progressUpdate1ReceivedMsgHeader;
        boolean update2Received = null != monitor.progressUpdate2ReceivedMsgHeader;
        boolean responseReceived = null != monitor.progressResponseReceivedMsgHeader;

        LoggingBase.logMessage("FastProvider.waiting(" + retVal + ")");
        LoggingBase.logMessage("FastProvider.checkCorrectNumberOfReceivedMessages(" + monitor.checkCorrectNumberOfReceivedMessages() + ")");
        LoggingBase.logMessage("FastProvider.ackNotReceived(" + ackNotReceived + ")");
        LoggingBase.logMessage("FastProvider.update1Received(" + update1Received + ")");
        LoggingBase.logMessage("FastProvider.update2Received(" + update2Received + ")");
        LoggingBase.logMessage("FastProvider.responseReceived(" + responseReceived + ")");

        return retVal && monitor.checkCorrectNumberOfReceivedMessages() && ackNotReceived && update1Received && update2Received && responseReceived;
    }

    public boolean errorProgressCompletesWithAnError() throws Exception {
        PatternTest.ResponseListener monitor = new PatternTest.ResponseListener("FastProvider", 0);

        boolean retVal = false;

        try {
            LoggingBase.logMessage("Calling PROGRESS incorrectly");
            testService.progress(null, monitor);
            LoggingBase.logMessage("ERROR: Calling PROGRESS incorrectly did not raise an error");
        } catch (MALInteractionException ex) {
            LoggingBase.logMessage("Received ACK ERROR correctly");
            retVal = true;
        }

        LoggingBase.logMessage("FastProvider.waiting(" + retVal + ")");

        if (retVal) {
            try {
                LoggingBase.logMessage("FastProvider.waiting for responses");
                retVal = !monitor.getCond().waitFor(1000);
            } catch (InterruptedException ex) {
                retVal = false;
            }
        }

        boolean ackNotReceived = (null == monitor.progressAckReceivedMsgHeader) && (null == monitor.progressAckErrorReceivedMsgHeader);
        boolean update1NotReceived = (null == monitor.progressUpdate1ReceivedMsgHeader) && (null == monitor.progressUpdateErrorReceivedMsgHeader);
        boolean update2NotReceived = (null == monitor.progressUpdate2ReceivedMsgHeader);
        boolean responseNotReceived = (null == monitor.progressResponseReceivedMsgHeader) && (null == monitor.progressResponseErrorReceivedMsgHeader);

        LoggingBase.logMessage("FastProvider.waiting(" + retVal + ")");
        LoggingBase.logMessage("FastProvider.checkCorrectNumberOfReceivedMessages(" + monitor.checkCorrectNumberOfReceivedMessages() + ")");
        LoggingBase.logMessage("FastProvider.ackNotReceived(" + ackNotReceived + ")");
        LoggingBase.logMessage("FastProvider.update1Received(" + update1NotReceived + ")");
        LoggingBase.logMessage("FastProvider.update2Received(" + update2NotReceived + ")");
        LoggingBase.logMessage("FastProvider.responseReceived(" + responseNotReceived + ")");

        return retVal && monitor.checkCorrectNumberOfReceivedMessages() && ackNotReceived && update1NotReceived && update2NotReceived && responseNotReceived;
    }
}
