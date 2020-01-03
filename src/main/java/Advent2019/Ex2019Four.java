package Advent2019;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ex2019Four extends ExceriseBase {

    public void process(String path) throws IOException {
        List<String> lines = readFile(path);

        String[] split = lines.get(0).split("-");
        int startNumber = Integer.parseInt(split[0]);
        int endNumber = Integer.parseInt(split[1]);

        List<Integer> matchingNumbers = new ArrayList<>();
        List<Integer> matchingNumbersB = new ArrayList<>();

        for (int i = startNumber; i <= endNumber; i++) {
            if (hasDouble(i) && hasIncreasingDigits(i)) {
                matchingNumbers.add(i);
            }
        }
        for (int i = startNumber; i <= endNumber; i++) {
            if (hasOnlyDouble(i) && hasIncreasingDigits(i)) {
                matchingNumbersB.add(i);
            }
        }

        System.out.println(matchingNumbers.size());
        System.out.println(matchingNumbersB.size());

        //1202 too high
        //818 too low
    }

    public boolean hasDouble(int number) {
        char[] digits = String.valueOf(number).toCharArray();

        for (int index = 0; index < digits.length - 1; index++) {
            if (digits[index] == digits[index + 1]) {
                return true;
            }
        }
        return false;
    }

    public boolean hasOnlyDouble(int number) {
        char[] digits = String.valueOf(number).toCharArray();

        for (int index = 0; index < digits.length - 1; index++) {
            //Check 1 ahead
            if (digits[index] == digits[index + 1]) {
                //Check 2 ahead
                if (index + 2 < digits.length) {
                    if (digits[index + 2] == digits[index]) {
                        continue;
                    }
                }
                //Check 1 behind
                if (index - 1 >= 0) {
                    if (digits[index -1] == digits[index]) {
                        continue;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean hasIncreasingDigits(int number) {
        char[] digits = String.valueOf(number).toCharArray();

        int currentMin = 0;
        for (int index = 0; index < digits.length; index++) {
            if (digits[index] >= currentMin) {
                currentMin = digits[index];
            } else {
                return false;
            }
        }
        return true;
    }

}
