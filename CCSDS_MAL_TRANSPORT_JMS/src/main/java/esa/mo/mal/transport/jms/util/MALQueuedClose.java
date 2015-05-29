/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO JMS Transport Framework
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
package esa.mo.mal.transport.jms.util;

import org.ccsds.moims.mo.mal.MALException;


/** 
 *
 * @param <T> The type of object held in the queue.
 * 
 * @author cooper_sf
 */
public abstract class MALQueuedClose<T> extends MALClose
{
  protected ActiveQueue messageQueue;

  public MALQueuedClose(MALClose parent)
  {
    super(parent);
  }
  
  protected void createQueue(String name, ActiveQueueAdapter<T> adapter) throws InterruptedException
  {
    messageQueue = new ActiveQueue<T>(name, adapter, 0);
    messageQueue.pleaseStartSync();
  }

  @Override
  protected void thisObjectClose() throws MALException
  {
    super.thisObjectClose();
    
    messageQueue.pleaseStop();
  }
}
