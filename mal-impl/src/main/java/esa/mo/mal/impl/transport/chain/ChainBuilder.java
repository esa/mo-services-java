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
package esa.mo.mal.impl.transport.chain;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.chain.Capability;
import org.ccsds.moims.mo.mal.transport.chain.InboundSink;
import org.ccsds.moims.mo.mal.transport.chain.Link;
import org.ccsds.moims.mo.mal.transport.chain.LinkDriver;
import org.ccsds.moims.mo.mal.transport.chain.Stage;
import org.ccsds.moims.mo.mal.transport.chain.Uplink;

/**
 * Assembles a {@link Chain} over a {@link LinkDriver}. Stages are added in
 * downward order (the order a sent message traverses, top first). At
 * {@link #build()} the builder resolves capabilities bottom-up: each stage's
 * {@link Stage#requires() required} capabilities must be provided by the link
 * plus every stage below it, otherwise the build fails. It then opens the link,
 * constructs the chain and wires the driver's inbound sink to the chain.
 */
public final class ChainBuilder {

    private final LinkDriver driver;
    private final List<Stage> stages = new ArrayList<Stage>();
    private final Map<String, Object> properties = new HashMap<String, Object>();
    private URI remote;
    private Uplink malDelivery;

    private ChainBuilder(final LinkDriver driver) {
        this.driver = driver;
    }

    /**
     * @param driver The link driver to build over. Must not be null.
     * @return A new builder.
     */
    public static ChainBuilder over(final LinkDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver must not be null");
        }
        return new ChainBuilder(driver);
    }

    /**
     * @param remote The peer URI to connect to. Must not be null.
     * @return This builder.
     */
    public ChainBuilder connectingTo(final URI remote) {
        this.remote = remote;
        return this;
    }

    /**
     * @param props Transport-specific properties, merged into the connect
     * properties. Must not be null.
     * @return This builder.
     */
    public ChainBuilder with(final Map<String, ?> props) {
        this.properties.putAll(props);
        return this;
    }

    /**
     * Adds a stage below the stages added so far (next in downward order).
     *
     * @param stage The stage to add. Must not be null.
     * @return This builder.
     */
    public ChainBuilder stage(final Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("stage must not be null");
        }
        this.stages.add(stage);
        return this;
    }

    /**
     * Adds several stages, in downward order.
     *
     * @param toAdd The stages to add. Must not be null and must not contain null.
     * @return This builder.
     */
    public ChainBuilder stages(final Stage... toAdd) {
        for (final Stage s : toAdd) {
            stage(s);
        }
        return this;
    }

    /**
     * @param malDelivery Where fully processed inbound exchanges are delivered
     * (the MAL side). Must not be null.
     * @return This builder.
     */
    public ChainBuilder deliveringTo(final Uplink malDelivery) {
        this.malDelivery = malDelivery;
        return this;
    }

    /**
     * Resolves capabilities, opens the link and assembles the chain.
     *
     * @return The assembled chain.
     * @throws MALException If a required capability is missing or the link
     * cannot be opened.
     */
    public Chain build() throws MALException {
        if (malDelivery == null) {
            throw new IllegalStateException("a MAL delivery target is required");
        }
        resolveCapabilities();

        final Link link = driver.connect(remote, properties);
        final Chain chain = new Chain(stages, link, driver.capabilities(), malDelivery);
        driver.listen(new InboundSink() {
            @Override
            public void onFrame(final Link source, final ByteBuffer frame) {
                try {
                    chain.inbound(frame);
                } catch (MALException ex) {
                    throw new RuntimeException("Inbound chain processing failed", ex);
                }
            }
        });
        return chain;
    }

    /**
     * Verifies, from the bottom up, that every stage's required capabilities are
     * provided by the link plus the stages below it.
     */
    private void resolveCapabilities() throws MALException {
        final EnumSet<Capability> provided = EnumSet.noneOf(Capability.class);
        provided.addAll(driver.capabilities().asSet());
        for (int i = stages.size() - 1; i >= 0; i--) {
            final Stage stage = stages.get(i);
            if (!provided.containsAll(stage.requires().asSet())) {
                final EnumSet<Capability> missing = EnumSet.copyOf(stage.requires().asSet());
                missing.removeAll(provided);
                throw new MALException("Stage " + stage.getClass().getName()
                        + " requires capabilities not provided below it: " + missing
                        + " (available below: " + Collections.unmodifiableSet(provided) + ")");
            }
            provided.addAll(stage.provides().asSet());
        }
    }
}
