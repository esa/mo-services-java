package org.ccsds.moims.mo.mps.structures;

/**
 * Enumeration class for RevisionStatusEnum.
 */
public final class RevisionStatusEnum extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 1407374900331003L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331003L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for NEW.
     */
    public static final int NEW_VALUE = 1;

    /**
     * Enumeration singleton for value NEW.
     */
    public static final org.ccsds.moims.mo.mps.structures.RevisionStatusEnum NEW = new org.ccsds.moims.mo.mps.structures.RevisionStatusEnum(org.ccsds.moims.mo.mps.structures.RevisionStatusEnum.NEW_VALUE);

    /**
     * Enumeration value for MODIFIED.
     */
    public static final int MODIFIED_VALUE = 2;

    /**
     * Enumeration singleton for value MODIFIED.
     */
    public static final org.ccsds.moims.mo.mps.structures.RevisionStatusEnum MODIFIED = new org.ccsds.moims.mo.mps.structures.RevisionStatusEnum(org.ccsds.moims.mo.mps.structures.RevisionStatusEnum.MODIFIED_VALUE);

    /**
     * Enumeration value for DELETED.
     */
    public static final int DELETED_VALUE = 3;

    /**
     * Enumeration singleton for value DELETED.
     */
    public static final org.ccsds.moims.mo.mps.structures.RevisionStatusEnum DELETED = new org.ccsds.moims.mo.mps.structures.RevisionStatusEnum(org.ccsds.moims.mo.mps.structures.RevisionStatusEnum.DELETED_VALUE);

    /**
     * Enumeration value for UNDEFINED.
     */
    public static final int UNDEFINED_VALUE = 4;

    /**
     * Enumeration singleton for value UNDEFINED.
     */
    public static final org.ccsds.moims.mo.mps.structures.RevisionStatusEnum UNDEFINED = new org.ccsds.moims.mo.mps.structures.RevisionStatusEnum(org.ccsds.moims.mo.mps.structures.RevisionStatusEnum.UNDEFINED_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mps.structures.RevisionStatusEnum[] _ENUMERATIONS = {
        NEW, MODIFIED, DELETED, UNDEFINED};

    /**
     * E3: The RevisionStatusEnum represents the type of changes that were made to an item in a given revision.
     */
    public RevisionStatusEnum() {
        super(-1);
    }

    /**
     * E3: The RevisionStatusEnum represents the type of changes that were made
     * to an item in a given revision.
     * 
     * @param value The value of the Enumeration.
     */
    public RevisionStatusEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case NEW_VALUE:
                return "NEW";
            case MODIFIED_VALUE:
                return "MODIFIED";
            case DELETED_VALUE:
                return "DELETED";
            case UNDEFINED_VALUE:
                return "UNDEFINED";
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
    public static org.ccsds.moims.mo.mps.structures.RevisionStatusEnum fromString(String s) {
        switch (s) {
            case "NEW":
                return RevisionStatusEnum.NEW;
            case "MODIFIED":
                return RevisionStatusEnum.MODIFIED;
            case "DELETED":
                return RevisionStatusEnum.DELETED;
            case "UNDEFINED":
                return RevisionStatusEnum.UNDEFINED;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case NEW_VALUE:
                return RevisionStatusEnum.NEW;
            case MODIFIED_VALUE:
                return RevisionStatusEnum.MODIFIED;
            case DELETED_VALUE:
                return RevisionStatusEnum.DELETED;
            case UNDEFINED_VALUE:
                return RevisionStatusEnum.UNDEFINED;
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
        return 4;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
