package org.ccsds.moims.mo.common.configuration.consumer;

import java.util.Map;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectIdList;
import org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.File;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Consumer adapter for Configuration service.
 */
public abstract class ConfigurationAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when an INVOKE acknowledgement is received from a provider
     * for the operation activate.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activateAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response is received from a provider for
     * the operation activate.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activationResult The service provider that implements the selected service shall, after the reception of the ConfigurationSwitch event, reconfigure itself and publish a ConfigurationSwitched COM event.
If the operation fails the previous configuration shall remain active.
In the case of a provider configuration, where multiple service configurations are being switched, the provider must switch all configurations successfully or roll back to the previous configuration. No partial reconfiguration is supported.
If a provider level configuration is successful, and a COM archive is being used, then the service provider that implements the selected service shall store in the COM archive a new ProviderConfigurationLink COM object that links its ServiceProvider object to the new activated ProviderConfiguration COM object.
The response message shall be sent when the configuration is either made active or fails.
If the activation was successful then the activationResult field shall be set to TRUE, otherwise FALSE for failure.
     * @param previousConfig The previousConfig field shall point to the previously active configuration or NULL if no configuration was previously active or the activationResult was FALSE for failure.
If a service configuration was requested, configObjId field shall referenced a ServiceConfiguration, the response shall contain a list of a single item of the previous ServiceConfiguration COM object.
If a provider configuration was requested, configObjId field shall referenced a ProviderConfiguration, the response shall contain a list of the previous ProviderConfiguration COM object followed by the list of previous ServiceConfiguration objects active for that provider.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activateResponseReceived(MALMessageHeader msgHeader,
            Boolean activationResult,
            ObjectIdList previousConfig,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement error is received from
     * a provider for the operation activate.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activateAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response error is received from a provider
     * for the operation activate.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activateResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation list.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objInstIds The operation shall return the list of matched configuration objects known to the configuration service provider.
If no configurations matched then an empty list shall be returned.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResponseReceived(MALMessageHeader msgHeader,
            ObjectIdList objInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation list.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getCurrent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objInstId If a service configuration was requested, serviceKey was not NULL, the response shall contain a list of a single item of the matched ServiceConfiguration COM object.
If a provider configuration was requested, serviceKey was NULL, the response shall contain a list of the matched ProviderConfiguration COM object followed by the list of active ServiceConfiguration objects active for that provider.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getCurrentResponseReceived(MALMessageHeader msgHeader,
            ObjectIdList objInstId,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getCurrent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getCurrentErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation exportXML.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param xmlConfiguration The returned File object shall contain the configuration XML.
The Configuration object shall not be deleted from the COM Archive.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void exportXMLResponseReceived(MALMessageHeader msgHeader,
            File xmlConfiguration,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation exportXML.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void exportXMLErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation add.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation add.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation remove.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation remove.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement is received from a provider
     * for the operation storeCurrent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void storeCurrentAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response is received from a provider for
     * the operation storeCurrent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objInstId The response shall contain the object identifier of the new configuration object if successful or NULL if not.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void storeCurrentResponseReceived(MALMessageHeader msgHeader,
            ObjectId objInstId,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement error is received from
     * a provider for the operation storeCurrent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void storeCurrentAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response error is received from a provider
     * for the operation storeCurrent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void storeCurrentResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation importXML.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objInstId The return response shall contain in the objInstId field the object identifier of the new configuration object.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void importXMLResponseReceived(MALMessageHeader msgHeader,
            ObjectId objInstId,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation importXML.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void importXMLErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ConfigurationServiceInfo._ADD_OP_NUMBER:
            addAckReceived(msgHeader, qosProperties);
            break;
          case ConfigurationServiceInfo._REMOVE_OP_NUMBER:
            removeAckReceived(msgHeader, qosProperties);
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
          case ConfigurationServiceInfo._ADD_OP_NUMBER:
            addErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ConfigurationServiceInfo._REMOVE_OP_NUMBER:
            removeErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case ConfigurationServiceInfo._LIST_OP_NUMBER:
            listResponseReceived(msgHeader,
                (ObjectIdList) body.getBodyElement(0, new ObjectIdList()), qosProperties);
            break;
          case ConfigurationServiceInfo._GETCURRENT_OP_NUMBER:
            getCurrentResponseReceived(msgHeader,
                (ObjectIdList) body.getBodyElement(0, new ObjectIdList()), qosProperties);
            break;
          case ConfigurationServiceInfo._EXPORTXML_OP_NUMBER:
            exportXMLResponseReceived(msgHeader,
                (File) body.getBodyElement(0, new File()), qosProperties);
            break;
          case ConfigurationServiceInfo._IMPORTXML_OP_NUMBER:
            importXMLResponseReceived(msgHeader,
                (ObjectId) body.getBodyElement(0, new ObjectId()), qosProperties);
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
          case ConfigurationServiceInfo._LIST_OP_NUMBER:
            listErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ConfigurationServiceInfo._GETCURRENT_OP_NUMBER:
            getCurrentErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ConfigurationServiceInfo._EXPORTXML_OP_NUMBER:
            exportXMLErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ConfigurationServiceInfo._IMPORTXML_OP_NUMBER:
            importXMLErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case ConfigurationServiceInfo._ACTIVATE_OP_NUMBER:
            activateAckReceived(msgHeader, qosProperties);
            break;
          case ConfigurationServiceInfo._STORECURRENT_OP_NUMBER:
            storeCurrentAckReceived(msgHeader, qosProperties);
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
          case ConfigurationServiceInfo._ACTIVATE_OP_NUMBER:
            activateAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ConfigurationServiceInfo._STORECURRENT_OP_NUMBER:
            storeCurrentAckErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case ConfigurationServiceInfo._ACTIVATE_OP_NUMBER:
            activateResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(),
                (ObjectIdList) body.getBodyElement(1, new ObjectIdList()), qosProperties);
            break;
          case ConfigurationServiceInfo._STORECURRENT_OP_NUMBER:
            storeCurrentResponseReceived(msgHeader,
                (ObjectId) body.getBodyElement(0, new ObjectId()), qosProperties);
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
          case ConfigurationServiceInfo._ACTIVATE_OP_NUMBER:
            activateResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ConfigurationServiceInfo._STORECURRENT_OP_NUMBER:
            storeCurrentResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
