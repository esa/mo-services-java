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
package org.ccsds.moims.mo.com.test.activity;

import org.ccsds.moims.mo.com.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_EXECUTION_STR;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_FORWARD_STR;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_RECEPTION_STR;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_ACCEPTANCE_STR;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_RELEASE_STR;

/**
 *
 */
public abstract class BaseActivityScenario extends LoggingBase
{
  protected final String loggingClassName;
  protected final Identifier ALL_ID = new Identifier("*");
  protected final Integer ALL_INT = 0;
  protected MALStandardError lastError = null;

  public BaseActivityScenario(String loggingClassName)
  {
    this.loggingClassName = loggingClassName;
  }

  public boolean testActivityServiceClientHasBeenCreated() throws Exception
  {
    logMessage(loggingClassName + ":testActivityServiceClientHasBeenCreated");
    return null != LocalMALInstance.instance().activityTestStub();
  }

  public boolean callResetTestOnServiceProvider() throws Exception
  {
    logMessage(loggingClassName + ":callResetTestOnServiceProvider");
    LocalMALInstance.instance().activityTestStub().resetTest();
    return true;
  }

  public void clearLastError()
  {
    logMessage(loggingClassName + ":clearLastError");
    lastError = null;
  }

  public String lastErrorMessageWas() throws Exception
  {
    logMessage(loggingClassName + ":lastErrorMessageWas");
    if (null != lastError)
    {
      // return COMParseHelper.parseErrorCode(lastError.getErrorNumber());
    }
    return "No last error";
  }

  public void waitForReasonableAmountOfTime() throws Exception
  {
    Thread.sleep(Configuration.WAIT_TIME_OUT);
  }

  public void closeTestActivityServiceClient() throws Exception
  {
    logMessage(loggingClassName + ":closeTestActivityServiceClient");
    LocalMALInstance.instance().close();
  }

  static public String objToEventName(String obj)
  {
    String strEventName = "UNKNOWN";

    if (obj.equalsIgnoreCase(OBJ_NO_ASE_RELEASE_STR))
    {
      strEventName = "RELEASE";
    }
    else if (obj.equalsIgnoreCase(OBJ_NO_ASE_RECEPTION_STR))
    {
      strEventName = "RECEPTION";
    }
    else if (obj.equalsIgnoreCase(OBJ_NO_ASE_FORWARD_STR))
    {
      strEventName = "FORWARD";
    }
    else if (obj.equalsIgnoreCase(OBJ_NO_ASE_ACCEPTANCE_STR))
    {
      strEventName = "ACCEPTANCE";
    }
    else if (obj.equalsIgnoreCase(OBJ_NO_ASE_EXECUTION_STR))
    {
      strEventName = "EXECUTION";
    }

    return strEventName;
  }
}
