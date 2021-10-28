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
    }

    @Test
    public void mapEscapeTest(){

    }

    @Test
    public void parserTest(){

    }
}
