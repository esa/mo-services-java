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
package org.ccsds.moims.mo.mal.test.patterns;

import java.util.Hashtable;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.test.util.AssertionHelper;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALTransmitMultipleErrorException;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo;
import org.ccsds.moims.mo.malprototype.iptest.body.RequestMultiResponse;
import org.ccsds.moims.mo.malprototype.iptest.provider.*;
import org.ccsds.moims.mo.malprototype.structures.*;
import org.ccsds.moims.mo.testbed.transport.TestMessageHeader;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class IPTestHandlerImpl extends IPTestInheritanceSkeleton {

    // The code will wait on this PERIOD: ~630 times !
    private final static int PERIOD = 250; // in ms
    private final Hashtable<PublishInteractionListenerKey, MALPublishInteractionListener> publishInteractionListeners;
    private final Hashtable<PublisherKey, MonitorPublisher> publishers;
    private final Hashtable<PublisherKey, MonitorMultiPublisher> publishersMulti;

    protected AssertionList assertions;
    private Identifier transactionId;
    private String ipTestProviderFileName;

    public IPTestHandlerImpl() {
        publishInteractionListeners = new Hashtable<>();
        publishers = new Hashtable<>();
        publishersMulti = new Hashtable<>();
        ipTestProviderFileName = IPTestServiceInfo.IPTEST_SERVICE_NAME.getValue();
    }

    public String getIpTestProviderFileName() {
        return ipTestProviderFileName;
    }

    public void setIpTestProviderWithSharedBrokerFileName(String ipTestProviderFileName) {
        this.ipTestProviderFileName = ipTestProviderFileName;
    }

    protected void resetAssertions() {
        LoggingBase.logMessage("IPTestHandlerImpl.resetAssertions()");
        assertions = new AssertionList();
    }

    public void send(IPTestDefinition _IPTestDefinition, MALInteraction interaction) throws MALException {
        // to do
    }

    public void sendMulti(IPTestDefinition _IPTestDefinition0, Element _Element1,
            MALInteraction interaction) throws MALInteractionException, MALException {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

    public void testSubmit(IPTestDefinition _IPTestDefinition, MALInteraction interaction) throws MALInteractionException {
        if (null != _IPTestDefinition) {
            int transId = Integer.parseInt(_IPTestDefinition.getProcedureName());

            switch (transId) {
                case 1:
                    // do nothing, should just ack for us
                    break;
                case 2:
                    throw new MALInteractionException(new MALStandardError(new UInteger(999), new Union("No error")));
                case 3:
                    // do nothing, should just ack for us
                    break;
                case 4:
                    throw new MALInteractionException(new MALStandardError(new UInteger(999), new Union("No error")));
                default:
                    throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER,
                            new Union("Unexpected procedure number of " + transId)));
            }
        }
    }

    public void submitMulti(IPTestDefinition _IPTestDefinition0, Element _Element1,
            MALInteraction interaction) throws MALInteractionException, MALException {
        testSubmit(_IPTestDefinition0, interaction);
    }

    public String request(IPTestDefinition _IPTestDefinition, MALInteraction interaction) throws MALInteractionException {
        if (null != _IPTestDefinition) {
            try {
                int transId = Integer.parseInt(_IPTestDefinition.getProcedureName());

                switch (transId) {
                    case 1:
                        // do nothing, should just ack for us
                        break;
                    case 2:
                        throw new MALInteractionException(new MALStandardError(new UInteger(999), new Union("No error")));
                    case 3:
                        // do nothing, should just ack for us
                        break;
                    case 4:
                        throw new MALInteractionException(new MALStandardError(new UInteger(999), new Union("No error")));
                    default:
                        throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER,
                                new Union("Unexpected procedure number of " + transId)));
                }
            } catch (NumberFormatException ex) {
                // ignore, this is expected for some interactions
            }
        }

        return "Hello";
    }

    public RequestMultiResponse requestMulti(IPTestDefinition _IPTestDefinition0, Element _Element1,
            MALInteraction interaction) throws MALInteractionException, MALException {
        return new RequestMultiResponse(request(_IPTestDefinition0, interaction), null);
    }

    public void testRequestEmptyBody(IPTestDefinition _IPTestDefinition,
            MALInteraction interaction) throws MALInteractionException, MALException {
        request(_IPTestDefinition, interaction);
    }

    public void invoke(IPTestDefinition _IPTestDefinition, InvokeInteraction interaction)
            throws MALInteractionException, MALException {
        if (null == _IPTestDefinition) {
            // this is the access control test then
            interaction.sendAcknowledgement(null);

            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
                // do nothing
            }

            interaction.sendResponse(null);
        } else {
            int transId = Integer.parseInt(_IPTestDefinition.getProcedureName());

            try {
                switch (transId) {
                    case 1:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 2:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    case 3:
                        interaction.sendError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    case 4:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 5:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    case 6:
                        interaction.sendError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    case 7:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendResponse("");
                        break;
                    default:
                        throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER,
                                new Union("Unexpected procedure number of " + transId)));
                }
            } catch (Exception ex) {
                // do nothing
            }
        }
    }

    public void invokeMulti(IPTestDefinition _IPTestDefinition0, Element _Element1,
            InvokeMultiInteraction interaction) throws MALInteractionException, MALException {
        invoke(_IPTestDefinition0, new InvokeMultiToInvokeInteractionMapper(interaction));
    }

    public void testInvokeEmptyBody(IPTestDefinition _IPTestDefinition,
            TestInvokeEmptyBodyInteraction interaction) throws MALInteractionException, MALException {
        invoke(_IPTestDefinition, new InvokeEmptyToInvokeInteractionMapper(interaction));
    }

    public void progress(IPTestDefinition _IPTestDefinition, ProgressInteraction interaction)
            throws MALInteractionException, MALException {
        if (null == _IPTestDefinition) {
            // this is the access control test then
            interaction.sendAcknowledgement(null);

            try {
                Thread.sleep(PERIOD);
            } catch (Exception ex) {
                // do nothing
            }

            interaction.sendUpdate(null);
            interaction.sendUpdate(null);
            interaction.sendUpdate(null);

            try {
                Thread.sleep(PERIOD);
            } catch (Exception ex) {
                // do nothing
            }

            interaction.sendResponse(null);
        } else {
            int transId = Integer.parseInt(_IPTestDefinition.getProcedureName());

            try {
                switch (transId) {
                    case 1:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 2:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    case 3:
                        interaction.sendError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    case 4:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        Thread.sleep(PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 5:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        Thread.sleep(PERIOD);
                        interaction.sendUpdateError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    case 6:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        Thread.sleep(PERIOD);
                        interaction.sendError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    case 7:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 8:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    case 9:
                        interaction.sendError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    case 10:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        Thread.sleep(PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 11:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        Thread.sleep(PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 12:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        Thread.sleep(PERIOD);
                        interaction.sendUpdateError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    case 13:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        Thread.sleep(PERIOD);
                        interaction.sendUpdateError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    case 14:
                        interaction.sendAcknowledgement("");
                        Thread.sleep(PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        Thread.sleep(PERIOD);
                        interaction.sendError(new MALStandardError(new UInteger(999), new Union("No error")));
                        break;
                    default:
                        throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER,
                                new Union("Unexpected procedure number of " + transId)));
                }
            } catch (Exception ex) {
                // do nothing
            }
        }
    }

    public void progressMulti(IPTestDefinition _IPTestDefinition0, Element _Element1,
            ProgressMultiInteraction interaction) throws MALInteractionException, MALException {
        progress(_IPTestDefinition0, new ProgressMultiToProgressInteractionMapper(interaction));
    }

    public void testProgressEmptyBody(IPTestDefinition _IPTestDefinition,
            TestProgressEmptyBodyInteraction interaction) throws MALInteractionException, MALException {
        progress(_IPTestDefinition, new ProgressEmptyToProgressInteractionMapper(interaction));
    }

    public void publishRegister(TestPublishRegister _TestPublishRegister, MALInteraction interaction)
            throws MALInteractionException, MALException {
        LoggingBase.logMessage("IPTestHandlerImpl.publishRegister(" + _TestPublishRegister + ')');
        resetAssertions();
        PublishInteractionListenerKey key = new PublishInteractionListenerKey(
                _TestPublishRegister.getDomain(),
                _TestPublishRegister.getNetworkZone(),
                _TestPublishRegister.getSession(),
                _TestPublishRegister.getSessionName());
        MonitorPublishInteractionListener listener = getPublishInteractionListener(key);

        doPublishRegister(_TestPublishRegister, listener);
        // The Publish Register header is checked with a shared broker
        // (see test.patterns.pubsub.IPTestHandlerWithSharedBroker)
    }

    protected void doPublishRegister(TestPublishRegister _TestPublishRegister,
            MonitorPublishInteractionListener listener)
            throws MALInteractionException, MALException {
        LoggingBase.logMessage("IPTestHandlerImpl.doPublishRegister(" + _TestPublishRegister + ')');
        // Reset the listener (useless for this test as the header and error are not checked)
        listener.setHeader(null);
        listener.setError(null);

        try {
            if (_TestPublishRegister.getTestMultiType()) {
                MonitorMultiPublisher publisher = getMonitorMultiPublisher(
                        _TestPublishRegister.getDomain(),
                        _TestPublishRegister.getNetworkZone(),
                        _TestPublishRegister.getSession(),
                        _TestPublishRegister.getSessionName(),
                        _TestPublishRegister.getQos(),
                        _TestPublishRegister.getPriority());

                LoggingBase.logMessage("IPTestHandlerImpl.doPublishRegister: The keyNames are: " + _TestPublishRegister.getKeyNames());
                publisher.asyncRegister(_TestPublishRegister.getKeyNames(), listener);
            } else {
                MonitorPublisher publisher = getMonitorPublisher(
                        _TestPublishRegister.getDomain(),
                        _TestPublishRegister.getNetworkZone(),
                        _TestPublishRegister.getSession(),
                        _TestPublishRegister.getSessionName(),
                        _TestPublishRegister.getQos(),
                        _TestPublishRegister.getPriority());

                LoggingBase.logMessage("IPTestHandlerImpl.doPublishRegister: The keyNames are: " + _TestPublishRegister.getKeyNames());
                publisher.asyncRegister(_TestPublishRegister.getKeyNames(), listener);
            }
            listener.cond.waitFor(Configuration.WAIT_TIME_OUT);
        } catch (InterruptedException e) {
        }

        listener.cond.reset();
    }

    public void publishUpdates(TestPublishUpdate _TestPublishUpdate, MALInteraction interaction) throws MALException {
        LoggingBase.logMessage("IPTestHandlerImpl.publishUpdates(" + _TestPublishUpdate + ')');
        resetAssertions();

        TestUpdateList testUpdateList = _TestPublishUpdate.getUpdates();

        PublishInteractionListenerKey key = new PublishInteractionListenerKey(
                _TestPublishUpdate.getDomain(),
                _TestPublishUpdate.getNetworkZone(),
                _TestPublishUpdate.getSession(),
                _TestPublishUpdate.getSessionName());
        MonitorPublishInteractionListener listener = getPublishInteractionListener(key);
        Time timestamp = new Time(System.currentTimeMillis());

        // Reset the listener
        listener.setHeader(null);
        listener.setError(null);
        listener.cond.reset();

        MALStandardError raisedPublishError = null;

        // Set time stamp and Source URI
        FileBasedDirectory.URIpair uris = getProviderURIs();
        UpdateHeaderList updateHeaderList = _TestPublishUpdate.getUpdateHeaders();

        UShort opNumber = null;
        try {
            if (_TestPublishUpdate.getTestMultiType()) {
                opNumber = IPTestServiceInfo.MONITORMULTI_OP.getNumber();
                MonitorMultiPublisher publisher = getMonitorMultiPublisher(
                        _TestPublishUpdate.getDomain(),
                        _TestPublishUpdate.getNetworkZone(),
                        _TestPublishUpdate.getSession(),
                        _TestPublishUpdate.getSessionName(),
                        _TestPublishUpdate.getQos(),
                        _TestPublishUpdate.getPriority());
                /*boolean specialSubKey = false;
        try {
            specialSubKey = updateHeaderList.get(0).getKey().getSecondSubKey() == 1;
        } catch (NullPointerException ex) {} catch (IndexOutOfBoundsException ex) {}
        if (specialSubKey) {
            IntegerList integerUpdateList = new IntegerList();
            for (int i = 0; i < testUpdateList.size(); i++) {
                integerUpdateList.add(testUpdateList.get(i).getCounter());
            }
            publisher.publish(updateHeaderList, testUpdateList, integerUpdateList);
        } else {
            publisher.publish(updateHeaderList, testUpdateList, testUpdateList);
        }*/
                publisher.publish(updateHeaderList, testUpdateList, testUpdateList);
            } else {
                opNumber = IPTestServiceInfo.MONITOR_OP.getNumber();
                MonitorPublisher publisher = getMonitorPublisher(
                        _TestPublishUpdate.getDomain(),
                        _TestPublishUpdate.getNetworkZone(),
                        _TestPublishUpdate.getSession(),
                        _TestPublishUpdate.getSessionName(),
                        _TestPublishUpdate.getQos(),
                        _TestPublishUpdate.getPriority());
                publisher.publish(updateHeaderList, testUpdateList);
            }
        } catch (MALInteractionException exc) {
            LoggingBase.logMessage("Publish error: " + exc);
            raisedPublishError = exc.getStandardError();
        }

        // The Publish header is checked with a shared broker
        // (see test.patterns.pubsub.IPTestHandlerWithSharedBroker)
        try {
            // this will timeout unless there is an error
            listener.cond.waitFor(Configuration.WAIT_TIME_OUT);
        } catch (InterruptedException e) {
        }

        listener.cond.reset();

        if (_TestPublishUpdate.getErrorCode().getValue() != 999) {
            MALStandardError error;
            UInteger expectedErrorCode;
            Object expectedExtraInfo;
            if (_TestPublishUpdate.getIsException().booleanValue()) {
                error = raisedPublishError;
                expectedErrorCode = MALHelper.INCORRECT_STATE_ERROR_NUMBER;
                expectedExtraInfo = null;
            } else {
                TestMessageHeader expectedPublishErrorHeader = new TestMessageHeader(
                        uris.broker,
                        getBrokerAuthenticationId(),
                        uris.uri,
                        timestamp,
                        listener.getPublishRegisterQoSLevel(),
                        listener.getPublishRegisterPriority(),
                        _TestPublishUpdate.getDomain(),
                        _TestPublishUpdate.getNetworkZone(),
                        _TestPublishUpdate.getSession(),
                        _TestPublishUpdate.getSessionName(),
                        InteractionType.PUBSUB,
                        new UOctet(MALPubSubOperation._PUBLISH_STAGE),
                        listener.getPublishRegisterTransactionId(),
                        MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
                        IPTestServiceInfo.IPTEST_SERVICE_NUMBER,
                        opNumber,
                        MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
                        Boolean.TRUE);

                MALMessageHeader publishHeader = listener.getHeader();

                assertions.add(new Assertion("PubSub.checkPublishErrorHeader",
                        "Publish Error received", (publishHeader != null)));

                if (publishHeader == null) {
                    return;
                }

                AssertionHelper.checkHeader("PubSub.checkPublishErrorHeader",
                        assertions, publishHeader, expectedPublishErrorHeader);

                error = listener.getError();
                expectedErrorCode = _TestPublishUpdate.getErrorCode();
                expectedExtraInfo = _TestPublishUpdate.getFailedKeyValues();
            }

            assertions.add(new Assertion("PubSub.checkPublishError",
                    "Error received", (error != null)));

            if (error != null) {
                AssertionHelper.checkEquality("PubSub.checkPublishError",
                        assertions, "errorNumber",
                        error.getErrorNumber(),
                        expectedErrorCode);

                AssertionHelper.checkEquality("PubSub.checkPublishError",
                        assertions, "extraInfo", error.getExtraInformation(), expectedExtraInfo);
            }
        } else {
            MALMessageHeader publishHeader = listener.getHeader();

            assertions.add(new Assertion("PubSub.checkPublish",
                    "Successful Publish", (publishHeader == null)));
        }
    }

    public void publishDeregister(TestPublishDeregister _TestPublishDeregister, MALInteraction interaction)
            throws MALInteractionException, MALException {
        LoggingBase.logMessage("IPTestHandlerImpl.publishDeregister(" + _TestPublishDeregister + ')');
        resetAssertions();
        PublishInteractionListenerKey key = new PublishInteractionListenerKey(
                _TestPublishDeregister.getDomain(),
                _TestPublishDeregister.getNetworkZone(),
                _TestPublishDeregister.getSession(),
                _TestPublishDeregister.getSessionName());

        // Should reuse the listener to check the header
        //removePublishInteractionListener(key);
        //MonitorPublishInteractionListener listener = new MonitorPublishInteractionListener();
        MonitorPublishInteractionListener listener = getPublishInteractionListener(key);

        doPublishDeregister(_TestPublishDeregister, listener);
        // The Publish Deregister header is checked with a shared broker
        // (see test.patterns.pubsub.IPTestHandlerWithSharedBroker)

        if (_TestPublishDeregister.getTestMultiType()) {
            MonitorMultiPublisher publisher = getMonitorMultiPublisher(
                    _TestPublishDeregister.getDomain(),
                    _TestPublishDeregister.getNetworkZone(),
                    _TestPublishDeregister.getSession(),
                    _TestPublishDeregister.getSessionName(),
                    _TestPublishDeregister.getQos(),
                    _TestPublishDeregister.getPriority());
            publisher.close();
            removeMonitorMultiPublisher(
                    _TestPublishDeregister.getDomain(),
                    _TestPublishDeregister.getNetworkZone(),
                    _TestPublishDeregister.getSession(),
                    _TestPublishDeregister.getSessionName(),
                    _TestPublishDeregister.getQos(),
                    _TestPublishDeregister.getPriority());
            if (publishersMulti.isEmpty()) {
                publishInteractionListeners.clear();
            }
        } else {
            MonitorPublisher publisher = getMonitorPublisher(
                    _TestPublishDeregister.getDomain(),
                    _TestPublishDeregister.getNetworkZone(),
                    _TestPublishDeregister.getSession(),
                    _TestPublishDeregister.getSessionName(),
                    _TestPublishDeregister.getQos(),
                    _TestPublishDeregister.getPriority());
            publisher.close();
            removeMonitorPublisher(
                    _TestPublishDeregister.getDomain(),
                    _TestPublishDeregister.getNetworkZone(),
                    _TestPublishDeregister.getSession(),
                    _TestPublishDeregister.getSessionName(),
                    _TestPublishDeregister.getQos(),
                    _TestPublishDeregister.getPriority());
            if (publishers.isEmpty()) {
                publishInteractionListeners.clear();
            }
        }
    }

    protected void doPublishDeregister(TestPublishDeregister _TestPublishDeregister,
            MonitorPublishInteractionListener listener)
            throws MALInteractionException, MALException {
        try {
            if (_TestPublishDeregister.getTestMultiType()) {
                MonitorMultiPublisher publisher = getMonitorMultiPublisher(
                        _TestPublishDeregister.getDomain(),
                        _TestPublishDeregister.getNetworkZone(),
                        _TestPublishDeregister.getSession(),
                        _TestPublishDeregister.getSessionName(),
                        _TestPublishDeregister.getQos(),
                        _TestPublishDeregister.getPriority());
                publisher.asyncDeregister(listener);
            } else {
                MonitorPublisher publisher = getMonitorPublisher(
                        _TestPublishDeregister.getDomain(),
                        _TestPublishDeregister.getNetworkZone(),
                        _TestPublishDeregister.getSession(),
                        _TestPublishDeregister.getSessionName(),
                        _TestPublishDeregister.getQos(),
                        _TestPublishDeregister.getPriority());
                publisher.asyncDeregister(listener);
            }
            listener.cond.waitFor(Configuration.WAIT_TIME_OUT);
        } catch (InterruptedException e) {
        }

        listener.cond.reset();
    }

    public IPTestResult getResult(Element _Element, MALInteraction interaction) throws MALException {
        LoggingBase.logMessage("IPTest: assertions = " + assertions);
        return new IPTestResult(transactionId, assertions);
    }

    private MonitorPublishInteractionListener getPublishInteractionListener(PublishInteractionListenerKey key) {
        MonitorPublishInteractionListener listener
                = (MonitorPublishInteractionListener) publishInteractionListeners.get(key);
        if (listener == null) {
            listener = new MonitorPublishInteractionListener();
            publishInteractionListeners.put(key, listener);
        }
        return listener;
    }

    protected MonitorPublisher getMonitorPublisher(IdentifierList domain, Identifier networkZone,
            SessionType session, Identifier sessionName, QoSLevel qosLevel, UInteger priority) throws MALException {
        PublisherKey key = new PublisherKey(domain, networkZone, session, sessionName, qosLevel, priority);
        MonitorPublisher publisher = publishers.get(key);
        if (publisher == null) {
            publisher = createMonitorPublisher(domain, networkZone, session, sessionName, qosLevel,
                    new Hashtable(), priority);
            publishers.put(key, publisher);
        }
        return publisher;
    }

    private void removeMonitorPublisher(IdentifierList domain, Identifier networkZone,
            SessionType session, Identifier sessionName, QoSLevel qosLevel, UInteger priority) {
        PublisherKey key = new PublisherKey(domain, networkZone, session, sessionName, qosLevel, priority);
        publishers.remove(key);
    }

    protected MonitorMultiPublisher getMonitorMultiPublisher(IdentifierList domain, Identifier networkZone,
            SessionType session, Identifier sessionName, QoSLevel qosLevel, UInteger priority) throws MALException {
        PublisherKey key = new PublisherKey(domain, networkZone, session, sessionName, qosLevel, priority);
        MonitorMultiPublisher publisher = publishersMulti.get(key);
        if (publisher == null) {
            publisher = createMonitorMultiPublisher(domain, networkZone, session, sessionName, qosLevel,
                    new Hashtable(), priority);
            publishersMulti.put(key, publisher);
        }
        return publisher;
    }

    private void removeMonitorMultiPublisher(IdentifierList domain, Identifier networkZone,
            SessionType session, Identifier sessionName, QoSLevel qosLevel, UInteger priority) {
        PublisherKey key = new PublisherKey(domain, networkZone, session, sessionName, qosLevel, priority);
        publishersMulti.remove(key);
    }

    private void removePublishInteractionListener(PublishInteractionListenerKey key) {
        publishInteractionListeners.remove(key);
    }

    public MonitorPublishInteractionListener getPublishInteractionListener(
            IdentifierList domain, Identifier networkZone, SessionType session,
            Identifier sessionName) {
        PublishInteractionListenerKey key = new PublishInteractionListenerKey(
                domain, networkZone, session, sessionName);
        return getPublishInteractionListener(key);
    }

    protected FileBasedDirectory.URIpair getProviderURIs() {
        return FileBasedDirectory.loadURIs(ipTestProviderFileName);
    }

    protected Blob getBrokerAuthenticationId() {
        return FileBasedDirectory.loadPrivateBrokerAuthenticationId();
    }

    public void testMultipleNotify(TestPublishUpdate _TestPublishUpdate, MALInteraction interaction)
            throws MALInteractionException, MALException {
        resetAssertions();

        TransportInterceptor.instance().resetTransmitMultipleCount();

        MonitorPublisher publisher = getMonitorPublisher(
                _TestPublishUpdate.getDomain(),
                _TestPublishUpdate.getNetworkZone(),
                _TestPublishUpdate.getSession(),
                _TestPublishUpdate.getSessionName(),
                _TestPublishUpdate.getQos(),
                _TestPublishUpdate.getPriority());
        UpdateHeaderList updateHeaderList = _TestPublishUpdate.getUpdateHeaders();
        TestUpdateList testUpdateList = _TestPublishUpdate.getUpdates();

        MALException expectedException = null;
        try {
            publisher.publish(updateHeaderList, testUpdateList);
        } catch (MALTransmitMultipleErrorException exc) {
            expectedException = exc;
        }

        try {
            // this will timeout unless there is an error
            Thread.sleep(Configuration.WAIT_TIME_OUT);
        } catch (InterruptedException e) {
        }

        if (_TestPublishUpdate.getErrorCode().getValue() != 999) {
            assertions.add(new Assertion("TransmitMultipleError",
                    "Transmit Multiple Error received", (expectedException != null)));
        } else {
            int transmitMultipleRequestCount
                    = TransportInterceptor.instance().getTransmitMultipleRequestCount();
            int transmitMultipleResponseCount
                    = TransportInterceptor.instance().getTransmitMultipleResponseCount();

            // Check the Transmit Multiple interaction
            assertions.add(new Assertion("TestMultipleNotify", "TransmitMultiple request count "
                    + transmitMultipleRequestCount + " == 1", (transmitMultipleRequestCount == 1)));
            if (transmitMultipleRequestCount != 0) {
                MALMessage[] messages = TransportInterceptor.instance().getLastSentMessages();
                assertions.add(new Assertion("TestMultipleNotify",
                        "TransmitMultiple last sent messages not null", (messages != null)));
                assertions.add(new Assertion("TestMultipleNotify",
                        "TransmitMultiple last sent messages count "
                        + messages.length + " == 2", (messages.length == 2)));
            }

            assertions.add(new Assertion("TestMultipleNotify", "TransmitMultiple response count "
                    + transmitMultipleResponseCount + " == 1", (transmitMultipleResponseCount == 1)));
        }
    }

    static class InvokeMultiToInvokeInteractionMapper extends InvokeInteraction {

        private final InvokeMultiInteraction interaction;

        public InvokeMultiToInvokeInteractionMapper(InvokeMultiInteraction interaction) {
            super(interaction.getInteraction());

            this.interaction = interaction;
        }

        @Override
        public MALMessage sendAcknowledgement(String _String0) throws MALInteractionException, MALException {
            return interaction.sendAcknowledgement(_String0, null);
        }

        @Override
        public MALMessage sendResponse(String _String0) throws MALInteractionException, MALException {
            return interaction.sendResponse(_String0, null);
        }

        @Override
        public MALMessage sendError(MALStandardError error) throws MALInteractionException, MALException {
            return interaction.sendError(error);
        }
    }

    static class InvokeEmptyToInvokeInteractionMapper extends InvokeInteraction {

        private final TestInvokeEmptyBodyInteraction interaction;

        public InvokeEmptyToInvokeInteractionMapper(TestInvokeEmptyBodyInteraction interaction) {
            super(interaction.getInteraction());

            this.interaction = interaction;
        }

        @Override
        public MALMessage sendAcknowledgement(String _String0) throws MALInteractionException, MALException {
            return interaction.sendAcknowledgement();
        }

        @Override
        public MALMessage sendResponse(String _String0) throws MALInteractionException, MALException {
            return interaction.sendResponse();
        }

        @Override
        public MALMessage sendError(MALStandardError error) throws MALInteractionException, MALException {
            return interaction.sendError(error);
        }
    }

    static class ProgressMultiToProgressInteractionMapper extends ProgressInteraction {

        private final ProgressMultiInteraction interaction;

        public ProgressMultiToProgressInteractionMapper(ProgressMultiInteraction interaction) {
            super(interaction.getInteraction());

            this.interaction = interaction;
        }

        @Override
        public MALMessage sendAcknowledgement(String _String0) throws MALInteractionException, MALException {
            return interaction.sendAcknowledgement(_String0, null);
        }

        @Override
        public MALMessage sendUpdate(Integer _Integer0) throws MALInteractionException, MALException {
            return interaction.sendUpdate(_Integer0, null);
        }

        @Override
        public MALMessage sendResponse(String _String0) throws MALInteractionException, MALException {
            return interaction.sendResponse(_String0, null);
        }

        @Override
        public MALMessage sendError(MALStandardError error) throws MALInteractionException, MALException {
            return interaction.sendError(error);
        }

        @Override
        public MALMessage sendUpdateError(MALStandardError error) throws MALInteractionException, MALException {
            return interaction.sendUpdateError(error);
        }
    }

    static class ProgressEmptyToProgressInteractionMapper extends ProgressInteraction {

        private final TestProgressEmptyBodyInteraction interaction;

        public ProgressEmptyToProgressInteractionMapper(TestProgressEmptyBodyInteraction interaction) {
            super(interaction.getInteraction());

            this.interaction = interaction;
        }

        @Override
        public MALMessage sendAcknowledgement(String _String0) throws MALInteractionException, MALException {
            return interaction.sendAcknowledgement();
        }

        @Override
        public MALMessage sendUpdate(Integer _Integer0) throws MALInteractionException, MALException {
            return interaction.sendUpdate();
        }

        @Override
        public MALMessage sendResponse(String _String0) throws MALInteractionException, MALException {
            return interaction.sendResponse();
        }

        @Override
        public MALMessage sendError(MALStandardError error) throws MALInteractionException, MALException {
            return interaction.sendError(error);
        }

        @Override
        public MALMessage sendUpdateError(MALStandardError error) throws MALInteractionException, MALException {
            return interaction.sendUpdateError(error);
        }
    }
}
