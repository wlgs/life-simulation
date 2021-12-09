package agh.ics.oop;
/**
 * The interface responsible for elements created on map.
 * Assumes that Vector2d is defined.
 *
 * @author wlgs
 */
public interface IMapElement {

    /**
     * Returns bool if the element is at position specified in parameter
     *
     * @param position The position checked for the occurrence of element.
     * @return True if the object occurs at the position.
     */
    boolean isAt(Vector2d position);

    /**
     * Retrieves the position of an element.
     *
     *
     * @return Vector2d object of position of the element.
     */
    Vector2d getPosition();

    String getFileName();
}
