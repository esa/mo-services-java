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
package esa.mo.mal.demo.provider;

import esa.mo.mal.demo.util.StructureHelper;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;

/**
 * This class provides a simple form for the control of the provider. It allows control of the generation of updates,
 * the rate the updates are generated, the size of the set of updates (the pool) and the block size of the update sets.
 */
public class DemoProviderGui extends javax.swing.JFrame
{
  /**
   * Logger
   */
  public static final java.util.logging.Logger LOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.demo.provider");
  private final DemoProviderServiceImpl handler = new DemoProviderServiceImpl();
  private final String defaultProtocol;

  /**
   * Main command line entry point.
   *
   * @param args the command line arguments
   */
  public static void main(final String args[])
  {
    try
    {
      final java.util.Properties sysProps = System.getProperties();

      File file = new File(System.getProperty("provider.properties", "demoProvider.properties"));
      if (file.exists())
      {
        sysProps.putAll(StructureHelper.loadProperties(file.toURI().toURL(), "provider.properties"));
      }

      file = new File(System.getProperty("broker.properties", "sharedBrokerURI.properties"));
      if (file.exists())
      {
        sysProps.putAll(StructureHelper.loadProperties(file.toURI().toURL(), "broker.properties"));
      }

      System.setProperties(sysProps);

      final String name = System.getProperty("application.name", "DemoServiceProvider");

      final DemoProviderGui gui = new DemoProviderGui(name);
      gui.handler.init();

      EventQueue.invokeLater(new Runnable()
      {
        public void run()
        {
          gui.setVisible(true);
        }
      });
    }
    catch (MalformedURLException ex)
    {
      LOGGER.log(Level.SEVERE, "Exception thrown during initialisation of Demo Provider {0}", ex);
    }
    catch (MALException ex)
    {
      LOGGER.log(Level.SEVERE, "Exception thrown during initialisation of Demo Provider {0}", ex);
    }
    catch (MALInteractionException ex)
    {
      LOGGER.log(Level.SEVERE, "Exception thrown during initialisation of Demo Provider {0}", ex);
    }
  }

