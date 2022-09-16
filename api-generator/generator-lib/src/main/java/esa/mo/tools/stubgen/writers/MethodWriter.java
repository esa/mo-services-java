/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Service Stub Generator
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
package esa.mo.tools.stubgen.writers;

import java.io.IOException;

/**
 * Writer for creating a method block.
 */
public interface MethodWriter {

    /**
     * Add a call to the super class.
     *
     * @param method The method name.
     * @param args the arguments to supply.
     * @throws IOException If there is an IO error.
     */
    void addSuperMethodStatement(String method, String args) throws IOException;

    /**
     * Adds a statement to the method.
     *
     * @param statement The statement to add.
     * @throws IOException If there is an IO error.
     */
    void addMethodStatement(String statement) throws IOException;

    /**
     * Adds a statement to the method.
     *
     * @param statement The statement to add.
     * @param addSemi True if adding a semicolon to the statement.
     * @throws IOException If there is an IO error.
     */
    void addMethodStatement(String statement, boolean addSemi) throws IOException;

    /**
     * Adds a statement to the method with type dependency.
     *
     * @param statement The statement to add.
     * @param dependency the types to depend on.
     * @param addSemi True if adding a semicolon to the statement.
     * @throws IOException If there is an IO error.
     */
    void addMethodWithDependencyStatement(String statement,
            String dependency, boolean addSemi) throws IOException;

    /**
     * Adds a set of statements to the method that index an array.
     *
     * @param arrayVariable The variable that contains the array.
     * @param indexVariable The index variable.
     * @param arrayMaxSize The array maximum size.
     * @throws IOException If there is an IO error.
     */
    void addArrayMethodStatement(String arrayVariable,
            String indexVariable, String arrayMaxSize) throws IOException;

    /**
     * Adds statement to close the method.
     *
     * @throws IOException If there is an IO error.
     */
    void addMethodCloseStatement() throws IOException;
}
