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
        attributeDefs.add(new AttributeDef(new Identifier("coordinates.lat"), AttributeType.DOUBLE));
        attributeDefs.add(new AttributeDef(new Identifier("coordinates.lon"), AttributeType.DOUBLE));
        attributeDefs.add(new AttributeDef(new Identifier("imageType"), AttributeType.STRING));
        typeImage = new ProductType(new Identifier("typeImage"), "An Image type", attributeDefs);

        addImgProduct1(domain);
        addImgProduct2(domain);
        addImgProduct3(domain);
    }

    private void addImgProduct1(IdentifierList domain) {
        // product1
        TimeWindow contentTimeWindow1 = new TimeWindow(
                Constants.IMAGE_DATA_1__TIME_START,
                Constants.IMAGE_DATA_1__TIME_END);
        NamedValueList attributes1 = new NamedValueList();
        attributes1.add(new NamedValue(new Identifier("ImageSubject"), new Union("Earth")));
        attributes1.add(new NamedValue(new Identifier("coordinates.lat"), Constants.IMAGE_DATA_1_LAT));
        attributes1.add(new NamedValue(new Identifier("coordinates.lon"), Constants.IMAGE_DATA_1_LON));
        attributes1.add(new NamedValue(new Identifier("imageType"), new Union("visible")));
        Blob productBody1 = new Blob(new byte[]{0x01, 0x02, 0x03});
        ObjectRef<Product> ref1 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("imageData1"), new UInteger(1));
        ProductMetadata metadata1 = new ProductMetadata(typeImage, ref1,
                Constants.IMAGE_DATA_1_CREATION_DATE,
                new Identifier("forest flyover"), null, contentTimeWindow1,
                attributes1, "description", null, null);
        super.addNewProduct(ref1, productBody1, metadata1);
    }

    private void addImgProduct2(IdentifierList domain) {
        // product2
        TimeWindow contentTimeWindow2 = new TimeWindow(
                Constants.IMAGE_DATA_2__TIME_START,
                Constants.IMAGE_DATA_2__TIME_END);
        NamedValueList attributes2 = new NamedValueList();
        attributes2.add(new NamedValue(new Identifier("ImageSubject"), new Union("Earth")));
        attributes2.add(new NamedValue(new Identifier("coordinates.lat"), Constants.IMAGE_DATA_2_LAT));
        attributes2.add(new NamedValue(new Identifier("coordinates.lon"), Constants.IMAGE_DATA_2_LON));
        attributes2.add(new NamedValue(new Identifier("imageType"), new Union("infrared")));
        ObjectRef<Product> ref2 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("imageData2"), new UInteger(1));
        Blob productBody2 = new Blob(new byte[]{0x09, 0x08, 0x07});
        ProductMetadata metadata2 = new ProductMetadata(typeImage, ref2,
                Constants.IMAGE_DATA_2_CREATION_DATE,
                new Identifier("take a photo"), null, contentTimeWindow2,
                attributes2, "description", null, null);
        super.addNewProduct(ref2, productBody2, metadata2);
    }

    private void addImgProduct3(IdentifierList domain) {
        TimeWindow contentTimeWindow3 = new TimeWindow(
                Constants.IMAGE_DATA_3__TIME_START,
                Constants.IMAGE_DATA_3__TIME_END);
        NamedValueList attributes3 = new NamedValueList();
        attributes3.add(new NamedValue(new Identifier("ImageSubject"), new Union("Mars")));
        attributes3.add(new NamedValue(new Identifier("coordinates.lat"), Constants.IMAGE_DATA_3_LAT));
        attributes3.add(new NamedValue(new Identifier("coordinates.lon"), Constants.IMAGE_DATA_3_LON));
        attributes3.add(new NamedValue(new Identifier("imageType"), new Union("infrared")));
        ObjectRef<Product> ref3 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("imageData3"), new UInteger(1));
        Blob productBody3 = new Blob(new byte[]{0x09, 0x08, 0x07});
        ProductMetadata metadata3 = new ProductMetadata(typeImage, ref3,
                Constants.IMAGE_DATA_3_CREATION_DATE,
                null, null,
                contentTimeWindow3, attributes3, "description", null, null);
        super.addNewProduct(ref3, productBody3, metadata3);
    }

    @Override
    public int getMaximumNumberOfResults() {
        return 10;
    }
}
