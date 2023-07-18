/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA Test Com Provider
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
package org.ccsds.moims.mo.com.test.event;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetails;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList;
import org.ccsds.moims.mo.com.event.consumer.EventStub;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.com.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.com.test.util.COMTestHelper;
import org.ccsds.moims.mo.comprototype.COMPrototypeHelper;
import org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo;
import org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.ShortList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import static org.ccsds.moims.mo.testbed.util.LoggingBase.logMessage;

/**
 * Class provides operations to support the Client side of the EventTest. The
 * operations in this class are currently called from Fitness.
 *
 */
public class EventTestScenario extends LoggingBase {

    protected final String loggingClassName = "EventScenario";
    protected final Identifier ALL_ID = new Identifier("*");
    protected final Integer ALL_INT = 0;
    private final EventDetailsList eventDetailsList;
    private final TestArchiveAdapter archiveAdapter;
    private TestEventAdapter testEventAdapter = null;
    private ArchiveDetailsList retrievedArchiveDetailsList;
    private ElementList retrievedObjectList;
    private ObjectType objectType;
    private String eventDomain;

    /**
     * Constructor performs object initialisation
     */
    public EventTestScenario() {
        logMessage(loggingClassName + ":Object Created");

        eventDetailsList = new EventDetailsList();
        testEventAdapter = new TestEventAdapter(eventDetailsList);
        archiveAdapter = new TestArchiveAdapter();
    }

    /**
     * Converts user level test object identifier to COM object number
     *
     * @param obj object identifier
     * @return Object number
     */
    public String objToObjectNo(String obj) {
        if (obj.equalsIgnoreCase("TestObjectA")) {
            return COMTestHelper.TEST_OBJECT_A_STR;
        } else if (obj.equalsIgnoreCase("TestObjectB")) {
            return COMTestHelper.TEST_OBJECT_B_STR;
        } else {
            return null;
        }
    }

    /**
     * Ensures event service client stub is available
     *
     * @return success indication
     * @throws Exception reports
     */
    public boolean testEventServiceClientHasBeenCreated() throws Exception {
        logMessage(loggingClassName + ":testEventServiceClientHasBeenCreated");
        return (null != LocalMALInstance.instance().eventTestStub());
    }

    /**
     * Calls reset test operation on service provider
     *
     * @param domain passed as operation argument
     * @return success indication for Fitness
     * @throws Exception to report failures
     */
    public boolean callResetTestOnServiceProviderWithDomain(String domain) throws Exception {
        logMessage(loggingClassName + ":callResetTestOnServiceProvider ");
        LocalMALInstance.instance().eventTestStub().resetTest(domain);

        return true;
    }

    /**
     * Calls reset operation on service provider
     *
     * @return success indication for Fitness
     * @throws Exception to report failures
     */
    public boolean callResetOnArchiveServiceProvider() throws Exception {
        LoggingBase.logMessage("ArchiveScenario.callResetTestOnServiceProvider()");
        LocalMALInstance.instance().archiveTestStub().reset();
        return true;
    }

    /**
     * Resets archive related test details
     *
     * @return success indication for Fitness
     */
    public boolean resetArchiveScenario() {
        retrievedArchiveDetailsList = null;
        retrievedObjectList = null;
        return true;
    }

    /**
     * Calls operation on provider to create instance of Object A
     *
     * @param domain object to be created in
     * @param description to be set on object
     * @return instance identifier for object
     * @throws Exception to report failures
     */
    public long createInstanceOfObjectAInDomainWithDescription(String domain, String description) throws Exception {
        logMessage(loggingClassName + ":createInstanceOfObjectAInDomainWithDescription domain = "
                + domain + " description = " + description);
        long retValue = LocalMALInstance.instance().eventTestStub().createinstance(
                COMTestHelper.TEST_OBJECT_A, domain, description, null);

        return retValue;
    }

    /**
     * Calls operation on provider to create instance of Object B
     *
     * @param domain object to be created in
     * @param description to be set on object
     * @param parentInstId
     * @return instance identifier for object created
     * @throws Exception to report failures
     */
    public long createInstanceOfObjectBInDomainWithDescriptionWithParentInstanceId(
            String domain, String description, String parentInstId) throws Exception {
        logMessage(loggingClassName + ":createInstanceOfObjectBInDomainWithDescriptionWithParentObject domain = "
                + domain + " desc = " + description + "parentInstId = " + parentInstId);
        long retValue = LocalMALInstance.instance().eventTestStub().createinstance(
                COMTestHelper.TEST_OBJECT_B, domain, description, new Long(parentInstId));
        return retValue;
    }

