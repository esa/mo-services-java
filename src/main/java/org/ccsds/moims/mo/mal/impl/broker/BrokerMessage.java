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
package org.ccsds.moims.mo.mal.impl.broker;

import java.util.LinkedList;
import java.util.List;
import org.ccsds.moims.mo.mal.impl.MessageDetails;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Container class that holds a set of notify messages from a single broker binding.
 */
public final class BrokerMessage
{
  /**
   * Binding to use.
   */
  public final MALBrokerBindingImpl binding;
  /**
   * Update messages.
   */
  public final List<NotifyMessage> msgs = new LinkedList<NotifyMessage>();

  /**
   * Constructor.
   *
   * @param binding Binding to associate.
   */
  public BrokerMessage(final MALBrokerBindingImpl binding)
  {
    this.binding = binding;
  }

  /**
   * Simple struct style class that holds a single notify message.
   */
  public static final class NotifyMessage
  {
    /**
     * Message header.
     */
    public MessageDetails details;
    /**
     * PubSub transaction id.
     */
    public Long transId;
    /**
     * PubSub domain.
     */
    public IdentifierList domain;
    /**
     * PubSub network zone.
     */
    public Identifier networkZone;
    /**
     * PubSub area.
     */
    public UShort area;
    /**
     * PubSub service.
     */
    public UShort service;
    /**
     * PubSub operation.
     */
    public UShort operation;
    /**
     * PubSub version.
     */
    public UOctet version;
    /**
     * PubSub updates.
     */
    public Object[] updates;
  }
}
