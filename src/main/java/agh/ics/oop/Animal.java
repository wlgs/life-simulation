package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractWorldMapElement {
    private MapDirection direction = MapDirection.NORTHWEST;
    private IWorldMap map;
    private int energy = 30;
    private final Gene genome = new Gene();

    public Animal(IWorldMap map, Vector2d initialPosition) {
        super(initialPosition);
        this.map = map;
        this.observers = new ArrayList<>();
    }
    public Animal(IWorldMap map, Vector2d initialPosition, int energy) {
        super(initialPosition);
        this.map = map;
        this.energy = energy;
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
}
