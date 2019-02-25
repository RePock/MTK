package ru.nsu.ccfit.mikov.lab2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            parser.init(args[0]);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[1])));
            String sequence = reader.readLine();

            System.out.println(parser.checkSequence(sequence));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