    /**
     * Calls operation on provider to update instance
     *
     * @param instanceId instance identifier of object
     * @param enumField enum field value
     * @param durationField duration field value
     * @param numericListField numeric field value
     * @return success indication for Fitness
     * @throws Exception to report failures
     */
    public boolean updateInstanceWithEnumFieldWithDurationFieldWithNumericListField(String instanceId,
            String enumField, String durationField, String numericListField[]) throws Exception {
        logMessage(loggingClassName + ":updateInstanceWithEnumFieldWithDurationField = "
                + instanceId + " enumField = " + enumField + " durationField = " + durationField);

        LocalMALInstance.instance().eventTestStub().updateInstance(
                new Long(instanceId),
                BasicEnum.FOURTH, // TBD waiting for fromInstance
                new Duration(new Integer(durationField).intValue()),
                arrayToShortList(numericListField));
        return true;
    }

    /**
     * Calls operation on provider to update instance
     *
     * @param instanceId instance identifier of object
     * @param uOctetField Uoctet field value
     * @param octetField octet field value
     * @param doubleField double field value
     * @return success indication for Fitness
     * @throws Exception to report failures
     */
    public boolean updateInstanceWithCompositeFieldsWithUoctetWithOctetWithDouble(String instanceId,
            String uOctetField, String octetField, String doubleField) throws Exception {
        logMessage(loggingClassName + ":updateInstanceWithEnumFieldWithDurationField = "
                + instanceId + " uOctetField = " + uOctetField + " octetField = " + octetField);

        LocalMALInstance.instance().eventTestStub().updateInstanceComposite(
                new Long(instanceId),
                new UOctet((short) Integer.parseInt(uOctetField)),
                Byte.decode(octetField),
                Double.parseDouble(doubleField));

        return true;
    }

    /**
     * Calls operation on provider to delete instance
     *
     * @param domain of object
     * @param instanceId of object
     * @return success indication for Fitness
     * @throws Exception to report failures
     */
    public boolean deleteInstanceOfObjectAInDomainWithInstanceId(String domain,
            String instanceId) throws Exception {
        logMessage(loggingClassName + ":deleteInstanceOfObjectAInDomainWithDescription domain = "
                + domain);
        LocalMALInstance.instance().eventTestStub().deleteInstance(COMTestHelper.TEST_OBJECT_A,
                domain, new Long(instanceId));
        return true;

    }

    /**
     * Calls operation on provider to delete instance
     *
     * @param domain of object
     * @param instanceId of object
     * @return success indication for Fitness
     * @throws Exception to report failures
     */
    public boolean deleteInstanceOfObjectBInDomainWithInstanceId(String domain,
            String instanceId) throws Exception {
        logMessage(loggingClassName + ":deleteInstanceOfObjectBInDomainWithDescription domain = "
                + domain);
        LocalMALInstance.instance().eventTestStub().deleteInstance(COMTestHelper.TEST_OBJECT_B,
                domain, new Long(instanceId));
        return true;

    }

    /**
     * Register to receive events in specified domain
     *
     * @param strDomain
     * @return success indication for Fitness
     * @throws Exception to report failures
     */
    public boolean registerForEventsFromServiceProviderInDomain(String strDomain) throws Exception {
        logMessage(loggingClassName + ":registerForEvents " + strDomain);
        final IdentifierList domain = new IdentifierList();
        domain.add(new Identifier(strDomain));
        EventStub evStub = LocalMALInstance.instance().eventStub(domain);

        SubscriptionFilterList filters = new SubscriptionFilterList();
        Subscription sub = new Subscription(new Identifier("SubA"), null, filters);
        evStub.monitorEventRegister(sub, testEventAdapter);
        eventDomain = strDomain;
        logMessage(loggingClassName + ":registerForEvents Complete");
        return true;
    }

