/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Testbed
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
package org.ccsds.mo.mpd.testbed;

import java.io.IOException;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class UC1_Ex1_Test extends MCTest {

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println(TEST_SET_UP_CLASS_1);
        System.out.println(TEST_SET_UP_CLASS_2);
        //setUp.setUp(backend, true, true, true);
    }

    /**
     * Test Case 1.
     */
    @Test
    public void testCase_01() {
        System.out.println("Running: testCase_1()");
        UInteger apidValue = new UInteger(100);
        test(apidValue, 1);
    }

    /**
     * Test Case 2.
     */
    @Test
    public void testCase_02() {
        System.out.println("Running: testCase_2()");
        UInteger apidValue = new UInteger(200);
        test(apidValue, 1);
    }

    private void test(UInteger apidValue, int expectedNumberOfResults) {
        testWithTimeWindow(apidValue, expectedNumberOfResults);
    }

    private synchronized void testWithTimeWindow(UInteger apidValue, int expectedNumberOfResults) {
    }
}
