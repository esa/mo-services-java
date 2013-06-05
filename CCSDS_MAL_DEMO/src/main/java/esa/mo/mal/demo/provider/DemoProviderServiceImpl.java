/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Demo Application
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
package esa.mo.mal.demo.provider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.structures.UpdateType;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.maldemo.MALDemoHelper;
import org.ccsds.moims.mo.maldemo.basicmonitor.BasicMonitorHelper;
import org.ccsds.moims.mo.maldemo.basicmonitor.body.ReturnMultipleResponse;
import org.ccsds.moims.mo.maldemo.basicmonitor.provider.BasicMonitorInheritanceSkeleton;
import org.ccsds.moims.mo.maldemo.basicmonitor.provider.MonitorPublisher;
import org.ccsds.moims.mo.maldemo.basicmonitor.structures.*;

/**
 *
 */
public class DemoProviderServiceImpl extends BasicMonitorInheritanceSkeleton
{
  private static final int DEFAULT_SLEEP = 10000;
  private final UpdateGenerationThread updateGenThread = new UpdateGenerationThread();
  private MALContextFactory malFactory;
  private MALContext mal;
  private MALProviderManager providerMgr;
  private MALProvider demoServiceProvider;
  private boolean initialiased = false;
  private boolean running = false;
  private boolean generating = false;
  private int generationSleep = DEFAULT_SLEEP;
  private MonitorPublisher publisher;
  private int gPoolSize = 1;
  private int gBlockSize = 1;
  private short gCurrentValue = 0;
  private boolean isRegistered = false;

  /**
   * creates the MAL objects, the publisher used to create updates and starts the publishing thread
   *
   * @throws MALException On initialisation error.
   */
  public synchronized void init() throws MALException
  {
    if (!initialiased)
    {
      malFactory = MALContextFactory.newFactory();
      mal = malFactory.createMALContext(System.getProperties());
      providerMgr = mal.createProviderManager();

      MALHelper.init(MALContextFactory.getElementFactoryRegistry());
      MALDemoHelper.init(MALContextFactory.getElementFactoryRegistry());
      BasicMonitorHelper.init(MALContextFactory.getElementFactoryRegistry());

      final IdentifierList domain = new IdentifierList();
      domain.add(new Identifier("esa"));
      domain.add(new Identifier("mission"));

      publisher = createMonitorPublisher(domain,
              new Identifier("GROUND"),
              SessionType.LIVE,
              new Identifier("LIVE"),
              QoSLevel.BESTEFFORT,
              null,
              new UInteger(0));

      startServices();

      running = true;

      updateGenThread.start();

      DemoProviderGui.LOGGER.info("Demo service READY");
      initialiased = true;
    }
  }

  /**
   * Closes any existing service providers and recreates them. Used to switch the transport used by the provider.
   *
   * @throws MALException On error.
   */
  public void startServices() throws MALException
  {
    // shut down old service transport
    if (null != demoServiceProvider)
    {
      demoServiceProvider.close();
    }

    // start transport
    URI sharedBrokerURI = null;
    if ((null != System.getProperty("demo.provider.useSharedBroker"))
            && (null != System.getProperty("shared.broker.uri")))
    {
      sharedBrokerURI = new URI(System.getProperty("shared.broker.uri"));
    }

    demoServiceProvider = providerMgr.createProvider("Demo",
            null,
            BasicMonitorHelper.BASICMONITOR_SERVICE,
            null,
            this,
            new QoSLevel[]
            {
              QoSLevel.ASSURED
            },
            new UInteger(1),
            null,
            true,
            sharedBrokerURI);

    DemoProviderGui.LOGGER.log(Level.INFO, "Demo Service URI       : {0}", demoServiceProvider.getURI());
    DemoProviderGui.LOGGER.log(Level.INFO, "Demo Service broker URI: {0}", demoServiceProvider.getBrokerURI());

    try
    {
      final File file = new File("demoServiceURI.properties");
      final FileOutputStream fos = new FileOutputStream(file);
      final OutputStreamWriter osw = new OutputStreamWriter(fos);
      final BufferedWriter wrt = new BufferedWriter(osw);
      wrt.append("uri=" + demoServiceProvider.getURI());
      wrt.newLine();
      wrt.append("broker=" + demoServiceProvider.getBrokerURI());
      wrt.newLine();
      wrt.close();
    }
    catch (IOException ex)
    {
      DemoProviderGui.LOGGER.log(Level.WARNING, "Unable to write URI information to properties file {0}", ex);
    }
  }

