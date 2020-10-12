package geometrygraphics;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import shapes.Shape;
import shapes.ShapeFactory;

public class ShapeFactoryPanel extends JPanel implements
  ItemListener,
  ChangeListener,
  ActionListener {

  private final JComboBox<String> shapeComboBox;
  private final JPanel shapeCardPanel;
  private final CardLayout shapeCards;
  private final HashMap<String, ShapeBuilderPanel> shapeBuilderPanels;
  private ShapeBuilderPanel currentBuilderPanel;
  private final ShapePreviewPanel shapePreviewPanel;
  private final JTextArea errorTextArea;
  private final ActionListener listener;

  public ShapeFactoryPanel(ActionListener listener, ShapeFactory factory) {
    super();

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setAlignmentX(JPanel.CENTER_ALIGNMENT);

    this.listener = listener;

    this.shapeBuilderPanels = new HashMap<>();

    this.shapeCards = new CardLayout();
    this.shapeCardPanel = new JPanel();
    this.shapeCardPanel.setLayout(this.shapeCards);

    String[] shapeNames = new String[factory.getNumShapes()];
    Iterator<String> shapes = factory.getShapes().iterator();

    Dimension largestCard = new Dimension();

    for (int i = 0; i < shapeNames.length; ++i) {
      String shapeName = shapes.next();
      int numVariations = factory.getNumVariations(shapeName);

      shapeNames[i] = shapeName;

      ShapeBuilderPanel panel;
      if (numVariations == 1) {
        panel = new SingleShapeBuilderPanel(
          this,
          factory.getBuilders(shapeName).iterator().next()
        );
      } else {
        panel = new VariableShapeBuilderPanel(
          this,
          factory.getBuilders(shapeName),
          numVariations
        );
      }

      if (
        panel.getPreferredSize().width*panel.getPreferredSize().height
          > largestCard.width*largestCard.height
      ) {
        largestCard = panel.getPreferredSize();
      }

      this.shapeBuilderPanels.put(shapeName, panel);
      this.shapeCardPanel.add(panel, shapeName);
    }

    this.shapeCardPanel.setPreferredSize(largestCard);

    this.currentBuilderPanel = this.shapeBuilderPanels.get(shapeNames[0]);
    this.shapeCards.show(this.shapeCardPanel, shapeNames[0]);

    this.shapeComboBox = new JComboBox<>(shapeNames);
    this.shapeComboBox.setEditable(false);
    this.shapeComboBox.addItemListener(this);

    this.shapePreviewPanel = new ShapePreviewPanel(250, 250);

    this.errorTextArea = new JTextArea(3, 20);
    this.errorTextArea.setEditable(false);
    this.errorTextArea.setLineWrap(true);

    JButton createButton = new JButton("Create Shape");
    createButton.setActionCommand("Create Shape");
    createButton.addActionListener(this);
    createButton.setAlignmentX(0.5f);
    JButton createPinButton = new JButton("Create Shape at Pin");
    createPinButton.setActionCommand("Create Shape at Pin");
    createPinButton.addActionListener(this);
    createPinButton.setAlignmentX(0.5f);

    this.add(this.shapeComboBox);
    this.add(this.shapeCardPanel);
    this.add(this.shapePreviewPanel);
    this.add(this.errorTextArea);
    this.add(createButton);
    this.add(createPinButton);
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    this.shapeCards.show(this.shapeCardPanel, (String)(e.getItem()));
    this.currentBuilderPanel = this.shapeBuilderPanels.get((String)(e.getItem()));
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    ShapeBuilderPanel panel = (ShapeBuilderPanel)(e.getSource());
    Shape product = panel.getProduct();
    if (product != null) {
      this.errorTextArea.setText("");
      this.shapePreviewPanel.setShapeToPreview(product);
    } else {
      this.errorTextArea.setText(panel.getErrorText());
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.errorTextArea.getText().length() > 0) {
      return;
    }
    ActionEvent newEvent = new ActionEvent(
      this.currentBuilderPanel.getNewProduct(),
      ActionEvent.ACTION_FIRST,
      e.getActionCommand()
    );
    this.listener.actionPerformed(newEvent);
  }
}
