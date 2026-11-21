package org.ccsds.moims.mo.comprototype1.test1.provider;

/**
 * Interface that providers of the Test1 service must implement to handle
 * the operations of that service.
 */
public interface Test1Handler {

    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(Test1Skeleton skeleton);
}
