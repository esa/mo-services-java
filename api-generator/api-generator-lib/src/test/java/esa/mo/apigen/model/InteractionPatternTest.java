/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO API Generator
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
package esa.mo.apigen.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class InteractionPatternTest {

    @Test
    public void patternsCarryTheirStagesInMessageOrder() {
        assertEquals(java.util.Arrays.asList(InteractionStage.PROGRESS, InteractionStage.ACK,
                InteractionStage.UPDATE, InteractionStage.RESPONSE),
                InteractionPattern.PROGRESS.getStages());
    }

    @Test
    public void subscriptionKeysBelongToPubSub() {
        assertTrue(InteractionPattern.PUBSUB.hasStage(InteractionStage.SUBSCRIPTION_KEYS));
        assertFalse(InteractionPattern.REQUEST.hasStage(InteractionStage.SUBSCRIPTION_KEYS));
    }

    @Test
    public void aRequestHasNoAcknowledgement() {
        assertFalse(InteractionPattern.REQUEST.hasStage(InteractionStage.ACK));
        assertTrue(InteractionPattern.INVOKE.hasStage(InteractionStage.ACK));
    }
}
