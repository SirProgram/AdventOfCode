package Advent2019;

import org.junit.Test;

import java.io.IOException;

public class Ex2019OneTest {

    @Test
    public void example() throws IOException {
        new Ex2019One().process("/Ex2019OneExample.txt");
    }

    @Test
    public void process() throws IOException {
        new Ex2019One().process("/Ex2019One.txt");
    }

    @Test
    public void processBExample() throws IOException {
        new Ex2019One().processB("/Ex2019OneBExample.txt");
    }

    @Test
    public void processB() throws IOException {
        new Ex2019One().processB("/Ex2019One.txt");
    }
}