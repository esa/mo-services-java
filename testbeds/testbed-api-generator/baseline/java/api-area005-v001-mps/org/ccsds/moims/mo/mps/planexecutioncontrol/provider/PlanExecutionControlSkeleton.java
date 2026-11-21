package org.ccsds.moims.mo.mps.planexecutioncontrol.provider;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;

/**
 * The skeleton interface for the PlanExecutionControl service.
 */
public interface PlanExecutionControlSkeleton {

    /**
     * Creates a publisher object using the current registered provider set for
     * the PubSub operation monitorPlanExecution.
     * 
     * @param domain The domain used for publishing
     * @param networkZone ~The network zone used for publishing
     * @param sessionType The session used for publishing
     * @param sessionName The session name used for publishing
     * @param qos The QoS used for publishing
     * @param qosProps The QoS properties used for publishing
     * @param priority The priority used for publishing
     * @return The new publisher object.
     * @throws MALException if a problem is detected during creation of the publisher
     */
    MonitorPlanExecutionPublisher createMonitorPlanExecutionPublisher(IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority) throws MALException;
    /**
     * Creates a publisher object using the current registered provider set for
     * the PubSub operation monitorPlanExecutionDetail.
     * 
     * @param domain The domain used for publishing
     * @param networkZone ~The network zone used for publishing
     * @param sessionType The session used for publishing
     * @param sessionName The session name used for publishing
     * @param qos The QoS used for publishing
     * @param qosProps The QoS properties used for publishing
     * @param priority The priority used for publishing
     * @return The new publisher object.
     * @throws MALException if a problem is detected during creation of the publisher
     */
    MonitorPlanExecutionDetailPublisher createMonitorPlanExecutionDetailPublisher(IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority) throws MALException;
    /**
     * Creates a publisher object using the current registered provider set for
     * the PubSub operation monitorSubPlanExecution.
     * 
     * @param domain The domain used for publishing
     * @param networkZone ~The network zone used for publishing
     * @param sessionType The session used for publishing
     * @param sessionName The session name used for publishing
     * @param qos The QoS used for publishing
     * @param qosProps The QoS properties used for publishing
     * @param priority The priority used for publishing
     * @return The new publisher object.
     * @throws MALException if a problem is detected during creation of the publisher
     */
    MonitorSubPlanExecutionPublisher createMonitorSubPlanExecutionPublisher(IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority) throws MALException;
}
