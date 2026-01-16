/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
 * ----------------------------------------------------------------------------
 * System                : ESA CCSDS MO Services
 * ----------------------------------------------------------------------------
 * Licensed under European Space Agency Public License (ESA-PL) Weak Copyleft – v2.4
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
package org.ccsds.moims.mo.mc;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mc.backends.BackendTimer;
import org.ccsds.moims.mo.mc.backends.ConversionFunction;
import org.ccsds.moims.mo.mc.backends.ParameterBackend;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;
import org.ccsds.moims.mo.mc.structures.ParameterDefinitionList;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ValidityState;

/**
 * A abstract class for all backend ParameterDataset.
 */
public abstract class ParameterDataset implements ParameterBackend {

	/** class logger */
	private static final Logger logger = Logger.getLogger(ParameterDataset.class.getName());
	/**
	 * List of <code>ParameterDefinition</code> objects known to the provider.
	 * The index of an object in this list is used as an identifier.
	 * Objects should never be deleted from this list.
	 */
	private final ParameterDefinitionList definitions = new ParameterDefinitionList();
	/** List of read-only properties, matching the definitions in the {@link definitions} list. */
	private final List<Boolean> readOnlyProperties = new Vector<>();
	/** List of default report configurations, matching the definitions in the {@link definitions} list. */
	private final List<DefaultReportConfiguration> defaultConfigs = new Vector<>();
	/** List of conversion functions, matching the definitions in the {@link definitions} list. */
	private final List<ConversionFunction> conversionFunctions = new Vector<>();
	/** backend timer to use by the provider, for time related operations */
	protected final BackendTimer timer;

	public ParameterDataset(BackendTimer timer) {
		this.timer = timer;
	}

	@Override
	public BackendTimer getTimer() {
		return timer;
	}

	@Override
	public ParameterDefinitionList getAllParameterDefinitions() {
		return definitions;
	}

