package geometrygraphics;

/**
 * An interface which represents the ability for an objcet
 * to be translated in 2D cartesian space.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public interface Translateable {
  /**
   * Translates this object by the given change to x and y
   * coordinates.
   *
   * @param dx The change in x coordinates to apply to this
   *           object.
   * @param dy The change in y coordinates to apply to this
   *           object.
   */
  public void translate(int dx, int dy);
}
