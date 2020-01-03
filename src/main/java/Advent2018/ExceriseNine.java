package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceriseNine extends ExceriseBase {

    Pattern inputRegex = Pattern.compile("(\\d+) players; last marble is worth (\\d+)");

    public void process(String path) throws IOException {
        List<String> lines = readFile(path);

        for (String line : lines) {
            Input input = toInput(getNextInput(line));
            //Input input = toInput(getNextInput(lines.get(0)));

            Players players = new Players(input);

            Marble startingMarble = new Marble(0);
            startingMarble.next = startingMarble;
            startingMarble.prev = startingMarble;

            Marble nextMarble = startingMarble;
            long marbleValue = 1L;
            while (marbleValue < input.lastMarble) {
                nextMarble = playTurn(nextMarble, marbleValue, players.getCurrentPlayer());
                marbleValue++;
                players.nextPlayer();
            }

            printGame(startingMarble);
            System.out.println();
            System.out.println(players.getMaxScore());
        }

    }

    private void printGame(Marble startingMarble) {
        System.out.print(startingMarble.score + " ");
        Marble currentMarble = startingMarble.next;
        while (currentMarble != startingMarble) {
            System.out.print(currentMarble.score + " ");
            currentMarble = currentMarble.next;
        }
    }

    private Marble playTurn(Marble currentMarble, long newMarbleValue, Player currentPlayer) {
        if (newMarbleValue % 23 != 0) {
            Marble newMarble = new Marble(newMarbleValue);
            currentMarble.next.addNextMarble(newMarble);
            return newMarble;
        } else {
            Marble toRemove = currentMarble.prev.prev.prev.prev.prev.prev.prev;
            toRemove.prev.setNextMarble(toRemove.next);
            currentPlayer.score += newMarbleValue;
            currentPlayer.score += toRemove.score;
            return toRemove.next;
        }

    }

    public MatchResult getNextInput(String line) {
        Matcher matcher = inputRegex.matcher(line);
        matcher.find();
        return matcher.toMatchResult();
    }

    private Input toInput(MatchResult result) {
        int numPlayers = Integer.parseInt(result.group(1));
        int lastMarble = Integer.parseInt(result.group(2));

        return new Input(numPlayers, lastMarble);
    }

    private class Marble {
        Marble prev;
        Marble next;

        long score;

        public Marble(long score) {
            this.score = score;
        }

        public void addNextMarble(Marble newMarble) {
            Marble oldNext = next;

            oldNext.prev = newMarble;
            this.next = newMarble;

            newMarble.next = oldNext;
            newMarble.prev = this;
        }

        public void setNextMarble(Marble nextMarble) {
            this.next = nextMarble;
            nextMarble.prev = this;
        }
    }

    private class Player {
        int id;
        long score = 0;

        public Player(int id) {
            this.id = id;
        }

        public long getScore() {
            return score;
        }
    }

    private class Input {

        int numPlayers;
        long lastMarble;

        public Input(int numPlayers, int lastMarble) {
            this.numPlayers = numPlayers;
            this.lastMarble = lastMarble;
        }
    }

    private class Players {
        private List<Player> players;
        int currentPlayer = 0;

        public Players(Input input) {
            players = new ArrayList<>();

            for (int i = 0; i < input.numPlayers; i++) {
                Player p = new Player(i);
                players.add(p);
            }
        }

        public List<Player> getPlayers() {
            return players;
        }

        public Player getCurrentPlayer() {
            return players.get(currentPlayer);
        }

        public void nextPlayer() {
            currentPlayer++;
            currentPlayer = currentPlayer % players.size();
        }

        public long getMaxScore() {
            return players.stream().mapToLong(Player::getScore).max().getAsLong();
        }
    }

}
