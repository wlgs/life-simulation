package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractWorldMap implements IWorldMap {
    protected List<Animal> animals = new ArrayList<>();
    protected MapVisualizer visualizer = new MapVisualizer(this);
    protected Vector2d mapBorderTR;
    protected Vector2d mapBorderBL;


    public AbstractWorldMap(int TRW, int TRH, int BLW, int BLH) {
        this.mapBorderTR = new Vector2d(TRW, TRH);
        this.mapBorderBL = new Vector2d(BLW, BLH);
    }

    abstract Vector2d getDrawLowerLeft();

    abstract Vector2d getDrawUpperRight();

    public String toString() {
        return visualizer.draw(getDrawLowerLeft(), getDrawUpperRight());
    }

    public boolean canMoveTo(Vector2d position) {
        return position.precedes(mapBorderBL) && position.follows(mapBorderTR) && !(objectAt(position) instanceof Animal);
    }

    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) instanceof Animal;
    }

    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.isAt(position)) {
                return animal;
            }
        }
        return null;
    }

}
