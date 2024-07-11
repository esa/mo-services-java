/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.structures;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * Class representing MAL Blob type.
 */
public class Blob implements Attribute {

    private static final long serialVersionUID = Attribute.BLOB_SHORT_FORM;
    private static final int T_BUFFER_SIZE = 4096;
    private byte[] value;
    private int offset;
    private int length;
    private String uvalue;
    private URL urlValue;

    /**
     * Default constructor.
     */
    public Blob() {
        this.value = null;
        this.uvalue = null;
        this.offset = 0;
        this.length = 0;
    }

    /**
     * Byte array constructor, does not copy the passed array. The byte array
     * should not be modified after the constructor has been called. If the byte
     * array is modified after the constructor has been called then the Blob
     * behaviour is unspecified.
     *
     * @param value Byte array to be wrapped.
     * @throws java.lang.IllegalArgumentException If the argument is null.
     */
    public Blob(final byte[] value) throws java.lang.IllegalArgumentException {
        this.value = value;
        this.uvalue = null;
        this.offset = 0;
        this.length = value.length;
    }

    /**
     * Byte array constructor, does not copy the passed array. The byte array
     * should not be modified after the constructor has been called. If the byte
     * array is modified after the constructor has been called then the Blob
     * behaviour is unspecified.
     *
     * @param value Byte array to be wrapped.
     * @param offset Offset into supplied array to start from.
     * @param length Length in supplied array to use.
     * @throws java.lang.IllegalArgumentException If the argument is null.
     */
    public Blob(final byte[] value, final int offset, final int length)
            throws java.lang.IllegalArgumentException {
        this.value = value;
        this.uvalue = null;
        this.offset = offset;
        this.length = length;
    }

    /**
     * URL constructor. The resource identified by the URL should not be
     * modified until the method ‘detach’ is called. If the resource identified
     * by the URL is modified after the constructor has been called then the
     * Blob behaviour is unspecified.
     *
     * @param sourceUrl Source URL.
     * @throws java.lang.IllegalArgumentException If the argument is null.
     */
    public Blob(final String sourceUrl) throws java.lang.IllegalArgumentException {
        this.value = null;
        this.offset = 0;
        this.length = 0;
        this.uvalue = sourceUrl;
        urlValue = null;
    }

    @Override
    public Element createElement() {
        return new Blob();
    }

    /**
     * The method indicates whether the Blob is attached to a URL or not.
     *
     * @return TRUE if the Blob is attached to a URL otherwise FALSE.
     */
    public boolean isURLBased() {
        return uvalue != null;
    }

//  This might be required for XML serialisation and technologies that use that.  
//  public void setValue(byte[] value)
//  {
//    this.urlValue = null;
//    this.uvalue = null;
//    this.value = value;
//  }
//  public void setURL(String nvalue)
//  {
//    detach();
//    this.urlValue = null;
//    this.uvalue = nvalue;
//    this.value = null;
//  }
    /**
     * Return the value of this Blob as a byte array. If the Blob contains a URL
     * then the designated content is loaded and copied in the returned byte
     * array. The returned byte array should not be modified. If the returned
     * byte array is modified then the Blob behaviour is unspecified.
     *
     * @return The Blob value as a byte array.
     */
    public byte[] getValue() {
        if (isURLBased()) {
            final ByteArrayOutputStream buf = new ByteArrayOutputStream();

            try {
                final byte[] b = new byte[T_BUFFER_SIZE];
                urlValue = new URL(uvalue);
                final InputStream is = urlValue.openStream();

                int read;
                while ((read = is.read(b)) != -1) {
                    buf.write(b, 0, read);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            this.urlValue = null;
            this.uvalue = null;
            this.value = buf.toByteArray();
        }

        return value;
    }

    /**
     * Returns the offset of the supplied byte array.
     *
     * @return The offset.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Returns the length of the supplied array.
     *
     * @return The length.
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the URL of this Blob.
     *
     * @return The URL if URL based, otherwise NULL.
     */
    public String getURL() {
        return uvalue;
    }

    /**
     * The method detaches this Blob wrapper from the resource designated by the
     * URL when this Blob is garbaged.
     */
    public void detach() {
        if (null != urlValue) {
            urlValue = null;
        }
    }

    @Override
    public TypeId getTypeId() {
        return new TypeId(Attribute.BLOB_SHORT_FORM);
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeBlob(this);
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        return decoder.decodeBlob();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Blob)) {
            return false;
        }

        final Blob other = (Blob) obj;
        if ((null != uvalue) || (null != other.uvalue)) {
            return Arrays.equals(this.getValue(), other.getValue());
        }

        return Arrays.equals(this.value, other.value);
    }

    @Override
    public int hashCode() {
        if (uvalue != null) {
            return uvalue.hashCode();
        } else {
            if (value != null) {
                return value.length;
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        if (isURLBased()) {
            return "Blob(URL based, URL=" + uvalue + ")";
        } else {
            if (value == null) {
                return "Blob(buffer based, value = null)";
            } else {
                return "Blob(buffer based, length=" + value.length + ")";
            }
        }
    }
}
