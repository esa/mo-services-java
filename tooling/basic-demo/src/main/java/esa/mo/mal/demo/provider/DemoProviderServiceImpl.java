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
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
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
public class DemoProviderServiceImpl extends BasicMonitorInheritanceSkeleton {

    private final UpdateGenerationThread updateGenThread = new UpdateGenerationThread();
    private MALContextFactory malFactory;
    private MALContext mal;
    private MALProviderManager providerMgr;
    private MALProvider demoServiceProvider;
    private boolean initialiased = false;
    private boolean running = false;
    private boolean generating = false;
    private int generationSleep = 1000;
    private MonitorPublisher publisher;
    private int gPoolSize = 1;
    private int gBlockSize = 1;
    private short gCurrentValue = 0;
    private boolean isRegistered = false;

    /**
     * creates the MAL objects, the publisher used to create updates and starts
     * the publishing thread
     *
     * @throws MALException On initialisation error.
     * @throws MALInteractionException On error.
     */
    public synchronized void init() throws MALException, MALInteractionException {
        if (!initialiased) {
            malFactory = MALContextFactory.newFactory();
            mal = malFactory.createMALContext(System.getProperties());
            providerMgr = mal.createProviderManager();

            MALHelper.init(MALContextFactory.getElementsRegistry());
            MALDemoHelper.init(MALContextFactory.getElementsRegistry());
            BasicMonitorHelper.init(MALContextFactory.getElementsRegistry());

            startServices(null);
            running = true;

            updateGenThread.start();
            DemoProviderGui.LOGGER.info("Demo service READY");
            initialiased = true;
        }
    }

    /**
     * Closes any existing service providers and recreates them. Used to switch
     * the transport used by the provider.
     *
     * @param protocol the transport protocol to use
     * @throws MALException On error.
     * @throws MALInteractionException On error.
     */
    public void startServices(final String protocol) throws MALException, MALInteractionException {
        boolean wasRegistered = false;

        // shut down old service transport
        if (null != demoServiceProvider) {
            if (isRegistered) {
                publisher.deregister();

                isRegistered = false;
                wasRegistered = true;
            }

            publisher.close();
            demoServiceProvider.close();
        }

        // start transport
        URI sharedBrokerURI = null;
        if ((null != System.getProperty("demo.provider.useSharedBroker"))
                && (null != System.getProperty("shared.broker.uri"))) {
            sharedBrokerURI = new URI(System.getProperty("shared.broker.uri"));
        }

        demoServiceProvider = providerMgr.createProvider("Demo",
                protocol,
                BasicMonitorHelper.BASICMONITOR_SERVICE,
                new Blob("".getBytes()),
                this,
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1),
                System.getProperties(),
                true,
                sharedBrokerURI);

        DemoProviderGui.LOGGER.log(Level.INFO,
                "Demo Service URI       : {0}", demoServiceProvider.getURI());
        DemoProviderGui.LOGGER.log(Level.INFO,
                "Demo Service broker URI: {0}", demoServiceProvider.getBrokerURI());

