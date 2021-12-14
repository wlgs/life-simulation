package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractWorldMapElement {
    private MapDirection direction = MapDirection.NORTHWEST;
    private IWorldMap map;
    private int energy;
    private final int moveEnergy;
    private final Gene genome = new Gene();

    public Animal(IWorldMap map, Vector2d initialPosition) {
        super(initialPosition);
        this.map = map;
        this.energy = 30;
        this.moveEnergy = 2;
        this.observers = new ArrayList<>();
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int startEnergy) {
        super(initialPosition);
        this.map = map;
        this.energy = startEnergy;
        this.moveEnergy = 2;
        this.observers = new ArrayList<>();
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int startEnergy, int moveEnergy) {
        super(initialPosition);
        this.map = map;
        this.energy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.observers = new ArrayList<>();
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
        this.addEnergy(-1);
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

    public void setEnergy(int val) { this.energy = val;}

    public Gene getAnimalGene(){
        return genome;
    }


}
