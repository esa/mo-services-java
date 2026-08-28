package org.ccsds.moims.mo.mc.statistic.provider;

/**
 * The skeleton interface for the Statistic service.
 */
public interface StatisticSkeleton {

    /**
     * Creates a publisher object using the current registered provider set for
     * the PubSub operation monitorStatistics.
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
    org.ccsds.moims.mo.mc.statistic.provider.MonitorStatisticsPublisher createMonitorStatisticsPublisher(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.QoSLevel qos,
            java.util.Map qosProps,
            org.ccsds.moims.mo.mal.structures.UInteger priority) throws org.ccsds.moims.mo.mal.MALException;
}
