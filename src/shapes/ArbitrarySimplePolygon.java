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
 * @version 1.4
 */
public class ArbitrarySimplePolygon extends Shape {
  private static final long serialVersionUID = 1602112893L;

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
  private final Polygon awtPolygon;

  /**
   * Constructs a new {@code ArbitrarySimplePolygon} with the
   * given coodinates, color, and {@code Point} array. All
   * {@code Point}s are copied and translated so that the
   * given coordinates equal the top left corner of the
   * bounding box for the polygon specified in the
   * {@code Point} array. The first and last {@code Point}s
   * are to be joined by a line segment, in addition to all
   * consecutive elements.
   *
   * @param x      The x coordinate of this
   *               {@code ArbitrarySimplePolygon}.
   * @param y      The y coordinate of this
   *               {@code ArbitrarySimplePolygon}.
   * @param color  The {@code Color} to draw this
   *               {@code ArbitrarySimplePolygon} with.
   * @param points The {@code Point}s which specify a path
   *               that forms a simple polygon.
   */
  public ArbitrarySimplePolygon(int x, int y, Color color, Point[] points) {
    super(x, y, color);

    this.points = new Point[points.length];
    this.awtPolygon = new Polygon();

    // find top left bounding corner
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
      areaSum += this.points[i].x * this.points[i+1].y;
      areaSum -= this.points[i].y * this.points[i+1].x;
    }
    areaSum += this.points[this.points.length-1].x * this.points[0].y;
    areaSum -= this.points[this.points.length-1].y * this.points[0].x;

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
      dx = this.points[i+1].x - this.points[i].x;
      dy = this.points[i+1].y - this.points[i].y;
      perimeter += Math.sqrt(dx*dx + dy*dy);
    }
    dx = this.points[0].x - this.points[this.points.length-1].x;
    dy = this.points[0].y - this.points[this.points.length-1].y;
    perimeter += Math.sqrt(dx*dx + dy*dy);

    return perimeter;
  }

  @Override
  public void draw(Graphics g) {
    g.setColor(this.getColor());
    g.fillPolygon(this.awtPolygon);
  }

  @Override
  public void translate(int dx, int dy) {
    super.translate(dx, dy);
    for (int i = 0; i < this.points.length; ++i) {
      this.points[i].translate(dx, dy);
    }
    this.awtPolygon.translate(dx, dy);
  }
}
