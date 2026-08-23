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
package esa.mo.apigen.generators.xhtml;

import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.EnumerationType;
import esa.mo.apigen.model.types.TypeDefinition;
import esa.mo.apigen.model.types.TypeRef;
import java.util.ArrayList;
import java.util.List;

/**
 * A message or a type opened out into everything it is made of.
 * <p>
 * Each node is one field: its name, the type in it, and - where that type is a composite -
 * the fields of that composite on the row below. A node knows how wide it is, in columns of
 * whatever it eventually holds, and how deep, so the drawing can be laid out before any of
 * it is drawn.
 * <p>
 * <b>A composite may contain itself.</b> Mission Planning declares six that do: a Plan
 * names a precursor Plan and a target Plan, an ActivityInstance holds its children, an
 * EventDefinition holds the definitions under it. These are ordinary models - a plan really
 * does supersede a plan - and the generator this replaces expanded them until the stack ran
 * out, so the whole area produced an empty file. Here a type already open above is drawn
 * but not opened again: it names the type, and the name is a link to where that type is
 * described in full.
 */
final class TypeNode {

    /**
     * How wide a drawing is allowed to get, in fields.
     * <p>
     * Opening a composite replaces one field with all of its own, so a message of nested
     * composites grows by multiplication: Mission Planning's plan status report opens out
     * to 824 fields, thirty metres of drawing, in a page of twenty megabytes. Nothing is
     * read at that size. The widest drawing anywhere else in the specifications is 46
     * fields, so this leaves every one of those alone and stops only what has stopped
     * being a diagram.
     */
    private static final int MAX_COLUMNS = 120;

    private final String name;

    private final TypeRef type;

    private final boolean list;

    private final boolean optional;

    private final List<TypeNode> children = new ArrayList<TypeNode>();

    private final boolean composite;

    private final boolean enumeration;

    private final boolean typeIsAbstract;

    /**
     * True where this node names a type that is already open above it, and so is left
     * closed. Kept because it is the reason a composite has no children, which is
     * otherwise indistinguishable from a composite with no fields.
     */
    private boolean closedOnCycle;

    /**
     * The types open from the root down to this node, which are the ones it must not open
     * again.
     */
    private List<TypeRef> open;

    /**
     * Hands out the number of each list in a drawing, so that "N1" and "N2" say which
     * count each "Repeated" refers to. Held by the root and asked for from anywhere below.
     */
    private int nextListIndex = 1;

    /**
     * The Integer of the MAL generation this specification is written against, which is
     * what the count of a list is. Held by the root, since it is the same throughout.
     */
    private TypeRef counter;

    private TypeNode root;

    private TypeNode(String name, TypeRef type, boolean list, boolean optional,
            MOModel model) {
        this.name = name;
        this.type = type;
        this.list = list;
        this.optional = optional;
        this.root = this;

        TypeDefinition definition = type == null ? null : model.resolve(type);
        this.composite = definition instanceof CompositeType;
        this.enumeration = definition instanceof EnumerationType;
        this.typeIsAbstract = definition != null && definition.isAbstract();
    }

    /**
     * Opens out a message.
     *
     * @param fields The fields of the message.
     * @param model Every loaded specification.
     * @param counter The Integer that counts a list, of the MAL generation in use.
     * @return the root of the tree, which draws the fields and nothing of its own.
     */
    static TypeNode ofMessage(List<Field> fields, MOModel model, TypeRef counter) {
        TypeNode root = new TypeNode(null, null, false, false, model);
        root.counter = counter;
        root.open = new ArrayList<TypeRef>();
        for (Field field : fields) {
            root.add(field.getName(), field.getType(), field.isCanBeNull(), model);
        }

        // A level at a time, so that what the drawing shows is the first so-many levels of
        // everything rather than the whole of whatever happens to be drawn first.
        List<TypeNode> frontier = compositesIn(root.children);
        while (!frontier.isEmpty()) {
            for (TypeNode node : frontier) {
                node.openOut(model);
            }
            if (root.width() > MAX_COLUMNS) {
                // This level does not fit. Close it again rather than show part of it, and
                // leave those types named and linked instead of opened.
                for (TypeNode node : frontier) {
                    node.children.clear();
                }
                break;
            }
            List<TypeNode> next = new ArrayList<TypeNode>();
            for (TypeNode node : frontier) {
                next.addAll(compositesIn(node.children));
            }
            frontier = next;
        }
        return root;
    }

    /**
     * @return those of these nodes that hold a composite still to be opened.
     */
    private static List<TypeNode> compositesIn(List<TypeNode> nodes) {
        List<TypeNode> found = new ArrayList<TypeNode>();
        for (TypeNode node : nodes) {
            if (node.composite && !node.closedOnCycle) {
                found.add(node);
            }
        }
        return found;
    }

    /**
     * @return true if this is the root, which stands for the message rather than for any
     * field of it.
     */
    private boolean isRoot() {
        return type == null;
    }