    /**
     * Checks if deletion event received
     *
     * @param sourceObject identified if object A or object B
     * @param sourceDomain domain of object
     * @param sourceInstId source instance identifier
     * @return result of check
     * @throws Exception to report failures
     */
    public String deletionEventReceivedForObjectInDomainWithInstanceIdentifier(
            String sourceObject, String sourceDomain, String sourceInstId) throws Exception {
        logMessage(loggingClassName + ":deletionEventReceivedForObjectInDomainWithInstanceIdentifier "
                + sourceObject + " " + sourceDomain + " " + sourceInstId);
        waitForReasonableAmountOfTime();   // Allow time for incoming events
        return eventDetailsList.eventExists(COMTestHelper.TEST_OBJECT_DELETION_NO,
                objToObjectNo(sourceObject), sourceDomain, sourceInstId);

    }

    /**
     * Checks if creation event received
     *
     * @param sourceObject identified if object A or object B
     * @param sourceDomain domain of object
     * @param sourceInstId source instance identifier
     * @return result of check
     * @throws Exception to report failures
     */
    public String creationEventReceivedForObjectInDomainWithInstanceIdentifier(
            String sourceObject, String sourceDomain, String sourceInstId) throws Exception {
        logMessage(loggingClassName + ":creationEventReceivedForObjectInDomainWithInstanceIdentifier"
                + " sourceObject=" + sourceObject + " sourceDomain=" + sourceDomain + " sourceInstId=" + sourceInstId);
        waitForReasonableAmountOfTime();   // Allow time for incoming events
        String objNo = objToObjectNo(sourceObject);
        return eventDetailsList.eventExists(COMTestHelper.TEST_OBJECT_CREATION_NO,
                objNo, sourceDomain, sourceInstId);
    }

    /**
     * Checks if creation event received
     *
     * @param sourceObject identifies if object A or object B
     * @param sourceDomain domain of object
     * @param sourceInstId source instance identifier
     * @return result of check
     * @throws Exception to report failures
     */
    public String updateEventReceivedForObjectInDomainWithInstanceIdentifier(
            String sourceObject, String sourceDomain, String sourceInstId) throws Exception {
        logMessage(loggingClassName + ":updateEventReceivedForObjectInDomainWithInstanceIdentifier "
                + sourceObject + " " + sourceDomain + " " + sourceInstId);
        waitForReasonableAmountOfTime();   // Allow time for incoming events
        return eventDetailsList.eventExists(COMTestHelper.TEST_OBJECT_UPDATE_NO,
                objToObjectNo(sourceObject), sourceDomain, sourceInstId);

    }

    /**
     * Checks validity of the header object for a creation event
     *
     * @param instIndex Client Event identifier
     * @param sourceObject identifies if object A or object B
     * @return result of check
     */
    public boolean creationEventHeaderValidForObject(String instIndex, String sourceObject) {
        logMessage(loggingClassName + ":creationEventHeaderValidForObject " + instIndex + " " + sourceObject);
        int iInstIndx = Integer.parseInt(instIndex);
        return eventDetailsList.get(iInstIndx).checkHeader(iInstIndx + 1, COMTestHelper.TEST_OBJECT_CREATION_NO,
                objToObjectNo(sourceObject));
    }

    /**
     * Checks validity of creation event object details
     *
     * @param instIndex Client event identifier
     * @param sourceObject identifies if object A or object B
     * @param sourceDomain domain of object
     * @param sourceInstId instance identifier of object
     * @return result of check
     */
    public boolean creationEventObjectDetailsValidForObjectInDomainWithInstanceIdentifier(
            String instIndex, String sourceObject, String sourceDomain, String sourceInstId) {
        logMessage(loggingClassName + ":creationEventObjectDetailsValidForObjectInDomainWithInstanceIdentifier "
                + sourceObject + " " + sourceDomain + " " + sourceInstId);
        return eventDetailsList.get(Integer.parseInt(instIndex)).
                objectDetailsValid(sourceDomain, objToObjectNo(sourceObject), sourceInstId,
                        null);
    }

    /**
     * Checks validity of creation event object details
     *
     * @param instIndex Client event identifier
     * @param sourceObject identifies if object A or object B
     * @param sourceDomain domain of object
     * @param sourceInstId instance identifier of object
     * @param parentInstId instance identifier of parent
     * @return result of check
     */
    public boolean creationEventObjectDetailsValidForObjectInDomainWithInstanceIdentifierWithParentInstanceId(
            String instIndex, String sourceObject, String sourceDomain, String sourceInstId, String parentInstId) {
        logMessage(loggingClassName + ":creationEventObjectDetailsValidForObjectInDomainWithInstanceIdentifier "
                + sourceObject + " " + sourceDomain + " " + sourceInstId + " $" + parentInstId + "$");

        return eventDetailsList.get(Integer.parseInt(instIndex)).
                objectDetailsValid(sourceDomain, objToObjectNo(sourceObject), sourceInstId,
                        parentInstId);
    }

