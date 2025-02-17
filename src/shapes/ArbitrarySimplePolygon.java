package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedHashSet;

import geometrygraphics.Rotateable;

/**
 * A class to represent any arbitrary simple polygon. Stores
 * an array of points representing the connected line
 * segments which form a polygon. This class does not verify
 * whether the given point list produces a simple or complex
 * (self-intersecting) polygon. If a complex polygon is
 * given, perimeter will still be properly calculated,
 * drawing will occur by the even-odd rule, but area will
 * not be properly calculated.
 *
 * @author Kevin Qiao
 * @version 1.6
 */
public class ArbitrarySimplePolygon extends Shape implements Rotateable {
  private static final long serialVersionUID = 1602472072L;

  /**
   * The {@code Point}s which specify a path that forms a
   * simple polygon. Each consecutive pair of {@code Point}s
   * are the ends of a line segment of the Polygon, with an
   * additional line segment between the first and last
   * element. No guarantees are made that the points represent
   * an actual simple polygon.
   */
  private final Point[] points;
  /**
   * The {@code java.awt.Polygon} which holds an identical
   * point list as {@code points}. Used only in
   * {@link #draw(Graphics)}.
   */
  private Polygon awtPolygon;
  /**
   * The amount, in degrees, this polygon is rotated from its
   * intial orientation. A positive value results in an
   * anticlockwise rotation. The polygon is rotated relative
   * to the origin, then translated back so that its {@code x}
   * and {@code y} still represent the coordinates of the top
   * left corner of its bounding (non-rotated) rectangle.
   */
  private int rotation;

  /**
   * Constructs a new {@code ArbitrarySimplePolygon} with the
   * given coodinates, color, {@code Point} array, and
   * rotation. All {@code Point}s are copied and translated so
   * that the given coordinates equal the top left corner of
   * the bounding box for the polygon specified in the
   * {@code Point} array with the specified rotation. The
   * first and last {@code Point}s are to be joined by a line
   * segment, in addition to all consecutive elements.
   *
   * @param x        The x coordinate of this
   *                 {@code ArbitrarySimplePolygon}.
   * @param y        The y coordinate of this
   *                 {@code ArbitrarySimplePolygon}.
   * @param color    The {@code Color} to draw this
   *                 {@code ArbitrarySimplePolygon} with.
   * @param points   The {@code Point}s which specify a path
   *                 that forms a simple polygon.
   * @param rotation The amount, in degrees, this polygon is
   *                 rotated from its intial orientation. A
   *                 positive value results in an
   *                 anticlockwise rotation.
   */
  public ArbitrarySimplePolygon(
    int x,
    int y,
    Color color,
    Point[] points,
    int rotation
  ) {
    super(x, y, color);

    this.points = new Point[points.length];
    this.awtPolygon = new Polygon();
    this.rotation = 0;

    // find top left bounding corner
    int minX = Integer.MAX_VALUE;
    int maxY = Integer.MIN_VALUE;

    // copy the array of given points so that there are no
    // references that can be modified externally
    for (int i = 0; i < points.length; ++i) {
      this.points[i] = new Point(points[i]);
      this.awtPolygon.addPoint(this.points[i].x, this.points[i].y);

      if (points[i].x < minX) {
        minX = points[i].x;
      }
      if (points[i].y > maxY) {
        maxY = points[i].y;
      }
    }

    this.updateCoords(minX, maxY);
    // might do nothing, so coords still need to be updated
    // above
    this.rotateTo(rotation);
  }

  /**
   * {@inheritDoc}
   * <p>
   * The formula used is the shoelace formula:
   * https://en.wikipedia.org/wiki/Shoelace_formula, which
   * only works on simple polygons.
   */
  @Override
  protected double calculateArea() {
    double areaSum = 0;

    for (int i = 0; i < this.points.length-1; ++i) {
      areaSum += this.points[i].x*this.points[i+1].y;
      areaSum -= this.points[i].y*this.points[i+1].x;
    }
    areaSum += this.points[this.points.length-1].x*this.points[0].y;
    areaSum -= this.points[this.points.length-1].y*this.points[0].x;

    return Math.abs(areaSum)/2.0;
  }

  /**
   * {@inheritDoc}
   * <p>
   * The calculation made is just a sum of all the distances
   * between each point in this
   * {@code ArbitrarySimplePolygon}.
   */
  @Override
  protected double calculatePerimeter() {
    double perimeter = 0;
    int dx, dy;

    for (int i = 0; i < this.points.length-1; ++i) {
      dx = this.points[i+1].x-this.points[i].x;
      dy = this.points[i+1].y-this.points[i].y;
      perimeter += Math.sqrt(dx*dx+dy*dy);
    }
    dx = this.points[0].x-this.points[this.points.length-1].x;
    dy = this.points[0].y-this.points[this.points.length-1].y;
    perimeter += Math.sqrt(dx*dx+dy*dy);

    return perimeter;
  }

