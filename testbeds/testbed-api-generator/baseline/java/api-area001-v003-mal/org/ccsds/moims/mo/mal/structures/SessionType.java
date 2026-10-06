package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.TypeId;

/**
 * Enumeration class for SessionType.
 */
public final class SessionType extends Enumeration {

    private static final long serialVersionUID = 281475027042406L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027042406L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for LIVE.
     */
    public static final int LIVE_VALUE = 1;

    /**
     * Enumeration singleton for value LIVE.
     */
    public static final SessionType LIVE = new SessionType(SessionType.LIVE_VALUE);

    /**
     * Enumeration value for SIMULATION.
     */
    public static final int SIMULATION_VALUE = 2;

    /**
     * Enumeration singleton for value SIMULATION.
     */
    public static final SessionType SIMULATION = new SessionType(SessionType.SIMULATION_VALUE);

    /**
     * Enumeration value for REPLAY.
     */
    public static final int REPLAY_VALUE = 3;

    /**
     * Enumeration singleton for value REPLAY.
     */
    public static final SessionType REPLAY = new SessionType(SessionType.REPLAY_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final SessionType[] _ENUMERATIONS = {
        LIVE, SIMULATION, REPLAY};

    /**
     * SessionType is an enumeration that shall be used to hold the session types. This facilitates the use of different Sessions in out-of-band agreements.
     */
    public SessionType() {
        super(-1);
    }

    /**
     * SessionType is an enumeration that shall be used to hold the session types.
     * This facilitates the use of different Sessions in out-of-band agreements.
     * 
     * @param value The value of the Enumeration.
     */
    public SessionType(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case LIVE_VALUE:
                return "LIVE";
            case SIMULATION_VALUE:
                return "SIMULATION";
            case REPLAY_VALUE:
                return "REPLAY";
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
    public static SessionType fromString(String s) {
        switch (s) {
            case "LIVE":
                return SessionType.LIVE;
            case "SIMULATION":
                return SessionType.SIMULATION;
            case "REPLAY":
                return SessionType.REPLAY;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case LIVE_VALUE:
                return SessionType.LIVE;
            case SIMULATION_VALUE:
                return SessionType.SIMULATION;
            case REPLAY_VALUE:
                return SessionType.REPLAY;
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
