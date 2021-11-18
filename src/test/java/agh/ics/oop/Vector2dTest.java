package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Vector2dTest {

    @Test
    public void equalsTest(){
        Assertions.assertTrue(new Vector2d(4,4).equals(new Vector2d(4,4)), "Two same vectors -> should be True");
        Assertions.assertFalse(new Vector2d(2,3).equals(new Vector2d(4,5)), "Two different vectors -> should be False");
        Assertions.assertFalse(new Vector2d(1,5).equals(new Vector2d(1,7)), "Only same X axis -> should be False");
    }

    @Test
    public void toStringTest(){
        Assertions.assertEquals("(3,4)", new Vector2d(3,4).toString());
        Assertions.assertEquals("(5,7)", new Vector2d(5,7).toString());
    }

    @Test
    public void precedesTest(){
        Assertions.assertFalse(new Vector2d(3,4).precedes(new Vector2d(5,5)), "(3,4) -> (5,5) False");
        Assertions.assertFalse(new Vector2d(1,1).precedes(new Vector2d(2,2)), "(1,1) -> (2,2) False");
        Assertions.assertTrue(new Vector2d(3,4).precedes(new Vector2d(2,2)), "(3,4) -> (2,2) True");
        Assertions.assertTrue(new Vector2d(1,1).precedes(new Vector2d(0,1)), "(1,1) -> (0,1) True");
    }

    @Test
    public void followsTest(){
        Assertions.assertTrue(new Vector2d(3,4).follows(new Vector2d(5,5)), "(3,4) -> (5,5) True");
        Assertions.assertTrue(new Vector2d(1,1).follows(new Vector2d(2,2)), "(1,1)-> (2,2) True");
        Assertions.assertFalse(new Vector2d(3,4).follows(new Vector2d(2,2)), "(3,4) -> (2,2) False");
        Assertions.assertFalse(new Vector2d(1,1).follows(new Vector2d(0,1)), "(1,1) -> (0,1) False");
    }

    @Test
    public void upperRightTest(){
        Assertions.assertEquals(new Vector2d(5,8).upperRight(new Vector2d(7,4)), new Vector2d(7,8));
        Assertions.assertEquals(new Vector2d(-5,0).upperRight(new Vector2d(0,-5)), new Vector2d(0,0));
    }

    @Test
    public void lowerLeftTest(){
        Assertions.assertEquals(new Vector2d(2,1), new Vector2d(2,1).lowerLeft(new Vector2d(6,7)));
        Assertions.assertEquals(new Vector2d(9,9), new Vector2d(100,9).lowerLeft(new Vector2d(9,100)));
    }

    @Test
    public void addTest(){
        Assertions.assertEquals(new Vector2d(8,8), new Vector2d(2,1).add(new Vector2d(6,7)));
        Assertions.assertEquals(new Vector2d(0,0), new Vector2d(2,1).add(new Vector2d(-2,-1)));
    }

    @Test
    public void subtractTest(){
        Assertions.assertEquals(new Vector2d(2,2),new Vector2d(7,7).subtract(new Vector2d(5,5)));
        Assertions.assertEquals(new Vector2d(14,14), new Vector2d(7,7).subtract(new Vector2d(-7,-7)));
    }

    @Test
    public void oppositeTest(){
        Assertions.assertEquals(new Vector2d(-7,-7), new Vector2d(7,7).opposite());
        Assertions.assertEquals(new Vector2d(0,0), new Vector2d(0,0).opposite());
        Assertions.assertEquals(new Vector2d(0,3), new Vector2d(0,-3).opposite());
    }


}
