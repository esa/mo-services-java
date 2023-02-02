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

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;

/**
 *
 * @author Cesar Coelho
 */
public class SearchToolbar extends JToolBar implements ActionListener {

    private final AtomicInteger currentInstance = new AtomicInteger(1);
    private final JLabel findingLabel = new JLabel("");
    private final JTextField searchField = new JTextField(30);
    private final JCheckBox regexCB = new JCheckBox("Regex");
    private final JCheckBox matchCaseCB = new JCheckBox("Match Case");
    private final RSyntaxTextArea textArea;

    public SearchToolbar(RSyntaxTextArea textArea) {
        this.textArea = textArea;
    }
    
    public JTextField getSearchField(){
        return searchField;
    }

    /**
     * Initializes the SearchToolbar class.
     */
    public void init() {
        this.add(searchField);
        this.add(findingLabel);
        this.add(new JLabel("    "));

        //JButton prevButton = new JButton("Find Previous");
        JButton prevButton = new JButton("▲");
        prevButton.setActionCommand("FindPrev");
        prevButton.addActionListener(this);
        this.add(prevButton);

        // final JButton nextButton = new JButton("Find Next");
        final JButton nextButton = new JButton("▼");
        nextButton.setActionCommand("FindNext");
        nextButton.addActionListener(this);
        this.add(nextButton);

        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                // Was the "Enter" Key pressed?
                if (arg0.getKeyCode() == 10) {
                    nextButton.doClick(0);
                } else {
                    liveSearching();
                }
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
        });

        this.add(regexCB);
        this.add(matchCaseCB);
    }

    private void liveSearching() {
        String text = searchField.getText();

        SearchContext context = new SearchContext();
        context.setSearchFor(text);
        context.setMatchCase(matchCaseCB.isSelected());
        context.setRegularExpression(regexCB.isSelected());
        context.setSearchForward(true);
        context.setWholeWord(false);

        // If new text, then just start the search from the top
        if (text.length() == 1) {
            textArea.setCaretPosition(0);
        }

        SearchResult searchResult = SearchEngine.find(textArea, context);

        if ("".equals(searchField.getText())) {
            findingLabel.setText("");
            return;
        }

        int totalCount = searchResult.getMarkedCount();
        this.setCounter(-1, totalCount);

        textArea.revalidate();

        if (totalCount > 0 && totalCount < 5000) {
            int counter = this.determineCurrentInstance(totalCount, text);
            currentInstance.set(counter);
            this.setCounter(counter, totalCount);
        }

        if (totalCount == 0) {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    public int determineCurrentInstance(int totalCount, String text) {
        long startTime = System.currentTimeMillis();
        SearchContext contextUp = new SearchContext();
        contextUp.setSearchFor(text);
        contextUp.setMatchCase(matchCaseCB.isSelected());
        contextUp.setRegularExpression(regexCB.isSelected());
        contextUp.setSearchForward(false);
        contextUp.setWholeWord(false);

        SearchContext contextDown = new SearchContext();
        contextDown.setSearchFor(text);
        contextDown.setMatchCase(matchCaseCB.isSelected());
        contextDown.setRegularExpression(regexCB.isSelected());
        contextDown.setSearchForward(true);
        contextDown.setWholeWord(false);

        SearchResult searchResultTop = SearchEngine.find(textArea, contextUp);

        // The long wait happens on the while and for loops!
        int counter = 0;
        // Count how many instances are above
        while (searchResultTop.wasFound()) {
            searchResultTop = SearchEngine.find(textArea, contextUp);
            counter++;
        }

        // Reposition the search highlight
        if (counter != 0) {
            for (int i = 0; i < counter - 1; i++) {
                SearchEngine.find(textArea, contextDown);
            }
        }
        long duration = System.currentTimeMillis() - startTime;

        // Edge case: When the cursor is above the first match
        counter = (counter == 0 && totalCount != 0) ? 1 : counter;

        Logger.getLogger(FindAndReplaceDemo.class.getName()).log(Level.WARNING,
                "The search took: {0} milliseconds", duration);

        return counter;
    }

    public final void requestFocusOnSearchField() {
        searchField.requestFocus();
    }

    public final void setCounter(int instance, int total) {
        String left = (instance == -1) ? "" : String.valueOf(instance) + " / ";
        String txt = "Found: " + left + String.valueOf(total);
        findingLabel.setText(txt);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // "FindNext" => search forward, "FindPrev" => search backward
        String command = e.getActionCommand();
        boolean forward = "FindNext".equals(command);
        String text = searchField.getText();
        this.find(text, forward);
    }

    public void find(String text, boolean forward) {
        // Create an object defining our search parameters.
        SearchContext context = new SearchContext();
        /*
        if (text.length() == 0) {
            return;
        }*/
        context.setSearchFor(text);
        context.setMatchCase(matchCaseCB.isSelected());
        context.setRegularExpression(regexCB.isSelected());
        context.setSearchForward(forward);
        context.setWholeWord(false);

        SearchResult searchResult = SearchEngine.find(textArea, context);
        int totalCounter = searchResult.getMarkedCount();
        boolean eof = !searchResult.wasFound();

        if (searchResult.getMarkedCount() == 0) {
            return;
        }

        int value = (forward) ? 1 : -1;
        int instance = this.currentInstance.addAndGet(value);

        if (eof && totalCounter != 0) {
            // Go back to the top or bottom!
            instance = (forward) ? 1 : totalCounter;
            this.currentInstance.set(instance);
            int caretPosition = (forward) ? 0 : textArea.getText().length();
            textArea.setCaretPosition(caretPosition);
            searchResult = SearchEngine.find(textArea, context);
        }

        this.setCounter(instance, searchResult.getMarkedCount());
    }

}
