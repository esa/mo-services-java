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

import esa.mo.navigator.mosdl.AutoCompleteForMOSDL;
import esa.mo.navigator.mosdl.guis.SearchToolbar;
import esa.mo.navigator.parsers.GeneratorMOSDL;
import esa.mo.navigator.parsers.GeneratorXML;
import esa.mo.navigator.parsers.ParserMOSDL;
import esa.mo.navigator.parsers.ParserXML;
import esa.mo.tools.stubgen.GeneratorDocx;
import esa.mo.xsd.util.XmlHelper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.xml.bind.JAXBException;
import org.ccsds.schema.serviceschema.SpecificationType;
import org.fife.ui.autocomplete.AbstractCompletionProvider;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.folding.Fold;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * The AreaTabbedPane class contains three tabs for a certain file. These tabs
 * contain respectively the XML view, the MOSDL view, and the Book generator
 * tab.
 *
 * @author Cesar Coelho
 */
public class AreaTabbedPane extends JTabbedPane {

    private final static String DEFAULT_TEMP_DIR = "_temp";
    private final static String DEFAULT_DOCX_DIR = "_docx";
    private final static JLabel LABEL_GENERATING = new JLabel("Generating...");
    private final static JLabel LABEL_ERROR_GENERATION = new JLabel("The file could not be generated!");
    private final JButton openButton = new JButton("Open Folder");

    private final RTextScrollPane textEditorXML;
    private final RTextScrollPane textEditorMOSDL;
    private final String filepath;

    public AreaTabbedPane(String filepath) {
        this.filepath = filepath;
        JPanel panelXML = new JPanel();
        JPanel panelMOSDL = new JPanel();
        JPanel panelDocs = new JPanel();
        textEditorXML = createTextEditorXML(panelXML);
        textEditorMOSDL = createTextEditorMOSDL(panelMOSDL);

        try {
            String test = FileSupport.readFile(filepath); // Loads a file
            textEditorXML.getTextArea().setText(test);
        } catch (IOException ex) {
            Logger.getLogger(AreaTabbedPane.class.getName()).log(
                    Level.SEVERE, "The file could not be read!", ex);
        }

        JButton buttonA = this.createButtonA(textEditorXML, textEditorMOSDL);
        JButton buttonB = this.createButtonB(textEditorMOSDL, textEditorXML);

        panelXML.setLayout(new BorderLayout());
        panelXML.add(buttonA, BorderLayout.NORTH);
        panelXML.add(textEditorXML, BorderLayout.CENTER);

        panelMOSDL.setLayout(new BorderLayout());
        panelMOSDL.add(buttonB, BorderLayout.NORTH);
        panelMOSDL.add(textEditorMOSDL, BorderLayout.CENTER);

        JButton generateDocs = createButtonDocs(textEditorXML, panelDocs, true);
        panelDocs.add(generateDocs);

        this.add("XML View", panelXML);
        this.add("MOSDL View", panelMOSDL);
        this.add("Generate Documents", panelDocs);

    }

    private JButton createButtonA(RTextScrollPane textEditorFrom, RTextScrollPane textEditorTo) {
        JButton buttonA = new JButton("Convert to MOSDL");
        buttonA.addActionListener(new ConvertXMLToMOSDL(textEditorFrom, textEditorTo));
        return buttonA;
    }

    private JButton createButtonB(RTextScrollPane textEditorFrom, RTextScrollPane textEditorTo) {
        JButton buttonB = new JButton("Convert to XML");
        buttonB.addActionListener(new ConvertMOSDLToXML(textEditorFrom, textEditorTo));

        return buttonB;
    }

