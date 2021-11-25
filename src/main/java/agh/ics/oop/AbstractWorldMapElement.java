package agh.ics.oop;


public abstract class AbstractWorldMapElement implements IMapElement {
    protected Vector2d position;

    public AbstractWorldMapElement(Vector2d pos){
        this.position = pos;
    }

    public boolean isAt(Vector2d position){
        return position.equals(this.position);
    }

    public Vector2d getPosition(){
        return this.position;
    }
}
