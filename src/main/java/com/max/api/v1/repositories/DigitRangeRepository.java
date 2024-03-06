package com.max.api.v1.repositories;

import com.max.api.v1.models.DigitRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DigitRangeRepository extends JpaRepository<DigitRange, Long> {

    @Query("SELECT r FROM DigitRange r WHERE (r.start, r.end) = (SELECT MIN(r.start), MIN(r.end) FROM DigitRange r)")
    DigitRange findMinimumDigitRange();
}
