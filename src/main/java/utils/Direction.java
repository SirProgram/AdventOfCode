package utils;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    STRAIGHT;

    private Direction left;
    private Direction opposite;
    private Direction right;

    static {
        UP.opposite = DOWN;
        DOWN.opposite = UP;
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;

        UP.left = LEFT;
        DOWN.left = RIGHT;
        LEFT.left = DOWN;
        RIGHT.left = UP;

        UP.right = RIGHT;
        DOWN.right = LEFT;
        LEFT.right = UP;
        RIGHT.right = DOWN;
    }

    public Direction getOpposite() {
        return this.opposite;
    }

    public Direction getRelativeLeft() {
        return this.left;
    }

    public Direction getRelativeRight() {
        return this.right;
    }

}
