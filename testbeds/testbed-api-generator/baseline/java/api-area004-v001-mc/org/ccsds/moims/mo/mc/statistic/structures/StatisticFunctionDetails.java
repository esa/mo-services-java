package org.ccsds.moims.mo.mc.statistic.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * The StatisticFunctionDetails structure holds the details of the function.
 */
public final class StatisticFunctionDetails implements Composite {

    private static final long serialVersionUID = 1125921398456321L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125921398456321L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The name of the statistical function.
     */
    private Identifier name;

    /**
     * The description of the statistical function.
     */
    private String description;

    /**
     * Default constructor for StatisticFunctionDetails.
     * 
     */
    public StatisticFunctionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The name of the statistical function.
     * @param description The description of the statistical function.
     */
    public StatisticFunctionDetails(Identifier name,
            String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public Element createElement() {
        return new StatisticFunctionDetails();
    }

    /**
     * Returns the field name.
     * 
     * @return The field name
     */
    public Identifier getName() {
        return name;
    }

    /**
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatisticFunctionDetails) {
            StatisticFunctionDetails other = (StatisticFunctionDetails) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else {
                if (! name.equals(other.name)) {
                    return false;
                }
            }
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
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
        hash = 83 * hash + (name != null ? name.hashCode() : 0);
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StatisticFunctionDetails: ");
        buf.append("name=").append(name);
        buf.append(", description=").append(description);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (name == null) {
            throw new MALException("The field 'name' cannot be null!");
        }
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeString(description);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        name = decoder.decodeIdentifier();
        description = decoder.decodeString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
