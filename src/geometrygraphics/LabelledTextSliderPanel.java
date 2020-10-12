package geometrygraphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 */
public class LabelledTextSliderPanel extends JPanel implements
  ChangeListener,
  KeyListener,
  FocusListener,
  AncestorListener {

  private ChangeListener listener;

  private JSlider slider;
  private JTextField textField;

  private String label;
  private boolean sliderValid;
  private int minValue;
  private int maxValue;
  private int value;

  public LabelledTextSliderPanel(
    String label,
    int min,
    int max,
    int initialValue,
    int sliderMin,
    int sliderMax,
    ChangeListener listener
  ) {
    super();

    this.setPreferredSize(new Dimension(300, 35));

    this.addAncestorListener(this);

    this.label = label;
    this.listener = listener;
    this.sliderValid = true;
    this.minValue = min;
    this.maxValue = max;
    this.value = initialValue;

    this.setLayout(new BorderLayout());

    this.add(new JLabel(label), BorderLayout.PAGE_START);

    this.slider = new JSlider(sliderMin, sliderMax, this.value);
    this.slider.addChangeListener(this);
    this.add(this.slider, BorderLayout.CENTER);

    this.textField = new JTextField(Integer.toString(this.value), 4);
    this.textField.addKeyListener(this);
    this.textField.addFocusListener(this);
    this.add(this.textField, BorderLayout.LINE_END);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    String input = this.textField.getText();

    // trying to type a negative value - let user continue
    if (input.equals("-")) {
      return;
    }

    int intInput;
    try {
      intInput = Integer.parseInt(input);
    } catch (NumberFormatException ex) {
      this.textField.setText("");
      return;
    }

    // silently clamp the input: if the min was 10 and the
    // textfield always updated itself to match the clamped
    // value, it would be a pain to enter anything because
    // writing the first digit will always result in "10".
    if ((intInput < this.minValue) || (intInput > this.maxValue)) {
      intInput = Math.min(Math.max(intInput, this.minValue), this.maxValue);
    }

    // prevent the slider from clamping the value if there is no
    // real maximum, and prevent this class from emitting two
    // events on one change
    this.sliderValid = false;
    this.slider.setValue(intInput);
    this.sliderValid = true;

    this.value = intInput;
    if (this.listener != null) {
      this.listener.stateChanged(new ChangeEvent(this));
    }
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    if (this.sliderValid) {
      // setText does not fire any listeners
      this.textField.setText(Integer.toString(this.slider.getValue()));

      this.value = this.slider.getValue();
      if (this.listener != null) {
        this.listener.stateChanged(new ChangeEvent(this));
      }
    }
  }

  @Override
  public void focusGained(FocusEvent e) {
    this.textField.selectAll();
  }

  @Override
  public void focusLost(FocusEvent e) {
    this.textField.select(0, 0);
    this.textField.setText(Integer.toString(this.value));
  }

  @Override
  public void ancestorAdded(AncestorEvent event) {
    this.stateChanged(new ChangeEvent(this));
  }

  public boolean getValueIsAdjusting() {
    return this.slider.getValueIsAdjusting();
  }

  public String getLabel() {
    return this.label;
  }

  public int getValue() {
    return this.value;
  }

  /**
   * @return the minValue
   */
  public int getMinValue() {
    return minValue;
  }

  /**
   * @return the maxValue
   */
  public int getMaxValue() {
    return maxValue;
  }

  // ignored

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
  }

  @Override
  public void ancestorRemoved(AncestorEvent event) {
  }

  @Override
  public void ancestorMoved(AncestorEvent event) {
  }
}
