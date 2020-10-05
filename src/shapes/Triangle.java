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
 * @version 1.1
 */
public class Triangle extends OrientedPolygon {
  private static final long serialVersionUID = 1601939037L;

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
   * Calculates and returns the area of this {@code Shape}. It
   * is almost certainly a better idea to use {@code getArea}
   * instead, as that stores the result so the calculation
   * only occurs once.
   * <p>
   * The formula used is {@code base * height / 2}.
   *
   * @return double, the area of this {@code Shape}.
   */
  @Override
  protected double calculateArea() {
    return this.getBase()*this.getHeight()/2.0;
  }
}
