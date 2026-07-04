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

import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mc.structures.AlertDefinitionList;

/**
 * The Backend interface to the Alert service.
 * 
 * The original Alert service was associating a condition to the Alert, defining in details the computation
 * to perform to known when the Alert was to be reported. This alert condition part is no longer normative.
 * We have nevertheless decided to keep this idea of a condition in the backend interface, even if there is
 * no actual definition or structure related to the condition.
 * 
 * The backend provides a dynamic API to signal changes in the computed value of an Alert condition.
 * It may be a change of status, i.e. the condition holds false while it was holding true before,
 * or a change in the values of the Alert arguments.
 */
public interface AlertBackend {

	/**
	 * Retrieves the Definition objects for all known Alerts of the provider.
	 * Alerts are then identified by their index in this list.
	 * This list is static.
	 * 
	 * @return 	the list of AlertDefinition objects known by the provider
	 */
	public AlertDefinitionList getAllAlertDefinitions();

	/**
	 * Listener interface used in calling {@link register}.
	 */
	public interface AlertListener {
		/**
		 * Signals a change of value for the condition of an Alert.
		 */
		public void notifyAlertCondition(int alertID, boolean status, NullableAttributeList arguments);
	}

	/**
	 * Registers a listener for receiving changes of condition value for all Alerts.
	 * 
	 * @param listener	listener to notify
	 */
	public void register(AlertListener listener);

	/**
	 * Unregisters a listener for the condition values changes.
	 * Returns silently if the provided listener was not previously registered.
	 * 
	 * @param listener	listener to unregister for notifications
	 */
	public void deregister(AlertListener listener);

}
