package esa.mo.common.support;

import esa.mo.com.support.BaseComServer;
import org.ccsds.moims.mo.common.CommonHelper;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementFactoryRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 *
 */
public abstract class BaseCommonServer extends BaseComServer
{
  protected final DirectoryServiceWrapper directoryService;

  public BaseCommonServer(IdentifierList domain, Identifier network)
  {
    super(domain, network);
    
    directoryService = new DirectoryServiceWrapper();
  }

  public BaseCommonServer(MALContextFactory malFactory, MALContext mal, MALConsumerManager consumerMgr, MALProviderManager providerMgr, IdentifierList domain, Identifier network)
  {
    super(malFactory, mal, consumerMgr, providerMgr, domain, network);
    
    directoryService = new DirectoryServiceWrapper();
  }
  
  @Override
  protected void subInitHelpers(MALElementFactoryRegistry bodyElementFactory) throws MALException
  {
    super.subInitHelpers(bodyElementFactory);
    
    CommonHelper.deepInit(bodyElementFactory);
  }

  @Override
  protected void subInit() throws MALException, MALInteractionException
  {
    super.subInit();
    
    String duri = System.getProperty("directory.uri", "rmi://localhost:1024/1024-DirectoryService");
    directoryService.init(consumerMgr, new URI(duri));
  }
}