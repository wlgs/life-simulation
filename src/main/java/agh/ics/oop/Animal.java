package agh.ics.oop;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);


    public String toString() {
        return "pozycja: " + position + ", kierunek: " + direction;
    }

    boolean isAt(Vector2d position) {
        return position.equals(this.position);
    }

    MapDirection getDirection() {
        return this.direction;
    }

    Vector2d getPosition() {
        return this.position;
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
                Vector2d movementChange = new Vector2d(0, 0);
                switch (this.direction) {
                    case EAST:
                        movementChange = movementChange.add(new Vector2d(1, 0));
                        break;
                    case WEST:
                        movementChange = movementChange.add(new Vector2d(-1, 0));
                        break;
                    case NORTH:
                        movementChange = movementChange.add(new Vector2d(0, 1));
                        break;
                    case SOUTH:
                        movementChange = movementChange.add(new Vector2d(0, -1));
                        break;
                }
                if (opposite) {
                    movementChange = movementChange.opposite();
                }
                Vector2d newPos = this.position.add(movementChange);
                Vector2d mapBorderTR = new Vector2d(4, 4);
                Vector2d mapBorderBL = new Vector2d(0, 0);
                if (newPos.precedes(mapBorderBL) && newPos.follows(mapBorderTR))
                    this.position = newPos;
                break;
        }

    }


}
