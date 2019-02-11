package ru.nsu.ccfit.mikov.lab1;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

import static ru.nsu.ccfit.mikov.lab1.Lexeme.LexemeType.*;

public class Lexer implements AutoCloseable {

    private Reader reader;
    private int curr;
    private int position;

    private int buffNextChar() throws IOException {
        curr = reader.read();
        position++;
        return curr;
    }

    private Lexeme readNumber() throws IOException {
        StringBuilder number = new StringBuilder();

        do {
            number.append((char) curr);
            buffNextChar();
        } while (Character.isDigit(curr));

        return new Lexeme(NUMBER, number.toString());
    }

    public Lexer(Reader reader) throws IOException {
        this.reader = reader;
        buffNextChar();
    }

    public Lexeme getLexeme() throws IOException, ParseException {
        while (Character.isWhitespace(curr))
            buffNextChar();

        switch (curr) {
            case '0':
                return readNumber();
            case '1':
                return readNumber();
            case '2':
                return readNumber();
            case '3':
                return readNumber();
            case '4':
                return readNumber();
            case '5':
                return readNumber();
            case '6':
                return readNumber();
            case '7':
                return readNumber();
            case '8':
                return readNumber();
            case '9':
                return readNumber();
            case '+':
                buffNextChar();
                return new Lexeme(PLUS);
            case '-':
                buffNextChar();
                return new Lexeme(MINUS);
            case '*':
                buffNextChar();
                return new Lexeme(MULTIPLICATION);
            case '/':
                buffNextChar();
                return new Lexeme(DIVISION);
            case '^':
                buffNextChar();
                return new Lexeme(EXPONENTIATION);
            case '(':
                buffNextChar();
                return new Lexeme(LEFT_BRACKET);
            case ')':
                buffNextChar();
                return new Lexeme(RIGHT_BRACKET);
            case -1:
                return new Lexeme(EOF);
            default:
                throw new ParseException("Error, surprising char", 0);
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
