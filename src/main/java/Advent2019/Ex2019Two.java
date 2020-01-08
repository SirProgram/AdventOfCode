package Advent2019;

import Advent2019.computer.Computer;
import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ex2019Two extends ExceriseBase {

    Computer computer = new Computer();

    public void process(String path) throws IOException {
        List<String> lines = readFile(path);

        for (String line : lines) {
            List<Integer> intOps = Computer.intOpsFromInput(line);

            int processingIndex = 0;

            while (processingIndex >= 0) {
                processingIndex = computer.processIntOps(intOps, processingIndex).getNextProcessingInput();
            }

            System.out.println(intOps);
            System.out.println(intOps.get(0));
        }

    }

    public void processB(String path) throws IOException {
        List<String> lines = readFile(path);

        List<Integer> intOps = Computer.intOpsFromInput(lines.get(0));

        for (int firstValue = 0; firstValue <= 99; firstValue++) {
            for (int secondValue = 0; secondValue <= 99; secondValue++) {
                List<Integer> intOpsCopy = new ArrayList<>(intOps);
                intOpsCopy.set(1, firstValue);
                intOpsCopy.set(2, secondValue);

                int processingIndex = 0;

                while (processingIndex >= 0) {
                    processingIndex = computer.processIntOps(intOpsCopy, processingIndex).getNextProcessingInput();
                }

                if (intOpsCopy.get(0) == 19690720) {
                    System.out.println(intOpsCopy);
                    System.out.println(intOpsCopy.get(1));
                    System.out.println(intOpsCopy.get(2));
                }
            }
        }


    }

}
