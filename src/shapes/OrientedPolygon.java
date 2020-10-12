package shapes;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * An abstract class to represent any polygon with a base
 * and height, i.e. an orientation. A subclass of
 * {@code OrientedPolygon} should be some polygon which has
 * use for storage of base and height, such as use in area
 * formulas.
 * <p>
 * An {@code OrientedPolygon} can be created with a rotated
 * {@code Point} list, as base and height should only be
 * used for rotation invariant calculations such as area. As
 * long as {@code base} and {@code height} are valid for
 * some other rotation of the given polygon, the polygon is
 * valid.
 *
 * @author Kevin Qiao
 * @version 1.2
 */
public abstract class OrientedPolygon extends ArbitrarySimplePolygon {
  private static final long serialVersionUID = 1602471327L;

  /**
   * The length of this {@code OrientedPolygon}'s base. The
   * base is the single straight horizontal edge which lies
   * exactly on the bottom edge of the bounding box of this
   * shape, i.e. the lowest y coordinate for some rotation of
   * the polygon.
   * <p>
   * No guarantees are made that the {@code Point} list has an
   * edge which corresponds to this attribute.
   */
  private final int base;
  /**
   * The height of this {@code OrientedPolygon}. The height is
   * the length of the longest possible straight line segment
   * perpendicular to the base, between the base and another
   * point within the polygon.
   * <p>
   * No guarantees are made that the {@code Point} list has a
   * height which corresponds to this attribute.
   */
  private final int height;

  /**
   * Constructs a new {@code OrientedPolygon} with the given
   * coodinates, color, {@code Point} array, rotation, base,
   * and height. All {@code Point}s are copied and translated
   * so that the given coordinates equal the top left corner
   * of the bounding box for the polygon specified in the
   * {@code Point} array with the specified rotation. The
   * first and last {@code Point}s are to be joined by a line
   * segment, in addition to all consecutive elements.
   *
   * @param x        The x coordinate of this
   *                 {@code OrientedPolygon}.
   * @param y        The y coordinate of this
   *                 {@code OrientedPolygon}.
   * @param color    The {@code Color} to draw this
   *                 {@code OrientedPolygon} with.
   * @param points   The {@code Point}s which specify a path
   *                 that forms a simple polygon.
   * @param rotation The amount, in degrees, this polygon is
   *                 rotated from its intial orientation. A
   *                 positive value results in an
   *                 anticlockwise rotation.
   * @param base     The length of this
   *                 {@code OrientedPolygon}'s base.
   * @param height   The height of this
   *                 {@code OrientedPolygon}.
   */
  protected OrientedPolygon(
    int x,
    int y,
    Color color,
    Point[] points,
    int rotation,
    int base,
    int height
  ) {
    super(x, y, color, points, rotation);
    Shape.checkDimension(base);
    Shape.checkDimension(height);

    this.base = base;
    this.height = height;
  }

  /**
   * Gets the length of this {@code OrientedPolygon}'s base.
   * The base is the single straight horizontal edge which
   * lies exactly on the bottom edge of the bounding box of
   * this shape, i.e. the lowest y coordinate for some
   * rotation of the polygon.
   * <p>
   * No guarantees are made that the {@code Point} list has an
   * edge which corresponds to this attribute.
   *
   * @return int, the length of this {@code OrientedPolygon}'s
   *         base.
   */
  public int getBase() {
    return this.base;
  }

  /**
   * The height of this {@code OrientedPolygon}. The height is
   * the length of the longest possible straight line segment
   * perpendicular to the base, between the base and another
   * point within the polygon.
   * <p>
   * No guarantees are made that the {@code Point} list has a
   * height which corresponds to this attribute.
   *
   * @return int, the height of this {@code OrientedPolygon}.
   */
  public int getHeight() {
    return this.height;
  }

  // the following 4 classes are identical except:
  // - BaseBuilder   requires Arg("Base", 0)
  // - HeightBuilder requires Arg("Height", 0)
  // - OffsetBuilder requires Arg("Base Offset")
  // - AngleBuilder  requires Arg("Angle", 1, 179)
  // and the inheritance structure is:
  // - BaseBuilder
  //    - HeightBuilder
  //       - OffsetBuilder
  //       - AngleBuilder

