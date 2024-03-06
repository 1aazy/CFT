package com.max.api.v1.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "letter_range")
public class LetterRange extends AbstractRange<String> {
    public LetterRange(String start, String end) {
        super(start, end);
    }

    public LetterRange() {
        super();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}