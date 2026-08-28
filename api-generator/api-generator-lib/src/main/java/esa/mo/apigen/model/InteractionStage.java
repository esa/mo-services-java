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

/**
 * The stages a message can occupy, across all interaction patterns.
 * <p>
 * {@link #SUBSCRIPTION_KEYS} exists only in v003 specifications.
 */
public enum InteractionStage {
    SEND("send", "send"),
    SUBMIT("submit", "submit"),
    REQUEST("request", "request"),
    RESPONSE("response", "response"),
    INVOKE("invoke", "invoke"),
    ACK("ack", "acknowledgement"),
    PROGRESS("progress", "progress"),
    UPDATE("update", "update"),
    SUBSCRIPTION_KEYS("subscriptionKeys", "subscriptionKeys"),
    PUBLISH_NOTIFY("publishNotify", "publishNotify");

    private final String tag;
    private final String xmlName;

    private InteractionStage(String tag, String xmlName) {
        this.tag = tag;
        this.xmlName = xmlName;
    }

    /**
     * Returns the short name used for this stage in documentation tags, for example
     * "request" or "ack".
     *
     * @return the tag name.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Returns the XML element name for this stage. It is not always the tag name: the
     * schema spells the acknowledgement stage "acknowledgement".
     *
     * @return the element name.
     */
    public String getXmlName() {
        return xmlName;
    }

    /**
     * Returns the stage for an XML element name.
     *
     * @param xmlName The element name.
     * @return the stage, or null if the name is not a message stage.
     */
    public static InteractionStage fromXmlName(String xmlName) {
        for (InteractionStage stage : values()) {
            if (stage.xmlName.equals(xmlName)) {
                return stage;
            }
        }
        return null;
    }
}
