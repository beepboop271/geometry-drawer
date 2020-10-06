package shapes;

import java.awt.Color;
import java.awt.Point;

/**
 * A class to represent a parallelogram: a quadrilateral
 * with two pairs of parallel lines. A {@code Parallelogram}
 * can use the area formula {@code base * height} instead of
 * a more general formula like the formula in
 * {@code Trapezoid}.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class Parallelogram extends Trapezoid {
  private static final long serialVersionUID = 1601942399L;

  /**
   * Constructs a new {@code Parallelogram} with the given
   * coordinates, color, {@code Point} array, base, and
   * height. All {@code Point}s are copied and translated so
   * that the given coordinates equal the top left corner of
   * the bounding box for the polygon specified in the
   * {@code Point} array. The first and last {@code Point}s
   * are to be joined by a line segment, in addition to all
   * consecutive elements.
   *
   * @param x      The x coordinate of this
   *               {@code Parallelogram}.
   * @param y      The y coordinate of this
   *               {@code Parallelogram}.
   * @param color  The {@code Color} to draw this
   *               {@code Parallelogram} with.
   * @param points The {@code Point}s which specify a path
   *               that forms a parallelogram.
   * @param base   The length of this {@code Parallelogram}'s
   *               base.
   * @param height The height of this {@code Parallelogram}.
   */
  public Parallelogram(
    int x,
    int y,
    Color color,
    Point[] points,
    int base,
    int height
  ) {
    super(x, y, color, points, base, height, base);
  }

  /**
   * Calculates and returns the area of this {@code Shape}. It
   * is almost certainly a better idea to use
   * {@link #getArea()} instead, as that stores the result so
   * the calculation only occurs once.
   * <p>
   * The formula used is {@code base * height}.
   *
   * @return double, the area of this {@code Shape}.
   */
  @Override
  protected double calculateArea() {
    return this.getBase()*this.getHeight();
  }
}
