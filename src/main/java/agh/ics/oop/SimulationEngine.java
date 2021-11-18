package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine {

    private final MoveDirection[] moves;
    private final List<Animal> animals;

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] animalsPositions) {
        this.moves = moves;
        this.animals = new ArrayList<>();
        for (Vector2d pos : animalsPositions) {
            Animal animalToAdd = new Animal(map, pos);
            if (map.place(animalToAdd))
                animals.add(animalToAdd);
        }

    }

    public Animal getAnimal(int idx) {
        return animals.get(idx);
    }

    @Override
    public void run() {
        int i = 0;
        for (MoveDirection move : moves) {
            animals.get(i).move(move);
            i++;
            if (i == animals.size())
                i = 0;
        }
    }


}
