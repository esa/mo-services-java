package esa.mo.mal.transport.http.util;

import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;

public class StatusCodeHelper {

  public static UInteger getMALErrorFromStatusCode(int statusCode) {
    switch (statusCode) {
      case 400:
        return MALHelper.BAD_ENCODING_ERROR_NUMBER;
      case 401:
      case 403:
        return MALHelper.AUTHORISATION_FAIL_ERROR_NUMBER;
      case 404:
        return MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER;
      case 405:
      case 501:
        return MALHelper.UNSUPPORTED_OPERATION_ERROR_NUMBER;
      case 408:
      case 504:
        return MALHelper.DELIVERY_TIMEDOUT_ERROR_NUMBER;
      case 410:
      case 503:
        return MALHelper.DESTINATION_TRANSIENT_ERROR_NUMBER;
      case 429:
        return MALHelper.TOO_MANY_ERROR_NUMBER;
      case 502:
        return MALHelper.DELIVERY_FAILED_ERROR_NUMBER;
      case 511:
        return MALHelper.AUTHENTICATION_FAILED_ERROR_NUMBER;
      case 500:
      default:
        return new UInteger(statusCode); // Convert to error
    }
  }

  public static int getStatusCodeFromMALError(UInteger errorNumber) {
    if (errorNumber.equals(MALHelper.INTERNAL_ERROR_NUMBER)) {
      return 500;
    }
    if (errorNumber.equals(MALHelper.BAD_ENCODING_ERROR_NUMBER)) {
      return 400;
    }
    if (errorNumber.equals(MALHelper.AUTHORISATION_FAIL_ERROR_NUMBER)) {
      return 401;
    }
    if (errorNumber.equals(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER)) {
      return 404;
    }
    if (errorNumber.equals(MALHelper.UNSUPPORTED_OPERATION_ERROR_NUMBER)) {
      return 501;
    }
    if (errorNumber.equals(MALHelper.DELIVERY_TIMEDOUT_ERROR_NUMBER)) {
      return 504;
    }
    if (errorNumber.equals(MALHelper.DESTINATION_TRANSIENT_ERROR_NUMBER)) {
      return 503;
    }
    if (errorNumber.equals(MALHelper.TOO_MANY_ERROR_NUMBER)) {
      return 429;
    }
    if (errorNumber.equals(MALHelper.DELIVERY_FAILED_ERROR_NUMBER)) {
      return 502;
    }
    if (errorNumber.equals(MALHelper.AUTHENTICATION_FAILED_ERROR_NUMBER)) {
      return 511;
    }
    return (int) errorNumber.getValue();
  }

  public static int getHttpResponseCode(InteractionType type, UOctet stage) {
    if (type.equals(InteractionType.SEND)) {
      return 204;
    } else if (type.equals(InteractionType.INVOKE)) {
      if (stage.equals(MALInvokeOperation.INVOKE_ACK_STAGE)) {
        return 202;
      }
    } else if (type.equals(InteractionType.PROGRESS)) {
      if (stage.equals(MALProgressOperation.PROGRESS_UPDATE_STAGE)
          || stage.equals(MALProgressOperation.PROGRESS_RESPONSE_STAGE)) {
        return 204;
      }
    } else if (type.equals(InteractionType.PUBSUB)) {
      if (stage.equals(MALPubSubOperation.NOTIFY_STAGE) || stage.equals(MALPubSubOperation.PUBLISH_STAGE)) {
        return 204;
      }
    }

    return 200;
  }

}
