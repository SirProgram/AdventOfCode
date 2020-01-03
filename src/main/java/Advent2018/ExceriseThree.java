package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.List;

public class ExceriseThree extends ExceriseBase {

    private Claim[][] grid;

    public void process() throws IOException {
        List<String> lines = readFile("/exceriseThree.txt");

        grid = new Claim[1000][1000];

        lines.stream().map(Claim::new).forEach(this::storeClaim);

        int total = 0;
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                if (grid[x][y] != null && grid[x][y].clashed) {
                    total++;
                }
            }
        }

        System.out.println(total);
    }

    public void processTwo() throws IOException {
        List<String> lines = readFile("/exceriseThree.txt");

        grid = new Claim[1000][1000];

        lines.stream().map(Claim::new).forEach(this::storeClaimTwo);

        int total = 0;
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                if (grid[x][y] != null && !grid[x][y].clashed) {
                    System.out.println(grid[x][y].id);
                }
            }
        }

        System.out.println(total);
    }

    private void storeClaimTwo(Claim claim) {
        for (int x = 0; x < claim.width; x++) {
            for (int y = 0; y < claim.height; y++) {
                int currentx = claim.startx + x;
                int currenty = claim.starty + y;
                if (grid[currentx][currenty] != null) {
                    grid[currentx][currenty].clashed = true;
                    claim.clashed = true;
                }
                grid[currentx][currenty] = claim;
            }
        }
    }

    private void storeClaim(Claim claim) {
        for (int x = 0; x < claim.width; x++) {
            for (int y = 0; y < claim.height; y++) {
                int currentx = claim.startx + x;
                int currenty = claim.starty + y;
                if (grid[currentx][currenty] != null) {
                    grid[currentx][currenty].clashed = true;
                } else {
                    grid[currentx][currenty] = claim.clone();
                }
            }
        }
    }

    private class Claim implements Cloneable {

        String id;
        int startx;
        int starty;
        int width;
        int height;
        boolean clashed = false;

        public Claim(String input) {
            String[] inputs = input.split(" ");
            id = inputs[0];
            String start = inputs[2];
            startx = Integer.parseInt(start.substring(0, start.indexOf(",")));
            starty = Integer.parseInt(start.substring(start.indexOf(",") + 1, start.indexOf(":")));
            String size = inputs[3];
            width = Integer.parseInt(size.substring(0, size.indexOf("x")));
            height = Integer.parseInt(size.substring(size.indexOf("x") + 1));
        }

        public Claim clone() {
            return new Claim(this);
        }

        public Claim(Claim claim) {
            this.id = claim.id;
            this.startx = claim.startx;
            this.starty = claim.starty;
            this.width = claim.width;
            this.height = claim.height;
            this.clashed = claim.clashed;
        }

    }

}
