package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E1: Specifically in the case of reporting the detailed execution status
 * of a plan, updates may be reported for multiple object types: planning
 * activities, planning events, and planning resources.  To support this an
 * abstract type of PlanDetailUpdate is defined as follows.
 */
public abstract class PlanDetailUpdate implements Composite {

    /**
     * Default constructor for PlanDetailUpdate.
     * 
     */
    public PlanDetailUpdate() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlanDetailUpdate) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PlanDetailUpdate: ");
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        return this;
    }

}
