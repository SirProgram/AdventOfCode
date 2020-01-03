package Advent2018;

import Advent2018.ExceriseEight;
import org.junit.Test;

import java.io.IOException;

public class ExceriseEightTest {

    @Test
    public void process() throws IOException {
        new ExceriseEight().process("/exceriseEight.txt");
    }

    @Test
    public void processExample() throws IOException {
        new ExceriseEight().process("/exceriseEightExample.txt");
    }

}