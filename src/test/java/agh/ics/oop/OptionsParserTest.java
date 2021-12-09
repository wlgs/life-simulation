package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionsParserTest {

    @Test
    public void parserTest() {
        OptionsParser parser = new OptionsParser();

        //entry test
        String[] test_args = {"f", "INVALID", "forward", "INVALID"};
        try{
            MoveDirection[] output = parser.parse(test_args);
            Assertions.fail("This should have failed since the arguments were invalid");
        }catch(IllegalArgumentException ex){
            Assertions.assertTrue(true, "successfully caught good exception");
        }


        // all movements check
        String[] test_args2 = {"f", "b", "r", "l", "forward", "backward", "right", "left"};
        MoveDirection[] valid_output2 = {MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT,
                MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.BACKWARD,
                MoveDirection.RIGHT, MoveDirection.LEFT};
        Assertions.assertArrayEquals(valid_output2, parser.parse(test_args2));

        // all invalid check
        String[] test_args3 = {"just", "random", "args"};
        try{
            MoveDirection[] output = parser.parse(test_args);
            Assertions.fail("This should have failed since the arguments were invalid");
        }catch(IllegalArgumentException ex){
            Assertions.assertTrue(true, "successfully caught good exception");
        }


    }
}
