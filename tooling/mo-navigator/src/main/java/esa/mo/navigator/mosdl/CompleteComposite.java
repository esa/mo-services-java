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
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;

/**
 * The CompleteComposite class auto-completes a Composite data structure.
 *
 * @author Cesar Coelho
 */
public class CompleteComposite {

    public static ShorthandCompletion composite2(DefaultCompletionProvider provider) {
        StringWriter txt = new StringWriter();
        txt.append("	/// The <structure-name> structure holds the <what-it-holds>.\n");
        txt.append("	composite <structure-name> [X] extends Composite {\n");
        txt.append("		/// The name of the parameter.\n");
        txt.append("		name: Identifier\n");
        txt.append("		/// The type of the parameter.\n");
        txt.append("		type: String\n");
        txt.append("	}\n");

        return new ShorthandCompletion(provider, "composite",
                txt.toString(), "Template - Fields: 2");
    }

}
