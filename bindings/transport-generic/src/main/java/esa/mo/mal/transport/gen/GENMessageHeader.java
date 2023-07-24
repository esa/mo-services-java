/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen;

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * A generic implementation of the message header interface.
 */
public class GENMessageHeader extends MALMessageHeader {

    protected static final long serialVersionUID = 111111111111111L;

    /**
     * Constructor.
     */
    public GENMessageHeader() {
    }

    /**
     * Constructor.
     *
     * @param uriFrom URI of the message source
     * @param authenticationId Authentication identifier of the message
     * @param uriTo URI of the message destination
     * @param timestamp Timestamp of the message
     * @param interactionType Interaction type of the operation
     * @param interactionStage Interaction stage of the interaction
     * @param transactionId Transaction identifier of the interaction, may be
     * null.
     * @param serviceArea Area number of the service
     * @param service Service number
     * @param operation Operation number
     * @param serviceVersion Service version number
     * @param isErrorMessage Flag indicating if the message conveys an error
     * @param supplements The header supplements
     */
    public GENMessageHeader(final Identifier uriFrom,
            final Blob authenticationId,
            final Identifier uriTo,
            final Time timestamp,
            final InteractionType interactionType,
            final UOctet interactionStage,
            final Long transactionId,
            final UShort serviceArea,
            final UShort service,
            final UShort operation,
            final UOctet serviceVersion,
            final Boolean isErrorMessage,
            final NamedValueList supplements) {
        super(uriFrom, authenticationId, uriTo, timestamp, interactionType,
                interactionStage, transactionId, serviceArea, service, operation,
                serviceVersion, isErrorMessage, supplements);
    }
}