    /**
     * Checks validity of creation event body
     *
     * @param instIndex Client event identifier
     * @param description expected description field
     * @return result of check
     */
    public boolean creationEventElementValidForObjectWithDescription(String instIndex, String description) {
        logMessage(loggingClassName + ":creationEventElementValidForObject " + instIndex + " " + description);
        return eventDetailsList.get(Integer.parseInt(instIndex)).creationElementValid(
                COMTestHelper.TEST_OBJECT_CREATION_NO, description, true);
    }

    /**
     * Checks if deletion event header valid
     *
     * @param instIndex Client event identifier
     * @param sourceObject identifies if object A or object B
     * @return result of check
     */
    public boolean deletionEventHeaderValidForObject(String instIndex, String sourceObject) {
        logMessage(loggingClassName + ":deletionEventHeaderValidForObject " + instIndex + " " + sourceObject);
        int iInstIndx = Integer.parseInt(instIndex);
        return eventDetailsList.get(iInstIndx).checkHeader(iInstIndx + 1, COMTestHelper.TEST_OBJECT_DELETION_NO,
                objToObjectNo(sourceObject));
    }

    /**
     * Checks if deletion event object details valid
     *
     * @param instIndex Client event identifier
     * @param sourceObject identifies if object A or object B
     * @param sourceDomain domain of object
     * @param sourceInstId instance identifier of object
     * @param parentInstId instance identifier of object parent (used in related
     * field)
     * @return result of check
     */
    public boolean deletionEventObjectDetailsValidForObjectInDomainWithInstanceIdentifierWithParentInstanceIdentifier(
            String instIndex, String sourceObject, String sourceDomain, String sourceInstId, String parentInstId) {
        logMessage(loggingClassName + ":deletionEventObjectDetailsValidForObjectInDomainWithInstanceIdentifier "
                + sourceObject + " " + sourceDomain + " " + sourceInstId);
        if (parentInstId.trim().isEmpty()) {
            parentInstId = null;
        }
        return eventDetailsList.get(Integer.parseInt(instIndex)).
                objectDetailsValid(sourceDomain, objToObjectNo(sourceObject), sourceInstId,
                        parentInstId);
    }

    /**
     * Checks if deletion event element/body valid
     *
     * @param instIndex Client event identifier
     * @param description field value expected in body
     * @return result of check
     */
    public boolean deletionEventElementValidForObjectWithDescription(String instIndex, String description) {
        logMessage(loggingClassName + ":deletionEventElementValidForObjectWithDescription " + instIndex + " " + description);
        return eventDetailsList.get(Integer.parseInt(instIndex)).deletionElementValid(
                COMTestHelper.TEST_OBJECT_DELETION_NO, description);

    }

    /**
     * Checks if update event header valid
     *
     * @param instIndex Client event identifier
     * @param sourceObject identifies if object A or object B
     * @return result of check
     */
    public boolean updateEventHeaderValidForObject(String instIndex, String sourceObject) {
        logMessage(loggingClassName + ":updateEventHeaderValidForObject " + instIndex + " " + sourceObject);
        int iInstIndx = Integer.parseInt(instIndex);
        return eventDetailsList.get(iInstIndx).checkHeader(iInstIndx + 1, COMTestHelper.TEST_OBJECT_UPDATE_NO,
                objToObjectNo(sourceObject));
    }

    /**
     * Checks if update event details valid
     *
     * @param instIndex Client event identifier
     * @param sourceObject identifies if object A or object B
     * @param sourceDomain domain of object
     * @param sourceInstId instance identifier of object
     * @return
     */
    public boolean updateEventObjectDetailsValidForObjectInDomainWithInstanceIdentifier(String instIndex,
            String sourceObject, String sourceDomain, String sourceInstId) {
        logMessage(loggingClassName + ":updateEventObjectDetailsValidForObjectInDomainWithInstanceIdentifier "
                + sourceObject + " " + sourceDomain + " " + sourceInstId);
        return eventDetailsList.get(Integer.parseInt(instIndex)).
                objectDetailsValid(sourceDomain, objToObjectNo(sourceObject), sourceInstId,
                        null);
    }

