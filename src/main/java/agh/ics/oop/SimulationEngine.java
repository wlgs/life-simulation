package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine{
    MoveDirection[] moves;
    IWorldMap map;
    List<Animal> animals = new ArrayList<>();

    SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] animalsPositions){
        this.moves = moves;
        this.map = map;
        for(Vector2d pos : animalsPositions){
            Animal animalToAdd = new Animal(this.map,pos);
            if (this.map.place(animalToAdd))
                animals.add(animalToAdd);
        }

    }

    @Override
    public void run() {
        int i = 0;
        for (MoveDirection move : moves){
            animals.get(i).move(move);
            i++;
            if(i == animals.size())
                i=0;
        }
    }
}
