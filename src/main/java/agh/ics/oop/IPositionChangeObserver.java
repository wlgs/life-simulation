package agh.ics.oop;

/**
 * Interface that observes change in position
 *
 *
 * @author wlgs
 */

public interface IPositionChangeObserver {
    /**
     * Function that deals with position changes.
     *
     * @param oldPosition The old position
     * @param newPosition The new position
     */
    void positionChanged(Animal a, Vector2d oldPosition, Vector2d newPosition);

}
