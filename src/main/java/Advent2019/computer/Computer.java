package Advent2019.computer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Computer {

    public List<Integer> intOpsFromInput(String input) {
        return Arrays.stream(input.split(",")).mapToInt(Integer::parseInt)
                .boxed().collect(Collectors.toList());
    }

    public Output processIntOps(List<Integer> intOps, int processingIndex) {
        return processIntOps(intOps, processingIndex, null);
    }

    public Output processIntOps(List<Integer> intOps, int processingIndex, Integer input) {

        Instruction instruction = new Instruction(intOps.get(processingIndex));

        if (instruction.getOperationNumber() == Operation.ADD.value) {
            int valueOne = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));
            int valueTwo = getValueGivenMode(intOps, processingIndex + 2, instruction.getParameters().get(1));
            int outputPosition = getValueGivenMode(intOps, processingIndex + 3, Parameter.IMMEDIATE);

            intOps.set(outputPosition, valueOne + valueTwo);
            return new Output(processingIndex + 4);
        } else if (instruction.getOperationNumber() == Operation.MULTIPLY.value) {
            int valueOne = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));
            int valueTwo = getValueGivenMode(intOps, processingIndex + 2, instruction.getParameters().get(1));
            int outputPosition = getValueGivenMode(intOps, processingIndex + 3, Parameter.IMMEDIATE);

            intOps.set(outputPosition, valueOne * valueTwo);
            return new Output(processingIndex + 4);
        } else if (instruction.getOperationNumber() == Operation.INPUT.value) {
            int outputPosition = getValueGivenMode(intOps, processingIndex + 1, Parameter.IMMEDIATE);

            intOps.set(outputPosition, input);
            return new Output(processingIndex + 2);
        } else if (instruction.getOperationNumber() == Operation.OUTPUT.value) {
            int outputValue = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));

            return new Output(processingIndex + 2, outputValue);

        } else if (instruction.getOperationNumber() == Operation.JUMP_IF_TRUE.value) {
            int valueOne = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));
            int jumpPosition = getValueGivenMode(intOps, processingIndex + 2, instruction.getParameters().get(1));

            if (valueOne != 0) {
                return new Output(jumpPosition);
            }
            return new Output(processingIndex + 3);

        } else if (instruction.getOperationNumber() == Operation.JUMP_IF_FALSE.value) {
            int valueOne = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));
            int jumpPosition = getValueGivenMode(intOps, processingIndex + 2, instruction.getParameters().get(1));

            if (valueOne == 0) {
                return new Output(jumpPosition);
            }
            return new Output(processingIndex + 3);
        } else if (instruction.getOperationNumber() == Operation.LESS_THAN.value) {
            int valueOne = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));
            int valueTwo = getValueGivenMode(intOps, processingIndex + 2, instruction.getParameters().get(1));
            int outputPosition = getValueGivenMode(intOps, processingIndex + 3, Parameter.IMMEDIATE);

            intOps.set(outputPosition, valueOne < valueTwo ? 1 : 0);

            return new Output(processingIndex + 4);

        } else if (instruction.getOperationNumber() == Operation.EQUALS.value) {
            int valueOne = getValueGivenMode(intOps, processingIndex + 1, instruction.getParameters().get(0));
            int valueTwo = getValueGivenMode(intOps, processingIndex + 2, instruction.getParameters().get(1));
            int outputPosition = getValueGivenMode(intOps, processingIndex + 3, Parameter.IMMEDIATE);

            intOps.set(outputPosition, valueOne == valueTwo ? 1 : 0);

            return new Output(processingIndex + 4);

        } else if (instruction.getOperationNumber() == Operation.TERMINATE.value) {
            return new Output(-1);
        }

        System.out.println("Unrecognised operation: " + instruction.getOperationNumber());
        return new Output(-1);
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
