package Advent2019;

import org.junit.Test;

import java.io.IOException;

public class Ex2019SixTest {

    @Test
    public void example() throws IOException {
        new Ex2019Six().process("/Ex2019SixExample.txt");
    }

    @Test
    public void process() throws IOException {
        new Ex2019Six().process("/Ex2019Six.txt");
    }

    @Test
    public void exampleB() throws IOException {
        new Ex2019Six().processB("/Ex2019SixBExample.txt");
    }

    @Test
    public void processB() throws IOException {
        new Ex2019Six().processB("/Ex2019Six.txt");
    }
}