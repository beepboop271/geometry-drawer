package geometrygraphics;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import shapes.InvalidShapeException;
import shapes.Shape;
import shapes.ShapeBuilder;

/**
 * A JPanel which can build one type of shape.
 * <p>
 * Events:
 * <p>
 * When a slider within this panel is changed
 * (ChangeListener), the ShapeBuilder associated with this
 * panel builds a new Shape and emits a new ChangeEvent to
 * ShapeFactoryPanel after building.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public abstract class ShapeBuilderPanel extends JPanel implements
  ChangeListener {

  private final ChangeListener listener;

  private Shape product;
  private String errorText;

  public ShapeBuilderPanel(ChangeListener listener) {
    super();

    this.listener = listener;

    this.product = null;
  }

  protected abstract ShapeBuilder getBuilder();

  @Override
  public void stateChanged(ChangeEvent e) {
    LabelledTextSliderPanel source = (LabelledTextSliderPanel)(e.getSource());
    ShapeBuilder builder = this.getBuilder();
    builder.withArg(source.getLabel(), source.getValue());
    try {
      this.product = builder.build();
    } catch (InvalidShapeException ex) {
      this.product = null;
      this.errorText = ex.getMessage();
    } finally {
      this.listener.stateChanged(new ChangeEvent(this));
    }
  }

  public Shape getProduct() {
    return this.product;
  }

  public Shape getNewProduct() {
    return this.getBuilder().build();
  }

  public String getErrorText() {
    return this.errorText;
  }
}
