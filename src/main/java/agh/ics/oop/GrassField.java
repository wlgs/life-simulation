package agh.ics.oop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GrassField  implements IWorldMap, IPositionChangeObserver {
    private final  Map<Vector2d, Grass> grasses = new LinkedHashMap<>();
    private final int maxSpawnRange;
    private final int minSpawnRange;
    private Map<Vector2d, List<Animal>> animals = new LinkedHashMap<>();
    private final Vector2d mapBorderTR = new Vector2d(10,10);
    private final Vector2d mapBorderBL = new Vector2d(0, 0);
    private final boolean foldable = false;


    public GrassField(int amount) {
        this.maxSpawnRange = (int) Math.sqrt(amount * 10);
        this.minSpawnRange = 0;
        for (int i = 0; i < amount; i++) {
            while (true)
                if (spawnGrassRandomly())
                    break;
        }
    }

    public Grass getGrassAt(Vector2d position) {
        return this.grasses.get(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (getBestAnimal(position)==null)
            return getGrassAt(position);
        return getBestAnimal(position);
    }

    public Animal getBestAnimal(Vector2d position){
        int best_energy = -1;
        Animal best_animal = null;
        if(animals.get(position)==null)
            return null;
        for(Animal a : animals.get(position)){
            if (best_energy< a.getEnergy()){
                best_energy = a.getEnergy();
                best_animal = a;
            }
        }
        return best_animal;
    }

    public boolean spawnGrassRandomly() {
        int randomX = (int) (Math.random() * maxSpawnRange) + minSpawnRange;
        int randomY = (int) (Math.random() * maxSpawnRange) + minSpawnRange;
        Vector2d randomPos = new Vector2d(randomX, randomY);
        if (getGrassAt(randomPos) == null) {
            Grass grassToAdd = new Grass(randomPos);
            grasses.put(grassToAdd.getPosition(), grassToAdd);
            return true;
        }
        return false;
    }

    public boolean spawnGrassAt(Vector2d position) {
        if (objectAt(position) == null) {
            Grass grassToAdd = new Grass(position);
            grasses.put(grassToAdd.getPosition(), grassToAdd);
            return true;
        }
        return false;
    }

    @Override
    public void positionChanged(Animal a, Vector2d oldPosition, Vector2d newPosition) {
        List<Animal> animalsOnSquare = this.animals.get(oldPosition);
        List<Animal> newAnimalsOnSquare = this.animals.get(newPosition);
        this.animals.remove(newPosition);
        this.animals.remove(oldPosition);

        animalsOnSquare.remove(a);
        if (objectAt(newPosition) instanceof Grass g){
            a.addEnergy(g.getEnergyValue());
            grasses.remove(newPosition);
        }

        if (newAnimalsOnSquare == null)
            newAnimalsOnSquare = new ArrayList<>();
        newAnimalsOnSquare.add(a);

        this.animals.put(oldPosition, animalsOnSquare);
        this.animals.put(newPosition, newAnimalsOnSquare);
    }

    public Vector2d getDrawLowerLeft() {
        return mapBorderBL;
    }

    public Vector2d getDrawUpperRight() {
        return mapBorderTR;
    }

    public boolean canMoveTo(Vector2d position) {
        return position.precedes(this.mapBorderBL) && position.follows(this.mapBorderTR);
    }

    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            List<Animal> animalsToAdd = this.animals.get(animal.getPosition());
            if (animalsToAdd == null){
                animalsToAdd = new ArrayList<>();
                this.animals.put(animal.getPosition(), animalsToAdd);
            }
            animalsToAdd.add(animal);
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition() + " is not a valid position to place to");
    }

    public List<Animal> animalsAt(Vector2d position){
        return animals.get(position);
    }
}
