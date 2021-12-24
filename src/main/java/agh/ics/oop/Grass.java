package agh.ics.oop;

import java.util.ArrayList;

public class Grass extends AbstractWorldMapElement {
    private final int energyValue;

    public Grass(Vector2d pos, int plantEnergy) {
        super(pos);
        this.energyValue = plantEnergy;
        this.observers = new ArrayList<>();
    }

    public String toString() {
        return "*";
    }

    public int getEnergyValue() {
        return energyValue;
    }
}