  /**
   * An abstract class which adds a base length argument to
   * {@code RotationBuilder}.
   *
   * @author Kevin Qiao
   * @version 1.0
   */
  public abstract static class BaseBuilder extends ArbitrarySimplePolygon.RotationBuilder {
    /** The {@code String} to represent the argument of angle. */
    private static final String BASE = "Base Length";
    /**
     * The set of arguments this {@code BaseBuilder} requires in
     * addition to inherited arguments.
     */
    private static final LinkedHashSet<Arg> REQUIRED_ARGS =
      new LinkedHashSet<>(
        Arrays.asList(new Arg(BaseBuilder.BASE, 0))
      );

    /**
     * Constructs a {@code BaseBuilder} with the given target
     * {@code Shape} name, variation name, and with the given
     * required arguments in addition to the ones specified in
     * this class and superclasses.
     *
     * @param targetShape The name of the {@code Shape} to be
     *                    built.
     * @param variation   The name of the build variation to be
     *                    used. See {@link #getVariation()}.
     * @param args        The required args specified by
     *                    subclasses to use when building the
     *                    product, in addition to the arguments
     *                    from this class.
     */
    public BaseBuilder(
      String targetShape,
      String variation,
      LinkedHashSet<Arg> args
    ) {
      super(
        targetShape,
        variation,
        ShapeBuilder.mergeArgs(BaseBuilder.REQUIRED_ARGS, args)
      );
    }

    /**
     * Sets the base length argument of this
     * {@code BaseBuilder}.
     *
     * @param base The base length of this {@code BaseBuilder}.
     * @return {@code BaseBuilder}, this
     *         {@code BaseBuilder}.
     */
    public BaseBuilder withBase(int base) {
      this.withArg(BaseBuilder.BASE, base);
      return this;
    }

    /**
     * Gets the base length argument of this
     * {@code BaseBuilder}.
     *
     * @return int, the base length argument of this
     *         {@code BaseBuilder}.
     */
    public int getBase() {
      return this.getArg(BaseBuilder.BASE);
    }
  }

  /**
   * An abstract class which adds a height argument to
   * {@code BaseBuilder}.
   *
   * @author Kevin Qiao
   * @version 1.0
   */
  public abstract static class HeightBuilder extends BaseBuilder {
    /** The {@code String} to represent the argument of height. */
    private static final String HEIGHT = "Height";
    /**
     * The set of arguments this {@code HeightBuilder} requires
     * in addition to inherited arguments.
     */
    private static final LinkedHashSet<Arg> REQUIRED_ARGS =
      new LinkedHashSet<>(
        Arrays.asList(new Arg(HeightBuilder.HEIGHT, 0))
      );

    /**
     * Constructs a {@code HeightBuilder} with the given target
     * {@code Shape} name, variation name, and with the given
     * required arguments in addition to the ones specified in
     * this class and superclasses.
     *
     * @param targetShape The name of the {@code Shape} to be
     *                    built.
     * @param variation   The name of the build variation to be
     *                    used. See {@link #getVariation()}.
     * @param args        The required args specified by
     *                    subclasses to use when building the
     *                    product, in addition to the arguments
     *                    from this class.
     */
    public HeightBuilder(
      String targetShape,
      String variation,
      LinkedHashSet<Arg> args
    ) {
      super(
        targetShape,
        variation,
        ShapeBuilder.mergeArgs(HeightBuilder.REQUIRED_ARGS, args)
      );
    }

    /**
     * Sets the height argument of this {@code HeightBuilder}.
     *
     * @param height The height of this {@code HeightBuilder}.
     * @return {@code HeightBuilder}, this
     *         {@code HeightBuilder}.
     */
    public HeightBuilder withHeight(int height) {
      this.withArg(HeightBuilder.HEIGHT, height);
      return this;
    }

    /**
     * Gets the height argument of this {@code HeightBuilder}.
     *
     * @return int, the height argument of this
     *         {@code HeightBuilder}.
     */
    public int getHeight() {
      return this.getArg(HeightBuilder.HEIGHT);
    }
  }

