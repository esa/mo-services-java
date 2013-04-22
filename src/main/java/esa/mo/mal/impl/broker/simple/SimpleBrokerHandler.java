/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : Sam Cooper
 *
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.impl.broker.simple;

import esa.mo.mal.impl.broker.MALBrokerHandlerImpl;
import esa.mo.mal.impl.broker.SubscriptionSource;
import esa.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Extends the base broker handler for the Simple broker implementation.
 */
public class SimpleBrokerHandler extends MALBrokerHandlerImpl
{
  /**
   * Constructor
   *
   * @param parent The parent of this class.
   */
  public SimpleBrokerHandler(MALClose parent)
  {
    super(parent);
  }

  @Override
  protected SubscriptionSource createEntry(final MALMessageHeader hdr)
  {
    return new SimpleSubscriptionSource(hdr);
  }
}
