/* ----------------------------------------------------------------------------
 * Copyright (C) 2022      European Space Agency
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
package org.ccsds.moims.mo.mal;

/**
 * Signals that something was not found.
 */
public class NotFoundException extends Exception {

    /**
     * Constructs an {@code NotFoundException} with {@code null} as its error
     * detail message.
     */
    public NotFoundException() {
        super();
    }

    /**
     * Constructs an {@code NotFoundException} with the specified detail
     * message.
     *
     * @param message The detail message (which is saved for later retrieval by
     * the {@link #getMessage()} method)
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code NotFoundException} with the specified detail message
     * and cause.
     *
     * <p>
     * Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated into this exception's detail
     * message.
     *
     * @param message The detail message (which is saved for later retrieval by
     * the {@link #getMessage()} method)
     *
     * @param cause The cause (which is saved for later retrieval by the
     * {@link #getCause()} method). (A null value is permitted, and indicates
     * that the cause is nonexistent or unknown.)
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code NotFoundException} with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}). This
     * constructor is useful for IO exceptions that are little more than
     * wrappers for other throwables.
     *
     * @param cause The cause (which is saved for later retrieval by the
     * {@link #getCause()} method). (A null value is permitted, and indicates
     * that the cause is nonexistent or unknown.)
     */
    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
