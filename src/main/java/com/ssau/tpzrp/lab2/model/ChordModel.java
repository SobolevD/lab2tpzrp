package com.ssau.tpzrp.lab2.model;

import com.ssau.tpzrp.lab2.validators.ChordModelAddNodeValidator;
import com.ssau.tpzrp.lab2.validators.ChordModelInitValidator;
import com.ssau.tpzrp.lab2.validators.ChordModelRemoveNodeValidator;
import com.ssau.tpzrp.lab2.validators.ChordModelSearchNodeValidator;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static com.ssau.tpzrp.lab2.constants.Constants.*;

@Data
public class ChordModel {

    private Map<BigInteger, ChordNode> model;
    private BigInteger modelSize;
    private int bitsCount;

    public ChordModel(int bitsCount, List<Integer> nodesPositions) {

        ChordModelInitValidator.validate(bitsCount, nodesPositions);

        this.bitsCount = bitsCount;
        this.modelSize = BigInteger.TWO.pow(bitsCount);
        this.model = new TreeMap<>();

        for (Integer pos : nodesPositions) {
            addNode(pos.toString());
        }
    }

    public void addNode(String position) {

        ChordModelAddNodeValidator.validate(this, position);

        BigInteger pos = new BigInteger(position);

        ChordNode newNode = ChordNode.builder()
                .position(pos)
                .build();
        newNode.setFingersTable(getFingersTable(newNode));
        model.put(pos, newNode);
        updateReferences();
    }

    public void removeNode(String position) {

        ChordModelRemoveNodeValidator.validate(this, position);

        BigInteger pos = new BigInteger(position);

        model.remove(pos);
        updateReferences();
    }

    public ChordNode findNode(String startPos, String targetPos) {

        ChordModelSearchNodeValidator.validate(this, startPos, targetPos);

        int transitionsCount = 0;

        BigInteger currentPos = new BigInteger(startPos);
        if (startPos.equals(targetPos)) {
            System.out.printf("Transitions count: " + transitionsCount);
            return model.get(currentPos);
        }

        BigInteger targetPosition = new BigInteger(targetPos);
        while (!currentPos.toString().equals(targetPos)) {
            ChordNode currentNode = model.get(currentPos);
            System.out.printf(currentPos + " -> ");
            ++transitionsCount;

            BigInteger fixedPosition = currentPos;
            for (Finger currentFinger : currentNode.getFingersTable()) {
                Interval currentInterval = currentFinger.getInterval();

                if (currentInterval.isNumInCyclic(targetPosition)) {
                    currentPos = currentFinger.getSuccessor().getPosition();
                    break;
                }
            }

            if (fixedPosition.equals(currentPos)) {
                int lastFingerIndex = currentNode.getFingersTable().size() - 2;
                currentPos = currentNode.getFingersTable().get(lastFingerIndex).getSuccessor().getPosition();
            }
        }
        System.out.printf(currentPos + "\n");
        System.out.printf("Transitions count: " + transitionsCount);
        return model.get(currentPos);
    }

    private List<Finger> getFingersTable(ChordNode node) {

        List<Finger> fingers = new ArrayList<>();

        int fingersTableSize = bitsCount;

        for (int i = 1; i <= fingersTableSize; ++i) {

            BigInteger fingerStart = calculateFingerStart(node, i);
            BigInteger nextFingerStart = calculateFingerStart(node, i + 1);

            Interval interval = Interval.builder()
                    .start(fingerStart)
                    .end(nextFingerStart)
                    .build();

            ChordNode successor = model.isEmpty() ? node : getSuccessor(interval);
            ChordNode predecessor = model.isEmpty() ? node : getPredecessor(interval);

            Finger currentFinger = Finger.builder()
                    .start(fingerStart)
                    .interval(interval)
                    .node(true)
                    .successor(successor)
                    .predecessor(predecessor)
                    .build();

            fingers.add(currentFinger);
        }

        return fingers;
    }

    private ChordNode getSuccessor(Interval interval) {
        Set<BigInteger> positions = model.keySet();

        for (BigInteger currentPosition : positions) {
            if (interval.isNumInCyclic(currentPosition)) {
                return model.get(currentPosition);
            }
        }

        BigInteger firstKey = positions.stream().toList().get(0);
        return model.get(firstKey);
    }

    private ChordNode getPredecessor(Interval interval) {
        Set<BigInteger> positions = model.keySet();

        int lastIndex = model.size() - 1;

        BigInteger previousKey = positions.stream().toList().get(lastIndex);
        for (BigInteger currentPosition : positions) {
            if (interval.isNumInCyclic(currentPosition)) {
                return model.get(previousKey);
            }
            previousKey = currentPosition;
        }

        previousKey = positions.stream().toList().get(lastIndex);

        return model.get(previousKey);
    }



    private void updateReferences() {

        for (ChordNode currentNode : model.values()) {
            for (Finger currentFinger : currentNode.getFingersTable()) {
                Interval currentInterval = currentFinger.getInterval();

                ChordNode newSuccessor = getSuccessor(currentInterval);
                ChordNode newPredecessor = getPredecessor(currentInterval);

                currentFinger.setSuccessor(newSuccessor);
                currentFinger.setPredecessor(newPredecessor);
            }
        }
    }

    private BigInteger calculateFingerStart(ChordNode node, int fingerNumber) {
        BigInteger fingerStart = node.getPosition();
        fingerStart = fingerStart.add(BigInteger.TWO.pow(fingerNumber - 1));
        return fingerStart.mod(BigInteger.TWO.pow(bitsCount));
    }

    public static void main(String[] args) {

        // 1. Create system
        ChordModel chordModel = new ChordModel(INITIAL_SYSTEM_BIT_DIMENSION, INITIAL_NODES_POSITION);
        chordModel.printSystem("#1. Created system");

        // 2. Add node
        chordModel.addNode(NODE_POSITION_TO_ADD);
        chordModel.printSystem("#2. System with new node");

        // 3. Remove node
        chordModel.removeNode(NODE_POSITION_TO_REMOVE);
        chordModel.printSystem("#3. System without one node");

        // 4. Search node
        chordModel.findNode(NODE_POSITION_START_SEARCH, NODE_POSITION_TO_SEARCH);
    }

    public void printSystem(String header) {
        System.out.println("======== " + header + " ========");
        System.out.println("=======================================");
        System.out.println(model);
        System.out.println("=======================================");
    }
}
