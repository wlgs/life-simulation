package agh.ics.oop;

import java.util.Comparator;

public class AnimalsComparator implements Comparator<Animal> {

    @Override
    public int compare(Animal a1, Animal a2){
        if(a1.getEnergy()>a2.getEnergy())
            return -1;
        if (a1.getEnergy()<a2.getEnergy())
            return 1;
        return 0;
    }
}
