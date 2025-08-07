/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Testbed - MPD
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
package org.ccsds.mo.mpd.testbed;

import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * The Constants class holds the constants that are used in this testbed.
 */
public class Constants {

    public final static Time APID100_CREATION_DATE = new Time("2010-01-01T09:13:51.352Z");
    public final static Time APID100_TIME_START = new Time("2009-12-31T11:41:53.437Z");
    public final static Time APID100_TIME_END = new Time("2010-01-01T09:07:51.352Z");

    public final static Time APID200_CREATION_DATE = new Time("2020-01-01T08:24:26.846Z");
    public final static Time APID200_TIME_START = new Time("2019-12-31T10:09:17.854Z");
    public final static Time APID200_TIME_END = new Time("2020-01-01T08:14:53.113Z");

    public final static Time IMAGE_DATA_1_CREATION_DATE = new Time("2022-01-22T20:19:06.728Z");
    public final static Time IMAGE_DATA_1__TIME_START = new Time("2022-01-22T18:14:01.352Z");
    public final static Time IMAGE_DATA_1__TIME_END = new Time("2022-01-22T20:18:10.539Z");

    public final static Time IMAGE_DATA_2_CREATION_DATE = new Time("2018-02-04T07:07:04.145Z");
    public final static Time IMAGE_DATA_2__TIME_START = new Time("2018-02-04T07:03:15.532Z");
    public final static Time IMAGE_DATA_2__TIME_END = new Time("2018-02-04T07:03:15.532Z");

    public final static Time IMAGE_DATA_3_CREATION_DATE = new Time("2014-05-05T12:11:53.235Z");
    public final static Time IMAGE_DATA_3__TIME_START = new Time("2014-05-05T08:14:35.642Z");
    public final static Time IMAGE_DATA_3__TIME_END = new Time("2014-05-05T09:10:25.835Z");

    public final static Union IMAGE_DATA_1_LAT = new Union(34.1949742); // Earth
    public final static Union IMAGE_DATA_1_LON = new Union(-118.1835993); // Earth

    public final static Union IMAGE_DATA_2_LAT = new Union(42.0929981); // Earth
    public final static Union IMAGE_DATA_2_LON = new Union(-72.6084406); // Earth

    public final static Union IMAGE_DATA_3_LAT = new Union(20); // Mars
    public final static Union IMAGE_DATA_3_LON = new Union(-130); // Mars

    public final static TimeWindow IMAGES_CONTENT_TIME_WINDOW = new TimeWindow(
            new Time("2020-01-22T10:10:06.728Z"),
            new Time("2023-02-22T10:10:06.728Z"));

}
