package agh.ics.oop;


import java.util.List;

public abstract class AbstractWorldMapElement implements IMapElement {
    protected Vector2d position;
    protected List<IPositionChangeObserver> observers;

    public AbstractWorldMapElement(Vector2d pos){
        this.position = pos;
    }

    public boolean isAt(Vector2d position){
        return position.equals(this.position);
    }

    public Vector2d getPosition(){
        return this.position;
    }

    void addObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }

    void removeObserver(IPositionChangeObserver observer) {
        this.observers.remove(observer);
    }
}
