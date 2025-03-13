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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.com.COMHelper;
import static org.ccsds.moims.mo.com.COMHelper.*;
import org.ccsds.moims.mo.com.archive.ArchiveServiceInfo;
import org.ccsds.moims.mo.com.archive.provider.ArchiveInheritanceSkeleton;
import org.ccsds.moims.mo.com.archive.provider.CountInteraction;
import org.ccsds.moims.mo.com.archive.provider.QueryInteraction;
import org.ccsds.moims.mo.com.archive.provider.RetrieveInteraction;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetails;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList;
import org.ccsds.moims.mo.com.archive.structures.ArchiveQuery;
import org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList;
import org.ccsds.moims.mo.com.archive.structures.CompositeFilter;
import org.ccsds.moims.mo.com.archive.structures.CompositeFilterSet;
import org.ccsds.moims.mo.com.archive.structures.ExpressionOperator;
import org.ccsds.moims.mo.com.archive.structures.QueryFilterList;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.com.test.provider.TestServiceProvider;
import org.ccsds.moims.mo.comprototype.COMPrototypeHelper;
import org.ccsds.moims.mo.comprototype.archivetest.ArchiveTestServiceInfo;
import org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObjectList;
import org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayload;
import org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayloadList;
import org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectCreation;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectCreationList;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectDeletion;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectDeletionList;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectUpdate;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectUpdateList;
import org.ccsds.moims.mo.comprototype1.COMPrototype1Helper;
import org.ccsds.moims.mo.comprototype1.test1.Test1ServiceInfo;
import org.ccsds.moims.mo.comprototype1.test2.Test2ServiceInfo;
import org.ccsds.moims.mo.comprototype2.COMPrototype2Helper;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementsRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.BlobList;
import org.ccsds.moims.mo.mal.structures.BooleanList;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.Enumeration;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.URIList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 * This class is the archive provider. It provides the archive functionality
 * specified in the COM specification. It acts as the interface front end using
 * the Archive class to store & retrieve events
 */
public class ArchiveHandlerImpl extends ArchiveInheritanceSkeleton {

    private final TestServiceProvider testService;
    private Archive archive;
    private long lastInstanceId;
    private final String CLS = "ArchiveHandlerImpl:";
    ArchiveEventPublisher archiveEventPublisher;
    // Event object numbers
    private final UShort OBJECT_STORED_OBJ_NO = new UShort(1);
    private final UShort OBJECT_UPDATED_OBJ_NO = new UShort(2);
    private final UShort OBJECT_DELETED_OBJ_NO = new UShort(3);

    // Object numbers 
    private final UShort COMPROTOTYPE_TEST_TEST_OBJECT_OBJ_NO = new UShort(1);
    private final UShort COMPROTOTYPE_TEST_TEST_OBJECT2_OBJ_NO = new UShort(2);
    private final UShort COMPROTOTYPE_TEST_TEST_OBJECT3_OBJ_NO = new UShort(3);
    private final UShort COMPROTOTYPE_TEST_TEST_OBJECT4_OBJ_NO = new UShort(4);
    private final UShort COMPROTOTYPE_TEST_TEST_OBJECT5_OBJ_NO = new UShort(5);
    private final UShort COMPROTOTYPE_TEST_TEST_OBJECT6_OBJ_NO = new UShort(6);
    private final UShort COMPROTOTYPE_TEST_TEST_OBJECT7_OBJ_NO = new UShort(7);

    private final UShort COMPROTOTYPE1_TEST1_TEST_OBJECTA_OBJ_NO = new UShort(1);
    private final UShort COMPROTOTYPE1_TEST2_TEST_OBJECTA_OBJ_NO = new UShort(2);

    private final UShort COMPROTOTYPE2_TEST1_TEST_OBJECTA_OBJ_NO = new UShort(1);

    private final UShort COMPROTOTYPE2_TEST2_TEST_OBJECTA_OBJ_NO = new UShort(1);
    private final UShort COMPROTOTYPE2_TEST2_TEST_OBJECTB_OBJ_NO = new UShort(2);
    private final UShort COMPROTOTYPE2_TEST2_TEST_OBJECTC_OBJ_NO = new UShort(3);

    private final UShort COMPROTOTYPE_TEST_EVENTTEST_OBJECT_CREATION_OBJ_NO = new UShort(3001);
    private final UShort COMPROTOTYPE_TEST_EVENTTEST_OBJECT_DELETION_OBJ_NO = new UShort(3002);
    private final UShort COMPROTOTYPE_TEST_EVENTTEST_OBJECT_UPDATE_OBJ_NO = new UShort(3003);
    // identifier wildcard used in vaious checks
    private final static Identifier IDENTIFIER_WILDCARD = new Identifier("*");

    // Enum used to classify filter
    private enum FilterType {
        NUMERIC, DOUBLE, STRING, BLOB, INVALID
    };

    /**
     * Constructor - performs class initialisation
     *
     * @param testService
     */
    public ArchiveHandlerImpl(TestServiceProvider testService) {
        this.testService = testService;
        init();
        archive = Archive.inst();
    }

    /**
     * Initialises the ObjectType factory used to instantiate element lists
     */
    private void init() {
        final UOctet VERSION1 = new UOctet((short) 1);
        final UOctet VERSION2 = new UOctet((short) 2);
        // initialise element factory

        MALElementsRegistry registry = MALContextFactory.getElementsRegistry();
        registry.loadFullArea(COMPrototypeHelper.COMPROTOTYPE_AREA);
        registry.loadFullArea(COMPrototype1Helper.COMPROTOTYPE1_AREA);
        registry.loadFullArea(COMPrototype2Helper.COMPROTOTYPE2_AREA);

        MALObjectTypeRegistry factory = MALObjectTypeRegistry.inst();

        factory.registerElements(
                new ObjectType(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER, ArchiveTestServiceInfo.ARCHIVETEST_SERVICE_NUMBER,
                        VERSION1, COMPROTOTYPE_TEST_TEST_OBJECT_OBJ_NO), new TestObjectPayload(), new TestObjectPayloadList());
        factory.registerElements(
                new ObjectType(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER, ArchiveTestServiceInfo.ARCHIVETEST_SERVICE_NUMBER,
                        VERSION1, COMPROTOTYPE_TEST_TEST_OBJECT3_OBJ_NO), null, new EnumeratedObjectList());
        factory.registerElements(
                new ObjectType(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER, ArchiveTestServiceInfo.ARCHIVETEST_SERVICE_NUMBER,
                        VERSION1, COMPROTOTYPE_TEST_TEST_OBJECT4_OBJ_NO), null, new BlobList());
        factory.registerElements(
                new ObjectType(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER, ArchiveTestServiceInfo.ARCHIVETEST_SERVICE_NUMBER,
                        VERSION1, COMPROTOTYPE_TEST_TEST_OBJECT6_OBJ_NO), new Identifier(), new IdentifierList());
        factory.registerElements(
                new ObjectType(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER, ArchiveTestServiceInfo.ARCHIVETEST_SERVICE_NUMBER,
                        VERSION1, COMPROTOTYPE_TEST_TEST_OBJECT7_OBJ_NO), new URI(), new URIList());

        factory.registerElements(
                new ObjectType(COMPrototype1Helper.COMPROTOTYPE1_AREA_NUMBER, Test1ServiceInfo.TEST1_SERVICE_NUMBER,
                        VERSION1, COMPROTOTYPE1_TEST1_TEST_OBJECTA_OBJ_NO), new TestObjectPayload(), new TestObjectPayloadList());
        factory.registerElements(
                new ObjectType(COMPrototype1Helper.COMPROTOTYPE1_AREA_NUMBER, Test2ServiceInfo.TEST2_SERVICE_NUMBER,
                        VERSION1, COMPROTOTYPE1_TEST2_TEST_OBJECTA_OBJ_NO), null, new LongList());

        factory.registerElements(
                new ObjectType(COMPrototype2Helper.COMPROTOTYPE2_AREA_NUMBER, Test1ServiceInfo.TEST1_SERVICE_NUMBER,
                        VERSION1, COMPROTOTYPE2_TEST2_TEST_OBJECTA_OBJ_NO),
                null, new IntegerList());

        factory.registerElements(
                new ObjectType(COMPrototype2Helper.COMPROTOTYPE2_AREA_NUMBER, Test2ServiceInfo.TEST2_SERVICE_NUMBER,
                        VERSION1, COMPROTOTYPE2_TEST2_TEST_OBJECTA_OBJ_NO),
                null, new BooleanList());
        factory.registerElements(
                new ObjectType(COMPrototype2Helper.COMPROTOTYPE2_AREA_NUMBER, Test2ServiceInfo.TEST2_SERVICE_NUMBER,
                        VERSION2, COMPROTOTYPE2_TEST2_TEST_OBJECTA_OBJ_NO),
                null, new StringList());
        factory.registerElements(
                new ObjectType(COMPrototype2Helper.COMPROTOTYPE2_AREA_NUMBER, Test2ServiceInfo.TEST2_SERVICE_NUMBER,
                        VERSION2, COMPROTOTYPE2_TEST2_TEST_OBJECTC_OBJ_NO),
                null, new LongList());

        factory.registerElements(
                new ObjectType(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER, EventTestServiceInfo.EVENTTEST_SERVICE_NUMBER,
                        VERSION1, COMPROTOTYPE_TEST_EVENTTEST_OBJECT_CREATION_OBJ_NO), new ObjectCreation(), new ObjectCreationList());
        factory.registerElements(
                new ObjectType(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER, EventTestServiceInfo.EVENTTEST_SERVICE_NUMBER,
                        VERSION1, COMPROTOTYPE_TEST_EVENTTEST_OBJECT_DELETION_OBJ_NO), new ObjectDeletion(), new ObjectDeletionList());
        factory.registerElements(
                new ObjectType(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER, EventTestServiceInfo.EVENTTEST_SERVICE_NUMBER,
                        VERSION1, COMPROTOTYPE_TEST_EVENTTEST_OBJECT_UPDATE_OBJ_NO), new ObjectUpdate(), new ObjectUpdateList());
    }

