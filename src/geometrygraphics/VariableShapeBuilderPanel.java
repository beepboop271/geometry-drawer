package geometrygraphics;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import shapes.ShapeBuilder;

public class VariableShapeBuilderPanel extends ShapeBuilderPanel implements
  ItemListener {

  private final JComboBox<String> variationComboBox;
  private final JPanel variationCardPanel;
  private final CardLayout variationCards;
  private final HashMap<String, ShapeBuilder> variationBuilders;
  private ShapeBuilder currentBuilder;

  public VariableShapeBuilderPanel(
    ChangeListener listener,
    Iterable<ShapeBuilder> builderIterator,
    int numVariations
  ) {
    super(listener);
    if (numVariations < 2) {
      throw new IllegalArgumentException(
        "Use SingleShapeBuilderPanel instead for one variation"
      );
    }

    this.setLayout(new BorderLayout());

    this.variationBuilders = new HashMap<>();

    this.variationCards = new CardLayout();
    this.variationCardPanel = new JPanel();
    this.variationCardPanel.setLayout(this.variationCards);

    String[] variations = new String[numVariations];
    Iterator<ShapeBuilder> builders = builderIterator.iterator();

    for (int i = 0; i < numVariations; ++i) {
      ShapeBuilder builder = builders.next();
      variations[i] = builder.getVariation();

      this.variationBuilders.put(variations[i], builder);
      this.variationCardPanel.add(
        new SingleShapeBuilderPanel(listener, builder),
        variations[i]
      );
    }

    this.currentBuilder = this.variationBuilders.get(variations[0]);
    this.variationCards.show(
      this.variationCardPanel,
      this.currentBuilder.getVariation()
    );

    this.variationComboBox = new JComboBox<>(variations);
    this.variationComboBox.setEditable(false);
    this.variationComboBox.addItemListener(this);
    this.variationComboBox.setMaximumSize(new Dimension(100, 25));

    this.add(this.variationComboBox, BorderLayout.PAGE_START);
    this.add(this.variationCardPanel, BorderLayout.CENTER);
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    this.currentBuilder = this.variationBuilders.get((String)(e.getItem()));
    this.variationCards.show(
      this.variationCardPanel,
      this.currentBuilder.getVariation()
    );
  }

  @Override
  protected ShapeBuilder getBuilder() {
    return this.currentBuilder;
  }
}
