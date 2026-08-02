/* ----------------------------------------------------------------------------
 * Copyright (C) 2022      European Space Agency
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
package esa.mo.tools.stubgen.java;

import esa.mo.tools.stubgen.GeneratorLangs;
import esa.mo.tools.stubgen.MOTypeInformation;
import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.AttributeType;
import esa.mo.xsd.CompositeType;
import esa.mo.xsd.EnumerationType;
import esa.mo.xsd.ServiceType;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Generates the factory that creates the Elements of an area.
 *
 * The factory switches on the service number and then on the type number, so
 * that the class of a type is only loaded once a message carries that type.
 * The type numbers of an area are handed out from 1 upwards, so a switch over
 * the band that starts at 1 compiles to a jump table. Where an area also
 * declares numbers far above that band, they are moved to a second method,
 * which keeps the jump table of the first one from being stretched to span the
 * gap between them.
 */
public class JavaElementFactory {

    private final GeneratorLangs generator;

    private final MOTypeInformation typeInformation;

    public JavaElementFactory(GeneratorLangs generator, MOTypeInformation typeInformation) {
        this.generator = generator;
        this.typeInformation = typeInformation;
    }

    /**
     * Collects the types of a set of data types, as pairs of type number and
     * the expression that creates one. A list type takes the negative of the
     * type number of the type it holds.
     *
     * @param areaName The name of the area the types belong to.
     * @param serviceName The name of the service, or null for area level types.
     * @param dataTypes The declared data types.
     * @return The pairs, in declaration order.
     */
    List<String[]> collectTypes(String areaName, String serviceName, List<Object> dataTypes) {
        List<String[]> types = new LinkedList<>();

        for (Object oType : dataTypes) {
            String name;
            int number;

            if (oType instanceof AttributeType) {
                AttributeType dt = (AttributeType) oType;
                AttributeTypeDetails details = typeInformation.getAttributeDetails(areaName, dt.getName());
                number = dt.getShortFormPart();
                name = dt.getName();

                // A native attribute is carried by a Union rather than by a class of its own
                String created = details.isNativeType()
                        ? typeInformation.createElementType(StdStrings.MAL, null, StdStrings.UNION)
                        + "(" + details.getDefaultValue() + ")"
                        : typeInformation.createElementType(areaName, serviceName, dt.getName()) + "()";
                types.add(new String[]{String.valueOf(number), "new " + created});
                types.add(new String[]{String.valueOf(-number),
                    "new " + typeInformation.createElementType(areaName, serviceName, name + "List") + "()"});
                continue;
            } else if (oType instanceof CompositeType) {
                CompositeType dt = (CompositeType) oType;

                if (dt.getShortFormPart() == null) {
                    continue; // Abstract: it is never created
                }

                number = dt.getShortFormPart();
                name = dt.getName();
            } else if (oType instanceof EnumerationType) {
                EnumerationType dt = (EnumerationType) oType;
                number = dt.getShortFormPart();
                name = dt.getName();
            } else {
                continue;
            }

            types.add(new String[]{String.valueOf(number),
                "new " + typeInformation.createElementType(areaName, serviceName, name) + "()"});
            types.add(new String[]{String.valueOf(-number),
                "new " + typeInformation.createElementType(areaName, serviceName, name + "List") + "()"});
        }

        return types;
    }

    /**
     * Returns true when a switch over these numbers compiles to a jump table
     * rather than to a binary search. This is the rule the Java compiler itself
     * applies, comparing the size of a table that spans the whole range against
     * the size of a list of the labels that are actually used.
     *
     * @param count How many labels there are.
     * @param span The distance from the lowest to the highest label.
     * @return True if a jump table is emitted.
     */
    static boolean compilesToJumpTable(int count, long span) {
        return (4 + span) + 3L * 3L <= (3 + 2L * count) + 3L * count;
    }

    /**
     * Returns the highest type number, counted out from 1 in both directions,
     * up to which a switch over the type numbers still compiles to a jump
     * table. Zero when no band does.
     *
     * The band is anchored at 1 rather than placed on the longest unbroken run,
     * because type numbers are handed out from 1 upwards, so a band that starts
     * anywhere else leaves behind the numbers that are most likely to be asked
     * for. Every candidate is tried rather than stopping at the first that
     * fails: a group of numbers further out can be dense enough to pay for the
     * gap in front of it.
     *
     * @param numbers The type numbers of the switch.
     * @return The highest type number of the band, or zero if there is none.
     */
    static int widestJumpTableBand(Collection<Integer> numbers) {
        // A type and the list that holds it share a number, save for the sign,
        // so the band has to widen by the same step on both sides at once.
        SortedSet<Integer> magnitudes = new TreeSet<>();

        for (Integer number : numbers) {
            magnitudes.add(Math.abs(number));
        }

        int widest = 0;

        for (Integer magnitude : magnitudes) {
            int count = 0;

            for (Integer number : numbers) {
                if (Math.abs(number) <= magnitude) {
                    count++;
                }
            }

            if (compilesToJumpTable(count, 2L * magnitude + 1L)) {
                widest = magnitude;
            }
        }

        return widest;
    }

