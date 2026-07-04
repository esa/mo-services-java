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

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mc.backends.AlertBackend;
import org.ccsds.moims.mo.mc.structures.AlertDefinition;
import org.ccsds.moims.mo.mc.structures.AlertDefinitionList;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList;
import org.ccsds.moims.mo.mc.structures.Severity;

/**
 * A abstract class for all backend AlertDataset.
 */
public abstract class AlertDataset implements AlertBackend {

	/** class logger */
	private static final Logger logger = Logger.getLogger(AlertDataset.class.getName());

	/**
	 * List of <code>AlertDefinition</code> objects known to the provider.
	 * The index of an object in this list is used as an identifier.
	 * Objects should never be deleted from this list.
	 */
	private final AlertDefinitionList definitions = new AlertDefinitionList();
	/** list of registered listeners to signal for condition values updates */
	private final Set<AlertListener> listeners = new HashSet<>();

	@Override
	public AlertDefinitionList getAllAlertDefinitions() {
		return definitions;
	}

	/**
	 * Adds an <code>AlertDefinition</code> object to the list handled by the provider.
	 * This function is expected to be called by the specific test backend, at initialization time.
	 * 
	 * @param identity  field of the <code>AlertDefinition</code> to create
	 * @param description  field of the <code>AlertDefinition</code> to create
	 * @param severity  field of the <code>AlertDefinition</code> to create
	 * @param arguments  field of the <code>AlertDefinition</code> to create
	 * @return  identifier of the object
	 */
	protected int addAlert(
			ObjectIdentity identity,
			String description,
			Severity severity,
			ArgumentDefinitionList arguments) {
		AlertDefinition definition =
				new AlertDefinition(identity, description, severity, arguments);
		if (!definitions.add(definition)) {
			IllegalArgumentException exc = new IllegalArgumentException(
					"cannot add the AlertDefinition object to the list");
			logger.log(Level.SEVERE, null, exc);
			throw exc;
		}
		int alertID = definitions.size()-1;
		logger.info("alert " + alertID + " added: " + definition);
		return alertID;
	}

	@Override
	public void register(AlertListener listener) {
		listeners.add(listener);
	}
	@Override
	public void deregister(AlertListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Reports the result of the evaluation of the condition of an Alert.
	 * The condition value is reported in the status parameter.
	 * When the condition value is <code>true</code>, then the values of the Alert arguments are provided
	 * in the arguments parameter.
	 * Notifies all listeners of the change.
	 * 
	 * In the testbed, this method is expected to be called by the test clients.
	 * 
	 * @param alertID	identifier of the reported Alert
	 * @param status	value of the Alert's condition
	 * @param arguments	argument values of the Alert when status is true
	 */
	public void reportAlertCondition(
			int alertID,
			boolean status,
			NullableAttributeList arguments) {
		for (AlertListener listener : listeners) {
			listener.notifyAlertCondition(alertID, status, arguments);
		}
	}
}
