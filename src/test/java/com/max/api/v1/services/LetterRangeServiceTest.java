package com.max.api.v1.services;

import com.max.api.v1.exceptions.InvalidRangeException;
import com.max.api.v1.models.LetterRange;
import com.max.api.v1.repositories.LetterRangeRepository;
import com.max.api.v1.services.LetterRangeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LetterRangeServiceTest {

    @Mock
    private LetterRangeRepository letterRangeRepository;

    @InjectMocks
    private LetterRangeService letterRangeService;

    @Test
    void mergeRanges_OverlappingRanges_CorrectMerge() {

        List<LetterRange> inputList = List.of(
                new LetterRange("a", "f"),
                new LetterRange("d", "j"),
                new LetterRange("r", "z")
        );

        List<LetterRange> expectedList = List.of(
                new LetterRange("a", "j"),
                new LetterRange("r", "z")
        );

        assertEquals(expectedList, letterRangeService.mergeRanges(inputList));
    }

    @Test
    void mergeRanges_SingleRange_NoChange() {

        List<LetterRange> inputList = List.of(
                new LetterRange("a", "f")
        );

        List<LetterRange> expectedList = List.of(
                new LetterRange("a", "f")
        );

        assertEquals(expectedList, letterRangeService.mergeRanges(inputList));
    }

    @Test
    void mergeRanges_RangeStartGreaterThanEnd_ThrowsInvalidRangeException() {

        List<LetterRange> inputList = List.of(
                new LetterRange("c", "a"),
                new LetterRange("g", "s"),
                new LetterRange("q", "z")
        );

        assertThrows(InvalidRangeException.class, () -> letterRangeService.mergeRanges(inputList));
    }

    @Test
    void mergeRanges_SubRangeWithinLargerRange_CorrectMerge() {

        List<LetterRange> inputList = List.of(
                new LetterRange("a", "h"),
                new LetterRange("c", "f")
        );

        List<LetterRange> expectedList = List.of(
                new LetterRange("a", "h")
        );

        assertEquals(expectedList, letterRangeService.mergeRanges(inputList));
    }

    @Test
    void mergeRanges_RangeStartEqualsToEnd_CorrectMerge() {

        List<LetterRange> inputList = List.of(
                new LetterRange("a", "k"),
                new LetterRange("k", "k")
        );

        List<LetterRange> expectedList = List.of(
                new LetterRange("a", "k")
        );

        assertEquals(expectedList, letterRangeService.mergeRanges(inputList));
    }

    @Test
    void mergeRanges_DuplicateRanges_MergedIntoSingleRange() {

        List<LetterRange> inputList = List.of(
                new LetterRange("a", "c"),
                new LetterRange("a", "c")
        );

        List<LetterRange> expectedList = List.of(
                new LetterRange("a", "c")
        );

        assertEquals(expectedList, letterRangeService.mergeRanges(inputList));
    }
}