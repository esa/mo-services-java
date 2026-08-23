package org.ccsds.moims.mo.com.archive;

/**
 * Helper class for Archive service.
 */
public class ArchiveServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _ARCHIVE_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort ARCHIVE_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ARCHIVE_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier ARCHIVE_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Archive");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            2, 1, ARCHIVE_SERVICE_NUMBER);

    /**
     * Operation number literal for operation RETRIEVE.
     */
    public static final int _RETRIEVE_OP_NUMBER = 1;

    /**
     * Operation number instance for operation RETRIEVE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort RETRIEVE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_RETRIEVE_OP_NUMBER);

    /**
     * Operation instance for operation RETRIEVE.
     */
    public static final org.ccsds.moims.mo.mal.MALInvokeOperation RETRIEVE_OP = new org.ccsds.moims.mo.mal.MALInvokeOperation(SERVICE_KEY, 
            RETRIEVE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("retrieve"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objType", true, org.ccsds.moims.mo.com.structures.ObjectType.SHORT_FORM, "The first part of the request shall contain the type of the object required.\nIf any of the fields of the object type contains the wildcard value of '0' then an INVALID error shall be returned.\nThe second part of the request shall contain the domain to match.\nIf the domain contains the wildcard value of '*' then an INVALID error shall be returned.\nThe third part of the request shall contain the list of object instance identifiers to match.\nIf the object instance identifier list contains the wildcard value '0' then all object instances shall be matched.\nIf any explicitly requested object cannot be matched then an UNKNOWN error shall be returned."),
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("objInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, null)}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objDetails", true, org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList.SHORT_FORM, "The response shall contain the set of matched objects.\nThe first returned list shall contain the matched object instance identifiers and object details of the matched objects.\nThe second returned list shall contain the object bodies ordered identically to the first list unless no body for the object is declared in the service specification, in which case a NULL replaces the complete list.\nThere shall be an entry in each returned list for each matched object.\nWhen no objects have been matched only a response with NULL for each part of the response shall be returned.\nThe ordering of the returned objects is not specified and implementation specific.\nIf ordering of the returned objects is required then the query operation should be used instead."),
                new org.ccsds.moims.mo.mal.OperationField("objBodies", true, null, null)}, 
            "Retrieves a set of objects identified by their object instance identifier.");

    /**
     * Operation number literal for operation QUERY.
     */
    public static final int _QUERY_OP_NUMBER = 2;

    /**
     * Operation number instance for operation QUERY.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort QUERY_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_QUERY_OP_NUMBER);

    /**
     * Operation instance for operation QUERY.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation QUERY_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            QUERY_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("query"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("returnBody", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "The first part of the request shall contain a Boolean that if set to TRUE requests that the body of the objects is returned otherwise only the ObjectType and ArchiveDetails shall be returned and the returned list of the bodies of the objects shall be replaced with a NULL.\nThe second part of the request shall contain the type of the object required.\nEach part of the object type may contain the wildcard value of '0'.\nThe third and fourth parts of the request shall contain the queries to evaluate.\nA single query shall be formed by the combination of an ArchiveQuery from the first list and a QueryFilter from the second list.\nThe two lists shall be ordered identically so that the query and the filter parts can be matched together.\nIf a query does not contain a QueryFilter part then that entry in the QueryFilter list shall be replaced with a NULL value.\nIf the request does not contain any QueryFilters then the complete list may be replaced with a NULL.\nThe size of the two lists must be the same unless the complete second list is replaced with a NULL otherwise an INVALID error shall be raised.\nFor each query, the ArchiveQuery and the QueryFilter shall contain the COM object fields to filter on.\nThe ArchiveQuery may contain the wildcard value of NULL on each of the fields.\nIf an ArchiveQuery contains an end time but no start time then it shall match the single object that has a timestamp closest to, but not greater than, the end time field.\nThe end time field may specify a time in the future.\nIf the sortFieldName of the ArchiveQuery does not reference a defined field then an INVALID error shall be returned.\nEach query shall be evaluated separately from each other, the filter of one query will not affect the filter of another. This forms a logical OR operation.\nIf the QueryFilter contains an error then an INVALID error shall be returned. The definition of erroneous values are filter specific and defined in the relevant filter structure specification."),
                new org.ccsds.moims.mo.mal.OperationField("objType", true, org.ccsds.moims.mo.com.structures.ObjectType.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("archiveQuery", true, org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("queryFilter", true, null, null)}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objType", true, org.ccsds.moims.mo.com.structures.ObjectType.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("objDetails", true, org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("objBodies", true, null, null)}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objType", true, org.ccsds.moims.mo.com.structures.ObjectType.SHORT_FORM, "The updates and the responses shall contain the set of matched objects.\nIf a wildcard was used in the ObjectType part of the request then the updates and response shall contain the ObjectType of each matched object.\nIf there was not any wildcards in the ObjectType part of the request the ObjectType in the updates and response shall be replaced by a NULL.\nThe first returned list shall contain the domain of the objects being returned.\nIf multiple ObjectTypes or domains have been matched then multiple Update message may be returned.\nThere shall be an entry in the second and third lists for each matched object.\nThe second returned list shall contain the archive details stored for the matched objects.\nIf the initial Boolean of the request was True the third returned list shall contain the bodies of the objects.\nIf the initial Boolean of the request was NULL or False the third returned list shall be replaced by a NULL.\nThe returned lists shall be sorted based on the sorting options specified in ArchiveQuery.\nEach domain/object type pair shall be sorted separately from other domain/object type pairs, there is no requirement for sorting to be applied across domain/object type pairs.\nWhen the field being sorted on contains a NULL value, or does not exist in the matched object (due to a containing composite being NULL), these entries shall be added to the end of the returned list in the order that they are matched.\nWhen no objects have been matched only a response with NULL for each part of the response shall be returned."),
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("objDetails", true, org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("objBodies", true, null, null)}, 
            "Retrieves a set of object instance identifiers, and optionally the object bodies, from a list of supplied queries. The PROGRESS interaction pattern is used as the returned set of data may be quite large and this allows it to be split over several MAL messages.");

    /**
     * Operation number literal for operation COUNT.
     */
    public static final int _COUNT_OP_NUMBER = 3;

    /**
     * Operation number instance for operation COUNT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort COUNT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_COUNT_OP_NUMBER);

    /**
     * Operation instance for operation COUNT.
     */
    public static final org.ccsds.moims.mo.mal.MALInvokeOperation COUNT_OP = new org.ccsds.moims.mo.mal.MALInvokeOperation(SERVICE_KEY, 
            COUNT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("count"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objType", true, org.ccsds.moims.mo.com.structures.ObjectType.SHORT_FORM, "The ObjectType, ArchiveQuery and QueryFilter parts of the request shall be populated exactly the same as for the query operation."),
                new org.ccsds.moims.mo.mal.OperationField("archiveQuery", true, org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("queryFilter", true, null, null)}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("counts", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The response shall contain the count of matched objects.\nThere shall be an entry in each returned list for each entry in the request list.\nThe returned lists shall be ordered the same as the request query lists so that the response can be matched to the corresponding request.")}, 
            "Counts the set of objects based on a supplied query.");

    /**
     * Operation number literal for operation STORE.
     */
    public static final int _STORE_OP_NUMBER = 4;

    /**
     * Operation number instance for operation STORE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort STORE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_STORE_OP_NUMBER);

    /**
     * Operation instance for operation STORE.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation STORE_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            STORE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("store"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("returnObjInstIds", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "The first part of the request indicates whether the operation should return the object instance identifiers used, if TRUE it shall return them, otherwise it returns NULL.\nThe second part of the request shall contain the type of object being stored.\nThe third part of the request shall contain the domain of the objects being stored.\nThe fourth part of the request shall contain the list of archive details to use, one for each object being stored.\nIf the object instance identifier supplied in the archive details is set to 0 then the store operation shall allocate a new and unused object instance identifier.\nIf the object instance identifier supplied in the archive details is not set to 0 and is currently used in the archive then a DUPLICATE error is returned and no objects from the request shall be stored.\nThe fifth part of the request shall contain the list of objects to store.\nThe fourth and fifth list must be the same size as there is only one entry in each for each object to be stored. If they differ in size an INVALID error is returned with the extra error information integer giving the index of the list entry without a matching entry in the other list.\nAn INVALID error shall be returned if a wildcard value of '0' is used in the object type.\nAn INVALID error shall be returned if a wildcard value of '*' is used in the domain identifier list.\nAn INVALID error shall be returned if the values of '0', '*', or NULL are used in the network, timestamp or provider fields of the archive details except for the object instance identifier.\nThe type of the body of the object should be checked against the declared type in the relevant service specification, if different an INVALID error is raised.\nIf any error is returned then the store operation shall be rolled back and nothing is stored as a result of the operation."),
                new org.ccsds.moims.mo.mal.OperationField("objType", true, org.ccsds.moims.mo.com.structures.ObjectType.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("objDetails", true, org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("objBodies", true, null, null)}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The response shall contain the set of new object instance identifiers if the request supplied an initial TRUE Boolean value, otherwise it returns NULL.\nThe returned list shall be ordered identically to the submitted list so that the returned object instance identifiers can be mapped to the correct objects.")}, 
            "Stores new objects in the archive and causes an ObjectStored event to be published by the archive.\nWhen new objects are being stored in an archive by a service provider the archive service provider is capable of allocating an unused object instance identifier for the objects being stored. The returned object instance identifier should be used by the service provider for identifying the object instances to its consumer to ensure that only a single object instance identifier is used for each object instance.");

    /**
     * Operation number literal for operation UPDATE.
     */
    public static final int _UPDATE_OP_NUMBER = 5;

    /**
     * Operation number instance for operation UPDATE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort UPDATE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_UPDATE_OP_NUMBER);

    /**
     * Operation instance for operation UPDATE.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation UPDATE_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            UPDATE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("update"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objType", true, org.ccsds.moims.mo.com.structures.ObjectType.SHORT_FORM, "The first part of the request shall contain the type of object being updated.\nThe second part of the request shall contain the domain of the objects being updated.\nThe third part of the request shall contain the list of ArchiveDetails.\nThe object instance identifier contained in the ArchiveDetails, combined with the object type and domain from the request, shall be used to match objects.\nIf requested object cannot be matched then an UNKNOWN error shall be returned and nothing will be updated.\nThe remainder of the ArchiveDetails shall be used to update the matched objects.\nThe fourth part of the request shall contain the list of objects to replace the matched objects with.\nNo wildcard values shall be accepted in the object type, the domain, and the object instance identifier, an INVALID error is returned in this case and no objects are updated."),
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("objDetails", true, org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("objBodies", true, null, null)}, 
            "Updates an object (or set of objects), causes an ObjectUpdated event to be published by the archive.");

    /**
     * Operation number literal for operation DELETE.
     */
    public static final int _DELETE_OP_NUMBER = 6;

    /**
     * Operation number instance for operation DELETE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort DELETE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DELETE_OP_NUMBER);

    /**
     * Operation instance for operation DELETE.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation DELETE_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            DELETE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("delete"), 
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objType", true, org.ccsds.moims.mo.com.structures.ObjectType.SHORT_FORM, "The first part of the request shall contain the type of object to match and is not permitted to contain the wildcard value.\nThe second part of the request shall contain the domain of the objects to match and is not permitted to contain the wildcard value.\nIf either the first or second part contain a wildcard value then an INVALID error shall be returned and no object deleted.\nThe third part of the request shall contain the list of object instance identifiers to match.\nIf the object instance identifier list contains the wildcard value '0' then all object instances shall be matched.\nIf any explicitly requested object cannot be matched then an UNKNOWN error shall be returned and nothing will be deleted.\nThe matched objects shall be deleted from the archive."),
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("objInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, null)}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("deletedObjInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The response shall contain the set of object instance identifiers of the deleted objects.")}, 
            "Deletes an object (or set of objects) and causes an ObjectDeleted event to be published by the archive.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] ARCHIVE_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{RETRIEVE_OP,
        QUERY_OP,
        COUNT_OP,
        STORE_OP,
        UPDATE_OP,
        DELETE_OP};

    /**
     * Literal for object OBJECTSTORED.
     */
    @Deprecated
    public static final int _OBJECTSTORED_OBJECT_NUMBER = 1;

    /**
     * Instance for object OBJECTSTORED.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort OBJECTSTORED_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_OBJECTSTORED_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier OBJECTSTORED_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ObjectStored");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType OBJECTSTORED_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(2), ARCHIVE_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), OBJECTSTORED_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject OBJECTSTORED_OBJECT = new org.ccsds.moims.mo.com.COMObject(OBJECTSTORED_OBJECT_TYPE, OBJECTSTORED_OBJECT_NAME, null, false, null, true, null, true);

    /**
     * Literal for object OBJECTUPDATED.
     */
    @Deprecated
    public static final int _OBJECTUPDATED_OBJECT_NUMBER = 2;

    /**
     * Instance for object OBJECTUPDATED.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort OBJECTUPDATED_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_OBJECTUPDATED_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier OBJECTUPDATED_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ObjectUpdated");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType OBJECTUPDATED_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(2), ARCHIVE_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), OBJECTUPDATED_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject OBJECTUPDATED_OBJECT = new org.ccsds.moims.mo.com.COMObject(OBJECTUPDATED_OBJECT_TYPE, OBJECTUPDATED_OBJECT_NAME, null, false, null, true, null, true);

    /**
     * Literal for object OBJECTDELETED.
     */
    @Deprecated
    public static final int _OBJECTDELETED_OBJECT_NUMBER = 3;

    /**
     * Instance for object OBJECTDELETED.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort OBJECTDELETED_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_OBJECTDELETED_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier OBJECTDELETED_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ObjectDeleted");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType OBJECTDELETED_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(2), ARCHIVE_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), OBJECTDELETED_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject OBJECTDELETED_OBJECT = new org.ccsds.moims.mo.com.COMObject(OBJECTDELETED_OBJECT_TYPE, OBJECTDELETED_OBJECT_NAME, null, false, null, true, null, true);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        OBJECTSTORED_OBJECT,
        OBJECTUPDATED_OBJECT,
        OBJECTDELETED_OBJECT,};

    /**
     * Creates an instance of the Archive ServiceInfo.
     * 
     */
    public ArchiveServiceInfo() {
        super(SERVICE_KEY, ARCHIVE_SERVICE_NAME, ARCHIVE_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.com.COMHelper.COM_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 70000:
                return new org.ccsds.moims.mo.com.InvalidException(extraInfo);
            case 70001:
                return new org.ccsds.moims.mo.com.DuplicateException(extraInfo);
        }
        return null;
    }

}
