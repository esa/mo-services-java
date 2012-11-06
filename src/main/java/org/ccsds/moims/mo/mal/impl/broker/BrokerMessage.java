/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : cooper_sf
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
  public BrokerMessage(MALBrokerBindingImpl binding)
  {
    this.binding = binding;
  }

  /**
   * A single notify message.
   */
  public static final class NotifyMessage
  {
    /**
     * Message header.
     */
    public MessageDetails details;
    public Long transId;
    public IdentifierList domain;
    public Identifier networkZone;
    public UShort area;
    public UShort service;
    public UShort operation;
    public UOctet version;
    public Object[] updates;
  }
}
