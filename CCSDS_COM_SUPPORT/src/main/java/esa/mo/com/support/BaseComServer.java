package esa.mo.com.support;

import esa.mo.mal.support.BaseMalServer;
import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.event.EventHelper;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementFactoryRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;

/**
 *
 */
public abstract class BaseComServer extends BaseMalServer
{
  protected ActivityTracking activityService;

  public BaseComServer()
  {
    super();
  }

  public BaseComServer(MALContextFactory malFactory, MALContext mal, MALConsumerManager consumerMgr, MALProviderManager providerMgr)
  {
    super(malFactory, mal, consumerMgr, providerMgr);
  }

  @Override
  protected void subInitHelpers(MALElementFactoryRegistry bodyElementFactory) throws MALException
  {
    COMHelper.deepInit(bodyElementFactory);
  }

  @Override
  protected void subInit() throws MALException, MALInteractionException
  {
    activityService = new ActivityTracking();

    createProvider(EventHelper.EVENT_SERVICE, activityService, true);
    activityService.init();
  }
}
