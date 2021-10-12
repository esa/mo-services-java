/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Demo Application
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
package esa.mo.mal.demo.consumer;

import esa.mo.mal.demo.util.StructureHelper;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.EntityRequest;
import org.ccsds.moims.mo.mal.structures.EntityRequestList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.maldemo.MALDemoHelper;
import org.ccsds.moims.mo.maldemo.basicmonitor.BasicMonitorHelper;
import org.ccsds.moims.mo.maldemo.basicmonitor.consumer.BasicMonitorAdapter;
import org.ccsds.moims.mo.maldemo.basicmonitor.consumer.BasicMonitorStub;
import org.ccsds.moims.mo.maldemo.basicmonitor.structures.BasicComposite;
import org.ccsds.moims.mo.maldemo.basicmonitor.structures.BasicEnum;
import org.ccsds.moims.mo.maldemo.basicmonitor.structures.BasicUpdate;
import org.ccsds.moims.mo.maldemo.basicmonitor.structures.BasicUpdateList;

/**
 * This class provides a simple form for the control of the consumer.
 */
public class DemoConsumerGui extends javax.swing.JFrame {

    /**
     * Logger
     */
    public static final java.util.logging.Logger LOGGER
            = Logger.getLogger("org.ccsds.moims.mo.mal.demo.consumer");
    private final IdentifierList domain = new IdentifierList();
    private final Identifier network = new Identifier("GROUND");
    private final SessionType session = SessionType.LIVE;
    private final Identifier sessionName = new Identifier("LIVE");
    private final ParameterLabel[] labels;
    private final Subscription subRequestWildcard;
    private final Subscription subRequestHalf;
    private final Subscription subRequestAll;
    private final DelayManager delayManager;
    private final DemoConsumerAdapter adapter = new DemoConsumerAdapter();
    private MALContextFactory malFactory;
    private MALContext mal;
    private MALConsumerManager consumerMgr;
    private MALConsumer tmConsumer = null;
    private BasicMonitorStub demoService = null;
    private boolean running = true;
    private boolean registered = false;

