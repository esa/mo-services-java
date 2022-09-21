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
package org.ccsds.moims.mo.mal.test.patterns;

import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.SessionType;;

public class PublishInteractionListenerKey
{
  private IdentifierList domain;
  private Identifier networkZone;
  private SessionType session;
  private Identifier sessionName;

  public PublishInteractionListenerKey(IdentifierList domain,
          Identifier networkZone, SessionType session, Identifier sessionName)
  {
    super();
    this.domain = domain;
    this.networkZone = networkZone;
    this.session = session;
    this.sessionName = sessionName;
  }

  public Identifier getSessionName()
  {
    return sessionName;
  }

  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((domain == null) ? 0 : domain.hashCode());
    result = prime * result + ((networkZone == null) ? 0 : networkZone.hashCode());
    result = prime * result + ((session == null) ? 0 : session.hashCode());
    result = prime * result + ((sessionName == null) ? 0 : sessionName.hashCode());
    return result;
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    PublishInteractionListenerKey other = (PublishInteractionListenerKey) obj;
    if (domain == null)
    {
      if (other.domain != null)
      {
        return false;
      }
    }
    else if (!domain.equals(other.domain))
    {
      return false;
    }
    if (networkZone == null)
    {
      if (other.networkZone != null)
      {
        return false;
      }
    }
    else if (!networkZone.equals(other.networkZone))
    {
      return false;
    }
    if (session == null)
    {
      if (other.session != null)
      {
        return false;
      }
    }
    else if (!session.equals(other.session))
    {
      return false;
    }
    if (sessionName == null)
    {
      if (other.sessionName != null)
      {
        return false;
      }
    }
    else if (!sessionName.equals(other.sessionName))
    {
      return false;
    }
    return true;
  }
}
