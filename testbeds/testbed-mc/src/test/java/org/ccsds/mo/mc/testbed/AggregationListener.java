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
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter;
import org.ccsds.moims.mo.mc.structures.AggregationDefinitionList;
import org.ccsds.moims.mo.mc.structures.AggregationValueList;
import org.ccsds.moims.mo.mc.structures.ParameterValueDataList;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;

public class AggregationListener extends AggregationAdapter {

	static class MonitorValueUpdate {
		IdentifierList domain;
		Identifier aggregationKey;
		UInteger aggregationVersion;
		Time timestamp;
		ParameterValueDataList values;
		public MonitorValueUpdate(
				IdentifierList domain,
				Identifier aggregationKey,
				UInteger aggregationVersion,
				Time timestamp,
				ParameterValueDataList values) {
			this.domain = domain;
			this.aggregationKey = aggregationKey;
			this.aggregationVersion = aggregationVersion;
			this.timestamp = timestamp;
			this.values = values;
		}
		public String toString() {
			StringBuilder out = new StringBuilder();
			out.append(this.getClass().getName());
			out.append("{domain=").append(domain);
			out.append(", aggregationKey=").append(aggregationKey);
			out.append(", aggregationVersion=").append(aggregationVersion);
			out.append(", timestamp=").append(timestamp);
			out.append(", values=").append(values);
			out.append("}");
			return out.toString();
		}
	}

	String testException = null;
	MOErrorException error = null;
	boolean registerAckReceived = false;
	boolean deregisterAckReceived = false;
	AggregationValueList getValueResponse = null;
	ConcurrentLinkedQueue<MonitorValueUpdate> valueUpdates = new ConcurrentLinkedQueue<>();
	ReportConfigurationList getReportConfigResponse = null;
	boolean enableReportingAckReceived = false;
	boolean disableReportingAckReceived = false;
	boolean setReportingPeriodAckReceived = false;
	AggregationDefinitionList listDefinitionResponse = null;
	boolean addAggregationAckReceived = false;
	boolean removeAggregationAckReceived = false;

	synchronized void reset() {
		testException = null;
		error = null;
		registerAckReceived = false;
		deregisterAckReceived = false;
		getValueResponse = null;
		valueUpdates.clear();
		getReportConfigResponse = null;
		enableReportingAckReceived = false;
		disableReportingAckReceived = false;
		setReportingPeriodAckReceived = false;
		listDefinitionResponse = null;
		addAggregationAckReceived = false;
		removeAggregationAckReceived = false;
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
		if (listDefinitionResponse != null) {
			out.append(prefix).append("listDefinitionResponse=").append(listDefinitionResponse);
			prefix = ", ";
		}
		if (addAggregationAckReceived) {
			out.append(prefix).append("addAggregationAckReceived=").append(addAggregationAckReceived);
			prefix = ", ";
		}
		if (removeAggregationAckReceived) {
			out.append(prefix).append("removeAggregationAckReceived=").append(removeAggregationAckReceived);
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
            AggregationValueList values,
            Map qosProperties) {
		System.out.println("Reached: getValueResponseReceived()");
		if (values == null) {
			// OUT field is not nullable
			addTestException("getValueResponse OUT parameter is null");
		}
		synchronized(this) {
			this.getValueResponse = values;
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
			ParameterValueDataList values,
			Map qosProperties) {
		System.out.println("Reached: monitorValueNotifyReceived() -> " + timestamp + " " + values);
		// all subscription keys are used in all the tests
		NullableAttributeList keyValues = updateHeader.getKeyValues();
		Identifier aggregationKey = null;
		UInteger aggregationVersion = null;
		if (keyValues == null || keyValues.size() != 2) {
			addTestException("Unexpected number of subscription key values");
		} else {
			Attribute aKey = keyValues.get(0).getValue();
			if (aKey != null)
				aggregationKey = (Identifier) aKey;
			Attribute aVersion = keyValues.get(1).getValue();
			if (aVersion != null)
				aggregationVersion = (UInteger) aVersion;
		}
		synchronized(this) {
			valueUpdates.add(new MonitorValueUpdate(
					updateHeader.getDomain(),
					aggregationKey, aggregationVersion,
					timestamp, values));
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

	@Override
    public void listDefinitionResponseReceived(
    		MALMessageHeader msgHeader,
            AggregationDefinitionList definitions,
            Map qosProperties) {
		System.out.println("Reached: listDefinitionResponseReceived()");
		synchronized(this) {
			this.listDefinitionResponse = definitions;
			notify();
		}
    }
	@Override
    public void listDefinitionErrorReceived(
    		MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
		System.out.println("Reached: listDefinitionErrorReceived(): " + error);
		synchronized(this) {
			this.error = error;
			notify();
		}
    }

	@Override
    public void addAggregationAckReceived(
    		MALMessageHeader msgHeader,
            Map qosProperties) {
		System.out.println("Reached: addAggregationAckReceived()");
		synchronized(this) {
			addAggregationAckReceived = true;
			notify();
		}
    }
	@Override
    public void addAggregationErrorReceived(
    		MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
		System.out.println("Reached: addAggregationErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
    }

	@Override
    public void removeAggregationAckReceived(
    		MALMessageHeader msgHeader,
            Map qosProperties) {
		System.out.println("Reached: removeAggregationAckReceived()");
		synchronized(this) {
			removeAggregationAckReceived = true;
			notify();
		}
    }
	@Override
    public void removeAggregationErrorReceived(
    		MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
		System.out.println("Reached: removeAggregationErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
    }
    
}