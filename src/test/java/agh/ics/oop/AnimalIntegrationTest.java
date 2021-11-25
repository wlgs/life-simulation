package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class AnimalIntegrationTest {


    @Test
    public void directionTest() {
        RectangularMap testMap = new RectangularMap(5, 5);
        Animal animal = new Animal(testMap, new Vector2d(2, 2));
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
        RectangularMap testMap = new RectangularMap(5, 5);
        Animal animal = new Animal(testMap, new Vector2d(2, 2));
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
        RectangularMap testMap = new RectangularMap(5, 5);
        Animal animal = new Animal(testMap, new Vector2d(2, 2));
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
    public void world1Test() {
        String[] testArgs = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse(testArgs);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        Assertions.assertEquals(engine.getAnimal(0).getPosition(), new Vector2d(2, 0));
        Assertions.assertEquals(engine.getAnimal(1).getPosition(), new Vector2d(3, 4));

    }

    @Test
    public void world2Test() {
        String[] testArgs = {"f", "r", "r", "f", "f", "f", "f", "f", "f", "l", "l", "l", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse(testArgs);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = {new Vector2d(0, 0), new Vector2d(0, 1)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        Assertions.assertEquals(engine.getAnimal(0).getPosition(), new Vector2d(3, 0));
        Assertions.assertEquals(engine.getAnimal(1).getPosition(), new Vector2d(2, 1));
    }
}
