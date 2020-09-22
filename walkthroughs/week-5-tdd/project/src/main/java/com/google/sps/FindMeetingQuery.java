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

import static com.google.sps.FindMeetingUtils.addToRanges;
import static com.google.sps.TimeRange.END_OF_DAY;
import static com.google.sps.TimeRange.START_OF_DAY;
import static com.google.sps.TimeRange.fromStartEnd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class FindMeetingQuery {

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    List<TimeRange> busyRanges = new ArrayList<>();
    List<TimeRange> optionalBusyRanges = new ArrayList<>();

    for (Event event : events) {
      if (!Collections.disjoint(event.getAttendees(), request.getAttendees())) {
        addToRanges(busyRanges, event.getWhen());
        addToRanges(optionalBusyRanges, event.getWhen());
      } else if(!Collections.disjoint(event.getAttendees(), request.getOptionalAttendees())) {
        addToRanges(optionalBusyRanges, event.getWhen());
      }
    }

    List<TimeRange> optionalFreeSlots = extractFreeSlots(optionalBusyRanges).stream()
        .filter(timeRange -> timeRange.duration() >= request.getDuration())
        .collect(Collectors.toList());

    if (optionalFreeSlots.isEmpty()) {
      return extractFreeSlots(busyRanges).stream()
          .filter(timeRange -> timeRange.duration() >= request.getDuration())
          .collect(Collectors.toList());
    } else {
      return optionalFreeSlots;
    }
  }

  private List<TimeRange> extractFreeSlots(List<TimeRange> busyRanges) {
    List<TimeRange> freeRanges = new ArrayList<>();

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
}
