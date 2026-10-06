package org.ccsds.moims.mo.mpd;

import org.ccsds.moims.mo.mal.AreaElementFactory;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mpd.structures.AttributeDef;
import org.ccsds.moims.mo.mpd.structures.AttributeDefList;
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum;
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnumList;
import org.ccsds.moims.mo.mpd.structures.FileMetadata;
import org.ccsds.moims.mo.mpd.structures.FileMetadataList;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductFilterList;
import org.ccsds.moims.mo.mpd.structures.ProductList;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductMetadataList;
import org.ccsds.moims.mo.mpd.structures.ProductType;
import org.ccsds.moims.mo.mpd.structures.ProductTypeList;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;
import org.ccsds.moims.mo.mpd.structures.StringPattern;
import org.ccsds.moims.mo.mpd.structures.StringPatternList;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;
import org.ccsds.moims.mo.mpd.structures.TimeWindowList;
import org.ccsds.moims.mo.mpd.structures.ValueRange;
import org.ccsds.moims.mo.mpd.structures.ValueRangeList;
import org.ccsds.moims.mo.mpd.structures.ValueSet;
import org.ccsds.moims.mo.mpd.structures.ValueSetList;

/**
 * Creates the Elements of the MPD area, without holding an instance of each
 * of them, so that the class of a type is only loaded once a message carries
 * that type.
 */
public final class MPDElementFactory implements AreaElementFactory {

    @Override
    public Element createElement(int serviceNumber,
            int typeNumber) {
        if (serviceNumber != 0) {
            return null; // This Area declares no types under a service
        }
        switch (typeNumber) {
            case -12: return new DeliveryMethodEnumList();
            case -11: return new StringPatternList();
            case -10: return new ValueSetList();
            case -9: return new ValueRangeList();
            case -8: return new AttributeDefList();
            case -7: return new TimeWindowList();
            case -6: return new ProductFilterList();
            case -5: return new FileMetadataList();
            case -4: return new ProductMetadataList();
            case -3: return new StandingOrderList();
            case -2: return new ProductTypeList();
            case -1: return new ProductList();
            case 1: return new Product();
            case 2: return new ProductType();
            case 3: return new StandingOrder();
            case 4: return new ProductMetadata();
            case 5: return new FileMetadata();
            case 6: return new ProductFilter();
            case 7: return new TimeWindow();
            case 8: return new AttributeDef();
            case 9: return new ValueRange();
            case 10: return new ValueSet();
            case 11: return new StringPattern();
            case 12: return new DeliveryMethodEnum();
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
