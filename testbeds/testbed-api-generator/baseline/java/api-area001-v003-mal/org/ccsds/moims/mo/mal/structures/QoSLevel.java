package org.ccsds.moims.mo.mal.structures;

/**
 * Enumeration class for QoSLevel.
 */
public final class QoSLevel extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 281475027042407L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027042407L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for BESTEFFORT.
     */
    public static final int BESTEFFORT_VALUE = 1;

    /**
     * Enumeration singleton for value BESTEFFORT.
     */
    public static final org.ccsds.moims.mo.mal.structures.QoSLevel BESTEFFORT = new org.ccsds.moims.mo.mal.structures.QoSLevel(org.ccsds.moims.mo.mal.structures.QoSLevel.BESTEFFORT_VALUE);

    /**
     * Enumeration value for ASSURED.
     */
    public static final int ASSURED_VALUE = 2;

    /**
     * Enumeration singleton for value ASSURED.
     */
    public static final org.ccsds.moims.mo.mal.structures.QoSLevel ASSURED = new org.ccsds.moims.mo.mal.structures.QoSLevel(org.ccsds.moims.mo.mal.structures.QoSLevel.ASSURED_VALUE);

    /**
     * Enumeration value for QUEUED.
     */
    public static final int QUEUED_VALUE = 3;

    /**
     * Enumeration singleton for value QUEUED.
     */
    public static final org.ccsds.moims.mo.mal.structures.QoSLevel QUEUED = new org.ccsds.moims.mo.mal.structures.QoSLevel(org.ccsds.moims.mo.mal.structures.QoSLevel.QUEUED_VALUE);

    /**
     * Enumeration value for TIMELY.
     */
    public static final int TIMELY_VALUE = 4;

    /**
     * Enumeration singleton for value TIMELY.
     */
    public static final org.ccsds.moims.mo.mal.structures.QoSLevel TIMELY = new org.ccsds.moims.mo.mal.structures.QoSLevel(org.ccsds.moims.mo.mal.structures.QoSLevel.TIMELY_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mal.structures.QoSLevel[] _ENUMERATIONS = {
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
    public static org.ccsds.moims.mo.mal.structures.QoSLevel fromString(String s) {
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
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
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
