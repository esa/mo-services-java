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
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mc.ActionDataset;
import org.ccsds.moims.mo.mc.ParameterDataset;
import org.ccsds.moims.mo.mc.backends.BackendTimer;
import org.ccsds.moims.mo.mc.structures.ActionCategory;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinition;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;
import org.ccsds.moims.mo.mc.structures.ReportConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

import org.ccsds.mo.mc.testbed.Constant;

/**
 * ParameterErrorDataset implements the dataset #PA-9.
 */
public class ParameterErrorDataset extends ParameterDataset {

	public static final ObjectRef<ParameterDefinition> sat1Mtq1VoltageRef =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					ParameterDefinition.TYPE_ID.getTypeId(),
					Constant.ID_MTQ1VOLTAGE,
					new UInteger(1));
	public final int sat1Mtq1VoltagePdId;
	public static final ObjectRef<ParameterDefinition> sat2Mtq1VoltageRef =
			new ObjectRef<>(
					Constant.DOMAIN_SAT2,
					ParameterDefinition.TYPE_ID.getTypeId(),
					Constant.ID_MTQ1VOLTAGE,
					new UInteger(1));
	public final int sat2Mtq1VoltagePdId;
	public static final ObjectRef<ParameterDefinition> sat1Mtq1EnabledRef =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					ParameterDefinition.TYPE_ID.getTypeId(),
					Constant.ID_MTQ1ENABLED,
					new UInteger(1));
	public final int sat1Mtq1EnabledPdId;

	public ParameterErrorDataset(BackendTimer timer) {
		super(timer);

		// the Mtq1Enabled conversion function requires access to the timer
		ParameterDefaultDataset.backendTimer = timer;

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
		// - identity: ("fr.cnes.mission.sat2", "ATT_BC_MTQ1VOLTAGE", version=1)
		// - description: ""
		// - rawType: DOUBLE
		// - rawUnit: "V"
		// - convertedType: null
		// - convertedUnit: null
		// Default Report Configuration
		// - reportingEnabled: false
		// - reportInterval: 300s (minimum sampling interval=300s)
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
						new DefaultReportConfiguration(true, 300*1000, 60*1000),
						ParameterDataset::noConversionFunction,
						false);
		sat2Mtq1VoltagePdId =
				addParameter(
						new ObjectIdentity(
								Constant.DOMAIN_SAT2,
								Constant.ID_MTQ1VOLTAGE,
								new UInteger(1)),
						new String("absolute time in seconds"),
						AttributeType.DOUBLE,
						new String("V"),
						null, null,
						new DefaultReportConfiguration(false, 300*1000, 300*1000),
						ParameterDataset::noConversionFunction,
						true);
		sat1Mtq1EnabledPdId =
				addParameter(
						new ObjectIdentity(
								Constant.DOMAIN_SAT1,
								Constant.ID_MTQ1ENABLED,
								new UInteger(1)),
						new String(""),
						AttributeType.UINTEGER,
						null,
						AttributeType.STRING, null,
						new DefaultReportConfiguration(false, 300*1000, 300*1000),
						ParameterDefaultDataset::mtq1EnabledConversionFunction,
						false);

		// register all values
		addValues();
	}

	void addValues() {
		// at this time, the timer must have been initialized to the time origin
		long timeOrigin = timer.currentTimeMillis();

		// paramRef.domain         paramRef.key          timestamp samplingTime  rawValue
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:00:10   0:00:00       12.00
		// "fr.cnes.mission.sat2"  "ATT_BC_MTQ1VOLTAGE"  0:00:10   0:00:00       22.00
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1ENABLED"  0:00:10   0:00:00       1
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+10*1000, timeOrigin, new Union(new Double(12.00)));
		addParameterValue(sat2Mtq1VoltagePdId, timeOrigin+10*1000, timeOrigin, new Union(new Double(22.00)));
		addParameterValue(sat1Mtq1EnabledPdId, timeOrigin+10*1000, timeOrigin, new UInteger(1));
	}
}
