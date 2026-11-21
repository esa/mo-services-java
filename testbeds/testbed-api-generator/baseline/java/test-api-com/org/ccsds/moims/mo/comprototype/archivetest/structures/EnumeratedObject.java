package org.ccsds.moims.mo.comprototype.archivetest.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for EnumeratedObject.
 */
public final class EnumeratedObject extends Enumeration {

    private static final long serialVersionUID = 56295021128712195L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295021128712195L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for OBJECT1.
     */
    public static final int OBJECT1_VALUE = 1;

    /**
     * Enumeration singleton for value OBJECT1.
     */
    public static final EnumeratedObject OBJECT1 = new EnumeratedObject(EnumeratedObject.OBJECT1_VALUE);

    /**
     * Enumeration value for OBJECT2.
     */
    public static final int OBJECT2_VALUE = 2;

    /**
     * Enumeration singleton for value OBJECT2.
     */
    public static final EnumeratedObject OBJECT2 = new EnumeratedObject(EnumeratedObject.OBJECT2_VALUE);

    /**
     * Enumeration value for OBJECT3.
     */
    public static final int OBJECT3_VALUE = 3;

    /**
     * Enumeration singleton for value OBJECT3.
     */
    public static final EnumeratedObject OBJECT3 = new EnumeratedObject(EnumeratedObject.OBJECT3_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final EnumeratedObject[] _ENUMERATIONS = {
        OBJECT1, OBJECT2, OBJECT3};

    /**
     * 
     */
    public EnumeratedObject() {
        super(-1);
    }

    /**
     * Creates an instance of the EnumeratedObject Enumeration.
     * 
     * @param value The value of the Enumeration.
     */
    public EnumeratedObject(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case OBJECT1_VALUE:
                return "OBJECT1";
            case OBJECT2_VALUE:
                return "OBJECT2";
            case OBJECT3_VALUE:
                return "OBJECT3";
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
    public static EnumeratedObject fromString(String s) {
        switch (s) {
            case "OBJECT1":
                return EnumeratedObject.OBJECT1;
            case "OBJECT2":
                return EnumeratedObject.OBJECT2;
            case "OBJECT3":
                return EnumeratedObject.OBJECT3;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case OBJECT1_VALUE:
                return EnumeratedObject.OBJECT1;
            case OBJECT2_VALUE:
                return EnumeratedObject.OBJECT2;
            case OBJECT3_VALUE:
                return EnumeratedObject.OBJECT3;
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
        return 3;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
