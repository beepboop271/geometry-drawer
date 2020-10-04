package shapes;

/**
 * A {@code RuntimeException} indicating that a negative
 * value was provided as a dimension, which is not valid.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class NegativeDimensionException extends RuntimeException {
  private static final long serialVersionUID = 1601834455L;

  /** The negative dimension which caused this exception. */
  private final int dimension;

  /**
   * Constructs a new {@code NegativeDimensionException} with
   * the dimension which caused this exception and a default
   * message.
   */
  public NegativeDimensionException(int dimension) {
    super("Invalid (negative) dimension: "+dimension);
    this.dimension = dimension;
  }

  /**
   * Constructs a new {@code NegativeDimensionException} with
   * the specified detail message and the dimension which
   * caused this exception.
   *
   * @param message The detail message.
   */
  public NegativeDimensionException(String message, int dimension) {
    super(message);
    this.dimension = dimension;
  }

  /**
   * Gets the negative dimension which caused this exception.
   *
   * @return int, the negative dimension which caused this
   *         exception.
   */
  public int getDimension() {
    return this.dimension;
  }
}
