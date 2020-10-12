package shapes;

import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * A class to represent any circle, a special case of an
 * ellipse with equal width and height.
 *
 * @author Kevin Qiao
 * @version 1.3
 */
public class Circle extends Ellipse {
  private static final long serialVersionUID = 1602471671L;

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
  protected Circle(int x, int y, Color color, int diameter) {
    super(x, y, color, diameter, diameter);
  }

  /**
   * Calculates and returns the area of this {@code Shape}. It
   * is almost certainly a better idea to use
   * {@link #getArea()} instead, as that stores the result so
   * the calculation only occurs once.
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
   * use {@link #getPerimeter()} instead, as that stores the
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

  /**
   * A builder class for a {@code Circle}. Adds argument for
   * diameter in addition to those from {@code ShapeBuilder}.
   *
   * @author Kevin Qiao
   * @version 1.0
   */
  public static class Builder extends ShapeBuilder {
    /** The {@code String} to represent the argument of diameter. */
    private static final String DIAMETER = "Diameter";
    /**
     * The set of arguments this {@code Builder} requires in
     * addition to inherited arguments.
     */
    private static final LinkedHashSet<Arg> REQUIRED_ARGS =
      new LinkedHashSet<>(
        Arrays.asList(new Arg(Builder.DIAMETER, 0))
      );

    /**
     * Creates a {@code Circle} builder which uses diameter to
     * construct a {@code Circle}.
     */
    public Builder() {
      super("Circle", "Lengths", Builder.REQUIRED_ARGS);
    }

    @Override
    public Circle build() {
      return new Circle(
        this.getX(),
        this.getY(),
        new Color(this.getRed(), this.getGreen(), this.getBlue()),
        this.getArg(Builder.DIAMETER)
      );
    }

    /**
     * Sets the diameter argument of this {@code Builder}.
     *
     * @param diameter The diameter argument of this
     *                 {@code Builder}.
     * @return {@code Builder}, this {@code Builder}.
     */
    public Builder withDiameter(int diameter) {
      this.withArg(Builder.DIAMETER, diameter);
      return this;
    }

    /**
     * Gets the diameter argument of this {@code Builder}.
     *
     * @return int, the diameter argument of this
     *         {@code Builder}.
     */
    public int getDiameter() {
      return this.getArg(Builder.DIAMETER);
    }
  }
}
