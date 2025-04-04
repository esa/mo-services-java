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
package esa.mo.provider;

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mpd.Dataset;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductType;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * A dummy backend in order to try out the provider.
 */
public class DummyProductsBackend extends Dataset {

    public DummyProductsBackend() {
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        // ---------------------------------------------------
        // Product Types
        // ---------------------------------------------------
        Identifier name = new Identifier("image.eo.rgb");
        ProductType type1 = new ProductType(name, "An Earth Observation RGB image.", null);

        // ---------------------------------------------------
        // Products
        // ---------------------------------------------------
        TimeWindow contentDate = new TimeWindow(Time.now(), Time.now());
        ObjectIdentity productId = new ObjectIdentity(domain, new Identifier("key1"), new UInteger(1));
        Blob productBody = new Blob();
        ProductMetadata metadata = new ProductMetadata(type1, null, Time.now(), contentDate);
        Product product = new Product(productId, metadata, productBody);
        ObjectRef<Product> ref = product.getObjectRef();
        super.addNewProduct(ref, productBody, metadata);
    }

    @Override
    public int getMaximumNumberOfResults() {
        return 10;
    }
}
