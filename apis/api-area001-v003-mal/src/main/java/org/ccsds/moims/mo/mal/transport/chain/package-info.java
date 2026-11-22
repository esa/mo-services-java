/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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

/**
 * Service Provider Interface for the composable transport chain.
 *
 * <p>
 * A transport is assembled from small composed interfaces rather than a
 * subclass of a generic base. The bottom of the chain is a {@link
 * org.ccsds.moims.mo.mal.transport.chain.LinkDriver} (the per-protocol provider
 * that declares its static capabilities and opens {@link
 * org.ccsds.moims.mo.mal.transport.chain.Link}s, each one ordered byte pipe to a
 * peer). Above it sits an ordered list of {@link
 * org.ccsds.moims.mo.mal.transport.chain.Stage}s, each performing one concern on
 * the way down (send) and reversing it on the way up (receive). The in-flight
 * unit threaded through the stages is an {@link
 * org.ccsds.moims.mo.mal.transport.chain.Exchange}.
 *
 * <p>
 * The chain builder, the thread pool and the ordered egress are the framework's
 * responsibility (mal-impl) and are not part of this SPI. The MAL-facing
 * boundary ({@code MALEndpoint} / {@code MALMessage}) is unchanged: the
 * framework presents a {@code MALEndpoint} facade on top of a chain, so the wire
 * format and the conformance contract are untouched.
 */
package org.ccsds.moims.mo.mal.transport.chain;
