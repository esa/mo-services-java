package org.ccsds.moims.mo.mpd.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * The FileMetadata contains specific metadata for files.
 */
public final class FileMetadata implements Composite {

    private static final long serialVersionUID = 2533274807173125L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173125L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The latest update date.
     */
    private Time updateDate;

    /**
     * The MIME type.
     */
    private Identifier mime;

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
    public FileMetadata(Time updateDate,
            Identifier mime,
            Long fileSize) {
        this.updateDate = updateDate;
        this.mime = mime;
        this.fileSize = fileSize;
    }

    @Override
    public Element createElement() {
        return new FileMetadata();
    }

    /**
     * Returns the field updateDate.
     * 
     * @return The field updateDate
     */
    public Time getUpdateDate() {
        return updateDate;
    }

    /**
     * Returns the field mime.
     * 
     * @return The field mime
     */
    public Identifier getMime() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (updateDate == null) {
            throw new MALException("The field 'updateDate' cannot be null!");
        }
        if (mime == null) {
            throw new MALException("The field 'mime' cannot be null!");
        }
        if (fileSize == null) {
            throw new MALException("The field 'fileSize' cannot be null!");
        }
        encoder.encodeTime(updateDate);
        encoder.encodeIdentifier(mime);
        encoder.encodeLong(fileSize);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        updateDate = decoder.decodeTime();
        mime = decoder.decodeIdentifier();
        fileSize = decoder.decodeLong();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
