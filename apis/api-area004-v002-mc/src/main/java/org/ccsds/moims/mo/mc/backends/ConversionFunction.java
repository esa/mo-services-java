/* ----------------------------------------------------------------------------
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

import org.ccsds.moims.mo.mc.backends.ParameterBackend.ParameterRawValue;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;

/**
 * This single method interface describes the conversion function of a Parameter.
 * It is no longer explicitly part of the latest specification of the service, however it is required
 * to compute the converted value of a parameter from its raw value. It is notably used when the raw value
 * is provided by the service consumer.
 * 
 * We also have the need to compute the validity state of a parameter from its raw value. We decided to reuse
 * the same operation for both usages, meaning that the function is called even for parameters which do not
 * use converted values.
 */
public interface ConversionFunction {

	/**
	 * Computes the validity state and possibly the converted value of a parameter.
	 * 
	 * @param 	parameterID	index of the parameter definition
	 * @param 	rawValue	raw value of the parameter
	 * @return	the complete parameter value as a {@link ParameterValueData} object, with updated
	 * 			<code>validityState</code> and <code>convertedValue</code> fields.
	 */
	public ParameterValueData getConvertedValue(int parameterID, ParameterRawValue rawValue);
	
}
