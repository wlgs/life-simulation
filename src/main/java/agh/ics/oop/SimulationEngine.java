package agh.ics.oop;

import agh.ics.oop.gui.App;
import agh.ics.oop.gui.IAnimalObserver;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable {

    private MoveDirection[] moves;
    private final List<Animal> animals;
    private List<IAnimalObserver> observers = new ArrayList<IAnimalObserver>();
    private int moveDelay = 0;

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] animalsPositions) {
        this.moves = moves;
        this.animals = new ArrayList<>();
        for (Vector2d pos : animalsPositions) {
            Animal animalToAdd = new Animal(map, pos);
            if (map.place(animalToAdd))
                animals.add(animalToAdd);
        }

    }

    public SimulationEngine(IWorldMap map, Vector2d[] animalsPositions) {
        this.moves = new MoveDirection[0];
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

    public void setMoveDelay(int val) {
        this.moveDelay = val;
    }

    public void addObserver(IAnimalObserver app) {
        this.observers.add(app);
    }

    public void setMoves(MoveDirection[] directions) {
        this.moves = directions;
    }

    @Override
    public void run() {
        int i = 0;
        for (MoveDirection move : this.moves) {
            animals.get(i).move(move);
            i++;
            if (i == animals.size())
                i = 0;
            for (IAnimalObserver observer : observers) {
                observer.animalMoved();
            }
            try {
                System.out.println("Sleeping..");
                Thread.sleep(this.moveDelay);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted -> " + ex);
            }
        }
    }
}
