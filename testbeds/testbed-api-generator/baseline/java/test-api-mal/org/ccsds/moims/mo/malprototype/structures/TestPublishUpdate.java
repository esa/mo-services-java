package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;

/**
 * This data structure specifies how the IPTest provider shall publish an
 * update.
 */
public final class TestPublishUpdate extends TestPublish {

    private static final long serialVersionUID = 28147497687842825L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842825L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The headers of the updates to be published by the provider.
     */
    private UpdateHeaderList updateHeaders;

    /**
     * The updates to be published by the provider.
     */
    private TestUpdateList updates;

    /**
     * The list of key values.
     */
    private NullableAttributeList keyValues;

    /**
     * The code of the Publish error expected to be received.-1 if no Publish
     * error is expected.
     */
    private UInteger errorCode;

    /**
     * Indicates whether the error is returned as an Exception or a Publish Error
     * message.
     */
    private Boolean isException;

    /**
     * The list of failed keys values.
     */
    private NullableAttributeList failedKeyValues;

    /**
     * Default constructor for TestPublishUpdate.
     * 
     */
    public TestPublishUpdate() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param Qos The QoS level to be used by the provider.
     * @param Priority The priority to be used by the provider.
     * @param domain The domain to be used by the provider.
     * @param networkZone The network zone to be used by the provider.
     * @param Session The session type to be used by the provider.
     * @param sessionName The session name to be used by the provider.
     * @param testMultiType Whether to use the multi type version of the PubSub operation.
     * @param updateHeaders The headers of the updates to be published by the provider
     * @param updates The updates to be published by the provider
     * @param keyValues The list of key values
     * @param errorCode The code of the Publish error expected to be received.-1 if no Publish error is expected.
     * @param isException Indicates whether the error is returned as an Exception or a Publish Error message
     * @param failedKeyValues The list of failed keys values.
     */
    public TestPublishUpdate(QoSLevel Qos,
            UInteger Priority,
            IdentifierList domain,
            Identifier networkZone,
            SessionType Session,
            Identifier sessionName,
            Boolean testMultiType,
            UpdateHeaderList updateHeaders,
            TestUpdateList updates,
            NullableAttributeList keyValues,
            UInteger errorCode,
            Boolean isException,
            NullableAttributeList failedKeyValues) {
        super(Qos,
            Priority,
            domain,
            networkZone,
            Session,
            sessionName,
            testMultiType);
        this.updateHeaders = updateHeaders;
        this.updates = updates;
        this.keyValues = keyValues;
        this.errorCode = errorCode;
        this.isException = isException;
        this.failedKeyValues = failedKeyValues;
    }

    @Override
    public Element createElement() {
        return new TestPublishUpdate();
    }

    /**
     * Returns the field updateHeaders.
     * 
     * @return The field updateHeaders
     */
    public UpdateHeaderList getUpdateHeaders() {
        return updateHeaders;
    }

    /**
     * Returns the field updates.
     * 
     * @return The field updates
     */
    public TestUpdateList getUpdates() {
        return updates;
    }

    /**
     * Returns the field keyValues.
     * 
     * @return The field keyValues
     */
    public NullableAttributeList getKeyValues() {
        return keyValues;
    }

    /**
     * Returns the field errorCode.
     * 
     * @return The field errorCode
     */
    public UInteger getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the field isException.
     * 
     * @return The field isException
     */
    public Boolean getIsException() {
        return isException;
    }

    /**
     * Returns the field failedKeyValues.
     * 
     * @return The field failedKeyValues
     */
    public NullableAttributeList getFailedKeyValues() {
        return failedKeyValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TestPublishUpdate) {
            if (! super.equals(obj)) {
                return false;
            }
            TestPublishUpdate other = (TestPublishUpdate) obj;
            if (updateHeaders == null) {
                if (other.updateHeaders != null) {
                    return false;
                }
            } else {
                if (! updateHeaders.equals(other.updateHeaders)) {
                    return false;
                }
            }
            if (updates == null) {
                if (other.updates != null) {
                    return false;
                }
            } else {
                if (! updates.equals(other.updates)) {
                    return false;
                }
            }
            if (keyValues == null) {
                if (other.keyValues != null) {
                    return false;
                }
            } else {
                if (! keyValues.equals(other.keyValues)) {
                    return false;
                }
            }
            if (errorCode == null) {
                if (other.errorCode != null) {
                    return false;
                }
            } else {
                if (! errorCode.equals(other.errorCode)) {
                    return false;
                }
            }
            if (isException == null) {
                if (other.isException != null) {
                    return false;
                }
            } else {
                if (! isException.equals(other.isException)) {
                    return false;
                }
            }
            if (failedKeyValues == null) {
                if (other.failedKeyValues != null) {
                    return false;
                }
            } else {
                if (! failedKeyValues.equals(other.failedKeyValues)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (updateHeaders != null ? updateHeaders.hashCode() : 0);
        hash = 83 * hash + (updates != null ? updates.hashCode() : 0);
        hash = 83 * hash + (keyValues != null ? keyValues.hashCode() : 0);
        hash = 83 * hash + (errorCode != null ? errorCode.hashCode() : 0);
        hash = 83 * hash + (isException != null ? isException.hashCode() : 0);
        hash = 83 * hash + (failedKeyValues != null ? failedKeyValues.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TestPublishUpdate: ");
        buf.append(super.toString());
        buf.append(", updateHeaders=").append(updateHeaders);
        buf.append(", updates=").append(updates);
        buf.append(", keyValues=").append(keyValues);
        buf.append(", errorCode=").append(errorCode);
        buf.append(", isException=").append(isException);
        buf.append(", failedKeyValues=").append(failedKeyValues);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        encoder.encodeNullableElement(updateHeaders);
        encoder.encodeNullableElement(updates);
        encoder.encodeNullableElement(keyValues);
        encoder.encodeNullableUInteger(errorCode);
        encoder.encodeNullableBoolean(isException);
        encoder.encodeNullableElement(failedKeyValues);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        updateHeaders = (UpdateHeaderList) decoder.decodeNullableElement(new UpdateHeaderList());
        updates = (TestUpdateList) decoder.decodeNullableElement(new TestUpdateList());
        keyValues = (NullableAttributeList) decoder.decodeNullableElement(new NullableAttributeList());
        errorCode = decoder.decodeNullableUInteger();
        isException = decoder.decodeNullableBoolean();
        failedKeyValues = (NullableAttributeList) decoder.decodeNullableElement(new NullableAttributeList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
