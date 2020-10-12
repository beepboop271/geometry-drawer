package geometrygraphics;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import shapes.Shape;

/**
 * Displays translate and possibly rotate options for a
 * single Shape that has already been created.
 * <p>
 * Events:
 * <p>
 * When the apply button is clicked, the transformation
 * operation is forwarded to GeometryDrawerFrame.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class SingleShapeSelectionPanel extends JPanel implements
  ActionListener {
  private static final JLabel EMPTY_LABEL = new JLabel("No detail selection");

  private ActionListener listener;

  private Shape shapeToDisplay;
  private LabelledTextSliderPanel xSlider;
  private LabelledTextSliderPanel ySlider;
  private LabelledTextSliderPanel rotationSlider;

  public SingleShapeSelectionPanel(ActionListener listener) {
    super();

    this.listener = listener;

    this.setPreferredSize(new Dimension(300, 200));
    this.setMaximumSize(new Dimension(350, 200));

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    SingleShapeSelectionPanel.EMPTY_LABEL.setAlignmentX(0.5f);
    this.add(SingleShapeSelectionPanel.EMPTY_LABEL);

    this.xSlider = null;
    this.ySlider = null;
    this.rotationSlider = null;
    this.shapeToDisplay = null;
  }

  public void clear(boolean empty) {
    this.xSlider = null;
    this.ySlider = null;
    this.rotationSlider = null;
    this.shapeToDisplay = null;

    this.removeAll();

    if (empty) {
      this.add(SingleShapeSelectionPanel.EMPTY_LABEL);
      this.validate();
      this.repaint();
    }
  }

  public void setShapeToDisplay(Shape s) {
    this.clear(false);
    this.shapeToDisplay = s;

    JTextArea info = new JTextArea(shapeToDisplay.toString(), 3, 30);
    info.setEditable(false);
    info.setLineWrap(true);
    this.add(info);
    if (s instanceof Translateable) {
      this.xSlider =
        new LabelledTextSliderPanel(
          "Translate x",
          Integer.MIN_VALUE,
          Integer.MAX_VALUE,
          0,
          -500,
          500,
          null
        );
      this.add(this.xSlider);
      this.ySlider =
        new LabelledTextSliderPanel(
          "Translate y",
          Integer.MIN_VALUE,
          Integer.MAX_VALUE,
          0,
          -500,
          500,
          null
        );
      this.add(this.ySlider);
    }
    if (s instanceof Rotateable) {
      this.rotationSlider =
        new LabelledTextSliderPanel("Rotate", -180, 180, 0, -180, 180, null);
      this.add(this.rotationSlider);
    }

    JButton applyButton = new JButton("Apply");
    applyButton.setActionCommand("Apply");
    applyButton.addActionListener(this);

    this.add(applyButton);

    this.validate();
    this.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.shapeToDisplay instanceof Translateable) {
      this.listener.actionPerformed(
        new ActionEvent(this, ActionEvent.ACTION_FIRST, "Apply Translate")
      );
    }
    if (this.shapeToDisplay instanceof Rotateable) {
      this.listener.actionPerformed(
        new ActionEvent(this, ActionEvent.ACTION_FIRST, "Apply Rotate")
      );
    }
  }

  public Shape getShape() {
    return this.shapeToDisplay;
  }

  public Point getTranslate() {
    if (this.xSlider == null) {
      return new Point();
    }
    return new Point(this.xSlider.getValue(), this.ySlider.getValue());
  }

  public int getRotation() {
    if (this.rotationSlider == null) {
      return 0;
    }
    return this.rotationSlider.getValue();
  }
}
