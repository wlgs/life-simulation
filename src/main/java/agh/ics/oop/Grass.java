package agh.ics.oop;

import java.util.ArrayList;

public class Grass extends AbstractWorldMapElement {
    public Grass(Vector2d pos) {
        super(pos);
        this.observers = new ArrayList<>();
    }

    public String toString() {
        return "*";
    }


    @Override
    public String getFileName() {
        return "grass.png";
    }
}
