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
package esa.mo.com.test.activity;

import org.ccsds.moims.mo.com.test.provider.TestServiceProvider;
import org.ccsds.moims.mo.comprototype.activitytest.provider.InvokeInteraction;
import org.ccsds.moims.mo.comprototype.activitytest.provider.ProgressInteraction;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.StringList;

/**
 *
 */
public class ActivityRelayInterceptor extends ActivityTestHandlerImpl
{
  private final ActivityRelayManagementHandlerImpl relayManager;

  public ActivityRelayInterceptor(TestServiceProvider testService, ActivityRelayManagementHandlerImpl relayManager)
  {
    super(testService);

    this.relayManager = relayManager;
  }

  @Override
  public void send(StringList _StringList0, MALInteraction interaction) throws MALInteractionException, MALException
  {
    relayManager.passToRelay(null, _StringList0, interaction);

    super.send(_StringList0, interaction);
  }

  @Override
  public void testSubmit(StringList _StringList0, MALInteraction interaction) throws MALInteractionException, MALException
  {
    relayManager.passToRelay(null, _StringList0, interaction);

    super.testSubmit(_StringList0, interaction);
  }

  @Override
  public StringList request(StringList _StringList0, MALInteraction interaction) throws MALInteractionException, MALException
  {
    relayManager.passToRelay(null, _StringList0, interaction);

    return super.request(_StringList0, interaction);
  }

  @Override
  public void invoke(StringList _StringList0, InvokeInteraction interaction) throws MALInteractionException, MALException
  {
    relayManager.passToRelay(null, _StringList0, interaction.getInteraction());

    super.invoke(_StringList0, interaction);
  }

  @Override
  public void progress(StringList _StringList0, ProgressInteraction interaction) throws MALInteractionException, MALException
  {
    relayManager.passToRelay(null, _StringList0, interaction.getInteraction());

    super.progress(_StringList0, interaction);
  }
}
