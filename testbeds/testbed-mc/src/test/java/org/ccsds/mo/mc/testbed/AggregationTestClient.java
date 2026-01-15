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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.ccsds.mo.mc.testbed.AggregationListener.MonitorValueUpdate;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mc.structures.AggregationDefinition;
import org.ccsds.moims.mo.mc.structures.AggregationDefinitionList;
import org.ccsds.moims.mo.mc.structures.AggregationValue;
import org.ccsds.moims.mo.mc.structures.AggregationValueList;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;
import org.ccsds.moims.mo.mc.structures.ValidityState;

/**
* This class provides shared functions for all Aggregation test clients.
*/
public class AggregationTestClient extends MCTest {

	public static final Duration DURATION__1 = new Duration(-1);
	public static final Duration DURATION_0 = new Duration(0);
	public static final Duration DURATION_60 = new Duration(60);
	public static final Duration DURATION_120 = new Duration(120);
	public static final Duration DURATION_301 = new Duration(301);
	public static final Duration DURATION_602 = new Duration(602);

	public static final ParameterValueData INVALID_RAW_VALUE =
			new ParameterValueData(
					ValidityState.INVALID_RAW,
					null,
					null);
	public static final ParameterValueData MTQ1ENABLED_ENABLED_VALUE =
			new ParameterValueData(
					ValidityState.VALID,
					NA_UINT_1.getValue(),
					NA_STRING_ENABLED.getValue());
	public static final ParameterValueData MTQ1ENABLED_EXPIRED_VALUE =
			new ParameterValueData(
					ValidityState.EXPIRED,
					NA_UINT_1.getValue(),
					NA_STRING_ENABLED.getValue());

	public static final ParameterValueData MTQ1VOLTAGE_1200_VALUE =
			new ParameterValueData(ValidityState.VALID,
					NA_DOUBLE_1200.getValue(),
					null);
	public static final ParameterValueData MTQ1VOLTAGE_2200_VALUE =
			new ParameterValueData(ValidityState.VALID,
					NA_DOUBLE_2200.getValue(),
					null);
	
	public static ObjectIdentity getAggregationIdentity(
			ObjectRef<AggregationDefinition> objectRef) {
		if (objectRef == null)
			return null;
		return new ObjectIdentity(
				objectRef.getDomain(),
				objectRef.getKey(),
				objectRef.getObjectVersion());
	}
	
