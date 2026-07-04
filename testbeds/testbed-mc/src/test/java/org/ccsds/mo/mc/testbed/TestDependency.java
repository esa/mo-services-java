/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Testbed - M&C
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
package org.ccsds.mo.mc.testbed;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

/**
 * This class is used to help managing dependencies between tests.
 * The M&C testbed does not always follow the standard pattern of JUnit tests
 * where tests should be completely independant.
 * Dependant tests use the before and after functions to be called at the beginning and at the end of the test.
 * 
 * @author serge
 *
 */
public class TestDependency {

	private static int testStep = 0;
	private static boolean testStatus = true;
	public interface TestProcedure {
	  void exec();
	}
	
	public static void reset() {
		testStep = 0;
		testStatus = true;
	}
	
	/**
	 * Checks that the dependant test has executed and successfully completed.
	 */
	public static void before(int previousStep, Object testObject, String previousTestProcName, int targetStep) {
		assertTrue("Previous dependant test failed.", testStatus);
		if (previousTestProcName != null && testStep != previousStep) {
			try {
				assertNotNull(testObject);
				Method previousTestProc = testObject.getClass().getMethod(previousTestProcName, (Class<?>[] ) null);
				previousTestProc.invoke(testObject, (Object[]) null);
			} catch (ReflectiveOperationException exc) {
				assertNull(exc.getMessage(), exc);
			}
		}
		testStep = targetStep;
		testStatus = false;
	}
	
	/**
	 * Validates the current test step for the depending test.
	 */
	public static void after() {
		testStatus = true;
	}
}
