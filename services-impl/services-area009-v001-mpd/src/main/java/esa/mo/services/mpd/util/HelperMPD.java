/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MPD services
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
package esa.mo.services.mpd.util;

import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * A utilities helper class.
 */
public class HelperMPD {

    private static final Logger LOGGER = Logger.getLogger(HelperMPD.class.getName());

    public static boolean isTimeWindowValid(TimeWindow window) {
        if (window != null) {
            Time start = window.getStart();
            Time end = window.getEnd();

            if (start != null && end != null) {
                if (end.getValue() < start.getValue()) { // Throw exception!
                    return false;
                }
            }
        }
        return true;
    }
}
