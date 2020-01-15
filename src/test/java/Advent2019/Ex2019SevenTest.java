package Advent2019;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;

public class Ex2019SevenTest {

    @Test
    public void example() throws IOException {
        List<Integer> result = new Ex2019Seven().process("/Ex2019SevenExample.txt", 0, Arrays.asList(4, 3, 2, 1, 0));
        Assert.assertThat(result, is(Arrays.asList(43210)));
    }

    @Test
    public void example2() throws IOException {
        List<Integer> result = new Ex2019Seven().process("/Ex2019SevenExample2.txt", 0, Arrays.asList(0, 1, 2, 3, 4));
        Assert.assertThat(result, is(Arrays.asList(54321)));
    }

    @Test
    public void example3() throws IOException {
        List<Integer> result = new Ex2019Seven().process("/Ex2019SevenExample3.txt", 0, Arrays.asList(1, 0, 4, 3, 2));
        Assert.assertThat(result, is(Arrays.asList(65210)));
    }

    @Test
    public void example3Find() throws IOException {
        List<Integer> result = new Ex2019Seven().process("/Ex2019SevenExample3.txt", 0);
        Assert.assertThat(result, is(Arrays.asList(65210)));
    }

    @Test
    public void process() throws IOException {
        List<Integer> result = new Ex2019Seven().process("/Ex2019Seven.txt", 0);
        System.out.println(result.get(0));
    }

    @Test
    public void exampleB() throws IOException {
        List<Integer> result = new Ex2019Seven().processB("/Ex2019SevenBExample.txt", 0, Arrays.asList(9, 8, 7, 6, 5));
        Assert.assertThat(result, is(Arrays.asList(139629729)));
    }

    @Test
    public void exampleBFind() throws IOException {
        List<Integer> result = new Ex2019Seven().processB("/Ex2019SevenBExample.txt", 0);
        Assert.assertThat(result, is(Arrays.asList(139629729)));
    }

    @Test
    public void exampleB2Find() throws IOException {
        List<Integer> result = new Ex2019Seven().processB("/Ex2019SevenBExample2.txt", 0);
        Assert.assertThat(result, is(Arrays.asList(18216)));
    }
    @Test
    public void processB() throws IOException {
        List<Integer> result = new Ex2019Seven().processB("/Ex2019Seven.txt", 0);
        System.out.println(result.get(0));
    }

}