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
package esa.mo.apigen.generators.java.writers;

import esa.mo.apigen.generators.java.JavaClassBuilder;
import esa.mo.apigen.generators.java.JavaMethodBuilder;
import esa.mo.apigen.generators.java.JavaNaming;
import esa.mo.apigen.generators.java.JavaSource;
import esa.mo.apigen.generators.java.JavaTypes;
import esa.mo.apigen.generators.java.JumpTable;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.types.AttributeType;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.EnumerationType;
import esa.mo.apigen.model.types.TypeDefinition;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Writes the factory that creates the Elements of an area.
 * <p>
 * A type is reached by its number: the type itself under its short form part, and its list
 * under the negative of it. Nothing is held, so the class of a type is only loaded once a
 * message carries that type.
 * <p>
 * The cases are laid out so that a lookup is a jump rather than a binary search, following
 * the rules in {@link JumpTable}: the numbers close enough together to be spanned by one
 * table are switched together, and the ones that lie past it are answered by a second
 * method, so that they do not stretch the table over the gap in front of them.
 */
public final class ElementFactoryWriter {

    /**
     * The Element the factory answers with, and the one argument every switch takes.
     */
    private static final String ELEMENT = JavaNaming.MAL_STRUCTURES + "Element";

    private static final String AREA_METHOD = "createAreaElement";

    private ElementFactoryWriter() {
    }

    /**
     * @return the source of the area's element factory.
     */
    public static String write(Area area) {
        String name = area.getName();
        JavaClassBuilder clazz = JavaClassBuilder.named(name + "ElementFactory")
                .inPackage(JavaNaming.packageOf(area)).asFinal()
                .implementing(JavaNaming.MAL + "AreaElementFactory")
                .comment("Creates the Elements of the " + name + " area, without holding an"
                        + " instance of each of them, so that the class of a type is only"
                        + " loaded once a message carries that type.");
        JavaSource out = clazz.open();

        // The types declared by the area itself answer to service number 0
        Map<Integer, String> areaTypes = collectTypes(area, null);
        JumpTable.Split areaSplit = JumpTable.splitTypes(areaTypes);

        // The types of each service, gathered up front: an Area whose services declare
        // none of their own is reached without a switch over them.
        Map<Service, Map<Integer, String>> typesByService
                = new LinkedHashMap<Service, Map<Integer, String>>();
        boolean anyServiceHasTypes = false;

        for (Service service : area.getServices()) {
            Map<Integer, String> serviceTypes = collectTypes(area, service);
            typesByService.put(service, serviceTypes);
            anyServiceHasTypes = anyServiceHasTypes || !serviceTypes.isEmpty();
        }

        JavaMethodBuilder createElement = JavaMethodBuilder.named("createElement")
                .asOverride()
                .returns(ELEMENT, null)
                .argument("int", "serviceNumber", null)
                .argument("int", "typeNumber", null);

        if (anyServiceHasTypes) {
            createElement.line("switch (serviceNumber) {");
            createElement.line("    case 0: return " + AREA_METHOD + "(typeNumber);");
            for (Service service : area.getServices()) {
                createElement.line("    case " + service.getNumber() + ": return "
                        + methodNameOf(service) + "(typeNumber);");
            }
            createElement.line("    default: return null;");
            createElement.line("}");
        } else {
            // Every type of this Area is declared by the Area itself, so the types are
            // answered here rather than through a switch over services that would only
            // ever lead back to the one branch.
            createElement.line("if (serviceNumber != 0) {");
            createElement.line("    return null; // This Area declares no types under a service");
            createElement.line("}");
            createElement.lines(switchBody(areaSplit.getInBand(),
                    areaSplit.isSplit() ? outOfBandNameOf(AREA_METHOD) : null));
        }
        createElement.write(out);

        // The factory says which Area it belongs to, so that registering it cannot
        // associate it with the wrong number or version.
        JavaMethodBuilder.named("getAreaNumber").asOverride().returns("int", null)
                .line("return " + area.getNumber() + ";").write(out);
        JavaMethodBuilder.named("getAreaVersion").asOverride().returns("int", null)
                .line("return " + area.getVersion() + ";").write(out);

        if (anyServiceHasTypes) {
            writeTypeSwitch(out, AREA_METHOD, areaTypes,
                    "Creates an Element declared by the area itself.");
            for (Map.Entry<Service, Map<Integer, String>> entry : typesByService.entrySet()) {
                writeTypeSwitch(out, methodNameOf(entry.getKey()), entry.getValue(),
                        "Creates an Element declared by the " + entry.getKey().getName()
                        + " service.");
            }
        } else if (areaSplit.isSplit()) {
            // createElement() holds the jump table itself, so only the types that did not
            // fit in it are left to write out
            writeOutOfBandMethod(out, AREA_METHOD, areaSplit);
        }

        return clazz.close();
    }

