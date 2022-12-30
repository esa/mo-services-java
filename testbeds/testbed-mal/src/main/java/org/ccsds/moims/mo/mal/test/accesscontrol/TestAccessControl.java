/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
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
package org.ccsds.moims.mo.mal.test.accesscontrol;

import java.util.Vector;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControl;
import org.ccsds.moims.mo.mal.accesscontrol.MALCheckErrorException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.malprototype.errortest.ErrorTestHelper;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class TestAccessControl extends LoggingBase implements MALAccessControl {

    private final Vector msgVec = new Vector();
    private String errorPrefix = "remote ";
    private boolean localRejections = true;
    private boolean localModifications = false;
    private boolean msgLogging = false;

    public TestAccessControl() {
        logMessage("Test security manager created");
    }

    public MALMessage check(MALMessage msg) throws MALCheckErrorException {
        if (msgLogging) {
            MsgDetails x = null;

            if ((null != msg) && (null != msg.getHeader())) {
                logMessage(msg.getHeader().toString());

                byte stage;
                if (msg.getHeader().getInteractionStage() != null) {
                    stage = (byte) msg.getHeader().getInteractionStage().getValue();
                } else {
                    stage = -1;
                }
                try {
                    x = new MsgDetails((int) msg.getHeader().getInteractionType().getNumericValue().getValue(), stage,
                            msg.getHeader().getAuthenticationId().getValue(), msg.getHeader().getIsErrorMessage().booleanValue());
                } catch (MALException exc) {
                    throw new MALCheckErrorException(new MALStandardError(MALHelper.AUTHENTICATION_FAIL_ERROR_NUMBER,
                            new Union("Could not get the authentication id: " + exc)),
                            msg.getQoSProperties());
                }
            }

            msgVec.add(x);
        }

        if (localRejections) {
            if (msg.getHeader().getServiceArea().getValue() == ErrorTestHelper.ERRORTEST_SERVICE.getArea().getNumber().getValue()
                    && msg.getHeader().getService().getValue() == ErrorTestHelper._ERRORTEST_SERVICE_NUMBER
                    && msg.getHeader().getInteractionStage().getValue() == 1) {
                if (msg.getHeader().getOperation().getValue() == ErrorTestHelper._TESTAUTHENTICATIONFAILURE_OP_NUMBER) {
                    logMessage("Rejecting operation " + ErrorTestHelper.TESTAUTHENTICATIONFAILURE_OP.getName().getValue());
                    throw new MALCheckErrorException(new MALStandardError(MALHelper.AUTHENTICATION_FAIL_ERROR_NUMBER,
                            new Union(errorPrefix + "Rejecting operation " + ErrorTestHelper.TESTAUTHENTICATIONFAILURE_OP.getName().getValue())),
                            msg.getQoSProperties());
                }

                if (msg.getHeader().getOperation().getValue() == ErrorTestHelper._TESTAUTHORIZATIONFAILURE_OP_NUMBER) {
                    logMessage("Rejecting operation " + ErrorTestHelper.TESTAUTHORIZATIONFAILURE_OP.getName().getValue());
                    throw new MALCheckErrorException(new MALStandardError(MALHelper.AUTHORISATION_FAIL_ERROR_NUMBER,
                            new Union(errorPrefix + "Rejecting operation " + ErrorTestHelper.TESTAUTHORIZATIONFAILURE_OP.getName().getValue())),
                            msg.getQoSProperties());
                }
            }
        }

        if (localModifications) {
            byte[] authId;

            try {
                authId = msg.getHeader().getAuthenticationId().getValue();
            } catch (MALException exc) {
                throw new MALCheckErrorException(new MALStandardError(MALHelper.AUTHENTICATION_FAIL_ERROR_NUMBER,
                        new Union("Could not get the authentication id: " + exc)),
                        msg.getQoSProperties());
            }

            if ((null != authId) && (0 < authId.length)) {
                // The assumption here is that the authId is a byte representation of a UTF16 char string, so need to swap 16bit
                //   values
                byte[] nauthId = new byte[authId.length];

                final int count = (nauthId.length) - 1;
                for (int index = 0; index <= count; ++index) {
                    nauthId[index] = authId[count - index];
                }

                logMessage("Swapping authentication from " + new String(authId) + " to " + new String(nauthId));

                msg.getHeader().setAuthenticationId(new Blob(nauthId));
            }
        }

        return msg;
    }

    public void switchOnACFailures(boolean switchOn) {
        errorPrefix = "local ";
        localRejections = switchOn;
    }

    public void switchOnAuthenticationModifications(boolean switchOn) {
        localModifications = switchOn;
    }

    public void switchOnMessageLogging() {
        msgLogging = true;
    }

    public void resetMessageCount() {
        msgVec.clear();
    }

    public int messageCount() {
        return msgVec.size();
    }

    public void sortMessages() {
        java.util.Collections.sort(msgVec);
    }

    public int getMessageInteraction(int index) {
        if ((0 < index) && (index <= msgVec.size())) {
            MsgDetails d = (MsgDetails) msgVec.get(index - 1);

            if (null != d) {
                return d.interaction;
            }
        }

        return -1;
    }

    public byte getMessageStage(int index) {
        if ((0 < index) && (index <= msgVec.size())) {
            MsgDetails d = (MsgDetails) msgVec.get(index - 1);

            if (null != d) {
                return d.stage;
            }
        }

        return -1;
    }

    public byte[] getAuthenticationIdentifier(int index) {
        if ((0 < index) && (index <= msgVec.size())) {
            MsgDetails d = (MsgDetails) msgVec.get(index - 1);

            if (null != d) {
                return d.authId;
            }
        }

        return null;
    }

    public boolean isErrorMessage(int index) {
        if ((0 < index) && (index <= msgVec.size())) {
            MsgDetails d = (MsgDetails) msgVec.get(index - 1);

            if (null != d) {
                return d.error;
            }
        }

        return true;
    }

    public void switchOffMessageLogging() {
        msgLogging = false;
    }

    private final class MsgDetails implements Comparable {

        public final int interaction;
        public final byte stage;
        public final byte[] authId;
        public final boolean error;

        public MsgDetails(int interaction, byte stage, byte[] authId, boolean error) {
            this.interaction = interaction;
            this.stage = stage;
            this.authId = authId;
            this.error = error;
        }

        public int compareTo(Object o) {
            MsgDetails other = (MsgDetails) o;

            if (interaction == other.interaction) {
                return new Integer(stage).compareTo(new Integer(other.stage));
            }

            return new Integer(interaction).compareTo(new Integer(other.interaction));
        }
    }
}
