package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MapDirectionTest {

    @Test
    public void mapDirNextTest(){
        Assertions.assertEquals(MapDirection.NORTHEAST, MapDirection.NORTH.next());
        Assertions.assertEquals(MapDirection.SOUTHEAST, MapDirection.EAST.next());
        Assertions.assertEquals(MapDirection.SOUTHWEST, MapDirection.SOUTH.next());
        Assertions.assertEquals(MapDirection.NORTHWEST, MapDirection.WEST.next());
    }

}
