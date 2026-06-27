/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
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

import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mc.structures.AggregationDefinitionList;

/**
 * The Backend interface to the Aggregation service.
 * 
 * In the testbed, we consider that the Aggregation provider gets the values of the aggregations parameters
 * from a Parameter provider. The Aggregation backend interface reflects this choice.
 * The method {@link #getParameterConnectionDetails()} allows the Aggregation provider to retrieve the
 * connection details to the Parameter provider.
 * 
 * The Aggregation backend requires a {@link BackendTimer} to answer to time related calls. The timer is provided
 * in the constructor of the concrete classes implementing the <code>AggregationBackend</code>, and can be
 * retrieved by the {@link #getTimer()} method. This timer should be the same as the Parameter backend's.
 * 
 * Unlike other M&amp;C service, the Aggregation service allows to define new object Definitions. This does not
 * show in the backend interface. Dynamically defined aggregations will be handled completely by the Aggregation
 * provider. There is however a specific method in the backend to retrieve the default configuration of those
 * dynamically defined aggregations.
 */
public interface AggregationBackend {

	/**
	 * Gets the timer of this backend.
	 * The timer must be set in the backend constructor.
	 * 
	 * @return 	timer
	 */
	public BackendTimer getTimer();

	/**
	 * Retrieves the Definition objects for all known Aggregations of the provider.
	 * Aggregations are then identified by their index in this list.
	 * 
	 * @return 	the list of AggregationDefinition objects known by the provider
	 */
	public AggregationDefinitionList getAllAggregationDefinitions();

	/**
	 * The DefaultReportConfiguration class is used to provide the default report configuration of an
	 * Aggregation, in the {@link AggregationBackend#getDefaultReportConfig()} method.
	 */
	public class DefaultReportConfiguration {
		public boolean generationEnabled;
		public long reportInterval;	// ms
		public DefaultReportConfiguration(
				boolean generationEnabled,
				long reportInterval) {
			this.generationEnabled = generationEnabled;
			this.reportInterval = reportInterval;
		}
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("DefaultReportConfiguration{");
			result.append("generationEnabled=").append(generationEnabled);
			result.append(",reportInterval=").append(reportInterval);
			result.append("}");
			return result.toString();
		}
	}

	/**
	 * retrieves the default reporting configuration for the requested Aggregation.
	 * 
	 * @param aggregationID	index of the requested aggregation reporting configuration
	 * @return	the default reporting configuration of the aggregation
	 */
	public DefaultReportConfiguration getDefaultReportConfig(int aggregationID);

	/**
	 * retrieves the default reporting configuration for supplementary Aggregations.
	 * 
	 * @return	the default reporting configuration for supplementary aggregations
	 */
	public DefaultReportConfiguration getDefaultReportConfig();

	/**
	 * retrieves the list of the deployment specific allowed values.
	 * 
	 * @return	the list of allowed category values, may be null
	 */
	public IdentifierList getCategories();
	
	/**
	 * Retrieves the details of the Parameter provider used to collect the Parameter values.
	 * 
	 * @return the connection details of the Parameter provider
	 */
	public SingleConnectionDetails getParameterConnectionDetails();

	/**
	 * Verifies that the provided identifier is valid according to the provider's specific rules.
	 * @param domain	domain part of the aggregation identifier
	 * @param key	key part of the aggregation identifier
	 * @return	<code>true</code> if the identifier is valid
	 */
	public boolean isValidId(IdentifierList domain, Identifier key);
	
}
