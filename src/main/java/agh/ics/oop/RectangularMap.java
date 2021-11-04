package agh.ics.oop;

import jdk.incubator.vector.VectorOperators;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {
    private final Vector2d mapBorderTR;
    private final Vector2d mapBorderBL;
    IWorldMap map;
    List<Animal> animals = new ArrayList<>();


    RectangularMap(int width, int height) {
        this.mapBorderTR = new Vector2d(width,height);
        this.mapBorderBL = new Vector2d(0,0);
    }

    @Override
    public String toString(){
        MapVisualizer visualizer = new MapVisualizer(this.map);
        return "";
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(mapBorderBL) && position.follows(mapBorderTR);
    }

    @Override
    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal a : animals) {
            if (position.equals(a.getPosition()))
                return true;
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }
}
