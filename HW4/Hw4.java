/*


*/

import renderer.scene.*;
import renderer.scene.util.ModelShading;
import renderer.scene.util.DrawSceneGraph;
import renderer.models_L.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.util.function.BiPredicate;
import java.util.ArrayList;

/**
   Implement a GUI with five draggable shapes.
<p>
   Use a list of data structures to represent the shapes.
<p>
   Make this class its own event handler.
*/
public class Hw4 implements KeyListener, ComponentListener,
                            MouseListener, MouseMotionListener
{
   private final int PIXELS_PER_UNIT = 80; // Pixels per unit of camera space.

   // The GUI state data (the Model part of MVC).
   private final JFrame jf;
   private final FrameBufferPanel fbp;
   private Scene scene = null;
   private boolean debugMouse = false;
   private boolean debugMouseExtra = false;
   private boolean debugComponent = false;
   private boolean axesVisible = true;
   private boolean takeScreenshot = false;
   private int screenshotNumber = 0;
   private double right  =  4.5;
   private double left   = -right;
   private double top    =  right;
   private double bottom = -top;
   private boolean mouseInside; // Used to detect mouse exit.
   private int mouseX_fb;       // Used for mouse dragging.
   private int mouseY_fb;
   private ArrayList<ModelInfo> infoList = new ArrayList<>();

   /**
      This constructor instantiates the Scene object,
      initializes it with appropriate geometry, defines
      and attaches all the event handlers, and then
      builds and displays the GUI.
   */
   public Hw4()
   {
      // Build the List of shape data structures.
      infoList.add(new ModelInfo(
                     "Square_1",
                     0, 0, 0.5,
                     Color.red,
                     (mi, v)-> Math.abs(mi.centerX - v.x) < mi.radius
                            && Math.abs(mi.centerY - v.y) < mi.radius));
      infoList.add(new ModelInfo(
                     "Square_2",
                     -2.5, -2.5, 1.0,
                     Color.blue,
                     (mi, v)-> Math.abs(mi.centerX - v.x) < mi.radius
                            && Math.abs(mi.centerY - v.y) < mi.radius));
      infoList.add(new ModelInfo(
                     "Square_3",
                     2.5, 2.5, 1.5,
                     Color.green,
                     (mi, v)-> Math.abs(mi.centerX - v.x) < mi.radius
                            && Math.abs(mi.centerY - v.y) < mi.radius));
      infoList.add(new ModelInfo(
                     "Diamond",
                     2.5, -2.5, 1.5,
                     Color.cyan,
                     (mi, v)-> Math.abs(mi.centerX - v.x)
                             + Math.abs(mi.centerY - v.y) < mi.radius));
      infoList.add(new ModelInfo(
                     "Circle",
                     -2.5, 2.5, 1.5,
                     Color.magenta,
                     (mi, v)-> distance(mi.centerX, mi.centerY, v) < mi.radius));

      // Build this program's scene graph.
      scene = new Scene("Hw4", Camera.projOrtho(left, right, bottom, top));

      scene.addPosition(new Position(new Square(infoList.get(0).radius),
                                     "Square_1",
                                     new Vector(infoList.get(0).centerX,
                                                infoList.get(0).centerY,
                                                0)),
                        new Position(new Square(infoList.get(1).radius),
                                     "Square_2",
                                     new Vector(infoList.get(1).centerX,
                                                infoList.get(1).centerY,
                                                0)),
                        new Position(new Square(infoList.get(2).radius),
                                     "Square_3",
                                     new Vector(infoList.get(2).centerX,
                                                infoList.get(2).centerY,
                                                0)),
                        new Position(new Circle(infoList.get(3).radius, 4),
                                     "Diamond",
                                     new Vector(infoList.get(3).centerX,
                                                infoList.get(3).centerY,
                                                0)),
                        new Position(new Circle(infoList.get(4).radius, 64),
                                     "Circle",
                                     new Vector(infoList.get(4).centerX,
                                                infoList.get(4).centerY,
                                                0)),
                        new Position(new Axes2D(-15, 15, -15, 15, 30, 30),
                                     "Axes",
                                     new Vector(0, 0, 0)));

      ModelShading.setColor(scene.getPosition(0).getModel(), infoList.get(0).color);
      ModelShading.setColor(scene.getPosition(1).getModel(), infoList.get(1).color);
      ModelShading.setColor(scene.getPosition(2).getModel(), infoList.get(2).color);
      ModelShading.setColor(scene.getPosition(3).getModel(), infoList.get(3).color);
      ModelShading.setColor(scene.getPosition(4).getModel(), infoList.get(4).color);

      // Draw a picture of the scene's tree (DAG) data structure.
      //DrawSceneGraph.draw(scene, "Hw4_SG");

      // Create the GUI.

      // Create a FrameBufferPanel that holds a FrameBuffer.
      final int fbWidth  = (int)(2 * right * PIXELS_PER_UNIT);
      final int fbHeight = (int)(2 *   top * PIXELS_PER_UNIT);
      fbp = new FrameBufferPanel(fbWidth, fbHeight);

      // Create a JFrame that will hold the FrameBufferPanel.
      jf = new JFrame("Hw 4");
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // Place the FrameBufferPanel in the JFrame.
      jf.getContentPane().add(fbp, BorderLayout.CENTER);

      // Register the FrameBufferPanel as the source for key events.
      fbp.addKeyListener( this );
      // Register the FrameBufferPanel as the source for mouse events!
      fbp.addMouseListener( this );
      // Register the FrameBufferPanel as the source for mouse events!
      fbp.addMouseMotionListener( this );
      // Register the FrameBufferPanel as the source for component events.
      fbp.addComponentListener( this );

      // Make the gui visible.
      jf.pack();
      jf.setLocationRelativeTo(null);
      jf.setVisible(true);
      print_help_message();
   }


   // Implement the MouseListener interface (5 methods).
   @Override public void mouseEntered(MouseEvent e)
   {
      System.out.println( e );
      mouseInside = true;
      if (debugMouse)
         System.out.println("Mouse inside of the FrameBufferPanel.");

      System.out.flush(); // Because System.out is buffered by PipelineLogger
   }

   @Override public void mouseExited(MouseEvent e)
   {
      System.out.println( e );
      mouseInside = false;
      if (debugMouse)
         System.out.println("Mouse outside of the FrameBufferPanel.");

      // Stop dragging the shapes (set each hit to false).





      System.out.flush(); // Because System.out is buffered by PipelineLogger
   }

   @Override public void mousePressed(MouseEvent e)
   {
      System.out.println( e );

      // Get the mouse click location in the FrameBuffer and
      // convert it to a point in the Camera's image-rectangle.
      // Log the results if debugMouse is true.
      mouseX_fb = ;
      mouseY_fb = REPLACE_THIS;
      final Vertex clicked = null; // call pixel2camera()



      if (debugMouse)
         System.out.printf("(mouseX_fb, mouseY_fb) = (%4d, %4d) ===> "
                         + "(x_ip, y_ip) = (% .4f, % .4f) - mouse pressed\n",
                           mouseX_fb,
                           mouseY_fb,
                           clicked.x,
                           clicked.y);

      // Determine which shapes have been hit by this
      // mouse click. Set their entry in hit[] to true.
      // Log the results if debugMouse is true.
      for (final ModelInfo mi : infoList)
      {



      }

      System.out.flush(); // Because System.out is buffered by PipelineLogger
   }

   @Override public void mouseReleased(MouseEvent e)
   {
      System.out.println( e );

      // Stop dragging the shapes (set each hit to false).




   }

   @Override public void mouseClicked(MouseEvent e){}


   // Implement the MouseMotionListener interface (2 methods).
   @Override public void mouseMoved(MouseEvent e)
   {
      System.out.println( e );
      if (debugMouseExtra)
      {
         // Get the mouse location in the FrameBuffer.
         final int mouseOverX_fb = e.getX();
         final int mouseOverY_fb = e.getY();
         // Calculate the mouse location in the image-plane.
         final Vertex vertex = pixel2camera(fbp.getFrameBuffer(),
                                            mouseOverX_fb,
                                            mouseOverY_fb);
         System.out.printf("(mouseX_fb, mouseY_fb) = (%4d, %4d) ===> "
                         + "(x_ip, y_ip) = (% .4f, % .4f)\n",
                           mouseOverX_fb,
                           mouseOverY_fb,
                           vertex.x,
                           vertex.y);
         System.out.flush(); // Because System.out is buffered by PipelineLogger
      }
   }


   @Override public void mouseDragged(MouseEvent e)
   {
      System.out.println( e );

      if (mouseInside)
      {
         // First, determine how much the mouse has moved.
         // Get the current mouse location (in the MouseEvent
         // object), then find the horizontal and vertical
         // displacement of the current mouse location from the
         // last mouse location, then divide the displacements
         // by the scaling factor of how many framebuffer pixels
         // there are per unit of the image-plane.
         final int mouseDeltaX_fb = REPLACE_THIS;
         final int mouseDeltaY_fb = REPLACE_THIS;
         final double mouseDeltaX_ip = REPLACE_THIS;
         final double mouseDeltaY_ip = REPLACE_THIS;

         // Log the results if debugMouse is true.
         if (debugMouse)
            System.out.printf("(mouseDeltaX_fb, mouseDeltaY_fb) = (% 3d, % 3d),  "
                            + "(deltaX_ip, deltaY_ip) = (% .4f, % .4f)\n",
                              mouseDeltaX_fb,
                              mouseDeltaY_fb,
                              mouseDeltaX_ip,
                              mouseDeltaY_ip);

         // Move (in the image-plane) each shape that
         // was hit by the last mouse pressed event.
         for (int i = 0; i < infoList.size(); ++i)
         {
            if ( infoList.get(i).hit )
            {






               if (debugMouse)
                  System.out.println( infoList.get(i).name );
            }
         }

         // Update the current mouse location.



         // Render again.
         final FrameBuffer fb = fbp.getFrameBuffer();
         fb.clearFB();
         Pipeline.render(scene, fb);
         fbp.repaint();
         System.out.flush(); // Because System.out is buffered by renderer.pipeline.PipelineLogger
      }
   }


   // Implement the ComponentListener interface (4 methods).
   @Override public void componentMoved(ComponentEvent e){}
   @Override public void componentHidden(ComponentEvent e){}
   @Override public void componentShown(ComponentEvent e){}
   @Override public void componentResized(ComponentEvent e)
   {
      System.out.println( e );

      if (debugComponent)
         System.out.printf("JFrame [w = %d, h = %d]: " +
                           "FrameBufferPanel [w = %d, h = %d].\n",
                           fbp.getTopLevelAncestor().getWidth(),
                           fbp.getTopLevelAncestor().getHeight(),
                           fbp.getWidth(),
                           fbp.getHeight());

      // Get the new size of the FrameBufferPanel.
      final int w = fbp.getWidth();
      final int h = fbp.getHeight();

      // Set the left, right top, bottom parameters
      // for the Camera's view rectangle.







      // Create a new FrameBuffer that fits the new window size.
      final FrameBuffer fb = new FrameBuffer(w, h);
      fbp.setFrameBuffer(fb);
      Pipeline.render(scene, fb);
      fbp.repaint();
      System.out.flush(); // Because System.out is buffered by renderer.pipeline.PipelineLogger
   }


   // Implement the KeyListener interface (3 methods).
   @Override public void keyPressed(KeyEvent e){}
   @Override public void keyReleased(KeyEvent e){}
   @Override public void keyTyped(KeyEvent e)
   {
      //System.out.println( e );
      final char c = e.getKeyChar();
      if ('h' == c)
      {
         print_help_message();
      }
      else if ('d' == c)
      {
         scene.debug = ! scene.debug;
      }
      else if ('i' == c)
      {
         debugMouse = ! debugMouse;
         System.out.print("Mouse event debugging information is turned ");
         System.out.println(debugMouse ? "on." : "off.");
      }
      else if ('j' == c)
      {
         debugMouseExtra = ! debugMouseExtra;
         System.out.print("Extra mouse event debugging information is turned ");
         System.out.println(debugMouse ? "on." : "off.");
      }
      else if ('c' == c)
      {
         debugComponent = ! debugComponent;
         System.out.print("Component event debugging information is turned ");
         System.out.println(debugComponent ? "on." : "off.");
      }
      else if ('s' == c) // Display shape information.
      {
         displayShapes();
      }
      else if ('w' == c) // Display window information.
      {
         displayWindow();
      }
      else if ('=' == c) // Reset the translations of the 5 shapes.
      {
         // Reset centerX[] and centerY[] for each shape.
         infoList.get(0).centerX =  0.0;
         infoList.get(0).centerY =  0.0;
         infoList.get(1).centerX = -2.5;
         infoList.get(1).centerY = -2.5;
         infoList.get(2).centerX =  2.5;
         infoList.get(2).centerY =  2.5;
         infoList.get(3).centerX =  2.5;
         infoList.get(3).centerY = -2.5;
         infoList.get(4).centerX = -2.5;
         infoList.get(4).centerY =  2.5;
         // Reset each position's translation vector.
         scene.getPosition(0).translate(infoList.get(0).centerX,
                                        infoList.get(0).centerY,
                                        0);
         scene.getPosition(1).translate(infoList.get(1).centerX,
                                        infoList.get(1).centerY,
                                        0);
         scene.getPosition(2).translate(infoList.get(2).centerX,
                                        infoList.get(2).centerY,
                                        0);
         scene.getPosition(3).translate(infoList.get(3).centerX,
                                        infoList.get(3).centerY,
                                        0);
         scene.getPosition(4).translate(infoList.get(4).centerX,
                                        infoList.get(4).centerY,
                                        0);
      }
      else if ('a' == c)
      {
         axesVisible = ! axesVisible;
         scene.getPosition(5).visible = axesVisible;
      }
      else if ('+' == c)
      {
         takeScreenshot = true;
      }

      // Render again.
      final FrameBuffer fb = fbp.getFrameBuffer();
      fb.clearFB();
      Pipeline.render(scene, fb);
      if (takeScreenshot)
      {
         fb.dumpFB2File(String.format("Screenshot%03d.png",
                                      screenshotNumber),
                        "png");
         ++screenshotNumber;
         takeScreenshot = false;
      }
      fbp.repaint();
      System.out.flush(); // Because System.out is buffered by PipelineLogger
   }//keyTyped()


   // The following five methods are helper methods.

   /**
      Convert the coordinates of a point (pixel) in the framebuffer
      to its coordinates as a point in the camera's view-rectangle.

      @param fb    FrameBuffer that is the source of the pixel
      @param x_fb  x-coordinate of a pixel in the FrameBuffer
      @param y_fb  y-coordinate of a pixel in the FrameBuffer
      @return a Vertex holding the view-rectangle coordinates of (x_fb, y_fb)
   */
   private Vertex pixel2camera(FrameBuffer fb, int x_fb, int y_fb)
   {
      // First find the "center" of the framebuffer (which
      // represents the origin (0,0) in the image-plane),
      // then find the displacements of x_fb and y_fb from
      // the framebuffer's center, then divide the displacements
      // by the scaling factor of how many framebuffer pixels
      // there are per unit of the image-plane, PIXELS_PER_UNIT.
      final int xOrigin_fb = fb.getWidthFB()  / 2;
      final int yOrigin_fb = fb.getHeightFB() / 2;
      final double deltaX_fb = (double)(x_fb - xOrigin_fb);
      final double deltaY_fb = (double)(yOrigin_fb - y_fb);
      final double xCoord_ip = deltaX_fb / (double)PIXELS_PER_UNIT;
      final double yCoord_ip = deltaY_fb / (double)PIXELS_PER_UNIT;
      return new Vertex(xCoord_ip, yCoord_ip, 0);
   }


   /**
      Compute the distance between two points
      in the camera's image-plane.

      @param x  x-coordinate of a point in the camera's image-plane
      @param y  y-coordinate of a point in the camera's image-plane
      @param v  a Vertex in the camera's image-plane
      @return the distance in the image-plane between (x, y) and (v.x, v.y)
   */
   private double distance(double x1, double y1, Vertex v2)
   {
      return Math.sqrt( (v2.x - x1) * (v2.x - x1)
                      + (v2.y - y1) * (v2.y - y1) );
   }


   /**
      Display in the console window information about the five shapes.
   */
   private void displayShapes()
   {
      for (int i = 0; i < infoList.size(); ++i)
      {
         System.out.println("Information for Model number = " + i);
         System.out.println("   name = " + infoList.get(i).name);
         System.out.println("   centerX_ip = " + infoList.get(i).centerX);
         System.out.println("   centerY_ip = " + infoList.get(i).centerY);
         System.out.println("   hit = " + infoList.get(i).hit);
      }
   }


   /**
      Display in the console window information about this program's window.
   */
   private void displayWindow()
   {
      // Get the size of the JFrame.
      final int wJF = jf.getWidth();
      final int hJF = jf.getHeight();
      // Get the size of the FrameBufferPanel.
      final int wFBP = fbp.getWidth();
      final int hFBP = fbp.getHeight();
      // Get the size of the FrameBuffer.
      final int wFB = fbp.getFrameBuffer().getWidthFB();
      final int hFB = fbp.getFrameBuffer().getHeightFB();
      // Get the size of the Viewport.
      final int wVP = fbp.getFrameBuffer().getViewport().getWidthVP();
      final int hVP = fbp.getFrameBuffer().getViewport().getHeightVP();
      // Get the location of the Viewport in the FrameBuffer.
      final int vp_ul_x = fbp.getFrameBuffer().getViewport().vp_ul_x;
      final int vp_ul_y = fbp.getFrameBuffer().getViewport().vp_ul_y;
      // Get the size of the camera's view rectangle.
      final Camera c = scene.camera;
      final double wVR = c.right - c.left;
      final double hVR = c.top - c.bottom;
      // Compute all the aspect ratios.
      final double rJF  = (double)wJF/(double)hJF;
      final double rFBP = (double)wFBP/(double)hFBP;
      final double rFB  = (double)wFB/(double)hFB;
      final double rVP  = (double)wVP/(double)hVP;
      final double rC   = wVR / hVR;

      System.out.printf(
         "Window information:\n" +
          "            JFrame [w=%4d, h=%4d], aspect ratio = %.2f\n" +
          "  FrameBufferPanel [w=%4d, h=%4d], aspect ratio = %.2f\n" +
          "       FrameBuffer [w=%4d, h=%4d], aspect ratio = %.2f\n" +
          "          Viewport [w=%4d, h=%4d, x=%d, y=%d], aspect ratio = %.2f\n" +
          "            Camera [w=%.2f, h=%.2f], aspect ratio = %.2f\n",
          wJF, hJF, rJF,
          wFBP, hFBP, rFBP,
          wFB, hFB, rFB,
          wVP, hVP, vp_ul_x, vp_ul_y, rVP,
          wVR, hVR, rC);

      System.out.flush(); // Because System.out is buffered by renderer.pipeline.PipelineLogger
   }


   /**
      Display in the console window this program's help message.
   */
   private void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle renderer debugging information on and off.");
      System.out.println("Use the 'i' key to toggle mouse event debugging information on and off.");
      System.out.println("Use the 'j' key to toggle extra mouse event debugging information on and off.");
      System.out.println("Use the 'c' key to toggle component event debugging information on and off.");
      System.out.println("Use the 's' key to show shape data.");
      System.out.println("Use the 'w' key to show window data.");
      System.out.println("Use the '=' key to reset the location of the shapes.");
      System.out.println("Use the 'a' key to turn off and on the display of the axes.");
      System.out.println("Use the '+' key to save a \"screenshot\" of the framebuffer.");
      System.out.println("Use the 'h' key to redisplay this help message.");
      System.out.flush(); // Because System.out is buffered by renderer.pipeline.PipelineLogger
   }


   /**
      Create an instance of this class which has
      the affect of creating the GUI application.
   */
   public static void main(String[] args)
   {
      // We need to call the program's constructor in the
      // Java GUI Event Dispatch Thread, otherwise we get a
      // race condition between the constructor (running in
      // the main() thread) and the very first ComponentEvent
      // (running in the EDT).
      javax.swing.SwingUtilities.invokeLater(
         () -> new Hw4()
      );
   }

   private final static int REPLACE_THIS = 0;
}



/**
   This data structure holds all the needed information
   for one shape. The above program uses a List of these
   data structures to keep track of all the shapes.
*/
class ModelInfo
{
   public String name;
   public double centerX;
   public double centerY;
   public final double radius;
   public Color color;
   public final BiPredicate<ModelInfo, Vertex> hitFn;
   public boolean hit;

   public ModelInfo(final String name,
                    final double centerX,
                    final double centerY,
                    final double radius,
                    final Color color,
                    final BiPredicate<ModelInfo, Vertex> hitFn)
   {
      this.name = name;
      this.centerX = centerX;
      this.centerY = centerY;
      this.radius = radius;
      this.color = color;
      this.hitFn = hitFn;
      this.hit = false;
   }

   /**
      Call this shape's hit function to
      determine if Vertex v hits this shape.

      @param v  Vertex to test for inclusion within this shape
      @return true if Vertex v is within this shape
   */
   public boolean hitBy(Vertex v)
   {
      return hitFn.test(this, v);
   }
}
