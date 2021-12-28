package agh.ics.oop;

import agh.ics.oop.gui.IAnimalObserver;
import java.util.*;

public class SimulationEngine implements IEngine, Runnable {
    private List<Animal> animals;
    private List<Animal> deadAnimals = new ArrayList<>();
    private List<IAnimalObserver> observers = new ArrayList<IAnimalObserver>();
    Comparator<Animal> animalComp = new AnimalsComparator();
    private final Random r = new Random();
    private int moveDelay = 300;
    public boolean running = false;
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

    public SimulationEngine(GrassField map, List<Animal> animalsToAdd) {
        this.animals = new ArrayList<>();
        this.map = map;
        for (Animal a : animalsToAdd) {
            if (map.place(a))
                animals.add(a);
        }

    }


    public SimulationEngine(int animalsAmount, int startEnergy, int moveEnergy, GrassField map) {
        this.animals = new ArrayList<>();
        this.map = map;
        for (int i = 1; i <= animalsAmount; i++) {

            int randomX = this.r.nextInt(map.getDrawUpperRight().x);
            int randomY = this.r.nextInt(map.getDrawUpperRight().y);
            Animal animalToAdd = new Animal(map, new Vector2d(randomX, randomY), startEnergy, moveEnergy);
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


    public void removeDeadAnimals() {
        List<Animal> deadAnimals = new ArrayList<>();
        for (Animal a : animals) {
            if (a.getEnergy() > 0) {
                continue;
            }
            deadAnimals.add(a);
        }
        for (Animal da : deadAnimals) {
            this.animals.remove(da);
            this.map.removeAnimalFromMap(da);
            this.deadAnimals.add(da);
        }
    }

    public void checkFood() {
        for (Animal a : animals) {
            if (map.getGrassAt(a.getPosition()) != null) {
                Grass g = map.getGrassAt(a.getPosition());
                List<Animal> bestAnimals = map.getBestAnimals(a.getPosition());
                int toDivide = bestAnimals.size();
                int energyValue = g.getEnergyValue();
                int portion = energyValue / toDivide;
                for (Animal ba : bestAnimals)
                    ba.addEnergy(portion);
                map.removeGrassFromMap(g);
                if (map.isJungleTile(g.getPosition()))
                    map.subtractTotalGrassInJungle(1);
                else
                    map.subtractTotalGrassOutsideJungle(1);
            }
        }
    }

    public void reproduceAnimals() {
        Random r = new Random();
        Map<Vector2d, Boolean> alreadyDone = new LinkedHashMap<>();
        List<Animal> newAnimals = new ArrayList<>();
        List<Animal> healthyAnimals = new ArrayList<>();
        List<Animal> candidates = new ArrayList<>();
        for (Animal a : animals) {

            Vector2d posToCheck = a.getPosition();
            if (alreadyDone.get(posToCheck) != null) {
                continue;
            }
            healthyAnimals.clear();
            int animalsAmount = map.animalsAt(posToCheck).size();
            if (animalsAmount < 2)
                continue;
            for (Animal a2 : map.animalsAt(posToCheck)) {
                if (a.getEnergy() > 0.5 * a.getStartEnergy())
                    healthyAnimals.add(a2);
            }
            if (healthyAnimals.size() < 2)
                continue;
            healthyAnimals.sort(animalComp);
            candidates.clear();
            candidates.add(healthyAnimals.get(0));
            healthyAnimals.remove(0);
            int best_energy = candidates.get(0).getEnergy();

            // after the loop we have potentially 40
            // or something like that 40, 40, 40, 40
            for (Animal a2 : healthyAnimals) {
                if (a2.getEnergy() == best_energy)
                    candidates.add(a2);
                break;
            }
            Animal firstAnimal;
            Animal secondAnimal;
            // now take second animals that have lower energy than the top one
            // we might get in result something like that: 40, 23, 23, 23
            if (candidates.size() < 2) {
                candidates.add(healthyAnimals.get(0));
                healthyAnimals.remove(0);
                best_energy = candidates.get(1).getEnergy();
                for (Animal a2 : healthyAnimals) {
                    if (a2.getEnergy() == best_energy)
                        candidates.add(a2);
                    break;
                }

                firstAnimal = candidates.get(0);
                candidates.remove(0);
                secondAnimal = candidates.get(r.nextInt(candidates.size()));
            } else {
                int randomNum = r.nextInt(candidates.size());
                firstAnimal = candidates.get(randomNum);
                candidates.remove(randomNum);
                secondAnimal = candidates.get(r.nextInt(candidates.size()));

            }
            Animal childAnimal = new Animal(
                    this.map,
                    posToCheck,
                    firstAnimal.getStartEnergy(),
                    firstAnimal.getMoveEnergy(),
                    firstAnimal,
                    secondAnimal);
            firstAnimal.addEnergy((int) (-firstAnimal.getEnergy() * (0.25)));
            firstAnimal.hasNewKid();
            secondAnimal.hasNewKid();
            secondAnimal.addEnergy((int) (-secondAnimal.getEnergy() * (0.25)));
            childAnimal.setEnergy((int) (firstAnimal.getEnergy() * (0.25)) + (int) (secondAnimal.getEnergy() * (0.25)));
            newAnimals.add(childAnimal);
            alreadyDone.put(posToCheck, true);

        }
        for (Animal na : newAnimals) {
            map.place(na);
            animals.add(na);
        }
    }

    public void spawnGrass() {
        if (this.map.canSpawnMoreGrassInJungle())
            this.map.spawnGrassInJungle();
        if (this.map.canSpawnMoreGrassOutsideJungle())
            this.map.spawnGrassOutsideJungle();
    }

    public void countLifeSpan(){
        for (Animal a : animals) {
            a.survivedEra();
        }
    }


    public void calculateEra() {
        removeDeadAnimals();
        checkFood();
        reproduceAnimals();
        spawnGrass();
        countLifeSpan();
        if (doMagicIfNecessary()){
            for (IAnimalObserver observer : observers)
                observer.magicHappened(this.map.isMapFoldable());
            this.map.magicHappened();
        }


    }

    public void startEngine() {
        this.running = true;
    }

    public void stopEngine() {
        this.running = false;
    }

    public int countAnimals() {
        return this.animals.size();
    }

    public double getAvgEnergy(){
        int sum = 0;
        for (Animal a : this.animals){
            sum += a.getEnergy();
        }
        return (sum*1.0)/this.countAnimals();
    }

    public double getAvgChildrenAmount(){
        int sum = 0;
        for (Animal a : this.animals){
            sum += a.getChildrenAmount();
        }
        return (sum*1.0)/this.countAnimals();
    }

    public double getAvgLifeSpan(){
        int sum = 0;
        for (Animal a : this.deadAnimals){
            sum += a.getLifeSpan();
        }
        if(this.deadAnimals.size()>0)
            return (sum*1.0)/this.deadAnimals.size();
        else
            return 0;
    }

    public boolean doMagicIfNecessary(){
        if (this.map.canMagicHappen() && countAnimals()==5){
            List<Animal> newAnimals = new ArrayList<>();

            for(Animal a : this.animals){
                int randomX = this.r.nextInt(map.getDrawUpperRight().x);
                int randomY = this.r.nextInt(map.getDrawUpperRight().y);

                Animal clonedAnimal = new Animal(this.map, new Vector2d(randomX,randomY),a.getStartEnergy(),a.getMoveEnergy(),a);
                newAnimals.add(clonedAnimal);
            }
            for (Animal na : newAnimals) {
                map.place(na);
                animals.add(na);
            }
            return true;
        }
        return false;

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted -> " + ex);
            }

            while (running) {
                for (Animal a : animals)
                    a.move();

                calculateEra();

                for (IAnimalObserver observer : observers)
                    observer.animalMoved();

                try {
                    Thread.sleep(moveDelay);
                } catch (InterruptedException ex) {
                    System.out.println("Interrupted -> " + ex);
                }
            }
        }

    }
}
