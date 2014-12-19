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
package org.ccsds.moims.mo.malspp.test.transport;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.malspp.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.ParseHelper;
import org.objectweb.util.monolog.api.BasicLevel;

public class MalSppTransportTest {
  
  public final static org.objectweb.util.monolog.api.Logger logger = fr.dyade.aaa.common.Debug
		  .getLogger(MalSppTransportTest.class.getName());
  
  public boolean supportsQosLevelIs(String qosLevelName, boolean res) {
    try {
      QoSLevel qosLevel = ParseHelper.parseQoSLevel(qosLevelName);
      return (res == LocalMALInstance.instance().getMalContext().getTransport("malspp").isSupportedQoSLevel(qosLevel));
    } catch (Exception exc) {
      if (logger.isLoggable(BasicLevel.WARN)) {
        logger.log(BasicLevel.WARN, "", exc);
    	}
    	LoggingBase.logMessage(exc.toString());
      return false;
    }
  }
  
  public boolean supportsInteractionTypeIs(String interactionTypeName, boolean res) {
    try {
      InteractionType interactionType = ParseHelper.parseInteractionType(interactionTypeName);
      return (res == LocalMALInstance.instance().getMalContext().getTransport("malspp").isSupportedInteractionType(interactionType));
    } catch (Exception exc) {
      if (logger.isLoggable(BasicLevel.WARN)) {
        logger.log(BasicLevel.WARN, "", exc);
    	}
    	LoggingBase.logMessage(exc.toString());
      return false;
    }
  }
  
}
