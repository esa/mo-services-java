/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The interaction map is responsible for maintaining the information pertaining
 * to PubSub interactions for a MAL instance.
 */
public class InteractionPubSubMap {

    private final Map<String, MALPublishInteractionListener> publisherMap = new HashMap<>();
    private final Map<String, Map<String, MALInteractionListener>> notifyListenersMap = new HashMap<>();
    private final Map<StringPair, MALInteractionListener> notifyMap = new HashMap<>();

    /**
     * Registers a publish listener.
     *
     * @param uriFrom Publisher URI.
     * @param listener The MAL publish interaction listener.
     */
    public void registerPublishListener(final String uriFrom, final MALPublishInteractionListener listener) {
        synchronized (publisherMap) {
            publisherMap.put(uriFrom, listener);
            MALContextFactoryImpl.LOGGER.log(Level.FINE, "Adding publisher: {0}", uriFrom);
        }
    }

    public MALPublishInteractionListener getPublishListener(final Identifier uri, final MALMessageHeader mshHdr) {
        MALPublishInteractionListener list;

        synchronized (publisherMap) {
            list = publisherMap.get(uri.getValue());
        }

        if (list != null) {
            MALContextFactoryImpl.LOGGER.log(Level.FINE, "Getting publisher: {0}", uri.getValue());
        }

        return list;
    }

    /**
     * Logs all publish listeners.
     */
    public void listPublishListeners() {
        StringBuilder str = new StringBuilder();
        str.append("listPublishListeners()\n");

        synchronized (publisherMap) {
            str.append("Starting dump of publisher map\n");

            for (String e : publisherMap.keySet()) {
                str.append("  >> ").append(e).append("\n");
            }
        }
        synchronized (notifyMap) {
            str.append("Starting dump of error map\n");

            for (String e : notifyListenersMap.keySet()) {
                str.append("  >> ").append(e).append("\n");
            }

            str.append("Starting dump of notify map\n");

            for (StringPair e : notifyMap.keySet()) {
                str.append("  >> ").append(e).append("\n");
            }
        }

        MALContextFactoryImpl.LOGGER.info(str.toString());
    }

    /**
     * Returns the MAL publish interaction listener and removes it from the publisher list.
     *
     * @param uri The MAL publish interaction listener URI.
     * @return The MAL publish interaction listener.
     */
    public MALPublishInteractionListener getPublishListenerAndRemove(final URI uri) {
        final String id = uri.getValue();
        MALPublishInteractionListener list;

        synchronized (publisherMap) {
            list = publisherMap.remove(id);
        }

        if (list != null) {
            MALContextFactoryImpl.LOGGER.log(Level.FINE, "Removing publisher: {0}", id);
        }

        return list;
    }

    /**
     * Registers a notify listener.
     *
     * @param uri   The URI.
     * @param subscription  The subscription.
     * @param list The MAL interaction listeners.
     */
    public void registerNotifyListener(final String uri,
            final Subscription subscription, final MALInteractionListener list) {
        final String subId = subscription.getSubscriptionId().getValue();
        final StringPair id = new StringPair(uri, subId);

        synchronized (notifyMap) {
            notifyMap.put(id, list);
            Map<String, MALInteractionListener> listeners = notifyListenersMap.get(uri);

            if (listeners == null) {
                listeners = new HashMap<>();
                notifyListenersMap.put(uri, listeners);
            }

            MALContextFactoryImpl.LOGGER.log(Level.FINE,
                    "PubSubMap({0}), adding notify handler: {1} : {2} : {3}",
                    new Object[]{this, uri, subId, list}
            );
            listeners.put(subId, list);
        }
    }

    public MALInteractionListener getNotifyListener(final Identifier uri, final Identifier subscription) {
        final StringPair id = new StringPair(uri.getValue(), subscription.getValue());

        MALContextFactoryImpl.LOGGER.log(Level.FINE,
                "PubSubMap({0}), looking for notify handler: {1} : {2}",
                new Object[]{this, uri, subscription}
        );
        synchronized (notifyMap) {
            if (notifyMap.containsKey(id)) {
                MALContextFactoryImpl.LOGGER.log(Level.FINE,
                        "PubSubMap({0}), found notify handler: {1} : {2}",
                        new Object[]{this, uri, subscription}
                );
                return notifyMap.get(id);
            }
        }

        MALContextFactoryImpl.LOGGER.log(Level.FINE,
                "PubSubMap({0}), failed to find notify handler: {1} : {2}",
                new Object[]{this, uri, subscription}
        );
        return null;
    }

    /**
     * Returns a MAP representation of the notified listeners and removes them.
     *
     * @param uri The URI.
     * @return The notified listeners.
     */
    public Map<String, MALInteractionListener> getNotifyListenersAndRemove(final String uri) {
        synchronized (notifyMap) {
            final Map<String, MALInteractionListener> listeners = notifyListenersMap.get(uri);

            if (listeners != null) {
                for (Map.Entry<String, MALInteractionListener> e : listeners.entrySet()) {
                    MALContextFactoryImpl.LOGGER.log(Level.FINE,
                            "PubSubMap({0}), removing notify handler: {1} : *",
                            new Object[]{this, uri}
                    );
                    notifyMap.remove(new StringPair(uri, e.getKey()));
                }
            }

            return listeners;
        }
    }

    /**
     * Removes the notified listeners.
     *
     * @param uri    The URI.
     * @param unsubscriptions  Notified listeners to unsubscribe.
     */
    public void deregisterNotifyListener(final String uri, final IdentifierList unsubscriptions) {
        synchronized (notifyMap) {
            for (Identifier unsubscription : unsubscriptions) {
                final String unsubId = unsubscription.getValue();
                final StringPair id = new StringPair(uri, unsubId);

                if (notifyMap.containsKey(id)) {
                    MALContextFactoryImpl.LOGGER.log(Level.FINE,
                            "PubSubMap({0}), removing notify handler: {1} : {2}",
                            new Object[]{this, uri, unsubId}
                    );
                    notifyMap.remove(id);

                    final Map<String, MALInteractionListener> listeners = notifyListenersMap.get(uri);
                    if (listeners != null) {
                        listeners.remove(unsubId);

                        if (listeners.isEmpty()) {
                            notifyListenersMap.remove(uri);
                        }
                    }
                }
            }
        }
    }
}
