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

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 */
public class UC3_Ex1_Meta_Test extends UC3_Ex1_Test {

    /**
     * Test Case 1.
     */
    @Test
    @Override
    public void testCase_01() {
        System.out.println("Running: testCase_1()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_JUST_METADATA, productType, 1);
    }

    /**
     * Test Case 2.
     */
    @Test
    @Override
    public void testCase_02() {
        System.out.println("Running: testCase_2()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.FILETRANSFER, productType, 0);
    }

    /**
     * Test Case 3.
     */
    @Test
    @Override
    public void testCase_03() {
        System.out.println("Running: testCase_3()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        Identifier productType = new Identifier("typeTMPacketDailyExtract");
        test(user, domain, DeliveryMethodEnum.SERVICE_JUST_METADATA, productType, 1);
    }

    /**
     * Test Case 4.
     */
    @Test
    @Override
    public void testCase_04() {
        System.out.println("Running: testCase_4()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        Identifier productType = new Identifier("typeImage");
        test(user, domain, DeliveryMethodEnum.SERVICE_JUST_METADATA, productType, 0);
    }

    /**
     * Test Case 5.
     */
    @Test
    @Override
    public void testCase_05() {
        System.out.println("Running: testCase_5()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        Identifier productType = new Identifier("*");
        test(user, domain, DeliveryMethodEnum.SERVICE_JUST_METADATA, productType, 0);
    }

    /**
     * Test Case 6.
     */
    @Test
    @Override
    public void testCase_06() {
        System.out.println("Running: testCase_6()");
        Identifier user = null;
        IdentifierList domain = null;
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_JUST_METADATA, productType, 1);
    }

    /**
     * Test Case 7.
     */
    @Test
    @Override
    public void testCase_07() {
        System.out.println("Running: testCase_7()");
        Identifier user = new Identifier("john.smith");
        IdentifierList domain = null;
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_JUST_METADATA, productType, 0);
    }

    /**
     * Test Case 8.
     */
    @Test
    @Override
    public void testCase_08() {
        System.out.println("Running: testCase_8()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("*"));
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_JUST_METADATA, productType, 1);
    }

    /**
     * Test Case 9.
     */
    @Test
    @Override
    public void testCase_09() {
        System.out.println("Running: testCase_9()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("juice"));
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_JUST_METADATA, productType, 0);
    }

    /**
     * Test Case 10.
     */
    @Test
    @Override
    public void testCase_10() {
        System.out.println("Running: testCase_10()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("nasa"));
        domain.add(new Identifier("dart"));
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_JUST_METADATA, productType, 0);
    }

    /**
     * Test Case 11.
     */
    @Test
    @Override
    public void testCase_11() {
        System.out.println("Running: testCase_11()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("nasa"));
        domain.add(new Identifier("*"));
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_JUST_METADATA, productType, 1);
    }

    /**
     * Test Case 12.
     */
    @Test
    @Override
    public void testCase_12() {
        System.out.println("Running: testCase_12()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("nasa"));
        domain.add(new Identifier("hubble"));
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_JUST_METADATA, productType, 1);
    }

    @Override
    public void checkProductBody(Product returnedProduct, Blob productBody) {
        assertEquals(returnedProduct.getProductBody(), null);
    }
}
