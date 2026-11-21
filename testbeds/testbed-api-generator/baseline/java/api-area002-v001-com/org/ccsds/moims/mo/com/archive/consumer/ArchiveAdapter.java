package org.ccsds.moims.mo.com.archive.consumer;

import java.util.Map;
import org.ccsds.moims.mo.com.archive.ArchiveServiceInfo;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Consumer adapter for Archive service.
 */
public abstract class ArchiveAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when an INVOKE acknowledgement is received from a provider
     * for the operation retrieve.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void retrieveAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
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
    public void retrieveResponseReceived(MALMessageHeader msgHeader,
            ArchiveDetailsList objDetails,
            HeterogeneousList objBodies,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement error is received from
     * a provider for the operation retrieve.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void retrieveAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response error is received from a provider
     * for the operation retrieve.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void retrieveResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation query.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
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
    public void queryUpdateReceived(MALMessageHeader msgHeader,
            ObjectType objType,
            IdentifierList domain,
            ArchiveDetailsList objDetails,
            HeterogeneousList objBodies,
            Map qosProperties) {
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
    public void queryResponseReceived(MALMessageHeader msgHeader,
            ObjectType objType,
            IdentifierList domain,
            ArchiveDetailsList objDetails,
            HeterogeneousList objBodies,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation query.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation query.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation query.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement is received from a provider
     * for the operation count.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void countAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
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
    public void countResponseReceived(MALMessageHeader msgHeader,
            LongList counts,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement error is received from
     * a provider for the operation count.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void countAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response error is received from a provider
     * for the operation count.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void countResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
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
    public void storeResponseReceived(MALMessageHeader msgHeader,
            LongList objInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation store.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void storeErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation update.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation update.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation delete.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param deletedObjInstIds The response shall contain the set of object instance identifiers of the deleted objects.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteResponseReceived(MALMessageHeader msgHeader,
            LongList deletedObjInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation delete.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._UPDATE_OP_NUMBER:
            updateAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void submitErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._UPDATE_OP_NUMBER:
            updateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._STORE_OP_NUMBER:
            storeResponseReceived(msgHeader,
                (LongList) body.getBodyElement(0, new LongList()), qosProperties);
            break;
          case ArchiveServiceInfo._DELETE_OP_NUMBER:
            deleteResponseReceived(msgHeader,
                (LongList) body.getBodyElement(0, new LongList()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._STORE_OP_NUMBER:
            storeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ArchiveServiceInfo._DELETE_OP_NUMBER:
            deleteErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void invokeAckReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._RETRIEVE_OP_NUMBER:
            retrieveAckReceived(msgHeader, qosProperties);
            break;
          case ArchiveServiceInfo._COUNT_OP_NUMBER:
            countAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void invokeAckErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._RETRIEVE_OP_NUMBER:
            retrieveAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ArchiveServiceInfo._COUNT_OP_NUMBER:
            countAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void invokeResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._RETRIEVE_OP_NUMBER:
            retrieveResponseReceived(msgHeader,
                (ArchiveDetailsList) body.getBodyElement(0, new ArchiveDetailsList()),
                (HeterogeneousList) body.getBodyElement(1, null), qosProperties);
            break;
          case ArchiveServiceInfo._COUNT_OP_NUMBER:
            countResponseReceived(msgHeader,
                (LongList) body.getBodyElement(0, new LongList()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void invokeResponseErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._RETRIEVE_OP_NUMBER:
            retrieveResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ArchiveServiceInfo._COUNT_OP_NUMBER:
            countResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._QUERY_OP_NUMBER:
            queryAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._QUERY_OP_NUMBER:
            queryAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._QUERY_OP_NUMBER:
            queryUpdateReceived(msgHeader,
                (ObjectType) body.getBodyElement(0, new ObjectType()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
                (ArchiveDetailsList) body.getBodyElement(2, new ArchiveDetailsList()),
                (HeterogeneousList) body.getBodyElement(3, null), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._QUERY_OP_NUMBER:
            queryUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._QUERY_OP_NUMBER:
            queryResponseReceived(msgHeader,
                (ObjectType) body.getBodyElement(0, new ObjectType()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
                (ArchiveDetailsList) body.getBodyElement(2, new ArchiveDetailsList()),
                (HeterogeneousList) body.getBodyElement(3, null), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ArchiveServiceInfo._QUERY_OP_NUMBER:
            queryResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
