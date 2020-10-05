package shapes;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A class to represent any ellipse. Stores the width and
 * height (the length of the horizontal and vertical axes).
 *
 * @author Kevin Qiao
 * @version 1.2
 */
public class Ellipse extends Shape {
  private static final long serialVersionUID = 1601937248L;

  /** The width of this {@code Ellipse}. */
  private final int width;
  /** The height of this {@code Ellipse}. */
  private final int height;

  /**
   * Constructs a new {@code Ellipse} with the given
   * coordinates, color, and size.
   *
   * @param x      The x coordinate of this {@code Ellipse}.
   * @param y      The y coordinate of this {@code Ellipse}.
   * @param color  The {@code Color} to draw this
   *               {@code Ellipse} with.
   * @param width  The width of this {@code Ellipse}.
   * @param height The height of this {@code Ellipse}.
   */
  public Ellipse(int x, int y, Color color, int width, int height) {
    super(x, y, color);
    Shape.checkDimension(width);
    Shape.checkDimension(height);

    this.width = width;
    this.height = height;
  }

  /**
   * {@inheritDoc}
   * <p>
   * The formula used is {@code PI * semi-minor axis *
   * semi-major axis}.
   */
  @Override
  protected double calculateArea() {
    return Math.PI*(this.width/2.0)*(this.height/2.0);
  }

  /**
   * {@inheritDoc}
   * <p>
   * The formula used is one of Ramanujan's approximations for
   * the circumference of an ellipse:
   * https://en.wikipedia.org/wiki/Ellipse#Circumference.
   * <p>
   * Though {@code a} and {@code b} are supposed to be the
   * semi-major and semi-minor axes (with a >= b), it does not
   * matter in this formula whether a >= b or not, so there is
   * no need to check.
   */
  @Override
  protected double calculatePerimeter() {
    double a = this.width/2.0;
    double b = this.height/2.0;
    double h = 3*(a-b)*(a-b)/((a+b)*(a+b));
    return Math.PI*(a+b)*(1+(h/(10+Math.sqrt(4-h))));
  }

  @Override
  public void draw(Graphics g) {
    g.setColor(this.getColor());
    g.fillOval(this.getX(), this.getY(), this.width, this.height);
  }

  /**
   * Gets the width of this {@code Ellipse}.
   *
   * @return int, the width of this {@code Ellipse}.
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the height of this {@code Ellipse}.
   *
   * @return int, the height of this {@code Ellipse}.
   */
  public int getHeight() {
    return this.height;
  }
}
