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
package esa.mo.tools.stubgen;

import esa.mo.apigen.generators.Generator;
import esa.mo.apigen.generators.docx.DocxGenerator;
import esa.mo.apigen.generators.java.JavaGenerator;
import esa.mo.apigen.generators.xhtml.XhtmlGenerator;
import esa.mo.apigen.importers.ImportException;
import esa.mo.apigen.importers.xml.XmlImporter;
import esa.mo.apigen.link.Linker;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.SourceRef;
import esa.mo.apigen.model.Specification;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Generates stubs and skeletons for CCSDS MO Service specifications.
 * <p>
 * Every specification, reference or not, is read and linked into one model before any is
 * generated, so that a reference across two files resolves whichever is read first. Only
 * the areas of the files in {@link #xmlDirectory} are generated.
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true)
public class StubGenerator extends AbstractMojo {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * The directory for XML files
     */
    @Parameter(defaultValue = "${basedir}/src/main/xml", required = true)
    protected File xmlDirectory;
    /**
     * The directory for XML reference files
     */
    @Parameter(defaultValue = "${basedir}/src/main/xml-ref", required = true)
    protected File xmlRefDirectory;
    /**
     * The working directory to create the generated java source files.
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/stub", required = true)
    protected File outputDirectory;
    /**
     * The target language to create.
     */
    @Parameter
    protected String[] targetLanguages;
    /**
     * Force generation
     */
    @Parameter(defaultValue = "false")
    protected boolean forceGeneration;

