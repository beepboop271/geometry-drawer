package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import geometrygraphics.Drawable;

/**
 * A class to represent any shape to be used in the geometry
 * drawer. Has a position in standard cartesian coordinates,
 * which should be transformed according to the drawer's
 * coordinate system when drawing to screen coordinates.
 * {@code Shape}s and subclasses should be immutable, except
 * for position.
 *
 * @author Kevin Qiao
 * @version 1.2
 */
public abstract class Shape implements Serializable, Drawable {
  private static final long serialVersionUID = 1601779914L;

  /**
   * The x coordinate of this {@code Shape}. The x coordinate
   * is the leftmost (smallest) x coordinate this
   * {@code Shape} covers, i.e. the x coordinate of this
   * {@code Shape}'s bounding box left edge.
   */
  private int x;
  /**
   * The y coordinate of this {@code Shape}. The y coordinate
   * is the topmost (largest) y coordinate this {@code Shape}
   * covers, i.e. the y coordinate of this {@code Shape}'s
   * bounding box top edge.
   */
  private int y;
  /** The {@code Colour} to draw this {@code Shape} with. */
  private final Color color;

  /**
   * The area of this {@code Shape}. A negative value means
   * the area has not been initialized yet, however only
   * private access to the {@code area} attribute could result
   * in the retrieval of an uninitialized value.
   */
  private double area;
  /**
   * The perimeter of this {@code Shape}. A negative value
   * means the perimeter has not been initialized yet, however
   * only private access to the {@code perimeter} attribute
   * could result in the retrieval of an uninitialized value.
   */
  private double perimeter;

  /**
   * Constructs a new {@code Shape} with the given coordinates
   * and color.
   *
   * @param x     The x coordinate of this {@code Shape}.
   * @param y     The y coordinate of this {@code Shape}.
   * @param color The {@code Color} to draw this {@code Shape}
   *              with.
   */
  public Shape(int x, int y, Color color) {
    this.x = x;
    this.y = y;
    this.color = color;

    // will be initialized on the first call to getPerimeter
    // and getArea, since subclass data that is required to
    // perform the calculation won't be ready on a super call
    // at the start of a constructor
    this.area = -1;
    this.perimeter = -1;
  }

  /**
   * Method to check whether a dimension is negative or not.
   * If the dimension is valid (non-negative), this method
   * does nothing. Otherwise, a
   * {@code NegativeDimensionException} is thrown.
   *
   * @param dimension The dimension to check.
   */
  public static void checkDimension(int dimension) {
    if (dimension < 0) {
      throw new NegativeDimensionException(dimension);
    }
  }

  /**
   * Draws this {@code Shape} onto the supplied
   * {@code Graphics} object. The {@code Shape} should be
   * drawn such that the coordinates of the top left corner of
   * the bounding box is equal to the coordinates specified in
   * the {@code x} and {@code y} attributes of this
   * {@code Shape}.
   *
   * @param g The {@code Graphics} object to draw to.
   */
  public abstract void draw(Graphics g);

  /**
   * Calculates and returns the area of this {@code Shape}.
   *
   * @return double, the area of this {@code Shape}.
   */
  public abstract double calculateArea();

  /**
   * Calculates and returns the perimeter of this
   * {@code Shape}.
   *
   * @return double, the perimeter of this {@code Shape}.
   */
  public abstract double calculatePerimeter();

  /**
   * Translates this {@code Shape} by the given change to x
   * and y coordinates.
   *
   * @param dx The change in x coordinates to apply to this
   *           {@code Shape}.
   * @param dy The change in y coordinates to apply to this
   *           {@code Shape}.
   */
  public void translate(int dx, int dy) {
    this.x += dx;
    this.y += dy;
  }

  /**
   * Gets the x coordinate of this {@code Shape}. The x
   * coordinate is the leftmost (smallest) x coordinate this
   * {@code Shape} covers, i.e. the x coordinate of this
   * {@code Shape}'s bounding box left edge.
   *
   * @return int, the x coordinate of this {@code Shape}.
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets the y coordinate of this {@code Shape}. The y
   * coordinate is the topmost (largest) y coordinate this
   * {@code Shape} covers, i.e. the y coordinate of this
   * {@code Shape}'s bounding box top edge.
   *
   * @return int, the y coordinate of this {@code Shape}.
   */
  public int getY() {
    return this.y;
  }

  /**
   * Gets the {@code Colour} to draw this {@code Shape} in.
   *
   * @return {@code Color}, the {@code Colour} to draw this
   *         {@code Shape} in.
   */
  public Color getColor() {
    return this.color;
  }

  /**
   * Gets the area of this {@code Shape}. The area is
   * calculated if it hasn't been calculated before.
   *
   * @return double, the area of this {@code Shape}.
   */
  public double getArea() {
    if (this.area < 0) {
      this.area = this.calculateArea();
    }
    return this.area;
  }

  /**
   * Gets the perimeter of this {@code Shape}. The perimeter
   * is calculated if it hasn't been calculated before.
   *
   * @return double, the perimeter of this {@code Shape}.
   */
  public double getPerimeter() {
    if (this.perimeter < 0) {
      this.perimeter = this.calculatePerimeter();
    }
    return this.perimeter;
  }
}
