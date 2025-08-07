package org.ccsds.moims.mo.mal.helpertools.connections;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.ccsds.moims.mo.mal.helpertools.connections.ServicesConnectionDetails;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestServicesConnectionDetails {

    @Test
    public void testLoadURIFromFiles() {
        ServicesConnectionDetails scd = new ServicesConnectionDetails();

        try {
            String filename = getClass().getClassLoader().getResource("providerURIs.properties").getFile();
            ServicesConnectionDetails res = scd.loadURIFromFiles(filename);
            HashMap<String, SingleConnectionDetails> resmap = res.getServices();
            Set<Entry<String, SingleConnectionDetails>> entryset = resmap.entrySet();
            List<SingleConnectionDetails> scdlist = entryset.stream().map(Entry::getValue).collect(Collectors.toList());
            assertEquals("maltcp://172.17.0.1:1024/nanosat-mo-supervisor-Archive", scdlist.get(0).getProviderURI().getValue());
            assertEquals("maltcp://172.17.0.1:1024/nanosat-mo-supervisor-Event", scdlist.get(1).getProviderURI().getValue());
        } catch (MalformedURLException | FileNotFoundException e) {
            e.printStackTrace();
            fail("Should not throw exception");
        }
    }

}
