package org.ccsds.moims.mo.mpd.structures;

/**
 * The FileMetadata contains specific metadata for files.
 */
public final class FileMetadata implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 2533274807173125L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173125L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The latest update date.
     */
    private org.ccsds.moims.mo.mal.structures.Time updateDate;

    /**
     * The MIME type.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier mime;

    /**
     * The size of the file in bytes.
     */
    private Long fileSize;

    /**
     * Default constructor for FileMetadata.
     * 
     */
    public FileMetadata() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param updateDate The latest update date.
     * @param mime The MIME type.
     * @param fileSize The size of the file in bytes.
     */
    public FileMetadata(org.ccsds.moims.mo.mal.structures.Time updateDate,
            org.ccsds.moims.mo.mal.structures.Identifier mime,
            Long fileSize) {
        this.updateDate = updateDate;
        this.mime = mime;
        this.fileSize = fileSize;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mpd.structures.FileMetadata();
    }

    /**
     * Returns the field updateDate.
     * 
     * @return The field updateDate
     */
    public org.ccsds.moims.mo.mal.structures.Time getUpdateDate() {
        return updateDate;
    }

    /**
     * Returns the field mime.
     * 
     * @return The field mime
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getMime() {
        return mime;
    }

    /**
     * Returns the field fileSize.
     * 
     * @return The field fileSize
     */
    public Long getFileSize() {
        return fileSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FileMetadata) {
            FileMetadata other = (FileMetadata) obj;
            if (updateDate == null) {
                if (other.updateDate != null) {
                    return false;
                }
            } else {
                if (! updateDate.equals(other.updateDate)) {
                    return false;
                }
            }
            if (mime == null) {
                if (other.mime != null) {
                    return false;
                }
            } else {
                if (! mime.equals(other.mime)) {
                    return false;
                }
            }
            if (fileSize == null) {
                if (other.fileSize != null) {
                    return false;
                }
            } else {
                if (! fileSize.equals(other.fileSize)) {
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
        hash = 83 * hash + (updateDate != null ? updateDate.hashCode() : 0);
        hash = 83 * hash + (mime != null ? mime.hashCode() : 0);
        hash = 83 * hash + (fileSize != null ? fileSize.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(FileMetadata: ");
        buf.append("updateDate=").append(updateDate);
        buf.append(", mime=").append(mime);
        buf.append(", fileSize=").append(fileSize);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (updateDate == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'updateDate' cannot be null!");
        }
        if (mime == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'mime' cannot be null!");
        }
        if (fileSize == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'fileSize' cannot be null!");
        }
        encoder.encodeTime(updateDate);
        encoder.encodeIdentifier(mime);
        encoder.encodeLong(fileSize);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        updateDate = decoder.decodeTime();
        mime = decoder.decodeIdentifier();
        fileSize = decoder.decodeLong();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
