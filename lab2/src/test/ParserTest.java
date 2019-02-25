package test;

import org.junit.Test;
import ru.nsu.ccfit.mikov.lab2.MyExceptions;
import ru.nsu.ccfit.mikov.lab2.Parser;

import java.io.IOException;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void CheckFirstAutomat() throws MyExceptions, IOException {
        Parser parser = new Parser();
        parser.init("C:\\Users\\123\\Git\\MTK\\lab2\\first.txt");
        assertTrue(parser.checkSequence("xxx"));
        assertTrue(parser.checkSequence("xxxx"));
        assertTrue(parser.checkSequence("yyyxxx"));
        assertTrue(parser.checkSequence("xxxyxxzxxx"));

        assertFalse(parser.checkSequence("x"));
        assertFalse(parser.checkSequence("xx"));
        assertFalse(parser.checkSequence("y"));
        assertFalse(parser.checkSequence("yyy"));
        assertFalse(parser.checkSequence("zzzz"));
        assertFalse(parser.checkSequence("yzyxx"));
        assertFalse(parser.checkSequence("yxxxy"));
    }

    @Test
    public void CheckSecondAutomat() throws MyExceptions, IOException {
        Parser parser = new Parser();
        parser.init("C:\\Users\\123\\Git\\MTK\\lab2\\second.txt");
        assertTrue(parser.checkSequence("bb"));
        assertTrue(parser.checkSequence("bbbb"));
        assertTrue(parser.checkSequence("abaaaab"));
        assertTrue(parser.checkSequence("bbaaaaaaabb"));
        assertTrue(parser.checkSequence("aaa"));

        assertFalse(parser.checkSequence("b"));
        assertFalse(parser.checkSequence("bbb"));
        assertFalse(parser.checkSequence("bbaaaab"));
        assertFalse(parser.checkSequence("aab"));
        assertFalse(parser.checkSequence("ababababababab"));
    }
}