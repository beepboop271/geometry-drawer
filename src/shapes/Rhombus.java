package shapes;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * A class to represent a rhombus: a parallelogram with
 * equal side lengths. A {@code Rhombus} can use the
 * perimeter formula {@code size*4} instead of a more
 * general formula.
 *
 * @author Kevin Qiao
 * @version 1.2
 */
public class Rhombus extends Parallelogram {
  private static final long serialVersionUID = 1602471272L;

  /**
   * Constructs a new {@code Rhombus} with the given
   * coordinates, color, {@code Point} array, rotation, side
   * length, and height. All {@code Point}s are copied and
   * translated so that the given coordinates equal the top
   * left corner of the bounding box for the polygon specified
   * in the {@code Point} array with the specified rotation.
   * The first and last {@code Point}s are to be joined by a
   * line segment, in addition to all consecutive elements.
   *
   * @param x        The x coordinate of this {@code Rhombus}.
   * @param y        The y coordinate of this {@code Rhombus}.
   * @param color    The {@code Color} to draw this
   *                 {@code Rhombus} with.
   * @param points   The {@code Point}s which specify a path
   *                 that forms a rhombus.
   * @param rotation The amount, in degrees, this
   *                 {@code Rhombus} is rotated from its
   *                 intial orientation. A positive value
   *                 results in an anticlockwise rotation.
   * @param size     The size of this {@code Rhombus}, the
   *                 length of all sides.
   * @param height   The height of this {@code Rhombus}.
   */
  protected Rhombus(
    int x,
    int y,
    Color color,
    Point[] points,
    int rotation,
    int size,
    int height
  ) {
    super(x, y, color, points, rotation, size, height);
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

  /**
   * A builder class for a {@code Rhombus}. Adds an argument
   * for bottom-left (with rotation of 0) angle in addition to
   * base length.
   *
   * @author Kevin Qiao
   * @version 1.0
   */
  public static class Builder extends OrientedPolygon.BaseBuilder {
    /** The {@code String} to represent the argument of angle. */
    private static final String ANGLE = "Angle";
    /**
     * The set of arguments this {@code Builder} requires in
     * addition to inherited arguments.
     */
    private static final LinkedHashSet<Arg> REQUIRED_ARGS =
      new LinkedHashSet<>(
        Arrays.asList(new Arg(Builder.ANGLE, 1, 179))
      );

    /**
     * Creates a {@code Rhombus} builder which uses base length
     * and angle to construct a {@code Rhombus}.
     */
    public Builder() {
      super("Rhombus", "Angle", Builder.REQUIRED_ARGS);
    }

    @Override
    public Rhombus build() {
      int x = this.getX();
      int y = this.getY();
      int size = this.getBase();

      double angle = Math.toRadians(this.getAngle());
      int height = (int)(size*Math.sin(angle));
      int offset = (int)(size*Math.cos(angle));

      Point[] points = new Point[4];
      points[0] = new Point(x, y-height);
      points[1] = new Point(x+size, y-height);
      points[2] = new Point(x+size+offset, y);
      points[3] = new Point(x+offset, y);

      return new Rhombus(
        x,
        y,
        new Color(this.getRed(), this.getGreen(), this.getBlue()),
        points,
        this.getRotation(),
        size,
        height
      );
    }

    /**
     * Sets the angle argument of this {@code Builder}.
     *
     * @param angle The angle argument of this {@code Builder}.
     * @return {@code Builder}, this {@code Builder}.
     */
    public Builder withAngle(int angle) {
      this.withArg(Builder.ANGLE, angle);
      return this;
    }

    /**
     * Gets the angle argument of this {@code Builder}.
     *
     * @return int, the angle argument of this {@code Builder}.
     */
    public int getAngle() {
      return this.getArg(Builder.ANGLE);
    }
  }
}
