package shapes;

import java.awt.Color;
import java.awt.Point;

/**
 * A class to represent a square: a rectangle with equal
 * side lengths. A {@code Square} can use the perimeter
 * formula {@code size*4} and the area formula
 * {@code size*size} instead of more general formulas.
 *
 * @author Kevin Qiao
 * @version 1.2
 */
public class Square extends Rectangle {
  private static final long serialVersionUID = 1602471206L;

  /**
   * Constructs a new {@code Square} with the given
   * coordinates, color, {@code Point} array, rotation, and
   * side length. All {@code Point}s are copied and translated
   * so that the given coordinates equal the top left corner
   * of the bounding box for the polygon specified in the
   * {@code Point} array with the specified rotation. The
   * first and last {@code Point}s are to be joined by a line
   * segment, in addition to all consecutive elements.
   *
   * @param x        The x coordinate of this {@code Square}.
   * @param y        The y coordinate of this {@code Square}.
   * @param color    The {@code Color} to draw this
   *                 {@code Square} with.
   * @param points   The {@code Point}s which specify a path
   *                 that forms a square.
   * @param rotation The amount, in degrees, this
   *                 {@code Square} is rotated from its intial
   *                 orientation. A positive value results in
   *                 an anticlockwise rotation.
   * @param size     The size of this {@code Square}, the
   *                 length of all sides.
   */
  protected Square(
    int x,
    int y,
    Color color,
    Point[] points,
    int rotation,
    int size
  ) {
    super(x, y, color, points, rotation, size, size);
  }

  /**
   * Calculates and returns the area of this {@code Shape}. It
   * is almost certainly a better idea to use
   * {@link #getArea()} instead, as that stores the result so
   * the calculation only occurs once.
   * <p>
   * The formula used is {@code size*size}.
   *
   * @return double, the area of this {@code Shape}.
   */
  @Override
  protected double calculateArea() {
    return this.getSize()*this.getSize();
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
   * Gets the size of this {@code Square}, the length of all
   * sides. Equal to the width, the base length, the height,
   * and the opposite base.
   *
   * @return int, the size of this {@code Square}.
   */
  public int getSize() {
    return this.getBase();
  }

  /**
   * A builder class for a {@code Square}. No arguments are
   * added.
   *
   * @author Kevin Qiao
   * @version 1.0
   */
  public static class Builder extends OrientedPolygon.BaseBuilder {
    /**
     * Creates a {@code Square} builder which uses the length of
     * the base to construct a {@code Square}.
     */
    public Builder() {
      super("Square", "Lengths", null);
    }

    @Override
    public Square build() {
      int x = this.getX();
      int y = this.getY();
      int size = this.getBase();

      Point[] points = new Point[4];
      points[0] = new Point(x, y);
      points[1] = new Point(x+size, y);
      points[2] = new Point(x+size, y-size);
      points[3] = new Point(x, y-size);

      return new Square(
        x,
        y,
        new Color(this.getRed(), this.getGreen(), this.getBlue()),
        points,
        this.getRotation(),
        size
      );
    }
  }
}
