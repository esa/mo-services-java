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

import java.util.HashMap;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mpd.backends.ProductRetrievalBackend;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductSummary;
import org.ccsds.moims.mo.mpd.structures.ProductSummaryList;
import org.ccsds.moims.mo.mpd.structures.ProductType;

/**
 * A abstract class for all Datasets.
 */
public abstract class Dataset implements ProductRetrievalBackend {

    protected final HashMap<ObjectRef, ProductType> productTypes = new HashMap();
    protected final HashMap<ObjectRef, Product> products = new HashMap();
    protected final HashMap<ObjectRef, ProductSummary> metadatas = new HashMap();
    private ProductSummaryList allMetadatas = null;

    @Override
    public ProductSummaryList getMetadataFromAllProducts() {
        if (allMetadatas == null) {
            allMetadatas = new ProductSummaryList();

            for (ProductSummary metadata : metadatas.values()) {
                allMetadatas.add(metadata);
            }
        }
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
