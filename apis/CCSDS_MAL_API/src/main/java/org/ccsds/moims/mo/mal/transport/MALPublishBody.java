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

import java.util.List;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;

/**
 * The MALPublishBody interface gives access to the body of the PUBLISH message defined by the PUBLISH-SUBSCRIBE
 * interaction.
 */
public interface MALPublishBody extends MALMessageBody
{
  /**
   * The method returns the list of UpdateHeaders from the message.
   *
   * @return The decoded UpdateHeaderList.
   * @throws MALException If an error occurs
   */
  UpdateHeaderList getUpdateHeaderList() throws MALException;

  /**
   * The method returns the update lists from the message.
   *
   * @param updateLists Update lists to decode
   * @return The decoded lists.
   * @throws MALException If an error occurs
   */
  List[] getUpdateLists(List... updateLists) throws MALException;

  /**
   * The method returns an update list of from the message.
   *
   * @param listIndex Index of the update list, starting from 0.
   * @param updateList Update list to decode
   * @return The decoded list.
   * @throws MALException If an error occurs
   */
  java.util.List getUpdateList(int listIndex, java.util.List updateList) throws MALException;

  /**
   * The method returns the number of UpdateHeader elements from the message.
   *
   * @return the number of updates.
   * @throws MALException If an error occurs
   */
  int getUpdateCount() throws MALException;

  /**
   * The method returns an Update from the message.
   *
   * @param listIndex Index of the update list, starting from 0.
   * @param updateIndex Index of the update, starting from 0.
   * @return The decoded update.
   * @throws MALException If an error occurs
   */
  Object getUpdate(int listIndex, int updateIndex) throws MALException;

  /**
   * The method returns an encoded Update from the message.
   *
   * @param listIndex Index of the update list, starting from 0.
   * @param updateIndex Index of the update, starting from 0.
   * @return The encoded update.
   * @throws MALException If the transport encoding format does support separately decoding the updates or if another
   * error occurs.
   */
  MALEncodedElement getEncodedUpdate(int listIndex, int updateIndex) throws MALException;
}
