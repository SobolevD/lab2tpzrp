package com.ssau.tpzrp.lab2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ChordNode {

    private BigInteger position;
    private List<Finger> fingersTable;

    @Override
    public String toString() {
        return "ChordNode{" +
                "   position=" + position +
                ",  fingersTable=" + fingersTableString() +
                "}\n";
    }

    private String fingersTableString() {
        StringBuilder result = new StringBuilder();

        for (Finger finger : fingersTable) {
            result.append("\n   {");
            result.append("\n       Start = ").append(finger.getStart());
            result.append("\n       Interval = ").append(finger.getInterval());
            result.append("\n       Successor position = ").append(finger.getSuccessor().getPosition());
            result.append("\n       Predecessor position = ").append(finger.getPredecessor().getPosition());
            result.append("\n   }\n");
        }
        return result.toString();
    }
}
