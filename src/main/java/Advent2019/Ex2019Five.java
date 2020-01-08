package Advent2019;

import Advent2019.computer.Computer;
import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ex2019Five extends ExceriseBase {

    Computer computer = new Computer();

    public List<Integer> process(String path, int input) throws IOException {
        List<String> lines = readFile(path);

        List<Integer> outputs = new ArrayList<>();

        for (String line : lines) {
            List<Integer> intOps = Computer.intOpsFromInput(line);

            outputs.addAll(computer.processProgram(intOps, Collections.singletonList(input)));
            System.out.println(intOps);
        }

        return outputs;
    }

}
