package Advent2018.Ex13;

import utils.ExceriseBase;
import utils.Direction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExceriseThirteen extends ExceriseBase {

    private Track[][] tracks; //Track[y][x]
    private List<Cart> carts = new ArrayList<>();
    private int trackHeight;
    private int trackWidth;

    public void process(String path) throws IOException, InterruptedException {
        List<String> lines = readFile(path);

        trackHeight = lines.size();
        trackWidth = lines.stream().mapToInt(String::length).max().getAsInt();

        tracks = new Track[trackHeight][trackWidth];
        buildCartsAndTracks(lines);
        draw();

        while (numberOfNonCrashedCarts() > 1) {
            moveCarts();
            //draw();
            //Thread.sleep(50);
        }

        Optional<Cart> aliveCart = carts.stream().filter(Cart::isAlive).findFirst();
        if (aliveCart.isPresent()) {
            System.out.println("Last Cart at " + new Position(aliveCart.get()));
        } else {
            System.out.println("All carts crashed!");
        }
    }

    private long numberOfNonCrashedCarts() {
        return carts.stream().filter(Cart::isAlive).count();
    }

    private void markCrashedCarts() {
        Map<Position, List<Cart>> cartsAtSamePosition = carts.stream().filter(Cart::isAlive).collect(Collectors.groupingBy(Position::new));

        for (Map.Entry<Position, List<Cart>> cartEntry : cartsAtSamePosition.entrySet()) {
            if (cartEntry.getValue().size() > 1) {
                System.out.println("Crash at " + cartEntry.getKey());
                for (Cart c : cartEntry.getValue()) {
                    c.setCrashed();
                }
            }
        }
    }

    private void moveCarts() {
        Collections.sort(carts);
        Iterator<Cart> cartIterator = carts.iterator();
        while (cartIterator.hasNext()) {
            Cart cart = cartIterator.next();
            cart.move(tracks);
            markCrashedCarts();
        }
    }

    private void buildCartsAndTracks(List<String> lines) {
        for (int y = 0; y < trackHeight; y++) {
            String trackLine = lines.get(y);
            for (int x = 0; x < trackWidth; x++) {
                char nextChar = trackLine.charAt(x);
                if (Cart.isCartCharacter(nextChar)) {
                    Cart newCart = Cart.ofCharacter(nextChar, x, y);
                    carts.add(newCart);
                }
                if (Track.ofCharacter(nextChar).isCorner()) {
                    if (y >= 1) {
                        tracks[y][x] = Track.ofCorner(nextChar, tracks[y - 1][x].directions.get(Direction.DOWN));
                    } else {
                        tracks[y][x] = Track.ofCorner(nextChar, false);
                    }
                } else {
                    tracks[y][x] = Track.ofCharacter(nextChar);
                }
            }
        }
    }

    public List<Cart> cartsAtPosition(int x, int y) {
        return carts.stream()
                .filter(cart -> cart.x == x)
                .filter(cart -> cart.y == y)
                .collect(Collectors.toList());
    }


    public void draw() {
        for (int y = 0; y < trackHeight; y++) {
            for (int x = 0; x < trackWidth; x++) {
                List<Cart> cartsAtPos = cartsAtPosition(x, y);
                if (cartsAtPos.size() > 0) {
                    System.out.print(Cart.drawingChacter(cartsAtPos.get(0)));
                } else {
                    System.out.print(tracks[y][x].drawingCharacter);
                }
            }
            System.out.print("\n");
        }
    }

    private class Position {
        int x;
        int y;

        public Position(Cart c) {
            this.x = c.getX();
            this.y = c.getY();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Position{" +
                    x +
                    "," +
                    y +
                    '}';
        }
    }
}
