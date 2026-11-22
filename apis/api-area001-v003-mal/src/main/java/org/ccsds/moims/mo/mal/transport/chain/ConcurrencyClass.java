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
package org.ccsds.moims.mo.mal.transport.chain;

/**
 * Declares how a {@link Stage} may be scheduled relative to other messages. The
 * framework's scheduler honours this when running the chain multi-threaded.
 */
public enum ConcurrencyClass {

    /**
     * The stage holds no state across messages, so different messages may run it
     * on different threads simultaneously. The default and most parallel class
     * (e.g. per-message encoding or per-message encryption).
     */
    STATELESS,

    /**
     * The stage reads immutable shared context (e.g. a fixed compression
     * dictionary) but mutates nothing per message, so it is still fully
     * parallel across messages.
     */
    SHARED_READ_ONLY,

    /**
     * The stage holds mutable per-channel state (e.g. a streaming compressor or
     * a sequence gate). Invocations for one channel are serialized in order;
     * different channels still run in parallel.
     */
    CHANNEL_SERIAL
}