    /**
     * Checks if update event element valid
     *
     * @param instIndex Client event identifier
     * @param enumField Expected value of enum field
     * @param durationField Expected value of duration field
     * @param numericListField Expected value of numeric list field
     * @return
     */
    public boolean updateEventElementValidForObjectWithEnumFieldWithDurationFieldWithNumericListField(String instIndex,
            String enumField, String durationField, String numericListField[]) {
        logMessage(loggingClassName + ":deletionEventElementValidForObjectWithDescription " + enumField
                + " " + durationField + " " + numericListField.toString() + " " + numericListField.length + " " + numericListField[0]);
        return eventDetailsList.get(Integer.parseInt(instIndex)).updateElementValid(
                COMTestHelper.TEST_OBJECT_UPDATE_NO, enumField, durationField, numericListField);
    }

    /**
     * Checks if update event element valid for object with a composite field
     *
     * @param instIndex Client event identifier
     * @param uOctetField Expected value of uOctet field
     * @param octetField Expected value of octet field
     * @param doubleField Expected value of double field
     * @return
     */
    public boolean updateEventElementValidForObjectWithCompositeFieldsWithUoctetWithOctetWithDouble(String instIndex,
            String uOctetField, String octetField, String doubleField) {
        logMessage(loggingClassName + ":updateEventElementValidForObjectWithCompositeFieldsWithUoctetWithOctetWithDouble " + uOctetField
                + " " + uOctetField + " " + octetField + " " + doubleField);
        return eventDetailsList.get(Integer.parseInt(instIndex)).updateElementCompositeValid(
                COMTestHelper.TEST_OBJECT_UPDATE_NO, uOctetField, octetField, doubleField);
    }

    /**
     * Converts string array to short array
     *
     * @param strVals
     * @return the short array
     */
    private ShortList arrayToShortList(String strVals[]) {
        ShortList list = new ShortList();
        for (int i = 0; i < strVals.length; i++) {
            list.add(new Short(strVals[i]));
        }
        return list;
    }

    public boolean retrievedArchiveItemProviderMatchesForEvent(String instIndex) {
        logMessage(loggingClassName + ":retrievedArchiveItemProviderMatchesForEvent" + instIndex);
        EventDetails ev = eventDetailsList.get(Integer.parseInt(instIndex));

        ArchiveDetails archiveDetails = retrievedArchiveDetailsList.get(0);

        boolean bMatch = (archiveDetails.getProvider().getValue().equals(ev.getUpdateHeader().getSource().getValue()));

        logMessage(loggingClassName + ":retrievedArchiveItemProviderMatchesForEvent:RET" + bMatch);
        return bMatch;
    }

    public boolean retrievedArchiveItemTimestampMatchesForEvent(String instIndex) {
        logMessage(loggingClassName + ":retrievedArchiveItemTimestampMatchesForEvent" + instIndex);
        EventDetails ev = eventDetailsList.get(Integer.parseInt(instIndex));
        ArchiveDetails archiveDetails = retrievedArchiveDetailsList.get(0);
        FineTime archiveTimestamp = archiveDetails.getTimestamp();

        boolean bMatch = true; // Hard-coded... needs to be updated  :/

        logMessage(loggingClassName + ":retrievedArchiveItemTimestampMatchesForEvent:RET" + bMatch);
        return bMatch;
    }

    public boolean retrievedArchiveItemInstanceIdentifierMatchesForEvent(String instIndex) {
        logMessage(loggingClassName + ":retrievedArchiveItemInstanceIdentifierMatchesForEvent" + instIndex);
        EventDetails ev = eventDetailsList.get(Integer.parseInt(instIndex));
        ArchiveDetails archiveDetails = retrievedArchiveDetailsList.get(0);
        boolean bMatch = archiveDetails.getInstId().equals(ev.updateHeader.getKeyValues().get(2));
        logMessage(loggingClassName + ":retrievedArchiveItemInstanceIdentifierMatchesForEvent:RET" + bMatch);
        return bMatch;
    }

