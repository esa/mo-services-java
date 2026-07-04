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

import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mc.ParameterDataset;
import org.ccsds.moims.mo.mc.backends.BackendTimer;
import org.ccsds.moims.mo.mc.backends.ConversionFunction;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ValidityState;

import org.ccsds.mo.mc.testbed.Constant;

/**
 * ParameterDefaultDataset implements the dataset #PA-2.
 */
public class ParameterDefaultDataset extends ParameterDataset {

	public static final ObjectRef<ParameterDefinition> sat1Mtq1VoltageRef =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					ParameterDefinition.TYPE_ID.getTypeId(),
					Constant.ID_MTQ1VOLTAGE,
					new UInteger(1));
	public final int sat1Mtq1VoltagePdId;
	public static final ObjectRef<ParameterDefinition> sat1Mtq1EnabledRef =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					ParameterDefinition.TYPE_ID.getTypeId(),
					Constant.ID_MTQ1ENABLED,
					new UInteger(1));
	public final int sat1Mtq1EnabledPdId;
	
	// the Mtq1Enabled conversion function requires access to the timer
	public static BackendTimer backendTimer;

	public ParameterDefaultDataset(BackendTimer timer) {
		this(timer, ParameterDefaultDataset::mtq1EnabledConversionFunction);
	}
	public ParameterDefaultDataset(BackendTimer timer, ConversionFunction mtq1EnabledSpecificConversionFunction) {
		super(timer);

		// the Mtq1Enabled conversion function requires access to the timer
		backendTimer = timer;
		
		// build ParameterDefinition objects

		// ParameterDefinition
		// - identity: ("fr.cnes.mission.sat1", "ATT_BC_MTQ1VOLTAGE", version=1)
		// - description: ""
		// - rawType: DOUBLE
		// - rawUnit: "V"
		// - convertedType: null
		// - convertedUnit: null
		// Default Report Configuration
		// - reportingEnabled: true
		// - reportInterval: 300s (minimum sampling interval=60s)
		// ParameterDefinition
		// - identity: ("fr.cnes.mission.sat1", "ATT_BC_MTQ1ENABLED", version=1)
		// - description: ""
		// - rawType: UINTEGER
		// - rawUnit: null
		// - convertedType: String
		// - convertedUnit: null
		// Default Report Configuration
		// - reportingEnabled: false
		// - reportInterval: 300s (minimum sampling interval=300s)
		DefaultReportConfiguration config = new DefaultReportConfiguration(true, 300*1000, 60*1000);
		sat1Mtq1VoltagePdId =
				addParameter(
						new ObjectIdentity(
								Constant.DOMAIN_SAT1,
								Constant.ID_MTQ1VOLTAGE,
								new UInteger(1)),
						new String("absolute time in seconds"),
						AttributeType.DOUBLE,
						new String("V"),
						null, null,
						config,
						ParameterDataset::noConversionFunction,
						false);
		config = new DefaultReportConfiguration(false, 300*1000, 300*1000);
		sat1Mtq1EnabledPdId =
				addParameter(
						new ObjectIdentity(
								Constant.DOMAIN_SAT1,
								Constant.ID_MTQ1ENABLED,
								new UInteger(1)),
						new String(""),
						AttributeType.UINTEGER,
						null,
						AttributeType.STRING,
						null,
						config,
						mtq1EnabledSpecificConversionFunction,
						false);

		// register all values
		addValues();
	}

	void addValues() {
		// at this time, the timer must have been initialized to the time origin
		long timeOrigin = timer.currentTimeMillis();

		// paramRef.domain         paramRef.key          timestamp samplingTime  rawValue
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:00:10   0:00:00       12.00
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1ENABLED"  0:00:10   0:00:00       1
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:01:10   0:01:00       12.01
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:02:10   0:02:00       12.02
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:03:10   0:03:00       12.03
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:04:10   0:04:00       12.04
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:05:10   0:05:00       12.05
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:10:10   0:06:00       12.06
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:10:10   0:07:00       12.07
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:10:10   0:08:00       12.08
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:11:10   0:09:00       12.09
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:11:10   0:10:00       12.10
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:11:10   0:11:00       12.11
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+10*1000, timeOrigin, new Union(new Double(12.00)));
		addParameterValue(sat1Mtq1EnabledPdId, timeOrigin+10*1000, timeOrigin, new UInteger(1));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((1*60)+10)*1000, timeOrigin+1*60*1000, new Union(new Double(12.01)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((2*60)+10)*1000, timeOrigin+2*60*1000, new Union(new Double(12.02)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((3*60)+10)*1000, timeOrigin+3*60*1000, new Union(new Double(12.03)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((4*60)+10)*1000, timeOrigin+4*60*1000, new Union(new Double(12.04)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((5*60)+10)*1000, timeOrigin+5*60*1000, new Union(new Double(12.05)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((10*60)+10)*1000, timeOrigin+6*60*1000, new Union(new Double(12.06)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((10*60)+10)*1000, timeOrigin+7*60*1000, new Union(new Double(12.07)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((10*60)+10)*1000, timeOrigin+8*60*1000, new Union(new Double(12.08)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((11*60)+10)*1000, timeOrigin+9*60*1000, new Union(new Double(12.09)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((11*60)+10)*1000, timeOrigin+10*60*1000, new Union(new Double(12.10)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((11*60)+10)*1000, timeOrigin+11*60*1000, new Union(new Double(12.11)));
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
		if (rawInt == 0)
			return new ParameterValueData(
					ValidityState.VALID,
					rawValue.rawValue, Constant.AT_STRING_DISABLED);
		if (rawInt == 1)
			return new ParameterValueData(
					ValidityState.VALID,
					rawValue.rawValue, Constant.AT_STRING_ENABLED);
		return new ParameterValueData(ValidityState.INVALID, rawValue.rawValue, Constant.AT_STRING_UNKNOWN);
	}
}
