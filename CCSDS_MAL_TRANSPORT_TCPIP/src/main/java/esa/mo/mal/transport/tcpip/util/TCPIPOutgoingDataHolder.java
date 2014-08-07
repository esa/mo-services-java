/**
 * 
 */
package esa.mo.mal.transport.tcpip.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import esa.mo.mal.transport.tcpip.TCPIPTransport;

/**
 * This class holds the data to be sent in raw format
 * and a reply queue that the internal sender of the data can listen to 
 * in order to be informed if the data was successfully
 * sent or not.
 * 
 * @author Petros Pissias
 *
 */
public class TCPIPOutgoingDataHolder {

    //reply queue
    private final BlockingQueue<Boolean> replyQueue;
    
    //the raw data
    private final byte[] data;
    
    //reference to logger
    public final java.util.logging.Logger LOGGER = TCPIPTransport.LOGGER;

    /**
     * Will construct a new object and create a new internal reply queue
     * @param data the data to be sent
     */
    public TCPIPOutgoingDataHolder(byte[] data) {
	this.data = data;
	replyQueue = new LinkedBlockingQueue<Boolean>();
    }
    
    /**
     * This method blocks until there is an attempt to send the data. 
     * @return TRUE if the data was successfully sent and FALSE if there was a communication or internal problem.
     * @throws InterruptedException in case of shutting down or internal error
     */
    public Boolean getResult() throws InterruptedException  {
	return replyQueue.take();
    }

    /**
     * Sets the result indicating if the data was sent successfully.
     * @param result TRUE if the data was successfully sent and FALSE if there was a communication or internal problem.
     */
    public void setResult(Boolean result)  {
	boolean inserted = replyQueue.add(result);
	if (!inserted) {
	    // log error. According to the specification (see *add* call
	    // documentation) this will always return true, or throw an
	    // exception
	    LOGGER.log(Level.SEVERE, "Could not insert result to processing queue", new Throwable());
	}
    }
    
    /**
     * Getter for the data to be sent
     * @return
     */
    public byte[] getData() {
        return data;
    }
    
    
}