  /**
   * Closes all running threads and releases the MAL resources.
   */
  public void close()
  {
    try
    {
      running = false;
      updateGenThread.interrupt();
      if (null != demoServiceProvider)
      {
        demoServiceProvider.close();
      }
      if (null != providerMgr)
      {
        providerMgr.close();
      }
      if (null != mal)
      {
        mal.close();
      }
    }
    catch (MALException ex)
    {
      DemoProviderGui.LOGGER.log(Level.WARNING, "Exception during close down of the provider {0}", ex);
    }
  }

  /**
   * Starts the generation of updates.
   */
  public void startGeneration()
  {
    generating = true;
  }

  /**
   * Pauses the generation of updates.
   */
  public void pauseGeneration()
  {
    generating = false;
  }

  /**
   * Sets the sleep interval between update generation attempts.
   *
   * @param msecs Time to sleep in milli seconds
   */
  public void setSleep(final int msecs)
  {
    generationSleep = msecs;
  }

  /**
   * Returns the block size.
   *
   * @return The block size.
   */
  public int getBlockSize()
  {
    return gBlockSize;
  }

  /**
   * Sets the block size.
   *
   * @param blockSize The new block size.
   */
  protected void setBlockSize(final int blockSize)
  {
    if (0 < blockSize)
    {
      gBlockSize = blockSize;
    }
  }

  /**
   * Returns the pool size.
   *
   * @return the pool size.
   */
  public int getPoolSize()
  {
    return gPoolSize;
  }

  /**
   * Sets the pool size.
   *
   * @param poolSize The new pool size.
   */
  protected void setPoolSize(final int poolSize)
  {
    if (0 < poolSize)
    {
      gPoolSize = poolSize;
    }
  }

  @Override
  public Boolean returnBoolean(final Boolean lBoolean, final MALInteraction interaction) throws MALException
  {
    if (null != lBoolean)
    {
      return lBoolean;
    }

    return Boolean.TRUE;
  }

  @Override
  public BasicComposite returnComposite(final BasicComposite lBasicComposite, final MALInteraction interaction)
          throws MALException
  {
    if (null != lBasicComposite)
    {
      return lBasicComposite;
    }

    return new BasicComposite(gCurrentValue, "String value", isRegistered);
  }

  @Override
  public BasicEnum returnEnumeration(final BasicEnum lBasicEnum, final MALInteraction interaction) throws MALException
  {
    if (null != lBasicEnum)
    {
      return lBasicEnum;
    }

    return BasicEnum.SECOND;
  }

  @Override
  public ComplexComposite returnComplex(final ComplexComposite lComplexComposite, final MALInteraction interaction)
          throws MALException
  {
    if (null != lComplexComposite)
    {
      return lComplexComposite;
    }

    final IntegerList iLst = new IntegerList();
    iLst.add((int) gCurrentValue);

    final BasicEnumList eLst = new BasicEnumList();
    eLst.add(BasicEnum.FOURTH);

    return new ComplexComposite(new URI("Base String value"),
            !isRegistered,
            (float) 3.142,
            new BasicComposite(gCurrentValue, "String value", isRegistered),
            BasicEnum.THIRD,
            QoSLevel.ASSURED,
            iLst,
            eLst,
            new EntityKey(new Identifier("First"), 2, 3, 4));
  }

  @Override
  public ReturnMultipleResponse returnMultiple(final ComplexComposite lComplexComposite0,
          final ComplexComposite lComplexComposite1,
          final ComplexComposite lComplexComposite2,
          final MALInteraction interaction) throws MALInteractionException, MALException
  {
    return new ReturnMultipleResponse(lComplexComposite0, lComplexComposite1, lComplexComposite2);
  }

  @Override
  public void testSubmit(final ComplexComposite lComplexComposite, final MALInteraction interaction)
          throws MALInteractionException, MALException
  {
    // Do nothing
  }

