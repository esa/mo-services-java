/*******************************************************************************
 * Copyright or © or Copr. CNES
 *
 * This software is a computer program whose purpose is to provide a 
 * framework for the CCSDS Mission Operations services.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL-C
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 *******************************************************************************/
package org.ccsds.moims.mo.mal.test.errors;

import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestStub;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.ParseHelper;

public class TransportErrorScenario
{
  public static final SessionType SESSION = SessionType.LIVE;
  public static final Identifier SESSION_NAME = new Identifier("LIVE");
  public static final Integer PRIORITY = new Integer(1);
  public static final QoSLevel QOS_LEVEL = QoSLevel.ASSURED;
  
  private ErrorTestStub errorTest;

  public boolean createConsumer() throws Exception {
    LoggingBase.logMessage("TransportErrorScenario.createConsumer()");
    Thread.sleep(2000);
    errorTest = LocalMALInstance.instance().errorTestStub();
    return true;
  }
  
  public boolean raiseError(String errorName) throws Exception {
    LoggingBase.logMessage("TransportErrorScenario.raiseError(" + errorName + ')');
    UInteger errorCode = ParseHelper.parseErrorCode(errorName);
    MALInteractionException exception = null;
    Union body = new Union(""); 
    try
    {
      switch ((int) errorCode.getValue())
      {
      case (int) MALHelper._DELIVERY_FAILED_ERROR_NUMBER:
        errorTest.testDeliveryFailed(body);
        break;
      case (int) MALHelper._DELIVERY_TIMEDOUT_ERROR_NUMBER:
        errorTest.testDeliveryTimedout(body);
        break;
      case (int) MALHelper._DELIVERY_DELAYED_ERROR_NUMBER:
        errorTest.testDeliveryDelayed(body);
        break;
      case (int) MALHelper._DESTINATION_UNKNOWN_ERROR_NUMBER:
        errorTest.testDestinationUnknown(body);
        break;
      case (int) MALHelper._DESTINATION_TRANSIENT_ERROR_NUMBER:
        errorTest.testDestinationTransient(body);
        break;
      case (int) MALHelper._DESTINATION_LOST_ERROR_NUMBER:
        errorTest.testDestinationLost(body);
        break;
      case (int) MALHelper._ENCRYPTION_FAIL_ERROR_NUMBER:
        errorTest.testEncryptionFail(body);
        break;
      case (int) MALHelper._UNSUPPORTED_AREA_ERROR_NUMBER:
        errorTest.testUnsupportedArea(body);
        break;
      case (int) MALHelper._UNSUPPORTED_OPERATION_ERROR_NUMBER:
        errorTest.testUnsupportedOperation(body);
        break;
      case (int) MALHelper._UNSUPPORTED_VERSION_ERROR_NUMBER:
        errorTest.testUnsupportedVersion(body);
        break;
      case (int) MALHelper._BAD_ENCODING_ERROR_NUMBER:
        errorTest.testBadEncoding(body);
        break;
      case (int) MALHelper._UNKNOWN_ERROR_NUMBER:
        errorTest.testUnknown(body);
        break;
      default:
        throw new Exception("Unknown error code: " + errorCode);
      }
    }
    catch (MALInteractionException exc)
    {
      exception = exc;
    }
    return (exception != null  && exception.getStandardError().getErrorNumber().equals(errorCode));
  }
}
