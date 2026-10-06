package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E1: Contains the information required to create one or more ActivityInstances,
 * including the specification of argument values and constraints. It should
 * be noted that the activityRef and activityOffset fields are only relevant
 * in the case that a Repetition has been specified in a parent ActivityNode.
 * Temporal and sequential constraints associated with the ActivityInstance
 * can be specified as constraints attached to a concrete SimpleActivityDetails
 * structure.
 */
public abstract class ActivityDetails implements Composite {

    /**
     * Specifies how the ActivityInstance is placed with respect to any defined
     * Repetition (0=Start; 1=End). Default is Start.
     */
    private Slider activityRef;

    /**
     * Specifies an offset in time for the ActivityInstance from any defined Repetition.
     * Default is no offset.
     */
    private Element activityOffset;

    /**
     * Specifies a related Event (or Event Group) for the ActivityInstance.  Argument
     * specifications and constraints may reference arguments and fields of the
     * RelatedEvent.
     */
    private Element relatedEvent;

    /**
     * Any notes associated with the ActivityDetails.
     */
    private String comments;

    /**
     * Default constructor for ActivityDetails.
     * 
     */
    public ActivityDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param activityRef Specifies how the ActivityInstance is placed with respect to any defined Repetition (0=Start; 1=End). Default is Start.
     * @param activityOffset Specifies an offset in time for the ActivityInstance from any defined Repetition. Default is no offset.
     * @param relatedEvent Specifies a related Event (or Event Group) for the ActivityInstance.  Argument specifications and constraints may reference arguments and fields of the RelatedEvent.
     * @param comments Any notes associated with the ActivityDetails.
     */
    public ActivityDetails(Slider activityRef,
            Element activityOffset,
            Element relatedEvent,
            String comments) {
        this.activityRef = activityRef;
        this.activityOffset = activityOffset;
        this.relatedEvent = relatedEvent;
        this.comments = comments;
    }

    /**
     * Returns the field activityRef.
     * 
     * @return The field activityRef
     */
    public Slider getActivityRef() {
        return activityRef;
    }

    /**
     * Returns the field activityOffset.
     * 
     * @return The field activityOffset
     */
    public Element getActivityOffset() {
        return activityOffset;
    }

    /**
     * Returns the field relatedEvent.
     * 
     * @return The field relatedEvent
     */
    public Element getRelatedEvent() {
        return relatedEvent;
    }

    /**
     * Returns the field comments.
     * 
     * @return The field comments
     */
    public String getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActivityDetails) {
            ActivityDetails other = (ActivityDetails) obj;
            if (activityRef == null) {
                if (other.activityRef != null) {
                    return false;
                }
            } else {
                if (! activityRef.equals(other.activityRef)) {
                    return false;
                }
            }
            if (activityOffset == null) {
                if (other.activityOffset != null) {
                    return false;
                }
            } else {
                if (! activityOffset.equals(other.activityOffset)) {
                    return false;
                }
            }
            if (relatedEvent == null) {
                if (other.relatedEvent != null) {
                    return false;
                }
            } else {
                if (! relatedEvent.equals(other.relatedEvent)) {
                    return false;
                }
            }
            if (comments == null) {
                if (other.comments != null) {
                    return false;
                }
            } else {
                if (! comments.equals(other.comments)) {
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
        hash = 83 * hash + (activityRef != null ? activityRef.hashCode() : 0);
        hash = 83 * hash + (activityOffset != null ? activityOffset.hashCode() : 0);
        hash = 83 * hash + (relatedEvent != null ? relatedEvent.hashCode() : 0);
        hash = 83 * hash + (comments != null ? comments.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActivityDetails: ");
        buf.append("activityRef=").append(activityRef);
        buf.append(", activityOffset=").append(activityOffset);
        buf.append(", relatedEvent=").append(relatedEvent);
        buf.append(", comments=").append(comments);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableElement(activityRef);
        encoder.encodeNullableAbstractElement(activityOffset);
        encoder.encodeNullableAbstractElement(relatedEvent);
        encoder.encodeNullableString(comments);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        activityRef = (Slider) decoder.decodeNullableElement(new Slider());
        activityOffset = (Element) decoder.decodeNullableAbstractElement();
        relatedEvent = (Element) decoder.decodeNullableAbstractElement();
        comments = decoder.decodeNullableString();
        return this;
    }

}
