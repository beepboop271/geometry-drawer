package geometrygraphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.LinkedHashSet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import shapes.Circle;
import shapes.Shape;

public class ShapeDrawingPanel extends CoordinatePlanePanel implements
  MouseListener {

  private LinkedHashSet<Shape> shapes;
  private ChangeListener listener;
  private Shape mousePin;

  public ShapeDrawingPanel(int width, int height, ChangeListener listener) {
    super(width, height);

    this.listener = listener;
    this.addMouseListener(this);

    this.shapes = new LinkedHashSet<>();

    this.mousePin = new Circle.Builder()
      .withDiameter(4)
      .withPosition(0, 0)
      .withColour(255, 0, 0)
      .build();
  }

  @Override
  public void paintWithGraphics2D(Graphics2D g2d) {
    for (Shape shape : this.shapes) {
      shape.draw(g2d);
    }

    this.mousePin.draw(g2d);
    Rectangle r = this.mousePin.getBounds();
    g2d.setColor(new Color(0, 0, 0));
    g2d.drawRect(r.x, r.y-r.height, r.width, r.height);
  }

  public void addShape(Shape s) {
    this.shapes.add(s);
    this.repaint(this.getClipFromCartesian(s.getBounds()));
  }

  public void addShapeAtPin(Shape s) {
    s.translate(this.mousePin.getX()-s.getX(), this.mousePin.getY()-s.getY());
    this.addShape(s);
  }

  public void removeShape(Shape s) {
    Rectangle rectToClear = s.getBounds();
    this.shapes.remove(s);
    this.repaint(this.getClipFromCartesian(rectToClear));
  }

  public void translate(Shape s, int dx, int dy) {
    Rectangle rectToClear = s.getBounds();
    s.translate(dx, dy);
    this.repaint(this.getClipFromCartesian(rectToClear));
    this.repaint(this.getClipFromCartesian(s.getBounds()));
  }

  public void translateAll(int dx, int dy) {
    for (Shape shape : this.shapes) {
      shape.translate(dx, dy);
    }
    this.repaint();
  }

  public void rotate(Shape s, int rotation) {
    Rectangle rectToClear = s.getBounds();
    ((Rotateable)s).rotateBy(rotation);
    this.repaint(this.getClipFromCartesian(rectToClear));
    this.repaint(this.getClipFromCartesian(s.getBounds()));
  }

  public void writeSerializedShapes(OutputStream out) {
    try {
      new ObjectOutputStream(out).writeObject(this.shapes);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  public void readSerializedShapes(InputStream in) {
    try {
      Object o = new ObjectInputStream(in).readObject();
      if (!(o instanceof LinkedHashSet<?>)) {
        throw new IllegalArgumentException(
          "Could not read a shape list from the given input"
        );
      }

      LinkedHashSet<Shape> shapes = (LinkedHashSet<Shape>)o;
      for (Shape shape : shapes) {
        this.addShape(shape);
      }
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException(
        "Could not read a shape list from the given input"
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    Rectangle rectToClear = this.getClipFromCartesian(this.mousePin.getBounds());

    Point p = this.transformScreenToCartesian(e.getPoint());

    this.mousePin.translate(p.x-this.mousePin.getX(), p.y-this.mousePin.getY());
    
    this.repaint(rectToClear);
    this.repaint(this.getClipFromCartesian(this.mousePin.getBounds()));
  
    this.selectIntersecting(p);
  }

  public void selectIntersecting(Point p) {
    LinkedHashSet<Shape> selectedShapes = new LinkedHashSet<>();

    for (Shape shape : this.shapes) {
      if (shape.contains(p)) {
        selectedShapes.add(shape);
      }
    }

    this.listener.stateChanged(new ChangeEvent(selectedShapes));
  }

  public void selectAll() {
    LinkedHashSet<Shape> selectedShapes = new LinkedHashSet<>(this.shapes);

    this.listener.stateChanged(new ChangeEvent(selectedShapes));
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
}
