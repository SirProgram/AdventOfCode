package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExceriseTen extends ExceriseBase {

    Pattern inputRegex = Pattern.compile("position=<([0-9 \\-]+),([0-9 \\-]+)> velocity=<([0-9 \\-]+),([0-9 \\-]+)>");

    public void process(String path) throws IOException {
        List<String> lines = readFile(path);

        List<Point> points = lines.stream()
                .map(this::getNextInput)
                .map(this::toPoint)
                .sorted()
                .collect(Collectors.toList());

        int minX = points.stream().mapToInt(p -> p.x).min().getAsInt();
        int maxX = points.stream().mapToInt(p -> p.x).max().getAsInt();
        int minY = points.stream().mapToInt(p -> p.y).min().getAsInt();
        int maxY = points.stream().mapToInt(p -> p.y).max().getAsInt();

        int minHeight = 999999;
        int height = maxY - minY;
        int count = 0;
        //drawPoints(points, minX, maxX, minY, maxY);
        while (height <= minHeight) {
            movePoints(points);
            count++;
            minX = points.stream().mapToInt(p -> p.x).min().getAsInt();
            maxX = points.stream().mapToInt(p -> p.x).max().getAsInt();
            minY = points.stream().mapToInt(p -> p.y).min().getAsInt();
            maxY = points.stream().mapToInt(p -> p.y).max().getAsInt();
            height = maxY - minY;

            if (height < minHeight) {
                minHeight = height;
            }
            //drawPoints(points, minX, maxX, minY, maxY);
        }

        points.forEach(Point::movePointBack);
        count--;
        Collections.sort(points);
        drawPoints(points, minX, maxX, minY, maxY);
        System.out.println(count);
    }

    private void movePoints(List<Point> points) {
        points.forEach(Point::movePoint);
    }

    public void drawPoints(List<Point> points, int minX, int maxX, int minY, int maxY) {

        int lastx = minX;
        int lasty = minY;

        for (Point p : points) {
            if (p.y > lasty) {
                //Complete Line
                completeLine(lastx, maxX);
                printLine(minX, p);
            } else {
                printLine(lastx, p);
            }

            lastx = p.x + 1;
            lasty = p.y;
        }
        completeLine(lastx, maxX);
    }

    private void completeLine(int startingX, int maxX) {
        for (int x = startingX; x <= maxX; x++) {
            System.out.print(".");
        }
        //New Line
        System.out.print("\n");
    }

    private void printLine(int startingX, Point p) {
        for (int x = startingX; x < p.x; x++) {
            System.out.print(".");
        }
        if (startingX <= p.x) {
            System.out.print("#");
        }
    }

    public MatchResult getNextInput(String line) {
        Matcher matcher = inputRegex.matcher(line);
        matcher.find();
        return matcher.toMatchResult();
    }

    private Point toPoint(MatchResult result) {
        int x = Integer.parseInt(result.group(1).trim());
        int y = Integer.parseInt(result.group(2).trim());
        int xvelocity = Integer.parseInt(result.group(3).trim());
        int yvelocity = Integer.parseInt(result.group(4).trim());

        return new Point(x, y, xvelocity, yvelocity);
    }

    private class Point implements Comparable {

        private int x;
        private int y;
        private int xvelocity;
        private int yvelocity;

        public Point(int x, int y, int xvelocity, int yvelocity) {
            this.x = x;
            this.y = y;
            this.xvelocity = xvelocity;
            this.yvelocity = yvelocity;
        }


        @Override
        public int compareTo(Object o) {
            Point other = (Point) o;

            int yCompare = Integer.compare(this.y, other.y);
            if (yCompare == 0) {
                return Integer.compare(this.x, other.x);
            } else return yCompare;
        }

        public void movePoint() {
            x += xvelocity;
            y += yvelocity;
        }

        public void movePointBack() {
            x -= xvelocity;
            y -= yvelocity;
        }
    }

}
