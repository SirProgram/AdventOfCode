package Advent2018.Ex14;

import org.junit.Test;

public class ExceriseFourteenTest {

    @Test
    public void process() throws Exception {
        new ExceriseFourteen().process("/exceriseFourteen.txt");
    }

    @Test
    public void processExample() throws Exception {
        new ExceriseFourteen().process("/exceriseFourteenExample.txt");
    }

    @Test
    public void processB() throws Exception {
        new ExceriseFourteen().processB("/exceriseFourteen.txt");
    }

    //20276286 too high
    //20276285 too high
    //20276280 too low

    @Test
    public void processExampleB() throws Exception {
        new ExceriseFourteen().processB("/exceriseFourteenBExample.txt");
    }

}