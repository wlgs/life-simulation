package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal extends AbstractWorldMapElement {
    private MapDirection direction = MapDirection.NORTHWEST;
    private IWorldMap map;
    private int energy;
    private final int moveEnergy;
    private final int startEnergy;
    private final Gene genome;

    public Animal(IWorldMap map, Vector2d initialPosition) {
        super(initialPosition);
        this.map = map;
        this.energy = 30;
        this.startEnergy = 30;
        this.moveEnergy = 2;
        this.genome = new Gene();
        this.observers = new ArrayList<>();
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int startEnergy) {
        super(initialPosition);
        this.map = map;
        this.energy = startEnergy;
        this.startEnergy = startEnergy;
        this.moveEnergy = 2;
        this.genome = new Gene();
        this.observers = new ArrayList<>();
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int startEnergy, int moveEnergy) {
        super(initialPosition);
        this.map = map;
        this.energy = startEnergy;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        MapDirection[] directions = {MapDirection.NORTH,
                MapDirection.NORTHEAST,
                MapDirection.EAST,
                MapDirection.SOUTHEAST,
                MapDirection.SOUTH,
                MapDirection.SOUTHWEST,
                MapDirection.WEST,
                MapDirection.NORTHWEST};
        Random r = new Random();
        this.direction = directions[r.nextInt(8)];
        this.observers = new ArrayList<>();
        this.genome = new Gene();
    }


    public Animal(IWorldMap map, Vector2d initialPosition, int startEnergy, int moveEnergy, Animal parent1, Animal parent2) {
        super(initialPosition);
        this.map = map;
        this.energy = startEnergy;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        MapDirection[] directions = {MapDirection.NORTH,
                MapDirection.NORTHEAST,
                MapDirection.EAST,
                MapDirection.SOUTHEAST,
                MapDirection.SOUTH,
                MapDirection.SOUTHWEST,
                MapDirection.WEST,
                MapDirection.NORTHWEST};
        Random r = new Random();
        this.direction = directions[r.nextInt(8)];
        this.observers = new ArrayList<>();
        this.genome = new Gene(parent1,parent2);
    }


    public MapDirection getDirection() {
        return this.direction;
    }

    public void rotate(int times){
        for (int i =1; i<=times; i++)
            this.direction = this.direction.next();
    }

    public void move() {
        int randomMove = genome.getRotationMove();
        this.addEnergy(-this.moveEnergy);
        if (randomMove !=0 && randomMove != 4){
            rotate(genome.getRotationMove());
            return;
        }
        Vector2d movementChange = this.direction.toUnitVector();
        if(randomMove == 4)
            movementChange = movementChange.opposite();
        Vector2d newPos = this.position.add(movementChange);
        if (map.canMoveTo(newPos))
            positionChanged(newPos);


    }

    void positionChanged(Vector2d newPos) {
        for (IPositionChangeObserver observer : observers)
            observer.positionChanged(this, this.position, newPos);
        this.position = newPos;
    }

    public int getEnergy(){
        return this.energy;
    }

    public void addEnergy(int val){
        this.energy += val;
    }

    public int getStartEnergy(){
        return this.startEnergy;
    }

    public void setEnergy(int val) { this.energy = val;}

    public int getMoveEnergy(){ return this.moveEnergy;}

    public Gene getAnimalGene(){
        return genome;
    }


}
