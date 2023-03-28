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
class InteractionPubSubMap {

    private final Map<String, MALPublishInteractionListener> publisherMap = new HashMap<>();
    private final Map<String, Map<String, MALInteractionListener>> errorMap = new HashMap<>();
    private final Map<StringPair, MALInteractionListener> notifyMap = new HashMap<>();

    public void registerPublishListener(final MessageDetails details, final MALPublishInteractionListener listener) {
        final String id = details.uriFrom.getValue();

        synchronized (publisherMap) {
            publisherMap.put(id, listener);
            MALContextFactoryImpl.LOGGER.log(Level.FINE, "Adding publisher: {0}", id);
        }
    }

    public MALPublishInteractionListener getPublishListener(final URI uri, final MALMessageHeader mshHdr) {
        MALPublishInteractionListener list;

        synchronized (publisherMap) {
            list = publisherMap.get(uri.getValue());
        }

        if (list != null) {
            MALContextFactoryImpl.LOGGER.log(Level.FINE, "Getting publisher: {0}", uri.getValue());
        }

        return list;
    }

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

            for (String e : errorMap.keySet()) {
                str.append("  >> ").append(e).append("\n");
            }

            str.append("Starting dump of notify map\n");

            for (StringPair e : notifyMap.keySet()) {
                str.append("  >> ").append(e).append("\n");
            }
        }

        MALContextFactoryImpl.LOGGER.info(str.toString());
    }

    public MALPublishInteractionListener getPublishListenerAndRemove(final URI uri, final MessageDetails details) {
        final String id = uri.getValue();
        MALPublishInteractionListener list;

        synchronized (publisherMap) {
            list = publisherMap.remove(id);
        }

        if (null != list) {
            MALContextFactoryImpl.LOGGER.log(Level.FINE, "Removing publisher: {0}", id);
        }

        return list;
    }

    public void registerNotifyListener(final MessageDetails details,
            final Subscription subscription, final MALInteractionListener list) {
        final String uri = details.endpoint.getURI().getValue();
        final String subId = subscription.getSubscriptionId().getValue();
        final StringPair id = new StringPair(uri, subId);

        synchronized (notifyMap) {
            notifyMap.put(id, list);
            Map<String, MALInteractionListener> ent = errorMap.get(uri);

            if (null == ent) {
                ent = new HashMap<>();
                errorMap.put(uri, ent);
            }

            MALContextFactoryImpl.LOGGER.log(Level.FINE,
                    "PubSubMap({0}), adding notify handler: {1} : {2} : {3}",
                    new Object[]{this, uri, subId, list}
            );
            ent.put(subId, list);
        }
    }

    public MALInteractionListener getNotifyListener(final URI uri, final Identifier subscription) {
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

    public Map<String, MALInteractionListener> getNotifyListenersAndRemove(final URI uriValue) {
        synchronized (notifyMap) {
            final String uri = uriValue.getValue();
            final Map<String, MALInteractionListener> ent = errorMap.get(uri);

            if (ent != null) {
                for (Map.Entry<String, MALInteractionListener> e : ent.entrySet()) {
                    MALContextFactoryImpl.LOGGER.log(Level.FINE,
                            "PubSubMap({0}), removing notify handler: {1} : *",
                            new Object[]{this, uri}
                    );
                    notifyMap.remove(new StringPair(uri, e.getKey()));
                }
            }

            return ent;
        }
    }

    public void deregisterNotifyListener(final MessageDetails details,
            final IdentifierList unsubscriptions) {
        synchronized (notifyMap) {
            final String uri = details.endpoint.getURI().getValue();

            for (Identifier unsubscription : unsubscriptions) {
                final String unsubId = unsubscription.getValue();
                final StringPair id = new StringPair(uri, unsubId);

                if (notifyMap.containsKey(id)) {
                    MALContextFactoryImpl.LOGGER.log(Level.FINE,
                            "PubSubMap({0}), removing notify handler: {1} : {2}",
                            new Object[]{this, uri, unsubId}
                    );
                    notifyMap.remove(id);

                    final Map<String, MALInteractionListener> ent = errorMap.get(uri);
                    if (null != ent) {
                        ent.remove(unsubId);

                        if (ent.isEmpty()) {
                            errorMap.remove(uri);
                        }
                    }
                }
            }
        }
    }
}
