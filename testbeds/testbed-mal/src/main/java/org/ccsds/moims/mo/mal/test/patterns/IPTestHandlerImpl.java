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

import java.util.HashMap;
import java.util.Hashtable;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
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
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 * Provider side
 */
public class IPTestHandlerImpl extends IPTestInheritanceSkeleton {

    private final HashMap<PublisherKey, MonitorPublisher> publishers;
    private final HashMap<PublisherKey, MonitorMultiPublisher> publishersMulti;
    protected MonitorPublishInteractionListener defaultListener = new MonitorPublishInteractionListener();

    protected AssertionList assertions;
    private Identifier transactionId;
    private String ipTestProviderFileName;

    public IPTestHandlerImpl() {
        publishers = new HashMap<>();
        publishersMulti = new HashMap<>();
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

    @Override
    public void send(IPTestDefinition _IPTestDefinition, MALInteraction interaction) throws MALException {
        // to do
    }

    @Override
    public void sendMulti(IPTestDefinition _IPTestDefinition0, Element _Element1,
            MALInteraction interaction) throws MALInteractionException, MALException {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void testSubmit(IPTestDefinition _IPTestDefinition, MALInteraction interaction) throws MALInteractionException {
        if (null != _IPTestDefinition) {
            int transId = Integer.parseInt(_IPTestDefinition.getProcedureName());

            switch (transId) {
                case 1:
                    // do nothing, should just ack for us
                    break;
                case 2:
                    throw new MALInteractionException(new MOErrorException(new UInteger(999), new Union("No error")));
                case 3:
                    // do nothing, should just ack for us
                    break;
                case 4:
                    throw new MALInteractionException(new MOErrorException(new UInteger(999), new Union("No error")));
                default:
                    throw new MALInteractionException(new MOErrorException(MALHelper.INTERNAL_ERROR_NUMBER,
                            new Union("Unexpected procedure number of " + transId)));
            }
        }
    }

    @Override
    public void submitMulti(IPTestDefinition _IPTestDefinition0, Element _Element1,
            MALInteraction interaction) throws MALInteractionException, MALException {
        testSubmit(_IPTestDefinition0, interaction);
    }

    @Override
    public String request(IPTestDefinition _IPTestDefinition, MALInteraction interaction) throws MALInteractionException {
        if (null != _IPTestDefinition) {
            try {
                int transId = Integer.parseInt(_IPTestDefinition.getProcedureName());

                switch (transId) {
                    case 1:
                        // do nothing, should just ack for us
                        break;
                    case 2:
                        throw new MALInteractionException(new MOErrorException(new UInteger(999), new Union("No error")));
                    case 3:
                        // do nothing, should just ack for us
                        break;
                    case 4:
                        throw new MALInteractionException(new MOErrorException(new UInteger(999), new Union("No error")));
                    default:
                        throw new MALInteractionException(new MOErrorException(MALHelper.INTERNAL_ERROR_NUMBER,
                                new Union("Unexpected procedure number of " + transId)));
                }
            } catch (NumberFormatException ex) {
                // ignore, this is expected for some interactions
            }
        }

        return "Hello";
    }

    @Override
    public RequestMultiResponse requestMulti(IPTestDefinition _IPTestDefinition0, Element _Element1,
            MALInteraction interaction) throws MALInteractionException, MALException {
        return new RequestMultiResponse(request(_IPTestDefinition0, interaction), null);
    }

    @Override
    public void testRequestEmptyBody(IPTestDefinition _IPTestDefinition,
            MALInteraction interaction) throws MALInteractionException, MALException {
        request(_IPTestDefinition, interaction);
    }

    @Override
    public void invoke(IPTestDefinition _IPTestDefinition, InvokeInteraction interaction)
            throws MALInteractionException, MALException {
        if (_IPTestDefinition == null) {
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
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 2:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendError(new MOErrorException(new UInteger(999), new Union("No error")));
                        break;
                    case 3:
                        interaction.sendError(new MOErrorException(new UInteger(999), new Union("No error")));
                        break;
                    case 4:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 5:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendError(new MOErrorException(new UInteger(999), new Union("No error")));
                        break;
                    case 6:
                        interaction.sendError(new MOErrorException(new UInteger(999), new Union("No error")));
                        break;
                    case 7:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 8:
                        interaction.sendAcknowledgement("");
                        // the interaction has no final message to match the test case
                        break;
                    default:
                        throw new MALInteractionException(new MOErrorException(MALHelper.INTERNAL_ERROR_NUMBER,
                                new Union("Unexpected procedure number of " + transId)));
                }
            } catch (Exception ex) {
                // do nothing
            }
        }
    }

