package org.ccsds.moims.mo.malprototype.iptest.provider;

/**
 * The skeleton interface for the IPTest service.
 */
public interface IPTestSkeleton {

    /**
     * Creates a publisher object using the current registered provider set for
     * the PubSub operation monitor.
     * 
     * @param domain The domain used for publishing
     * @param networkZone ~The network zone used for publishing
     * @param sessionType The session used for publishing
     * @param sessionName The session name used for publishing
     * @param qos The QoS used for publishing
     * @param qosProps The QoS properties used for publishing
     * @param priority The priority used for publishing
     * @return The new publisher object.
     * @throws org.ccsds.moims.mo.mal.MALException if a problem is detected during creation of the publisher
     */
    org.ccsds.moims.mo.malprototype.iptest.provider.MonitorPublisher createMonitorPublisher(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.QoSLevel qos,
            java.util.Map qosProps,
            org.ccsds.moims.mo.mal.structures.UInteger priority) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Creates a publisher object using the current registered provider set for
     * the PubSub operation monitorMulti.
     * 
     * @param domain The domain used for publishing
     * @param networkZone ~The network zone used for publishing
     * @param sessionType The session used for publishing
     * @param sessionName The session name used for publishing
     * @param qos The QoS used for publishing
     * @param qosProps The QoS properties used for publishing
     * @param priority The priority used for publishing
     * @return The new publisher object.
     * @throws org.ccsds.moims.mo.mal.MALException if a problem is detected during creation of the publisher
     */
    org.ccsds.moims.mo.malprototype.iptest.provider.MonitorMultiPublisher createMonitorMultiPublisher(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.QoSLevel qos,
            java.util.Map qosProps,
            org.ccsds.moims.mo.mal.structures.UInteger priority) throws org.ccsds.moims.mo.mal.MALException;
}
