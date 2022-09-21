/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Demo Application
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
package esa.mo.mal.demo.consumer;

/**
 * Class that extends the basic Swing label class to add in automatic handling
 * for displaying an update and also calculating the transmission delay for the
 * specific update. It also interacts with the DelayManager to calculate the
 * total delay.
 */
class ParameterValue {

    private final DelayManager delayManager;
    private final boolean isFirst;
    private short curValue = -1;
    private short labelValue = -1;
    private boolean inError = false;

    public ParameterValue(final int index, final DelayManager delayManager) {
        this.delayManager = delayManager;
        this.isFirst = (0 == index);
    }

    public short getLabelValue() {
        return labelValue;
    }

    public boolean isInError() {
        return inError;
    }

    public void setNewValue(final short newVal, final long iDiff) {
        delayManager.addDelay(isFirst, iDiff);

        boolean updatelabel = false;

        // work out whether we need to update the label and whether there has been a jump in the value greater than 1
        // in which case we need to make it in error
        if (-1 != curValue) {
            if (-35536 == newVal) {
                if (35535 != curValue) {
                    inError = true;
                }
            } else {
                if (1 < (newVal - curValue)) {
                    inError = true;
                }
            }

            // this complicated bit tried to work out whether the update we are receiving is in sequence in case we are
            // receiving them out of order. This happens when the provider is publishing too fast for the consumer or
            // transport technology to cope with.
            if ((0 > curValue) || (0 > newVal)) {
                if ((0 > curValue) && (0 > newVal)) {
                    // both -tive
                    if (curValue < newVal) {
                        updatelabel = true;
                    }
                } else {
                    if (0 > curValue) {
                        // current -tive, new is +ive
                        updatelabel = true;
                    } else {
                        // current +tive, new is -ive
                        if (curValue > 35500) {
                            updatelabel = true;
                        }
                    }
                }
            } else {
                // both +tive
                if (curValue < newVal) {
                    updatelabel = true;
                }
            }
        } else {
            updatelabel = true;
        }

        // display the new value
        if (updatelabel) {
            labelValue = newVal;
        }

        curValue = newVal;
    }

    public void reset() {
        inError = false;
        curValue = -1;
    }
}
