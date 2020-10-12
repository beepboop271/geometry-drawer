package shapes;

import java.util.HashMap;

/**
 * A class containing a mapping of all {@code ShapeBuilder}s
 * and variations of builders. Allows for abstract handling of
 * {@code Shape} creation.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class ShapeFactory {
  /**
   * A map of {@code Shape} names to another map of variation
   * names to actual {@code ShapeBuilder}s.
   */
  private HashMap<String, HashMap<String, ShapeBuilder>> builders;

  /**
   * Creates a new {@code ShapeFactory} and adds all builders
   * to it.
   */
  public ShapeFactory() {
    this.builders = new HashMap<>();

    this.registerBuilders();
  }

  /**
   * Checks that the given shape name exists in this
   * {@code ShapeFactory}. Does nothing if it exists, and
   * throws an {@code IllegalArgumentException} if it doesn't.
   *
   * @param shape The shape name to check.
   */
  private void checkShapeKey(String shape) {
    if (!this.builders.containsKey(shape)) {
      throw new IllegalArgumentException("Unknown shape: "+shape);
    }
  }

  /**
   * Registers all {@code ShapeBuilder}s to this factory. New
   * {@code Shape}s would need to have their builders added
   * here so that they can be constructed through this
   * factory and set of builders.
   */
  protected void registerBuilders() {
    this.registerBuilder(new Circle.Builder());
    this.registerBuilder(new Ellipse.Builder());
    this.registerBuilder(new Triangle.SasBuilder());
    this.registerBuilder(new Square.Builder());
    this.registerBuilder(new Rectangle.Builder());
    this.registerBuilder(new Rhombus.Builder());
    this.registerBuilder(new Parallelogram.OffsetBuilder());
    this.registerBuilder(new Parallelogram.AngleBuilder());
    this.registerBuilder(new Trapezoid.OffsetBuilder());
    this.registerBuilder(new Trapezoid.AngleBuilder());
  }

  /**
   * Adds a {@code ShapeBuilder} to this factory's mapping for
   * later access.
   *
   * @param builder The {@code ShapeBuilder} to add.
   */
  protected void registerBuilder(ShapeBuilder builder) {
    HashMap<String, ShapeBuilder> variations;
    variations = this.builders.get(builder.getTargetShape());

    if (variations == null) {
      variations = new HashMap<>();
      this.builders.put(builder.getTargetShape(), variations);
    }
    variations.put(builder.getVariation(), builder);
  }

  /**
   * Gets an iterator of all builder variations for a given
   * {@code Shape} name.
   *
   * @param shape The name of the {@code Shape} to get all
   *              build variations for.
   * @return ReadOnlyIterator, an iterator of all builder
   *         variations for the given {@code Shape} name.
   */
  public ReadOnlyIterator<ShapeBuilder> getBuilders(String shape) {
    this.checkShapeKey(shape);
    return new ReadOnlyIterator<>(
      this.builders.get(shape).values().iterator()
    );
  }

  /**
   * Gets the builder for the given {@code Shape} name and
   * variation name.
   *
   * @param shape     The name of the {@code Shape} to get the
   *                  builder for.
   * @param variation The name of the builder variation to
   *                  get.
   * @return ShapeBuilder, the builder associated with the
   *         given {@code Shape} and variation.
   */
  public ShapeBuilder getBuilder(String shape, String variation) {
    this.checkShapeKey(shape);
    HashMap<String, ShapeBuilder> variations = this.builders.get(shape);
    if (!variations.containsKey(variation)) {
      throw new IllegalArgumentException("Unknown variation: "+variation+" for shape "+shape);
    }
    return variations.get(variation);
  }

  /**
   * Gets an iterator of all the {@code Shape} names in this
   * factory.
   *
   * @return ReadOnlyIterator, an iterator of all the
   *         {@code Shape} names in this factory.
   */
  public ReadOnlyIterator<String> getShapes() {
    return new ReadOnlyIterator<>(this.builders.keySet().iterator());
  }

  /**
   * Gets the number of {@code Shape} names mapped by this
   * factory.
   *
   * @return int, the number of {@code Shape} names mapped by
   *         this factory.
   */
  public int getNumShapes() {
    return this.builders.keySet().size();
  }

  /**
   * Gets the number of builder variations mapped by this
   * factory for the given {@code Shape} name.
   *
   * @param shape The {@code Shape} name to get a variation
   *              count for.
   * @return int, the number of builder variations mapped by
   *         this factory for the given {@code Shape} name.
   */
  public int getNumVariations(String shape) {
    this.checkShapeKey(shape);
    return this.builders.get(shape).size();
  }
}
