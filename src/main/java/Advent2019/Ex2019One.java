package Advent2019;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.List;

public class Ex2019One extends ExceriseBase {

    public void process(String path) throws IOException {
        List<String> lines = readFile(path);

        int sum = lines.stream().mapToInt(Integer::parseInt).map(this::calculateFuel).sum();
        System.out.println(sum);

    }

    public void processB(String path) throws IOException {
        List<String> lines = readFile(path);

        int sum = lines.stream().mapToInt(Integer::parseInt).map(this::calculateFuelOfFuel).sum();
        System.out.println(sum);

    }

    private int calculateFuel(int inputValue) {
        return (inputValue / 3) - 2;
    }

    private int calculateFuelOfFuel(int inputValue) {
        int fuelTotal = 0;
        int workingValue = inputValue;
        while (workingValue >= 9) {
            int result = calculateFuel(workingValue);
            fuelTotal += result;
            workingValue = result;
            System.out.println(result);
        }
        //fuelTotal += workingValue;
        return fuelTotal;
    }

}
