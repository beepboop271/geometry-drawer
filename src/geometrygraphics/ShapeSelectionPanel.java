package geometrygraphics;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import shapes.Shape;

public class ShapeSelectionPanel extends JPanel implements ChangeListener, ActionListener {
  private static final JLabel EMPTY_LABEL = new JLabel("No selection");

  private final ActionListener listener;
  private final JPanel selectedShapesPanel;
  private final HashMap<Shape, ShapeInfoPanel> selectedShapes;
  private final SingleShapeSelectionPanel singleSelectionPanel;
  
  private final LabelledTextSliderPanel translateAllXSlider;
  private final LabelledTextSliderPanel translateAllYSlider;

  public ShapeSelectionPanel(ActionListener listener) {
    super();

    this.listener = listener;
    this.selectedShapes = new HashMap<>();

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    this.selectedShapesPanel = new JPanel();
    JScrollPane scrollPanel = new JScrollPane(this.selectedShapesPanel);
    scrollPanel.getVerticalScrollBar().setUnitIncrement(10);

    this.selectedShapesPanel.setLayout(
      new BoxLayout(this.selectedShapesPanel, BoxLayout.Y_AXIS)
    );

    ShapeSelectionPanel.EMPTY_LABEL.setAlignmentX(JComponent.LEFT_ALIGNMENT);
    this.selectedShapesPanel.add(ShapeSelectionPanel.EMPTY_LABEL);

    this.singleSelectionPanel = new SingleShapeSelectionPanel(this);

    this.translateAllXSlider = new LabelledTextSliderPanel(
      "Translate all x",
      Integer.MIN_VALUE,
      Integer.MAX_VALUE,
      0,
      -500,
      500,
      null
    );
    this.translateAllXSlider.setMaximumSize(new Dimension(300, 60));
    this.translateAllYSlider = new LabelledTextSliderPanel(
      "Translate all y",
      Integer.MIN_VALUE,
      Integer.MAX_VALUE,
      0,
      -500,
      500,
      null
    );
    this.translateAllYSlider.setMaximumSize(new Dimension(300, 60));
    JButton translateAllButton = new JButton("Apply");
    translateAllButton.setActionCommand("Apply Translate All");
    translateAllButton.addActionListener(this);

    this.add(scrollPanel);
    this.add(this.singleSelectionPanel);
    this.add(this.translateAllXSlider);
    this.add(this.translateAllYSlider);
    this.add(translateAllButton);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void stateChanged(ChangeEvent e) {
    LinkedHashSet<Shape> selectedShapes = (LinkedHashSet<Shape>)(e.getSource());
    this.selectedShapes.clear();
    this.selectedShapesPanel.removeAll();
    for (Shape shape : selectedShapes) {
      ShapeInfoPanel panel = new ShapeInfoPanel(shape, this);
      this.selectedShapes.put(shape, panel);
      this.selectedShapesPanel.add(panel);
    }
    if (this.selectedShapes.size() == 0) {
      this.selectedShapesPanel.add(ShapeSelectionPanel.EMPTY_LABEL);
    }
    this.selectedShapesPanel.add(Box.createVerticalGlue());
    this.selectedShapesPanel.validate();
    this.selectedShapesPanel.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Select Shape": {
        this.singleSelectionPanel.setShapeToDisplay((Shape)(e.getSource()));
        break;
      }
      case "Remove Shape": {
        Shape source = (Shape)(e.getSource());
        if (source == this.singleSelectionPanel.getShape()) {
          this.singleSelectionPanel.clear(true);
        }
        ShapeInfoPanel infoPanelToRemove = this.selectedShapes.get(source);
        if (infoPanelToRemove != null) {
          this.selectedShapes.remove(source);
          this.selectedShapesPanel.remove(infoPanelToRemove);
          if (this.selectedShapes.size() == 0) {
            this.selectedShapesPanel.removeAll();
            this.selectedShapesPanel.add(ShapeSelectionPanel.EMPTY_LABEL);
          }
          this.selectedShapesPanel.validate();
          this.selectedShapesPanel.repaint();
        }
        this.listener.actionPerformed(e);
        break;
      }
      case "Apply Translate": {
        this.listener.actionPerformed(e);
        break;
      }
      case "Apply Rotate": {
        this.listener.actionPerformed(e);
        break;
      }
      case "Apply Translate All": {
        ActionEvent newEvent = new ActionEvent(
          new Point(
            this.translateAllXSlider.getValue(),
            this.translateAllYSlider.getValue()
          ),
          ActionEvent.ACTION_FIRST,
          "Apply Translate All"
        );
        this.listener.actionPerformed(newEvent);
        break;
      }
    }
  }
}
