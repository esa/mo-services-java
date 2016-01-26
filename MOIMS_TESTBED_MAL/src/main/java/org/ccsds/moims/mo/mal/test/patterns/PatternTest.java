/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
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
package org.ccsds.moims.mo.mal.test.patterns;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.HeaderTestProcedure;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.PubSubTestCaseHelper;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.mal.test.suite.TestServiceProvider;
import org.ccsds.moims.mo.mal.test.util.AssertionHelper;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestDefinition;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestTransition;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestTransitionList;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestTransitionType;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.testbed.suite.BooleanCondition;
import org.ccsds.moims.mo.testbed.transport.TestEndPoint;
import org.ccsds.moims.mo.testbed.transport.TestMessageHeader;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.ParseHelper;

/**
 *
 */
public class PatternTest
{
  protected LocalMALInstance.IPTestConsumer ipTestConsumer;
  private AssertionList assertions;
  private boolean correctNumberOfTransistions = false;
  private TestEndPoint ep;

  public boolean patternInitiationForWithMultiWithEmptyBodyAndQosAndSessionAndTransistionsAndBehaviourIdTest(String pattern, boolean callMultiVersion, boolean callEmptyVersion, String qosLevel, String sessionType, String[] transistions, int procedureId) throws Exception
  {
    LoggingBase.logMessage("PatternTest(" + pattern + ", " + callMultiVersion + ", " + qosLevel + ", " + sessionType + ", " + procedureId + ")");
    resetAssertions();

    QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
    SessionType session = ParseHelper.parseSessionType(sessionType);
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);

    ipTestConsumer = LocalMALInstance.instance().ipTestStub(HeaderTestProcedure.AUTHENTICATION_ID,
            HeaderTestProcedure.DOMAIN,
            HeaderTestProcedure.NETWORK_ZONE,
            session,
            sessionName,
            qos,
            HeaderTestProcedure.PRIORITY,
            false);
    IPTestStub ipTest = ipTestConsumer.getStub();

    ep = TransportInterceptor.instance().getEndPoint(ipTestConsumer.getConsumer().getURI());

    MALMessageHeader expectedInitialHeader = new TestMessageHeader(
            ipTestConsumer.getConsumer().getURI(),
            HeaderTestProcedure.AUTHENTICATION_ID,
            ipTestConsumer.getConsumer().getURI(),
            new Time(System.currentTimeMillis()),
            qos,
            HeaderTestProcedure.PRIORITY,
            HeaderTestProcedure.DOMAIN,
            HeaderTestProcedure.NETWORK_ZONE,
            session,
            sessionName,
            InteractionType.PUBSUB,
            new UOctet(MALPubSubOperation._REGISTER_STAGE),
            null, // transaction id not checked here (see below)
            MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
            IPTestHelper.IPTEST_SERVICE_NUMBER,
            IPTestHelper.MONITOR_OP.getNumber(),
            MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
            Boolean.FALSE);

    MALMessageHeader expectedFinalHeader = new TestMessageHeader(
            ipTestConsumer.getConsumer().getURI(),
            TestServiceProvider.IP_TEST_AUTHENTICATION_ID,
            ipTestConsumer.getConsumer().getURI(),
            new Time(System.currentTimeMillis()),
            qos,
            HeaderTestProcedure.PRIORITY,
            HeaderTestProcedure.DOMAIN,
            HeaderTestProcedure.NETWORK_ZONE,
            session,
            sessionName,
            InteractionType.PUBSUB,
            new UOctet(MALPubSubOperation._REGISTER_STAGE),
            null, // transaction id not checked here (see below)
            MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
            IPTestHelper.IPTEST_SERVICE_NUMBER,
            IPTestHelper.MONITOR_OP.getNumber(),
            MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
            Boolean.FALSE);

    IPTestTransitionList transList = new IPTestTransitionList();
    List initialFaultyTransList = new LinkedList();
    List finalFaultyTransList = new LinkedList();

    boolean seenGoodTransition = false;
    int expectedTransitionCount = 0;
    for (String trans : transistions)
    {
      IPTestTransitionType transition = IPTestTransitionTypeFromString(trans);

      if (trans.startsWith("_"))
      {
        if (seenGoodTransition)
        {
          finalFaultyTransList.add(transition);
        }
        else
        {
          ++expectedTransitionCount;
          initialFaultyTransList.add(transition);
        }

        // there should be no more transitions after a faulty one
        break;
      }
      else
      {
        if (initialFaultyTransList.isEmpty())
        {
          seenGoodTransition = true;
          ++expectedTransitionCount;
        }
      }

      transList.add(new IPTestTransition(transition, null));
    }

