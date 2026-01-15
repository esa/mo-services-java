/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import org.ccsds.mo.mc.testbed.Constant;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mc.ActionDataset;
import org.ccsds.moims.mo.mc.structures.ActionCategory;
import org.ccsds.moims.mo.mc.structures.ActionDefinition;
import org.ccsds.moims.mo.mc.structures.ActionExecutionRequest;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinition;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList;

/**
 * ActionBasicDataset implements the dataset #AC-2.
 */
public class ActionDefaultDataset extends ActionDataset {

	public static final ObjectRef<ActionDefinition> sat1ChgTAbsValRef =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					ActionDefinition.TYPE_ID.getTypeId(),
					Constant.ID_CHGTABSVAL,
					new UInteger(1));
	public static final ObjectRef<ActionDefinition> sat2ChgTAbsValRef =
			new ObjectRef<>(
					Constant.DOMAIN_SAT2,
					ActionDefinition.TYPE_ID.getTypeId(),
					Constant.ID_CHGTABSVAL,
					new UInteger(1));
	public static final ObjectRef<ActionDefinition> sat1DefAttitude1Ref =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					ActionDefinition.TYPE_ID.getTypeId(),
					Constant.ID_DEFATTITUDE,
					new UInteger(1));
	public static final ObjectRef<ActionDefinition> sat1DefAttitude2Ref =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					ActionDefinition.TYPE_ID.getTypeId(),
					Constant.ID_DEFATTITUDE,
					new UInteger(2));

	public ActionDefaultDataset() {
		// build ArgumentDefinition objects

		// ArgumentDefinition
		// - argId: "GENE_AR_TIMEABSVAL"
		// - description: "absolute time in seconds"
		// - type: TIME
		// - unit="s"
		ArgumentDefinition argDefTimeAbsVal =
				new ArgumentDefinition(
						Constant.ID_TIMEABSVAL,
						"absolute time in seconds",
						AttributeType.TIME,
						"s");

		// ArgumentDefinition
		// - argId: "GENE_AR_STARTTIME"
		// - description: "Parameter GENE_AR_Z0427"
		// - type: DOUBLE
		// - unit=null
		ArgumentDefinition argDefStartTime =
				new ArgumentDefinition(
						Constant.ID_STARTTIME,
						"Parameter GENE_AR_Z0427",
						AttributeType.DOUBLE,
						null);

		// ArgumentDefinition
		// - argId: "GENE_AR_DURATION"
		// - description: "Parameter GENE_AR_Z0481"
		// - type: DOUBLE
		// - unit=null
		ArgumentDefinition argDefDuration =
				new ArgumentDefinition(
						Constant.ID_DURATION,
						"Parameter GENE_AR_Z0481",
						AttributeType.DOUBLE,
						null);

		// ArgumentDefinition
		// - argId: "GENE_AR_MANEUVTYPE"
		// - description: "Parameter GENE_AR_Z0584"
		// - type: STRING
		// - unit=null
		ArgumentDefinition argDefManeuvType =
				new ArgumentDefinition(
						Constant.ID_MANEUVTYPE,
						"Parameter GENE_AR_Z0584",
						AttributeType.STRING,
						null);

		// ArgumentDefinition
		// - argId: "GENE_AR_POLYNOMDEG"
		// - description: ""
		// - type: ULONG
		// - unit=null
		ArgumentDefinition argDefTimePolyNomDeg =
				new ArgumentDefinition(
						Constant.ID_POLYNOMDEG,
						"",
						AttributeType.ULONG,
						null);

		// ArgumentDefinition
		// - argId: "GENE_AR_POLVALUE"
		// - description: ""
		// - type: DOUBLE
		// - unit=null
		ArgumentDefinition argDefPolValue =
				new ArgumentDefinition(
						Constant.ID_POLVALUE,
						"",
						AttributeType.DOUBLE,
						null);

		// build ActionDefinition objects

		// ActionDefinition
		// - identity: ("fr.cnes.mission.sat1", "SAT_TC_CHGTABSVAL", version=1)
		// - description: "TC 9.128 – CHANGE_ONBOARD_TIME_ABSOLUTE_VALUE"
		// - category: DEFAULT
		// - progressStepCount: 0
		// - arguments: { "GENE_AR_TIMEABSVAL" }
		addAction(
				new ObjectIdentity(
						sat1ChgTAbsValRef.getDomain(),
						sat1ChgTAbsValRef.getKey(),
						sat1ChgTAbsValRef.getObjectVersion()),
				new String("TC 9.128 – CHANGE_ONBOARD_TIME_ABSOLUTE_VALUE"),
				ActionCategory.DEFAULT,
				new UShort(0),
				new ArgumentDefinitionList(new ArrayList<> (Arrays.asList(argDefTimeAbsVal))));

		// ActionDefinition
		// - identity: ("fr.cnes.mission.sat2", "SAT_TC_CHGTABSVAL", version=1)
		// - description: "TC 9.128 – CHANGE_ONBOARD_TIME_ABSOLUTE_VALUE"
		// - category: DEFAULT
		// - progressStepCount: 0
		// - arguments: { "GENE_AR_TIMEABSVAL" }
		addAction(
				new ObjectIdentity(
						sat2ChgTAbsValRef.getDomain(),
						sat2ChgTAbsValRef.getKey(),
						sat2ChgTAbsValRef.getObjectVersion()),
				new String("TC 9.128 – CHANGE_ONBOARD_TIME_ABSOLUTE_VALUE"),
				ActionCategory.DEFAULT,
				new UShort(0),
				new ArgumentDefinitionList(new ArrayList<> (Arrays.asList(argDefTimeAbsVal))));

		// ActionDefinition
		// - identity: ("fr.cnes.mission.sat1", "MIS_TC_DEFATTITUDE", version=1)
		// - description: "Change the satellite attitude – Deprecated"
		// - category: DEFAULT
		// - progressStepCount: 3
		// - arguments: null
		addAction(
				new ObjectIdentity(
						sat1DefAttitude1Ref.getDomain(),
						sat1DefAttitude1Ref.getKey(),
						sat1DefAttitude1Ref.getObjectVersion()),
				new String("Change the satellite attitude – Deprecated"),
				ActionCategory.DEFAULT,
				new UShort(3),
				null);

		// ActionDefinition
		// - identity: ("fr.cnes.mission.sat1", "MIS_TC_DEFATTITUDE", version=2)
		// - description: "Change the satellite attitude"
		// - category: DEFAULT
		// - progressStepCount: 3
		// - arguments: { "GENE_AR_STARTTIME", "GENE_AR_DURATION", "GENE_AR_MANEUVTYPE", "GENE_AR_POLYNOMDEG", "GENE_AR_POLVALUE", "GENE_AR_POLVALUE", "GENE_AR_POLVALUE" }
		addAction(
				new ObjectIdentity(
						sat1DefAttitude2Ref.getDomain(),
						sat1DefAttitude2Ref.getKey(),
						sat1DefAttitude2Ref.getObjectVersion()),
				new String("Change the satellite attitude"),
				ActionCategory.DEFAULT,
				new UShort(3),
				new ArgumentDefinitionList(new ArrayList<>(Arrays.asList(
						argDefStartTime, argDefDuration, argDefManeuvType, argDefTimePolyNomDeg,
						argDefPolValue, argDefPolValue, argDefPolValue))));
	}

	private boolean skipNextCall = false;
	private final HashMap<Long, CountDownLatch> requestLatchMap = new HashMap();

	@Override
	public String check(ActionExecutionRequest executionRequest, ActionDefinition definition) {
		if (skipNextCall) {
			skipNextCall = false;
			return Constant.STR_SKIPPED;
		}

		if (!Constant.ID_DEFATTITUDE.equals(definition.getObjectIdentity().getKey()) ||
				definition.getObjectIdentity().getVersion().getValue() != 2)
			return null;

		// GENE_AR_MANEUVTYPE is the third argument, index 2
		NullableAttribute argManeuvTypeNA = executionRequest.getArgumentValues().get(2);
		if (argManeuvTypeNA == null)
			return "Invalid value for argument " + Constant.ID_MANEUVTYPE + ": null";
		String maneuvType = argManeuvTypeNA.getValue().attribute2string();
		if (Constant.STR_OK.equals(maneuvType) ||
				Constant.STR_STEPS.equals(maneuvType) ||
				Constant.STR_SKIP.equals(maneuvType) ||
				Constant.STR_WAIT.equals(maneuvType) ||
				Constant.STR_FAIL2.equals(maneuvType)) {
			return null;
		} else {
			return "Invalid value for argument " + Constant.ID_MANEUVTYPE + ": " + maneuvType;
		}
	}

	@Override
	public boolean execute(ActionExecutionRequest executionRequest, ActionDefinition definition, ExecuteListener listener) {
		// The Action MIS_TC_DEFATTITUDE  shall be implemented by the test provider with specific test behaviors related to the value of its arguments:
		//   - GENE_AR_MANEUVTYPE="ok" → complete successfully
		//   - GENE_AR_MANEUVTYPE="steps"→ complete GENE_AR_POLYNOMDEG steps, then fails
		//   - GENE_AR_MANEUVTYPE="skip"→ fails next call with error code Rejected and extraInfo "skipped"
		//   - GENE_AR_MANEUVTYPE="wait"→ wait for a MCPrototype:ActionTest:resumeAction call after sending the first ActionInProgressEvent
		//   - GENE_AR_MANEUVTYPE="fail-2"→ fails after sending a first successful ActionInProgressEvent
		//   - GENE_AR_MANEUVTYPE=any else → error Rejected
		if (!Constant.ID_DEFATTITUDE.equals(definition.getObjectIdentity().getKey()) ||
				definition.getObjectIdentity().getVersion().getValue() != 2)
			return super.execute(executionRequest, definition, listener);

		// GENE_AR_MANEUVTYPE is the third argument, index 2
		String maneuvType = executionRequest.getArgumentValues().get(2).getValue().attribute2string();
		if (Constant.STR_OK.equals(maneuvType)) {
			return super.execute(executionRequest, definition, listener);
		} else if (Constant.STR_STEPS.equals(maneuvType)) {
			// GENE_AR_POLYNOMDEG is the 4th argument, index 3
			int progressStepCount = ((Union) executionRequest.getArgumentValues().get(3).getValue()).getLongValue().intValue();
			for (int i=0; i<progressStepCount; i++)
				listener.progressReport();
			return false;
		} else if (Constant.STR_SKIP.equals(maneuvType)) {
			skipNextCall = true;
			return super.execute(executionRequest, definition, listener);
		} else if (Constant.STR_WAIT.equals(maneuvType)) {
			listener.progressReport();
			CountDownLatch latch = new CountDownLatch(1);
			requestLatchMap.put(executionRequest.getRequestId(), latch);
			try {
				latch.await();
			} catch (InterruptedException exc) {
				return false;
			}
			for (int i=1; i<definition.getProgressStepCount().getValue(); i++)
				listener.progressReport();
			return true;
		} else if (Constant.STR_FAIL2.equals(maneuvType)) {
			listener.progressReport();
			return false;
		} else {
			// should have been rejected by the call to check
			return false;
		}
	}

	public void resumeAction(long requestId) {
		CountDownLatch latch = requestLatchMap.get(new Long(requestId));
		if (latch == null)
			return;
		latch.countDown();
	}
}
