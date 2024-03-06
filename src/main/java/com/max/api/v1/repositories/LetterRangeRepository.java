package com.max.api.v1.repositories;

import com.max.api.v1.models.LetterRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRangeRepository extends JpaRepository<LetterRange, Long> {

    @Query("SELECT r FROM LetterRange r WHERE (r.start, r.end) = (SELECT MIN(r.start), MIN(r.end) FROM LetterRange r)")
    LetterRange findMinimumLetterRange();
}
