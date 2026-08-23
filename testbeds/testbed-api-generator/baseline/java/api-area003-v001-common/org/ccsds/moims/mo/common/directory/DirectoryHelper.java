package org.ccsds.moims.mo.common.directory;

/**
 * Helper class for Directory service.
 */
public class DirectoryHelper {

    /**
     * Service singleton instance.
     */
    public static final org.ccsds.moims.mo.common.directory.DirectoryServiceInfo DIRECTORY_SERVICE = new org.ccsds.moims.mo.common.directory.DirectoryServiceInfo();

    private DirectoryHelper() {
        // Utility class; not meant to be instantiated.
    }

}
