package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {
    private final Vector2d mapBorderTR;
    private final Vector2d mapBorderBL;
    private final MapVisualizer visualizer;
    private final List<Animal> animals;


    public RectangularMap(int width, int height) {
        this.mapBorderTR = new Vector2d(width - 1, height - 1);
        this.mapBorderBL = new Vector2d(0, 0);
        this.visualizer = new MapVisualizer(this);
        this.animals = new ArrayList<>();
    }

    @Override
    public String toString() {
        return visualizer.draw(mapBorderBL, mapBorderTR);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(mapBorderBL) && position.follows(mapBorderTR) && !isOccupied(position);
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
        for (Animal animal : animals) {
            if (animal.isAt(position)) {
                return animal;
            }
        }
        return null;
    }
}
