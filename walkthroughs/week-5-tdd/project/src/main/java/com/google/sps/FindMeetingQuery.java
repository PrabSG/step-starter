// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import static com.google.sps.TimeRange.END_OF_DAY;
import static com.google.sps.TimeRange.START_OF_DAY;
import static com.google.sps.TimeRange.fromStartDuration;
import static com.google.sps.TimeRange.fromStartEnd;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public final class FindMeetingQuery {

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    List<TimeRange> busyRanges = new LinkedList<>();

    for (Event event : events) {
      if (!Collections.disjoint(event.getAttendees(), request.getAttendees())) {
        addToRanges(busyRanges, event.getWhen());
      }
    }

    return extractFreeSlots(busyRanges).stream()
        .filter(timeRange -> timeRange.duration() >= request.getDuration())
        .collect(Collectors.toList());

  }

  private List<TimeRange> extractFreeSlots(List<TimeRange> busyRanges) {
    List<TimeRange> freeRanges = new LinkedList<>();

    int startTime = START_OF_DAY;

    for (TimeRange busyRange : busyRanges) {

      TimeRange freeRange = fromStartEnd(startTime, busyRange.start(), false);

      if (freeRange.duration() > 0) {
        freeRanges.add(freeRange);
      }

      startTime = busyRange.end();
    }

    freeRanges.add(fromStartEnd(startTime, END_OF_DAY, true));

    return freeRanges;
  }

  private void addToRanges(List<TimeRange> ranges, TimeRange newRange) {
    if (ranges.isEmpty()) {
      ranges.add(newRange);
      return;
    }

    int nextIndex = 1;

    while (nextIndex < ranges.size()) {
      if (ranges.get(nextIndex).start() >= newRange.start()) {
        break;
      }

      nextIndex++;
    }

    ranges.add(nextIndex, newRange);

    int currIndex = nextIndex - 1;

    if (ranges.get(currIndex).overlaps(newRange)) {
      mergeRanges(ranges, currIndex);
    } else {
      mergeRanges(ranges, nextIndex);
    }
  }

  private void mergeRanges(List<TimeRange> ranges, int startIndex) {
    if (startIndex >= ranges.size() - 1) {
      return;
    }

    TimeRange currRange = ranges.get(startIndex);
    TimeRange nextRange = ranges.get(startIndex + 1);

    if (currRange.overlaps(nextRange)) {
      TimeRange newRange =
          TimeRange
              .fromStartEnd(currRange.start(), Math.max(currRange.end(), nextRange.end()), false);

      ranges.remove(startIndex + 1);
      ranges.remove(startIndex);
      ranges.add(startIndex, newRange);

      mergeRanges(ranges, startIndex);
    }
  }
}