    /* Checks all specified instance identifier values exist in a archive details list*/
    /**
     * For a list instance identifiers checks they all exist in a list of
     * archive objects
     *
     * @param instIds
     * @param archiveObjects
     * @throws MALInteractionException if one or more identifiers do not exist
     */
    private void checkAllInstancesExist(LongList instIds, Archive.ArchiveObjectList archiveObjects) throws MALInteractionException {
        LoggingBase.logMessage(CLS + ":checkAllInstancesExist " + instIds);
        UIntegerList errorList = new UIntegerList();

        for (int instCnt = 0; instCnt < instIds.size(); instCnt++) {
            boolean matchFound = false;
            // 0 = wildcard so no check required
            if (instIds.get(instCnt) != 0) {
                for (int archCnt = 0; archCnt < archiveObjects.size(); archCnt++) {
                    if (instIds.get(instCnt).longValue()
                            == archiveObjects.get(archCnt).getArchiveDetails().getInstId().longValue()) {
                        matchFound = true;
                    }
                }
                if (!matchFound) {
                    errorList.add(new UInteger(instCnt));
                }
            }
        }
        if (!errorList.isEmpty()) {
            LoggingBase.logMessage(CLS + "checkAllInstancesExist:throw Ex " + errorList);
            throw new MALInteractionException(new MOErrorException(MALHelper.UNKNOWN_ERROR_NUMBER, errorList));
        }
        LoggingBase.logMessage(CLS + " checkAllInstancesExist RET:" + instIds);
    }

    /**
     * Object types can be defined with no body. This function checks if a
     * specified ObjectType corresponds to one which is defined in XML spec as
     * having no body. Note this function only supports ObjectTypes used in the
     * test
     *
     * @param objectType
     * @return result
     */
    private boolean objectTypeHasNoBody(ObjectType objectType) {
        boolean bRet = false;

        if ((objectType.getArea().equals(COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER)
                && objectType.getService().equals(ArchiveTestServiceInfo.ARCHIVETEST_SERVICE_NUMBER)
                && objectType.getVersion().equals(COMPrototypeHelper.COMPROTOTYPE_AREA_VERSION)
                && objectType.getNumber().equals(COMPROTOTYPE_TEST_TEST_OBJECT5_OBJ_NO))
                || (objectType.getArea().equals(COMHelper.COM_AREA_NUMBER)
                && objectType.getService().equals(ArchiveServiceInfo.ARCHIVE_SERVICE_NUMBER)
                && objectType.getVersion().equals(COMHelper.COM_AREA_VERSION))) {
            bRet = true;
        }
        return bRet;
    }

    /**
     * Checks if a domain matches a domain specified in a query. The check takes
     * into account wildcards in the query.
     *
     * @param domain the domain to check.
     * @param qDomain the domain specified in the query.
     * @return the result of the check
     */
    private boolean domainMatches(IdentifierList domain, IdentifierList qDomain) {
        boolean end = false;
        boolean match = true;
        for (int i = 0; i < qDomain.size() && end == false && i < domain.size(); i++) {
            if (qDomain.get(i).equals(IDENTIFIER_WILDCARD)) {
                // Wildcard found - super - we have match
                end = true;
                match = true;
            } else if (!qDomain.get(i).equals(domain.get(i))) {
                // Doesn't match end
                end = true;
                match = false;
            }
        }
        if (!end) {
            // reached end no none matches - check all entries checked
            if (qDomain.size() == domain.size()) {
                match = true;
            } else if (qDomain.size() == (domain.size() + 1) && qDomain.get(qDomain.size() - 1).equals(IDENTIFIER_WILDCARD)) {
                // Also allow the case where there is only 1 extra domain in the filter and it is a wildcard
                // for example MSG1.aocs and MSG1.aocs.*
                match = true;
            } else {
                match = false;
            }
        }
        return match;
    }

    /**
     * Checks if an objectId match the value specified in a query. The check
     * takes into account wildcards in the query.
     *
     * @param objectId the objectId to check
     * @param qObjectId the objectId specified in the query
     * @return the result of the check
     */
    private boolean objectIdMatches(ObjectId objectId, ObjectId qObjectId) {
        boolean bMatch = true;
        if (!Archive.objectTypeMatches(objectId.getType(), qObjectId.getType())) {
            bMatch = false;
        }
        if (!domainMatches(objectId.getKey().getDomain(), qObjectId.getKey().getDomain())) {
            bMatch = false;
        }
        if (!(qObjectId.getKey().getInstId() == 0
                || qObjectId.getKey().getInstId().equals(objectId.getKey().getInstId()))) {
            bMatch = false;
        }

        return bMatch;
    }

