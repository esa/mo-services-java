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
package org.ccsds.moims.mo.com.test.suite;

import java.util.Hashtable;
import org.ccsds.moims.mo.com.activitytracking.ActivityTrackingHelper;
import org.ccsds.moims.mo.com.archive.ArchiveHelper;
import org.ccsds.moims.mo.com.event.EventHelper;
import org.ccsds.moims.mo.comprototype.COMPrototypeHelper;
import org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementHelper;
import org.ccsds.moims.mo.comprototype.activitytest.ActivityTestHelper;
import org.ccsds.moims.mo.comprototype.archivetest.ArchiveTestHelper;
import org.ccsds.moims.mo.comprototype.eventtest.EventTestHelper;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALTransport;
import org.ccsds.moims.mo.testbed.suite.BaseTestServiceProvider;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import static org.ccsds.moims.mo.testbed.util.LoggingBase.logMessage;

/**
 *
 */
public abstract class BaseCOMTestServiceProvider extends BaseTestServiceProvider
{
  public String getProtocol()
  {
    return System.getProperty(Configuration.DEFAULT_PROTOCOL);
  }

  protected void initHelpers() throws MALException
  {
    org.ccsds.moims.mo.com.COMHelper.init(MALContextFactory.getElementFactoryRegistry());
    ActivityTrackingHelper.init(MALContextFactory.getElementFactoryRegistry());
    ArchiveHelper.init(MALContextFactory.getElementFactoryRegistry());
    EventHelper.init(MALContextFactory.getElementFactoryRegistry());

    COMPrototypeHelper.init(MALContextFactory.getElementFactoryRegistry());
    ActivityTestHelper.init(MALContextFactory.getElementFactoryRegistry());
    ActivityRelayManagementHelper.init(MALContextFactory.getElementFactoryRegistry());
    ArchiveTestHelper.init(MALContextFactory.getElementFactoryRegistry());
    EventTestHelper.init(MALContextFactory.getElementFactoryRegistry());
  }

