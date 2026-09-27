package org.ccsds.moims.mo.mal.helpertools.helpers;

import static org.junit.Assert.assertEquals;
import java.sql.Timestamp;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Time;
import org.junit.Test;

public class TestHelperTime {

    private final long MIO = 1000000L;

    /* Tests for toReadableString */
    @Test
    public void testTime2ReadableString1() {
        FineTime stamp = new FineTime(0L);
        String res = stamp.toReadableString();
        assertEquals("Wrong result for epoch begin", "1970-01-01 00:00:00.000", res);
    }

    @Test
    public void testTime2ReadableString2() {
        FineTime stamp = new FineTime(1557314923000L * MIO);
        String res = stamp.toReadableString();
        assertEquals("Wrong result for valid fine time", "2019-05-08 11:28:43.000", res);
    }

    @Test
    public void testTime2ReadableString5() {
        String res = new Time(0L).toReadableString();
        assertEquals("Wrong result for epoch begin", "1970-01-01 00:00:00.000", res);
    }

    @Test
    public void testTime2ReadableString6() {
        String res = new Time(1557317072000L).toReadableString();
        assertEquals("Wrong result for valid time", "2019-05-08 12:04:32.000", res);
    }

    /* Tests for getFractionalPart */
    @Test
    public void testGetFractionalPart1() {
        assertEquals(0, HelperTime.getFractionalPart(0L));
    }

    @Test
    public void testGetFractionalPart2() {
        assertEquals(123456789, HelperTime.getFractionalPart(42123456789L));
    }

    @Test
    public void testGetFractionalPart3() {
        assertEquals(854775807, HelperTime.getFractionalPart(Long.MAX_VALUE));
    }

    /* Tests for getNanosecondsFromSQLTimestamp */
    @Test(expected = IllegalArgumentException.class)
    public void testGetNanosecondsFromSQLTimestamp1() {
        HelperTime.getNanosecondsFromSQLTimestamp(null);
    }

    @Test
    public void testGetNanosecondsFromSQLTimestamp2() {
        Timestamp t = new Timestamp(0);
        assertEquals(0, HelperTime.getNanosecondsFromSQLTimestamp(t));
    }

    @Test
    public void testGetNanosecondsFromSQLTimestamp3() {
        Timestamp t = new Timestamp(123456789L);
        assertEquals(123456789000000L, HelperTime.getNanosecondsFromSQLTimestamp(t));
    }

    @Test
    public void testGetNanosecondsFromSQLTimestamp4() {
        Timestamp t = new Timestamp(123456789L);
        t.setNanos(789012345);
        assertEquals(123456789012345L, HelperTime.getNanosecondsFromSQLTimestamp(t));
    }

    @Test
    public void testGetNanosecondsFromSQLTimestamp5() {
        Timestamp t = new Timestamp(Long.MAX_VALUE);
        t.setNanos(807000000 + 999999);
        assertEquals(Long.MAX_VALUE * 1000000L + 999999L, HelperTime.getNanosecondsFromSQLTimestamp(t));
    }
}
