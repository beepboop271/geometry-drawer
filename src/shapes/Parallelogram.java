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
 * @version 1.2
 */
public class Parallelogram extends Trapezoid {
  private static final long serialVersionUID = 1602471308L;

  /**
   * Constructs a new {@code Parallelogram} with the given
   * coordinates, color, {@code Point} array, rotation, base,
   * and height. All {@code Point}s are copied and translated
   * so that the given coordinates equal the top left corner
   * of the bounding box for the polygon specified in the
   * {@code Point} array with the specified rotation. The
   * first and last {@code Point}s are to be joined by a line
   * segment, in addition to all consecutive elements.
   *
   * @param x        The x coordinate of this
   *                 {@code Parallelogram}.
   * @param y        The y coordinate of this
   *                 {@code Parallelogram}.
   * @param color    The {@code Color} to draw this
   *                 {@code Parallelogram} with.
   * @param points   The {@code Point}s which specify a path
   *                 that forms a parallelogram.
   * @param rotation The amount, in degrees, this
   *                 {@code Parallelogram} is rotated from its
   *                 intial orientation. A positive value
   *                 results in an anticlockwise rotation.
   * @param base     The length of this
   *                 {@code Parallelogram}'s base.
   * @param height   The height of this {@code Parallelogram}.
   */
  protected Parallelogram(
    int x,
    int y,
    Color color,
    Point[] points,
    int rotation,
    int base,
    int height
  ) {
    super(x, y, color, points, rotation, base, height, base);
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

  public static class OffsetBuilder extends OrientedPolygon.OffsetBuilder {
    public OffsetBuilder() {
      super("Parallelogram", "Offset", null);
    }

    @Override
    public Parallelogram build() {
      int x = this.getX();
      int y = this.getY();
      int base = this.getBase();
      int height = this.getHeight();
      int offset = this.getOffset();

      Point[] points = new Point[4];
      points[0] = new Point(x, y-height);
      points[1] = new Point(x+base, y-height);
      points[2] = new Point(x+base+offset, y);
      points[3] = new Point(x+offset, y);

      return new Parallelogram(
        x,
        y,
        new Color(this.getRed(), this.getGreen(), this.getBlue()),
        points,
        this.getRotation(),
        base,
        height
      );
    }
  }

  public static class AngleBuilder extends OrientedPolygon.AngleBuilder {
    public AngleBuilder() {
      super("Parallelogram", "Angle", null);
    }

    @Override
    public Parallelogram build() {
      int x = this.getX();
      int y = this.getY();
      int base = this.getBase();
      int height = this.getHeight();

      int offset;
      if (this.getAngle() == 90) {
        offset = 0;
      } else {
        double angle = Math.toRadians(this.getAngle());
        offset = (int)(height/Math.tan(angle));
      }

      Point[] points = new Point[4];
      points[0] = new Point(x, y-height);
      points[1] = new Point(x+base, y-height);
      points[2] = new Point(x+base+offset, y);
      points[3] = new Point(x+offset, y);

      return new Parallelogram(
        x,
        y,
        new Color(this.getRed(), this.getGreen(), this.getBlue()),
        points,
        this.getRotation(),
        base,
        height
      );
    }
  }
}
