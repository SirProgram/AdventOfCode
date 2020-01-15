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

    public List<Integer> processB(String path, int input) throws IOException {

        int max = 0;
        List<Integer> bestPhaseSettings = new ArrayList<>();
        for (List<Integer> phaseSettings : Permutations.permutations(Arrays.asList(5, 6, 7, 8, 9))) {
            int newOutput = processB(path, input, phaseSettings).get(0);
            if (newOutput > max) {
                max = newOutput;
                bestPhaseSettings = phaseSettings;
            }
        }

        System.out.println(bestPhaseSettings);
        System.out.println(max);

        return Arrays.asList(max);
    }

    public List<Integer> processB(String path, int input, List<Integer> phaseSettings) throws IOException {

        List<String> lines = readFile(path);

        List<Integer> intOpsA = Computer.intOpsFromInput(lines.get(0));
        List<Integer> intOpsB = Computer.intOpsFromInput(lines.get(0));
        List<Integer> intOpsC = Computer.intOpsFromInput(lines.get(0));
        List<Integer> intOpsD = Computer.intOpsFromInput(lines.get(0));
        List<Integer> intOpsE = Computer.intOpsFromInput(lines.get(0));

        Computer computerA = new Computer();
        Computer computerB = new Computer();
        Computer computerC = new Computer();
        Computer computerD = new Computer();
        Computer computerE = new Computer();


        boolean terminated = false;
        List<Integer> finalOutput = new ArrayList<>();

        List<Integer> inputsA = new ArrayList<>();
        inputsA.add(phaseSettings.get(0));
        inputsA.add(input);
        List<Integer> inputsB = new ArrayList<>();
        inputsB.add(phaseSettings.get(1));
        List<Integer> inputsC = new ArrayList<>();
        inputsC.add(phaseSettings.get(2));
        List<Integer> inputsD = new ArrayList<>();
        inputsD.add(phaseSettings.get(3));
        List<Integer> inputsE = new ArrayList<>();
        inputsE.add(phaseSettings.get(4));

        while (!terminated) {

            List<Integer> outputA = computerA.processProgramUntilOutput(intOpsA, inputsA);
            System.out.println(outputA);
            if (outputA.get(0) != -1) {
                inputsB.add(outputA.remove(0));
            }
            terminated = outputA.contains(-1);

            List<Integer> outputB = computerB.processProgramUntilOutput(intOpsB, inputsB);
            System.out.println(outputB);
            if (outputB.get(0) != -1) {
                inputsC.add(outputB.remove(0));
            }

            terminated = terminated || outputB.contains(-1);

            List<Integer> outputC = computerC.processProgramUntilOutput(intOpsC, inputsC);
            System.out.println(outputC);
            if (outputC.get(0) != -1) {
                inputsD.add(outputC.remove(0));
            }

            terminated = terminated || outputC.contains(-1);

            List<Integer> outputD = computerD.processProgramUntilOutput(intOpsD, inputsD);
            System.out.println(outputD);
            if (outputD.get(0) != -1) {
                inputsE.add(outputD.remove(0));
            }

            terminated = terminated || outputD.contains(-1);

            List<Integer> outputE = computerE.processProgramUntilOutput(intOpsE, inputsE);
            System.out.println(outputE);
            if (outputE.get(0) != -1) {
                finalOutput = new ArrayList<>(outputE);
                inputsA.add(outputE.remove(0));
            }

        }

        return finalOutput;
    }

}
