/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
 * ----------------------------------------------------------------------------
 * System                : ESA CCSDS MO Services
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
package org.ccsds.moims.mo.mc;

import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mc.backends.AggregationBackend;
import org.ccsds.moims.mo.mc.backends.BackendTimer;
import org.ccsds.moims.mo.mc.structures.AggregationDefinition;
import org.ccsds.moims.mo.mc.structures.AggregationDefinitionList;

/**
 * A abstract class for all backend AggregationDataset.
 */
public abstract class AggregationDataset implements AggregationBackend {

	/** class logger */
	private static final Logger logger = Logger.getLogger(AggregationDataset.class.getName());
	/**
	 * List of <code>AggregationDefinition</code> objects known to the provider.
	 * The index of an object in this list is used as an identifier.
	 * Objects should never be deleted from this list.
	 */
	private final AggregationDefinitionList definitions = new AggregationDefinitionList();
	/** List of default report configuration, matching the definitions in the {@link definitions} list. */
	private final List<DefaultReportConfiguration> defaultConfigs = new Vector<>();
	/** Default report configuration for all supplementary Aggregations of the provider. */
	private DefaultReportConfiguration defaultSupplementaryConfig;
	/** Connection details to the Parameter provider */
	protected final SingleConnectionDetails parameterConnectionDetails;
	/** Backend timer to use by the provider, for time related operations */
	protected final BackendTimer timer;

	/**
	 * Constructor with required parameters.
	 * 
	 * @param timer		Backend timer to use by the provider
	 * @param parameterConnectionDetails	Connection details to the Parameter provider
	 */
	public AggregationDataset(
			BackendTimer timer,
			SingleConnectionDetails parameterConnectionDetails) {
		this.timer = timer;
		this.parameterConnectionDetails = parameterConnectionDetails;
	}

	@Override
	public BackendTimer getTimer() {
		return timer;
	}

	@Override
	public AggregationDefinitionList getAllAggregationDefinitions() {
		return definitions;
	}

	@Override
	public DefaultReportConfiguration getDefaultReportConfig(int aggregationID) {
		if (aggregationID >= defaultConfigs.size()) {
			return defaultSupplementaryConfig;
		}
		return defaultConfigs.get(aggregationID);
	}

	@Override
	public DefaultReportConfiguration getDefaultReportConfig() {
		return defaultSupplementaryConfig;
	}

	@Override
	public IdentifierList getCategories() {
		return null;
	}
	
	@Override
	public SingleConnectionDetails getParameterConnectionDetails() {
		return parameterConnectionDetails;
	}

	/**
	 * Adds an <code>AggregationDefinition</code> object to the list handled by the provider.
	 * This function is expected to be called by the specific test backend, at initialization time.
	 * 
	 * @param identity  field of the <code>AggregationDefinition</code> to create
	 * @param description  field of the <code>AggregationDefinition</code> to create
	 * @param category  field of the <code>AggregationDefinition</code> to create
	 * @param parameters  field of the <code>AggregationDefinition</code> to create
	 * @param config  default configuration of the <code>AggregationDefinition</code> to create
	 * @return  identifier of the object
	 */
	protected int addAggregation(
			ObjectIdentity identity,
			String description,
			Identifier category,
			ObjectRefList parameters,
			DefaultReportConfiguration config) {
		AggregationDefinition definition =
				new AggregationDefinition(identity, description, category, parameters);
		if (!definitions.add(definition)) {
			IllegalArgumentException exc = new IllegalArgumentException(
					"cannot add the AggregationDefinition object to the list");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
		int aggregationID = definitions.size()-1;
		logger.info("aggregation " + aggregationID + " added: " + definition);
		if (!defaultConfigs.add(config)) {
			IllegalArgumentException exc = new IllegalArgumentException(
					"cannot add the default configuration to the list");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
		return aggregationID;
	}

	/**
	 * Defines the default reporting configuration for supplementary Aggregations.
	 * 
	 * @param defaultSupplementaryConfig	default reporting configuration
	 */
	protected void addDefaultReportConfig(DefaultReportConfiguration defaultSupplementaryConfig) {
		this.defaultSupplementaryConfig = defaultSupplementaryConfig;
	}

	public static final Identifier INVALID_ID = new Identifier("INVALID_ID");
	@Override
	public boolean isValidId(IdentifierList domain, Identifier key) {
		if (domain == null)
			return false;
		if (key == null)
			return false;
		for (int i = 0; i < domain.size(); i++) {
			if ("".equals(domain.get(i)) ||
					"*".equals(domain.get(i)))
				return false;
		}
		if (INVALID_ID.equals(key))
			return false;
		return true;
	}
}
