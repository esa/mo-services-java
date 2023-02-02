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

import java.util.concurrent.atomic.AtomicLong;

/**
 * Class that generates a unique transaction id counter that can be used by the
 * MAL interactions for the transactionId field.
 *
 * The following constant defines a time epoch for the MAL transaction id. The
 * date is 1st September 2010 which is when the MAL was first published. It is
 * used to subtract from the UNIX time used as the seed for the transaction id.
 * It gives us an effective time range for transaction values from the MAL epoch
 * until approximately year 2045.
 * <br>
 * The transaction number is made up as follows:
 * <pre>
 *  |        (Part A)        |   (Part B)   |      (Part C)         |
 *  |        40  bits        |    8 bits    |      16  bits         |
 *  |  Time (ms resolution)  |  Randomness  |  Transaction Counter  |
 * </pre>
 */
public class TransactionIdCounter {

    private static final long MAL_EPOCH = 1283299200000L;
    private static final long MAX_OFFSET = 0xFFFFL;
    private static final long RANDOM_MASK = 0xFFL;

    private static long partAB = recalculatePartAB(); // Time with Randomness
    private static final AtomicLong transactionCounter = new AtomicLong(0);

    public static synchronized Long nextTransactionId() {
        long counter = transactionCounter.incrementAndGet();

        if (counter > MAX_OFFSET) {
            recalculatePartAB();
            transactionCounter.set(0);
        }

        return partAB + transactionCounter.get();
    }

    private static long recalculatePartAB() {
        partAB = (System.currentTimeMillis() - MAL_EPOCH) << 24; // Time
        partAB += ((System.nanoTime()) & RANDOM_MASK) << 16; // Randomness
        return partAB;
    }
}
