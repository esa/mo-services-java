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

import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;

/**
 * Interface to know when a new Product is added on the backend.
 */
public interface NewProductAddedListener {

    public void onNewProductAdded(ObjectRef<Product> ref, ProductMetadata metadata);
}
