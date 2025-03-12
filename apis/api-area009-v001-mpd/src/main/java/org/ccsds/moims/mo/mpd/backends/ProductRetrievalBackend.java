/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MPD services
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
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
package org.ccsds.moims.mo.mpd.backends;

import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mpd.NewProductAddedListener;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductMetadataList;

/**
 * The Backend interface to the Product Retrieval service.
 */
public interface ProductRetrievalBackend {

    /**
     * Retrieves the metadata from all available Products.
     *
     * @return The metadata from all available products.
     */
    public ProductMetadataList getMetadataFromAllProducts();

    /**
     * Returns the corresponding product for the given productRef or NULL if not
     * found.
     *
     * @param productRef The product reference.
     * @param includesProductBody Indicates if the returned Product includes the
     * product body or a null in its field.
     * @return The Product or NULL if not found.
     */
    public Product getProduct(ObjectRef productRef, boolean includesProductBody);

    /**
     * Returns the corresponding metadata for the given productRef or NULL if
     * not found.
     *
     * @param productRef The product reference.
     * @return The Metadata or NULL if not found.
     */
    public ProductMetadata getMetadata(ObjectRef productRef);

    /**
     * Sets the listener for the notifications of when new products are added.
     *
     * @param listener The listener.
     */
    public void setNewProductAddedListener(NewProductAddedListener listener);
}
