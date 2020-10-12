package shapes;

import java.awt.Color;
import java.awt.Point;

/**
 * A class to represent a rectangle: a parallelogram with 90
 * degree internal angles. A {@code Rectangle} can use the
 * perimeter formula {@code width*2 + height*2} instead of a
 * more general formula. The class does not use
 * {@link java.awt.Graphics#fillRect(int, int, int, int)} in
 * order to support rotation provided in superclasses.
 *
 * @author Kevin Qiao
 * @version 1.2
 */
public class Rectangle extends Parallelogram {
  private static final long serialVersionUID = 1602471288L;

  /**
   * Constructs a new {@code Rectangle} with the given
   * coordinates, color, {@code Point} array, rotation, base,
   * and height. All {@code Point}s are copied and translated
   * so that the given coordinates equal the top left corner
   * of the bounding box for the polygon specified in the
   * {@code Point} array with the specified rotation. The
   * first and last {@code Point}s are to be joined by a line
   * segment, in addition to all consecutive elements.
   *
   * @param x        The x coordinate of this
   *                 {@code Rectangle}.
   * @param y        The y coordinate of this
   *                 {@code Rectangle}.
   * @param color    The {@code Color} to draw this
   *                 {@code Rectangle} with.
   * @param points   The {@code Point}s which specify a path
   *                 that forms a rectangle.
   * @param rotation The amount, in degrees, this
   *                 {@code Rectangle} is rotated from its
   *                 intial orientation. A positive value
   *                 results in an anticlockwise rotation.
   * @param base     The length of this {@code Rectangle}'s
   *                 base (the width).
   * @param height   The height of this {@code Rectangle}.
   */
  protected Rectangle(
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
   * Calculates and returns the perimeter of this
   * {@code Shape}. It is almost certainly a better idea to
   * use {@link #getPerimeter()} instead, as that stores the
   * result so the calculation only occurs once.
   * <p>
   * The calculation made is {@code width*2 + height*2}.
   *
   * @return double, the perimeter of this {@code Shape}.
   */
  @Override
  protected double calculatePerimeter() {
    return this.getWidth()*2+this.getHeight()*2;
  }

  /**
   * Gets the width of this {@code Rectangle}. Equal to the
   * length of the base and the opposite base.
   *
   * @return int, the width of this {@code Rectangle}.
   */
  public int getWidth() {
    return this.getBase();
  }

  public static class Builder extends OrientedPolygon.HeightBuilder {
    public Builder() {
      super("Rectangle", "Lengths", null);
    }

    @Override
    public Rectangle build() {
      int x = this.getX();
      int y = this.getY();
      int width = this.getBase();
      int height = this.getHeight();

      Point[] points = new Point[4];
      points[0] = new Point(x, y);
      points[1] = new Point(x+width, y);
      points[2] = new Point(x+width, y-height);
      points[3] = new Point(x, y-height);

      return new Rectangle(
        x,
        y,
        new Color(this.getRed(), this.getGreen(), this.getBlue()),
        points,
        this.getRotation(),
        width,
        height
      );
    }
  }
}
