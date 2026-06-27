/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Testbed - M&C
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
package org.ccsds.mo.mc.testbed;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.action.consumer.ActionAdapter;
import org.ccsds.moims.mo.mc.action.consumer.MonitorExecutionSubscriptionKeys;
import org.ccsds.moims.mo.mc.structures.ActionCategory;
import org.ccsds.moims.mo.mc.structures.ActionEvent;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;

public class ActionListener extends ActionAdapter {

	static class MonitorExecutionUpdate {
		IdentifierList domain;
		Long requestId;
		Identifier actionKey;
		ActionCategory actionCategory;
		ActionEvent progressEvent;
		public MonitorExecutionUpdate(
				IdentifierList domain,
				Long requestId,
				Identifier actionKey,
				ActionCategory actionCategory,
				ActionEvent progressEvent) {
			this.domain = domain;
			this.requestId = requestId;
			this.actionKey = actionKey;
			this.actionCategory = actionCategory;
			this.progressEvent = progressEvent;
		}
		public String toString() {
			StringBuilder out = new StringBuilder();
			out.append(this.getClass().getName());
			out.append("{domain=").append(domain);
			out.append(", requestId=").append(requestId);
			out.append(", actionKey=").append(actionKey);
			out.append(", actionCategory=").append(actionCategory);
			out.append(", progressEvent=").append(progressEvent);
			out.append("}");
			return out.toString();
		}
	}

	String testException = null;
	MOErrorException error = null;
	boolean executeAckReceived = false;
	boolean registerAckReceived = false;
	boolean deregisterAckReceived = false;
	ConcurrentLinkedQueue<MonitorExecutionUpdate> executionUpdates = new ConcurrentLinkedQueue<>();

	synchronized void reset() {
		testException = null;
		error = null;
		executeAckReceived = false;
		registerAckReceived = false;
		deregisterAckReceived = false;
		executionUpdates.clear();
	}

	public String toString() {
		StringBuilder out = new StringBuilder();
		String prefix="";
		out.append(this.getClass().getName());
		if (testException != null) {
			out.append(prefix).append("testException=").append(testException);
			prefix = ", ";
		}
		if (error != null) {
			out.append(prefix).append("error=").append(error);
			prefix = ", ";
		}
		if (executeAckReceived) {
			out.append(prefix).append("executeAckReceived=").append(executeAckReceived);
			prefix = ", ";
		}
		if (registerAckReceived) {
			out.append(prefix).append("registerAckReceived=").append(registerAckReceived);
			prefix = ", ";
		}
		if (deregisterAckReceived) {
			out.append(prefix).append("deregisterAckReceived=").append(deregisterAckReceived);
			prefix = ", ";
		}
		if (executionUpdates != null) {
			out.append(prefix).append("executionUpdates=").append(executionUpdates);
			prefix = ", ";
		}
		out.append("}");
		return out.toString();
	}

	public boolean hasError() {
		return testException != null || error != null;
	}
	public String getError() {
		if (error != null)
			return error.toString();
		return testException;
	}

	private void addTestException(String testException) {
		if (this.testException == null)
			this.testException = testException;
	}

	@Override
	public void executeAckReceived(
			MALMessageHeader msgHeader,
			Map qosProperties) {
		System.out.println("Reached: executeAckReceived()");
		synchronized(this) {
			executeAckReceived = true;
			notify();
		}
	}
	@Override
	public void executeErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: executeErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}

	@Override
	public void monitorExecutionRegisterAckReceived(
			MALMessageHeader msgHeader,
			Map qosProperties) {
		System.out.println("Reached: monitorExecutionRegisterAckReceived()");
		synchronized(this) {
			registerAckReceived = true;
			notify();
		}
	}
	@Override
	public void monitorExecutionRegisterErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: monitorExecutionRegisterErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}
	@Override
	public void monitorExecutionNotifyReceived(
			MALMessageHeader msgHeader,
			Identifier subscriptionId,
			UpdateHeader updateHeader,
			MonitorExecutionSubscriptionKeys keys,
			ActionEvent progressEvent,
			Map qosProperties) {
		System.out.println("Reached: monitorExecutionNotifyReceived() -> " + progressEvent);
		// all subscription keys are used in all the tests
		NullableAttributeList keyValues = updateHeader.getKeyValues();
		Long requestId = null;
		Identifier actionKey = null;
		ActionCategory actionCategory = null;
		if (keyValues == null || keyValues.size() != 3) {
			addTestException("Unexpected number of subscription key values");
		} else {
			Attribute key = keyValues.get(0).getValue();
			if (key != null)
				requestId = ((Union) key).getLongValue();
			key = keyValues.get(1).getValue();
			if (key != null)
				actionKey = (Identifier) key;
			key = keyValues.get(2).getValue();
			if (key != null)
				actionCategory = new ActionCategory(((UOctet) key).getValue());
		}
		synchronized(this) {
			executionUpdates.add(new MonitorExecutionUpdate(
					updateHeader.getDomain(),
					requestId,
					actionKey,
					actionCategory,
					progressEvent));
			notify();
		}
	}
	@Override
	public void monitorExecutionNotifyErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: monitorExecutionNotifyErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}
	@Override
	public void monitorExecutionDeregisterAckReceived(
			MALMessageHeader msgHeader,
			Map qosProperties) {
		System.out.println("Reached: monitorExecutionDeregisterAckReceived()");
		synchronized(this) {
			deregisterAckReceived = true;
			notify();
		}
	}
}
