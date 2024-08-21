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

import java.util.HashMap;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mpd.backends.ProductRetrievalBackend;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductSummary;
import org.ccsds.moims.mo.mpd.structures.ProductSummaryList;

/**
 * A dummy backend in order to try out the provider.
 */
public class DummyProductsBackend implements ProductRetrievalBackend {

    private final HashMap<ObjectRef, Product> products = new HashMap();
    private final HashMap<ObjectRef, ProductSummary> metadatas = new HashMap();
    private final ProductSummaryList allMetadatas = new ProductSummaryList();

    public DummyProductsBackend() {
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        ObjectIdentity objectIdentity = new ObjectIdentity(domain, new Identifier("key1"), new UInteger(1));
        Product product = new Product();
        ObjectRef<Product> ref = product.getObjectRef();
        ProductSummary metadata = new ProductSummary();

        products.put(ref, product);
        metadatas.put(ref, metadata);
        allMetadatas.add(metadata);
    }

    @Override
    public ProductSummaryList getMetadataForAllProducts() {
        return allMetadatas;
    }

    @Override
    public Product getProduct(ObjectRef productRef) {
        return products.get(productRef);
    }

    @Override
    public ProductSummary getMetadata(ObjectRef productRef) {
        return metadatas.get(productRef);
    }

}
