package org.ccsds.moims.mo.mc.alert;

import org.ccsds.moims.mo.com.COMObject;
import org.ccsds.moims.mo.com.COMService;
import org.ccsds.moims.mo.com.DuplicateException;
import org.ccsds.moims.mo.com.InvalidException;
import org.ccsds.moims.mo.com.structures.InstanceBooleanPairList;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.alert.structures.AlertCreationRequestList;
import org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetails;
import org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetailsList;
import org.ccsds.moims.mo.mc.alert.structures.AlertEventDetails;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;

/**
 * Helper class for Alert service.
 */
public class AlertServiceInfo extends COMService {

    /**
     * Service number literal.
     */
    public static final int _ALERT_SERVICE_NUMBER = 3;

    /**
     * Service number instance.
     */
    public static final UShort ALERT_SERVICE_NUMBER = new UShort(_ALERT_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier ALERT_SERVICE_NAME = new Identifier("Alert");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            4, 1, ALERT_SERVICE_NUMBER);

    /**
     * Operation number literal for operation ENABLEGENERATION.
     */
    public static final int _ENABLEGENERATION_OP_NUMBER = 1;

    /**
     * Operation number instance for operation ENABLEGENERATION.
     */
    private static final UShort ENABLEGENERATION_OP_NUMBER = new UShort(_ENABLEGENERATION_OP_NUMBER);

