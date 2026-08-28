/**
 * The aggregation service allows the user to acquire several parameter values
 * in a single request.
 * Aggregations are generated either periodically, on an ad-hoc basis, or
 * after a configurable timeout when being filtered. Periodic is where they
 * are generated at a specific generation interval; in this case the periodicity
 * is an aspect of the aggregation rather than the contained parameters. Ad-hoc
 * is when the aggregation generation is triggered by some other deployment-specific
 * mechanism.
 * Filtering is where an aggregation is only generated when the change in
 * the value of the filtered parameters exceeds a specified threshold or a
 * timeout is passed. Filtering can be applied to both periodic and ad-hoc
 * aggregations.
 * As there may be a large amount of time between reports of an aggregation
 * when filtering is applied, the filtering concept also has a maximum reporting
 * interval. If a report of the aggregation has not passed the filter in the
 * maximum reporting interval a report is generated regardless. This allows
 * there to be regular reports of an aggregation sent regardless of filtering.
 * It should be noted that applying a filter with a maximum reporting interval
 * to an ad-hoc aggregation will cause the aggregation to generate reports
 * in a periodic way if the maximum reporting interval is left to expire.
 * This is expected behaviour.
 * There are a number of intervals defined in the structures of the service,
 * the diagram in Figure 3-12 illustrates the use of these:
 * insert timing diagram
 * The diagram shows a single report of an aggregation being generated which
 * contains two parameter sets. Each set of parameter values in the aggregation
 * report contains two optional durations, delta time and interval time.
 * <ul>
 * <li>The timestamp of the first value of the first set (T1) is defined
 * as the timestamp of the aggregation report plus the delta time of the first
 * set.</li>
 * <li>The timestamp of the second value of the first set (T2) is defined
 * as the timestamp of the first parameter (T1) plus the interval time of
 * the set.</li>
 * <li>The timestamp of the first value of the second set (T3) is defined
 * as the timestamp of the last value of the previous set (T2) plus the delta
 * time of the second set.</li>
 * <li>The timestamp of the second value of the second set (T4) is defined
 * as the timestamp of the first parameter (T3) plus the interval time of
 * the set.</li>
 * </ul>
 * The COM archive is used to hold the definitions of the aggregations.
*/
package org.ccsds.moims.mo.mc.aggregation;
