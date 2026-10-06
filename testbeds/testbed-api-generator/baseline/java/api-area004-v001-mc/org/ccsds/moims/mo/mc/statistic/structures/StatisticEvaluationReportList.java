package org.ccsds.moims.mo.mc.statistic.structures;

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HomogeneousList;

/**
 * List class for StatisticEvaluationReport.
 */
public final class StatisticEvaluationReportList extends ArrayList<StatisticEvaluationReport> implements HomogeneousList<StatisticEvaluationReport> {

    private static final long serialVersionUID = 1125921415233530L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125921415233530L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for StatisticEvaluationReportList.
     * 
     */
    public StatisticEvaluationReportList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public StatisticEvaluationReportList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public StatisticEvaluationReportList(ArrayList<StatisticEvaluationReport> elementList) {
        for(StatisticEvaluationReport element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(StatisticEvaluationReport element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public Element createElement() {
        return new StatisticEvaluationReportList();
    }

    @Override
    public Element createTypedElement() {
        return new StatisticEvaluationReport();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeHomogeneousList(this);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        decoder.decodeHomogeneousList(this);
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
