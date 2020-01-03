package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ExceriseSix extends ExceriseBase {

    private String ids = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int newIdNumber = 0;
    private Grid grid;

    public void process(String path, int distance) throws IOException {
        List<String> lines = readFile(path);

        List<StartingPoint> points = lines.stream().map(StartingPoint::new).collect(Collectors.toList());

        StartingPoint minx = points.stream().min(Comparator.comparingInt(StartingPoint::getStartx)).get();
        StartingPoint maxx = points.stream().max(Comparator.comparingInt(StartingPoint::getStartx)).get();
        StartingPoint miny = points.stream().min(Comparator.comparingInt(StartingPoint::getStarty)).get();
        StartingPoint maxy = points.stream().max(Comparator.comparingInt(StartingPoint::getStarty)).get();

        grid = new Grid(minx.startx, maxx.startx, miny.starty, maxy.starty);

        grid.populateFromPoints(points);

        Map<StartingPoint, Integer> areas = grid.findAreas();
        Set<StartingPoint> infinitePoints = grid.infinitePoints();

        Map.Entry<StartingPoint, Integer> biggestNonInfiniteArea = areas.entrySet().stream()
                .filter(entry -> !infinitePoints.contains(entry.getKey()))
                .max(Comparator.comparing(Map.Entry::getValue))
                .get();

        System.out.println(biggestNonInfiniteArea.getKey().id + ": " + biggestNonInfiniteArea.getValue());

        //-----

        List<GridMark> marksInDistance = Arrays.stream(grid.marks)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(mark -> mark.allPointsBelowDistance(distance))
                .collect(Collectors.toList());

        System.out.println(marksInDistance.size());
    }

    public String draw() {
        return grid.draw();
    }


    public class StartingPoint {

        int startx;
        int starty;
        String id;

        public StartingPoint(String line) {
            String[] split = line.split(", ");

            this.startx = Integer.parseInt(split[0]);
            this.starty = Integer.parseInt(split[1]);

            this.id = String.valueOf(ids.charAt(newIdNumber++));
        }

        public int getStartx() {
            return startx;
        }

        public int getStarty() {
            return starty;
        }
    }

    public class Grid {

        int minx;
        int maxx;
        int miny;
        int maxy;

        GridMark[][] marks;

        public Grid(int minx, int maxx, int miny, int maxy) {
            this.minx = minx - 1;
            this.maxx = maxx + 2;
            this.miny = miny - 1;
            this.maxy = maxy + 2;

            this.marks = new GridMark[this.maxx][this.maxy];
        }

        public void addMark(StartingPoint point, int x, int y) {
            int distance = Math.abs(x - point.startx) + Math.abs(y - point.starty);
            if (marks[x][y] == null) {
                marks[x][y] = new GridMark(point, x, y, distance);
            } else if (distance < marks[x][y].distance) {
                marks[x][y].startingPoints = point;
                marks[x][y].distance = distance;
                marks[x][y].equidistance = false;
            }
            if (marks[x][y].startingPoints != point && marks[x][y].distance == distance) {
                marks[x][y].equidistance = true;
            }

            marks[x][y].visit(point, distance);
        }

        public Map<StartingPoint, Integer> findAreas() {
            Map<StartingPoint, Integer> areaByPoint = new HashMap<>();

            for (int y = miny; y < maxy; y++) {
                for (int x = minx; x < maxx; x++) {
                    if (!marks[x][y].equidistance) {
                        areaByPoint.merge(marks[x][y].startingPoints, 1, Integer::sum);
                    }
                }
            }

            return areaByPoint;
        }

        public Set<StartingPoint> infinitePoints() {
            Set<StartingPoint> pointsOnBorder = new HashSet<>();

            for (int x = minx; x < maxx; x++) {
                //topEdge
                addIfNotEquidistance(pointsOnBorder, miny, x);
                //bottomEdge
                addIfNotEquidistance(pointsOnBorder, maxy - 1, x);
            }
            for (int y = miny; y < maxy; y++) {
                //leftEdge
                addIfNotEquidistance(pointsOnBorder, y, minx);
                //rightEdge
                addIfNotEquidistance(pointsOnBorder, y, maxx - 1);

            }
            return pointsOnBorder;
        }

        private void addIfNotEquidistance(Set<StartingPoint> pointsOnBorder, int y, int minx) {
            if (!marks[minx][y].equidistance) {
                pointsOnBorder.add(marks[minx][y].startingPoints);
            }
        }

        public String draw() {
            StringBuilder printString = new StringBuilder();

            for (int y = miny; y < maxy; y++) {
                for (int x = minx; x < maxx; x++) {
                    if (marks[x][y] == null) {
                        printString.append("#");
                    } else if (marks[x][y].equidistance) {
                        printString.append(".");
                    } else {
                        printString.append(marks[x][y].distance != 0 ? marks[x][y].startingPoints.id : marks[x][y].startingPoints.id.toUpperCase());
                        //printString.append(marks[x][y].distance != 0 ? marks[x][y].distance : marks[x][y].startingPoints.id);
                    }
                }
                printString.append("\n");
            }

            System.out.println(printString.toString());
            return printString.toString();
        }

        public void populateFromPoints(List<StartingPoint> points) {
            for (StartingPoint point : points) {
                for (int y = miny; y < maxy; y++) {
                    for (int x = minx; x < maxx; x++) {
                        addMark(point, x, y);
                    }
                }
            }
        }
    }

    public class GridMark {

        int distance;
        StartingPoint startingPoints;
        int x;
        int y;
        boolean equidistance = false;
        Map<StartingPoint, Integer> pointsByDistance = new HashMap<>();

        public GridMark(StartingPoint point, int x, int y, int distance) {
            this.distance = distance;
            this.startingPoints = point;
            this.x = x;
            this.y = y;
        }

        public void visit(StartingPoint point, int distance) {
            pointsByDistance.put(point, distance);
        }

        public boolean allPointsBelowDistance(int distance) {
            int totalDistance = 0;
            for (int value : pointsByDistance.values()) {
                totalDistance += value;
            }
            return totalDistance < distance;
        }
    }

}
