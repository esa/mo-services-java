/**
 * The statistic service allows the consumer to monitor and control the statistical
 * evaluation (e.g., average) of parameters.
 * The statistic service allows the consumer to link the statistic function
 * to a parameter to be evaluated. Figure 3-9 shows the nominal sequence of
 * operations for the statistic service:
 * ---- nominal sequence diag ----
 * Statistic service nominal sequence
 * For each statistics link the service allows a consumer to specify a sampling
 * interval, a reporting interval, and a collection interval.
 * The sampling interval defines the interval between samples of the linked
 * parameter, the reporting interval defines the interval between reports
 * of the current statistic evaluation value, and the collection interval
 * defines the period of time which parameter values are collected for the
 * statistic function.
 * A consumer can create several links to the same parameter with different
 * intervals, for example for the function &quot;Mean&quot;, a consumer might
 * define two links with different collection intervals, one for an hour (D0)
 * and one for four hours (D1). The consumer might also specify the reporting
 * interval of half an hour for the first link and two hours for the second
 * link. This would mean that the service provider would produce reports as
 * shown in Figure 3-10.
 * --- timeline chart ---
 * Example Statistic interval reporting
 * If a statistic is defined to reset on collection interval expiration the
 * currently calculated value, and any input collection of values, for the
 * parameter being sampled is reset every collection interval, so if an hourly
 * collection interval is defined for the function &quot;Mean&quot; then every
 * hour the current mean average value is reset (this does not affect the
 * value reported by the Parameter service itself or any other links for the
 * same function and parameter), the set of values used to calculate that
 * mean average is cleared, and a report is generated containing the final
 * value of the function just before the reset.
 * If a statistic is defined not to reset every collection interval, then
 * the statistic maintains a moving evaluation for the collection interval.
 * For example, if a collection interval of an hour is defined with a reset
 * Boolean of FALSE for the function &quot;Maximum&quot; then the evaluation
 * will hold the maximum value obtained in the last hour.
 * The consumer is also able to define the sampling interval of the parameter
 * for a link. This is independent of both the collection interval and the
 * reporting interval. It makes sense for the sampling interval to be smaller
 * than both the collection and reporting interval however it is perfectly
 * possible to specify other values, as this is a deployment decision.
 * For the statistic service, the list of possible statistics functions is
 * deployment-dependent, as any function would have to be implemented in the
 * service provider. There are no operations for the creation, modification,
 * or deletion of the statistic functions. The statistic service defines the
 * evaluation of minimum, maximum, mean, and standard deviation functions,
 * however the service supports the addition of other statistical functions
 * by implementations.
 * NOTE: To ensure deterministic behaviour, implementations should endeavour
 * to respect Nyquistï¿½s theorem.
*/
package org.ccsds.moims.mo.mc.statistic;
