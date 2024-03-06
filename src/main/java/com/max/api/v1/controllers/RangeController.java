package com.max.api.v1.controllers;

import com.max.api.v1.exceptions.RangeExceptionResponse;
import com.max.api.v1.exceptions.RangeTypeMismatchException;
import com.max.api.v1.models.DigitRange;
import com.max.api.v1.models.LetterRange;
import com.max.api.v1.services.DigitRangeService;
import com.max.api.v1.services.LetterRangeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/intervals")
@AllArgsConstructor
public class RangeController {

    private final DigitRangeService digitRangeService;
    private final LetterRangeService letterRangeService;

    @GetMapping("/min")
    public ResponseEntity<List<?>> getMinimumRange(@RequestParam("kind") String type) {
        if (type.equals("digits")) {

            DigitRange range = digitRangeService.getMinimumDigitRange();
            List<Integer> response = List.of(range.getStart(), range.getEnd());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } else if (type.equals("letters")) {

            LetterRange range = letterRangeService.getMinimumLetterRange();
            List<String> response = List.of(range.getStart(), range.getEnd());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            throw new RangeTypeMismatchException("The provided kind type is not supported. Please use a valid kind.");
        }
    }

    @PostMapping("/merge")
    public ResponseEntity<?> mergeRanges(@RequestParam("kind") String kind,
                                            @RequestBody List<List<Object>> rangeList) {

        if (kind.equals("digits")) {

            List<DigitRange> digitRanges = rangeList.stream()
                    .map(innerList -> innerList.stream()
                            .filter(o -> o instanceof Integer)
                            .mapToInt(o -> (int) o)
                            .toArray())
                    .filter(arr -> arr.length > 0)
                    .map(arr -> new DigitRange(arr[0], arr[1]))
                    .toList();

            if (digitRanges.isEmpty()) {
                throw new RangeTypeMismatchException("Range type mismatch - type: 'digits', but receive range of letters.");
            }

            List<DigitRange> mergedRanges = digitRangeService.mergeRanges(digitRanges);

            digitRangeService.saveDigitRange(mergedRanges);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        if (kind.equals("letters")) {

            List<LetterRange> letterRanges = rangeList.stream()
                    .map(innerList -> innerList.stream()
                            .filter(o -> o instanceof String)
                            .map(Object::toString)
                            .toArray(String[]::new))
                    .filter(arr -> arr.length > 0)
                    .map(arr -> new LetterRange(arr[0], arr[1]))
                    .toList();

                if (letterRanges.isEmpty()) {
                throw new RangeTypeMismatchException("Range type mismatch - type: 'letters', but receive range of digits.");
            }

            List<LetterRange> mergedRanges = letterRangeService.mergeRanges(letterRanges);

            letterRangeService.saveLetterRange(mergedRanges);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(new RangeExceptionResponse("The provided kind type is not supported. Please use a valid kind."), HttpStatus.NOT_FOUND);
    }
}