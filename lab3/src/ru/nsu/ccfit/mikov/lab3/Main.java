package ru.nsu.ccfit.mikov.lab3;

public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            parser.init(args[0]);

            System.out.println(parser.checkSequence("aba"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}