/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The transport backend thread factory
 */
public class TransportThreadFactory implements ThreadFactory {

    /**
     * System property to control the number of input processors.
     */
    public static final String INPUT_PROCESSORS_PROPERTY
            = "org.ccsds.moims.mo.mal.transport.gen.inputprocessors";
    /**
     * System property to control the number of input processors.
     */
    public static final String MIN_INPUT_PROCESSORS_PROPERTY
            = "org.ccsds.moims.mo.mal.transport.gen.mininputprocessors";
    /**
     * System property to control the number of input processors.
     */
    public static final String IDLE_INPUT_PROCESSORS_PROPERTY
            = "org.ccsds.moims.mo.mal.transport.gen.idleinputprocessors";

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public TransportThreadFactory(String prefix) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup()
                : Thread.currentThread().getThreadGroup();
        namePrefix = prefix + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }

    public static ExecutorService createDispatcherExecutor(final java.util.Map properties) {
        boolean needsTuning = false;
        int nThreads = 100;
        int lMinInputProcessorThreads = nThreads;
        int lIdleTimeInSeconds = 0;

        if (null != properties) {
            // minium number of internal threads that process incoming MAL packets
            if (properties.containsKey(MIN_INPUT_PROCESSORS_PROPERTY)) {
                needsTuning = true;
                lMinInputProcessorThreads = Integer.parseInt((String) properties.get(
                        MIN_INPUT_PROCESSORS_PROPERTY));
            }

            // number of seconds for internal threads that process incoming 
            // MAL packets to be idle before being terminated
            if (properties.containsKey(IDLE_INPUT_PROCESSORS_PROPERTY)) {
                needsTuning = true;
                lIdleTimeInSeconds = Integer.parseInt(
                        (String) properties.get(IDLE_INPUT_PROCESSORS_PROPERTY));
            }

            // number of internal threads that process incoming MAL packets
            if (properties.containsKey(INPUT_PROCESSORS_PROPERTY)) {
                nThreads = Integer.parseInt((String) properties.get(INPUT_PROCESSORS_PROPERTY));
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(nThreads,
                new TransportThreadFactory("Transport_Dispatcher"));

        // see if we can tune the thread pool
        if (needsTuning) {
            if (executorService instanceof ThreadPoolExecutor) {
                ThreadPoolExecutor tpe = (ThreadPoolExecutor) executorService;
                tpe.setKeepAliveTime(lIdleTimeInSeconds, TimeUnit.SECONDS);
                tpe.setCorePoolSize(lMinInputProcessorThreads);
            }
        }

        return executorService;
    }
}
