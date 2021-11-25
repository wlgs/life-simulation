package agh.ics.oop;

public class Grass extends AbstractWorldMapElement {
    public Grass(Vector2d pos) {
        super(pos);
    }

    public String toString() {
        return "*";
    }
}
