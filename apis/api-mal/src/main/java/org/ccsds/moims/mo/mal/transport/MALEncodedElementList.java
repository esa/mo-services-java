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

/**
 * The type MALEncodedElementList represents a body element typed as a list and containing encoded elements.
 */
public class MALEncodedElementList extends java.util.ArrayList<MALEncodedElement>
{
  private final Object shortForm;
  private static final long serialVersionUID = 0x1000000000099L;

  /**
   * Constructor.
   * @param shortForm The short form of the elements held by this list.
   * @param size Size of the list.
   */
  public MALEncodedElementList(final Object shortForm, final int size)
  {
    super(size);
    this.shortForm = shortForm;
  }

  /**
   * Returns the short form of the elements in this list.
   * @return The short form.
   */
  public Object getShortForm()
  {
    return shortForm;
  }
}
