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
package org.ccsds.moims.mo.malspp.test.uri;

import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;
import org.ccsds.moims.mo.malspp.test.suite.LocalMALInstance;

public class UriTest {

  private MALMessage templateSendMsg = null;

  public boolean createAndSendTemplateSendMessage() throws Exception {
    templateSendMsg = LocalMALInstance.instance().segCounterTestStub().send(null);
    return true;
  }

  public boolean setUriFrom(String uri) {
    templateSendMsg.getHeader().setURIFrom(new URI(uri));
    return true;
  }

  public boolean setUriTo(String uri) {
    templateSendMsg.getHeader().setURITo(new URI(uri));
    return true;
  }

  public String transmitRequestReturns() throws Exception {
    MALEndpoint ep = LocalMALInstance.instance().getMalContext().getTransport("malspp").getEndpoint("segmentationCounterSelectTestConsumer");
    try {
      templateSendMsg.getHeader().setTransactionId(templateSendMsg.getHeader().getTransactionId() + 1);
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
