package agh.ics.oop;

import java.util.ArrayList;

public class Grass extends AbstractWorldMapElement {
    private final int energyValue = 30;
    public Grass(Vector2d pos) {
        super(pos);
        this.observers = new ArrayList<>();
    }

    public String toString() {
        return "*";
    }

    public int getEnergyValue(){
        return energyValue;
    }
}
