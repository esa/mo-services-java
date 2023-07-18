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
package org.ccsds.moims.mo.malspp.test.patterns;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.test.patterns.PatternTest;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.HeaderTestProcedure;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.PubSubTestCaseHelper;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestDefinition;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestTransitionList;
import org.ccsds.moims.mo.malspp.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.malspp.test.suite.TestServiceProvider;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.ParseHelper;
import org.objectweb.util.monolog.api.Logger;

public class MalSppPatternTest extends PatternTest {

    private MALMessage rcvdMsg = null;

    public final static Logger logger = fr.dyade.aaa.common.Debug
            .getLogger(MalSppPatternTest.class.getName());

    protected SpacePacketCheck spacePacketCheck;

    public MalSppPatternTest() {
        super();
        spacePacketCheck = new SpacePacketCheck();
    }

    public boolean consumerPacketIsTc(boolean isTc) {
        return spacePacketCheck.consumerPacketIsTc(isTc);
    }

    public boolean providerPacketIsTc(boolean isTc) {
        return spacePacketCheck.providerPacketIsTc(isTc);
    }

    protected void initConsumer(SessionType session, Identifier sessionName,
            QoSLevel qos) throws Exception {
        int consumerPacketType = spacePacketCheck.getConsumerPacketType();
        int providerPacketType = spacePacketCheck.getProviderPacketType();
        if (consumerPacketType == 1) {
            if (providerPacketType == 1) {
                ipTestConsumer = LocalMALInstance.instance().getTcTcIpTestStub(
                        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
                        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
                        HeaderTestProcedure.PRIORITY, false);
            } else {
                ipTestConsumer = LocalMALInstance.instance().getTcTmIpTestStub(
                        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
                        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
                        HeaderTestProcedure.PRIORITY, false);
            }
        } else {
            if (providerPacketType == 1) {
                ipTestConsumer = LocalMALInstance.instance().getTmTcIpTestStub(
                        HeaderTestProcedure.AUTHENTICATION_ID,
                        HeaderTestProcedure.DOMAIN,
                        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
                        HeaderTestProcedure.PRIORITY, false);
            } else {
                ipTestConsumer = LocalMALInstance.instance().getTmTmIpTestStub(
                        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
                        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
                        HeaderTestProcedure.PRIORITY, false);
            }
        }
    }

    public boolean selectReceivedPacketAt(int index) {
        return spacePacketCheck.selectReceivedPacketAt(index);
    }

    public boolean selectSentPacketAt(int index) {
        return spacePacketCheck.selectSentPacketAt(index);
    }

    public boolean checkTimestamp() throws Exception {
        return spacePacketCheck.checkTimestamp();
    }

    public boolean checkSpacePacketType() {
        return spacePacketCheck.checkSpacePacketType();
    }

    public int versionIs() {
        return spacePacketCheck.versionIs();
    }

    public int sduTypeIs() {
        return spacePacketCheck.sduTypeIs();
    }

    public int areaIs() {
        return spacePacketCheck.areaIs();
    }

    public int serviceIs() {
        return spacePacketCheck.serviceIs();
    }

    public int operationIs() {
        return spacePacketCheck.operationIs();
    }

    public int areaVersionIs() {
        return spacePacketCheck.areaVersionIs();
    }

    public int errorFlagIs() {
        return spacePacketCheck.errorFlagIs();
    }

    public boolean checkUriFrom() {
        return spacePacketCheck.checkUriFrom();
    }

    public boolean checkUriTo() {
        return spacePacketCheck.checkUriTo();
    }

    public boolean checkTransactionId() {
        return spacePacketCheck.checkTransactionId();
    }

    public boolean resetSppInterceptor() {
        return spacePacketCheck.resetSppInterceptor();
    }

    public boolean initiateSendPatternWithQosAndSession(String qosLevel,
            String sessionType) throws Exception {
        QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
        SessionType session = ParseHelper.parseSessionType(sessionType);
        Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
        initConsumer(session, sessionName, qos);
        IPTestStub ipTest = ipTestConsumer.getStub();
        IPTestDefinition testDef = new IPTestDefinition("TestSendPattern",
                ipTestConsumer.getConsumer().getURI(),
                HeaderTestProcedure.AUTHENTICATION_ID,
                qos,
                HeaderTestProcedure.PRIORITY,
                HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE,
                session, sessionName,
                new IPTestTransitionList(),
                new Time(System.currentTimeMillis()));

        ipTest.send(testDef);
        return true;
    }

    public boolean checkQos(String qosLevelAsString) throws Exception {
        return spacePacketCheck.checkQos(qosLevelAsString);
    }

    public boolean checkSession(String sessionTypeAsString) throws Exception {
        return spacePacketCheck.checkSession(sessionTypeAsString);
    }

    public boolean checkSecondaryApid() {
        return spacePacketCheck.checkSecondaryApid();
    }

