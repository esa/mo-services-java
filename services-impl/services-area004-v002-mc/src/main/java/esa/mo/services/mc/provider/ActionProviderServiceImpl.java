/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO services
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

package esa.mo.services.mc.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConfigurationProviderSingleton;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALSubmit;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mc.DuplicateException;
import org.ccsds.moims.mo.mc.InvalidException;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.RejectedException;
import org.ccsds.moims.mo.mc.action.ActionHelper;
import org.ccsds.moims.mo.mc.action.provider.ActionInheritanceSkeleton;
import org.ccsds.moims.mo.mc.action.provider.MonitorExecutionPublisher;
import org.ccsds.moims.mo.mc.backends.ActionBackend;
import org.ccsds.moims.mo.mc.structures.ActionCategory;
import org.ccsds.moims.mo.mc.structures.ActionCompleteEvent;
import org.ccsds.moims.mo.mc.structures.ActionDefinition;
import org.ccsds.moims.mo.mc.structures.ActionDefinitionList;
import org.ccsds.moims.mo.mc.structures.ActionEvent;
import org.ccsds.moims.mo.mc.structures.ActionExecutionRequest;
import org.ccsds.moims.mo.mc.structures.ActionInProgressEvent;
import org.ccsds.moims.mo.mc.structures.ActionStartEvent;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinition;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList;

/**
 * Action service implementation, provider side.
 */
public class ActionProviderServiceImpl extends ActionInheritanceSkeleton {

    private static final Logger LOGGER = Logger.getLogger(ActionProviderServiceImpl.class.getName());

    private final ConnectionProvider connection = new ConnectionProvider();
    private ActionBackend backend;
    private MALProvider service;
    private MonitorExecutionPublisher monitorExecutionPublisher;
    private boolean running = false;
    private boolean isRegistered = false;

    private ActionDefinitionList definitions;
    private final ConcurrentHashMap<java.lang.Long, Object> knownRequestIds = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<java.lang.Long, AtomicInteger> executionStageCounters = new ConcurrentHashMap<>();

    /**
     * Starts the service and registers the monitorExecution publisher
     */
    public synchronized void init(ActionBackend backend) throws MALException {
        if (backend == null) {
            throw new IllegalArgumentException("The backend cannot be null!");
        }

        this.backend = backend;
        this.definitions = backend.getAllActionDefinitions();

        if (service != null) {
            connection.closeAll();
        }

        service = connection.startService(ActionHelper.ACTION_SERVICE, true, this);

        IdentifierList domain = connection.getConnectionDetails().getDomain();
        Identifier network = ConfigurationProviderSingleton.getNetwork();
        if (network == null) {
            network = new Identifier("");
        }

        monitorExecutionPublisher = super.createMonitorExecutionPublisher(
                domain,
                network,
                SessionType.LIVE,
                new Identifier("LIVE"),
                QoSLevel.BESTEFFORT,
                null,
                new UInteger(0));

        try {
            IdentifierList keyNames = new IdentifierList();
            keyNames.add(new Identifier("requestId"));
            keyNames.add(new Identifier("actionKey"));
            keyNames.add(new Identifier("actionCategory"));

            org.ccsds.moims.mo.mal.structures.AttributeTypeList keyTypes =
                    new org.ccsds.moims.mo.mal.structures.AttributeTypeList();
            keyTypes.add(AttributeType.LONG);
            keyTypes.add(AttributeType.IDENTIFIER);
            keyTypes.add(AttributeType.UOCTET);

            monitorExecutionPublisher.register(keyNames, keyTypes, new PublishInteractionListener());
            isRegistered = true;
        } catch (MALInteractionException ex) {
            LOGGER.log(Level.SEVERE, "Failed to register monitorExecution publisher", ex);
            throw new MALException("Failed to register monitorExecution publisher", ex);
        }

        running = true;
        LOGGER.info("Action service READY");
    }

