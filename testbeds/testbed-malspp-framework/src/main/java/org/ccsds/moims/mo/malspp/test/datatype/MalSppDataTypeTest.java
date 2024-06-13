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
package org.ccsds.moims.mo.malspp.test.datatype;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.CompositeList;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.test.datatype.DataTypeScenario;
import org.ccsds.moims.mo.mal.test.datatype.TestData;
import org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestStub;
import org.ccsds.moims.mo.malprototype.structures.TestPublish;
import org.ccsds.moims.mo.malprototype.structures.TestPublishList;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malspp.test.sppinterceptor.SPPInterceptor;
import org.ccsds.moims.mo.malspp.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.malspp.test.suite.TestServiceProvider;
import org.ccsds.moims.mo.malspp.test.util.BufferReader;
import org.ccsds.moims.mo.malspp.test.util.MappingConfiguration;
import org.ccsds.moims.mo.malspp.test.util.MappingConfigurationRegistry;
import org.ccsds.moims.mo.malspp.test.util.QualifiedApid;
import org.ccsds.moims.mo.malspp.test.util.SecondaryHeader;
import org.ccsds.moims.mo.malspp.test.util.TestHelper;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.spp.SpacePacket;
import org.ccsds.moims.mo.testbed.util.spp.SpacePacketHeader;
import org.objectweb.util.monolog.api.BasicLevel;
import org.objectweb.util.monolog.api.Logger;

public class MalSppDataTypeTest extends DataTypeScenario {

    public final static Logger logger = fr.dyade.aaa.common.Debug
            .getLogger(MalSppDataTypeTest.class.getName());

    protected SpacePacket currentPacket;

    protected SpacePacketHeader primaryHeader;

    protected SecondaryHeader secondaryHeader;

    protected int firstIndexOfMalBody;

    protected byte[] packetBody;

    private BufferReader bufferReader;

    private MappingConfiguration mappingConf;

    private MappingConfiguration defaultMappingConf;

    private MappingConfiguration alternateMappingConf;

    private boolean useDefaultConfiguration;

    public MalSppDataTypeTest() {
        // Mapping configuration parameters 
        // for default DataTest service provider
        // available at APID 248:2
        /*
    timeCode = new CUCTimeCode(TimeCode.EPOCH_TAI, TimeCode.UNIT_SECOND, 4, 3);
    fineTimeCode = new CUCTimeCode(new AbsoluteDate("2013-01-01T00:00:00.000",
        TimeScalesFactory.getTAI()), TimeCode.UNIT_SECOND, 4, 5);
    durationCode = new CUCTimeCode(null, TimeCode.UNIT_SECOND, 4, 0);
         */
        defaultMappingConf = MappingConfigurationRegistry.getSingleton().get(
                new QualifiedApid(TestServiceProvider.TC_REMOTE_APID_QUALIFIER,
                        TestServiceProvider.TC_REMOTE_APID));
        alternateMappingConf = MappingConfigurationRegistry.getSingleton().get(
                new QualifiedApid(TestServiceProvider.TM_REMOTE_APID_QUALIFIER,
                        TestServiceProvider.TM_REMOTE_APID));
        useDefaultConfiguration(true);
    }

    public boolean useDefaultConfiguration(boolean useDefaultConfiguration) {
        this.useDefaultConfiguration = useDefaultConfiguration;
        if (useDefaultConfiguration) {
            mappingConf = defaultMappingConf;
        } else {
            mappingConf = alternateMappingConf;
        }
        return true;
    }

    protected DataTestStub getDataTestStub() throws MALException {
        if (useDefaultConfiguration) {
            return super.getDataTestStub();
        } else {
            return LocalMALInstance.instance().alternateDataTestStub();
        }
    }

    public String explicitDurationTypeWorks() throws MALInteractionException, MALException {
        if (logger.isLoggable(BasicLevel.DEBUG)) {
            logger.log(BasicLevel.DEBUG, "explicitDurationTypeWorks()");
        }
        try {
            return super.explicitDurationTypeWorks();
        } catch (Throwable error) {
            if (logger.isLoggable(BasicLevel.DEBUG)) {
                logger.log(BasicLevel.DEBUG, "", error);
            }
            error.printStackTrace();
            return null;
        }
    }

