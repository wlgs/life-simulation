package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionsParserTest {

    @Test
    public void parserTest() {
        OptionsParser parser = new OptionsParser();

        //entry test
        String[] test_args = {"f", "INVALID", "forward", "INVALID"};
        MoveDirection[] valid_output = {MoveDirection.FORWARD, null, MoveDirection.FORWARD, null};
        Assertions.assertArrayEquals(valid_output, parser.parse(test_args));


        // all movements check
        String[] test_args2 = {"f", "b", "r", "l", "forward", "backward", "right", "left"};
        MoveDirection[] valid_output2 = {MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT,
                MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.BACKWARD,
                MoveDirection.RIGHT, MoveDirection.LEFT};
        Assertions.assertArrayEquals(valid_output2, parser.parse(test_args2));

        // all invalid check
        String[] test_args3 = {"just", "random", "args"};
        MoveDirection[] valid_output3 = {null, null, null};
        Assertions.assertArrayEquals(valid_output3, parser.parse(test_args3));


    }
}
