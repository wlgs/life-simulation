package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GrassFieldTest {
    @Test
    public void canMoveToTest() {
        GrassField map = new GrassField(10);
        Assertions.assertTrue(map.canMoveTo(new Vector2d(1, 2)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(1, 2)));
        map.place(new Animal(map, new Vector2d(3, 3)));
        map.place(new Animal(map, new Vector2d(3, 3)));
        map.place(new Animal(map, new Vector2d(3, 3)));
        map.place(new Animal(map, new Vector2d(3, 3)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(3, 3)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(-1, -1)));

        // foldable test

    }

    @Test
    public void objectAtTest(){
        GrassField map = new GrassField(10);
        map.place(new Animal(map, new Vector2d(3, 3)));
        map.place(new Animal(map, new Vector2d(3, 3)));
        map.place(new Animal(map, new Vector2d(3, 3)));
        map.place(new Animal(map, new Vector2d(3, 3)));
        map.place(new Animal(map, new Vector2d(3, 3)));
        map.spawnGrassAt(new Vector2d(3,4));
        Assertions.assertTrue(map.objectAt(new Vector2d(3,3)) instanceof Animal);
        Assertions.assertTrue(map.objectAt(new Vector2d(3,4)) instanceof Grass);
    }

    @Test
    public void removeTest(){
        GrassField map = new GrassField(10);
        Animal testAnimal = new Animal(map, new Vector2d(3, 3));
        map.place(testAnimal);
        map.removeAnimalFromMap(testAnimal);
        Assertions.assertNull(map.objectAt(new Vector2d(3, 3)));
    }

    @Test
    public void bestAnimalTest(){
        GrassField map = new GrassField(10);
        map.place(new Animal(map, new Vector2d(3, 3), 12));
        map.place(new Animal(map, new Vector2d(3, 3), 10));
        map.place(new Animal(map, new Vector2d(3, 3), 15));

        Assertions.assertEquals(15, map.getBestAnimal(new Vector2d(3,3)).getEnergy());

    }

    @Test
    public void bestAnimalsTest(){
        GrassField map = new GrassField(10);
        map.place(new Animal(map, new Vector2d(3, 3), 12));
        map.place(new Animal(map, new Vector2d(3, 3), 10));
        map.place(new Animal(map, new Vector2d(3, 3), 15));
        map.place(new Animal(map, new Vector2d(3, 3), 15));
        map.place(new Animal(map, new Vector2d(3, 3), 10));
        map.place(new Animal(map, new Vector2d(3, 3), 10));
        map.place(new Animal(map, new Vector2d(3, 3), 15));
        map.place(new Animal(map, new Vector2d(3, 3), 10));

        Assertions.assertEquals(3, map.getBestAnimals(new Vector2d(3,3)).size());

    }
}
