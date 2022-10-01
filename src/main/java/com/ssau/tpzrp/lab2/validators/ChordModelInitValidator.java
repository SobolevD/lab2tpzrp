package com.ssau.tpzrp.lab2.validators;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChordModelInitValidator {
    public static void validate(int bitsCount, List<Integer> nodesPositions) {

        Set<Integer> uniquePositions = new HashSet<>(nodesPositions);

        if (uniquePositions.size() != nodesPositions.size()) {
            throw new RuntimeException("[ERROR] Provided nodes position must have no duplicates!");
        }

        BigInteger modelSize = BigInteger.TWO.pow(bitsCount);

        for (Integer position : nodesPositions) {
            if (modelSize.compareTo(BigInteger.valueOf(position)) <= 0) {
                throw new RuntimeException("[ERROR] Provided position bigger than system size!");
            }
        }
    }
}
