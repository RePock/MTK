package ru.nsu.ccfit.mikov.lab1;


import java.io.IOException;
import java.text.ParseException;

import static ru.nsu.ccfit.mikov.lab1.Lexeme.LexemeType.*;

public class Parser {

    private Lexer lexer;
    private Lexeme curr;

    public Parser(Lexer lexer) throws IOException, ParseException {
        this.lexer = lexer;
        curr = lexer.getLexeme();
    }

    private int parsExpr() throws IOException, ParseException {
        int result = parsTerm();
        Lexeme.LexemeType lexemeType = curr.type;

        int sign = 1;

        if (lexemeType == PLUS) sign = 1;
        if (lexemeType == MINUS) sign = -1;

        while (lexemeType == PLUS || lexemeType == MINUS) {
            curr = lexer.getLexeme();
            result += sign * parsTerm();
            lexemeType = curr.type;
        }
        return result;
    }

    private int parsTerm() throws IOException, ParseException {
        int result = parsFactor();
        Lexeme.LexemeType lexemeType = curr.type;

        while (lexemeType == MULTIPLICATION || lexemeType == DIVISION) {
            curr = lexer.getLexeme();
            if (lexemeType == MULTIPLICATION)
                result *= parsFactor();
            else
                result /= parsFactor();
            lexemeType = curr.type;
        }

        return result;
    }

    private int parsFactor() throws IOException, ParseException {
        int result = parsPower();
        if (curr.type == EXPONENTIATION) {
            curr = lexer.getLexeme();
            return (int) Math.pow(result, parsFactor());
        }
        return result;
    }

    private int parsPower() throws IOException, ParseException {

        if (curr.type == MINUS) {
            curr = lexer.getLexeme();
            return -parsAtom();
        }
        return parsAtom();
    }

    private int parsAtom() throws IOException, ParseException {
        if (curr.type == NUMBER) {
            int result = Integer.parseInt(curr.lem);
            curr = lexer.getLexeme();
            return result;
        } else if (curr.type == LEFT_BRACKET) {
            curr = lexer.getLexeme();
            int result = parsExpr();
            if (curr.type != RIGHT_BRACKET)
                throw new ParseException("Error, not closing bracket", 0);
            curr = lexer.getLexeme();
            return result;
        } else
            throw new ParseException("Error, unknown lexeme type: " + curr.type.name(), 0);
    }

    public int parsPayment() throws IOException, ParseException {
        int result = parsExpr();
        if (curr.type != EOF)
            throw new ParseException("Error, waiting EOF", 0);
        return result;
    }

}

