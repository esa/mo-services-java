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
package org.ccsds.moims.mo.mal.transport;

import org.ccsds.moims.mo.mal.structures.Blob;

/**
 * The MALEncodedBody class represents an encoded body.
 */
public class MALEncodedBody
{
  private Blob encodedBody;

  /**
   * Constructor.
   *
   * @param encodedBody The encoded body
   */
  public MALEncodedBody(final Blob encodedBody)
  {
    this.encodedBody = encodedBody;
  }

  /**
   * Returns the encoded body.
   *
   * @return The encoded body
   */
  public Blob getEncodedBody()
  {
    return encodedBody;
  }
}
