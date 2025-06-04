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

import org.ccsds.mo.mpd.testbed.Constants;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mpd.Dataset;
import org.ccsds.moims.mo.mpd.structures.*;

/**
 * The TMPacketsDataset backend contains data similar to a set of TM Packets.
 */
public class TMPacketsDataset extends Dataset {

    public final static TimeWindow contentTimeWindowAPID100 = new TimeWindow(Constants.APID100_TIME_START, Constants.APID100_TIME_END);
    public final static TimeWindow contentTimeWindowAPID200 = new TimeWindow(Constants.APID200_TIME_START, Constants.APID200_TIME_END);
    public final ProductType typeTMPacketDailyExtract;

    public TMPacketsDataset() {
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        // ---------------------------------------------------
        // Product Types
        // ---------------------------------------------------
        Identifier name = new Identifier("typeTMPacketDailyExtract");
        AttributeDefList attributeDefs = new AttributeDefList();
        attributeDefs.add(new AttributeDef(new Identifier("APID"), AttributeType.UINTEGER));
        typeTMPacketDailyExtract = new ProductType(name, "A TM Packet Daily Extract type", attributeDefs);

        // ---------------------------------------------------
        // Products
        // ---------------------------------------------------
        // product1
        NamedValueList attributes1 = new NamedValueList();
        attributes1.add(new NamedValue(new Identifier("APID"), new UInteger(100)));
        ObjectRef<Product> ref1 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("tmData1"), new UInteger(1));
        Blob productBody1 = new Blob(new byte[]{0x01, 0x02, 0x03});
        ProductMetadata metadata1 = new ProductMetadata(typeTMPacketDailyExtract, ref1, Time.now(),
                null, null, contentTimeWindowAPID100, attributes1, "description", null, null);
        super.addNewProduct(ref1, productBody1, metadata1);

        // product2
        NamedValueList attributes2 = new NamedValueList();
        attributes2.add(new NamedValue(new Identifier("APID"), new UInteger(200)));
        ObjectRef<Product> ref2 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("tmData2"), new UInteger(1));
        Blob productBody2 = new Blob(new byte[]{0x09, 0x08, 0x07});
        ProductMetadata metadata2 = new ProductMetadata(typeTMPacketDailyExtract, ref2, Time.now(),
                null, null, contentTimeWindowAPID200, attributes2, "description", null, null);
        super.addNewProduct(ref2, productBody2, metadata2);
    }

    public ProductType getProductType() {
        return typeTMPacketDailyExtract;
    }

    @Override
    public int getMaximumNumberOfResults() {
        return 10;
    }
}
