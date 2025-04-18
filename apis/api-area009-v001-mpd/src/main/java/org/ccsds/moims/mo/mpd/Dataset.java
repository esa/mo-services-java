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
package org.ccsds.moims.mo.mpd;

import java.util.HashMap;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mpd.backends.ProductRetrievalBackend;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductMetadataList;

/**
 * A abstract class for all backend Datasets.
 */
public abstract class Dataset implements ProductRetrievalBackend {

    private final HashMap<ObjectRef, Blob> productBodies = new HashMap();
    private final HashMap<ObjectRef, ProductMetadata> metadatas = new HashMap();
    private ProductMetadataList allMetadatas = null;
    private NewProductAddedListener listener = null;

    @Override
    public ProductMetadataList getMetadataFromAllProducts() {
        if (allMetadatas == null || metadatas.size() != allMetadatas.size()) {
            allMetadatas = new ProductMetadataList();

            for (ProductMetadata metadata : metadatas.values()) {
                allMetadatas.add(metadata);
            }
        }
        return allMetadatas;
    }

    @Override
    public Product getProduct(ObjectRef productRef, boolean includesProductBody) {
        ProductMetadata metadata = getMetadata(productRef);

        if (metadata == null) {
            return null;
        }

        Blob productBody = (includesProductBody) ? productBodies.get(productRef) : null;
        ObjectIdentity objId = new ObjectIdentity(productRef.getDomain(),
                productRef.getKey(), productRef.getObjectVersion());

        return new Product(objId, metadata, productBody);
    }

    @Override
    public ProductMetadata getMetadata(ObjectRef productRef) {
        return metadatas.get(productRef);
    }

    @Override
    public void setNewProductAddedListener(NewProductAddedListener listener) {
        this.listener = listener;
    }

    public void addNewProduct(ObjectRef<Product> ref, Blob productBody, ProductMetadata metadata) {
        productBodies.put(ref, productBody);
        metadatas.put(ref, metadata);

        if (listener != null) {
            listener.onNewProductAdded(ref, metadata);
        }
    }
}
