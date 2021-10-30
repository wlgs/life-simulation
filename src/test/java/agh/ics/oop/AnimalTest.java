package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {

    @Test
    public void directionTest() {
        Animal animal = new Animal();
        // starting direction is NORTH
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.RIGHT);
        Assertions.assertEquals(MapDirection.NORTH, animal.getDirection());

        animal.move(MoveDirection.RIGHT);
        Assertions.assertEquals(MapDirection.EAST, animal.getDirection());

        animal.move(MoveDirection.RIGHT);
        Assertions.assertEquals(MapDirection.SOUTH, animal.getDirection());

        animal.move(MoveDirection.RIGHT);
        Assertions.assertEquals(MapDirection.WEST, animal.getDirection());

        animal.move(MoveDirection.RIGHT);

        // we are back now heading north

        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.RIGHT);
        // nothing should change
        Assertions.assertEquals(MapDirection.NORTH, animal.getDirection());
    }

    @Test
    public void movementTest() {
        Animal animal = new Animal();
        // starting at 2,2
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        //should be 2,4 and heading north
        Assertions.assertEquals(new Vector2d(2, 4), animal.getPosition());

        animal.move(MoveDirection.RIGHT);
        //heading east so we go forward now
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);

        //now the position should be 4,4
        Assertions.assertEquals(new Vector2d(4, 4), animal.getPosition());


        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);

        Assertions.assertEquals(new Vector2d(0, 4), animal.getPosition());

        //heading east and animal is at (0,4) so now lets try to get to (0,0)

        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);

        Assertions.assertEquals(new Vector2d(0, 0), animal.getPosition());

    }

    @Test
    public void mapEscapeTest() {
        Animal animal = new Animal();
        animal.move(MoveDirection.RIGHT);

        //starting position is (2,2), heading east

        //right border of the map
        for (int i = 0; i < 20; i++)
            animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2d(4, 2), animal.getPosition());


        animal.move(MoveDirection.LEFT);

        //heading north

        //top border of the map
        for (int i = 0; i < 20; i++)
            animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2d(4, 4), animal.getPosition());

        animal.move(MoveDirection.LEFT);

        //left border of the map
        for (int i = 0; i < 20; i++)
            animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2d(0, 4), animal.getPosition());

        animal.move(MoveDirection.LEFT);

        //bottom border of the map
        for (int i = 0; i < 20; i++)
            animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2d(0, 0), animal.getPosition());
    }

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
