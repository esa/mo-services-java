/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA CCSDS MO Services
 * ----------------------------------------------------------------------------
 * Licensed under European Space Agency Public License (ESA-PL) Weak Copyleft – v2.4
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
package org.ccsds.mo.mpd.testbed.backends;

import java.util.HashMap;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mpd.backends.ProductRetrievalBackend;
import org.ccsds.moims.mo.mpd.structures.ParameterDef;
import org.ccsds.moims.mo.mpd.structures.ParameterDefList;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductSummary;
import org.ccsds.moims.mo.mpd.structures.ProductSummaryList;
import org.ccsds.moims.mo.mpd.structures.ProductType;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * A dummy backend in order to try out the provider.
 */
public class TMPacketsDataset implements ProductRetrievalBackend {

    private final static Time APID100_TIME_START = Time.now();
    private final static Time APID100_TIME_END = Time.now();
    private final static Time APID200_TIME_START = Time.now();
    private final static Time APID200_TIME_END = Time.now();

    private final HashMap<ObjectRef, ProductType> productTypes = new HashMap();
    private final HashMap<ObjectRef, Product> products = new HashMap();
    private final HashMap<ObjectRef, ProductSummary> metadatas = new HashMap();
    private final ProductSummaryList allMetadatas = new ProductSummaryList();

    public TMPacketsDataset() {
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        // ---------------------------------------------------
        // Product Types
        // ---------------------------------------------------
        ObjectIdentity typeId1 = new ObjectIdentity(domain, new Identifier("tmPacketDailyExtract"), new UInteger(1));
        ParameterDefList parameterDefs = new ParameterDefList();
        parameterDefs.add(new ParameterDef(new Identifier("APID"), AttributeType.UINTEGER));
        ProductType typeTMPacketDailyExtract = new ProductType(typeId1, "A TM Packet Daily Extract type", parameterDefs);
        ObjectRef<ProductType> productTypeRefTM = typeTMPacketDailyExtract.getObjectRef();
        productTypes.put(productTypeRefTM, typeTMPacketDailyExtract);

        // ---------------------------------------------------
        // Products
        // ---------------------------------------------------
        TimeWindow timeWindow = new TimeWindow(Time.now(), Time.now());

        // product1
        ObjectIdentity productId1 = new ObjectIdentity(domain, new Identifier("tmData1"), new UInteger(1));
        Product product1 = new Product(productId1, productTypeRefTM,
                Time.now(), timeWindow, "description",
                new Blob(new byte[]{0x01, 0x02, 0x03}));
        ObjectRef<Product> ref1 = product1.getObjectRef();
        NamedValueList parameters1 = new NamedValueList();
        parameters1.add(new NamedValue(new Identifier("APID"), new UInteger(100)));
        ProductSummary metadata1 = new ProductSummary(productTypeRefTM, ref1, Time.now(), null, timeWindow, parameters1, "description");
        products.put(ref1, product1);
        metadatas.put(ref1, metadata1);
        allMetadatas.add(metadata1);

        // product2
        ObjectIdentity productId2 = new ObjectIdentity(domain, new Identifier("tmData2"), new UInteger(1));
        Product product2 = new Product(productId2, productTypeRefTM,
                Time.now(), timeWindow, "description",
                new Blob(new byte[]{0x09, 0x08, 0x07}));
        ObjectRef<Product> ref2 = product2.getObjectRef();
        NamedValueList parameters2 = new NamedValueList();
        parameters2.add(new NamedValue(new Identifier("APID"), new UInteger(200)));
        ProductSummary metadata2 = new ProductSummary(productTypeRefTM, ref2, Time.now(), null, timeWindow, parameters2, "description");
        products.put(ref2, product2);
        metadatas.put(ref2, metadata2);
        allMetadatas.add(metadata2);
    }

    @Override
    public ProductSummaryList getMetadataForAllProducts() {
        return allMetadatas;
    }

    @Override
    public Product getProduct(ObjectRef productRef) {
        return products.get(productRef);
    }

    @Override
    public ProductSummary getMetadata(ObjectRef productRef) {
        return metadatas.get(productRef);
    }

}