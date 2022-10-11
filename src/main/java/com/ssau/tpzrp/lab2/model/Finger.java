package com.ssau.tpzrp.lab2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@Builder
public class Finger {
    private BigInteger start;
    private Interval interval;
    private ChordNode successor;
    private ChordNode predecessor;
}
