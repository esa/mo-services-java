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

    public MixedProductDataset() {
        super();
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        AttributeDefList attributeDefsTmPacket = new AttributeDefList();
        attributeDefsTmPacket.add(new AttributeDef(new Identifier("APID"), AttributeType.UINTEGER));
        typeTMPacket = new ProductType(new Identifier("typeTMPacketDailyExtract"), "A TM Packet Daily Extract type", attributeDefsTmPacket);

        // Note that the Products 1, 2, and 3, are added on the super() call
        addTmpProduct4(domain);
        addTmpProduct5(domain);
    }

    private void addTmpProduct4(IdentifierList domain) {
        NamedValueList attributes4 = new NamedValueList();
        attributes4.add(new NamedValue(new Identifier("APID"), new UInteger(100)));
        ObjectRef<Product> ref4 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("tmData4"), new UInteger(1));
        Blob productBody4 = new Blob(new byte[]{0x01, 0x02, 0x03});
        ProductMetadata metadata4 = new ProductMetadata(typeTMPacket, ref4,
                Constants.APID100_CREATION_DATE,
                null, null,
                TMPacketsDataset.contentTimeWindowAPID100, attributes4, "description", null, null);
        super.addNewProduct(ref4, productBody4, metadata4);
    }

    private void addTmpProduct5(IdentifierList domain) {
        NamedValueList attributes5 = new NamedValueList();
        attributes5.add(new NamedValue(new Identifier("APID"), new UInteger(200)));
        ObjectRef<Product> ref5 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("tmData5"), new UInteger(1));
        Blob productBody5 = new Blob(new byte[]{0x09, 0x08, 0x07});
        ProductMetadata metadata4 = new ProductMetadata(typeTMPacket, ref5,
                Constants.APID200_CREATION_DATE,
                null, null,
                TMPacketsDataset.contentTimeWindowAPID200, attributes5, "description", null, null);
        super.addNewProduct(ref5, productBody5, metadata4);
    }
}
