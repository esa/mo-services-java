package org.ccsds.moims.mo.malprototype.structures;

/**
 * List class for TestObjectBase.
 */
public final class TestObjectBaseList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for TestObjectBaseList.
     * 
     */
    public TestObjectBaseList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof TestObjectBase)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: TestObjectBase");
        }
        return super.add(element);
    }

}
