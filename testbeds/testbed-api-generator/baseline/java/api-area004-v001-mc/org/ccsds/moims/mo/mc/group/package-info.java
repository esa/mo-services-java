/**
 * The group service provides a mechanism for other services to reference
 * sets of their own objects using a single group reference. These groups
 * are used by the other MC services (such as Action, Alert, Check, Aggregation
 * and Parameter) to reduce the complexity of operations by allowing consumers
 * to reference groups of objects (such as parameters) in operations rather
 * than having to supply large lists of object references.
 * Where operations of other service mention the use of groups in their operations,
 * any reference to a group object instance identifier implicitly means a
 * GroupIdentity object.
 * Groups of other groups is supported, however all objects within the group
 * of groups should have the same object type as most operations expect a
 * single type.
 * The creation of cyclic group of groups should also be avoided.
 * The group service does not provide any operations directly, but allows
 * consumers to add, remove, and modify groups via the COM archive.
*/
package org.ccsds.moims.mo.mc.group;