    /**
     * Main command line entry point.
     *
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        try {
            final Properties sysProps = System.getProperties();

            final File file = new File(System.getProperty("provider.properties", "demoConsumer.properties"));
            if (file.exists()) {
                sysProps.putAll(StructureHelper.loadProperties(file.toURI().toURL(), "provider.properties"));
            }

            System.setProperties(sysProps);

            final String name = System.getProperty("application.name", "DemoServiceConsumer");
            final Integer parametersNum = Integer.parseInt(System.getProperty("esa.mo.mal.demo.consumer.numparams", "512"));

            final DemoConsumerGui gui = new DemoConsumerGui(name, parametersNum);
            gui.init();

            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    gui.setVisible(true);
                }
            });
        } catch (MalformedURLException ex) {
            LOGGER.log(Level.SEVERE, "Exception thrown during initialisation of Demo Consumer {0}", ex);
        } catch (MALException ex) {
            LOGGER.log(Level.SEVERE, "Exception thrown during initialisation of Demo Consumer {0}", ex);
        }
    }

    /**
     * Creates new form DemoConsumerGui
     *
     * @param name The name to display on the title bar of the form.
     * @param parameterNum Number of parameters to display.
     */
    public DemoConsumerGui(final String name, Integer parameterNum) {
        labels = new ParameterLabel[parameterNum];
        initComponents();

        this.setTitle(name);

        delayManager = new DelayManager(delayLabel, 16);

        final java.awt.Dimension dim = new java.awt.Dimension(64, 16);
        for (int i = 0; i < labels.length; ++i) {
            labels[i] = new ParameterLabel(i, delayManager);
            labels[i].setMinimumSize(dim);
            labels[i].setPreferredSize(dim);
            labels[i].setMaximumSize(dim);
            labels[i].setOpaque(true);
            labels[i].setBackground(Color.BLACK);
            labels[i].setForeground(Color.GREEN);
            labels[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            this.jPanel1.add(labels[i]);
        }

        domain.add(new Identifier("esa"));
        domain.add(new Identifier("mission"));

        final Identifier subscriptionId = new Identifier("SUB");
        // set up the wildcard subscription
        {
            // final EntityKey entitykey = new EntityKey(new Identifier("*"), 0L, 0L, 0L);
            final EntityKey entitykey = new EntityKey(new NamedValueList());

            final EntityKeyList entityKeys = new EntityKeyList();
            entityKeys.add(entitykey);

            final EntityRequest entity = new EntityRequest(null, false, false, false, false, entityKeys);

            final EntityRequestList entities = new EntityRequestList();
            entities.add(entity);

            subRequestWildcard = new Subscription(subscriptionId, entities);
        }
        // set up the named first half subscription
        {
            final EntityKeyList entityKeys = new EntityKeyList();

            for (int i = 0; i < (labels.length / 2); i++) {
                NamedValueList subkeys = new NamedValueList();
                subkeys.add(new NamedValue(new Identifier("key1"), new Identifier(String.valueOf(i))));
                subkeys.add(new NamedValue(new Identifier("key2"), null));
                subkeys.add(new NamedValue(new Identifier("key3"), null));
                subkeys.add(new NamedValue(new Identifier("key4"), null));
                EntityKey entitykey = new EntityKey(subkeys);
                //final EntityKey entitykey = new EntityKey(new Identifier(String.valueOf(i)), 0L, 0L, 0L);
                entityKeys.add(entitykey);
            }

            final EntityRequest entity = new EntityRequest(null, false, false, false, false, entityKeys);

            final EntityRequestList entities = new EntityRequestList();
            entities.add(entity);

            subRequestHalf = new Subscription(subscriptionId, entities);
        }
        // set up the named all subscription
        {
            final EntityKeyList entityKeys = new EntityKeyList();

            for (int i = 0; i < labels.length; i++) {
                // final EntityKey entitykey = new EntityKey(new Identifier(String.valueOf(i)), 0L, 0L, 0L);
                NamedValueList subkeys = new NamedValueList();
                subkeys.add(new NamedValue(new Identifier("key1"), new Identifier(String.valueOf(i))));
                subkeys.add(new NamedValue(new Identifier("key2"), null));
                subkeys.add(new NamedValue(new Identifier("key3"), null));
                subkeys.add(new NamedValue(new Identifier("key4"), null));
                EntityKey entitykey = new EntityKey(subkeys);
                entityKeys.add(entitykey);
            }

            final EntityRequest entity = new EntityRequest(null, false, false, false, false, entityKeys);

            final EntityRequestList entities = new EntityRequestList();
            entities.add(entity);

            subRequestAll = new Subscription(subscriptionId, entities);
        }
    }

    private void init() throws MALException, MalformedURLException {
        loadURIs();

        malFactory = MALContextFactory.newFactory();
        mal = malFactory.createMALContext(System.getProperties());

        MALHelper.init(MALContextFactory.getElementFactoryRegistry());
        MALDemoHelper.init(MALContextFactory.getElementFactoryRegistry());
        BasicMonitorHelper.init(MALContextFactory.getElementFactoryRegistry());

        consumerMgr = mal.createConsumerManager();

        startService();

        Thread asyncSendThread = new Thread() {
            @Override
            public void run() {
                while (running) {
                    for (int i = 0; i < labels.length; ++i) {
                        labels[i].displayValue();
                    }

                    // sleep
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        // do nothing
                    }
                }
            }
        };

        asyncSendThread.start();
    }

    private void startService() throws MALException, MalformedURLException {
        // close old transport
        if (null != tmConsumer) {
            tmConsumer.close();
            resetErrorMenuItemActionPerformed(null);
        }

        loadURIs();

        final String tpuri = System.getProperty("uri");
        final String tburi = System.getProperty("broker");

        tmConsumer = consumerMgr.createConsumer((String) null,
                new URI(tpuri),
                new URI(tburi),
                BasicMonitorHelper.BASICMONITOR_SERVICE,
                new Blob("".getBytes()),
                domain,
                network,
                session,
                sessionName,
                QoSLevel.ASSURED,
                System.getProperties(),
                new UInteger(0));

        demoService = new BasicMonitorStub(tmConsumer);
    }

    private static void loadURIs() throws MalformedURLException {
        final java.util.Properties sysProps = System.getProperties();

        final String configFile = System.getProperty("providerURI.properties", 
                "demoServiceURI.properties");

        final java.io.File file = new java.io.File(configFile);
        if (file.exists()) {
            sysProps.putAll(StructureHelper.loadProperties(file.toURI().toURL(), 
                    "providerURI.properties"));
        }

        System.setProperties(sysProps);
    }

    private class DemoConsumerAdapter extends BasicMonitorAdapter {

