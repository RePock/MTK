
import org.junit.Test;
import ru.nsu.ccfit.mikov.lab1.*;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    public void assertPayment(String input, int result) throws IOException, ParseException {
        try (Lexer lexer = new Lexer(new StringReader(input))) {
            assertEquals(result, new Parser(lexer).parsPayment());
        }
    }

    @Test
   public void checkNumber() throws IOException, ParseException {
        assertPayment("007", 007);
    }

    @Test
   public void checkOneOperation() throws IOException, ParseException {
        assertPayment("2 + 2", 4);
        assertPayment("2 - 2", 0);
        assertPayment("2 * 2", 4);
        assertPayment("2 / 2", 1);
        assertPayment("2 ^ 2", 4);
    }

    @Test
   public void checkBracketsNumber() throws IOException, ParseException {
        assertPayment("(2)", 2);
        assertPayment("((2))", 2);
        assertPayment("((((2))))", 2);
    }

    @Test
    public void checkBracketsAndOperations() throws IOException, ParseException {
        assertPayment("(- 2)", -2);
        assertPayment("2 + (-2)", 0);
        assertPayment("(2 + 2)", 4);
        assertPayment("(2) * (2)", 4);
        assertPayment("((2)) / ((2))", 1);
        assertPayment("((((2)) ^ ((2))))", 4);
    }

    @Test
    public void checkRightComplex() throws IOException, ParseException {
        assertPayment("2 ^ 2 ^ 2", 16);
        assertPayment("2 * 2 * 2", 8);
    }

    @Test
   public void checksWrongSyntaxNoOperands() {
        assertThrows(ParseException.class, () -> assertPayment("", 0));
        assertThrows(ParseException.class, () -> assertPayment("2 2 2 2", 0));
        assertThrows(ParseException.class, () -> assertPayment("+ - - - -", 0));
    }

    @Test
    public void checkWrongSyntaxBrackets() {
        assertThrows(ParseException.class, () -> assertPayment("(", 0));
        assertThrows(ParseException.class, () -> assertPayment(")", 0));
        assertThrows(ParseException.class, () -> assertPayment("()", 0));
        assertThrows(ParseException.class, () -> assertPayment(")(", 0));
    }

    @Test
    public void checkWrongSyntaxBracketsAndNoOperants() {
        assertThrows(ParseException.class, () -> assertPayment("2 * (2-)", 0));
        assertThrows(ParseException.class, () -> assertPayment("2 + (*2)", 0));
        assertThrows(ParseException.class, () -> assertPayment("2 + (/2)", 0));
        assertThrows(ParseException.class, () -> assertPayment("2 + (^2)", 0));
    }

    @Test
    public void checkDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> assertPayment("2 / 0", 0));
        assertThrows(ArithmeticException.class, () -> assertPayment("2 / (2 - 2)", 0));
    }

}