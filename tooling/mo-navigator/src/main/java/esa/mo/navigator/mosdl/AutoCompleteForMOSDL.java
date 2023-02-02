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
package esa.mo.navigator.mosdl;

import java.io.StringWriter;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;

/**
 * The ParserXML class parses the XML from a text field.
 *
 * @author Cesar Coelho
 */
public class AutoCompleteForMOSDL {

    /**
     * The createCompletionProvider returns a CompletionProvider class for the
     * MOSDL language.
     *
     * @return The CompletionProvider class
     */
    public static CompletionProvider createCompletionProvider() {
        // A DefaultCompletionProvider is the simplest concrete implementation
        // of CompletionProvider. This provider has no understanding of
        // language semantics. It simply checks the text entered up to the
        // caret position for a match against known completions. This is all
        // that is needed in the majority of cases.
        DefaultCompletionProvider provider = new DefaultCompletionProvider();

        // Add completions for all Java keywords. A BasicCompletion is just
        // a straightforward word completion.
        provider.addCompletion(new BasicCompletion(provider, "abstract"));
        provider.addCompletion(new BasicCompletion(provider, "area"));
        provider.addCompletion(new BasicCompletion(provider, "attribute"));
        //provider.addCompletion(new BasicCompletion(provider, "Boolean"));
        // provider.addCompletion(new BasicCompletion(provider, "Composite"));
        provider.addCompletion(new BasicCompletion(provider, "contains"));
        provider.addCompletion(new BasicCompletion(provider, "enum"));
        provider.addCompletion(new BasicCompletion(provider, "error"));
        provider.addCompletion(new BasicCompletion(provider, "extends"));
        provider.addCompletion(new BasicCompletion(provider, "fundamental"));
        provider.addCompletion(new BasicCompletion(provider, "instance"));

        // Data Types
        provider.addCompletion(new BasicCompletion(provider, "String"));
        provider.addCompletion(new BasicCompletion(provider, "Identifier"));
        provider.addCompletion(new BasicCompletion(provider, "UInteger"));
        provider.addCompletion(new BasicCompletion(provider, "Long"));
        provider.addCompletion(new BasicCompletion(provider, "List<String>"));
        provider.addCompletion(new BasicCompletion(provider, "List<Identifier>"));
        provider.addCompletion(new BasicCompletion(provider, "List<UInteger>"));
        provider.addCompletion(new BasicCompletion(provider, "List<Long>"));
        
        // Errors
        provider.addCompletion(new BasicCompletion(provider, "MAL::UNKNOWN"));
        provider.addCompletion(new BasicCompletion(provider, "COM::INVALID"));
        
        /*
         provider.addCompletion(new BasicCompletion(provider, "service"));
         provider.addCompletion(new BasicCompletion(provider, "capability"));
         */

        /*        
         provider.addCompletion(new BasicCompletion(provider, "send"));
         provider.addCompletion(new BasicCompletion(provider, "submit"));
         provider.addCompletion(new BasicCompletion(provider, "invoke"));
         provider.addCompletion(new BasicCompletion(provider, "request"));
         provider.addCompletion(new BasicCompletion(provider, "progress"));
         provider.addCompletion(new BasicCompletion(provider, "pubsub"));
         */
        StringWriter service_1 = new StringWriter();
        service_1.append("/**\n");
        service_1.append(" * The <service-name> service provides <what-it-provides>.\n");
        service_1.append(" * <description-of-the-service-second-line>\n");
        service_1.append(" **/ \n");
        service_1.append("service MyService [x] {\n");
        service_1.append("	capability [1] {\n");
        service_1.append("	\n");
        service_1.append("	}\n");
        service_1.append("	\n");
        service_1.append("}\n");

        StringWriter service_2 = new StringWriter();
        service_2.append("/**\n");
        service_2.append(" * The <service-name> service provides <what-it-provides>.\n");
        service_2.append(" * <description-of-the-service-second-line>\n");
        service_2.append(" **/ \n");
        service_2.append("service MyService [x] {\n");
        service_2.append("	capability [1] {\n");
        service_2.append("	\n");
        service_2.append("	}\n");
        service_2.append("	\n");
        service_2.append("	capability [2] {\n");
        service_2.append("	\n");
        service_2.append("	}\n");
        service_2.append("	\n");
        service_2.append("}\n");

        provider.addCompletion(new ShorthandCompletion(provider, "service",
                service_1.toString(), "Template - Capabilities: 1"));

        provider.addCompletion(new ShorthandCompletion(provider, "service",
                service_2.toString(), "Template - Capabilities: 2"));

        // All Operations, except PUB-SUB
        provider.addCompletion(CompleteIPs.submit_2(provider));
        provider.addCompletion(CompleteIPs.request_1_1(provider));
        
        // PUB-SUB
        provider.addCompletion(CompletePUBSUB.pubSub_1(provider));
        provider.addCompletion(CompletePUBSUB.pubSub_2(provider));

        // Composites
        provider.addCompletion(CompleteComposite.composite2(provider));
        
        return provider;
    }

}
