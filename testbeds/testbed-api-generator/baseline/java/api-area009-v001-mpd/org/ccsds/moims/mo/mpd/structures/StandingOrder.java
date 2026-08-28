package org.ccsds.moims.mo.mpd.structures;

/**
 * A StandingOrder is a data structure that holds the details of a standing
 * order. This includes the orderID, the user who owns the product order,
 * the product filter, how the products are to be delivered, the target URI
 * where to deliver the files, and additional comments.
 */
public final class StandingOrder implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 2533274807173123L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173123L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The orderID of the standing order. It shall be NULL when the order is being
     * submitted.
     */
    private Long orderID;

    /**
     * The user that is the owner of the product order.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier user;

    /**
     * Specifies the filter criteria for the standing order, including the product
     * type and optional filters on product source and metadata attributes.
     */
    private org.ccsds.moims.mo.mpd.structures.ProductFilter productFilter;

    /**
     * Period of time over which the standing order is required to be active.
     */
    private org.ccsds.moims.mo.mpd.structures.TimeWindow validityPeriod;

    /**
     * Specifies how the product is to be delivered: via the service interface
     * or by file transfer.
     */
    private org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum deliveryMethod;

    /**
     * The delivery address for file transfer. Not required if deliveryMethod
     * is SERVICE.
     */
    private org.ccsds.moims.mo.mal.structures.URI deliverTo;

    /**
     * Any optional notes or comments.
     */
    private String comments;

    /**
     * Default constructor for StandingOrder.
     * 
     */
    public StandingOrder() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param orderID The orderID of the standing order. It shall be NULL when the order is being submitted.
     * @param user The user that is the owner of the product order.
     * @param productFilter Specifies the filter criteria for the standing order, including the product type and optional filters on product source and metadata attributes.
     * @param validityPeriod Period of time over which the standing order is required to be active.
     * @param deliveryMethod Specifies how the product is to be delivered: via the service interface or by file transfer.
     * @param deliverTo The delivery address for file transfer. Not required if deliveryMethod is SERVICE.
     * @param comments Any optional notes or comments.
     */
    public StandingOrder(Long orderID,
            org.ccsds.moims.mo.mal.structures.Identifier user,
            org.ccsds.moims.mo.mpd.structures.ProductFilter productFilter,
            org.ccsds.moims.mo.mpd.structures.TimeWindow validityPeriod,
            org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum deliveryMethod,
            org.ccsds.moims.mo.mal.structures.URI deliverTo,
            String comments) {
        this.orderID = orderID;
        this.user = user;
        this.productFilter = productFilter;
        this.validityPeriod = validityPeriod;
        this.deliveryMethod = deliveryMethod;
        this.deliverTo = deliverTo;
        this.comments = comments;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param user The user that is the owner of the product order.
     * @param deliveryMethod Specifies how the product is to be delivered: via the service interface or by file transfer.
     */
    public StandingOrder(org.ccsds.moims.mo.mal.structures.Identifier user,
            org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum deliveryMethod) {
        this.orderID = null;
        this.user = user;
        this.productFilter = null;
        this.validityPeriod = null;
        this.deliveryMethod = deliveryMethod;
        this.deliverTo = null;
        this.comments = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mpd.structures.StandingOrder();
    }

    /**
     * Returns the field orderID.
     * 
     * @return The field orderID
     */
    public Long getOrderID() {
        return orderID;
    }

    /**
     * Returns the field user.
     * 
     * @return The field user
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getUser() {
        return user;
    }

    /**
     * Returns the field productFilter.
     * 
     * @return The field productFilter
     */
    public org.ccsds.moims.mo.mpd.structures.ProductFilter getProductFilter() {
        return productFilter;
    }

    /**
     * Returns the field validityPeriod.
     * 
     * @return The field validityPeriod
     */
    public org.ccsds.moims.mo.mpd.structures.TimeWindow getValidityPeriod() {
        return validityPeriod;
    }

    /**
     * Returns the field deliveryMethod.
     * 
     * @return The field deliveryMethod
     */
    public org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum getDeliveryMethod() {
        return deliveryMethod;
    }

    /**
     * Returns the field deliverTo.
     * 
     * @return The field deliverTo
     */
    public org.ccsds.moims.mo.mal.structures.URI getDeliverTo() {
        return deliverTo;
    }

    /**
     * Returns the field comments.
     * 
     * @return The field comments
     */
    public String getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StandingOrder) {
            StandingOrder other = (StandingOrder) obj;
            if (orderID == null) {
                if (other.orderID != null) {
                    return false;
                }
            } else {
                if (! orderID.equals(other.orderID)) {
                    return false;
                }
            }
            if (user == null) {
                if (other.user != null) {
                    return false;
                }
            } else {
                if (! user.equals(other.user)) {
                    return false;
                }
            }
            if (productFilter == null) {
                if (other.productFilter != null) {
                    return false;
                }
            } else {
                if (! productFilter.equals(other.productFilter)) {
                    return false;
                }
            }
            if (validityPeriod == null) {
                if (other.validityPeriod != null) {
                    return false;
                }
            } else {
                if (! validityPeriod.equals(other.validityPeriod)) {
                    return false;
                }
            }
            if (deliveryMethod == null) {
                if (other.deliveryMethod != null) {
                    return false;
                }
            } else {
                if (! deliveryMethod.equals(other.deliveryMethod)) {
                    return false;
                }
            }
            if (deliverTo == null) {
                if (other.deliverTo != null) {
                    return false;
                }
            } else {
                if (! deliverTo.equals(other.deliverTo)) {
                    return false;
                }
            }
            if (comments == null) {
                if (other.comments != null) {
                    return false;
                }
            } else {
                if (! comments.equals(other.comments)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (orderID != null ? orderID.hashCode() : 0);
        hash = 83 * hash + (user != null ? user.hashCode() : 0);
        hash = 83 * hash + (productFilter != null ? productFilter.hashCode() : 0);
        hash = 83 * hash + (validityPeriod != null ? validityPeriod.hashCode() : 0);
        hash = 83 * hash + (deliveryMethod != null ? deliveryMethod.hashCode() : 0);
        hash = 83 * hash + (deliverTo != null ? deliverTo.hashCode() : 0);
        hash = 83 * hash + (comments != null ? comments.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StandingOrder: ");
        buf.append("orderID=").append(orderID);
        buf.append(", user=").append(user);
        buf.append(", productFilter=").append(productFilter);
        buf.append(", validityPeriod=").append(validityPeriod);
        buf.append(", deliveryMethod=").append(deliveryMethod);
        buf.append(", deliverTo=").append(deliverTo);
        buf.append(", comments=").append(comments);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (user == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'user' cannot be null!");
        }
        if (deliveryMethod == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'deliveryMethod' cannot be null!");
        }
        encoder.encodeNullableLong(orderID);
        encoder.encodeIdentifier(user);
        encoder.encodeNullableElement(productFilter);
        encoder.encodeNullableElement(validityPeriod);
        encoder.encodeElement(deliveryMethod);
        encoder.encodeNullableURI(deliverTo);
        encoder.encodeNullableString(comments);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        orderID = decoder.decodeNullableLong();
        user = decoder.decodeIdentifier();
        productFilter = (org.ccsds.moims.mo.mpd.structures.ProductFilter) decoder.decodeNullableElement(new org.ccsds.moims.mo.mpd.structures.ProductFilter());
        validityPeriod = (org.ccsds.moims.mo.mpd.structures.TimeWindow) decoder.decodeNullableElement(new org.ccsds.moims.mo.mpd.structures.TimeWindow());
        deliveryMethod = (org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum) decoder.decodeElement(org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum.SERVICE_COMPLETE);
        deliverTo = decoder.decodeNullableURI();
        comments = decoder.decodeNullableString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