    /**
     * Performs filter check on a numeric
     *
     * @param numericVal the numeric value
     * @param numericFilterVal the filter value
     * @param operator the filter operator
     * @return the result of the check
     * @throws MALInteractionException
     */
    private boolean matchesFilter(Long numericVal, Long numericFilterVal,
            ExpressionOperator operator) throws MALInteractionException {
        boolean bMatch;
        LoggingBase.logMessage(CLS + ":matchesFilter:numeric:" + numericVal + ":"
                + ":" + operator.getOrdinal());
        if (numericFilterVal != null) {
            switch (operator.getOrdinal()) {
                case ExpressionOperator._EQUAL_INDEX:
                    bMatch = (numericVal.longValue() == numericFilterVal.longValue());
                    break;
                case ExpressionOperator._DIFFER_INDEX:
                    bMatch = (numericVal.longValue() != numericFilterVal.longValue());
                    break;
                case ExpressionOperator._GREATER_INDEX:
                    bMatch = (numericVal.longValue() > numericFilterVal.longValue());
                    break;
                case ExpressionOperator._GREATER_OR_EQUAL_INDEX:
                    bMatch = (numericVal.longValue() >= numericFilterVal.longValue());
                    break;
                case ExpressionOperator._LESS_INDEX:
                    bMatch = (numericVal.longValue() < numericFilterVal.longValue());
                    break;
                case ExpressionOperator._LESS_OR_EQUAL_INDEX:
                    bMatch = (numericVal.longValue() <= numericFilterVal.longValue());
                    break;
                default:
                    bMatch = false;
                    LoggingBase.logMessage(CLS + ":matchesFilter:Operator not suppported for numeric:"
                            + operator);
                    break;
            }
        } else {
            // NULL value not supported for numeric
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    null));
        }

        LoggingBase.logMessage(CLS + ":matchesFilter:numeric RET:" + bMatch);
        return bMatch;
    }

    /**
     * Performs filter check on a double
     *
     * @param numericVal the numeric value
     * @param numericFilterVal the filter value
     * @param operator the filter operator
     * @return the result of the check
     * @throws MALInteractionException
     */
    private boolean matchesFilter(Double numericVal, Double numericFilterVal,
            ExpressionOperator operator) throws MALInteractionException {
        boolean bMatch;
        LoggingBase.logMessage(CLS + ":matchesFilter:numeric:" + numericVal.longValue() + ":"
                + ":" + operator.getOrdinal());
        if (numericFilterVal != null) {
            switch (operator.getOrdinal()) {
                case ExpressionOperator._EQUAL_INDEX:
                    bMatch = (numericVal.longValue() == numericFilterVal.longValue());
                    break;
                case ExpressionOperator._DIFFER_INDEX:
                    bMatch = (numericVal.longValue() != numericFilterVal.longValue());
                    break;
                case ExpressionOperator._GREATER_INDEX:
                    bMatch = (numericVal.longValue() > numericFilterVal.longValue());
                    break;
                case ExpressionOperator._GREATER_OR_EQUAL_INDEX:
                    bMatch = (numericVal.longValue() >= numericFilterVal.longValue());
                    break;
                case ExpressionOperator._LESS_INDEX:
                    bMatch = (numericVal.longValue() < numericFilterVal.longValue());
                    break;
                case ExpressionOperator._LESS_OR_EQUAL_INDEX:
                    bMatch = (numericVal.longValue() <= numericFilterVal.longValue());
                    break;
                default:
                    bMatch = false;
                    LoggingBase.logMessage(CLS + ":matchesFilter:Operator not suppported for numeric:"
                            + operator);
                    break;
            }
        } else {
            // NULL value not supported for numeric
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    null));
        }

        LoggingBase.logMessage(CLS + ":matchesFilter:numeric RET:" + bMatch);
        return bMatch;
    }

    /**
     * Performs filter check on a string
     *
     * @param stringVal the numeric value
     * @param stringFilterVal the filter value
     * @param operator the filter operator
     * @return the result of the check
     * @throws MALInteractionException
     */
    private boolean matchesFilter(String stringVal, String stringFilterVal,
            ExpressionOperator operator) throws MALInteractionException {
        boolean bMatch;

        switch (operator.getOrdinal()) {
            case ExpressionOperator._EQUAL_INDEX:
                if (!(stringVal == null || stringFilterVal == null)) {
                    bMatch = stringVal.equals(stringFilterVal);
                } else if (stringVal == null && stringFilterVal == null) {
                    bMatch = true;
                } else {
                    bMatch = false;
                }
                break;
            case ExpressionOperator._DIFFER_INDEX:
                if (!(stringVal == null || stringFilterVal == null)) {
                    bMatch = !stringVal.equals(stringFilterVal);
                } else if (stringVal == null && stringFilterVal == null) {
                    bMatch = false;
                } else {
                    bMatch = true;
                }
                break;
            case ExpressionOperator._CONTAINS_INDEX:
                if (!(stringVal == null || stringFilterVal == null)) {
                    bMatch = stringVal.contains(stringFilterVal);
                } else if (stringFilterVal == null) {
                    throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                            null));
                } else {
                    bMatch = false;
                }
                break;
            case ExpressionOperator._ICONTAINS_INDEX:
                if (!(stringVal == null || stringFilterVal == null)) {
                    bMatch = stringVal.toUpperCase().contains(stringFilterVal.toUpperCase());
                } else if (stringFilterVal == null) {
                    throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                            null));
                } else {
                    bMatch = false;
                }
                break;
            case ExpressionOperator._GREATER_INDEX:
            case ExpressionOperator._GREATER_OR_EQUAL_INDEX:
            case ExpressionOperator._LESS_INDEX:
            case ExpressionOperator._LESS_OR_EQUAL_INDEX:
                if (stringFilterVal == null) {
                    throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                            null));
                } else {
                    bMatch = false;
                }
                break;
            default:
                bMatch = false;
                LoggingBase.logMessage(CLS + ":matchesFilter:Operator not suppported for string:"
                        + operator);
                break;

        }
        return bMatch;
    }

    /**
     * Performs filter check on a blob
     *
     * @param blobVal the numeric value
     * @param blobFilterVal the filter value
     * @param operator the filter operator
     * @return the result of the check
     * @throws MALInteractionException if operator invalid
     */
    private boolean matchesFilter(Blob blobVal, Blob blobFilterVal,
            ExpressionOperator operator) throws MALInteractionException {
        boolean bMatch;

        switch (operator.getOrdinal()) {
            case ExpressionOperator._EQUAL_INDEX:
                bMatch = blobVal.equals(blobFilterVal);
                break;
            case ExpressionOperator._DIFFER_INDEX:
                bMatch = !blobVal.equals(blobFilterVal);
                break;
            default:
                LoggingBase.logMessage(CLS + ":matchesFilter:Operator not suppported for blob:"
                        + operator);
                throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                        null));

        }
        return bMatch;
    }

    /**
     * Checks if a filter operator is contains or icontains and if so checks if
     * the specified value is a string
     *
     * @param compositeFilter check the contains rule - contains/icontains only
     * valid for string
     * @throws MALInteractionException if filter invalid
     */
    private void checkFilterContainsRule(CompositeFilter compositeFilter) throws MALInteractionException {
        if ((compositeFilter.getType() == ExpressionOperator.CONTAINS
                || compositeFilter.getType() == ExpressionOperator.ICONTAINS)
                && (!(compositeFilter.getFieldValue() instanceof Union)
                || ((Union) compositeFilter.getFieldValue()).getStringValue() == null)) {
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    null));
        }
    }

    /**
     * Apply a composite filter to an archive objects.
     *
     * @param objs Objects to be filtered
     * @param compositeFilter filter
     * @return result of filter
     * @throws MALInteractionException if filter violates filter rules
     */
    private boolean matchesFilter(Archive.ArchiveObject obj,
            CompositeFilter compositeFilter) throws MALInteractionException {
        LoggingBase.logMessage(CLS + ":matchesFilter:"
                + obj + ":" + compositeFilter);
        boolean bMatch = true;
        Long numericVal = null;
        Long numericFilterVal = null;
        Double doubleVal = null;
        Double doubleFilterVal = null;
        FilterType filterType = FilterType.INVALID;
        String stringVal = null;
        String stringFilterVal = null;
        Blob blobVal = null;
        Blob blobFilterVal = null;

        try {
            // First check the contains rule - contains/icontains only valid for string
            checkFilterContainsRule(compositeFilter);
            if (obj.getElement() != null) {
                if (!(obj.getElement() instanceof Composite)) {
                    // For atributes 
                    if (obj.getElement() instanceof Integer) {
                        numericVal = new Long(((Integer) obj.getElement()).longValue());
                        numericFilterVal = ((Union) compositeFilter.getFieldValue()).getIntegerValue().longValue();
                        filterType = FilterType.NUMERIC;
                        LoggingBase.logMessage(CLS + ":matchesFilter:integer:" + numericVal + ":" + numericFilterVal);

                    } else if (obj.getElement() instanceof Duration) {
                        doubleFilterVal = ((Union) compositeFilter.getFieldValue()).getDoubleValue();
                        doubleVal = ((Duration) obj.getElement()).getValue();
                        filterType = FilterType.NUMERIC;
                    } else if (obj.getElement() instanceof Long) {
                        numericVal = (Long) obj.getElement();
                        numericFilterVal = ((Union) compositeFilter.getFieldValue()).getLongValue();
                        filterType = FilterType.DOUBLE;
                    } else if (obj.getElement() instanceof String) {
                        stringVal = (String) obj.getElement();
                        stringFilterVal = ((Union) compositeFilter.getFieldValue()).getStringValue();
                        filterType = FilterType.STRING;
                    } else if (obj.getElement() instanceof Enumeration) {
                        // Standard states filter value must be UIntger but does not indicate error returned
                        if (compositeFilter.getFieldValue() instanceof UInteger) {
                            numericFilterVal = ((UInteger) compositeFilter.getFieldValue()).getValue();
                            numericVal = new Long(((Enumeration) obj.getElement()).getOrdinal());
                            filterType = FilterType.NUMERIC;
                        } else {
                            bMatch = false;
                        }
                    } else if (obj.getElement() instanceof Identifier) {
                        LoggingBase.logMessage(CLS + ":matchesFilter:Identifier():" + compositeFilter.getFieldValue().getClass());
                        stringVal = ((Identifier) obj.getElement()).toString();
                        // Support types identifer & String for filter
                        if (compositeFilter.getFieldValue() instanceof Union) {
                            stringFilterVal = ((Union) compositeFilter.getFieldValue()).getStringValue();
                        }
                        filterType = FilterType.STRING;
                    } else if (obj.getElement() instanceof URI) {
                        stringVal = ((URI) obj.getElement()).toString();
                        // Support types identifer & String for filter
                        if (compositeFilter.getFieldValue() instanceof Union) {
                            stringFilterVal = ((Union) compositeFilter.getFieldValue()).getStringValue();
                        } else {
                            stringFilterVal = ((URI) compositeFilter.getFieldValue()).toString();
                        }
                        filterType = FilterType.STRING;
                    } else if (obj.getElement() instanceof Blob) {
                        blobVal = ((Blob) obj.getElement());
                        blobFilterVal = ((Blob) compositeFilter.getFieldValue());
                        filterType = FilterType.BLOB;
                    } else {
                        LoggingBase.logMessage(CLS + ":matchesFilter:sort not supported for class "
                                + obj.getElement().getClass());
                    }

                } else if (obj.getElement() instanceof TestObjectPayload) {
                    LoggingBase.logMessage(CLS + ":matchesFilter:testObjectpayload");
                    if (compositeFilter.getFieldName().equals("enumeratedField")) {
                        numericVal = new Long(((TestObjectPayload) obj.getElement()).getEnumeratedField().getOrdinal());
                        numericFilterVal = ((UInteger) compositeFilter.getFieldValue()).getValue();
                        filterType = FilterType.NUMERIC;
                    } else if (compositeFilter.getFieldName().equals("integerField")) {
                        numericVal = new Long(((TestObjectPayload) obj.getElement()).getIntegerField().longValue());
                        numericFilterVal = ((Union) compositeFilter.getFieldValue()).getIntegerValue().longValue();
                        filterType = FilterType.NUMERIC;
                        LoggingBase.logMessage(CLS + ":matchesFilter:integer:" + numericVal + ":" + numericFilterVal);
                    } else if (compositeFilter.getFieldName().equals("stringField")) {
                        stringVal = ((TestObjectPayload) obj.getElement()).getStringField();
                        if (compositeFilter.getFieldValue() != null) {
                            stringFilterVal = ((Union) compositeFilter.getFieldValue()).getStringValue();
                        }
                        filterType = FilterType.STRING;
                    } else if (compositeFilter.getFieldName().equals("compositeField.integerField")) {
                        if (((TestObjectPayload) obj.getElement()).getCompositeField() != null) {
                            numericVal = new Long(((TestObjectPayload) obj.getElement()).getCompositeField().getIntegerField().longValue());
                            numericFilterVal = ((Union) compositeFilter.getFieldValue()).getIntegerValue().longValue();
                            filterType = FilterType.NUMERIC;
                        } else {
                            bMatch = false;
                        }
                    } else {
                        LoggingBase.logMessage(CLS + ":matchesFilter:field invalid for TestObjectPayload:"
                                + compositeFilter.getFieldName());
                    }
                }
            }
        } catch (ClassCastException ex) {
            LoggingBase.logMessage(CLS + ":matchesFilter:class cast exception - filter not expected type:"
                    + compositeFilter);
            if (obj.getElement() != null) {
                LoggingBase.logMessage(CLS + ":matchesFilter:object ele class " + obj.getElement().getClass());
            }
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    ex.toString()));
        }
        if (bMatch) {
            if (filterType == FilterType.NUMERIC) {
                bMatch = matchesFilter(numericVal, numericFilterVal,
                        compositeFilter.getType());
            } else if (filterType == FilterType.DOUBLE) {
                bMatch = matchesFilter(doubleVal, doubleFilterVal,
                        compositeFilter.getType());
            } else if (filterType == FilterType.STRING) {
                bMatch = matchesFilter(stringVal, stringFilterVal,
                        compositeFilter.getType());
            } else if (filterType == FilterType.BLOB) {
                bMatch = matchesFilter(blobVal, blobFilterVal,
                        compositeFilter.getType());
            } else {
                bMatch = false;
            }
        }
        LoggingBase.logMessage(CLS + ":matchesFilter:RET" + bMatch);
        return bMatch;
    }

    /**
     * Apply a composite filter to a list of objects.
     *
     * @param objs Objects to be filtered
     * @param compositeFilter filter
     * @return Filtered objects
     */
    private Archive.ArchiveObjectList applyCompositeFilter(Archive.ArchiveObjectList objs,
            CompositeFilter compositeFilter) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":applyCompositeFilter:"
                + objs.size() + ":" + compositeFilter);
        Archive.ArchiveObjectList retObjs = new Archive.ArchiveObjectList();
        Iterator<Archive.ArchiveObject> objsIt = objs.iterator();

        Archive.ArchiveObject nextObj;
        while (objsIt.hasNext()) {
            nextObj = objsIt.next();
            if (!matchesFilter(nextObj, compositeFilter)) {
                objsIt.remove();
            }

        }
        LoggingBase.logMessage(CLS + ":applyCompositeFilter:RET" + objs.size());
        return objs;
    }

    /**
     * Apply a composite set filter to a list of objects. Filter applied ANDing
     * the individual filters.
     *
     * @param objs
     * @param compositeFilterSet
     * @return Filtered objects
     */
    private Archive.ArchiveObjectList applyCompositeFilterSet(Archive.ArchiveObjectList objs,
            CompositeFilterSet compositeFilterSet) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":applyCompositeFilterSet:"
                + objs.size() + ":" + compositeFilterSet);
        Archive.ArchiveObjectList retObjs = new Archive.ArchiveObjectList();
        Iterator<CompositeFilter> filtersIt = compositeFilterSet.getFilters().iterator();
        CompositeFilter nextFilter;
        while (filtersIt.hasNext()) {
            nextFilter = filtersIt.next();
            if (nextFilter != null) {
                applyCompositeFilter(objs, nextFilter);
            }
        }
        LoggingBase.logMessage(CLS + ":applyCompositeFilterSet:RET" + objs.size());
        return objs;
    }

    /**
     * Applies query to a list of archive objects, returning a list of objects
     * which pass the query
     *
     * @param objs List of objects on which query to be performed
     * @param archiveQuery Query to be performed
     * @return list of objects which match the query
     */
    private Archive.ArchiveObjectList applyArchiveQuery(Archive.ArchiveObjectList objs,
            ArchiveQuery archiveQuery) {
        LoggingBase.logMessage(CLS + ":applyArchiveQuery:"
                + objs.size() + ":" + archiveQuery);
        long currentMatchingEndTime = 0;
        int currentMatchingObj = -1;

        Archive.ArchiveObjectList retObjs = new Archive.ArchiveObjectList();
        for (int i = 0; i < objs.size(); i++) {
            boolean match = true;
            Archive.ArchiveObject nextObj = objs.get(i);
            if (!(archiveQuery.getDomain() == null || domainMatches(nextObj.getDomain(), archiveQuery.getDomain()))) {
                match = false;
                LoggingBase.logMessage(CLS + ":applyArchiveQuery Domain No Match:"
                        + archiveQuery.getDomain() + ":" + nextObj);
            }
            if (!(archiveQuery.getNetwork() == null
                    || nextObj.getArchiveDetails().getNetwork().equals(archiveQuery.getNetwork()))) {
                match = false;
                LoggingBase.logMessage(CLS + ":applyArchiveQuery Network No Match:"
                        + archiveQuery.getDomain() + ":" + nextObj);
            }
            if (!(archiveQuery.getProvider() == null
                    || nextObj.getArchiveDetails().getProvider().equals(archiveQuery.getProvider()))) {
                match = false;
                LoggingBase.logMessage(CLS + ":applyArchiveQuery provider No Match:"
                        + archiveQuery.getProvider() + ":" + nextObj);
            }
            if (!(archiveQuery.getRelated() == 0
                    || nextObj.getArchiveDetails().getDetails().getRelated().equals(archiveQuery.getRelated()))) {
                match = false;
                LoggingBase.logMessage(CLS + ":applyArchiveQuery Related No Match:"
                        + archiveQuery.getProvider() + ":" + nextObj);
            }
            if (!(archiveQuery.getSource() == null
                    || objectIdMatches(nextObj.getArchiveDetails().getDetails().getSource(), archiveQuery.getSource()))) {
                match = false;
                LoggingBase.logMessage(CLS + ":applyArchiveQuery Source No Match:"
                        + archiveQuery.getSource() + ":" + nextObj);
            }
            if (!(archiveQuery.getStartTime() == null
                    || nextObj.getArchiveDetails().getTimestamp().getValue() >= archiveQuery.getStartTime().getValue())) {
                match = false;
                LoggingBase.logMessage(CLS + ":applyArchiveQuery Start Time No Match:"
                        + archiveQuery.getStartTime() + ":" + nextObj);
            }
            if (!(archiveQuery.getEndTime() == null
                    || nextObj.getArchiveDetails().getTimestamp().getValue() <= archiveQuery.getEndTime().getValue())) {
                match = false;
                LoggingBase.logMessage(CLS + ":applyArchiveQuery End Time No Match:"
                        + archiveQuery.getStartTime() + ":" + nextObj);
            }
            // A special rule applies for the case where quert contains an and time but no start time
            // in this case - it shall match the the single object closest to but not greater than the end time field
            if (archiveQuery.getStartTime() == null && archiveQuery.getEndTime() != null && match) {
                if (nextObj.getArchiveDetails().getTimestamp().getValue() > currentMatchingEndTime) {
                    currentMatchingEndTime = nextObj.getArchiveDetails().getTimestamp().getValue();
                    currentMatchingObj = i;
                    LoggingBase.logMessage(CLS + ":applyArchiveQuery:Special rule match " + i);
                }
            } else if (match) {
                retObjs.add(nextObj);
            }
        }
        // A special rule applies for the case where query contains an end time but no start time
        // in this case - it shall match the the single object closest to but not greater than the end time field
        if (archiveQuery.getStartTime() == null && archiveQuery.getEndTime() != null && currentMatchingObj != -1) {
            retObjs.add(objs.get(currentMatchingObj));
            LoggingBase.logMessage(CLS + ":applyArchiveQuery:Special rule add obj " + currentMatchingObj);
        }

        LoggingBase.logMessage(CLS + ":applyArchiveQuery:RET" + retObjs.size());
        return retObjs;
    }

    /**
     * Apply a sort to a list of objects
     *
     * @param objs Objects to be sorted
     * @param archiveQuery Query holding sort criteria
     * @throws MALInteractionException to report sort failures
     */
    private void sortObjects(
            Archive.ArchiveObjectList objs, ArchiveQuery archiveQuery)
            throws MALInteractionException {
        LoggingBase.logMessage(CLS + ":sortObjects:" + objs);

        if (archiveQuery.getSortOrder() != null) {
            ArchiveObjectCompare sorter = new ArchiveObjectCompare(archiveQuery.getSortOrder(),
                    archiveQuery.getSortFieldName());
            // Remove objects for which the sort field is NULL they should not be sorted
            Archive.ArchiveObjectList nullObjs = new Archive.ArchiveObjectList();
            Archive.ArchiveObjectList nonNullObjs = new Archive.ArchiveObjectList();
            sorter.getNullObjs(objs, nullObjs, nonNullObjs);

            Collections.sort(nonNullObjs, sorter);
            if (sorter.sortFailed()) {
                LoggingBase.logMessage(CLS + ":sortObjects:Sort failed");
                throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                        null));
            } else {
                // Combine sorted & non-sorted objects
                objs.clear();
                objs.addAll(nonNullObjs);
                objs.addAll(nullObjs);
            }
        }

        LoggingBase.logMessage(CLS + ":sortObjects:RET" + objs);
    }

    /**
     * Apply a sort to a number of object lists, where each list holds entries
     * for an Object Type/domain pair. This means all entries will have the same
     * type
     *
     * @param objLists Lists to be sorted
     * @param archiveQuery Query holding sort criteria
     * @throws MALInteractionException
     */
    private void sortObjects(
            ArrayList<Archive.ArchiveObjectList> objLists, ArchiveQuery archiveQuery)
            throws MALInteractionException {
        Archive.ArchiveObjectList nextList;
        if (archiveQuery != null) {
            // Loop through each object list - containing objects of the same type
            Iterator<Archive.ArchiveObjectList> objListsIt = objLists.iterator();
            while (objListsIt.hasNext()) {
                nextList = objListsIt.next();
                if (!nextList.isEmpty()) {
                    sortObjects(nextList, archiveQuery);
                }
            }
        }
    }

    /**
     * Creates a ArchiveObjectList for each domain/ObectType combination in an
     * an input list
     *
     * @param objs objects to be organised into lists
     * @return a list of ArchiveObjectLists
     */
    private ArrayList<Archive.ArchiveObjectList> createDomainObjectTypeLists(
            Archive.ArchiveObjectList objs) {
        LoggingBase.logMessage(CLS + ":createDomainObjectTypeLists: " + objs.size());
        ArrayList<Archive.ArchiveObjectList> lists = new ArrayList<>();
        ObjectType objectType;
        IdentifierList domain;
        Archive.ArchiveObjectList domainObjectTypeList;
        Archive.ArchiveObjectList nextList;
        Archive.ArchiveObject nextObj;
        Iterator<Archive.ArchiveObject> objIt = objs.iterator();
        Iterator<Archive.ArchiveObjectList> listsIt;
        // Iterate through all archived objects
        while (objIt.hasNext()) {
            // Check if a list has aready been created for the domain/object type pair
            nextObj = objIt.next();
            objectType = nextObj.getObjectType();
            domain = nextObj.getDomain();
            listsIt = lists.iterator();
            domainObjectTypeList = null;
            while (listsIt.hasNext() && domainObjectTypeList == null) {
                nextList = listsIt.next();
                if (domain.equals(nextList.get(0).getDomain())
                        && objectType.equals(nextList.get(0).getObjectType())) {
                    domainObjectTypeList = nextList;
                }
            }
            if (domainObjectTypeList == null) {
                domainObjectTypeList = new Archive.ArchiveObjectList();
                lists.add(domainObjectTypeList);
                LoggingBase.logMessage(CLS + ":createDomainObjectTypeLists:Create list for:"
                        + nextObj.getDomain() + ":" + nextObj.getObjectType());
            }
            domainObjectTypeList.add(nextObj);
        }

        LoggingBase.logMessage(CLS + ":createDomainObjectTypeLists:RET " + lists.size());
        return lists;
    }

    /**
     * Generates and sends the query results using update and response methods
     * on the interaction
     *
     * @param bodyRequired - specifies whether the object body should be
     * required
     * @param objLists - number of object lists holding the results
     * @param returnObjectType - object type of the returned lists
     * @param interaction - interaction
     * @throws MALInteractionException
     * @throws MALException
     */
    private void returnQueryResults(boolean bodyRequired, ArrayList<Archive.ArchiveObjectList> objLists,
            boolean returnObjectType, QueryInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":returnQueryResults:" + objLists.size());
        ArchiveDetailsList archiveDetailsList;
        ElementList elementList = null;
        HeterogeneousList objs = null;

        if (objLists.isEmpty()) {
            // No Matches
            interaction.sendResponse(null, null, null, null);
        } else {
            /* Create and populate list for each domain/object type pair then iterate through them  
       * sending an update or response for last message.
             */
            Iterator<Archive.ArchiveObjectList> listsIt = objLists.iterator();
            while (listsIt.hasNext()) {
                Archive.ArchiveObjectList nextList = listsIt.next();
                ObjectType objectType = nextList.get(0).getObjectType();
                if (!objectTypeHasNoBody(objectType) && bodyRequired) {
                    elementList = createElementList(objectType);
                }
                archiveDetailsList = new ArchiveDetailsList();

                IdentifierList domain = nextList.get(0).getDomain();

                // Loop through each object and populate archive details & element
                Iterator<Archive.ArchiveObject> objsIt = nextList.iterator();

                while (objsIt.hasNext()) {
                    Archive.ArchiveObject nextObj = objsIt.next();
                    archiveDetailsList.add(nextObj.getArchiveDetails());

                    if (elementList != null) {
                        elementList.add(nextObj.getElement());
                    }
                }

                if (!returnObjectType) {
                    objectType = null;
                }

                if (listsIt.hasNext()) {
                    LoggingBase.logMessage(CLS + ":returnQueryResults:send update: " + archiveDetailsList.size() + ":" + archiveDetailsList);
                    if (elementList != null) {
                        LoggingBase.logMessage(CLS + ":returnQueryResults:send update elementList" + elementList.size() + ":" + elementList);
                    }

                    if (elementList != null) {
                        objs = new HeterogeneousList();
                        objs.addAll(elementList);
                    }

                    interaction.sendUpdate(objectType, domain, archiveDetailsList, objs);
                } else {
                    LoggingBase.logMessage(CLS + ":returnQueryResults:send response: " + archiveDetailsList.size() + ":" + archiveDetailsList);
                    if (elementList != null) {
                        LoggingBase.logMessage(CLS + ":returnQueryResults:send update elementList" + elementList.size() + ":" + elementList);
                    }

                    if (elementList != null) {
                        objs = new HeterogeneousList();
                        objs.addAll(elementList);
                    }

                    interaction.sendResponse(objectType, domain, archiveDetailsList, objs);
                }
                waitForShortTime();
            }
        }
        LoggingBase.logMessage(CLS + ":returnQueryResults:RET");
    }

    /**
     *
     * Create an element list for the specified Object Type
     *
     * @param objectType
     * @return element list
     */
    private ElementList createElementList(ObjectType objectType) throws MALInteractionException, MALException {
        ElementList element = MALObjectTypeRegistry.inst().lookupElementlist(objectType);

        try {
            Long typeId = element.getTypeId().getTypeId();
            MALElementsRegistry registry = MALContextFactory.getElementsRegistry();
            return (ElementList) registry.createElement(typeId);
        } catch (Exception ex1) {
            Logger.getLogger(ArchiveHandlerImpl.class.getName()).log(Level.SEVERE,
                    CLS + ":returnQueryResults:Raise ERR - Element List Creation " + objectType, ex1);
        }

        return null;
    }

    /**
     * Retrieves a set of objects
     *
     * @param objectType ObjectType to be retrieved - no wild cards
     * @param domain Domain to be retrieved - no wild cards
     * @param instIds List of instance identifiers to be retrieved
     * @param interaction Interaction used to return errors
     * @throws MALInteractionException to report failures
     */
    @Override
    public void retrieve(ObjectType objectType, IdentifierList domain, LongList instIds,
            RetrieveInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":retrieve Domain:" + domain);
        ArchiveDetailsList archiveDetailsList = new ArchiveDetailsList();
        ElementList elementList = null;
        boolean retrieveAll = false;
        if (!objectTypeHasNoBody(objectType)) {
            elementList = createElementList(objectType);
        }

        /* Check clause If any of the fields of the object type contains the wildcard value 
     of '0' then an INVALID error shall be returned. */
        if (containsWildcard(objectType)) {
            LoggingBase.logMessage(CLS + ":retrieve:Raise ERR - objectType");
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    null));
        }
        // Check clause If the domain contains the wildcard value of '*' then an INVALID error shall be returned..
        if (containsWildcard(domain)) {
            LoggingBase.logMessage(CLS + ":retrieve:Raise ERR - domain");
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    null));
        }
        if (instIds.contains(new Long(0))) {
            LoggingBase.logMessage(CLS + ":retrieve:retrieve All");
            retrieveAll = true;
        }

        interaction.sendAcknowledgement();
        this.waitForShortTime();   // Allow time for message to be sent

        // Perfrom retrieve
        Archive.ArchiveObjectList rtrObjs = archive.retrieve(objectType, domain, instIds, retrieveAll);
        checkAllInstancesExist(instIds, rtrObjs);
        // Populate return list
        for (int i = 0; i < rtrObjs.size(); i++) {
            archiveDetailsList.add(rtrObjs.get(i).getArchiveDetails());
            if (elementList != null) {
                elementList.add(rtrObjs.get(i).getElement());
            }
        }
        // If no entries returned set returned lists to NULL
        if (archiveDetailsList.isEmpty()) {
            archiveDetailsList = null;
            elementList = null;
        }

        HeterogeneousList objs = null;

        if (elementList != null) {
            objs = new HeterogeneousList();
            objs.addAll(elementList);
        }

        interaction.sendResponse(archiveDetailsList, objs);
        LoggingBase.logMessage(CLS + ":retrieve RET");
    }

    /**
     * Performs a query on the archive the results are returned via the
     * interaction. Multiple results can be returned using the Update call. A
     * result shall be generated for each ObjectType found.
     *
     * @param bodyRequired indicates if the bodies of objects are required
     * @param objectType type of object required, wildcards allowed
     * @param archiveQueryList archive queries/filter to be applied
     * @param queryFilterList general purpose filters to be applied (can be
     * NULL)
     * @param interaction Interaction used to return results
     * @throws MALInteractionException used to report invalid query
     */
    @Override
    public void query(Boolean bodyRequired, ObjectType objectType, ArchiveQueryList archiveQueryList,
            QueryFilterList queryFilterList, QueryInteraction interaction)
            throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":query:" + objectType + ":" + archiveQueryList);
        if (queryFilterList != null) {
            LoggingBase.logMessage(CLS + ":query:Filter List" + queryFilterList);
        }
        UIntegerList failedQueries = new UIntegerList();
        ArrayList<Archive.ArchiveObjectList> returnedLists = new ArrayList<>();

        interaction.sendAcknowledgement();
        // Wait for short time to ensure ACK has been sent
        waitForShortTime();

        Archive.ArchiveObjectList retrievedObjs = archive.retrieve(objectType, null, null, true);
        // If queries specified they must be same size
        if ((queryFilterList != null && archiveQueryList != null)
                && (queryFilterList.size() != archiveQueryList.size())) {
            LoggingBase.logMessage(CLS + ":query:Filter List error throw exception");
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    null));
        }
        // Apply each archive query & filter
        for (int i = 0; archiveQueryList != null && i < archiveQueryList.size(); i++) {
            try {
                // Perform archive query
                Archive.ArchiveObjectList objs = applyArchiveQuery(retrievedObjs, archiveQueryList.get(i));
                // Apply filter (if specified)
                if (queryFilterList != null && queryFilterList.get(i) != null) {
                    // CompositeFilterSet is the only filter supported
                    objs = applyCompositeFilterSet(objs, (CompositeFilterSet) queryFilterList.get(i));
                }

                // Group objects according to domain/object type then sort
                ArrayList<Archive.ArchiveObjectList> domainObjectTypeList = createDomainObjectTypeLists(objs);
                sortObjects(domainObjectTypeList, archiveQueryList.get(i));

                returnedLists.addAll(domainObjectTypeList);
            } catch (MALInteractionException ex) {
                LoggingBase.logMessage(CLS + ":query:exception caught:" + i + ":" + ex);
                failedQueries.add(new UInteger(i));
            }
        }
        // If any failures occured generate exception with failure list
        if (!failedQueries.isEmpty()) {
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    failedQueries));
        }
        // In the returned values, objectType is not required if the query objectType contains no Wildcards
        boolean returnObjectType = containsWildcard(objectType);
        LoggingBase.logMessage(CLS + ":returnQueryResults:RET" + ":" + bodyRequired + ":" + returnedLists
                + ":" + returnObjectType + ":" + interaction);
        if (bodyRequired == null) {
            bodyRequired = new Boolean(false);
        }
        returnQueryResults(bodyRequired, returnedLists, returnObjectType, interaction);
        waitForShortTime();
        LoggingBase.logMessage(CLS + ":query:RET");
    }

    /**
     * Count the set of objects based upon a list of queries
     *
     * @param objectType ObjectType of objects to be counted can contain
     * wildcards
     * @param archiveQueryList list of archive queries
     * @param queryFilterList list of filters
     * @param interaction
     * @throws MALInteractionException
     * @throws MALException
     */
    @Override
    public void count(ObjectType objectType, ArchiveQueryList archiveQueryList, QueryFilterList queryFilterList,
            CountInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":count:" + objectType + ":" + archiveQueryList);
        if (queryFilterList != null) {
            LoggingBase.logMessage(CLS + ":count:Filter List" + queryFilterList);
        }
        UIntegerList failedQueries = new UIntegerList();
        LongList countResults = new LongList();
        ArrayList<Archive.ArchiveObjectList> returnedLists = new ArrayList<>();

        interaction.sendAcknowledgement();
        // Wait for short time to ensure ACK has been sent
        waitForShortTime();
        // If queries specified they must be same size
        if ((queryFilterList != null && archiveQueryList != null)
                && (queryFilterList.size() != archiveQueryList.size())) {
            LoggingBase.logMessage(CLS + ":query:Filter List error throw exception");
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    null));
        }
        // Retieve objects on which we will perform a query
        Archive.ArchiveObjectList retrievedObjs = archive.retrieve(objectType, null, null, true);
        // Apply each archive query & filter
        for (int i = 0; archiveQueryList != null && i < archiveQueryList.size(); i++) {
            try {
                // Perform archive query
                Archive.ArchiveObjectList objs = applyArchiveQuery(retrievedObjs, archiveQueryList.get(i));
                // Apply filter (if specified)
                if (queryFilterList != null && queryFilterList.get(i) != null) {
                    // CompositeFilterSet is the only filter supported
                    objs = applyCompositeFilterSet(objs, (CompositeFilterSet) queryFilterList.get(i));
                }
                // Group objects according to domain/object type then sort 
                // This is necessary as spec states sort parameters must be checked 
                ArrayList<Archive.ArchiveObjectList> domainObjectTypeList = createDomainObjectTypeLists(objs);
                sortObjects(domainObjectTypeList, archiveQueryList.get(i));
                // Now update count
                countResults.add(new Long(objs.size()));

            } catch (MALInteractionException ex) {
                LoggingBase.logMessage(CLS + ":query:exception caught:" + i + ":" + ex);
                failedQueries.add(new UInteger(i));
            }
        }
        // If any failures occured generate exception with failure list
        if (!failedQueries.isEmpty()) {
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    failedQueries));
        }

        interaction.sendResponse(countResults);
    }

    /**
     * Helper function just performs a short wait
     */
    private void waitForShortTime() {
        try {
            Thread.sleep((long) Configuration.COM_PERIOD);
        } catch (Exception ex) {
            LoggingBase.logMessage(CLS + ":query:Sleep failure " + ex);
        }
    }

    /**
     * Checks if an identifierList contains a wildcard
     *
     * @param identifierList
     * @return result of check
     */
    private boolean containsWildcard(IdentifierList identifierList) {
        for (int i = 0; i < identifierList.size(); i++) {
            if (identifierList.get(i).equals(IDENTIFIER_WILDCARD)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Extracts the instance identifiers from the archive details list
     *
     * @param archiveDetailsList
     * @return list of instance identifiers
     */
    private LongList getInstids(ArchiveDetailsList archiveDetailsList) {
        LongList instIds = new LongList();
        for (int i = 0; i < archiveDetailsList.size(); i++) {
            instIds.add(archiveDetailsList.get(i).getInstId());
        }
        return instIds;
    }

    /**
     * Checks if any of the instance identifiers in the archive details list are
     * duplicates
     *
     * @param archiveDetailsList
     * @throws MALInteractionException if duplicates found
     */
    private void checkInstIdDuplicates(ArchiveDetailsList archiveDetailsList) throws MALInteractionException {
        UIntegerList errorList = new UIntegerList();
        for (int i = 0; i < archiveDetailsList.size(); i++) {
            long instId = archiveDetailsList.get(i).getInstId();
            LoggingBase.logMessage("CHK DUPL " + i + " " + instId);
            if (instId != 0) {
                for (int j = 0; j < i; j++) {
                    LoggingBase.logMessage("CHK DUPL " + i + " " + instId + " " + j + " " + archiveDetailsList.get(j).getInstId());
                    if (archiveDetailsList.get(j).getInstId() == instId) {
                        LoggingBase.logMessage(CLS + ":checkInstIdDuplicates:Raise ERR - ");
                        errorList.add(new UInteger(i));
                        throw new MALInteractionException(new MOErrorException(DUPLICATE_ERROR_NUMBER, errorList));
                    }

                }
            }
            LoggingBase.logMessage("CHK DUPL END" + i);
        }
    }

    /**
     * Checks if an objectType instance contains any wildcards
     *
     * @param objectType
     * @return result of check
     */
    private boolean containsWildcard(ObjectType objectType) {
        return (objectType.getArea().getValue() == 0 || objectType.getService().getValue() == 0
                || objectType.getVersion().getValue() == 0 || objectType.getNumber().getValue() == 0);
    }

    /**
     * Checks validity of set of parameters supplied as part of a store
     * operation
     *
     * @param setInstId
     * @param objectType
     * @param domain
     * @param archiveDetailsList
     * @param elementList
     * @throws MALInteractionException if an invalid value has been provided
     */
    private void checkStoreValidity(Boolean setInstId, ObjectType objectType, IdentifierList domain,
            ArchiveDetailsList archiveDetailsList, ElementList elementList) throws MALInteractionException {
        UIntegerList errorList = new UIntegerList();
        // Check clause (h) - The fourth and fifth list must be the same size 
        if (archiveDetailsList == null || (elementList != null && archiveDetailsList.size() != elementList.size())) {
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    new UInteger(archiveDetailsList.size())));
        }
        // Check clause (i) An INVALID error shall be returned if a wildcard value of '0' is detected in the object type.
        if (objectType.getArea().getValue() == 0 || objectType.getService().getValue() == 0
                || objectType.getVersion().getValue() == 0 || objectType.getNumber().getValue() == 0) {
            if (containsWildcard(objectType)) {
                throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                        null));
            }
        }
        // Check clause (j) An INVALID error shall be returned if a wildcard value of '*' is detcted in the domain identifier list.
        if (containsWildcard(domain)) {
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    null));
        }

        // Check clause (j) instId aready appears in archive details list
        checkInstIdDuplicates(archiveDetailsList);

        // Now check enties in archiveDetailsList & elementList
        for (int i = 0; i < archiveDetailsList.size(); i++) {
            ArchiveDetails archiveDetails = archiveDetailsList.get(i);
            // Element element  =  (Element) elementList.get(i);
            /* Check clause (l) - No wildcard values of '0', '*', or NULL shall be accepted in the network, 
       timestamp or provider fields of the archive details except for the object instance identifier 
       * or an INVALID error is returned. */
            if (archiveDetails.getNetwork() == null || archiveDetails.getNetwork().equals(IDENTIFIER_WILDCARD)) {
                errorList.add(new UInteger(i));
                LoggingBase.logMessage(CLS + ":checkStoreValidity:Raise ERR - Network");
                throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER, errorList));
            }

            if (archiveDetails.getTimestamp() == null || archiveDetails.getTimestamp().getValue() == 0) {
                errorList.add(new UInteger(i));
                LoggingBase.logMessage(CLS + ":checkStoreValidity:Raise ERR - Timestamp");
                throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER, errorList));
            }

            if (archiveDetails.getProvider() == null) {
                LoggingBase.logMessage(CLS + ":checkStoreValidity:Raise ERR - Provider");
                errorList.add(new UInteger(i));
                throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER, errorList));
            }

            // Check clause (f) instId aready used
            long instId = archiveDetails.getInstId();
            if (!setInstId && archive.contains(objectType, domain, instId)) {
                LoggingBase.logMessage(CLS + ":checkStoreValidity:Raise ERR - InstId");
                errorList.add(new UInteger(i));
                throw new MALInteractionException(new MOErrorException(DUPLICATE_ERROR_NUMBER, errorList));
            }

        }

    }

    /**
     * Store operation - stores a number of objects
     *
     * @param setInstId - specifies whether instance identifier should be
     * allocated within operation
     * @param objectType - Object Type of elements to be stored
     * @param domain - Domain of elements to be stored
     * @param archiveDetailsList - archive details for each element
     * @param elementList - body of each element
     * @param interaction - interaction to be used to return results
     * @return instance identifiers for objects stored
     * @throws MALInteractionException
     * @throws MALException
     */
    @Override
    public LongList store(Boolean setInstId, ObjectType objectType, IdentifierList domain,
            ArchiveDetailsList archiveDetailsList, HeterogeneousList elementList,
            MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":Store " + " ObjectType" + ":" + objectType + ":archiveDetails:" + archiveDetailsList);
        LongList instIds = new LongList();
        LongList retVal = null;

        try {
            checkStoreValidity(setInstId, objectType, domain, archiveDetailsList, elementList);
            ArchiveDetailsList newArcDetails = new ArchiveDetailsList();

            for (int i = 0; i < archiveDetailsList.size(); i++) {
                if (archiveDetailsList.get(i).getInstId() == 0) {
                    ArchiveDetails newDetails = new ArchiveDetails((++lastInstanceId),
                            archiveDetailsList.get(i).getDetails(),
                            archiveDetailsList.get(i).getNetwork(),
                            archiveDetailsList.get(i).getTimestamp(),
                            archiveDetailsList.get(i).getProvider());

                    newArcDetails.add(newDetails);
                    instIds.add(new Long(lastInstanceId));
                } else {
                    instIds.add(archiveDetailsList.get(i).getInstId());
                    if (archiveDetailsList.get(i).getInstId() > lastInstanceId) {
                        lastInstanceId = archiveDetailsList.get(i).getInstId();
                    }
                    newArcDetails.add(archiveDetailsList.get(i));
                }
            }

            for (int i = 0; i < newArcDetails.size(); i++) {
                Object element = (elementList != null) ? elementList.get(i) : null;
                archive.add(objectType, domain, newArcDetails.get(i), element);
            }

        } catch (MALInteractionException malEx) {
            throw malEx;
        } catch (Exception ex) {
            LoggingBase.logMessage(CLS + ":store ERROR " + ex);
            ex.printStackTrace();
            instIds = new LongList();
        }
        if (setInstId) {
            retVal = instIds;
            LoggingBase.logMessage(CLS + ":store instIds " + instIds);
        } else {
            LoggingBase.logMessage(CLS + ":store RET NULL");
        }
        LoggingBase.logMessage(CLS + ":store RET");
        archiveEventPublisher.publishEvents(OBJECT_STORED_OBJ_NO, objectType, domain, instIds);
        waitForShortTime();
        return retVal;
    }

    /**
     * Implements the archive update operation as documented in the COM spec
     *
     * @param objectType objectType to match for deletion
     * @param domain domain to match for deletion
     * @param archiveDetailsList archiveDetails for each object to be updated
     * @param elementList elementList for each object to be updated
     * @param interaction associated interaction
     * @throws MALInteractionException
     * @throws MALException
     */
    @Override
    public void update(ObjectType objectType, IdentifierList domain,
            ArchiveDetailsList archiveDetailsList, HeterogeneousList elementList, MALInteraction interaction)
            throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":update ObjectType:" + objectType + ":Domain:"
                + domain + ":archiveDetailsList:" + archiveDetailsList + ":elementList:" + elementList);
        LongList instIds = getInstids(archiveDetailsList);

        // Check clause If any of the fields of the object type contains the wildcard value of '0' then an INVALID error shall be returned.
        if (containsWildcard(objectType)) {
            LoggingBase.logMessage(CLS + ":update:Raise ERR - objectType");
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    null));
        }
        // Check clause If the domain contains the wildcard value of '*' then an INVALID error shall be returned..
        if (containsWildcard(domain)) {
            LoggingBase.logMessage(CLS + ":update:Raise ERR - domain");
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    null));
        }
        if (instIds.contains(new Long(0))) {
            LoggingBase.logMessage(CLS + ":update:instance has wildcard");
            UIntegerList errorList = new UIntegerList();
            for (int i = 0; i < instIds.size(); i++) {
                if (instIds.get(i) == 0) {
                    errorList.add(new UInteger(i));
                }
            }
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    errorList));
        }
        // Perfrom retrieve to check specified objects exist
        Archive.ArchiveObjectList rtrObjs
                = archive.retrieve(objectType, domain, instIds, false);
        checkAllInstancesExist(instIds, rtrObjs);
        // If everything OK do update
        archive.update(objectType, domain, archiveDetailsList, elementList);
        archiveEventPublisher.publishEvents(OBJECT_UPDATED_OBJ_NO, objectType, domain, instIds);
        LoggingBase.logMessage(CLS + ":update ObjectType:RET");
    }

    /**
     * Implements the archive delete operation as documented in the COM spec
     *
     * @param objectType objectType to match for deletion
     * @param domain domain to match for deletion
     * @param instIds instance identifiers to be matched for deletion (can
     * wildcard)
     * @param interaction
     * @return list containing instance identifers deleted
     * @throws MALInteractionException
     * @throws MALException
     */
    @Override
    public LongList delete(ObjectType objectType, IdentifierList domain, LongList instIds,
            MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage(CLS + ":delete ObjectType:" + objectType + ":Domain:"
                + domain + ":instIdList:" + instIds);
        LongList deletedInstIds;
        boolean deleteAll = false;

        // Check clause If any of the fields of the object type contains the wildcard value of '0' then an INVALID error shall be returned.
        if (containsWildcard(objectType)) {
            LoggingBase.logMessage(CLS + ":delete:Raise ERR - objectType");
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    null));
        }
        // Check clause If the domain contains the wildcard value of '*' then an INVALID error shall be returned..
        if (containsWildcard(domain)) {
            LoggingBase.logMessage(CLS + ":delete:Raise ERR - domain");
            throw new MALInteractionException(new MOErrorException(INVALID_ERROR_NUMBER,
                    null));
        }
        if (instIds.contains(new Long(0))) {
            LoggingBase.logMessage(CLS + ":delete:delete All");
            deleteAll = true;
        }
        // Perfrom retrieve to check specified objects exist
        Archive.ArchiveObjectList rtrObjs
                = archive.retrieve(objectType, domain, instIds, deleteAll);
        checkAllInstancesExist(instIds, rtrObjs);
        // If everything OK do delete
        deletedInstIds = archive.delete(objectType, domain, instIds, deleteAll);
        archiveEventPublisher.publishEvents(OBJECT_DELETED_OBJ_NO, objectType, domain, instIds);
        return deletedInstIds;
    }

    /**
     * Reset operation - creates archive event publisher
     */
    public void reset() {
        LoggingBase.logMessage(CLS + ":Reset");
        Archive.inst().reset();
        lastInstanceId = 0;
        // Setup archive event publisher
        if (archiveEventPublisher == null) {
            try {
                LoggingBase.logMessage(CLS + ":Reset Create archive publisher");
                archiveEventPublisher = new ArchiveEventPublisher();
                archiveEventPublisher.createPublisher(testService);
            } catch (MALInteractionException ex) {
                LoggingBase.logMessage(CLS + ":constructor ERR " + ex);
                ex.printStackTrace();
            } catch (MALException ex) {
                LoggingBase.logMessage(CLS + ":constructor ERR " + ex);
                ex.printStackTrace();
            }
        }
    }
}
