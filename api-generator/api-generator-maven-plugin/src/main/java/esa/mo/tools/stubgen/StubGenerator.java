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

import esa.mo.xsd.SpecificationType;
import esa.mo.xsd.util.XmlHelper;
import esa.mo.xsd.util.XmlHelper.XmlSpecification;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import w3c.xsd.Schema;

/**
 * Generates stubs and skeletons for CCSDS MO Service specifications.
 *
 * @goal generate
 *
 * @phase generate-sources
 */
public class StubGenerator extends AbstractMojo {

    /**
     * The directory for XML files
     *
     * @parameter default-value="${basedir}/src/main/xml"
     * @required
     */
    protected File xmlDirectory;
    /**
     * The directory for XML reference files
     *
     * @parameter default-value="${basedir}/src/main/xml-ref"
     * @required
     */
    protected File xmlRefDirectory;
    /**
     * The directory for XSD type reference files
     *
     * @parameter default-value="${basedir}/src/main/xsd-ref"
     * @required
     */
    protected File xsdRefDirectory;
    /**
     * The working directory to create the generated java source files.
     *
     * @parameter
     * default-value="${project.build.directory}/generated-sources/stub"
     * @required
     */
    protected File outputDirectory;
    /**
     * The target language to create.
     *
     * @parameter
     */
    protected String[] targetLanguages;
    /**
     * Generate structures code?
     *
     * @parameter default-value="true"
     */
    protected boolean generateStructures;
    /**
     * Generate COM code?
     *
     * @parameter default-value="true"
     */
    protected boolean generateCOM;
    /**
     * Force generation
     *
     * @parameter default-value="false"
     */
    protected boolean forceGeneration;
    /**
     * Extra generator specific properties, held in name/value pairs
     *
     * @parameter
     */
    protected HashMap<String, String> extraProperties;
    /**
     * Package bindings, held in AREA/package pairs For JAXB bindings, held in
     * URI/package pairs
     *
     * @parameter
     */
    protected HashMap<String, String> packageBindings;
    private static final Map<String, Generator> GENERATOR_MAP = new HashMap<>();
    private static boolean generatorsLoaded = false;

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