    /**
     * Operation instance for operation ENABLEGENERATION.
     */
    public static final MALRequestOperation ENABLEGENERATION_OP = new MALRequestOperation(SERVICE_KEY, 
            ENABLEGENERATION_OP_NUMBER, 
            new Identifier("enableGeneration"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("isGroupIds", true, Attribute.BOOLEAN_SHORT_FORM, "If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains AlertIdentity object instance identifiers.\nThe AlertIdentity objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the AlertIdentity objects to match.\nThe id of the enableInstances field shall support the wildcard value of '0' and matches all AlertIdentity objects of the provider.\nThe service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.\nIf the enableInstances field contains a value of TRUE then instances of matching AlertIdentity objects shall be generated, a value of FALSE requests that instances will not be generated.\nNo error shall be raised if the enableInstances Boolean value supplied is the same as the current generationEnabled field for an alert object i.e. enabling an already enabled alert will not result in an error.\nIf a requested AlertIdentity or GroupIdentity object is unknown then an UNKNOWN error shall be returned.\nIf a requested Group, or the Group objects referenced by that Group, does not contain AlertIdentity objects then an INVALID error shall be returned.\nIf an error is raised then no modifications shall be made as a result of this operation call.\nThe provider shall create and store a new AlertDefinition object in the COM archive if the generationEnabled field is changed.\nIf a new AlertDefinition object is created then that new object shall be the current AlertDefinition used for the specific AlertIdentity."),
                new OperationField("enableInstances", true, InstanceBooleanPairList.SHORT_FORM, null)}, 
            new OperationField[] {
                new OperationField("newObjInstIds", true, LongList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the new AlertDefinition objects.")}, 
            "The enableGeneration operation allows a consumer to control whether updates for specific alerts are generated or not. The operation allows the consumer to select the alerts directly or indirectly using groups.");

    /**
     * Operation number literal for operation LISTDEFINITION.
     */
    public static final int _LISTDEFINITION_OP_NUMBER = 2;

    /**
     * Operation number instance for operation LISTDEFINITION.
     */
    private static final UShort LISTDEFINITION_OP_NUMBER = new UShort(_LISTDEFINITION_OP_NUMBER);

    /**
     * Operation instance for operation LISTDEFINITION.
     */
    public static final MALRequestOperation LISTDEFINITION_OP = new MALRequestOperation(SERVICE_KEY, 
            LISTDEFINITION_OP_NUMBER, 
            new Identifier("listDefinition"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("alertNames", true, IdentifierList.SHORT_FORM, "The alertNames field shall contain a list of alert names to retrieve the AlertIdentity and AlertDefinition object instance identifiers for.\nThe alertNames field may contain the wildcard value of '*' to return all supported AlertIdentity and AlertDefinition objects.\nThe wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.\nIf a provided identifier does not include a wildcard and does not match an existing AlertIdentity object then this operation shall fail with an UNKNOWN error.")}, 
            new OperationField[] {
                new OperationField("alertObjInstIds", true, ObjectInstancePairList.SHORT_FORM, "The response shall contain a list of matching AlertIdentity and AlertDefinition object instance identifiers.\nThe returned list shall maintain the same order as the submitted list unless the wildcard value was included in the request.")}, 
            "The listDefinition operation allows a consumer to request the latest object instance identifiers of the AlertIdentity and AlertDefinition objects for the supported alerts of the provider.");

    /**
     * Operation number literal for operation ADDALERT.
     */
    public static final int _ADDALERT_OP_NUMBER = 3;

    /**
     * Operation number instance for operation ADDALERT.
     */
    private static final UShort ADDALERT_OP_NUMBER = new UShort(_ADDALERT_OP_NUMBER);

    /**
     * Operation instance for operation ADDALERT.
     */
    public static final MALRequestOperation ADDALERT_OP = new MALRequestOperation(SERVICE_KEY, 
            ADDALERT_OP_NUMBER, 
            new Identifier("addAlert"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("alertDefDetails", true, AlertCreationRequestList.SHORT_FORM, "The alertDefDetails field shall hold the name and the AlertDefinitionDetails to be added.\nThe name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).\nThe supplied name must be unique among all AlertIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.\nIf an error is raised then no new identities and definitions shall be added as a result of this operation call.\nIf the supplied name matches an existing, but removed, AlertIdentity then that AlertIdentity shall be reused otherwise a new AlertIdentity shall be created.\nThe provider shall create a new AlertDefinition object and store it, and any new AlertIdentity objects, in the COM archive.")}, 
            new OperationField[] {
                new OperationField("newObjInstIds", true, ObjectInstancePairList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the AlertIdentity and new AlertDefinition objects.\nThe returned list shall maintain the same order as the submitted definitions.")}, 
            "The addAlert operation allows a consumer to define one or more alerts that do not currently exist.\nThe new AlertIdentity and AlertDefinition objects are expected to be stored in the COM archive by the provider of the alert service.");

    /**
     * Operation number literal for operation UPDATEDEFINITION.
     */
    public static final int _UPDATEDEFINITION_OP_NUMBER = 4;

    /**
     * Operation number instance for operation UPDATEDEFINITION.
     */
    private static final UShort UPDATEDEFINITION_OP_NUMBER = new UShort(_UPDATEDEFINITION_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEDEFINITION.
     */
    public static final MALRequestOperation UPDATEDEFINITION_OP = new MALRequestOperation(SERVICE_KEY, 
            UPDATEDEFINITION_OP_NUMBER, 
            new Identifier("updateDefinition"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("alertObjInstIds", true, LongList.SHORT_FORM, "The alertObjInstIds field shall contain the object instance identifiers of the AlertIdentity objects to be updated.\nThe supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.\nIf the alertObjInstIds list contains either NULL or '0' an INVALID error shall be raised."),
                new OperationField("alertDefDetails", true, AlertDefinitionDetailsList.SHORT_FORM, "The alertDefDetails field shall contain the replacement AlertDefinitionDetails.\nThe two lists shall be ordered the same.\nThe number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.\nIf an error is raised then no definitions shall be updated as a result of this operation call.\nThe provider shall create a new AlertDefinition object and store it in the COM archive.\nThe new AlertDefinition object shall be the current AlertDefinition used for the specific AlertIdentity.")}, 
            new OperationField[] {
                new OperationField("newObjInstIds", true, LongList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the new AlertDefinition objects.\nThe returned list shall maintain the same order as the submitted definitions.")}, 
            "The updateDefinition operation allows a consumer to update a definition for one or more alerts.\nThis differs from deleting an existing alert and adding a new definition with the same alert name in the fact that the AlertIdentity object is not changed between the two definitions.\nThe replacement definition should be stored in the COM archive by the service provider. The operation does not remove the previous object from the COM archive, merely removes the object from the provider.");

    /**
     * Operation number literal for operation REMOVEALERT.
     */
    public static final int _REMOVEALERT_OP_NUMBER = 5;

    /**
     * Operation number instance for operation REMOVEALERT.
     */
    private static final UShort REMOVEALERT_OP_NUMBER = new UShort(_REMOVEALERT_OP_NUMBER);

    /**
     * Operation instance for operation REMOVEALERT.
     */
    public static final MALSubmitOperation REMOVEALERT_OP = new MALSubmitOperation(SERVICE_KEY, 
            REMOVEALERT_OP_NUMBER, 
            new Identifier("removeAlert"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("alertInstIds", true, LongList.SHORT_FORM, "The alertInstIds field shall hold the object instance identifiers of the AlertIdentity objects to be removed from the provider.\nThe list may contain the wildcard value of '0'.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a provided AlertIdentity object instance identifier does not include a wildcard and does not match an existing AlertIdentity object then this operation shall fail with an UNKNOWN error.\nMatched AlertIdentity objects shall not be removed from the COM archive only the list of AlertIdentity objects in the provider.\nIf an error is raised then no alerts shall be removed as a result of this operation call.\nIf the operation succeeds then the provider shall not publish AlertEvent events for the deleted AlertIdentity objects anymore.")}, 
            "The removeAlert operation allows a consumer to remove one or more definitions from the list of alerts supported by the alert provider.\nThe operation does not remove the AlertIdentity or AlertDefinition objects from the COM archive, merely removes the objects from the provider. This permits existing AlertEvent objects to continue to reference the correct AlertDefinition object in the COM archive.");

    /**
     * Area elements.
     */
    public static final Element[] ALERT_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{ENABLEGENERATION_OP,
        LISTDEFINITION_OP,
        ADDALERT_OP,
        UPDATEDEFINITION_OP,
        REMOVEALERT_OP};

    /**
     * Literal for object ALERTIDENTITY.
     */
    @Deprecated
    public static final int _ALERTIDENTITY_OBJECT_NUMBER = 1;

    /**
     * Instance for object ALERTIDENTITY.
     */
    @Deprecated
    public static final UShort ALERTIDENTITY_OBJECT_NUMBER = new UShort(_ALERTIDENTITY_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier ALERTIDENTITY_OBJECT_NAME = new Identifier("AlertIdentity");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType ALERTIDENTITY_OBJECT_TYPE = new ObjectType(new UShort(4), ALERT_SERVICE_NUMBER, new UOctet(1), ALERTIDENTITY_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject ALERTIDENTITY_OBJECT = new COMObject(ALERTIDENTITY_OBJECT_TYPE, ALERTIDENTITY_OBJECT_NAME, Attribute.IDENTIFIER_SHORT_FORM, false, null, true, null, false);

    /**
     * Literal for object ALERTDEFINITION.
     */
    @Deprecated
    public static final int _ALERTDEFINITION_OBJECT_NUMBER = 2;

    /**
     * Instance for object ALERTDEFINITION.
     */
    @Deprecated
    public static final UShort ALERTDEFINITION_OBJECT_NUMBER = new UShort(_ALERTDEFINITION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier ALERTDEFINITION_OBJECT_NAME = new Identifier("AlertDefinition");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType ALERTDEFINITION_OBJECT_TYPE = new ObjectType(new UShort(4), ALERT_SERVICE_NUMBER, new UOctet(1), ALERTDEFINITION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject ALERTDEFINITION_OBJECT = new COMObject(ALERTDEFINITION_OBJECT_TYPE, ALERTDEFINITION_OBJECT_NAME, AlertDefinitionDetails.SHORT_FORM, true, AlertServiceInfo.ALERTIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object ALERTEVENT.
     */
    @Deprecated
    public static final int _ALERTEVENT_OBJECT_NUMBER = 3;

    /**
     * Instance for object ALERTEVENT.
     */
    @Deprecated
    public static final UShort ALERTEVENT_OBJECT_NUMBER = new UShort(_ALERTEVENT_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier ALERTEVENT_OBJECT_NAME = new Identifier("AlertEvent");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType ALERTEVENT_OBJECT_TYPE = new ObjectType(new UShort(4), ALERT_SERVICE_NUMBER, new UOctet(1), ALERTEVENT_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject ALERTEVENT_OBJECT = new COMObject(ALERTEVENT_OBJECT_TYPE, ALERTEVENT_OBJECT_NAME, AlertEventDetails.SHORT_FORM, true, AlertServiceInfo.ALERTDEFINITION_OBJECT_TYPE, true, null, true);

    /**
     * Object instance.
     */
    public static final COMObject[] COM_OBJECTS = {
        ALERTIDENTITY_OBJECT,
        ALERTDEFINITION_OBJECT,
        ALERTEVENT_OBJECT,};

    /**
     * Creates an instance of the Alert ServiceInfo.
     * 
     */
    public AlertServiceInfo() {
        super(SERVICE_KEY, ALERT_SERVICE_NAME, ALERT_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
    }

    @Override
    public MALArea getArea() {
        return MCHelper.MC_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 1:
                switch (errorNumber) {
                    case 70000:
                        return new InvalidException(extraInfo);
                }
                break;
            case 2:
                switch (errorNumber) {
                }
                break;
            case 3:
                switch (errorNumber) {
                    case 70001:
                        return new DuplicateException(extraInfo);
                    case 70000:
                        return new InvalidException(extraInfo);
                }
                break;
            case 4:
                switch (errorNumber) {
                    case 70000:
                        return new InvalidException(extraInfo);
                }
                break;
            case 5:
                switch (errorNumber) {
                }
                break;
        }
        MOErrorException areaError = MCHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
