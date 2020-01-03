package Advent2018;

import Advent2018.ExceriseNine;
import org.junit.Test;

import java.io.IOException;

public class ExceriseNineTest {

    @Test
    public void process() throws IOException {
        new ExceriseNine().process("/exceriseNine.txt");
    }

    @Test
    public void processExample() throws IOException {
        new ExceriseNine().process("/exceriseNineExample.txt");
    }

}