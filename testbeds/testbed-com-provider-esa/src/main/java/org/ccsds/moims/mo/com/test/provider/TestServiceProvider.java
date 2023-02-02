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
package org.ccsds.moims.mo.com.test.provider;

import esa.mo.com.test.activity.ActivityRelayInterceptor;
import esa.mo.com.test.activity.ActivityRelayManagementHandlerImpl;
import esa.mo.com.test.activity.MonitorEventPublisherSkeleton;
import esa.mo.com.test.archive.ArchiveEventHandlerImpl;
import esa.mo.com.test.archive.ArchiveHandlerImpl;
import esa.mo.com.test.archive.ArchiveTestHandlerImpl;
import esa.mo.com.test.event.EventHandlerImpl;
import esa.mo.com.test.event.EventTestHandlerImpl;
import org.ccsds.moims.mo.com.test.suite.BaseCOMTestServiceProvider;
import org.ccsds.moims.mo.comprototype.eventtest.provider.EventTestDelegationSkeleton;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;

/**
 *
 */
public class TestServiceProvider extends BaseCOMTestServiceProvider {

    private final ActivityRelayInterceptor activityTestHandler;
    private final ActivityRelayManagementHandlerImpl activityRelayManagementHandler;
    private final EventTestHandlerImpl eventTestHandler;
    private final EventTestDelegationSkeleton eventTestDelegationSkel;
    private final ArchiveHandlerImpl archiveHandlerImpl;
    private final ArchiveTestHandlerImpl archiveTestHandlerImpl;
    private final MonitorEventPublisherSkeleton monitorEventPublisherSkeleton = new MonitorEventPublisherSkeleton();
    private final EventHandlerImpl eventHandlerImpl = new EventHandlerImpl();

    public TestServiceProvider() {
        eventTestHandler = new EventTestHandlerImpl(this);
        eventTestDelegationSkel = new EventTestDelegationSkeleton(eventTestHandler);
        activityRelayManagementHandler = new ActivityRelayManagementHandlerImpl(this);
        activityTestHandler = new ActivityRelayInterceptor(this, activityRelayManagementHandler);
        archiveHandlerImpl = new ArchiveHandlerImpl(this);
        archiveTestHandlerImpl = new ArchiveTestHandlerImpl(this, archiveHandlerImpl);
    }

    public MonitorEventPublisherSkeleton getActivityEventPublisher() {
        return monitorEventPublisherSkeleton;
    }

    @Override
    protected void initProviders() {
    }

    @Override
    protected MALInteractionHandler getActivityTestServiceHandler() {
        return activityTestHandler;
    }

    @Override
    protected MALInteractionHandler getActivityRelayManagementServiceHandler() {
        return activityRelayManagementHandler;
    }

    @Override
    protected MALInteractionHandler getActivityEventHandler() {
        return monitorEventPublisherSkeleton;
    }

    @Override
    protected MALInteractionHandler getEventTestServiceHandler() {
        return this.eventTestDelegationSkel;
    }

    @Override
    protected MALInteractionHandler getArchiveHandler() {
        return archiveHandlerImpl;
    }

    @Override
    protected MALInteractionHandler getArchiveTestHandler() {
        return archiveTestHandlerImpl;
    }

    @Override
    protected MALInteractionHandler getArchiveEventHandler() {
        // this doesn't seem correct to me
        return new ArchiveEventHandlerImpl();
    }

    @Override
    protected MALInteractionHandler getEventServiceHandler() {
        return eventHandlerImpl;
    }
}
