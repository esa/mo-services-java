/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Test bed
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.com.test.util;

import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 * provides general support functions such as value checking
 */
public class COMChecker {

    /**
     * Checks an object is not null. Logs a message if it is not
     *
     * @param strSrc Object or class which is the source of check.
     * @param strCheck Check description
     * @param obj object to be checked
     * @param bRunningValidity Overall validity status prior to this check
     * @ret Overall validity status after this check - true if bRunningValidity
     * is valid and the validity check is valid.
     */
    public static boolean nullCheck(String strSrc, String strCheck, Object obj,
            boolean bRunningValidity) {
        boolean bValid = (obj != null);

        if (!bValid) {
            LoggingBase.logMessage("FAILURE:" + strSrc + strCheck + " Is NULL");
        }
        return bValid && bRunningValidity;
    }

    /**
     * Checks two UShort values are equal. Logs a message if they are not
     *
     * @param strSrc Object or class which is the source of check.
     * @param strCheck Check description
     * @param val Value to be checked
     * @param expVal Expected value
     * @param bRunningValidity Overall validity status prior to this check
     * @ret Overall validity status after this check - true if bRunningValidity
     * is valid and the validity check is valid.
     */
    public static boolean equalsCheck(String strSrc, String strCheck, UOctet val,
            UOctet expVal, boolean bRunningValidity) {
        boolean bValid = false;

        if (val == null) {
            bValid = (expVal == null);
            if (!bValid) {
                LoggingBase.logMessage("FAILURE:" + strSrc + " " + strCheck
                        + " Val is NULL ");
            }
        } else {
            bValid = (val.getValue() == expVal.getValue());
            if (!bValid) {
                LoggingBase.logMessage("FAILURE:" + strSrc + " " + strCheck
                        + " Val = " + val.getValue() + " Exp = " + expVal.getValue());
            }
        }

        return bRunningValidity && bValid;
    }

    /**
     * Checks two UShort values are equal. Logs a message if they are not
     *
     * @param strSrc Object or class which is the source of check.
     * @param strCheck Check description
     * @param val Value to be checked
     * @param expVal Expected value
     * @param bRunningValidity Overall validity status prior to this check
     * @ret Overall validity status after this check - true if bRunningValidity
     * is valid and the validity check is valid.
     */
    public static boolean equalsCheck(String strSrc, String strCheck, UShort val,
            UShort expVal, boolean bRunningValidity) {
        boolean bValid = false;

        if (val == null) {
            bValid = (expVal == null);
            if (!bValid) {
                LoggingBase.logMessage("FAILURE:" + strSrc + " " + strCheck
                        + " Val is NULL ");
            }
        } else {
            bValid = (val.getValue() == expVal.getValue());
            if (!bValid) {
                LoggingBase.logMessage("FAILURE:" + strSrc + " " + strCheck
                        + " Val = " + val.getValue() + " Exp = " + expVal.getValue());
            }
        }

        return bRunningValidity && bValid;
    }

    /**
     * Checks two double values are equal. Logs a message if they are not
     *
     * @param strSrc Object or class which is the source of check.
     * @param strCheck Check description
     * @param dVal Value to be checked
     * @param dExpVal Expected value
     * @param bRunningValidity Overall validity status prior to this check
     * @ret Overall validity status after this check - true if bRunningValidity
     * is valid and the validity check is valid.
     */
    public static boolean equalsCheck(String strSrc, String strCheck, double dVal,
            double dExpVal, boolean bRunningValidity) {
        boolean bValid = (dVal == dExpVal);
        if (!bValid) {
            LoggingBase.logMessage("FAILURE:" + strSrc + " " + strCheck
                    + " Val = " + dVal + " Exp = " + dExpVal);
        }
        return bRunningValidity && bValid;
    }

    /**
     * Checks two integer values are equal. Logs a message if they are not
     *
     * @param strSrc Object or class which is the source of check.
     * @param strCheck Check description
     * @param iVal Value to be checked
     * @param iExpVal Expected value
     * @param bRunningValidity Overall validity status prior to this check
     * @ret Overall validity status after this check - true if bRunningValidity
     * is valid and the validity check is valid.
     */
    public static boolean equalsCheck(String strSrc, String strCheck, int iVal,
            int iExpVal, boolean bRunningValidity) {
        boolean bValid = (iVal == iExpVal);
        if (!bValid) {
            LoggingBase.logMessage("FAILURE:" + strSrc + " " + strCheck
                    + " Val = " + iVal + " Exp = " + iExpVal);
        }
        return bRunningValidity && bValid;
    }

    /**
     * Checks two long values are equal. Logs a message if they are not
     *
     * @param strSrc Object or class which is the source of check.
     * @param strCheck Check description
     * @param lVal Value to be checked
     * @param lExpVal Expected value
     * @param bRunningValidity Overall validity status prior to this check
     * @ret Overall validity status after this check - true if bRunningValidity
     * is valid and the validity check is valid.
     */
    public static boolean equalsCheck(String strSrc, String strCheck, long lVal,
            long lExpVal, boolean bRunningValidity) {
        boolean bValid = (lVal == lExpVal);
        if (!bValid) {
            LoggingBase.logMessage("FAILURE:" + strSrc + " " + strCheck
                    + " Val = " + lVal + " Exp = " + lExpVal);
        }
        return bRunningValidity && bValid;
    }

    /**
     * Checks two string values are equal. Logs a message if they are not
     *
     * @param strObj Object or class which is the source of check.
     * @param strCheck Check description
     * @param strVal Value to be checked
     * @param strExpVal Expected value
     * @param bRunningValidity Overall validity status prior to this check
     * @ret Overall validity status after this check - true if bRunningValidity
     * is valid and the validity check is valid.
     */
    public static boolean equalsCheck(String strObj, String strCheck, String strVal, String strExpVal,
            boolean bRunningValidity) {
        boolean bValid;
        if (strExpVal == null) {
            bValid = (strVal == null);
        } else {
            bValid = strExpVal.equals(strVal);
        }

        if (!bValid) {
            LoggingBase.logMessage("FAILURE:" + strObj + " " + strCheck
                    + " Val = " + strVal + " Exp = " + strExpVal);
        }
        return bRunningValidity && bValid;
    }

    public static boolean timeCheck(String strObj, String strCheck, Time val,
            Time min, Time max, boolean bRunningValidity) {
        boolean bValid = false;

        if (val == null) {
            LoggingBase.logMessage("FAILURE:" + strObj + " " + strCheck
                    + " Val = NULL");
        } else if (min != null) {
            bValid = val.getValue() >= min.getValue();
            if (!bValid) {
                LoggingBase.logMessage("FAILURE:" + strObj + " " + strCheck
                        + " Val < min - " + val);
            }
        } else if (max != null) {
            bValid = (val.getValue() <= max.getValue());
            if (!bValid) {
                LoggingBase.logMessage("FAILURE:" + strObj + " " + strCheck
                        + " Val > max - Val = " + val + " Max = " + max);
            }
        }
        return bRunningValidity && bValid;

    }

    public static void recordError(String strSrc, String strError) {
        LoggingBase.logMessage("FAILURE:" + strSrc + " " + strError);
    }
}
