package org.ccsds.moims.mo.com.archive.consumer;

/**
 * Consumer adapter for Archive service.
 */
public abstract class ArchiveAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when an INVOKE acknowledgement is received from a provider
     * for the operation retrieve.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void retrieveAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response is received from a provider for
     * the operation retrieve.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objDetails The response shall contain the set of matched objects.
The first returned list shall contain the matched object instance identifiers and object details of the matched objects.
The second returned list shall contain the object bodies ordered identically to the first list unless no body for the object is declared in the service specification, in which case a NULL replaces the complete list.
There shall be an entry in each returned list for each matched object.
When no objects have been matched only a response with NULL for each part of the response shall be returned.
The ordering of the returned objects is not specified and implementation specific.
If ordering of the returned objects is required then the query operation should be used instead.
     * @param objBodies objBodies Argument number 1 as defined by the service operation
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void retrieveResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList objDetails,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList objBodies,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement error is received from
     * a provider for the operation retrieve.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void retrieveAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response error is received from a provider
     * for the operation retrieve.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void retrieveResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation query.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation query.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objType objType Argument number 0 as defined by the service operation
     * @param domain domain Argument number 1 as defined by the service operation
     * @param objDetails objDetails Argument number 2 as defined by the service operation
     * @param objBodies objBodies Argument number 3 as defined by the service operation
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryUpdateReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList objDetails,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList objBodies,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation query.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objType The updates and the responses shall contain the set of matched objects.
If a wildcard was used in the ObjectType part of the request then the updates and response shall contain the ObjectType of each matched object.
If there was not any wildcards in the ObjectType part of the request the ObjectType in the updates and response shall be replaced by a NULL.
The first returned list shall contain the domain of the objects being returned.
If multiple ObjectTypes or domains have been matched then multiple Update message may be returned.
There shall be an entry in the second and third lists for each matched object.
The second returned list shall contain the archive details stored for the matched objects.
If the initial Boolean of the request was True the third returned list shall contain the bodies of the objects.
If the initial Boolean of the request was NULL or False the third returned list shall be replaced by a NULL.
The returned lists shall be sorted based on the sorting options specified in ArchiveQuery.
Each domain/object type pair shall be sorted separately from other domain/object type pairs, there is no requirement for sorting to be applied across domain/object type pairs.
When the field being sorted on contains a NULL value, or does not exist in the matched object (due to a containing composite being NULL), these entries shall be added to the end of the returned list in the order that they are matched.
When no objects have been matched only a response with NULL for each part of the response shall be returned.
     * @param domain domain Argument number 1 as defined by the service operation
     * @param objDetails objDetails Argument number 2 as defined by the service operation
     * @param objBodies objBodies Argument number 3 as defined by the service operation
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList objDetails,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList objBodies,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation query.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation query.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryUpdateErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation query.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement is received from a provider
     * for the operation count.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void countAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response is received from a provider for
     * the operation count.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param counts The response shall contain the count of matched objects.
There shall be an entry in each returned list for each entry in the request list.
The returned lists shall be ordered the same as the request query lists so that the response can be matched to the corresponding request.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void countResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.LongList counts,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement error is received from
     * a provider for the operation count.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void countAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response error is received from a provider
     * for the operation count.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void countResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation store.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objInstIds The response shall contain the set of new object instance identifiers if the request supplied an initial TRUE Boolean value, otherwise it returns NULL.
The returned list shall be ordered identically to the submitted list so that the returned object instance identifiers can be mapped to the correct objects.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void storeResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation store.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void storeErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation update.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation update.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation delete.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param deletedObjInstIds The response shall contain the set of object instance identifiers of the deleted objects.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.LongList deletedObjInstIds,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation delete.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._UPDATE_OP_NUMBER:
            updateAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void submitErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._UPDATE_OP_NUMBER:
            updateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._STORE_OP_NUMBER:
            storeResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()), qosProperties);
            break;
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._DELETE_OP_NUMBER:
            deleteResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._STORE_OP_NUMBER:
            storeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._DELETE_OP_NUMBER:
            deleteErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void invokeAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._RETRIEVE_OP_NUMBER:
            retrieveAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._COUNT_OP_NUMBER:
            countAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void invokeAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._RETRIEVE_OP_NUMBER:
            retrieveAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._COUNT_OP_NUMBER:
            countAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void invokeResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._RETRIEVE_OP_NUMBER:
            retrieveResponseReceived(msgHeader,
                (org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList) body.getBodyElement(0, new org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList()),
                (org.ccsds.moims.mo.mal.structures.HeterogeneousList) body.getBodyElement(1, null), qosProperties);
            break;
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._COUNT_OP_NUMBER:
            countResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void invokeResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._RETRIEVE_OP_NUMBER:
            retrieveResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._COUNT_OP_NUMBER:
            countResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._QUERY_OP_NUMBER:
            queryAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._QUERY_OP_NUMBER:
            queryAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._QUERY_OP_NUMBER:
            queryUpdateReceived(msgHeader,
                (org.ccsds.moims.mo.com.structures.ObjectType) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectType()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList) body.getBodyElement(2, new org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList()),
                (org.ccsds.moims.mo.mal.structures.HeterogeneousList) body.getBodyElement(3, null), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._QUERY_OP_NUMBER:
            queryUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._QUERY_OP_NUMBER:
            queryResponseReceived(msgHeader,
                (org.ccsds.moims.mo.com.structures.ObjectType) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectType()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList) body.getBodyElement(2, new org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList()),
                (org.ccsds.moims.mo.mal.structures.HeterogeneousList) body.getBodyElement(3, null), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._QUERY_OP_NUMBER:
            queryResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
