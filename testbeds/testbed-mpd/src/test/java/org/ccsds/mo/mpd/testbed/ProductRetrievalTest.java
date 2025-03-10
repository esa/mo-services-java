/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Testbed - MPD
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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.mo.mpd.testbed.backends.OneProductDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mpd.MPDHelper;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class ProductRetrievalTest extends MPDTest {

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println(TEST_SET_UP_CLASS_1);
        System.out.println(TEST_SET_UP_CLASS_2);
        setUp.setUp(new OneProductDataset(), false, false, true);
    }

    /**
     * Test Case 1.
     */
    @Test
    public void testCase_01() {
        System.out.println("Running: testCase_01()");

        try {
            ProductFilter productFilter = new ProductFilter();
            Time now = Time.now();
            TimeWindow creationDate = new TimeWindow(now, new Time(now.getValue() - 100));
            TimeWindow contentDate = null;
            testMOErrorListProducts(productFilter, creationDate, contentDate);
            fail("The operation was expected to throw an 'Invalid' exception!");
        } catch (MALInteractionException ex) {
            MOErrorException moError = ex.getStandardError();
            long errorNumber = moError.getErrorNumber().getValue();
            if (errorNumber == MPDHelper.INVALID_ERROR_NUMBER.getValue()) {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Error returned successfully!");
            } else {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Failed!", ex);
                fail("The operation was expected to throw an 'Invalid' exception!");
            }
        }
    }

    /**
     * Test Case 2.
     */
    @Test
    public void testCase_02() {
        System.out.println("Running: testCase_02()");

        try {
            ProductFilter productFilter = new ProductFilter();
            Time now = Time.now();
            TimeWindow creationDate = null;
            TimeWindow contentDate = new TimeWindow(now, new Time(now.getValue() - 100));
            testMOErrorListProducts(productFilter, creationDate, contentDate);
            fail("The operation was expected to throw an 'Invalid' exception!");
        } catch (MALInteractionException ex) {
            MOErrorException moError = ex.getStandardError();
            long errorNumber = moError.getErrorNumber().getValue();
            if (errorNumber == MPDHelper.INVALID_ERROR_NUMBER.getValue()) {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Error returned successfully!");
            } else {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Failed!", ex);
                fail("The operation was expected to throw an 'Invalid' exception!");
            }
        }
    }

    private void testMOErrorListProducts(ProductFilter productFilter,
            TimeWindow creationDate, TimeWindow contentDate) throws MALInteractionException {
        try {
            consumerPR.listProducts(productFilter, creationDate, contentDate);
        } catch (MALException ex) {
            Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }

}
