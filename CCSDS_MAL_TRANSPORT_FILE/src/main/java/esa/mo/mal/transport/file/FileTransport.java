/* ----------------------------------------------------------------------------
 * (C) 2011      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO File Transport
 * Author       : Sam Cooper
 *
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.transport.file;

import esa.mo.mal.transport.gen.GENEndpoint;
import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.GENTransport;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALTransportFactory;

/**
 * An implementation of the transport interface for a file based protocol.
 */
public class FileTransport extends GENTransport
{
  /**
   * Logger
   */
  public static final java.util.logging.Logger RLOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.transport.file");
  private static final String FILE_PREFIX = "CCSDS_FILE_TRANSPORT_";
  private static final String QOS_DELETE_FILE = "ccsds.mal.transport.file.qos.delete";
  private final boolean deleteFiles;
  private Thread asyncPollThread = null;
  private String transportString = "";
  private String filenameString = "";
  private long msgCount = 0;
  private WatchService watcher = null;
  private final Path dir = Paths.get(System.getProperty("user.dir"));

  /**
   * Constructor.
   *
   * @param protocol The protocol string.
   * @param factory The factory that created us.
   * @param properties The QoS properties.
   * @throws MALException On error.
   */
  public FileTransport(final String protocol,
          final MALTransportFactory factory,
          final java.util.Map properties) throws MALException
  {
    super(protocol, '-', false, false, factory, properties);

    if ((null != properties) && (properties.containsKey(QOS_DELETE_FILE)))
    {
      RLOGGER.info("File transport set to NOT delete message files");
      deleteFiles = false;
    }
    else
    {
      deleteFiles = true;
    }
  }

  @Override
  public void init() throws MALException
  {
    super.init();

    // set up polling thread for new files appearing in the file directory
    RLOGGER.log(Level.INFO, "Monitoring directory {0} for file prefix {1}", new Object[]
            {
              System.getProperty("java.io.tmpdir"), transportString
            });

    try
    {
      watcher = FileSystems.getDefault().newWatchService();
      System.out.println("Watching : " + dir.toString());

      dir.register(watcher, java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY);

      asyncPollThread = new Thread()
      {
        @Override
        public void run()
        {
          // we loop whilst there are still messages to process
          boolean bContinue = true;

          while (bContinue)
          {
            // wait for key to be signalled
            WatchKey key;
            try
            {
              key = watcher.take();
            }
            catch (InterruptedException x)
            {
              return;
            }

            for (WatchEvent<?> event : key.pollEvents())
            {
              WatchEvent.Kind kind = event.kind();

              // TBD - provide example of how OVERFLOW event is handled
              if (kind == java.nio.file.StandardWatchEventKinds.OVERFLOW)
              {
                continue;
              }

              // Context for directory entry event is the file name of entry
              WatchEvent<Path> ev = (WatchEvent<Path>) event;
              Path name = ev.context();
              Path child = dir.resolve(name);

              if (Files.isRegularFile(child, LinkOption.NOFOLLOW_LINKS))
              {
                String subname = child.getFileName().toString();
                if (subname.startsWith(transportString) && subname.endsWith(".msg"))
                {
                  System.out.println("Found file : " + child.getFileName());
                  try
                  {
                    FileChannel fc;
                    if (deleteFiles)
                    {
                      fc = FileChannel.open(child, StandardOpenOption.READ, StandardOpenOption.DELETE_ON_CLOSE);
                    }
                    else
                    {
                      fc = FileChannel.open(child, StandardOpenOption.READ);
                    }

                    receive(Channels.newInputStream(fc));
                  }
                  catch (IOException ex)
                  {
                    ex.printStackTrace();
                  }
                }
              }
            }

            // reset the key
            boolean valid = key.reset();
            if (!valid)
            {
              // object no longer registered
              System.out.println("Not registered : " + dir.toString());
            }
          }

          System.out.println("Not Watching : " + dir.toString());
        }
      };
      asyncPollThread.start();

    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
  }

  @Override
  protected String createTransportAddress() throws MALException
  {
    filenameString = ManagementFactory.getRuntimeMXBean().getName();
    transportString = FILE_PREFIX + filenameString + "-";
    return filenameString;
  }

  @Override
  protected GENEndpoint internalCreateEndpoint(final String localName, final Map qosProperties) throws MALException
  {
    return new GENEndpoint(this, localName, uriBase + localName, false);
  }

  @Override
  public MALBrokerBinding createBroker(final String localName,
          final Blob authenticationId,
          final QoSLevel[] expectedQos,
          final UInteger priorityLevelNumber,
          final Map defaultQoSProperties) throws MALException
  {
    // not support by RMI transport
    return null;
  }

  @Override
  public MALBrokerBinding createBroker(final MALEndpoint endpoint,
          final Blob authenticationId,
          final QoSLevel[] qosLevels,
          final UInteger priorities,
          final Map properties) throws MALException
  {
    // not support by RMI transport
    return null;
  }

  @Override
  public boolean isSupportedInteractionType(final InteractionType type)
  {
    // Supports all IPs except Pub Sub
    return (InteractionType.PUBSUB.getOrdinal() != type.getOrdinal());
  }

  @Override
  public boolean isSupportedQoSLevel(final QoSLevel qos)
  {
    // The transport only supports BESTEFFORT in reality but this is only a test transport so we say it supports all
    return QoSLevel.BESTEFFORT.equals(qos);
  }

  @Override
  public void close() throws MALException
  {
    asyncPollThread.interrupt();
  }

  @Override
  protected void internalSendMessage(final GENTransport.MsgPair tmsg) throws IOException
  {
    RLOGGER.log(Level.INFO, "File Sending data to {0}", new Object[]
            {
              tmsg.addr
            });

    // create tmp file name
    String tmpname = FILE_PREFIX + tmsg.addr.substring(7) + "-" + filenameString + "-" + String.format("%07d", ++msgCount);
    java.io.File tmpFile = new File(tmpname + ".tmp");
    try
    {
      try (java.io.FileOutputStream fos = new FileOutputStream(tmpFile))
      {
        final MALElementOutputStream enc = getStreamFactory().createOutputStream(fos);
        tmsg.msg.encodeMessage(getStreamFactory(), enc, fos);

        enc.flush();
        enc.close();
        fos.flush();
      }
    }
    catch (MALException ex)
    {
      ex.printStackTrace();
    }

    // rename file to correct file name
    tmpFile.renameTo(new File(tmpname + ".msg"));
  }

  @Override
  public GENMessage createMessage(InputStream ios) throws MALException
  {
    return new FileBasedMessage(qosProperties, ios, getStreamFactory());
  }

  @Override
  public GENMessage createMessage(byte[] packet) throws MALException
  {
    return null;
  }
}
