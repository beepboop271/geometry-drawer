import geometrygraphics.GeometryDrawerFrame;

/**
 * Starts the GUI of the geometry drawer.
 *
 * @author Kevin Qiao
 * @version 1.0
 */
class Main {
  /**
   * Starts the GUI on swing's threads to run the program.
   *
   * @param args The command line arguments (none used).
   */
  public static void main(String[] args) {
    // don't call repaint in an infinite loop because it's a
    // massive waste of computing power. most of the panels
    // don't change, so they can manually repaint their own
    // dirty rects when they need to.
    new GeometryDrawerFrame();
  }
}
