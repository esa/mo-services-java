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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionIdCounterTest {

    @Test
    public void idsAreStrictlyIncreasingSingleThreaded() {
        int n = 1000;
        long prev = TransactionIdCounter.nextTransactionId();
        for (int i = 0; i < n - 1; i++) {
            long next = TransactionIdCounter.nextTransactionId();
            assertTrue("IDs must be strictly increasing: " + prev + " >= " + next, next > prev);
            prev = next;
        }
    }

    @Test
    public void idsAreUniqueSingleThreaded() {
        int n = 2000;
        Set<Long> ids = new HashSet<>(n);
        for (int i = 0; i < n; i++) {
            Long id = TransactionIdCounter.nextTransactionId();
            assertTrue("Duplicate ID: " + id, ids.add(id));
        }
        assertEquals(n, ids.size());
    }

    @Test
    public void idsAreUniqueUnderConcurrentAccess() throws InterruptedException {
        int threads = 16;
        int idsPerThread = 500;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        Set<Long> allIds = Collections.newSetFromMap(new ConcurrentHashMap<>());
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);
        AtomicInteger duplicateCount = new AtomicInteger(0);

        for (int t = 0; t < threads; t++) {
            executor.submit(() -> {
                try {
                    start.await();
                    for (int i = 0; i < idsPerThread; i++) {
                        Long id = TransactionIdCounter.nextTransactionId();
                        if (!allIds.add(id)) {
                            duplicateCount.incrementAndGet();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new AssertionError(e);
                } finally {
                    done.countDown();
                }
            });
        }
        start.countDown();
        assertTrue("All threads should finish within 30s", done.await(30, TimeUnit.SECONDS));
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));

        assertEquals("No duplicate IDs under concurrency", 0, duplicateCount.get());
        assertEquals(threads * idsPerThread, allIds.size());
    }

    @Test
    public void idsAreNonNullAndPositive() {
        for (int i = 0; i < 100; i++) {
            Long id = TransactionIdCounter.nextTransactionId();
            assertNotNull(id);
            assertTrue("ID must be positive", id > 0);
        }
    }
}
