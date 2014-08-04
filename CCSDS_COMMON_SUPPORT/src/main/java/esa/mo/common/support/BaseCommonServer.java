package esa.mo.common.support;

import esa.mo.com.support.BaseComServer;
import org.ccsds.moims.mo.common.CommonHelper;
import org.ccsds.moims.mo.mal.MALElementFactoryRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 *
 */
public abstract class BaseCommonServer extends BaseComServer
{
  protected DirectoryServiceWrapper directoryService;
  
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
    
    directoryService = new DirectoryServiceWrapper();
    
    String duri = System.getProperty("directory.uri", "rmi://localhost:1024/1024-DirectoryService");
    directoryService.init(consumerMgr, new URI(duri));
  }
}