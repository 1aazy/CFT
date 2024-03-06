package com.max.api.v1.services;

import com.max.api.v1.exceptions.InvalidRangeException;
import com.max.api.v1.models.DigitRange;
import com.max.api.v1.repositories.DigitRangeRepository;
import com.max.api.v1.services.DigitRangeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DigitRangeServiceTest {

    @Mock
    private DigitRangeRepository digitRangeRepository;

    @InjectMocks
    private DigitRangeService digitRangeService;

    @Test
    void mergeRanges_OverlappingRanges_CorrectMerge() {

        List<DigitRange> inputList = List.of(
                new DigitRange(1, 4),
                new DigitRange(3, 6),
                new DigitRange(6, 7),
                new DigitRange(8, 10),
                new DigitRange(11, 14),
                new DigitRange(13, 15)
        );

        List<DigitRange> expectedList = List.of(
                new DigitRange(1, 7),
                new DigitRange(8, 10),
                new DigitRange(11, 15)
        );

        assertEquals(expectedList, digitRangeService.mergeRanges(inputList));
    }

    @Test
    void mergeRanges_SingleRange_NoChange() {

        List<DigitRange> inputList = List.of(
                new DigitRange(1, 4)
        );

        List<DigitRange> expectedList = List.of(
                new DigitRange(1, 4)
        );

        assertEquals(expectedList, digitRangeService.mergeRanges(inputList));
    }

    @Test
    void mergeRanges_RangeStartGreaterThanEnd_ThrowsInvalidRangeException() {

        List<DigitRange> inputList = List.of(
                new DigitRange(4, 1),
                new DigitRange(3, 6),
                new DigitRange(6, 7)
        );

        assertThrows(InvalidRangeException.class, () -> digitRangeService.mergeRanges(inputList));
    }

    @Test
    void mergeRanges_SubRangeWithinLargerRange_CorrectMerge() {

        List<DigitRange> inputList = List.of(
                new DigitRange(1, 10),
                new DigitRange(3, 6)
        );

        List<DigitRange> expectedList = List.of(
                new DigitRange(1, 10)
        );

        assertEquals(expectedList, digitRangeService.mergeRanges(inputList));
    }

    @Test
    void mergeRanges_RangeStartEqualsToEnd_CorrectMerge() {

        List<DigitRange> inputList = List.of(
                new DigitRange(1, 10),
                new DigitRange(10, 10)
        );

        List<DigitRange> expectedList = List.of(
                new DigitRange(1, 10)
        );

        assertEquals(expectedList, digitRangeService.mergeRanges(inputList));
    }

    @Test
    void mergeRanges_DuplicateRanges_MergedIntoSingleRange() {

        List<DigitRange> inputList = List.of(
                new DigitRange(1, 10),
                new DigitRange(1, 10)
        );

        List<DigitRange> expectedList = List.of(
                new DigitRange(1, 10)
        );

        assertEquals(expectedList, digitRangeService.mergeRanges(inputList));
    }
}