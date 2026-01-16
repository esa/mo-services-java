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

import org.ccsds.moims.mo.mc.structures.ActionDefinition;
import org.ccsds.moims.mo.mc.structures.ActionDefinitionList;
import org.ccsds.moims.mo.mc.structures.ActionExecutionRequest;

/**
 * The Backend interface to the Action service.
 */
public interface ActionBackend {

	/**
	 * Retrieves the Definition objects for all known Actions of the provider.
	 * This list is static.
	 * 
	 * @return the list of ActionDefinition objects known by the provider
	 */
	public ActionDefinitionList getAllActionDefinitions();

	/**
	 * Listener interface used in calling {@link execute}.
	 */
	public interface ExecuteListener {
		/**
		 * Signals the success in executing the next step.
		 */
		public void progressReport();
	}

	/**
	 * Checks that an Action execution request is ready for execution.
	 * This function is called before {@link execute} to allow the provider to perform
	 * some acceptance tests before actually executing the Action.
	 * 
	 * @param executionRequest	request planned for execution
	 * @param definition		definition of the request Action
	 * @return 	<code>null</code> if the request is ready for execution, or an error message otherwise.
	 */
	public String check(
			ActionExecutionRequest executionRequest,
			ActionDefinition definition);

	/**
	 * Actually executes an Action.
	 * Successful progress steps are reported via the ExecuteListener interface.
	 * The function returns when the execution completes, and reports the success status of the execution.
	 * 
	 * @param executionRequest	request to execute
	 * @param definition		definition of the request Action
	 * @param listener			listener to report execution progress to
	 * @return 	the success status of the execution.
	 */
	public boolean execute(
			ActionExecutionRequest executionRequest,
			ActionDefinition definition,
			ExecuteListener listener);

}
