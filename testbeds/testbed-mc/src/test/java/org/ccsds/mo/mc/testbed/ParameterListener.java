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
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.parameter.consumer.ParameterAdapter;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;

public class ParameterListener extends ParameterAdapter {

	static class MonitorValueUpdate {
		IdentifierList domain;
		Identifier parameterKey;
		UInteger parameterVersion;
		Time timestamp;
		Time samplingTime;
		ParameterValueData newValue;
		public MonitorValueUpdate(
				IdentifierList domain,
				Identifier parameterKey,
				UInteger parameterVersion,
				Time timestamp,
				Time samplingTime,
				ParameterValueData newValue) {
			this.domain = domain;
			this.parameterKey = parameterKey;
			this.parameterVersion = parameterVersion;
			this.timestamp = timestamp;
			this.samplingTime = samplingTime;
			this.newValue = newValue;
		}
		public String toString() {
			StringBuilder out = new StringBuilder();
			out.append(this.getClass().getName());
			out.append("{domain=").append(domain);
			out.append(", parameterKey=").append(parameterKey);
			out.append(", parameterVersion=").append(parameterVersion);
			out.append(", timestamp=").append(timestamp);
			out.append(", samplingTime=").append(samplingTime);
			out.append(", newValue=").append(newValue);
			out.append("}");
			return out.toString();
		}
	}

	String testException = null;
	MOErrorException error = null;
	boolean setValueAckReceived = false;
	boolean registerAckReceived = false;
	boolean deregisterAckReceived = false;
	ParameterValueList getValueResponse = null;
	ConcurrentLinkedQueue<MonitorValueUpdate> valueUpdates = new ConcurrentLinkedQueue<>();
	ReportConfigurationList getReportConfigResponse = null;
	boolean enableReportingAckReceived = false;
	boolean disableReportingAckReceived = false;
	boolean setReportingPeriodAckReceived = false;

	synchronized void reset() {
		testException = null;
		error = null;
		setValueAckReceived = false;
		registerAckReceived = false;
		deregisterAckReceived = false;
		getValueResponse = null;
		valueUpdates.clear();
		getReportConfigResponse = null;
		enableReportingAckReceived = false;
		disableReportingAckReceived = false;
		setReportingPeriodAckReceived = false;
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
		if (setValueAckReceived) {
			out.append(prefix).append("setValueAckReceived=").append(setValueAckReceived);
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
		if (getValueResponse != null) {
			out.append(prefix).append("getValueResponse=").append(getValueResponse);
			prefix = ", ";
		}
		if (valueUpdates != null) {
			out.append(prefix).append("valueUpdates=").append(valueUpdates);
			prefix = ", ";
		}
		if (getReportConfigResponse != null) {
			out.append(prefix).append("getReportConfigResponse=").append(getReportConfigResponse);
			prefix = ", ";
		}
		if (enableReportingAckReceived) {
			out.append(prefix).append("enableReportingAckReceived=").append(enableReportingAckReceived);
			prefix = ", ";
		}
		if (disableReportingAckReceived) {
			out.append(prefix).append("disableReportingAckReceived=").append(disableReportingAckReceived);
			prefix = ", ";
		}
		if (setReportingPeriodAckReceived) {
			out.append(prefix).append("setReportingPeriodAckReceived=").append(setReportingPeriodAckReceived);
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
	public void getValueResponseReceived(
			MALMessageHeader msgHeader,
			ParameterValueList parameterValues,
			Map qosProperties) {
		System.out.println("Reached: getValueResponseReceived()");
		if (parameterValues == null) {
			// OUT field is not nullable
			addTestException("getValue OUT parameter is null");
		}
		synchronized(this) {
			this.getValueResponse = parameterValues;
			notify();
		}
	}
	@Override
	public void getValueErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: getValueErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}

	@Override
	public void setValueAckReceived(
			MALMessageHeader msgHeader,
			Map qosProperties) {
		System.out.println("Reached: setValueAckReceived()");
		synchronized(this) {
			setValueAckReceived = true;
			notify();
		}
	}
	@Override
	public void setValueErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: setValueErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}

	@Override
	public void monitorValueRegisterAckReceived(
			MALMessageHeader msgHeader,
			Map qosProperties) {
		System.out.println("Reached: monitorValueRegisterAckReceived()");
		synchronized(this) {
			registerAckReceived = true;
			notify();
		}
	}
	@Override
	public void monitorValueRegisterErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: monitorValueRegisterErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}
	@Override
	public void monitorValueNotifyReceived(
			MALMessageHeader msgHeader,
			Identifier subscriptionId,
			UpdateHeader updateHeader,
			Time timestamp,
			Time samplingTime,
			ParameterValueData newValue,
			Map qosProperties) {
		System.out.println("Reached: monitorValueNotifyReceived() -> " + timestamp + " " + samplingTime + " " + newValue);
		// all subscription keys are used in all the tests
		NullableAttributeList keyValues = updateHeader.getKeyValues();
		Identifier parameterKey = null;
		UInteger parameterVersion = null;
		if (keyValues == null || keyValues.size() != 2) {
			addTestException("Unexpected number of subscription key values");
		} else {
			Attribute pKey = keyValues.get(0).getValue();
			if (pKey != null)
				parameterKey = (Identifier) pKey;
			Attribute pVersion = keyValues.get(1).getValue();
			if (pVersion != null)
				parameterVersion = (UInteger) pVersion;
		}
		synchronized(this) {
			valueUpdates.add(new MonitorValueUpdate(
					updateHeader.getDomain(),
					parameterKey, parameterVersion,
					timestamp, samplingTime, newValue));
			notify();
		}
	}
	@Override
	public void monitorValueNotifyErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: monitorValueNotifyErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}
	@Override
	public void monitorValueDeregisterAckReceived(
			MALMessageHeader msgHeader,
			Map qosProperties) {
		System.out.println("Reached: monitorValueDeregisterAckReceived()");
		synchronized(this) {
			deregisterAckReceived = true;
			notify();
		}
	}

	@Override
	public void getReportingConfigurationResponseReceived(
			MALMessageHeader msgHeader,
			ReportConfigurationList reportConfigs,
			Map qosProperties) {
		System.out.println("Reached: getReportingConfigurationResponseReceived()");
		if (reportConfigs == null) {
			// OUT field is not nullable
			addTestException("getReportingConfiguration OUT parameter is null");
		}
		synchronized(this) {
			this.getReportConfigResponse = reportConfigs;
			notify();
		}
	}
	@Override
	public void getReportingConfigurationErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: getReportingConfigurationErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}

	@Override
	public void enableReportingAckReceived(
			MALMessageHeader msgHeader,
			Map qosProperties) {
		System.out.println("Reached: enableReportingAckReceived()");
		synchronized(this) {
			enableReportingAckReceived = true;
			notify();
		}
	}
	@Override
	public void enableReportingErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: enableReportingErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}

	@Override
	public void disableReportingAckReceived(
			MALMessageHeader msgHeader,
			Map qosProperties) {
		System.out.println("Reached: disableReportingAckReceived()");
		synchronized(this) {
			disableReportingAckReceived = true;
			notify();
		}
	}
	@Override
	public void disableReportingErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: disableReportingErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}

	@Override
    public void setReportingPeriodAckReceived(
    		MALMessageHeader msgHeader,
    		Map qosProperties) {
		System.out.println("Reached: setReportingPeriodAckReceived()");
		synchronized(this) {
			setReportingPeriodAckReceived = true;
			notify();
		}
    }
	@Override
    public void setReportingPeriodErrorReceived(
    		MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
		System.out.println("Reached: setReportingPeriodErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
    }
}