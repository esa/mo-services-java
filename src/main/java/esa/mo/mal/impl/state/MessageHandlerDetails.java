/* ----------------------------------------------------------------------------
 * Copyright (C) 2016      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.state;

import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Wrapper class to hold message details when passing from a reception handler to process handler.
 */
public final class MessageHandlerDetails
{
  private final boolean ackStage;
  private final MALMessage message;
  private final boolean needToReturnAnException;

  protected MessageHandlerDetails(boolean isAckStage, MALMessage msg)
  {
    this.ackStage = isAckStage;
    this.message = msg;
    this.needToReturnAnException = false;
  }

  protected MessageHandlerDetails(boolean isAckStage, MALMessage src, UInteger errNum)
  {
    this.ackStage = isAckStage;
    src.getHeader().setIsErrorMessage(true);
    this.message = new DummyMessage(src.getHeader(), new DummyErrorBody(new MALStandardError(errNum, null)), src.getQoSProperties());
    this.needToReturnAnException = true;
  }

  protected boolean isAckStage()
  {
    return ackStage;
  }

  protected MALMessage getMessage()
  {
    return message;
  }

  public boolean isNeedToReturnAnException()
  {
    return needToReturnAnException;
  }
}
