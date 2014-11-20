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
package org.ccsds.moims.mo.malspp.test.suite;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.malprototype.iptest.body.RequestMultiResponse;
import org.ccsds.moims.mo.malprototype.iptest.provider.IPTestInheritanceSkeleton;
import org.ccsds.moims.mo.malprototype.iptest.provider.InvokeInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.InvokeMultiInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.ProgressInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.ProgressMultiInteraction;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestDefinition;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestResult;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malspp.test.segmentation.MalSppSegmentationTest;
import org.ccsds.moims.mo.malspp.test.sppinterceptor.SPPInterceptorSocket;

public class IPSegmentationTestHandlerImpl extends IPTestInheritanceSkeleton {

  ProgressMultiInteraction progressA;
  ProgressMultiInteraction progressB;

  public void send(IPTestDefinition _IPTestDefinition0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void testSubmit(IPTestDefinition _IPTestDefinition0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public String request(IPTestDefinition _IPTestDefinition0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void invoke(IPTestDefinition _IPTestDefinition0, InvokeInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void progress(IPTestDefinition _IPTestDefinition0, ProgressInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public IPTestResult getResult(Element _Element0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void publishUpdates(TestPublishUpdate _TestPublishUpdate0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void publishRegister(TestPublishRegister _TestPublishRegister0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void publishDeregister(TestPublishDeregister _TestPublishDeregister0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void testMultipleNotify(TestPublishUpdate _TestPublishUpdate0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  // triggers updates (elem == 1, 2 or 3) or response (elem == 0) to be sent
  public void sendMulti(IPTestDefinition ipTestDefinition, Element elem, MALInteraction interaction) throws MALInteractionException, MALException {
    String identifier = ipTestDefinition.getProcedureName();
    ProgressMultiInteraction progressInteraction = null;
    if (identifier.equals("A")) {
      progressInteraction = progressA;
    } else if (identifier.equals("B")) {
      progressInteraction = progressB;
    } else {
      throw new MALException("Invalid identifier " + identifier);
    }
    int nPackets = ((Union) elem).getIntegerValue();

    Element payload = null;
    switch (nPackets) {
      case 0:
        progressInteraction.sendResponse(null, null);
        return;
      case 1:
        payload = MalSppSegmentationTest.update1Packets;
        break;
      case 2:
        payload = MalSppSegmentationTest.update2Packets;
        break;
      case 3:
        payload = MalSppSegmentationTest.update3Packets;
        break;
    }
    progressInteraction.sendUpdate(nPackets, payload);
  }

  // used for enabling/disabling scrambling patterns or delays for messages sent to local
  // NB: already the ACK message returned for this SUBMIT IP is affected
  public void submitMulti(IPTestDefinition ipTestDefinition, Element elem, MALInteraction interaction) throws MALInteractionException, MALException {
    try {
      String proc = ipTestDefinition.getProcedureName();
      if (proc.equals("setScramblingPattern")) {
        MalSppSegmentationTest.setScramblingPattern(((StringList) elem).toArray(new String[]{}));
      } else if (proc.equals("setDelay")) {
        int index = ((IntegerList) elem).get(0);
        int delay = ((IntegerList) elem).get(1);
        SPPInterceptorSocket.setDelay(delay * 1000, index);
      } else {
        throw new Exception("Not a valid procedure name.");
      }
    } catch (Exception ex) {
      throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, ex.toString()));
    }
  }

  public RequestMultiResponse requestMulti(IPTestDefinition _IPTestDefinition0, Element _Element1, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void invokeMulti(IPTestDefinition _IPTestDefinition0, Element _Element1, InvokeMultiInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void progressMulti(IPTestDefinition ipTestDefinition, Element elem, ProgressMultiInteraction interaction) throws MALInteractionException, MALException {
    String identifier = ((Union) elem).getStringValue();
    if (identifier.equals("A")) {
      progressA = interaction;
    } else if (identifier.equals("B")) {
      progressB = interaction;
    } else {
      interaction.sendError(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, "Invalid identifier " + identifier));
    }
    interaction.sendAcknowledgement(null, null);
  }

}
