package geometrygraphics;

import java.awt.Graphics;
import java.awt.Rectangle;

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

  /**
   * Gets the clip rect (bounding box) required to update this
   * object when dirty.
   *
   * @return Rectangle, the clip rect/bounding box of this
   *         object.
   */
  public Rectangle getBounds();
}
