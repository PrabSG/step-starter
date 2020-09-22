package com.google.sps;

import static com.google.sps.FindMeetingUtils.mergeRanges;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FindMeetingUtilsTest {

  private static final int TIME_0800AM = TimeRange.getTimeInMinutes(8, 0);
  private static final int TIME_0830AM = TimeRange.getTimeInMinutes(8, 30);
  private static final int TIME_0900AM = TimeRange.getTimeInMinutes(9, 0);
  private static final int TIME_0930AM = TimeRange.getTimeInMinutes(9, 30);
  private static final int TIME_1000AM = TimeRange.getTimeInMinutes(10, 0);
  private static final int TIME_1100AM = TimeRange.getTimeInMinutes(11, 00);

  private static final int DURATION_15_MINUTES = 15;
  private static final int DURATION_30_MINUTES = 30;
  private static final int DURATION_60_MINUTES = 60;
  private static final int DURATION_90_MINUTES = 90;
  private static final int DURATION_1_HOUR = 60;
  private static final int DURATION_2_HOUR = 120;

  @Test
  public void noOverlapDoesNotMergeRanges() {
    // Attempt to merge two time ranges which are completely disjoint.
    // Events   : |--1--|    |--2--|
    // Expected : |--1--|    |--2--|

    TimeRange range1 = TimeRange.fromStartEnd(TIME_0800AM, TIME_0900AM, false);
    TimeRange range2 = TimeRange.fromStartEnd(TIME_1000AM, TIME_1100AM, false);

    List<TimeRange> ranges = new ArrayList<>(Arrays.asList(range1, range2));

    mergeRanges(ranges, 0);

    assertThat(ranges, contains(range1, range2));
  }

  @Test
  public void adjacentRangesDoMergeRanges() {
    // Merge two time ranges which are directly adjacent.
    // Events   : |--1--|--2--|
    // Expected : |-----A-----|

    TimeRange range1 = TimeRange.fromStartDuration(TIME_0900AM, DURATION_1_HOUR);
    TimeRange range2 = TimeRange.fromStartDuration(TIME_1000AM, DURATION_1_HOUR);

    List<TimeRange> ranges = new ArrayList<>(Arrays.asList(range1, range2));

    mergeRanges(ranges, 0);

    assertThat(ranges, contains(TimeRange.fromStartDuration(TIME_0900AM, DURATION_2_HOUR)));
  }

  @Test
  public void overlappingRangesAreMerged() {
    // Attempt to merge three time ranges which overlap
    // Events   : |--1--|    |--3--|
    //               |----2----|
    // Expected : |-------A--------|

    TimeRange range1 = TimeRange.fromStartEnd(TIME_0800AM, TIME_0900AM, false);
    TimeRange range2 = TimeRange.fromStartDuration(TIME_0830AM, DURATION_2_HOUR);
    TimeRange range3 = TimeRange.fromStartEnd(TIME_1000AM, TIME_1100AM, false);

    List<TimeRange> ranges = new ArrayList<>(Arrays.asList(range1, range2, range3));

    mergeRanges(ranges, 0);

    assertThat(ranges, contains(TimeRange.fromStartEnd(TIME_0800AM, TIME_1100AM, false)));
  }

  @Test
  public void rangesContainedWithinAnotherAreRemoved() {
    // Attempt to merge three time ranges which overlap
    // Events   : |----1----|  |--3--|
    //               |-2-|
    // Expected : |----1----|  |--3--|

    TimeRange range1 = TimeRange.fromStartDuration(TIME_0800AM, DURATION_1_HOUR);
    TimeRange range2 = TimeRange.fromStartDuration(TIME_0830AM, DURATION_15_MINUTES);
    TimeRange range3 = TimeRange.fromStartDuration(TIME_1000AM, DURATION_1_HOUR);

    List<TimeRange> ranges = new ArrayList<>(Arrays.asList(range1, range2, range3));

    mergeRanges(ranges, 0);

    assertThat(ranges, contains(range1, range3));
  }
}
