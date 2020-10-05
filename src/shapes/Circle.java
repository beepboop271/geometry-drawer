package shapes;

import java.awt.Color;

/**
 * A class to represent any circle, a special case of an
 * ellipse with equal width and height.
 *
 * @author Kevin Qiao
 * @version 1.2
 */
public class Circle extends Ellipse {
  private static final long serialVersionUID = 1601937401L;

  /**
   * Constructs a new {@code Circle} with the given
   * coordinates, color, and diameter.
   *
   * @param x        The x coordinate of this {@code Circle}.
   * @param y        The y coordinate of this {@code Circle}.
   * @param color    The {@code Color} to draw this
   *                 {@code Circle} with.
   * @param diameter The diameter of this {@code Circle}.
   */
  public Circle(int x, int y, Color color, int diameter) {
    super(x, y, color, diameter, diameter);
  }

  /**
   * Calculates and returns the area of this {@code Shape}. It
   * is almost certainly a better idea to use {@code getArea}
   * instead, as that stores the result so the calculation
   * only occurs once.
   * <p>
   * The formula used is {@code PI * radius * radius}.
   *
   * @return double, the area of this {@code Shape}.
   */
  @Override
  protected double calculateArea() {
    double r = this.getWidth()/2.0;
    return Math.PI*r*r;
  }

  /**
   * Calculates and returns the perimeter of this
   * {@code Shape}. It is almost certainly a better idea to
   * use {@code getPerimeter} instead, as that stores the
   * result so the calculation only occurs once.
   * <p>
   * The formula used is {@code PI * diameter}.
   *
   * @return double, the perimeter of this {@code Shape}.
   */
  @Override
  protected double calculatePerimeter() {
    return Math.PI*this.getWidth();
  }

  /**
   * Gets the diameter of this {@code Circle}. Equal to both
   * the width and height of the parent {@code Ellipse}.
   *
   * @return int, the diameter of this {@code Circle}.
   */
  public int getDiameter() {
    return this.getWidth();
  }
}
