package ru.nsu.ccfit.mikov.lab3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Parser {
    private ArrayList<Integer> finalStates = new ArrayList<>();
    private HashMap<Integer, HashMap<Character, ArrayList<Integer>>>  transitionRules = new HashMap<>();
    private int defaultCurrentState = 1;
    int startState, endState;
    char type;

    private class Conf {
        public int pos;
        public int state;

        public Conf(int pos, int state) {
            this.pos = pos;
            this.state = state;
        }
    }

    public void init(String fileName) throws MyExceptions, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String line;

        while ((line = reader.readLine()) != null && line.startsWith(" ")){}
        if (line == null) {
            throw new MyExceptions("Error incorrect values in file");
        }

        for (String number : line.replace("\n", "").split(" ")) {
            try {
                int i = Integer.valueOf(number);
                if (i < 1) {
                    throw new MyExceptions("Error incorrect values in file: final state");
                }
            } catch (NumberFormatException e) {
                throw new MyExceptions("Error incorrect values in file: final state");
            }
            finalStates.add(Integer.valueOf(number));
        }
        if (finalStates.isEmpty()) {
            throw new MyExceptions("Error incorrect values in file: final states is empty");
        }

        while ((line = reader.readLine()) != null){
            String[] tmp = line.replace("\n", "").split(" ");
            if (tmp.length != 3) {
                throw new MyExceptions("Error incorrect values in file");
            }

            try {
                startState = Integer.valueOf(tmp[0]);
                type = tmp[1].toCharArray()[0];
                endState = Integer.valueOf(tmp[2]);
                if (startState < 1 || endState < 1) {
                    throw new MyExceptions("Error incorrect values in file:  start or end state");
                }
                if (!Character.isLetter(type)) {
                    throw new MyExceptions("Error incorrect values in file: transition type");
                }
            } catch (NumberFormatException e) {
                throw new MyExceptions("Error incorrect values in file: start or end state");
            }

            if (!transitionRules.containsKey(startState)) {
                transitionRules.put(startState, new HashMap<>());
            }
            if (!transitionRules.get(startState).containsKey(type)) {
                transitionRules.get(startState).put(type, new ArrayList<>());
            }
            if (transitionRules.get(startState).get(type).contains(endState)) {
                throw new MyExceptions("Error incorrect values in file: duplicate transition");
            }
            transitionRules.get(startState).get(type).add(endState);
        }
        if (transitionRules.isEmpty()) {
            throw new MyExceptions("Error incorrect values in file: transition rules is empty");
        }
    }

    public boolean checkSequence(String seq) throws MyExceptions {
        Stack<Conf> confStack = new Stack<>();
        confStack.push(new Conf(0, defaultCurrentState));

        if (finalStates.isEmpty() || transitionRules.isEmpty()) {
            throw new MyExceptions("Error incorrect values in file: don't initilization yet");
        }

        boolean recognized = false;
        while (!confStack.empty()) {
            Conf curConf = confStack.pop();
            if (curConf.pos == seq.length()) {
                if (finalStates.contains(curConf.state)) {
                    recognized = true;
                }
                continue;
            }
            if (!Character.isLetter(seq.charAt(curConf.pos))) {
                throw new MyExceptions("Error incorrect values in file: unexpected symbol");
            }
            if (!transitionRules.containsKey(curConf.state) ||
                    !transitionRules.get(curConf.state).containsKey(seq.charAt(curConf.pos))) {
                continue;
            }
            for (Integer endState : transitionRules.get(curConf.state).get(seq.charAt(curConf.pos))) {
                confStack.push(new Conf(curConf.pos + 1, endState));
            }
        }

        return recognized;
    }
}