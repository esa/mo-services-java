package org.ccsds.moims.mo.malprototype.structures;

/**
 * List class for TestPublish.
 */
public final class TestPublishList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for TestPublishList.
     * 
     */
    public TestPublishList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof TestPublish)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: TestPublish");
        }
        return super.add(element);
    }

}
