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

    public int[] getGenomeArray(){
        return this.genomeArray;
    }

    public void printGenome(){
        System.out.println(Arrays.toString(genomeArray));
    }

    // when we just spawn an animal
    public Gene (){
        Random r = new Random();
        for (int i = 0 ; i <32; i++)
            this.genomeArray[i] = r.nextInt(8);
        Arrays.sort(this.genomeArray);
    }

    public Gene (Animal a1, Animal a2){
        //a1 is the stronger animal (!)
        // firstly we choose the side
        int[] gArr1 = a1.getAnimalGene().getGenomeArray();
        int[] gArr2 = a2.getAnimalGene().getGenomeArray();
        int totalEnergy = a1.getEnergy()+a2.getEnergy();
        int whereToCut = (a1.getEnergy()*32)/ totalEnergy;
        Random r = new Random();

        //left side

        if (r.nextInt(2)==0){
            int idx = 0;
            while (idx < whereToCut){
                genomeArray[idx]=gArr1[idx];
                idx++;
            }
            while (idx <= 31){
                genomeArray[idx]=gArr2[idx];
                idx++;
            }

        }
        //right side
        else{
            whereToCut = 32-whereToCut;

            int idx = 31;
            while (idx > whereToCut){
                genomeArray[idx]=gArr1[idx];
                idx--;
            }
            while (idx >= 0){
                genomeArray[idx]=gArr2[idx];
                idx--;
            }

        }
        Arrays.sort(genomeArray);
    }


}
