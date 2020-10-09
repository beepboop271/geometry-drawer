package shapes;

import java.awt.Color;
import java.awt.Point;

/**
 * A class to represent a polygon with 3 sides. A
 * {@code Triangle} can use the area formula
 * {@code base * height / 2} instead of a more general
 * formula like the shoelace formula found in
 * {@code ArbitrarySimplePolygon}.
 *
 * @author Kevin Qiao
 * @version 1.2
 */
public class Triangle extends OrientedPolygon {
  private static final long serialVersionUID = 1602214330L;

  /**
   * Constructs a new {@code Triangle} with the given
   * coordinates, color, {@code Point} array, rotation, base,
   * and height. All {@code Point}s are copied and translated
   * so that the given coordinates equal the top left corner
   * of the bounding box for the polygon specified in the
   * {@code Point} array with the specified rotation. The
   * first and last {@code Point}s are to be joined by a line
   * segment, in addition to all consecutive elements.
   *
   * @param x        The x coordinate of this
   *                 {@code Triangle}.
   * @param y        The y coordinate of this
   *                 {@code Triangle}.
   * @param color    The {@code Color} to draw this
   *                 {@code Triangle} with.
   * @param points   The {@code Point}s which specify a path
   *                 that forms a triangle.
   * @param rotation The amount, in degrees, this
   *                 {@code Triangle} is rotated from its
   *                 intial orientation. A positive value
   *                 results in an anticlockwise rotation.
   * @param base     The length of this {@code Triangle}'s
   *                 base.
   * @param height   The height of this {@code Triangle}.
   */
  public Triangle(
    int x,
    int y,
    Color color,
    Point[] points,
    int rotation,
    int base,
    int height
  ) {
    super(x, y, color, points, rotation, base, height);
  }

  /**
   * Calculates and returns the area of this {@code Shape}. It
   * is almost certainly a better idea to use
   * {@link #getArea()} instead, as that stores the result so
   * the calculation only occurs once.
   * <p>
   * The formula used is {@code base * height / 2}.
   *
   * @return double, the area of this {@code Shape}.
   */
  @Override
  protected double calculateArea() {
    return this.getBase()*this.getHeight()/2.0;
  }
}