    /**
     * Writes the method, or the pair of methods, that create the Element of a
     * type number. The class of a type is named only inside its own branch, so
     * it is loaded when a message first carries that type, and not before.
     */
    private void addTypeSwitch(ClassWriter file, String methodName,
            List<String[]> types, String comment) throws IOException {
        Map<Integer, String> byNumber = new TreeMap<>();

        for (String[] type : types) {
            byNumber.put(Integer.valueOf(type[0]), type[1]);
        }

        int band = widestJumpTableBand(byNumber.keySet());
        Map<Integer, String> inBand = new TreeMap<>();
        Map<Integer, String> outOfBand = new TreeMap<>();

        for (Map.Entry<Integer, String> entry : byNumber.entrySet()) {
            boolean fits = Math.abs(entry.getKey()) <= band;
            (fits ? inBand : outOfBand).put(entry.getKey(), entry.getValue());
        }

        // Splitting the types over two methods only pays off when there is a
        // jump table to keep and something that would otherwise stretch it.
        if (inBand.isEmpty() || outOfBand.isEmpty()) {
            addSwitchMethod(file, methodName, comment, byNumber, null);
            return;
        }

        String outside = methodName + "OutOfBand";
        addSwitchMethod(file, methodName, comment, inBand, outside);
        addSwitchMethod(file, outside, "Creates an Element whose type number lies too"
                + " far out to be held in the jump table of " + methodName + "()."
                + " This says nothing about how often the type is asked for: the"
                + " numbers of an Area are not handed out in the order of use.",
                outOfBand, null);
    }

    /**
     * Returns true when the numbers are better switched on one side of zero at
     * a time than all together.
     *
     * A type and the list that holds it take the same number with opposite
     * signs, so numbers that lie far from zero leave the whole distance across
     * zero empty between them. A switch over both sides at once has to span
     * that emptiness and falls back to a binary search, while each side on its
     * own is tight enough for a jump table.
     *
     * @param numbers The type numbers of the switch.
     * @return True if each side of zero should be switched on its own.
     */
    static boolean shouldSplitOnSign(Collection<Integer> numbers) {
        int positives = 0;
        int negatives = 0;
        int highest = Integer.MIN_VALUE;
        int lowest = Integer.MAX_VALUE;
        int highestPositive = Integer.MIN_VALUE;
        int lowestPositive = Integer.MAX_VALUE;
        int highestNegative = Integer.MIN_VALUE;
        int lowestNegative = Integer.MAX_VALUE;

        for (Integer number : numbers) {
            highest = Math.max(highest, number);
            lowest = Math.min(lowest, number);

            if (number > 0) {
                positives++;
                highestPositive = Math.max(highestPositive, number);
                lowestPositive = Math.min(lowestPositive, number);
            } else {
                negatives++;
                highestNegative = Math.max(highestNegative, number);
                lowestNegative = Math.min(lowestNegative, number);
            }
        }

        if (positives == 0 || negatives == 0) {
            return false; // There is only one side to switch on
        }

        // Nothing is gained by splitting numbers that already answer with a
        // jump table, and a side that stays a binary search is not worth the
        // test that reaches it.
        return !compilesToJumpTable(numbers.size(), (long) highest - lowest + 1L)
                && compilesToJumpTable(positives, (long) highestPositive - lowestPositive + 1L)
                && compilesToJumpTable(negatives, (long) highestNegative - lowestNegative + 1L);
    }

