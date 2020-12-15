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
package esa.mo.mal.impl.provider;

import esa.mo.mal.impl.MALContextImpl;
import esa.mo.mal.impl.ServiceComponentImpl;
import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALPublisher;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorListener;

/**
 * MALProvider implementation.
 */
class MALProviderImpl extends ServiceComponentImpl implements MALProvider
{
  private final boolean isPublisher;
  private final Map<String, MALPublisher> publishers = new HashMap<String, MALPublisher>();
  private final URI sharedBrokerUri;
  private final MALBrokerBinding localBrokerBinding;
  private final URI localBrokerUri;
  private MALTransmitErrorListener listener;

  MALProviderImpl(final MALProviderManagerImpl parent,
          final MALContextImpl impl,
          final String localName,
          final String protocol,
          final MALService service,
          final Blob authenticationId,
          final MALInteractionHandler handler,
          final QoSLevel[] expectedQos,
          final UInteger priorityLevelNumber,
          final Map defaultQoSProperties,
          final Boolean isPublisher,
          final URI sharedBrokerUri) throws MALException
  {
    super(parent,
            impl,
            localName,
            protocol,
            service,
            authenticationId,
            expectedQos,
            priorityLevelNumber,
            defaultQoSProperties,
            handler);

    this.isPublisher = isPublisher;
    this.sharedBrokerUri = sharedBrokerUri;

    if (this.isPublisher)
    {
      this.handler.malInitialize(this);

      if (null == this.sharedBrokerUri)
      {
        this.localBrokerBinding = impl.createBrokerManager().createBrokerBinding(null,
                localName + "InternalBroker",
                protocol,
                authenticationId,
                expectedQos,
                priorityLevelNumber,
                defaultQoSProperties);
        this.localBrokerUri = this.localBrokerBinding.getURI();
      }
      else
      {
        this.localBrokerBinding = null;
        this.localBrokerUri = null;
      }
    }
    else
    {
      this.localBrokerBinding = null;
      this.localBrokerUri = null;
    }
  }

  MALProviderImpl(final MALProviderManagerImpl parent,
          final MALContextImpl impl,
          final MALEndpoint endPoint,
          final MALService service,
          final Blob authenticationId,
          final MALInteractionHandler handler,
          final QoSLevel[] expectedQos,
          final UInteger priorityLevelNumber,
          final Map defaultQoSProperties,
          final Boolean isPublisher,
          final URI sharedBrokerUri) throws MALException
  {
    super(parent,
            impl,
            endPoint,
            service,
            authenticationId,
            expectedQos,
            priorityLevelNumber,
            defaultQoSProperties,
            handler);

    this.isPublisher = isPublisher;
    this.sharedBrokerUri = sharedBrokerUri;

    if (this.isPublisher)
    {
      this.handler.malInitialize(this);

      if (null == this.sharedBrokerUri)
      {
        this.localBrokerBinding = impl.createBrokerManager().createBrokerBinding(
                impl.createBrokerManager().createBroker(),
                endPoint,
                authenticationId,
                expectedQos,
                priorityLevelNumber,
                defaultQoSProperties);
        this.localBrokerUri = this.localBrokerBinding.getURI();
      }
      else
      {
        this.localBrokerBinding = null;
        this.localBrokerUri = null;
      }
    }
    else
    {
      this.localBrokerBinding = null;
      this.localBrokerUri = null;
    }
  }

  @Override
  public boolean isPublisher()
  {
    return isPublisher;
  }

  @Override
  public MALService getService()
  {
    return service;
  }

  @Override
  public synchronized MALPublisher createPublisher(final MALPubSubOperation op,
          final IdentifierList domain,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel remotePublisherQos,
          final Map remotePublisherQosProps,
          final UInteger remotePublisherPriority) throws IllegalArgumentException, MALException
  {
    final String key = createPublisherKey(op,
            domain,
            networkZone,
            sessionType,
            sessionName,
            remotePublisherQos,
            remotePublisherPriority);
    MALPublisher pub = publishers.get(key);

    if (null == pub)
    {
      pub = new MALPublisherImpl(this,
              sendHandler,
              op, domain,
              networkZone,
              sessionType,
              sessionName,
              remotePublisherQos,
              remotePublisherQosProps,
              remotePublisherPriority);
      publishers.put(key, pub);
    }

    return pub;
  }

  @Override
  public URI getBrokerURI()
  {
    if (isPublisher())
    {
      if (null != sharedBrokerUri)
      {
        return this.sharedBrokerUri;
      }
      else
      {
        return this.localBrokerUri;
      }
    }

    return null;
  }

  @Override
  public Blob getBrokerAuthenticationId()
  {
    if (isPublisher() && (null == sharedBrokerUri))
    {
      return this.localBrokerBinding.getAuthenticationId();
    }

    return null;
  }

  public Blob setBrokerAuthenticationId(Blob newAuthenticationId)
  {
    if (isPublisher() && (null == sharedBrokerUri))
    {
      return this.localBrokerBinding.setAuthenticationId(newAuthenticationId);
    }

    return null;
  }

  @Override
  public void setTransmitErrorListener(final MALTransmitErrorListener plistener) throws MALException
  {
    listener = plistener;
  }

  @Override
  public MALTransmitErrorListener getTransmitErrorListener() throws MALException
  {
    return listener;
  }

  @Override
  public void close() throws MALException
  {
    super.close();

    this.handler.malFinalize(this);
  }

  @Override
  protected void thisObjectClose() throws MALException
  {
    super.thisObjectClose();

    if (null != localBrokerBinding)
    {
      localBrokerBinding.close();
    }
  }

  String createPublisherKey(final MALPubSubOperation op,
          final IdentifierList domain,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel remotePublisherQos,
          final UInteger remotePublisherPriority)
  {
    final StringBuilder buf = new StringBuilder();
    buf.append(op.getNumber());
    buf.append(domain);
    buf.append(networkZone);
    buf.append(sessionType);
    buf.append(sessionName);
    buf.append(remotePublisherQos);
    buf.append(remotePublisherPriority);
    return buf.toString();
  }
}
