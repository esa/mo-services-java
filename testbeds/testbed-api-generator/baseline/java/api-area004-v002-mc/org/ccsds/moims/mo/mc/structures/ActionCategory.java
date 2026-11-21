package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for ActionCategory.
 */
public final class ActionCategory extends Enumeration {

    private static final long serialVersionUID = 1125899940397066L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397066L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for DEFAULT.
     */
    public static final int DEFAULT_VALUE = 1;

    /**
     * Enumeration singleton for value DEFAULT.
     */
    public static final ActionCategory DEFAULT = new ActionCategory(ActionCategory.DEFAULT_VALUE);

    /**
     * Enumeration value for HIPRIORITY.
     */
    public static final int HIPRIORITY_VALUE = 2;

    /**
     * Enumeration singleton for value HIPRIORITY.
     */
    public static final ActionCategory HIPRIORITY = new ActionCategory(ActionCategory.HIPRIORITY_VALUE);

    /**
     * Enumeration value for CRITICAL.
     */
    public static final int CRITICAL_VALUE = 3;

    /**
     * Enumeration singleton for value CRITICAL.
     */
    public static final ActionCategory CRITICAL = new ActionCategory(ActionCategory.CRITICAL_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final ActionCategory[] _ENUMERATIONS = {
        DEFAULT, HIPRIORITY, CRITICAL};

    /**
     * Contains the default Action category values.
     */
    public ActionCategory() {
        super(-1);
    }

    /**
     * Contains the default Action category values.
     * 
     * @param value The value of the Enumeration.
     */
    public ActionCategory(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case DEFAULT_VALUE:
                return "DEFAULT";
            case HIPRIORITY_VALUE:
                return "HIPRIORITY";
            case CRITICAL_VALUE:
                return "CRITICAL";
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
    public static ActionCategory fromString(String s) {
        switch (s) {
            case "DEFAULT":
                return ActionCategory.DEFAULT;
            case "HIPRIORITY":
                return ActionCategory.HIPRIORITY;
            case "CRITICAL":
                return ActionCategory.CRITICAL;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case DEFAULT_VALUE:
                return ActionCategory.DEFAULT;
            case HIPRIORITY_VALUE:
                return ActionCategory.HIPRIORITY;
            case CRITICAL_VALUE:
                return ActionCategory.CRITICAL;
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