    private void add(String fieldName, TypeRef fieldType, boolean canBeNull, MOModel model) {
        TypeRef bare = fieldType.unwrapped();
        TypeNode child = new TypeNode(nameOr(fieldName), bare, bare.isList(), canBeNull, model);
        child.root = root;
        child.open = new ArrayList<TypeRef>(open);
        if (!isRoot()) {
            child.open.add(keyOf(type));
        }
        child.closedOnCycle = child.composite && child.open.contains(keyOf(bare));
        children.add(child);
    }

    /**
     * Adds the fields of the type in this node, one level down.
     */
    private void openOut(MOModel model) {
        CompositeType definition = (CompositeType) model.resolve(type);
        if (definition == null) {
            return;
        }
        // What a composite extends is drawn as a field of its own, opened out in turn, so
        // the fields it inherits arrive through it rather than being repeated here.
        TypeRef parent = definition.getSuperType();
        if (parent != null && !"Composite".equals(parent.getName())) {
            add(parent.getName(), parent, false, model);
        }
        for (Field field : definition.getFields()) {
            add(field.getName(), field.getType(), field.isCanBeNull(), model);
        }
    }

    /**
     * @return how many columns this node takes up, which is however many its contents need.
     */
    int width() {
        if (children.isEmpty()) {
            return isRoot() ? 0 : 1 + (list ? 1 : 0);
        }
        int width = list ? 1 : 0;
        for (TypeNode child : children) {
            width += child.width();
        }
        return width;
    }

    /**
     * @param above How many rows are above this one.
     * @return the row the deepest thing under this node sits on.
     */
    int depth(int above) {
        int mine = isRoot() ? above : above + 1;
        int deepest = mine;
        for (TypeNode child : children) {
            int reach = child.depth(mine);
            if (reach > deepest) {
                deepest = reach;
            }
        }
        return deepest;
    }

    /**
     * @return how many rules are drawn under this node and everything in it, so that the
     * ones belonging to a node higher up clear them.
     */
    int spanDepth() {
        int mine = 0;
        if (!isRoot()) {
            mine = (list ? 1 : 0) + (optional ? 1 : 0);
        }
        int deepest = 0;
        for (TypeNode child : children) {
            int reach = child.spanDepth();
            if (reach > deepest) {
                deepest = reach;
            }
        }
        return deepest + mine;
    }

    /**
     * Draws this node and everything in it.
     *
     * @param diagram The drawing to place it on.
     * @param area The area whose page is being written, so that links within it are short.
     * @param model Every loaded specification.
     * @param x The column to start at.
     * @param y The row to start at.
     * @param remaining How many rows are left below this one, which is how tall the box of
     * a node with nothing under it has to be.
     */
    void draw(SvgDiagram diagram, Area area, MOModel model, int x, int y, int remaining) {
        if (!isRoot()) {
            drawSelf(diagram, area, model, x, y, remaining);
        }
        int at = x + (isRoot() || !list ? 0 : 1);
        for (TypeNode child : children) {
            child.draw(diagram, area, model, at, isRoot() ? y : y + 1,
                    isRoot() ? remaining : remaining - 1);
            at += child.width();
        }
    }

    private void drawSelf(SvgDiagram diagram, Area area, MOModel model,
            int x, int y, int remaining) {
        // What is left below this node is what the box has to fill: a field with nothing in
        // it reaches the bottom of the drawing, one with a composite in it stops above it.
        int height = remaining - depth(0) + 1;
        String href = XhtmlLink.hrefTo(model, area, type);
        String typeName = type.getName();

        if (list) {
            int index = root.nextListIndex++;
            diagram.nestedField("N" + index, "Integer",
                    XhtmlLink.hrefTo(model, area, root.counter),
                    x, y, 1, remaining, false, false, false);
            diagram.nestedField(name, typeName, href, x + 1, y, width() - 1, height,
                    typeIsAbstract, enumeration, composite);

            int rules = spanDepth();
            if (optional) {
                diagram.nestedSpan(x, y + remaining, width(), rules, "Nullable");
                rules--;
            }
            diagram.nestedSpan(x + 1, y + remaining, width() - 1, rules,
                    "Repeated N" + index + " times");
        } else {
            diagram.nestedField(name, typeName, href, x, y, width(), height,
                    typeIsAbstract, enumeration, composite);
            if (optional) {
                diagram.nestedSpan(x, y + remaining, width(), spanDepth(), "Nullable");
            }
        }
    }

    /**
     * A message field need not be named - the specifications leave the name off where the
     * body is a single value - and a box has to say something.
     */
    private static String nameOr(String fieldName) {
        return fieldName == null || fieldName.isEmpty() ? "Part" : fieldName;
    }

    /**
     * What makes two references the same type for the purpose of noticing a cycle: the
     * type itself, whether or not this mention of it is a list.
     */
    private static TypeRef keyOf(TypeRef type) {
        return new TypeRef(type.getArea(), type.getAreaVersion(), type.getService(),
                type.getName(), false, false);
    }

    /**
     * @return true where the type in this node was left closed because it is already open
     * higher up the drawing.
     */
    boolean isClosedOnCycle() {
        return closedOnCycle;
    }
}
