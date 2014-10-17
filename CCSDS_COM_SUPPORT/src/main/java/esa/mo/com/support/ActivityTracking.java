package esa.mo.com.support;

import java.util.Map;
import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.activitytracking.ActivityTrackingHelper;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityAcceptance;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityAcceptanceList;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityExecution;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityExecutionList;
import org.ccsds.moims.mo.com.event.provider.EventInheritanceSkeleton;
import org.ccsds.moims.mo.com.event.provider.MonitorEventPublisher;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.com.structures.ObjectDetailsList;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.structures.UpdateType;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 *
 */
public class ActivityTracking extends EventInheritanceSkeleton
{
  public final static int OBJ_NO_ASE_ACCEPTANCE = 4;
  public final static int OBJ_NO_ASE_EXECUTION = 5;
  public final static int OBJ_NO_ASE_OPERATION_ACTIVITY = 6;
  public final static String OBJ_NO_ASE_ACCEPTANCE_STR = Integer.toString(OBJ_NO_ASE_ACCEPTANCE);
  public final static String OBJ_NO_ASE_EXECUTION_STR = Integer.toString(OBJ_NO_ASE_EXECUTION);
  private final ObjectType OPERATION_ACTIVITY_OBJECT_TYPE = new ObjectType();
  private MonitorEventPublisher monitorEventPublisher = null;
  private int instanceIdentifier = 0;

  public ActivityTracking()
  {
    OPERATION_ACTIVITY_OBJECT_TYPE.setArea(COMHelper.COM_AREA_NUMBER);
    OPERATION_ACTIVITY_OBJECT_TYPE.setService(ActivityTrackingHelper.ACTIVITYTRACKING_SERVICE_NUMBER);
    OPERATION_ACTIVITY_OBJECT_TYPE.setVersion(COMHelper.COM_AREA_VERSION);
    OPERATION_ACTIVITY_OBJECT_TYPE.setNumber(new UShort(OBJ_NO_ASE_OPERATION_ACTIVITY));
  }

  public void init() throws MALInteractionException, MALException
  {
    System.out.println("ActivityTracking:init");

    if (null == monitorEventPublisher)
    {
      System.out.println("ActivityTracking:creating event publisher");

      final IdentifierList domain = new IdentifierList();

      monitorEventPublisher = createMonitorEventPublisher(domain,
              new Identifier("SPACE"),
              SessionType.LIVE,
              new Identifier("LIVE"),
              QoSLevel.BESTEFFORT,
              null,
              new UInteger(0));
      final EntityKeyList lst = new EntityKeyList();
      lst.add(new EntityKey(new Identifier("*"), (long) 0, (long) 0, (long) 0));

      monitorEventPublisher.register(lst, new ActivityTrackingPublisher());
    }
  }

  public void publishAcceptanceEventOperation(MALInteraction interaction, boolean success) throws MALInteractionException, MALException
  {
    publishAcceptanceEvent(interaction, success, null);
  }

