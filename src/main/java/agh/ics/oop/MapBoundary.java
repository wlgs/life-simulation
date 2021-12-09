package agh.ics.oop;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {


    Comparator<IMapElement> xCompX = new XComparator(true);
    Comparator<IMapElement> xCompY = new XComparator(false);
    SortedSet<IMapElement> sortedSetX = new TreeSet<IMapElement>(xCompX);
    SortedSet<IMapElement> sortedSetY = new TreeSet<IMapElement>(xCompY);

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }

    public void newElement(IMapElement element){
        sortedSetX.add(element);
        sortedSetY.add(element);
    }

    public void removeElement(IMapElement element){
        sortedSetY.remove(element);
        sortedSetX.remove(element);
    }

    public Vector2d getLowerLeft(){
        return new Vector2d(sortedSetX.first().getPosition().x,sortedSetY.first().getPosition().y);
    }

    public Vector2d getUpperRight(){
        return new Vector2d(sortedSetX.last().getPosition().x, sortedSetY.last().getPosition().y);
    }

}
