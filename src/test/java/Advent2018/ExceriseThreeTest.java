package Advent2018;

import Advent2018.ExceriseThree;
import org.junit.Test;

import java.io.IOException;

public class ExceriseThreeTest {


    @Test
    public void ex3() throws IOException {
        ExceriseThree exceriseThree = new ExceriseThree();
        exceriseThree.process();

    }

    @Test
    public void ex3Part2() throws IOException {
        ExceriseThree exceriseThree = new ExceriseThree();
        exceriseThree.processTwo();

    }
}