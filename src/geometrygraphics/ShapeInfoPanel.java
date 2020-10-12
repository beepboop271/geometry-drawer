package geometrygraphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import shapes.Shape;

/**
 * Displays info about a single Shape, as well as select and
 * remove buttons to get more detailed options or remove the
 * Shape from the drawing.
 * <p>
 * Events:
 * <p>
 * When the select or remove JButtons are pressed
 * (ActionListener), the event is forwarded to the
 * ShapeSelectionPanel with an updated source of the Shape
 * to perform the action on.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class ShapeInfoPanel extends JPanel implements ActionListener {
  
  private final ActionListener listener;
  private final Shape shapeToDisplay;

  public ShapeInfoPanel(Shape shapeToDisplay, ActionListener listener) {
    super();

    this.shapeToDisplay = shapeToDisplay;
    this.listener = listener;

    this.setLayout(new BorderLayout());

    this.setPreferredSize(new Dimension(250, 75));
    this.setMaximumSize(new Dimension(350, 75));
    
    JTextArea info = new JTextArea(shapeToDisplay.toString(), 3, 30);
    info.setEditable(false);
    info.setLineWrap(true);

    JButton selectButton = new JButton("Select");
    selectButton.setActionCommand("Select Shape");
    selectButton.addActionListener(this);
    selectButton.setMaximumSize(new Dimension(100, 25));
    JButton removeButton = new JButton("Remove");
    removeButton.setActionCommand("Remove Shape");
    removeButton.addActionListener(this);
    selectButton.setMaximumSize(new Dimension(100, 25));

    this.add(info, BorderLayout.PAGE_START);
    this.add(selectButton, BorderLayout.LINE_START);
    this.add(removeButton, BorderLayout.LINE_END);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.listener.actionPerformed(new ActionEvent(
      this.shapeToDisplay,
      ActionEvent.ACTION_FIRST,
      e.getActionCommand()
    ));
  }
}
