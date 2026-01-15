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
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mc.ActionDataset;
import org.ccsds.moims.mo.mc.structures.ActionCategory;
import org.ccsds.moims.mo.mc.structures.ActionDefinition;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinition;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList;
import java.util.ArrayList;
import java.util.Arrays;

import org.ccsds.mo.mc.testbed.Constant;

/**
 * ActionBasicDataset implements the dataset #AC-1.
 */
public class ActionBasicDataset extends ActionDataset {

	public static final ObjectRef<ActionDefinition> sat1ChgTAbsValRef =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					ActionDefinition.TYPE_ID.getTypeId(),
					Constant.ID_CHGTABSVAL,
					new UInteger(1));

	public ActionBasicDataset() {
		// build ArgumentDefinition objects

		// ArgumentDefinition
		// - argId: "GENE_AR_TIMEABSVAL"
		// - description: "absolute time in seconds"
		// - type: TIME
		// - unit="s"
		ArgumentDefinition argDefTimeAbsVal =
				new ArgumentDefinition(
						Constant.ID_TIMEABSVAL,
						"absolute time in seconds",
						AttributeType.TIME,
						"s");

		// build ActionDefinition objects

		// ActionDefinition
		// - identity: ("fr.cnes.mission.sat1", "SAT_TC_CHGTABSVAL", version=1)
		// - description: "TC 9.128 – CHANGE_ONBOARD_TIME_ABSOLUTE_VALUE"
		// - category: DEFAULT
		// - progressStepCount: 0
		// - arguments: { "GENE_AR_TIMEABSVAL" }
		addAction(
				new ObjectIdentity(
						sat1ChgTAbsValRef.getDomain(),
						sat1ChgTAbsValRef.getKey(),
						sat1ChgTAbsValRef.getObjectVersion()),
				new String("TC 9.128 – CHANGE_ONBOARD_TIME_ABSOLUTE_VALUE"),
				ActionCategory.DEFAULT,
				new UShort(0),
				new ArgumentDefinitionList(new ArrayList<> (Arrays.asList(argDefTimeAbsVal))));
	}

	// use default check function from base class
	// use default execute function from base class
}
