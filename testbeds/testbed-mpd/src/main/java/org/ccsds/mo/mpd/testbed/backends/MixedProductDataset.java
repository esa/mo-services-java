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
import org.ccsds.moims.mo.mpd.structures.*;

/**
 * A dummy backend in order to try out the provider.
 */
public class MixedProductDataset extends ImagesDataset {

    public final ProductType typeTMPacket;

    private void addTmpProduct1(IdentifierList domain) {
        /*
APID100_CREATION_DATE	2010-01-01T09:13:51.352Z
APID100_TIME_END	2010-01-01T09:07:51.352Z
APID100_TIME_START	2009-12-31T11:41:53.437Z

Product name: product4 (with metadata4)
    - identity.domain: myDomain
    - identity.key: "tmData3"
    - identity.version: 1
    - productType: typeTMPacketDailyExtract
    - creationDate: <APID100_CREATION_DATE>
    - timeWindow.start: <APID100_TIME_START>
    - timeWindow.end: <APID100_TIME_END>
    - description: "description"
    - productBody: [0x01,0x02,0x03]
    - attributes: APID
Product metadata: metadata4
    - productType: typeTMPacketDailyExtract
    - product: product1
    - creationDate: <APID100_CREATION_DATE>
    - source: NULL
    - timeWindow.start: <APID100_TIME_START>
    - timeWindow.end: <APID100_TIME_END>
    - parameters: APID=100
 */
        NamedValueList attributes4 = new NamedValueList();
        attributes4.add(new NamedValue(new Identifier("APID"), new UInteger(100)));
        ObjectRef<Product> ref4 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("tmData3"), new UInteger(1));
        Blob productBody4 = new Blob(new byte[]{0x01,0x02,0x03});
        ProductMetadata metadata4 = new ProductMetadata(typeTMPacket, ref4,
                Constants.APID100_CREATION_DATE,
                null, null,
                TMPacketsDataset.contentTimeWindowAPID100, attributes4, "description", null, null);
        super.addNewProduct(ref4, productBody4, metadata4);
    }

    private void addTmpProduct2(IdentifierList domain) {
        /*
APID200_CREATION_DATE	2020-01-01T08:24:26.846Z
APID200_TIME_END	2020-01-01T08:14:53.113Z
APID200_TIME_START	2019-12-31T10:09:17.854Z

Product name: product5 (with metadata5)
    - identity.domain: myDomain
    - identity.key: "tmData42"
    - identity.version: 1
    - productType: typeTMPacketDailyExtract
    - creationDate: <APID200_CREATION_DATE>
    - timeWindow.start: <APID200_TIME_START>
    - timeWindow.end: <APID200_TIME_END>
    - description: "description"
    - productBody: [0x09,0x08,0x07]
    - attributes: APID
Product metadata: metadata5
    - productType: typeTMPacketDailyExtract
    - product: product2
    - creationDate: <APID200_CREATION_DATE>
    - source: NULL
    - timeWindow.start: <APID200_TIME_START>
    - timeWindow.end: <APID200_TIME_END>
    - parameters: APID=200

 */
        NamedValueList attributes4 = new NamedValueList();
        attributes4.add(new NamedValue(new Identifier("APID"), new UInteger(200)));
        ObjectRef<Product> ref4 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("tmData42"), new UInteger(1));
        Blob productBody4 = new Blob(new byte[]{0x09,0x08,0x07});
        ProductMetadata metadata4 = new ProductMetadata(typeTMPacket, ref4,
                Constants.APID200_CREATION_DATE,
                null, null,
                TMPacketsDataset.contentTimeWindowAPID200, attributes4, "description", null, null);
        super.addNewProduct(ref4, productBody4, metadata4);
    }

    public MixedProductDataset() {
        super();
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        AttributeDefList attributeDefsTmPacket = new AttributeDefList();
        attributeDefsTmPacket.add(new AttributeDef(new Identifier("APID"), AttributeType.UINTEGER));
        typeTMPacket = new ProductType(new Identifier("typeTMPacketDailyExtract"), "A TM Packet Daily Extract typee", attributeDefsTmPacket);

        addTmpProduct1(domain);
        addTmpProduct2(domain);
    }
}