  private void publishParameterUpdate()
  {
    try
    {
      if (!isRegistered)
      {
        final EntityKeyList lst = new EntityKeyList();
        lst.add(new EntityKey(new Identifier("*"), 0, 0, 0));
        publisher.register(lst, new PublishInteractionListener());

        isRegistered = true;
      }

      final List<Map.Entry<UpdateHeaderList, BasicUpdateList>> updateList = generateUpdates();

      if (0 < updateList.size())
      {
        for (Map.Entry<UpdateHeaderList, BasicUpdateList> entry : updateList)
        {
          publisher.publish(entry.getKey(), entry.getValue());
        }
      }
    }
    catch (IllegalArgumentException ex)
    {
      ex.printStackTrace();
    }
    catch (MALException ex)
    {
      ex.printStackTrace();
    }
    catch (MALInteractionException ex)
    {
      ex.printStackTrace();
    }
  }

  private List<Map.Entry<UpdateHeaderList, BasicUpdateList>> generateUpdates()
  {
    final short currentValue = ++gCurrentValue;
    final int poolSize = gPoolSize;
    final int blockSize = gBlockSize;

    DemoProviderGui.LOGGER.log(Level.FINE,
            "Generating Demo update ({0}) from pool of ({1}) with block size of ({2})",
            new Object[]
            {
              currentValue, poolSize, blockSize
            });

    final List<Map.Entry<UpdateHeaderList, BasicUpdateList>> updateList =
            new LinkedList<Map.Entry<UpdateHeaderList, BasicUpdateList>>();

    for (int i = 0; i < poolSize;)
    {
      final UpdateHeaderList hdrLst = new UpdateHeaderList();
      final BasicUpdateList lst = new BasicUpdateList();

      for (int j = 0; (j < blockSize) && (i < poolSize); ++j, ++i)
      {
        generateUpdate(hdrLst, lst, currentValue, i);
      }

      updateList.add(new AbstractMap.SimpleEntry<UpdateHeaderList, BasicUpdateList>(hdrLst, lst));
    }

    return updateList;
  }

  private void generateUpdate(final UpdateHeaderList hdrLst,
          final BasicUpdateList lst,
          final short currentValue,
          final int i)
  {
    final EntityKey ekey = new EntityKey(new Identifier(String.valueOf(i)), null, null, null);
    final Time timestamp = new Time(System.currentTimeMillis());

    hdrLst.add(new UpdateHeader(timestamp, new URI("SomeURI"), UpdateType.UPDATE, ekey));
    lst.add(new BasicUpdate(currentValue));
  }

  private static final class PublishInteractionListener implements MALPublishInteractionListener
  {
    @Override
    public void publishDeregisterAckReceived(final MALMessageHeader header, final Map qosProperties)
            throws MALException
    {
      DemoProviderGui.LOGGER.fine("PublishInteractionListener::publishDeregisterAckReceived");
    }

    @Override
    public void publishErrorReceived(final MALMessageHeader header, final MALErrorBody body, final Map qosProperties)
            throws MALException
    {
      DemoProviderGui.LOGGER.fine("PublishInteractionListener::publishErrorReceived");
    }

    @Override
    public void publishRegisterAckReceived(final MALMessageHeader header, final Map qosProperties)
            throws MALException
    {
      DemoProviderGui.LOGGER.fine("PublishInteractionListener::publishRegisterAckReceived");
    }

    @Override
    public void publishRegisterErrorReceived(final MALMessageHeader header,
            final MALErrorBody body,
            final Map qosProperties)
            throws MALException
    {
      DemoProviderGui.LOGGER.fine("PublishInteractionListener::publishRegisterErrorReceived");
    }
  }

  private final class UpdateGenerationThread extends Thread
  {
    public UpdateGenerationThread()
    {
      super("UpdateGenerationThread");
    }

    @Override
    public void run()
    {
      while (running)
      {
        if (generating)
        {
          publishParameterUpdate();
        }

        // sleep
        try
        {
          Thread.sleep(generationSleep);
        }
        catch (InterruptedException ex)
        {
          // do nothing
        }
      }
    }
  }
}
