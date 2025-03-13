/** *****************************************************************************
 * Copyright or © or Copr. CNES
 *
 * This software is a computer program whose purpose is to provide a
 * framework for the CCSDS Mission Operations services.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL-C
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 ****************************************************************************** */
package org.ccsds.moims.mo.mal.test.patterns;

import java.util.HashMap;
import java.util.Map;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.testbed.suite.BooleanCondition;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class MonitorPublishInteractionListener implements MALPublishInteractionListener {

    public BooleanCondition cond = new BooleanCondition();

    private final HashMap<String, Long> publishRegisterTransactionIds = new HashMap<>();
    private String key = "default";
    private MALMessageHeader header;
    private MOErrorException error;

    @Override
    public synchronized void publishRegisterAckReceived(MALMessageHeader header, Map props) throws MALException {
        LoggingBase.logMessage("MonitorPublishInteractionListener.publishRegisterAckReceived(" + header + ')');
        this.header = header;
        Long tranId = publishRegisterTransactionIds.get(key);
        if (tranId == null) {
            LoggingBase.logMessage("MonitorPublishInteractionListener.acknowledgementReceived,"
                    + " setting transaction id: " + header.getTransactionId() + ')');
            publishRegisterTransactionIds.put(key, header.getTransactionId());
            //publishRegisterTransactionId = header.getTransactionId();
        }
        cond.set();
    }

    @Override
    public synchronized void publishDeregisterAckReceived(MALMessageHeader header, Map props) throws MALException {
        this.header = header;
        cond.set();
    }

    public void setKey(String value) {
        this.key = value;
    }

    @Override
    public void publishRegisterErrorReceived(MALMessageHeader header, MALErrorBody body, Map props) throws MALException {
        errorReceived(header, body.getError());
    }

    @Override
    public void publishErrorReceived(MALMessageHeader header, MALErrorBody body, Map props) throws MALException {
        errorReceived(header, body.getError());
    }

    private synchronized void errorReceived(MALMessageHeader header,
            MOErrorException error) throws MALException {
        LoggingBase.logMessage("MonitorPublishInteractionListener.errorReceived:"
                + "\nHeader: " + header + "\nError: " + error + ')');
        this.header = header;
        this.error = error;
        cond.set();
    }

    public MALMessageHeader getHeader() {
        return header;
    }

    public MOErrorException getError() {
        return error;
    }

    public void setHeader(MALMessageHeader header) {
        this.header = header;
    }

    public void setError(MOErrorException error) {
        this.error = error;
    }

    public synchronized Long getPublishRegisterTransactionId(String key) {
        return publishRegisterTransactionIds.get(key);
    }

    @Override
    public String toString() {
        return '(' + super.toString() + ')';
    }
}
