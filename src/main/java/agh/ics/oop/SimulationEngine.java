package agh.ics.oop;

import agh.ics.oop.gui.App;
import agh.ics.oop.gui.IAnimalObserver;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable {
    private List<Animal> animals;
    private List<IAnimalObserver> observers = new ArrayList<IAnimalObserver>();
    private int moveDelay = 0;
    public boolean stop = true;

    public SimulationEngine(IWorldMap map, Vector2d[] animalsPositions) {
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

    @Override
    public void run() {
        while (!stop){
            for (Animal a : animals){
                a.move();
            }
            for (IAnimalObserver observer : observers)
                observer.animalMoved();
            try {
                Thread.sleep(this.moveDelay);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted -> " + ex);
                stop = false;
            }
        }
    }
}
