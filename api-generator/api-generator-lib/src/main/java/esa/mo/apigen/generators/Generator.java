/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO API Generator
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
package esa.mo.apigen.generators;

import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.MOModel;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Turns a model into output of some kind.
 * <p>
 * The caller says which areas to generate: the model itself holds every specification
 * loaded, including those present only so that references resolve, and which of them are
 * wanted is a property of the build rather than of the model.
 * <p>
 * There is no configuration phase. A generator is constructed and then called.
 */
public interface Generator {

    /**
     * @return the short name used to select this generator, for example "Java".
     */
    String getShortName();

    /**
     * @return a one-line description.
     */
    String getDescription();

    /**
     * Generates output for the given areas.
     *
     * @param model Every loaded specification, linked and validated.
     * @param targets The areas to generate. Others are available for reference only.
     * @param outputDir The directory to write into.
     * @throws IOException if writing fails.
     */
    void generate(MOModel model, List<Area> targets, Path outputDir) throws IOException;
}
