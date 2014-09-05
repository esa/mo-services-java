/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Test bed
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
package org.ccsds.moims.mo.com.test.suite;

import fitnesse.junit.FitNesseSuite;
import fitnesse.junit.FitNesseSuite.FitnesseDir;
import fitnesse.junit.FitNesseSuite.Name;
import fitnesse.junit.FitNesseSuite.OutputDir;
import fitnesse.junit.FitNesseSuite.Port;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 */
@RunWith(FitNesseSuite.class)
@Name("ComTests.TestDocument")
@FitnesseDir("src/main/fitnesse")
@OutputDir(systemProperty = "ccsds.fitnesse.output.dir")
@Port(systemProperty = "ccsds.fitnesse.port")
public class JUnitSuiteTest
{
  @Test
  public void dummy()
  {
  }
}
