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

import javax.swing.JLabel;

/**
 * A simple class that sums the transmission delay over a set of parameter
 * updates and puts the average in a supplied label.
 *
 */
class DelayManager {

    private final JLabel label;
    private final long[] delays;
    private long totalDelay = 0;
    private int index = 0;

    public DelayManager(final JLabel label, final int size) {
        super();

        this.label = label;
        delays = new long[size];

        // cl;ear the delay set
        for (int i = 0; i < delays.length; i++) {
            delays[i] = 0;
        }
    }

    public synchronized void addDelay(final boolean displayTotal, final long delay) {
        final int i = index++;

        if (index >= delays.length) {
            index = 0;
        }

        // update the circular delay array and the total delay calculation
        final long oldDelay = delays[i];
        delays[i] = delay;
        totalDelay -= oldDelay;
        totalDelay += delay;

        if (displayTotal) {
            // turn the delay into mseconds and display
            final double del = ((double) totalDelay) / (1000.0 * delays.length);
            label.setText(String.valueOf(del));
        }
    }

    public synchronized void resetDelay() {
        totalDelay = 0;
        index = 0;

        for (int i = 0; i < delays.length; i++) {
            delays[i] = 0;
        }
    }
}
