package Advent2018;

import Advent2018.ExceriseEleven;
import org.junit.Test;

import java.io.IOException;

public class ExceriseElevenTest {

    @Test
    public void process() throws IOException {
        new ExceriseEleven().process("/exceriseEleven.txt");
    }

    @Test
    public void processExample() throws IOException {
        new ExceriseEleven().process("/exceriseElevenExample.txt");
    }

    @Test
    public void processPartTwo() throws IOException {
        new ExceriseEleven().processPartTwo("/exceriseEleven.txt");
    }

    @Test
    public void processPartTwoExample() throws IOException {
        new ExceriseEleven().processPartTwo("/exceriseElevenExample.txt");
    }

}