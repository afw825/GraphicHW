/*


*/

import renderer.scene.*;
import renderer.scene.primitives.*;

import java.awt.Color;

/**
   A three-dimensional wireframe model of the letter W.


*/
public class W extends Model
{
   /**
      The letter W.
   */
   public W()
   {
      super("W");

      // Create the front face vertices.

      addVertex(
              new Vertex(0.15, 0.0, 0), //0
              new Vertex(0.0, 1.0, 0), //1
              new Vertex(0.2, 1.0, 0), //2
              new Vertex(0.3, 0.45, 0), //3
              new Vertex(0.4, 1.0, 0), //4
              new Vertex(0.6, 1.0, 0), //5
              new Vertex(0.7, 0.45, 0), //6
              new Vertex(0.8, 1.0, 0), //7
              new Vertex(1.0, 1.0, 0), //8
              new Vertex(0.8, 0.0, 0), //9
              new Vertex(0.6, 0.0, 0), //10
              new Vertex(0.5, 0.55, 0), //11
              new Vertex(0.4, 0.0, 0)); //12

      // Create the back face vertices.
      addVertex(
              new Vertex(0.15, 0.0, -0.25), //0
              new Vertex(0.0, 1.0, -0.25), //1
              new Vertex(0.2, 1.0, -0.25), //2
              new Vertex(0.3, 0.45, -0.25), //3
              new Vertex(0.4, 1.0, -0.25), //4
              new Vertex(0.6, 1.0, -0.25), //5
              new Vertex(0.7, 0.45, -0.25), //6
              new Vertex(0.8, 1.0, -0.25), //7
              new Vertex(1.0, 1.0, -0.25), //8
              new Vertex(0.8, 0.0, -0.25), //9
              new Vertex(0.6, 0.0, -0.25), //10
              new Vertex(0.5, 0.55, -0.25), //11
              new Vertex(0.4, 0.0, -0.25)); //12

      // Create the Color objects.

      colorList.add(Color.GREEN);
      colorList.add(Color.MAGENTA);
      colorList.add(Color.RED);

      // Create the front face line segments.

      addPrimitive( new LineSegment(0,1, 1),
              new LineSegment(1,2, 0),
              new LineSegment(2,3, 1),
              new LineSegment(3,4, 1),
              new LineSegment(4,5, 0),
              new LineSegment(5,6, 1),
              new LineSegment(6,7, 1),
              new LineSegment(7,8, 0),
              new LineSegment(8,9, 1),
              new LineSegment(9,10, 2),
              new LineSegment(10,11, 1),
              new LineSegment(11,12, 1),
              new LineSegment(12,0, 2));

      // Create the back face line segments.

      addPrimitive( new LineSegment(13,14, 1),
              new LineSegment(14,15, 0),
              new LineSegment(15,16, 1),
              new LineSegment(16,17, 1),
              new LineSegment(17,18, 0),
              new LineSegment(18,19, 1),
              new LineSegment(19,20, 1),
              new LineSegment(20,21, 0),
              new LineSegment(21,22, 1),
              new LineSegment(22,23, 2),
              new LineSegment(23,24, 1),
              new LineSegment(24,25, 1),
              new LineSegment(25,13, 2));

      // Create the front face to back face line segments.

      addPrimitive( new LineSegment(0,13, 2),
              new LineSegment(1,14, 0),
              new LineSegment(2,15, 0),
              new LineSegment(3,16, 1),
              new LineSegment(4,17, 0),
              new LineSegment(5,18, 0),
              new LineSegment(6,19, 1),
              new LineSegment(7,20, 0),
              new LineSegment(8,21, 0),
              new LineSegment(9,22, 2),
              new LineSegment(10,23, 2),
              new LineSegment(11,24, 0),
              new LineSegment(12,25, 2));

   }
}
