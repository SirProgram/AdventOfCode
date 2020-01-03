package Advent2018.Ex13;

import utils.Direction;

import java.util.HashMap;
import java.util.Map;

public class Track {

    Map<Direction, Boolean> directions = new HashMap<>();
    String drawingCharacter;

    public static Track ofCorner(char c, boolean upDirection) {
        switch (c) {
            case '/':
                return new Track(upDirection, !upDirection, upDirection, !upDirection, c);
            case '\\':
                return new Track(upDirection, !upDirection, !upDirection, upDirection, c);
            default:
                return new Track(false, false, false, false, c);
        }
    }

    public static Track ofCharacter(char c) {
        switch (c) {
            case '+':
                return new Track(true, true, true, true, c);
            case '/':
                return new Track(false, true, false, true, c);
            case '\\':
                return new Track(false, false, true, true, c);
            case '|':
            case 'v':
            case '^':
                return new Track(true, true, false, false, '|');
            case '-':
            case '>':
            case '<':
                return new Track(false, false, true, true, '-');
            default:
                return new Track(false, false, false, false, c);
        }
    }

    public Track(boolean up, boolean down, boolean left, boolean right, char drawingCharacter) {
        this.drawingCharacter = String.valueOf(drawingCharacter);
        directions.put(Direction.UP, up);
        directions.put(Direction.DOWN, down);
        directions.put(Direction.LEFT, left);
        directions.put(Direction.RIGHT, right);
    }

    public boolean isIntersection() {
        return drawingCharacter.equals(String.valueOf('+'));
        //int numDirections = directions.values().stream().mapToInt(value -> value ? 1 : 0).sum();
        //return numDirections > 2;
    }

    public boolean isCorner() {
        return drawingCharacter.equals(String.valueOf('/')) ||
                drawingCharacter.equals(String.valueOf('\\'));
    }

    public boolean isVertical() {
        return drawingCharacter.equals(String.valueOf('|'));
    }

    public boolean isHorizontal() {
        return drawingCharacter.equals(String.valueOf('-'));
    }
}
