package com.max.api.v1.services;

import com.max.api.v1.exceptions.InvalidRangeException;
import com.max.api.v1.models.AbstractRange;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRangeService<T> {

    public <T extends Comparable<T>, S extends AbstractRange<T>> List<S> mergeRanges(List<S> ranges) {

        List<S> result = new ArrayList<>();
        result.add(ranges.get(0));

        if (ranges.size() == 1) return result;

        validRange(ranges);

        S prevRange = result.get(0);

        for (int i = 1; i < ranges.size(); i++) {

            S currRange = ranges.get(i);

            if (0 <= prevRange.getEnd().compareTo(currRange.getStart())) {

                if (0 <= prevRange.getEnd().compareTo(currRange.getEnd())) {
                    continue;
                }

                prevRange.setEnd(currRange.getEnd());

            } else {
                result.add(currRange);
                prevRange = currRange;
            }
        }
        return result;
    }

    private <T extends Comparable<T>, S extends AbstractRange<T>> void validRange(List<S> ranges) {
        for (S range : ranges) {
            if (0 < range.getStart().compareTo(range.getEnd())) {
                throw new InvalidRangeException("The start of the range can't be greater than the end.");
            }
        }
    }
}
