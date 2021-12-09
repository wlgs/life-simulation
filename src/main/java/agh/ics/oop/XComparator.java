package agh.ics.oop;

import java.util.Comparator;

public class XComparator implements Comparator<Vector2d> {

    private boolean compareByX = false;

    public XComparator(boolean compX){
       this.compareByX = compX;
    }
    @Override
    public int compare(Vector2d v1, Vector2d v2){
        if (compareByX){
            if (v1.x< v2.x)
                return -1;
            else if (v1.x > v2.x)
                return 1;
            else if (v1.y < v2.y)
                return -1;
            else if (v1.y > v2.y)
                return 1;
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
        }
        return 0;
    }
}