  /**
   * An abstract class which adds a top edge offset from the
   * base argument to {@code HeightBuilder}. The offset is the
   * horizontal displacement between the top edge and the base
   * of a {@code Shape} (with rotation of 0).
   *
   * @author Kevin Qiao
   * @version 1.0
   */
  public abstract static class OffsetBuilder extends HeightBuilder {
    /** The {@code String} to represent the argument of base offset. */
    private static final String OFFSET = "Base Offset";
    /**
     * The set of arguments this {@code OffsetBuilder} requires
     * in addition to inherited arguments.
     */
    private static final LinkedHashSet<Arg> REQUIRED_ARGS =
      new LinkedHashSet<>(
        Arrays.asList(new Arg(OffsetBuilder.OFFSET))
      );

    /**
     * Constructs an {@code OffsetBuilder} with the given target
     * {@code Shape} name, variation name, and with the given
     * required arguments in addition to the ones specified in
     * this class and superclasses.
     *
     * @param targetShape The name of the {@code Shape} to be
     *                    built.
     * @param variation   The name of the build variation to be
     *                    used. See {@link #getVariation()}.
     * @param args        The required args specified by
     *                    subclasses to use when building the
     *                    product, in addition to the arguments
     *                    from this class.
     */
    public OffsetBuilder(
      String targetShape,
      String variation,
      LinkedHashSet<Arg> args
    ) {
      super(
        targetShape,
        variation,
        ShapeBuilder.mergeArgs(OffsetBuilder.REQUIRED_ARGS, args)
      );
    }

    /**
     * Sets the offset argument of this {@code OffsetBuilder}.
     *
     * @param offset The offset of this {@code OffsetBuilder}.
     * @return {@code OffsetBuilder}, this
     *         {@code OffsetBuilder}.
     */
    public OffsetBuilder withOffset(int offset) {
      this.withArg(OffsetBuilder.OFFSET, offset);
      return this;
    }

    /**
     * Gets the offset argument of this {@code OffsetBuilder}.
     *
     * @return int, the offset argument of this
     *         {@code OffsetBuilder}.
     */
    public int getOffset() {
      return this.getArg(OffsetBuilder.OFFSET);
    }
  }

  /**
   * An abstract class which adds a bottom left (with rotation
   * of 0) angle argument to {@code HeightBuilder}.
   *
   * @author Kevin Qiao
   * @version 1.0
   */
  public abstract static class AngleBuilder extends HeightBuilder {
    /** The {@code String} to represent the argument of angle. */
    private static final String ANGLE = "Angle";
    /**
     * The set of arguments this {@code AngleBuilder} requires
     * in addition to inherited arguments.
     */
    private static final LinkedHashSet<Arg> REQUIRED_ARGS =
      new LinkedHashSet<>(
        Arrays.asList(new Arg(AngleBuilder.ANGLE, 1, 179))
      );

    /**
     * Constructs an {@code AngleBuilder} with the given target
     * {@code Shape} name, variation name, and with the given
     * required arguments in addition to the ones specified in
     * this class and superclasses.
     *
     * @param targetShape The name of the {@code Shape} to be
     *                    built.
     * @param variation   The name of the build variation to be
     *                    used. See {@link #getVariation()}.
     * @param args        The required args specified by
     *                    subclasses to use when building the
     *                    product, in addition to the arguments
     *                    from this class.
     */
    public AngleBuilder(
      String targetShape,
      String variation,
      LinkedHashSet<Arg> args
    ) {
      super(
        targetShape,
        variation,
        ShapeBuilder.mergeArgs(AngleBuilder.REQUIRED_ARGS, args)
      );
    }

    /**
     * Sets the angle argument of this {@code AngleBuilder}.
     *
     * @param angle The angle of this {@code AngleBuilder}.
     * @return {@code AngleBuilder}, this {@code AngleBuilder}.
     */
    public AngleBuilder withAngle(int angle) {
      this.withArg(AngleBuilder.ANGLE, angle);
      return this;
    }

    /**
     * Gets the angle argument of this {@code AngleBuilder}.
     *
     * @return int, the angle argument of this
     *         {@code AngleBuilder}.
     */
    public int getAngle() {
      return this.getArg(AngleBuilder.ANGLE);
    }
  }
}
