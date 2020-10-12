package shapes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public abstract class ShapeBuilder implements Cloneable {
  private static final String X = "x";
  private static final String Y = "y";
  private static final String RED = "Red";
  private static final String GREEN = "Green";
  private static final String BLUE = "Blue";
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

  private final String targetShape;
  private final String variation;

  private final HashMap<String, Integer> args;
  private final LinkedHashMap<String, Arg> requiredArgs;

  public ShapeBuilder(
    String targetShape,
    String variation,
    LinkedHashSet<Arg> args
  ) {
    this.targetShape = targetShape;
    this.variation = variation;
    this.args = new HashMap<>();
    this.requiredArgs = new LinkedHashMap<>();

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

  protected static LinkedHashSet<Arg> mergeArgs(
    LinkedHashSet<Arg> firstArgs,
    LinkedHashSet<Arg> secondArgs
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
    LinkedHashSet<Arg> set = new LinkedHashSet<>(firstArgs);
    set.addAll(secondArgs);
    return set;
  }

  public abstract Shape build();

  public ReadOnlyIterator<Arg> getRequiredArgs() {
    return new ReadOnlyIterator<>(this.requiredArgs.values().iterator());
  }

  @Override
  public ShapeBuilder clone() {
    try {
      return ((ShapeBuilder)super.clone());
    } catch (CloneNotSupportedException e) {
      // never occurs
      return null;
    }
  }

  public ShapeBuilder withPosition(int x, int y) {
    this.withArg(ShapeBuilder.X, x);
    this.withArg(ShapeBuilder.Y, y);
    return this;
  }

  public ShapeBuilder withColour(int r, int g, int b) {
    this.withArg(ShapeBuilder.RED, r);
    this.withArg(ShapeBuilder.GREEN, g);
    this.withArg(ShapeBuilder.BLUE, b);
    return this;
  }

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

  public int getX() {
    return this.getArg(ShapeBuilder.X);
  }

  public int getY() {
    return this.getArg(ShapeBuilder.Y);
  }

  public int getRed() {
    return this.getArg(ShapeBuilder.RED);
  }

  public int getGreen() {
    return this.getArg(ShapeBuilder.GREEN);
  }

  public int getBlue() {
    return this.getArg(ShapeBuilder.BLUE);
  }

  public int getArg(String arg) {
    // prevent NullPointerException if the function return tries
    // to unbox a null returned from the map
    if (!this.args.containsKey(arg)) {
      throw new IllegalArgumentException("Unknown argument: "+arg);
    }
    return this.args.get(arg);
  }

  public String getTargetShape() {
    return this.targetShape;
  }

  public String getVariation() {
    return this.variation;
  }

  public static class Arg {
    private final String name;
    private final int minValue;
    private final int maxValue;

    public Arg(String name, int minValue, int maxValue) {
      if (minValue >= maxValue) {
        throw new IllegalArgumentException("Invalid range: "+minValue+" "+maxValue);
      }
      this.name = name;
      this.minValue = minValue;
      this.maxValue = maxValue;
    }

    public Arg(String name, int minValue) {
      this(name, minValue, Integer.MAX_VALUE);
    }

    public Arg(String name) {
      this(name, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public boolean test(int value) {
      return (this.minValue <= value) && (value <= this.maxValue);
    }

    /**
     * @return the name
     */
    public String getName() {
      return this.name;
    }

    /**
     * @return the minValue
     */
    public int getMinValue() {
      return this.minValue;
    }

    /**
     * @return the maxValue
     */
    public int getMaxValue() {
      return this.maxValue;
    }
  }
}