    @Override
    public void invokeMulti(IPTestDefinition _IPTestDefinition0, Element _Element1,
            InvokeMultiInteraction interaction) throws MALInteractionException, MALException {
        invoke(_IPTestDefinition0, new InvokeMultiToInvokeInteractionMapper(interaction));
    }

    @Override
    public void testInvokeEmptyBody(IPTestDefinition _IPTestDefinition,
            TestInvokeEmptyBodyInteraction interaction) throws MALInteractionException, MALException {
        invoke(_IPTestDefinition, new InvokeEmptyToInvokeInteractionMapper(interaction));
    }

    @Override
    public void progress(IPTestDefinition _IPTestDefinition, ProgressInteraction interaction)
            throws MALInteractionException, MALException {
        if (_IPTestDefinition == null) {
            // this is the access control test then
            interaction.sendAcknowledgement(null);

            try {
                Thread.sleep(Configuration.PERIOD);
            } catch (Exception ex) {
                // do nothing
            }

            interaction.sendUpdate(null);
            interaction.sendUpdate(null);
            interaction.sendUpdate(null);

            try {
                Thread.sleep(Configuration.PERIOD);
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
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 2:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendError(new MOErrorException(new UInteger(999), new Union("No error")));
                        break;
                    case 3:
                        interaction.sendError(new MOErrorException(new UInteger(999), new Union("No error")));
                        break;
                    case 4:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 5:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendUpdateError(new MOErrorException(new UInteger(999), new Union("No error")));
                        break;
                    case 6:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendError(new MOErrorException(new UInteger(999), new Union("No error")));
                        break;
                    case 7:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 8:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendError(new MOErrorException(new UInteger(999), new Union("No error")));
                        break;
                    case 9:
                        interaction.sendAcknowledgement("");
                        // the interaction has no final message to match the test case
                        break;
                    case 10:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 11:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendResponse("");
                        break;
                    case 12:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendUpdateError(new MOErrorException(new UInteger(999), new Union("No error")));
                        break;
                    case 13:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendUpdateError(new MOErrorException(new UInteger(999), new Union("No error")));
                        break;
                    case 14:
                        interaction.sendAcknowledgement("");
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendUpdate(new Integer(1));
                        interaction.sendUpdate(new Integer(2));
                        //Thread.sleep(Configuration.PERIOD);
                        interaction.sendError(new MOErrorException(new UInteger(999), new Union("No error")));
                        break;
                    case 15:
                      interaction.sendError(new MOErrorException(new UInteger(999), new Union("No error")));
                      // the interaction has no final message to match the test case
                      break;
                    default:
                        throw new MALInteractionException(new MOErrorException(MALHelper.INTERNAL_ERROR_NUMBER,
                                new Union("Unexpected procedure number of " + transId)));
                }
            } catch (Exception ex) {
                // do nothing
            }
        }
    }

    @Override
    public void progressMulti(IPTestDefinition _IPTestDefinition0, Element _Element1,
            ProgressMultiInteraction interaction) throws MALInteractionException, MALException {
        progress(_IPTestDefinition0, new ProgressMultiToProgressInteractionMapper(interaction));
    }

    @Override
    public void testProgressEmptyBody(IPTestDefinition _IPTestDefinition,
            TestProgressEmptyBodyInteraction interaction) throws MALInteractionException, MALException {
        progress(_IPTestDefinition, new ProgressEmptyToProgressInteractionMapper(interaction));
    }

    @Override
    public void publishRegister(TestPublishRegister publishRegister, MALInteraction interaction)
            throws MALInteractionException, MALException {
        LoggingBase.logMessage("IPTestHandlerImpl.publishRegister(" + publishRegister + ')');
        resetAssertions();

        String key = publishRegister.getDomain().toString()
                + publishRegister.getNetworkZone().toString()
                + publishRegister.getSession().toString()
                + publishRegister.getSessionName().toString();

        MonitorPublishInteractionListener listener = defaultListener;
        listener.setKey(key);

        doPublishRegister(publishRegister, listener);
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
                publisher.asyncRegister(_TestPublishRegister.getKeyNames(), _TestPublishRegister.getKeyTypes(), listener);
            } else {
                MonitorPublisher publisher = getMonitorPublisher(
                        _TestPublishRegister.getDomain(),
                        _TestPublishRegister.getNetworkZone(),
                        _TestPublishRegister.getSession(),
                        _TestPublishRegister.getSessionName(),
                        _TestPublishRegister.getQos(),
                        _TestPublishRegister.getPriority());

                LoggingBase.logMessage("IPTestHandlerImpl.doPublishRegister: The keyNames are: " + _TestPublishRegister.getKeyNames());
                publisher.asyncRegister(_TestPublishRegister.getKeyNames(), _TestPublishRegister.getKeyTypes(), listener);
            }
            listener.cond.waitFor(Configuration.WAIT_TIME_OUT);
        } catch (InterruptedException e) {
        }

        listener.cond.reset();
    }

    @Override
    public void publishUpdates(TestPublishUpdate publishUpdate, MALInteraction interaction) throws MALException {
        LoggingBase.logMessage("\n\n------------- Provider Side -------------\n");
        LoggingBase.logMessage("IPTestHandlerImpl.publishUpdates(" + publishUpdate + ')');
        resetAssertions();

        TestUpdateList testUpdateList = publishUpdate.getUpdates();
        String key = publishUpdate.getDomain().toString()
                + publishUpdate.getNetworkZone().toString()
                + publishUpdate.getSession().toString()
                + publishUpdate.getSessionName().toString();

        FileBasedDirectory.URIpair uris = getProviderURIs();
        MonitorPublishInteractionListener listener = defaultListener;

        Time timestamp = new Time(System.currentTimeMillis());

        // Reset the listener
        listener.setHeader(null);
        listener.setError(null);
        listener.cond.reset();

        MOErrorException raisedPublishError = null;

        // Set time stamp and Source URI
        UpdateHeaderList updateHeaderList = publishUpdate.getUpdateHeaders();

        UShort opNumber = null;
        try {
            for (int i = 0; i < testUpdateList.size(); i++) {
                UpdateHeader updateHeader = updateHeaderList.get(i);
                TestUpdate testUpdate = testUpdateList.get(i);

                if (publishUpdate.getTestMultiType()) {
                    opNumber = IPTestServiceInfo.MONITORMULTI_OP.getNumber();
                    MonitorMultiPublisher publisher = getMonitorMultiPublisher(
                            publishUpdate.getDomain(),
                            publishUpdate.getNetworkZone(),
                            publishUpdate.getSession(),
                            publishUpdate.getSessionName(),
                            publishUpdate.getQos(),
                            publishUpdate.getPriority());

                    publisher.publish(updateHeader, testUpdate, testUpdateList);
                } else {
                    // Normal case (not TestMultiType)
                    opNumber = IPTestServiceInfo.MONITOR_OP.getNumber();
                    MonitorPublisher publisher = getMonitorPublisher(
                            publishUpdate.getDomain(),
                            publishUpdate.getNetworkZone(),
                            publishUpdate.getSession(),
                            publishUpdate.getSessionName(),
                            publishUpdate.getQos(),
                            publishUpdate.getPriority());
                    publisher.publish(updateHeader, testUpdate);
                }
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

        if (publishUpdate.getErrorCode().getValue() != 999) {
            MOErrorException error;
            UInteger expectedErrorCode;
            Object expectedExtraInfo;

            if (publishUpdate.getIsException()) {
                error = raisedPublishError;
                expectedErrorCode = MALHelper.INCORRECT_STATE_ERROR_NUMBER;
                expectedExtraInfo = null;
            } else {
                MALMessageHeader publishHeader = listener.getHeader();

                // Check if the header is not null
                assertions.add(new Assertion("PubSub.checkPublishErrorHeader",
                        "Publish Error received", (publishHeader != null)));

                if (publishHeader == null) {
                    return;
                }

                MALMessageHeader expectedPublishErrorHeader = new MALMessageHeader(
                        new Identifier(uris.broker.getValue()),
                        getBrokerAuthenticationId(),
                        new Identifier(uris.uri.getValue()),
                        timestamp,
                        InteractionType.PUBSUB,
                        new UOctet(MALPubSubOperation._PUBLISH_STAGE),
                        listener.getPublishRegisterTransactionId(key),
                        MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
                        IPTestServiceInfo.IPTEST_SERVICE_NUMBER,
                        opNumber,
                        MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
                        Boolean.TRUE,
                        new NamedValueList());

                // The CNES implementation of an internal broker shares the endpoint of the provider.
                // As the definition of the supplements field is attached to the endpoint at endpoint creation
                // (this is a testbed policy), then we cannot differenciate the supplements field value
                // from the broker and from the provider. The check must then be restricted as
                // this is not a MAL issue.
                AssertionHelper.checkHeader("PubSub.checkPublishErrorHeader",
                        assertions, publishHeader, expectedPublishErrorHeader, true);

                error = listener.getError();
                expectedErrorCode = publishUpdate.getErrorCode();
                expectedExtraInfo = publishUpdate.getFailedKeyValues();
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

        LoggingBase.logMessage("------------------------\n\n");
    }

    @Override
    public void publishDeregister(TestPublishDeregister publishDeregister, MALInteraction interaction)
            throws MALInteractionException, MALException {
        LoggingBase.logMessage("IPTestHandlerImpl.publishDeregister(" + publishDeregister + ')');
        resetAssertions();

        MonitorPublishInteractionListener listener = defaultListener;

        doPublishDeregister(publishDeregister, listener);
        // The Publish Deregister header is checked with a shared broker
        // (see test.patterns.pubsub.IPTestHandlerWithSharedBroker)

        if (publishDeregister.getTestMultiType()) {
            MonitorMultiPublisher publisher = getMonitorMultiPublisher(publishDeregister.getDomain(),
                    publishDeregister.getNetworkZone(),
                    publishDeregister.getSession(),
                    publishDeregister.getSessionName(),
                    publishDeregister.getQos(),
                    publishDeregister.getPriority());
            publisher.close();
            removeMonitorMultiPublisher(publishDeregister.getDomain(),
                    publishDeregister.getNetworkZone(),
                    publishDeregister.getSession(),
                    publishDeregister.getSessionName(),
                    publishDeregister.getQos(),
                    publishDeregister.getPriority());
            if (publishersMulti.isEmpty()) {
                defaultListener = new MonitorPublishInteractionListener();
            }
        } else {
            MonitorPublisher publisher = getMonitorPublisher(publishDeregister.getDomain(),
                    publishDeregister.getNetworkZone(),
                    publishDeregister.getSession(),
                    publishDeregister.getSessionName(),
                    publishDeregister.getQos(),
                    publishDeregister.getPriority());
            publisher.close();
            removeMonitorPublisher(publishDeregister.getDomain(),
                    publishDeregister.getNetworkZone(),
                    publishDeregister.getSession(),
                    publishDeregister.getSessionName(),
                    publishDeregister.getQos(),
                    publishDeregister.getPriority());
            if (publishers.isEmpty()) {
                defaultListener = new MonitorPublishInteractionListener();
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

    @Override
    public IPTestResult getResult(Element _Element, MALInteraction interaction) throws MALException {
        LoggingBase.logMessage("IPTest: assertions = " + assertions);
        return new IPTestResult(transactionId, assertions);
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

    protected FileBasedDirectory.URIpair getProviderURIs() {
        return FileBasedDirectory.loadURIs(ipTestProviderFileName);
    }

    protected Blob getBrokerAuthenticationId() {
        return FileBasedDirectory.loadPrivateBrokerAuthenticationId();
    }

    @Override
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
            for (int i = 0; i < updateHeaderList.size(); i++) {
                UpdateHeader updateHeader = updateHeaderList.get(i);
                TestUpdate testUpdate = testUpdateList.get(i);
                publisher.publish(updateHeader, testUpdate);
            }
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
        public MALMessage sendError(MOErrorException error) throws MALInteractionException, MALException {
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
        public MALMessage sendError(MOErrorException error) throws MALInteractionException, MALException {
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
        public MALMessage sendError(MOErrorException error) throws MALInteractionException, MALException {
            return interaction.sendError(error);
        }

        @Override
        public MALMessage sendUpdateError(MOErrorException error) throws MALInteractionException, MALException {
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
        public MALMessage sendError(MOErrorException error) throws MALInteractionException, MALException {
            return interaction.sendError(error);
        }

        @Override
        public MALMessage sendUpdateError(MOErrorException error) throws MALInteractionException, MALException {
            return interaction.sendUpdateError(error);
        }
    }
}
