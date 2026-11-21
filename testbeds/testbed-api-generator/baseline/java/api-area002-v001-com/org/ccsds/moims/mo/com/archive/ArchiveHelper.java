package org.ccsds.moims.mo.com.archive;

/**
 * Helper class for Archive service.
 */
public class ArchiveHelper {

    /**
     * Service singleton instance.
     */
    public static final ArchiveServiceInfo ARCHIVE_SERVICE = new ArchiveServiceInfo();

    private ArchiveHelper() {
        // Utility class; not meant to be instantiated.
    }

}
