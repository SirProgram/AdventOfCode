package Advent2019;

import org.junit.Test;

import java.io.IOException;

public class Ex2019TwoTest {

    @Test
    public void example() throws IOException {
        new Ex2019Two().process("/Ex2019TwoExample.txt");
    }

    @Test
    public void process() throws IOException {
        new Ex2019Two().process("/Ex2019Two.txt");
    }

    @Test
    public void processB() throws IOException {
        new Ex2019Two().processB("/Ex2019Two.txt");
    }

}