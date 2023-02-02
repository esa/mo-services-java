/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Testbed ESA provider
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
package esa.mo.com.test.archive;

import org.ccsds.moims.mo.com.test.provider.TestServiceProvider;
import org.ccsds.moims.mo.comprototype.archivetest.provider.ArchiveTestInheritanceSkeleton;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;

/**
 *
 */
public class ArchiveTestHandlerImpl extends ArchiveTestInheritanceSkeleton {

    private final TestServiceProvider testService;
    ArchiveHandlerImpl archiveHandler;

    public ArchiveTestHandlerImpl(TestServiceProvider testService, ArchiveHandlerImpl archiveHandler) {
        this.testService = testService;
        this.archiveHandler = archiveHandler;
    }

    public void reset(MALInteraction interaction) throws MALInteractionException, MALException {
        archiveHandler.reset();
    }
}
