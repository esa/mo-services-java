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

import org.ccsds.moims.mo.mpd.Dataset;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mpd.structures.AttributeDef;
import org.ccsds.moims.mo.mpd.structures.AttributeDefList;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductType;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * A dummy backend in order to try out the provider.
 */
public class ImagesDataset extends Dataset {

    public final ProductType typeImage;

    public ImagesDataset() {
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        // ---------------------------------------------------
        // Product Types
        // ---------------------------------------------------
        AttributeDefList attributeDefs = new AttributeDefList();
        attributeDefs.add(new AttributeDef(new Identifier("ImageSubject"), AttributeType.STRING));
        attributeDefs.add(new AttributeDef(new Identifier("imageType"), AttributeType.STRING));
        typeImage = new ProductType(new Identifier("typeImage"), "An Image type", attributeDefs);

        // ---------------------------------------------------
        // Products
        // ---------------------------------------------------
        TimeWindow timeWindow = new TimeWindow(Time.now(), Time.now());

        // product1
        NamedValueList attributes1 = new NamedValueList();
        attributes1.add(new NamedValue(new Identifier("ImageSubject"), new Union("Earth")));
        attributes1.add(new NamedValue(new Identifier("imageType"), new Union("visible")));
        Blob productBody1 = new Blob(new byte[]{0x01, 0x02, 0x03});
        ObjectRef<Product> ref1 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("imageData1"), new UInteger(1));
        ProductMetadata metadata1 = new ProductMetadata(typeImage, ref1, Time.now(),
                null, null, timeWindow, attributes1, "description", null);
        super.addNewProduct(ref1, productBody1, metadata1);

        // product2
        NamedValueList attributes2 = new NamedValueList();
        attributes2.add(new NamedValue(new Identifier("ImageSubject"), new Union("Earth")));
        attributes2.add(new NamedValue(new Identifier("imageType"), new Union("infrared")));
        ObjectRef<Product> ref2 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("imageData2"), new UInteger(1));
        Blob productBody2 = new Blob(new byte[]{0x09, 0x08, 0x07});
        ProductMetadata metadata2 = new ProductMetadata(typeImage, ref2, Time.now(),
                null, null, timeWindow, attributes2, "description", null);
        super.addNewProduct(ref2, productBody2, metadata2);
    }
}
