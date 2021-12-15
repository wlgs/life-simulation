package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeneTest {

    @Test
    public void geneGenTest() {
        GrassField map = new GrassField(23);
        Animal a1 = new Animal(map, new Vector2d(1,1), 50);
        Animal a2 = new Animal(map, new Vector2d(1,1), 50);

        Gene reproduced = new Gene(a1,a2);
        a1.getAnimalGene().printGenome();
        a2.getAnimalGene().printGenome();
        reproduced.printGenome();

        // just for seeing if it actually works, test would be a waste of time to implement I guess

    }
}