  @Override
  public boolean contains(Point p) {
    return this.awtPolygon.contains(p);
  }

  @Override
  public void draw(Graphics g) {
    g.setColor(this.getColor());
    g.fillPolygon(this.awtPolygon);
  }

  @Override
  public Rectangle getBounds() {
    Rectangle r = this.awtPolygon.getBounds();
    return new Rectangle(this.getX(), this.getY(), r.width, r.height);
  }

  @Override
  public void rotateTo(int degrees) {
    if ((degrees-this.rotation)%360 == 0) {
      return;
    }
    this.rotateBy(degrees-this.rotation);
  }

  @Override
  public void rotateBy(int degreeChange) {
    double sinT = Math.sin(Math.toRadians(degreeChange));
    double cosT = Math.cos(Math.toRadians(degreeChange));

    int minX = Integer.MAX_VALUE;
    int maxY = Integer.MIN_VALUE;

    Polygon newPolygon = new Polygon();
    for (int i = 0; i < this.points.length; ++i) {
      int x = this.points[i].x;
      int y = this.points[i].y;
      this.points[i].x = (int)Math.round(x*cosT-y*sinT);
      this.points[i].y = (int)Math.round(x*sinT+y*cosT);
      newPolygon.addPoint(this.points[i].x, this.points[i].y);

      if (this.points[i].x < minX) {
        minX = this.points[i].x;
      }
      if (this.points[i].y > maxY) {
        maxY = this.points[i].y;
      }
    }

    this.awtPolygon = newPolygon;
    this.updateCoords(minX, maxY);
    this.rotation += degreeChange;
    this.rotation %= 360;
  }

  @Override
  public void translate(int dx, int dy) {
    super.translate(dx, dy);
    this.translateSelf(dx, dy);
  }

  /**
   * Translates the internal point representations of this
   * {@code ArbitrarySimplePolygon}.
   *
   * @param dx The change in x coordinates to apply to this
   *           shape.
   * @param dy The change in y coordinates to apply to this
   *           shape.
   */
  private void translateSelf(int dx, int dy) {
    for (int i = 0; i < this.points.length; ++i) {
      this.points[i].translate(dx, dy);
    }
    this.awtPolygon.translate(dx, dy);
  }

  /**
   * Translates the internal point representation to be
   * consistent with the coordinates of this
   * {@code ArbitrarySimplePolygon}, given the coordinates of
   * the top-left bounding corner.
   *
   * @param minX The x coordinate of the leftmost point.
   * @param maxY The y coordinate of the topmost point.
   */
  private void updateCoords(int minX, int maxY) {
    if ((minX == this.getX()) && (maxY == this.getY())) {
      return;
    }
    this.translateSelf(this.getX()-minX, this.getY()-maxY);
  }

  /**
   * Gets this object's rotation, in degrees.
   *
   * @return int, the rotation, in degrees, this object is
   *         rotated by.
   */
  public int getRotation() {
    return this.rotation;
  }

  /**
   * An abstract class which adds a rotation argument to
   * {@code ShapeBuilder}.
   *
   * @author Kevin Qiao
   * @version 1.0
   */
  public abstract static class RotationBuilder extends ShapeBuilder {
    /** The {@code String} to represent the argument of rotation. */
    private static final String ROTATION = "Rotation";
    /**
     * The set of arguments this {@code RotationBuilder}
     * requires in addition to inherited arguments.
     */
    private static final LinkedHashSet<Arg> REQUIRED_ARGS =
      new LinkedHashSet<>(
        Arrays.asList(new Arg(RotationBuilder.ROTATION, 0, 359))
      );

    /**
     * Constructs a {@code RotationBuilder} with the given
     * target {@code Shape} name, variation name, and with the
     * given required arguments in addition to the ones
     * specified in this class and superclasses.
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
    public RotationBuilder(
      String targetShape,
      String variation,
      LinkedHashSet<Arg> args
    ) {
      super(
        targetShape,
        variation,
        ShapeBuilder.mergeArgs(REQUIRED_ARGS, args)
      );
    }

    /**
     * Sets the rotation argument of this
     * {@code RotationBuilder}.
     *
     * @param rotation The rotation argument of this
     *                 {@code RotationBuilder}.
     * @return {@code RotationBuilder}, this
     *         {@code RotationBuilder}.
     */
    public RotationBuilder withRotation(int rotation) {
      this.withArg(RotationBuilder.ROTATION, rotation);
      return this;
    }

    /**
     * Gets the rotation argument of this
     * {@code RotationBuilder}.
     *
     * @return int, the rotation argument of this
     *         {@code RotationBuilder}.
     */
    public int getRotation() {
      return this.getArg(RotationBuilder.ROTATION);
    }
  }
}
