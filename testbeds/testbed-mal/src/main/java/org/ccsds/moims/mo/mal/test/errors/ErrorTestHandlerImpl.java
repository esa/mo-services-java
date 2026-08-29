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

import org.ccsds.moims.mo.mal.BadEncodingException;
import org.ccsds.moims.mo.mal.DeliveryDelayedException;
import org.ccsds.moims.mo.mal.DeliveryFailedException;
import org.ccsds.moims.mo.mal.DeliveryTimedoutException;
import org.ccsds.moims.mo.mal.DestinationLostException;
import org.ccsds.moims.mo.mal.DestinationTransientException;
import org.ccsds.moims.mo.mal.DestinationUnknownException;
import org.ccsds.moims.mo.mal.EncryptionFailException;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.UnsupportedAreaException;
import org.ccsds.moims.mo.mal.UnsupportedAreaVersionException;
import org.ccsds.moims.mo.mal.UnsupportedOperationException;
import org.ccsds.moims.mo.mal.UnsupportedServiceException;
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

    public Element testBadEncoding(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new BadEncodingException());
    }

    public Element testDeliveryDelayed(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new DeliveryDelayedException());
    }

    public Element testDeliveryFailed(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new DeliveryFailedException());
    }

    public Element testDeliveryTimedout(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new DeliveryTimedoutException());
    }

    public Element testDestinationLost(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new DestinationLostException());
    }

    public Element testDestinationTransient(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new DestinationTransientException());
    }

    public Element testDestinationUnknown(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new DestinationUnknownException());
    }

    public Element testEncryptionFail(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new EncryptionFailException());
    }

    public Element testUnknown(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new UnknownException());
    }

    public Element testUnsupportedArea(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new UnsupportedAreaException());
    }

    public Element testUnsupportedOperation(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new UnsupportedOperationException());
    }

    public Element testUnsupportedAreaVersion(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new UnsupportedAreaVersionException());
    }

    public Element testUnsupportedService(Element _Element, MALInteraction interaction) throws MALInteractionException {
        throw new MALInteractionException(new UnsupportedServiceException());
    }
}
