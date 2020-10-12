package shapes;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * A class to represent a trapezoid: a quadrilateral with at
 * least one pair of parallel lines. A {@code Trapezoid} can
 * use the area formula
 * {@code height * (base+oppositeBase) / 2} instead of a
 * more general formula like the shoelace formula found in
 * {@code ArbitrarySimplePolygon}.
 *
 * @author Kevin Qiao
 * @version 1.2
 */
public class Trapezoid extends OrientedPolygon {
  private static final long serialVersionUID = 1602471183L;

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
   * coordinates, color, {@code Point} array, rotation, base,
   * height, and opposite base. All {@code Point}s are copied
   * and translated so that the given coordinates equal the
   * top left corner of the bounding box for the polygon
   * specified in the {@code Point} array with the specified
   * rotation. The first and last {@code Point}s are to be
   * joined by a line segment, in addition to all consecutive
   * elements.
   *
   * @param x            The x coordinate of this
   *                     {@code Trapezoid}.
   * @param y            The y coordinate of this
   *                     {@code Trapezoid}.
   * @param color        The {@code Color} to draw this
   *                     {@code Trapezoid} with.
   * @param points       The {@code Point}s which specify a
   *                     path that forms a trapezoid.
   * @param rotation     The amount, in degrees, this
   *                     {@code Trapezoid} is rotated from its
   *                     intial orientation. A positive value
   *                     results in an anticlockwise rotation.
   * @param base         The length of this
   *                     {@code Trapezoid}'s base.
   * @param height       The height of this {@code Trapezoid}.
   * @param oppositeBase The length of the opposite edge
   *                     parallel to this {@code Trapezoid}'s
   *                     base.
   */
  protected Trapezoid(
    int x,
    int y,
    Color color,
    Point[] points,
    int rotation,
    int base,
    int height,
    int oppositeBase
  ) {
    super(x, y, color, points, rotation, base, height);
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

  /**
   * A builder class for a {@code Trapezoid} using an
   * offset. Adds an argument for top edge length.
   *
   * @author Kevin Qiao
   * @version 1.0
   */
  public static class OffsetBuilder extends OrientedPolygon.OffsetBuilder {
    /** The {@code String} to represent the argument of top edge length. */
    private static final String OPPOSITE_BASE = "Top Edge Length";
    /**
     * The set of arguments this {@code OffsetBuilder} requires
     * in addition to inherited arguments.
     */
    private static final LinkedHashSet<Arg> REQUIRED_ARGS =
      new LinkedHashSet<>(
        Arrays.asList(new Arg(OffsetBuilder.OPPOSITE_BASE, 0))
      );

    /**
     * Creates a {@code Trapezoid} builder which uses the length
     * of the base, height, horizontal offset between top and
     * bottom edges, and length of the top edge to construct a
     * {@code Trapezoid}.
     */
    public OffsetBuilder() {
      super("Trapezoid", "Offset", OffsetBuilder.REQUIRED_ARGS);
    }

    @Override
    public Trapezoid build() {
      int x = this.getX();
      int y = this.getY();
      int base = this.getBase();
      int height = this.getHeight();
      int offset = this.getOffset();
      int oppositeBase = this.getOppositeBase();

      Point[] points = new Point[4];
      points[0] = new Point(x, y-height);
      points[1] = new Point(x+base, y-height);
      points[2] = new Point(x+oppositeBase+offset, y);
      points[3] = new Point(x+offset, y);

      return new Trapezoid(
        x,
        y,
        new Color(this.getRed(), this.getGreen(), this.getBlue()),
        points,
        this.getRotation(),
        base,
        height,
        oppositeBase
      );
    }

    /**
     * Sets the top edge length argument of this
     * {@code OffsetBuilder}.
     *
     * @param oppositeBase The top edge length argument of this
     *                     {@code OffsetBuilder}.
     * @return {@code OffsetBuilder}, this
     *         {@code OffsetBuilder}.
     */
    public OffsetBuilder withOppositeBase(int oppositeBase) {
      this.withArg(OffsetBuilder.OPPOSITE_BASE, oppositeBase);
      return this;
    }

    /**
     * Gets the top edge length argument of this
     * {@code OffsetBuilder}.
     *
     * @return int, the top edge length argument of this
     *         {@code OffsetBuilder}.
     */
    public int getOppositeBase() {
      return this.getArg(OffsetBuilder.OPPOSITE_BASE);
    }
  }

  /**
   * A builder class for a {@code Trapezoid} using an angle.
   * Adds an argument for top edge length.
   *
   * @author Kevin Qiao
   * @version 1.0
   */
  public static class AngleBuilder extends OrientedPolygon.AngleBuilder {
    /** The {@code String} to represent the argument of top edge length. */
    private static final String OPPOSITE_BASE = "Top Edge";
    /**
     * The set of arguments this {@code AngleBuilder} requires
     * in addition to inherited arguments.
     */
    private static final LinkedHashSet<Arg> REQUIRED_ARGS =
      new LinkedHashSet<>(
        Arrays.asList(new Arg(AngleBuilder.OPPOSITE_BASE, 0))
      );

    /**
     * Creates a {@code Trapezoid} builder which uses the length
     * of the base, height, bottom left (with rotation of 0)
     * angle, and length of the top edge to construct a
     * {@code Trapezoid}.
     */
    public AngleBuilder() {
      super("Trapezoid", "Angle", AngleBuilder.REQUIRED_ARGS);
    }

    @Override
    public Trapezoid build() {
      int x = this.getX();
      int y = this.getY();
      int base = this.getBase();
      int height = this.getHeight();
      int oppositeBase = this.getOppositeBase();

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
      points[2] = new Point(x+oppositeBase+offset, y);
      points[3] = new Point(x+offset, y);

      return new Trapezoid(
        x,
        y,
        new Color(this.getRed(), this.getGreen(), this.getBlue()),
        points,
        this.getRotation(),
        base,
        height,
        oppositeBase
      );
    }

    /**
     * Sets the top edge length argument of this
     * {@code AngleBuilder}.
     *
     * @param oppositeBase The top edge length argument of this
     *                     {@code AngleBuilder}.
     * @return {@code AngleBuilder}, this {@code AngleBuilder}.
     */
    public AngleBuilder withOppositeBase(int oppositeBase) {
      this.withArg(AngleBuilder.OPPOSITE_BASE, oppositeBase);
      return this;
    }

    /**
     * Gets the top edge length argument of this
     * {@code AngleBuilder}.
     *
     * @return int, the top edge length argument of this
     *         {@code AngleBuilder}.
     */
    public int getOppositeBase() {
      return this.getArg(AngleBuilder.OPPOSITE_BASE);
    }
  }
}
