/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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
package esa.mo.mal.impl.broker;

import java.util.List;
import java.util.Map;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;

/**
 * Simple struct style class that holds a set of notify message for a single subscription
 */
public final class NotifyMessageSet
{
  /**
   * Message header.
   */
  public MessageHeaderDetails details;
  /**
   * Message bodies.
   */
  public List<NotifyMessage> messages;

  /**
   * Simple struct style class that holds details common to a set of subscriptions from a single consumer.
   */
  public static class MessageHeaderDetails
  {
    /**
     * The URI of the subscriber.
     */
    public final URI uriTo;
    /**
     * The transaction id of the subscription.
     */
    public final Long transactionId;
    /**
     * The session type of the subscription.
     */
    public final SessionType sessionType;
    /**
     * The session name of the subscription.
     */
    public final Identifier sessionName;
    /**
     * The QoS level of the subscription.
     */
    public final QoSLevel qosLevel;
    /**
     * The QoS properties of the subscription.
     */
    public final Map qosProps;
    /**
     * The priority of the subscription.
     */
    public final UInteger priority;

    /**
     * Constructor.
     * @param uriTo The URI of the subscriber.
     * @param transactionId The transaction id of the subscription.
     * @param sessionType The session type of the subscription.
     * @param sessionName The session name of the subscription.
     * @param qosLevel The QoS level of the subscription.
     * @param qosProps The QoS properties of the subscription.
     * @param priority The priority of the subscription.
     */
    public MessageHeaderDetails(URI uriTo, Long transactionId, SessionType sessionType, Identifier sessionName, QoSLevel qosLevel, Map qosProps, UInteger priority)
    {
      this.uriTo = uriTo;
      this.transactionId = transactionId;
      this.sessionType = sessionType;
      this.sessionName = sessionName;
      this.qosLevel = qosLevel;
      this.qosProps = qosProps;
      this.priority = priority;
    }
  }

  /**
   * Simple struct style class that holds a single notify message body.
   */
  public static final class NotifyMessage
  {
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
     * PubSub subscription Id.
     */
    public Identifier subscriptionId;
    /**
     * PubSub update headers.
     */
    public UpdateHeaderList updateHeaderList;
    /**
     * PubSub updates.
     */
    public java.util.List[] updateList;
  }
}
