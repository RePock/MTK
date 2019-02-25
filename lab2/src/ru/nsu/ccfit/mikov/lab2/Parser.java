package ru.nsu.ccfit.mikov.lab2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    private ArrayList<Integer> finalStates = new ArrayList<>();
    private HashMap<Integer, HashMap<Character, Integer>> regulationstransition = new HashMap<>();
    private int currentState = 1;
    int startState, endState;
    char type;
    
    public void init(String fileName) throws MyExceptions, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String line;

        while ((line = reader.readLine()) != null && line.startsWith(" ")) {
        }
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

        while ((line = reader.readLine()) != null) {
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

            if (!regulationstransition.containsKey(startState)) {
                regulationstransition.put(startState, new HashMap<>());
            }
            if (regulationstransition.get(startState).containsKey(type)) {
                throw new MyExceptions("Error incorrect values in file: duplicate transition");
            }
            regulationstransition.get(startState).put(type, endState);
        }
        if (regulationstransition.isEmpty()) {
            throw new MyExceptions("Error incorrect values in file: transition rules is empty");
        }
    }

    public boolean checkSequence(String seq) throws MyExceptions {
        currentState = 1;
        if (finalStates.isEmpty() || regulationstransition.isEmpty()) {
            throw new MyExceptions("Error incorrect values in file: don't initilization yet");
        }

        for (Character c : seq.toCharArray()) {
            if (!regulationstransition.get(currentState).containsKey(c)) {
                throw new MyExceptions("Error incorrect values in file: unexpected transition");
            }

            currentState = regulationstransition.get(currentState).get(c);
        }

        return finalStates.contains(currentState);
    }

}

