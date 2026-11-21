package org.ccsds.moims.mo.mal;

import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.BlobList;
import org.ccsds.moims.mo.mal.structures.BooleanList;
import org.ccsds.moims.mo.mal.structures.DoubleList;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.DurationList;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.File;
import org.ccsds.moims.mo.mal.structures.FileList;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.FineTimeList;
import org.ccsds.moims.mo.mal.structures.FloatList;
import org.ccsds.moims.mo.mal.structures.IdBooleanPair;
import org.ccsds.moims.mo.mal.structures.IdBooleanPairList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.InteractionTypeList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.MOArea;
import org.ccsds.moims.mo.mal.structures.MOAreaList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectIdentityList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.OctetList;
import org.ccsds.moims.mo.mal.structures.Pair;
import org.ccsds.moims.mo.mal.structures.PairList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.QoSLevelList;
import org.ccsds.moims.mo.mal.structures.ServiceId;
import org.ccsds.moims.mo.mal.structures.ServiceIdList;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.SessionTypeList;
import org.ccsds.moims.mo.mal.structures.ShortList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.SubscriptionList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.TimeList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.ULongList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UOctetList;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.URIList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UShortList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;

/**
 * Creates the Elements of the MAL area, without holding an instance of each
 * of them, so that the class of a type is only loaded once a message carries
 * that type.
 */
public final class MALElementFactory implements AreaElementFactory {

    @Override
    public Element createElement(int serviceNumber,
            int typeNumber) {
        if (serviceNumber != 0) {
            return null; // This Area declares no types under a service
        }
        switch (typeNumber) {
            case -105: return new MOAreaList();
            case -104: return new AttributeTypeList();
            case -103: return new QoSLevelList();
            case -102: return new SessionTypeList();
            case -101: return new InteractionTypeList();
            case -19: return new ObjectRefList();
            case -18: return new URIList();
            case -17: return new FineTimeList();
            case -16: return new TimeList();
            case -15: return new StringList();
            case -14: return new ULongList();
            case -13: return new LongList();
            case -12: return new UIntegerList();
            case -11: return new IntegerList();
            case -10: return new UShortList();
            case -9: return new ShortList();
            case -8: return new UOctetList();
            case -7: return new OctetList();
            case -6: return new IdentifierList();
            case -5: return new DoubleList();
            case -4: return new FloatList();
            case -3: return new DurationList();
            case -2: return new BooleanList();
            case -1: return new BlobList();
            case 1: return new Blob();
            case 2: return new Union(Boolean.FALSE);
            case 3: return new Duration();
            case 4: return new Union(Float.MAX_VALUE);
            case 5: return new Union(Double.MAX_VALUE);
            case 6: return new Identifier();
            case 7: return new Union(Byte.MAX_VALUE);
            case 8: return new UOctet();
            case 9: return new Union(Short.MAX_VALUE);
            case 10: return new UShort();
            case 11: return new Union(Integer.MAX_VALUE);
            case 12: return new UInteger();
            case 13: return new Union(Long.MAX_VALUE);
            case 14: return new ULong();
            case 15: return new Union("");
            case 16: return new Time();
            case 17: return new FineTime();
            case 18: return new URI();
            case 19: return new ObjectRef();
            case 101: return new InteractionType();
            case 102: return new SessionType();
            case 103: return new QoSLevel();
            case 104: return new AttributeType();
            case 105: return new MOArea();
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
    private static Element createAreaElementOutOfBand(int typeNumber) {
        if (typeNumber > 0) {
            switch (typeNumber) {
                case 1001: return new Subscription();
                case 1002: return new SubscriptionFilter();
                case 1003: return new UpdateHeader();
                case 1004: return new IdBooleanPair();
                case 1005: return new Pair();
                case 1006: return new NamedValue();
                case 1007: return new File();
                case 1008: return new ObjectIdentity();
                case 1009: return new ServiceId();
                case 1010: return new NullableAttribute();
                default: return null;
            }
        }
        switch (typeNumber) {
            case -1010: return new NullableAttributeList();
            case -1009: return new ServiceIdList();
            case -1008: return new ObjectIdentityList();
            case -1007: return new FileList();
            case -1006: return new NamedValueList();
            case -1005: return new PairList();
            case -1004: return new IdBooleanPairList();
            case -1003: return new UpdateHeaderList();
            case -1002: return new SubscriptionFilterList();
            case -1001: return new SubscriptionList();
            default: return null;
        }
    }

}
