package Advent2018;

import Advent2018.ExceriseTen;
import org.junit.Test;

import java.io.IOException;

public class ExceriseTenTest {

    @Test
    public void process() throws IOException {
        new ExceriseTen().process("/exceriseTen.txt");
    }

    @Test
    public void processExample() throws IOException {
        new ExceriseTen().process("/exceriseTenExample.txt");
    }

}