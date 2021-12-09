package agh.ics.oop;

public class OptionsParser {

    public MoveDirection[] parse(String[] args) {
        MoveDirection[] result = new MoveDirection[args.length];
        int i = 0;
        for (String arg : args) {
            switch (arg) {
                case "f", "forward" -> {
                    result[i] = MoveDirection.FORWARD;
                    i += 1;
                }
                case "b", "backward" -> {
                    result[i] = MoveDirection.BACKWARD;
                    i += 1;
                }
                case "r", "right" -> {
                    result[i] = MoveDirection.RIGHT;
                    i += 1;
                }
                case "l", "left" -> {
                    result[i] = MoveDirection.LEFT;
                    i += 1;
                }
                default -> throw new IllegalArgumentException(arg + " is not a valid move keyword");
            }
        }
        return result;
    }
}