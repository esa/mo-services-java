package org.ccsds.moims.mo.mal;

/**
 * Creates the Elements of the MAL area, without holding an instance of each
 * of them, so that the class of a type is only loaded once a message carries
 * that type.
 */
public final class MALElementFactory implements org.ccsds.moims.mo.mal.AreaElementFactory {

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement(int serviceNumber,
            int typeNumber) {
        if (serviceNumber != 0) {
            return null; // This Area declares no types under a service
        }
        switch (typeNumber) {
            case -105: return new org.ccsds.moims.mo.mal.structures.MOAreaList();
            case -104: return new org.ccsds.moims.mo.mal.structures.AttributeTypeList();
            case -103: return new org.ccsds.moims.mo.mal.structures.QoSLevelList();
            case -102: return new org.ccsds.moims.mo.mal.structures.SessionTypeList();
            case -101: return new org.ccsds.moims.mo.mal.structures.InteractionTypeList();
            case -19: return new org.ccsds.moims.mo.mal.structures.ObjectRefList();
            case -18: return new org.ccsds.moims.mo.mal.structures.URIList();
            case -17: return new org.ccsds.moims.mo.mal.structures.FineTimeList();
            case -16: return new org.ccsds.moims.mo.mal.structures.TimeList();
            case -15: return new org.ccsds.moims.mo.mal.structures.StringList();
            case -14: return new org.ccsds.moims.mo.mal.structures.ULongList();
            case -13: return new org.ccsds.moims.mo.mal.structures.LongList();
            case -12: return new org.ccsds.moims.mo.mal.structures.UIntegerList();
            case -11: return new org.ccsds.moims.mo.mal.structures.IntegerList();
            case -10: return new org.ccsds.moims.mo.mal.structures.UShortList();
            case -9: return new org.ccsds.moims.mo.mal.structures.ShortList();
            case -8: return new org.ccsds.moims.mo.mal.structures.UOctetList();
            case -7: return new org.ccsds.moims.mo.mal.structures.OctetList();
            case -6: return new org.ccsds.moims.mo.mal.structures.IdentifierList();
            case -5: return new org.ccsds.moims.mo.mal.structures.DoubleList();
            case -4: return new org.ccsds.moims.mo.mal.structures.FloatList();
            case -3: return new org.ccsds.moims.mo.mal.structures.DurationList();
            case -2: return new org.ccsds.moims.mo.mal.structures.BooleanList();
            case -1: return new org.ccsds.moims.mo.mal.structures.BlobList();
            case 1: return new org.ccsds.moims.mo.mal.structures.Blob();
            case 2: return new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE);
            case 3: return new org.ccsds.moims.mo.mal.structures.Duration();
            case 4: return new org.ccsds.moims.mo.mal.structures.Union(Float.MAX_VALUE);
            case 5: return new org.ccsds.moims.mo.mal.structures.Union(Double.MAX_VALUE);
            case 6: return new org.ccsds.moims.mo.mal.structures.Identifier();
            case 7: return new org.ccsds.moims.mo.mal.structures.Union(Byte.MAX_VALUE);
            case 8: return new org.ccsds.moims.mo.mal.structures.UOctet();
            case 9: return new org.ccsds.moims.mo.mal.structures.Union(Short.MAX_VALUE);
            case 10: return new org.ccsds.moims.mo.mal.structures.UShort();
            case 11: return new org.ccsds.moims.mo.mal.structures.Union(Integer.MAX_VALUE);
            case 12: return new org.ccsds.moims.mo.mal.structures.UInteger();
            case 13: return new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE);
            case 14: return new org.ccsds.moims.mo.mal.structures.ULong();
            case 15: return new org.ccsds.moims.mo.mal.structures.Union("");
            case 16: return new org.ccsds.moims.mo.mal.structures.Time();
            case 17: return new org.ccsds.moims.mo.mal.structures.FineTime();
            case 18: return new org.ccsds.moims.mo.mal.structures.URI();
            case 19: return new org.ccsds.moims.mo.mal.structures.ObjectRef();
            case 101: return new org.ccsds.moims.mo.mal.structures.InteractionType();
            case 102: return new org.ccsds.moims.mo.mal.structures.SessionType();
            case 103: return new org.ccsds.moims.mo.mal.structures.QoSLevel();
            case 104: return new org.ccsds.moims.mo.mal.structures.AttributeType();
            case 105: return new org.ccsds.moims.mo.mal.structures.MOArea();
            default: return createAreaElementOutOfBand(typeNumber);
        }
    }

    @Override
    public int getAreaNumber() {
        return 1;
    }

    @Override
    public int getAreaVersion() {
        return 3;
    }

    /**
     * Creates an Element whose type number lies too far out to be held in the
     * jump table that is asked first. This says nothing about how often the type
     * is asked for: the numbers of an Area are not handed out in the order of
     * use.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createAreaElementOutOfBand(int typeNumber) {
        if (typeNumber > 0) {
            switch (typeNumber) {
                case 1001: return new org.ccsds.moims.mo.mal.structures.Subscription();
                case 1002: return new org.ccsds.moims.mo.mal.structures.SubscriptionFilter();
                case 1003: return new org.ccsds.moims.mo.mal.structures.UpdateHeader();
                case 1004: return new org.ccsds.moims.mo.mal.structures.IdBooleanPair();
                case 1005: return new org.ccsds.moims.mo.mal.structures.Pair();
                case 1006: return new org.ccsds.moims.mo.mal.structures.NamedValue();
                case 1007: return new org.ccsds.moims.mo.mal.structures.File();
                case 1008: return new org.ccsds.moims.mo.mal.structures.ObjectIdentity();
                case 1009: return new org.ccsds.moims.mo.mal.structures.ServiceId();
                case 1010: return new org.ccsds.moims.mo.mal.structures.NullableAttribute();
                default: return null;
            }
        }
        switch (typeNumber) {
            case -1010: return new org.ccsds.moims.mo.mal.structures.NullableAttributeList();
            case -1009: return new org.ccsds.moims.mo.mal.structures.ServiceIdList();
            case -1008: return new org.ccsds.moims.mo.mal.structures.ObjectIdentityList();
            case -1007: return new org.ccsds.moims.mo.mal.structures.FileList();
            case -1006: return new org.ccsds.moims.mo.mal.structures.NamedValueList();
            case -1005: return new org.ccsds.moims.mo.mal.structures.PairList();
            case -1004: return new org.ccsds.moims.mo.mal.structures.IdBooleanPairList();
            case -1003: return new org.ccsds.moims.mo.mal.structures.UpdateHeaderList();
            case -1002: return new org.ccsds.moims.mo.mal.structures.SubscriptionFilterList();
            case -1001: return new org.ccsds.moims.mo.mal.structures.SubscriptionList();
            default: return null;
        }
    }

}
