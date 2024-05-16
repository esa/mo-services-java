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
package org.ccsds.moims.mo.malspp.test.datatype;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malspp.test.util.TestHelper;

public final class LargeEnumeration extends org.ccsds.moims.mo.mal.structures.Enumeration {

    /**
     * Start from the maximum type short form to avoid collision with the MAL
     * prototype data types declared in XML. TYPE_SHORT_FORM_MAX:
     * LargeEnumeration TYPE_SHORT_FORM_MAX - 1: MediumEnumeration
     */
    public static final Integer TYPE_SHORT_FORM = TestHelper.TYPE_SHORT_FORM_MAX;

    public static final Long SHORT_FORM = TestHelper.getAbsoluteShortForm(
            MALPrototypeHelper._MALPROTOTYPE_AREA_NUMBER, 0, MALPrototypeHelper._MALPROTOTYPE_AREA_VERSION, TYPE_SHORT_FORM);

    public LargeEnumeration(Integer ordinal) {
        super(ordinal);
    }

    public static LargeEnumeration fromOrdinal(int ordinal) {
        return new LargeEnumeration(ordinal);
    }

    public static LargeEnumeration fromNumericValue(org.ccsds.moims.mo.mal.structures.UInteger numericValue) {
        return null;
    }

    public static LargeEnumeration fromString(String s) {
        return null;
    }

    public String toString() {
        return "";
    }

    public org.ccsds.moims.mo.mal.structures.UInteger getNumericValue() {
        return null;
    }

    public org.ccsds.moims.mo.mal.structures.UShort getAreaNumber() {
        return org.ccsds.moims.mo.mal.MALHelper.MAL_AREA_NUMBER;
    }

    public org.ccsds.moims.mo.mal.structures.UOctet getAreaVersion() {
        return org.ccsds.moims.mo.mal.MALHelper.MAL_AREA_VERSION;
    }

    public org.ccsds.moims.mo.mal.structures.UShort getServiceNumber() {
        return org.ccsds.moims.mo.mal.MALService.NULL_SERVICE_NUMBER;
    }

    public Long getShortForm() {
        return SHORT_FORM;
    }

    public Integer getTypeShortForm() {
        return TYPE_SHORT_FORM;
    }

    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeUInteger(new UInteger(getOrdinal()));
    }

    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        int ordinal;
        ordinal = (int) decoder.decodeUInteger().getValue();
        return fromOrdinal(ordinal);
    }

    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new LargeEnumeration(null);
    }

    @Override
    public TypeId getTypeId() {
        return new TypeId(SHORT_FORM);
    }

}