	protected static void execAndCheckMonitorValueRegister(
			Subscription subscription,
			AggregationListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncMonitorValueRegister(
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
			AggregationListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncMonitorValueDeregister(
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
			AggregationListener listener,
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
	 * @param updatesTab	list of expected updates, grouped by Aggregation
	 */
	protected static void waitAndCheckForUpdates(
			AggregationListener listener,
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
			Hashtable<ObjectRef<AggregationDefinition>, List<MonitorValueUpdate>> received =
					new Hashtable<>(updatesTab.length);
			for (int i=0; i<updatesTab.length; i++)
				received.put(
						new ObjectRef<>(
								updatesTab[i][0].domain,
								AggregationDefinition.TYPE_ID.getTypeId(),
								updatesTab[i][0].aggregationKey,
								updatesTab[i][0].aggregationVersion),
						new ArrayList<MonitorValueUpdate>(updatesTab[i].length));
			Iterator<MonitorValueUpdate> it = listener.valueUpdates.iterator();
			while (it.hasNext()) {
				MonitorValueUpdate update = it.next();
				List<MonitorValueUpdate> updates = received.get(
						new ObjectRef<>(
								update.domain,
								AggregationDefinition.TYPE_ID.getTypeId(),
								update.aggregationKey,
								update.aggregationVersion));
				updates.add(update);
			}
			for (int i=0; i<updatesTab.length; i++) {
				ObjectRef<AggregationDefinition> aggregation = new ObjectRef<>(
						updatesTab[i][0].domain,
						AggregationDefinition.TYPE_ID.getTypeId(),
						updatesTab[i][0].aggregationKey,
						updatesTab[i][0].aggregationVersion);
				List<MonitorValueUpdate> updates = received.get(aggregation);
				if (updates.size() != updatesTab[i].length)
					unitTestFail("Incorrect number of updates received for Aggregation " + aggregation + ": " +
							"expected " + updatesTab[i].length +
							" was " + updates.size());
				it = updates.iterator();
				for (int idx = 0; it.hasNext(); idx++) {
					assertEquals(
							"Update[" + idx + "] for Aggregation " + aggregation,
							updatesTab[i][idx], it.next());
				}
			}
		}
	}

	protected static void waitAndCheckNoUpdate(
			AggregationListener listener,
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
            AggregationListener listener,
			long maxTime,
			AggregationValueList expected) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncGetValue(
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
            AggregationListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncGetValue(
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

	protected static void execAndCheckGetReportingConfiguration(
			IdentifierList domain,
            IdentifierList keys,
            AggregationListener listener,
			long maxTime,
			ReportConfigurationList expected) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncGetReportingConfiguration(
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
				
				ParameterTestClient.assertEquals(
						"error in getReportingConfiguration RESPONSE",
						expected,
						listener.getReportConfigResponse);
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
            AggregationListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncEnableReporting(
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
            AggregationListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncEnableReporting(
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
            AggregationListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncDisableReporting(
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
            AggregationListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncDisableReporting(
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
            AggregationListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncSetReportingPeriod(
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
            AggregationListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncSetReportingPeriod(
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

	protected static void execAndCheckListDefinition(
			IdentifierList domain,
            IdentifierList keys,
            AggregationListener listener,
			long maxTime,
			AggregationDefinitionList expected) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncListDefinition(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for RESPONSE or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						listener.listDefinitionResponse == null &&
						timeout > 0) {
					try {
						System.out.println("wait for RESPONSE");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.hasError())
					unitTestFail(listener.getError());
				if (listener.listDefinitionResponse == null)
					unitTestFail("The RESPONSE was not received!");
				
				if (keys == null) {
					assertMatches(
							"error in listDefinition RESPONSE",
							expected,
							listener.listDefinitionResponse);
				} else {
					assertEquals(
							"error in listDefinition RESPONSE",
							expected,
							listener.listDefinitionResponse);
				}
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}
	protected static void execAndCheckErrorListDefinition(
			IdentifierList domain,
            IdentifierList keys,
            AggregationListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncListDefinition(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for RESPONSE or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						listener.listDefinitionResponse == null &&
						timeout > 0) {
					try {
						System.out.println("wait for RESPONSE");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.listDefinitionResponse != null)
					unitTestFail("Unexpected RESPONSE received");
				if (listener.error == null)
					unitTestFail("Missing expected RESPONSE error");
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

	protected static void execAndCheckAddAggregation(
			AggregationDefinitionList newObjects,
            AggregationListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncAddAggregation(
					newObjects,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ack or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.addAggregationAckReceived &&
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
				if (!listener.addAggregationAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}
	protected static void execAndCheckErrorAddAggregation(
			AggregationDefinitionList newObjects,
            AggregationListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncAddAggregation(
					newObjects,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ERROR or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.addAggregationAckReceived &&
						timeout > 0) {
					try {
						System.out.println("wait for ACK");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.addAggregationAckReceived)
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

	protected static void execAndCheckRemoveAggregation(
			IdentifierList domain,
            IdentifierList keys,
            AggregationListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncRemoveAggregation(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ack or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.removeAggregationAckReceived &&
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
				if (!listener.removeAggregationAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}
	protected static void execAndCheckErrorRemoveAggregation(
			IdentifierList domain,
            IdentifierList keys,
            AggregationListener listener,
			long maxTime,
			UInteger errorNumber,
			Object extraInfo) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			aggregationConsumerStub.asyncRemoveAggregation(
					domain,
					keys,
					listener);

			// ------------------------------------------------------------------------
			// Wait for ERROR or TIMOUT
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.removeAggregationAckReceived &&
						timeout > 0) {
					try {
						System.out.println("wait for ACK");
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate timer
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.removeAggregationAckReceived)
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
		if (!expected.aggregationKey.equals(actual.aggregationKey))
			unitTestFail(error + " unexpected aggregationKey subscription key" +
					", expecting " + expected.aggregationKey +
					", was " + actual.aggregationKey);
		if (!expected.aggregationVersion.equals(actual.aggregationVersion))
			unitTestFail(error + " unexpected aggregationVersion subscription key" +
					", expecting " + expected.aggregationVersion +
					", was " + actual.aggregationVersion);
		ParameterTestClient.assertEquals(error + " values", expected.values, actual.values);
	}

	public static void assertEquals(String error, AggregationValueList expected, AggregationValueList actual) {
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
	public static void assertEquals(String error, AggregationValue expected, AggregationValue actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected value, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
		assertEquals(error + " aggregationRef", expected.getAggregationRef(), actual.getAggregationRef());
		// ignore timestamp
		ParameterTestClient.assertEquals(error + " value", expected.getParameterValues(), actual.getParameterValues());
	}

	public static void assertEquals(String error, AggregationDefinitionList expected, AggregationDefinitionList actual) {
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
	public static void assertEquals(String error, AggregationDefinition expected, AggregationDefinition actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected value, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
		if (! expected.getObjectIdentity().equals(actual.getObjectIdentity()))
			unitTestFail(error + " unexpected objectIdentity" +
					", expecting " + expected.getObjectIdentity() +
					", was " + actual.getObjectIdentity());
		if (! expected.getDescription().equals(actual.getDescription()))
			unitTestFail(error + " unexpected description" +
					", expecting " + expected.getDescription() +
					", was " + actual.getDescription());
		if ((expected.getCategory() == null && actual.getCategory() != null) ||
				(expected.getCategory() != null && !expected.getCategory().equals(actual.getCategory())))
			unitTestFail(error + " unexpected category" +
					", expecting " + expected.getCategory() +
					", was " + actual.getCategory());
		assertEquals(error + " parameters", expected.getParameters(), actual.getParameters());
	}
	public static void assertMatches(String error, AggregationDefinitionList expected, AggregationDefinitionList actual) {
		// order of elements is not relevant
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected list, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected list, expecting " + expected + ", was " + actual);
		if (actual.size() != expected.size())
			unitTestFail(error + " wrong list size, expecting " + expected.size() + ", was " + actual.size());
		HashMap<ObjectIdentity, AggregationDefinition> actualMap = new HashMap<>();
		for (int i = 0; i < actual.size(); i++) {
			if (actualMap.put(actual.get(i).getObjectIdentity(), actual.get(i)) != null)
				unitTestFail(error + " duplicate element " + i + ": " + actual.get(i));
		}
		for (int i = 0; i < expected.size(); i++) {
			ObjectIdentity aggregId = expected.get(i).getObjectIdentity();
			AggregationDefinition actualDefinition = actualMap.get(aggregId);
			assertEquals(error + " [" + i + "]", expected.get(i), actualDefinition);
			actualMap.remove(aggregId);
		}
	}
	
	public static void assertEquals(String error, ObjectRefList expected, ObjectRefList actual) {
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
	public static void assertEquals(String error, ObjectRef expected, ObjectRef actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected value, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
		if (! expected.getTypeId().equals(actual.getTypeId()))
			unitTestFail(error + " unexpected typeId" +
					", expecting " + expected.getTypeId() +
					", was " + actual.getTypeId());
		if (! expected.getDomain().equals(actual.getDomain()))
			unitTestFail(error + " unexpected domain" +
					", expecting " + expected.getDomain() +
					", was " + actual.getDomain());
		if (! expected.getKey().equals(actual.getKey()))
			unitTestFail(error + " unexpected key" +
					", expecting " + expected.getKey() +
					", was " + actual.getKey());
		if (! expected.getObjectVersion().equals(actual.getObjectVersion()))
			unitTestFail(error + " unexpected objectVersion" +
					", expecting " + expected.getObjectVersion() +
					", was " + actual.getObjectVersion());
	}
	
}