package Advent2018.Ex13;

import utils.Direction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Cart implements Comparable {

    int x;
    int y;
    Direction direction;
    boolean crashed = false;

    List<Direction> turningDirections = Arrays.asList(Direction.LEFT, Direction.STRAIGHT, Direction.RIGHT);
    int turningIndex = 0;

    public static boolean isCartCharacter(char c) {
        return (c == '^' || c == 'v' || c == '<' || c == '>');
    }

    public static String drawingChacter(Cart cart) {
        if (cart.direction == Direction.UP) {
            return "^";
        }
        if (cart.direction == Direction.DOWN) {
            return "v";
        }
        if (cart.direction == Direction.LEFT) {
            return "<";
        }
        if (cart.direction == Direction.RIGHT) {
            return ">";
        }
        return "#";
    }

    public static Cart ofCharacter(char c, int x, int y) {
        switch (c) {
            case '^':
                return new Cart(x, y, Direction.UP);
            case 'v':
                return new Cart(x, y, Direction.DOWN);
            case '<':
                return new Cart(x, y, Direction.LEFT);
            case '>':
                return new Cart(x, y, Direction.RIGHT);
            default:
                throw new IllegalArgumentException("Not a value cart character " + c);
        }
    }

    public Cart(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void move(Track[][] tracks) {
        if (tracks[y][x].isIntersection()) {
            moveIntersection();
        } else if (tracks[y][x].isCorner()) {
            Direction directionToMove = tracks[y][x].directions.entrySet().stream()
                    .filter(d -> d.getKey() != direction.getOpposite())
                    .filter(Map.Entry::getValue)
                    .findFirst().get().getKey();

            moveCartInDirection(directionToMove);
        } else {
            moveCartInDirection(direction);
        }
    }

    private void moveIntersection() {
        Direction turnDirection = turningDirections.get(turningIndex);
        turningIndex = (turningIndex + 1) % turningDirections.size();

        switch (turnDirection) {
            case STRAIGHT:
                moveCartInDirection(direction);
                break;
            case LEFT:
                moveCartInDirection(direction.getRelativeLeft());
                break;
            case RIGHT:
                moveCartInDirection(direction.getRelativeRight());
                break;
        }
    }

    private void moveCartInDirection(Direction moveTowards) {
        this.direction = moveTowards;
        switch (moveTowards) {
            case UP:
                y -= 1;
                break;
            case DOWN:
                y += 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
        }
    }

    @Override
    public int compareTo(Object o) {
        Cart other = (Cart) o;

        int yCompare = Integer.compare(this.y, other.y);
        if (yCompare == 0) {
            return Integer.compare(this.x, other.x);
        } else return yCompare;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return !crashed;
    }

    public void setCrashed() {
        this.crashed = true;
    }
}

