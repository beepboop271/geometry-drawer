package geometrygraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

/**
 * A JPanel containing x-y axes. The origin can be placed
 * anywhere in the panel. The y-axis is positive upwards,
 * not downwards.
 * <p>
 * Events:
 * <p>
 * Mouse scrolling zooms the panel in/out.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
public abstract class CoordinatePlanePanel extends JPanel implements MouseWheelListener {
  private static final double SCROLL_SCALE_FACTOR = 0.05;
  private static final Color BACKGROUND = new Color(255, 255, 255);
  private static final Color ORIGIN_LINES = new Color(0, 0, 0);
  private double scale;
  private double xAxisPosition;
  private double yAxisPosition;

  public CoordinatePlanePanel(
    int width,
    int height,
    double xAxisPosition,
    double yAxisPosition
  ) {
    super();

    this.setMinimumSize(new Dimension(width, height));
    this.setPreferredSize(new Dimension(width, height));

    this.xAxisPosition = xAxisPosition;
    this.yAxisPosition = yAxisPosition;

    this.addMouseWheelListener(this);

    this.scale = 1.0;
  }

  public CoordinatePlanePanel(int width, int height) {
    this(width, height, 0.5, 0.5);
  }

  public abstract void paintWithGraphics2D(Graphics2D g2d);

  public Point transformScreenToCartesian(Point p) {
    return new Point(
      (int)((p.x - (this.getWidth()*this.xAxisPosition))/this.scale),
      (int)((-p.y + (this.getHeight()*this.yAxisPosition))/this.scale)
    );
  }

  public Rectangle getClipFromCartesian(Rectangle rect) {
    Rectangle r = new Rectangle(
      (int)((this.scale*rect.x) + (this.getWidth()*this.xAxisPosition)),
      (int)((this.scale*-rect.y) + (this.getHeight()*this.yAxisPosition)),
      (int)Math.ceil(this.scale*rect.width),
      (int)Math.ceil(this.scale*rect.height)
    );
    r.grow((int)(2*this.scale), (int)(2*this.scale));
    return r;
  }

  @Override
  public void paintComponent(Graphics g) {
    int width = this.getWidth();
    int height = this.getHeight();
  
    g.setColor(CoordinatePlanePanel.BACKGROUND);
    g.fillRect(0, 0, width, height);
    g.setColor(CoordinatePlanePanel.ORIGIN_LINES);
    g.drawLine((int)(width*this.xAxisPosition), 0, (int)(width*this.xAxisPosition), height);
    g.drawLine(0, (int)(height*this.yAxisPosition), width, (int)(height*this.yAxisPosition));

    Graphics2D g2d = (Graphics2D)g;
    g2d.translate((int)(width*this.xAxisPosition), (int)(height*this.yAxisPosition));
    // negative y so that it turns from screen coords to typical
    // cartesian coords
    g2d.scale(this.scale, -this.scale);

    this.paintWithGraphics2D(g2d);
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    int rotation = e.getWheelRotation();

    double factor = 1 - Math.copySign(CoordinatePlanePanel.SCROLL_SCALE_FACTOR, rotation);

    for (int i = 0; i < Math.abs(rotation); ++i) {
      this.scale *= factor;
    }

    this.repaint();
  }
}
