package org.ccsds.moims.mo.common.configuration.consumer;

/**
 * Consumer adapter for Configuration service.
 */
public abstract class ConfigurationAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when an INVOKE acknowledgement is received from a provider
     * for the operation activate.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activateAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
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
    public void activateResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            Boolean activationResult,
            org.ccsds.moims.mo.com.structures.ObjectIdList previousConfig,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement error is received from
     * a provider for the operation activate.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activateAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response error is received from a provider
     * for the operation activate.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activateResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
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
    public void listResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.com.structures.ObjectIdList objInstIds,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation list.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
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
    public void getCurrentResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.com.structures.ObjectIdList objInstId,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getCurrent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getCurrentErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
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
    public void exportXMLResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.File xmlConfiguration,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation exportXML.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void exportXMLErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation add.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation add.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation remove.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation remove.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement is received from a provider
     * for the operation storeCurrent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void storeCurrentAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response is received from a provider for
     * the operation storeCurrent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objInstId The response shall contain the object identifier of the new configuration object if successful or NULL if not.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void storeCurrentResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.com.structures.ObjectId objInstId,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement error is received from
     * a provider for the operation storeCurrent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void storeCurrentAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response error is received from a provider
     * for the operation storeCurrent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void storeCurrentResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation importXML.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objInstId The return response shall contain in the objInstId field the object identifier of the new configuration object.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void importXMLResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.com.structures.ObjectId objInstId,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation importXML.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void importXMLErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._ADD_OP_NUMBER:
            addAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._REMOVE_OP_NUMBER:
            removeAckReceived(msgHeader, qosProperties);
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
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._ADD_OP_NUMBER:
            addErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._REMOVE_OP_NUMBER:
            removeErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._LIST_OP_NUMBER:
            listResponseReceived(msgHeader,
                (org.ccsds.moims.mo.com.structures.ObjectIdList) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectIdList()), qosProperties);
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._GETCURRENT_OP_NUMBER:
            getCurrentResponseReceived(msgHeader,
                (org.ccsds.moims.mo.com.structures.ObjectIdList) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectIdList()), qosProperties);
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._EXPORTXML_OP_NUMBER:
            exportXMLResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.File) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.File()), qosProperties);
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._IMPORTXML_OP_NUMBER:
            importXMLResponseReceived(msgHeader,
                (org.ccsds.moims.mo.com.structures.ObjectId) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectId()), qosProperties);
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
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._LIST_OP_NUMBER:
            listErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._GETCURRENT_OP_NUMBER:
            getCurrentErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._EXPORTXML_OP_NUMBER:
            exportXMLErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._IMPORTXML_OP_NUMBER:
            importXMLErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._ACTIVATE_OP_NUMBER:
            activateAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._STORECURRENT_OP_NUMBER:
            storeCurrentAckReceived(msgHeader, qosProperties);
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
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._ACTIVATE_OP_NUMBER:
            activateAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._STORECURRENT_OP_NUMBER:
            storeCurrentAckErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._ACTIVATE_OP_NUMBER:
            activateResponseReceived(msgHeader,
                (body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                (org.ccsds.moims.mo.com.structures.ObjectIdList) body.getBodyElement(1, new org.ccsds.moims.mo.com.structures.ObjectIdList()), qosProperties);
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._STORECURRENT_OP_NUMBER:
            storeCurrentResponseReceived(msgHeader,
                (org.ccsds.moims.mo.com.structures.ObjectId) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectId()), qosProperties);
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
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._ACTIVATE_OP_NUMBER:
            activateResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._STORECURRENT_OP_NUMBER:
            storeCurrentResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
