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

import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mc.backends.BackendTimer;

/**
 * ParameterDatasetForReportConfig implements the dataset #PA-3.
 * Definitions are retrieved from ParameterDefaultDataset.
 */
public class ParameterDatasetForReportConfig extends ParameterDefaultDataset {

	public ParameterDatasetForReportConfig(BackendTimer timer) {
		super(timer);
	}

	@Override
	void addValues() {
		// at this time, the timer must have been initialized to the time origin
		long timeOrigin = timer.currentTimeMillis();

		// paramRef.domain         paramRef.key          timestamp samplingTime  rawValue
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:00:10  0:00:00       12.00
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1ENABLED"  0:00:10  0:00:00       1
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:05:10  0:05:00       12.05
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:10:10  0:10:00       12.10
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:15:10  0:15:00       12.15
		// "fr.cnes.mission.sat1"  "ATT_BC_MTQ1VOLTAGE"  0:20:10  0:20:00       12.20
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+10*1000, timeOrigin, new Union(new Double(12.00)));
		addParameterValue(sat1Mtq1EnabledPdId, timeOrigin+10*1000, timeOrigin, new UInteger(1));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((5*60)+10)*1000, timeOrigin+5*60*1000, new Union(new Double(12.05)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((10*60)+10)*1000, timeOrigin+10*60*1000, new Union(new Double(12.10)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((15*60)+10)*1000, timeOrigin+15*60*1000, new Union(new Double(12.15)));
		addParameterValue(sat1Mtq1VoltagePdId, timeOrigin+((20*60)+10)*1000, timeOrigin+20*60*1000, new Union(new Double(12.20)));
	}
}
