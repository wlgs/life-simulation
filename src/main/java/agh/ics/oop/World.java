package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2, 1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
        MapDirection dir1 = MapDirection.NORTH;
        System.out.println(dir1);
        System.out.println(dir1.next());
        System.out.println(dir1.previous());
        System.out.println(dir1.toUnitVector());
    }

    public static void run(Direction[] array) {
        for (Direction d : array) {
            switch (d) {
                case FORWARD -> System.out.println("zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("zwierzak idzie do tylu");
                case RIGHT -> System.out.println("zwierzak skręca w prawo");
                case LEFT -> System.out.println("zwierzak skręca w lewo");
                default -> {
                }
            }
        }
    }
    public static Direction[] convert(String[] array){
        Direction[] result = new Direction[array.length];
        for(int i = 0; i< array.length; i++){
            switch(array[i]){
                case "f" -> result[i] = Direction.FORWARD;
                case "b" -> result[i] = Direction.BACKWARD;
                case "r" -> result[i] = Direction.RIGHT;
                case "l" -> result[i] = Direction.LEFT;
                default -> { result[i] = Direction.UNKNOWN;
                }
            }
        }
        return result;
    }
}