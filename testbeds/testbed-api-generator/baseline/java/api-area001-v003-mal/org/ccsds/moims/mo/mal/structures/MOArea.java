package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.TypeId;

/**
 * Enumeration class for MOArea.
 */
public final class MOArea extends Enumeration {

    private static final long serialVersionUID = 281475027042409L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027042409L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for MAL.
     */
    public static final int MAL_VALUE = 1;

    /**
     * Enumeration singleton for value MAL.
     */
    public static final MOArea MAL = new MOArea(MOArea.MAL_VALUE);

    /**
     * Enumeration value for COM.
     */
    public static final int COM_VALUE = 2;

    /**
     * Enumeration singleton for value COM.
     */
    public static final MOArea COM = new MOArea(MOArea.COM_VALUE);

    /**
     * Enumeration value for COMMON.
     */
    public static final int COMMON_VALUE = 3;

    /**
     * Enumeration singleton for value COMMON.
     */
    public static final MOArea COMMON = new MOArea(MOArea.COMMON_VALUE);

    /**
     * Enumeration value for MC.
     */
    public static final int MC_VALUE = 4;

    /**
     * Enumeration singleton for value MC.
     */
    public static final MOArea MC = new MOArea(MOArea.MC_VALUE);

    /**
     * Enumeration value for MPS.
     */
    public static final int MPS_VALUE = 5;

    /**
     * Enumeration singleton for value MPS.
     */
    public static final MOArea MPS = new MOArea(MOArea.MPS_VALUE);

    /**
     * Enumeration value for SM.
     */
    public static final int SM_VALUE = 7;

    /**
     * Enumeration singleton for value SM.
     */
    public static final MOArea SM = new MOArea(MOArea.SM_VALUE);

    /**
     * Enumeration value for MDPD.
     */
    public static final int MDPD_VALUE = 9;

    /**
     * Enumeration singleton for value MDPD.
     */
    public static final MOArea MDPD = new MOArea(MOArea.MDPD_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final MOArea[] _ENUMERATIONS = {
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
    public static MOArea fromString(String s) {
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
    public Enumeration fromValue(Integer value) {
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
    public Element createElement() {
        return _ENUMERATIONS[0];
    }

    @Override
    public int getEnumSize() {
        return 7;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
