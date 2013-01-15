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
package org.ccsds.moims.mo.mal.impl.provider;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.impl.MessageDetails;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublisher;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Implementation of the MALPublisher interface.
 */
class MALPublisherImpl implements MALPublisher
{
  /**
   * Logger
   */
  public static final java.util.logging.Logger LOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.impl.provider");
  private final MALProviderImpl parent;
  private final MessageSend handler;
  private final MALPubSubOperation operation;
  private final IdentifierList domain;
  private final Identifier networkZone;
  private final SessionType sessionType;
  private final Identifier sessionName;
  private final QoSLevel remotePublisherQos;
  private final Map remotePublisherQosProps;
  private final UInteger remotePublisherPriority;
  private final Map<AddressKey, Long> transIdMap = new TreeMap<AddressKey, Long>();

  MALPublisherImpl(final MALProviderImpl parent,
          final MessageSend handler,
          final MALPubSubOperation operation,
          final IdentifierList domain,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel remotePublisherQos,
          final Map remotePublisherQosProps,
          final UInteger remotePublisherPriority)
  {
    this.parent = parent;
    this.handler = handler;
    this.operation = operation;
    this.domain = domain;
    this.networkZone = networkZone;
    this.sessionType = sessionType;
    this.sessionName = sessionName;
    this.remotePublisherQos = remotePublisherQos;
    this.remotePublisherQosProps = remotePublisherQosProps;
    this.remotePublisherPriority = remotePublisherPriority;
  }

  @Override
  public void close() throws MALException
  {
  }

  @Override
  public MALProvider getProvider()
  {
    return parent;
  }

  @Override
  public void register(final EntityKeyList entityKeys, final MALPublishInteractionListener listener)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    final MessageDetails details = new MessageDetails(parent.getEndpoint(),
            parent.getURI(),
            null,
            parent.getBrokerURI(),
            operation.getService(),
            parent.getAuthenticationId(),
            domain,
            networkZone,
            sessionType,
            sessionName,
            remotePublisherQos,
            remotePublisherQosProps,
            remotePublisherPriority);

