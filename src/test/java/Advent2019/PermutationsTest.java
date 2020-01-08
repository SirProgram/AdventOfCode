package Advent2019;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class PermutationsTest {

    @Test
    public void permutations() {
        List<Integer> initialList = Arrays.asList(0, 1, 2, 3, 4);
        List<List<Integer>> permutations = Permutations.permutations(initialList);
        System.out.println(permutations);
        Assert.assertThat(permutations.size(), is(24));

    }
}