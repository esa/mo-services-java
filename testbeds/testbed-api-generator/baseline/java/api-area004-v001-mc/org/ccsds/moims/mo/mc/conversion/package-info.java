/**
 * The conversion service provides a set of basic conversion definition types
 * that allows the specification of a conversion between two representations.
 * These conversions are used by the other MC services (such as Action, Alert,
 * and Parameter) to define conversions from raw field representations to
 * some engineering representation.
 * Conversions are associated with other entities such as parameters or action/alert
 * arguments through the configuration of the relevant service (action/alert/parameter).
 * The conversion service does not provide any operations directly, but allows
 * consumers to add, remove, and modify conversion definitions via the COM
 * archive.
*/
package org.ccsds.moims.mo.mc.conversion;
