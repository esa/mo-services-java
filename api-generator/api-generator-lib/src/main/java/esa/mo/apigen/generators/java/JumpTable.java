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
package esa.mo.apigen.generators.java;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Decides how the type numbers of an element factory are laid out, so that a lookup
 * compiles to a jump rather than to a binary search.
 * <p>
 * Carried over from the generator this replaces, rule for rule. A type and the list that
 * holds it share a number save for the sign, so numbers far from zero leave the whole
 * distance across zero empty between them, and a switch spanning that emptiness falls back
 * to a binary search. Two things follow: numbers that lie past the widest table worth
 * building are answered by a second method, and where each side of zero would answer with
 * a table of its own but the two together would not, the sides are switched apart.
 */
public final class JumpTable {

    private JumpTable() {
    }

    /**
     * Returns true when a switch over this many numbers, spanning this far, is worth
     * compiling to a jump table rather than to a binary search.
     *
     * @param count How many numbers the switch answers for.
     * @param span The distance from the lowest number to the highest, inclusive.
     * @return true if a jump table pays.
     */
    public static boolean compilesToJumpTable(int count, long span) {
        return (4 + span) + 3L * 3L <= (3 + 2L * count) + 3L * count;
    }

    /**
     * Returns the widest distance from zero within which the numbers still pay for a jump
     * table. A type and its list share a number save for the sign, so the band widens by
     * the same step on both sides at once.
     *
     * @param numbers The type numbers.
     * @return the widest band, or zero if no table pays.
     */
    public static int widestJumpTableBand(Collection<Integer> numbers) {
        SortedSet<Integer> magnitudes = new TreeSet<Integer>();
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
     * Returns true when the numbers are better switched on one side of zero at a time than
     * all together: neither side is worth it unless both sides answer with a table of
     * their own and the two together would not.
     *
     * @param numbers The type numbers of the switch.
     * @return true if each side of zero should be switched on its own.
     */
    public static boolean shouldSplitOnSign(Collection<Integer> numbers) {
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

        return !compilesToJumpTable(numbers.size(), (long) highest - lowest + 1L)
                && compilesToJumpTable(positives, (long) highestPositive - lowestPositive + 1L)
                && compilesToJumpTable(negatives, (long) highestNegative - lowestNegative + 1L);
    }

    /**
     * The types of one switch, split into the ones a jump table can hold and the ones
     * whose numbers lie too far out for it.
     */
    public static final class Split {

        private final Map<Integer, String> inBand = new TreeMap<Integer, String>();
        private final Map<Integer, String> outOfBand = new TreeMap<Integer, String>();

        /**
         * @return true if the types have to be reached by two switches.
         */
        public boolean isSplit() {
            return !inBand.isEmpty() && !outOfBand.isEmpty();
        }

        public Map<Integer, String> getInBand() {
            return inBand;
        }

        public Map<Integer, String> getOutOfBand() {
            return outOfBand;
        }

        /**
         * @return every type, whether or not the jump table can hold it.
         */
        public Map<Integer, String> all() {
            Map<Integer, String> every = new TreeMap<Integer, String>(inBand);
            every.putAll(outOfBand);
            return every;
        }
    }

    /**
     * Splits the types into the ones the widest jump table can hold and the ones past it.
     *
     * @param types The types, by number, in declaration order.
     * @return the split.
     */
    public static Split splitTypes(Map<Integer, String> types) {
        Map<Integer, String> byNumber = new TreeMap<Integer, String>(types);
        int band = widestJumpTableBand(byNumber.keySet());
        Split split = new Split();
        for (Map.Entry<Integer, String> entry : byNumber.entrySet()) {
            boolean fits = Math.abs(entry.getKey()) <= band;
            (fits ? split.inBand : split.outOfBand).put(entry.getKey(), entry.getValue());
        }
        return split;
    }
}
