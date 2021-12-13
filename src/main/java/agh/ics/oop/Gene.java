package agh.ics.oop;

import java.util.*;

public class Gene {

    private final int[] genomeArray = new int[32];


    public int getRotationMove(){
        Random r = new Random();
        int maxIndex = 31;
        int result = r.nextInt(maxIndex + 1);
        return genomeArray[result];
    }

    // when we just spawn an animal
    public Gene (){
        Random r = new Random();
        for (int i = 0 ; i <32; i++)
            this.genomeArray[i] = r.nextInt(8);
        Arrays.sort(this.genomeArray); // so we have the sorted one!
    }


}
