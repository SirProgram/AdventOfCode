package Advent2019.computer;

import java.util.Arrays;

public enum Operation {

    ADD(1),
    MULTIPLY(2),
    INPUT(3),
    OUTPUT(4),
    JUMP_IF_TRUE(5),
    JUMP_IF_FALSE(6),
    LESS_THAN(7),
    EQUALS(8),
    TERMINATE(99);

    public int value;

    Operation(int value) {
        this.value = value;
    }

    public static Operation getByValue(int value) {
        return Arrays.stream(Operation.values()).filter(o -> o.value == value).findFirst().orElseThrow();
    }
}
