/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
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
package org.ccsds.moims.mo.com.test.event;

import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.test.util.COMChecker;
import org.ccsds.moims.mo.com.test.util.COMTestHelper;
import org.ccsds.moims.mo.comprototype.COMPrototypeHelper;
import org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectCreation;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectDeletion;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectUpdate;
import org.ccsds.moims.mo.comprototype.eventtest.structures.UpdateComposite;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ShortList;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 * @author Cesar.Coelho
 */
public class EventDetails {

    UpdateHeader updateHeader = null;
    ObjectDetails objectDetails = null;
    Element element = null;
    String strObject = "";
    String loggingClassName = "EventDetails";

    public EventDetails(UpdateHeader aUpdateHeader, ObjectDetails aObjectDetails, Element aElement) {
        updateHeader = aUpdateHeader;
        objectDetails = aObjectDetails;
        element = aElement;
    }

    public UpdateHeader getUpdateHeader() {
        return updateHeader;
    }

    public ObjectDetails getObjectDetails() {
        return objectDetails;
    }

    public Element getElement() {
        return element;
    }

    public boolean checkHeader(int instId, String objNumber, String sourceObjNumber) {
        LoggingBase.logMessage(loggingClassName + ":headerValid " + strObject + " " + updateHeader);
        boolean bValid = true;

        // Check key
        // First Sub Key = event object number (Identifier) 
        bValid = COMChecker.equalsCheck(strObject, "Header.Key.First",
                updateHeader.getKeyValues().get(0).getValue().toString(),
                objNumber, bValid);
        // Second Sub Key = event object type (3 sub-fields)
        Attribute attVal = updateHeader.getKeyValues().get(1).getValue();
        Long myLong1 = (Long) Attribute.attribute2JavaType(attVal);

        bValid = COMChecker.equalsCheck(strObject, "Header.Key.Second",
                myLong1,
                COMTestHelper.getEventTestObjectTypeAsKey(0), bValid);
        // Third Sub Key = event object instance identifier 
        //updateHeader.getKey().getThirdSubKey().intValue();
        Attribute attVal2 = updateHeader.getKeyValues().get(2).getValue();
        Long myLong2 = (Long) Attribute.attribute2JavaType(attVal2);
        bValid = COMChecker.equalsCheck(strObject, "Header.Key.Third",
                myLong2, instId, bValid);
        // Fourth Sub Key = event source object type (4 sub-fields)
        Attribute attVal3 = updateHeader.getKeyValues().get(3).getValue();
        Long myLong3 = (Long) Attribute.attribute2JavaType(attVal3);
        bValid = COMChecker.equalsCheck(strObject, "Header.Key.Fourth",
                myLong3,
                COMTestHelper.getEventTestObjectTypeAsKey((new Integer(sourceObjNumber)).intValue()), bValid);
        // Check source URI
        COMChecker.equalsCheck(strObject, "Header.UpdateType", updateHeader.getSource().toString(),
                EventTestServiceInfo.EVENTTEST_SERVICE_NAME.toString(), bValid);
        return bValid;
    }

    public boolean objectDetailsValid(String sourceDomain, String sourceObjNumber, String sourceInstId,
            String relatedInstId) {
        boolean bSourceValid = true;
        boolean bRelatedValid = true;
        LoggingBase.logMessage(loggingClassName + ":objectDetailsValid " + sourceDomain + " " + sourceObjNumber + " " + sourceInstId);

        bSourceValid = COMChecker.nullCheck(strObject, "Source",
                objectDetails.getSource(), bSourceValid);
        if (bSourceValid) {
            bSourceValid = objectIdValid("Source", objectDetails.getSource(),
                    sourceDomain, sourceObjNumber, sourceInstId);
        }
        if (relatedInstId != null) {

            bRelatedValid = COMChecker.nullCheck(strObject, "Related",
                    objectDetails.getRelated(), bRelatedValid);
            if (bRelatedValid) {
                bRelatedValid = objectIdValid("Source", objectDetails.getSource(),
                        sourceDomain, sourceObjNumber, sourceInstId);
                bRelatedValid = COMChecker.equalsCheck(strObject, "Related",
                        objectDetails.getRelated().toString(),
                        relatedInstId, bRelatedValid);
            }
        }

        return bSourceValid && bRelatedValid;
    }

    public boolean deletionElementValid(String objectNumber, String description) {
        LoggingBase.logMessage(loggingClassName + ":deletionElementValid " + description);
        boolean bValid = true;

        if (objectNumber.equals(COMTestHelper.TEST_OBJECT_DELETION_NO)) {
            if (element instanceof ObjectDeletion) {
                ObjectDeletion od = (ObjectDeletion) element;
                bValid = COMChecker.equalsCheck(strObject, "Element.description",
                        od.getDescription(), description, bValid);
            } else {
                COMChecker.recordError(strObject, "Element not of expected type - ObjectDeletion");
                bValid = false;
            }
        } else {
            COMChecker.recordError(strObject, "ObjectNumber not as expected");
            bValid = false;
        }
        return bValid;
    }

    public boolean creationElementValid(String objectNumber, String description, boolean success) {
        LoggingBase.logMessage(loggingClassName + ":creationElementValid " + description + " " + success);
        boolean bValid = true;

        if (objectNumber.equals(COMTestHelper.TEST_OBJECT_CREATION_NO)) {
            if (element instanceof ObjectCreation) {
                ObjectCreation oc = (ObjectCreation) element;
                bValid = COMChecker.equalsCheck(strObject, "Element.description",
                        oc.getDescription(), description, bValid);
                bValid = COMChecker.equalsCheck(strObject, "Element.success",
                        oc.getSuccess().toString(), Boolean.valueOf(success).toString(), bValid);
            } else {
                COMChecker.recordError(strObject, "Element not of expected type - ObjectCreation");
                bValid = false;
            }
        } else {
            COMChecker.recordError(strObject, "Objectnumber not supported");
            bValid = false;
        }
        return bValid;
    }

