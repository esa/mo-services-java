/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Test bed utilities
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
package org.ccsds.moims.mo.testbed.util;

import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;

public class ParseHelper {

    public static final String BEST_EFFORT_QOS = "Best Effort";
    public static final String ASSURED_QOS = "Assured";
    public static final String QUEUED_QOS = "Queued";
    public static final String TIMELY_QOS = "Timely";

    public static final String LIVE_SESSION = "Live";
    public static final String SIMULATION_SESSION = "Simulation";
    public static final String REPLAY_SESSION = "Replay";

    public static final String SEND_IP = "Send";
    public static final String SUBMIT_IP = "Submit";
    public static final String REQUEST_IP = "Request";
    public static final String INVOKE_IP = "Invoke";
    public static final String PROGRESS_IP = "Progress";
    public static final String PUBSUB_IP = "Pub/Sub";

    public static final String DELIVERY_FAILED_ERROR = "DELIVERY_FAILED";
    public static final String DELIVERY_TIMEDOUT_ERROR = "DELIVERY_TIMEDOUT";
    public static final String DELIVERY_DELAYED_ERROR = "DELIVERY_DELAYED";
    public static final String DESTINATION_UNKNOWN_ERROR = "DESTINATION_UNKNOWN";
    public static final String DESTINATION_TRANSIENT_ERROR = "DESTINATION_TRANSIENT";
    public static final String DESTINATION_LOST_ERROR = "DESTINATION_LOST";
    public static final String ENCRYPTION_FAIL_ERROR = "ENCRYPTION_FAIL";
    public static final String UNSUPPORTED_AREA_ERROR = "UNSUPPORTED_AREA";
    public static final String UNSUPPORTED_OPERATION_ERROR = "UNSUPPORTED_OPERATION";
    public static final String UNSUPPORTED_VERSION_ERROR = "UNSUPPORTED_VERSION";
    public static final String AUTHENTICATION_FAIL_ERROR = "AUTHENTICATION_FAIL";
    public static final String AUTHORISATION_FAIL_ERROR = "AUTHORISATION_FAIL";
    public static final String BAD_ENCODING_ERROR = "BAD_ENCODING";
    public static final String UNKNOWN_ERROR = "UNKNOWN";

    public static QoSLevel parseQoSLevel(String qosLevel) throws Exception {
        if (BEST_EFFORT_QOS.equals(qosLevel)) {
            return QoSLevel.BESTEFFORT;
        } else if (ASSURED_QOS.equals(qosLevel)) {
            return QoSLevel.ASSURED;
        } else if (QUEUED_QOS.equals(qosLevel)) {
            return QoSLevel.QUEUED;
        } else if (TIMELY_QOS.equals(qosLevel)) {
            return QoSLevel.TIMELY;
        } else {
            throw new Exception("Unknown qos level:" + qosLevel);
        }
    }

    public static SessionType parseSessionType(String sessionType)
            throws Exception {
        if (LIVE_SESSION.equals(sessionType)) {
            return SessionType.LIVE;
        } else if (SIMULATION_SESSION.equals(sessionType)) {
            return SessionType.SIMULATION;
        } else if (REPLAY_SESSION.equals(sessionType)) {
            return SessionType.REPLAY;
        } else {
            throw new Exception("Unknown session type:" + sessionType);
        }
    }

    public static InteractionType parseInteractionType(String ip) throws Exception {
        if (SEND_IP.equals(ip)) {
            return InteractionType.SEND;
        } else if (SUBMIT_IP.equals(ip)) {
            return InteractionType.SUBMIT;
        } else if (REQUEST_IP.equals(ip)) {
            return InteractionType.REQUEST;
        } else if (INVOKE_IP.equals(ip)) {
            return InteractionType.INVOKE;
        } else if (PROGRESS_IP.equals(ip)) {
            return InteractionType.PROGRESS;
        } else if (PUBSUB_IP.equals(ip)) {
            return InteractionType.PUBSUB;
        } else {
            throw new Exception("Unknown IP type:" + ip);
        }
    }

