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
import org.ccsds.moims.mo.mpd.structures.ParameterDef;
import org.ccsds.moims.mo.mpd.structures.ParameterDefList;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductSummary;
import org.ccsds.moims.mo.mpd.structures.ProductType;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * A dummy backend in order to try out the provider.
 */
public class TMPacketsDataset extends Dataset {

    private final static Time APID100_TIME_START = Time.now();
    private final static Time APID100_TIME_END = Time.now();
    private final static Time APID200_TIME_START = Time.now();
    private final static Time APID200_TIME_END = Time.now();
    private final ObjectRef<ProductType> productTypeRefTM;

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
        productTypeRefTM = typeTMPacketDailyExtract.getObjectRef();
        productTypes.put(productTypeRefTM, typeTMPacketDailyExtract);

        // ---------------------------------------------------
        // Products
        // ---------------------------------------------------
        TimeWindow timeWindow = new TimeWindow(Time.now(), Time.now());

        // product1
        NamedValueList parameters1 = new NamedValueList();
        parameters1.add(new NamedValue(new Identifier("APID"), new UInteger(100)));
        ObjectIdentity productId1 = new ObjectIdentity(domain, new Identifier("tmData1"), new UInteger(1));
        Product product1 = new Product(productId1, productTypeRefTM,
                Time.now(), null, timeWindow, parameters1, "description",
                new Blob(new byte[]{0x01, 0x02, 0x03}));
        ObjectRef<Product> ref1 = product1.getObjectRef();
        ProductSummary metadata1 = new ProductSummary(productTypeRefTM, ref1, Time.now(), null, timeWindow, parameters1, "description");
        products.put(ref1, product1);
        metadatas.put(ref1, metadata1);

        // product2
        NamedValueList parameters2 = new NamedValueList();
        parameters2.add(new NamedValue(new Identifier("APID"), new UInteger(200)));
        ObjectIdentity productId2 = new ObjectIdentity(domain, new Identifier("tmData2"), new UInteger(1));
        Product product2 = new Product(productId2, productTypeRefTM,
                Time.now(), null, timeWindow, parameters2, "description",
                new Blob(new byte[]{0x09, 0x08, 0x07}));
        ObjectRef<Product> ref2 = product2.getObjectRef();
        ProductSummary metadata2 = new ProductSummary(productTypeRefTM, ref2, Time.now(), null, timeWindow, parameters2, "description");
        products.put(ref2, product2);
        metadatas.put(ref2, metadata2);
    }

    public ObjectRef<ProductType> getTMPacketsProductRef() {
        return productTypeRefTM;

    }
}
