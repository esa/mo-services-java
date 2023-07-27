/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Testbed ESA provider
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
package esa.mo.com.test.event;

import esa.mo.com.support.ComStructureHelper;
import java.util.Hashtable;
import org.ccsds.moims.mo.com.archive.ArchiveHelper;
import org.ccsds.moims.mo.com.archive.ArchiveServiceInfo;
import org.ccsds.moims.mo.com.archive.consumer.ArchiveStub;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetails;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList;
import org.ccsds.moims.mo.com.event.EventHelper;
import org.ccsds.moims.mo.com.event.EventServiceInfo;
import org.ccsds.moims.mo.com.event.provider.MonitorEventPublisher;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.com.test.provider.TestServiceProvider;
import org.ccsds.moims.mo.comprototype.COMPrototypeHelper;
import org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo;
import org.ccsds.moims.mo.comprototype.eventtest.provider.EventTestHandler;
import org.ccsds.moims.mo.comprototype.eventtest.provider.EventTestSkeleton;
import org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectCreation;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectCreationList;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectDeletion;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectDeletionList;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectUpdate;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectUpdateList;
import org.ccsds.moims.mo.comprototype.eventtest.structures.UpdateComposite;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.ShortList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class EventTestHandlerImpl implements EventTestHandler {

    private final TestServiceProvider testService;
    private EventTestSkeleton skeleton = null;
    private MonitorEventPublisherSkeleton monitorEventPublisherSkeleton = null;
    private MonitorEventPublisher monitorEventPublisher = null;
    private PublisherListener publisherListener = new PublisherListener();
    private final String CLS = "EventTestHandlerImpl";
    // Count of test object instances created - also use as instanceId
    private long instCount = 1;
    // Count of test object instances created - also use as instanceId
    private long eventInstCount = 1;
    // Maintain a list hold description for each instance created
    private final java.util.ArrayList<TestObjectDetails> testObjectDetailsList = new java.util.ArrayList<>();
    // Archive stub used to archive events 
    private ArchiveStub archiveStub;
    // Event numbers allocated to events
    private String TEST_OBJECT_CREATION_NO = "3001";
    private String TEST_OBJECT_DELETION_NO = "3002";
    private String TEST_OBJECT_UPDATE_NO = "3003";
    private final Identifier NETWORK = new Identifier("networkZone");
    IdentifierList eventDomainId;

    public EventTestHandlerImpl(TestServiceProvider testService) {
        this.testService = testService;
    }

    /**
     * Implements the operation
     *
     * @param interaction The MAL object representing the interaction in the
     * provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a
     * problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation
     * exception.
     */
    @Override
    public void resetTest(String eventDomain, MALInteraction interaction)
            throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":resetTest " + eventDomain);
        eventDomainId = new IdentifierList();
        eventDomainId.add(new Identifier(eventDomain));
        // if (null == monitorEventPublisher) 
        {
            createMonitorEventPublisher(eventDomainId);

        }
    }

    @Override
    public Long createinstance(Short objectNumber, String domain, String desc,
            Long parentInstId, MALInteraction inter) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":createInstance domain = "
                + domain + " desc = " + desc);
        testObjectDetailsList.add(new TestObjectDetails(domain, objectNumber, parentInstId, desc));
        publishTestObjectCreation(desc, domain, true,
                instCount, objectNumber, parentInstId, inter);
        return new Long(instCount++);
    }

    @Override
    public void deleteInstance(Short objectNumber, String domain, Long instId, MALInteraction inter)
            throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":deleteInstance domain = "
                + domain + " instId = " + instId);
        publishTestObjectDeletion(domain, instId, objectNumber, inter);
    }

    @Override
    public void updateInstance(Long instId, BasicEnum enumField, Duration durationField,
            ShortList shortListField, MALInteraction interaction)
            throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":updateInstance instId = "
                + instId + " enumField = " + enumField + " durationField = " + durationField);
        publishTestObjectUpdate(instId,
                enumField, durationField, shortListField, null, null, null, interaction);
    }

    @Override
    public void updateInstanceComposite(Long instId, UOctet uOctetField, Byte octetField, Double doubleField,
            MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":updateInstance instId = "
                + instId + " uOctetField = " + uOctetField + " octetField = " + octetField);
        publishTestObjectUpdate(instId,
                null, null, null, uOctetField, octetField, doubleField, interaction);
    }

    /**
     * Sets the skeleton to be used for creation of publishers.
     *
     * @param skeleton The skeleton to be used.
     */
    public void setSkeleton(EventTestSkeleton skeleton) {
        this.skeleton = skeleton;
    }

    private void createMonitorEventPublisher(IdentifierList eventDomainId) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":createMonitorEventPublisher " + eventDomainId);

        monitorEventPublisherSkeleton = new MonitorEventPublisherSkeleton();

        MALProviderManager malProviderMgr = testService.getDefaultProviderMgr();

        MALProvider malProvider = malProviderMgr.createProvider("MonitorEventPublisher - Event Test",
                null,
                EventHelper.EVENT_SERVICE,
                new Blob("".getBytes()),
                monitorEventPublisherSkeleton,
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1),
                null,
                true,
                null,
                null);
        LoggingBase.logMessage(CLS + ":createMonitorEventPublisher - calling store UI");
        FileBasedDirectory.storeURI(EventServiceInfo.EVENT_SERVICE_NAME.getValue(), malProvider.getURI(), malProvider.getBrokerURI());

        monitorEventPublisherSkeleton.malInitialize(malProvider);

        monitorEventPublisher = monitorEventPublisherSkeleton.createMonitorEventPublisher(eventDomainId,
                new Identifier("GROUND"),
                SessionType.LIVE,
                new Identifier("LIVE"),
                QoSLevel.BESTEFFORT,
                null,
                new UInteger(0));
        /*
    final EntityKeyList lst = new EntityKeyList();
    lst.add(new EntityKey(new Identifier("*"), new Long(0), new Long(0), new Long(0)));
         */
        IdentifierList keys = new IdentifierList();
        keys.add(new Identifier("K1"));
        keys.add(new Identifier("K2"));
        keys.add(new Identifier("K3"));
        keys.add(new Identifier("K4"));

        monitorEventPublisher.register(keys, publisherListener);

    }

    // Generate a EntityKey subkey using fields as specified in COM STD 3.2.4.2b
    static protected Long generateSubKey(int area, int service, int version, int objectNumber) {
        long subkey = objectNumber;
        subkey = subkey | (((long) version) << 24);
        subkey = subkey | ((long) service << 32);
        subkey = subkey | ((long) area << 48);

        return new Long(subkey);
    }

    protected void publishTestObjectCreation(String description, String sourceDomain,
            boolean success, long sourceInstId, short sourceObjectNumber,
            Long relatedInstId,
            MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + "publishTestObjectCreation malInter = " + interaction);

        // Produce ObjectCreationList
        ObjectCreationList ocl = new ObjectCreationList();
        ObjectCreation oc = new ObjectCreation(success, description);
        ocl.add(oc);

        ObjectId source = setObjectId(sourceDomain, sourceObjectNumber, sourceInstId);
        ObjectDetails objDetails = new ObjectDetails(relatedInstId, source);

        // Produce header
        UpdateHeader uh = setUpdateHeader(TEST_OBJECT_CREATION_NO, sourceInstId);

        // We can now publish the event
        monitorEventPublisher.publish(uh, objDetails, oc);

        // Write the event to the archive
        storeInArchive(objDetails, uh, ocl, TEST_OBJECT_CREATION_NO);
    }

    protected void publishTestObjectDeletion(String sourceDomain,
            long sourceInstId, short sourceObjectNumber,
            MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + "publishTestObjectCreation malInter = " + interaction);

        // Produce ObjectDeletionList
        ObjectDeletionList odl = new ObjectDeletionList();
        ObjectDeletion od = new ObjectDeletion(testObjectDetailsList.get((int) (sourceInstId - 1)).description);
        odl.add(od);

        // Produce ObjectDetails 
        ObjectDetails objDetails;
        ObjectId source = setObjectId(sourceDomain, sourceObjectNumber, sourceInstId);

        // Set related (if supplied)
        if (testObjectDetailsList.get((int) (sourceInstId - 1)).parentInstId != null) {
            objDetails = new ObjectDetails(testObjectDetailsList.get((int) (sourceInstId - 1)).parentInstId, source);
        } else {
            objDetails = new ObjectDetails(null, source);
        }

        // Produce header
        UpdateHeader uh = setUpdateHeader(TEST_OBJECT_DELETION_NO, sourceInstId);

        // We can now publish the event
        monitorEventPublisher.publish(uh, objDetails, od);

        // Write the event to the archive
        storeInArchive(objDetails, uh, odl, TEST_OBJECT_DELETION_NO);

    }

    protected void storeInArchive(ObjectDetails objDetails, UpdateHeader updateHeader,
            ElementList elementList, String objectNumber) throws MALInteractionException, MALException {
        ArchiveDetailsList archiveDetailsList = new ArchiveDetailsList();
        archiveDetailsList.add(new ArchiveDetails((Long) updateHeader.getKeyValues().get(2), objDetails, NETWORK,
                FineTime.now(), new URI(updateHeader.getSource().getValue())));

        // Domain is TBD
//        final IdentifierList domain = new IdentifierList();
//        domain.add(new Identifier("esa"));
//        domain.add(new Identifier("mission"));
        archiveStub().store(Boolean.FALSE,
                new ObjectType(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER, EventTestServiceInfo.EVENTTEST_SERVICE_NUMBER,
                        COMPrototypeHelper.COMPROTOTYPE_AREA_VERSION, new UShort(Integer.parseInt(objectNumber))),
                eventDomainId,
                archiveDetailsList,
                elementList);
    }

    protected void publishTestObjectUpdate(long sourceInstId,
            BasicEnum enumField, Duration durationField, ShortList numericListField,
            UOctet uOctetField, Byte octetField, Double doubleField,
            MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + "publishTestObjectUpdate malInter = " + interaction);

        UpdateComposite uComposite = null;
        if (uOctetField != null || octetField != null || doubleField != null) {
            uComposite = new UpdateComposite(uOctetField, octetField, doubleField);
        }

        ObjectUpdate ou = new ObjectUpdate(enumField, durationField, numericListField, uComposite);

        // Produce ObjectUpdateList
        ObjectUpdateList oul = new ObjectUpdateList();
        oul.add(ou);

        // Produce ObjectDetails 
        short sourceObjectNumber = testObjectDetailsList.get((int) (sourceInstId - 1)).objectNumber;
        String domain = testObjectDetailsList.get((int) (sourceInstId - 1)).domain;
        ObjectId source = setObjectId(domain, sourceObjectNumber, sourceInstId);
        Long related = null;

        // Set related (if supplied)
        if (testObjectDetailsList.get((int) (sourceInstId - 1)).parentInstId != null) {
            related = testObjectDetailsList.get((int) (sourceInstId - 1)).parentInstId;
        }

        ObjectDetails objDetails = new ObjectDetails(related, source);

        // Produce header
        UpdateHeader uh = setUpdateHeader(TEST_OBJECT_UPDATE_NO, sourceInstId);

        // We can now publish the event
        monitorEventPublisher.publish(uh, objDetails, ou);

        // Write the event to the archive
        storeInArchive(objDetails, uh, oul, TEST_OBJECT_UPDATE_NO);

    }

    private UpdateHeader setUpdateHeader(String eventObjectNumber, long sourceInstId) {
        short sourceObjectNumber = testObjectDetailsList.get((int) (sourceInstId - 1)).objectNumber;

        AttributeList keyValues = new AttributeList();
        keyValues.add(new Identifier(eventObjectNumber));
        keyValues.add(new Union(ComStructureHelper.generateSubKey(
                COMPrototypeHelper._COMPROTOTYPE_AREA_NUMBER,
                EventTestServiceInfo._EVENTTEST_SERVICE_NUMBER,
                COMPrototypeHelper._COMPROTOTYPE_AREA_VERSION,
                0)));
        keyValues.add(new Union((long) eventInstCount++));
        keyValues.add(new Union(ComStructureHelper.generateSubKey(
                COMPrototypeHelper._COMPROTOTYPE_AREA_NUMBER,
                EventTestServiceInfo._EVENTTEST_SERVICE_NUMBER,
                COMPrototypeHelper._COMPROTOTYPE_AREA_VERSION,
                sourceObjectNumber)));

        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("mission"));

        UpdateHeader uh = new UpdateHeader(
                new Identifier(EventTestServiceInfo.EVENTTEST_SERVICE_NAME.getValue()),
                domain, keyValues);
        return uh;
    }

    private ObjectId setObjectId(String domain, short objectNumber, long instanceId) {
        ObjectType type = new ObjectType(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER,
                EventTestServiceInfo.EVENTTEST_SERVICE_NUMBER,
                COMPrototypeHelper.COMPROTOTYPE_AREA_VERSION,
                new UShort(objectNumber));

        final IdentifierList domainIdent = new IdentifierList();
        domainIdent.add(new Identifier(domain));

        ObjectKey key = new ObjectKey(domainIdent, instanceId);
        return new ObjectId(type, key);
    }

    public synchronized ArchiveStub archiveStub() throws MALException {
        if (null == archiveStub) {

            FileBasedDirectory.URIpair uris = FileBasedDirectory.loadURIs(ArchiveServiceInfo.ARCHIVE_SERVICE_NAME.getValue());

            final IdentifierList domain = new IdentifierList();
            domain.add(new Identifier("esa"));
            domain.add(new Identifier("mission"));
            MALConsumer consumer = testService.getDefaultMal().createConsumerManager().createConsumer(
                    "EventTestProviderArchiveConsumer",
                    uris.uri,
                    uris.broker,
                    ArchiveHelper.ARCHIVE_SERVICE,
                    new Blob("".getBytes()),
                    domain,
                    NETWORK,
                    SessionType.LIVE,
                    new Identifier("LIVE"),
                    QoSLevel.BESTEFFORT,
                    new Hashtable(),
                    new UInteger(0),
                    null);

            archiveStub = new ArchiveStub(consumer);
        }

        return archiveStub;
    }

    private class TestObjectDetails {

        String domain;
        short objectNumber;
        Long parentInstId;
        String description;

        TestObjectDetails(String domain, short objectNumber, Long parentInstId, String description) {
            this.parentInstId = parentInstId;
            this.description = description;
            this.domain = domain;
            this.objectNumber = objectNumber;
        }
    }
}
