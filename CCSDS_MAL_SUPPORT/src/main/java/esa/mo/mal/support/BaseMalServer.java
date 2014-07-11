package esa.mo.mal.support;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 *
 */
public abstract class BaseMalServer
{
  public static final java.util.logging.Logger RLOGGER = Logger.getLogger("esa.mo.mal");
  public static final Object terminateSignal = new Object();
  protected MALContextFactory malFactory;
  protected MALContext mal;
  protected MALConsumerManager consumerMgr;
  protected MALProviderManager providerMgr;
  protected MALTransport transport;
  protected MALEndpoint ep;

  public BaseMalServer()
  {
    this.malFactory = null;
    this.mal = null;
    this.consumerMgr = null;
    this.providerMgr = null;
  }

  public BaseMalServer(MALContextFactory malFactory, MALContext mal, MALConsumerManager consumerMgr, MALProviderManager providerMgr)
  {
    this.malFactory = malFactory;
    this.mal = mal;
    this.consumerMgr = consumerMgr;
    this.providerMgr = providerMgr;
  }

  public void init(String localname, String protocol) throws Exception
  {
    if (null == malFactory)
    {
      malFactory = MALContextFactory.newFactory();
    }
    if (null == mal)
    {
      mal = malFactory.createMALContext(System.getProperties());
    }
    if (null == consumerMgr)
    {
      consumerMgr = mal.createConsumerManager();
    }
    if (null == providerMgr)
    {
      providerMgr = mal.createProviderManager();
    }

    MALHelper.init(MALContextFactory.getElementFactoryRegistry());
    subInitHelpers(MALContextFactory.getElementFactoryRegistry());

    if (null == protocol)
    {
      protocol = System.getProperty("org.ccsds.moims.mo.mal.transport.default.protocol");
    }

    transport = mal.getTransport(protocol);

    ep = transport.createEndpoint(localname, System.getProperties());
    ep.startMessageDelivery();

    subInit();
  }

  public void start() throws MALException, MALInteractionException
  {
    RLOGGER.log(Level.INFO, "Provider URI : {0}", ep.getURI());
  }

  public void stop() throws MALException
  {
    providerMgr.close();
    mal.close();
  }

  public MALContext getMal()
  {
    return mal;
  }

  public MALConsumerManager getConsumerMgr()
  {
    return consumerMgr;
  }

  public MALProviderManager getProviderMgr()
  {
    return providerMgr;
  }

  protected abstract void subInitHelpers(org.ccsds.moims.mo.mal.MALElementFactoryRegistry bodyElementFactory) throws MALException;

  protected abstract void subInit() throws MALException, MALInteractionException;

  protected MALProvider createProvider(MALService service, MALInteractionHandler handler, boolean isPublisher) throws MALException
  {
    return providerMgr.createProvider(ep,
            service,
            new Blob("".getBytes()),
            handler,
            new QoSLevel[]
            {
              QoSLevel.BESTEFFORT
            },
            new UInteger(1),
            System.getProperties(),
            isPublisher,
            null);
  }

  public MALConsumer createConsumer(MALEndpoint cep, MALService service, URI uri) throws MALException
  {
    if (uri == null)
    {
      uri = ep.getURI();
    }

    return consumerMgr.createConsumer(cep,
            uri,
            uri,
            service,
            new Blob("".getBytes()),
            new IdentifierList(),
            new Identifier("Space"),
            SessionType.LIVE,
            new Identifier("LIVE"),
            QoSLevel.BESTEFFORT,
            System.getProperties(),
            new UInteger(0));
  }
}
