package org.ccsds.moims.mo.malspp.test.suite;

import fitnesse.junit.FitNesseRunner;
import fitnesse.junit.FitNesseRunner.FitnesseDir;
import fitnesse.junit.FitNesseRunner.Suite;
import fitnesse.junit.FitNesseRunner.OutputDir;
import fitnesse.junit.FitNesseRunner.Port;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author cooper_sf
 */
@RunWith(FitNesseRunner.class)
@Suite("MalSppTests.TestDocument")
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
