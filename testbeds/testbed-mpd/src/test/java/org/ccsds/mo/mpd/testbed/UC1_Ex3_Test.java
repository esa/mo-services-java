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
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.mo.mpd.testbed.backends.ImagesDataset;
import org.ccsds.mo.mpd.testbed.backends.MixedProductDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter;
import org.ccsds.moims.mo.mpd.structures.*;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class UC1_Ex3_Test extends MPDTest {

    private ProductType productType;

    private static final ImagesDataset backend = new ImagesDataset();
    private static final MixedProductDataset backendMixed = new MixedProductDataset();

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println(TEST_SET_UP_CLASS_1);
        System.out.println(TEST_SET_UP_CLASS_2);
        setUp.setUp(backend, true, true, true);
    }

    /**
     * Test Case 1.
     */
    @Test
    public void testCase_01() {
        productType = backend.typeImage;  //  productType=typeTMPacket
        System.out.println("Running: testCase_01()");
        testWithTimeWindowValueSet("visible", true, "forest flyover", 1, null);
    }

    /**
     * Test Case 1.
     */
    @Test
    public void testCase_02() {
        productType = backend.typeImage;  //  productType=typeTMPacket
        System.out.println("Running: testCase_02()");
        testWithTimeWindowValueSet("visible", true, null, 1, null);
    }

    @Test
    public void testCase_03() {
        productType = backend.typeImage;  //  productType=typeTMPacket
        System.out.println("Running: testCase_03() Skipped");
    }

    @Test
    public void testCase_04() {
        productType = backend.typeImage;  //  productType=typeTMPacket
        System.out.println("Running: testCase_04() Skipped");
        testWithTimeWindowValueSet("visible", false, "takes a photo", 0, null);
    }

    @Test
    public void testCase_05() {
        productType = backend.typeImage;  //  productType=typeTMPacket
        System.out.println("Running: testCase_05()");
        TimeWindow contentTimeWindow = new TimeWindow(
                new Time(Instant.parse("2020-01-22T10:10:06.728Z").toEpochMilli()),
                new Time(Instant.parse("2023-02-22T10:10:06.728Z").toEpochMilli()));
        testWithTimeWindowValueSet("visible", true, "forest flyover", 1, contentTimeWindow);
    }

    @Test
    public void testCase_06() {
        productType = new ProductType(null, "Invalid Type", null);
        System.out.println("Running: testCase_06() Skipped");
    }

    @Test
    public void testCase_07() throws IOException {
        setUp.setUp(backendMixed, true, true, true);
        productType = new ProductType(null, "Invalid Type", null);
        System.out.println("Running: testCase_07()");
        TimeWindow contentTimeWindow = new TimeWindow(
                new Time(Instant.parse("2009-12-31T11:41:53.437Z").toEpochMilli()),
                new Time(Instant.parse("2022-01-22T20:18:10.539Z").toEpochMilli()));
        testWithTimeWindowValueRange(50L, 150L, true, null, 1, contentTimeWindow);
    }

    @Test
    public void testCase_08() throws IOException {
        setUp.setUp(backendMixed, true, true, true);
        productType = new ProductType(null, "Invalid Type", null);
        System.out.println("Running: testCase_08()");
        TimeWindow contentTimeWindow = new TimeWindow(
                new Time(Instant.parse("2009-12-31T11:41:53.437Z").toEpochMilli()),
                new Time(Instant.parse("2022-01-22T20:18:10.539Z").toEpochMilli()));
        testWithTimeWindowStringPattern("tree", null, true, null, 0, contentTimeWindow);
    }

    @Test
    public void testCase_09() throws IOException {
        setUp.setUp(backendMixed, true, true, true);
        productType = new ProductType(null, "Invalid Type", null);
        System.out.println("Running: testCase_09()");
        TimeWindow contentTimeWindow = new TimeWindow(
                new Time(Instant.parse("2013-01-22T10:10:06.728Z").toEpochMilli()),
                new Time(Instant.parse("2023-02-22T10:10:06.728Z").toEpochMilli()));
        List<String> actualValues = new ArrayList<>(Arrays.asList("Earth", "Earth"));
        testWithTimeWindowStringPattern("Ear.*", actualValues, true, null, 2, contentTimeWindow);
    }

    @Test
    public void testCase_10() throws IOException {
        setUp.setUp(backendMixed, true, true, true);
        productType = new ProductType(null, "Invalid Type", null);
        System.out.println("Running: testCase_10()");
        TimeWindow contentTimeWindow = new TimeWindow(
                new Time(Instant.parse("2013-01-22T10:10:06.728Z").toEpochMilli()),
                new Time(Instant.parse("2023-02-22T10:10:06.728Z").toEpochMilli()));
        testWithTimeWindowValueRange(null, null, false, null, 4, contentTimeWindow);
    }

    private synchronized void testWithTimeWindowValueSet(String attributeFilterStr,
            boolean isAttributeFilterIncluded, String source, int expectedNumberOfResults, TimeWindow contentDate) {
        ValueSet valueSet = null;
        Union attributeFilterStrMO = new Union(attributeFilterStr);
        if (attributeFilterStr != null) {
            AttributeList values = new AttributeList();
            values.add(attributeFilterStrMO);
            valueSet = new ValueSet(new Identifier("imageType"), isAttributeFilterIncluded, values);
        }
        Consumer<NamedValueList> isValidAttribute = attributes -> {
            for (NamedValue att : attributes) {
                if ("imageType".equals(att.getName().toString())) {
                    assertEquals(attributeFilterStrMO, att.getValue());
                }
            }
        };

        testWithTimeWindowGeneric(valueSet, source, expectedNumberOfResults, contentDate, isValidAttribute);
    }

    private synchronized void testWithTimeWindowValueRange(Long minVal, Long maxVal,
            boolean isAttributeFilterIncluded, String source, int expectedNumberOfResults, TimeWindow contentDate) {
        ValueRange valueRange = null;
        if (minVal != null) {
            valueRange = new ValueRange(new Identifier("APID"), isAttributeFilterIncluded, new UInteger(minVal), new UInteger(maxVal));
        }
        Consumer<NamedValueList> isValidAttribute = attributes -> {
            for (NamedValue att : attributes) {
                if (!Objects.isNull(minVal) && "APID".equals(att.getName().toString())) {

                    long attributeValue = ((UInteger) att.getValue()).getValue();
                    System.out.println("APID: " + attributeValue + " " + minVal + " " + maxVal);
                    assertTrue((attributeValue >= minVal && attributeValue <= maxVal) == isAttributeFilterIncluded);
                }
            }
        };
        testWithTimeWindowGeneric(valueRange, source, expectedNumberOfResults, contentDate, isValidAttribute);
    }

    private synchronized void testWithTimeWindowStringPattern(String pattern, List<String> actualValues,
            boolean isAttributeFilterIncluded, String source, int expectedNumberOfResults, TimeWindow contentDate) {
        StringPattern stringPattern = null;
        if (pattern != null) {
            stringPattern = new StringPattern(new Identifier("ImageSubject"), isAttributeFilterIncluded, pattern);
        }
        Consumer<NamedValueList> isValidAttribute = attributes -> {
            int index = 0;
            for (NamedValue att : attributes) {
                if ("ImageSubject".equals(att.getName().toString())) {
                    assertEquals(actualValues.get(index++), Attribute.attribute2JavaType(att.getValue()));
//                    Integer attributeValue = (int) Attribute.attribute2JavaType(att.getValue());
//                    assertTrue();
                }
            }
        };
        testWithTimeWindowGeneric(stringPattern, source, expectedNumberOfResults, contentDate, isValidAttribute);
    }

    private synchronized void testWithTimeWindowGeneric(AttributeFilter attributeFilterSingle,
            String source, int expectedNumberOfResults, TimeWindow contentDate, Consumer<NamedValueList> isValidAttribute) {
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));
        IdentifierList sources;
        if (Objects.isNull(source)) {
            sources = null;
        } else {
            sources = new IdentifierList();
            sources.add(new Identifier(source));
        }
        AttributeFilterList attributeFilter = null;

        // When the apidValue is NULL, then the filtering is off!
        if (attributeFilterSingle != null) {
            attributeFilter = new AttributeFilterList();
            attributeFilter.add(attributeFilterSingle);
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
                    TimeWindow receivedTW = p.getContentDate();

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
                if (!Objects.isNull(productType.getName()) && !p.getProductType().equals(productType)) {
                    fail("The productType isnot the same! For product: " + p.toString());
                }
                NamedValueList attributes = p.getAttributes();

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
