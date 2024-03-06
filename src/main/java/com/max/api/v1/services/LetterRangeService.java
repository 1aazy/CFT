package com.max.api.v1.services;

import com.max.api.v1.models.LetterRange;
import com.max.api.v1.repositories.LetterRangeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class LetterRangeService extends AbstractRangeService<String> {

    private final LetterRangeRepository letterRangeRepository;

    public void saveLetterRange(List<LetterRange> ranges) {
        letterRangeRepository.saveAll(ranges.stream().toList());
    }

    public LetterRange getMinimumLetterRange() {
        return letterRangeRepository.findMinimumLetterRange();
    }
}
