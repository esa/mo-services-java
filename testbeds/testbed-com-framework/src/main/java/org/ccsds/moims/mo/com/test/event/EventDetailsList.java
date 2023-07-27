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
package org.ccsds.moims.mo.com.test.event;

import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import static org.ccsds.moims.mo.testbed.util.LoggingBase.logMessage;

/**
 *
 */
public class EventDetailsList extends java.util.ArrayList<EventDetails> {

    String loggingClassName = "EventDetailsList";

    /**
     * Checks if an event exists with the specified values Checks from the end
     * of the list so will return the last event received matching the criteria
     *
     * @param objNumber object number
     * @param sourceObjNumber source object number
     * @param sourceDomain source domain
     * @param sourceInstId source instance identifier
     * @return the index within this array of the event if found, otherwise NULL
     */
    String eventExists(String objNumber, String sourceObjNumber, String sourceDomain, String sourceInstId) {
        logMessage(loggingClassName + ":eventExists objNumber=" + objNumber
                + " sourceObjNumber=" + sourceObjNumber
                + " sourceDomain=" + sourceDomain + " sourceInstId=" + sourceInstId);
        boolean bFound = false;

        IdentifierList evSourceDomainId = new IdentifierList();
        evSourceDomainId.add(new Identifier(sourceDomain));
        int index;
        for (index = (size() - 1); index >= 0 && !bFound; index--) {
            EventDetails eventDetails = get(index);
            String evObjNumber = eventDetails.getUpdateHeader().getKeyValues().get(0).getValue().toString();
            String evSourceObjNumber = eventDetails.getObjectDetails().getSource().getType().getNumber().toString();
            IdentifierList evSourceDomain = eventDetails.getObjectDetails().getSource().getKey().getDomain();
            String evSourceInstId = eventDetails.getObjectDetails().getSource().getKey().getInstId().toString();
            bFound = evObjNumber.equals(objNumber) && evSourceObjNumber.equals(sourceObjNumber)
                    && evSourceDomainId.equals(evSourceDomainId) && evSourceInstId.equals(sourceInstId);
            logMessage(loggingClassName + ":eventExists chk " + evObjNumber + " " + evSourceObjNumber + " " + evSourceDomain + " " + evSourceInstId);
            logMessage(loggingClassName + ":eventExists index " + index);
        }

        logMessage(loggingClassName + ":eventExists index " + index);
        if (bFound) {
            return new Integer(index + 1).toString();
        } else {
            return null;
        }

    }
}
