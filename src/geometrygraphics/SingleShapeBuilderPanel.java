package geometrygraphics;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import shapes.ShapeBuilder;

public class SingleShapeBuilderPanel extends ShapeBuilderPanel {

  private static final int DEFAULT_SLIDER_RANGE = 400;

  private final ShapeBuilder builder;

  public SingleShapeBuilderPanel(
    ChangeListener listener,
    ShapeBuilder builder
  ) {
    super(listener);

    this.builder = builder;

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setAlignmentX(JPanel.CENTER_ALIGNMENT);

    for (ShapeBuilder.Arg arg : this.builder.getRequiredArgs()) {
      int sliderMin = arg.getMinValue();
      if (sliderMin == Integer.MIN_VALUE) {
        sliderMin = -SingleShapeBuilderPanel.DEFAULT_SLIDER_RANGE;
      }
      int sliderMax = arg.getMaxValue();
      if (sliderMax == Integer.MAX_VALUE) {
        sliderMax = SingleShapeBuilderPanel.DEFAULT_SLIDER_RANGE;
      }
      
      this.add(new LabelledTextSliderPanel(
        arg.getName(),
        arg.getMinValue(),
        arg.getMaxValue(),
        builder.getArg(arg.getName()),
        sliderMin,
        sliderMax,
        this
      ));
    }
  }

  @Override
  protected ShapeBuilder getBuilder() {
    return this.builder;
  }
}
