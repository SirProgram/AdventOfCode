package Advent2019;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;

public class Ex2019FiveTest {

    @Test
    public void example() throws IOException {
        new Ex2019Five().process("/Ex2019FiveExample.txt", 1);
    }

    @Test
    public void exampleBOne() throws IOException {
        List<Integer> outputs = new Ex2019Five().process("/Ex2019FiveBExample.txt", 1);
        Assert.assertThat(outputs, is(Arrays.asList(0, 1, 0, 1)));
    }

    @Test
    public void exampleBEight() throws IOException {
        List<Integer> outputs = new Ex2019Five().process("/Ex2019FiveBExample.txt", 8);
        Assert.assertThat(outputs, is(Arrays.asList(1, 0, 1, 0)));
    }

    @Test
    public void exampleBTen() throws IOException {
        List<Integer> outputs = new Ex2019Five().process("/Ex2019FiveBExample.txt", 10);
        Assert.assertThat(outputs, is(Arrays.asList(0, 0, 0, 0)));
    }

    @Test
    public void exampleB2Zero() throws IOException {
        List<Integer> outputs = new Ex2019Five().process("/Ex2019FiveBExample2.txt", 0);
        Assert.assertThat(outputs, is(Arrays.asList(0, 0)));
    }

    @Test
    public void exampleB2NonZero() throws IOException {
        List<Integer> outputs = new Ex2019Five().process("/Ex2019FiveBExample2.txt", 10);
        Assert.assertThat(outputs, is(Arrays.asList(1, 1)));
    }

    @Test
    public void exampleBBigOne() throws IOException {
        List<Integer> outputs = new Ex2019Five().process("/Ex2019FiveBExampleBig.txt", 1);
        Assert.assertThat(outputs, is(Arrays.asList(999)));
    }

    @Test
    public void exampleBBigEight() throws IOException {
        List<Integer> outputs = new Ex2019Five().process("/Ex2019FiveBExampleBig.txt", 8);
        Assert.assertThat(outputs, is(Arrays.asList(1000)));
    }

    @Test
    public void exampleBBigTen() throws IOException {
        List<Integer> outputs = new Ex2019Five().process("/Ex2019FiveBExampleBig.txt", 9);
        Assert.assertThat(outputs, is(Arrays.asList(1001)));
    }

    @Test
    public void process() throws IOException {
        new Ex2019Five().process("/Ex2019Five.txt", 1);
    }

    @Test
    public void processB() throws IOException {
        new Ex2019Five().process("/Ex2019Five.txt", 5);
    }

}