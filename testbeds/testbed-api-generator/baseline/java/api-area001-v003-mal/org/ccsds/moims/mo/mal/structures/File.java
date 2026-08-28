package org.ccsds.moims.mo.mal.structures;

/**
 * The File structure represents a file and shall be used to hold details
 * about a file. It may also, optionally, hold a BLOB of the file data. The
 * file type shall be denoted using the internet MIME media types.
 */
public final class File implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 281475027043311L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043311L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The file name.
     */
    private String name;

    /**
     * The MIME type of the file, NULL if not known.
     */
    private String mimeType;

    /**
     * The creation timestamp of the file, NULL if not known.
     */
    private org.ccsds.moims.mo.mal.structures.Time creationDate;

    /**
     * The last modification timestamp of the file, NULL if not known.
     */
    private org.ccsds.moims.mo.mal.structures.Time modificationDate;

    /**
     * The size of the file in Octets, NULL if not known.
     */
    private org.ccsds.moims.mo.mal.structures.ULong size;

    /**
     * The contents of the file, NULL if not supplied.
     */
    private org.ccsds.moims.mo.mal.structures.Blob content;

    /**
     * A list of extra metadata for the file.
     */
    private org.ccsds.moims.mo.mal.structures.NamedValueList metaData;

    /**
     * Default constructor for File.
     * 
     */
    public File() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The file name.
     * @param mimeType The MIME type of the file, NULL if not known.
     * @param creationDate The creation timestamp of the file, NULL if not known.
     * @param modificationDate The last modification timestamp of the file, NULL if not known.
     * @param size The size of the file in Octets, NULL if not known.
     * @param content The contents of the file, NULL if not supplied.
     * @param metaData A list of extra metadata for the file.
     */
    public File(String name,
            String mimeType,
            org.ccsds.moims.mo.mal.structures.Time creationDate,
            org.ccsds.moims.mo.mal.structures.Time modificationDate,
            org.ccsds.moims.mo.mal.structures.ULong size,
            org.ccsds.moims.mo.mal.structures.Blob content,
            org.ccsds.moims.mo.mal.structures.NamedValueList metaData) {
        this.name = name;
        this.mimeType = mimeType;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.size = size;
        this.content = content;
        this.metaData = metaData;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param name The file name.
     */
    public File(String name) {
        this.name = name;
        this.mimeType = null;
        this.creationDate = null;
        this.modificationDate = null;
        this.size = null;
        this.content = null;
        this.metaData = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mal.structures.File();
    }

    /**
     * Returns the field name.
     * 
     * @return The field name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the field mimeType.
     * 
     * @return The field mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Returns the field creationDate.
     * 
     * @return The field creationDate
     */
    public org.ccsds.moims.mo.mal.structures.Time getCreationDate() {
        return creationDate;
    }

    /**
     * Returns the field modificationDate.
     * 
     * @return The field modificationDate
     */
    public org.ccsds.moims.mo.mal.structures.Time getModificationDate() {
        return modificationDate;
    }

    /**
     * Returns the field size.
     * 
     * @return The field size
     */
    public org.ccsds.moims.mo.mal.structures.ULong getSize() {
        return size;
    }

    /**
     * Returns the field content.
     * 
     * @return The field content
     */
    public org.ccsds.moims.mo.mal.structures.Blob getContent() {
        return content;
    }

    /**
     * Returns the field metaData.
     * 
     * @return The field metaData
     */
    public org.ccsds.moims.mo.mal.structures.NamedValueList getMetaData() {
        return metaData;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof File) {
            File other = (File) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else {
                if (! name.equals(other.name)) {
                    return false;
                }
            }
            if (mimeType == null) {
                if (other.mimeType != null) {
                    return false;
                }
            } else {
                if (! mimeType.equals(other.mimeType)) {
                    return false;
                }
            }
            if (creationDate == null) {
                if (other.creationDate != null) {
                    return false;
                }
            } else {
                if (! creationDate.equals(other.creationDate)) {
                    return false;
                }
            }
            if (modificationDate == null) {
                if (other.modificationDate != null) {
                    return false;
                }
            } else {
                if (! modificationDate.equals(other.modificationDate)) {
                    return false;
                }
            }
            if (size == null) {
                if (other.size != null) {
                    return false;
                }
            } else {
                if (! size.equals(other.size)) {
                    return false;
                }
            }
            if (content == null) {
                if (other.content != null) {
                    return false;
                }
            } else {
                if (! content.equals(other.content)) {
                    return false;
                }
            }
            if (metaData == null) {
                if (other.metaData != null) {
                    return false;
                }
            } else {
                if (! metaData.equals(other.metaData)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (name != null ? name.hashCode() : 0);
        hash = 83 * hash + (mimeType != null ? mimeType.hashCode() : 0);
        hash = 83 * hash + (creationDate != null ? creationDate.hashCode() : 0);
        hash = 83 * hash + (modificationDate != null ? modificationDate.hashCode() : 0);
        hash = 83 * hash + (size != null ? size.hashCode() : 0);
        hash = 83 * hash + (content != null ? content.hashCode() : 0);
        hash = 83 * hash + (metaData != null ? metaData.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(File: ");
        buf.append("name=").append(name);
        buf.append(", mimeType=").append(mimeType);
        buf.append(", creationDate=").append(creationDate);
        buf.append(", modificationDate=").append(modificationDate);
        buf.append(", size=").append(size);
        buf.append(", content=").append(content);
        buf.append(", metaData=").append(metaData);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (name == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'name' cannot be null!");
        }
        encoder.encodeString(name);
        encoder.encodeNullableString(mimeType);
        encoder.encodeNullableTime(creationDate);
        encoder.encodeNullableTime(modificationDate);
        encoder.encodeNullableULong(size);
        encoder.encodeNullableBlob(content);
        encoder.encodeNullableElement(metaData);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        name = decoder.decodeString();
        mimeType = decoder.decodeNullableString();
        creationDate = decoder.decodeNullableTime();
        modificationDate = decoder.decodeNullableTime();
        size = decoder.decodeNullableULong();
        content = decoder.decodeNullableBlob();
        metaData = (org.ccsds.moims.mo.mal.structures.NamedValueList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.NamedValueList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
