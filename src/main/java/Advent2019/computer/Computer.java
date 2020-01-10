package Advent2019.computer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Computer {

    public static List<Integer> intOpsFromInput(String input) {
        return Arrays.stream(input.split(",")).mapToInt(Integer::parseInt)
                .boxed().collect(Collectors.toList());
    }

    public List<Integer> processProgram(List<Integer> intOps, List<Integer> input) {
        List<Integer> outputs = new ArrayList<>();
        int processingIndex = 0;

        while (processingIndex >= 0) {
            Computer.Output result = processIntOps(intOps, processingIndex, input);
            processingIndex = result.getNextProcessingInput();
            //System.out.println(intOps);
            result.getOutput().ifPresent(System.out::println);
            result.getOutput().ifPresent(outputs::add);
        }

        return outputs;
    }

    public List<Integer> processProgramUntilOutput(List<Integer> intOps, List<Integer> input) {
        List<Integer> outputs = new ArrayList<>();
        int processingIndex = 0;

        while (outputs.size() == 0) {
            Computer.Output result = processIntOps(intOps, processingIndex, input);
            processingIndex = result.getNextProcessingInput();
            //System.out.println(intOps);
            result.getOutput().ifPresent(System.out::println);
            result.getOutput().ifPresent(outputs::add);
        }

        return outputs;
    }

    public Output processIntOps(List<Integer> intOps, int processingIndex) {
        return processIntOps(intOps, processingIndex, new ArrayList<>());
    }

    public Output processIntOps(List<Integer> intOps, int processingIndex, List<Integer> input) {

        Instruction instruction = new Instruction(intOps.get(processingIndex));

        switch (Operation.getByValue(instruction.getOperationNumber())) {
            case ADD:
                return add(intOps, processingIndex, instruction);
            case MULTIPLY:
                return multiply(intOps, processingIndex, instruction);
            case INPUT:
                return input(intOps, processingIndex, input);
            case OUTPUT:
                return output(intOps, processingIndex, instruction);
            case JUMP_IF_TRUE:
                return jumpIfTrue(intOps, processingIndex, instruction);
            case JUMP_IF_FALSE:
                return jumpIfFalse(intOps, processingIndex, instruction);
            case LESS_THAN:
                return lessThan(intOps, processingIndex, instruction);
            case EQUALS:
                return equals(intOps, processingIndex, instruction);
            case TERMINATE:
                return terminate();
        }

        System.out.println("Unrecognised operation: " + instruction.getOperationNumber());
        return terminate();
    }

    private Output terminate() {
        return new Output(-1);
    }

    private Output equals(List<Integer> intOps, int processingIndex, Instruction instruction) {
        int valueOne = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));
        int valueTwo = getValueGivenMode(intOps, processingIndex + 2, instruction.getParameters().get(1));
        int outputPosition = getValueGivenMode(intOps, processingIndex + 3, Parameter.IMMEDIATE);

        intOps.set(outputPosition, valueOne == valueTwo ? 1 : 0);

        return new Output(processingIndex + 4);
    }

    private Output lessThan(List<Integer> intOps, int processingIndex, Instruction instruction) {
        int valueOne = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));
        int valueTwo = getValueGivenMode(intOps, processingIndex + 2, instruction.getParameters().get(1));
        int outputPosition = getValueGivenMode(intOps, processingIndex + 3, Parameter.IMMEDIATE);

        intOps.set(outputPosition, valueOne < valueTwo ? 1 : 0);

        return new Output(processingIndex + 4);
    }

    private Output jumpIfFalse(List<Integer> intOps, int processingIndex, Instruction instruction) {
        int valueOne = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));
        int jumpPosition = getValueGivenMode(intOps, processingIndex + 2, instruction.getParameters().get(1));

        if (valueOne == 0) {
            return new Output(jumpPosition);
        }
        return new Output(processingIndex + 3);
    }

    private Output jumpIfTrue(List<Integer> intOps, int processingIndex, Instruction instruction) {
        int valueOne = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));
        int jumpPosition = getValueGivenMode(intOps, processingIndex + 2, instruction.getParameters().get(1));

        if (valueOne != 0) {
            return new Output(jumpPosition);
        }
        return new Output(processingIndex + 3);
    }

    private Output output(List<Integer> intOps, int processingIndex, Instruction instruction) {
        int outputValue = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));

        return new Output(processingIndex + 2, outputValue);
    }

    private Output input(List<Integer> intOps, int processingIndex, List<Integer> input) {
        int outputPosition = getValueGivenMode(intOps, processingIndex + 1, Parameter.IMMEDIATE);

        intOps.set(outputPosition, input.remove(0));
        return new Output(processingIndex + 2);
    }

    private Output multiply(List<Integer> intOps, int processingIndex, Instruction instruction) {
        int valueOne = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));
        int valueTwo = getValueGivenMode(intOps, processingIndex + 2, instruction.getParameters().get(1));
        int outputPosition = getValueGivenMode(intOps, processingIndex + 3, Parameter.IMMEDIATE);

        intOps.set(outputPosition, valueOne * valueTwo);
        return new Output(processingIndex + 4);
    }

    private Output add(List<Integer> intOps, int processingIndex, Instruction instruction) {
        int valueOne = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));
        int valueTwo = getValueGivenMode(intOps, processingIndex + 2, instruction.getParameters().get(1));
        int outputPosition = getValueGivenMode(intOps, processingIndex + 3, Parameter.IMMEDIATE);

        intOps.set(outputPosition, valueOne + valueTwo);
        return new Output(processingIndex + 4);
    }

    private int getValueGivenMode(List<Integer> intOps, int input, Parameter mode) {
        if (mode == Parameter.IMMEDIATE) {
            return intOps.get(input);
        } else {
            return intOps.get(intOps.get(input));
        }
    }

    public class Instruction {
        private int operationNumber;
        private List<Parameter> parameters;

        public Instruction(int input) {
            operationNumber = input % 100;
            parameters = new ArrayList<>();

            int paramA = (input / 10000) % 10;
            int paramB = (input / 1000) % 10;
            int paramC = (input / 100) % 10;

            parameters.add(Parameter.intToParam(paramC));
            parameters.add(Parameter.intToParam(paramB));
            parameters.add(Parameter.intToParam(paramA));
            System.out.println(input + ": " + operationNumber + ": " + paramC + " " +  paramB + " " + paramA);
        }

        public int getOperationNumber() {
            return operationNumber;
        }

        public List<Parameter> getParameters() {
            return parameters;
        }
    }

    public class Output {
        private int nextProcessingInput;
        private Optional<Integer> output;

        public Output(int nextProcessingInput) {
            this.nextProcessingInput = nextProcessingInput;
            this.output = Optional.empty();
        }

        public Output(int nextProcessingInput, int output) {
            this.nextProcessingInput = nextProcessingInput;
            this.output = Optional.of(output);
        }

        public int getNextProcessingInput() {
            return nextProcessingInput;
        }

        public Optional<Integer> getOutput() {
            return output;
        }
    }
}
