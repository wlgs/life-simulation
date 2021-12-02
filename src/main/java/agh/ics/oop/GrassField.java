package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class GrassField extends AbstractWorldMap {
    private final List<Grass> grasses;
    private final int amount;
    private final int maxSpawnRange;
    private final int minSpawnRange;


    public GrassField(int amount) {
        super(Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1, Integer.MIN_VALUE + 1, Integer.MIN_VALUE + 1);
        this.amount = amount;
        this.maxSpawnRange = (int) Math.sqrt(amount * 10);
        this.minSpawnRange = 0;
        grasses = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            while (true)
                if (spawnGrassRandomly())
                    break;
        }
    }

    public boolean isOccupied(Vector2d position) {
        if (super.isOccupied(position))
            return true;
        for (Grass grass : grasses) {
            if (position.equals(grass.getPosition()))
                return true;
        }
        return false;
    }

    public Object objectAt(Vector2d position) {
        if (super.objectAt(position) != null) {
            return super.objectAt(position);
        }
        for (Grass grass : grasses) {
            if (grass.isAt(position))
                return grass;
        }
        return null;
    }

    public boolean canMoveTo(Vector2d position) {
        Object objMovingTo = objectAt(position);
        if (position.precedes(mapBorderBL) &&
                position.follows(mapBorderTR) &&
                !(objMovingTo instanceof Animal)) {
            //animal can move to desired coords
            //now lets check if the move is going to happen on grass
            if (objMovingTo instanceof Grass) {
                // we would want to remove the grass, but first let us spawn another one explanation down below
                // try spawning the grass until it succeeds
                while (true)
                    if (spawnGrassRandomly())
                        break;
                grasses.remove(objMovingTo); // we delete after just to make sure the grass doesn't reappear
            }
            return true;
        }
        return false;
    }

    public boolean spawnGrassRandomly() {
        int randomX = (int) (Math.random() * maxSpawnRange) + minSpawnRange;
        int randomY = (int) (Math.random() * maxSpawnRange) + minSpawnRange;
        Vector2d randomPos = new Vector2d(randomX, randomY);
        if (objectAt(randomPos) == null) {
            grasses.add(new Grass(randomPos));
            return true;
        }
        return false;
    }

    public boolean spawnGrassAt(Vector2d position) {
        if (objectAt(position) == null) {
            grasses.add(new Grass(position));
            return true;
        }
        return false;
    }

    public Vector2d getDrawLowerLeft() {
        Vector2d drawLowerLeft = mapBorderTR;
        for (Vector2d pos : animals.keySet()) {
            drawLowerLeft = drawLowerLeft.lowerLeft(pos);
        }
        for (Grass grass : grasses) {
            drawLowerLeft = drawLowerLeft.lowerLeft(grass.getPosition());
        }
        return drawLowerLeft;
    }

    public Vector2d getDrawUpperRight() {
        Vector2d drawUpperRight = mapBorderBL;
        for (Vector2d pos : animals.keySet()) {
            drawUpperRight = drawUpperRight.upperRight(pos);
        }
        for (Grass grass : grasses) {
            drawUpperRight = drawUpperRight.upperRight(grass.getPosition());
        }
        return drawUpperRight;
    }
}