    private JButton createButtonDocs(RTextScrollPane textEditorFrom, JPanel panel, boolean all) {
        final JButton buttonB = new JButton("Generate Word document");
        buttonB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.remove(openButton);
                panel.remove(LABEL_ERROR_GENERATION);
                panel.add(LABEL_GENERATING);
                panel.revalidate();
                panel.repaint();

                long timestamp = System.currentTimeMillis();
                org.apache.maven.plugin.logging.SystemStreamLog logger = new org.apache.maven.plugin.logging.SystemStreamLog();
                GeneratorDocx generator = new GeneratorDocx(logger);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String userDir = System.getProperty("user.dir");
                String sourFolder = userDir + File.separator + DEFAULT_TEMP_DIR + File.separator + timeStamp;
                String destFolder = userDir + File.separator + DEFAULT_DOCX_DIR;
                HashMap<String, String> packageBindings = new HashMap();
                HashMap<String, String> extraProperties = new HashMap();

                try {
                    final File folder = new File(sourFolder);
                    folder.mkdirs();
                    generator.init(destFolder, true, true, packageBindings, extraProperties);

                    // Output the edited XML file in a temporary folder...
                    String tempFilePath = sourFolder + File.separator + timeStamp + "_temp.xml";
                    String text = textEditorFrom.getTextArea().getText();
                    FileSupport.writeFile(tempFilePath, text);

                    File xmlRefDirectory = new File(sourFolder);
                    List<Map.Entry<esa.mo.xsd.SpecificationType, XmlHelper.XmlSpecification>> specs = XmlHelper.loadSpecifications(xmlRefDirectory);

                    // now generator from each specification
                    for (Map.Entry<esa.mo.xsd.SpecificationType, XmlHelper.XmlSpecification> spec : specs) {
                        try {
                            generator.preProcess(spec.getKey());
                            generator.compile(destFolder, spec.getKey(), spec.getValue().rootElement);
                        } catch (Exception ex) {
                            Logger.getLogger(AreaTabbedPane.class.getName()).log(Level.INFO,
                                    "Exception thrown during the processing of XML file: "
                                    + spec.getValue().file.getPath(), ex);
                        }
                    }

                    openButton.addActionListener((new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                Desktop.getDesktop().open(new File(destFolder));
                            } catch (IOException ex) {
                                Logger.getLogger(AreaTabbedPane.class.getName()).log(
                                        Level.SEVERE, "The folder could not be opened!", ex);
                            }
                        }
                    }));

                    panel.remove(LABEL_GENERATING);
                    panel.add(openButton);
                    panel.revalidate();
                    panel.repaint();

                    timestamp = System.currentTimeMillis() - timestamp;
                    Logger.getLogger(AreaTabbedPane.class.getName()).log(Level.INFO,
                            "Success! Generated the Book in " + timestamp + " miliseconds!", text);
                    buttonB.revalidate();
                    buttonB.repaint();
                } catch (JAXBException ex) {
                    panel.remove(LABEL_GENERATING);
                    panel.add(LABEL_ERROR_GENERATION);
                    Logger.getLogger(AreaTabbedPane.class.getName()).log(
                            Level.SEVERE, "Something went wrong...", ex);
                } catch (Exception ex) {
                    panel.remove(LABEL_GENERATING);
                    panel.add(LABEL_ERROR_GENERATION);
                    Logger.getLogger(AreaTabbedPane.class.getName()).log(
                            Level.SEVERE, "Something went wrong...", ex);
                }
            }
        });

        return buttonB;
    }

    private RTextScrollPane createTextEditorXML(JPanel panelXML) {
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        textArea.setCodeFoldingEnabled(true);
        displayFindTextAreaOnPanel(textArea, panelXML);
        RTextScrollPane pane = new RTextScrollPane(textArea);
        addZoomingToScrollPane(pane);
        return pane;
    }

    private RTextScrollPane createTextEditorMOSDL(JPanel panelMOSDL) {
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        //textArea.setSyntaxEditingStyle(SyntaxConstants..SYNTAX_STYLE_CPLUSPLUS);
        textArea.setCodeFoldingEnabled(true);
        SyntaxScheme scheme = textArea.getSyntaxScheme();
        //scheme.setStyle(Token.COMMENT_DOCUMENTATION, new Style(Color.blue));
        scheme.setStyle(Token.COMMENT_DOCUMENTATION, scheme.getStyle(Token.COMMENT_EOL));
        textArea.revalidate();
        displayFindTextAreaOnPanel(textArea, panelMOSDL);

        RTextScrollPane textPane = new RTextScrollPane(textArea);
        addZoomingToScrollPane(textPane);
        CompletionProvider provider = AutoCompleteForMOSDL.createCompletionProvider();
        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(textArea);
        ac.setAutoCompleteEnabled(true);

        ac.setAutoActivationEnabled(true);
        ac.setAutoActivationDelay(100);
        ac.setAutoCompleteSingleChoices(false);
        ac.setParameterAssistanceEnabled(false);
        // Better yet, do this in createCompletionProvider() to avoid casting...
        ((AbstractCompletionProvider) provider).setAutoActivationRules(true, null);

        return textPane;
    }

    private static void addZoomingToScrollPane(final RTextScrollPane scrollPane) {
        MouseWheelListener listener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.isControlDown()) {
                    Font font = scrollPane.getTextArea().getFont();
                    // Is it zooming in or zooming out?
                    float diff = (e.getWheelRotation() < 0) ? 1.0f : -1.0f;
                    float size = font.getSize() + diff;
                    scrollPane.getTextArea().setFont(font.deriveFont(size));
                }
            }
        };

        scrollPane.addMouseWheelListener(listener);
    }

    private static void displayFindTextAreaOnPanel(final RSyntaxTextArea textArea, final JPanel panel) {
        final SearchToolbar toolbar = new SearchToolbar(textArea);
        final AtomicBoolean isvisible = new AtomicBoolean(false);
        toolbar.init();

        AbstractAction ctrlFAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isvisible.get()) {
                    panel.add(toolbar, BorderLayout.SOUTH);
                    isvisible.set(true);
                }
                toolbar.requestFocusOnSearchField();
                toolbar.getSearchField().selectAll();
                panel.revalidate();
                panel.repaint();
            }
        };

        AbstractAction escAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isvisible.get()) {
                    toolbar.find("", true); // Remove the highlight from the text
                    panel.remove(toolbar);
                    isvisible.set(false);
                    textArea.requestFocus();
                }
                panel.revalidate();
                panel.repaint();
            }
        };

        String actionNameFind = "ctrl find";
        String actionNameEsc = "esc";
        textArea.getActionMap().put(actionNameFind, ctrlFAction);
        textArea.getActionMap().put(actionNameEsc, escAction);
        toolbar.getSearchField().getActionMap().put(actionNameEsc, escAction);

        textArea.getInputMap().put(KeyStroke.getKeyStroke("control F"), actionNameFind);
        textArea.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), actionNameEsc);
        toolbar.getSearchField().getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), actionNameEsc);
    }

    public String getFilepath() {
        return filepath;
    }

    public String getXMLText() {
        return textEditorXML.getTextArea().getText();
    }

    private static class ConvertMOSDLToXML implements ActionListener {

        private final RTextScrollPane textEditorFrom;
        private final RTextScrollPane textEditorTo;

        public ConvertMOSDLToXML(RTextScrollPane textEditorFrom, RTextScrollPane textEditorTo) {
            this.textEditorFrom = textEditorFrom;
            this.textEditorTo = textEditorTo;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            long timestamp = System.currentTimeMillis();
            String text = textEditorFrom.getTextArea().getText();

            try {
                SpecificationType spec = ParserMOSDL.parseMOSDL(text);
                String newText = GeneratorXML.generateXML(spec);
                int caretPosition = textEditorTo.getTextArea().getCaretPosition();
                textEditorTo.getTextArea().setText(newText);
                textEditorTo.getTextArea().setCaretPosition(caretPosition);
                textEditorFrom.getTextArea().removeAllLineHighlights();

                timestamp = System.currentTimeMillis() - timestamp;
                Logger.getLogger(FileSupport.class.getName()).log(Level.INFO,
                        "Success! From MOSDL to XML in {0} miliseconds!",
                        new Object[]{timestamp}
                );
            } catch (IOException ex) {
                Logger.getLogger(AreaTabbedPane.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                String msg = ex.getMessage();

                if (msg != null) {
                    Integer lineNumber = Integer.parseInt(msg);
                    highlightLine(lineNumber, textEditorFrom);
                }

                Logger.getLogger(AreaTabbedPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static class ConvertXMLToMOSDL implements ActionListener {

        private final RTextScrollPane textEditorFrom;
        private final RTextScrollPane textEditorTo;

        public ConvertXMLToMOSDL(RTextScrollPane textEditorFrom, RTextScrollPane textEditorTo) {
            this.textEditorFrom = textEditorFrom;
            this.textEditorTo = textEditorTo;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            long timestamp = System.currentTimeMillis();
            String text = textEditorFrom.getTextArea().getText();

            try {
                SpecificationType spec = ParserXML.parseXML(text);
                GeneratorMOSDL generator = new GeneratorMOSDL(GeneratorMOSDL.DocType.BULK);
                String newText = generator.generateMOSDL(spec);

                int caretPosition = textEditorTo.getTextArea().getCaretPosition();
                textEditorTo.getTextArea().setText(newText);
                textEditorTo.getTextArea().setCaretPosition(caretPosition);
                textEditorFrom.getTextArea().removeAllLineHighlights();

                timestamp = System.currentTimeMillis() - timestamp;
                Logger.getLogger(AreaTabbedPane.class.getName()).log(Level.INFO,
                        "Success! From XML to MOSDL in {0} miliseconds!",
                        new Object[]{timestamp}
                );
            } catch (SAXException ex1) {
                if (ex1 instanceof SAXParseException) {
                    int lineNumber = ((SAXParseException) ex1).getLineNumber();
                    highlightLine(lineNumber, textEditorFrom);
                }
            } catch (JAXBException ex2) {
                if (ex2.getLinkedException() instanceof SAXParseException) {
                    SAXParseException sax = (SAXParseException) ex2.getLinkedException();
                    int lineNumber = sax.getLineNumber();

                    Logger.getLogger(AreaTabbedPane.class.getName()).log(
                            Level.WARNING, "The line with the error is: {0}", lineNumber);
                    highlightLine(lineNumber, textEditorFrom);
                } else {
                    String message = ex2.getCause().getMessage();
                    String localizedMessage = ex2.getCause().getLocalizedMessage();
                    String errotCode = ex2.getErrorCode();
                    Logger.getLogger(FileSupport.class.getName()).log(
                            Level.WARNING,
                            "Error"
                            + "\n--------------\n" + message
                            + "\n--------------\n" + localizedMessage
                            + "\n--------------\n" + errotCode
                            + "\n--------------",
                            ex2);
                }
            }
        }
    }

    public static void highlightLine(int lineNumber, RTextScrollPane editor) {
        int lineIndex = lineNumber - 1;

        try {
            RSyntaxTextArea rsta = (RSyntaxTextArea) editor.getTextArea();

            // Find the line with the fold to highlight the complete Fold
            ArrayList<Integer> linesToHighlight = new ArrayList<>();

            // Find all lines above
            for (int i = lineIndex; i > 0; i--) {
                Fold fold = rsta.getFoldManager().getFoldForLine(i);
                linesToHighlight.add(i);

                if (fold != null) {
                    break;
                }
            }

            // Find all lines below
            for (int i = lineIndex + 1; i < rsta.getLineCount(); i++) {
                Fold fold = rsta.getFoldManager().getFoldForLine(i);

                if (fold != null) {
                    break;
                }

                linesToHighlight.add(i);
            }

            for (Integer line : linesToHighlight) {
                rsta.addLineHighlight(line, Color.PINK);
            }

            rsta.revalidate();
            rsta.repaint();
        } catch (BadLocationException ex1) {
            Logger.getLogger(AreaTabbedPane.class.getName()).log(Level.SEVERE,
                    "The line number does not exist: " + lineIndex, ex1);
        }
    }
}
