package shapes;

/**
 * A {@code RuntimeException} indicating that some input
 * created a {@code Shape} that is not valid.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class InvalidShapeException extends RuntimeException {
  /**
   * Constructs a new {@code InvalidShapeException} with the
   * specified detail message.
   *
   * @param message The detail message.
   */
  public InvalidShapeException(String message) {
    super(message);
  }
}
