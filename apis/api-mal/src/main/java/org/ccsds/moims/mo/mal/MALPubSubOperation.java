/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal;

import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;

/**
 * Class representing a Publish-Subscribe operation.
 */
public class MALPubSubOperation extends MALOperation {

    /**
     * Literal representing the REGISTER stage.
     */
    public static final byte _REGISTER_STAGE = (byte) 0x1;
    /**
     * MAL UOctet representing the REGISTER stage.
     */
    public static final UOctet REGISTER_STAGE = new UOctet(_REGISTER_STAGE);
    /**
     * Literal representing the REGISTER_ACK stage.
     */
    public static final byte _REGISTER_ACK_STAGE = (byte) 0x2;
    /**
     * MAL UOctet representing the REGISTER_ACK stage.
     */
    public static final UOctet REGISTER_ACK_STAGE = new UOctet(_REGISTER_ACK_STAGE);
    /**
     * Literal representing the PUBLISH_REGISTER stage.
     */
    public static final byte _PUBLISH_REGISTER_STAGE = (byte) 0x3;
    /**
     * MAL UOctet representing the PUBLISH_REGISTER stage.
     */
    public static final UOctet PUBLISH_REGISTER_STAGE = new UOctet(_PUBLISH_REGISTER_STAGE);
    /**
     * Literal representing the PUBLISH_REGISTER_ACK stage.
     */
    public static final byte _PUBLISH_REGISTER_ACK_STAGE = (byte) 0x4;
    /**
     * MAL UOctet representing the PUBLISH_REGISTER_ACK stage.
     */
    public static final UOctet PUBLISH_REGISTER_ACK_STAGE = new UOctet(_PUBLISH_REGISTER_ACK_STAGE);
    /**
     * Literal representing the PUBLISH stage.
     */
    public static final byte _PUBLISH_STAGE = (byte) 0x5;
    /**
     * MAL UOctet representing the PUBLISH stage.
     */
    public static final UOctet PUBLISH_STAGE = new UOctet(_PUBLISH_STAGE);
    /**
     * Literal representing the NOTIFY stage.
     */
    public static final byte _NOTIFY_STAGE = (byte) 0x6;
    /**
     * MAL UOctet representing the NOTIFY stage.
     */
    public static final UOctet NOTIFY_STAGE = new UOctet(_NOTIFY_STAGE);
    /**
     * Literal representing the DEREGISTER stage.
     */
    public static final byte _DEREGISTER_STAGE = (byte) 0x7;
    /**
     * MAL UOctet representing the DEREGISTER stage.
     */
    public static final UOctet DEREGISTER_STAGE = new UOctet(_DEREGISTER_STAGE);
    /**
     * Literal representing the DEREGISTER_ACK stage.
     */
    public static final byte _DEREGISTER_ACK_STAGE = (byte) 0x8;
    /**
     * MAL UOctet representing the DEREGISTER_ACK stage.
     */
    public static final UOctet DEREGISTER_ACK_STAGE = new UOctet(_DEREGISTER_ACK_STAGE);
    /**
     * Literal representing the PUBLISH_DEREGISTER stage.
     */
    public static final byte _PUBLISH_DEREGISTER_STAGE = (byte) 0x9;
    /**
     * MAL UOctet representing the PUBLISH_DEREGISTER stage.
     */
    public static final UOctet PUBLISH_DEREGISTER_STAGE = new UOctet(_PUBLISH_DEREGISTER_STAGE);
    /**
     * Literal representing the PUBLISH_DEREGISTER_ACK stage.
     */
    public static final byte _PUBLISH_DEREGISTER_ACK_STAGE = (byte) 0x0A;
    /**
     * MAL UOctet representing the PUBLISH_DEREGISTER_ACK stage.
     */
    public static final UOctet PUBLISH_DEREGISTER_ACK_STAGE = new UOctet(_PUBLISH_DEREGISTER_ACK_STAGE);
    private static final MALOperationStage PUBSUB_REG_OPERATION_STAGE
            = new MALOperationStage(REGISTER_STAGE, new Object[]{Subscription.SHORT_FORM}, new Object[0]);
    private static final MALOperationStage PUBSUB_REGACK_OPERATION_STAGE
            = new MALOperationStage(REGISTER_ACK_STAGE, new Object[0], new Object[0]);
    private static final MALOperationStage PUBSUB_PUBREG_OPERATION_STAGE
            = new MALOperationStage(PUBLISH_REGISTER_STAGE, new Object[]{EntityKeyList.SHORT_FORM}, new Object[0]);
    private static final MALOperationStage PUBSUB_PUBREGACK_OPERATION_STAGE
            = new MALOperationStage(PUBLISH_REGISTER_ACK_STAGE, new Object[0], new Object[0]);
    private static final MALOperationStage PUBSUB_DEREG_OPERATION_STAGE
            = new MALOperationStage(DEREGISTER_STAGE, new Object[]{IdentifierList.SHORT_FORM}, new Object[0]);
    private static final MALOperationStage PUBSUB_DEREGACK_OPERATION_STAGE
            = new MALOperationStage(DEREGISTER_ACK_STAGE, new Object[0], new Object[0]);
    private static final MALOperationStage PUBSUB_PUBDEREG_OPERATION_STAGE
            = new MALOperationStage(PUBLISH_DEREGISTER_STAGE, new Object[0], new Object[0]);
    private static final MALOperationStage PUBSUB_PUBDEREGACK_OPERATION_STAGE
            = new MALOperationStage(PUBLISH_DEREGISTER_ACK_STAGE, new Object[0], new Object[0]);
    private final MALOperationStage pubSubPublishStage;
    private final MALOperationStage pubSubNotifyStage;

