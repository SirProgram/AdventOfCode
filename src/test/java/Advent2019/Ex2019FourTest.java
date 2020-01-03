package Advent2019;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;

public class Ex2019FourTest {

    @Test
    public void process() throws IOException {
        new Ex2019Four().process("/Ex2019Four.txt");
    }


    @Test
    public void hasDouble() {
        Ex2019Four ex = new Ex2019Four();

        Assert.assertThat(ex.hasDouble(123445), is(true));
        Assert.assertThat(ex.hasDouble(123457), is(false));
    }

    @Test
    public void increasedDigits() {
        Ex2019Four ex = new Ex2019Four();

        Assert.assertThat(ex.hasIncreasingDigits(123445), is(true));
        Assert.assertThat(ex.hasIncreasingDigits(123486), is(false));
    }

    @Test
    public void hasOnlyDouble() {
        Ex2019Four ex = new Ex2019Four();

        //Assert.assertThat(ex.hasOnlyDouble(112233), is(true));
        //Assert.assertThat(ex.hasOnlyDouble(123444), is(false));
        //Assert.assertThat(ex.hasOnlyDouble(111122), is(true));
        //Assert.assertThat(ex.hasOnlyDouble(112345), is(true));
        Assert.assertThat(ex.hasOnlyDouble(111345), is(false));
    }
}