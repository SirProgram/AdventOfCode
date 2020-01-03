package Advent2018;

import Advent2018.ExceriseSevenB;
import org.junit.Test;

import java.io.IOException;

public class ExceriseSevenBTest {

    @Test
    public void process() throws IOException {
        new ExceriseSevenB().process("/exceriseSeven.txt", 5, 60);
        // 829 > ? > 824
    }

    @Test
    public void processExample() throws IOException {
        new ExceriseSevenB().process("/exceriseSevenExample.txt", 2, 0);
    }
}