    /**
     * Writes one method holding a switch over type numbers.
     *
     * @param file The file to write the method to.
     * @param methodName The name of the method.
     * @param comment The comment of the method.
     * @param types The types the switch answers for, by type number.
     * @param fallback The method the switch falls back on for a type number it
     * does not answer for, or null to answer with nothing.
     */
    private void addSwitchMethod(ClassWriter file, String methodName, String comment,
            Map<Integer, String> types, String fallback) throws IOException {
        CompositeField rtype = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "structures.Element", false),
                false, true, null);
        CompositeField arg = generator.createCompositeElementsDetails(file, false, "typeNumber",
                TypeUtils.createTypeReference(null, null, "int", false), false, false, null);

        MethodWriter method = file.addMethodOpenStatement(false, true, StdStrings.PRIVATE,
                rtype, methodName, Arrays.asList(arg), null, comment, null, null, false);

        String missing = (fallback == null) ? "null" : fallback + "(typeNumber)";

        if (types.isEmpty()) {
            method.addLine("return " + missing + ";");
            method.addMethodCloseStatement();
            return;
        }

        if (shouldSplitOnSign(types.keySet())) {
            Map<Integer, String> positives = new TreeMap<>();
            Map<Integer, String> negatives = new TreeMap<>();

            for (Map.Entry<Integer, String> entry : types.entrySet()) {
                boolean positive = entry.getKey() > 0;
                (positive ? positives : negatives).put(entry.getKey(), entry.getValue());
            }

            // These numbers lie far from zero, so the types and their lists are
            // reached apart: together they would span the whole way across zero
            method.addLine("if (typeNumber > 0) {");
            addSwitchLines(method, positives, missing, "    ");
            method.addLine("}");
            method.addLine("");
            addSwitchLines(method, negatives, missing, "");
            method.addMethodCloseStatement();
            return;
        }

        addSwitchLines(method, types, missing, "");
        method.addMethodCloseStatement();
    }

    /**
     * Writes the lines of one switch over type numbers.
     *
     * @param method The method to write the switch to.
     * @param types The types the switch answers for, by type number.
     * @param missing What the switch answers with for a type number it does not
     * answer for.
     * @param indent The step this switch takes past the body of the method.
     */
    private void addSwitchLines(MethodWriter method, Map<Integer, String> types,
            String missing, String indent) throws IOException {
        // The writer indents each line by itself, so only the step that the
        // body of the switch takes past the switch is added here.
        method.addLine(indent + "switch (typeNumber) {");

        for (Map.Entry<Integer, String> entry : types.entrySet()) {
            method.addLine(indent + "    case " + entry.getKey()
                    + ": return " + entry.getValue() + ";");
        }

        method.addLine(indent + "    default: return " + missing + ";");
        method.addLine(indent + "}");
    }

    /**
     * Creates the factory that makes the Elements of an area.
     *
     * The factory switches on the service number and then on the type number,
     * rather than holding an instance of every type of the area. Holding the
     * instances meant that every class of the area was loaded as soon as the
     * area was, which on a small computer costs a noticeable part of the start
     * up time, for types that a given deployment may never exchange.
     *
     * @param areaFolder The folder to write the factory to.
     * @param area The area to write the factory of.
     * @throws IOException If the file could not be written.
     */
    public void createAreaElementFactoryClass(File areaFolder, AreaType area) throws IOException {
        String areaName = area.getName();
        String className = areaName + "ElementFactory";
        ClassWriter file = generator.createClassFile(areaFolder, className);

        file.addPackageStatement(areaName, null, null);
        file.addClassOpenStatement(className, true, false, null,
                "org.ccsds.moims.mo.mal.AreaElementFactory",
                "Creates the Elements of the " + areaName + " area, without holding an"
                + " instance of each of them, so that the class of a type is only loaded"
                + " once a message carries that type.");

        // The types declared by the area itself answer to service number 0
        List<String[]> areaTypes = (area.getDataTypes() == null) ? new LinkedList<>()
                : collectTypes(areaName, null, area.getDataTypes().getFundamentalOrAttributeOrComposite());

        CompositeField rtype = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "structures.Element", false),
                false, true, null);
        CompositeField argService = generator.createCompositeElementsDetails(file, false, "serviceNumber",
                TypeUtils.createTypeReference(null, null, "int", false), false, false, null);
        CompositeField argType = generator.createCompositeElementsDetails(file, false, "typeNumber",
                TypeUtils.createTypeReference(null, null, "int", false), false, false, null);

        MethodWriter method = file.addMethodOpenStatementOverride(rtype, "createElement",
                Arrays.asList(argService, argType), null, false);
        method.addLine("switch (serviceNumber) {");
        method.addLine("    case 0: return createAreaElement(typeNumber);");

        for (ServiceType service : area.getService()) {
            method.addLine("    case " + service.getNumber() + ": return create"
                    + service.getName() + "Element(typeNumber);");
        }

        method.addLine("    default: return null;");
        method.addLine("}");
        method.addMethodCloseStatement();

        // The factory says which Area it belongs to, so that registering it
        // cannot associate it with the wrong number or version.
        CompositeField intReturn = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(null, null, "int", false), false, false, null);

        MethodWriter areaNumber = file.addMethodOpenStatementOverride(intReturn,
                "getAreaNumber", Arrays.asList(), null, false);
        areaNumber.addLine("return " + area.getNumber() + ";");
        areaNumber.addMethodCloseStatement();

        MethodWriter areaVersion = file.addMethodOpenStatementOverride(intReturn,
                "getAreaVersion", Arrays.asList(), null, false);
        areaVersion.addLine("return " + area.getVersion() + ";");
        areaVersion.addMethodCloseStatement();

        addTypeSwitch(file, "createAreaElement", areaTypes,
                "Creates an Element declared by the area itself.");

        for (ServiceType service : area.getService()) {
            List<String[]> serviceTypes = (service.getDataTypes() == null) ? new LinkedList<>()
                    : collectTypes(areaName, service.getName(),
                            service.getDataTypes().getCompositeOrEnumeration());
            addTypeSwitch(file, "create" + service.getName() + "Element", serviceTypes,
                    "Creates an Element declared by the " + service.getName() + " service.");
        }

        file.addClassCloseStatement();
        file.flush();
    }
}
