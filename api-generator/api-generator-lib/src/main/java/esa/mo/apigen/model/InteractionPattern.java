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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The MAL interaction patterns, each with the stages its messages occupy.
 */
public enum InteractionPattern {
    SEND(InteractionStage.SEND),
    SUBMIT(InteractionStage.SUBMIT),
    REQUEST(InteractionStage.REQUEST, InteractionStage.RESPONSE),
    INVOKE(InteractionStage.INVOKE, InteractionStage.ACK, InteractionStage.RESPONSE),
    PROGRESS(InteractionStage.PROGRESS, InteractionStage.ACK,
            InteractionStage.UPDATE, InteractionStage.RESPONSE),
    PUBSUB(InteractionStage.SUBSCRIPTION_KEYS, InteractionStage.PUBLISH_NOTIFY);

    private final List<InteractionStage> stages;

    private InteractionPattern(InteractionStage... stages) {
        this.stages = Collections.unmodifiableList(Arrays.asList(stages));
    }

    /**
     * Returns the stages of this pattern, in message order.
     *
     * @return the stages, never null.
     */
    public List<InteractionStage> getStages() {
        return stages;
    }

    /**
     * Returns true if the pattern uses the given stage.
     *
     * @param stage The stage to look for.
     * @return true if the stage belongs to this pattern.
     */
    public boolean hasStage(InteractionStage stage) {
        return stages.contains(stage);
    }
}