        @Override
        public void monitorNotifyReceived(final MALMessageHeader msgHeader,
                final Identifier lIdentifier,
                final UpdateHeaderList lUpdateHeaderList,
                final BasicUpdateList lBasicUpdateList,
                final Map qosp) {
            LOGGER.log(Level.INFO, "Received update list of size : {0}", 
                    lBasicUpdateList.size());
            final long iDiff = System.currentTimeMillis() - msgHeader.getTimestamp().getValue();

            for (int i = 0; i < lBasicUpdateList.size(); i++) {
                final UpdateHeader updateHeader = lUpdateHeaderList.get(i);
                final BasicUpdate updateValue = lBasicUpdateList.get(i);
                NamedValueList lst = updateHeader.getKey().getSubkeys();
                if (lst.isEmpty()){
                    continue;
                }
                    
                final String name = ((Identifier) lst.get(0).getValue()).getValue();

                try {
                    final int index = Integer.parseInt(name);

                    if ((0 <= index) && (index < labels.length)) {
                        labels[index].setNewValue(updateValue.getCounter(), iDiff);
                    }
                } catch (NumberFormatException ex) {
                    LOGGER.log(Level.WARNING, 
                            "Error decoding update with name: {0}", name);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {

    subscriptionButtonGroup = new javax.swing.ButtonGroup();
    jToolBar1 = new javax.swing.JToolBar();
    jLabel1 = new javax.swing.JLabel();
    delayLabel = new javax.swing.JLabel();
    jPanel1 = new javax.swing.JPanel();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    quitMenuItem = new javax.swing.JMenuItem();
    jMenu2 = new javax.swing.JMenu();
    jMenu3 = new javax.swing.JMenu();
    regWildcardRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
    regHalfRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
    regAllRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
    deregMenuItem = new javax.swing.JMenuItem();
    jSeparator1 = new javax.swing.JSeparator();
    resetErrorMenuItem = new javax.swing.JMenuItem();
    jMenuItem1 = new javax.swing.JMenuItem();
    jMenu4 = new javax.swing.JMenu();
    returnBoolean = new javax.swing.JMenuItem();
    returnComposite = new javax.swing.JMenuItem();
    returnEnum = new javax.swing.JMenuItem();
    testSubmit = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setName("Form"); // NOI18N
    addWindowListener(new java.awt.event.WindowAdapter()
    {
      public void windowClosing(java.awt.event.WindowEvent evt)
      {
        formWindowClosing(evt);
      }
    });
    getContentPane().setLayout(new java.awt.BorderLayout(0, 4));

    jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
    jToolBar1.setFloatable(false);
    jToolBar1.setRollover(true);
    jToolBar1.setName("jToolBar1"); // NOI18N

    jLabel1.setText("Average Delay:");
    jLabel1.setName("jLabel1"); // NOI18N
    jToolBar1.add(jLabel1);

    delayLabel.setText("0.0");
    delayLabel.setName("delayLabel"); // NOI18N
    jToolBar1.add(delayLabel);

    getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

    jPanel1.setName("jPanel1"); // NOI18N
    jPanel1.setPreferredSize(new java.awt.Dimension(800, 600));
    jPanel1.setLayout(new java.awt.GridLayout(32, 16, 1, 1));
    getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

    jMenuBar1.setName("jMenuBar1"); // NOI18N

    jMenu1.setText("File");
    jMenu1.setName("jMenu1"); // NOI18N

    quitMenuItem.setText("Quit");
    quitMenuItem.setName("quitMenuItem"); // NOI18N
    quitMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        quitMenuItemActionPerformed(evt);
      }
    });
    jMenu1.add(quitMenuItem);

    jMenuBar1.add(jMenu1);

    jMenu2.setText("Consumer");
    jMenu2.setName("jMenu2"); // NOI18N

    jMenu3.setText("Register");
    jMenu3.setName("jMenu3"); // NOI18N

    subscriptionButtonGroup.add(regWildcardRadioButtonMenuItem);
    regWildcardRadioButtonMenuItem.setSelected(true);
    regWildcardRadioButtonMenuItem.setText("Wildcard");
    regWildcardRadioButtonMenuItem.setName("regWildcardRadioButtonMenuItem"); // NOI18N
    regWildcardRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        regWildcardRadioButtonMenuItemActionPerformed(evt);
      }
    });
    jMenu3.add(regWildcardRadioButtonMenuItem);

    subscriptionButtonGroup.add(regHalfRadioButtonMenuItem);
    regHalfRadioButtonMenuItem.setText("Half");
    regHalfRadioButtonMenuItem.setName("regHalfRadioButtonMenuItem"); // NOI18N
    regHalfRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        regHalfRadioButtonMenuItemActionPerformed(evt);
      }
    });
    jMenu3.add(regHalfRadioButtonMenuItem);

    subscriptionButtonGroup.add(regAllRadioButtonMenuItem);
    regAllRadioButtonMenuItem.setText("All");
    regAllRadioButtonMenuItem.setName("regAllRadioButtonMenuItem"); // NOI18N
    regAllRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        regAllRadioButtonMenuItemActionPerformed(evt);
      }
    });
    jMenu3.add(regAllRadioButtonMenuItem);

    jMenu2.add(jMenu3);

    deregMenuItem.setText("Deregister");
    deregMenuItem.setName("deregMenuItem"); // NOI18N
    deregMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        deregMenuItemActionPerformed(evt);
      }
    });
    jMenu2.add(deregMenuItem);

    jSeparator1.setName("jSeparator1"); // NOI18N
    jMenu2.add(jSeparator1);

    resetErrorMenuItem.setText("Reset errors");
    resetErrorMenuItem.setName("resetErrorMenuItem"); // NOI18N
    resetErrorMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        resetErrorMenuItemActionPerformed(evt);
      }
    });
    jMenu2.add(resetErrorMenuItem);

    jMenuItem1.setText("Reconnect");
    jMenuItem1.setName("jMenuItem1"); // NOI18N
    jMenuItem1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        reconnectActionPerformed(evt);
      }
    });
    jMenu2.add(jMenuItem1);

    jMenuBar1.add(jMenu2);

    jMenu4.setText("IPs");
    jMenu4.setName("jMenu4"); // NOI18N

    returnBoolean.setText("Return Boolean");
    returnBoolean.setName("returnBoolean"); // NOI18N
    returnBoolean.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        returnBooleanActionPerformed(evt);
      }
    });
    jMenu4.add(returnBoolean);

    returnComposite.setText("Return Composite");
    returnComposite.setName("returnComposite"); // NOI18N
    returnComposite.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        returnCompositeActionPerformed(evt);
      }
    });
    jMenu4.add(returnComposite);

    returnEnum.setText("Return Enum");
    returnEnum.setName("returnEnum"); // NOI18N
    returnEnum.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        returnEnumActionPerformed(evt);
      }
    });
    jMenu4.add(returnEnum);

    testSubmit.setText("Test Submit");
    testSubmit.setName("testSubmit"); // NOI18N
    testSubmit.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        testSubmitActionPerformed(evt);
      }
    });
    jMenu4.add(testSubmit);

    jMenuBar1.add(jMenu4);

    setJMenuBar(jMenuBar1);

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void quitMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_quitMenuItemActionPerformed
    {//GEN-HEADEREND:event_quitMenuItemActionPerformed
        try {
            running = false;

            if (null != tmConsumer) {
                deregMenuItemActionPerformed(null);
                tmConsumer.close();
            }
            if (null != consumerMgr) {
                consumerMgr.close();
            }
            if (null != mal) {
                mal.close();
            }
        } catch (MALException ex) {
            LOGGER.log(Level.SEVERE, "Exception during close down of the consumer {0}", ex);
        }

        dispose();
    }//GEN-LAST:event_quitMenuItemActionPerformed

    private void deregMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deregMenuItemActionPerformed
    {//GEN-HEADEREND:event_deregMenuItemActionPerformed
        if (registered) {
            try {
                registered = false;
                final Identifier subscriptionId = new Identifier("SUB");
                final IdentifierList subLst = new IdentifierList();
                subLst.add(subscriptionId);
                demoService.monitorDeregister(subLst);
            } catch (MALException ex) {
                Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MALInteractionException ex) {
                Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_deregMenuItemActionPerformed

    private void resetErrorMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_resetErrorMenuItemActionPerformed
    {//GEN-HEADEREND:event_resetErrorMenuItemActionPerformed
        for (int i = 0; i < labels.length; ++i) {
            labels[i].reset();
        }
    }//GEN-LAST:event_resetErrorMenuItemActionPerformed

    private void regWildcardRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_regWildcardRadioButtonMenuItemActionPerformed
    {//GEN-HEADEREND:event_regWildcardRadioButtonMenuItemActionPerformed
        try {
            demoService.monitorRegister(subRequestWildcard, adapter);
            registered = true;
        } catch (MALException ex) {
            Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALInteractionException ex) {
            Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_regWildcardRadioButtonMenuItemActionPerformed

    private void regHalfRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_regHalfRadioButtonMenuItemActionPerformed
    {//GEN-HEADEREND:event_regHalfRadioButtonMenuItemActionPerformed
        try {
            demoService.monitorRegister(subRequestHalf, adapter);
            registered = true;
        } catch (MALException ex) {
            Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALInteractionException ex) {
            Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_regHalfRadioButtonMenuItemActionPerformed

    private void regAllRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_regAllRadioButtonMenuItemActionPerformed
    {//GEN-HEADEREND:event_regAllRadioButtonMenuItemActionPerformed
        try {
            demoService.monitorRegister(subRequestAll, adapter);
            registered = true;
        } catch (MALException ex) {
            Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALInteractionException ex) {
            Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_regAllRadioButtonMenuItemActionPerformed

    private void reconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reconnectActionPerformed
        try {
            this.delayManager.resetDelay();
            labels[0].reset();
            StructureHelper.clearLoadedPropertiesList();
            startService();
        } catch (MALException ex) {
            Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_reconnectActionPerformed

  private void returnBooleanActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_returnBooleanActionPerformed
  {//GEN-HEADEREND:event_returnBooleanActionPerformed
      try {
          LOGGER.log(Level.INFO, "returnBooleanActionPerformed: {0}", demoService.returnBoolean(Boolean.TRUE));
      } catch (MALException ex) {
          Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
      } catch (MALInteractionException ex) {
          Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
      }
  }//GEN-LAST:event_returnBooleanActionPerformed

  private void returnCompositeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_returnCompositeActionPerformed
  {//GEN-HEADEREND:event_returnCompositeActionPerformed
      try {
          LOGGER.log(Level.INFO,
                  "returnCompositeActionPerformed: {0}",
                  demoService.returnComposite(new BasicComposite(Short.MIN_VALUE, "String", Boolean.FALSE)));
      } catch (MALException ex) {
          Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
      } catch (MALInteractionException ex) {
          Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
      }
  }//GEN-LAST:event_returnCompositeActionPerformed

  private void returnEnumActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_returnEnumActionPerformed
  {//GEN-HEADEREND:event_returnEnumActionPerformed
      try {
          LOGGER.log(Level.INFO, "returnEnumActionPerformed: {0}", demoService.returnEnumeration(BasicEnum.SECOND));
      } catch (MALException ex) {
          Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
      } catch (MALInteractionException ex) {
          Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
      }
  }//GEN-LAST:event_returnEnumActionPerformed

  private void testSubmitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_testSubmitActionPerformed
  {//GEN-HEADEREND:event_testSubmitActionPerformed
      try {
          LOGGER.info("testSubmitActionPerformed started");
          demoService.testSubmit(null);
          LOGGER.info("testSubmitActionPerformed returned");
      } catch (MALException ex) {
          Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
      } catch (MALInteractionException ex) {
          Logger.getLogger(DemoConsumerGui.class.getName()).log(Level.SEVERE, null, ex);
      }
  }//GEN-LAST:event_testSubmitActionPerformed

  private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
  {//GEN-HEADEREND:event_formWindowClosing
      quitMenuItemActionPerformed(null);
  }//GEN-LAST:event_formWindowClosing

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel delayLabel;
  private javax.swing.JMenuItem deregMenuItem;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenu jMenu3;
  private javax.swing.JMenu jMenu4;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JToolBar jToolBar1;
  private javax.swing.JMenuItem quitMenuItem;
  private javax.swing.JRadioButtonMenuItem regAllRadioButtonMenuItem;
  private javax.swing.JRadioButtonMenuItem regHalfRadioButtonMenuItem;
  private javax.swing.JRadioButtonMenuItem regWildcardRadioButtonMenuItem;
  private javax.swing.JMenuItem resetErrorMenuItem;
  private javax.swing.JMenuItem returnBoolean;
  private javax.swing.JMenuItem returnComposite;
  private javax.swing.JMenuItem returnEnum;
  private javax.swing.ButtonGroup subscriptionButtonGroup;
  private javax.swing.JMenuItem testSubmit;
  // End of variables declaration//GEN-END:variables
}