    public boolean selectReceivedPacketAt(int index) {
        if (logger.isLoggable(BasicLevel.DEBUG)) {
            logger.log(BasicLevel.DEBUG, "selectReceivedPacketAt(" + index + ')');
        }
        LoggingBase.logMessage("selectReceivedPacketAt(" + index + ')');
        LoggingBase.logMessage("ReceivedPacketCount=" + SPPInterceptor.instance().getReceivedPacketCount());
        SpacePacket packet = SPPInterceptor.instance().getReceivedPacket(index);
        return selectPacket(packet);
    }

    public boolean selectSentPacketAt(int index) {
        if (logger.isLoggable(BasicLevel.DEBUG)) {
            logger.log(BasicLevel.DEBUG, "selectSentPacketAt(" + index + ')');
        }
        LoggingBase.logMessage("selectSentPacketAt(" + index + ')');
        LoggingBase.logMessage("SentPacketCount=" + SPPInterceptor.instance().getSentPacketCount());
        SpacePacket packet = SPPInterceptor.instance().getSentPacket(index);
        return selectPacket(packet);
    }

    private boolean selectPacket(SpacePacket packet) {
        if (logger.isLoggable(BasicLevel.DEBUG)) {
            logger.log(BasicLevel.DEBUG, "selectSentPacketAt(" + packet + ')');
        }
        currentPacket = packet;
        packetBody = packet.getBody();
        LoggingBase.logMessage("packetBody.length=" + packetBody.length);
        primaryHeader = packet.getHeader();
        LoggingBase.logMessage("primaryHeader=" + primaryHeader);
        secondaryHeader = new SecondaryHeader();
        try {
            LoggingBase.logMessage("fineTimeCode=" + mappingConf.getFineTimeCode());
            boolean useVarInt = useDefaultConfiguration ? defaultMappingConf.isVarintSupported() : alternateMappingConf.isVarintSupported();
            LoggingBase.logMessage("isVarintSupported=" + useVarInt);
            bufferReader = new BufferReader(packetBody, packet.getOffset(),
                    useVarInt,
                    mappingConf.getTimeCode(), mappingConf.getFineTimeCode(),
                    mappingConf.getDurationCode());
            firstIndexOfMalBody = TestHelper.decodeSecondaryHeader(secondaryHeader, bufferReader,
                    packet.getHeader().getSequenceFlags());
            LoggingBase.logMessage("secondaryHeader=" + secondaryHeader);
        } catch (Exception e) {
            if (logger.isLoggable(BasicLevel.WARN)) {
                logger.log(BasicLevel.WARN, "", e);
            }
            LoggingBase.logMessage(e.toString());
            return false;
        }
        return true;
    }

    public boolean malMessageBodyIsEmpty() {
        return firstIndexOfMalBody == currentPacket.getOffset() + currentPacket.getLength();
    }

    public int presenceFlagIs() {
        return bufferReader.read();
    }

    public long areaNumberIs() {
        return bufferReader.read16();
    }

    public long serviceNumberIs() {
        return bufferReader.read16();
    }

    public int versionIs() {
        return bufferReader.read();
    }

    public int typeNumberIs() {
        return bufferReader.read24();
    }

    public int listSizeIs() throws Exception {
        return (int) bufferReader.readUInteger().getValue();
    }

    public String stringFieldIs() throws Exception {
        String s = bufferReader.readString();
        LoggingBase.logMessage("String=" + s);
        return s;
    }

    public int integerFieldIs() throws Exception {
        return bufferReader.readInteger();
    }

    public long longFieldIs() throws Exception {
        return bufferReader.readLong();
    }

    public long uintegerFieldIs() {
        return bufferReader.readUInteger().getValue();
    }

    public boolean booleanFieldIs() throws Exception {
        return bufferReader.readBoolean();
    }

    public int enumeratedIs() {
        return bufferReader.read();
    }

    public long mediumEnumeratedIs() {
        return bufferReader.readUShort().getValue();
    }

    public long largeEnumeratedIs() {
        return bufferReader.readUInteger().getValue();
    }

    public int attributeTagIs() throws Exception {
        return bufferReader.readUnsignedVarInt();
    }

