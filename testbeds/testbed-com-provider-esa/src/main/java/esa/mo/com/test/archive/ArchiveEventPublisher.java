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
package esa.mo.com.test.archive;

import esa.mo.com.support.ComStructureHelper;
import java.util.Iterator;
import java.util.Map;
import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.archive.ArchiveServiceInfo;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetails;
import org.ccsds.moims.mo.com.event.EventHelper;
import org.ccsds.moims.mo.com.event.EventServiceInfo;
import org.ccsds.moims.mo.com.event.provider.MonitorEventPublisher;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.com.test.provider.TestServiceProvider;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 * Provides support functions associated with publishing archive events
 */
public class ArchiveEventPublisher {

    private ArchiveEventPublisherSkeleton archiveEventPublisherSkeleton = null;
    private MonitorEventPublisher monitorEventPublisher = null;
    public static final String ARCHIVE_EVENT_NAME = "ArchiveEvent";
    private final String CLS = "ArchiveEventPublisher";
    // Count of events created
    private long eventInstCount = 0;
    IdentifierList eventDomainId = null;
    private final Identifier NETWORK = new Identifier("networkZone");

    /**
     * Create the publisher object used to transmit events
     *
     * @param testService test service provider
     * @throws MALInteractionException
     * @throws MALException
     */
    public void createPublisher(TestServiceProvider testService) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":createMonitorEventPublisher " + eventDomainId);

        eventDomainId = new IdentifierList();
        archiveEventPublisherSkeleton = new ArchiveEventPublisherSkeleton();

        MALProviderManager malProviderMgr = testService.getDefaultProviderMgr();

        if (malProviderMgr == null) {
            LoggingBase.logMessage(CLS + ":createMonitorEventPublisher MAL provder NULL!");
        }

        MALProvider malProvider = malProviderMgr.createProvider("MonitorEventPublisher - Archive Test",
                null,
                EventHelper.EVENT_SERVICE,
                new Blob("".getBytes()),
                archiveEventPublisherSkeleton,
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1),
                null,
                true,
                null,
                null);
        LoggingBase.logMessage(CLS + ":createMonitorEventPublisher - calling store URI");
        FileBasedDirectory.storeURI(ARCHIVE_EVENT_NAME,
                malProvider.getURI(), malProvider.getBrokerURI());

        archiveEventPublisherSkeleton.malInitialize(malProvider);

        monitorEventPublisher = archiveEventPublisherSkeleton.createMonitorEventPublisher(eventDomainId,
                NETWORK,
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

        PublisherListener publisherListener = new PublisherListener();
        monitorEventPublisher.register(keys, new AttributeTypeList(), publisherListener);

    }

    /**
     * Generate a EntityKey subkey using fields as specified in COM STD 3.2.4.2b
     *
     * @param area
     * @param service
     * @param version
     * @param objectNumber
     * @return the sub key
     */
    static protected Long generateSubKey(int area, int service, int version, int objectNumber) {
        long subkey = objectNumber;
        subkey = subkey | (((long) version) << 24);
        subkey = subkey | ((long) service << 32);
        subkey = subkey | ((long) area << 48);

        return new Long(subkey);
    }

    /**
     * Populate the event updateheader
     *
     * @param updateHeader
     * @param eventObjectNumber
     * @param sourceObjectType
     */
    private UpdateHeader setUpdateHeader(UShort eventObjectNumber, ObjectType sourceObjectType) {
        /*
    final EntityKey ekey = new EntityKey(
            new Identifier(eventObjectNumber.toString()),
            generateSubKey(COMHelper.COM_AREA_NUMBER.getValue(),
                    ArchiveHelper.ARCHIVE_SERVICE_NUMBER.getValue(), COMHelper.COM_AREA_VERSION.getValue(), 0),
            new Long(++eventInstCount),
            generateSubKey(sourceObjectType.getArea().getValue(), sourceObjectType.getServiceNumber().getValue(),
                    sourceObjectType.getAreaVersion().getValue(), sourceObjectType.getNumber().getValue()));
    final Time timestamp = new Time(System.currentTimeMillis());
    updateHeader.setKey(ekey);
    updateHeader.setSourceURI(new URI(EventHelper.EVENT_SERVICE_NAME.getValue()));
    updateHeader.setUpdateType(UpdateType.DELETION);
    updateHeader.setTimestamp(timestamp);
         */

        AttributeList keyValues = new AttributeList();
        keyValues.add(new Identifier(eventObjectNumber.toString()));
        keyValues.add(new Union(ComStructureHelper.generateSubKey(
                COMHelper._COM_AREA_NUMBER,
                ArchiveServiceInfo.ARCHIVE_SERVICE_NUMBER.getValue(),
                COMHelper._COM_AREA_VERSION,
                0)));
        keyValues.add(new Union((long) ++eventInstCount));
        keyValues.add(new Union(ComStructureHelper.generateSubKey(
                sourceObjectType.getArea().getValue(),
                sourceObjectType.getServiceNumber().getValue(),
                sourceObjectType.getAreaVersion().getValue(),
                sourceObjectType.getNumber().getValue())));

        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("mission"));

        return new UpdateHeader(new Identifier(EventServiceInfo.EVENT_SERVICE_NAME.getValue()),
                domain, keyValues.getAsNullableAttributeList());
    }

    /**
     * Set the objectId fields key & domain
     *
     * @param objectId
     * @param domain
     * @param objectType
     * @param instanceId
     */
    private ObjectId setObjectId(IdentifierList domain, ObjectType objectType, Long instanceId) {
        ObjectKey key = new ObjectKey(domain, instanceId);
        return new ObjectId(objectType, key);
    }

    /**
     * Stores a archive event in the archive. Writes the event directly to the
     * archive rather than using ArchiveHandlerImpl as this avoids any recursive
     * issues
     *
     * @param objectType
     * @param domain
     * @param instId
     * @param timestamp
     * @param source
     * @param objDetails
     * @param objectNumber
     */
    public void storeEvent(ObjectType objectType, IdentifierList domain, Long instId,
            Time timestamp, Identifier source, ObjectDetails objDetails, UShort objectNumber) {
        Archive.inst().add(
                new ObjectType(COMHelper.COM_AREA_NUMBER, ArchiveServiceInfo.ARCHIVE_SERVICE_NUMBER, COMHelper.COM_AREA_VERSION, objectNumber),
                eventDomainId,
                new ArchiveDetails(instId, objDetails, NETWORK, new FineTime(timestamp.getValue()), new URI(source.getValue())), null);
    }

    /**
     * Publishes one or more archive service events - as defined in COM
     * specification. These events have no bodies defined, so the body is not
     * published. Also stores the events in the archive.
     *
     * @param objectNumber Object number for event to be published.
     * @param objectType Object type for event
     * @param domain Domain for event
     * @param instIds Instance identifiers, an event is raised for each instance
     * identifier.
     * @throws MALInteractionException
     * @throws MALException
     */
    public void publishEvents(UShort objectNumber, ObjectType objectType, IdentifierList domain,
            LongList instIds) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":publishStoreEvents:" + objectType
                + ":" + domain + ":" + instIds);

        Iterator<Long> instIt = instIds.iterator();
        while (instIt.hasNext()) {
            ObjectId source = setObjectId(eventDomainId, objectType, instIt.next());
            ObjectDetails objDetails = new ObjectDetails(null, source);

            UpdateHeader uh = setUpdateHeader(objectNumber, objectType);
            storeEvent(objectType, eventDomainId, eventInstCount, Time.now(), uh.getSource(), objDetails, objectNumber);

            monitorEventPublisher.publish(uh, objDetails, null);

            LoggingBase.logMessage(CLS + ":publishEvents:RET:" + uh + ":" + objDetails + ":");
        }
    }

    /**
     * Listener class used to receive responses to publishing events
     */
    public class PublisherListener implements MALPublishInteractionListener {

        public void publishRegisterAckReceived(MALMessageHeader header, Map qosProperties) throws MALException {
            LoggingBase.logMessage(CLS + ":publishRegisterAckReceived");
        }

        public void publishRegisterErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void publishErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException {
            LoggingBase.logMessage("ActivityTestPublisher:publishErrorReceived - " + body.toString());
        }

        public void publishDeregisterAckReceived(MALMessageHeader header, Map qosProperties) throws MALException {
            LoggingBase.logMessage(CLS + ":publishRegisterAckReceived");
        }
    }
}
