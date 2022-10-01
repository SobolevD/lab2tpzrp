package com.ssau.tpzrp.lab2.validators;

import com.ssau.tpzrp.lab2.model.ChordModel;

import java.math.BigInteger;
import java.util.Set;

public class ChordModelSearchNodeValidator {
    public static void validate(ChordModel model, String startPos, String targetPos) {
        Set<BigInteger> nodesPositions = model.getModel().keySet();

        BigInteger pos = new BigInteger(targetPos);
        if (!nodesPositions.contains(pos)) {
            throw new RuntimeException("[ERROR] Target node doesn't exist");
        }

        if (!isPositionValid(new BigInteger(startPos), model.getModelSize()) ||
                !isPositionValid(new BigInteger(targetPos), model.getModelSize())) {
            throw new RuntimeException("[ERROR] Positions not valid");
        }
    }

    private static boolean isPositionValid(BigInteger position, BigInteger modelSize) {
        return position.compareTo(BigInteger.ZERO) >= 0 && position.compareTo(modelSize) < 0;
    }
}
