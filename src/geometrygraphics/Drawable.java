package geometrygraphics;

import java.awt.Graphics;

/**
 * An interface which represents the ability for an object
 * to be drawn to a {@code Graphics} object.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public interface Drawable {
  /**
   * Draws this object onto the specified {@code Graphics}
   * object.
   *
   * @param g The {@code Graphics} object to draw to.
   */
  public void draw(Graphics g);
}
