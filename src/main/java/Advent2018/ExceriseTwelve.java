package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceriseTwelve extends ExceriseBase {

    Pattern startingRegex = Pattern.compile("initial state: ([.#]+)");
    Pattern stateRegex = Pattern.compile("([.#]+) => ([.#])");

    public void process(String path, long generations) throws IOException {
        List<String> lines = readFile(path);
        List<Rule> rules = new ArrayList<>();

        PlantRow startingPlantRow = toPlantRow(getStartingInput(lines.get(0)));
        startingPlantRow.print();

        for (int i = 2; i < lines.size(); i++) {
            rules.add(getRule(lines.get(i)));
        }

        PlantRow currentPlantRow = startingPlantRow;
        for (long gen = 1L; gen <= generations; gen++ ) {
            currentPlantRow = applyRules(currentPlantRow, rules, gen);
            //if (gen % 100000 == 0) {
                currentPlantRow.print();
            //}
        }

        System.out.println(currentPlantRow.score());
    }

    private PlantRow applyRules(PlantRow plantRow, List<Rule> rules, long gen) {
        List<Boolean> newPlantRow = new ArrayList<>();

        int newStartIndex = plantRow.startIndex - 2;
        for (int i = newStartIndex; i < plantRow.startIndex + plantRow.plants.size() + 2; i++) {

            boolean ruleMatched = isAnyRuleMatched(plantRow, rules, i);
            newPlantRow.add(ruleMatched);
        }

        boolean foundStart = false;
        while (!foundStart) {
            if (newPlantRow.get(0)) {
                foundStart = true;
            } else {
                newPlantRow.remove(0);
                newStartIndex++;
            }
        }

        for (int i = newPlantRow.size() - 1; i >= 0; i--) {
            if (newPlantRow.get(i)) {
                newPlantRow = newPlantRow.subList(0, i + 1);
                break;
            }
        }

        return new PlantRow(newPlantRow, newStartIndex, gen);

    }

    private boolean isAnyRuleMatched(PlantRow plantRow, List<Rule> rules, int i) {
        boolean[] currentState = new boolean[5];
        currentState[0] = plantRow.plantAt(i - 2);
        currentState[1] = plantRow.plantAt(i - 1);
        currentState[2] = plantRow.plantAt(i);
        currentState[3] = plantRow.plantAt(i + 1);
        currentState[4] = plantRow.plantAt(i + 2);

        for (Rule r : rules) {
            if (Arrays.equals(r.expectedState, currentState)) {
                return r.endState;
            }
        }
        return false;
    }

    private Rule getRule(String input) {
        Matcher matcher = stateRegex.matcher(input);
        matcher.find();
        MatchResult matchResult = matcher.toMatchResult();
        return new Rule(matchResult.group(1), matchResult.group(2));
    }


    public MatchResult getStartingInput(String line) {
        Matcher matcher = startingRegex.matcher(line);
        matcher.find();
        return matcher.toMatchResult();
    }

    private PlantRow toPlantRow(MatchResult result) {
        String plantString = result.group(1);
        return new PlantRow(plantString);
    }

    private class PlantRow {

        List<Boolean> plants = new ArrayList<>();
        int startIndex = 0;

        private long gen;

        public PlantRow(List<Boolean> plants, int startIndex, long gen) {
            this.plants = plants;
            this.startIndex = startIndex;
            this.gen = gen;
        }

        public PlantRow(String input) {
            for (char c : input.toCharArray()) {
                plants.add(c == '#');
            }
        }

        public void print() {
            System.out.print(gen + ", " + startIndex + ": ");
            for (boolean b : plants) {
                System.out.print(b ? '#' : ".");
            }
            System.out.print("\n");
        }

        public boolean plantAt(int index) {
            if (index < startIndex || index >= startIndex + plants.size()) {
                return false;
            }
            return plants.get(index - startIndex);
        }

        public int score() {
            int sum = 0;
            for (int i = 0; i < plants.size(); i++) {
                if (plants.get(i)) {
                    sum += startIndex + i;
                }
            }
            return sum;
        }
    }

    private class Rule {

        boolean[] expectedState;
        boolean endState;

        public Rule(String input, String endState) {
            char[] inputChars = input.toCharArray();
            expectedState = new boolean[inputChars.length];
            for (int i = 0; i < inputChars.length; i++) {
                expectedState[i] = inputChars[i] == '#';
            }

            this.endState = endState.equals("#");
        }
    }


}
