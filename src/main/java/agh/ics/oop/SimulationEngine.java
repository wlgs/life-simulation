package agh.ics.oop;

import agh.ics.oop.gui.App;
import agh.ics.oop.gui.IAnimalObserver;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable {
    private List<Animal> animals;
    private List<IAnimalObserver> observers = new ArrayList<IAnimalObserver>();
    private int moveDelay = 3300;
    public boolean running = true;
    private GrassField map;

    public SimulationEngine(GrassField map, Vector2d[] animalsPositions) {
        this.animals = new ArrayList<>();
        this.map = map;
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


    public void removeDeadAnimals(){
        List<Animal> deadAnimals = new ArrayList<>();
        for (Animal a : animals){
            if (a.getEnergy()>0){
                continue;
            }
            deadAnimals.add(a);
        }
        for (Animal da : deadAnimals){
            this.animals.remove(da);
            this.map.removeAnimalFromMap(da);
        }
    }

    public void checkFood(){
        for (Animal a : animals){
            if( map.getGrassAt(a.getPosition())!=null){
                Grass g = map.getGrassAt(a.getPosition());
                List<Animal> bestAnimals = map.getBestAnimals(a.getPosition());
                int toDivide = bestAnimals.size();
                int energyValue = g.getEnergyValue();
                int portion = energyValue/toDivide;
                for (Animal ba : bestAnimals)
                    ba.addEnergy(portion);
                map.removeGrassFromMap(g);
            }
        }
    }

    // calculateEra means play all moves, check if food was eaten,
    public void calculateEra(){
        removeDeadAnimals();
        checkFood();

    }
    @Override
    public void run() {
        while (running){
            for (Animal a : animals)
                a.move();

            calculateEra();

            for (IAnimalObserver observer : observers)
                observer.animalMoved();

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted -> " + ex);
                this.running = false;
            }
        }
    }
}
