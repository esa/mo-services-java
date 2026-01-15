/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
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

import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * Defines constants used in both consumers and providers of the M&C testbed.
 * 
 * @author serge.lacourte
 *
 */
public class Constant {

	// used in all test services

	// domain values
	public static final Identifier ID_FR = new Identifier("fr");
	public static final Identifier ID_CNES = new Identifier("cnes");
	public static final Identifier ID_MISSION = new Identifier("mission");
	public static final Identifier ID_SAT1 = new Identifier("sat1");
	public static final IdentifierList DOMAIN_SAT1 =
			new IdentifierList(new ArrayList<> (Arrays.asList(ID_FR, ID_CNES, ID_MISSION, ID_SAT1)));
	public static final Identifier ID_SAT2 = new Identifier("sat2");
	public static final IdentifierList DOMAIN_SAT2 =
			new IdentifierList(new ArrayList<> (Arrays.asList(ID_FR, ID_CNES, ID_MISSION, ID_SAT2)));
	public static final Identifier ID_WILDCARD = new Identifier("*");
	public static final IdentifierList DOMAIN_WILDCARD =
			new IdentifierList(new ArrayList<> (Arrays.asList(ID_FR, ID_CNES, ID_MISSION, ID_WILDCARD)));
	public static final Identifier ID_UNKNOWN = new Identifier("unknown");
	public static final IdentifierList DOMAIN_UNKNOWN =
			new IdentifierList(new ArrayList<> (Arrays.asList(ID_FR, ID_CNES, ID_MISSION, ID_UNKNOWN)));


	// used in Action test service

	// ActionDefinition keys
	public static final Identifier ID_CHGTABSVAL = new Identifier("SAT_TC_CHGTABSVAL");
	public static final Identifier ID_DEFATTITUDE = new Identifier("MIS_TC_DEFATTITUDE");

	// ArgumentDefinition keys
	public static final Identifier ID_TIMEABSVAL = new Identifier("GENE_AR_TIMEABSVAL");
	public static final Identifier ID_STARTTIME = new Identifier("GENE_AR_STARTTIME");
	public static final Identifier ID_DURATION = new Identifier("GENE_AR_DURATION");
	public static final Identifier ID_MANEUVTYPE = new Identifier("GENE_AR_MANEUVTYPE");
	public static final Identifier ID_POLYNOMDEG = new Identifier("GENE_AR_POLYNOMDEG");
	public static final Identifier ID_POLVALUE = new Identifier("GENE_AR_POLVALUE");

	// specific values of the GENE_AR_MANEUVTYPE argument
	public static final String STR_OK = "ok";
	public static final String STR_STEPS = "steps";
	public static final String STR_SKIP = "skip";
	public static final String STR_WAIT = "wait";
	public static final String STR_FAIL2 = "fail-2";

	// specific value of the extraInfo error field
	public static final String STR_SKIPPED = "skipped";

	// used in Parameter test service

	// ParameterDefinition keys
	public static final Identifier ID_MTQ1VOLTAGE = new Identifier("ATT_BC_MTQ1VOLTAGE");
	public static final Identifier ID_MTQ1ENABLED = new Identifier("ATT_BC_MTQ1ENABLED");

	// converted values
	public static final String STR_DISABLED = "DISABLED";
	public static final String STR_ENABLED = "ENABLED";
	public static final String STR_UNKNOWN = "UNKNOWN";
	public static final Attribute AT_STRING_DISABLED = new Union(Constant.STR_DISABLED);
	public static final Attribute AT_STRING_ENABLED = new Union(Constant.STR_ENABLED);
	public static final Attribute AT_STRING_UNKNOWN = new Union(Constant.STR_UNKNOWN);
	
	// used in Alert test service

	// AlertDefinition keys
	public static final Identifier ID_MTQ1VOLTAGE_HIGH = new Identifier("MTQ1VOLTAGE_HIGH");
	public static final Identifier ID_MTQ1VOLTAGE_LOW = new Identifier("MTQ1VOLTAGE_LOW");

	// ArgumentDefinition keys
	public static final Identifier ID_MTQ1VOLTAGE_HIGH_VAL = new Identifier("MTQ1VOLTAGE_HIGH_VAL");
	
	// used in Aggregation test service

	// AggregationDefinition keys
	public static final Identifier ID_BC_MTQ1 = new Identifier("AGG_BC_MTQ1");
	public static final Identifier ID_BC_MTQ1_REV = new Identifier("AGG_BC_MTQ1_REV");

	// used in Packet service
	
	public static final Identifier ID_APID = new Identifier("apid");
	public static final Identifier ID_DESTID = new Identifier("destID");
	
}
