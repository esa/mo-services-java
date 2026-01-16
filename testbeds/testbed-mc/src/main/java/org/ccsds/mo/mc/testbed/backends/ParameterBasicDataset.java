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
import org.ccsds.moims.mo.mc.backends.ConversionFunction;
import org.ccsds.moims.mo.mc.structures.ActionCategory;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinition;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;
import org.ccsds.moims.mo.mc.structures.ReportConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

import org.ccsds.mo.mc.testbed.Constant;

/**
 * ParameterBasicDataset implements the dataset #PA-1.
 */
public class ParameterBasicDataset extends ParameterDataset {

	public static final ObjectRef<ParameterDefinition> sat1Mtq1VoltageRef =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					ParameterDefinition.TYPE_ID.getTypeId(),
					Constant.ID_MTQ1VOLTAGE,
					new UInteger(1));
	public final int sat1Mtq1VoltagePdId;

	public ParameterBasicDataset(BackendTimer timer) {
		super(timer);

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
		DefaultReportConfiguration config = new DefaultReportConfiguration(true, 300*1000, 60*1000);
		sat1Mtq1VoltagePdId =
				addParameter(
						new ObjectIdentity(
								sat1Mtq1VoltageRef.getDomain(),
								sat1Mtq1VoltageRef.getKey(),
								sat1Mtq1VoltageRef.getObjectVersion()),
						new String("absolute time in seconds"),
						AttributeType.DOUBLE,
						new String("V"),
						null, null,
						config,
						ParameterDataset::noConversionFunction,
						false);

		// register all values
		// at this time, the timer must have been initialized to the time origin
		long timeOrigin = timer.currentTimeMillis();

		// paramRef.domain         paramRef.key          timestamp samplingTime  rawValue
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:00:10   0:00:00       12.00
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+10000, timeOrigin, new Union(new Double(12.00)));
	}

}
