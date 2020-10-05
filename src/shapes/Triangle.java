package shapes;

import java.awt.Color;
import java.awt.Point;

/**
 * A class to represent a polygon with 3 sides. A
 * {@code Triangle} can use the area formula
 * {@code base * height / 2} instead of a more general
 * formula like the shoelace formula found in
 * {@code ArbitrarySimplePolygon}.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class Triangle extends OrientedPolygon {
  private static final long serialVersionUID = 1601850095L;

  public Triangle(
    int x,
    int y,
    Color color,
    Point[] points,
    int base,
    int height
  ) {
    super(x, y, color, points, base, height);
  }

  /**
   * Calculates and returns the area of this {@code Shape}.
   * <p>
   * The formula used is {@code base * height / 2}.
   *
   * @return double, the area of this {@code Shape}.
   */
  @Override
  public double calculateArea() {
    return this.getBase()*this.getHeight()/2.0;
  }
}
