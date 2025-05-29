/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MPD services
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
package esa.mo.services.mpd.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mpd.structures.Product;

/**
 * The FileTransferManager class is responsible for transferring files based on
 * the defined orders.
 */
public class FileTransferManager {

    private final URI deliverTo;

    /**
     * Constructor.
     *
     * @param deliverTo The target URI to deliver the file.
     */
    public FileTransferManager(URI deliverTo) {
        this.deliverTo = deliverTo;
    }

    /**
     * Connect to the target.
     *
     * @throws IOException if target could not be reached.
     */
    public void connect() throws IOException {
        String location = deliverTo.getValue(); // URI

        if (location.startsWith("file://")) {
            // Check if the directory exists...
            File path = new File(location.replace("file://", ""));

            if (!path.exists()) {
                throw new IOException("The directory does not exist!");
            }
        }
    }

    /**
     * Performs the product transfer for the selected product and filename.
     *
     * @param product The product to be transferred.
     * @param filename The filename of the stored file.
     * @return True if the transfer was successful, false otherwise.
     */
    public boolean executeTransfer(Product product, String filename) {
        String location = deliverTo.getValue(); // URI

        if (location.startsWith("file://")) {
            fileInternal(product, deliverTo, filename);
            return true;
        }

        if (location.startsWith("scp://")) {
            Logger.getLogger(FileTransferManager.class.getName()).log(
                    Level.WARNING, "The selected protocol is not supported: scp");
            return false;
        }

        Logger.getLogger(FileTransferManager.class.getName()).log(Level.WARNING,
                "The selected protocol is not supported! For location: {0}", location);
        return false;
    }

    private static void fileInternal(Product product, URI deliverTo, String filename) {
        // Extract file path:
        String location = deliverTo.getValue(); // URI
        File path = new File(location.replace("file://", ""));

        if (!path.exists()) {
            path.mkdirs();
        }

        File productLocation = new File(path, filename);
        // Create a Product on the specified location
        if (productLocation.exists()) {
            Logger.getLogger(FileTransferManager.class.getName()).log(
                    Level.WARNING, "The file already exists! The file will be overridden.");
        }
        if (!productLocation.exists()) {
            try {
                productLocation.createNewFile();
                try (FileOutputStream fos = new FileOutputStream(productLocation)) {
                    byte[] productBody = product.getProductBody().getValue();
                    productBody = (productBody == null) ? new byte[0] : productBody;
                    fos.write(productBody);
                    Logger.getLogger(FileTransferManager.class.getName()).log(
                            Level.INFO, "The file was successfully written.");
                }
            } catch (IOException ex) {
                Logger.getLogger(FileTransferManager.class.getName()).log(
                        Level.SEVERE, "The file could not be created!", ex);
            }
        }
    }

    @Deprecated
    private boolean deliverProductsRemotely(Product product, URI deliverTo, String filename) {
        FTPClient ftpClient = new FTPClient();
        boolean deliverySuccess = true;

        try {
            // Extract the FTP credentials and address from the URI
            String rootURI = deliverTo.getRootURI('/', 0);
            String server = deliverTo.getValue();
            int port = 21;  // Default FTP port 21
            String username = "anonymous";
            String password = "";

            // Connect to the FTP server
            ftpClient.connect(server, port);
            boolean login = ftpClient.login(username, password);

            if (!login) {
                System.out.println("Failed to login to the FTP server.");
                return false;
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Loop through the products in matchedProducts
            byte[] blob = product.getProductBody().getValue();

            try (InputStream byteArrayInputStream = new ByteArrayInputStream(blob)) {
                // Upload file to the FTP server
                boolean uploaded = ftpClient.storeFile(filename, byteArrayInputStream);

                if (!uploaded) {
                    System.out.println("Failed to upload file: " + filename);
                    deliverySuccess = false;
                }
            } catch (IOException e) {
                System.out.println("Error uploading file: " + filename);
                e.printStackTrace();
                deliverySuccess = false;
            }

            // Logout and disconnect from FTP server
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
            deliverySuccess = false;
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return deliverySuccess;
    }
}
