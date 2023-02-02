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

import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class SecondaryHeaderReader {

    private BufferReader bufferReader;

    private byte byteToParse;

    private SecondaryHeader secondaryHeader;

    public SecondaryHeaderReader(BufferReader bufferReader) {
        this(bufferReader, new SecondaryHeader());
    }

    public SecondaryHeaderReader(BufferReader bufferReader,
            SecondaryHeader secondaryHeader) {
        super();
        this.bufferReader = bufferReader;
        this.secondaryHeader = secondaryHeader;
    }

    public SecondaryHeader getSecondaryHeader() {
        return secondaryHeader;
    }

    public void readAll(int sequenceFlag) throws Exception {
        readVersion();
        readSduType();
        readArea();
        readService();
        readOperation();
        readAreaVersion();
        readIsError();
        readQos();
        readSession();
        readSecondaryApid();
        readSecondaryApidQualifier();
        readTransactionId();

        byte sourceIdFlag = readSourceIdFlag();
        byte destinationIdFlag = readDestinationIdFlag();
        byte priorityFlag = readPriorityFlag();
        byte timestampFlag = readTimestampFlag();
        byte networkZoneFlag = readNetworkZoneFlag();
        byte sessionNameFlag = readSessionNameFlag();
        byte domainFlag = readDomainFlag();
        byte authenticationIdFlag = readAuthenticationIdFlag();

        if (sourceIdFlag > 0) {
            readSourceId();
        } else {
            secondaryHeader.setSourceId(-1);
        }

        if (destinationIdFlag > 0) {
            readDestinationId();
        } else {
            secondaryHeader.setDestinationId(-1);
        }

        if (sequenceFlag != 3) {
            readSegmentCounter();
        }

        if (priorityFlag > 0) {
            readPriority();
        }

        if (timestampFlag > 0) {
            readTimestamp();
        }

        if (networkZoneFlag > 0) {
            readNetworkZone();
        }

        if (sessionNameFlag > 0) {
            readSessionName();
        }

        if (domainFlag > 0) {
            readDomain();
        }

        if (authenticationIdFlag > 0) {
            readAuthenticationId();
        }
    }

    public int readVersion() {
        byteToParse = bufferReader.read();
        int malsppVersion = (byteToParse >>> 5) & 0x07;
        secondaryHeader.setMalsppVersion(malsppVersion);

        LoggingBase.logMessage("version = " + malsppVersion);

        return malsppVersion;
    }

    public int readSduType() {
        int sduType = byteToParse & 0x1F;
        secondaryHeader.setSduType(sduType);

        LoggingBase.logMessage("sdu type = " + sduType);

        return sduType;
    }

    public int readArea() {
        int area = bufferReader.read16() & 0xFFFF;
        secondaryHeader.setArea(area);

        LoggingBase.logMessage("area = " + area);

        return area;
    }

    public int readService() {
        int service = bufferReader.read16() & 0xFFFF;
        secondaryHeader.setService(service);

        LoggingBase.logMessage("service = " + service);

        return service;
    }

    public int readOperation() {
        int operation = bufferReader.read16() & 0xFFFF;
        secondaryHeader.setOperation(operation);

        LoggingBase.logMessage("operation = " + operation);

        return operation;
    }

    public int readAreaVersion() {
        int areaVersion = bufferReader.read();
        secondaryHeader.setAreaVersion(areaVersion);

        LoggingBase.logMessage("areaVersion = " + areaVersion);

        return areaVersion;
    }

    public int readIsError() {
        byteToParse = bufferReader.read();
        int isError = (byteToParse >>> 7) & 0x01;
        secondaryHeader.setIsError(isError);

        LoggingBase.logMessage("isError = " + isError);

        return isError;
    }

    public int readQos() {
        int qos = (byteToParse >>> 5) & 0x03;
        secondaryHeader.setQos(qos);

        LoggingBase.logMessage("qos = " + qos);

        return qos;
    }

    public int readSession() {
        int session = (byteToParse >>> 3) & 0x03;
        secondaryHeader.setSession(session);

        LoggingBase.logMessage("session = " + session);

        return session;
    }

    public int readSecondaryApid() {
        int secondaryApid = ((byteToParse >>> 0) & 0x07) << 8 | bufferReader.read();
        secondaryHeader.setSecondaryApid(secondaryApid);

        LoggingBase.logMessage("secondaryApid = " + secondaryApid);

        return secondaryApid;
    }

    public int readSecondaryApidQualifier() {
        int secondaryApidQualifier = bufferReader.read16();
        secondaryHeader.setSecondaryApidQualifier(secondaryApidQualifier);

        LoggingBase.logMessage("secondaryApidQualifier = " + secondaryApidQualifier);

        return secondaryApidQualifier;
    }

    public long readTransactionId() {
        long transactionId = bufferReader.read64();
        secondaryHeader.setTransactionId(transactionId);

        LoggingBase.logMessage("transactionId = " + transactionId);

        return transactionId;
    }

    public byte readSourceIdFlag() {
        byteToParse = bufferReader.read();
        byte sourceIdFlag = (byte) ((byteToParse >>> 7) & 0x01);
        secondaryHeader.setSourceIdFlag(sourceIdFlag);

        LoggingBase.logMessage("sourceIdFlag = " + sourceIdFlag);

        return sourceIdFlag;
    }

    public byte readDestinationIdFlag() {
        byte destinationIdFlag = (byte) ((byteToParse >>> 6) & 0x01);
        secondaryHeader.setDestinationIdFlag(destinationIdFlag);

        LoggingBase.logMessage("destinationIdFlag = " + destinationIdFlag);

        return destinationIdFlag;
    }

    public byte readPriorityFlag() {
        byte priorityFlag = (byte) ((byteToParse >>> 5) & 0x01);
        secondaryHeader.setPriorityFlag(priorityFlag);

        LoggingBase.logMessage("priorityFlag = " + priorityFlag);

        return priorityFlag;
    }

    public byte readTimestampFlag() {
        byte timestampFlag = (byte) ((byteToParse >>> 4) & 0x01);
        secondaryHeader.setTimestampFlag(timestampFlag);

        LoggingBase.logMessage("timestampFlag = " + timestampFlag);

        return timestampFlag;
    }

    public byte readNetworkZoneFlag() {
        byte networkZoneFlag = (byte) ((byteToParse >>> 3) & 0x01);
        secondaryHeader.setNetworkZoneFlag(networkZoneFlag);

        LoggingBase.logMessage("networkZoneFlag = " + networkZoneFlag);

        return networkZoneFlag;
    }

    public byte readSessionNameFlag() {
        byte sessionNameFlag = (byte) ((byteToParse >>> 2) & 0x01);
        secondaryHeader.setSessionNameFlag(sessionNameFlag);

        LoggingBase.logMessage("sessionNameFlag = " + sessionNameFlag);

        return sessionNameFlag;
    }

    public byte readDomainFlag() {
        byte domainFlag = (byte) ((byteToParse >>> 1) & 0x01);
        secondaryHeader.setDomainFlag(domainFlag);

        LoggingBase.logMessage("domainFlag = " + domainFlag);

        return domainFlag;
    }

    public byte readAuthenticationIdFlag() {
        byte authenticationIdFlag = (byte) ((byteToParse >>> 0) & 0x01);
        secondaryHeader.setAuthenticationIdFlag(authenticationIdFlag);

        LoggingBase.logMessage("authenticationIdFlag = " + authenticationIdFlag);

        return authenticationIdFlag;
    }

    public int readSourceId() {
        byte sourceIdFlag = secondaryHeader.getSourceIdFlag();
        if (sourceIdFlag > 0) {
            int sourceId = bufferReader.read() & 0xFF;
            secondaryHeader.setSourceId(sourceId);

            LoggingBase.logMessage("sourceId = " + sourceId);

            return sourceId;
        } else {
            return -1;
        }
    }

    public int readDestinationId() {
        byte destinationIdFlag = secondaryHeader.getDestinationIdFlag();
        if (destinationIdFlag > 0) {
            int destinationId = bufferReader.read() & 0xFF;
            secondaryHeader.setDestinationId(destinationId);

            LoggingBase.logMessage("destinationId = " + destinationId);

            return destinationId;
        } else {
            return -1;
        }
    }

    public long readSegmentCounter() {
        long segmentCounter = bufferReader.read32() & 0xFFFFFFFFL;
        secondaryHeader.setSegmentCounter(segmentCounter);

        LoggingBase.logMessage("segmentCounter = " + segmentCounter);

        return segmentCounter;
    }

    public long readPriority() throws Exception {
        long priority = bufferReader.readUInteger().getValue();
        secondaryHeader.setPriority(priority);

        LoggingBase.logMessage("priority = " + priority);

        return priority;
    }

    public long readTimestamp() throws Exception {
        long timestamp = bufferReader.readTimestamp();
        secondaryHeader.setTimestamp(timestamp);

        LoggingBase.logMessage("timestamp = " + timestamp);

        return timestamp;
    }

    public String readNetworkZone() throws Exception {
        String networkZone = bufferReader.readString();
        secondaryHeader.setNetworkZone(new Identifier(networkZone));

        LoggingBase.logMessage("networkZone = " + networkZone);

        return networkZone;
    }

    public String readSessionName() throws Exception {
        String sessionName = bufferReader.readString();
        secondaryHeader.setSessionName(new Identifier(sessionName));

        LoggingBase.logMessage("sessionName = " + sessionName);

        return sessionName;
    }

    public IdentifierList readDomain() throws Exception {
        IdentifierList domain = new IdentifierList();
        long size = bufferReader.readUInteger().getValue();

        LoggingBase.logMessage("domain size = " + size);

        for (long i = 0; i < size; i++) {
            domain.add(bufferReader.readNullableIdentifier());
        }
        secondaryHeader.setDomain(domain);

        LoggingBase.logMessage("domain = " + domain);

        return domain;
    }

    public byte[] readAuthenticationId() throws Exception {
        byte[] authenticationId = bufferReader.readBytes();
        secondaryHeader.setAuthenticationId(authenticationId);

        LoggingBase.logMessage("authenticationId = " + authenticationId);

        return authenticationId;
    }

}
