package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for TestPublish.
 */
public final class TestPublishList extends HeterogeneousList {

    /**
     * Default constructor for TestPublishList.
     * 
     */
    public TestPublishList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof TestPublish)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: TestPublish");
        }
        return super.add(element);
    }

}
