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
package esa.mo.navigator.mosdl.guis;

import java.awt.*;
import javax.swing.*;

import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

/**
 * A simple example showing how to do search and replace in a RSyntaxTextArea.
 * The toolbar isn't very user-friendly, but this is just to show you how to use
 * the API.<p>
 *
 * This example uses RSyntaxTextArea 2.5.6.
 */
public class FindAndReplaceDemo extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private final RSyntaxTextArea textArea;
    
    public FindAndReplaceDemo() {
        JPanel cp = new JPanel(new BorderLayout());
        
        textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        cp.add(sp);

        // Create a toolbar with searching options:
        SearchToolbar toolbar = new SearchToolbar(textArea);
        toolbar.init();
        cp.add(toolbar, BorderLayout.NORTH);
        
        setContentPane(cp);
        setTitle("Find and Replace Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        // Start all Swing applications on the EDT.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    String laf = UIManager.getSystemLookAndFeelClassName();
                    UIManager.setLookAndFeel(laf);
                } catch (Exception e) {
                    /* never happens */ }
                FindAndReplaceDemo demo = new FindAndReplaceDemo();
                demo.setVisible(true);
                demo.textArea.requestFocusInWindow();
            }
        });
    }
    
}
