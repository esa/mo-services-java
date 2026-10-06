package org.ccsds.moims.mo.common.directory;

/**
 * Helper class for Directory service.
 */
public class DirectoryHelper {

    /**
     * Service singleton instance.
     */
    public static final DirectoryServiceInfo DIRECTORY_SERVICE = new DirectoryServiceInfo();

    private DirectoryHelper() {
        // Utility class; not meant to be instantiated.
    }

}
