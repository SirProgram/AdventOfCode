package Advent2018.Ex13;

import org.junit.Test;

public class ExceriseThirteenTest {

    @Test
    public void process() throws Exception {
        new ExceriseThirteen().process("/exceriseThirteen.txt");
    }

    @Test
    public void processExample() throws Exception {
        new ExceriseThirteen().process("/exceriseThirteenExample.txt");
    }
}