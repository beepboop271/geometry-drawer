package shapes;

import java.util.HashMap;

public class ShapeFactory {
  private HashMap<String, HashMap<String, ShapeBuilder>> builders;

  public ShapeFactory() {
    this.builders = new HashMap<>();

    this.registerBuilders();
  }

  public ReadOnlyIterator<String> getShapes() {
    return new ReadOnlyIterator<>(this.builders.keySet().iterator());
  }

  private void checkShapeKey(String shape) {
    if (!this.builders.containsKey(shape)) {
      throw new IllegalArgumentException("Unknown shape: "+shape);
    }
  }

  public int getNumShapes() {
    return this.builders.keySet().size();
  }

  public int getNumVariations(String shape) {
    this.checkShapeKey(shape);
    return this.builders.get(shape).size();
  }

  public ReadOnlyIterator<ShapeBuilder> getBuilders(String shape) {
    this.checkShapeKey(shape);
    return new ReadOnlyIterator<>(
      this.builders.get(shape).values().iterator()
    );
  }

  public ShapeBuilder getBuilder(String shape, String variation) {
    this.checkShapeKey(shape);
    HashMap<String, ShapeBuilder> variations = this.builders.get(shape);
    if (!variations.containsKey(variation)) {
      throw new IllegalArgumentException("Unknown variation: "+variation+" for shape "+shape);
    }
    return variations.get(variation);
  }

  private void registerBuilders() {
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

  private void registerBuilder(ShapeBuilder builder) {
    HashMap<String, ShapeBuilder> variations;
    variations = this.builders.get(builder.getTargetShape());

    if (variations == null) {
      variations = new HashMap<>();
      this.builders.put(builder.getTargetShape(), variations);
    }
    variations.put(builder.getVariation(), builder);
  }
}
