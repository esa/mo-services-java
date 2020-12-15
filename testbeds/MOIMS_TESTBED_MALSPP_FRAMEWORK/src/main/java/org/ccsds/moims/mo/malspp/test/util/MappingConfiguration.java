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
package org.ccsds.moims.mo.malspp.test.util;

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UInteger;

public class MappingConfiguration {
  
  private UInteger priority;
  
  private Identifier networkZone;
  
  private Identifier sessionName;
  
  private IdentifierList domain;
  
  private Blob authenticationId;
  
  private boolean varintSupported;
  
  private TimeCode timeCode;
  
  private TimeCode fineTimeCode;
  
  private TimeCode durationCode;

  public MappingConfiguration(UInteger priority, Identifier networkZone,
      Identifier sessionName, IdentifierList domain, Blob authenticationId,
      boolean varintSupported, TimeCode timeCode, TimeCode fineTimeCode,
      TimeCode durationCode) {
    super();
    this.priority = priority;
    this.networkZone = networkZone;
    this.sessionName = sessionName;
    this.domain = domain;
    this.authenticationId = authenticationId;
    this.varintSupported = varintSupported;
    this.timeCode = timeCode;
    this.fineTimeCode = fineTimeCode;
    this.durationCode = durationCode;
  }

  public UInteger getPriority() {
    return priority;
  }

  public void setPriority(UInteger priority) {
    this.priority = priority;
  }

  public Identifier getNetworkZone() {
    return networkZone;
  }

  public void setNetworkZone(Identifier networkZone) {
    this.networkZone = networkZone;
  }

  public Identifier getSessionName() {
    return sessionName;
  }

  public void setSessionName(Identifier sessionName) {
    this.sessionName = sessionName;
  }

  public IdentifierList getDomain() {
    return domain;
  }

  public void setDomain(IdentifierList domain) {
    this.domain = domain;
  }

  public Blob getAuthenticationId() {
    return authenticationId;
  }

  public void setAuthenticationId(Blob authenticationId) {
    this.authenticationId = authenticationId;
  }

  public boolean isVarintSupported() {
    return varintSupported;
  }

  public void setVarintSupported(boolean varintSupported) {
    this.varintSupported = varintSupported;
  }

  public TimeCode getTimeCode() {
    return timeCode;
  }

  public void setTimeCode(TimeCode timeCode) {
    this.timeCode = timeCode;
  }

  public TimeCode getFineTimeCode() {
    return fineTimeCode;
  }

  public void setFineTimeCode(TimeCode fineTimeCode) {
    this.fineTimeCode = fineTimeCode;
  }

  public TimeCode getDurationCode() {
    return durationCode;
  }

  public void setDurationCode(TimeCode durationCode) {
    this.durationCode = durationCode;
  }

}
