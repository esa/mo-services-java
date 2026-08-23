/**
 * The Planning Request Service, introduced in 2.5.2, is provided by a planning
 * function and enables its consumers to manage the submission of planning
 * requests and to receive feedback on their status.  It comprises the operations
 * defined below, of which only those in capability set 1 are mandatory. In
 * the context of a hierarchical or federated planning system, the Planning
 * Request Service submitRequest operation can be used to submit a Plan (4.5.6)
 * to a planning function, either embedding the Plan in the request itself
 * or passing it by reference.  If passed by reference, the Plan can be retrieved
 * using the Plan Distribution Service (3.6).  Patch plans are not permitted
 * in the context of a planning request.
*/
package org.ccsds.moims.mo.mps.planningrequest;
