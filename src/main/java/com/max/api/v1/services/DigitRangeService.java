package com.max.api.v1.services;

import com.max.api.v1.repositories.DigitRangeRepository;
import com.max.api.v1.models.DigitRange;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DigitRangeService extends AbstractRangeService<Integer>{

    private final DigitRangeRepository digitRangeRepository;

    public void saveDigitRange(List<DigitRange> ranges) {
        digitRangeRepository.saveAll(ranges.stream().toList());
    }

    public DigitRange getMinimumDigitRange() {
        return digitRangeRepository.findMinimumDigitRange();
    }
}