    IPTestDefinition testDef = new IPTestDefinition(String.valueOf(procedureId),
            ipTestConsumer.getConsumer().getURI(),
            HeaderTestProcedure.AUTHENTICATION_ID,
            qos,
            HeaderTestProcedure.PRIORITY,
            HeaderTestProcedure.DOMAIN,
            HeaderTestProcedure.NETWORK_ZONE,
            session, sessionName,
            transList,
            new Time(System.currentTimeMillis()));

    ResponseListener monitor = new ResponseListener("PatternTest", expectedTransitionCount);

    setupInitialFaultyTransitions(initialFaultyTransList);

    if ("SUBMIT".equalsIgnoreCase(pattern))
    {
      expectedInitialHeader.setInteractionType(InteractionType.SUBMIT);
      expectedInitialHeader.setInteractionStage(MALSubmitOperation.SUBMIT_STAGE);
      if (callMultiVersion)
      {
        expectedInitialHeader.setOperation(IPTestHelper.SUBMITMULTI_OP.getNumber());
      }
      else
      {
        expectedInitialHeader.setOperation(IPTestHelper.TESTSUBMIT_OP.getNumber());
      }
      testSubmit(monitor, ipTest, callMultiVersion, testDef);
    }
    else if ("REQUEST".equalsIgnoreCase(pattern))
    {
      expectedInitialHeader.setInteractionType(InteractionType.REQUEST);
      expectedInitialHeader.setInteractionStage(MALRequestOperation.REQUEST_STAGE);
      if (callMultiVersion)
      {
        expectedInitialHeader.setOperation(IPTestHelper.REQUESTMULTI_OP.getNumber());
      }
      else
      {
        if (callEmptyVersion)
        {
          expectedInitialHeader.setOperation(IPTestHelper.TESTREQUESTEMPTYBODY_OP.getNumber());
        }
        else
        {
          expectedInitialHeader.setOperation(IPTestHelper.REQUEST_OP.getNumber());
        }
      }
      testRequest(monitor, ipTest, callMultiVersion, callEmptyVersion, testDef);
    }
    else if ("INVOKE".equalsIgnoreCase(pattern))
    {
      expectedInitialHeader.setInteractionType(InteractionType.INVOKE);
      expectedInitialHeader.setInteractionStage(MALInvokeOperation.INVOKE_STAGE);
      if (callMultiVersion)
      {
        expectedInitialHeader.setOperation(IPTestHelper.INVOKEMULTI_OP.getNumber());
      }
      else
      {
        if (callEmptyVersion)
        {
          expectedInitialHeader.setOperation(IPTestHelper.TESTINVOKEEMPTYBODY_OP.getNumber());
        }
        else
        {
          expectedInitialHeader.setOperation(IPTestHelper.INVOKE_OP.getNumber());
        }
      }
      testInvoke(monitor, ipTest, callMultiVersion, callEmptyVersion, testDef);
    }
    else if ("PROGRESS".equalsIgnoreCase(pattern))
    {
      expectedInitialHeader.setInteractionType(InteractionType.PROGRESS);
      expectedInitialHeader.setInteractionStage(MALProgressOperation.PROGRESS_STAGE);
      if (callMultiVersion)
      {
        expectedInitialHeader.setOperation(IPTestHelper.PROGRESSMULTI_OP.getNumber());
      }
      else
      {
        if (callEmptyVersion)
        {
          expectedInitialHeader.setOperation(IPTestHelper.TESTPROGRESSEMPTYBODY_OP.getNumber());
        }
        else
        {
          expectedInitialHeader.setOperation(IPTestHelper.PROGRESS_OP.getNumber());
        }
      }
      testProgress(monitor, ipTest, callMultiVersion, callEmptyVersion, testDef);
    }

    MALMessageHeader msgHeader = addInitialHeaderAssertions(expectedInitialHeader);

    expectedFinalHeader.setInteractionType(expectedInitialHeader.getInteractionType());
    expectedFinalHeader.setOperation(expectedInitialHeader.getOperation());
    expectedFinalHeader.setURIFrom(msgHeader.getURITo());
    expectedFinalHeader.setTransactionId(msgHeader.getTransactionId());

    sendInitialFaultyTransitions(initialFaultyTransList, expectedFinalHeader);

    boolean retVal = false;

    try
    {
      LoggingBase.logMessage("PatternTest.waiting for responses");
      retVal = monitor.cond.waitFor(10000);
    }
    catch (InterruptedException ex)
    {
      // do nothing, we are expecting this
    }

