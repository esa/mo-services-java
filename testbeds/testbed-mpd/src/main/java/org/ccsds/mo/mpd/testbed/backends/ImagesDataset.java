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

import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mpd.Dataset;
import org.ccsds.moims.mo.mpd.structures.*;

import java.time.Instant;

/**
 * A dummy backend in order to try out the provider.
 */
public class ImagesDataset extends Dataset {

    public final ProductType typeImage;

    private void addImgProduct1(IdentifierList domain) {
                /*
        - creationDate: <IMAGE_DATA_1_CREATION_DATE>	2022-01-22T20:19:06.728Z
        - timeWindow.start: <IMAGE_DATA_1__TIME_START>	2022-01-22T18:14:01.352Z
        - timeWindow.end: <IMAGE_DATA_1__TIME_END>	2022-01-22T20:18:10.539Z
        imageData1 lat/long	34.1949742,-118.1835993	Earth
Product name: product1 (with metadata1)
    - identity.domain: myDomain
    - identity.key: "imageData1"
    - identity.version: 1
    - productType: typeImage
    - creationDate: Time.now()
    - timeWindow.start: Time.now()
    - timeWindow.end: Time.now()
    - source: "string"
    - description: "description"
    - productBody: [0x01,0x02,0x03]
    - attributes: imageSubject["named target", coordinates_lat, coordinates_lon, imageType
Product metadata: metadata1
    - productType: typeImage
    - product: product1
    - creationDate: <IMAGE_DATA_1_CREATION_DATE>
    - timeWindow.start: <IMAGE_DATA_1__TIME_START>
    - timeWindow.end: <IMAGE_DATA_1__TIME_END>
    - source: "forest flyover"
    - attributes: imageSubject["Earth",coordinates[lat,lon]],imageType=visible
*/
///////////////////////////////////////////////////////////////////////////////////////////////
        // product1
        TimeWindow contentTimeWindow1 = new TimeWindow(
                new Time(Instant.parse("2022-01-22T18:14:01.352Z").toEpochMilli()),
                new Time(Instant.parse("2022-01-22T20:18:10.539Z").toEpochMilli()));
        NamedValueList attributes1 = new NamedValueList();
        attributes1.add(new NamedValue(new Identifier("ImageSubject"), new Union("Earth")));
        attributes1.add(new NamedValue(new Identifier("imageType"), new Union("visible")));
        attributes1.add(new NamedValue(new Identifier("coordinates.lat"), new Union(34.1949742)));
        attributes1.add(new NamedValue(new Identifier("coordinates.lon"), new Union(-118.1835993)));
        Blob productBody1 = new Blob(new byte[]{0x01, 0x02, 0x03});
        ObjectRef<Product> ref1 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("imageData1"), new UInteger(1));
        ProductMetadata metadata1 = new ProductMetadata(typeImage, ref1,
                new Time(Instant.parse("2022-01-22T20:19:06.728Z").toEpochMilli()),
                new Identifier("forest flyover"), null, contentTimeWindow1, attributes1,
                "description", null, null);
        super.addNewProduct(ref1, productBody1, metadata1);
    }

    private void addImgProduct2(IdentifierList domain) {
        /*
        - creationDate: <IMAGE_DATA_2_CREATION_DATE>	2018-02-04T07:07:04.145Z
        - timeWindow.start: <IMAGE_DATA_2__TIME_START>	2018-02-04T07:03:15.532Z
        - timeWindow.end: <IMAGE_DATA_2__TIME_END>	2018-02-04T07:03:15.532Z
imageData2 lat/long	42.0929981,-72.6084406	Earth
Product name: product2 (with metadata2)
    - identity.domain: myDomain
    - identity.key: "imageData2"
    - identity.version: 1
    - productType: typeImage
    - creationDate: Time.now()
    - timeWindow.start: Time.now()
    - timeWindow.end: Time.now()
    - source: "string"
    - description: "description"
    - productBody: [0x09,0x08,0x07]
    - attributes: imageSubject["named target", coordinates_lat, coordinates_lon, imageType
Product metadata: metadata2
    - productType: typeImage
    - product: product2
    - creationDate: <IMAGE_DATA_2_CREATION_DATE>
    - timeWindow.start: <IMAGE_DATA_2__TIME_START>
    - timeWindow.end: <IMAGE_DATA_2__TIME_END>
    - source: "take a photo"
    - attributes: imageSubject["Earth",coordinates[lat2,lon2]],imageType=infrared
 */
///////////////////////////////////////////////////////////////////////////////////////////////
        // product2
        TimeWindow contentTimeWindow2 = new TimeWindow(
                new Time(Instant.parse("2018-02-04T07:03:15.532Z").toEpochMilli()),
                new Time(Instant.parse("2018-02-04T07:03:15.532Z").toEpochMilli()));
        NamedValueList attributes2 = new NamedValueList();
        attributes2.add(new NamedValue(new Identifier("ImageSubject"), new Union("Earth")));
        attributes2.add(new NamedValue(new Identifier("imageType"), new Union("infrared")));
        attributes2.add(new NamedValue(new Identifier("coordinates.lat"), new Union(42.0929981)));
        attributes2.add(new NamedValue(new Identifier("coordinates.lon"), new Union(-72.6084406)));
        ObjectRef<Product> ref2 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("imageData2"), new UInteger(1));
        Blob productBody2 = new Blob(new byte[]{0x09, 0x08, 0x07});
        ProductMetadata metadata2 = new ProductMetadata(typeImage, ref2,
                new Time(Instant.parse("2018-02-04T07:07:04.145Z").toEpochMilli()),
                new Identifier("take a photo"), null, contentTimeWindow2, attributes2, "description", null, null);
        super.addNewProduct(ref2, productBody2, metadata2);
    }

    private void addImgProduct3(IdentifierList domain) {
        /*
        - creationDate: <IMAGE_DATA_2_CREATION_DATE>	2014-05-05T12:11:53.235Z
        - timeWindow.start: <IMAGE_DATA_2__TIME_START>	2014-05-05T08:14:35.642Z
        - timeWindow.end: <IMAGE_DATA_2__TIME_END>	2014-05-05T09:10:25.835Z
imageData3 lat/long	130W, 20N	Mars
Product name: product3 (with metadata3)
    - identity.domain: myDomain
    - identity.key: "imageData3"
    - identity.version: 1
    - productType: typeImage
    - creationDate: Time.now()
    - timeWindow.start: Time.now()
    - timeWindow.end: Time.now()
    - source: "string"
    - description: "description"
    - productBody: [0x09,0x08,0x07]
    - attributes: imageSubject["named target", coordinates_lat, coordinates_lon, imageType
Product metadata: metadata3
    - productType: typeImage
    - product: product3
    - creationDate: <IMAGE_DATA_3_CREATION_DATE>
    - source: NULL
    - timeWindow.start: <IMAGE_DATA_3__TIME_START>
    - timeWindow.end: <IMAGE_DATA_3__TIME_END>
    - attributes: imageSubject["Mars",coordinates[lat,lon]],imageType=infrared
 */
        TimeWindow contentTimeWindow3 = new TimeWindow(
                new Time(Instant.parse("2014-05-05T08:14:35.642Z").toEpochMilli()),
                new Time(Instant.parse("2014-05-05T09:10:25.835Z").toEpochMilli()));
        NamedValueList attributes3 = new NamedValueList();
        attributes3.add(new NamedValue(new Identifier("ImageSubject"), new Union("Mars")));
        attributes3.add(new NamedValue(new Identifier("imageType"), new Union("infrared")));
        attributes3.add(new NamedValue(new Identifier("coordinates.lat"), new Union(20)));
        attributes3.add(new NamedValue(new Identifier("coordinates.lon"), new Union(-130)));
        ObjectRef<Product> ref3 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("imageData3"), new UInteger(1));
        Blob productBody3 = new Blob(new byte[]{0x09, 0x08, 0x07});
        ProductMetadata metadata3 = new ProductMetadata(typeImage, ref3,
                new Time(Instant.parse("2014-05-05T12:11:53.235Z").toEpochMilli()),
                null, null,
                contentTimeWindow3, attributes3, "description", null, null);
        super.addNewProduct(ref3, productBody3, metadata3);
    }

    public ImagesDataset() {
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        // ---------------------------------------------------
        // Product Types
        // ---------------------------------------------------
        AttributeDefList attributeDefs = new AttributeDefList();
        attributeDefs.add(new AttributeDef(new Identifier("ImageSubject"), AttributeType.STRING));
        attributeDefs.add(new AttributeDef(new Identifier("imageType"), AttributeType.STRING));
        attributeDefs.add(new AttributeDef(new Identifier("coordinates.lat"), AttributeType.DOUBLE));
        attributeDefs.add(new AttributeDef(new Identifier("coordinates.lon"), AttributeType.DOUBLE));
        typeImage = new ProductType(new Identifier("typeImage"), "An Image type", attributeDefs);

        addImgProduct1(domain);
        addImgProduct2(domain);
        addImgProduct3(domain);
    }
}