	@Override
	public DefaultReportConfiguration getDefaultReportConfig(int parameterID) {
		if (parameterID >= defaultConfigs.size()) {
			IllegalArgumentException exc = new IllegalArgumentException("unknown ParameterDefinition id");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
		return defaultConfigs.get(parameterID);
	}

	/**
	 * Adds a ParameterDefinition object to the list handled by the provider.
	 * This function is expected to be called by the specific test backend, at initialization time.
	 * 
	 * @param identity  field of the ParameterDefinition to create
	 * @param description  field of the ParameterDefinition to create
	 * @param rawType  field of the ParameterDefinition to create
	 * @param rawUnit  field of the ParameterDefinition to create
	 * @param convertedType  field of the ParameterDefinition to create
	 * @param convertedUnit  field of the ParameterDefinition to create
	 * @param config  default configuration of the ParameterDefinition to create
	 * @param conversionFunction	conversion function, may be null
	 * @param readOnly	read-only property of the Parameter
	 * @return  identifier of the object
	 */
	protected int addParameter(
			ObjectIdentity identity,
			String description,
			AttributeType rawType,
			String rawUnit,
			AttributeType convertedType,
			String convertedUnit,
			DefaultReportConfiguration config,
			ConversionFunction conversionFunction,
			boolean readOnly) {
		ParameterDefinition definition =
				new ParameterDefinition(identity, description, rawType, rawUnit, convertedType, convertedUnit);
		if (!definitions.add(definition)) {
			IllegalArgumentException exc = new IllegalArgumentException(
					"cannot add the ParameterDefinition object to the list");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
		int parameterID = definitions.size()-1;
		logger.info("parameter " + parameterID + " added: " + definition);
		if (!defaultConfigs.add(config)) {
			IllegalArgumentException exc = new IllegalArgumentException(
					"cannot add the default configuration to the list");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
		if (!conversionFunctions.add(conversionFunction)) {
			IllegalArgumentException exc = new IllegalArgumentException(
					"cannot add the conversion function to the list");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
		if (!readOnlyProperties.add(readOnly)) {
			IllegalArgumentException exc = new IllegalArgumentException(
					"cannot add the read-only property to the list");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
		return parameterID;
	}

	/**
	 * List of time stamped Parameter values used in the dataset.
	 * Values are ordered by timestamp and samplingTime.
	 */
	private ArrayList<List<ParameterRawValue>> parameterValues;

	/**
	 * Adds a Parameter value to the list of values used in the dataset.
	 * Values for a Parameter must be added in a growing timestamp/samplingTime order.
	 * This method is expected to be called by the specific test backend, at initialization time.
	 * 
	 * The timestamp of the Parameter value is used be the {@link getValue} method to know if the value
	 * has been received, as compared to the current time provided by the backend timer.
	 * 
	 * @param parameterID	identifier of the Parameter
	 * @param timestamp		timestamp of the Parameter value
	 * @param samplingTime	sampling time of the Parameter value
	 * @param rawValue		raw value of the Parameter
	 */
	protected void addParameterValue(
			int parameterID,
			long timestamp,
			long samplingTime,
			Attribute rawValue) {
		logger.info("add parameter value for " + parameterID);
		if (parameterID >= definitions.size()) {
			IllegalArgumentException exc = new IllegalArgumentException("unknown ParameterDefinition id");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
		ParameterRawValue value = new ParameterRawValue(timestamp, samplingTime, rawValue);
		if (parameterValues == null) {
			parameterValues = new ArrayList<>(definitions.size());
			for (int i = 0; i < definitions.size(); i++)
				parameterValues.add(new Vector<>());
		}
		List<ParameterRawValue> vlist = parameterValues.get(parameterID);
		ParameterRawValue lastValue = null;
		if (!vlist.isEmpty())
			lastValue = vlist.get(vlist.size()-1);
		if (lastValue != null &&
				(timestamp < lastValue.timestamp ||
						timestamp == lastValue.timestamp && samplingTime <= lastValue.samplingTime)) {
			IllegalArgumentException exc = new IllegalArgumentException("misordered Parameter value");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
		vlist.add(value);
		logger.info("parameter value added: " + value);
	}

	@Override
	public ParameterRawValue getValue(int parameterID) {
		logger.info("getValue " + parameterID);
		if (timer == null) {
			IllegalStateException exc = new IllegalStateException("backend timer has not been set");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}

		if (parameterID >= definitions.size()) {
			logger.severe("unknown ParameterDefinition id");
			return null;
		}

		long now = timer.currentTimeMillis();
		logger.info("now=" + now);
		List<ParameterRawValue> vlist = parameterValues.get(parameterID);
		ParameterRawValue currentValue = null;
		if (vlist.isEmpty()) {
			logger.info("no value for " + parameterID);
		} else {
			boolean found = false;
			for (int i=0; !found && i<vlist.size(); i++) {
				ParameterRawValue svalue = vlist.get(i);
				logger.info("checking " + svalue);
				if (svalue.timestamp > now) {
					found = true;
				} else {
					currentValue = svalue;
				}
			}
		}

		logger.info("backend getValue(" + parameterID + ") return " + currentValue);
		return currentValue;
	}

	@Override
	public boolean isReadOnly(int parameterID) {
		if (parameterID >= defaultConfigs.size()) {
			IllegalArgumentException exc = new IllegalArgumentException("unknown ParameterDefinition id");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
		return readOnlyProperties.get(parameterID);
	}
	
	@Override
	public ConversionFunction getConversionFunction(int parameterID) {
		if (parameterID >= defaultConfigs.size()) {
			IllegalArgumentException exc = new IllegalArgumentException("unknown ParameterDefinition id");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
		return conversionFunctions.get(parameterID);
	}

	/**
	 * Simple conversion function with no converted value and simple validity state.
	 * 
	 * @param 	parameterID	index of the parameter definition
	 * @param 	rawValue	raw value of the parameter
	 * @return	the complete parameter value as a {@link ParameterValueData} object, with updated
	 * 			<code>validityState</code> and <code>convertedValue</code> fields.
	 */
	protected static ParameterValueData noConversionFunction(
			int parameterID,
			ParameterRawValue rawValue) {
		if (rawValue == null || rawValue.rawValue == null)
			return new ParameterValueData(ValidityState.INVALID_RAW, null, null);
		return new ParameterValueData(ValidityState.VALID, rawValue.rawValue, null);
	}

}
