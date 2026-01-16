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

import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mc.ActionDataset;
import org.ccsds.moims.mo.mc.AggregationDataset;
import org.ccsds.moims.mo.mc.ParameterDataset;
import org.ccsds.moims.mo.mc.backends.BackendTimer;
import org.ccsds.moims.mo.mc.backends.ConversionFunction;
import org.ccsds.moims.mo.mc.structures.ActionCategory;
import org.ccsds.moims.mo.mc.structures.AggregationDefinition;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinition;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;
import org.ccsds.moims.mo.mc.structures.ReportConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

import org.ccsds.mo.mc.testbed.Constant;

/**
 * AggregationDefaultDataset implements the dataset #AG-2.
 */
public class AggregationDefaultDataset extends AggregationDataset {

	public static final ObjectRef<AggregationDefinition> sat1BcMtq1Ref =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					AggregationDefinition.TYPE_ID.getTypeId(),
					Constant.ID_BC_MTQ1,
					new UInteger(1));
	public final int sat1BcMtq1AdId;

	public AggregationDefaultDataset(BackendTimer timer, SingleConnectionDetails parameterConnectionDetails) {
		super(timer, parameterConnectionDetails);

		// build AggregationDefinition objects

		// AggregationDefinition
		// - identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1", version=1)
		// - description: ""
		// - category: null
		// - parameters: {
		// -- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// -- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1ENABLED", version=1}}
		// Default Report Configuration
		// - reportingEnabled: true
		// - reportInterval: 301s
		sat1BcMtq1AdId =
				addAggregation(
						new ObjectIdentity(
								Constant.DOMAIN_SAT1,
								Constant.ID_BC_MTQ1,
								new UInteger(1)),
						new String(""),
						null,
						new ObjectRefList(new ArrayList<> (Arrays.asList(
								ParameterDefaultDataset.sat1Mtq1VoltageRef,
								ParameterDefaultDataset.sat1Mtq1EnabledRef))),
						new DefaultReportConfiguration(true, 301*1000));

	}

}
