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

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductSummary;
import org.ccsds.moims.mo.mpd.structures.ProductType;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * A dummy backend in order to try out the provider.
 */
public class OneProductDataset extends Dataset {

    public ObjectRef<ProductType> productTypeRef1 = null;

    public OneProductDataset() {
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        // ---------------------------------------------------
        // Product Types
        // ---------------------------------------------------
        ObjectIdentity typeId1 = new ObjectIdentity(domain, new Identifier("image.eo.rgb"), new UInteger(1));
        ProductType type1 = new ProductType(typeId1, "An Earth Observation RGB image.");
        productTypeRef1 = type1.getObjectRef();
        productTypes.put(productTypeRef1, type1);

        // ---------------------------------------------------
        // Products
        // ---------------------------------------------------
        TimeWindow timeWindow = new TimeWindow(Time.now(), Time.now());
        ObjectIdentity productId = new ObjectIdentity(domain, new Identifier("key1"), new UInteger(1));
        Product product = new Product(productId, productTypeRef1,
                Time.now(), timeWindow, "description", new Blob());
        ObjectRef<Product> ref = product.getObjectRef();
        ProductSummary metadata = new ProductSummary();

        products.put(ref, product);
        metadatas.put(ref, metadata);
    }
}