  public void publishAcceptanceEvent(MALInteraction interaction, boolean success, ObjectId source) throws MALInteractionException, MALException
  {
    if (source == null)
    {
      System.out.println("ActivityTracking:publishAcceptance malInter = " + interaction);
    }
    else
    {
      System.out.println("ActivityTracking:publishAcceptance malInter = " + interaction + " source " + source);
    }

    // Produce ActivityTransferList
    ActivityAcceptanceList aal = new ActivityAcceptanceList();
    ActivityAcceptance aa = new ActivityAcceptance();
    aa.setSuccess(success);
    aal.add(aa);

    // Produce ObjectDetails
    ObjectDetailsList odl = new ObjectDetailsList();
    ObjectDetails objDetails = new ObjectDetails();
    objDetails.setRelated(null);

    // Set source
    if (source == null)
    {
      source = new ObjectId();
      source.setType(OPERATION_ACTIVITY_OBJECT_TYPE);
      System.out.println("ActivityTracking:publishAcceptance source = " + source);

      ObjectKey key = new ObjectKey();
      key.setDomain(interaction.getMessageHeader().getDomain());
      key.setInstId(interaction.getMessageHeader().getTransactionId());
      if (interaction.getMessageHeader().getTransactionId() == null)
      {
        System.out.println("ActivityTracking:getTransactionId = NULL");
      }
      System.out.println("ActivityTracking:key = " + key);
      source.setKey(key);
    }

    objDetails.setSource(source);
    odl.add(objDetails);

    // Produce header
    UpdateHeaderList uhl = new UpdateHeaderList();
    final EntityKey ekey = new EntityKey(
            new Identifier(OBJ_NO_ASE_ACCEPTANCE_STR),
            generateSubKey(COMHelper._COM_AREA_NUMBER, ActivityTrackingHelper._ACTIVITYTRACKING_SERVICE_NUMBER, COMHelper._COM_AREA_VERSION, 0),
            new Long(instanceIdentifier++),
            generateSubKey(COMHelper._COM_AREA_NUMBER, ActivityTrackingHelper._ACTIVITYTRACKING_SERVICE_NUMBER, COMHelper._COM_AREA_VERSION, OBJ_NO_ASE_OPERATION_ACTIVITY));
    System.out.println("ActivityTracking:eKey = " + ekey);
    final Time timestamp = new Time(System.currentTimeMillis());
    UpdateHeader uh = new UpdateHeader(timestamp, interaction.getMessageHeader().getURITo(), UpdateType.DELETION, ekey);
    uhl.add(uh);

    // We can now publish the event
    monitorEventPublisher.publish(uhl, odl, aal);
  }

  public void publishExecutionEventSubmitAck(MALInteraction interaction, boolean success) throws MALInteractionException, MALException
  {
    publishExecutionEventOperation(interaction, success, 1, 1);
  }

  public void publishExecutionEventRequestResponse(MALInteraction interaction, boolean success) throws MALInteractionException, MALException
  {
    publishExecutionEventOperation(interaction, success, 1, 1);
  }

  public void publishExecutionEventInvokeAck(MALInteraction interaction, boolean success) throws MALInteractionException, MALException
  {
    publishExecutionEventOperation(interaction, success, 1, 2);
  }

  public void publishExecutionEventInvokeResponse(MALInteraction interaction, boolean success) throws MALInteractionException, MALException
  {
    publishExecutionEventOperation(interaction, success, 2, 2);
  }

  public void publishExecutionEventOperation(MALInteraction interaction, boolean success,
          int currentStageCount, int totalStageCount) throws MALInteractionException, MALException
  {
    System.out.println("ActivityTracking:publishexecution malInter = " + interaction);
    // Produce header
    UpdateHeaderList uhl = new UpdateHeaderList();
    final EntityKey ekey = new EntityKey(
            new Identifier(OBJ_NO_ASE_EXECUTION_STR),
            generateSubKey(COMHelper._COM_AREA_NUMBER, ActivityTrackingHelper._ACTIVITYTRACKING_SERVICE_NUMBER, COMHelper._COM_AREA_VERSION, 0),
            new Long(instanceIdentifier++),
            generateSubKey(COMHelper._COM_AREA_NUMBER, ActivityTrackingHelper._ACTIVITYTRACKING_SERVICE_NUMBER, COMHelper._COM_AREA_VERSION, OBJ_NO_ASE_OPERATION_ACTIVITY));

    System.out.println("ActivityTracking:publishexecution ekey = " + ekey);
    final Time timestamp = new Time(System.currentTimeMillis());
    uhl.add(new UpdateHeader(timestamp, interaction.getMessageHeader().getURITo(), UpdateType.DELETION, ekey));

    // Produce ActivityTransferList
    ActivityExecutionList ael = new ActivityExecutionList();
    ActivityExecution activityExecutionInstance = new ActivityExecution();
    activityExecutionInstance.setExecutionStage(new UInteger(currentStageCount)); // TBD
    activityExecutionInstance.setStageCount(new UInteger(totalStageCount));
    activityExecutionInstance.setSuccess(success);

    ael.add(activityExecutionInstance);

    // Produce ObjectDetails
    ObjectDetailsList odl = new ObjectDetailsList();
    ObjectDetails objDetails = new ObjectDetails();
    objDetails.setRelated(null);

    ObjectKey key = new ObjectKey();
    key.setDomain(interaction.getMessageHeader().getDomain());
    key.setInstId(interaction.getMessageHeader().getTransactionId());
    if (interaction.getMessageHeader().getTransactionId() == null)
    {
      System.out.println("ActivityTracking:getTransactionId = NULL");
    }

    ObjectId source = new ObjectId(OPERATION_ACTIVITY_OBJECT_TYPE, key);
    objDetails.setSource(source);
    odl.add(objDetails);

    // We can now publish the event
    monitorEventPublisher.publish(uhl, odl, ael);
  }