    /**
     * The main entry point when running from the command line.
     *
     * @param args the command line arguments, run with -h option to see help.
     */
    public static void main(final String[] args) {
        final StubGenerator gen = new StubGenerator();

        boolean printHelp = false;

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                final String arg = args[i];

                if ("-h".equalsIgnoreCase(arg)) {
                    // print out help and exit
                    printHelp = true;
                    break;
                } else if ("-?".equalsIgnoreCase(arg)) {
                    // print out help and exit
                    printHelp = true;
                    break;
                } else if ("-l".equalsIgnoreCase(arg)) {
                    // print out list of supported generators and exit
                    System.out.println("The following language generators are supported:");

                    for (Generator g : generators()) {
                        System.out.println(String.format("%8s", g.getShortName()) + "  :  " + g.getDescription());
                    }

                    return;
                } else if ("-x".equalsIgnoreCase(arg)) {
                    // XML directory is held in next argument
                    i++;
                    gen.xmlDirectory = new File(args[i]);
                } else if ("-r".equals(arg)) {
                    // XML reference directory is held in next argument
                    i++;
                    gen.xmlRefDirectory = new File(args[i]);
                } else if ("-o".equalsIgnoreCase(arg)) {
                    // output directory is held in next argument
                    i++;
                    gen.outputDirectory = new File(args[i]);
                } else if ("-t".equalsIgnoreCase(arg)) {
                    // target languages is held in next argument as a comma separated list
                    i++;
                    final String targets = args[i];

                    gen.targetLanguages = targets.split(",");
                }
            }
        } else {
            printHelp = true;
        }

        if (printHelp) {
            printHelp(System.out);
        } else {
            try {
                gen.execute();
            } catch (MojoExecutionException ex) {
                System.err.println("ERROR: Exception thrown : " + ex.getMessage());
            }
        }
    }

    /**
     * The main entry point when running the stub generator externally from
     * Maven.
     *
     * @param xmlDirectory The directory for XML files
     * @param xmlRefDirectory The directory for XML reference files
     * @param outputDirectory The working directory to create the generated java
     * source files
     * @return the new stub generator instance
     */
    public static StubGenerator createStubGenerator(final File xmlDirectory,
            final File xmlRefDirectory,
            final File outputDirectory) {
        final StubGenerator gen = new StubGenerator();

        gen.setXmlDirectory(xmlDirectory);
        gen.setXmlRefDirectory(xmlRefDirectory);
        gen.setOutputDirectory(outputDirectory);

        return gen;
    }

    /**
     * Sets the directory for XML files
     *
     * @param xmlDirectory The directory for XML files
     */
    public void setXmlDirectory(File xmlDirectory) {
        this.xmlDirectory = xmlDirectory;
    }

    /**
     * Sets the directory for XML reference files
     *
     * @param xmlRefDirectory The directory for XML reference files
     */
    public void setXmlRefDirectory(File xmlRefDirectory) {
        this.xmlRefDirectory = xmlRefDirectory;
    }

    /**
     * Sets the working directory to create the generated java source files.
     *
     * @param outputDirectory The working directory to create the generated java
     * source files.
     */
    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    /**
     * Sets the target languages to create.
     *
     * @param targetLanguages The target languages to create.
     */
    public void setTargetLanguages(String[] targetLanguages) {
        this.targetLanguages = targetLanguages;
    }

    /**
     * Sets whether to generate even when no input file is newer than the output.
     *
     * @param forceGeneration If True, always generate.
     */
    public void setForceGeneration(boolean forceGeneration) {
        this.forceGeneration = forceGeneration;
    }

    @Override
    public void execute() throws MojoExecutionException {
        if (!xmlDirectory.exists()) {
            getLog().error("XML directory is not valid");
            return;
        }

        if ((targetLanguages == null) || (targetLanguages.length == 0)) {
            getLog().error("No generators selected - could not process files");
            return;
        }

        final List<File> refFiles = listFiles(xmlRefDirectory);
        final List<File> files = listFiles(xmlDirectory);

        if (!forceGeneration && (outputDirectory.lastModified() >= latestTimestamp(refFiles, files))) {
            getLog().info("No change in input files detected, generation skipped");
            return;
        }

        if (forceGeneration) {
            getLog().info("Generation being forced");
        }

        // A file named in both directories is read once, and generated.
        final Map<String, File> loaded = new LinkedHashMap<String, File>();
        for (File file : refFiles) {
            loaded.put(file.getAbsolutePath(), file);
        }
        for (File file : files) {
            loaded.put(file.getAbsolutePath(), file);
        }

        final MOModel model = new MOModel();
        final List<Area> areas = new ArrayList<Area>();

        for (File file : loaded.values()) {
            final Specification specification = read(file);
            model.add(specification);
            if (files.contains(file)) {
                areas.addAll(specification.getAreas());
            }
        }

        new Linker().link(model);

        for (String targetLanguage : targetLanguages) {
            final Generator generator = generatorFor(targetLanguage);
            if (generator == null) {
                getLog().warn("Could not find generator for language: " + targetLanguage);
                continue;
            }

            getLog().info("Generating " + generator.getShortName());
            final long started = System.currentTimeMillis();
            try {
                generator.generate(model, areas, outputDirectory.toPath());
            } catch (IOException ex) {
                throw new MojoExecutionException(
                        "Exception thrown while generating " + generator.getShortName(), ex);
            }
            getLog().info("Processed all Areas in " + (System.currentTimeMillis() - started)
                    + " ms (" + areas.size() + " area(s))");
        }

        outputDirectory.setLastModified(System.currentTimeMillis());
    }

    private static void printHelp(java.io.PrintStream out) {
        out.println("Usage: stub-generator [-options]");
        out.println("");
        out.println("where options include:");
        out.println("    -x <directory containing the XML service specification>");
        out.println("                  Specify the location of the XML specifications to process");
        out.println("    -r <directory containing the reference XML service specification>");
        out.println("                  Specify the location of the XML specifications to process");
        out.println("                  that are referenced but do not require any generation");
        out.println("    -o <output directory>");
        out.println("                  Specify the location of the output directory");
        out.println("    -t <target languages to generate>");
        out.println("                  A , separated list of language generators");
        out.println("    -l");
        out.println("                  Lists supported language generators");
        out.println("    -? -h         Print this help message");
    }

    /**
     * @return one of each generator the library supplies.
     */
    private static List<Generator> generators() {
        return Arrays.<Generator>asList(new JavaGenerator(), new DocxGenerator(), new XhtmlGenerator());
    }

    private static Generator generatorFor(final String shortName) {
        for (Generator g : generators()) {
            if (g.getShortName().equalsIgnoreCase(shortName)) {
                return g;
            }
        }
        return null;
    }

    /**
     * @return every file directly in the directory, by name, or none if it does not exist.
     */
    private static List<File> listFiles(final File directory) {
        final List<File> list = new ArrayList<File>();
        final File[] found = directory.listFiles();

        if (found != null) {
            Arrays.sort(found);
            for (File file : found) {
                if (file.isFile()) {
                    list.add(file);
                }
            }
        }

        return list;
    }

    private static long latestTimestamp(final List<File> refFiles, final List<File> files) {
        long latest = 0;
        for (File file : refFiles) {
            latest = Math.max(latest, file.lastModified());
        }
        for (File file : files) {
            latest = Math.max(latest, file.lastModified());
        }
        return latest;
    }

    private static Specification read(final File file) throws MojoExecutionException {
        try {
            final Reader in = new InputStreamReader(new FileInputStream(file), UTF8);
            try {
                return new XmlImporter().read(in, new SourceRef(file.getName(), file.getPath()));
            } finally {
                in.close();
            }
        } catch (IOException ex) {
            throw new MojoExecutionException("Could not read " + file.getPath(), ex);
        } catch (ImportException ex) {
            throw new MojoExecutionException("Could not read " + file.getPath() + ": " + ex.getMessage(), ex);
        }
    }
}
