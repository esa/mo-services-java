/** *****************************************************************************
 * Copyright or © or Copr. CNES
 *
 * This software is a computer program whose purpose is to provide a
 * framework for the CCSDS Mission Operations services.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL-C
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 ****************************************************************************** */
package org.ccsds.moims.mo.malspp.test.sppinterceptor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.spp.SPPSocket;
import org.ccsds.moims.mo.testbed.util.spp.SpacePacket;

public class SPPInterceptorSocket implements SPPSocket {

    private final SPPSocket socket;
    // statically setting scrambling or delay is not nice but good enough for now
    private static int[] scramblePattern = null;
    private Thread scrambleThread;
    private final List<SpacePacket> packets = Collections.synchronizedList(new LinkedList<SpacePacket>());
    // collect all packets requested to be sent out during 1 second before scrambling them
    private static final int COLLECTION_INTERVAL = 1000;
    private static int delay = 0;
    private static int sentPackets = 0;
    private static int delayIndex = 0;
    private static int nFailPackets = 0;

    public SPPInterceptorSocket(SPPSocket socket) {
        this.socket = socket;
    }

    public void send(SpacePacket packet) throws Exception {
        if (nFailPackets != 0) {
            if (nFailPackets > 0) {
                --nFailPackets;
            }
            throw new Exception("sending request failing on purpose");
        }
        // Put packets into queue in order of send. Any scrambling or delay happens afterwards.
        SPPInterceptor.instance().packetSent(packet);
        if (delay > 0) {
            sentPackets++;
            if (sentPackets == delayIndex + 1) {
                Thread delayThread = createDelayThread(delay, packet);
                delayThread.start();
                return;
            }
        }
        if (scramblePattern == null) {
            internalSend(packet);
        } else {
            synchronized (this) {
                if (scrambleThread == null || !scrambleThread.isAlive()) {
                    scrambleThread = createScrambleThread(scramblePattern.clone());
                    scrambleThread.start();
                }
            }
            packets.add(packet);
        }
    }

    private synchronized void internalSend(SpacePacket packet) throws Exception {
        socket.send(packet);
    }

    private Thread createScrambleThread(final int[] scramblePattern) {
        return new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(COLLECTION_INTERVAL);
                    scrambleSend(new LinkedList<SpacePacket>(packets), scramblePattern);
                } catch (Exception ex) {
                    LoggingBase.logMessage("Exception thrown: " + ex.getMessage());
                }
                packets.clear();
            }
        };
    }

    private void scrambleSend(final List<SpacePacket> packets, final int[] pattern) throws Exception {
        LoggingBase.logMessage("Collected " + packets.size() + " packet(s). Now send them scrambled.");
        int skip = 0;
        for (int i = 0; i < packets.size(); i++) {
            boolean sent = false;
            while (!sent) {
                int rawIdx = pattern[(i + skip) % pattern.length];
                if (rawIdx < 0) {
                    LoggingBase.logMessage("Drop packet.");
                    break;
                }
                int idx = rawIdx + ((i / pattern.length) * pattern.length);
                LoggingBase.logMessage("Send packet " + idx + " @ " + i);
                try {
                    SpacePacket p = packets.get(idx);
                    internalSend(p);
                    sent = true;
                } catch (IndexOutOfBoundsException ex) {
                    ++skip;
                    LoggingBase.logMessage("    ... skip index");
                }
            }
        }
    }

    private Thread createDelayThread(final int delay, final SpacePacket packet) {
        return new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay);
                    internalSend(packet);
                } catch (Exception ex) {
                    LoggingBase.logMessage("Exception thrown: " + ex.getMessage());
                }
            }
        };
    }

    public SpacePacket receive() throws Exception {
        SpacePacket packet = socket.receive();
        SPPInterceptor.instance().packetReceived(packet);
        return packet;
    }

    public void close() throws Exception {
        socket.close();
    }

    public String getDescription() {
        return socket.getDescription();
    }

    public static void setScramblePattern(int[] pattern) {
        scramblePattern = pattern;
    }

    public static void setDelay(int delayInMillisecs, int index) {
        delay = delayInMillisecs;
        sentPackets = 0;
        delayIndex = index;
    }

    public static void setSendFail(boolean failState) {
        nFailPackets = failState ? -1 : 0;
    }

    public static void setNumberOFFailPackets(int n) throws Exception {
        if (n < 0) {
            throw new Exception("Number of failing packets must not be negative.");
        }
        nFailPackets = n;
    }
}
