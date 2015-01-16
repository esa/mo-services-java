/*******************************************************************************
 * Copyright or � or Copr. CNES
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
package org.ccsds.moims.mo.malspp.test.suite;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.malprototype.iptest.body.RequestMultiResponse;
import org.ccsds.moims.mo.malprototype.iptest.provider.IPTestInheritanceSkeleton;
import org.ccsds.moims.mo.malprototype.iptest.provider.InvokeInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.InvokeMultiInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.ProgressInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.ProgressMultiInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.TestInvokeEmptyBodyInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.TestProgressEmptyBodyInteraction;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestDefinition;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestResult;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malspp.test.util.TestHelper;

public class QosPropertiesIPTestHandler extends IPTestInheritanceSkeleton {
  
  public static final String AUTHENTICATION_ID_FLAG_TEST = "authenticationIdFlagTest";
  public static final String DOMAIN_FLAG_TEST = "domainFlagTest";
  public static final String NETWORK_ZONE_FLAG_TEST = "networkZoneFlagTest";
  public static final String PRIORITY_FLAG_TEST = "priorityFlagTest";
  public static final String SESSION_NAME_FLAG_TEST = "sessionNameFlagTest";
  public static final String TIMESTAMP_FLAG_TEST = "timestampFlagTest";

	public QosPropertiesIPTestHandler() {}

	public IPTestResult getResult(Element arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub
		return null;
	}

	public void invoke(IPTestDefinition arg0, InvokeInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void invokeMulti(IPTestDefinition arg0, Element arg1,
	    InvokeMultiInteraction arg2) throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void progress(IPTestDefinition arg0, ProgressInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void progressMulti(IPTestDefinition arg0, Element arg1,
	    ProgressMultiInteraction arg2) throws MALInteractionException,
	    MALException {
		// TODO Auto-generated method stub

	}

	public void publishDeregister(TestPublishDeregister arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void publishRegister(TestPublishRegister arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void publishUpdates(TestPublishUpdate testPublishUpdate,
	    MALInteraction interaction) throws MALInteractionException, MALException {
	  // TODO Auto-generated method stub
	  
	}

	public String request(IPTestDefinition ipTestDefinition, MALInteraction interaction)
	    throws MALInteractionException, MALException {
	  String procedureName = ipTestDefinition.getProcedureName();
	  if (AUTHENTICATION_ID_FLAG_TEST.equals(procedureName)) {
	    interaction.setQoSProperty(TestHelper.AUTHENTICATION_ID_FLAG, Boolean.FALSE);
	  } else if (DOMAIN_FLAG_TEST.equals(procedureName)) {
      interaction.setQoSProperty(TestHelper.DOMAIN_FLAG, Boolean.FALSE);
    } else if (NETWORK_ZONE_FLAG_TEST.equals(procedureName)) {
      interaction.setQoSProperty(TestHelper.NETWORK_ZONE_FLAG, Boolean.FALSE);
    } else if (PRIORITY_FLAG_TEST.equals(procedureName)) {
      interaction.setQoSProperty(TestHelper.PRIORITY_FLAG, Boolean.FALSE);
    } else if (SESSION_NAME_FLAG_TEST.equals(procedureName)) {
      interaction.setQoSProperty(TestHelper.SESSION_NAME_FLAG, Boolean.FALSE);
    } else if (TIMESTAMP_FLAG_TEST.equals(procedureName)) {
      interaction.setQoSProperty(TestHelper.TIMESTAMP_FLAG, Boolean.FALSE);
    } else {
      throw new MALException("Unexpected procedure: " + procedureName);
    }
		return "qosPropertiesTest";
	}

	public RequestMultiResponse requestMulti(IPTestDefinition arg0, Element arg1,
	    MALInteraction arg2) throws MALInteractionException, MALException {
		// TODO Auto-generated method stub
		return null;
	}

	public void send(IPTestDefinition arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void sendMulti(IPTestDefinition arg0, Element arg1, MALInteraction arg2)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void submitMulti(IPTestDefinition arg0, Element arg1,
	    MALInteraction arg2) throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void testMultipleNotify(TestPublishUpdate arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void testSubmit(IPTestDefinition arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void testRequestEmptyBody(IPTestDefinition _IPTestDefinition0, MALInteraction interaction) throws MALInteractionException, MALException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void testInvokeEmptyBody(IPTestDefinition _IPTestDefinition0, TestInvokeEmptyBodyInteraction interaction) throws MALInteractionException, MALException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void testProgressEmptyBody(IPTestDefinition _IPTestDefinition0, TestProgressEmptyBodyInteraction interaction) throws MALInteractionException, MALException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
