/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Test bed
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
package org.ccsds.moims.mo.com.test.util;

import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.activitytracking.ActivityTrackingServiceInfo;
import org.ccsds.moims.mo.comprototype.COMPrototypeHelper;
import org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo;

/**
 *
 * Holds set of constants used throughout COM test
 */
public class COMTestHelper {
    // Activity Service details taken from COM Red Book

    public final static int OBJ_NO_ASE_RELEASE = 1;
    public final static int OBJ_NO_ASE_RECEPTION = 2;
    public final static int OBJ_NO_ASE_FORWARD = 3;
    public final static int OBJ_NO_ASE_ACCEPTANCE = 4;
    public final static int OBJ_NO_ASE_EXECUTION = 5;
    public final static int OBJ_NO_ASE_OPERATION_ACTIVITY = 6;
    // Also for convenience provide string equivalents
    public final static String OBJ_NO_ASE_RELEASE_STR = Integer.toString(OBJ_NO_ASE_RELEASE);
    public final static String OBJ_NO_ASE_RECEPTION_STR = Integer.toString(OBJ_NO_ASE_RECEPTION);
    public final static String OBJ_NO_ASE_FORWARD_STR = Integer.toString(OBJ_NO_ASE_FORWARD);
    public final static String OBJ_NO_ASE_ACCEPTANCE_STR = Integer.toString(OBJ_NO_ASE_ACCEPTANCE);
    public final static String OBJ_NO_ASE_EXECUTION_STR = Integer.toString(OBJ_NO_ASE_EXECUTION);
    public final static String OBJ_NO_ASE_OPERATION_ACTIVITY_STR = Integer.toString(OBJ_NO_ASE_OPERATION_ACTIVITY);
    // Strings that identify consumer & provider - must be used in associated URI
    public final static String CONSUMER_STR = "CONSUMER";
    public final static String PROVIDER_STR = "PROVIDER";
    // Test event service details taken from XML
    // Event numbers allocated to events
    public final static String TEST_OBJECT_CREATION_NO = "3001";
    public final static String TEST_OBJECT_DELETION_NO = "3002";
    public final static String TEST_OBJECT_UPDATE_NO = "3003";
    // EventTest object numbers
    public final static short TEST_OBJECT_A = 2001;
    public final static short TEST_OBJECT_B = 2002;
    public final static String TEST_OBJECT_A_STR = Integer.toString(TEST_OBJECT_A);
    public final static String TEST_OBJECT_B_STR = Integer.toString(TEST_OBJECT_B);

    public static ObjectType getOperationActivityType() {
        ObjectType type = new ObjectType();
        type.setArea(COMHelper.COM_AREA_NUMBER);
        type.setService(ActivityTrackingServiceInfo.ACTIVITYTRACKING_SERVICE_NUMBER);
        type.setVersion(COMHelper.COM_AREA_VERSION);
        type.setNumber(new UShort(OBJ_NO_ASE_OPERATION_ACTIVITY));
        return type;
    }

    // Generates the long format for object type
    public static long getActivityObjectTypeAsKey(int objectNumber) {
        long iKey;

        iKey = (long) objectNumber;
        iKey = iKey | (long) COMHelper._COM_AREA_NUMBER << 48;
        iKey = iKey | (long) ActivityTrackingServiceInfo._ACTIVITYTRACKING_SERVICE_NUMBER << 32;
        iKey = iKey | (long) COMHelper._COM_AREA_VERSION << 24;
        return iKey;
    }

    public static long getEventTestObjectTypeAsKey(int objectNumber) {
        long iKey;

        iKey = (long) objectNumber;
        iKey = iKey | (long) COMPrototypeHelper._COMPROTOTYPE_AREA_NUMBER << 48;
        iKey = iKey | (long) EventTestServiceInfo._EVENTTEST_SERVICE_NUMBER << 32;
        iKey = iKey | (long) COMPrototypeHelper._COMPROTOTYPE_AREA_VERSION << 24;
        return iKey;
    }
}
