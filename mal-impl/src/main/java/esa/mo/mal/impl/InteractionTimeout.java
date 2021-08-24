/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
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
package esa.mo.mal.impl;

import esa.mo.mal.impl.state.BaseOperationHandler;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALStandardError;

/**
 * The InteractionTimeout class is responsible for maintaining a queue of
 * transactions and checking if they have timedout.
 */
public class InteractionTimeout {

    // The property key to set the MAL Interaction Timeout
    private final static String PROP_INTERACTION_TIMEOUT
            = "org.ccsds.moims.mo.mal.interaction.timeout";

    /**
     * The queue that holds a set of entries with the time when interaction was
     * started and the respective operation handler.
     */
    private final LinkedBlockingQueue<SimpleEntry<Long, BaseOperationHandler>> queue
            = new LinkedBlockingQueue<>();

    // Defines if this object has been initialized
    private boolean initialized = false;

    // Defines if the MAL Interaction Timeout is enabled
    private boolean enabled = false;

    // The timeout in milliseconds
    private long timeout = 0;

    public InteractionTimeout() {
        // For testing purposes, one can use:
        // System.setProperty(PROP_INTERACTION_TIMEOUT, "1000"); // in ms
        Logger.getLogger(InteractionTimeout.class.getName()).log(
                Level.FINE, "New InteractionTimeout()");
    }

    private void initialize() {
        if (!initialized) {
            final String prop = System.getProperty(PROP_INTERACTION_TIMEOUT, null);

            if (prop != null) {
                try {
                    timeout = Long.valueOf(prop);
                    // 
                    if (timeout > 0) {
                        enabled = true;
                    }

                    if (enabled) {
                        Thread t1 = createTimeoutCheckingThread();
                        t1.setName("MAL_Interaction_Timeout_Thread");
                        t1.start();
                    }
                } catch (NumberFormatException ex) {
                    Logger.getLogger(InteractionTimeout.class.getName()).log(
                            Level.SEVERE,
                            "The MAL timeout property value must be a number. "
                            + "Please provide it with the key: "
                            + PROP_INTERACTION_TIMEOUT, ex);
                }
            }
        }

        initialized = true;
    }

    public synchronized void insertInQueue(BaseOperationHandler handler) {
        if (!initialized) {
            Logger.getLogger(InteractionTimeout.class.getName()).log(
                    Level.FINE, "Initializing Interaction Timeout Thread...");
            initialize();
        }

        if (enabled) {
            queue.add(new SimpleEntry(System.currentTimeMillis(), handler));
        }
    }

    private Thread createTimeoutCheckingThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        SimpleEntry<Long, BaseOperationHandler> entry = queue.take();
                        long timeoutAt = entry.getKey() + timeout;
                        long sleepFor = timeoutAt - System.currentTimeMillis();

                        // If the timeout was not reached yet
                        // then we sleep until we reach it
                        if (sleepFor > 0) {
                            try {
                                Thread.sleep(sleepFor);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(InteractionTimeout.class.getName()).log(
                                        Level.SEVERE, "Something went wrong...", ex);
                            }
                        }

                        BaseOperationHandler handler = entry.getValue();

                        // Is the interaction still pending?
                        if (!handler.finished()) {
                            // Then we must trigger an Exception!
                            Logger.getLogger(InteractionTimeout.class.getName()).log(
                                    Level.FINE, "Timeout triggered!");

                            String msg = "The interaction timeout in the MAL "
                                    + "was triggered! The timeout is currently "
                                    + "set to: " + timeout + " ms";

                            handler.handleError(
                                    null,
                                    new MALStandardError(
                                            MALHelper.DELIVERY_TIMEDOUT_ERROR_NUMBER,
                                            msg),
                                    null);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(InteractionTimeout.class.getName()).log(
                                Level.SEVERE, "Something went wrong...", ex);
                    }
                }
            }
        });
    }

}
