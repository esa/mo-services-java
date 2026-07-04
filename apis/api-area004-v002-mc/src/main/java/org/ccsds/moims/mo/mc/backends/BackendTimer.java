/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
 * ----------------------------------------------------------------------------
 * System                : ESA CCSDS MO Services
 * ----------------------------------------------------------------------------
 * Licensed under European Space Agency Public License (ESA-PL) Weak Copyleft – v2.4
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * Provides a Timer interface to use by the provider to allow simulating a virtual time during tests.
 */
public interface BackendTimer {

	/**
	 * Initializes the timer.
	 * 
	 * @param origin	time origin (ms since Epoch) in the test implementation,
	 * 					may be ignored in a standard implementation.
	 */
	public void init(long origin);

	/**
	 * Stops the timer.
	 * Frees all waiting threads.
	 */
	public void stop();

	/**
	 * Gets the current virtual time of the timer in milliseconds.
	 * This results in a call to {@link System#currentTimeMillis()} in a standard implementation.
	 * 
	 * @return	the current time
	 */
	public long currentTimeMillis();

	/**
	 * Causes the currently executing thread to sleep for the specified number of milliseconds of virtual time.
	 * This results in a call to {@link Thread#sleep(long)} in a standard implementation.
	 * 
	 * @param millis	time to wait in milliseconds 	
	 * @throws InterruptedException
	 */
	public void sleep(long millis)
			throws InterruptedException;

	/**
	 * Submits a one-shot task that will be planified for execution by the provided Executor
	 * after the specified number of milliseconds of virtual time.
	 * This results in a call to {@link java.util.concurrent.ScheduledExecutorService#schedule(Runnable, long, java.util.concurrent.TimeUnit)} in a standard implementation.
	 * 
	 * @param command	task to execute
	 * @param executor	executor to use for executing the task
	 * @param delay		delay in milliseconds before executing the task
	 * @throws RejectedExecutionException
	 * @throws NullPointerException
	 */
	public void schedule(Runnable command, ExecutorService executor, long delay)
			throws RejectedExecutionException, NullPointerException;

}