    public static UInteger parseErrorCode(String error) throws Exception {
        if (DELIVERY_FAILED_ERROR.equals(error)) {
            return MALHelper.DELIVERY_FAILED_ERROR_NUMBER;
        } else if (DELIVERY_TIMEDOUT_ERROR.equals(error)) {
            return MALHelper.DELIVERY_TIMEDOUT_ERROR_NUMBER;
        } else if (DELIVERY_DELAYED_ERROR.equals(error)) {
            return MALHelper.DELIVERY_DELAYED_ERROR_NUMBER;
        } else if (DESTINATION_UNKNOWN_ERROR.equals(error)) {
            return MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER;
        } else if (DESTINATION_TRANSIENT_ERROR.equals(error)) {
            return MALHelper.DESTINATION_TRANSIENT_ERROR_NUMBER;
        } else if (DESTINATION_LOST_ERROR.equals(error)) {
            return MALHelper.DESTINATION_LOST_ERROR_NUMBER;
        } else if (ENCRYPTION_FAIL_ERROR.equals(error)) {
            return MALHelper.ENCRYPTION_FAIL_ERROR_NUMBER;
        } else if (UNSUPPORTED_AREA_ERROR.equals(error)) {
            return MALHelper.UNSUPPORTED_AREA_ERROR_NUMBER;
        } else if (UNSUPPORTED_OPERATION_ERROR.equals(error)) {
            return MALHelper.UNSUPPORTED_OPERATION_ERROR_NUMBER;
        } else if (UNSUPPORTED_VERSION_ERROR.equals(error)) {
            return MALHelper.UNSUPPORTED_AREA_VERSION_ERROR_NUMBER;
        } else if (BAD_ENCODING_ERROR.equals(error)) {
            return MALHelper.BAD_ENCODING_ERROR_NUMBER;
        } else if (UNKNOWN_ERROR.equals(error)) {
            return MALHelper.UNKNOWN_ERROR_NUMBER;
        } else if (AUTHENTICATION_FAIL_ERROR.equals(error)) {
            return MALHelper.AUTHENTICATION_FAILED_ERROR_NUMBER;
        } else if (AUTHORISATION_FAIL_ERROR.equals(error)) {
            return MALHelper.AUTHORISATION_FAIL_ERROR_NUMBER;
        } else {
            throw new Exception("Unknown Error code:" + error);
        }
    }

    public static String parseErrorCode(UInteger error) throws Exception {
        if (MALHelper.DELIVERY_FAILED_ERROR_NUMBER.equals(error)) {
            return DELIVERY_FAILED_ERROR;
        } else if (MALHelper.DELIVERY_TIMEDOUT_ERROR_NUMBER.equals(error)) {
            return DELIVERY_TIMEDOUT_ERROR;
        } else if (MALHelper.DELIVERY_DELAYED_ERROR_NUMBER.equals(error)) {
            return DELIVERY_DELAYED_ERROR;
        } else if (MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER.equals(error)) {
            return DESTINATION_UNKNOWN_ERROR;
        } else if (MALHelper.DESTINATION_TRANSIENT_ERROR_NUMBER.equals(error)) {
            return DESTINATION_TRANSIENT_ERROR;
        } else if (MALHelper.DESTINATION_LOST_ERROR_NUMBER.equals(error)) {
            return DESTINATION_LOST_ERROR;
        } else if (MALHelper.ENCRYPTION_FAIL_ERROR_NUMBER.equals(error)) {
            return ENCRYPTION_FAIL_ERROR;
        } else if (MALHelper.UNSUPPORTED_AREA_ERROR_NUMBER.equals(error)) {
            return UNSUPPORTED_AREA_ERROR;
        } else if (MALHelper.UNSUPPORTED_OPERATION_ERROR_NUMBER.equals(error)) {
            return UNSUPPORTED_OPERATION_ERROR;
        } else if (MALHelper.UNSUPPORTED_AREA_VERSION_ERROR_NUMBER.equals(error)) {
            return UNSUPPORTED_VERSION_ERROR;
        } else if (MALHelper.BAD_ENCODING_ERROR_NUMBER.equals(error)) {
            return BAD_ENCODING_ERROR;
        } else if (MALHelper.UNKNOWN_ERROR_NUMBER.equals(error)) {
            return UNKNOWN_ERROR;
        } else if (MALHelper.AUTHENTICATION_FAILED_ERROR_NUMBER.equals(error)) {
            return AUTHENTICATION_FAIL_ERROR;
        } else if (MALHelper.AUTHORISATION_FAIL_ERROR_NUMBER.equals(error)) {
            return AUTHORISATION_FAIL_ERROR;
        } else {
            throw new Exception("Unknown Error code:" + error);
        }
    }
}
