/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
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
package esa.mo.mal.transport.gen;

import esa.mo.mal.transport.gen.receivers.GENIncomingMessageHolder;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * The Incoming Message Dispatcher. This Runnable task is responsible for
 * processing the already decoded message. It holds a queue of messages split on
 * transaction id so that messages with the same transaction id get processed in
 * reception order.
 *
 */
public class IncomingMessageDispatcher implements Runnable {

    private final Queue<GENIncomingMessageHolder> malMsgs = new ArrayDeque<>();
    private final GENTransport transport;
    private boolean finished = false;

    /**
     * Constructor
     *
     * @param transport
     * @param malMsg The MAL message.
     */
    public IncomingMessageDispatcher(final GENTransport transport,
            final GENIncomingMessageHolder malMsg) {
        this.transport = transport;
        malMsgs.add(malMsg);
    }

    /**
     * Adds a message to the internal queue. If the thread associated with this
     * executor has finished it resets the flag and returns true to indicate
     * that it should be resubmitted for more processing to the Executor pool.
     *
     * @param malMsg The decoded message.
     * @return True if this needs to be resubmitted to the processing executor
     * pool.
     */
    public synchronized boolean addMessage(final GENIncomingMessageHolder malMsg) {
        malMsgs.add(malMsg);

        if (finished) {
            finished = false;
            return true; // need to resubmit this to the processing threads
        }

        return false;
    }

    /**
     * Returns true if this thread has finished processing its queue.
     *
     * @return True if finished processing queue.
     */
    public synchronized boolean isFinished() {
        return finished;
    }

    @Override
    public void run() {
        GENIncomingMessageHolder msg;

        synchronized (this) {
            msg = malMsgs.poll();
        }

        while (null != msg) {
            // send message for further processing and routing
            transport.dispatchMessage(msg.malMsg, msg.smsg);

            synchronized (this) {
                msg = malMsgs.poll();

                if (null == msg) {
                    finished = true;
                }
            }
        }
    }
}
