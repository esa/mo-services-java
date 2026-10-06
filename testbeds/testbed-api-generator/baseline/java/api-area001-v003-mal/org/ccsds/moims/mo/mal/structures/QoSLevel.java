package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.TypeId;

/**
 * Enumeration class for QoSLevel.
 */
public final class QoSLevel extends Enumeration {

    private static final long serialVersionUID = 281475027042407L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027042407L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for BESTEFFORT.
     */
    public static final int BESTEFFORT_VALUE = 1;

    /**
     * Enumeration singleton for value BESTEFFORT.
     */
    public static final QoSLevel BESTEFFORT = new QoSLevel(QoSLevel.BESTEFFORT_VALUE);

    /**
     * Enumeration value for ASSURED.
     */
    public static final int ASSURED_VALUE = 2;

    /**
     * Enumeration singleton for value ASSURED.
     */
    public static final QoSLevel ASSURED = new QoSLevel(QoSLevel.ASSURED_VALUE);

    /**
     * Enumeration value for QUEUED.
     */
    public static final int QUEUED_VALUE = 3;

    /**
     * Enumeration singleton for value QUEUED.
     */
    public static final QoSLevel QUEUED = new QoSLevel(QoSLevel.QUEUED_VALUE);

    /**
     * Enumeration value for TIMELY.
     */
    public static final int TIMELY_VALUE = 4;

    /**
     * Enumeration singleton for value TIMELY.
     */
    public static final QoSLevel TIMELY = new QoSLevel(QoSLevel.TIMELY_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final QoSLevel[] _ENUMERATIONS = {
        BESTEFFORT, ASSURED, QUEUED, TIMELY};

    /**
     * QoSLevel is an enumeration that shall be used to hold the possible QoS levels. This facilitates the use of different QoS in out-of-band agreements.
     */
    public QoSLevel() {
        super(-1);
    }

    /**
     * QoSLevel is an enumeration that shall be used to hold the possible QoS
     * levels. This facilitates the use of different QoS in out-of-band agreements.
     * 
     * @param value The value of the Enumeration.
     */
    public QoSLevel(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case BESTEFFORT_VALUE:
                return "BESTEFFORT";
            case ASSURED_VALUE:
                return "ASSURED";
            case QUEUED_VALUE:
                return "QUEUED";
            case TIMELY_VALUE:
                return "TIMELY";
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
    public static QoSLevel fromString(String s) {
        switch (s) {
            case "BESTEFFORT":
                return QoSLevel.BESTEFFORT;
            case "ASSURED":
                return QoSLevel.ASSURED;
            case "QUEUED":
                return QoSLevel.QUEUED;
            case "TIMELY":
                return QoSLevel.TIMELY;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case BESTEFFORT_VALUE:
                return QoSLevel.BESTEFFORT;
            case ASSURED_VALUE:
                return QoSLevel.ASSURED;
            case QUEUED_VALUE:
                return QoSLevel.QUEUED;
            case TIMELY_VALUE:
                return QoSLevel.TIMELY;
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
        return 4;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
