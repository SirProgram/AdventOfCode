package Advent2019;

import Advent2019.computer.Computer;
import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ex2019Seven extends ExceriseBase {

    Computer computer = new Computer();

    public List<Integer> process(String path, int input) throws IOException {

        int max = 0;
        List<Integer> bestPhaseSettings = new ArrayList<>();
        for (List<Integer> phaseSettings : Permutations.permutations(Arrays.asList(0, 1, 2, 3, 4))) {
            int newOutput = process(path, input, phaseSettings).get(0);
            if (newOutput > max) {
                max = newOutput;
                bestPhaseSettings = phaseSettings;
            }
        }

        System.out.println(bestPhaseSettings);
        System.out.println(max);

        return Arrays.asList(max);
    }

    public List<Integer> process(String path, int input, List<Integer> phaseSettings) throws IOException {

        List<String> lines = readFile(path);

        List<Integer> intOpsA = Computer.intOpsFromInput(lines.get(0));

        List<Integer> inputs = new ArrayList<>();
        inputs.add(phaseSettings.get(0));
        inputs.add(input);
        List<Integer> outputA = computer.processProgram(intOpsA, inputs);
        System.out.println(outputA);

        List<Integer> intOpsB = Computer.intOpsFromInput(lines.get(0));
        inputs = new ArrayList<>();
        inputs.add(phaseSettings.get(1));
        inputs.add(outputA.get(0));
        List<Integer> outputB = computer.processProgram(intOpsB, inputs);
        System.out.println(outputB);

        List<Integer> intOpsC = Computer.intOpsFromInput(lines.get(0));
        inputs = new ArrayList<>();
        inputs.add(phaseSettings.get(2));
        inputs.add(outputB.get(0));
        List<Integer> outputC = computer.processProgram(intOpsC, inputs);
        System.out.println(outputC);

        List<Integer> intOpsD = Computer.intOpsFromInput(lines.get(0));
        inputs = new ArrayList<>();
        inputs.add(phaseSettings.get(3));
        inputs.add(outputC.get(0));
        List<Integer> outputD = computer.processProgram(intOpsD, inputs);
        System.out.println(outputD);

        List<Integer> intOpsE = Computer.intOpsFromInput(lines.get(0));
        inputs = new ArrayList<>();
        inputs.add(phaseSettings.get(4));
        inputs.add(outputD.get(0));
        List<Integer> outputE = computer.processProgram(intOpsE, inputs);
        System.out.println(outputE);

        return outputE;
    }

    public List<Integer> processB(String path, int input, List<Integer> phaseSettings) throws IOException {

        List<String> lines = readFile(path);

        List<Integer> intOpsA = Computer.intOpsFromInput(lines.get(0));


        List<Integer> inputs = new ArrayList<>();
        inputs.add(phaseSettings.get(0));
        inputs.add(input);
        List<Integer> outputA = computer.processProgramUntilOutput(intOpsA, inputs);
        System.out.println(outputA);

        List<Integer> intOpsB = Computer.intOpsFromInput(lines.get(0));
        inputs = new ArrayList<>();
        inputs.add(phaseSettings.get(1));
        inputs.add(outputA.get(0));
        List<Integer> outputB = computer.processProgramUntilOutput(intOpsB, inputs);
        System.out.println(outputB);

        List<Integer> intOpsC = Computer.intOpsFromInput(lines.get(0));
        inputs = new ArrayList<>();
        inputs.add(phaseSettings.get(2));
        inputs.add(outputB.get(0));
        List<Integer> outputC = computer.processProgramUntilOutput(intOpsC, inputs);
        System.out.println(outputC);

        List<Integer> intOpsD = Computer.intOpsFromInput(lines.get(0));
        inputs = new ArrayList<>();
        inputs.add(phaseSettings.get(3));
        inputs.add(outputC.get(0));
        List<Integer> outputD = computer.processProgramUntilOutput(intOpsD, inputs);
        System.out.println(outputD);

        List<Integer> intOpsE = Computer.intOpsFromInput(lines.get(0));
        inputs = new ArrayList<>();
        inputs.add(phaseSettings.get(4));
        inputs.add(outputD.get(0));
        List<Integer> outputE = computer.processProgramUntilOutput(intOpsE, inputs);
        System.out.println(outputE);

        return outputE;
    }

}
