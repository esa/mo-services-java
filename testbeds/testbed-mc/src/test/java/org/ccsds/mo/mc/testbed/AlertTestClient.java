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

import java.util.Iterator;
import org.ccsds.mo.mc.testbed.AlertListener.MonitorAlertUpdate;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mc.structures.AlertConfiguration;
import org.ccsds.moims.mo.mc.structures.AlertConfigurationList;

/**
* This class provides shared functions for all Alert test clients.
*/
public class AlertTestClient extends MCTest {

	protected static void execAndCheckMonitorAlertRegister(
			Subscription subscription,
			AlertListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			alertConsumerStub.asyncMonitorAlertRegister(
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
					// Recalculate it
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

	protected static void execAndCheckMonitorAlertDeregister(
			IdentifierList subscriptions,
			AlertListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			alertConsumerStub.asyncMonitorAlertDeregister(
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

	/**
	 * Waits for expected updates and checks them.
	 * 
	 * @param listener	callback listener
	 * @param maxTime	max waiting time
	 * @param updates	expected updates
	 */
	protected static void waitAndCheckForUpdates(
			AlertListener listener,
			long maxTime,
			MonitorAlertUpdate[] updates) {
		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		long timeout = maxTime - System.currentTimeMillis();
		int nbUpdates = updates.length;
		synchronized(listener) {
			while (!listener.hasError() &&
					listener.alertUpdates.size() != nbUpdates &&
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
			if (listener.alertUpdates.size() != nbUpdates)
				unitTestFail("Incorrect number of updates received: " +
						"expected " + nbUpdates +
						" was " + listener.alertUpdates.size());
			// check received updates
			Iterator<MonitorAlertUpdate> it = listener.alertUpdates.iterator();
			for (int idx = 0; it.hasNext(); idx++) {
				assertEquals("Update[" + idx + "]", updates[idx], it.next());
			}
		}
	}

	protected static void waitAndCheckNoUpdate(
			AlertListener listener,
			long maxTime) {
		// ------------------------------------------------------------------------
		// Wait for TIMOUT
		long timeout = maxTime - System.currentTimeMillis();
		synchronized(listener) {
			while (!listener.hasError() &&
					timeout > 0) {
				try {
					System.out.println("wait for timeout");
					listener.wait(timeout);
				} catch (InterruptedException e) {}
				// Recalculate timer
				timeout = maxTime - System.currentTimeMillis();
			}
			if (listener.hasError())
				unitTestFail(listener.getError());

			// check no new message from subscription
			if (!listener.alertUpdates.isEmpty())
			unitTestFail("received unexpected updates");
		}
	}

	protected static void execAndCheckGetAlertConfiguration(
			IdentifierList domain,
            IdentifierList keys,
			AlertListener listener,
			long maxTime,
			AlertConfigurationList expected) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			alertConsumerStub.asyncGetAlertConfiguration(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for response or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						listener.getAlertConfigurationResponse == null &&
						timeout > 0) {
					try {
						System.out.println("wait for response");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.hasError())
					unitTestFail(listener.getError());
				if (listener.getAlertConfigurationResponse == null)
					unitTestFail("The RESPONSE was not received!");
				
				assertEquals(
						"error in getAlertConfiguration RESPONSE",
						expected,
						listener.getAlertConfigurationResponse);
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}
	protected static void execAndCheckErrorGetAlertConfiguration(
			IdentifierList domain,
            IdentifierList keys,
			AlertListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			alertConsumerStub.asyncGetAlertConfiguration(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for response or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						listener.getAlertConfigurationResponse == null &&
						timeout > 0) {
					try {
						System.out.println("wait for response");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.getAlertConfigurationResponse != null)
					unitTestFail("Unexpected RESPONSE received");
				if (listener.error == null)
					unitTestFail("Missing expected response error");
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

	protected static void execAndCheckEnableGeneration(
			IdentifierList domain,
            IdentifierList keys,
			AlertListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			alertConsumerStub.asyncEnableGeneration(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ack or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.enableGenerationAckReceived &&
						timeout > 0) {
					try {
						System.out.println("wait for ACK");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.hasError())
					unitTestFail(listener.getError());
				if (!listener.enableGenerationAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}
	protected static void execAndCheckErrorEnableGeneration(
			IdentifierList domain,
            IdentifierList keys,
			AlertListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			alertConsumerStub.asyncEnableGeneration(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ack or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.enableGenerationAckReceived &&
						timeout > 0) {
					try {
						System.out.println("wait for ACK");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.enableGenerationAckReceived)
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

	protected static void execAndCheckDisableGeneration(
			IdentifierList domain,
            IdentifierList keys,
			AlertListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			alertConsumerStub.asyncDisableGeneration(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ack or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.disableGenerationAckReceived &&
						timeout > 0) {
					try {
						System.out.println("wait for ACK");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.hasError())
					unitTestFail(listener.getError());
				if (!listener.disableGenerationAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}
	protected static void execAndCheckErrorDisableGeneration(
			IdentifierList domain,
            IdentifierList keys,
			AlertListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			alertConsumerStub.asyncDisableGeneration(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ack or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.disableGenerationAckReceived &&
						timeout > 0) {
					try {
						System.out.println("wait for ACK");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.disableGenerationAckReceived)
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
			MonitorAlertUpdate expected,
			MonitorAlertUpdate actual) {
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
		if (!actual.alertKey.equals(expected.alertKey))
			unitTestFail(error + " unexpected alertKey subscription key" +
					", expecting " + expected.alertKey +
					", was " + actual.alertKey);
		if (!actual.alertSeverity.equals(expected.alertSeverity))
			unitTestFail(error + " unexpected alertSeverity subscription key" +
					", expecting " + expected.alertSeverity +
					", was " + actual.alertSeverity);
		if (!actual.alertVersion.equals(expected.alertVersion))
			unitTestFail(error + " unexpected alertVersion subscription key" +
					", expecting " + expected.alertVersion +
					", was " + actual.alertVersion);
		assertEquals(error + " arguments", expected.arguments, actual.arguments);
	}

	public static void assertEquals(String error, AlertConfigurationList expected, AlertConfigurationList actual) {
		if (expected == null) {
			if (actual != null)
				unitTestFail(error + " unexpected list, expecting null, was " + actual);
			return;
		}
		if (actual == null)
			unitTestFail(error + " unexpected list, expecting " + expected + ", was " + actual);
		if (actual.size() != expected.size())
			unitTestFail(error + " wrong list size, expecting " + expected.size() + ", was " + actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(error + " [" + i + "]", expected.get(i), actual.get(i));
		}
	}
	public static void assertEquals(String error, AlertConfiguration expected, AlertConfiguration actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected value, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
		if (expected.getGenerationEnabled() == null && actual.getGenerationEnabled() != null ||
				expected.getGenerationEnabled() != null && !expected.getGenerationEnabled().equals(actual.getGenerationEnabled()))
			unitTestFail(error + " unexpected generationEnabled, expecting " + expected.getGenerationEnabled() + ", was " + actual.getGenerationEnabled());
	}
}