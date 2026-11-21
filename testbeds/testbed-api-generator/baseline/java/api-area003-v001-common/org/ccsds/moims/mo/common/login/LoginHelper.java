package org.ccsds.moims.mo.common.login;

/**
 * Helper class for Login service.
 */
public class LoginHelper {

    /**
     * Service singleton instance.
     */
    public static final LoginServiceInfo LOGIN_SERVICE = new LoginServiceInfo();

    private LoginHelper() {
        // Utility class; not meant to be instantiated.
    }

}