    public void close() {
        try {
            if (isRegistered && monitorExecutionPublisher != null) {
                try {
                    monitorExecutionPublisher.deregister();
                } catch (MALInteractionException | MALException ex) {
                    LOGGER.log(Level.WARNING, "Exception during publisher deregistration {0}", ex);
                }
                isRegistered = false;
            }

            if (monitorExecutionPublisher != null) {
                try {
                    monitorExecutionPublisher.close();
                } catch (MALException ex) {
                    LOGGER.log(Level.WARNING, "Exception during publisher close {0}", ex);
                }
            }

            if (service != null) {
                service.close();
            }

            connection.closeAll();
            knownRequestIds.clear();
            running = false;
        } catch (MALException ex) {
            LOGGER.log(Level.WARNING, "Exception during close down of the provider {0}", ex);
        }
    }

    @Override
    public ConnectionProvider getConnection() {
        return this.connection;
    }

    @Override
    public void execute(ActionExecutionRequest executionRequest, MALInteraction interaction)
            throws DuplicateException, InvalidException, RejectedException, UnknownException, MALInteractionException, MALException {

        java.lang.Long requestId = executionRequest.getRequestId();
        if (knownRequestIds.putIfAbsent(requestId, Boolean.TRUE) != null) {
            throw new DuplicateException(null);
        }

        ActionDefinition definition = resolveActionDefinition(executionRequest.getActionRef());
        if (definition == null) {
            knownRequestIds.remove(requestId);
            throw new UnknownException(null);
        }

        UIntegerList invalidIndices = validateArgumentValues(executionRequest, definition);
        if (invalidIndices != null) {
            knownRequestIds.remove(requestId);
            throw new InvalidException(invalidIndices);
        }

        String rejectReason = backend.check(executionRequest, definition);
        if (rejectReason != null) {
            knownRequestIds.remove(requestId);
            throw new RejectedException(rejectReason);
        }
        
        ((MALSubmit) interaction).sendAcknowledgement();

        runExecutionAsync(executionRequest, definition);
    }

    /**
     * Looks up definition by ref; version 0 means use latest.
     */
    private ActionDefinition resolveActionDefinition(org.ccsds.moims.mo.mal.structures.ObjectRef<ActionDefinition> actionRef) {
        if (actionRef == null || definitions == null) {
            return null;
        }
        IdentifierList refDomain = actionRef.getDomain();
        Identifier refKey = actionRef.getKey();
        UInteger refVersion = actionRef.getObjectVersion();
        boolean wantLatest = (refVersion == null || refVersion.getValue() == 0);

        ActionDefinition match = null;
        for (int i = 0; i < definitions.size(); i++) {
            ActionDefinition def = definitions.get(i);
            if (!def.getObjectIdentity().getKey().equals(refKey)) {
                continue;
            }
            if (refDomain != null && !def.getObjectIdentity().getDomain().equals(refDomain)) {
                continue;
            }
            if (wantLatest) {
                match = def;
                continue;
            }
            if (def.getObjectIdentity().getVersion().getValue() == refVersion.getValue()) {
                return def;
            }
        }
        return match;
    }

    /**
     * Checks argument count and types; returns list of bad indices or null if ok.
     */
    private UIntegerList validateArgumentValues(ActionExecutionRequest executionRequest, ActionDefinition definition) {
        ArgumentDefinitionList argDefs = definition.getArguments();
        NullableAttributeList values = executionRequest.getArgumentValues();

        int defSize = (argDefs == null) ? 0 : argDefs.size();
        int valSize = (values == null) ? 0 : values.size();

        if (defSize != valSize) {
            UIntegerList list = new UIntegerList();
            list.add(new UInteger(Math.min(defSize, valSize)));
            return list;
        }
        if (defSize == 0) {
            return null;
        }

        List<Integer> invalid = new ArrayList<>();
        for (int i = 0; i < defSize; i++) {
            ArgumentDefinition argDef = argDefs.get(i);
            NullableAttribute nv = values.get(i);
            Object v = (nv != null) ? nv.getValue() : null;
            if (v == null) {
                continue;
            }
            if (v instanceof Union) {
                AttributeType expectedType = argDef.getType();
                if (expectedType != null && !isUnionCompatibleWithType((Union) v, expectedType)) {
                    invalid.add(i);
                }
            }
            else {
                if (!isAttributeValue(v)) {
                    invalid.add(i);
                }
            }
        }
        if (invalid.isEmpty()) {
            return null;
        }
        UIntegerList list = new UIntegerList();
        for (int idx : invalid) {
            list.add(new UInteger(idx));
        }
        return list;
    }

