/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the rule that decides how far the switch of an area factory reaches
 * before the rest of the type numbers are moved out to a second method.
 */
public class TestJavaElementFactory {

    /**
     * Builds the type numbers of an area the way the generator sees them: a
     * type and the list that holds it, which takes the same number negated.
     *
     * @param magnitudes The declared type numbers.
     * @return The type numbers with the numbers of their lists.
     */
    private static Collection<Integer> withLists(int... magnitudes) {
        List<Integer> numbers = new ArrayList<>();

        for (int magnitude : magnitudes) {
            numbers.add(magnitude);
            numbers.add(-magnitude);
        }

        return numbers;
    }

    private static int[] range(int first, int last) {
        int[] numbers = new int[last - first + 1];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = first + i;
        }

        return numbers;
    }

    /**
     * An area whose numbers run without a gap is answered by one switch, so the
     * band has to reach the last of them.
     */
    @Test
    public void bandCoversAnAreaThatHasNoGaps() {
        assertEquals(12, JavaElementFactory.widestJumpTableBand(withLists(range(1, 12))));
        assertEquals(45, JavaElementFactory.widestJumpTableBand(withLists(range(1, 45))));
    }

    /**
     * The numbers of the MAL run 1 to 19, then 101 to 105, then 1001 to 1010.
     * The first two groups are close enough to be held together, the last one
     * is not, so it is left for the second method.
     */
    @Test
    public void bandStopsBeforeAGapThatIsTooWideToPayFor() {
        List<Integer> mal = new ArrayList<>();
        mal.addAll(withLists(range(1, 19)));
        mal.addAll(withLists(range(101, 105)));
        mal.addAll(withLists(range(1001, 1010)));

        assertEquals(105, JavaElementFactory.widestJumpTableBand(mal));
    }

    /**
     * A group of numbers that lies past a gap is still taken in when it is
     * dense enough to pay for the gap in front of it. Stopping at the first
     * candidate that fails would leave it out.
     */
    @Test
    public void bandReachesPastAGapThatTheNumbersBehindItPayFor() {
        List<Integer> numbers = new ArrayList<>();
        numbers.addAll(withLists(1, 2, 3));
        numbers.addAll(withLists(range(20, 60)));

        assertEquals(60, JavaElementFactory.widestJumpTableBand(numbers));
    }

    /**
     * The band is counted out from 1, not placed on the longest run of numbers
     * that follow one another. An area that starts with a gap would otherwise
     * leave its first types out of the jump table.
     */
    @Test
    public void bandIsAnchoredAtOneAndNotOnTheLongestRun() {
        // The run 10 to 15 is the longest, but 1 and 6 come first
        List<Integer> numbers = new ArrayList<>();
        numbers.addAll(withLists(1, 6));
        numbers.addAll(withLists(range(10, 15)));

        int band = JavaElementFactory.widestJumpTableBand(numbers);
        assertEquals(15, band);
        assertTrue("Type number 1 has to be inside the band", band >= 1);
    }

    /**
     * A single type is answered by a lookup rather than by a jump table, so
     * there is no band and the types are left in one switch.
     */
    @Test
    public void aSingleTypeHasNoBand() {
        assertEquals(0, JavaElementFactory.widestJumpTableBand(withLists(1)));
    }

    /**
     * A number that lies on its own far from the rest cannot widen the band,
     * whatever else the area declares.
     */
    @Test
    public void aLoneNumberFarOutIsLeftOutOfTheBand() {
        List<Integer> numbers = new ArrayList<>();
        numbers.addAll(withLists(range(1, 20)));
        numbers.addAll(withLists(5000));

        assertEquals(20, JavaElementFactory.widestJumpTableBand(numbers));
    }

    /**
     * The band that is chosen has to be one the Java compiler really answers
     * with a jump table, and the one past it must not be.
     */
    @Test
    public void chosenBandIsTheWidestThatStillCompilesToAJumpTable() {
        List<Integer> numbers = new ArrayList<>();
        numbers.addAll(withLists(range(1, 19)));
        numbers.addAll(withLists(range(101, 105)));
        numbers.addAll(withLists(range(1001, 1010)));

        int band = JavaElementFactory.widestJumpTableBand(numbers);
        int inside = 0;

        for (Integer number : numbers) {
            if (Math.abs(number) <= band) {
                inside++;
            }
        }

        assertTrue("The chosen band is not a jump table",
                JavaElementFactory.compilesToJumpTable(inside, 2L * band + 1L));
        assertTrue("The whole area would still be a jump table",
                !JavaElementFactory.compilesToJumpTable(numbers.size(), 2L * 1010 + 1L));
    }

    /**
     * Nothing to switch over is answered with no band, rather than with an
     * empty one that the generator would then try to split.
     */
    @Test
    public void noTypesGiveNoBand() {
        assertEquals(0, JavaElementFactory.widestJumpTableBand(new ArrayList<>()));
    }

    /**
     * Numbers that lie far from zero leave the whole way across zero empty
     * between the types and their lists, so each side is switched on its own.
     */
    @Test
    public void numbersFarFromZeroAreSwitchedOneSideAtATime() {
        assertTrue(JavaElementFactory.shouldSplitOnSign(withLists(range(1001, 1010))));
    }

    /**
     * Numbers that already answer with a jump table gain nothing from being
     * taken apart, and would only pay for the test that reaches each side.
     */
    @Test
    public void numbersThatAreAlreadyAJumpTableAreLeftWhole() {
        assertFalse(JavaElementFactory.shouldSplitOnSign(withLists(range(1, 19))));
        assertFalse(JavaElementFactory.shouldSplitOnSign(withLists(range(1, 105))));
    }

    /**
     * A single type far from zero is reached faster by one lookup over both of
     * its numbers than by a test and a jump table holding one entry each.
     */
    @Test
    public void aSingleTypeFarFromZeroIsLeftWhole() {
        assertFalse(JavaElementFactory.shouldSplitOnSign(withLists(90)));
    }

    /**
     * There is nothing to take apart when the numbers all fall on one side of
     * zero.
     */
    @Test
    public void numbersOnOneSideOfZeroAreLeftWhole() {
        List<Integer> positives = new ArrayList<>();

        for (int number = 1001; number <= 1010; number++) {
            positives.add(number);
        }

        assertFalse(JavaElementFactory.shouldSplitOnSign(positives));
        assertFalse(JavaElementFactory.shouldSplitOnSign(new ArrayList<>()));
    }

    /**
     * Both sides have to be worth a jump table on their own. One side that
     * stays a binary search is not worth the test that reaches it.
     */
    @Test
    public void bothSidesHaveToBeWorthAJumpTable() {
        // The lists follow one another, the types themselves do not
        List<Integer> lopsided = new ArrayList<>();

        for (int number = 1001; number <= 1010; number++) {
            lopsided.add(-number);
        }

        lopsided.add(1001);
        lopsided.add(3000);
        lopsided.add(9000);

        assertFalse(JavaElementFactory.shouldSplitOnSign(lopsided));
    }
}
