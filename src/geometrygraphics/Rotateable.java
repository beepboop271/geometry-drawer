package geometrygraphics;

/**
 * An interface which represents the ability for an object
 * to be rotated in a 2D space. Uses degrees.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public interface Rotateable {
  /**
   * Rotates this object so that its rotation is equal to the
   * specified amount of degrees.
   *
   * @param degrees The rotation, in degrees, this object
   *                should be rotated to.
   */
  public void rotateTo(int degrees);

  /**
   * Rotates this object by the given change in rotation, in
   * degrees.
   *
   * @param degreeChange The rotation, in degrees, this object
   *                     should be rotated by.
   */
  public void rotateBy(int degreeChange);

  /**
   * Gets this object's rotation, in degrees.
   *
   * @return int, the rotation, in degrees, this object is
   *         rotated by.
   */
  public int getRotation();
}
