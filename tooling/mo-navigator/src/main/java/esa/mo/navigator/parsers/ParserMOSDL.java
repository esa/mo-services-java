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
package esa.mo.navigator.parsers;

import de.dlr.gsoc.mcds.mosdl.MOSDLLexer;
import de.dlr.gsoc.mcds.mosdl.MOSDLParser;
import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.ccsds.schema.serviceschema.SpecificationType;

/**
 * The XMLParser class parses the XML from a text field.
 *
 * @author Cesar Coelho
 */
public class ParserMOSDL {

    /**
     * The parseXML method parses a file and returns the set of ParsedLines.
     *
     * @param text The text
     * @return The parsed Data
     * @throws java.io.IOException
     */
    public static SpecificationType parseMOSDL(String text) throws IOException {
        // Find and replace! Java comments style to the classic Stefan style
        text = text.replace("/**", "\"\"\"").replace(" * ", "").replace(" **/", "\"\"\"");

        SpecificationType spec = new SpecificationType();
        boolean isLaxMode = true;
        MosdlSpecLoader.ParseListener parseListener = new MosdlSpecLoader.ParseListener(spec, isLaxMode);
        MosdlSpecLoader.ErrorListener errorListener = new MosdlSpecLoader.ErrorListener(isLaxMode);

        try {
            CharStream input = CharStreams.fromString(text);
            MOSDLLexer lexer = new MOSDLLexer(input);
            lexer.removeErrorListeners(); // remove default listener
            lexer.addErrorListener(errorListener);
            MOSDLParser parser = new MOSDLParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners(); // remove default listener
            parser.addErrorListener(errorListener);
            ParseTree parseTree = parser.area();
            ParseTreeWalker.DEFAULT.walk(parseListener, parseTree);
        } catch (RecognitionException ex) {
            throw new IOException("1. The text is not valid!", ex);
        }

        return spec;
    }

}
