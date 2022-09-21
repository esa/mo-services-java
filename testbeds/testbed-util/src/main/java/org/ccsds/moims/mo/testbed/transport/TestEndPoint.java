/*******************************************************************************
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
 *******************************************************************************/
package org.ccsds.moims.mo.testbed.transport;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.*;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class TestEndPoint implements MALEndpoint
{
  private MALEndpoint delegate;
  private ReceivedMessageInterceptor receivedMessageInterceptor;

  TestEndPoint(MALEndpoint delegate)
  {
    this.delegate = delegate;
  }

  public void startMessageDelivery() throws MALException
  {
    delegate.startMessageDelivery();
  }

  public void close() throws MALException
  {
    delegate.close();
  }

  public MALMessage createMessage(Blob authenticationId, URI uRITo,
      Time timestamp, QoSLevel qoSlevel, UInteger priority,
      IdentifierList domain, Identifier networkZone, SessionType session,
      Identifier sessionName, InteractionType interactionType,
      UOctet interactionStage, Long transactionId, UShort serviceArea,
      UShort service, UShort operation, UOctet serviceVersion,
      Boolean isErrorMessage, Map qosProperties, Object... body)
      throws MALException
  {
    return delegate.createMessage(authenticationId, uRITo, timestamp, qoSlevel,
        priority, domain, networkZone, session, sessionName, interactionType,
        interactionStage, transactionId, serviceArea, service, operation,
        serviceVersion, isErrorMessage, qosProperties, body);
  }

  public MALMessage createMessage(Blob authenticationId, URI uriTo, Time timestamp, QoSLevel qosLevel, UInteger priority, IdentifierList domain, Identifier networkZone, SessionType session, Identifier sessionName, Long transactionId, Boolean isErrorMessage, MALOperation op, UOctet interactionStage, Map qosProperties, Object... body) throws IllegalArgumentException, MALException
  {
    return delegate.createMessage(authenticationId, uriTo, timestamp, qosLevel,
        priority, domain, networkZone, session, sessionName,
        transactionId, isErrorMessage, op, interactionStage, qosProperties, body);
  }

  public String getLocalName()
  {
    return delegate.getLocalName();
  }

  public URI getURI()
  {
    return delegate.getURI();
  }

  public void sendMessage(MALMessage msg) throws MALTransmitErrorException, MALException
  {
    TransportInterceptor.instance().messageSent(msg);

    if (null != TransportInterceptor.instance().getEndpointSendInterceptor())
    {
      TransportInterceptor.instance().getEndpointSendInterceptor().sendMessage(this, msg);
    }

    TransportInterceptor.instance().incrementTransmitRequestCount(
            msg.getHeader().getInteractionType());
    delegate.sendMessage(msg);
    TransportInterceptor.instance().incrementTransmitResponseCount(
            msg.getHeader().getInteractionType());
  }

  public static MALMessageHeader createErrorHeader(
          MALMessageHeader initialHeader,
          Blob authId,
          UOctet stage)
  {
    TestMessageHeader res = new TestMessageHeader(
            initialHeader.getURITo(),
            authId,
            initialHeader.getURIFrom(),
            new Time(System.currentTimeMillis()),
            initialHeader.getQoSlevel(),
            initialHeader.getPriority(),
            initialHeader.getDomain(),
            initialHeader.getNetworkZone(),
            initialHeader.getSession(),
            initialHeader.getSessionName(),
            initialHeader.getInteractionType(),
            stage,
            initialHeader.getTransactionId(),
            initialHeader.getServiceArea(),
            initialHeader.getService(),
            initialHeader.getOperation(),
            initialHeader.getAreaVersion(),
            Boolean.TRUE);
    return res;
  }

  public void sendMessages(MALMessage[] messages) 
      throws MALTransmitMultipleErrorException, MALException
  {
    if (null != TransportInterceptor.instance().getEndpointSendInterceptor())
    {
      TransportInterceptor.instance().getEndpointSendInterceptor().sendMessages(this, messages);
    }

    doSendMessages(messages);
  }

  public void doSendMessages(MALMessage[] messages) throws MALTransmitMultipleErrorException, MALException
  {
    TransportInterceptor.instance().incrementTransmitMultipleRequestCount();
    TransportInterceptor.instance().messagesSent(messages);
    delegate.sendMessages(messages);
    TransportInterceptor.instance().incrementTransmitMultipleResponseCount();
  }

  public void setMessageListener(MALMessageListener listener) throws MALException
  {
    this.receivedMessageInterceptor = new ReceivedMessageInterceptor(listener);
    delegate.setMessageListener(receivedMessageInterceptor);
  }

  public MALMessage createTestMessage(MALMessageHeader header, Element bodyElement, Map props)
  {
    return new TestMessage(header, new TestMessageBody(bodyElement), props);
  }
  
  public MALMessage createTestMessage(MALMessageHeader header, Identifier id, 
      UpdateHeaderList updateHeaders, List[] updateLists, Map props)
  {
    return new TestMessage(header, new TestNotifyBody(id, updateHeaders, updateLists), props);
  }
  
  public MALMessage createTestMessage(MALMessageHeader header, MALStandardError error, Map props)
  {
    return new TestMessage(header, new TestErrorBody(error.getErrorNumber(), error.getExtraInformation()), props);
  }

  public void receiveMultiple(MALMessage[] messages)
  {
    receivedMessageInterceptor.receiveMultiple(TestEndPoint.this, messages);
  }

  public void receive(MALMessage message)
  {
    receivedMessageInterceptor._onMessage(TestEndPoint.this, message);
  }

  public void blockReceivedMessages()
  {
    receivedMessageInterceptor.blockReceivedMessages();
  }

  public void releaseReceivedMessages()
  {
    receivedMessageInterceptor.releaseReceivedMessages();
  }

  public MALEndpoint getDelegate()
  {
    return delegate;
  }

  public ReceivedMessageInterceptor getReceivedMessageInterceptor()
  {
    return receivedMessageInterceptor;
  }

  public class ReceivedMessageInterceptor implements MALMessageListener
  {
    MALMessageListener listener;
    final List blockedMessageQueue = new LinkedList();
    boolean isBlocked = false;

    ReceivedMessageInterceptor(MALMessageListener listener)
    {
      this.listener = listener;
    }

    public void blockReceivedMessages()
    {
      isBlocked = true;
    }

    public void releaseReceivedMessages()
    {
      final List localBlockedMessageQueue = new LinkedList();

      synchronized (this)
      {
        isBlocked = false;
        localBlockedMessageQueue.addAll(blockedMessageQueue);
        blockedMessageQueue.clear();
      }

      for (int i = 0; i < localBlockedMessageQueue.size(); i++)
      {
        Object object = localBlockedMessageQueue.get(i);

        if (object instanceof MALMessage)
        {
          onMessage(TestEndPoint.this, (MALMessage) object);
        }
        else
        {
          onMessages(TestEndPoint.this, (MALMessage[]) object);
        }
      }
    }

    public void onInternalError(MALEndpoint callingEndpoint, Throwable error)
    {
      error.printStackTrace();
    }

    public void onTransmitError(MALEndpoint callingEndpoint, MALMessageHeader srcMessageHeader, MALStandardError err, Map qosMap)
    {
      throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onMessage(MALEndpoint callingEndpoint, MALMessage msg)
    {
      boolean selfProcess = false;

      synchronized (this)
      {
        if (isBlocked)
        {
          blockedMessageQueue.add(msg);
        }
        else
        {
          selfProcess = true;
        }
      }

      if (selfProcess)
      {
        _onMessage(callingEndpoint, msg);
      }
    }

    public void onMessages(MALEndpoint callingEndpoint, MALMessage[] messages)
    {
      boolean selfProcess = false;

      synchronized (this)
      {
        if (isBlocked)
        {
          blockedMessageQueue.add(messages);
        }
        else
        {
          selfProcess = true;
        }
      }

      if (selfProcess)
      {
        _onMessages(callingEndpoint, messages);
      }
    }

    protected void _onMessage(MALEndpoint callingEndpoint, MALMessage msg)
    {
      TransportInterceptor.instance().incrementReceiveCount(msg.getHeader().getInteractionType());
      TransportInterceptor.instance().messageReceived(msg);
      listener.onMessage(callingEndpoint, msg);
    }

    protected void _onMessages(MALEndpoint callingEndpoint, MALMessage[] messages)
    {
      TransportInterceptor.instance().messagesReceived(messages);
      listener.onMessages(callingEndpoint, messages);
    }

    public void receiveMultiple(MALEndpoint callingEndpoint, MALMessage[] messages)
    {
      LoggingBase.logMessage("ReceivedMessageInterceptor.receiveMultiple()");
      listener.onMessages(callingEndpoint, messages);
    }
  }

  static class TestMessage implements MALMessage
  {
    private MALMessageHeader header;
    private MALMessageBody body;
    private Map props;

    public TestMessage(MALMessageHeader header, MALMessageBody body, Map props)
    {
      super();
      this.header = header;
      this.body = body;
      this.props = props;
    }

    public void free() throws MALException
    {
      // Do nothing
    }
    
    public MALMessageHeader getHeader()
    {
      return header;
    }
    
    public Map getQoSProperties()
    {
      return props;
    }

    public MALMessageBody getBody()
    {
      return body;
    }

    @Override
    public String toString()
    {
      return "TestMessage [header=" + header + ", body=" + body + ", props="
          + props + "]";
    }
  }

  public void stopMessageDelivery() throws MALException {
    delegate.stopMessageDelivery();
  }

	public MALMessage createMessage(Blob arg0, URI arg1, Time arg2,
      QoSLevel arg3, UInteger arg4, IdentifierList arg5, Identifier arg6,
      SessionType arg7, Identifier arg8, Long arg9, Boolean arg10,
      MALOperation arg11, UOctet arg12, Map arg13, MALEncodedBody arg14)
      throws IllegalArgumentException, MALException {
	  return delegate.createMessage(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14);
  }

	public MALMessage createMessage(Blob arg0, URI arg1, Time arg2,
      QoSLevel arg3, UInteger arg4, IdentifierList arg5, Identifier arg6,
      SessionType arg7, Identifier arg8, InteractionType arg9, UOctet arg10,
      Long arg11, UShort arg12, UShort arg13, UShort arg14, UOctet arg15,
      Boolean arg16, Map arg17, MALEncodedBody arg18)
      throws IllegalArgumentException, MALException {
	  return delegate.createMessage(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18);
  }
}
