package org.ccsds.moims.mo.mpd;

/**
 * Creates the Elements of the MPD area, without holding an instance of each
 * of them, so that the class of a type is only loaded once a message carries
 * that type.
 */
public final class MPDElementFactory implements org.ccsds.moims.mo.mal.AreaElementFactory {

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement(int serviceNumber,
            int typeNumber) {
        if (serviceNumber != 0) {
            return null; // This Area declares no types under a service
        }
        switch (typeNumber) {
            case -12: return new org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnumList();
            case -11: return new org.ccsds.moims.mo.mpd.structures.StringPatternList();
            case -10: return new org.ccsds.moims.mo.mpd.structures.ValueSetList();
            case -9: return new org.ccsds.moims.mo.mpd.structures.ValueRangeList();
            case -8: return new org.ccsds.moims.mo.mpd.structures.AttributeDefList();
            case -7: return new org.ccsds.moims.mo.mpd.structures.TimeWindowList();
            case -6: return new org.ccsds.moims.mo.mpd.structures.ProductFilterList();
            case -5: return new org.ccsds.moims.mo.mpd.structures.FileMetadataList();
            case -4: return new org.ccsds.moims.mo.mpd.structures.ProductMetadataList();
            case -3: return new org.ccsds.moims.mo.mpd.structures.StandingOrderList();
            case -2: return new org.ccsds.moims.mo.mpd.structures.ProductTypeList();
            case -1: return new org.ccsds.moims.mo.mpd.structures.ProductList();
            case 1: return new org.ccsds.moims.mo.mpd.structures.Product();
            case 2: return new org.ccsds.moims.mo.mpd.structures.ProductType();
            case 3: return new org.ccsds.moims.mo.mpd.structures.StandingOrder();
            case 4: return new org.ccsds.moims.mo.mpd.structures.ProductMetadata();
            case 5: return new org.ccsds.moims.mo.mpd.structures.FileMetadata();
            case 6: return new org.ccsds.moims.mo.mpd.structures.ProductFilter();
            case 7: return new org.ccsds.moims.mo.mpd.structures.TimeWindow();
            case 8: return new org.ccsds.moims.mo.mpd.structures.AttributeDef();
            case 9: return new org.ccsds.moims.mo.mpd.structures.ValueRange();
            case 10: return new org.ccsds.moims.mo.mpd.structures.ValueSet();
            case 11: return new org.ccsds.moims.mo.mpd.structures.StringPattern();
            case 12: return new org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum();
            default: return null;
        }
    }

    @Override
    public int getAreaNumber() {
        return 9;
    }

    @Override
    public int getAreaVersion() {
        return 1;
    }

}
