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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mc.backends.ActionBackend;
import org.ccsds.moims.mo.mc.structures.ActionCategory;
import org.ccsds.moims.mo.mc.structures.ActionDefinition;
import org.ccsds.moims.mo.mc.structures.ActionDefinitionList;
import org.ccsds.moims.mo.mc.structures.ActionExecutionRequest;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList;

/**
 * A abstract class for all backend Action Datasets
 */
public abstract class ActionDataset implements ActionBackend {

	/** class logger */
	private static final Logger logger = Logger.getLogger(ActionDataset.class.getName());
	/** List of <code>ActionDefinition</code> objects known to the provider. */
	private final ActionDefinitionList definitions = new ActionDefinitionList();

	@Override
	public ActionDefinitionList getAllActionDefinitions() {
		return definitions;
	}

	/**
	 * Adds an <code>ActionDefinition</code> object to the list handled by the provider.
	 * This method is expected to be called by the specific test backend, at initialization time.
	 * 
	 * @param identity		field of the <code>ActionDefinition</code> to create
	 * @param description	field of the <code>ActionDefinition</code> to create
	 * @param category		field of the <code>ActionDefinition</code> to create
	 * @param progressStepCount	field of the <code>ActionDefinition</code> to create
	 * @param arguments		field of the <code>ActionDefinition</code> to create
	 */
	protected void addAction(
			ObjectIdentity identity,
			String description,
			ActionCategory category,
			UShort progressStepCount,
			ArgumentDefinitionList arguments) {
		ActionDefinition definition =
				new ActionDefinition(identity, description, category, progressStepCount, arguments);
		if (!definitions.add(definition)) {
			IllegalArgumentException exc = new IllegalArgumentException(
					"cannot add the ActionDefinition object to the list");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
	}

	@Override
	public String check(
			ActionExecutionRequest executionRequest,
			ActionDefinition definition) {
		// to be overloaded in derived classes
		logger.info("backend check return null");
		return null;
	}

	@Override
	public boolean execute(
			ActionExecutionRequest executionRequest,
			ActionDefinition definition,
			ExecuteListener listener) {
		int progressStepCount = definition.getProgressStepCount().getValue();
		if (progressStepCount == 0) {
			logger.info("backend execute return true");
			return true;
		}
		if (listener == null) {
			logger.info("backend execute return false");
			return false;
		}
		for (int i=0; i<progressStepCount; i++) {
			logger.info("backend execute send progress report");
			listener.progressReport();
		}
		logger.info("backend execute return true");
		return true;
	}
}
