/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO HTTP Transport Framework
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
package esa.mo.mal.transport.http.test;

import java.util.Map;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.body.LazyMessageBody;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

public class GENMessageBuilder {

  private Identifier from;
  private Blob authenticationId;
  private Identifier to;
  private Time timestamp;
  private InteractionType interactionType;
  private UOctet interactionStage;
  private Long transactionId;
  private UShort serviceArea;
  private UShort service;
  private UShort operation;
  private UOctet serviceVersion;
  private Boolean isErrorMessage = false;
  private NamedValueList supplements = new NamedValueList();

  private Map qosProperties;
//  private MALElementStreamFactory streamFactory = new HTTPXMLStreamFactory();
  private Object[] body = new Object[0];

  public GENMessageBuilder() {
  }

  public GENMessage build() throws MALInteractionException {
    MALMessageHeader header = new MALMessageHeader(from, authenticationId, to, timestamp, interactionType, interactionStage, transactionId,
        serviceArea, service, operation, serviceVersion, isErrorMessage, supplements);
    LazyMessageBody msg = LazyMessageBody.createMessageBody(header, null, body);
    return new GENMessage(header, msg, null, qosProperties);
  }

  public GENMessageBuilder from(final Identifier from) {
    this.from = from;
    return this;
  }

  public GENMessageBuilder authenticationId(final Blob authenticationId) {
    this.authenticationId = authenticationId;
    return this;
  }

  public GENMessageBuilder to(final Identifier to) {
    this.to = to;
    return this;
  }

  public GENMessageBuilder timestamp(final Time timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public GENMessageBuilder interactionType(final InteractionType interactionType) {
    this.interactionType = interactionType;
    return this;
  }

  public GENMessageBuilder interactionStage(final UOctet interactionStage) {
    this.interactionStage = interactionStage;
    return this;
  }

  public GENMessageBuilder transactionId(final Long transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  public GENMessageBuilder serviceArea(final UShort serviceArea) {
    this.serviceArea = serviceArea;
    return this;
  }

  public GENMessageBuilder service(final UShort service) {
    this.service = service;
    return this;
  }

  public GENMessageBuilder operation(final UShort operation) {
    this.operation = operation;
    return this;
  }

  public GENMessageBuilder serviceVersion(final UOctet serviceVersion) {
    this.serviceVersion = serviceVersion;
    return this;
  }

  public GENMessageBuilder isErrorMessage(final Boolean isErrorMessage) {
    this.isErrorMessage = isErrorMessage;
    return this;
  }

  public GENMessageBuilder supplements(final NamedValueList supplements) {
    this.supplements = supplements;
    return this;
  }

  public GENMessageBuilder qosProperties(Map qosProperties) {
    this.qosProperties = qosProperties;
    return this;
  }

  public GENMessageBuilder body(Object... body) {
    this.body = body;
    return this;
  }
}