    public boolean updateElementValid(String objectNumber, String enumField, String durationField, String numericListField[]) {
        LoggingBase.logMessage(loggingClassName + ":updateElementValid " + objectNumber + " " + enumField + " " + durationField + numericListField);
        boolean bValid = true;

        if (objectNumber.equals(COMTestHelper.TEST_OBJECT_UPDATE_NO)) {
            if (element instanceof ObjectUpdate) {
                ObjectUpdate ou = (ObjectUpdate) element;
                bValid = COMChecker.equalsCheck(strObject, "Element.enumField",
                        ou.getEnumField().toString(), enumField, bValid);
                bValid = COMChecker.equalsCheck(strObject, "Element.duration",
                        ou.getDurationField().getInSeconds(), new Integer(durationField).intValue(), bValid);
                // Check each element in numericListField
                ShortList list = ou.getNumericListField();
                LoggingBase.logMessage(loggingClassName + ":updateElementValid list RX " + list);
                for (int i = 0; i < numericListField.length; i++) {
                    bValid = COMChecker.equalsCheck(strObject, "Element.listField " + i,
                            list.get(i).intValue(),
                            Integer.parseInt(numericListField[i]), bValid);
                }
            } else {
                COMChecker.recordError(strObject, "Element not of expected type - ObjectUpdate");
                bValid = false;
            }
        } else {
            COMChecker.recordError(strObject, "Objectnumber not of expected type update");
            bValid = false;
        }
        return bValid;
    }

    public boolean updateElementCompositeValid(String objectNumber, String uOctetField, String octetField, String doubleField) {
        LoggingBase.logMessage(loggingClassName + ":updateElementCompositeValid " + objectNumber + " " + uOctetField + " " + octetField + doubleField);
        boolean bValid = true;

        if (objectNumber.equals(COMTestHelper.TEST_OBJECT_UPDATE_NO)) {
            if (element instanceof ObjectUpdate) {
                ObjectUpdate ou = (ObjectUpdate) element;
                UpdateComposite compositeField = ou.getCompositeField();

                int compUoctet = compositeField.getUOctetField().getValue();
                int compOctet = compositeField.getOctetField().byteValue();
                double compDouble = compositeField.getDoubleField().doubleValue();

                bValid = COMChecker.equalsCheck(strObject, "Element.compUoctet",
                        compUoctet, Integer.parseInt(uOctetField), bValid);
                bValid = COMChecker.equalsCheck(strObject, "Element.compOctet",
                        compOctet, Integer.parseInt(octetField), bValid);
                bValid = COMChecker.equalsCheck(strObject, "Element.compDouble",
                        compDouble, Double.parseDouble(doubleField), bValid);
            } else {
                COMChecker.recordError(strObject, "Element not of expected type - ObjectUpdate");
                bValid = false;
            }
        } else {
            COMChecker.recordError(strObject, "Objectnumber not of expected type update");
            bValid = false;
        }
        return bValid;
    }

    private boolean objectIdValid(String check, ObjectId objectId,
            String domain, String objNumber, String instId) {
        boolean bTypeValid = true;
        boolean bKeyValid = true;
        LoggingBase.logMessage(loggingClassName + ":objectIdValid " + check + domain + " " + objNumber + " " + instId);

        bTypeValid = COMChecker.nullCheck(strObject, check + "Type",
                objectId.getType(), bTypeValid);
        // If type valid check fields
        if (bTypeValid) {
            bTypeValid = COMChecker.equalsCheck(strObject, check + ".Type.Area",
                    objectId.getType().getArea(),
                    COMPrototypeHelper.COMPROTOTYPE_AREA_NUMBER, bTypeValid);
            bTypeValid = COMChecker.equalsCheck(strObject, check + ".Type.Service",
                    objectId.getType().getService(),
                    EventTestServiceInfo.EVENTTEST_SERVICE_NUMBER, bTypeValid);
            bTypeValid = COMChecker.equalsCheck(strObject, check + ".Type.Version",
                    objectId.getType().getVersion(),
                    COMPrototypeHelper.COMPROTOTYPE_AREA_VERSION, bTypeValid);
            bTypeValid = COMChecker.equalsCheck(strObject, check + ".Type.Number",
                    objectId.getType().getNumber().toString(),
                    objNumber, bTypeValid);
        }
        bKeyValid = COMChecker.nullCheck(strObject, "Source key",
                objectId.getKey(), bKeyValid);
        // If type valid check fields
        if (bKeyValid) {
            IdentifierList evSourceDomainId = new IdentifierList();
            evSourceDomainId.add(new Identifier(domain));
            LoggingBase.logMessage("MonitorEventDetailsList:KEY = " + objectId.getKey());
            bKeyValid = COMChecker.equalsCheck(strObject, check + ".Key.Domain",
                    objectId.getKey().getDomain().toString(),
                    evSourceDomainId.toString(), bKeyValid);

            bKeyValid = COMChecker.equalsCheck(strObject, check + ".Key.Inst Id",
                    objectId.getKey().getInstId().toString(),
                    instId, bKeyValid);
        }

        return bTypeValid && bKeyValid;
    }
}
