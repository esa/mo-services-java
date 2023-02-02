/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA MO Navigator
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
package esa.mo.navigator;

import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.UIManager;

/**
 * Class with the User Interface Look and Feel code
 *
 * @author Cesar Coelho
 */
public class UILookAndFeel {

    public static void adaptFontSizeToDisplay() {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font) {
                Font oldFont = (Font) value;
                int defaultWidth = 1280;
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int width = gd.getDisplayMode().getWidth();
                float ratio = ((float) width / (float) defaultWidth);
//                int size = (int) (oldFont.getSize() * ratio);
                int size = (int) (12 * ratio); // 12 is the default size to 1280!

                Font newFont = new Font(oldFont.getName(), oldFont.getStyle(), size);
                UIManager.put(key, newFont);
            }
        }
    }

}
