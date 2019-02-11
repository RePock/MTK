package ru.nsu.ccfit.mikov.lab1;

public class Lexeme {
    public enum LexemeType {
        NUMBER,
        PLUS,
        MINUS,
        MULTIPLICATION,
        DIVISION,
        EXPONENTIATION,
        LEFT_BRACKET,
        RIGHT_BRACKET,
        EOF;

        private String defaultValue;

        LexemeType() {
            this.defaultValue = null;
        }
    }

    public LexemeType type;
    public String lem;

    public Lexeme(LexemeType type, String lem) {
        this.type = type;
        this.lem = lem;
    }

    public Lexeme(LexemeType type) {
        this.type = type;
        this.type.defaultValue = type.defaultValue;
    }

}
