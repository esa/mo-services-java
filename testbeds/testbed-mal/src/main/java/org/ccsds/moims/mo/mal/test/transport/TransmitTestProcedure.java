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
package org.ccsds.moims.mo.mal.test.transport;

import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.HeaderTestProcedure;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.ParseHelper;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.structures.IPTestTransitionList;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;

public class TransmitTestProcedure {

    public static final SessionType SESSION = SessionType.LIVE;
    public static final Identifier SESSION_NAME = new Identifier("LIVE");
    public static final UInteger PRIORITY = new UInteger(1);
    public static final QoSLevel QOS_LEVEL = QoSLevel.ASSURED;

    public static final String PROCEDURE_NAME = "TransmitTestProcedure";

    private IPTestStub ipTest;

    public boolean createConsumer() throws Exception {
        LoggingBase.logMessage("TransmitTestProcedure.createConsumer()");
        Thread.sleep(2000);

        ipTest = LocalMALInstance.instance().ipTestStub(
                HeaderTestProcedure.AUTHENTICATION_ID,
                HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE,
                SESSION, SESSION_NAME, QOS_LEVEL, PRIORITY, false).getStub();
        return true;
    }

    public boolean initiateInteraction(String interactionType) throws Exception {
        LoggingBase.logMessage("TransmitTestProcedure.initiateInteraction(" + interactionType + ')');

        InteractionType ip = ParseHelper.parseInteractionType(interactionType);

        TransportInterceptor.instance().resetTransmitCount(ip);

        IPTestTransitionList transitions = new IPTestTransitionList();
        switch (ip.getOrdinal()) {
            case InteractionType._SEND_INDEX:
                ipTest.send(null);
                break;
            case InteractionType._SUBMIT_INDEX:
                ipTest.asyncTestSubmit(null, new IPListener());
                break;
            case InteractionType._REQUEST_INDEX:
                ipTest.asyncRequest(null, new IPListener());
                break;
            case InteractionType._INVOKE_INDEX:
                ipTest.asyncInvoke(null, new IPListener());
                break;
            case InteractionType._PROGRESS_INDEX:
                ipTest.asyncProgress(null, new IPListener());
                break;
            case InteractionType._PUBSUB_INDEX:
                IdentifierList ids = new IdentifierList();
                ipTest.asyncMonitorDeregister(ids, new IPListener());
                break;
            default:
                throw new Exception("Unknown ip: " + ip);
        }

        return true;
    }

    public int transmitRequestCount(String interactionType) throws Exception {
        InteractionType ip = ParseHelper.parseInteractionType(interactionType);
        return TransportInterceptor.instance().getTransmitRequestCount(ip);
    }

    public int transmitResponseCount(String interactionType) throws Exception {
        InteractionType ip = ParseHelper.parseInteractionType(interactionType);
        return TransportInterceptor.instance().getTransmitResponseCount(ip);
    }

    static class IPListener extends IPTestAdapter {
    }
}
