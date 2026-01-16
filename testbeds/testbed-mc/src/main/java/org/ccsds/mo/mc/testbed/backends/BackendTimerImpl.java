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
package org.ccsds.mo.mc.testbed.backends;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

import org.ccsds.moims.mo.mc.backends.BackendTimer;

public class BackendTimerImpl implements BackendTimer {

	static final Logger logger = Logger.getLogger(BackendTimerImpl.class.getName());

	long origin;
	long now;
	long target;
	boolean running = false;
	boolean skipping = false;

	private class Schedule {
		long wakeUp;
		Runnable task;
		public Schedule(long wakeUp, Runnable task) {
			this.wakeUp = wakeUp;
			this.task = task;
		}
		public Schedule(long wakeUp) {
			this(wakeUp, null);
		}
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("{ wakeUp=").append(wakeUp);
			builder.append(", task=").append(task);
			return builder.toString();
		}
	}
	// must be accessed with lock on this BackendTimer object
	private final LinkedList<Schedule> schedules = new LinkedList<>();

	// must be accessed with lock on this BackendTimer object
	private final Set<Future<?>> runningTasks = new HashSet<>();
	
	public BackendTimerImpl() {}

	public BackendTimerImpl(long origin) {
		logger.info("new backend timer, origin=" + origin);
		init(origin);
	}

	@Override
	public void init(long origin) {
		this.origin = origin;
		now = origin;
		target = now;
		running = true;
	}

	@Override
	public void stop() {

	}

	@Override
	public long currentTimeMillis() {
		return now;
	}

	@Override
	public void sleep(long millis)
			throws InterruptedException {
		if (millis <= 0)
			return;
		long wakeUp = now + millis;
		synchronized(this) {
			addSchedule(wakeUp);
			while (running && now < wakeUp)
				wait();
			if (!running)
				logger.info("exit sleep, timer not running");
		}
	}

	@Override
	public void schedule(Runnable command, ExecutorService executor, long delay)
			throws RejectedExecutionException, NullPointerException {
		if (delay <= 0) {
			Future<?> task = executor.submit(command);
			// synchronize with task execution completion
			try {
				task.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			long wakeUp = now + delay;
			Schedule schedule = new Schedule(wakeUp,
					() -> {
						Future<?> task = executor.submit(command);
						// synchronize with task execution completion
						try {
							task.get();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
			synchronized(this) {
				addSchedule(schedule);
			}
		}
	}

	private void addSchedule(long wakeUp) {
		Schedule schedule = new Schedule(wakeUp);
		addSchedule(schedule);
	}
	private void addSchedule(Schedule schedule) {
		logger.info("schedule=" + schedule);
		int index = 0;
		long wakeUp = schedule.wakeUp;
		while (index < schedules.size()) {
			Schedule next = schedules.get(index);
			long nextTime = next.wakeUp;
			if (wakeUp < nextTime)
				break;
			if (wakeUp == nextTime && schedule.task == null && next.task == null)
				return;
			index++;
		}
		logger.info("add schedule at index=" + index);
		schedules.add(index, schedule);
	}

	/**
	 * Updates virtual time, and trigger all necessary executions.
	 * @param delay time increase in milliseconds
	 */
	public void skip(long delay) {
		logger.info("now=" + now + ", delay=" + delay);
		synchronized(this) {
			if (skipping) {
				logger.warning("should not call skip while skipping");
				long newTarget = now + delay;
				if (newTarget > target)
					target = newTarget;
				return;
			}
			skipping = true;
			target = now + delay;
		}
		while (skipping) {
			Schedule next = null;
			synchronized(this) {
				if (!schedules.isEmpty())
					next = schedules.getFirst();
				if (next == null || target < next.wakeUp) {
					now = target;
					skipping = false;
					return;
				}
				now = next.wakeUp;
				schedules.removeFirst();
			}
			if (next.task != null) {
				logger.info("execute task at " + now);
				next.task.run();
			} else {
				synchronized(this) {
					logger.info("notify waiting task at " + now);
					notifyAll();
				}
			}
			// give some time for the asynchronous consequences to take place
			// before skipping time again
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
}