    public boolean checkDuration() {
        try {
            return checkData(bufferReader.readDuration(), TestData.testDuration);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkIdentifier() {
        try {
            return checkData(bufferReader.readIdentifier(), TestData.testIdentifier);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkUri() {
        try {
            return checkData(bufferReader.readUri(), TestData.testURI);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkBlob() {
        try {
            return checkData(bufferReader.readBlob(), TestData.testBlob);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkBoolean() {
        try {
            return checkData(bufferReader.readBoolean(), TestData.testBoolean);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean booleanIs() {
        try {
            return bufferReader.readBoolean();
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkOctet() {
        try {
            return checkData(bufferReader.readOctet(), TestData.testOctet);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkDouble() {
        try {
            return checkData(bufferReader.readDouble(), TestData.testDouble);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkFloat() {
        try {
            return checkData(bufferReader.readFloat(), TestData.testFloat);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkInteger() {
        try {
            return checkData(bufferReader.readInteger(), TestData.testInteger);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkLong() {
        try {
            return checkData(bufferReader.readLong(), TestData.testLong);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkShort() {
        try {
            return checkData(bufferReader.readShort(), TestData.testShort);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkString() {
        try {
            return checkData(bufferReader.readString(), TestData.testString);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkUoctet() {
        try {
            return checkData(bufferReader.readUOctet(), TestData.testUOctet);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkUinteger() {
        try {
            return checkData(bufferReader.readUInteger(), TestData.testUInteger);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkUlong() {
        try {
            return checkData(bufferReader.readULong(), TestData.testULong);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkTime() {
        try {
            return checkData(bufferReader.readTime(), TestData.testTime);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkFineTime() {
        try {
            return checkData(bufferReader.readFineTime(), TestData.testFineTime);
        } catch (Exception e) {
            return logError(e);
        }
    }

    public boolean checkUshort() {
        try {
            return checkData(bufferReader.readUShort(), TestData.testUShort);
        } catch (Exception e) {
            return logError(e);
        }
    }

    private boolean checkData(Object readData, Object expectedData) {
        boolean res = expectedData.equals(readData);
        if (!res) {
            LoggingBase.logMessage(expectedData + " != " + readData);
        }
        return res;
    }

    private boolean logError(Exception e) {
        if (logger.isLoggable(BasicLevel.WARN)) {
            logger.log(BasicLevel.WARN, "", e);
        }
        LoggingBase.logMessage(e.toString());
        return false;
    }

    public boolean resetSppInterceptor() {
        if (logger.isLoggable(BasicLevel.DEBUG)) {
            logger.log(BasicLevel.DEBUG, "resetSppInterceptor()");
        }
        LoggingBase.logMessage("SentPacketCount=" + SPPInterceptor.instance().getSentPacketCount());
        LoggingBase.logMessage("ReceivedPacketCount=" + SPPInterceptor.instance().getReceivedPacketCount());
        SPPInterceptor.instance().reset();
        // Need to reset the tables
        //TransportInterceptor.instance().resetReceiveCount(ip);
        return true;
    }

    public boolean testMalAttribute() {
        Attribute attribute = new UInteger(0xFFFFFFFFL);
        LoggingBase.logMessage("attribute=" + attribute);
        Attribute res;
        try {
            res = getDataTestStub().testMalAttribute(attribute);
        } catch (Exception exc) {
            if (logger.isLoggable(BasicLevel.WARN)) {
                logger.log(BasicLevel.WARN, "", exc);
            }
            LoggingBase.logMessage(exc.toString());
            return false;
        }
        LoggingBase.logMessage("res=" + res);
        return attribute.equals(res);
    }

    public boolean testMalComposite() {
        Composite composite = new TestPublishUpdate(null, null, null, null, null, null, null, null, null, null, null, null, null);
        Composite res;
        try {
            res = getDataTestStub().testMalComposite(composite);
        } catch (Exception exc) {
            if (logger.isLoggable(BasicLevel.WARN)) {
                logger.log(BasicLevel.WARN, "", exc);
            }
            LoggingBase.logMessage(exc.toString());
            return false;
        }
        return composite.equals(res);
    }

    public boolean testAbstractComposite() {
        TestPublish composite = new TestPublishUpdate(null, null, null, null, null, null, null, null, null, null, null, null, null);
        Composite res;
        try {
            res = getDataTestStub().testAbstractComposite(composite);
        } catch (Exception exc) {
            if (logger.isLoggable(BasicLevel.WARN)) {
                logger.log(BasicLevel.WARN, "", exc);
            }
            LoggingBase.logMessage(exc.toString());
            return false;
        }
        return composite.equals(res);
    }

    public boolean testMalAttributeList() {
        UInteger attribute = new UInteger(0xFFFFFFFFL);
        AttributeList attributeList = new AttributeList();
        attributeList.add(attribute);
        attributeList.add(attribute);
        AttributeList res;
        try {
            res = getDataTestStub().testMalAttributeList(attributeList);
        } catch (Exception exc) {
            if (logger.isLoggable(BasicLevel.WARN)) {
                logger.log(BasicLevel.WARN, "", exc);
            }
            LoggingBase.logMessage(exc.toString());
            return false;
        }
        return attributeList.equals(res);
    }

    public boolean testMalCompositeList() {
        Composite composite = new TestPublishUpdate(null, null, null, null, null, null, null, null, null, null, null, null, null);
        CompositeList compositeList = new CompositeList();
        compositeList.add(composite);
        compositeList.add(composite);
        CompositeList res;
        try {
            res = getDataTestStub().testMalCompositeList(compositeList);
        } catch (Exception exc) {
            if (logger.isLoggable(BasicLevel.WARN)) {
                logger.log(BasicLevel.WARN, "", exc);
            }
            LoggingBase.logMessage(exc.toString());
            return false;
        }
        return compositeList.equals(res);
    }

    public boolean testCompositeListSentAsMalElementList() {
        TestPublishUpdate composite = new TestPublishUpdate(null, null, null, null, null, null, null, null, null, null, null, null, null);
        CompositeList compositeList = new CompositeList();
        compositeList.add(composite);
        compositeList.add(composite);
        ElementList res;
        try {
            res = getDataTestStub().testMalElementList(compositeList);
        } catch (Exception exc) {
            if (logger.isLoggable(BasicLevel.WARN)) {
                logger.log(BasicLevel.WARN, "", exc);
            }
            LoggingBase.logMessage(exc.toString());
            return false;
        }
        return compositeList.equals(res);
    }

    public boolean testAttributeListSentAsMalElementList() {
        UInteger attribute = new UInteger(0xFFFFFFFFL);
        HeterogeneousList attributeList = new HeterogeneousList();
        attributeList.add(attribute);
        attributeList.add(attribute);
        ElementList res;
        try {
            res = getDataTestStub().testMalElementList(attributeList);
        } catch (Exception exc) {
            if (logger.isLoggable(BasicLevel.WARN)) {
                logger.log(BasicLevel.WARN, "", exc);
            }
            LoggingBase.logMessage(exc.toString());
            return false;
        }
        return attributeList.equals(res);
    }

    public boolean testAbstractCompositeList() {
        TestPublishUpdate composite = new TestPublishUpdate(null, null, null, null, null, null, null, null, null, null, null, null, null);
        TestPublishList abstractCompositeList = new TestPublishList();
        abstractCompositeList.add(composite);
        abstractCompositeList.add(composite);
        TestPublishList res;
        try {
            res = getDataTestStub().testAbstractCompositeList(abstractCompositeList);
        } catch (Exception exc) {
            if (logger.isLoggable(BasicLevel.WARN)) {
                logger.log(BasicLevel.WARN, "", exc);
            }
            LoggingBase.logMessage(exc.toString());
            return false;
        }
        return abstractCompositeList.equals(res);
    }

    public boolean testMediumEnumeration() {
        MediumEnumeration mediumEnum = new MediumEnumeration(0xFFF);
        Element res;
        try {
            res = getDataTestStub().testData(mediumEnum);
        } catch (Exception exc) {
            // An exception is expected as the MediumEnumeration is not 
            // a registered type at the provider side.
            return true;
        }
        return mediumEnum.equals(res);
    }

    public boolean testLargeEnumeration() {
        LargeEnumeration largeEnum = new LargeEnumeration(0xFFFFFF);
        Element res;
        try {
            res = getDataTestStub().testData(largeEnum);
        } catch (Exception exc) {
            // An exception is expected as the LargeEnumeration is not 
            // a registered type at the provider side.
            return true;
        }
        return largeEnum.equals(res);
    }

    public boolean testEmptyBody() {
        try {
            getDataTestStub().testEmptyBody();
        } catch (Exception exc) {
            if (logger.isLoggable(BasicLevel.WARN)) {
                logger.log(BasicLevel.WARN, "", exc);
            }
            LoggingBase.logMessage(exc.toString());
            return false;
        }
        return true;
    }

}
