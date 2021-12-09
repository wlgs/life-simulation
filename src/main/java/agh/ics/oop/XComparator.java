package agh.ics.oop;

import java.util.Comparator;

public class XComparator implements Comparator<IMapElement> {

    private boolean compareByX = false;

    public XComparator(boolean compX){
       this.compareByX = compX;
    }
    @Override
    public int compare(IMapElement o1, IMapElement o2){
        Vector2d v1 = o1.getPosition();
        Vector2d v2 = o2.getPosition();

        if (compareByX){
            if (v1.x< v2.x)
                return -1;
            else if (v1.x > v2.x)
                return 1;
            else if (v1.y < v2.y)
                return -1;
            else if (v1.y > v2.y)
                return 1;
            else if (o1 instanceof Animal && o2 instanceof Grass)
                return 1;
            else if (o1 instanceof Grass && o2 instanceof Animal)
                return -1;
        }
        else{
            if (v1.y< v2.y)
                return -1;
            else if (v1.y > v2.y)
                return 1;
            else if (v1.x < v2.x)
                return -1;
            else if (v1.x > v2.x)
                return 1;
            else if (o1 instanceof Animal && o2 instanceof Grass)
                return 1;
            else if (o1 instanceof Grass && o2 instanceof Animal)
                return -1;
        }
        return 0;
    }
}
