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
import org.ccsds.moims.mo.mc.AlertDataset;
import org.ccsds.moims.mo.mc.structures.AlertDefinition;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinition;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList;
import org.ccsds.moims.mo.mc.structures.Severity;
import java.util.ArrayList;
import java.util.Arrays;

import org.ccsds.mo.mc.testbed.Constant;

/**
 * AlertBasicDataset implements the dataset #AL-1.
 */
public class AlertBasicDataset extends AlertDataset {

	public static final ObjectRef<AlertDefinition> sat1Mtq1VoltageHighRef =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					AlertDefinition.TYPE_ID.getTypeId(),
					Constant.ID_MTQ1VOLTAGE_HIGH,
					new UInteger(1));
	public final int sat1Mtq1VoltageHighAdId;

	public AlertBasicDataset() {
		// build ArgumentDefinition objects

		// ArgumentDefinition
		// - argId: "MTQ1VOLTAGE_HIGH_VAL"
		// - description: "value of exceeding voltage"
		// - type: DOUBLE
		// - unit="V"
		ArgumentDefinition argDefVoltageHighVal =
				new ArgumentDefinition(
						Constant.ID_MTQ1VOLTAGE_HIGH_VAL,
						"value of exceeding voltage",
						AttributeType.DOUBLE,
						"V");

		// build AlertDefinition objects

		// AlertDefinition
		// - identity: ("fr.cnes.mission.sat1", "MTQ1VOLTAGE_HIGH", version=1)
		// - description: ""
		// - severity: SEVERE
		// - arguments: { "MTQ1VOLTAGE_HIGH_VAL" }
		sat1Mtq1VoltageHighAdId =
				addAlert(
						new ObjectIdentity(
								Constant.DOMAIN_SAT1,
								Constant.ID_MTQ1VOLTAGE_HIGH,
								new UInteger(1)),
						new String(""),
						Severity.SEVERE,
						new ArgumentDefinitionList(new ArrayList<> (Arrays.asList(argDefVoltageHighVal))));
	}

}
