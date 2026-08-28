/**
 * The action service allows consumers to submit an action for execution and
 * to subsequently monitor the execution progress of these actions via the
 * COM activity tracking pattern. The progress of the action is split into
 * two parts, firstly transfer from the consumer to the provider, and secondly
 * execution in the provider.
 * An action is submitted to the provider using the submitAction operation,
 * the progress of which can be monitored using the COM activity tracking
 * pattern, which completes when the action has been delivered to the provider.
 * The action is then executed in the provider which can also be monitored
 * using the activity tracking pattern. The submitAction operation takes the
 * object instance identifier of the submitted action and uses that to populate
 * the source fields of the activity tracking events. Coordination may be
 * required between action service consumers to ensure the action object instance
 * identifiers are unique. How this is done is outside the scope of this specification
 * however a good approach is the use of a central COM archive as that can
 * provide the unique instance identifiers.
 * The nominal sequence of action submission and execution monitoring are
 * shown in Figure 3-1:
 * insert sequence diag here
 * The consumer is responsible for creating and archiving the action instance
 * object and then using the submitAction operation to submit it to the action
 * provider for execution. The action provider reports, using the COM ActivityTracking
 * service, both the execution progress of the submitAction operation and
 * also the execution of the action itself. Once the supplied action instance
 * details have been checked and execution of the action started by the submitAction
 * operation, that operation finishes, whilst the execution of the action
 * possibly continues (depends on the actual action). The final interaction
 * in the sequence shows the execution events of the executing action instance,
 * the &quot;*&quot; at the start of that line indicates zero to many events
 * being published.
 * If the execution of an action fails with an error, the action service provider
 * can publish an ActionFailure COM event to hold the error code being reported
 * by the failure. If no error code is required to be reported then there
 * is no need to publish the event as the normal COM ActivityTracking event
 * contains a success indication.
 * The service also includes an operation, preCheckAction, which checks that
 * an action would be accepted for execution without actually submitting it
 * for execution. It is expected to be provided by local action proxies, rather
 * than the remote system, to allow for localised checking of things such
 * as link state, argument values, action safety, before sending the action
 * over long and slow space links.
 * The action service defines three types of objects, the first type is the
 * ActionIdentity object that holds the name of an action. The second type
 * is the ActionDefinition object that holds the description of an action
 * with the list of required/optional arguments. The third type is the ActionInstance
 * object that holds details of a specific action instance namely a value
 * for each of the arguments of the action.
*/
package org.ccsds.moims.mo.mc.action;
