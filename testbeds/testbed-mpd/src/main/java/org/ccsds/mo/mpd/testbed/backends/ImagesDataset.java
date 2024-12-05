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
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mpd.structures.ParameterDef;
import org.ccsds.moims.mo.mpd.structures.ParameterDefList;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductType;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * A dummy backend in order to try out the provider.
 */
public class ImagesDataset extends Dataset {

    public ImagesDataset() {
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        // ---------------------------------------------------
        // Product Types
        // ---------------------------------------------------
        ObjectIdentity typeId1 = new ObjectIdentity(domain, new Identifier("Image"), new UInteger(1));
        ParameterDefList parameterDefs = new ParameterDefList();
        parameterDefs.add(new ParameterDef(new Identifier("ImageSubject"), AttributeType.STRING));
        parameterDefs.add(new ParameterDef(new Identifier("imageType"), AttributeType.STRING));
        ProductType typeImage = new ProductType(typeId1, "An Image type", parameterDefs);
        ObjectRef<ProductType> productTypeRef1 = typeImage.getObjectRef();
        productTypes.put(productTypeRef1, typeImage);

        // ---------------------------------------------------
        // Products
        // ---------------------------------------------------
        TimeWindow timeWindow = new TimeWindow(Time.now(), Time.now());

        // product1
        NamedValueList parameters1 = new NamedValueList();
        parameters1.add(new NamedValue(new Identifier("ImageSubject"), new Union("Earth")));
        parameters1.add(new NamedValue(new Identifier("imageType"), new Union("visible")));
        Blob productBody1 = new Blob(new byte[]{0x01, 0x02, 0x03});
        ObjectRef<Product> ref1 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("imageData1"), new UInteger(1));
        ProductMetadata metadata1 = new ProductMetadata(productTypeRef1, ref1, Time.now(), null, timeWindow, parameters1, "description");
        productBodies.put(ref1, productBody1);
        metadatas.put(ref1, metadata1);

        // product2
        NamedValueList parameters2 = new NamedValueList();
        parameters2.add(new NamedValue(new Identifier("ImageSubject"), new Union("Earth")));
        parameters2.add(new NamedValue(new Identifier("imageType"), new Union("infrared")));
        ObjectRef<Product> ref2 = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), new Identifier("imageData2"), new UInteger(1));
        Blob productBody2 = new Blob(new byte[]{0x09, 0x08, 0x07});
        ProductMetadata metadata2 = new ProductMetadata(productTypeRef1, ref2, Time.now(), null, timeWindow, parameters2, "description");
        productBodies.put(ref2, productBody2);
        metadatas.put(ref2, metadata2);
    }
}
