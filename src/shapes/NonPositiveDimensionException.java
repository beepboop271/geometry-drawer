package shapes;

/**
 * A {@code RuntimeException} indicating that a non-positive
 * (negative or zero) value was provided as a dimension,
 * which is not valid.
 *
 * @author Kevin Qiao
 * @version 1.1
 */
public class NonPositiveDimensionException extends InvalidShapeException {
  /**
   * The non-positive dimension which caused this exception.
   */
  private final int dimension;

  /**
   * Constructs a new {@code NonPositiveDimensionException}
   * with the dimension which caused this exception and a
   * default message.
   *
   * @param dimension The non-positive dimension which caused
   *                  this exception.
   */
  public NonPositiveDimensionException(int dimension) {
    super("Invalid (non-positive) dimension: "+dimension);
    this.dimension = dimension;
  }

  /**
   * Constructs a new {@code NonPositiveDimensionException}
   * with the specified detail message and the dimension which
   * caused this exception.
   *
   * @param message   The detail message.
   * @param dimension The non-positive dimension which caused
   *                  this exception.
   */
  public NonPositiveDimensionException(String message, int dimension) {
    super(message);
    this.dimension = dimension;
  }

  /**
   * Gets the non-positive dimension which caused this
   * exception.
   *
   * @return int, the non-positive dimension which caused this
   *         exception.
   */
  public int getDimension() {
    return this.dimension;
  }
}
