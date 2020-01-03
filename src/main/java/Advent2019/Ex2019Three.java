package Advent2019;

import utils.Direction;
import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Ex2019Three extends ExceriseBase {

    Set<Point> visitedPoints = new HashSet<>();
    Set<Point> visitedPointsTwo = new HashSet<>();
    private int offsetX;
    private int offsetY;

    public void process(String path) throws IOException {
        List<String> lines = readFile(path);

        for (int i = 0; i < lines.size() / 2; i ++) {

            visitedPoints = new HashSet<>();
            visitedPointsTwo = new HashSet<>();

            List<Move> movesOne = Arrays.stream(lines.get(i * 2).split(",")).map(Move::new).collect(Collectors.toList());
            List<Move> movesTwo = Arrays.stream(lines.get((i * 2) + 1).split(",")).map(Move::new).collect(Collectors.toList());

            offsetX = 0;
            offsetY = 0;

            createSets(movesOne, movesTwo);
            List<Point> intersections = findIntersections(visitedPoints, visitedPointsTwo);
            Point closestPoint = findClosestPoint(intersections, new Point(offsetX, offsetY, 0));
            System.out.println(closestPoint);
            System.out.println(closestPoint.distanceTo(new Point(offsetX, offsetY, 0)));
            Point shortestSteps = findShortestSteps(intersections);
            System.out.println(shortestSteps);
            System.out.println(shortestSteps.steps);
        }

    }

    private List<Point> findIntersections(Set<Point> visitedPoints, Set<Point> visitedPointsTwo) {
        List<Point> intersections = new ArrayList<>();
        for (Point p : visitedPoints) {
            if (visitedPointsTwo.contains(p)) {
                Point otherPoint = getPointFromSet(visitedPointsTwo, p);
                intersections.add(new Point(p.x, p.y, p.stepsWith(otherPoint)));
            }
        }
        return intersections;
    }

    private Point getPointFromSet(Set<Point> setPoints, Point searchedPoint) {
        for (Point p : setPoints) {
            if (p.equals(searchedPoint)) {
                return p;
            }
        }
        return null;
    }

    private Point findClosestPoint(List<Point> intersections, Point point) {
        int minDistance = Integer.MAX_VALUE;
        Point closestPoint = null;
        for (Point intersectingPoint : intersections) {
            int distance = intersectingPoint.distanceTo(point);
            if (distance < minDistance) {
                closestPoint = intersectingPoint;
                minDistance = distance;
            }
        }
        return closestPoint;

    }

    private Point findShortestSteps(List<Point> intersections) {
        int minDistance = Integer.MAX_VALUE;
        Point shortestSteps = null;
        for (Point intersectingPoint : intersections) {
            int steps = intersectingPoint.steps;
            if (steps < minDistance) {
                shortestSteps = intersectingPoint;
                minDistance = steps;
            }
        }
        return shortestSteps;

    }

    private List<Point> createSets(List<Move> movesOne, List<Move> movesTwo) {
        List<Point> intersections = new ArrayList<>();
        int steps = 0;

        Point currentPosition = new Point(offsetX, offsetY, 0);
        for (Move move : movesOne) {
            currentPosition = moveInDirection(currentPosition, move, visitedPoints);
        }

        currentPosition = new Point(offsetX, offsetY, 0);
        for (Move move : movesTwo) {
            currentPosition = moveInDirection(currentPosition, move, visitedPointsTwo);
        }

        return intersections;
    }

    private Point moveInDirection(Point startingPoint, Move move, Set<Point> setPoints) {
        int startingX = startingPoint.x;
        int startingY = startingPoint.y;
        int currentSteps = startingPoint.steps;

        switch (move.direction) {
            case UP:
                for (int y = startingY - 1; y >= startingY - move.distance; y--) {
                    currentSteps++;
                    setPoints.add(new Point(startingX , y, currentSteps));
                }
                return new Point(startingX, startingY - move.distance, currentSteps);
            case DOWN:
                for (int y = startingY + 1; y <= startingY + move.distance; y++) {
                    currentSteps++;
                    setPoints.add(new Point(startingX , y, currentSteps));
                }
                return new Point(startingX, startingY + move.distance, currentSteps);
            case LEFT:
                for (int x = startingX - 1; x >= startingX - move.distance; x--) {
                    currentSteps++;
                    setPoints.add(new Point(x , startingY, currentSteps));
                }
                return new Point(startingX - move.distance, startingY, currentSteps);
            case RIGHT:
                for (int x = startingX + 1; x <= startingX + move.distance; x++) {
                    currentSteps++;
                    setPoints.add(new Point(x , startingY, currentSteps));
                }
                return new Point(startingX + move.distance, startingY, currentSteps);
        }
        throw new RuntimeException("Unknown Direction " + move.direction);
    }

    public class Move {

        Direction direction;
        int distance;

        public Move(String input) {
            switch (input.charAt(0)) {
                case 'U':
                    this.direction = Direction.UP;
                    break;
                case 'D':
                    this.direction = Direction.DOWN;
                    break;
                case 'L':
                    this.direction = Direction.LEFT;
                    break;
                case 'R':
                    this.direction = Direction.RIGHT;
                    break;
            }
            this.distance = Integer.parseInt(input.substring(1));
        }

        public Move(Direction direction, int distance) {
            this.direction = direction;
            this.distance = distance;
        }
    }

    public class Point {

        final int x;
        final int y;
        final int steps;

        public Point(int x, int y, int steps) {
            this.x = x;
            this.y = y;
            this.steps = steps;
        }

        public int distanceTo(Point point) {
            return Math.abs(point.x - this.x) + Math.abs(point.y - this.y);
        }

        public int stepsWith(Point point) {
            return this.steps + point.steps;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }


}
