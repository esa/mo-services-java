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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.fife.ui.rtextarea.RTextArea;

/**
 *
 * @author Cesar Coelho
 */
public class MiddlePanel extends javax.swing.JPanel {

    private final JLabel statusBar;
    private final static String DEFAULT_XMLS_DIR = "_xmls";
    private File currentFolder;

    /**
     * Creates new form MiddlePanel
     * @param statusBar The status bar to report status information.
     */
    public MiddlePanel(JLabel statusBar) {
        this.statusBar = statusBar;
        initComponents();
        tabsServices.removeAll();

        // Iterate through all the files in the xmls folder...
        this.setWorkingDirectory(new File(DEFAULT_XMLS_DIR));
    }

    public final void setWorkingDirectory(File folder) {
        currentFolder = folder;
        tabsServices.removeAll();

        if (!folder.isDirectory()) {
            openFolderWithXMLs();
        }

        ArrayList<String> filenames = new ArrayList<>();
        for (final File f : folder.listFiles()) {
            if (f.isFile()) {
                filenames.add(f.getName());
            }
        }

        for (String filename : filenames) {
            String location = currentFolder + File.separator + filename;
            tabsServices.add(filename, new AreaTabbedPane(location));
        }

        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(createMenuBar(), BorderLayout.NORTH);
        this.add(tabsServices, BorderLayout.CENTER);
    }

    private void openFolderWithXMLs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(currentFolder);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        switch (fileChooser.showOpenDialog(this)) {
            case JFileChooser.APPROVE_OPTION:
                // Open file...
                setWorkingDirectory(fileChooser.getSelectedFile());
                break;
        }
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open Folder with xmls...");
        open.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFolderWithXMLs();
            }
        }));

        JMenuItem save = new JMenuItem("Save the selected xml file");
        save.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusBar.setText("Saving...");
                AreaTabbedPane pane = (AreaTabbedPane) tabsServices.getSelectedComponent();

                try {  // Save the current edited xml
                    String filepath = pane.getFilepath();
                    String text = pane.getXMLText();
                    FileSupport.writeFile(filepath, text);
                    statusBar.setText("Saved on folder: " + filepath);
                } catch (IOException ex) {
                    Logger.getLogger(MiddlePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));

        fileMenu.add(open);
        fileMenu.add(save);
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.UNDO_ACTION)));
        editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.REDO_ACTION)));
        editMenu.addSeparator();
        editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.CUT_ACTION)));
        editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.COPY_ACTION)));
        editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.PASTE_ACTION)));
        editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.DELETE_ACTION)));
        editMenu.addSeparator();
        editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.SELECT_ALL_ACTION)));
        menuBar.add(editMenu);

        return menuBar;
    }

    private static JMenuItem createMenuItem(Action action) {
        JMenuItem item = new JMenuItem(action);
        item.setToolTipText(null); // Swing annoyingly adds tool tip text to the menu item
        return item;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabsServices = new javax.swing.JTabbedPane();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(0, 0));
        setLayout(new java.awt.BorderLayout());
        add(tabsServices, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabsServices;
    // End of variables declaration//GEN-END:variables

}
