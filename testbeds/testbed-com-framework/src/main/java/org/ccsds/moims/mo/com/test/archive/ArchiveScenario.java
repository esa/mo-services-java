/** *****************************************************************************
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
 ****************************************************************************** */
package org.ccsds.moims.mo.com.test.archive;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.CountDownLatch;

import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.archive.ArchiveServiceInfo;
import org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetails;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList;
import org.ccsds.moims.mo.com.archive.structures.ArchiveQuery;
import org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList;
import org.ccsds.moims.mo.com.archive.structures.CompositeFilter;
import org.ccsds.moims.mo.com.archive.structures.CompositeFilterList;
import org.ccsds.moims.mo.com.archive.structures.CompositeFilterSet;
import org.ccsds.moims.mo.com.archive.structures.ExpressionOperator;
import org.ccsds.moims.mo.com.archive.structures.QueryFilterList;
import org.ccsds.moims.mo.com.event.consumer.EventAdapter;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.com.structures.ObjectDetailsList;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.com.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.com.test.util.COMParseHelper;
import org.ccsds.moims.mo.comprototype.COMPrototypeHelper;
import org.ccsds.moims.mo.comprototype.archivetest.ArchiveTestServiceInfo;
import org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObject;
import org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObjectList;
import org.ccsds.moims.mo.comprototype.archivetest.structures.SubComposite;
import org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayload;
import org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayloadList;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.BlobList;
import org.ccsds.moims.mo.mal.structures.BooleanList;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.URIList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class ArchiveScenario {

    public static final String DEFAULT_PROVIDER_URI = "proto://provider";

    public static final Identifier ARCHIVE_EVENT_SUBSCRIPTION = new Identifier("archiveEvents");

    private ObjectType objectType;

    private ObjectDetails objectDetails;

    private ArchiveDetailsList archiveDetailsList;

    private List objectList;

    private ArchiveDetailsList retrievedArchiveDetailsList;

    private List retrievedObjectList;

    private ObjectId sourceId;

    private URI providerURI;

    private LongList returnedInstanceIds;

    private TestArchiveAdapter archiveAdapter;

    private MOErrorException returnedError;

    private UIntegerList invalidQueryIndexes;

    private UIntegerList unknownRetrieveIndexes;

    private UIntegerList invalidStoreIndexes;

    private UIntegerList duplicateStoreIndexes;

    private LongList instanceIdsToRetrieve;

    private ArchiveQueryList archiveQueryList;

    private QueryFilterList compositeFilterSetList;

    private CompositeFilterSet compositeFilterSet;

    private ArchiveDetailsList queriedArchiveDetailsList;

    private List<IdentifierList> queriedDomains;

    private List<ObjectType> queriedObjectTypes;

    private List queriedObjectList;

    private LongList countLongList;

    private LongList longList;

    private IdentifierList domain;

    private UpdateHeaderList notifiedUpdateHeaderList;

    private ObjectDetailsList notifiedObjectDetailsList;

    private List notifiedEventBodyList;

    private UpdateHeader selectedNotifiedUpdateHeader;

    private ObjectDetails selectedNotifiedObjectDetails;

    private Object selectedNotifiedEventBody;

    private LongList instanceIdsToDelete;

    private LongList deletedInstanceIds;

    private UIntegerList unknownDeleteIndexes;

    private UIntegerList invalidUpdateIndexes;

    private UIntegerList unknownUpdateIndexes;

    public boolean resetArchiveScenario() {
        providerURI = new URI("provider URI");
        objectList = new ArrayList();
        archiveDetailsList = new ArchiveDetailsList();
        objectDetails = null;
        archiveAdapter = new TestArchiveAdapter();
        returnedError = null;
        instanceIdsToRetrieve = new LongList();
        instanceIdsToDelete = new LongList();
        archiveQueryList = new ArchiveQueryList();
        resetCompositeFilterSetList();
        resetObjectType();
        longList = new LongList();
        resetCompositeFilterSet();
        domain = new IdentifierList();
        queriedDomains = new ArrayList<>();
        queriedObjectTypes = new ArrayList<>();
        return true;
    }

    public boolean setNullObjectList() {
        objectList = null;
        return true;
    }

    public boolean resetInstanceIdsToRetrieve() {
        instanceIdsToRetrieve.clear();
        return true;
    }

    public boolean resetInstanceIdsToDelete() {
        instanceIdsToDelete.clear();
        return true;
    }

    public boolean resetCompositeFilterSetList() {
        compositeFilterSetList = new QueryFilterList();
        return true;
    }

    public boolean resetCompositeFilterSet() {
        compositeFilterSet = new CompositeFilterSet(new CompositeFilterList());
        return true;
    }

    /**
     * Default is the first object type (1)
     *
     * @return
     */
    public boolean resetObjectType() {
        return resetObjectType(1);
    }

    public boolean resetObjectType(int typeNumber) {
        objectType = new ObjectType(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER,
                ArchiveTestServiceInfo.ARCHIVETEST_SERVICE_NUMBER,
                COMPrototypeHelper.COMPROTOTYPE_AREA_VERSION,
                new UShort(typeNumber));
        return true;
    }

    public boolean testArchiveClientHasBeenCreated() throws Exception {
        return (null != LocalMALInstance.instance().archiveTestStub()
                && null != LocalMALInstance.instance().archiveStub());
    }

    public boolean callResetTestOnServiceProvider() throws Exception {
        LoggingBase.logMessage("ArchiveScenario.callResetTestOnServiceProvider()");
        LocalMALInstance.instance().archiveTestStub().reset();
        return true;
    }

    public boolean clearArchiveDetailsList() {
        archiveDetailsList.clear();
        return true;
    }

    public boolean clearTestObjectPayloadList() {
        objectList.clear();
        return true;
    }

    public boolean clearArchiveQueryList() {
        archiveQueryList.clear();
        return true;
    }

    private static void parseDomain(String domainIdToParse, IdentifierList result) {
        StringTokenizer st = new StringTokenizer(domainIdToParse, ".");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            result.add(new Identifier(token));
        }
    }

    public boolean setDomain(String domainIdToParse) {
        domain.clear();
        parseDomain(domainIdToParse, domain);
        return true;
    }

    public boolean setObjectTypeWithAreaAndServiceAndVersionAndNumber(int area, int service, short version, int number) {
        objectType = new ObjectType(new UShort(area), new UShort(service), new UOctet(version), new UShort(number));
        return true;
    }

    public boolean setSourceId(long instanceId) {
        sourceId = new ObjectId(objectType, new ObjectKey(domain, instanceId));
        return true;
    }

    public boolean createObjectDetails(long related) {
        objectDetails = new ObjectDetails(related, sourceId);
        return true;
    }

    public boolean addArchiveDetailsWithInstanceIdAndNetwork(long instanceId, String network) {
        return addArchiveDetailsWithInstanceIdAndNetworkAndTimestamp(instanceId, network, "" + System.currentTimeMillis());
    }

    public boolean addArchiveDetailsWithInstanceIdAndNetworkAndTimestamp(long instanceId, String network, String timestamp) {
        return addArchiveDetailsWithInstanceIdAndNetworkAndTimestampAndProviderUri(instanceId,
                network, timestamp, DEFAULT_PROVIDER_URI);
    }

    public boolean addArchiveDetailsWithInstanceIdAndNetworkAndTimestampAndProviderUri(
            long instanceId, String networkToParse, String timestampToParse, String providerUriToParse) {
        Identifier network = COMParseHelper.parseIdentifier(networkToParse);
        FineTime timestamp = COMParseHelper.parseFineTime(timestampToParse);
        URI providerUri = COMParseHelper.parseURI(providerUriToParse);
        ArchiveDetails archiveDetails = new ArchiveDetails(instanceId,
                objectDetails, network, timestamp,
                providerUri);
        archiveDetailsList.add(archiveDetails);
        return true;
    }

    public boolean addArchiveQueryWithWildcards() {
        ArchiveQuery archiveQuery = new ArchiveQuery(null, null, null, 0L, null,
                null, null, null, null);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addArchiveQueryWithDomain(String filterDomainToParse) {
        IdentifierList filterDomain = new IdentifierList();
        parseDomain(filterDomainToParse, filterDomain);
        ArchiveQuery archiveQuery = new ArchiveQuery(filterDomain, null, null,
                0L, null, null, null, null, null);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addArchiveQueryWithDomainAndSortingOrderAndField(String filterDomainToParse,
            String sortingOrderToParse, String fieldNameToParse) {
        IdentifierList filterDomain = new IdentifierList();
        parseDomain(filterDomainToParse, filterDomain);
        Boolean sortingOrder = COMParseHelper.parseBoolean(sortingOrderToParse);
        String fieldName = COMParseHelper.parseString(fieldNameToParse);
        ArchiveQuery archiveQuery = new ArchiveQuery(filterDomain, null,
                null, 0L, null, null, null, sortingOrder, fieldName);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addArchiveQueryWithNetworkAndProviderUriAndRelatedAndStartTimeAndEndTimeAndSortOrderAndSortFieldName(
            String network, String providerUri, Long related, long startTime,
            long endTime, boolean sortOrder, String sortFieldName) {
        ArchiveQuery archiveQuery = new ArchiveQuery(domain,
                new Identifier(network), new URI(providerUri), related, sourceId,
                new FineTime(startTime), new FineTime(endTime), sortOrder,
                sortFieldName);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addCompositeFilterWithFieldNameAndOperatorAndInteger(String fieldName, String operatorName, String valueToParse) {
        Attribute value;
        Integer intValue = COMParseHelper.parseInt(valueToParse);
        if (intValue == null) {
            value = null;
        } else {
            value = new Union(intValue);
        }
        ExpressionOperator op = ExpressionOperator.fromString(operatorName);
        CompositeFilter compositeFilter = new CompositeFilter(fieldName, op, value);
        compositeFilterSet.getFilters().add(compositeFilter);
        return true;
    }

    public boolean addCompositeFilterWithFieldNameAndOperatorAndUinteger(String fieldName, String operatorName, String valueToParse) {
        UInteger value = COMParseHelper.parseUInt(valueToParse);
        ExpressionOperator op = ExpressionOperator.fromString(operatorName);
        CompositeFilter compositeFilter = new CompositeFilter(fieldName, op, value);
        compositeFilterSet.getFilters().add(compositeFilter);
        return true;
    }

    public boolean addCompositeFilterWithFieldNameAndOperatorAndString(String fieldName, String operatorName, String valueToParse) {
        Attribute value;
        String stringValue = COMParseHelper.parseString(valueToParse);
        if (stringValue == null) {
            value = null;
        } else {
            value = new Union(stringValue);
        }
        ExpressionOperator op = ExpressionOperator.fromString(operatorName);
        CompositeFilter compositeFilter = new CompositeFilter(fieldName, op, value);
        compositeFilterSet.getFilters().add(compositeFilter);
        return true;
    }

    public boolean addCompositeFilterWithFieldNameAndOperatorAndIdentifier(String fieldName, String operatorName, String valueToParse) {
        Identifier idValue = COMParseHelper.parseIdentifier(valueToParse);
        ExpressionOperator op = ExpressionOperator.fromString(operatorName);
        CompositeFilter compositeFilter = new CompositeFilter(fieldName, op, idValue);
        compositeFilterSet.getFilters().add(compositeFilter);
        return true;
    }

    public boolean addCompositeFilterWithFieldNameAndOperatorAndURI(String fieldName, String operatorName, String valueToParse) {
        URI idValue = COMParseHelper.parseURI(valueToParse);
        ExpressionOperator op = ExpressionOperator.fromString(operatorName);
        CompositeFilter compositeFilter = new CompositeFilter(fieldName, op, idValue);
        compositeFilterSet.getFilters().add(compositeFilter);
        return true;
    }

    public boolean addCompositeFilterWithFieldNameAndOperatorAndBlob(String fieldName, String operatorName, String valueToParse) {
        Blob blob = COMParseHelper.parseBlob(valueToParse);
        ExpressionOperator op = ExpressionOperator.fromString(operatorName);
        CompositeFilter compositeFilter = new CompositeFilter(fieldName, op, blob);
        compositeFilterSet.getFilters().add(compositeFilter);
        return true;
    }

    public boolean setNullQueryFilterList() {
        compositeFilterSetList = null;
        return true;
    }

    public boolean addNullQueryFilter() {
        compositeFilterSetList.add(null);
        return true;
    }

    public boolean addNullCompositeFilterSet() {
        compositeFilterSetList.add(null);
        return true;
    }

    public boolean addCompositeFilterSet() {
        compositeFilterSetList.add(compositeFilterSet);
        return true;
    }

    public boolean addArchiveQueryWithNetwork(String network) {
        ArchiveQuery archiveQuery = new ArchiveQuery(null,
                new Identifier(network), null, 0L, null, null, null, null, null);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addArchiveQueryWithProviderUri(String providerUri) {
        ArchiveQuery archiveQuery = new ArchiveQuery(null,
                null, new URI(providerUri), 0L, null, null, null, null, null);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addArchiveQueryWithRelated(long related) {
        ArchiveQuery archiveQuery = new ArchiveQuery(null,
                null, null, related, null, null, null, null, null);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addArchiveQueryWithSource(long instanceId) {
        ObjectId srcId = new ObjectId(objectType, new ObjectKey(domain, instanceId));
        ArchiveQuery archiveQuery = new ArchiveQuery(null,
                null, null, 0L, srcId, null, null, null, null);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addArchiveQueryWithStartTime(long time) {
        ArchiveQuery archiveQuery = new ArchiveQuery(null,
                null, null, 0L, null, new FineTime(time), null, null, null);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addArchiveQueryWithStartTimeAndEndTime(long startTime,
            long endTime) {
        ArchiveQuery archiveQuery = new ArchiveQuery(null, null, null, 0L, null,
                new FineTime(startTime), new FineTime(endTime), null, null);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addArchiveQueryWithStartTimeAndFutureEndTime(long startTime) {
        ArchiveQuery archiveQuery = new ArchiveQuery(null, null, null, 0L, null,
                new FineTime(startTime), new FineTime(System.currentTimeMillis() + 60000), null, null);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addArchiveQueryWithEndTime(long time) {
        ArchiveQuery archiveQuery = new ArchiveQuery(null,
                null, null, 0L, null, null, new FineTime(time), null, null);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addArchiveQueryWithSortingOrderAndField(String sortingOrderToParse, String fieldNameToParse) {
        Boolean sortingOrder = COMParseHelper.parseBoolean(sortingOrderToParse);
        String fieldName = COMParseHelper.parseString(fieldNameToParse);
        ArchiveQuery archiveQuery = new ArchiveQuery(null,
                null, null, 0L, null, null, null, sortingOrder, fieldName);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addArchiveQueryWithNetworkAndRelatedAndStartTimeAndEndTimeAndSortOrder(
            String network, long related, long startTime, long endTime,
            boolean sortOrder) {
        // TODO: test the domain
        ArchiveQuery archiveQuery = new ArchiveQuery(new IdentifierList(),
                new Identifier(network), providerURI, related, sourceId, new FineTime(
                        startTime), new FineTime(endTime), sortOrder, null);
        archiveQueryList.add(archiveQuery);
        return true;
    }

    public boolean addObjectPayloadWithIntAndSubCompositeAndInt(String intToParse,
            boolean setSubComposite, String subIntToParse) {

        Integer integerField = COMParseHelper.parseInt(intToParse);
        Integer subIntegerField = COMParseHelper.parseInt(subIntToParse);

        SubComposite subComposite;
        if (setSubComposite) {
            subComposite = new SubComposite(subIntegerField);
        } else {
            subComposite = null;
        }
        TestObjectPayload testObjectPayload = new TestObjectPayload(null, integerField,
                null, subComposite, null, null);
        objectList.add(testObjectPayload);
        return true;
    }

    public boolean addObjectPayloadWithString(String stringToParse) {
        String stringField = COMParseHelper.parseString(stringToParse);
        TestObjectPayload testObjectPayload = new TestObjectPayload(null,
                null, stringField, null, null, null);
        objectList.add(testObjectPayload);
        return true;
    }

    public boolean addObjectPayloadWithEnumerated(String enumeratedToParse) {
        EnumeratedObject enumerated = EnumeratedObject.fromString(enumeratedToParse);
        TestObjectPayload testObjectPayload = new TestObjectPayload(null,
                null, null, null, enumerated, null);
        objectList.add(testObjectPayload);
        return true;
    }

    public boolean addObjectPayloadWithList() {
        TestObjectPayload testObjectPayload = new TestObjectPayload(null,
                null, null, null, null, new IntegerList());
        objectList.add(testObjectPayload);
        return true;
    }

    public boolean addIntegerPayload(int payload) {
        objectList.add(payload);
        return true;
    }

    public boolean addEnumeratedPayload(String enumerated) {
        EnumeratedObject enumeratedObject = EnumeratedObject.fromString(enumerated);
        objectList.add(enumeratedObject);
        return true;
    }

    public boolean addBooleanPayload(boolean payload) {
        objectList.add(payload);
        return true;
    }

    public boolean addStringPayload(String payload) {
        objectList.add(payload);
        return true;
    }

    public boolean addLongPayload(long payload) {
        objectList.add(payload);
        return true;
    }

    public boolean addIdentifierPayload(String toParse) {
        Identifier id = COMParseHelper.parseIdentifier(toParse);
        objectList.add(id);
        return true;
    }

    public boolean addUriPayload(String toParse) {
        URI uri = COMParseHelper.parseURI(toParse);
        objectList.add(uri);
        return true;
    }

    public boolean addBlobPayload(String valueToParse) {
        Blob blob = COMParseHelper.parseBlob(valueToParse);
        objectList.add(blob);
        return true;
    }

    private ElementList getObjectList() throws Exception {
        if (objectList == null) {
            return null;
        }

        Object firstObjectToStore = objectList.get(0);

        ElementList specificList;
        if (firstObjectToStore instanceof TestObjectPayload) {
            specificList = new TestObjectPayloadList();
        } else if (firstObjectToStore instanceof Integer) {
            specificList = new IntegerList();
        } else if (firstObjectToStore instanceof Boolean) {
            specificList = new BooleanList();
        } else if (firstObjectToStore instanceof Long) {
            specificList = new LongList();
        } else if (firstObjectToStore instanceof String) {
            specificList = new StringList();
        } else if (firstObjectToStore instanceof EnumeratedObject) {
            specificList = new EnumeratedObjectList();
        } else if (firstObjectToStore instanceof Identifier) {
            specificList = new IdentifierList();
        } else if (firstObjectToStore instanceof URI) {
            specificList = new URIList();
        } else if (firstObjectToStore instanceof Blob) {
            specificList = new BlobList();
        } else {
            throw new Exception("Unexpected type: " + firstObjectToStore.getClass());
        }
        specificList.addAll(objectList);

        return specificList;
    }

    public boolean store(boolean returnInstanceIds) throws Exception {
        LoggingBase.logMessage("ArchiveScenario.store(" + returnInstanceIds + ")");

        // Reset previous results
        returnedInstanceIds = null;
        returnedError = null;
        invalidStoreIndexes = null;
        duplicateStoreIndexes = null;

        ElementList specificList = getObjectList();

        try {
            returnedInstanceIds = LocalMALInstance.instance().archiveStub().store(
                    returnInstanceIds, objectType, domain, archiveDetailsList, specificList);
        } catch (MALInteractionException exc) {
            onStoreError(exc.getStandardError());
        }

        LoggingBase.logMessage("returnedInstanceIds=" + returnedInstanceIds);
        LoggingBase.logMessage("returnedError=" + returnedError);
        return true;
    }

    private void onStoreError(MOErrorException error) {
        returnedError = error;
        if (COMHelper.INVALID_ERROR_NUMBER.equals(returnedError.getErrorNumber())) {
            Object extraInfo = returnedError.getExtraInformation();
            if (extraInfo instanceof UIntegerList) {
                invalidStoreIndexes = (UIntegerList) extraInfo;
            }
        } else if (COMHelper.DUPLICATE_ERROR_NUMBER.equals(returnedError.getErrorNumber())) {
            Object extraInfo = returnedError.getExtraInformation();
            if (extraInfo instanceof UIntegerList) {
                duplicateStoreIndexes = (UIntegerList) extraInfo;
            }
        }
    }

    public boolean update() throws Exception {
        LoggingBase.logMessage("ArchiveScenario.update()");

        // Reset previous results
        returnedInstanceIds = null;
        returnedError = null;
        invalidUpdateIndexes = null;
        unknownUpdateIndexes = null;

        ElementList specificList = getObjectList();

        try {
            LocalMALInstance.instance().archiveStub().update(
                    objectType, domain, archiveDetailsList, specificList);
        } catch (MALInteractionException exc) {
            onUpdateError(exc.getStandardError());
        }

        LoggingBase.logMessage("returnedError=" + returnedError);
        return true;
    }

    private void onUpdateError(MOErrorException error) {
        returnedError = error;
        if (COMHelper.INVALID_ERROR_NUMBER.equals(returnedError.getErrorNumber())) {
            Object extraInfo = returnedError.getExtraInformation();
            if (extraInfo instanceof UIntegerList) {
                invalidUpdateIndexes = (UIntegerList) extraInfo;
            }
        } else if (MALHelper.UNKNOWN_ERROR_NUMBER.equals(returnedError.getErrorNumber())) {
            Object extraInfo = returnedError.getExtraInformation();
            if (extraInfo instanceof UIntegerList) {
                unknownUpdateIndexes = (UIntegerList) extraInfo;
            }
        }
    }

    public boolean invalidUpdateIndexAtIs(int index, long expectedIndex) {
        return invalidUpdateIndexes.get(index).getValue() == expectedIndex;
    }

    public boolean unknownUpdateIndexAtIs(int index, long expectedIndex) {
        return unknownUpdateIndexes.get(index).getValue() == expectedIndex;
    }

    public boolean delete() throws Exception {
        LoggingBase.logMessage("ArchiveScenario.delete()");

        deletedInstanceIds = null;
        unknownDeleteIndexes = null;

        try {
            deletedInstanceIds = LocalMALInstance.instance().archiveStub().delete(
                    objectType, domain, instanceIdsToDelete);
        } catch (MALInteractionException exc) {
            onDeleteError(exc.getStandardError());
        }

        LoggingBase.logMessage("deletedInstanceIds=" + deletedInstanceIds);
        LoggingBase.logMessage("unknownDeleteIndexes=" + unknownDeleteIndexes);
        return true;
    }

    private void onDeleteError(MOErrorException error) {
        returnedError = error;
        if (MALHelper.UNKNOWN_ERROR_NUMBER.equals(returnedError.getErrorNumber())) {
            Object extraInfo = returnedError.getExtraInformation();
            if (extraInfo instanceof UIntegerList) {
                unknownDeleteIndexes = (UIntegerList) extraInfo;
            }
        }
    }

    public boolean unknownDeleteIndexAtIs(int index, long expectedIndex) {
        return unknownDeleteIndexes.get(index).getValue() == expectedIndex;
    }

    public boolean deletedInstanceIdListSizeIs(int size) {
        return (deletedInstanceIds.size() == size);
    }

    public boolean deletedInstanceIdListContains(long value) {
        return deletedInstanceIds.contains(value);
    }

    public boolean returnedInstanceIdListSizeIs(int size) {
        return (returnedInstanceIds.size() == size);
    }

    public boolean returnedInstanceIdAtIndexIs(int index, long value) {
        return returnedInstanceIds.get(index) == value;
    }

    public boolean returnedInstanceIdListIsNull() {
        return (returnedInstanceIds == null);
    }

    public boolean returnedErrorIsInvalid() {
        return returnedError.getErrorNumber().getValue() == COMHelper._INVALID_ERROR_NUMBER;
    }

    public boolean returnedErrorIsUnknown() {
        return returnedError.getErrorNumber().getValue() == MALHelper._UNKNOWN_ERROR_NUMBER;
    }

    public boolean returnedErrorIsDuplicate() {
        return returnedError.getErrorNumber().getValue() == COMHelper._DUPLICATE_ERROR_NUMBER;
    }

    public boolean noReturnedError() {
        return returnedError == null;
    }

    public boolean addReturnedInstanceIdsInRetrieveList() throws Exception {
        instanceIdsToRetrieve.addAll(returnedInstanceIds);
        return true;
    }

    public boolean retrieve() throws Exception {
        LoggingBase.logMessage("ArchiveScenario.retrieve()");

        // reset the previous results
        retrievedArchiveDetailsList = null;
        retrievedObjectList = null;
        returnedError = null;
        unknownRetrieveIndexes = null;

        archiveAdapter.resetLatch();

        try {
            LocalMALInstance.instance().archiveStub().retrieve(objectType, domain, instanceIdsToRetrieve, archiveAdapter);
            archiveAdapter.waitResponse();
        } catch (MALInteractionException exc) {
            onRetrieveError(exc.getStandardError());
        }

        LoggingBase.logMessage("retrievedArchiveDetailsList=" + retrievedArchiveDetailsList + ")");
        LoggingBase.logMessage("retrievedObjectList=" + retrievedObjectList + ")");
        LoggingBase.logMessage("returnedError=" + returnedError + ")");
        LoggingBase.logMessage("unknownRetrieveIndexes=" + unknownRetrieveIndexes);

        return true;
    }

    private void onRetrieveError(MOErrorException error) {
        returnedError = error;
        if (MALHelper.UNKNOWN_ERROR_NUMBER.equals(returnedError.getErrorNumber())) {
            Object extraInfo = returnedError.getExtraInformation();
            if (extraInfo instanceof UIntegerList) {
                LoggingBase.logMessage("set unknownRetrieveIndexes");
                unknownRetrieveIndexes = (UIntegerList) extraInfo;
            }
        }
    }

    public boolean retrieveAll() throws Exception {
        instanceIdsToRetrieve.add(0L);
        return retrieve();
    }

    public boolean subscribeToArchiveEvents() {
        notifiedUpdateHeaderList = new UpdateHeaderList();
        notifiedObjectDetailsList = new ObjectDetailsList();
        notifiedEventBodyList = new ArrayList();

        long secondSubKey = 0L;
        secondSubKey |= (COMHelper.COM_AREA.getVersion().getValue() & 0xFF) << 24;
        secondSubKey |= (ArchiveServiceInfo._ARCHIVE_SERVICE_NUMBER & 0xFFFFL) << 32;
        secondSubKey |= (COMHelper._COM_AREA_NUMBER & 0xFFFFL) << 48;

        /*
    EntityKey everyArchiveEventKey = new EntityKey(new Identifier("*"),
            secondSubKey, 0L, 0L);

    EntityKeyList entityKeys = new EntityKeyList();
    entityKeys.add(everyArchiveEventKey);

    EntityRequest entityRequest = new EntityRequest(null, Boolean.FALSE,
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, entityKeys);

    EntityRequestList entityRequestList = new EntityRequestList();
    entityRequestList.add(entityRequest);
         */
        SubscriptionFilterList filters = new SubscriptionFilterList();
        Subscription subscription = new Subscription(ARCHIVE_EVENT_SUBSCRIPTION, domain, null, filters);
        try {
            LoggingBase.logMessage("subscribeToArchiveEvents:: calling monitorEventRegister");
            LocalMALInstance.instance().archiveEventStub()
                    .monitorEventRegister(subscription, new ArchiveEventListener());
            LoggingBase.logMessage("subscribeToArchiveEvents calling monitorEventRegister RET");
            return true;
        } catch (MALInteractionException exc) {
            LoggingBase.logMessage("subscribeToArchiveEvents MALInteractionException " + exc);
            exc.printStackTrace();
            return false;
        } catch (MALException exc) {
            LoggingBase.logMessage("subscribeToArchiveEvents MALException " + exc);
            exc.printStackTrace();
            return false;
        }
    }

    public boolean unsubscribeFromArchiveEvents() {
        try {
            IdentifierList ids = new IdentifierList();
            ids.add(ARCHIVE_EVENT_SUBSCRIPTION);
            LocalMALInstance.instance().archiveEventStub()
                    .monitorEventDeregister(ids);
            return true;
        } catch (MALInteractionException exc) {
            return false;
        } catch (MALException exc) {
            return false;
        }
    }

    public boolean notifiedUpdateHeaderListIs(int size) {
        // Small delay to allow sufficient time to receive event
        waitForShortTime();
        return notifiedUpdateHeaderList.size() == size;
    }

    public boolean notifiedObjectDetailsListIs(int size) {
        return notifiedObjectDetailsList.size() == size;
    }

    public boolean notifiedEventBodyListIs(int size) {
        return notifiedEventBodyList.size() == size;
    }

    public boolean selectNotifiedObjectWithSourceInstanceId(long instanceId) {
        int index = 0;
        LoggingBase.logMessage("selectNotifiedObjectWithSourceInstanceId=" + notifiedObjectDetailsList);
        for (ObjectDetails od : notifiedObjectDetailsList) {
            if (od.getSource().getKey().getInstId() == instanceId) {
                selectedNotifiedUpdateHeader = notifiedUpdateHeaderList.get(index);
                selectedNotifiedObjectDetails = notifiedObjectDetailsList.get(index);
                return true;
            }
            index++;
        }
        return false;
    }

    public boolean selectedNotifiedObjectNumberIs(String objectNumber) {
        return selectedNotifiedUpdateHeader.getKeyValues().get(0).getValue().equals(new Identifier(objectNumber));
    }

    public boolean queriedArchiveDetailsListIsEqualToLocalList() {
        LoggingBase.logMessage("queriedArchiveDetailsList=" + queriedArchiveDetailsList);
        LoggingBase.logMessage("archiveDetailsList=" + archiveDetailsList);
        return archiveDetailsList.equals(queriedArchiveDetailsList);
    }

    public boolean countLongListIsEqualToLocalList() {
        LoggingBase.logMessage("countLongList=" + countLongList);
        LoggingBase.logMessage("longList=" + longList);
        return longList.equals(countLongList);
    }

    public boolean retrievedObjectListContainsInstanceIdAndHasIntegerValue(long instanceId, int intValue) {
        int index = 0;
        for (ArchiveDetails ad : retrievedArchiveDetailsList) {
            if (ad.getInstId() == instanceId) {
                TestObjectPayload object = (TestObjectPayload) retrievedObjectList.get(index);
                return object.getIntegerField() == intValue;
            }
            index++;
        }
        return false;
    }

    public boolean queriedObjectListIsEqualToLocalList() {
        LoggingBase.logMessage("queriedObjectList=" + queriedObjectList);
        LoggingBase.logMessage("objectList=" + objectList);
        return objectList.equals(queriedObjectList);
    }

    public boolean retrievedArchiveDetailsListSizeIs(int size) {
        // TODO: check if COM allows NULL list as a returned value
        //if (retrievedArchiveDetailsList == null) return (size == 0);
        return retrievedArchiveDetailsList.size() == size;
    }

    public boolean retrievedArchiveDetailsListIsNull() {
        return retrievedArchiveDetailsList == null;
    }

    public boolean retrievedObjectListSizeIs(int size) {
        // TODO: check if COM allows NULL list as a returned value
        //if (retrievedObjectList == null) return (size == 0);
        return retrievedObjectList.size() == size;
    }

    public boolean retrievedObjectListIsNull() {
        // TODO: check if COM allows NULL list as a returned value
        //if (retrievedObjectList == null) return (size == 0);
        return retrievedObjectList == null;
    }

    public boolean queriedArchiveDetailsListIsNull() {
        return (queriedArchiveDetailsList == null);
    }

    public boolean queriedArchiveDetailsListSizeIs(int size) {
        LoggingBase.logMessage("ArchiveScenario.queriedArchiveDetailsListSizeIs(" + size + ")");
        LoggingBase.logMessage("queriedArchiveDetailsList=" + queriedArchiveDetailsList);
        return queriedArchiveDetailsList.size() == size;
    }

    public boolean queriedObjectListIsNull() {
        return queriedObjectList == null;
    }

    public boolean queriedObjectListSizeIs(int size) {
        return queriedObjectList.size() == size;
    }

    public boolean queriedObjectAtIndexHasIntegerValue(int index, String intToParse) {
        Integer expectedIntValue = COMParseHelper.parseInt(intToParse);
        TestObjectPayload objectPayload = (TestObjectPayload) queriedObjectList.get(index);
        if (expectedIntValue == null) {
            return objectPayload.getIntegerField() == null;
        } else {
            return expectedIntValue.equals(objectPayload.getIntegerField());
        }
    }

    public boolean queriedObjectAtIndexHasStringValue(int index, String toParse) {
        String expectedStringValue = COMParseHelper.parseString(toParse);
        TestObjectPayload objectPayload = (TestObjectPayload) queriedObjectList.get(index);
        if (expectedStringValue == null) {
            return objectPayload.getStringField() == null;
        } else {
            return expectedStringValue.equals(objectPayload.getStringField());
        }
    }

    public boolean queriedObjectAtIndexHasEnumeratedValue(int index, String toParse) {
        EnumeratedObject expectedEnumeratedValue = EnumeratedObject.fromString(toParse);
        TestObjectPayload objectPayload = (TestObjectPayload) queriedObjectList.get(index);
        return expectedEnumeratedValue.equals(objectPayload.getEnumeratedField());
    }

    public boolean queriedObjectAtIndexIsInt(int index, int expectedValue) {
        Integer objectPayload = (Integer) queriedObjectList.get(index);
        return objectPayload == expectedValue;
    }

    public boolean queriedObjectAtIndexIsEnumerated(int index, String expectedValue) {
        EnumeratedObject expectedEnumerated = EnumeratedObject.fromString(expectedValue);
        EnumeratedObject objectPayload = (EnumeratedObject) queriedObjectList.get(index);
        return expectedEnumerated.equals(objectPayload);
    }

    public boolean queriedDomainAtIndexIs(int index, String domainIdToParse) {
        IdentifierList expectedDomain = new IdentifierList();
        parseDomain(domainIdToParse, domain);
        return expectedDomain.equals(queriedDomains.get(index));
    }

    public boolean queriedObjectAtIndexHasCompositeIntegerValue(int index, int expectedIntValue) {
        TestObjectPayload objectPayload = (TestObjectPayload) queriedObjectList.get(index);
        return objectPayload.getCompositeField().getIntegerField() == expectedIntValue;
    }

    public boolean queriedObjectAtIndexHasNullCompositeValue(int index) {
        TestObjectPayload objectPayload = (TestObjectPayload) queriedObjectList.get(index);
        return objectPayload.getCompositeField() == null;
    }

    public boolean queriedObjectAtIndexHasTimestamp(int index, long timestamp) {
        ArchiveDetails archiveDetails = (ArchiveDetails) queriedArchiveDetailsList.get(index);
        return archiveDetails.getTimestamp().getValue() == timestamp;
    }

    public boolean queriedObjectListContainsTimestamp(long timestamp) {
        for (ArchiveDetails archiveDetails : queriedArchiveDetailsList) {
            if (archiveDetails.getTimestamp().getValue() == timestamp) {
                return true;
            }
        }
        return false;
    }

    public boolean queriedObjectAtIndexHasProviderUri(int index, String providerUri) {
        ArchiveDetails archiveDetails = (ArchiveDetails) queriedArchiveDetailsList.get(index);
        return archiveDetails.getProvider().getValue().equals(providerUri);
    }

    public boolean queriedObjectAtIndexHasNetwork(int index, String network) {
        ArchiveDetails archiveDetails = (ArchiveDetails) queriedArchiveDetailsList.get(index);
        return archiveDetails.getNetwork().getValue().equals(network);
    }

    public boolean queriedObjectAtIndexHasInstanceId(int index, long instanceId) {
        ArchiveDetails archiveDetails = (ArchiveDetails) queriedArchiveDetailsList.get(index);
        return archiveDetails.getInstId() == instanceId;
    }

    public boolean queriedObjectAtIndexHasSourceInstanceId(int index, long instanceId) {
        ArchiveDetails archiveDetails = (ArchiveDetails) queriedArchiveDetailsList.get(index);
        return archiveDetails.getDetails().getSource().getKey().getInstId() == instanceId;
    }

    public boolean queriedObjectAtIndexHasRelated(int index, long related) {
        ArchiveDetails archiveDetails = (ArchiveDetails) queriedArchiveDetailsList.get(index);
        return archiveDetails.getDetails().getRelated() == related;
    }

    public boolean invalidQueryIndexAtIs(int index, long expectedQueryIndex) {
        return invalidQueryIndexes.get(index).getValue() == expectedQueryIndex;
    }

    public boolean invalidStoreIndexAtIs(int index, long expectedQueryIndex) {
        return invalidStoreIndexes.get(index).getValue() == expectedQueryIndex;
    }

    public boolean unknownRetrieveIndexAtIs(int index, long expectedQueryIndex) {
        return unknownRetrieveIndexes.get(index).getValue() == expectedQueryIndex;
    }

    public boolean addInstanceIdToRetrieve(long instanceId) {
        instanceIdsToRetrieve.add(instanceId);
        return true;
    }

    public boolean addInstanceIdToDelete(long instanceId) {
        instanceIdsToDelete.add(instanceId);
        return true;
    }

    public boolean query(String returnBodiesToParse) throws Exception {
        LoggingBase.logMessage("ArchiveScenario.query(" + returnBodiesToParse + ")");

        Boolean returnBodies = COMParseHelper.parseBoolean(returnBodiesToParse);

        // reset the previous results
        queriedArchiveDetailsList = null;
        queriedObjectList = null;
        returnedError = null;
        invalidQueryIndexes = null;
        queriedDomains = new ArrayList<>();
        queriedObjectTypes = new ArrayList<>();

        archiveAdapter.resetLatch();
        LoggingBase.logMessage("Query:returnBodies=" + returnBodies + ")");
        LoggingBase.logMessage("Query:objectType=" + objectType + ")");
        LoggingBase.logMessage("Query:archiveQueryList=" + archiveQueryList + ")");
        LoggingBase.logMessage("Query:compositeFilterSetList=" + compositeFilterSetList + ")");

        try {
            LocalMALInstance.instance().archiveStub().query(returnBodies, objectType,
                    archiveQueryList, compositeFilterSetList,
                    archiveAdapter);

            archiveAdapter.waitResponse();
        } catch (MALInteractionException exc) {
            onQueryError(exc.getStandardError());
        }

        LoggingBase.logMessage("queriedArchiveDetailsList=" + queriedArchiveDetailsList + ")");
        LoggingBase.logMessage("queriedObjectList=" + queriedObjectList + ")");
        LoggingBase.logMessage("returnedError=" + returnedError + ")");
        LoggingBase.logMessage("invalidQueryIndexes=" + invalidQueryIndexes + ")");
        LoggingBase.logMessage("queriedObjectTypes=" + queriedObjectTypes + ")");
        LoggingBase.logMessage("queriedDomains=" + queriedDomains + ")");

        return true;
    }

    private void onQueryError(MOErrorException error) {
        returnedError = error;
        if (COMHelper.INVALID_ERROR_NUMBER.equals(returnedError.getErrorNumber())) {
            Object extraInfo = returnedError.getExtraInformation();
            if (extraInfo instanceof UIntegerList) {
                invalidQueryIndexes = (UIntegerList) extraInfo;
            }
        }
    }

    public boolean count() throws Exception {
        LoggingBase.logMessage("ArchiveScenario.count()");

        // reset the previous results
        countLongList = null;
        returnedError = null;
        invalidQueryIndexes = null;

        archiveAdapter.resetLatch();

        try {
            LocalMALInstance.instance().archiveStub().count(objectType,
                    archiveQueryList, compositeFilterSetList,
                    archiveAdapter);

            archiveAdapter.waitResponse();
        } catch (MALInteractionException exc) {
            onCountError(exc.getStandardError());
        }

        LoggingBase.logMessage("countArchiveDetailsList=" + countLongList + ")");
        LoggingBase.logMessage("returnedError=" + returnedError + ")");
        LoggingBase.logMessage("invalidQueryIndexes=" + invalidQueryIndexes + ")");

        return true;
    }

    private void onCountError(MOErrorException error) {
        returnedError = error;
        if (COMHelper.INVALID_ERROR_NUMBER.equals(returnedError.getErrorNumber())) {
            Object extraInfo = returnedError.getExtraInformation();
            if (extraInfo instanceof UIntegerList) {
                invalidQueryIndexes = (UIntegerList) extraInfo;
            }
        }
    }

    public boolean countAtIndexIs(int index, long expectedCount) {
        return countLongList.get(index) == expectedCount;
    }

    public boolean resetLongList() {
        longList.clear();
        return true;
    }

    public boolean addLong(long l) {
        longList.add(l);
        return true;
    }

    private void addAllInQueriedDetailsList(ArchiveDetailsList detailsList) {
        if (detailsList != null) {
            if (queriedArchiveDetailsList == null) {
                queriedArchiveDetailsList = new ArchiveDetailsList();
            }
            queriedArchiveDetailsList.addAll(detailsList);
        }
    }

    private void addAllInQueriedObjectList(ElementList objectList) {
        if (objectList != null) {
            if (queriedObjectList == null) {
                queriedObjectList = new ArrayList();
            }
            queriedObjectList.addAll(objectList);
        }
    }

    private void addQueriedDomain(IdentifierList domain) {
        if (!queriedDomains.contains(domain)) {
            queriedDomains.add(domain);
        }
    }

    private void addQueriedObjectType(ObjectType objectType) {
        if (objectType != null && !queriedObjectTypes.contains(objectType)) {
            queriedObjectTypes.add(objectType);
        }
    }

    public boolean queriedDomainListContains(String domainToParse) {
        IdentifierList domainToCheck = new IdentifierList();
        parseDomain(domainToParse, domainToCheck);
        return queriedDomains.contains(domainToCheck);
    }

    public boolean queriedObjectTypeListContainsAreaAndServiceAndVersionAndNumber(int area, int service,
            short version, int number) {
        ObjectType objectTypeToCheck = new ObjectType(new UShort(area), new UShort(
                service), new UOctet(version), new UShort(number));
        return queriedObjectTypes.contains(objectTypeToCheck);
    }

    public boolean queriedObjectTypeListSizeIs(int expectedSize) {
        return (queriedObjectTypes.size() == expectedSize);
    }

    private void waitForShortTime() {
        try {
            Thread.sleep((long) 500);
        } catch (Exception ex) {

        }
    }

    class TestArchiveAdapter extends ArchiveAdapter {

        private CountDownLatch countDownLatch;

        @Override
        public void retrieveResponseReceived(MALMessageHeader msgHeader,
                ArchiveDetailsList archiveDetailsList, ElementList objectList,
                Map qosProperties) {
            retrievedArchiveDetailsList = archiveDetailsList;
            retrievedObjectList = objectList;
            countDownLatch.countDown();
        }

        @Override
        public void retrieveResponseErrorReceived(MALMessageHeader msgHeader,
                MOErrorException error, Map qosProperties) {
            onRetrieveError(error);
            countDownLatch.countDown();
        }

        @Override
        public void queryResponseReceived(MALMessageHeader msgHeader,
                ObjectType objectType, IdentifierList domain, ArchiveDetailsList archiveDetailsList,
                ElementList objectList, Map qosProperties) {
            LoggingBase.logMessage("queryResponseReceived=" + objectType + ":" + archiveDetailsList + ")");

            // TODO: check object type and domain
            addQueriedObjectType(objectType);
            addQueriedDomain(domain);

            addAllInQueriedDetailsList(archiveDetailsList);
            addAllInQueriedObjectList(objectList);

            countDownLatch.countDown();
        }

        @Override
        public void queryUpdateReceived(MALMessageHeader msgHeader,
                ObjectType objectType, IdentifierList domain, ArchiveDetailsList archiveDetailsList,
                ElementList objectList, Map qosProperties) {
            LoggingBase.logMessage("queryUpdateReceived=" + objectType + ":" + archiveDetailsList + ")");

            // TODO: check object type and domain
            addQueriedObjectType(objectType);
            addQueriedDomain(domain);

            addAllInQueriedDetailsList(archiveDetailsList);
            addAllInQueriedObjectList(objectList);
        }

        @Override
        public void queryResponseErrorReceived(MALMessageHeader msgHeader,
                MOErrorException error, Map qosProperties) {
            onQueryError(error);
            countDownLatch.countDown();
        }

        @Override
        public void queryUpdateErrorReceived(MALMessageHeader msgHeader,
                MOErrorException error, Map qosProperties) {
            onQueryError(error);
            countDownLatch.countDown();
        }

        @Override
        public void countResponseErrorReceived(MALMessageHeader msgHeader,
                MOErrorException error, Map qosProperties) {
            onCountError(error);
            countDownLatch.countDown();
        }

        @Override
        public void countResponseReceived(MALMessageHeader msgHeader,
                LongList bodyElement0, Map qosProperties) {
            countLongList = bodyElement0;
            countDownLatch.countDown();
        }

        public void resetLatch() {
            countDownLatch = new CountDownLatch(1);
        }

        public void waitResponse() throws Exception {
            countDownLatch.await();
        }

    }

    class ArchiveEventListener extends EventAdapter {

        @Override
        public void monitorEventNotifyErrorReceived(MALMessageHeader msgHeader,
                MOErrorException error, Map qosProperties) {
            // TODO Auto-generated method stub
            super.monitorEventNotifyErrorReceived(msgHeader, error, qosProperties);
        }

        @Override
        public void monitorEventNotifyReceived(MALMessageHeader msgHeader,
                Identifier subscriptionId, UpdateHeader updateHeader,
                ObjectDetails monitorEventUpdate,
                Element monitorEventUpdate2, Map qosProperties) {

            LoggingBase.logMessage("monitorEventNotifyReceived::" + updateHeader);

            notifiedUpdateHeaderList.add(updateHeader);
            if (monitorEventUpdate != null) {
                notifiedObjectDetailsList.add(monitorEventUpdate);
            }
            if (monitorEventUpdate2 != null) {
                notifiedEventBodyList.add(monitorEventUpdate2);
                LoggingBase.logMessage("monitorEventNotifyReceived::Body:" + monitorEventUpdate2);
            } else {
                LoggingBase.logMessage("monitorEventNotifyReceived::Body:NULL");
            }
        }
    }
}
