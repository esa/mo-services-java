/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.accesscontrol;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;

/**
 * The MALCheckErrorException class represents a CHECK ERROR as an exception.
 */
public class MALCheckErrorException extends MALInteractionException
{
  private final Map qosProperties;

  /**
   * The constructor.
   * @param standardError Error preventing the message to be transmitted
   * @param qosProperties QoS properties of the MALMessage which cannot be transmitted
   */
  public MALCheckErrorException(final MALStandardError standardError, final Map qosProperties)
  {
    super(standardError);
    
    this.qosProperties = qosProperties;
  }

  /**
   * Returns the QoS properties.
   * @return The QoS properties.
   */
  public Map getQosProperties()
  {
    return qosProperties;
  }
}
