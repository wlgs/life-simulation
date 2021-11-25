package agh.ics.oop;

public class Animal extends AbstractWorldMapElement {
    private MapDirection direction = MapDirection.NORTH;
    private IWorldMap map;

    public Animal(IWorldMap map, Vector2d initialPosition) {
        super(initialPosition);
        this.map = map;
    }

    public String toString() {
        return switch (this.direction) {
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }

    MapDirection getDirection() {
        return this.direction;
    }

    void move(MoveDirection direction) {
        boolean opposite = false;
        switch (direction) {
            case RIGHT:
                this.direction = this.direction.next();
                break;
            case LEFT:
                this.direction = this.direction.previous();
                break;
            case BACKWARD:
                opposite = true;
            case FORWARD:
                Vector2d movementChange = this.direction.toUnitVector();
                if (opposite)
                    movementChange = movementChange.opposite();
                Vector2d newPos = this.position.add(movementChange);
                if (map.canMoveTo(newPos))
                    this.position = newPos;
                break;
        }

    }
}
