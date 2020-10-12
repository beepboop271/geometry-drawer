package shapes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * A class which represents a builder for a {@code Shape}.
 * The required {@code Shape} arguments of x, y, and color
 * are provided, as well as the capability to inherit and
 * add more arguments in the form of String keys.
 * <p>
 * A {@code ShapeBuilder} allows for a more abstract version
 * of {@code Shape} creation, by allowing the required
 * arguments to be retrieved and passed to something like a
 * UI without the program really needing to know exactly
 * what the target {@code Shape}'s constructors or creation
 * involves. This is continued further when using
 * {@code ShapeFactory}.
 * <p>
 * In addition, {@code Shape} constructors which require
 * some calculation to get all the required arguments can
 * use a {@code ShapeBuilder} to calculate the arguments
 * needed.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public abstract class ShapeBuilder implements Cloneable {
  /** The {@code String} to represent the argument of x coordinate. */
  private static final String X = "x";
  /** The {@code String} to represent the argument of y coordinate. */
  private static final String Y = "y";
  /** The {@code String} to represent the argument of red in an RGB color. */
  private static final String RED = "Red";
  /** The {@code String} to represent the argument of green in an RGB color. */
  private static final String GREEN = "Green";
  /** The {@code String} to represent the argument of blue in an RGB color. */
  private static final String BLUE = "Blue";
  /**
   * The set of arguments all {@code ShapeBuilder}s require
   * and will inherit.
   */
  private static final LinkedHashSet<Arg> REQUIRED_SHAPE_ARGS =
    new LinkedHashSet<>(
      Arrays.asList(
        new Arg(ShapeBuilder.X),
        new Arg(ShapeBuilder.Y),
        new Arg(ShapeBuilder.RED, 0, 255),
        new Arg(ShapeBuilder.GREEN, 0, 255),
        new Arg(ShapeBuilder.BLUE, 0, 255)
      )
    );

  /**
   * The {@code String} name of the {@code Shape} to be built
   * by this {@code ShapeBuilder}.
   */
  private final String targetShape;
  /**
   * The {@code String} name of the build variation to be used
   * by this {@code ShapeBuilder} when building. For example,
   * a builder which uses angles versus a builder which uses
   * lengths to build the same target shape could provide
   * differentiating identification with this field.
   */
  private final String variation;

  /**
   * The current arguments in this {@code ShapeBuilder} to be
   * used when building a final product {@code Shape}.
   */
  private final HashMap<String, Integer> args;
  /**
   * The required arguments this {@code ShapeBuilder} needs to
   * build its target shape. This attribute is created by
   * merging together required arguments passed through the
   * chain of inheritance via the {@code args} constructor
   * parameter. A {@code LinkedHashMap} is used to provide
   * predictable iteration order, which is useful when
   * presenting the list of required arguments in a user
   * interface.
   */
  private final LinkedHashMap<String, Arg> requiredArgs;

  /**
   * Constructs a {@code ShapeBuilder} with the given target
   * {@code Shape} name, variation name, and with the given
   * required arguments in addition to the ones specified in
   * this class.
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
  public ShapeBuilder(
    String targetShape,
    String variation,
    LinkedHashSet<Arg> args
  ) {
    this.targetShape = targetShape;
    this.variation = variation;
    this.args = new HashMap<>();
    this.requiredArgs = new LinkedHashMap<>();

    // combine the arg lists together
    for (Arg arg : ShapeBuilder.REQUIRED_SHAPE_ARGS) {
      this.requiredArgs.put(arg.getName(), arg);
    }
    for (Arg arg : args) {
      this.requiredArgs.put(arg.getName(), arg);
    }

    // fill args with default of 0 or the minimum value (if 0 is
    // not valid)
    for (Arg arg : this.requiredArgs.values()) {
      if ((arg.getMinValue() <= 0) && (0 <= arg.getMaxValue())) {
        this.args.put(arg.getName(), 0);
      } else {
        this.args.put(arg.getName(), arg.getMinValue());
      }
    }
  }

  /**
   * Creates a new {@code LinkedHashSet} with the keys
   * contained in both set arguments.
   *
   * @param <T>        The type of the keys in the sets to
   *                   merge.
   * @param firstArgs  The first {@code LinkedHashSet}
   *                   containing keys to merge.
   * @param secondArgs The second {@code LinkedHashSet}
   *                   containing keys to merge.
   * @return {@code LinkedhashSet}, a new set containing the
   *         keys of both set arguments.
   */
  protected static <T> LinkedHashSet<T> mergeArgs(
    LinkedHashSet<T> firstArgs,
    LinkedHashSet<T> secondArgs
  ) {
    if (firstArgs == null) {
      if (secondArgs == null) {
        return new LinkedHashSet<>();
      }
      return new LinkedHashSet<>(secondArgs);
    }
    if (secondArgs == null) {
      return new LinkedHashSet<>(firstArgs);
    }
    LinkedHashSet<T> set = new LinkedHashSet<>(firstArgs);
    set.addAll(secondArgs);
    return set;
  }

  /**
   * Creates a new instance of the target {@code Shape} using
   * the arguments of this {@code ShapeBuilder}.
   *
   * @return Shape, a new instance of the target {@code Shape}
   *         using the arguments of this {@code ShapeBuilder}.
   */
  public abstract Shape build();

  /**
   * Sets the argument with the given name on this builder to
   * the given value. The argument must be declared already as
   * a required argument, and it must be within the declared
   * range. Otherwise, an {@code IllegalArgumentException}
   * will be thrown.
   * <p>
   * When possible, the statically typed versions such as
   * {@link #withPosition(int, int)} and
   * {@link #withColour(int, int, int)} should be used over
   * this method, since those are checked on compile time.
   *
   * @param arg   The name of the argument to set.
   * @param value The value to set for the argument.
   * @return {@code ShapeBuilder}, this {@code ShapeBuilder}
   */
  public ShapeBuilder withArg(String arg, int value) {
    // don't let new unknown args be added in
    if (!this.args.containsKey(arg)) {
      throw new IllegalArgumentException("Unknown argument: "+arg);
    }
    Arg requiredArg = requiredArgs.get(arg);
    if (!requiredArg.test(value)) {
      throw new IllegalArgumentException(
        "Argument "+arg+": "
        +value+" out of range (expected "
        +requiredArg.getMinValue()+"<=x<="+requiredArg.getMaxValue()
        +")"
      );
    }
    this.args.put(arg, value);
    return this;
  }

  /**
   * Sets the x and y arguments on this {@code ShapeBuilder}
   * to the given values.
   *
   * @param x The x coordinate of the {@code Shape} to build.
   * @param y The y coordinate of the {@code Shape} to build.
   * @return {@code ShapeBuilder}, this {@code ShapeBuilder}
   */
  public ShapeBuilder withPosition(int x, int y) {
    this.withArg(ShapeBuilder.X, x);
    this.withArg(ShapeBuilder.Y, y);
    return this;
  }

  /**
   * Sets the color arguments on this {@code ShapeBuilder} to
   * the given values.
   *
   * @param r The red channel of the RGB color for the
   *          {@code Shape} to build.
   * @param g The green channel of the RGB color for the
   *          {@code Shape} to build.
   * @param b The blue channel of the RGB color for the
   *          {@code Shape} to build.
   * @return {@code ShapeBuilder}, this {@code ShapeBuilder}
   */
  public ShapeBuilder withColour(int r, int g, int b) {
    this.withArg(ShapeBuilder.RED, r);
    this.withArg(ShapeBuilder.GREEN, g);
    this.withArg(ShapeBuilder.BLUE, b);
    return this;
  }

  /**
   * Gets the {@code String} name of the {@code Shape} to be
   * built by this {@code ShapeBuilder}.
   *
   * @return String, the {@code String} name of the
   *         {@code Shape} to be built by this
   *         {@code ShapeBuilder}.
   */
  public String getTargetShape() {
    return this.targetShape;
  }

  /**
   * Gets the {@code String} name of the build variation to be
   * used by this {@code ShapeBuilder} when building. For
   * example, a builder which uses angles versus a builder
   * which uses lengths to build the same target shape could
   * provide differentiating identification with this field.
   *
   * @return String, the name of the build variation to be
   *         used by this {@code ShapeBuilder} when building.
   */
  public String getVariation() {
    return this.variation;
  }

  /**
   * Gets the value of the argument on this builder with the
   * given name. The argument must be declared already as a
   * required argument, otherwise an
   * {@code IllegalArgumentException} will be thrown.
   * <p>
   * When possible, the statically typed versions such as
   * {@link #getX()} and {@link #getRed()} should be used over
   * this method, since those are checked on compile time.
   *
   * @param arg The name of the argument to get.
   * @return int, the value of the argument with the given
   *         name.
   */
  public int getArg(String arg) {
    // prevent NullPointerException if the function return tries
    // to unbox a null returned from the map
    if (!this.args.containsKey(arg)) {
      throw new IllegalArgumentException("Unknown argument: "+arg);
    }
    return this.args.get(arg);
  }

  /**
   * Get an iterator of the {@code ShapeBuilder.Arg}s required
   * to build a new {@code Shape} using this builder.
   *
   * @return ReadOnlyIterator, an iterator of
   *         {@code ShapeBuilder.Arg}s which specify the
   *         arguments required to build a new {@code Shape}.
   */
  public ReadOnlyIterator<Arg> getRequiredArgs() {
    return new ReadOnlyIterator<>(this.requiredArgs.values().iterator());
  }

  /**
   * Gets the x coordinate of the {@code Shape} to build.
   *
   * @return int, the x coordinate of the {@code Shape} to
   *         build.
   */
  public int getX() {
    return this.getArg(ShapeBuilder.X);
  }

  /**
   * Gets the y coordinate of the {@code Shape} to build.
   *
   * @return int, the y coordinate of the {@code Shape} to
   *         build.
   */
  public int getY() {
    return this.getArg(ShapeBuilder.Y);
  }

  /**
   * Gets the red channel of the RGB color for the
   * {@code Shape} to build.
   *
   * @return int, the red channel of the RGB color for the
   *         {@code Shape} to build.
   */
  public int getRed() {
    return this.getArg(ShapeBuilder.RED);
  }

  /**
   * Gets the green channel of the RGB color for the
   * {@code Shape} to build.
   *
   * @return int, the green channel of the RGB color for the
   *         {@code Shape} to build.
   */
  public int getGreen() {
    return this.getArg(ShapeBuilder.GREEN);
  }

  /**
   * Gets the blue channel of the RGB color for the
   * {@code Shape} to build.
   *
   * @return int, the blue channel of the RGB color for the
   *         {@code Shape} to build.
   */
  public int getBlue() {
    return this.getArg(ShapeBuilder.BLUE);
  }

  /**
   * An immutable class to represent a {@code ShapeBuilder}
   * argument. An argument has a {@code String} name, and an
   * integer value, with optional bounds restrictions.
   *
   * @author Kevin Qiao
   * @version 1.0
   */
  public static class Arg {
    /** The name of this argument. */
    private final String name;
    /** The minimum allowed value of this argument. */
    private final int minValue;
    /** The maximum allowed value of this argument. */
    private final int maxValue;

    /**
     * Creates an argument with the given name, minimum, and
     * maximum values.
     *
     * @param name     The name of this argument.
     * @param minValue The minimum allowed value of this
     *                 argument.
     * @param maxValue The maximum allowed value of this
     *                 argument.
     */
    public Arg(String name, int minValue, int maxValue) {
      if (minValue >= maxValue) {
        throw new IllegalArgumentException("Invalid range: "+minValue+" "+maxValue);
      }
      this.name = name;
      this.minValue = minValue;
      this.maxValue = maxValue;
    }

    /**
     * Creates an argument with the given name and minimum
     * bound, with no upper bound. The upper bound is set to
     * {@code Integer.MAX_VALUE}, which is effectively
     * unlimited.
     *
     * @param name     The name of this argument.
     * @param minValue The minimum allowed value of this
     *                 argument.
     */
    public Arg(String name, int minValue) {
      this(name, minValue, Integer.MAX_VALUE);
    }

    /**
     * Creates an argument with the given name and no bounds
     * on the value. The upper and lower bounds are set to
     * {@code Integer} min and max values respectively, which
     * is effectively unlimited.
     *
     * @param name The name of this argument.
     */
    public Arg(String name) {
      this(name, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Returns whether or not the given value is within the
     * range as a valid value of this argument.
     *
     * @param value The value to test for validity.
     * @return boolean, true if the value is within the range of
     *         valid values of this argument, false otherwise.
     */
    public boolean test(int value) {
      return (this.minValue <= value) && (value <= this.maxValue);
    }

    /**
     * Gets the name of this argument.
     *
     * @return String, the name of this argument.
     */
    public String getName() {
      return this.name;
    }

    /**
     * Gets the minimum allowed value of this argument.
     *
     * @return int, the minimum allowed value of this argument.
     */
    public int getMinValue() {
      return this.minValue;
    }

    /**
     * Gets the maximum allowed value of this argument.
     *
     * @return int, the maximum allowed value of this argument.
     */
    public int getMaxValue() {
      return this.maxValue;
    }
  }
}
