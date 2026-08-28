/**
 * The Plan Edit Service, introduced in 2.5.6, is provided by a plan execution
 * function and enables its consumers to modify Plans that have already been
 * submitted for execution.  It allows an external user or function to update
 * the status of the Plan; insert, modify, or delete its constituent ActivityInstances
 * and EventInstances; update the value of Resources; and apply a time shift
 * to a Plan.  It comprises the operations defined below, of which only those
 * in capability set 1 are mandatory. In some deployments, the Plan Edit Service
 * could also be provided by a planning function to enable users to make adjustments
 * to their planned activities prior to submission of the plan for execution.
*/
package org.ccsds.moims.mo.mps.planedit;
