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
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;

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

    private static final OperationField[] PUBSUB_REG_OPERATION_STAGE = new OperationField[]{
        new OperationField("subscription", false, Subscription.SHORT_FORM)
    };

    private static final OperationField[] PUBSUB_REGACK_OPERATION_STAGE = new OperationField[0];

    private static final OperationField[] PUBSUB_PUBREG_OPERATION_STAGE
            = new OperationField[]{
                new OperationField("names", false, IdentifierList.SHORT_FORM),
                new OperationField("types", false, AttributeTypeList.SHORT_FORM)
            };

    private static final OperationField[] PUBSUB_PUBREGACK_OPERATION_STAGE = new OperationField[0];

    private static final OperationField[] PUBSUB_DEREG_OPERATION_STAGE = new OperationField[]{
        new OperationField("subscriptionIds", false, IdentifierList.SHORT_FORM)
    };

    private static final OperationField[] PUBSUB_DEREGACK_OPERATION_STAGE = new OperationField[0];

    private static final OperationField[] PUBSUB_PUBDEREG_OPERATION_STAGE = new OperationField[0];

    private static final OperationField[] PUBSUB_PUBDEREGACK_OPERATION_STAGE = new OperationField[0];

    private final OperationField[] pubSubPublishStage;

    private final OperationField[] pubSubNotifyStage;

    /**
     * Initialises the internal variables with the supplied values.
     *
     * @param serviceKey Service Key for the service of this operation.
     * @param number Number of the operation.
     * @param name Name of the operation.
     * @param capabilitySet Capability set of the operation.
     * @param fields PUB-SUB fields. transmitted by the PUBLISH/NOTIFY message
     * of a PUBLISH-SUBSCRIBE operation.
     * @throws java.lang.IllegalArgumentException If any argument is null,
     * except the operation stage arguments.
     */
    public MALPubSubOperation(final ServiceKey serviceKey,
            final UShort number,
            final Identifier name,
            final UShort capabilitySet,
            final OperationField[] fields)
            throws java.lang.IllegalArgumentException {
        super(serviceKey, number, name, InteractionType.PUBSUB, capabilitySet);

        OperationField[] publishFields = new OperationField[fields.length + 1];
        OperationField[] notifyFields = new OperationField[fields.length + 2];

        for (int i = 0; i < fields.length; i++) {
            final OperationField field = fields[i];
            publishFields[i + 1] = field;
            notifyFields[i + 2] = field;
        }
        // Publish message:
        publishFields[0] = new OperationField("header", false, UpdateHeader.SHORT_FORM);

        // Notify message:
        notifyFields[0] = new OperationField("subscriptionId", false, Attribute.IDENTIFIER_SHORT_FORM);
        notifyFields[1] = new OperationField("updateHeader", false, UpdateHeader.SHORT_FORM);

        this.pubSubPublishStage = publishFields;
        this.pubSubNotifyStage = notifyFields;
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
    public OperationField[] getFieldsOnStage(final UOctet stageNumber) throws IllegalArgumentException {
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
