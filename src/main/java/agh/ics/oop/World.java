package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        Animal animal = new Animal();
        OptionsParser parser = new OptionsParser();
        MoveDirection[] moves = parser.parse(args);
        System.out.println(animal);
        for (MoveDirection move : moves){
            if (move == null)
                continue;
            animal.move(move);
            System.out.println(animal);
        }
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