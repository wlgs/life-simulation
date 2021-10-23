package agh.ics.oop;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
public class Vector2dTest {

    @Test
    public void equalsTest(){
        assertTrue("Two same vectors", new Vector2d(4,4).equals(new Vector2d(4,4)));
        assertFalse("Two different vectors", new Vector2d(2,3).equals(new Vector2d(4,5)));
        assertFalse("Only same X axis", new Vector2d(1,5).equals(new Vector2d(1,7)));
    }

    @Test
    public void toStringTest(){
        assertEquals("(3,4)", new Vector2d(3,4).toString());
        assertEquals("(5,7)", new Vector2d(5,7).toString());
        assertNotEquals("(1,2)", new Vector2d(4,4).toString());
        assertNotEquals("(0,0)", new Vector2d(1,0).toString());
    }

    @Test
    public void precedesTest(){
        assertFalse("(3,4) -> (5,5) False", new Vector2d(3,4).precedes(new Vector2d(5,5)));
        assertFalse("(1,1) -> (2,2) False", new Vector2d(1,1).precedes(new Vector2d(2,2)));
        assertTrue("(3,4) -> (2,2) True", new Vector2d(3,4).precedes(new Vector2d(2,2)));
        assertTrue("(1,1) -> (0,1) True", new Vector2d(1,1).precedes(new Vector2d(0,1)));
    }

    @Test
    public void followsTest(){
        assertTrue("(3,4) -> (5,5) True", new Vector2d(3,4).follows(new Vector2d(5,5)));
        assertTrue("(1,1)-> (2,2) True", new Vector2d(1,1).follows(new Vector2d(2,2)));
        assertFalse("(3,4) -> (2,2) False", new Vector2d(3,4).follows(new Vector2d(2,2)));
        assertFalse("(1,1) -> (0,1) False", new Vector2d(1,1).follows(new Vector2d(0,1)));
    }

    @Test
    public void upperRightTest(){
        assertEquals(new Vector2d(5,8).upperRight(new Vector2d(7,4)), new Vector2d(7,8));
        assertEquals(new Vector2d(-5,0).upperRight(new Vector2d(0,-5)), new Vector2d(0,0));
        assertNotEquals(new Vector2d(100,9).upperRight(new Vector2d(9,100)), new Vector2d(9,9));
    }

    @Test
    public void lowerLeftTest(){
        assertEquals(new Vector2d(2,1).lowerLeft(new Vector2d(6,7)), new Vector2d(2,1));
        assertEquals(new Vector2d(100,9).lowerLeft(new Vector2d(9,100)), new Vector2d(9,9));
        assertNotEquals(new Vector2d(44,22).lowerLeft(new Vector2d(21,20)), new Vector2d(21,22));
    }

    @Test
    public void addTest(){
        assertEquals(new Vector2d(2,1).add(new Vector2d(6,7)), new Vector2d(8,8));
        assertEquals(new Vector2d(2,1).add(new Vector2d(-2,-1)), new Vector2d(0,0));
        assertNotEquals(new Vector2d(50,-50).add(new Vector2d(-50,50)), new Vector2d(100,100));
    }

    @Test
    public void subtractTest(){
        assertEquals(new Vector2d(7,7).substract(new Vector2d(5,5)), new Vector2d(2,2));
        assertEquals(new Vector2d(7,7).substract(new Vector2d(-7,-7)), new Vector2d(14,14));
        assertNotEquals(new Vector2d(10,10).substract(new Vector2d(0,0)), new Vector2d(11,11));
    }

    @Test
    public void oppositeTest(){
        assertEquals(new Vector2d(7,7).opposite(), new Vector2d(-7,-7));
        assertEquals(new Vector2d(0,0).opposite(), new Vector2d(0,0));
    }


}
