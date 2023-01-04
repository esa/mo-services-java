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
package org.ccsds.moims.mo.malspp.test.util;

import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;

public abstract class TimeCode {

    // CCSDS recommended TAI epoch of 1958 January 1
    public static final AbsoluteDate EPOCH_TAI = new AbsoluteDate(1958, 1, 1,
            TimeScalesFactory.getTAI());

    public static final int CUC = 0;
    public static final int CDS = 1;

    public static final int UNIT_SECOND = 0;
    public static final int UNIT_MILLISECOND = 1;
    public static final int UNIT_MICROSECOND = 2;
    public static final int UNIT_NANOSECOND = 3;
    public static final int UNIT_PICOSECOND = 4;

    public static final String UNIT_NAME_SECOND = "second";
    public static final String UNIT_NAME_MILLISECOND = "millisecond";
    public static final String UNIT_NAME_MICROSECOND = "microsecond";
    public static final String UNIT_NAME_NANOSECOND = "nanosecond";
    public static final String UNIT_NAME_PICOSECOND = "picosecond";

    public static int parseUnit(String s) throws Exception {
        if (UNIT_NAME_SECOND.equals(s)) {
            return UNIT_SECOND;
        }
        if (UNIT_NAME_MILLISECOND.equals(s)) {
            return UNIT_MILLISECOND;
        }
        if (UNIT_NAME_MICROSECOND.equals(s)) {
            return UNIT_MICROSECOND;
        }
        if (UNIT_NAME_NANOSECOND.equals(s)) {
            return UNIT_NANOSECOND;
        }
        if (UNIT_NAME_PICOSECOND.equals(s)) {
            return UNIT_PICOSECOND;
        }
        throw new Exception("Unknown time unit: " + s);
    }

    private int type;

    private AbsoluteDate epoch;

    private int unit;

    public TimeCode(int type, AbsoluteDate epoch, int unit) {
        this.type = type;
        this.epoch = epoch;
        this.unit = unit;
    }

    public int getType() {
        return type;
    }

    public AbsoluteDate getEpoch() {
        return epoch;
    }

    public void setEpoch(AbsoluteDate epoch) {
        this.epoch = epoch;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "TimeCode [epoch=" + epoch + ", unit=" + unit + "]";
    }

}
