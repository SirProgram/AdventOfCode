package Advent2018;

import Advent2018.ExceriseSeven;
import org.junit.Test;

import java.io.IOException;

public class ExceriseSevenTest {

    @Test
    public void process() throws IOException {
        new ExceriseSeven().process("/exceriseSeven.txt");
    }

    @Test
    public void processExample() throws IOException {
        new ExceriseSeven().process("/exceriseSevenExample.txt");
    }
}