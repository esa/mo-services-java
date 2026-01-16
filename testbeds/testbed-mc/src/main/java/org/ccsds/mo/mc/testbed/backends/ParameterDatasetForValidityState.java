/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
 *                         Adapted to the M&C testbed from the MPD testbed
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Testbed - M&C
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
package org.ccsds.mo.mc.testbed.backends;

import org.ccsds.mo.mc.testbed.Constant;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mc.backends.BackendTimer;
import org.ccsds.moims.mo.mc.backends.ParameterBackend.ParameterRawValue;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ValidityState;

/**
 * ParameterDatasetForValidityState implements the dataset #PA-4.
 * Definitions are retrieved from ParameterDefaultDataset.
 */
public class ParameterDatasetForValidityState extends ParameterDefaultDataset {

	public ParameterDatasetForValidityState(BackendTimer timer) {
		super(timer, ParameterDatasetForValidityState::mtq1EnabledConversionFunction);
	}

	@Override
	void addValues() {
		// at this time, the timer must have been initialized to the time origin
		long timeOrigin = timer.currentTimeMillis();

		// paramRef.domain         paramRef.key          timestamp samplingTime  rawValue
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1ENABLED"  0:00:10  0:00:00       null
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1ENABLED"  0:01:10  0:01:00       1
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1ENABLED"  0:13:10  0:13:00       8
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1ENABLED"  0:25:10  0:25:00       2
		addParameterValue(sat1Mtq1EnabledPdId, timeOrigin+10*1000, timeOrigin, null);
		addParameterValue(sat1Mtq1EnabledPdId, timeOrigin+((1*60)+10)*1000, timeOrigin+1*60*1000, new UInteger(1));
		addParameterValue(sat1Mtq1EnabledPdId, timeOrigin+((13*60)+10)*1000, timeOrigin+13*60*1000, new UInteger(8));
		addParameterValue(sat1Mtq1EnabledPdId, timeOrigin+((25*60)+10)*1000, timeOrigin+25*60*1000, new UInteger(2));
	}

	/**
	 * Specific conversion function for the parameter ATT-BC-MTQ1ENABLED.
	 * - 0 → "DISABLED"
	 * - 1 → "ENABLED"
	 * - >1&<8 → valid value but fails conversion
	 * - >=8 → "UNKNOWN" (the raw value is considered invalid)
	 * Any value older that 10mn becomes EXPIRED
	 * 
	 * @param parameterID
	 * @param rawValue
	 * @return
	 */
	public static ParameterValueData mtq1EnabledConversionFunction(int parameterID, ParameterRawValue rawValue) {
		if (rawValue == null || rawValue.rawValue == null)
			return new ParameterValueData(ValidityState.INVALID_RAW, null, null);
		if (!(rawValue.rawValue instanceof UInteger))
			return new ParameterValueData(ValidityState.INVALID_CONVERSION, rawValue.rawValue, null);
		long rawInt = ((UInteger) rawValue.rawValue).getValue();
		boolean expired = (rawValue.timestamp + 10*60*1000) < backendTimer.currentTimeMillis();
		if (rawInt == 0)
			return new ParameterValueData(
					expired ? ValidityState.EXPIRED : ValidityState.VALID,
					rawValue.rawValue, Constant.AT_STRING_DISABLED);
		if (rawInt == 1)
			return new ParameterValueData(
					expired ? ValidityState.EXPIRED : ValidityState.VALID,
					rawValue.rawValue, Constant.AT_STRING_ENABLED);
		if (rawInt < 8)
			return new ParameterValueData(ValidityState.INVALID_CONVERSION, rawValue.rawValue, null);
		return new ParameterValueData(ValidityState.INVALID, rawValue.rawValue, Constant.AT_STRING_UNKNOWN);
	}
}
