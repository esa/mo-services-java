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

import esa.mo.xsd.util.XmlHelper;
import esa.mo.xsd.util.XmlSpecification;
import esa.mo.xsd.util.XsdSpecification;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import w3c.xsd.Schema;

/**
 * Generates stubs and skeletons for CCSDS MO Service specifications.
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true)
public class StubGenerator extends AbstractMojo {

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
     * The directory for XSD type reference files
     */
    @Parameter(defaultValue = "${basedir}/src/main/xsd-ref", required = true)
    protected File xsdRefDirectory;
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
     * Generate structures code?
     */
    @Parameter(defaultValue = "true")
    protected boolean generateStructures;
    /**
     * Generate COM code?
     */
    @Parameter(defaultValue = "true")
    protected boolean generateCOM;
    /**
     * Force generation
     */
    @Parameter(defaultValue = "false")
    protected boolean forceGeneration;
    /**
     * Extra generator specific properties, held in name/value pairs
     */
    @Parameter
    protected HashMap<String, String> extraProperties;
    /**
     * Package bindings, held in AREA/package pairs For JAXB bindings, held in
     * URI/package pairs
     */
    @Parameter
    protected HashMap<String, String> packageBindings;
    private final Map<String, Generator> GENERATOR_MAP = new HashMap<>();
    private boolean generatorsLoaded = false;

    /**
     * The main entry point when running from the command line.
     *
     * @param args the command line arguments, run with -h option to see help.
     */
    public static void main(final String[] args) {
        final StubGenerator gen = new StubGenerator();
        // default a few values
        gen.generateStructures = true;
        gen.generateCOM = true;
        gen.extraProperties = new HashMap<>();
        gen.packageBindings = new HashMap<>();

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
                    List<HashMap.Entry<String, String>> generators
                            = gen.getSupportedLanguages(new SystemStreamLog());

                    System.out.println("The following language generators are supported:");

                    for (Map.Entry<String, String> g : generators) {
                        System.out.println(String.format("%8s", g.getKey()) + "  :  " + g.getValue());
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
                } else if ("-R".equals(arg)) {
                    // XSD reference directory is held in next argument
                    i++;
                    gen.xsdRefDirectory = new File(args[i]);
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
     * Returns a list of the currently supported generators in a list of
     * name/description pairs. The name value is the one that should be passed
     * to the generator via the setTargetLanguage method.
     *
     * @param logger The logger for the language generators to use when
     * executing.
     * @return The list of available generators.
     */
    public List<Map.Entry<String, String>> getSupportedLanguages(
            final org.apache.maven.plugin.logging.Log logger) {
        loadGenerators(logger);

        List<Map.Entry<String, String>> availableGens = new ArrayList<>(GENERATOR_MAP.size());

        for (Generator g : GENERATOR_MAP.values()) {
            String shortName = g.getShortName();
            String description = g.getDescription();
            availableGens.add(new AbstractMap.SimpleEntry<>(shortName, description));
        }

        return availableGens;
    }

    /**
     * The main entry point when running the stub generator externally from
     * Maven.
     *
     * @param xmlDirectory The directory for XML files
     * @param xmlRefDirectory The directory for XML reference files
     * @param xsdRefDirectory The directory for XSD type reference files
     * @param outputDirectory The working directory to create the generated java
     * source files
     * @return the new stub generator instance
     */
    public static StubGenerator createStubGenerator(final File xmlDirectory,
            final File xmlRefDirectory,
            final File xsdRefDirectory,
            final File outputDirectory) {
        final StubGenerator gen = new StubGenerator();

        gen.setXmlDirectory(xmlDirectory);
        gen.setXmlRefDirectory(xmlRefDirectory);
        gen.setXsdRefDirectory(xsdRefDirectory);
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
     * Sets the directory for XSD type reference files
     *
     * @param xsdRefDirectory The directory for XSD type reference files
     */
    public void setXsdRefDirectory(File xsdRefDirectory) {
        this.xsdRefDirectory = xsdRefDirectory;
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
     * Generate structures code.
     *
     * @param generateStructures If True, generate structure code.
     */
    public void setGenerateStructures(boolean generateStructures) {
        this.generateStructures = generateStructures;
    }

    /**
     * Generate COM code.
     *
     * @param generateCOM If True, generate COM specific code.
     */
    public void setGenerateCOM(boolean generateCOM) {
        this.generateCOM = generateCOM;
    }

    /**
     * Sets the extra generator specific properties, held in name/value pairs
     *
     * @param extraProperties Extra generator specific properties, held in
     * name/value pairs
     */
    public void setExtraProperties(HashMap<String, String> extraProperties) {
        this.extraProperties = extraProperties;
    }

    /**
     * Sets the package bindings, held in AREA/package pairs or URI/package
     * pairs for JAXB
     *
     * @param packageBindings The package bindings.
     */
    public void setPackageBindings(HashMap<String, String> packageBindings) {
        this.packageBindings = packageBindings;
    }

    @Override
    public void execute() throws MojoExecutionException {
        loadGenerators(getLog());

        if (extraProperties == null) {
            extraProperties = new HashMap<>();
        }

        // if the directoy containing the xml specifications exists
        if (xmlDirectory.exists()) {
            // load in any reference specifications
            String steps = "Step 0.. ";
            try {
                final List<XmlSpecification> refSpecs = XmlHelper.loadSpecifications(xmlRefDirectory);
                steps += "Step 1.. ";

                // load in any reference XML schema
                final List<XsdSpecification> refXsd = loadXsdSpecifications(xsdRefDirectory);
                steps += "Step 2.. ";

                // load in the specifications
                final List<XmlSpecification> specs = XmlHelper.loadSpecifications(xmlDirectory);
                steps += "Step 3.. ";

                // work out the latest timestamp of the input files
                long inputTimestamp = getLatestTimestamp(0, refSpecs);
                inputTimestamp = getLatestSchemaTimestamp(inputTimestamp, refXsd);
                inputTimestamp = getLatestTimestamp(inputTimestamp, specs);

                // run the specifications through each generator
                // first process the list of languages to generate
                if ((targetLanguages != null) && (targetLanguages.length > 0)) {

                    if (forceGeneration || (outputDirectory.lastModified() < inputTimestamp)) {
                        if (forceGeneration) {
                            getLog().info("Generation being forced");
                        }
                        for (String targetLanguage : targetLanguages) {
                            final Generator gen = GENERATOR_MAP.get(targetLanguage.toLowerCase());
                            if (gen != null) {
                                processWithGenerator(gen, refSpecs, refXsd, specs);
                            } else {
                                getLog().warn("Could not find generator for language: " + targetLanguage);
                            }
                        }

                        outputDirectory.setLastModified(System.currentTimeMillis());
                    } else {
                        getLog().info("No change in input files detected, generation skipped");
                    }
                } else {
                    getLog().error("No generators selected - could not process files");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new MojoExecutionException(
                        "(a) Exception thrown during the processing of XML file: ", ex);
            } catch (JAXBException ex) {
                ex.printStackTrace();
                throw new MojoExecutionException(
                        steps + " (b) Exception thrown during the processing of XML file: ", ex);
            }
        } else {
            getLog().error("XML directory is not valid");
        }
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

    private static List<XsdSpecification> loadXsdSpecifications(final File directory) throws IOException, JAXBException {
        final List<XsdSpecification> specList = new LinkedList<>();

        if (directory.exists()) {
            final File[] xmsFiles = directory.listFiles();

            for (File file : xmsFiles) {
                if (file.isFile()) {
                    specList.add(loadXsdSpecification(file));
                }
            }
        }

        return specList;
    }

    private static XsdSpecification loadXsdSpecification(final File file) throws IOException, JAXBException {
        final JAXBContext jc = JAXBContext.newInstance("w3c.xsd");
        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        Schema schema = (Schema) unmarshaller.unmarshal(file);
        return new XsdSpecification(file, schema);
    }

    /**
     * The resource each module supplying generators declares them in, one class name per
     * line, in the form the JDK's own service loading uses.
     */
    private static final String SERVICES = "META-INF/services/" + Generator.class.getName();

    /**
     * Finds the generators on the classpath.
     * <p>
     * Each module that supplies one names it in a service file, and this reads those files.
     * It used to be found by scanning: every jar on the plugin's classpath was walked for
     * subtypes of {@link Generator}, which cost around 300 ms of every build to discover
     * five classes - close to ten times what generating the code itself takes. A module
     * that supplies a generator already knows it does, so it says so.
     */
    private void loadGenerators(final org.apache.maven.plugin.logging.Log logger) {
        if (generatorsLoaded) {
            return;
        }

        generatorsLoaded = true;

        for (String name : declaredGenerators(logger)) {
            try {
                final Class<?> cls = Class.forName(name, true, loader());
                if (Modifier.isAbstract(cls.getModifiers())) {
                    continue;
                }
                final Generator g = (Generator) cls.getConstructor(new Class[]{
                    org.apache.maven.plugin.logging.Log.class
                }).newInstance(new Object[]{logger});

                GENERATOR_MAP.put(g.getShortName().toLowerCase(), g);
            } catch (Exception ex) {
                logger.warn("Could not construct generator : " + name);
            }
        }
    }

    /**
     * The plugin's own class loader, which is the realm holding both the plugin and
     * whatever generators the build supplied to it. The thread's context loader is not it:
     * inside a Mojo that is Maven's own, and it can see neither.
     */
    private static ClassLoader loader() {
        return StubGenerator.class.getClassLoader();
    }

    /**
     * @return the class name of every generator declared on the classpath, in the order the
     * class loader offers them.
     */
    private static Set<String> declaredGenerators(
            final org.apache.maven.plugin.logging.Log logger) {
        final Set<String> names = new LinkedHashSet<String>();
        try {
            final Enumeration<URL> found = loader().getResources(SERVICES);
            while (found.hasMoreElements()) {
                final BufferedReader in = new BufferedReader(new InputStreamReader(
                        found.nextElement().openStream(), "UTF-8"));
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        final int comment = line.indexOf('#');
                        final String name = (comment < 0 ? line : line.substring(0, comment)).trim();
                        if (!name.isEmpty()) {
                            names.add(name);
                        }
                    }
                } finally {
                    in.close();
                }
            }
        } catch (IOException ex) {
            logger.warn("Could not read the declared generators: " + ex.getMessage());
        }
        return names;
    }

    private void processWithGenerator(final Generator generator,
            final List<XmlSpecification> refSpecs,
            final List<XsdSpecification> refXsd,
            final List<XmlSpecification> specs) throws MojoExecutionException {
        try {
            generator.reset();
            generator.init(outputDirectory.getPath(), generateStructures, generateCOM, packageBindings, extraProperties);
            generator.postinit(outputDirectory.getPath(), generateStructures, generateCOM, packageBindings, extraProperties);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new MojoExecutionException(
                    "Exception thrown during the opening of the generator", ex);
        }

        // Load the reference specifications
        for (XmlSpecification refSpec : refSpecs) {
            try {
                generator.loadXML(refSpec);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new MojoExecutionException(
                        "Exception thrown during the pre-processing of reference XML file: "
                        + refSpec.getFile().getPath(), ex);
            }
        }

        // Load the XSD specifications
        for (XsdSpecification spec : refXsd) {
            try {
                generator.loadXSD(spec);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new MojoExecutionException(
                        "Exception thrown during the pre-processing of reference XSD file: "
                        + spec.getFile().getPath(), ex);
            }
        }

        // Load the XML specifications
        for (XmlSpecification spec : specs) {
            try {
                generator.loadXML(spec);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new MojoExecutionException(
                        "Exception thrown during the pre-processing of XML file: "
                        + spec.getFile().getPath(), ex);
            }
        }

        // now generator from each specification
        for (XmlSpecification spec : specs) {
            try {
                getLog().info("Generating " + generator.getShortName());
                generator.generate(outputDirectory.getPath(), spec, spec.getRootElement());
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new MojoExecutionException(
                        "(c) Exception thrown during the processing of XML file: "
                        + spec.getFile().getPath(), ex);
            }
        }

        try {
            generator.close(outputDirectory.getPath());
        } catch (IOException ex) {
            throw new MojoExecutionException(
                    "Exception thrown during the closing of the generator", ex);
        }
    }

    private long getLatestTimestamp(long inputTimestamp, final List<XmlSpecification> specs) {
        for (XmlSpecification spec : specs) {
            long fileTimestamp = spec.getFile().lastModified();
            if (fileTimestamp > inputTimestamp) {
                inputTimestamp = fileTimestamp;
            }
        }

        return inputTimestamp;
    }

    private long getLatestSchemaTimestamp(long inputTimestamp, final List<XsdSpecification> specs) {
        for (XsdSpecification spec : specs) {
            long fileTimestamp = spec.getFile().lastModified();
            if (fileTimestamp > inputTimestamp) {
                inputTimestamp = fileTimestamp;
            }
        }

        return inputTimestamp;
    }
}
