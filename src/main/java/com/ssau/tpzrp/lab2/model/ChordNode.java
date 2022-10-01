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

    public void addFinger(Finger finger) {
        fingersTable.add(finger);
    }

    @Override
    public String toString() {
        return "ChordNode{" +
                "position=" + position +
                ", fingersTable=" + fingersTableString() +
                '}';
    }

    private String fingersTableString() {
        StringBuilder result = new StringBuilder();

        for (Finger finger : fingersTable) {
            result.append("\n{");
            result.append("\nStart = ").append(finger.getStart());
            result.append("\nInterval = ").append(finger.getInterval());
            result.append("\nSuccessor position = ").append(finger.getSuccessor().getPosition());
            result.append("\nPredecessor position = ").append(finger.getPredecessor().getPosition());
            result.append("}");
        }
        return result.toString();
    }
}
