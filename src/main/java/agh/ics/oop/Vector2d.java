package agh.ics.oop;

import java.util.Vector;

public class Vector2d {
    public final int x;
    public final int y;

    Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    boolean precedes(Vector2d other) {
        return other.x <= this.x && other.y <= this.y;
    }

    boolean follows(Vector2d other) {
        return other.x >= this.x && other.y >= this.y;
    }

    Vector2d upperRight(Vector2d other) {
        int betterX = Math.max(this.x, other.x);
        int betterY = Math.max(this.y, other.y);
        return new Vector2d(betterX, betterY);
    }

    Vector2d lowerLeft(Vector2d other) {
        int betterX = Math.min(this.x, other.x);
        int betterY = Math.min(this.y, other.y);
        return new Vector2d(betterX, betterY);
    }

    Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    Vector2d substract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }
}
