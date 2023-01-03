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
 * The CompleteIPs class contains auto-completes for all Interaction Patterns, 
 * with the exception of the PUB-SUB.
 * 
 * @author Cesar Coelho
 */
public class CompleteIPs {

    public static ShorthandCompletion submit_2(DefaultCompletionProvider provider) {
        StringWriter txt = new StringWriter();
        txt.append("/**\n");
        txt.append("		 * The <operation-name> operation allows a consumer to <insert-what-it-does>.\n");
        txt.append("		 * \n");
        txt.append("		 * @submitparam field1: The field1 field shall hold <insert-what-contains>.\n");
        txt.append("		 * @submitparam field2: The field2 field shall hold <insert-what-contains>.\n");
        txt.append("		 * \n");
        txt.append("		 * @error MAL::UNKNOWN: If the field1 is unknown in the provider.\n");
        txt.append("		 * @errorinfo MAL::UNKNOWN: The extra information field contains a list of <what the list contains>.\n");
        txt.append("		 **/\n");
        txt.append("		submit myOperationName [x] (field1: Long?, field2: Identifier?)\n");
        txt.append("			throws MAL::UNKNOWN: List<UInteger>\n");

        return new ShorthandCompletion(provider, "submit",
                txt.toString(), "Template - Fields: 2");
    }

    public static ShorthandCompletion request_1_1(DefaultCompletionProvider provider) {
        StringWriter txt = new StringWriter();
        txt.append("/**\n");
        txt.append("		 * The <operation-name> operation allows a consumer to <insert-what-it-does>.\n");
        txt.append("		 * \n");
        txt.append("		 * @requestparam field1: The field1 field shall hold <insert-what-contains>.\n");
        txt.append("		 * \n");
        txt.append("		 * @responseparam field2: The field2 field shall hold <insert-what-contains>.\n");
        txt.append("		 * \n");
        txt.append("		 * @error MAL::UNKNOWN: If the field1 is unknown in the provider.\n");
        txt.append("		 * @errorinfo MAL::UNKNOWN: The extra information field contains a list of <what the list contains>.\n");
        txt.append("		 **/\n");
        txt.append("		request myOperationName [x] (field1: List?<Identifier>)\n");
        txt.append("			-> (field2: List?<Integer>)\n");
        txt.append("			throws MAL::UNKNOWN: List<UInteger>\n");

        return new ShorthandCompletion(provider, "request",
                txt.toString(), "Template - Fields request: 1 - Fields response: 1");
    }

}
