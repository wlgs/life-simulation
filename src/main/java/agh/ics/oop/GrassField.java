package agh.ics.oop;

import java.util.*;

public class GrassField implements IWorldMap, IPositionChangeObserver {
    private final Map<Vector2d, Grass> grasses = new LinkedHashMap<>();
    private final int maxSpawnRange;
    private final int minSpawnRange;
    private Map<Vector2d, List<Animal>> animals = new LinkedHashMap<>();
    private final Vector2d mapBorderTR;
    private final Vector2d mapBorderBL;
    private final int plantEnergy;
    private final float jungleRatio;
    private final Vector2d jungleBorderTR;
    private final Vector2d jungleBorderBL;
    private final int jungleSize;
    private final int mapWidth;
    private final int mapHeight;
    private int totalGrassInJungle = 0;
    private int totalGrassOutsideJungle = 0;
    private Random r = new Random();

    public final boolean foldable = false;


    public GrassField(int amount) {
        this.mapBorderBL = new Vector2d(0, 0);
        this.mapBorderTR = new Vector2d(10, 10);
        this.mapHeight = 11;
        this.mapWidth = 11;
        this.maxSpawnRange = (int) Math.sqrt(amount * 10);
        this.minSpawnRange = 0;
        this.plantEnergy = 10;
        this.jungleRatio = 0;
        this.jungleSize = 0;
        this.jungleBorderBL = new Vector2d(0, 0);
        this.jungleBorderTR = new Vector2d(0, 0);
        for (int i = 0; i < amount; i++) {
            while (true)
                if (spawnGrassRandomly())
                    break;
        }
    }

    public GrassField(int mapWidth, int mapHeight, int plantEnergy, float jungleRatio) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.maxSpawnRange = (int) Math.sqrt(2 * 10);
        this.minSpawnRange = 0;
        this.mapBorderBL = new Vector2d(0, 0);
        this.mapBorderTR = new Vector2d(mapWidth - 1, mapHeight - 1);
        this.plantEnergy = plantEnergy;
        this.jungleRatio = jungleRatio;
        this.jungleSize = (int) ((Math.sqrt(jungleRatio)) * (this.mapBorderTR.x + 1));
        int diffX = this.mapBorderTR.x + 1 - jungleSize;
        int diffY = this.mapBorderTR.y + 1 - jungleSize;
        this.jungleBorderTR = new Vector2d((int) Math.ceil(this.mapBorderTR.x - diffX / 2.0), (int) Math.ceil(this.mapBorderTR.y - diffY / 2.0));
        this.jungleBorderBL = new Vector2d((int) Math.ceil(this.mapBorderBL.x + diffX / 2.0), (int) Math.ceil(this.mapBorderBL.y + diffY / 2.0));
    }

    public void substractTotalGrassInJungle(int v) {
        this.totalGrassInJungle -= v;
    }

    public void substractTotalGrassOutsideJungle(int v) {
        this.totalGrassOutsideJungle -= v;
    }

    public boolean canSpawnMoreGrassInJungle() {
        return this.totalGrassInJungle < this.jungleSize * this.jungleSize;
    }

    public boolean canSpawnMoreGrassOutsideJungle() {
        return this.totalGrassOutsideJungle < (this.mapWidth * this.mapHeight) - (this.jungleSize * this.jungleSize);
    }

    public void spawnGrassInJungle() {
        Vector2d randomPos;
        do {
            int randomX = this.r.nextInt(this.jungleBorderTR.x - this.jungleBorderBL.x + 1) + this.jungleBorderBL.x;
            int randomY = this.r.nextInt(this.jungleBorderTR.y - this.jungleBorderBL.y + 1) + this.jungleBorderBL.y;
            randomPos = new Vector2d(randomX, randomY);
        } while (getGrassAt(randomPos) != null);
        Grass grassToAdd = new Grass(randomPos, this.plantEnergy);
        grasses.put(randomPos, grassToAdd);
        this.totalGrassInJungle += 1;
    }

    public void spawnGrassOutsideJungle() {
        Vector2d randomPos;
        do {
            int randomX = this.r.nextInt(this.mapBorderTR.x + 1);
            int randomY = this.r.nextInt(this.mapBorderTR.y + 1);
            randomPos = new Vector2d(randomX, randomY);
        } while (isJungleTile(randomPos) || getGrassAt(randomPos) != null);
        Grass grassToAdd = new Grass(randomPos, plantEnergy);
        grasses.put(randomPos, grassToAdd);
        this.totalGrassOutsideJungle += 1;
    }

    public boolean isJungleTile(Vector2d position) {
        return position.precedes(this.jungleBorderBL) && position.follows(this.jungleBorderTR);
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
        if (animals.get(position) == null)
            return getGrassAt(position);
        if (animals.get(position).size() == 0)
            return getGrassAt(position);
        return getBestAnimal(position);
    }

    public Animal getBestAnimal(Vector2d position) {
        List<Animal> animalsOnSquare = this.animals.get(position);
        if (animalsOnSquare == null || animalsOnSquare.size() == 0)
            return null;
        Comparator<Animal> animalComp = new AnimalsComparator();
        animalsOnSquare.sort(animalComp);
        return animalsOnSquare.get(0);
    }

    public List<Animal> getBestAnimals(Vector2d position) {
        List<Animal> animalsOnSquare = this.animals.get(position);
        if (animalsOnSquare == null || animalsOnSquare.size() == 0)
            return null;
        Comparator<Animal> animalComp = new AnimalsComparator();
        animalsOnSquare.sort(animalComp);
        int best_energy = animalsOnSquare.get(0).getEnergy();
        List<Animal> bestAnimals = new ArrayList<>();
        for (Animal a : animalsOnSquare) {
            if (a.getEnergy() == best_energy)
                bestAnimals.add(a);
            else
                break;
        }
        return bestAnimals;
    }

    public boolean spawnGrassRandomly() {
        int randomX = (int) (Math.random() * maxSpawnRange) + minSpawnRange;
        int randomY = (int) (Math.random() * maxSpawnRange) + minSpawnRange;
        Vector2d randomPos = new Vector2d(randomX, randomY);
        if (getGrassAt(randomPos) == null) {
            Grass grassToAdd = new Grass(randomPos, plantEnergy);
            grasses.put(grassToAdd.getPosition(), grassToAdd);
            return true;
        }
        return false;
    }

    public boolean spawnGrassAt(Vector2d position) {
        if (objectAt(position) == null) {
            Grass grassToAdd = new Grass(position, plantEnergy);
            grasses.put(grassToAdd.getPosition(), grassToAdd);
            return true;
        }
        return false;
    }

    public void removeAnimalFromMap(Animal a) {
        List<Animal> animalsOnSquare = this.animals.get(a.getPosition());
        animalsOnSquare.remove(a);
    }

    public void removeGrassFromMap(Grass g) {
        this.grasses.remove(g.getPosition());
    }

    @Override
    public void positionChanged(Animal a, Vector2d oldPosition, Vector2d newPosition) {
        List<Animal> animalsOnSquare = this.animals.get(oldPosition);
        List<Animal> newAnimalsOnSquare = this.animals.computeIfAbsent(newPosition, k -> new ArrayList<>());

        animalsOnSquare.remove(a);
        newAnimalsOnSquare.add(a);
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
            List<Animal> animalsListToAdd = this.animals.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>());
            animalsListToAdd.add(animal);
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition() + " is not a valid position to place to");
    }

    public List<Animal> animalsAt(Vector2d position) {
        return animals.get(position);
    }
}
