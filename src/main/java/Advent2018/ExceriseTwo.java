package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ExceriseTwo extends ExceriseBase {

    public void process() throws IOException {
        List<String> lines = readFile("/exceriseTwo.txt");

        int twoCharOccurances = 0;
        int threeCharOccurances = 0;

        for (String line : lines) {
            HashMap<Character, Integer> characterOccurances = new HashMap<>();
            for (char c : line.toCharArray()) {
                characterOccurances.merge(c, 1, Integer::sum);
            }

            if (characterOccurances.values().contains(2)) {
                twoCharOccurances++;
            }
            if (characterOccurances.values().contains(3)) {
                threeCharOccurances++;
            }
        }

        int total = twoCharOccurances * threeCharOccurances;
        System.out.println(total);

    }

    public void partTwo() throws IOException {
        List<String> lines = readFile("/exceriseTwo.txt");

        for (String line : lines) {
            for (String comparisonLine : lines) {

                int differences = 0;
                char[] lineChars = line.toCharArray();
                char[] comparisonLineChars = comparisonLine.toCharArray();

                for (int i = 0; i < line.length(); i++) {
                    if (lineChars[i] != comparisonLineChars[i]) {
                        differences++;
                    }
                }
                if (differences == 1) {
                    System.out.println(line + " " + comparisonLine);
                }
            }
        }
    }

}
