package Advent2018;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExceriseOne extends ExceriseBase {

    private Map<Integer, Integer> numberOccurance;
    private int total;
    private boolean firstDoubleOccuranceFound = false;

    public void sumFile() throws IOException {
        numberOccurance = new HashMap<>();
        total = 0;
        firstDoubleOccuranceFound = false;
        List<String> lines = readFile("/exceriseOne.txt");
        int lineSum = lines.stream().mapToInt(Integer::parseInt).map(this::processNumber).sum();

        int iterations = 0;
        while (!firstDoubleOccuranceFound) {
            iterations++;
            lines.stream().mapToInt(Integer::parseInt).map(this::processNumber).sum();
            System.out.println(lineSum);
        }
        System.out.println(iterations);
    }

    private int processNumber(int number) {
        total += number;

        numberOccurance.merge(total, 1, Integer::sum);
        if (!firstDoubleOccuranceFound && numberOccurance.get(total) == 2) {
            System.out.println("First double occurance is " + total);
            firstDoubleOccuranceFound = true;
        }
        return number;
    }
}
