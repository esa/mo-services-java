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
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.alert.consumer.AlertAdapter;
import org.ccsds.moims.mo.mc.structures.AlertConfigurationList;

public class AlertListener extends AlertAdapter {

	static class MonitorAlertUpdate {
		IdentifierList domain;
		Identifier alertKey;
		UInteger alertVersion;
		UOctet alertSeverity;
		Time timestamp;
		NullableAttributeList arguments;
		public MonitorAlertUpdate(
				IdentifierList domain,
				Identifier alertKey,
				UInteger alertVersion,
				UOctet alertSeverity,
				Time timestamp,
				NullableAttributeList arguments) {
			this.domain = domain;
			this.alertKey = alertKey;
			this.alertVersion = alertVersion;
			this.alertSeverity = alertSeverity;
			this.timestamp = timestamp;
			this.arguments = arguments;
		}
	}

	String testException = null;
	MOErrorException error = null;
	boolean registerAckReceived = false;
	boolean deregisterAckReceived = false;
	boolean enableGenerationAckReceived = false;
	boolean disableGenerationAckReceived = false;
	AlertConfigurationList getAlertConfigurationResponse = null;
	ConcurrentLinkedQueue<MonitorAlertUpdate> alertUpdates = new ConcurrentLinkedQueue<>();

	synchronized void reset() {
		testException = null;
		error = null;
		registerAckReceived = false;
		deregisterAckReceived = false;
		enableGenerationAckReceived = false;
		disableGenerationAckReceived = false;
		getAlertConfigurationResponse = null;
		alertUpdates.clear();
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
    public void enableGenerationAckReceived(
    		MALMessageHeader msgHeader,
            Map qosProperties) {
		System.out.println("Reached: enableGenerationAckReceived()");
		synchronized(this) {
			enableGenerationAckReceived = true;
			notify();
		}
    }
	@Override
    public void enableGenerationErrorReceived(
    		MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
		System.out.println("Reached: enableGenerationErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
    }

	@Override
    public void disableGenerationAckReceived(
    		MALMessageHeader msgHeader,
            Map qosProperties) {
		System.out.println("Reached: disableGenerationAckReceived()");
		synchronized(this) {
			disableGenerationAckReceived = true;
			notify();
		}
    }
	@Override
    public void disableGenerationErrorReceived(
    		MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
		System.out.println("Reached: disableGenerationErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
    }
    
	@Override
    public void getAlertConfigurationResponseReceived(
    		MALMessageHeader msgHeader,
            AlertConfigurationList alertConfigs,
            java.util.Map qosProperties) {
		System.out.println("Reached: getAlertConfigurationResponseReceived()");
		if (alertConfigs == null) {
			// OUT field is not nullable
			addTestException("getAlertConfiguration OUT parameter is null");
		}
		synchronized(this) {
			this.getAlertConfigurationResponse = alertConfigs;
			notify();
		}
    }
	@Override
    public void getAlertConfigurationErrorReceived(
    		MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
		System.out.println("Reached: getAlertConfigurationErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
    }
    
	@Override
    public void monitorAlertRegisterAckReceived(
    		MALMessageHeader msgHeader,
            Map qosProperties) {
		System.out.println("Reached: monitorAlertRegisterAckReceived()");
		synchronized(this) {
			registerAckReceived = true;
			notify();
		}
	}
	@Override
    public void monitorAlertRegisterErrorReceived(
    		MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            Map qosProperties) {
		System.out.println("Reached: monitorAlertRegisterErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}
	@Override
    public void monitorAlertNotifyReceived(
			MALMessageHeader msgHeader,
			Identifier subscriptionId,
			UpdateHeader updateHeader,
			Time timestamp,
			NullableAttributeList arguments,
			Map qosProperties) {
		System.out.println("Reached: monitorAlertNotifyReceived() -> " + timestamp + " " + arguments);
		// all subscription keys are used in all the tests
		NullableAttributeList keyValues = updateHeader.getKeyValues();
		Identifier alertKey = null;
		UInteger alertVersion = null;
		UOctet alertSeverity = null;
		if (keyValues == null || keyValues.size() != 3) {
			addTestException("Unexpected number of subscription key values");
		} else {
			Attribute aKey = keyValues.get(0).getValue();
			if (aKey != null)
				alertKey = (Identifier) aKey;
			Attribute aVersion = keyValues.get(1).getValue();
			if (aVersion != null)
				alertVersion = (UInteger) aVersion;
			Attribute aSeverity = keyValues.get(2).getValue();
			if (aSeverity != null)
				alertSeverity = (UOctet) aSeverity;
		}
		synchronized(this) {
			alertUpdates.add(new MonitorAlertUpdate(
					updateHeader.getDomain(),
					alertKey, alertVersion, alertSeverity,
					timestamp, arguments));
			notify();
		}
	}
	@Override
	public void monitorAlertNotifyErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: monitorAlertNotifyErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}
	@Override
	public void monitorAlertDeregisterAckReceived(
			MALMessageHeader msgHeader,
			Map qosProperties) {
		System.out.println("Reached: monitorAlertDeregisterAckReceived()");
		synchronized(this) {
			deregisterAckReceived = true;
			notify();
		}
	}

}