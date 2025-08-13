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
import java.util.Comparator;
import org.ccsds.moims.mo.comprototype.archivetest.structures.*;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.mal.structures.Composite;

/**
 *
 */
public class ArchiveObjectCompare implements Comparator<Archive.ArchiveObject> {

    boolean sortOrder;
    boolean sortFailed;
    String sortFieldName;
    private final String CLS = "ArchiveObjectCompare:";

    public ArchiveObjectCompare(boolean sortOrder, String sortFieldname) {
        this.sortOrder = sortOrder;
        this.sortFieldName = sortFieldname;
        sortFailed = false;
    }

    public boolean sortFailed() {
        return sortFailed;
    }

    private boolean isNull(Archive.ArchiveObject obj) {
        boolean bReturn = false;
        if (sortFieldName != null) {
            if (obj.getElement() == null) {
                bReturn = true;
            } else if (sortFieldName.equals("enumeratedField")
                    && ((TestObjectPayload) obj.getElement()).getEnumeratedField() == null) {
                bReturn = true;
            } else if (sortFieldName.equals("integerField")
                    && ((TestObjectPayload) obj.getElement()).getIntegerField() == null) {
                bReturn = true;
            } else if (sortFieldName.equals("booleanField")
                    && ((TestObjectPayload) obj.getElement()).getBooleanField() == null) {
                bReturn = true;
            } else if (sortFieldName.equals("stringField")
                    && ((TestObjectPayload) obj.getElement()).getStringField() == null) {
                bReturn = true;
            } else if (sortFieldName.equals("compositeField.integerField")
                    && ((TestObjectPayload) obj.getElement()).getCompositeField() == null) {
                bReturn = true;
            } else if (sortFieldName.equals("compositeField.integerField")
                    && ((TestObjectPayload) obj.getElement()).getCompositeField().getIntegerField() == null) {
                bReturn = true;
            }
        }
        return bReturn;
    }

    public void getNullObjs(Archive.ArchiveObjectList objs,
            Archive.ArchiveObjectList nullObjs, Archive.ArchiveObjectList nonNullObjs) {
        LoggingBase.logMessage(CLS + ":getNullObjs:Objs:" + objs.size());
        for (int i = 0; i < objs.size(); i++) {
            if (isNull(objs.get(i))) {
                nullObjs.add(objs.get(i));
            } else {
                nonNullObjs.add(objs.get(i));
            }
        }
        LoggingBase.logMessage(CLS + ":getNullObjs:" + nullObjs.size() + ":" + nonNullObjs.size());
    }

    @Override
    public int compare(Archive.ArchiveObject o1, Archive.ArchiveObject o2) {
        int retVal = 0;
        // First deal with default case sort field - timestamp
        if (sortFieldName == null) {
            retVal = (int) (o1.getArchiveDetails().getTimestamp().getValue()
                    - o2.getArchiveDetails().getTimestamp().getValue());
        } else if (!(o1.getElement() instanceof Composite)) {
            // For atributes 
            if (o1.getElement() instanceof Integer) {
                retVal = (int) (((Integer) o1.getElement()).intValue()
                        - ((Integer) o2.getElement()).intValue());
            } else if (o1.getElement() instanceof Duration) {
                retVal = (int) (((Duration) o1.getElement()).getInSeconds()
                        - ((Duration) o2.getElement()).getInSeconds());
            } else if (o1.getElement() instanceof Long) {
                retVal = (int) (((Duration) o1.getElement()).getInSeconds()
                        - ((Duration) o2.getElement()).getInSeconds());
            } else if (o1.getElement() instanceof String) {
                retVal
                        = (((String) o1.getElement()).compareTo(
                                ((String) o2.getElement())));
            } else if (o1.getElement() instanceof Enumeration) {
                retVal = (int) (((Enumeration) o1.getElement()).getValue()
                        - ((Enumeration) o2.getElement()).getValue());
            } else if (o1.getElement() instanceof Identifier) {
                retVal = o1.getElement().toString().compareTo(o2.getElement().toString());
//                (((Identifier) o1.getElement()).toString().compareTo(
//                ((String) o2.getElement()).toString()));
            } else if (o1.getElement() instanceof URI) {
                retVal = o1.getElement().toString().compareTo(o2.getElement().toString());
//                (((Identifier) o1.getElement()).toString().compareTo(
//                ((String) o2.getElement()).toString()));
            } else if (o1.getElement() instanceof Blob || o1.getElement() instanceof ArrayList) {
                // No sorting for blob or list
                retVal = 0;
            } else {
                LoggingBase.logMessage(CLS + ":comparet:sort not supported for class "
                        + o1.getElement().getClass());
                sortFailed = true;
            }

        } else if (o1.getElement() instanceof TestObjectPayload) {
            LoggingBase.logMessage(CLS + ":comparet:test object o1:" + o1.getElement());
            if (sortFieldName.equals("enumeratedField")) {
                retVal = (int) (((TestObjectPayload) o1.getElement()).getEnumeratedField().getValue()
                        - ((TestObjectPayload) o2.getElement()).getEnumeratedField().getValue());
            } else if (sortFieldName.equals("integerField")) {
                retVal = (int) (((TestObjectPayload) o1.getElement()).getIntegerField().compareTo(
                        ((TestObjectPayload) o2.getElement()).getIntegerField()));

            } else if (sortFieldName.equals("stringField")) {

                retVal = (int) (((TestObjectPayload) o1.getElement()).getStringField().compareTo(
                        ((TestObjectPayload) o2.getElement()).getStringField()));
            } else if (sortFieldName.equals("compositeField.integerField")) {
                retVal = ((TestObjectPayload) o1.getElement()).getCompositeField().getIntegerField().compareTo(
                        ((TestObjectPayload) o2.getElement()).getCompositeField().getIntegerField());
            } else if (sortFieldName.equals("compositeField") || sortFieldName.equals("listField")) {
                // No sorting applied for composite or list
                retVal = 0;
            } else {
                LoggingBase.logMessage(CLS + ":comparet:field invalid for TestObjectPayload:"
                        + sortFieldName);
                sortFailed = true;
            }
        } else {
            LoggingBase.logMessage(CLS + ":comparet:sort not supported for class "
                    + o1.getElement().getClass());
        }
        // Take into account sort direction
        if (sortOrder == false) {
            retVal = (-retVal);
        }
        LoggingBase.logMessage(CLS + ":comparet:RET " + retVal);
        return retVal;
    }
}
