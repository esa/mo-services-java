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
 * The MALEncodedElement class represents an encoded element. It can only be used to represent an encoded body element
 * or an encoded element contained in a body element typed as a list. The type MALEncodedElementList must be used when
 * representing a body element typed as a list and containing encoded elements.
 *
 */
public class MALEncodedElement
{
  private final Blob encodedElement;

  /**
   * Constructor.
   *
   * @param encodedElement The encoded element.
   */
  public MALEncodedElement(final Blob encodedElement)
  {
    this.encodedElement = encodedElement;
  }

  /**
   * Gets the encoded element.
   *
   * @return The encoded element.
   */
  public Blob getEncodedElement()
  {
    return encodedElement;
  }
}