        final IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("mission"));
        IdentifierList keys = new IdentifierList();

        publisher = createMonitorPublisher(domain,
                new Identifier("GROUND"),
                SessionType.LIVE,
                new Identifier("LIVE"),
                QoSLevel.BESTEFFORT,
                null,
                new UInteger(0));

        try {
            final File file = new File("demoServiceURI.properties");
            final FileOutputStream fos = new FileOutputStream(file);
            final OutputStreamWriter osw = new OutputStreamWriter(fos);
            final BufferedWriter wrt = new BufferedWriter(osw);
            wrt.append("uri=").append(String.valueOf(demoServiceProvider.getURI()));
            wrt.newLine();
            wrt.append("broker=").append(String.valueOf(demoServiceProvider.getBrokerURI()));
            wrt.newLine();
            wrt.close();
        } catch (IOException ex) {
            DemoProviderGui.LOGGER.log(Level.WARNING,
                    "Unable to write URI information to properties file {0}", ex);
        }

        if (wasRegistered) {
            registerPublisher();
        }
    }

    /**
     * Closes all running threads and releases the MAL resources.
     */
    public void close() {
        try {
            running = false;
            updateGenThread.interrupt();
            if (null != demoServiceProvider) {
                demoServiceProvider.close();
            }
            if (null != providerMgr) {
                providerMgr.close();
            }
            if (null != mal) {
                mal.close();
            }
        } catch (MALException ex) {
            DemoProviderGui.LOGGER.log(Level.WARNING,
                    "Exception during close down of the provider {0}", ex);
        }
    }

    /**
     * Starts the generation of updates.
     */
    public void startGeneration() {
        generating = true;
    }

    /**
     * Pauses the generation of updates.
     */
    public void pauseGeneration() {
        generating = false;
    }

    public boolean isGenerating() {
        return generating;
    }

    /**
     * Sets the sleep interval between update generation attempts.
     *
     * @param msecs Time to sleep in milli seconds
     */
    public void setSleep(final int msecs) {
        generationSleep = msecs;
    }

    /**
     * Returns the block size.
     *
     * @return The block size.
     */
    public int getBlockSize() {
        return gBlockSize;
    }

    /**
     * Sets the block size.
     *
     * @param blockSize The new block size.
     */
    protected void setBlockSize(final int blockSize) {
        if (0 < blockSize) {
            gBlockSize = blockSize;
        }
    }

    /**
     * Returns the pool size.
     *
     * @return the pool size.
     */
    public int getPoolSize() {
        return gPoolSize;
    }

    /**
     * Sets the pool size.
     *
     * @param poolSize The new pool size.
     */
    protected void setPoolSize(final int poolSize) {
        if (0 < poolSize) {
            gPoolSize = poolSize;
        }
    }

    @Override
    public Boolean returnBoolean(final Boolean lBoolean, final MALInteraction interaction) throws MALException {
        if (null != lBoolean) {
            return lBoolean;
        }

        return Boolean.TRUE;
    }

    @Override
    public BasicComposite returnComposite(final BasicComposite lBasicComposite, final MALInteraction interaction)
            throws MALException {
        if (null != lBasicComposite) {
            return lBasicComposite;
        }

        return new BasicComposite(gCurrentValue, "String value", isRegistered);
    }

    @Override
    public BasicEnum returnEnumeration(final BasicEnum lBasicEnum, final MALInteraction interaction) throws MALException {
        if (null != lBasicEnum) {
            return lBasicEnum;
        }

        return BasicEnum.SECOND;
    }

    @Override
    public ComplexComposite returnComplex(final ComplexComposite lComplexComposite, final MALInteraction interaction)
            throws MALException {
        if (null != lComplexComposite) {
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
                eLst);
    }

    @Override
    public ReturnMultipleResponse returnMultiple(final ComplexComposite lComplexComposite0,
            final ComplexComposite lComplexComposite1,
            final ComplexComposite lComplexComposite2,
            final MALInteraction interaction) throws MALInteractionException, MALException {
        return new ReturnMultipleResponse(lComplexComposite0, lComplexComposite1, lComplexComposite2);
    }

    @Override
    public void testSubmit(final ComplexComposite lComplexComposite, final MALInteraction interaction)
            throws MALInteractionException, MALException {
        // Do nothing
    }

    private void registerPublisher() throws MALException, MALInteractionException {
        if (!isRegistered) {
            IdentifierList keys = new IdentifierList();
            publisher.register(keys, new PublishInteractionListener());
            isRegistered = true;
        }
    }

    private void publishParameterUpdate() {
        try {
            registerPublisher();

            final List<Map.Entry<UpdateHeader, BasicUpdate>> updateList = generateUpdates();

            if (0 < updateList.size()) {
                for (Map.Entry<UpdateHeader, BasicUpdate> entry : updateList) {
                    publisher.publish(entry.getKey(), entry.getValue());
                }
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (MALException ex) {
            ex.printStackTrace();
        } catch (MALInteractionException ex) {
            ex.printStackTrace();
        }
    }

    private List<Map.Entry<UpdateHeader, BasicUpdate>> generateUpdates() {
        final short currentValue = ++gCurrentValue;
        final int poolSize = gPoolSize;
        final int blockSize = gBlockSize;

        DemoProviderGui.LOGGER.log(Level.FINE,
                "Generating Demo update ({0}) from pool of ({1}) with block size of ({2})",
                new Object[]{
                    currentValue, poolSize, blockSize
                });

        final List<Map.Entry<UpdateHeader, BasicUpdate>> updateList = new LinkedList<>();

        for (int i = 0; i < poolSize;) {
            BasicUpdate basic = new BasicUpdate(currentValue);
            AttributeList keyValues = new AttributeList();
            keyValues.add(new Identifier(String.valueOf(i)));
            UpdateHeader hdr = new UpdateHeader(new Identifier("SomeURI"), null, keyValues);
            updateList.add(new AbstractMap.SimpleEntry<>(hdr, basic));
        }

        return updateList;
    }

    private static final class PublishInteractionListener implements MALPublishInteractionListener {

        @Override
        public void publishDeregisterAckReceived(final MALMessageHeader header,
                final Map qosProperties) throws MALException {
            DemoProviderGui.LOGGER.fine("PublishInteractionListener::publishDeregisterAckReceived");
        }

        @Override
        public void publishErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            DemoProviderGui.LOGGER.fine("PublishInteractionListener::publishErrorReceived");
        }

        @Override
        public void publishRegisterAckReceived(final MALMessageHeader header,
                final Map qosProperties) throws MALException {
            DemoProviderGui.LOGGER.fine("PublishInteractionListener::publishRegisterAckReceived");
        }

        @Override
        public void publishRegisterErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            DemoProviderGui.LOGGER.fine("PublishInteractionListener::publishRegisterErrorReceived");
        }
    }

    private final class UpdateGenerationThread extends Thread {

        public UpdateGenerationThread() {
            super("UpdateGenerationThread");
        }

        @Override
        public void run() {
            while (running) {
                if (generating) {
                    publishParameterUpdate();
                }

                // sleep
                try {
                    Thread.sleep(generationSleep);
                } catch (InterruptedException ex) {
                    // do nothing
                }
            }
        }
    }
}
