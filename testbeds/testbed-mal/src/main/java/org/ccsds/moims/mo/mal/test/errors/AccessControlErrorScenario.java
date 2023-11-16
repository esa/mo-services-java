/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
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
package org.ccsds.moims.mo.mal.test.errors;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.test.accesscontrol.TestAccessControlFactory;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.ParseHelper;
import org.ccsds.moims.mo.testbed.util.StopTest;

/**
 *
 */
public class AccessControlErrorScenario extends LoggingBase {

    private MALInteractionException lastError = null;

    public boolean securityManagerHasBeenCreated() throws StopTest {
        if (!TestAccessControlFactory.securityManagerHasBeenCreated()) {
            // we can't continue so tell fitnesse to stop
            throw new StopTest("Test Security Manager has not been created therefore test cannot continue!");
        }

        return true;
    }

    public void switchOnLocalRejections() {
        TestAccessControlFactory.managerInstance().switchOnACFailures(true);
    }

    public void switchOffLocalRejections() {
        TestAccessControlFactory.managerInstance().switchOnACFailures(false);
    }

    public boolean aTestAuthenticationFailureInteractionFails() throws MALInteractionException, MALException {
        logMessage("Sending TestAuthenticationFailure");

        lastError = null;

        try {
            LocalMALInstance.instance().errorTestStub().testAuthenticationFailure(null);
        } catch (MALInteractionException ex) {
            lastError = ex;

            if (ex.getStandardError().getErrorNumber().getValue() == MALHelper._AUTHENTICATION_FAILED_ERROR_NUMBER) {
                return true;
            }

            throw ex;
        }

        return false;
    }

    public boolean aTestAuthorisationFailInteractionFails() throws MALInteractionException, MALException {
        logMessage("Sending TestAuthorisationFailure");

        lastError = null;

        try {
            LocalMALInstance.instance().errorTestStub().testAuthorizationFailure(null);
        } catch (MALInteractionException ex) {
            lastError = ex;

            if (ex.getStandardError().getErrorNumber().getValue() == MALHelper._AUTHORISATION_FAIL_ERROR_NUMBER) {
                return true;
            }

            throw ex;
        }

        return false;
    }

    public boolean errorTypeIs(String requiredType) throws Exception {
        logMessage("checking errorTypeIs " + requiredType);
        return (null != lastError) && lastError.getStandardError().getErrorNumber().equals(ParseHelper.parseErrorCode(requiredType));
    }

    public boolean errorSourceIs(String requiredSource) throws Exception {
        logMessage("checking errorSourceIs " + requiredSource);
        if (null != lastError) {
            if ("local".equals(requiredSource)) {
                if (((Union) lastError.getStandardError().getExtraInformation()).getStringValue().startsWith("local")) {
                    return true;
                }
            } else if ("remote".equals(requiredSource)) {
                if (((Union) lastError.getStandardError().getExtraInformation()).getStringValue().startsWith("remote")) {
                    return true;
                }
            }
        }

        return false;
    }
}