    public boolean checkSecondaryApidQualifier() {
        return spacePacketCheck.checkSecondaryApidQualifier();
    }

    public byte sourceIdFlagIs() throws Exception {
        return spacePacketCheck.sourceIdFlagIs();
    }

    public byte destinationIdFlagIs() throws Exception {
        return spacePacketCheck.destinationIdFlagIs();
    }

    public byte priorityFlagIs() throws Exception {
        return spacePacketCheck.priorityFlagIs();
    }

    public byte timestampFlagIs() throws Exception {
        return spacePacketCheck.timestampFlagIs();
    }

    public byte networkZoneFlagIs() throws Exception {
        return spacePacketCheck.networkZoneFlagIs();
    }

    public byte sessionNameFlagIs() throws Exception {
        return spacePacketCheck.sessionNameFlagIs();
    }

    public byte domainFlagIs() throws Exception {
        return spacePacketCheck.domainFlagIs();
    }

    public byte authenticationIdFlagIs() throws Exception {
        return spacePacketCheck.authenticationIdFlagIs();
    }

    public boolean readSourceId() {
        return spacePacketCheck.readSourceId();
    }

    public boolean readDestinationId() {
        return spacePacketCheck.readDestinationId();
    }

    public long segmentCounterIs() {
        return spacePacketCheck.segmentCounterIs();
    }

    public long priorityIs() throws Exception {
        return spacePacketCheck.priorityIs();
    }

    public String networkZoneIs() throws Exception {
        return spacePacketCheck.networkZoneIs();
    }

    public String sessionNameIs() throws Exception {
        return spacePacketCheck.sessionNameIs();
    }

    public boolean checkDomainId() throws Exception {
        return spacePacketCheck.checkDomainId();
    }

    public boolean checkAuthenticationId() throws Exception {
        return spacePacketCheck.checkAuthenticationId();
    }

    public boolean checkPriorityIsLeftOut() throws Exception {
        return spacePacketCheck.checkPriorityIsLeftOut();
    }

    public boolean checkAuthenticationIdIsLeftOut() throws Exception {
        return spacePacketCheck.checkAuthenticationIdIsLeftOut();
    }

    public boolean checkDomainIsLeftOut() throws Exception {
        return spacePacketCheck.checkDomainIsLeftOut();
    }

    public boolean checkNetworkZoneIsLeftOut() throws Exception {
        return spacePacketCheck.checkNetworkZoneIsLeftOut();
    }

    public boolean checkSessionNameIsLeftOut() throws Exception {
        return spacePacketCheck.checkSessionNameIsLeftOut();
    }

    public boolean checkTimestampIsLeftOut() throws Exception {
        return spacePacketCheck.checkTimestampIsLeftOut();
    }

    public int presenceFlagIs() {
        return spacePacketCheck.presenceFlagIs();
    }

    public String stringFieldIs() throws Exception {
        return spacePacketCheck.stringFieldIs();
    }

    public int packetVersionNumberIs() {
        return spacePacketCheck.packetVersionNumberIs();
    }

    public int secondaryHeaderFlagIs() {
        return spacePacketCheck.secondaryHeaderFlagIs();
    }

    public int packetDataLengthIs() {
        return spacePacketCheck.packetDataLengthIs();
    }

    public boolean packetDataLengthIsLengthOfPacketDataFieldMinusOne() {
        return spacePacketCheck.packetDataLengthIsLengthOfPacketDataFieldMinusOne();
    }

    private MALMessage createMessage(URI uriTo, MALEndpoint ep, InteractionType type, UOctet stage, UShort operation, QoSLevel qos, SessionType session) throws Exception {
        Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
        Long transId = 0L;
        try {
            transId = TransportInterceptor.instance().getLastSentMessage(0).getHeader().getTransactionId() + 1;
        } catch (Exception ex) {
            // do nothing
        }
        return ep.createMessage(
                TestServiceProvider.IP_TEST_AUTHENTICATION_ID,
                uriTo,
                new Time(System.currentTimeMillis()),
                qos,
                HeaderTestProcedure.PRIORITY,
                HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE,
                session,
                sessionName,
                type,
                stage,
                transId,
                MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
                IPTestHelper.IPTEST_SERVICE_NUMBER,
                operation,
                MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
                Boolean.FALSE,
                null, // qos proerties
                new Object[]{null} // body
        );
    }

    public boolean patternInitiationForWithUnknownUriToAndQosAndSession(String pattern, String uri, String qosLevel, String sessionType) throws Exception {
        URI uriTo = new URI(uri);
        QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
        SessionType session = ParseHelper.parseSessionType(sessionType);
        Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);

        initConsumer(session, sessionName, qos);
        IPTestStub ipTest = ipTestConsumer.getStub();

        MALEndpoint ep = TransportInterceptor.instance().getEndPoint(ipTestConsumer.getConsumer().getURI());
        MALMessage msg = null;

