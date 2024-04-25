package esa.mo.mal.transport.http.test;

import static org.junit.Assert.*;

import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.junit.Test;

import esa.mo.mal.transport.http.util.StatusCodeHelper;

public class StatusCodeHelperTest {

  @Test
  public void testGetHttpResponseCode() {

    assertEquals(204, StatusCodeHelper.getHttpResponseCode(InteractionType.SEND, null));
    assertEquals(200, StatusCodeHelper.getHttpResponseCode(InteractionType.SUBMIT, MALSubmitOperation.SUBMIT_ACK_STAGE));
    assertEquals(200, StatusCodeHelper.getHttpResponseCode(InteractionType.REQUEST, MALRequestOperation.REQUEST_RESPONSE_STAGE));
    assertEquals(200, StatusCodeHelper.getHttpResponseCode(InteractionType.INVOKE, MALInvokeOperation.INVOKE_RESPONSE_STAGE));
    assertEquals(202, StatusCodeHelper.getHttpResponseCode(InteractionType.INVOKE, MALInvokeOperation.INVOKE_ACK_STAGE));
    assertEquals(200, StatusCodeHelper.getHttpResponseCode(InteractionType.PROGRESS, MALProgressOperation.PROGRESS_ACK_STAGE));
    assertEquals(204, StatusCodeHelper.getHttpResponseCode(InteractionType.PROGRESS, MALProgressOperation.PROGRESS_UPDATE_STAGE));
    assertEquals(204, StatusCodeHelper.getHttpResponseCode(InteractionType.PROGRESS, MALProgressOperation.PROGRESS_RESPONSE_STAGE));
    assertEquals(200, StatusCodeHelper.getHttpResponseCode(InteractionType.PUBSUB, MALPubSubOperation.REGISTER_ACK_STAGE));
    assertEquals(200, StatusCodeHelper.getHttpResponseCode(InteractionType.PUBSUB, MALPubSubOperation.DEREGISTER_ACK_STAGE));
    assertEquals(200, StatusCodeHelper.getHttpResponseCode(InteractionType.PUBSUB, MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE));
    assertEquals(200, StatusCodeHelper.getHttpResponseCode(InteractionType.PUBSUB, MALPubSubOperation.PUBLISH_DEREGISTER_ACK_STAGE));
    assertEquals(204, StatusCodeHelper.getHttpResponseCode(InteractionType.PUBSUB, MALPubSubOperation.NOTIFY_STAGE));
    assertEquals(204, StatusCodeHelper.getHttpResponseCode(InteractionType.PUBSUB, MALPubSubOperation.PUBLISH_STAGE));

    
  }

}
