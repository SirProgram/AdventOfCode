package Advent2019;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class Ex2019ThreeTest {

    @Test
    public void example() throws IOException {
        new Ex2019Three().process("/Ex2019ThreeExample.txt");
    }

    @Test
    public void process() throws IOException {
        new Ex2019Three().process("/Ex2019Three.txt");
    }
}