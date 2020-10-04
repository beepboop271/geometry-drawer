package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

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
 * @version 1.1
 */
public class ArbitraryPolygon extends Shape {
  private static final long serialVersionUID = 1601791285L;

  /**
   * The {@code Point}s which specify a path that forms a
   * simple polygon. Each consecutive pair of {@code Point}s
   * are the ends of a line segment of the Polygon. No
   * guarantees are made that the points represent an actual
   * simple polygon.
   */
  private final Point[] points;
  /**
   * The {@code java.awt.Polygon} which holds an identical
   * point list as {@code points}. Used only in {@code draw}.
   */
  private final Polygon awtPolygon;

  /**
   * Constructs a new {@code ArbitraryPolygon} with the given
   * coodinates, color, and {@code Point} array. All
   * {@code Point}s are copied and translated so that the
   * given coordinates equal the top left corner of the
   * bounding box for the polygon specified in the
   * {@code Point} array.
   *
   * @param x      The x coordinate of this
   *               {@code ArbitraryPolygon}.
   * @param y      The y coordinate of this
   *               {@code ArbitraryPolygon}.
   * @param color  The {@code Color} to draw this
   *               {@code ArbitraryPolygon} with.
   * @param points The {@code Point}s which specify a path
   *               that forms a simple polygon.
   */
  public ArbitraryPolygon(int x, int y, Color color, Point[] points) {
    super(x, y, color);

    this.points = new Point[points.length];
    this.awtPolygon = new Polygon();

    // find top left corner
    int minX = Integer.MAX_VALUE;
    int maxY = Integer.MIN_VALUE;

    // copy the array of given points so that there are no
    // references that can be modified externally
    for (int i = 0; i < points.length; ++i) {
      this.points[i] = new Point(points[i]);
      if (points[i].x < minX) {
        minX = points[i].x;
      }
      if (points[i].y > maxY) {
        maxY = points[i].y;
      }
    }

    // translate points so that x, y represents the top-left
    // corner of the bounding box
    int dx = x-minX;
    int dy = y-maxY;
    for (int i = 0; i < this.points.length; ++i) {
      this.points[i].translate(dx, dy);
      this.awtPolygon.addPoint(this.points[i].x, this.points[i].y);
    }
  }

  @Override
  public void draw(Graphics g) {
    g.setColor(this.getColor());
    g.fillPolygon(this.awtPolygon);
  }

  /**
   * {@inheritDoc}
   * <p>
   * The formula used is the shoelace formula:
   * https://en.wikipedia.org/wiki/Shoelace_formula, which
   * only works on simple polygons.
   */
  @Override
  public double calculateArea() {
    // TODO implement shoelace formula
    return -1;
  }

  /**
   * {@inheritDoc}
   * <p>
   * The calculation made is just a sum of all the distances
   * between each point in this {@code ArbitraryPolygon}.
   */
  @Override
  public double calculatePerimeter() {
    // TODO sum distances between points
    return -1;
  }
}
