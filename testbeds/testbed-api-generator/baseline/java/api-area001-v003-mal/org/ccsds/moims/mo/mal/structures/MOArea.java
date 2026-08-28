package org.ccsds.moims.mo.mal.structures;

/**
 * Enumeration class for MOArea.
 */
public final class MOArea extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 281475027042409L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027042409L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for MAL.
     */
    public static final int MAL_VALUE = 1;

    /**
     * Enumeration singleton for value MAL.
     */
    public static final org.ccsds.moims.mo.mal.structures.MOArea MAL = new org.ccsds.moims.mo.mal.structures.MOArea(org.ccsds.moims.mo.mal.structures.MOArea.MAL_VALUE);

    /**
     * Enumeration value for COM.
     */
    public static final int COM_VALUE = 2;

    /**
     * Enumeration singleton for value COM.
     */
    public static final org.ccsds.moims.mo.mal.structures.MOArea COM = new org.ccsds.moims.mo.mal.structures.MOArea(org.ccsds.moims.mo.mal.structures.MOArea.COM_VALUE);

    /**
     * Enumeration value for COMMON.
     */
    public static final int COMMON_VALUE = 3;

    /**
     * Enumeration singleton for value COMMON.
     */
    public static final org.ccsds.moims.mo.mal.structures.MOArea COMMON = new org.ccsds.moims.mo.mal.structures.MOArea(org.ccsds.moims.mo.mal.structures.MOArea.COMMON_VALUE);

    /**
     * Enumeration value for MC.
     */
    public static final int MC_VALUE = 4;

    /**
     * Enumeration singleton for value MC.
     */
    public static final org.ccsds.moims.mo.mal.structures.MOArea MC = new org.ccsds.moims.mo.mal.structures.MOArea(org.ccsds.moims.mo.mal.structures.MOArea.MC_VALUE);

    /**
     * Enumeration value for MPS.
     */
    public static final int MPS_VALUE = 5;

    /**
     * Enumeration singleton for value MPS.
     */
    public static final org.ccsds.moims.mo.mal.structures.MOArea MPS = new org.ccsds.moims.mo.mal.structures.MOArea(org.ccsds.moims.mo.mal.structures.MOArea.MPS_VALUE);

    /**
     * Enumeration value for SM.
     */
    public static final int SM_VALUE = 7;

    /**
     * Enumeration singleton for value SM.
     */
    public static final org.ccsds.moims.mo.mal.structures.MOArea SM = new org.ccsds.moims.mo.mal.structures.MOArea(org.ccsds.moims.mo.mal.structures.MOArea.SM_VALUE);

    /**
     * Enumeration value for MDPD.
     */
    public static final int MDPD_VALUE = 9;

    /**
     * Enumeration singleton for value MDPD.
     */
    public static final org.ccsds.moims.mo.mal.structures.MOArea MDPD = new org.ccsds.moims.mo.mal.structures.MOArea(org.ccsds.moims.mo.mal.structures.MOArea.MDPD_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mal.structures.MOArea[] _ENUMERATIONS = {
        MAL, COM, COMMON, MC, MPS, SM, MDPD};

    /**
     * MOArea is an enumeration that shall be used to hold the known existing area numbers in use.
     */
    public MOArea() {
        super(-1);
    }

    /**
     * MOArea is an enumeration that shall be used to hold the known existing
     * area numbers in use.
     * 
     * @param value The value of the Enumeration.
     */
    public MOArea(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case MAL_VALUE:
                return "MAL";
            case COM_VALUE:
                return "COM";
            case COMMON_VALUE:
                return "COMMON";
            case MC_VALUE:
                return "MC";
            case MPS_VALUE:
                return "MPS";
            case SM_VALUE:
                return "SM";
            case MDPD_VALUE:
                return "MDPD";
            default:
                throw new RuntimeException("Unknown ordinal!");
        }
    }

    /**
     * Returns the enumeration element represented by the supplied string, or
     * null if not matched.
     * 
     * @param s s The string to search for.
     * @return The matched enumeration element, or null if not matched.
     */
    public static org.ccsds.moims.mo.mal.structures.MOArea fromString(String s) {
        switch (s) {
            case "MAL":
                return MOArea.MAL;
            case "COM":
                return MOArea.COM;
            case "COMMON":
                return MOArea.COMMON;
            case "MC":
                return MOArea.MC;
            case "MPS":
                return MOArea.MPS;
            case "SM":
                return MOArea.SM;
            case "MDPD":
                return MOArea.MDPD;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case MAL_VALUE:
                return MOArea.MAL;
            case COM_VALUE:
                return MOArea.COM;
            case COMMON_VALUE:
                return MOArea.COMMON;
            case MC_VALUE:
                return MOArea.MC;
            case MPS_VALUE:
                return MOArea.MPS;
            case SM_VALUE:
                return MOArea.SM;
            case MDPD_VALUE:
                return MOArea.MDPD;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided value: " + value);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return _ENUMERATIONS[0];
    }

    @Override
    public int getEnumSize() {
        return 7;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
