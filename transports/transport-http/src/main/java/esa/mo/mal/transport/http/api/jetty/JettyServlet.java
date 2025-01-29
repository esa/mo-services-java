/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO HTTP Transport Framework
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
package esa.mo.mal.transport.http.api.jetty;

import esa.mo.mal.transport.http.api.IContextHandler;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;

/**
 * An implementation of the javax.servlet.http.HttpServlet interface to be
 * embedded into JettyServer.
 */
public class JettyServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IContextHandler contextHandler = (IContextHandler) getServletContext().getAttribute("AbstractContextHandler");
        try {
            final Continuation continuation = ContinuationSupport.getContinuation(request);
            continuation.suspend(response);
            contextHandler.processRequest(new JettyHttpRequest(request));
            contextHandler.processResponse(new JettyHttpResponse(response, continuation));
            contextHandler.finishHandling();
        } catch (HttpApiImplException ex) {
            throw new IOException("JettyServlet: HttpApiImplException at doPost", ex);
        }
    }
}
