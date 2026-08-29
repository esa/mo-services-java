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

import org.ccsds.moims.mo.mal.BadEncodingException;
import org.ccsds.moims.mo.mal.DeliveryDelayedException;
import org.ccsds.moims.mo.mal.DeliveryFailedException;
import org.ccsds.moims.mo.mal.DeliveryTimedoutException;
import org.ccsds.moims.mo.mal.DestinationLostException;
import org.ccsds.moims.mo.mal.DestinationTransientException;
import org.ccsds.moims.mo.mal.DestinationUnknownException;
import org.ccsds.moims.mo.mal.EncryptionFailException;
import org.ccsds.moims.mo.mal.InternalException;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.UnsupportedAreaException;
import org.ccsds.moims.mo.mal.UnsupportedAreaVersionException;
import org.ccsds.moims.mo.mal.UnsupportedOperationException;
import org.ccsds.moims.mo.mal.UnsupportedServiceException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.HeaderTestProcedure;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALPublishRegisterBody;
import org.ccsds.moims.mo.mal.transport.MALRegisterBody;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;
import org.ccsds.moims.mo.mal.transport.MALTransmitMultipleErrorException;
import org.ccsds.moims.mo.malprototype.errortest.ErrorTestHelper;
import org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo;
import org.ccsds.moims.mo.testbed.transport.TestEndPoint;
import org.ccsds.moims.mo.testbed.transport.TestEndPointSendInterceptor;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class MALTestEndPointSendInterceptor implements TestEndPointSendInterceptor {

    @Override
    public void sendMessage(TestEndPoint ep, MALMessage msg) throws MALTransmitErrorException, MALException {
        if (IPTestHelper.IPTEST_SERVICE.getAreaNumber().equals(msg.getHeader().getServiceArea())
                && IPTestServiceInfo.IPTEST_SERVICE_NUMBER.equals(msg.getHeader().getService())) {
            if (msg.getHeader().getInteractionType().getValue() == InteractionType.PUBSUB_VALUE) {
                if (msg.getHeader().getInteractionStage().getValue() == MALPubSubOperation._PUBLISH_REGISTER_STAGE) {
                    MALPublishRegisterBody publishRegisterBody = (MALPublishRegisterBody) msg.getBody();

                    IdentifierList keyNames = publishRegisterBody.getSubscriptionKeyNames();
                    if (keyNames.contains(HeaderTestProcedure.PUBLISH_REGISTER_ERROR_KEY_VALUE)) {
                        MALMessageHeader errorHeader = TestEndPoint.createErrorHeader(msg.getHeader(),
                                FileBasedDirectory.loadSharedBrokerAuthenticationId(),
                                MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE);
                        MALMessage ack = ep.createTestMessage(errorHeader,
                                new InternalException(), msg.getQoSProperties());
                        ep.getReceivedMessageInterceptor().onMessage(ep, ack);
                        return;
                    }
                } else if (msg.getHeader().getInteractionStage().getValue() == MALPubSubOperation._REGISTER_STAGE) {
                    MALRegisterBody registerBody = (MALRegisterBody) msg.getBody();
                    Subscription subscription = registerBody.getSubscription();

                    if (subscription.getSubscriptionId().equals(HeaderTestProcedure.REGISTER_ERROR_SUBSCRIPTION_ID)) {
                        Blob authId = HeaderTestProcedure.getBrokerAuthId(HeaderTestProcedure.isSharedbroker(msg.getHeader().getToURI()));

                        MALMessageHeader errorHeader = TestEndPoint.createErrorHeader(msg.getHeader(),
                                authId, MALPubSubOperation.REGISTER_ACK_STAGE);

                        MALMessage ack = ep.createTestMessage(errorHeader,
                                new InternalException(), msg.getQoSProperties());
                        LoggingBase.logMessage("TestEndPoint: return register error");
                        ep.getReceivedMessageInterceptor().onMessage(ep, ack);
                    }
                }
            }
        } else if (ErrorTestHelper.ERRORTEST_SERVICE.getAreaNumber().equals(msg.getHeader().getServiceArea())
                && ErrorTestServiceInfo.ERRORTEST_SERVICE_NUMBER.equals(msg.getHeader().getService())) {
            if (ErrorTestServiceInfo.TESTDELIVERYFAILED_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(
                        msg.getHeader(), new DeliveryFailedException(), msg.getQoSProperties());
            } else if (ErrorTestServiceInfo.TESTDELIVERYTIMEDOUT_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(
                        msg.getHeader(), new DeliveryTimedoutException(), msg.getQoSProperties());
            } else if (ErrorTestServiceInfo.TESTDELIVERYDELAYED_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(
                        msg.getHeader(), new DeliveryDelayedException(), msg.getQoSProperties());
            } else if (ErrorTestServiceInfo.TESTDESTINATIONUNKNOWN_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(
                        msg.getHeader(), new DestinationUnknownException(), msg.getQoSProperties());
            } else if (ErrorTestServiceInfo.TESTDESTINATIONTRANSIENT_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(
                        msg.getHeader(), new DestinationTransientException(), msg.getQoSProperties());
            } else if (ErrorTestServiceInfo.TESTDESTINATIONLOST_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(
                        msg.getHeader(), new DestinationLostException(), msg.getQoSProperties());
            } else if (ErrorTestServiceInfo.TESTENCRYPTIONFAIL_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(
                        msg.getHeader(), new EncryptionFailException(), msg.getQoSProperties());
            } else if (ErrorTestServiceInfo.TESTUNSUPPORTEDAREA_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(
                        msg.getHeader(), new UnsupportedAreaException(), msg.getQoSProperties());
            } else if (ErrorTestServiceInfo.TESTUNSUPPORTEDSERVICE_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(
                        msg.getHeader(), new UnsupportedServiceException(), msg.getQoSProperties());
            } else if (ErrorTestServiceInfo.TESTUNSUPPORTEDOPERATION_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(
                        msg.getHeader(), new UnsupportedOperationException(), msg.getQoSProperties());
            } else if (ErrorTestServiceInfo.TESTUNSUPPORTEDAREAVERSION_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(
                        msg.getHeader(), new UnsupportedAreaVersionException(), msg.getQoSProperties());
            } else if (ErrorTestServiceInfo.TESTBADENCODING_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(
                        msg.getHeader(), new BadEncodingException(), msg.getQoSProperties());
            } else if (ErrorTestServiceInfo.TESTUNKNOWN_OP.getNumber().equals(
                    msg.getHeader().getOperation())) {
                throw new MALTransmitErrorException(msg.getHeader(), new UnknownException(), msg.getQoSProperties());
            }
        }
    }

    @Override
    public void sendMessages(TestEndPoint ep, MALMessage[] messages)
            throws MALTransmitMultipleErrorException, MALException {
    }
}
