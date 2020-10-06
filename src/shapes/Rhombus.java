package shapes;

import java.awt.Color;
import java.awt.Point;

/**
 * A class to represent a rhombus: a parallelogram with
 * equal side lengths. A {@code Rhombus} can use the
 * perimeter formula {@code size*4} instead of a more
 * general formula.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class Rhombus extends Parallelogram {
  private static final long serialVersionUID = 1601961814L;

  /**
   * Constructs a new {@code Rhombus} with the given
   * coordinates, color, {@code Point} array, side length, and
   * height. All {@code Point}s are copied and translated so
   * that the given coordinates equal the top left corner of
   * the bounding box for the polygon specified in the
   * {@code Point} array. The first and last {@code Point}s
   * are to be joined by a line segment, in addition to all
   * consecutive elements.
   *
   * @param x      The x coordinate of this {@code Rhombus}.
   * @param y      The y coordinate of this {@code Rhombus}.
   * @param color  The {@code Color} to draw this
   *               {@code Rhombus} with.
   * @param points The {@code Point}s which specify a path
   *               that forms a rhombus.
   * @param size   The size of this {@code Rhombus}, the
   *               length of all sides.
   * @param height The height of this {@code Rhombus}.
   */
  public Rhombus(
    int x,
    int y,
    Color color,
    Point[] points,
    int size,
    int height
  ) {
    super(x, y, color, points, size, height);
  }

  /**
   * Calculates and returns the perimeter of this
   * {@code Shape}. It is almost certainly a better idea to
   * use {@link #getPerimeter()} instead, as that stores the
   * result so the calculation only occurs once.
   * <p>
   * The calculation made is {@code size*4}.
   *
   * @return double, the perimeter of this {@code Shape}.
   */
  @Override
  protected double calculatePerimeter() {
    return this.getSize()*4;
  }

  /**
   * Gets the size of this {@code Rhombus}, the length of all
   * sides. Equal to the length of the base and the opposite
   * base.
   *
   * @return int, the size of this {@code Rhombus}.
   */
  public int getSize() {
    return this.getBase();
  }
}
