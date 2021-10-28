package agh.ics.oop;

public class OptionsParser {
    MoveDirection[] parse(String[] args){
        MoveDirection[] result = new MoveDirection[args.length];
        int i = 0;
        boolean invalid = false;
        for (String arg : args){
            switch (arg) {
                case "f", "forward" -> result[i] = MoveDirection.FORWARD;
                case "b", "backward" -> result[i] = MoveDirection.BACKWARD;
                case "r", "right" -> result[i] = MoveDirection.RIGHT;
                case "l", "left" -> result[i] = MoveDirection.LEFT;
                default -> invalid = true;
            }
            if (!invalid)
                i++;
            invalid = false;
        }
        return result;

    }

}
