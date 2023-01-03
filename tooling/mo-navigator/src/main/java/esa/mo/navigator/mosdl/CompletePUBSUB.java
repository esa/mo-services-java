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
 * The CompletePUBSUB class contains auto-completes for the PUB-SUB Interaction
 * Pattern.
 *
 * @author Cesar Coelho
 */
public class CompletePUBSUB {

    public static ShorthandCompletion pubSub_1(DefaultCompletionProvider provider) {
        StringWriter txt = new StringWriter();
        txt.append("/**\n");
        txt.append("		 * The <operation-name> operation allows a provider to <insert-what-it-does>.\n");
        txt.append("		 * \n");
        txt.append("		 * @publishparam field1: The field1 field shall hold <insert-what-contains>.\n");
        txt.append("		 **/\n");
        txt.append("");
        txt.append("		pubsub *myOperation [x]  <- (field1: Long?)\n");

        return new ShorthandCompletion(provider, "pubsub",
                txt.toString(), "Template - Fields: 1");
    }

    public static ShorthandCompletion pubSub_2(DefaultCompletionProvider provider) {
        StringWriter txt = new StringWriter();
        txt.append("/**\n");
        txt.append("		 * The <operation-name> operation allows a provider to <insert-what-it-does>.\n");
        txt.append("		 * \n");
        txt.append("		 * @publishparam field1: The field1 field shall hold <insert-what-contains>.\n");
        txt.append("		 * @publishparam field2: The field2 field shall hold <insert-what-contains>.\n");
        txt.append("		 **/\n");
        txt.append("");
        txt.append("		pubsub *myOperation [x]  <- (field1: Long?, field2: List?<Identifier>)\n");

        return new ShorthandCompletion(provider, "pubsub",
                txt.toString(), "Template - Fields: 2");
    }

}
