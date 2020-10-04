package shapes;

import java.awt.Color;
import java.awt.Point;

/**
 * An abstract class to represent any polygon with a base
 * and height, i.e. an orientation. A subclass of
 * {@code OrientedPolygon} should be some polygon which has
 * use for storage of base and height, such as use in area
 * formulas. Polygons which have more than one bottom edge
 * do not count as an {@code OrientedPolygon}.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public abstract class OrientedPolygon extends ArbitraryPolygon {
  private static final long serialVersionUID = 1601830295L;

  /**
   * The length of this {@code OrientedPolygon}'s base. The
   * base is the single straight horizontal edge which lies
   * exactly on the bottom edge of the bounding box of this
   * shape, i.e. the lowest y coordinate. No guarantees are
   * made that the {@code Point} list has an edge which
   * corresponds to this attribute.
   */
  private final int base;
  /**
   * The height of this {@code OrientedPolygon}. The height is
   * the vertical distance between the bottom and top edge of
   * the bounding box of the polygon, i.e. the vertical
   * distance between the lowest and highest point. No
   * guarantees are made that the {@code Point} list has a
   * height which corresponds to this attribute.
   */
  private final int height;

  /**
   * Constructs a new {@code OrientedPolygon} with the given
   * coordinates, color, {@code Point} array, base, and
   * height. All {@code Point}s are copied and translated so
   * that the given coordinates equal the top left corner of
   * the bounding box for the polygon specified in the
   * {@code Point} array.
   *
   * @param x      The x coordinate of this
   *               {@code OrientedPolygon}.
   * @param y      The y coordinate of this
   *               {@code OrientedPolygon}.
   * @param color  The {@code Color} to draw this
   *               {@code OrientedPolygon} with.
   * @param points The {@code Point}s which specify a path
   *               that forms a simple polygon.
   * @param base   The length of this
   *               {@code OrientedPolygon}'s base.
   * @param height The height of this {@code OrientedPolygon}.
   */
  public OrientedPolygon(
    int x,
    int y,
    Color color,
    Point[] points,
    int base,
    int height
  ) {
    super(x, y, color, points);
    Shape.checkDimension(base);
    Shape.checkDimension(height);

    this.base = base;
    this.height = height;
  }

  /**
   * Gets the length of this {@code OrientedPolygon}'s base.
   * The base is the single straight horizontal edge which
   * lies exactly on the bottom edge of the bounding box of
   * this shape, i.e. the lowest y coordinate.
   *
   * @return int, the length of this {@code OrientedPolygon}'s
   *         base.
   */
  public int getBase() {
    return this.base;
  }

  /**
   * The height of this {@code OrientedPolygon}. The height is
   * the vertical distance between the bottom and top edge of
   * the bounding box of the polygon, i.e. the vertical
   * distance between the lowest and highest point.
   *
   * @return int, the height of this {@code OrientedPolygon}.
   */
  public int getHeight() {
    return this.height;
  }
}
