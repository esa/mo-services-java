package org.ccsds.moims.mo.mal.helpertools.connections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class TestConnectionProvider {

    Properties props;

    @Before
    public void saveProps() {
        props = (Properties) System.getProperties().clone();
    }

    @After
    public void restoreProps() {
        System.setProperties(props);
    }

    @Test
    public void resetURILinkFileCreation() {
        System.setProperty(HelperMisc.PROP_INIT_URI_FILES, "true");
        File f = new File("providerURIs.properties");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write("I am a provider properties file and my content is meaningless because I get deleted anyways.".getBytes());
            fos.close();
            ConnectionProvider.resetURILinksFile();
            assertEquals(0, f.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("File should be found...");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Should be able to write to this file");
        }
    }
}
