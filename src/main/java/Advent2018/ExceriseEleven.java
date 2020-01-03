package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.List;

public class ExceriseEleven extends ExceriseBase {

    public void process(String path) throws IOException {
        int[][] grid = generateGrid(path);

        int maxGridPower = 0;
        int maxGridX = 0;
        int maxGridY = 0;

        for (int x = 0; x < 298; x++) {
            for (int y = 0; y < 298; y++) {
                int totalGridPower = powerSquare(grid, x, y, 3);
                if (totalGridPower > maxGridPower) {
                    maxGridPower = totalGridPower;
                    maxGridX = x;
                    maxGridY = y;
                }
            }
        }

        System.out.println(maxGridPower);
        System.out.println(maxGridX + "," + maxGridY);
    }

    public void processPartTwo(String path) throws IOException {
        int[][] grid = generateGrid(path);

        int maxGridPower = 0;
        int maxSquareSize = 1;
        int maxGridX = 0;
        int maxGridY = 0;

        for (int squareSize = 1; squareSize < 300; squareSize++) {
            System.out.println(squareSize + " " + String.format("%d", squareSize / 300 * 100) + "%");
            for (int x = 0; x < 298; x++) {
                for (int y = 0; y < 298; y++) {
                    int totalGridPower = powerSquare(grid, x, y, squareSize);
                    if (totalGridPower >= maxGridPower) {
                        maxSquareSize = squareSize;
                        maxGridPower = totalGridPower;
                        maxGridX = x;
                        maxGridY = y;
                    }
                }
            }
        }

        System.out.println(maxGridPower);
        System.out.println(maxGridX + "," + maxGridY + "," + maxSquareSize);
    }

    private int[][] generateGrid(String path) throws IOException {
        List<String> lines = readFile(path);
        int gridNumber = Integer.parseInt(lines.get(0));

        int[][] grid = new int[300][300];

        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 300; y++) {
                grid[y][x] = findPower(gridNumber, x, y);
            }
        }

        return grid;
    }

    private int findPower(int gridNumber, int x, int y) {
        int rackId = x + 10;
        int power = rackId * y;
        power += gridNumber;
        power *= rackId;
        //Keep Hundreds
        power %= 1000;
        power -= power % 100;
        power /= 100;

        power -= 5;

        return power;
    }

    public int powerSquare(int[][] powerGrid, int x, int y, int size) {
        if (x + size > 300 || y + size > 300) {
            return -1;
        }

        int totalPower = 0;
        for (int sizex = x; sizex < x + size; sizex++) {
            for (int sizey = y; sizey < y + size; sizey++) {
                totalPower += powerGrid[sizey][sizex];
            }
        }

        return totalPower;
        /*return powerGrid[y][x] + powerGrid[y][x + 1] + powerGrid[y][x + 2] +
                powerGrid[y + 1][x] + powerGrid[y + 1][x + 1] + powerGrid[y + 1][x + 2] +
                powerGrid[y + 2][x] + powerGrid[y + 2][x + 1] + powerGrid[y + 2][x + 2];*/
    }

}