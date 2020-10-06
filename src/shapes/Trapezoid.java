package shapes;

import java.awt.Color;
import java.awt.Point;

/**
 * A class to represent a trapezoid: a quadrilateral with at
 * least one pair of parallel lines. A {@code Trapezoid} can
 * use the area formula
 * {@code height * (base+oppositeBase) / 2} instead of a
 * more general formula like the shoelace formula found in
 * {@code ArbitrarySimplePolygon}.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class Trapezoid extends OrientedPolygon {
  private static final long serialVersionUID = 1601939305L;

  /**
   * The length of the opposite edge parallel to this
   * {@code Trapezoid}'s base.
   * <p>
   * No guarantees are made that the {@code Point} list has an
   * edge which corresponds to this attribute.
   *
   * @see shapes.OrientedPolygon#getBase()
   */
  private final int oppositeBase;

  /**
   * Constructs a new {@code Trapezoid} with the given
   * coordinates, color, {@code Point} array, base, height,
   * and opposite base. All {@code Point}s are copied and
   * translated so that the given coordinates equal the top
   * left corner of the bounding box for the polygon specified
   * in the {@code Point} array. The first and last
   * {@code Point}s are to be joined by a line segment, in
   * addition to all consecutive elements.
   *
   * @param x            The x coordinate of this
   *                     {@code Trapezoid}.
   * @param y            The y coordinate of this
   *                     {@code Trapezoid}.
   * @param color        The {@code Color} to draw this
   *                     {@code Trapezoid} with.
   * @param points       The {@code Point}s which specify a
   *                     path that forms a trapezoid.
   * @param base         The length of this
   *                     {@code Trapezoid}'s base.
   * @param height       The height of this {@code Trapezoid}.
   * @param oppositeBase The length of the opposite edge
   *                     parallel to this {@code Trapezoid}'s
   *                     base.
   */
  public Trapezoid(
    int x,
    int y,
    Color color,
    Point[] points,
    int base,
    int height,
    int oppositeBase
  ) {
    super(x, y, color, points, base, height);
    Shape.checkDimension(oppositeBase);

    this.oppositeBase = oppositeBase;
  }

  /**
   * Calculates and returns the area of this {@code Shape}. It
   * is almost certainly a better idea to use
   * {@link #getArea()} instead, as that stores the result so
   * the calculation only occurs once.
   * <p>
   * The formula used is
   * {@code height * (base+oppositeBase) / 2}.
   *
   * @return double, the area of this {@code Shape}.
   */
  @Override
  protected double calculateArea() {
    return this.getHeight()*(this.getBase()+this.oppositeBase)/2.0;
  }

  /**
   * Gets the length of the opposite edge parallel to this
   * {@code Trapezoid}'s base.
   * <p>
   * No guarantees are made that the {@code Point} list has an
   * edge which corresponds to this attribute.
   *
   * @see shapes.OrientedPolygon#getBase()
   * @return int, the length of the edge parallel to the base.
   */
  public int getOppositeBase() {
    return this.oppositeBase;
  }
}