    LoggingBase.logMessage("PatternTest.waiting(" + retVal + ")");
    if (retVal)
    {
      sendFinalFaultyTransitions(finalFaultyTransList, expectedFinalHeader);

      if ("SUBMIT".equalsIgnoreCase(pattern))
      {
        retVal = addSubmitReturnAssertions(monitor, procedureId, expectedFinalHeader);
      }
      else if ("REQUEST".equalsIgnoreCase(pattern))
      {
        retVal = addRequestReturnAssertions(monitor, procedureId, expectedFinalHeader);
      }
      else if ("INVOKE".equalsIgnoreCase(pattern))
      {
        retVal = addInvokeReturnAssertions(monitor, procedureId, expectedFinalHeader);
      }
      else if ("PROGRESS".equalsIgnoreCase(pattern))
      {
        retVal = addProgressReturnAssertions(monitor, procedureId, expectedFinalHeader);
      }
    }

    LoggingBase.logMessage("PatternTest" + pattern + "(" + retVal + ")");

    correctNumberOfTransistions = monitor.checkCorrectNumberOfReceivedMessages();

    return retVal;
  }

  private void testSubmit(ResponseListener monitor, IPTestStub ipTest, boolean callMultiVersion, IPTestDefinition testDef) throws Exception
  {
    LoggingBase.logMessage("PatternTest.testSubmit(" + callMultiVersion + ")");

    if (callMultiVersion)
    {
      ipTest.asyncSubmitMulti(testDef, new UInteger(100), monitor);
    }
    else
    {
      ipTest.asyncTestSubmit(testDef, monitor);
    }
  }

  private boolean addSubmitReturnAssertions(ResponseListener monitor, int procedureId, MALMessageHeader expectedFinalHeader) throws Exception
  {
    MALMessageHeader msgHeaderFinal = null;

    if ((1 == procedureId) || (3 == procedureId))
    {
      msgHeaderFinal = monitor.submitAckReceivedMsgHeader;
    }
    else if ((2 == procedureId) || (4 == procedureId))
    {
      expectedFinalHeader.setIsErrorMessage(Boolean.TRUE);
      msgHeaderFinal = monitor.submitErrorReceivedMsgHeader;
    }

    expectedFinalHeader.setInteractionStage(MALSubmitOperation.SUBMIT_ACK_STAGE);
    AssertionHelper.checkHeader("PatternTest.checkFinalHeader", assertions, msgHeaderFinal, expectedFinalHeader);

    LoggingBase.logMessage("PatternTest.testSubmit(" + msgHeaderFinal + ")");

    return (null != msgHeaderFinal);
  }

  private void testRequest(ResponseListener monitor, IPTestStub ipTest, boolean callMultiVersion, boolean callEmptyVersion, IPTestDefinition testDef) throws Exception
  {
    LoggingBase.logMessage("PatternTest.testRequest(" + callMultiVersion + ")");

    if (callMultiVersion)
    {
      ipTest.asyncRequestMulti(testDef, new UInteger(100), monitor);
    }
    else
    {
      if (callEmptyVersion)
      {
        ipTest.asyncTestRequestEmptyBody(testDef, monitor);
      }
      else
      {
        ipTest.asyncRequest(testDef, monitor);
      }
    }
  }

  private boolean addRequestReturnAssertions(ResponseListener monitor, int procedureId, MALMessageHeader expectedFinalHeader) throws Exception
  {
    MALMessageHeader msgHeaderFinal = null;

    if ((1 == procedureId) || (3 == procedureId))
    {
      msgHeaderFinal = monitor.requestResponseReceivedMsgHeader;
    }
    else if ((2 == procedureId) || (4 == procedureId))
    {
      expectedFinalHeader.setIsErrorMessage(Boolean.TRUE);
      msgHeaderFinal = monitor.requestErrorReceivedMsgHeader;
    }

    expectedFinalHeader.setInteractionStage(MALRequestOperation.REQUEST_RESPONSE_STAGE);
    AssertionHelper.checkHeader("PatternTest.checkFinalHeader", assertions, msgHeaderFinal, expectedFinalHeader);

    LoggingBase.logMessage("PatternTest.testRequest(" + msgHeaderFinal + ")");

    return (null != msgHeaderFinal);
  }

  private void testInvoke(ResponseListener monitor, IPTestStub ipTest, boolean callMultiVersion, boolean callEmptyVersion, IPTestDefinition testDef) throws Exception
  {
    LoggingBase.logMessage("PatternTest.testInvoke(" + callMultiVersion + ")");

    if (callMultiVersion)
    {
      ipTest.asyncInvokeMulti(testDef, new UInteger(100), monitor);
    }
    else
    {
      if (callEmptyVersion)
      {
        ipTest.asyncTestInvokeEmptyBody(testDef, monitor);
      }
      else
      {
        ipTest.asyncInvoke(testDef, monitor);
      }
    }
  }

  private boolean addInvokeReturnAssertions(ResponseListener monitor, int procedureId, MALMessageHeader expectedFinalHeader) throws Exception
  {
    expectedFinalHeader.setInteractionStage(MALInvokeOperation.INVOKE_ACK_STAGE);

    MALMessageHeader msgHeaderAck;
    MALMessageHeader msgHeaderFinal;

    if ((3 == procedureId) || (6 == procedureId))
    {
      expectedFinalHeader.setIsErrorMessage(Boolean.TRUE);
      msgHeaderAck = monitor.invokeAckErrorReceivedMsgHeader;
    }
    else if (7 == procedureId)
    {
      expectedFinalHeader.setIsErrorMessage(Boolean.TRUE);
      msgHeaderAck = monitor.invokeAckErrorReceivedMsgHeader;
      expectedFinalHeader.setInteractionStage(MALInvokeOperation.INVOKE_RESPONSE_STAGE);
    }
    else
    {
      msgHeaderAck = monitor.invokeAckReceivedMsgHeader;
    }

    AssertionHelper.checkHeader("PatternTest.checkAckHeader", assertions, msgHeaderAck, expectedFinalHeader);

    expectedFinalHeader.setInteractionStage(MALInvokeOperation.INVOKE_RESPONSE_STAGE);

    if ((2 == procedureId) || (5 == procedureId))
    {
      expectedFinalHeader.setIsErrorMessage(Boolean.TRUE);
      msgHeaderFinal = monitor.invokeResponseErrorReceivedMsgHeader;
      AssertionHelper.checkHeader("PatternTest.checkFinalHeader", assertions, msgHeaderFinal, expectedFinalHeader);
    }
    else if ((1 == procedureId) || (4 == procedureId))
    {
      msgHeaderFinal = monitor.invokeResponseReceivedMsgHeader;
      AssertionHelper.checkHeader("PatternTest.checkFinalHeader", assertions, msgHeaderFinal, expectedFinalHeader);
    }
    else
    {
      // this is so that we return true on return
      msgHeaderFinal = msgHeaderAck;
    }

    LoggingBase.logMessage("PatternTest.testInvoke(" + msgHeaderAck + "," + msgHeaderFinal + ")");

    return (null != msgHeaderFinal);
  }

  private void testProgress(ResponseListener monitor, IPTestStub ipTest, boolean callMultiVersion, boolean callEmptyVersion, IPTestDefinition testDef) throws Exception
  {
    LoggingBase.logMessage("PatternTest.testProgress(" + callMultiVersion + ")");

    synchronized (monitor)
    {
      if (callMultiVersion)
      {
        ipTest.asyncProgressMulti(testDef, new UInteger(100), monitor);
      }
      else
      {
        if (callEmptyVersion)
        {
          ipTest.asyncTestProgressEmptyBody(testDef, monitor);
        }
        else
        {
          ipTest.asyncProgress(testDef, monitor);
        }
      }
    }
  }

  private boolean addProgressReturnAssertions(ResponseListener monitor, int procedureId, MALMessageHeader expectedFinalHeader) throws Exception
  {
    expectedFinalHeader.setInteractionStage(MALProgressOperation.PROGRESS_ACK_STAGE);

    MALMessageHeader msgHeaderAck;
    MALMessageHeader msgHeaderUpdate1;
    MALMessageHeader msgHeaderUpdate2;
    MALMessageHeader msgHeaderFinal = null;

    if ((3 == procedureId) || (9 == procedureId))
    {
      expectedFinalHeader.setIsErrorMessage(Boolean.TRUE);
      msgHeaderAck = monitor.progressAckErrorReceivedMsgHeader;
    }
    else if ((10 == procedureId) || (11 == procedureId) || (12 == procedureId))
    {
      expectedFinalHeader.setIsErrorMessage(Boolean.TRUE);
      msgHeaderAck = monitor.progressAckErrorReceivedMsgHeader;
      if (12 == procedureId)
      {
        expectedFinalHeader.setInteractionStage(MALProgressOperation.PROGRESS_RESPONSE_STAGE);
      }
      else
      {
        expectedFinalHeader.setInteractionStage(MALProgressOperation.PROGRESS_UPDATE_STAGE);
      }
    }
    else
    {
      msgHeaderAck = monitor.progressAckReceivedMsgHeader;
    }

    AssertionHelper.checkHeader("PatternTest.checkAckHeader", assertions, msgHeaderAck, expectedFinalHeader);

    expectedFinalHeader.setInteractionStage(MALProgressOperation.PROGRESS_UPDATE_STAGE);

    if ((4 == procedureId) || (5 == procedureId) || ((13 <= procedureId) && (14 >= procedureId)))
    {
      msgHeaderUpdate1 = monitor.progressUpdate1ReceivedMsgHeader;
      msgHeaderUpdate2 = monitor.progressUpdate2ReceivedMsgHeader;
      AssertionHelper.checkHeader("PatternTest.checkUpdate1Header", assertions, msgHeaderUpdate1, expectedFinalHeader);
      AssertionHelper.checkHeader("PatternTest.checkUpdate2Header", assertions, msgHeaderUpdate2, expectedFinalHeader);
    }
    else
    {
      // this is so that we return true on return
      msgHeaderUpdate1 = msgHeaderAck;
      msgHeaderUpdate2 = msgHeaderAck;
    }

    if ((5 == procedureId) || (13 == procedureId))
    {
      expectedFinalHeader.setIsErrorMessage(Boolean.TRUE);
      msgHeaderFinal = monitor.progressUpdateErrorReceivedMsgHeader;
      AssertionHelper.checkHeader("PatternTest.checkFinalHeader", assertions, msgHeaderFinal, expectedFinalHeader);
    }

    expectedFinalHeader.setInteractionStage(MALProgressOperation.PROGRESS_RESPONSE_STAGE);

    if ((2 == procedureId) || (6 == procedureId) || (8 == procedureId) || (14 == procedureId))
    {
      expectedFinalHeader.setIsErrorMessage(Boolean.TRUE);
      msgHeaderFinal = monitor.progressResponseErrorReceivedMsgHeader;
      AssertionHelper.checkHeader("PatternTest.checkFinalHeader", assertions, msgHeaderFinal, expectedFinalHeader);
    }
    else if ((1 == procedureId) || (4 == procedureId) || (7 == procedureId))
    {
      msgHeaderFinal = monitor.progressResponseReceivedMsgHeader;
      AssertionHelper.checkHeader("PatternTest.checkFinalHeader", assertions, msgHeaderFinal, expectedFinalHeader);
    }
    else if ((5 != procedureId) || (12 != procedureId) || (13 != procedureId))
    {
      // this is so that we return true on return
      msgHeaderFinal = msgHeaderAck;
    }

    LoggingBase.logMessage("PatternTest.testProgress(" + msgHeaderAck + "," + msgHeaderUpdate1 + "," + msgHeaderUpdate2 + "," + msgHeaderFinal + ")");

    return (null != msgHeaderFinal);
  }

  public boolean theConsumerAssertionsAreOk()
  {
    LoggingBase.logMessage("PatternTest.theConsumerAssertionsAreOk()");
    boolean result = AssertionHelper.checkAssertions(assertions);
    LoggingBase.logMessage("PatternTest.theConsumerAssertionsAreOk(" + result + ")");
    return result;
  }

  public boolean theTransitionsAreOk() throws Exception
  {
    LoggingBase.logMessage("PatternTest.theTransitionsAreOk(" + correctNumberOfTransistions + ")");
    return correctNumberOfTransistions;
  }

  private void resetAssertions()
  {
    assertions = new AssertionList();
    correctNumberOfTransistions = false;
  }

  private void setupInitialFaultyTransitions(List initialFaultyTransList)
  {
    if (0 < initialFaultyTransList.size())
    {
      LoggingBase.logMessage("PatternTest.setupInitialFaultyTransitions blocking incoming messages to set up faulty initial messages");
      ep.blockReceivedMessages();
    }
  }

  private void sendInitialFaultyTransitions(List initialFaultyTransList, MALMessageHeader hdr) throws Exception
  {
    if (0 < initialFaultyTransList.size())
    {
      for (int i = 0; i < initialFaultyTransList.size(); i++)
      {
        IPTestTransitionType transition = (IPTestTransitionType) initialFaultyTransList.get(i);

        transmitBrokenMessage(hdr, transition);
      }

      LoggingBase.logMessage("PatternTest.sendInitialFaultyTransitions unblocking incoming messages");
      ep.releaseReceivedMessages();
    }
  }

  private void sendFinalFaultyTransitions(List finalFaultyTransList, MALMessageHeader hdr) throws Exception
  {
    if (0 < finalFaultyTransList.size())
    {
      for (int i = 0; i < finalFaultyTransList.size(); i++)
      {
        IPTestTransitionType transition = (IPTestTransitionType) finalFaultyTransList.get(i);

        transmitBrokenMessage(hdr, transition);
      }
    }
  }

  private MALMessageHeader addInitialHeaderAssertions(MALMessageHeader expectedInitialHeader)
  {
    MALMessage msg = TransportInterceptor.instance().getLastSentMessage(ipTestConsumer.getConsumer().getURI());
    MALMessageHeader msgHeader = msg.getHeader();

    expectedInitialHeader.setURITo(msgHeader.getURITo());

    AssertionHelper.checkHeader("PatternTest.checkHeader", assertions, msgHeader, expectedInitialHeader);

    return msgHeader;
  }

  private void transmitBrokenMessage(MALMessageHeader srcHdr, IPTestTransitionType transitionType) throws Exception
  {
    boolean isError = false;

    if ((IPTestTransitionType.ACK_ERROR == transitionType) || (IPTestTransitionType.UPDATE_ERROR == transitionType) || (IPTestTransitionType.RESPONSE_ERROR == transitionType))
    {
      isError = true;
    }

    MALMessageHeader brokenHeader = new TestMessageHeader(srcHdr.getURIFrom(),
            srcHdr.getAuthenticationId(),
            srcHdr.getURITo(),
            srcHdr.getTimestamp(),
            srcHdr.getQoSlevel(),
            srcHdr.getPriority(),
            srcHdr.getDomain(),
            srcHdr.getNetworkZone(),
            srcHdr.getSession(),
            srcHdr.getSessionName(),
            srcHdr.getInteractionType(),
            transitionTypeToInteractionStage(transitionType, srcHdr.getInteractionType()),
            srcHdr.getTransactionId(),
            srcHdr.getServiceArea(),
            srcHdr.getService(),
            srcHdr.getOperation(),
            srcHdr.getAreaVersion(),
            isError);

    MALMessage brokenMessage;
    if (isError)
    {
      brokenMessage = ep.createTestMessage(brokenHeader,
              new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, null), new HashMap());
    }
    else
    {
      brokenMessage = ep.createTestMessage(brokenHeader, (Element) null, new HashMap());
    }
    LoggingBase.logMessage("Sending brokenMessage = " + brokenMessage);

    ep.receive(brokenMessage);
  }

  private IPTestTransitionType IPTestTransitionTypeFromString(String transO)
  {
    String trans = transO;
    if (transO.startsWith("_"))
    {
      trans = transO.substring(1, transO.length() - 1);
    }

    if ("ACK".equals(trans))
    {
      return IPTestTransitionType.ACK;
    }
    else if ("RESPONSE".equals(trans))
    {
      return IPTestTransitionType.RESPONSE;
    }
    else if ("ACK_ERROR".equals(trans))
    {
      return IPTestTransitionType.ACK_ERROR;
    }
    else if ("RESPONSE_ERROR".equals(trans))
    {
      return IPTestTransitionType.RESPONSE_ERROR;
    }
    else if ("UPDATE".equals(trans))
    {
      return IPTestTransitionType.UPDATE;
    }
    else if ("UPDATE_ERROR".equals(trans))
    {
      return IPTestTransitionType.UPDATE_ERROR;
    }
    else
    {
      throw new RuntimeException("Unknown ordinal! " + transO + " : " + trans);
    }
  }

  private UOctet transitionTypeToInteractionStage(IPTestTransitionType transType, InteractionType interactionType)
  {
    switch (transType.getOrdinal())
    {
      case IPTestTransitionType._ACK_INDEX:
      case IPTestTransitionType._ACK_ERROR_INDEX:
        switch (interactionType.getOrdinal())
        {
          case InteractionType._SUBMIT_INDEX:
            return MALSubmitOperation.SUBMIT_ACK_STAGE;
          case InteractionType._INVOKE_INDEX:
            return MALInvokeOperation.INVOKE_ACK_STAGE;
          case InteractionType._PROGRESS_INDEX:
            return MALProgressOperation.PROGRESS_ACK_STAGE;
        }
        break;
      case IPTestTransitionType._UPDATE_INDEX:
      case IPTestTransitionType._UPDATE_ERROR_INDEX:
        switch (interactionType.getOrdinal())
        {
          case InteractionType._PROGRESS_INDEX:
            return MALProgressOperation.PROGRESS_UPDATE_STAGE;
        }
        break;
      case IPTestTransitionType._RESPONSE_INDEX:
      case IPTestTransitionType._RESPONSE_ERROR_INDEX:
        switch (interactionType.getOrdinal())
        {
          case InteractionType._REQUEST_INDEX:
            return MALRequestOperation.REQUEST_RESPONSE_STAGE;
          case InteractionType._INVOKE_INDEX:
            return MALInvokeOperation.INVOKE_RESPONSE_STAGE;
          case InteractionType._PROGRESS_INDEX:
            return MALProgressOperation.PROGRESS_RESPONSE_STAGE;
        }
        break;
    }

    return null;
  }

  public static class ResponseListener extends IPTestAdapter
  {
    private final BooleanCondition cond = new BooleanCondition();
    public MALMessageHeader submitAckReceivedMsgHeader = null;
    public MALMessageHeader requestResponseReceivedMsgHeader = null;
    public MALMessageHeader invokeAckReceivedMsgHeader = null;
    public MALMessageHeader invokeResponseReceivedMsgHeader = null;
    public MALMessageHeader progressAckReceivedMsgHeader = null;
    public MALMessageHeader progressUpdate1ReceivedMsgHeader = null;
    public MALMessageHeader progressUpdate2ReceivedMsgHeader = null;
    public MALMessageHeader progressResponseReceivedMsgHeader = null;
    public MALMessageHeader submitErrorReceivedMsgHeader = null;
    public MALStandardError submitErrorReceivedError = null;
    public MALMessageHeader requestErrorReceivedMsgHeader = null;
    public MALStandardError requestErrorReceivedError = null;
    public MALMessageHeader invokeAckErrorReceivedMsgHeader = null;
    public MALStandardError invokeAckErrorReceivedError = null;
    public MALMessageHeader invokeResponseErrorReceivedMsgHeader = null;
    public MALStandardError invokeResponseErrorReceivedError = null;
    public MALMessageHeader progressAckErrorReceivedMsgHeader = null;
    public MALStandardError progressAckErrorReceivedError = null;
    public MALMessageHeader progressUpdateErrorReceivedMsgHeader = null;
    public MALStandardError progressUpdateErrorReceivedError = null;
    public MALMessageHeader progressResponseErrorReceivedMsgHeader = null;
    public MALStandardError progressResponseErrorReceivedError = null;
    int receivedMessages = 0;
    final String loggingName;
    final int expectedMessages;

    public ResponseListener(String loggingName, int expectedMessages)
    {
      this.loggingName = loggingName;
      this.expectedMessages = expectedMessages;
    }

    public BooleanCondition getCond()
    {
      return cond;
    }

    public boolean checkCorrectNumberOfReceivedMessages()
    {
      if (!(expectedMessages == receivedMessages))
      {
        LoggingBase.logMessage(loggingName + " expected " + expectedMessages + " messages but received " + receivedMessages);
      }
      return expectedMessages == receivedMessages;
    }

    @Override
    public synchronized void testSubmitAckReceived(MALMessageHeader msgHeader, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      submitAckReceivedMsgHeader = msgHeader;
      cond.set();
    }

    @Override
    public void submitMultiAckReceived(MALMessageHeader msgHeader, Map qosProperties)
    {
      testSubmitAckReceived(msgHeader, qosProperties);
    }

    @Override
    public synchronized void testSubmitErrorReceived(MALMessageHeader msgHeader,
            MALStandardError error, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      submitErrorReceivedMsgHeader = msgHeader;
      submitErrorReceivedError = error;
      cond.set();
    }

    @Override
    public void submitMultiErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      testSubmitErrorReceived(msgHeader, error, qosProperties);
    }

    @Override
    public synchronized void requestResponseReceived(MALMessageHeader msgHeader, String result, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      requestResponseReceivedMsgHeader = msgHeader;
      cond.set();
    }

    @Override
    public void requestMultiResponseReceived(MALMessageHeader msgHeader, String _String0, Element _Element1, Map qosProperties)
    {
      requestResponseReceived(msgHeader, (String) null, qosProperties);
    }

    @Override
    public void testRequestEmptyBodyResponseReceived(MALMessageHeader msgHeader, Map qosProperties)
    {
      requestResponseReceived(msgHeader, (String) null, qosProperties);
    }

    @Override
    public synchronized void requestErrorReceived(MALMessageHeader msgHeader,
            MALStandardError error, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      requestErrorReceivedMsgHeader = msgHeader;
      requestErrorReceivedError = error;
      cond.set();
    }

    @Override
    public void requestMultiErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      requestErrorReceived(msgHeader, error, qosProperties);
    }

    @Override
    public void testRequestEmptyBodyErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      requestErrorReceived(msgHeader, error, qosProperties);
    }

    @Override
    public synchronized void invokeAckReceived(MALMessageHeader msgHeader,
            String bodyElement1, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      invokeAckReceivedMsgHeader = msgHeader;
    }

    @Override
    public void invokeMultiAckReceived(MALMessageHeader msgHeader, String _String0, Element _Element1, Map qosProperties)
    {
      invokeAckReceived(msgHeader, (String) null, qosProperties);
    }

    @Override
    public void testInvokeEmptyBodyAckReceived(MALMessageHeader msgHeader, Map qosProperties)
    {
      invokeAckReceived(msgHeader, (String) null, qosProperties);
    }

    @Override
    public synchronized void invokeAckErrorReceived(MALMessageHeader msgHeader,
            MALStandardError error, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      invokeAckErrorReceivedMsgHeader = msgHeader;
      invokeAckErrorReceivedError = error;
      cond.set();
    }

    @Override
    public void invokeMultiAckErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      invokeAckErrorReceived(msgHeader, error, qosProperties);
    }

    @Override
    public void testInvokeEmptyBodyAckErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      invokeAckErrorReceived(msgHeader, error, qosProperties);
    }

    @Override
    public synchronized void invokeResponseReceived(MALMessageHeader msgHeader, String _String, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      invokeResponseReceivedMsgHeader = msgHeader;
      cond.set();
    }

    @Override
    public void invokeMultiResponseReceived(MALMessageHeader msgHeader, String _String0, Element _Element1, Map qosProperties)
    {
      invokeResponseReceived(msgHeader, (String) null, qosProperties);
    }

    @Override
    public void testInvokeEmptyBodyResponseReceived(MALMessageHeader msgHeader, Map qosProperties)
    {
      invokeResponseReceived(msgHeader, (String) null, qosProperties);
    }

    @Override
    public synchronized void invokeResponseErrorReceived(MALMessageHeader msgHeader,
            MALStandardError error, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      invokeResponseErrorReceivedMsgHeader = msgHeader;
      invokeResponseErrorReceivedError = error;
      cond.set();
    }

    @Override
    public void invokeMultiResponseErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      invokeResponseErrorReceived(msgHeader, error, qosProperties);
    }

    @Override
    public void testInvokeEmptyBodyResponseErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      invokeResponseErrorReceived(msgHeader, error, qosProperties);
    }

    @Override
    public synchronized void progressAckReceived(MALMessageHeader msgHeader,
            String bodyElement1, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      progressAckReceivedMsgHeader = msgHeader;
    }

    @Override
    public void progressMultiAckReceived(MALMessageHeader msgHeader, String _String0, Element _Element1, Map qosProperties)
    {
      progressAckReceived(msgHeader, (String) null, qosProperties);
    }

    @Override
    public void testProgressEmptyBodyAckReceived(MALMessageHeader msgHeader, Map qosProperties)
    {
      progressAckReceived(msgHeader, (String) null, qosProperties);
    }

    @Override
    public synchronized void progressAckErrorReceived(MALMessageHeader msgHeader,
            MALStandardError error, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      progressAckErrorReceivedMsgHeader = msgHeader;
      progressAckErrorReceivedError = error;
      cond.set();
    }

    @Override
    public void progressMultiAckErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      progressAckErrorReceived(msgHeader, error, qosProperties);
    }

    @Override
    public void testProgressEmptyBodyAckErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      progressAckErrorReceived(msgHeader, error, qosProperties);
    }

    @Override
    public void progressUpdateReceived(MALMessageHeader msgHeader, Integer result, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;

      if (null == progressUpdate1ReceivedMsgHeader)
      {
        progressUpdate1ReceivedMsgHeader = msgHeader;
      }
      else
      {
        progressUpdate2ReceivedMsgHeader = msgHeader;
      }
    }

    @Override
    public void progressMultiUpdateReceived(MALMessageHeader msgHeader, Integer _Integer0, Element _Element1, Map qosProperties)
    {
      progressUpdateReceived(msgHeader, (Integer) null, qosProperties);
    }

    @Override
    public void testProgressEmptyBodyUpdateReceived(MALMessageHeader msgHeader, Map qosProperties)
    {
      progressUpdateReceived(msgHeader, (Integer) null, qosProperties);
    }

    @Override
    public synchronized void progressUpdateErrorReceived(MALMessageHeader msgHeader,
            MALStandardError error, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      progressUpdateErrorReceivedMsgHeader = msgHeader;
      progressUpdateErrorReceivedError = error;
      cond.set();
    }

    @Override
    public void progressMultiUpdateErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      progressUpdateErrorReceived(msgHeader, error, qosProperties);
    }

    @Override
    public void testProgressEmptyBodyUpdateErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      progressUpdateErrorReceived(msgHeader, error, qosProperties);
    }

    @Override
    public synchronized void progressResponseReceived(MALMessageHeader msgHeader, String result, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      progressResponseReceivedMsgHeader = msgHeader;
      cond.set();
    }

    @Override
    public void progressMultiResponseReceived(MALMessageHeader msgHeader, String _String0, Element _Element1, Map qosProperties)
    {
      progressResponseReceived(msgHeader, (String) null, qosProperties);
    }

    @Override
    public void testProgressEmptyBodyResponseReceived(MALMessageHeader msgHeader, Map qosProperties)
    {
      progressResponseReceived(msgHeader, (String) null, qosProperties);
    }

    @Override
    public synchronized void progressResponseErrorReceived(MALMessageHeader msgHeader,
            MALStandardError error, Map qosProperties)
    {
      checkTransactionId(msgHeader);

      ++receivedMessages;
      progressResponseErrorReceivedMsgHeader = msgHeader;
      progressResponseErrorReceivedError = error;
      cond.set();
    }

    @Override
    public void progressMultiResponseErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      progressResponseErrorReceived(msgHeader, error, qosProperties);
    }

    @Override
    public void testProgressEmptyBodyResponseErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties)
    {
      progressResponseErrorReceived(msgHeader, error, qosProperties);
    }

    protected void checkTransactionId(MALMessageHeader msgHeader)
    {
      LoggingBase.logMessage(loggingName + " received T[" + msgHeader.getTransactionId() + " : " + msgHeader.getInteractionType().getOrdinal() + " : " + msgHeader.getInteractionStage().getValue() + "]");
    }
  }
}
