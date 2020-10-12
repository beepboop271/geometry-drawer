package geometrygraphics;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import shapes.Shape;
import shapes.ShapeFactory;

/**
 * The main JFrame of the program. Contains the 3 main views
 * to see, create, and manage shapes.
 * <p>
 * Events:
 * <p>
 * ShapeFactoryPanel emits "Create Shape" and "Create Shape
 * at Pin" when the corresponding buttons are pressed, and
 * those events trigger shape additions in
 * ShapeDrawingPanel.
 * <p>
 * ShapeSelectionPanel emits "Remove Shape", "Apply
 * Translate", "Apply Rotate", and "Apply Translate All"
 * when the corresponding buttons are pressed, and those
 * events cause removals or transformations in
 * ShapeDrawingPanel.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class GeometryDrawerFrame extends JFrame implements ActionListener {

  private final ShapeDrawingPanel shapeDrawingPanel;

  public GeometryDrawerFrame() {
    super("Geometry Drawing Pain");

    JPanel contentPane = new JPanel();

    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
    contentPane.setAlignmentY(JPanel.TOP_ALIGNMENT);
    
    ShapeSelectionPanel selectionPanel = new ShapeSelectionPanel(this);

    this.shapeDrawingPanel = new ShapeDrawingPanel(600, 600, selectionPanel);

    contentPane.add(new ShapePanel(this.shapeDrawingPanel));
    contentPane.add(new ShapeFactoryPanel(this, new ShapeFactory()));
    contentPane.add(selectionPanel);

    this.setContentPane(contentPane);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setUndecorated(false);
    this.pack();
    this.setResizable(false);
    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Create Shape": {
        this.shapeDrawingPanel.addShape((Shape)(e.getSource()));
        break;
      }
      case "Create Shape at Pin": {
        this.shapeDrawingPanel.addShapeAtPin((Shape)(e.getSource()));
        break;
      }
      case "Remove Shape": {
        this.shapeDrawingPanel.removeShape((Shape)(e.getSource()));
        break;
      }
      case "Apply Translate": {
        SingleShapeSelectionPanel source = (SingleShapeSelectionPanel)(e.getSource());
        Point translation = source.getTranslate();
        this.shapeDrawingPanel.translate(
          source.getShape(),
          translation.x,
          translation.y
        );
        break;
      }
      case "Apply Rotate": {
        SingleShapeSelectionPanel source = (SingleShapeSelectionPanel)(e.getSource());
        this.shapeDrawingPanel.rotate(
          source.getShape(),
          source.getRotation()
        );
        break;
      }
      case "Apply Translate All": {
        Point source = (Point)(e.getSource());
        this.shapeDrawingPanel.translateAll(source.x, source.y);
        break;
      }
    }
  }
}
