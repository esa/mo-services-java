/** *****************************************************************************
 * Copyright or © or Copr. CNES
 *
 * This software is a computer program whose purpose is to provide a
 * framework for the CCSDS Mission Operations services.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL-C
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 ****************************************************************************** */
package org.ccsds.moims.mo.malspp.test.util;

import java.util.Arrays;

import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;

public class SecondaryHeader {

    private int malsppVersion;

    private int sduType;

    private int area;

    private int service;

    private int operation;

    private int areaVersion;

    private int isError;

    private int qos;

    private int session;

    private int secondaryApid;

    private int secondaryApidQualifier;

    private long transactionId;

    private byte sourceIdFlag;

    private byte destinationIdFlag;

    private byte priorityFlag;

    private byte timestampFlag;

    private byte networkZoneFlag;

    private byte sessionNameFlag;

    private byte domainFlag;

    private byte authenticationIdFlag;

    private int sourceId;

    private int destinationId;

    private long segmentCounter;

    private long priority;

    private long timestamp;

    private Identifier networkZone;

    private Identifier sessionName;

    private IdentifierList domain;

    private byte[] authenticationId;

    public int getMalsppVersion() {
        return malsppVersion;
    }

    public void setMalsppVersion(int malsppVersion) {
        this.malsppVersion = malsppVersion;
    }

    /**
     * @return the sduType
     */
    public int getSduType() {
        return sduType;
    }

    /**
     * @param sduType the sduType to set
     */
    public void setSduType(int sduType) {
        this.sduType = sduType;
    }

    /**
     * @return the area
     */
    public int getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(int area) {
        this.area = area;
    }

    /**
     * @return the areaVersion
     */
    public int getAreaVersion() {
        return areaVersion;
    }

    /**
     * @param serviceVersion the areaVersion to set
     */
    public void setAreaVersion(int areaVersion) {
        this.areaVersion = areaVersion;
    }

    /**
     * @return the service
     */
    public int getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(int service) {
        this.service = service;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public int getSecondaryApid() {
        return secondaryApid;
    }

    public void setSecondaryApid(int secondaryApid) {
        this.secondaryApid = secondaryApid;
    }

    public int getSecondaryApidQualifier() {
        return secondaryApidQualifier;
    }

    public void setSecondaryApidQualifier(int secondaryApidQualifier) {
        this.secondaryApidQualifier = secondaryApidQualifier;
    }

    public byte getSourceIdFlag() {
        return sourceIdFlag;
    }

    public void setSourceIdFlag(byte sourceIdFlag) {
        this.sourceIdFlag = sourceIdFlag;
    }

    public byte getDestinationIdFlag() {
        return destinationIdFlag;
    }

    public void setDestinationIdFlag(byte destinationIdFlag) {
        this.destinationIdFlag = destinationIdFlag;
    }

    public byte getPriorityFlag() {
        return priorityFlag;
    }

    public void setPriorityFlag(byte priorityFlag) {
        this.priorityFlag = priorityFlag;
    }

    public byte getTimestampFlag() {
        return timestampFlag;
    }

    public void setTimestampFlag(byte timestampFlag) {
        this.timestampFlag = timestampFlag;
    }

    public byte getNetworkZoneFlag() {
        return networkZoneFlag;
    }

    public void setNetworkZoneFlag(byte networkZoneFlag) {
        this.networkZoneFlag = networkZoneFlag;
    }

    public byte getSessionNameFlag() {
        return sessionNameFlag;
    }

    public void setSessionNameFlag(byte sessionNameFlag) {
        this.sessionNameFlag = sessionNameFlag;
    }

    public byte getDomainFlag() {
        return domainFlag;
    }

    public void setDomainFlag(byte domainFlag) {
        this.domainFlag = domainFlag;
    }

    public byte getAuthenticationIdFlag() {
        return authenticationIdFlag;
    }

    public void setAuthenticationIdFlag(byte authenticationIdFlag) {
        this.authenticationIdFlag = authenticationIdFlag;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    /**
     * @return the operation
     */
    public int getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(int operation) {
        this.operation = operation;
    }

    /**
     * @return the transactionId
     */
    public long getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public int getIsError() {
        return isError;
    }

    public void setIsError(int isError) {
        this.isError = isError;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    public Identifier getNetworkZone() {
        return networkZone;
    }

    public void setNetworkZone(Identifier networkZone) {
        this.networkZone = networkZone;
    }

    public Identifier getSessionName() {
        return sessionName;
    }

    public void setSessionName(Identifier sessionName) {
        this.sessionName = sessionName;
    }

    public IdentifierList getDomain() {
        return domain;
    }

    public void setDomain(IdentifierList domain) {
        this.domain = domain;
    }

    public byte[] getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(byte[] authenticationId) {
        this.authenticationId = authenticationId;
    }

    public long getSegmentCounter() {
        return segmentCounter;
    }

    public void setSegmentCounter(long segmentCounter) {
        this.segmentCounter = segmentCounter;
    }

    @Override
    public String toString() {
        return "MALSPPSecondaryHeader [malsppVersion=" + malsppVersion
                + ", sduType=" + sduType + ", area=" + area + ", service=" + service
                + ", operation=" + operation + ", areaVersion=" + areaVersion
                + ", isError=" + isError + ", qos=" + qos + ", session=" + session
                + ", secondaryApid=" + secondaryApid + ", secondaryApidQualifier="
                + secondaryApidQualifier + ", transactionId=" + transactionId
                + ", sourceIdFlag=" + sourceIdFlag + ", destinationIdFlag="
                + destinationIdFlag + ", priorityFlag=" + priorityFlag
                + ", timestampFlag=" + timestampFlag + ", networkZoneFlag="
                + networkZoneFlag + ", sessionNameFlag=" + sessionNameFlag
                + ", domainFlag=" + domainFlag + ", authenticationIdFlag="
                + authenticationIdFlag + ", sourceId=" + sourceId + ", destinationId="
                + destinationId + ", segmentCounter=" + segmentCounter + ", priority="
                + priority + ", timestamp=" + timestamp + ", networkZone="
                + networkZone + ", sessionName=" + sessionName + ", domain=" + domain
                + ", authenticationId=" + Arrays.toString(authenticationId) + "]";
    }

}