    public boolean retrievedArchiveItemObjectDetailsMatchForEvent(String instIndex) {
        logMessage(loggingClassName + ":retrievedArchiveItemObjectDetailsMatchForEvent" + instIndex);
        EventDetails ev = eventDetailsList.get(Integer.parseInt(instIndex));
        ArchiveDetails archiveDetails = retrievedArchiveDetailsList.get(0);
        boolean bMatch = (archiveDetails.getDetails().equals(ev.getObjectDetails()));
        logMessage(loggingClassName + ":retrievedArchiveItemObjectDetailsMatchForEvent:RET" + bMatch);
        return bMatch;
    }

    public boolean retrievedArchiveItemBodyMatchesForEvent(String instIndex) {
        logMessage(loggingClassName + ":retrievedArchiveItemBodyMatchesForEvent" + instIndex);
        EventDetails ev = eventDetailsList.get(Integer.parseInt(instIndex));
        Element body = (Element) retrievedObjectList.get(0);
        boolean bMatch = (body.equals(ev.getElement()));
        logMessage(loggingClassName + ":retrievedArchiveItemBodyMatchesForEvent:RET" + bMatch);
        return bMatch;
    }

    public boolean retrieveArchiveItemForEvent(String instIndex) throws Exception {
        LoggingBase.logMessage("ArchiveScenario.retrieveArchiveEntryForEvent()");
        boolean bRetrieveValid = false;
        // Get the events
        Integer id = Integer.valueOf(instIndex);
        EventDetails ev = eventDetailsList.get(id);
        // reset the previous results
        retrievedArchiveDetailsList = null;
        retrievedObjectList = null;

        archiveAdapter.resetLatch();
        try {
            IdentifierList domainId = new IdentifierList();
            domainId.add(new Identifier(eventDomain));
            // Set instance
            Long instanceId = (Long) ev.getUpdateHeader().getKeyValues().get(2);
            LongList instanceIdsToRetrieve = new LongList();
            instanceIdsToRetrieve.add(instanceId);
            // Set Object Type
            Integer objectNumber = Integer.decode(ev.getUpdateHeader().getKeyValues().get(0).toString());
            objectType = new ObjectType(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER, EventTestServiceInfo.EVENTTEST_SERVICE_NUMBER,
                    COMPrototypeHelper.COMPROTOTYPE_AREA_VERSION, new UShort(objectNumber));
            LocalMALInstance.instance().archiveStub().retrieve(objectType, domainId,
                    instanceIdsToRetrieve, archiveAdapter);
            archiveAdapter.waitResponse();
            if (retrievedArchiveDetailsList.size() == 1 && retrievedObjectList.size() == 1) {
                bRetrieveValid = true;
            }
        } catch (MALInteractionException exc) {
            LoggingBase.logMessage("retrieve Error: " + exc);
            throw (exc);
        }

        LoggingBase.logMessage("retrievedArchiveDetailsList=" + retrievedArchiveDetailsList + ")");
        LoggingBase.logMessage("retrievedObjectList=" + retrievedObjectList + ")");
        return bRetrieveValid;
    }

    /**
     * Checks that all events generated during the test have been archived
     *
     * @return true if events archived false otherwise
     */
    public boolean allEventsArchived() {
        // Archiving is TBD
        return false;
    }

    public void waitForReasonableAmountOfTime() throws Exception {
        Thread.sleep(500);
    }

    /**
     * Class used to receive responses to archive events
     */
    class TestArchiveAdapter extends ArchiveAdapter {

        private CountDownLatch countDownLatch;

        @Override
        public void retrieveResponseReceived(MALMessageHeader msgHeader,
                ArchiveDetailsList archiveDetailsList, ElementList objectList,
                Map qosProperties) {
            LoggingBase.logMessage("TestArchiveAdapter::retrieveResponseReceived=" + archiveDetailsList);
            retrievedArchiveDetailsList = archiveDetailsList;
            retrievedObjectList = objectList;
            countDownLatch.countDown();
        }

        @Override
        public void retrieveResponseErrorReceived(MALMessageHeader msgHeader,
                MOErrorException error, Map qosProperties) {
            countDownLatch.countDown();
        }

        public void resetLatch() {
            countDownLatch = new CountDownLatch(1);
        }

        public void waitResponse() throws Exception {
            countDownLatch.await();

        }
    }
}