        if ("SUBMIT".equalsIgnoreCase(pattern)) {
            msg = createMessage(uriTo, ep,
                    InteractionType.SUBMIT,
                    MALSubmitOperation.SUBMIT_STAGE,
                    IPTestHelper.TESTSUBMIT_OP_NUMBER,
                    qos, session
            );
        } else if ("REQUEST".equalsIgnoreCase(pattern)) {
            msg = createMessage(uriTo, ep,
                    InteractionType.REQUEST,
                    MALRequestOperation.REQUEST_STAGE,
                    IPTestHelper.REQUEST_OP_NUMBER,
                    qos, session
            );
        } else if ("INVOKE".equalsIgnoreCase(pattern)) {
            msg = createMessage(uriTo, ep,
                    InteractionType.INVOKE,
                    MALInvokeOperation.INVOKE_STAGE,
                    IPTestHelper.INVOKE_OP_NUMBER,
                    qos, session
            );
        } else if ("PROGRESS".equalsIgnoreCase(pattern)) {
            msg = createMessage(uriTo, ep,
                    InteractionType.PROGRESS,
                    MALProgressOperation.PROGRESS_STAGE,
                    IPTestHelper.PROGRESS_OP_NUMBER,
                    qos, session
            );
        }
        ep.sendMessage(msg);
        Thread.sleep(2000);
        return true;
    }

    public boolean selectLastReceivedMessage() {
        rcvdMsg = TransportInterceptor.instance().getLastReceivedMessage();
        return true;
    }

    public boolean receivedMessageMalHeaderFieldIsErrorMessage() {
        return rcvdMsg.getHeader().getIsErrorMessage();
    }

    public String receivedMessageBodyContainsError() throws Exception {
        if (rcvdMsg.getBody() instanceof MALErrorBody) {
            MOErrorException error = ((MALErrorBody) rcvdMsg.getBody()).getError();
            if (error.getErrorNumber().equals(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER)) {
                return "destination unknown";
            } else {
                return error.getErrorName().toString();
            }
        }
        return "Not an error.";
    }

    public String receivedMessageMalHeaderFieldUriFromIs() {
        return rcvdMsg.getHeader().getURIFrom().toString();
    }

    public int bufferRemainingSizeIs() {
        return spacePacketCheck.bufferRemainingSizeIs();
    }

    public boolean readInteger() {
        spacePacketCheck.readInteger();
        return true;
    }

    public boolean readUInteger() {
        spacePacketCheck.readUInteger();
        return true;
    }

    public boolean readString() throws Exception {
        spacePacketCheck.stringFieldIs();
        return true;
    }

    public boolean readUri() throws Exception {
        spacePacketCheck.readUri();
        return true;
    }

    public boolean readBlob() throws Exception {
        spacePacketCheck.readBlob();
        return true;
    }

    public boolean readUInt8Enum() throws Exception {
        spacePacketCheck.readUInt8Enum();
        return true;
    }

    public boolean readIdentifierList() throws Exception {
        UInteger length = spacePacketCheck.readUInteger();
        for (long i = 0; i < length.getValue(); i++) {
            if (spacePacketCheck.presenceFlagIs() == 1) {
                spacePacketCheck.readIdentifier();
            }
        }
        return true;
    }

    public boolean readIdentifier() throws Exception {
        spacePacketCheck.readIdentifier();
        return true;
    }

    public boolean readIpTestTransitionList() throws Exception {
        UInteger length = spacePacketCheck.readUInteger();
        for (long i = 0; i < length.getValue(); i++) {
            if (spacePacketCheck.presenceFlagIs() == 1) {
                if (spacePacketCheck.presenceFlagIs() == 1) {
                    spacePacketCheck.readUInt8Enum();
                }
                if (spacePacketCheck.presenceFlagIs() == 1) {
                    spacePacketCheck.readUInteger();
                }
            }
        }
        return true;
    }

    public boolean readTime() throws Exception {
        spacePacketCheck.readTime();
        return true;
    }

    public int elementAreaNumberIs() {
        return spacePacketCheck.readUInt16();
    }

    public long elementServiceNumberIs() {
        return spacePacketCheck.readUInt16();
    }

    public int elementVersionIs() {
        return spacePacketCheck.readUInt8();
    }

    public int elementTypeNumberIs() {
        return spacePacketCheck.readInt24();
    }

    public boolean checkTransmitQosProperties() {
        Map packetQosProperties = spacePacketCheck.getPacketQoSproperties();
        LoggingBase.logMessage("packetQosProperties:  " + packetQosProperties);
        Map consumerQosProperties = ipTestConsumer.getQosProperties();
        LoggingBase.logMessage("consumerQosProperties:  " + consumerQosProperties);
        if (consumerQosProperties == null || consumerQosProperties.size() == 0) {
            return (packetQosProperties == null || packetQosProperties.size() == 0);
        } else {
            return consumerQosProperties.equals(packetQosProperties);
        }
    }

}
