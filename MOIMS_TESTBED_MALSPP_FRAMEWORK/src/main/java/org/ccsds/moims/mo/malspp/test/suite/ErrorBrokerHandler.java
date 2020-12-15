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
package org.ccsds.moims.mo.malspp.test.suite;

import java.util.HashMap;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.broker.MALBrokerHandler;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALDeregisterBody;
import org.ccsds.moims.mo.mal.transport.MALPublishBody;
import org.ccsds.moims.mo.mal.transport.MALPublishRegisterBody;
import org.ccsds.moims.mo.mal.transport.MALRegisterBody;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class ErrorBrokerHandler implements MALBrokerHandler {
  
  public static final String SUBSCRIPTION_RAISING_ERROR = "subscriptionRaisingError";
	
	private MALBrokerBinding binding;
	
	private URI subscriberUri;
	
	private Long transactionId;

	public void handleDeregister(MALInteraction interaction, MALDeregisterBody body)
      throws MALInteractionException, MALException {
	  // TODO Auto-generated method stub
	  
  }

	public void handlePublish(MALInteraction interaction, MALPublishBody body)
      throws MALInteractionException, MALException {
		LoggingBase.logMessage("ErrorBrokerHandler.handlePublish(" + interaction.getMessageHeader() + ')');
		
		// Wait to avoid message order ambiguity
		// Another solution would be that the 'publishUpdates' operation be a SEND
		// instead of a SUBMIT (no acknowledgement)
		try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {}
		
		binding.sendNotifyError(interaction.getOperation(), subscriberUri, transactionId, 
				interaction.getMessageHeader().getDomain(), 
				interaction.getMessageHeader().getNetworkZone(), 
				interaction.getMessageHeader().getSession(), 
				interaction.getMessageHeader().getSessionName(),
				interaction.getMessageHeader().getQoSlevel(), new HashMap(0), 
				interaction.getMessageHeader().getPriority(), 
				new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, null));
  }

	public void handlePublishDeregister(MALInteraction interaction)
      throws MALInteractionException, MALException {
	  // TODO Auto-generated method stub
	  
  }

	public void handlePublishRegister(MALInteraction interaction,
      MALPublishRegisterBody body) throws MALInteractionException, MALException {
	  throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, null));
  }

	public void handleRegister(MALInteraction interaction, MALRegisterBody body)
      throws MALInteractionException, MALException {
	  LoggingBase.logMessage("ErrorBrokerHandler.handleRegister(" + interaction.getMessageHeader() + ')');
	  if (body.getSubscription().getSubscriptionId().getValue().equals(SUBSCRIPTION_RAISING_ERROR)) {
	    throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, null));
	  } else {
		  subscriberUri = interaction.getMessageHeader().getURIFrom();
		  transactionId = interaction.getMessageHeader().getTransactionId();
	  }		
  }

	public void malFinalize(MALBrokerBinding binding) {
	  
  }

	public void malInitialize(MALBrokerBinding binding) {
		this.binding = binding;
  }

}
