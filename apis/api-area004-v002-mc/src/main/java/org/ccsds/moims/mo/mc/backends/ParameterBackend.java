/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO services
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
package org.ccsds.moims.mo.mc.backends;

import java.io.IOException;

import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mc.structures.ParameterDefinitionList;

/**
 * The Backend interface to the Parameter service.
 * 
 * The Parameter backend requires a {@link BackendTimer} to answer to time related calls. The timer is provided
 * in the constructor of the concrete classes implementing the <code>ParameterBackend</code>, and can be
 * retrieved by the {@link getTimer} method.
 * 
 * Note that the setValue operation of the service is not included in the backend interface. This operation
 * must be completely handled by the provider implementation.
 */
public interface ParameterBackend {

	/**
	 * Gets the timer of this backend.
	 * The timer must be set in the backend constructor.
	 * 
	 * @return 	timer
	 */
	public BackendTimer getTimer();

	/**
	 * Retrieves the Definition objects for all known Parameters of the provider.
	 * Parameters are then identified by their index in this list.
	 * This list is static.
	 * 
	 * @return 	the list of ParameterDefinition objects known by the provider
	 */
	public ParameterDefinitionList getAllParameterDefinitions();

	/**
	 * The DefaultReportConfiguration class is used to provide the default report configuration of a
	 * Parameter, in the {@link getDefaultReportConfig} method.
	 */
	public class DefaultReportConfiguration {
		public boolean generationEnabled;
		public long reportInterval;	// ms
		public long minimalReportInterval;	// ms
		public DefaultReportConfiguration(
				boolean generationEnabled,
				long reportInterval,
				long minimalReportInterval) {
			this.generationEnabled = generationEnabled;
			this.reportInterval = reportInterval;
			this.minimalReportInterval = minimalReportInterval;
		}
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("DefaultReportConfiguration{");
			result.append("generationEnabled=").append(generationEnabled);
			result.append(",reportInterval=").append(reportInterval);
			result.append(",minimalReportInterval=").append(minimalReportInterval);
			result.append("}");
			return result.toString();
		}
	}

	/**
	 * retrieves the default reporting configuration for the requested Parameter.
	 * 
	 * @param parameterID	index of the requested parameter reporting configuration
	 * @return	the default reporting configuration of the parameter
	 */
	public DefaultReportConfiguration getDefaultReportConfig(int parameterID);

	/**
	 * The ParameterRawValue class is used to provide the latest known raw value of a Parameter,
	 * in the {@link getValue} method.
	 */
	public class ParameterRawValue {
		public long timestamp;
		public long samplingTime;
		public Attribute rawValue;
		public ParameterRawValue(
				long timestamp,
				long samplingTime,
				Attribute rawValue) {
			this.timestamp = timestamp;
			this.samplingTime = samplingTime;
			this.rawValue = rawValue;
		}
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("ParameterRawValue{");
			result.append("timestamp=").append(timestamp);
			result.append(",samplingTime=").append(samplingTime);
			result.append(",rawValue=").append(rawValue);
			result.append("}");
			return result.toString();
		}
	}

	/**
	 * Retrieves the latest known raw value of the parameter at the current time,
	 * together with its timestamp and sampling time. The current time is retrieved from the backend timer.
	 * 
	 * @param parameterID	index of the requested parameter value
	 * @return	the latest known raw value of the parameter, or <code>null</code> if no value is known for
	 * 			the parameter at the current time
	 */
	public ParameterRawValue getValue(int parameterID);

	/**
	 * Gets the read-only property of a Parameter.
	 * 
	 * @param parameterID	index of the requested parameter
	 * @return	<code>true</code> if the Parameter is read-only, <code>false</code> otherwise
	 */
	public boolean isReadOnly(int parameterID);
	
	/**
	 * Retrieves the conversion function associated to the parameter.
	 * 
	 * @param parameterID	index of the requested parameter conversion function
	 * @return	the conversion function associated to the parameter
	 */
	public ConversionFunction getConversionFunction(int parameterID);

}