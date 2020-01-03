package Advent2019.computer;

public enum Parameter {

    POSITION(0),
    IMMEDIATE(1);

    public int value;

    Parameter(int value) {
        this.value = value;
    }

    public static Parameter intToParam(int value) {
        if (value == 1) {
            return Parameter.IMMEDIATE;
        }
        return Parameter.POSITION;
    }
}
