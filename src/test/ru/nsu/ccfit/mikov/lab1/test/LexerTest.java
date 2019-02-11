
import org.junit.Test;
import ru.nsu.ccfit.mikov.lab1.*;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.nsu.ccfit.mikov.lab1.Lexeme.LexemeType.*;

public class LexerTest {

    void assertLexemeTypes(String input, Lexeme.LexemeType... types) throws IOException, ParseException {
        try (Lexer lexer = new Lexer(new StringReader(input))) {
            for (Lexeme.LexemeType type : types)
                assertEquals(type, lexer.getLexeme().type);
        }
    }

    void assertLexemeLem(String input, String... lems) throws IOException, ParseException {
        try (Lexer lexer = new Lexer(new StringReader(input))) {
            for (String lem : lems)
                assertEquals(lem, lexer.getLexeme().lem);
        }
    }

    @Test
   public void checkGetSingleTypes() throws IOException, ParseException {
        assertLexemeTypes("", EOF);
        assertLexemeTypes(" ", EOF);
        assertLexemeTypes("\t", EOF);
        assertLexemeTypes("\t\t", EOF);
        assertLexemeTypes("(", LEFT_BRACKET, EOF);
        assertLexemeTypes(")", RIGHT_BRACKET, EOF);
        assertLexemeTypes("+", PLUS, EOF);
        assertLexemeTypes("-", MINUS, EOF);
        assertLexemeTypes("*", MULTIPLICATION, EOF);
        assertLexemeTypes("/", DIVISION, EOF);
        assertLexemeTypes("^", EXPONENTIATION, EOF);
    }

    @Test
   public void checkGetNumberTypes() throws IOException, ParseException {
        assertLexemeTypes("0", NUMBER, EOF);
        assertLexemeTypes("1", NUMBER, EOF);
        assertLexemeTypes("2", NUMBER, EOF);
        assertLexemeTypes("3", NUMBER, EOF);
        assertLexemeTypes("4", NUMBER, EOF);
        assertLexemeTypes("5", NUMBER, EOF);
        assertLexemeTypes("6", NUMBER, EOF);
        assertLexemeTypes("7", NUMBER, EOF);
        assertLexemeTypes("8", NUMBER, EOF);
        assertLexemeTypes("9", NUMBER, EOF);
        assertLexemeTypes("123", NUMBER, EOF);
    }

    @Test
    public void checkGetNumberValues() throws IOException, ParseException {
       assertLexemeLem("0", "0");
       assertLexemeLem("1", "1");
       assertLexemeLem("2", "2");
       assertLexemeLem("3", "3");
       assertLexemeLem("4", "4");
       assertLexemeLem("5", "5");
       assertLexemeLem("6", "6");
       assertLexemeLem("7", "7");
       assertLexemeLem("8", "8");
       assertLexemeLem("9", "9");
       assertLexemeLem("123", "123");
    }

    @Test
    public void checkGetComplexShortTypes() throws IOException, ParseException {
        assertLexemeTypes("+-*/^()", PLUS, MINUS, MULTIPLICATION, DIVISION, EXPONENTIATION, LEFT_BRACKET, RIGHT_BRACKET, EOF);
    }

    @Test
    public void checkGetComplexNumberTypes() throws IOException, ParseException {
        assertLexemeTypes("++1--", PLUS, PLUS, NUMBER, MINUS, MINUS, EOF);
        assertLexemeTypes("** 1 //", MULTIPLICATION, MULTIPLICATION, NUMBER, DIVISION, DIVISION, EOF);
    }

    @Test
    public void checkGetComplexLong() throws IOException, ParseException {
        assertLexemeTypes("1+--2^3 4", NUMBER, PLUS, MINUS, MINUS, NUMBER, EXPONENTIATION, NUMBER, NUMBER, EOF);
    }

    @Test
    public void checkGetWrong() {
        assertThrows(ParseException.class, () -> assertLexemeTypes(",", EOF));
        assertThrows(ParseException.class, () -> assertLexemeTypes("?", EOF));
        assertThrows(ParseException.class, () -> assertLexemeTypes("1 + 2, 3 / 4", NUMBER, PLUS, NUMBER, DIVISION, NUMBER, EOF));
        assertThrows(ParseException.class, () -> assertLexemeTypes("&", EOF));
    }

}