    setTransId(parent.getBrokerURI(),
            domain,
            networkZone.getValue(),
            sessionType,
            sessionName.getValue(),
            handler.publishRegister(details, operation, entityKeys, listener));
  }

  @Override
  public MALMessage asyncRegister(final EntityKeyList entityKeys, final MALPublishInteractionListener listener)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    final MessageDetails details = new MessageDetails(parent.getEndpoint(),
            parent.getURI(),
            null,
            parent.getBrokerURI(),
            operation.getService(),
            parent.getAuthenticationId(),
            domain,
            networkZone,
            sessionType,
            sessionName,
            remotePublisherQos,
            remotePublisherQosProps,
            remotePublisherPriority);

    final MALMessage msg = handler.publishRegisterAsync(details, operation, entityKeys, listener);

    setTransId(parent.getBrokerURI(),
            domain,
            networkZone.getValue(),
            sessionType,
            sessionName.getValue(),
            msg.getHeader().getTransactionId());

    return msg;
  }

  @Override
  public MALMessage publish(final UpdateHeaderList updateHeaderList, final List... updateLists)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    final MessageDetails details = new MessageDetails(parent.getEndpoint(),
            parent.getURI(),
            null,
            parent.getBrokerURI(),
            operation.getService(),
            parent.getAuthenticationId(),
            domain,
            networkZone,
            sessionType,
            sessionName,
            remotePublisherQos,
            remotePublisherQosProps,
            remotePublisherPriority);

    final Long tid = getTransId(parent.getBrokerURI(),
            domain,
            networkZone.getValue(),
            sessionType,
            sessionName.getValue());

    if (null != tid)
    {
      LOGGER.log(Level.INFO, "Publisher using transaction Id of: {0}", tid);

      final Object[] body = new Object[updateLists.length + 1];
      body[0] = updateHeaderList;
      System.arraycopy(updateLists, 0, body, 1, updateLists.length);

      return handler.onewayInteraction(details, tid, operation, MALPubSubOperation.PUBLISH_STAGE, body);
    }
    else
    {
      // this means that we haven't successfully registered, need to throw an exception
      throw new MALInteractionException(new MALStandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
    }
  }

  @Override
  public void deregister() throws MALInteractionException, MALException
  {
    final MessageDetails details = new MessageDetails(parent.getEndpoint(),
            parent.getURI(),
            null,
            parent.getBrokerURI(),
            operation.getService(),
            parent.getAuthenticationId(),
            domain,
            networkZone,
            sessionType,
            sessionName,
            remotePublisherQos,
            remotePublisherQosProps,
            remotePublisherPriority);

    handler.publishDeregister(details, operation);

    clearTransId(parent.getBrokerURI(),
            domain,
            networkZone.getValue(),
            sessionType,
            sessionName.getValue());
  }

  @Override
  public MALMessage asyncDeregister(final MALPublishInteractionListener listener)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    final MessageDetails details = new MessageDetails(parent.getEndpoint(),
            parent.getURI(),
            null,
            parent.getBrokerURI(),
            operation.getService(),
            parent.getAuthenticationId(),
            domain,
            networkZone,
            sessionType,
            sessionName,
            remotePublisherQos,
            remotePublisherQosProps,
            remotePublisherPriority);

    final MALMessage msg = handler.publishDeregisterAsync(details, operation, listener);

    clearTransId(parent.getBrokerURI(),
            domain,
            networkZone.getValue(),
            sessionType,
            sessionName.getValue());

    return msg;
  }

  private synchronized void setTransId(final URI lbrokerUri,
          final IdentifierList ldomain,
          final String lnetworkZone,
          final SessionType lsession,
          final String lsessionName,
          final Long lid)
  {
    final AddressKey key = new AddressKey(lbrokerUri, ldomain, lnetworkZone, lsession, lsessionName);

    if (!transIdMap.containsKey(key))
    {
      LOGGER.log(Level.INFO, "Publisher setting transaction Id to: {0}", lid);
      transIdMap.put(key, lid);
    }
  }

  private synchronized void clearTransId(final URI lbrokerUri,
          final IdentifierList ldomain,
          final String lnetworkZone,
          final SessionType lsession,
          final String lsessionName)
  {
    final AddressKey key = new AddressKey(lbrokerUri, ldomain, lnetworkZone, lsession, lsessionName);

    final Long id = transIdMap.get(key);
    if (null != id)
    {
      LOGGER.log(Level.INFO, "Publisher removing transaction Id of: {0}", id);
      transIdMap.remove(key);
    }
  }

  private synchronized Long getTransId(final URI lbrokerUri,
          final IdentifierList ldomain,
          final String lnetworkZone,
          final SessionType lsession,
          final String lsessionName)
  {
    return transIdMap.get(new AddressKey(lbrokerUri, ldomain, lnetworkZone, lsession, lsessionName));
  }

  private static class AddressKey implements Comparable
  {
    private static final int HASH_MAGIC_NUMBER = 42;
    private final String uri;
    private final String domain;
    private final String networkZone;
    private final int session;
    private final String sessionName;

    /**
     * Constructor.
     *
     * @param uri URI.
     * @param domain Domain.
     * @param networkZone Network zone.
     * @param session Session type.
     * @param sessionName Session name.
     */
    public AddressKey(final URI uri,
            final IdentifierList domain,
            final String networkZone,
            final SessionType session,
            final String sessionName)
    {
      this.uri = uri.getValue();
      this.domain = StructureHelper.domainToString(domain);
      this.networkZone = networkZone;
      this.session = session.getOrdinal();
      this.sessionName = sessionName;
    }

    /**
     * Constructor.
     *
     * @param hdr Source message.
     */
    public AddressKey(final MALMessageHeader hdr)
    {
      this.uri = hdr.getURITo().getValue();
      this.domain = StructureHelper.domainToString(hdr.getDomain());
      this.networkZone = hdr.getNetworkZone().getValue();
      this.session = hdr.getSession().getOrdinal();
      this.sessionName = hdr.getSessionName().getValue();
    }

    @Override
    public boolean equals(final Object obj)
    {
      if (obj instanceof AddressKey)
      {
        final AddressKey other = (AddressKey) obj;
        if (uri == null)
        {
          if (other.uri != null)
          {
            return false;
          }
        }
        else
        {
          if (!uri.equals(other.uri))
          {
            return false;
          }
        }
        if (domain == null)
        {
          if (other.domain != null)
          {
            return false;
          }
        }
        else
        {
          if (!domain.equals(other.domain))
          {
            return false;
          }
        }
        if (networkZone == null)
        {
          if (other.networkZone != null)
          {
            return false;
          }
        }
        else
        {
          if (!networkZone.equals(other.networkZone))
          {
            return false;
          }
        }
        if (session != other.session)
        {
          return false;
        }
        if (sessionName == null)
        {
          if (other.sessionName != null)
          {
            return false;
          }
        }
        else
        {
          if (!sessionName.equals(other.sessionName))
          {
            return false;
          }
        }
        return true;
      }
      return false;
    }

    @Override
    public int hashCode()
    {
      assert false : "hashCode not designed";
      return HASH_MAGIC_NUMBER;
    }

    @Override
    public int compareTo(final Object o)
    {
      final AddressKey other = (AddressKey) o;

      if (uri.equals(other.uri))
      {
        if (domain.equals(other.domain))
        {
          if (networkZone.equals(other.networkZone))
          {
            if (session == other.session)
            {
              if (sessionName.equals(other.sessionName))
              {
                return 0;
              }
              else
              {
                return sessionName.compareTo(other.sessionName);
              }
            }
            else
            {
              return session - other.session;
            }
          }
          else
          {
            return networkZone.compareTo(other.networkZone);
          }
        }
        else
        {
          return domain.compareTo(other.domain);
        }
      }
      else
      {
        return uri.compareTo(other.uri);
      }
    }
  }
}