  protected void createProviders() throws MALException
  {
    String protocol = getProtocol();
    logMessage("Create Providers Called");

    MALTransport transport = defaultMal.getTransport(protocol);
    MALEndpoint activityEndPoint = transport.createEndpoint(ActivityTestHelper.ACTIVITYTEST_SERVICE_NAME.getValue(), new Hashtable());
    MALEndpoint activityRelayManagementEndPoint = transport.createEndpoint(ActivityRelayManagementHelper.ACTIVITYRELAYMANAGEMENT_SERVICE_NAME.getValue(), new Hashtable());
    MALEndpoint archiveEndpoint = transport.createEndpoint(ArchiveHelper.ARCHIVE_SERVICE_NAME.getValue(), new Hashtable());
    MALEndpoint eventTestEndPoint = transport.createEndpoint(EventTestHelper.EVENTTEST_SERVICE_NAME.getValue(), new Hashtable());

    initProviders();

    MALProvider archiveProvider = defaultProviderMgr.createProvider(
            archiveEndpoint,
            ArchiveHelper.ARCHIVE_SERVICE,
            new Blob(new byte[0]),
            getArchiveHandler(),
            new QoSLevel[]
    {
      QoSLevel.ASSURED
    },
            new UInteger(1), // number of priority levels
            null,
            Boolean.TRUE, // isPublisher
            null);

    MALProvider archiveTestProvider = defaultProviderMgr.createProvider(
            archiveEndpoint,
            ArchiveTestHelper.ARCHIVETEST_SERVICE,
            new Blob(new byte[0]),
            getArchiveTestHandler(),
            new QoSLevel[]
    {
      QoSLevel.ASSURED
    },
            new UInteger(1), // number of priority levels
            null,
            Boolean.TRUE, // isPublisher
            null);

    MALProvider archiveEventProvider = defaultProviderMgr.createProvider(
            archiveEndpoint,
            EventHelper.EVENT_SERVICE,
            new Blob(new byte[0]),
            getArchiveEventHandler(),
            new QoSLevel[]
    {
      QoSLevel.ASSURED
    },
            new UInteger(1), // number of priority levels
            null,
            Boolean.TRUE, // isPublisher
            null);

    MALProvider activityTestProvider = defaultProviderMgr.createProvider(
            activityEndPoint,
            ActivityTestHelper.ACTIVITYTEST_SERVICE,
            new Blob(new byte[0]),
            getActivityTestServiceHandler(),
            new QoSLevel[]
    {
      QoSLevel.ASSURED
    },
            new UInteger(1), // number of priority levels
            null,
            Boolean.FALSE, // isPublisher
            null);

    MALProvider activityRelayManagementProvider = defaultProviderMgr.createProvider(
            activityRelayManagementEndPoint,
            ActivityRelayManagementHelper.ACTIVITYRELAYMANAGEMENT_SERVICE,
            new Blob(new byte[0]),
            getActivityRelayManagementServiceHandler(),
            new QoSLevel[]
    {
      QoSLevel.ASSURED
    },
            new UInteger(1), // number of priority levels
            null,
            Boolean.FALSE, // isPublisher
            null);

    MALProvider activityEventProvider = defaultProviderMgr.createProvider(
            activityEndPoint,
            EventHelper.EVENT_SERVICE,
            new Blob(new byte[0]),
            getActivityEventHandler(),
            new QoSLevel[]
    {
      QoSLevel.ASSURED
    },
            new UInteger(1), // number of priority levels
            null,
            Boolean.TRUE, // isPublisher
            null);

    MALProvider eventTestProvider = defaultProviderMgr.createProvider(
            eventTestEndPoint,
            EventTestHelper.EVENTTEST_SERVICE,
            new Blob(new byte[0]),
            getEventTestServiceHandler(),
            new QoSLevel[]
    {
      QoSLevel.ASSURED
    },
            new UInteger(1), // number of priority levels
            null,
            Boolean.FALSE, // isPublisher
            null);

    MALProvider eventProvider = defaultProviderMgr.createProvider(
            eventTestEndPoint,
            EventHelper.EVENT_SERVICE,
            new Blob(new byte[0]),
            getEventServiceHandler(),
            new QoSLevel[]
    {
      QoSLevel.ASSURED
    },
            new UInteger(1), // number of priority levels
            null,
            Boolean.TRUE, // isPublisher
            null);

    FileBasedDirectory.storeURI(ActivityTestHelper.ACTIVITYTEST_SERVICE_NAME.getValue(), activityTestProvider.getURI(), activityTestProvider.getBrokerURI());
    FileBasedDirectory.storeURI(ActivityRelayManagementHelper.ACTIVITYRELAYMANAGEMENT_SERVICE_NAME.getValue(), activityRelayManagementProvider.getURI(), activityRelayManagementProvider.getBrokerURI());
    FileBasedDirectory.storeURI(LocalMALInstance.ACTIVITY_EVENT_NAME, activityEventProvider.getURI(), activityEventProvider.getBrokerURI());
    FileBasedDirectory.storeURI(EventTestHelper.EVENTTEST_SERVICE_NAME.getValue(), eventTestProvider.getURI(), eventTestProvider.getBrokerURI());
    FileBasedDirectory.storeURI(EventHelper.EVENT_SERVICE_NAME.getValue(), eventProvider.getURI(), eventProvider.getBrokerURI());
    FileBasedDirectory.storeURI(ArchiveHelper.ARCHIVE_SERVICE_NAME.getValue(), archiveProvider.getURI(), archiveProvider.getBrokerURI());
    FileBasedDirectory.storeURI(ArchiveTestHelper.ARCHIVETEST_SERVICE_NAME.getValue(), archiveTestProvider.getURI(), archiveTestProvider.getBrokerURI());
    FileBasedDirectory.storeURI(LocalMALInstance.ARCHIVE_EVENT_NAME, archiveEventProvider.getURI(), archiveEventProvider.getBrokerURI());

    activityEndPoint.startMessageDelivery();
    activityRelayManagementEndPoint.startMessageDelivery();
    eventTestEndPoint.startMessageDelivery();
    archiveEndpoint.startMessageDelivery();
  }

  public void storeActivityRelayURI(String name, URI uri, URI brokerURI) throws MALException
  {
    FileBasedDirectory.storeURI(ActivityTestHelper.ACTIVITYTEST_SERVICE_NAME.getValue() + name, uri, brokerURI);
  }

  public MALProvider createActivityRelay(String name,
          MALInteractionHandler activityEventRelayHandler)
          throws MALException
  {
    logMessage("BaseCOMTestServiceProvider:createActivityRelay " + name);
    String protocol = getProtocol();

    MALProvider activityEventRelayProvider = defaultProviderMgr.createProvider(
            LocalMALInstance.ACTIVITY_EVENT_NAME + name,
            protocol,
            EventHelper.EVENT_SERVICE,
            new Blob(new byte[0]),
            activityEventRelayHandler,
            new QoSLevel[]
    {
      QoSLevel.ASSURED
    },
            new UInteger(1), // number of priority levels
            null,
            Boolean.TRUE, // isPublisher
            null);

    FileBasedDirectory.storeURI(LocalMALInstance.ACTIVITY_EVENT_NAME + name, activityEventRelayProvider.getURI(), activityEventRelayProvider.getBrokerURI());

    return activityEventRelayProvider;
  }

  abstract protected void initProviders();

  abstract protected MALInteractionHandler getActivityTestServiceHandler();

  abstract protected MALInteractionHandler getActivityRelayManagementServiceHandler();

  abstract protected MALInteractionHandler getActivityEventHandler();

  abstract protected MALInteractionHandler getEventTestServiceHandler();

  abstract protected MALInteractionHandler getEventServiceHandler();

  abstract protected MALInteractionHandler getArchiveHandler();

  abstract protected MALInteractionHandler getArchiveTestHandler();

  abstract protected MALInteractionHandler getArchiveEventHandler();
}
