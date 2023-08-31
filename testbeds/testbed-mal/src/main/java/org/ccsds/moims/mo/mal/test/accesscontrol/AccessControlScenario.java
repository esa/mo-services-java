/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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
package org.ccsds.moims.mo.mal.test.accesscontrol;

import java.util.Map;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.testbed.suite.BooleanCondition;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.ParseHelper;
import org.ccsds.moims.mo.testbed.util.StopTest;

/**
 *
 */
public class AccessControlScenario extends LoggingBase {

    public static final SessionType SESSION = SessionType.LIVE;
    public static final Identifier SESSION_NAME = new Identifier("LIVE");
    public static final Identifier NETWORK_ZONE = new Identifier("networkZone");
    public static final UInteger PRIORITY = new UInteger(1);
    public static final QoSLevel QOS_LEVEL = QoSLevel.ASSURED;
    public static final IdentifierList DOMAIN = new IdentifierList();
    public static final Blob AUTHENTICATION_ID = new Blob("ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes());
    private LocalMALInstance.IPTestConsumer ipTestConsumer;
    private IPTestStub ipTestStub;

    public boolean securityManagerHasBeenCreated() throws Exception {
        if (!TestAccessControlFactory.securityManagerHasBeenCreated()) {
            // we can't continue so tell fitnesse to stop
            throw new StopTest("Test Security Manager has not been created therefore test cannot continue!");
        }

        ipTestConsumer = LocalMALInstance.instance().ipTestStub(AUTHENTICATION_ID, DOMAIN, 
                NETWORK_ZONE, SESSION, SESSION_NAME, QOS_LEVEL, PRIORITY, new NamedValueList(), false);
        ipTestStub = ipTestConsumer.getStub();

        return true;
    }

    public void switchOnMessageLogging() {
        TestAccessControlFactory.managerInstance().switchOnMessageLogging();
    }

    public void switchOnAuthenticationModification() {
        TestAccessControlFactory.managerInstance().switchOnAuthenticationModifications(true);
    }

    public void resetMessageCount() {
        TestAccessControlFactory.managerInstance().resetMessageCount();
    }

    public boolean aSendInteractionCompletes() throws MALInteractionException, MALException {
        TransportInterceptor.instance().resetTransmitCount(InteractionType.SEND);
        TransportInterceptor.instance().resetReceiveCount(InteractionType.SEND);
        ipTestStub.send(null);
        return true;
    }

    public boolean aSubmitInteractionCompletes() throws MALInteractionException, MALException {
        TransportInterceptor.instance().resetTransmitCount(InteractionType.SUBMIT);
        TransportInterceptor.instance().resetReceiveCount(InteractionType.SUBMIT);
        ipTestStub.testSubmit(null);
        return true;
    }

    public boolean aRequestInteractionCompletes() throws MALInteractionException, MALException {
        TransportInterceptor.instance().resetTransmitCount(InteractionType.REQUEST);
        TransportInterceptor.instance().resetReceiveCount(InteractionType.REQUEST);
        ipTestStub.request(null);
        return true;
    }

    public boolean anInvokeInteractionCompletes() throws MALInteractionException, MALException {
        MonitorListener monitor = new MonitorListener();

        TransportInterceptor.instance().resetTransmitCount(InteractionType.INVOKE);
        TransportInterceptor.instance().resetReceiveCount(InteractionType.INVOKE);
        ipTestStub.invoke(null, monitor);

        boolean retVal = false;
        try {
            retVal = monitor.cond.waitFor(10000);
        } catch (InterruptedException ex) {
            // do nothing, we are expecting this
        }

        boolean result = monitor.receivedCompletion();

        logMessage("anInvokeInteractionCompletes: monitor received message (" + retVal + ") and good invoke message returned (" + result + ")");

        return retVal && result;
    }

    public boolean aProgressInteractionCompletes() throws MALInteractionException, MALException {
        MonitorListener monitor = new MonitorListener();

        TransportInterceptor.instance().resetTransmitCount(InteractionType.PROGRESS);
        TransportInterceptor.instance().resetReceiveCount(InteractionType.PROGRESS);
        ipTestStub.progress(null, monitor);

        boolean retVal = false;
        try {
            retVal = monitor.cond.waitFor(10000);
        } catch (InterruptedException ex) {
            // do nothing, we are expecting this
        }

        return retVal && (monitor.receivedCompletion());
    }

    public int accessControlMessageCount() {
        TestAccessControlFactory.managerInstance().sortMessages();
        return TestAccessControlFactory.managerInstance().messageCount();
    }

    public int messageAtIndexHasInteractionOf(int index) {
        return TestAccessControlFactory.managerInstance().getMessageInteraction(index);
    }

    public byte messageAtIndexHasStageOf(int index) {
        return TestAccessControlFactory.managerInstance().getMessageStage(index);
    }

    public boolean messageAtIndexIsNotAnError(int index) {
        return false == TestAccessControlFactory.managerInstance().isErrorMessage(index);
    }

    public void switchOffMessageLogging() {
        TestAccessControlFactory.managerInstance().switchOffMessageLogging();
    }

    public void switchOffAuthenticationModification() {
        TestAccessControlFactory.managerInstance().switchOnAuthenticationModifications(false);
    }

    public int transmitCountForInteraction(String interactionType) throws Exception {
        InteractionType ip = ParseHelper.parseInteractionType(interactionType);
        return TransportInterceptor.instance().getTransmitRequestCount(ip);
    }

    public int receiveCountForInteraction(String interactionType) throws Exception {
        InteractionType ip = ParseHelper.parseInteractionType(interactionType);
        return TransportInterceptor.instance().getReceiveCount(ip);
    }

    public boolean transmittedMessageAuthIdIsReversedComparedToSecurityIndex(int sindex) throws Exception {
        MALMessage msg = TransportInterceptor.instance().getLastSentMessage(ipTestConsumer.getConsumer().getURI());
        byte[] id = TestAccessControlFactory.managerInstance().getAuthenticationIdentifier(sindex);

        return receivedAuthenticationIdentifierIsReversed(msg, id);
    }

    public boolean receivedMessageAtIndexAuthIdIsReversedComparedToSecurityIndex(int rindex, int sindex) throws Exception {
        MALMessage msg = TransportInterceptor.instance().getLastReceivedMessage(rindex - 1);
        byte[] id = TestAccessControlFactory.managerInstance().getAuthenticationIdentifier(sindex);

        return receivedAuthenticationIdentifierIsReversed(msg, id);
    }

    protected boolean receivedAuthenticationIdentifierIsReversed(MALMessage firstMsg, byte[] secondId) throws Exception {
        if ((null != firstMsg) && (null != firstMsg.getHeader().getAuthenticationId())) {
            byte[] firstId = firstMsg.getHeader().getAuthenticationId().getValue();

            if ((null != firstId) && (null != secondId) && (firstId.length == secondId.length) && (0 < firstId.length)) {
                final int count = (firstId.length) - 1;
                boolean bGood = true;
                for (int index = 0; bGood && (index <= count); ++index) {
                    bGood = firstId[index] == secondId[count - index];
                }

                return bGood;
            }
        }

        return false;
    }

    private static final class MonitorListener extends IPTestAdapter {

        private final BooleanCondition cond = new BooleanCondition();
        private boolean receivedCompletion = false;

        @Override
        public void invokeResponseErrorReceived(MALMessageHeader msgHeader,
                MOErrorException error, Map qosProperties) {
            cond.set();
        }

        @Override
        public void invokeResponseReceived(MALMessageHeader msgHeader,
                String bodyElement1, Map qosProperties) {
            receivedCompletion = true;
            cond.set();
        }

        @Override
        public void progressResponseErrorReceived(MALMessageHeader msgHeader,
                MOErrorException error, Map qosProperties) {
            cond.set();
        }

        @Override
        public void progressResponseReceived(MALMessageHeader msgHeader,
                String bodyElement1, Map qosProperties) {
            receivedCompletion = true;
            cond.set();
        }

        /*
    public void invokeErrorReceived(MessageHeader msgHeader, StandardError error)
    {
      cond.set();
    }

    public void invokeResponseReceived(MessageHeader msgHeader, String _String)
    {
      receivedCompletion = true;
      cond.set();
    }

    public void progressErrorReceived(MessageHeader msgHeader, StandardError error)
    {
      cond.set();
    }

    public void progressResponseReceived(MessageHeader msgHeader, String result)
    {
      receivedCompletion = true;
      cond.set();
    }
         */
        public synchronized boolean receivedCompletion() {
            return receivedCompletion;
        }
    }
}
