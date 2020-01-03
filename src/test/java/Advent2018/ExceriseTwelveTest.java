package Advent2018;

import Advent2018.ExceriseTwelve;
import org.junit.Test;

import java.io.IOException;

public class ExceriseTwelveTest {

    @Test
    public void process() throws IOException {
        new ExceriseTwelve().process("/exceriseTwelve.txt", 20);
    }

    @Test
    public void processExample() throws IOException {
        new ExceriseTwelve().process("/exceriseTwelveExample.txt", 20);
    }

    @Test
    public void processBShorter() throws IOException {
        new ExceriseTwelve().process("/exceriseTwelve.txt", 5000000L);
    }

    @Test
    public void processBShortest() throws IOException {
        new ExceriseTwelve().process("/exceriseTwelve.txt", 500000L);
    }

    /*@Test
    public void processB() throws IOException {
        new Advent2018.ExceriseTwelve().process("/exceriseTwelve.txt", 50000000000L);

    }*/

}