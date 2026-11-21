package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;

/**
 * This data structure specifies how the IPTest provider shall register.
 */
public final class TestPublishDeregister extends TestPublish {

    private static final long serialVersionUID = 28147497687842824L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842824L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The code of the Publish Deregister error expected to be received.-1 if
     * no Publish Deregister error is expected.
     */
    private UInteger errorCode;

    /**
     * Default constructor for TestPublishDeregister.
     * 
     */
    public TestPublishDeregister() {
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
     * @param errorCode The code of the Publish Deregister error expected to be received.-1 if no Publish Deregister error is expected.
     */
    public TestPublishDeregister(QoSLevel Qos,
            UInteger Priority,
            IdentifierList domain,
            Identifier networkZone,
            SessionType Session,
            Identifier sessionName,
            Boolean testMultiType,
            UInteger errorCode) {
        super(Qos,
            Priority,
            domain,
            networkZone,
            Session,
            sessionName,
            testMultiType);
        this.errorCode = errorCode;
    }

    @Override
    public Element createElement() {
        return new TestPublishDeregister();
    }

    /**
     * Returns the field errorCode.
     * 
     * @return The field errorCode
     */
    public UInteger getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TestPublishDeregister) {
            if (! super.equals(obj)) {
                return false;
            }
            TestPublishDeregister other = (TestPublishDeregister) obj;
            if (errorCode == null) {
                if (other.errorCode != null) {
                    return false;
                }
            } else {
                if (! errorCode.equals(other.errorCode)) {
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
        hash = 83 * hash + (errorCode != null ? errorCode.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TestPublishDeregister: ");
        buf.append(super.toString());
        buf.append(", errorCode=").append(errorCode);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        encoder.encodeNullableUInteger(errorCode);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        errorCode = decoder.decodeNullableUInteger();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
