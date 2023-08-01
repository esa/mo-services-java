/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Encoder performance test
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
package esa.mo.performance.mal;

import esa.mo.performance.util.TestStructureBuilder;
import java.util.Date;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.perftest.perftest.PerfTestHelper;
import org.ccsds.moims.mo.perftest.perftest.consumer.PerfTestStub;
import org.ccsds.moims.mo.perftest.perftest.provider.PerfTestInheritanceSkeleton;
import org.ccsds.moims.mo.perftest.structures.Report;

public class TestMAL {

    public static void main(String[] args) throws Exception {
        int runCount = 100000;
        int pktsPerReport = 1;
        int paramsPerPkt = 1000;

//    Handler fh = new ConsoleHandler();
//    fh.setLevel (Level.WARNING);
//    Logger logger = Logger.getLogger("org.ccsds.moims.mo.mal.transport.gen");
//    logger.addHandler (fh);
//    logger.setLevel (Level.WARNING);
//    logger = Logger.getLogger("org.ccsds.moims.mo.mal.impl");
//    logger.addHandler (fh);
//    logger.setLevel (Level.WARNING);
        System.setProperty("org.ccsds.moims.mo.mal.factory.class", "esa.mo.mal.impl.MALContextFactoryImpl");
        System.setProperty("org.ccsds.moims.mo.mal.transport.protocol.rmi", "esa.mo.mal.transport.rmi.RMITransportFactoryImpl");
        System.setProperty("org.ccsds.moims.mo.mal.encoding.protocol.rmi", "esa.mo.mal.encoder.binary.variable.VariableBinaryStreamFactory");
        System.setProperty("org.ccsds.moims.mo.mal.transport.gen.debug", "false");
        System.setProperty("org.ccsds.moims.mo.mal.transport.gen.wrap", "false");
        System.setProperty("org.ccsds.moims.mo.mal.transport.gen.fastInProcessMessages", "true");

        long result = runtest(runCount, pktsPerReport, paramsPerPkt);

        System.out.println("Times are in microseconds");
        System.out.println("               Call time(us)    Packets(PPS)");

        long eTime = (long) (((double) result) / ((double) (runCount * 1000)));
        System.out.println("                 " + String.format("%6d", eTime)
                + "      " + String.format("%8d", (long) ((1000000.0 / ((float) eTime)) * pktsPerReport * paramsPerPkt)));
    }

    protected static long runtest(int count, int pktsPerReport, int paramsPerPkt) throws Exception {
        System.out.println("Creating objects");
        MALContextFactory malFactory = MALContextFactory.newFactory();
        MALContext mal = malFactory.createMALContext(System.getProperties());
        MALProviderManager providerMgr = mal.createProviderManager();
        MALConsumerManager consumerMgr = mal.createConsumerManager();

        org.ccsds.moims.mo.perftest.PerfTestHelper.deepInit(MALContextFactory.getElementsRegistry());

        Date now = new Date();
        Time timestamp = new Time(now.getTime());

        Composite testObject = TestStructureBuilder.createTestMALComposite(timestamp, pktsPerReport, paramsPerPkt);

        MALProvider serviceProvider = providerMgr.createProvider("Demo",
                null,
                PerfTestHelper.PERFTEST_SERVICE,
                new Blob("".getBytes()),
                new DummyProvider(),
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1),
                System.getProperties(),
                false,
                null,
                new NamedValueList());

        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("ccsds"));
        domain.add(new Identifier("mission"));
        Identifier network = new Identifier("network");

        MALConsumer serviceConsumer = consumerMgr.createConsumer((String) null,
                serviceProvider.getURI(),
                serviceProvider.getBrokerURI(),
                PerfTestHelper.PERFTEST_SERVICE,
                new Blob("".getBytes()),
                domain,
                network,
                SessionType.LIVE,
                new Identifier("LIVE"),
                QoSLevel.ASSURED,
                System.getProperties(),
                new UInteger(0),
                null);

        PerfTestStub testService = new PerfTestStub(serviceConsumer);

        System.out.println("Testing");

        long startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            testService.request((Report) testObject);
        }
        long stopTime = System.nanoTime();

        System.out.println("Finished");

        mal.close();

        return stopTime - startTime;
    }

    private static final class DummyProvider extends PerfTestInheritanceSkeleton {

        public void send(Report _Report0, MALInteraction interaction)
                throws MALInteractionException, MALException {
        }

        public void send2(org.ccsds.moims.mo.xml.test.Report body0, 
                MALInteraction interaction) throws MALInteractionException, MALException {
        }

        public Report request(Report body0, MALInteraction interaction) 
                throws MALInteractionException, MALException {
            return body0;
        }
    }
}
