package geometrygraphics;

import java.awt.Rectangle;
import java.awt.Graphics2D;

import shapes.Shape;

/**
 * Draws a single Shape. No events.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public class ShapePreviewPanel extends CoordinatePlanePanel {

  private Shape shapeToPreview;

  public ShapePreviewPanel(int width, int height) {
    super(width, height, 0, 0);
    this.shapeToPreview = null;
  }

  @Override
  public void paintWithGraphics2D(Graphics2D g2d) {
    if (this.shapeToPreview != null) {
      this.shapeToPreview.draw(g2d);
    }
  }

  public void setShapeToPreview(Shape shape) {
    if (this.shapeToPreview == null) {
      Rectangle rectToPaint = this.getClipFromCartesian(shape.getBounds());
      this.shapeToPreview = shape;
      this.repaint(rectToPaint);
    }
    Rectangle rectToClear = this.getClipFromCartesian(this.shapeToPreview.getBounds());
    Rectangle rectToPaint = this.getClipFromCartesian(shape.getBounds());
    this.shapeToPreview = shape;
    this.repaint(rectToClear);
    this.repaint(rectToPaint);
  }
}
