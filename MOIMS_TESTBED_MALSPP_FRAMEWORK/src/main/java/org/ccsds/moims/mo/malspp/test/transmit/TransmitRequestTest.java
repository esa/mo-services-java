/*
 Copyright (C) 2014, Deutsches Zentrum für Luft- und Raumfahrt e.V.,
 Author: Stefan Gärtner

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.ccsds.moims.mo.malspp.test.transmit;

import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;
import org.ccsds.moims.mo.malspp.test.suite.LocalMALInstance;

public class TransmitRequestTest {

  private MALMessage templateSendMsg = null;
  private URI origURIFrom = null;
  private Blob origAuthenticationId = null;
  private URI origUriTo = null;
  private Time origTimestamp = null;
  private QoSLevel origQoSlevel = null;
  private UInteger origPriority = null;
  private IdentifierList origDomain = null;
  private Identifier origNetworkZone = null;
  private SessionType origSession = null;
  private Identifier origSessionName = null;
  private InteractionType origInteractionType = null;
  private UOctet origInteractionStage = null;
  private Long origTransactionId = null;
  private UShort origServiceArea = null;
  private UShort origService = null;
  private UShort origOperation = null;
  private UOctet origAreaVersion = null;
  private Boolean origIsErrorMessage = null;

  public boolean createAndSendTemplateSendMessage() throws Exception {
    templateSendMsg = LocalMALInstance.instance().segCounterTestStub().send(null);
    MALMessageHeader header = templateSendMsg.getHeader();
    origURIFrom = header.getURIFrom();
    origAuthenticationId = header.getAuthenticationId();
    origUriTo = header.getURITo();
    origTimestamp = header.getTimestamp();
    origQoSlevel = header.getQoSlevel();
    origPriority = header.getPriority();
    origDomain = header.getDomain();
    origNetworkZone = header.getNetworkZone();
    origSession = header.getSession();
    origSessionName = header.getSessionName();
    origInteractionType = header.getInteractionType();
    origInteractionStage = header.getInteractionStage();
    origTransactionId = header.getTransactionId();
    origServiceArea = header.getServiceArea();
    origService = header.getService();
    origOperation = header.getOperation();
    origAreaVersion = header.getAreaVersion();
    origIsErrorMessage = header.getIsErrorMessage();
    return true;
  }
  
  private void resetHeaderFields() {
    MALMessageHeader header = templateSendMsg.getHeader();
    header.setURIFrom(origURIFrom);
    header.setAuthenticationId(origAuthenticationId);
    header.setURITo(origUriTo);
    header.setTimestamp(origTimestamp);
    header.setQoSlevel(origQoSlevel);
    header.setPriority(origPriority);
    header.setDomain(origDomain);
    header.setNetworkZone(origNetworkZone);
    header.setSession(origSession);
    header.setSessionName(origSessionName);
    header.setInteractionType(origInteractionType);
    header.setInteractionStage(origInteractionStage);
    header.setTransactionId(origTransactionId);
    header.setServiceArea(origServiceArea);
    header.setService(origService);
    header.setOperation(origOperation);
    header.setAreaVersion(origAreaVersion);
    header.setIsErrorMessage(origIsErrorMessage);
  }

  public boolean setUriFrom(String uri) {
    templateSendMsg.getHeader().setURIFrom(new URI(uri));
    return true;
  }

  public boolean setUriTo(String uri) {
    templateSendMsg.getHeader().setURITo(new URI(uri));
    return true;
  }
  
  public boolean setNull(String field) {
    resetHeaderFields();
    MALMessageHeader header = templateSendMsg.getHeader();
    if ("uri from".equalsIgnoreCase(field)) {
      header.setURIFrom(null);
    } else if ("authentication id".equalsIgnoreCase(field)) {
      header.setAuthenticationId(null);
    } else if ("uri to".equalsIgnoreCase(field)) {
      header.setURITo(null);
    } else if ("timestamp".equalsIgnoreCase(field)) {
      header.setTimestamp(null);
    } else if ("qoslevel".equalsIgnoreCase(field)) {
      header.setQoSlevel(null);
    } else if ("priority".equalsIgnoreCase(field)) {
      header.setPriority(null);
    } else if ("domain".equalsIgnoreCase(field)) {
      header.setDomain(null);
    } else if ("network zone".equalsIgnoreCase(field)) {
      header.setNetworkZone(null);
    } else if ("session".equalsIgnoreCase(field)) {
      header.setSession(null);
    } else if ("session name".equalsIgnoreCase(field)) {
      header.setSessionName(null);
    } else if ("interaction type".equalsIgnoreCase(field)) {
      header.setInteractionType(null);
    } else if ("interaction stage".equalsIgnoreCase(field)) {
      header.setInteractionStage(null);
    } else if ("transaction id".equalsIgnoreCase(field)) {
      header.setTransactionId(null);
    } else if ("service area".equalsIgnoreCase(field)) {
      header.setServiceArea(null);
    } else if ("service".equalsIgnoreCase(field)) {
      header.setService(null);
    } else if ("operation".equalsIgnoreCase(field)) {
      header.setOperation(null);
    } else if ("area version".equalsIgnoreCase(field)) {
      header.setAreaVersion(null);
    } else if ("is error message".equalsIgnoreCase(field)) {
      header.setIsErrorMessage(null);
    }
    return true;
  }

  public String transmitRequestReturns() throws Exception {
    MALEndpoint ep = LocalMALInstance.instance().getMalContext().getTransport("malspp").getEndpoint("segmentationCounterSelectTestConsumer");
    try {
      if (null != templateSendMsg.getHeader().getTransactionId()) {
        origTransactionId++;
        templateSendMsg.getHeader().setTransactionId(origTransactionId);
      }
      ep.sendMessage(templateSendMsg);
      return "no transmit error";
    } catch (MALTransmitErrorException ex) {
      if (ex.getStandardError().getErrorNumber().equals(MALHelper.INTERNAL_ERROR_NUMBER)) {
        return "transmit error internal";
      } else {
        return "transmit error " + ex.getMessage();
      }
    } catch (Exception ex) {
      return "error " + ex.getMessage();
    }
  }
}
