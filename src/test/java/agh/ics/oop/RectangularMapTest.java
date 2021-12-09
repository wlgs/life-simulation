package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RectangularMapTest {

    @Test
    public void canMoveToTest() {
        IWorldMap map = new RectangularMap(2, 2);
        // the map is 2x2

        Assertions.assertTrue(map.canMoveTo(new Vector2d(1, 0)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(1, 1)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(1, 2)));
    }

    @Test
    public void placeTest() {
        IWorldMap map = new RectangularMap(2, 2);
        // the map is 2x2
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(1, 1))));
        try{
            map.place(new Animal(map, new Vector2d(1, 1)));
            Assertions.fail("an exception was meant to throw at (1,1)");
        }
        catch (IllegalArgumentException ex){
            Assertions.assertTrue(true, "an exception was caught (1,1)");
        }

        try{
            map.place(new Animal(map, new Vector2d(3, 1)));
            Assertions.fail("an exception was meant to throw at (3,1)");
        }
        catch (IllegalArgumentException ex){
            Assertions.assertTrue(true, "an exception was caught at (3,1)");
        }
    }

    @Test
    public void isOccupiedTest() {
        IWorldMap map = new RectangularMap(2, 2);
        Assertions.assertFalse(map.isOccupied(new Vector2d(1, 1)));
        map.place(new Animal(map, new Vector2d(1, 1)));
        Assertions.assertTrue(map.isOccupied(new Vector2d(1, 1)));
    }

    @Test
    public void objectAtTest() {
        IWorldMap map = new RectangularMap(2, 2);
        Assertions.assertNull(map.objectAt(new Vector2d(1, 1)));
        map.place(new Animal(map, new Vector2d(1, 1)));
        Assertions.assertTrue(map.objectAt(new Vector2d(1, 1)) instanceof Animal);
    }
}
