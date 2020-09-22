package com.google.sps;

import java.util.List;

public class FindMeetingUtils {
  /**
   * Inserts a TimeRange into an object maintaining a sorted ordering by start time, and coalescing
   * any ranges that overlap with the newly inserted range.
   */
  public static void addToRanges(List<TimeRange> ranges, TimeRange newRange) {
    if (ranges.isEmpty()) {
      ranges.add(newRange);
      return;
    }

    int sortedInsertIndex = 0;

    while (sortedInsertIndex < ranges.size()) {
      if (ranges.get(sortedInsertIndex).start() >= newRange.start()) {
        break;
      }

      sortedInsertIndex++;
    }

    ranges.add(sortedInsertIndex, newRange);

    int prevIndex = sortedInsertIndex - 1;

    if (sortedInsertIndex != 0 && ranges.get(prevIndex).overlaps(newRange)) {
      mergeRanges(ranges, prevIndex);
    } else {
      mergeRanges(ranges, sortedInsertIndex);
    }
  }

  /**
   * Merges the TimeRange objects within a list that contains overlapping or adjacent ranges,
   * starting at the specified index and ending when the current range is no longer overlapping the
   * range after it.
   *
   * @param ranges - List of ranges to merge.
   * @param startIndex - Position at which to start merging ranges from.
   */
  public static void mergeRanges(List<TimeRange> ranges, int startIndex) {
    if (startIndex >= ranges.size() - 1) {
      return;
    }

    TimeRange currRange = ranges.get(startIndex);
    TimeRange nextRange = ranges.get(startIndex + 1);

    while ((currRange.overlaps(nextRange) || nextRange.contains(currRange.end())) &&
        startIndex < ranges.size() - 1) {
      if (currRange.contains(nextRange)) {
        ranges.remove(startIndex + 1);

      } else {
        TimeRange newRange =
            TimeRange
                .fromStartEnd(currRange.start(), Math.max(currRange.end(), nextRange.end()), false);

        ranges.remove(startIndex + 1);
        ranges.set(startIndex, newRange);

        currRange = newRange;
      }

      if (startIndex + 1 < ranges.size()) {
        nextRange = ranges.get(startIndex + 1);
      }
    }
  }
}