    private boolean isAttributeValue(Object value) {
        return value != null;
    }

    /**
     * Reject e.g. string when definition says TIME.
     */
    private boolean isUnionCompatibleWithType(Union value, AttributeType expectedType) {
        if (value == null || expectedType == null) {
            return true;
        }
        if (expectedType.equals(AttributeType.TIME) && value.isStringAttribute()) {
            return false;
        }
        return true;
    }

    /**
     * Runs backend in a thread and pushes Start / Progress / Complete when requested.
     */
    private void runExecutionAsync(final ActionExecutionRequest executionRequest, final ActionDefinition definition) {
        final java.lang.Long requestId = executionRequest.getRequestId();
        final boolean stageStartedRequired = executionRequest.getStageStartedRequired();
        final boolean stageProgressRequired = executionRequest.getStageProgressRequired();
        final boolean stageCompletedRequired = executionRequest.getStageCompletedRequired();
        final int progressStepCount = definition.getProgressStepCount() != null
                ? definition.getProgressStepCount().getValue() : 0;

        executionStageCounters.put(requestId, new AtomicInteger(0));

        Thread runner = new Thread(() -> {
            try {
                if (stageStartedRequired) {
                    publishStart(requestId, definition, true);
                }
                ActionBackend.ExecuteListener listener = () -> {
                    
                    if (!stageProgressRequired || progressStepCount <= 0) {
                        return;
                    }
                    int stage = executionStageCounters.get(requestId).incrementAndGet();
                    if (stage <= progressStepCount) {
                        publishInProgress(requestId, definition, progressStepCount, stage, true);
                    }  
                };
                boolean success = backend.execute(executionRequest, definition, listener);
                
                if (!success && stageProgressRequired && progressStepCount > 0) {
                    int currentStage = executionStageCounters.get(requestId).get();
                    if (currentStage > 0 && currentStage < progressStepCount) {
                        publishInProgress(requestId, definition, progressStepCount, currentStage + 1, false);
                    }
                }  
                publishComplete(requestId, definition, stageCompletedRequired, success, null);
            } catch (Throwable t) {
                LOGGER.log(Level.WARNING, "Action execution failed for requestId=" + requestId, t);
                publishComplete(requestId, definition, stageCompletedRequired, false, t.getMessage());
            } finally {
                knownRequestIds.remove(requestId);
                executionStageCounters.remove(requestId);
            }
        }, "Action-" + requestId);
        runner.setDaemon(true);
        runner.start();
    }

    private void publishStart(java.lang.Long requestId, ActionDefinition definition, boolean success) {
        publishEvent(requestId, definition, new ActionStartEvent(success));
    }

    private void publishInProgress(java.lang.Long requestId, ActionDefinition definition,
                                  int stageCount, int executionStage, boolean success) {
        publishEvent(requestId, definition,
                new ActionInProgressEvent(success, new UInteger(stageCount), new UInteger(executionStage)));
    }

    private void publishComplete(java.lang.Long requestId, ActionDefinition definition,
                                boolean stageCompletedRequired, boolean success, String comment) {
        if (stageCompletedRequired) {
            publishEvent(requestId, definition, new ActionCompleteEvent(success));
        }
    }

    private void publishEvent(java.lang.Long requestId, ActionDefinition definition, ActionEvent progressEvent) {
        try {
            IdentifierList domain = definition.getObjectIdentity().getDomain();
            Identifier actionKey = definition.getObjectIdentity().getKey();
            UOctet actionCategory = new UOctet(definition.getCategory().getValue());

            NullableAttributeList keyValues = new NullableAttributeList();
            keyValues.add(new NullableAttribute(new Union(requestId)));
            keyValues.add(new NullableAttribute(actionKey));
            keyValues.add(new NullableAttribute(actionCategory));

            Identifier source = new Identifier(connection.getConnectionDetails().getProviderURI().getValue());
            UpdateHeader updateHeader = new UpdateHeader(source, domain, keyValues);
            monitorExecutionPublisher.publish(updateHeader, progressEvent);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Exception during monitorExecution publish: " + ex.getMessage(), ex);
        }
    }
}