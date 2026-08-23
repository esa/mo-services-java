/**
 * The check service allows the user to monitor and control the checking of
 * parameters values against check definitions including limit sets. Violations
 * of these defined checks are published using the COM event service.
 * The check service allows the consumer to define checks and then link the
 * check definition to a parameter to be monitored. Figure 3-6 shows the nominal
 * sequence of operations for the check service:
 * ---- check sequence diag ----
 * Check service nominal sequence
 * A consumer first subscribes for check events before linking some already
 * existing checks to some parameters. The figure shows how check evaluation
 * is normally triggered by either a periodic interval or a change in a monitored
 * value, however, the service also supports the optional ability for consumers
 * to trigger the evaluation of checks using the triggerCheck operation. Finally,
 * if a check event has not been generated for a configurable period of time,
 * known as the maximum reporting interval, an event is generated regardless
 * of whether the check is in violation or not. This maximum reporting interval
 * supports the situation where regular confirmation of the non-violating
 * state of a check is required.
 * The list of currently violating checks can be obtained using the getCurrentTransitionList
 * operation; to get a full report including non-violating checks the getSummaryReport
 * operation should be used.
 * The following figure (3-7) shows the flow chart for determining the status
 * of a check:
 * --- check state diag ---
 * Flow Chart for Determining the Status of a Check
 * Each time a check is evaluated, the procedure indicated in the flow chart
 * depicted in figure 3-7 is performed to calculate the check result. Each
 * check link can have a condition included which determines whether the check
 * should be applied or not; this allows several checks to be associated to
 * a single parameter and applied in different conditions.
 * The service defines five basic check types, constant, reference, delta,
 * limit, and compound. A constant check is used to ensure that a parameter
 * value either does not change or does not change from a set of specific
 * values.
 * In the Reference and Delta checks, a value is compared against another
 * value which serves as a reference value. The reference value can be taken
 * from either another parameter or the parameter being checked, and can be
 * the previous value or a value in the past, specified by using a delta time
 * in the ReferenceValue composite. This does not affect the logic of the
 * flow chart above.
 * Limit checks monitor parameters to see if the parameter value is either
 * inside or outside a range of values.
 * The final check type, compound, monitors a set of previously defined checks
 * and only violates when a specified number of those monitored checks violate
 * themselves.
 * The service only publishes check results (as check transition events) when
 * either the result changes from the previous value (for example a check
 * starts to violate), or the maximum reporting interval expires. This reduces
 * the amount of &quot;no change&quot; reporting data being distributed. For
 * situations where regular reporting of the check result is required, even
 * when no change is detected, a low maximum reporting interval value should
 * be specified.
*/
package org.ccsds.moims.mo.mc.check;