    /**
     * Initialises the internal variables with the supplied values.
     *
     * @param number Number of the operation.
     * @param name Name of the operation.
     * @param replayable Boolean that indicates whether the operation is
     * replayable or not
     * @param capabilitySet Capability set of the operation.
     * @param updateListShortForms Absolute short forms of the update lists
     * transmitted by the PUBLISH/NOTIFY message of a PUBLISH-SUBSCRIBE
     * operation.
     * @param lastUpdateListShortForms Absolute short forms of the update lists
     * that can be assigned to the last element of the PUBLISH/NOTIFY message
     * body
     * @throws java.lang.IllegalArgumentException If any argument is null,
     * except the operation stage arguments.
     */
    public MALPubSubOperation(final UShort number,
            final Identifier name,
            final Boolean replayable,
            final UShort capabilitySet,
            final Object[] updateListShortForms,
            final Object[] lastUpdateListShortForms)
            throws java.lang.IllegalArgumentException {
        super(number, name, replayable, InteractionType.PUBSUB, capabilitySet);

        final Object[] pSF = new Object[updateListShortForms.length + 1];
        final Object[] nSF = new Object[updateListShortForms.length + 2];
        for (int i = 0; i < updateListShortForms.length; i++) {
            final Object v = updateListShortForms[i];
            pSF[i + 1] = v;
            nSF[i + 2] = v;
        }
        nSF[0] = Attribute.IDENTIFIER_SHORT_FORM;
        nSF[1] = UpdateHeaderList.SHORT_FORM;
        pSF[0] = nSF[1];

        this.pubSubPublishStage = new MALOperationStage(PUBLISH_STAGE, pSF, lastUpdateListShortForms);
        this.pubSubNotifyStage = new MALOperationStage(NOTIFY_STAGE, nSF, lastUpdateListShortForms);
    }

    /**
     * Returns the operation stage for the supplied stage number.
     *
     * @param stageNumber The stage number to return.
     * @return The operation stage.
     * @throws java.lang.IllegalArgumentException if the supplied argument is
     * null or stage does not exist for this pattern.
     */
    @Override
    public MALOperationStage getOperationStage(final UOctet stageNumber)
            throws IllegalArgumentException {
        if (stageNumber == null) {
            throw new IllegalArgumentException("Supplied stage number must not be NULL");
        }

        switch (stageNumber.getValue()) {
            case _REGISTER_STAGE:
                return PUBSUB_REG_OPERATION_STAGE;
            case _REGISTER_ACK_STAGE:
                return PUBSUB_REGACK_OPERATION_STAGE;
            case _PUBLISH_REGISTER_STAGE:
                return PUBSUB_PUBREG_OPERATION_STAGE;
            case _PUBLISH_REGISTER_ACK_STAGE:
                return PUBSUB_PUBREGACK_OPERATION_STAGE;
            case _PUBLISH_STAGE:
                return pubSubPublishStage;
            case _NOTIFY_STAGE:
                return pubSubNotifyStage;
            case _DEREGISTER_STAGE:
                return PUBSUB_DEREG_OPERATION_STAGE;
            case _DEREGISTER_ACK_STAGE:
                return PUBSUB_DEREGACK_OPERATION_STAGE;
            case _PUBLISH_DEREGISTER_STAGE:
                return PUBSUB_PUBDEREG_OPERATION_STAGE;
            case _PUBLISH_DEREGISTER_ACK_STAGE:
                return PUBSUB_PUBDEREGACK_OPERATION_STAGE;
            default:
                throw new IllegalArgumentException(
                        "Supplied stage number not supported by interaction pattern");
        }
    }
}
