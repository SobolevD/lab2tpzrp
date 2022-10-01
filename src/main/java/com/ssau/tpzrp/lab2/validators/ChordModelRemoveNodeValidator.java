package com.ssau.tpzrp.lab2.validators;

import com.ssau.tpzrp.lab2.model.ChordModel;

import java.math.BigInteger;
import java.util.Set;

public class ChordModelRemoveNodeValidator {
    public static void validate(ChordModel model, String position) {
        Set<BigInteger> nodesPositions = model.getModel().keySet();

        BigInteger pos = new BigInteger(position);
        if (!nodesPositions.contains(pos)) {
            throw new RuntimeException("[ERROR] Trying to delete not existing node");
        }
    }
}