        if (0 < args.length) {
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
                            = getSupportedLanguages(new SystemStreamLog());

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
    public static List<Map.Entry<String, String>> getSupportedLanguages(
            final org.apache.maven.plugin.logging.Log logger) {
        loadGenerators(logger);

        List<Map.Entry<String, String>> rv = new ArrayList<>(GENERATOR_MAP.size());

        for (Map.Entry<String, Generator> entry : GENERATOR_MAP.entrySet()) {
            final Generator g = entry.getValue();

            rv.add(new AbstractMap.SimpleEntry<>(g.getShortName(), g.getDescription()));
        }

        return rv;
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
     * @param packageBindings
     */
    public void setPackageBindings(HashMap<String, String> packageBindings) {
        this.packageBindings = packageBindings;
    }

    @Override
    public void execute() throws MojoExecutionException {
        loadGenerators(getLog());

        if (null == extraProperties) {
            extraProperties = new HashMap<>();
        }

        // if the directoy containing the xml specifications exists
        if (xmlDirectory.exists()) {
            // load in any reference specifications
            String steps = "Step 0.. ";
            try {
                final List<Map.Entry<SpecificationType, XmlSpecification>> refSpecs
                        = XmlHelper.loadSpecifications(xmlRefDirectory);
                steps += "Step 1.. ";

                // load in any reference XML schema
                final List<Map.Entry<Schema, XmlSpecification>> refXsd
                        = loadXsdSpecifications(xsdRefDirectory);
                steps += "Step 2.. ";

                // load in the specifications
                final List<Map.Entry<SpecificationType, XmlSpecification>> specs
                        = XmlHelper.loadSpecifications(xmlDirectory);
                steps += "Step 3.. ";

                // work out the latest timestamp of the input files
                long inputTimestamp = getLatestTimestamp(0, refSpecs);
                inputTimestamp = getLatestSchemaTimestamp(inputTimestamp, refXsd);
                inputTimestamp = getLatestTimestamp(inputTimestamp, specs);

                // run the specifications through each generator
                // first process the list of languages to generate
                if ((null != targetLanguages) && (0 < targetLanguages.length)) {

                    if (forceGeneration || (outputDirectory.lastModified() < inputTimestamp)) {
                        if (forceGeneration) {
                            getLog().info("Generation being forced");
                        }
                        for (String targetLanguage : targetLanguages) {
                            final Generator gen = GENERATOR_MAP.get(targetLanguage.toLowerCase());
                            if (null != gen) {
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

    private static List<Map.Entry<Schema, XmlSpecification>> loadXsdSpecifications(
            final File directory) throws IOException, JAXBException {
        final List<Map.Entry<Schema, XmlSpecification>> specList = new LinkedList<>();

        if (directory.exists()) {
            final File xmlFiles[] = directory.listFiles();

            for (File file : xmlFiles) {
                if (file.isFile()) {
                    specList.add(loadXsdSpecification(file));
                }
            }
        }

        return specList;
    }

    private static AbstractMap.SimpleEntry<Schema, XmlSpecification> loadXsdSpecification(
            final File is) throws IOException, JAXBException {
        final JAXBContext jc = JAXBContext.newInstance("w3c.xsd");
        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        return new AbstractMap.SimpleEntry<>((Schema) unmarshaller.unmarshal(is),
                new XmlSpecification(is, null));
    }

    private static void loadGenerators(final org.apache.maven.plugin.logging.Log logger) {
        if (!generatorsLoaded) {
            generatorsLoaded = true;

            final Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forClassLoader())
                    .setScanners(new SubTypesScanner()));

            final Set<Class<? extends Generator>> classes = reflections.getSubTypesOf(Generator.class);

            for (Class<? extends Generator> cls : classes) {
                final int mods = cls.getModifiers();
                if (!Modifier.isAbstract(mods)) {
                    try {
                        final Generator g = (Generator) cls.getConstructor(new Class[]{
                            org.apache.maven.plugin.logging.Log.class
                        })
                                .newInstance(new Object[]{
                            logger
                        });

                        GENERATOR_MAP.put(g.getShortName().toLowerCase(), g);
                    } catch (Exception ex) {
                        logger.warn("Could not construct generator : " + cls.getName());
                    }
                }
            }
        }
    }

    private void processWithGenerator(final Generator generator,
            final List<Map.Entry<SpecificationType, XmlSpecification>> refSpecs,
            final List<Map.Entry<Schema, XmlSpecification>> refXsd,
            final List<Map.Entry<SpecificationType, XmlSpecification>> specs) throws MojoExecutionException {
        try {
            generator.reset();
            generator.init(outputDirectory.getPath(), generateStructures, generateCOM, packageBindings, extraProperties);
            generator.postinit(outputDirectory.getPath(), generateStructures, generateCOM, packageBindings, extraProperties);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new MojoExecutionException(
                    "Exception thrown during the opening of the generator", ex);
        }

        // pre process the reference specifications
        for (Map.Entry<SpecificationType, XmlSpecification> spec : refSpecs) {
            try {
                generator.preProcess(spec.getKey());
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new MojoExecutionException(
                        "Exception thrown during the pre-processing of reference XML file: "
                        + spec.getValue().file.getPath(), ex);
            }
        }

        // pre process the reference XSD specifications
        for (Map.Entry<Schema, XmlSpecification> spec : refXsd) {
            try {
                generator.preProcess(spec.getKey());
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new MojoExecutionException(
                        "Exception thrown during the pre-processing of reference XSD file: "
                        + spec.getValue().file.getPath(), ex);
            }
        }

        // pre process the specifications
        for (Map.Entry<SpecificationType, XmlSpecification> spec : specs) {
            try {
                generator.preProcess(spec.getKey());
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new MojoExecutionException(
                        "Exception thrown during the pre-processing of XML file: "
                        + spec.getValue().file.getPath(), ex);
            }
        }

        // now generator from each specification
        for (Map.Entry<SpecificationType, XmlSpecification> spec : specs) {
            try {
                getLog().info("Generating " + generator.getShortName());
                generator.compile(outputDirectory.getPath(), spec.getKey(), spec.getValue().rootElement);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new MojoExecutionException(
                        "(c) Exception thrown during the processing of XML file: "
                        + spec.getValue().file.getPath(), ex);
            }
        }

        try {
            generator.close(outputDirectory.getPath());
        } catch (IOException ex) {
            throw new MojoExecutionException(
                    "Exception thrown during the closing of the generator", ex);
        }
    }

    private long getLatestTimestamp(long inputTimestamp,
            final List<Map.Entry<SpecificationType, XmlSpecification>> specs) {
        for (Map.Entry<SpecificationType, XmlSpecification> spec : specs) {
            long fileTimestamp = spec.getValue().file.lastModified();
            if (fileTimestamp > inputTimestamp) {
                inputTimestamp = fileTimestamp;
            }
        }

        return inputTimestamp;
    }

    private long getLatestSchemaTimestamp(long inputTimestamp,
            final List<Map.Entry<Schema, XmlSpecification>> specs) {
        for (Map.Entry<Schema, XmlSpecification> spec : specs) {
            long fileTimestamp = spec.getValue().file.lastModified();
            if (fileTimestamp > inputTimestamp) {
                inputTimestamp = fileTimestamp;
            }
        }

        return inputTimestamp;
    }
}
