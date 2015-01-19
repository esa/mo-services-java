/*******************************************************************************
 * Copyright or � or Copr. CNES
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
 *******************************************************************************/
package org.ccsds.moims.mo.malspp.test.util;

import java.util.HashMap;

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.malspp.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.malspp.test.suite.TestServiceProvider;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.orekit.errors.OrekitException;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScale;
import org.orekit.time.TimeScalesFactory;

public class MappingConfigurationRegistry {
  
  private static MappingConfigurationRegistry singleton = new MappingConfigurationRegistry();
  
  public static MappingConfigurationRegistry getSingleton() {
    return singleton;
  }
  
  private HashMap<QualifiedApid, MappingConfiguration> mappingConfigurations;
  
  private MappingConfigurationRegistry() {
    super();
    mappingConfigurations = new HashMap<QualifiedApid, MappingConfiguration>();
    
    CUCTimeCode timeCode = new CUCTimeCode(TimeCode.EPOCH_TAI, TimeCode.UNIT_SECOND, 4, 3);
    CUCTimeCode fineTimeCode = new CUCTimeCode(new AbsoluteDate("2013-01-01T00:00:00.000",
        TimeScalesFactory.getTAI()), TimeCode.UNIT_SECOND, 4, 5);
    CUCTimeCode durationCode = new CUCTimeCode(null, TimeCode.UNIT_SECOND, 4, 0);
    
    TimeScale utcTimeScale;
    try {
      utcTimeScale = TimeScalesFactory.getUTC();
    } catch (OrekitException exc) {
      LoggingBase.logMessage("SpacePacketCheck error: " + exc);
      throw new RuntimeException(exc);
    }
    
    // CDS format (instead of CUC), agency epoch (instead of CCSDS), UTC time scale (instead of TAI)
    CDSTimeCode alternateTimeCode = new CDSTimeCode(new AbsoluteDate(
        "1958-01-01T00:00:00.000", utcTimeScale),
        TimeCode.UNIT_SECOND, 3, 4);
    
    // CDS format (instead of CUC), alternate agency epoch, UTC time scale (instead of TAI)
    CDSTimeCode alternateFineTimeCode = new CDSTimeCode(new AbsoluteDate(
        "2012-01-01T00:00:00.000", utcTimeScale),
        TimeCode.UNIT_SECOND, 3, 4);
    
    // Alternate time code format with a 3-bit fractional time
    CUCTimeCode alternateDurationCode = new CUCTimeCode(null,
        TimeCode.UNIT_SECOND, 4, 3);
    
    UInteger priority = new UInteger(0xFFFFFFFFL);
    Blob authenticationId = new Blob(new byte[] {0x0A, 0x0B, 0x0C});
    IdentifierList domain = new IdentifierList();
    domain.add(new Identifier("malspp"));
    domain.add(new Identifier("test"));
    domain.add(new Identifier("domain"));
    Identifier networkZone = new Identifier("malsppTestNw");
    Identifier sessionName = new Identifier("malsppTestSession");
    
    boolean varintSupported = true;
    
    MappingConfiguration defaultMappingConfiguration = new MappingConfiguration(
        priority, networkZone, sessionName, domain, authenticationId,
        varintSupported, timeCode, fineTimeCode, durationCode);
    
    MappingConfiguration nullMappingConfiguration = new MappingConfiguration(
        null, null, null, null, null,
        varintSupported, timeCode, fineTimeCode, alternateDurationCode);
    
    MappingConfiguration alternateMappingConfiguration = new MappingConfiguration(
        priority, networkZone, sessionName, domain, authenticationId,
        false, alternateTimeCode, alternateFineTimeCode, durationCode);
    
    mappingConfigurations.put(new QualifiedApid(
        LocalMALInstance.TC_TC_LOCAL_APID_QUALIFIER,
        LocalMALInstance.TC_TC_LOCAL_APID), nullMappingConfiguration);
    mappingConfigurations.put(new QualifiedApid(
        LocalMALInstance.TC_TM_LOCAL_APID_QUALIFIER,
        LocalMALInstance.TC_TM_LOCAL_APID), defaultMappingConfiguration);
    mappingConfigurations.put(new QualifiedApid(
        LocalMALInstance.TM_TC_LOCAL_APID_QUALIFIER,
        LocalMALInstance.TM_TC_LOCAL_APID), defaultMappingConfiguration);
    mappingConfigurations.put(new QualifiedApid(
        LocalMALInstance.TM_TM_LOCAL_APID_QUALIFIER,
        LocalMALInstance.TM_TM_LOCAL_APID), defaultMappingConfiguration);
    mappingConfigurations.put(new QualifiedApid(
        TestServiceProvider.TC_REMOTE_APID_QUALIFIER,
        TestServiceProvider.TC_REMOTE_APID), defaultMappingConfiguration);
    mappingConfigurations.put(new QualifiedApid(
        TestServiceProvider.TM_REMOTE_APID_QUALIFIER,
        TestServiceProvider.TM_REMOTE_APID), alternateMappingConfiguration);
    mappingConfigurations.put(new QualifiedApid(
        TestServiceProvider.SEGMENTATION_COUNTER_SELECT_REMOTE_APID_QUALIFIER,
        TestServiceProvider.SEGMENTATION_COUNTER_SELECT_REMOTE_APID), defaultMappingConfiguration);
  }
  
  public MappingConfiguration get(QualifiedApid apid) {
    return mappingConfigurations.get(apid);
  }

}