    /**
     * Collects the types of an area or of one of its services, as the expression that
     * creates one, by type number. The list of a type takes the negative of its number.
     *
     * @param area The area the types belong to.
     * @param service The service, or null for the types of the area itself.
     * @return the expressions, by type number.
     */
    private static Map<Integer, String> collectTypes(Area area, Service service) {
        Map<Integer, String> types = new TreeMap<Integer, String>();
        String pkg = (service == null)
                ? JavaNaming.packageOf(area, JavaNaming.STRUCTURES)
                : JavaNaming.packageOf(service, JavaNaming.STRUCTURES);
        List<TypeDefinition> declared = (service == null)
                ? area.getDataTypes() : service.getDataTypes();

        for (TypeDefinition type : declared) {
            Integer number = shortFormPartOf(type);

            if (number == null) {
                continue; // Abstract, or a fundamental: it is never created
            }

            String name = type.getName();
            String created;

            if (type instanceof AttributeType && JavaTypes.isNative(area.getName(), name)) {
                // A native attribute is carried by a Union rather than by a class of its own
                created = "new " + JavaNaming.MAL_STRUCTURES + "Union("
                        + JavaTypes.nativeDefault(name) + ")";
            } else {
                created = "new " + pkg + "." + JavaTypes.className(area.getName(), name) + "()";
            }

            types.put(number, created);
            types.put(-number, "new " + pkg + "."
                    + JavaTypes.className(area.getName(), name + "List") + "()");
        }

        return types;
    }

    /**
     * Writes the method, or the pair of methods, that create the Element of a type number.
     * The class of a type is named only inside its own branch, so it is loaded when a
     * message first carries that type, and not before.
     */
    private static void writeTypeSwitch(JavaSource out, String methodName,
            Map<Integer, String> types, String comment) {
        JumpTable.Split split = JumpTable.splitTypes(types);

        // Splitting the types over two methods only pays off when there is a jump table to
        // keep and something that would otherwise stretch it.
        if (!split.isSplit()) {
            writeSwitchMethod(out, methodName, comment, split.all(), null);
            return;
        }

        writeSwitchMethod(out, methodName, comment, split.getInBand(), outOfBandNameOf(methodName));
        writeOutOfBandMethod(out, methodName, split);
    }

    /**
     * Writes the method that answers for the types the jump table could not hold.
     */
    private static void writeOutOfBandMethod(JavaSource out, String methodName,
            JumpTable.Split split) {
        writeSwitchMethod(out, outOfBandNameOf(methodName),
                "Creates an Element whose type number lies too far out to be held in the"
                + " jump table that is asked first. This says nothing about how often the"
                + " type is asked for: the numbers of an Area are not handed out in the"
                + " order of use.", split.getOutOfBand(), null);
    }

    /**
     * Writes one method holding a switch over type numbers.
     *
     * @param out The source to write the method to.
     * @param methodName The name of the method.
     * @param comment The comment of the method.
     * @param types The types the switch answers for, by type number.
     * @param fallback The method the switch falls back on for a type number it does not
     * answer for, or null to answer with nothing.
     */
    private static void writeSwitchMethod(JavaSource out, String methodName, String comment,
            Map<Integer, String> types, String fallback) {
        JavaMethodBuilder.named(methodName).scope("private").asStatic()
                .comment(comment)
                .returns(ELEMENT, null)
                .argument("int", "typeNumber", null)
                .lines(switchBody(types, fallback))
                .write(out);
    }

    /**
     * Builds the body that answers a type number: one switch, or a switch for each side of
     * zero where the numbers lie too far out to be held together.
     *
     * @param types The types the body answers for, by type number.
     * @param fallback The method the body falls back on for a type number it does not
     * answer for, or null to answer with nothing.
     * @return the lines of the body, indented relative to it.
     */
    private static List<String> switchBody(Map<Integer, String> types, String fallback) {
        String missing = (fallback == null) ? "null" : fallback + "(typeNumber)";
        List<String> lines = new ArrayList<String>();

        if (types.isEmpty()) {
            lines.add("return " + missing + ";");
            return lines;
        }

        if (JumpTable.shouldSplitOnSign(types.keySet())) {
            Map<Integer, String> positives = new TreeMap<Integer, String>();
            Map<Integer, String> negatives = new TreeMap<Integer, String>();

            for (Map.Entry<Integer, String> entry : types.entrySet()) {
                boolean positive = entry.getKey() > 0;
                (positive ? positives : negatives).put(entry.getKey(), entry.getValue());
            }

            // These numbers lie far from zero, so the types and their lists are reached
            // apart: together they would span the whole way across zero
            lines.add("if (typeNumber > 0) {");
            addSwitchLines(lines, positives, missing, "    ");
            lines.add("}");
            addSwitchLines(lines, negatives, missing, "");
            return lines;
        }

        addSwitchLines(lines, types, missing, "");
        return lines;
    }

    /**
     * Adds the lines of one switch over type numbers.
     *
     * @param lines The body being built.
     * @param types The types the switch answers for, by type number.
     * @param missing What the switch answers with for a type number it does not answer for.
     * @param indent The step this switch takes past the body of the method.
     */
    private static void addSwitchLines(List<String> lines, Map<Integer, String> types,
            String missing, String indent) {
        lines.add(indent + "switch (typeNumber) {");
        for (Map.Entry<Integer, String> entry : types.entrySet()) {
            lines.add(indent + "    case " + entry.getKey() + ": return "
                    + entry.getValue() + ";");
        }
        lines.add(indent + "    default: return " + missing + ";");
        lines.add(indent + "}");
    }

    private static String methodNameOf(Service service) {
        return "create" + service.getName() + "Element";
    }

    private static String outOfBandNameOf(String methodName) {
        return methodName + "OutOfBand";
    }

    private static Integer shortFormPartOf(TypeDefinition type) {
        if (type instanceof EnumerationType) {
            return ((EnumerationType) type).getShortFormPart();
        }
        if (type instanceof AttributeType) {
            return ((AttributeType) type).getShortFormPart();
        }
        if (type instanceof CompositeType) {
            return ((CompositeType) type).getShortFormPart();
        }
        return null;
    }
}
