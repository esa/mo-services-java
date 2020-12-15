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

import fitnesse.junit.FitNesseRunner;
import fitnesse.junit.FitNesseRunner.FitnesseDir;
import fitnesse.junit.FitNesseRunner.Suite;
import fitnesse.junit.FitNesseRunner.OutputDir;
import fitnesse.junit.FitNesseRunner.Port;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 */
@RunWith(FitNesseRunner.class)
@Suite("ComTests.TestDocument")
@FitnesseDir("src/main/fitnesse")
@OutputDir(systemProperty = "ccsds.fitnesse.output.dir")
public class JUnitSuiteTest
{
  @Test
  public void dummy()
  {
  }
}
