package Advent2018;

import Advent2018.ExceriseSix;
import org.junit.Test;

import java.io.IOException;

public class ExceriseSixTest {

    @Test
    public void process() throws IOException {
        new ExceriseSix().process("/exceriseSix.txt", 10000);
    }

    @Test
    public void example() throws IOException {
        ExceriseSix exceriseSix = new ExceriseSix();
        exceriseSix.process("/exceriseSixExample.txt", 32);
        exceriseSix.draw();
    }
}