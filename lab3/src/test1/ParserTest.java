package test;


import org.junit.Test;
import ru.nsu.ccfit.mikov.lab3.MyExceptions;
import ru.nsu.ccfit.mikov.lab3.Parser;

import java.io.IOException;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void CheckFirstAutomat() throws MyExceptions, IOException {
        Parser parser = new Parser();
        parser.init("C:\\Users\\123\\Git\\MTK\\lab3\\first.txt");
        assertTrue(parser.checkSequence("ab"));
        assertTrue(parser.checkSequence("abababa"));
        assertTrue(parser.checkSequence("aaaabaaaaa"));
        assertTrue(parser.checkSequence("bbbbbbbbabbbbb"));

        assertFalse(parser.checkSequence("b"));
        assertFalse(parser.checkSequence("bb"));
        assertFalse(parser.checkSequence("ba"));
        assertFalse(parser.checkSequence(""));
    }

    @Test
    public void CheckSecondAutomat() throws MyExceptions, IOException {
        Parser parser = new Parser();
        parser.init("C:\\Users\\123\\Git\\MTK\\lab3\\second.txt");
        assertTrue(parser.checkSequence("ab"));
        assertTrue(parser.checkSequence("ababbaba"));
        assertTrue(parser.checkSequence("abaaaa"));
        assertTrue(parser.checkSequence("abbbbb"));

        assertFalse(parser.checkSequence("b"));
        assertFalse(parser.checkSequence("ba"));
        assertFalse(parser.checkSequence("aab"));
        assertFalse(parser.checkSequence("bababba"));
        assertFalse(parser.checkSequence(""));
    }
}