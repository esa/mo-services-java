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
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.mo.mpd.testbed.backends.ImagesDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter;
import org.ccsds.moims.mo.mpd.structures.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class UC1_Ex2_Test extends MPDTest {

    private ProductType productType;
    private AttributeFilterList attributeFilter;
    private static final ImagesDataset backend = new ImagesDataset();

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println(TEST_SET_UP_CLASS_1);
        System.out.println(TEST_SET_UP_CLASS_2);
        setUp.setUp(backend, true, true, true);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        attributeFilter = new AttributeFilterList();

    }

    /**
     * Test Case 1.
     */
    @Test
    public void testCase_01() {
        System.out.println("Running: testCase_01()");
        productType = backend.typeImage;  //  productType=typeTMPacket

        AttributeList values = new AttributeList();
        values.add(new Union("infrared"));
        ValueSet valueSet = new ValueSet(new Identifier("imageType"), true, values);
        attributeFilter.add(valueSet);
        Consumer<NamedValueList> isValidAttribute = attributes -> {
            Union val1 = new Union("infrared");
            for (NamedValue att : attributes) {
                if ("imageType".equals(att.getName().toString())) {
                    assertEquals(val1, att.getValue());
                }
            }
        };

        testWithTimeWindowGeneric(null, 2, null, isValidAttribute);
    }

    @Test
    public void testCase_02() {
        System.out.println("Running: testCase_02()");
        productType = backend.typeImage;  //  productType=typeTMPacket
        Union val1 = new Union("Earth");
        AttributeList values = new AttributeList();
        values.add(val1);
        ValueSet valueSet = new ValueSet(new Identifier("ImageSubject"), true, values);
        attributeFilter.add(valueSet);
        Consumer<NamedValueList> isValidAttribute = attributes -> {
            for (NamedValue att : attributes) {
                if ("ImageSubject".equals(att.getName().toString())) {
                    assertEquals(val1, att.getValue());
                }
            }
        };
        testWithTimeWindowGeneric(null, 2, null, isValidAttribute);
    }

    @Test
    public void testCase_03() {
        System.out.println("Running: testCase_03()");
        productType = backend.typeImage;  //  productType=typeTMPacket
        Union val1 = new Union("Earth");
        AttributeList values = new AttributeList();
        values.add(val1);
        attributeFilter.add(new ValueSet(new Identifier("ImageSubject"), true, values));

        Union val2 = new Union("infrared");
        AttributeList values2 = new AttributeList();
        values2.add(val2);
        attributeFilter.add(new ValueSet(new Identifier("imageType"), true, values2));

        Consumer<NamedValueList> isValidAttribute = attributes -> {
            for (NamedValue att : attributes) {
                if ("ImageSubject".equals(att.getName().toString())) {
                    assertEquals(val1, att.getValue());
                }
                if ("imageType".equals(att.getName().toString())) {
                    assertEquals(val2, att.getValue());
                }
            }
        };
        testWithTimeWindowGeneric(null, 1, null, isValidAttribute);
    }

    @Test
    public void testCase_04() {
        System.out.println("Running: testCase_04()");
        productType = backend.typeImage;  //  productType=typeTMPacket

        TimeWindow contentTimeWindow = new TimeWindow(
                new Time("2020-01-22T10:10:06.728Z"),
                new Time("2023-02-22T10:10:06.728Z"));

        Consumer<NamedValueList> isValidAttribute = attributes -> {
        };
        testWithTimeWindowGeneric(null, 1, contentTimeWindow, isValidAttribute);
    }

    @Test
    public void testCase_05() {
        System.out.println("Running: testCase_05()");
        productType = backend.typeImage;  //  productType=typeTMPacket
        Union val1 = new Union("Earth");
        Union val2 = new Union("visible");
        AttributeList values2 = new AttributeList();
        values2.add(val2);
        attributeFilter.add(new ValueSet(new Identifier("imageType"), true, values2));

        Consumer<NamedValueList> isValidAttribute = attributes -> {
            for (NamedValue att : attributes) {
                if ("ImageSubject".equals(att.getName().toString())) {
                    assertEquals(val1, att.getValue());
                }
                if ("imageType".equals(att.getName().toString())) {
                    assertEquals(val2, att.getValue());
                }
            }
        };
        TimeWindow contentTimeWindow = new TimeWindow(
                new Time("2020-01-22T10:10:06.728Z"),
                new Time("2023-02-22T10:10:06.728Z"));

        testWithTimeWindowGeneric(null, 1, contentTimeWindow, isValidAttribute);
    }

    @Test
    public void testCase_06() {
        System.out.println("Running: testCase_06()");
        productType = backend.typeImage;  //  productType=typeTMPacket

        AttributeList values = new AttributeList();
        values.add(new Union("ultraviolet"));
        ValueSet valueSet = new ValueSet(new Identifier("imageType"), true, values);
        attributeFilter.add(valueSet);
        Consumer<NamedValueList> isValidAttribute = attributes -> {
            Union val1 = new Union("infrared");
            for (NamedValue att : attributes) {
                if ("imageType".equals(att.getName().toString())) {
                    assertEquals(val1, att.getValue());
                }
            }
        };

        testWithTimeWindowGeneric(null, 0, null, isValidAttribute);
    }

    @Test
    public void testCase_07() {
        System.out.println("Running: testCase_07()");
        productType = backend.typeImage;  //  productType=typeTMPacket
        Union val1 = new Union("Jupiter");
        AttributeList values = new AttributeList();
        values.add(val1);
        attributeFilter.add(new ValueSet(new Identifier("ImageSubject"), true, values));

        Union val2 = new Union("infrared");
        AttributeList values2 = new AttributeList();
        values2.add(val2);
        attributeFilter.add(new ValueSet(new Identifier("imageType"), true, values2));

        Consumer<NamedValueList> isValidAttribute = attributes -> {
            for (NamedValue att : attributes) {
                if ("ImageSubject".equals(att.getName().toString())) {
                    assertEquals(val1, att.getValue());
                }
                if ("imageType".equals(att.getName().toString())) {
                    assertEquals(val2, att.getValue());
                }
            }
        };
        testWithTimeWindowGeneric(null, 0, null, isValidAttribute);
    }

    private synchronized void testWithTimeWindowGeneric(String source, int expectedNumberOfResults, TimeWindow contentDate, Consumer<NamedValueList> isValidAttribute) {
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));
        IdentifierList sources;
        if (Objects.isNull(source)) {
            sources = null;
        } else {
            sources = new IdentifierList();
            sources.add(new Identifier(source));
        }

        ProductFilter productFilter = new ProductFilter(productType.getName(), domain, sources, attributeFilter);
        ProductMetadataList list = null;

        try {
            TimeWindow creationDate = null;
            list = consumerPR.listProducts(productFilter, creationDate, contentDate);
            int size = list.size();
            System.out.println("Number of listed products returned: " + size);
            assertEquals(expectedNumberOfResults, size);
        } catch (MALInteractionException ex) {
            Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }

        if (list == null) {
            fail("The list cannot be null");
            return;
        }

        // Prepare the ObjectRefList with the returned data from the previous step
        ObjectRefList productRefs = new ObjectRefList();

        for (ProductMetadata metadata : list) {
            productRefs.add(metadata.getProduct());
        }

        try {
            ProductList returnedProducts = new ProductList();
            long startTime = System.currentTimeMillis();
            final AtomicBoolean ackReceived = new AtomicBoolean(false);
            final AtomicBoolean rspReceived = new AtomicBoolean(false);
            consumerPR.asyncGetProducts(productRefs, new ProductRetrievalAdapter() {

                @Override
                public void getProductsAckReceived(MALMessageHeader msgHeader, Map qosProperties) {
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("ACK received in: " + duration + " ms");
                    ackReceived.set(true);
                }

                @Override
                public void getProductsUpdateReceived(MALMessageHeader msgHeader, Product product, Map qosProperties) {
                    returnedProducts.add(product);
                }

                @Override
                public void getProductsResponseReceived(MALMessageHeader msgHeader, Map qosProperties) {
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("RESPONSE received in: " + duration + " ms");
                    rspReceived.set(true);
                }

                @Override
                public void getProductsAckErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE,
                            "Something went wrong...", error);
                    fail(error.toString());
                }

                @Override
                public void getProductsUpdateErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE,
                            "Something went wrong...", error);
                    fail(error.toString());
                }

                @Override
                public void getProductsResponseErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE,
                            "Something went wrong...", error);
                    fail(error.toString());
                }

            });

            // ------------------------------------------------------------------------
            // Wait while ACK has not been received and 1 second has not passed yet...
            long timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            while (!ackReceived.get() && timeSinceInteractionStarted < TIMEOUT) {
                // Recalculate it
                timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            }

            if (!ackReceived.get()) {
                Logger.getLogger(UC1_Ex1_Test.class.getName()).log(
                        Level.SEVERE, "The ACK was not received!");
                fail("The ACK was not received!");
            }

            // ------------------------------------------------------------------------
            // Wait while RESPONSE has not been received and 1 second has not passed yet...
            timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            while (!rspReceived.get() && timeSinceInteractionStarted < TIMEOUT) {
                // Recalculate it
                timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            }

            if (!rspReceived.get()) {
                Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE, "The RESPONSE was not received!");
                fail("The RESPONSE was not received!");
            }

            assertNotNull(returnedProducts);
            int size = returnedProducts.size();
            System.out.println("Number of products returned: " + size);
            assertEquals(expectedNumberOfResults, size);

            // Finish the test if nothing was returned.. (there's nothing else to check)
            if (size == 0) {
                return;
            }

            // Check the timeWindows for all the received products, if one was selected
            if (contentDate != null) {
                for (Product p : returnedProducts) {
                    TimeWindow receivedTW = p.getProductMetadata().getContentDate();

                    if (receivedTW.getStart().getValue() > contentDate.getEnd().getValue()) {
                        fail("The received TimeWindow start time is after the requested TimeWindow end time!");
                    }
                    if (receivedTW.getEnd().getValue() < contentDate.getStart().getValue()) {
                        fail("The received TimeWindow end time is before the requested TimeWindow start time!");
                    }
                }
            }

            // Check that the productType matches
            for (Product p : returnedProducts) {
                ProductMetadata metadata = p.getProductMetadata();
                if (!Objects.isNull(productType.getName()) && !metadata.getProductType().equals(productType)) {
                    fail("The productType isnot the same! For product: " + p.toString());
                }
                NamedValueList attributes = metadata.getAttributes();
                isValidAttribute.accept(attributes);
            }
            System.out.flush();
        } catch (MALInteractionException ex) {
            Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }

}
