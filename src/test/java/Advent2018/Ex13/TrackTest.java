package Advent2018.Ex13;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrackTest {

    @Test
    public void isIntersection() {
        assertTrue(Track.ofCharacter('+').isIntersection());

        assertFalse(Track.ofCharacter('/').isIntersection());
        assertFalse(Track.ofCharacter('\\').isIntersection());
    }

    @Test
    public void isCorner() {
        assertTrue(Track.ofCharacter('/').isCorner());
        assertTrue(Track.ofCharacter('\\').isCorner());

        assertThat(Track.ofCharacter('+').isCorner(), CoreMatchers.is(false));

    }
}