  /**
   * Creates new form DemoProviderGui.
   *
   * @param name The name to display on the title bar of the form.
   */
  public DemoProviderGui(final String name)
  {
    initComponents();

    defaultProtocol = System.getProperty("org.ccsds.moims.mo.mal.transport.default.protocol");

    String protocolString = System.getProperty("esa.mo.mal.demo.provider.protocols");

    if ((null != protocolString) && (0 < protocolString.trim().length()))
    {
      String[] protocols = protocolString.trim().split(",");

      for (int i = 0; i < protocols.length; i = i + 2)
      {
        String displayName = protocols[i];
        String protocol = protocols[i + 1];

        javax.swing.JMenuItem tmpMenuItem = new javax.swing.JMenuItem();
        tmpMenuItem.setText(displayName);
        tmpMenuItem.setActionCommand(protocol);
        tmpMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
          public void actionPerformed(java.awt.event.ActionEvent evt)
          {
            transportSelected(evt);
          }
        });

        jMenu3.add(tmpMenuItem);
      }
    }

    this.setTitle(name);
    statusLabel.setOpaque(true);

    ((javax.swing.SpinnerNumberModel) poolSize.getModel()).setMinimum(1);
    ((javax.swing.SpinnerNumberModel) blockSize.getModel()).setMinimum(1);
    ((javax.swing.SpinnerNumberModel) poolSize.getModel()).setValue(handler.getPoolSize());
    ((javax.swing.SpinnerNumberModel) blockSize.getModel()).setValue(handler.getBlockSize());
    
    updateRateStateChanged(null);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
   * content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {

    mainPanel = new javax.swing.JPanel();
    controlsPanel = new javax.swing.JPanel();
    jLabel2 = new javax.swing.JLabel();
    updateRate = new javax.swing.JSlider();
    jLabel3 = new javax.swing.JLabel();
    poolSize = new javax.swing.JSpinner();
    jLabel4 = new javax.swing.JLabel();
    blockSize = new javax.swing.JSpinner();
    statusLabel = new javax.swing.JLabel();
    statusPanel = new javax.swing.JPanel();
    jSeparator2 = new javax.swing.JSeparator();
    statusMessageLabel = new javax.swing.JLabel();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    quitMenuItem = new javax.swing.JMenuItem();
    jMenu2 = new javax.swing.JMenu();
    genTMMenuItem = new javax.swing.JMenuItem();
    pauseTMMenuItem = new javax.swing.JMenuItem();
    jMenu3 = new javax.swing.JMenu();
    defaultTransportMenuItem = new javax.swing.JMenuItem();
    jSeparator1 = new javax.swing.JPopupMenu.Separator();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setName("Form"); // NOI18N

    mainPanel.setName("mainPanel"); // NOI18N
    mainPanel.setLayout(new java.awt.BorderLayout());

    controlsPanel.setName("controlsPanel"); // NOI18N
    controlsPanel.setLayout(new java.awt.GridLayout(3, 2));

    jLabel2.setText("Update rate");
    jLabel2.setName("jLabel2"); // NOI18N
    controlsPanel.add(jLabel2);

    updateRate.setMajorTickSpacing(1);
    updateRate.setMaximum(11);
    updateRate.setPaintTicks(true);
    updateRate.setValue(10);
    updateRate.setName("updateRate"); // NOI18N
    updateRate.addChangeListener(new javax.swing.event.ChangeListener()
    {
      public void stateChanged(javax.swing.event.ChangeEvent evt)
      {
        updateRateStateChanged(evt);
      }
    });
    controlsPanel.add(updateRate);

    jLabel3.setText("Pool size");
    jLabel3.setName("jLabel3"); // NOI18N
    controlsPanel.add(jLabel3);

    poolSize.setName("poolSize"); // NOI18N
    poolSize.addChangeListener(new javax.swing.event.ChangeListener()
    {
      public void stateChanged(javax.swing.event.ChangeEvent evt)
      {
        poolSizeStateChanged(evt);
      }
    });
    controlsPanel.add(poolSize);

    jLabel4.setText("Block size");
    jLabel4.setName("jLabel4"); // NOI18N
    controlsPanel.add(jLabel4);

    blockSize.setName("blockSize"); // NOI18N
    blockSize.addChangeListener(new javax.swing.event.ChangeListener()
    {
      public void stateChanged(javax.swing.event.ChangeEvent evt)
      {
        blockSizeStateChanged(evt);
      }
    });
    controlsPanel.add(blockSize);

    mainPanel.add(controlsPanel, java.awt.BorderLayout.PAGE_START);

    statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    statusLabel.setText("Status");
    statusLabel.setMaximumSize(new java.awt.Dimension(10000, 10000));
    statusLabel.setName("statusLabel"); // NOI18N
    statusLabel.setPreferredSize(new java.awt.Dimension(31, 100));
    mainPanel.add(statusLabel, java.awt.BorderLayout.CENTER);

    getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

    statusPanel.setName("statusPanel"); // NOI18N
    statusPanel.setLayout(new java.awt.BorderLayout());

    jSeparator2.setName("jSeparator2"); // NOI18N
    statusPanel.add(jSeparator2, java.awt.BorderLayout.NORTH);

    statusMessageLabel.setMaximumSize(new java.awt.Dimension(4000, 27));
    statusMessageLabel.setMinimumSize(new java.awt.Dimension(0, 27));
    statusMessageLabel.setName("statusMessageLabel"); // NOI18N
    statusMessageLabel.setPreferredSize(new java.awt.Dimension(0, 27));
    statusPanel.add(statusMessageLabel, java.awt.BorderLayout.CENTER);

    getContentPane().add(statusPanel, java.awt.BorderLayout.PAGE_END);

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

    jMenu2.setText("Publisher");
    jMenu2.setName("jMenu2"); // NOI18N

    genTMMenuItem.setText("Start TM");
    genTMMenuItem.setName("genTMMenuItem"); // NOI18N
    genTMMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        genTMMenuItemActionPerformed(evt);
      }
    });
    jMenu2.add(genTMMenuItem);

    pauseTMMenuItem.setText("Pause TM");
    pauseTMMenuItem.setName("pauseTMMenuItem"); // NOI18N
    pauseTMMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        pauseTMMenuItemActionPerformed(evt);
      }
    });
    jMenu2.add(pauseTMMenuItem);

    jMenuBar1.add(jMenu2);

    jMenu3.setText("Transport");
    jMenu3.setName("jMenu3"); // NOI18N

    defaultTransportMenuItem.setText("default");
    defaultTransportMenuItem.setName("defaultTransportMenuItem"); // NOI18N
    defaultTransportMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        transportSelected(evt);
      }
    });
    jMenu3.add(defaultTransportMenuItem);

    jSeparator1.setName("jSeparator1"); // NOI18N
    jMenu3.add(jSeparator1);

    jMenuBar1.add(jMenu3);

    setJMenuBar(jMenuBar1);

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void quitMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_quitMenuItemActionPerformed
    {//GEN-HEADEREND:event_quitMenuItemActionPerformed
      handler.close();
      dispose();
    }//GEN-LAST:event_quitMenuItemActionPerformed

    private void genTMMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_genTMMenuItemActionPerformed
    {//GEN-HEADEREND:event_genTMMenuItemActionPerformed
      handler.startGeneration();
      statusLabel.setText("GENERATING");
      statusLabel.setBackground(Color.ORANGE);
    }//GEN-LAST:event_genTMMenuItemActionPerformed

    private void pauseTMMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_pauseTMMenuItemActionPerformed
    {//GEN-HEADEREND:event_pauseTMMenuItemActionPerformed
      handler.pauseGeneration();
      statusLabel.setText("PAUSED");
      statusLabel.setBackground(Color.GRAY);

    }//GEN-LAST:event_pauseTMMenuItemActionPerformed

    private void updateRateStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_updateRateStateChanged
    {//GEN-HEADEREND:event_updateRateStateChanged
      if (0 < updateRate.getValue())
      {
        handler.setSleep((11 - updateRate.getValue()) * 1000);
      }
      else
      {
        handler.pauseGeneration();
        statusLabel.setText("PAUSED");
        statusLabel.setBackground(Color.GRAY);
      }
    }//GEN-LAST:event_updateRateStateChanged

    private void poolSizeStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_poolSizeStateChanged
    {//GEN-HEADEREND:event_poolSizeStateChanged
      handler.setPoolSize((Integer) poolSize.getValue());
    }//GEN-LAST:event_poolSizeStateChanged

    private void blockSizeStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_blockSizeStateChanged
    {//GEN-HEADEREND:event_blockSizeStateChanged
      handler.setBlockSize((Integer) blockSize.getValue());
    }//GEN-LAST:event_blockSizeStateChanged

  private void transportSelected(java.awt.event.ActionEvent evt)//GEN-FIRST:event_transportSelected
  {//GEN-HEADEREND:event_transportSelected
    String newProtocol;

    if (evt.getSource() == this.defaultTransportMenuItem)
    {
      System.out.println("SELECTED DEFAULT TRANSPORT");
      newProtocol = defaultProtocol;
    }
    else
    {
      System.out.println("SELECTED TRANSPORT: " + evt.getActionCommand());
      newProtocol = evt.getActionCommand();
    }

    boolean isGenerating = handler.isGenerating();

    if (isGenerating)
    {
      pauseTMMenuItemActionPerformed(null);
    }

    try
    {
      handler.startServices(newProtocol);
    }
    catch (MALException ex)
    {
      LOGGER.log(Level.SEVERE, "Exception thrown during initialisation of Demo Provider {0}", ex);
    }
    catch (MALInteractionException ex)
    {
      LOGGER.log(Level.SEVERE, "Exception thrown during initialisation of Demo Provider {0}", ex);
    }

    if (isGenerating)
    {
      genTMMenuItemActionPerformed(null);
    }
  }//GEN-LAST:event_transportSelected

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JSpinner blockSize;
  private javax.swing.JPanel controlsPanel;
  private javax.swing.JMenuItem defaultTransportMenuItem;
  private javax.swing.JMenuItem genTMMenuItem;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenu jMenu3;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JPopupMenu.Separator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JPanel mainPanel;
  private javax.swing.JMenuItem pauseTMMenuItem;
  private javax.swing.JSpinner poolSize;
  private javax.swing.JMenuItem quitMenuItem;
  private javax.swing.JLabel statusLabel;
  private javax.swing.JLabel statusMessageLabel;
  private javax.swing.JPanel statusPanel;
  private javax.swing.JSlider updateRate;
  // End of variables declaration//GEN-END:variables
}
