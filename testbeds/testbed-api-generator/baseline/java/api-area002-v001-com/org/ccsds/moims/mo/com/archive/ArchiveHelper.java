package org.ccsds.moims.mo.com.archive;

/**
 * Helper class for Archive service.
 */
public class ArchiveHelper {

    /**
     * Service singleton instance.
     */
    public static final org.ccsds.moims.mo.com.archive.ArchiveServiceInfo ARCHIVE_SERVICE = new org.ccsds.moims.mo.com.archive.ArchiveServiceInfo();

    private ArchiveHelper() {
        // Utility class; not meant to be instantiated.
    }

}
