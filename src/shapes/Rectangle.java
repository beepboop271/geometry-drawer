package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * A class to represent a rectangle: a parallelogram with 90
 * degree internal angles. A {@code Rectangle} can use the
 * perimeter formula {@code width*2 + height*2} and
 * {@link java.awt.Graphics#fillRect(int, int, int, int)}
 * instead of more general methods.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class Rectangle extends Parallelogram {
  private static final long serialVersionUID = 1601949620L;

  /**
   * Constructs a new {@code Rectangle} with the given
   * coordinates, color, {@code Point} array, base, and
   * height. All {@code Point}s are copied and translated so
   * that the given coordinates equal the top left corner of
   * the bounding box for the polygon specified in the
   * {@code Point} array. The first and last {@code Point}s
   * are to be joined by a line segment, in addition to all
   * consecutive elements.
   *
   * @param x      The x coordinate of this {@code Rectangle}.
   * @param y      The y coordinate of this {@code Rectangle}.
   * @param color  The {@code Color} to draw this
   *               {@code Rectangle} with.
   * @param points The {@code Point}s which specify a path
   *               that forms a rectangle.
   * @param base   The length of this {@code Rectangle}'s base
   *               (the width).
   * @param height The height of this {@code Rectangle}.
   */
  public Rectangle(
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
   * Calculates and returns the perimeter of this
   * {@code Shape}. It is almost certainly a better idea to
   * use {@link #getPerimeter()} instead, as that stores the
   * result so the calculation only occurs once.
   * <p>
   * The calculation made is {@code width*2 + height*2}.
   *
   * @return double, the perimeter of this {@code Shape}.
   */
  @Override
  protected double calculatePerimeter() {
    return this.getWidth()*2+this.getHeight()*2;
  }

  @Override
  public void draw(Graphics g) {
    g.setColor(this.getColor());
    g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
  }

  /**
   * Gets the width of this {@code Rectangle}. Equal to the
   * length of the base and the opposite base.
   *
   * @return int, the width of this {@code Rectangle}.
   */
  public int getWidth() {
    return this.getBase();
  }
}