  public void publishExecutionEvent(URI uriTo, boolean success,
          int currentStageCount, int totalStageCount, ObjectId source) throws MALInteractionException, MALException
  {
    System.out.println("ActivityTracking:publishexecution to (" + uriTo + "), source (" + source + ")");

    // Produce header
    UpdateHeaderList uhl = new UpdateHeaderList();
    final EntityKey ekey = new EntityKey(
            new Identifier(OBJ_NO_ASE_EXECUTION_STR),
            generateSubKey(COMHelper._COM_AREA_NUMBER, ActivityTrackingHelper._ACTIVITYTRACKING_SERVICE_NUMBER, COMHelper._COM_AREA_VERSION, 0),
            new Long(instanceIdentifier++),
            generateSubKey(COMHelper._COM_AREA_NUMBER, ActivityTrackingHelper._ACTIVITYTRACKING_SERVICE_NUMBER, COMHelper._COM_AREA_VERSION, OBJ_NO_ASE_OPERATION_ACTIVITY));

    System.out.println("ActivityTracking:publishexecution ekey = " + ekey);
    final Time timestamp = new Time(System.currentTimeMillis());
    uhl.add(new UpdateHeader(timestamp, uriTo, UpdateType.DELETION, ekey));

    // Produce ActivityTransferList
    ActivityExecutionList ael = new ActivityExecutionList();
    ActivityExecution activityExecutionInstance = new ActivityExecution();
    activityExecutionInstance.setExecutionStage(new UInteger(currentStageCount)); // TBD
    activityExecutionInstance.setStageCount(new UInteger(totalStageCount));
    activityExecutionInstance.setSuccess(success);

    ael.add(activityExecutionInstance);

    // Produce ObjectDetails
    ObjectDetailsList odl = new ObjectDetailsList();
    ObjectDetails objDetails = new ObjectDetails();
    objDetails.setRelated(null);

    objDetails.setSource(source);
    odl.add(objDetails);

    // We can now publish the event
    monitorEventPublisher.publish(uhl, odl, ael);
  }

  /**
   * Generate a EntityKey sub key using fields as specified in COM STD 3.2.4.2b
   *
   * @param area
   * @param service
   * @param version
   * @param objectNumber
   * @return
   */
  static public Long generateSubKey(int area, int service, int version, int objectNumber)
  {
    long subkey = objectNumber;
    subkey = subkey | (((long) version) << 24);
    subkey = subkey | ((long) service << 32);
    subkey = subkey | ((long) area << 48);

    return subkey;
  }

  public class ActivityTrackingPublisher implements MALPublishInteractionListener
  {
    public void publishRegisterAckReceived(MALMessageHeader header, Map qosProperties) throws MALException
    {
    }

    public void publishRegisterErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException
    {
    }

    public void publishErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException
    {
      System.out.println("ActivityTracking:publishErrorReceived - " + body.toString());
    }

    public void publishDeregisterAckReceived(MALMessageHeader header, Map qosProperties) throws MALException
    {
    }
  }
}
