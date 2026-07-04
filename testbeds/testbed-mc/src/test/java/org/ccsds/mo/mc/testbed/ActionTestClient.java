/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
 *                         Adapted to the M&C testbed from the MPD testbed
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Testbed - M&C
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
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
package org.ccsds.mo.mc.testbed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mc.structures.ActionExecutionRequest;

/**
 * This class provides shared functions for all Action test clients.
 */
public class ActionTestClient extends MCTest {

	// test values used in Action tests for MIS_TC_DEFATTITUDE args
	public static final NullableAttribute NA_DOUBLE_13E0 = new NullableAttribute(new Union(new Double(1.3e0)));
	public static final NullableAttribute NA_DOUBLE_21E9 = new NullableAttribute(new Union(new Double(2.1e9)));
	public static final NullableAttribute NA_DOUBLE_60E1 = new NullableAttribute(new Union(new Double(6.0e1)));
	public static final NullableAttribute NA_STRING_OK = new NullableAttribute(new Union(Constant.STR_OK));
	public static final NullableAttribute NA_STRING_FAIL2 = new NullableAttribute(new Union(Constant.STR_FAIL2));
	public static final NullableAttribute NA_STRING_SKIP = new NullableAttribute(new Union(Constant.STR_SKIP));
	public static final NullableAttribute NA_STRING_WAIT = new NullableAttribute(new Union(Constant.STR_WAIT));
	public static final NullableAttribute NA_STRING_ERROR_REJECTED =
			new NullableAttribute(new Union(new String("error Rejected")));
	public static final NullableAttribute NA_ULONG_0 = new NullableAttribute(new ULong(new java.math.BigInteger("0")));
	public static final NullableAttribute NA_NULL = new NullableAttribute(null);

	public static final NullableAttribute NA_TIME_10000 =
			new NullableAttribute(new Time(10000));
	public static final NullableAttributeList CHGTABSVAL_DFLT_ARGS =
			new NullableAttributeList(new ArrayList<> (Arrays.asList(NA_TIME_10000)));
	public static final NullableAttributeList DEFATTITUDE_DFLT_ARGS =
			new NullableAttributeList(new ArrayList<> (Arrays.asList(
					NA_DOUBLE_21E9, NA_DOUBLE_60E1, NA_STRING_OK, NA_ULONG_0,
					NA_DOUBLE_13E0, NA_NULL, NA_NULL)));

	protected static void execAndCheckMonitorExecutionRegister(
			Subscription subscription,
			ActionListener listener,
			long maxTime) {

		try {
			long startTime = System.currentTimeMillis();
			long timeout = maxTime - startTime;
			actionConsumerStub = setUp.getActionConsumer();
			actionConsumerStub.asyncMonitorExecutionRegister(
					subscription,
					listener);

			// ------------------------------------------------------------------------
			// Wait while ACK has not been received and TIMOUT has not passed yet...
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.registerAckReceived &&
						timeout > 0) {
					try {
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timeout
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.hasError())
					unitTestFail(listener.getError());
				if (!listener.registerAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}

	protected static void execAndCheckMonitorExecutionDeregister(
			IdentifierList subscriptions,
			ActionListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			actionConsumerStub.asyncMonitorExecutionDeregister(
					subscriptions,
					listener);

			// ------------------------------------------------------------------------
			// Wait while ACK has not been received and TIMOUT has not passed yet...
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.deregisterAckReceived &&
						timeout > 0) {
					try {
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate it
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.hasError())
					unitTestFail(listener.getError());
				if (!listener.deregisterAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}

	protected static void waitAndCheckForUpdates(
			ActionListener listener,
			long maxTime,
			ActionListener.MonitorExecutionUpdate[] updates) {
		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		long timeout = maxTime - System.currentTimeMillis();
		int nbUpdates = updates.length;
		synchronized(listener) {
			while (!listener.hasError() &&
					listener.executionUpdates.size() != nbUpdates &&
					timeout > 0) {
				try {
					System.out.println("wait for Updates from subscription");
					listener.wait(timeout);
				} catch (InterruptedException e) {}
				// Recalculate timer
				timeout = maxTime - System.currentTimeMillis();
			}
			System.out.println("end wait, listener=" + listener);
			if (listener.hasError())
				unitTestFail(listener.getError());
			if (listener.executionUpdates.size() != nbUpdates)
				unitTestFail("Incorrect number of updates received: " +
						"expected " + nbUpdates +
						" was " + listener.executionUpdates.size());
			// check received updates
			Iterator<ActionListener.MonitorExecutionUpdate> it = listener.executionUpdates.iterator();
			for (int idx = 0; it.hasNext(); idx++) {
				assertEquals("Update[" + idx + "]", updates[idx], it.next());
			}
		}
	}

	protected static void execAndCheckExecute(
			ActionExecutionRequest request,
			ActionListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			actionConsumerStub.asyncExecute(
					request,
					listener);

			// ------------------------------------------------------------------------
			// Wait while ACK has not been received and TIMOUT has not passed yet...
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.executeAckReceived &&
						timeout > 0) {
					try {
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timeout
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.hasError())
					unitTestFail(listener.getError());
				if (!listener.executeAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}

	protected static void execAndCheckErrorExecute(
			ActionExecutionRequest request,
			ActionListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			actionConsumerStub.asyncExecute(
					request,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ERROR or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.executeAckReceived &&
						timeout > 0) {
					try {
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timeout
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.executeAckReceived)
					unitTestFail("Unexpected ACK received");
				if (listener.error == null)
					unitTestFail("Missing expected ACK error");
				if (!errorNumber.equals(listener.error.getErrorNumber()))
					unitTestFail("Wrong error received" +
							", expecting " + errorNumber +
							", was " + listener.error);
				if (extraInfo != null) {
					if (extraInfo instanceof UIntegerList) {
						assertEquals(
								"Error in extraInfo field",
								(UIntegerList) extraInfo,
								(UIntegerList) listener.error.getExtraInformation());
					} else if (!extraInfo.equals(listener.error.getExtraInformation())) {
						unitTestFail("Error in extraInfo field" +
								", expecting " + extraInfo +
								", was " + listener.error.getExtraInformation());
					}
				}
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}


	protected static void assertEquals(
			String error,
			ActionListener.MonitorExecutionUpdate expected,
			ActionListener.MonitorExecutionUpdate actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + ", expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + ", expecting " + expected + ", was " + actual);
		if (!actual.domain.equals(expected.domain))
			unitTestFail(error + " unexpected domain" +
					", expecting " + expected.domain +
					", was " + actual.domain);
		if (!actual.requestId.equals(expected.requestId))
			unitTestFail(error + " unexpected requestId subscription key" +
					", expecting " + expected.requestId +
					", was " + actual.requestId);
		if (!actual.actionKey.equals(expected.actionKey))
			unitTestFail(error + " unexpected actionKey subscription key" +
					", expecting " + expected.actionKey +
					", was " + actual.actionKey);
		if (!actual.actionCategory.equals(expected.actionCategory))
			unitTestFail(error + " unexpected actionCategory subscription key" +
					", expecting " + expected.actionCategory +
					", was " + actual.actionCategory);
		assertEquals(error + " progressEvent", expected.progressEvent, actual.progressEvent);
	}

}