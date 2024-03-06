package com.max.api.v1.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractRange<T extends Comparable<T>> implements Comparable<AbstractRange<T>> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "range_start")
    private T start;

    @Column(name = "range_end")
    private T end;

    public AbstractRange(T start, T end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[" + start + ", " + end + "]";
    }

    @Override
    public int compareTo(AbstractRange<T> o) {
        return this.start.compareTo(o.start) + this.end.compareTo(o.end);
    }
}
