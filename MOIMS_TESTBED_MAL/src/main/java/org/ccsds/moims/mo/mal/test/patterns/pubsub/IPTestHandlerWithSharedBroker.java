/*******************************************************************************
 * Copyright or © or Copr. CNES
 *
 * This software is a computer program whose purpose is to provide a 
 * framework for the CCSDS Mission Operations services.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL-C
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 *******************************************************************************/
package org.ccsds.moims.mo.mal.test.patterns.pubsub;

import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.test.patterns.IPTestHandlerImpl;
import org.ccsds.moims.mo.mal.test.patterns.MonitorPublishInteractionListener;
import org.ccsds.moims.mo.mal.test.suite.TestServiceProvider;
import org.ccsds.moims.mo.mal.test.util.AssertionHelper;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.provider.MonitorMultiPublisher;
import org.ccsds.moims.mo.malprototype.iptest.provider.MonitorPublisher;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.testbed.transport.TestMessageHeader;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class IPTestHandlerWithSharedBroker extends IPTestHandlerImpl
{
  
  private String ipTestProviderWithSharedBrokerFileName;
  
  public IPTestHandlerWithSharedBroker() {
    super();
    ipTestProviderWithSharedBrokerFileName = TestServiceProvider.IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME;
  }

  public String getIpTestProviderWithSharedBrokerFileName() {
    return ipTestProviderWithSharedBrokerFileName;
  }

  public void setIpTestProviderWithSharedBrokerFileName(String ipTestProviderWithSharedBrokerFileName) {
    this.ipTestProviderWithSharedBrokerFileName = ipTestProviderWithSharedBrokerFileName;
  }

  @Override
  protected void doPublishRegister(
          TestPublishRegister _TestPublishRegister,
          MonitorPublishInteractionListener listener) throws MALInteractionException, MALException
  {
    LoggingBase.logMessage("IPTestHandlerWithSharedBroker.doPublishRegister(" + _TestPublishRegister + ')');
    FileBasedDirectory.URIpair uris = FileBasedDirectory.loadURIs(ipTestProviderWithSharedBrokerFileName);
    // Reset the listener
    listener.setHeader(null);
    listener.setError(null);

    Time timestamp = new Time(System.currentTimeMillis());

    QoSLevel firstPublishRegisterQoSLevel = listener.getPublishRegisterQoSLevel();
    if (firstPublishRegisterQoSLevel == null)
    {
      firstPublishRegisterQoSLevel = _TestPublishRegister.getQos();
    }

    UInteger firstPublishRegisterPriority = listener.getPublishRegisterPriority();
    if (firstPublishRegisterPriority == null)
    {
      firstPublishRegisterPriority = _TestPublishRegister.getPriority();
    }

    UShort opNumber = null;
    try
    {
      if (_TestPublishRegister.getTestMultiType())
      {
        opNumber = IPTestHelper.MONITORMULTI_OP.getNumber();
        MonitorMultiPublisher publisher = getMonitorMultiPublisher(
                _TestPublishRegister.getDomain(),
                _TestPublishRegister.getNetworkZone(),
                _TestPublishRegister.getSession(),
                _TestPublishRegister.getSessionName(),
                _TestPublishRegister.getQos(),
                _TestPublishRegister.getPriority());
        publisher.asyncRegister(_TestPublishRegister.getEntityKeys(), listener);
      }
      else
      {
        opNumber = IPTestHelper.MONITOR_OP.getNumber();
        MonitorPublisher publisher = getMonitorPublisher(
                _TestPublishRegister.getDomain(),
                _TestPublishRegister.getNetworkZone(),
                _TestPublishRegister.getSession(),
                _TestPublishRegister.getSessionName(),
                _TestPublishRegister.getQos(),
                _TestPublishRegister.getPriority());
        publisher.asyncRegister(_TestPublishRegister.getEntityKeys(), listener);
      }
      listener.cond.waitFor(Configuration.WAIT_TIME_OUT);
    }
    catch (InterruptedException e)
    {
    }

    listener.cond.reset();

    MALMessageHeader expectedPublishRegisterHeader = new TestMessageHeader(
            uris.uri,
            TestServiceProvider.IP_TEST_AUTHENTICATION_ID,
            uris.broker,
            timestamp,
            _TestPublishRegister.getQos(),
            _TestPublishRegister.getPriority(),
            _TestPublishRegister.getDomain(),
            _TestPublishRegister.getNetworkZone(),
            _TestPublishRegister.getSession(),
            _TestPublishRegister.getSessionName(),
            InteractionType.PUBSUB,
            new UOctet(MALPubSubOperation._PUBLISH_REGISTER_STAGE),
            null,
            MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
            IPTestHelper.IPTEST_SERVICE_NUMBER,
            opNumber,
            MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
            Boolean.FALSE);

    MALMessage publishRegisterMsg = TransportInterceptor.instance().getLastSentMessage(uris.uri);

    LoggingBase.logMessage("IPTestHandlerWithSharedBroker.doPublishRegister: Looking for last message sent to " + uris.broker + " : " + publishRegisterMsg);

    AssertionHelper.checkHeader("PubSub.checkPublishRegisterHeader", assertions,
            publishRegisterMsg.getHeader(), expectedPublishRegisterHeader);

    boolean isErrorTest = (_TestPublishRegister.getErrorCode().getValue() != 999);

    MALMessageHeader expectedPublishRegisterAckHeader = new TestMessageHeader(
            uris.broker,
            FileBasedDirectory.loadSharedBrokerAuthenticationId(),
            uris.uri,
            publishRegisterMsg.getHeader().getTimestamp(),
            firstPublishRegisterQoSLevel,
            firstPublishRegisterPriority,
            _TestPublishRegister.getDomain(),
            _TestPublishRegister.getNetworkZone(),
            _TestPublishRegister.getSession(),
            _TestPublishRegister.getSessionName(),
            InteractionType.PUBSUB,
            new UOctet(MALPubSubOperation._PUBLISH_REGISTER_ACK_STAGE),
            publishRegisterMsg.getHeader().getTransactionId(),
            MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
            IPTestHelper.IPTEST_SERVICE_NUMBER,
            opNumber,
            MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(), isErrorTest);

    String procedureName;
    if (isErrorTest)
    {
      procedureName = "PubSub.checkPublishRegisterErrorHeader";
    }
    else
    {
      procedureName = "PubSub.checkPublishRegisterAckHeader";
    }

    MALMessageHeader publishRegisterAck = listener.getHeader();
    AssertionHelper.checkHeader(procedureName, assertions,
            publishRegisterAck, expectedPublishRegisterAckHeader);

    if (_TestPublishRegister.getErrorCode().getValue() != 999)
    {
      MALStandardError error = listener.getError();
      assertions.add(new Assertion(procedureName,
              "Error received", (error != null)));
      if (error != null)
      {
        AssertionHelper.checkEquality(procedureName,
                assertions, "errorNumber", error.getErrorNumber(),
                _TestPublishRegister.getErrorCode());
      }
    }
  }

  @Override
  public void publishUpdates(TestPublishUpdate _TestPublishUpdate, MALInteraction interaction) throws MALException
  {
    LoggingBase.logMessage("IPTestHandlerWithSharedBroker.publishUpdates(" + _TestPublishUpdate + ')');
    FileBasedDirectory.URIpair uris = FileBasedDirectory.loadURIs(ipTestProviderWithSharedBrokerFileName);

    MonitorPublishInteractionListener listener = getPublishInteractionListener(
            _TestPublishUpdate.getDomain(), _TestPublishUpdate.getNetworkZone(),
            _TestPublishUpdate.getSession(), _TestPublishUpdate.getSessionName());

    UShort opNumber;
    if (_TestPublishUpdate.getTestMultiType())
    {
      opNumber = IPTestHelper.MONITORMULTI_OP.getNumber();
    }
    else
    {
      opNumber = IPTestHelper.MONITOR_OP.getNumber();
    }

    MALMessageHeader expectedPublishHeader = new TestMessageHeader(
            uris.uri,
            TestServiceProvider.IP_TEST_AUTHENTICATION_ID,
            uris.broker,
            new Time(System.currentTimeMillis()),
            _TestPublishUpdate.getQos(),
            _TestPublishUpdate.getPriority(),
            _TestPublishUpdate.getDomain(),
            _TestPublishUpdate.getNetworkZone(),
            _TestPublishUpdate.getSession(),
            _TestPublishUpdate.getSessionName(),
            InteractionType.PUBSUB,
            new UOctet(MALPubSubOperation._PUBLISH_STAGE),
            listener.getPublishRegisterTransactionId(),
            MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
            IPTestHelper.IPTEST_SERVICE_NUMBER,
            opNumber,
            MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
            Boolean.FALSE);

    super.publishUpdates(_TestPublishUpdate, interaction);

    if (_TestPublishUpdate.getErrorCode().getValue() != 999
            && _TestPublishUpdate.getIsException().booleanValue())
    {
      // No Publish message is expected to be sent
      return;
    }

    MALMessage publishMsg = TransportInterceptor.instance().getLastSentMessage(uris.uri);

    assertions.add(new Assertion("PubSub.checkPublishHeader", "Publish sent", (publishMsg != null)));

    if (publishMsg == null)
    {
      return;
    }

    AssertionHelper.checkHeader("PubSub.checkPublishHeader", assertions,
            publishMsg.getHeader(), expectedPublishHeader);
  }

  @Override
  protected void doPublishDeregister(TestPublishDeregister _TestPublishDeregister,
          MonitorPublishInteractionListener listener)
          throws MALInteractionException, MALException
  {
    LoggingBase.logMessage("IPTestHandlerWithSharedBroker.doPublishDeregister(" + _TestPublishDeregister + ')');
    FileBasedDirectory.URIpair uris = FileBasedDirectory.loadURIs(ipTestProviderWithSharedBrokerFileName);

    Time timestamp = new Time(System.currentTimeMillis());

    UShort opNumber = null;
    try
    {
      if (_TestPublishDeregister.getTestMultiType())
      {
        opNumber = IPTestHelper.MONITORMULTI_OP.getNumber();
        MonitorMultiPublisher publisher = getMonitorMultiPublisher(
                _TestPublishDeregister.getDomain(),
                _TestPublishDeregister.getNetworkZone(),
                _TestPublishDeregister.getSession(),
                _TestPublishDeregister.getSessionName(),
                _TestPublishDeregister.getQos(),
                _TestPublishDeregister.getPriority());
        publisher.asyncDeregister(listener);
      }
      else
      {
        opNumber = IPTestHelper.MONITOR_OP.getNumber();
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
    }
    catch (InterruptedException e)
    {
    }

    listener.cond.reset();

    MALMessageHeader expectedPublishDeregisterHeader = new TestMessageHeader(
            uris.uri,
            TestServiceProvider.IP_TEST_AUTHENTICATION_ID,
            uris.broker,
            timestamp,
            _TestPublishDeregister.getQos(),
            _TestPublishDeregister.getPriority(),
            _TestPublishDeregister.getDomain(),
            _TestPublishDeregister.getNetworkZone(),
            _TestPublishDeregister.getSession(),
            _TestPublishDeregister.getSessionName(),
            InteractionType.PUBSUB,
            new UOctet(MALPubSubOperation._PUBLISH_DEREGISTER_STAGE),
            null,
            MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
            IPTestHelper.IPTEST_SERVICE_NUMBER,
            opNumber,
            MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
            Boolean.FALSE);

    MALMessage publishDeregisterMsg = TransportInterceptor.instance().getLastSentMessage(uris.uri);

    AssertionHelper.checkHeader("PubSub.checkPublishDeregisterHeader", assertions,
            publishDeregisterMsg.getHeader(), expectedPublishDeregisterHeader);

    MALMessageHeader expectedPublishDeregisterAckHeader = new TestMessageHeader(
            uris.broker,
            FileBasedDirectory.loadSharedBrokerAuthenticationId(),
            uris.uri,
            publishDeregisterMsg.getHeader().getTimestamp(),
            listener.getPublishRegisterQoSLevel(),
            listener.getPublishRegisterPriority(),
            _TestPublishDeregister.getDomain(),
            _TestPublishDeregister.getNetworkZone(),
            _TestPublishDeregister.getSession(),
            _TestPublishDeregister.getSessionName(),
            InteractionType.PUBSUB,
            new UOctet(MALPubSubOperation._PUBLISH_DEREGISTER_ACK_STAGE),
            publishDeregisterMsg.getHeader().getTransactionId(),
            MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
            IPTestHelper.IPTEST_SERVICE_NUMBER,
            opNumber,
            MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
            Boolean.FALSE);

    MALMessageHeader publishDeregisterAck = listener.getHeader();
    AssertionHelper.checkHeader("PubSub.checkPublishDeregisterAckHeader", assertions,
            publishDeregisterAck, expectedPublishDeregisterAckHeader);
  }

  @Override
  public void testMultipleNotify(TestPublishUpdate _TestPublishUpdate, MALInteraction interaction) throws MALInteractionException
  {
    throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER,
            new Union("The transmit multiple is not supported with a shared broker")));
  }

  @Override
  protected FileBasedDirectory.URIpair getProviderURIs()
  {
    return FileBasedDirectory.loadURIs(ipTestProviderWithSharedBrokerFileName);
  }

  @Override
  protected Blob getBrokerAuthenticationId()
  {
    return FileBasedDirectory.loadSharedBrokerAuthenticationId();
  }
}
