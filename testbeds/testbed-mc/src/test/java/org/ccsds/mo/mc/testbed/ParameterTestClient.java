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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.ccsds.mo.mc.testbed.ParameterListener.MonitorValueUpdate;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;
import org.ccsds.moims.mo.mc.structures.ParameterValue;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ParameterValueDataList;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.ReportConfiguration;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;

/**
* This class provides shared functions for all Parameter test clients.
*/
public class ParameterTestClient extends MCTest {

	public static final Duration DURATION_0 = new Duration(0);
	public static final Duration DURATION_60 = new Duration(60);
	public static final Duration DURATION_300 = new Duration(300);
	public static final Duration DURATION_600 = new Duration(600);
	
	protected static void execAndCheckMonitorValueRegister(
			Subscription subscription,
			ParameterListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncMonitorValueRegister(
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

	protected static void execAndCheckMonitorValueDeregister(
			IdentifierList subscriptions,
			ParameterListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncMonitorValueDeregister(
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
	 * All updates relates to the same Parameter.
	 * 
	 * @param listener	callback listener
	 * @param maxTime	max waiting time
	 * @param updates	expected updates
	 */
	protected static void waitAndCheckForUpdates(
			ParameterListener listener,
			long maxTime,
			MonitorValueUpdate[] updates) {
		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		long timeout = maxTime - System.currentTimeMillis();
		int nbUpdates = updates.length;
		synchronized(listener) {
			while (!listener.hasError() &&
					listener.valueUpdates.size() != nbUpdates &&
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
			if (listener.valueUpdates.size() != nbUpdates)
				unitTestFail("Incorrect number of updates received: " +
						"expected " + nbUpdates +
						" was " + listener.valueUpdates.size());
			// check received updates
			Iterator<MonitorValueUpdate> it = listener.valueUpdates.iterator();
			for (int idx = 0; it.hasNext(); idx++) {
				assertEquals("Update[" + idx + "]", updates[idx], it.next());
			}
		}
	}

	/**
	 * Waits for expected updates and checks them.
	 * 
	 * @param listener	callback listener
	 * @param maxTime	max waiting time
	 * @param updatesTab	list of expected updates, grouped by Parameter
	 */
	protected static void waitAndCheckForUpdates(
			ParameterListener listener,
			long maxTime,
			MonitorValueUpdate[][] updatesTab) {
		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		long timeout = maxTime - System.currentTimeMillis();
		int nbUpdates = 0;
		for (int i=0; i<updatesTab.length; i++)
			nbUpdates += updatesTab[i].length;
		synchronized(listener) {
			while (!listener.hasError() &&
					listener.valueUpdates.size() != nbUpdates &&
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
			if (listener.valueUpdates.size() != nbUpdates)
				unitTestFail("Incorrect number of updates received: " +
						"expected " + nbUpdates +
						" was " + listener.valueUpdates.size());
			// check received updates
			Hashtable<ObjectRef<ParameterDefinition>, List<MonitorValueUpdate>> received =
					new Hashtable<>(updatesTab.length);
			for (int i=0; i<updatesTab.length; i++)
				received.put(
						new ObjectRef<>(
								updatesTab[i][0].domain,
								ParameterDefinition.TYPE_ID.getTypeId(),
								updatesTab[i][0].parameterKey,
								updatesTab[i][0].parameterVersion),
						new ArrayList<MonitorValueUpdate>(updatesTab[i].length));
			Iterator<MonitorValueUpdate> it = listener.valueUpdates.iterator();
			while (it.hasNext()) {
				MonitorValueUpdate update = it.next();
				List<MonitorValueUpdate> updates = received.get(
						new ObjectRef<>(
								update.domain,
								ParameterDefinition.TYPE_ID.getTypeId(),
								update.parameterKey,
								update.parameterVersion));
				updates.add(update);
			}
			for (int i=0; i<updatesTab.length; i++) {
				ObjectRef<ParameterDefinition> parameter = new ObjectRef<>(
						updatesTab[i][0].domain,
						ParameterDefinition.TYPE_ID.getTypeId(),
						updatesTab[i][0].parameterKey,
						updatesTab[i][0].parameterVersion);
				List<MonitorValueUpdate> updates = received.get(parameter);
				if (updates.size() != updatesTab[i].length)
					unitTestFail("Incorrect number of updates received for Parameter " + parameter + ": " +
							"expected " + updatesTab[i].length +
							" was " + updates.size());
				it = updates.iterator();
				for (int idx = 0; it.hasNext(); idx++) {
					assertEquals(
							"Update[" + idx + "] for Parameter " + parameter,
							updatesTab[i][idx], it.next());
				}
			}
		}
	}

	protected static void waitAndCheckNoUpdate(
			ParameterListener listener,
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
			if (!listener.valueUpdates.isEmpty())
			unitTestFail("received unexpected updates");
		}
	}

	protected static void execAndCheckGetValue(
			IdentifierList domain,
            IdentifierList keys,
			ParameterListener listener,
			long maxTime,
			ParameterValueList expected) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncGetValue(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for response or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						listener.getValueResponse == null &&
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
				if (listener.getValueResponse == null)
					unitTestFail("The RESPONSE was not received!");
				
				assertEquals("error in getValue RESPONSE", expected, listener.getValueResponse);
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}
	protected static void execAndCheckErrorGetValue(
			IdentifierList domain,
            IdentifierList keys,
			ParameterListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncGetValue(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ERROR or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						listener.getValueResponse == null &&
						timeout > 0) {
					try {
						System.out.println("wait for response");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.getValueResponse != null)
					unitTestFail("Unexpected RESPONSE received");
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

	protected static void execAndCheckSetValue(
			IdentifierList domain,
            IdentifierList keys,
            NullableAttributeList newRawValues,
			ParameterListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncSetValue(
					domain,
					keys,
					newRawValues,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ack or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.setValueAckReceived &&
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
				if (!listener.setValueAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}
	protected static void execAndCheckErrorSetValue(
			IdentifierList domain,
            IdentifierList keys,
            NullableAttributeList newRawValues,
			ParameterListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncSetValue(
					domain,
					keys,
					newRawValues,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ERROR or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.setValueAckReceived &&
						timeout > 0) {
					try {
						System.out.println("wait for ACK");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.setValueAckReceived)
					unitTestFail("Unexpected RESPONSE received");
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

	protected static void execAndCheckGetReportingConfiguration(
			IdentifierList domain,
            IdentifierList keys,
			ParameterListener listener,
			long maxTime,
			ReportConfigurationList expected) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncGetReportingConfiguration(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for response or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						listener.getReportConfigResponse == null &&
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
				if (listener.getReportConfigResponse == null)
					unitTestFail("The RESPONSE was not received!");
				
				assertEquals("error in getReportingConfiguration RESPONSE", expected, listener.getReportConfigResponse);
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}

	protected static void execAndCheckEnableReporting(
			IdentifierList domain,
            IdentifierList keys,
			ParameterListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncEnableReporting(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ack or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.enableReportingAckReceived &&
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
				if (!listener.enableReportingAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}
	protected static void execAndCheckErrorEnableReporting(
			IdentifierList domain,
            IdentifierList keys,
			ParameterListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncEnableReporting(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ERROR or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.enableReportingAckReceived &&
						timeout > 0) {
					try {
						System.out.println("wait for ACK");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.enableReportingAckReceived)
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

	protected static void execAndCheckDisableReporting(
			IdentifierList domain,
            IdentifierList keys,
			ParameterListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncDisableReporting(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ack or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.disableReportingAckReceived &&
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
				if (!listener.disableReportingAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}
	protected static void execAndCheckErrorDisableReporting(
			IdentifierList domain,
            IdentifierList keys,
			ParameterListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncDisableReporting(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ERROR or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.disableReportingAckReceived &&
						timeout > 0) {
					try {
						System.out.println("wait for ACK");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.disableReportingAckReceived)
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

	protected static void execAndCheckSetReportingPeriod(
			IdentifierList domain,
            IdentifierList keys,
            Duration reportInterval,
			ParameterListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncSetReportingPeriod(
					domain,
					keys,
					reportInterval,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ack or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.setReportingPeriodAckReceived &&
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
				if (!listener.setReportingPeriodAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}
	protected static void execAndCheckErrorSetReportingPeriod(
			IdentifierList domain,
            IdentifierList keys,
            Duration reportInterval,
			ParameterListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			parameterConsumerStub.asyncSetReportingPeriod(
					domain,
					keys,
					reportInterval,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ack or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.setReportingPeriodAckReceived &&
						timeout > 0) {
					try {
						System.out.println("wait for ACK");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.setReportingPeriodAckReceived)
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

	public static void assertEquals(String error, ParameterValueList expected, ParameterValueList actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected list, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected list, expecting " + expected + ", was " + actual);
		if (actual.size() != expected.size())
			unitTestFail(error + " wrong list size, expecting " + expected.size() + ", was " + actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(error + " [" + i + "]", expected.get(i), actual.get(i));
		}
	}
	public static void assertEquals(String error, ParameterValue expected, ParameterValue actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected value, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
		assertEquals(error + " paramRef", expected.getParamRef(), actual.getParamRef());
		// ignore timestamp and samplingTime
		assertEquals(error + " value", expected.getValue(), actual.getValue());
	}
	public static void assertEquals(String error, ParameterValueDataList expected, ParameterValueDataList actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected list, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected list, expecting " + expected + ", was " + actual);
		if (actual.size() != expected.size())
			unitTestFail(error + " wrong list size, expecting " + expected.size() + ", was " + actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(error + " [" + i + "]", expected.get(i), actual.get(i));
		}
	}
	public static void assertEquals(String error, ParameterValueData expected, ParameterValueData actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected value, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
		if (!expected.getValidityState().equals(actual.getValidityState()))
			unitTestFail(error + " unexpected validity state, expecting " + expected.getValidityState() + ", was " + actual.getValidityState());
		if (expected.getRawValue() == null && actual.getRawValue() != null ||
				expected.getRawValue() != null && !expected.getRawValue().equals(actual.getRawValue()))
			unitTestFail(error + " unexpected raw value, expecting " + expected.getRawValue() + ", was " + actual.getRawValue());
		if (expected.getConvertedValue() == null && actual.getConvertedValue() != null ||
				expected.getConvertedValue() != null && !expected.getConvertedValue().equals(actual.getConvertedValue()))
			unitTestFail(error + " unexpected converted value, expecting " + expected.getConvertedValue() + ", was " + actual.getConvertedValue());
	}

	public static void assertEquals(String error, ReportConfigurationList expected, ReportConfigurationList actual) {
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
	public static void assertEquals(String error, ReportConfiguration expected, ReportConfiguration actual) {
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
		if (expected.getReportInterval() == null && actual.getReportInterval() != null ||
				expected.getReportInterval() != null && !expected.getReportInterval().equals(actual.getReportInterval()))
			unitTestFail(error + " unexpected reportInterval, expecting " + expected.getReportInterval() + ", was " + actual.getReportInterval());
	}
	
	public static void assertEquals(
			String error,
			MonitorValueUpdate expected,
			MonitorValueUpdate actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + ", expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + ", expecting " + expected + ", was " + actual);
		if (!expected.domain.equals(actual.domain))
			unitTestFail(error + " unexpected domain" +
					", expecting " + expected.domain +
					", was " + actual.domain);
		if (!actual.parameterKey.equals(expected.parameterKey))
			unitTestFail(error + " unexpected parameterKey subscription key" +
					", expecting " + expected.parameterKey +
					", was " + actual.parameterKey);
		if (!actual.parameterVersion.equals(expected.parameterVersion))
			unitTestFail(error + " unexpected parameterVersion subscription key" +
					", expecting " + expected.parameterVersion +
					", was " + actual.parameterVersion);
		assertEquals(error + " value data", expected.newValue, actual.newValue);
	}
}