package org.ccsds.moims.mo.comprototype2.test2.provider;

/**
 * Interface that providers of the Test2 service must implement to handle
 * the operations of that service.
 */
public interface Test2Handler {

    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.comprototype2.test2.provider.Test2Skeleton skeleton);
}
