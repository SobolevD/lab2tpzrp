package com.ssau.tpzrp.lab2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@Builder
public class Interval {
    private BigInteger start;
    private BigInteger end;

    private boolean isNumIn(BigInteger num) {
        return num.compareTo(start) >= 0 && num.compareTo(end) <= 0;
    }

    public boolean isNumInCyclic(BigInteger num) {
        if (isNumIn(num))
            return true;
        if (start.compareTo(num) == 0 || start.compareTo(end) == 0) {
            return true;
        }
        return start.compareTo(end) >= 1 && num.compareTo(start) >= 1;
    }

    @Override
    public String toString() {
        return String.format("(%s;%s)", start, end);
    }
}
