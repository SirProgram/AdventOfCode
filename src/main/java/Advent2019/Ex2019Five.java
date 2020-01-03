package Advent2019;

import Advent2019.computer.Computer;
import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ex2019Five extends ExceriseBase {

    Computer computer = new Computer();

    public List<Integer> process(String path, int input) throws IOException {
        List<String> lines = readFile(path);

        List<Integer> outputs = new ArrayList<>();

        for (String line : lines) {
            List<Integer> intOps = computer.intOpsFromInput(line);

            int processingIndex = 0;

            while (processingIndex >= 0) {
                Computer.Output result = computer.processIntOps(intOps, processingIndex, input);
                processingIndex = result.getNextProcessingInput();
                //System.out.println(intOps);
                result.getOutput().ifPresent(System.out::println);
                result.getOutput().ifPresent(outputs::add);
            }

            System.out.println(intOps);
        }

        return outputs;
    }

}
