package org.ccsds.moims.mo.common.configuration.structures;

/**
 * List class for ConfigurationType.
 */
public final class ConfigurationTypeList extends java.util.ArrayList<org.ccsds.moims.mo.common.configuration.structures.ConfigurationType> implements org.ccsds.moims.mo.mal.structures.HomogeneousList<org.ccsds.moims.mo.common.configuration.structures.ConfigurationType> {

    private static final long serialVersionUID = 844446438522876L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844446438522876L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Default constructor for ConfigurationTypeList.
     * 
     */
    public ConfigurationTypeList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public ConfigurationTypeList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public ConfigurationTypeList(java.util.ArrayList<org.ccsds.moims.mo.common.configuration.structures.ConfigurationType> elementList) {
        for(org.ccsds.moims.mo.common.configuration.structures.ConfigurationType element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(org.ccsds.moims.mo.common.configuration.structures.ConfigurationType element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new ConfigurationTypeList();
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createTypedElement() {
        return org.ccsds.moims.mo.common.configuration.structures.ConfigurationType.PROVIDER;
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeHomogeneousList(this);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        decoder.decodeHomogeneousList(this);
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
