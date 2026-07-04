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
package org.ccsds.moims.mo.mal.test.util;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class AssertionHelper {

    /**
     * Check the equality of all the MAL message header fields.The field
     * 'transactionId' is not checked if the interaction type and stages are: -
     * Submit, Request, Invoke, Progress: initiation stage - Pub/Sub:
     * REGISTER_STAGE, DEREGISTER_STAGE, PUBLISH_REGISTER_STAGE,
     * PUBLISH_DEREGISTER_STAGE
     *
     * @param procedureName
     * @param assertions
     * @param expectedHeader
     * @param header
     * @param limited if TRUE do not check the authenticationId and supplements fields
     */
    public static void checkHeader(String procedureName,
            AssertionList assertions, MALMessageHeader header,
            MALMessageHeader expectedHeader, boolean limited) {
        if ((header == null) || (expectedHeader == null)) {
            checkEquality(procedureName, assertions, "Header", header, expectedHeader);
        } else {
            checkEquality(procedureName, assertions, "Area", header.getServiceArea(),
                    expectedHeader.getServiceArea());
            if (!limited)
                checkEquality(procedureName, assertions, "AuthenticationId", header
                        .getAuthenticationId(), expectedHeader.getAuthenticationId());
            checkEquality(procedureName, assertions, "InteractionType", header
                    .getInteractionType(), expectedHeader.getInteractionType());
            checkEquality(procedureName, assertions, "InteractionStage", header
                    .getInteractionStage(), expectedHeader.getInteractionStage());
            checkEquality(procedureName, assertions, "IsError", header
                    .getIsErrorMessage(), expectedHeader.getIsErrorMessage());
            if (!limited)
                checkEquality(procedureName, assertions, "Supplements",
                        header.getSupplements(), expectedHeader.getSupplements());
            checkEquality(procedureName, assertions, "Operation",
                    header.getOperation(), expectedHeader.getOperation());
            checkEquality(procedureName, assertions, "Service", header.getService(),
                    expectedHeader.getService());
            checkTimestamp(procedureName, assertions, header.getTimestamp(),
                    expectedHeader.getTimestamp());

            boolean checkTransactionId;
            if (expectedHeader.getInteractionType().getValue() == InteractionType.PUBSUB_VALUE) {
                switch (expectedHeader.getInteractionStage().getValue()) {
                    case MALPubSubOperation._REGISTER_STAGE:
                    case MALPubSubOperation._DEREGISTER_STAGE:
                    case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
                    case MALPubSubOperation._PUBLISH_DEREGISTER_STAGE:
                        checkTransactionId = false;
                        break;
                    default:
                        checkTransactionId = true;
                }
            } else {
                checkTransactionId = (expectedHeader.getInteractionStage().getValue() != 0x01);
            }

            if (checkTransactionId) {
                checkEquality(procedureName, assertions, "TransactionId", header
                        .getTransactionId(), expectedHeader.getTransactionId());
            }
            checkEquality(procedureName, assertions, "From", header.getFrom(),
                    expectedHeader.getFrom());
            checkEquality(procedureName, assertions, "To", header.getTo(),
                    expectedHeader.getTo());
            checkEquality(procedureName, assertions, "Version", header.getAreaVersion(),
                    expectedHeader.getAreaVersion());
        }
    }

    /**
     * Check the equality of all the MAL message header fields.The field
     * 'transactionId' is not checked if the interaction type and stages are: -
     * Submit, Request, Invoke, Progress: initiation stage - Pub/Sub:
     * REGISTER_STAGE, DEREGISTER_STAGE, PUBLISH_REGISTER_STAGE,
     * PUBLISH_DEREGISTER_STAGE
     *
     * @param procedureName
     * @param assertions
     * @param expectedHeader
     * @param header
     */
    public static void checkHeader(String procedureName,
            AssertionList assertions, MALMessageHeader header,
            MALMessageHeader expectedHeader) {
        checkHeader(procedureName, assertions, header, expectedHeader, false);
    }
    
    public static void checkEquality(String procedureName,
            AssertionList assertions, String fieldName,
            Object field, Object expectedField) {
        boolean res;
        if (expectedField == null) {
            res = (field == null);
        } else {
            // if checking for supplements, they may be in a different order
            if (field.getClass().getName() == NamedValueList.class.getName() && expectedField.getClass().getName() == NamedValueList.class.getName()) {
                res = checkSupplementEquality(NamedValueList.class.cast(field), NamedValueList.class.cast(expectedField));
            }
            else {
                res = expectedField.equals(field);
            }
        }
        Assertion assertion = new Assertion(procedureName, "Check header field '"
                + fieldName + "': " + toString(field) + " == " + toString(expectedField), res);
        assertions.add(assertion);
    }

    private static boolean checkSupplementEquality(NamedValueList supplements, NamedValueList expectedSupplements) {
        if (supplements.size() != expectedSupplements.size()) {
            return false;
        }
        for (NamedValue namedValue : supplements){
            if (!expectedSupplements.contains(namedValue)) {
                return false;
            }
        }
        return true;
    }

    private static String toString(Object field) {
        if (field == null) {
            return "null";
        } else if (field instanceof Blob) {
            Blob b = (Blob) field;
            byte[] bytes;
            bytes = b.getValue();
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                buf.append(bytes[i]);
                if (i < bytes.length - 1) {
                    buf.append('.');
                }
            }
            return buf.toString();
        } else if (field instanceof IdentifierList) {
            IdentifierList domainId = (IdentifierList) field;
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < domainId.size(); i++) {
                buf.append(domainId.get(i).getValue());
                if (i < domainId.size() - 1) {
                    buf.append('.');
                }
            }
            return buf.toString();
        } else {
            return field.toString();
        }
    }

    public static void checkTimestamp(String procedureName,
            AssertionList assertions, Time field, Time expectedField) {
        boolean res;
        if (field == null) {
            res = false;
        } else {
            res = expectedField.getValue() <= field.getValue();
        }
        Assertion assertion = new Assertion(procedureName, "Check header field 'timestamp': "
                + field.getValue() + " >= " + expectedField.getValue(), res);
        assertions.add(assertion);
    }

    public static boolean checkAssertions(AssertionList assertions) {
        boolean res = true;
        for (int i = 0; i < assertions.size(); i++) {
            Assertion assertion = assertions.get(i);
            String msg = assertion.getProcedureName()
                    + indent(assertion.getProcedureName(), 40) + " | "
                    + assertion.getInfo()
                    + indent(assertion.getInfo(), 70) + " | ";
            if (assertion.getResult()) {
                LoggingBase.logMessage(msg + "OK");
            } else {
                LoggingBase.logMessage(msg + "FAILED");
                res = false;
            }
        }
        return res;
    }

    private static String indent(String msg, int size) {
        int msgLength;
        if (msg == null) {
            msgLength = 0;
        } else {
            msgLength = msg.length();
        }
        int indentLength = size - msgLength;
        String indent = "";
        if (indentLength > 0) {
            for (int i = 0; i < indentLength; i++) {
                indent = indent + " ";
            }
        }
        return indent;
    }
}
