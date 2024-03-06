package com.max.api.v1.models;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "digit_range")
public class DigitRange extends AbstractRange<Integer>{

    public DigitRange(Integer start, Integer end) {
        super(start, end);
    }

    public DigitRange() {
        super();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

