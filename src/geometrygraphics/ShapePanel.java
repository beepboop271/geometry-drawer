package geometrygraphics;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Holds the main drawing panel, as well as the file IO
 * buttons and the select all button.
 * <p>
 * Events:
 * <p>
 * When a button is clicked (ActionListener), the
 * corresponding action is performed with the main drawing
 * panel (ShapeDrawingPanel).
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class ShapePanel extends JPanel implements ActionListener {

  ShapeDrawingPanel drawingPanel;
  JTextField fileNameField;

  public ShapePanel(ShapeDrawingPanel drawingPanel) {
    super();

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    this.drawingPanel = drawingPanel;
    this.fileNameField = new JTextField("shapes", 20);
    this.fileNameField.setMaximumSize(new Dimension(200, 50));

    this.add(this.drawingPanel);
    this.add(this.fileNameField);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

    JButton selectAllButton = new JButton("Select All");
    selectAllButton.setActionCommand("Select All");
    selectAllButton.addActionListener(this);
    selectAllButton.setPreferredSize(new Dimension(400, 50));
    JButton writeButton = new JButton("Write Shapes");
    writeButton.setActionCommand("Write Shapes");
    writeButton.addActionListener(this);
    selectAllButton.setPreferredSize(new Dimension(150, 50));
    JButton readButton = new JButton("Read Shapes");
    readButton.setActionCommand("Read Shapes");
    readButton.addActionListener(this);
    selectAllButton.setPreferredSize(new Dimension(150, 50));

    buttonPanel.add(writeButton);
    buttonPanel.add(readButton);
    buttonPanel.add(selectAllButton);

    this.add(buttonPanel);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String fileName = this.fileNameField.getText();
    if ((fileName == null) || (fileName.equals(""))) {
      fileName = "shapes";
    }
    try {
      switch (e.getActionCommand()) {
        case "Select All": {
          this.drawingPanel.selectAll();
          break;
        }
        case "Write Shapes": {
          this.drawingPanel.writeSerializedShapes(
            new FileOutputStream(new File(fileName))
          );
          break;
        }
        case "Read Shapes": {
          this.drawingPanel.readSerializedShapes(
            new FileInputStream(new File(fileName))
          );
          break;
        }
      }
    } catch (FileNotFoundException ex) {
      this.fileNameField.setText("File could not be opened");
    }
  }
}
