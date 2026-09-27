/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
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
package org.ccsds.moims.mo.mal.test.errors;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.malprototype.errortest.provider.ErrorTestInheritanceSkeleton;

/**
 *
 */
public class ErrorTestHandlerImpl extends ErrorTestInheritanceSkeleton {

    public Element testAuthenticationFailure(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testAuthorizationFailure(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testBadEncoding(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testDeliveryDelayed(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testDeliveryFailed(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testDeliveryTimedout(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testDestinationLost(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testDestinationTransient(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testDestinationUnknown(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testEncryptionFail(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testUnknown(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testUnsupportedArea(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testUnsupportedOperation(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testUnsupportedAreaVersion(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }

    public Element testUnsupportedService(Element _Element, MALInteraction interaction) throws MALException {
        // should never be reached
        return null;
    }
}
