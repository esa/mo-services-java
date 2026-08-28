package org.ccsds.moims.mo.mc.conversion.provider;

/**
 * Interface that providers of the Conversion service must implement to handle
 * the operations of that service.
 */
public interface ConversionHandler {

    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mc.conversion.provider.ConversionSkeleton skeleton);
}
