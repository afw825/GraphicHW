/*


*/

import renderer.scene.*;
import renderer.scene.primitives.*;

import java.awt.Color;

/**
   A three-dimensional wireframe model of the letter P.

  1______________ 2
   |             \
   |   8_______ 9 \ 3
   |    |      |  |
   |    |______|  |
   |   11     10  / 4
   |   6_________/
   |    |         5
   |____|
  0     7
*/
public class P extends Model
{
   /**
      The letter P.
   */
   public P()
   {
      super("P");

      // Create the front face vertices.
      addVertex(new Vertex(0.00, 0.00, 0.0),
                new Vertex(0.00, 1.00, 0.0),
                new Vertex(0.75, 1.00, 0.0),
                new Vertex(1.00, 0.8,  0.0),
                new Vertex(1.00, 0.6,  0.0),
                new Vertex(0.75, 0.4,  0.0),
                new Vertex(0.25, 0.4,  0.0),
                new Vertex(0.25, 0.0,  0.0));

      addVertex(new Vertex(0.25, 0.8,  0.0),
                new Vertex(0.75, 0.8,  0.0),
                new Vertex(0.75, 0.6,  0.0),
                new Vertex(0.25, 0.6,  0.0));

      // Create the back face vertices.(offset (.2,.02,0)
      addVertex(new Vertex(0.00, 0.00, -0.25),
              new Vertex(0.00, 1.00, -0.25),
              new Vertex(0.75, 1.00, -0.25),
              new Vertex(1.00, 0.8,  -0.25),
              new Vertex(1.00, 0.6,  -0.25),
              new Vertex(0.75, 0.4,  -0.25),
              new Vertex(0.25, 0.4,  -0.25),
              new Vertex(0.25, 0.0,  -0.25));

      addVertex(new Vertex(0.25, 0.8,  -0.25),
              new Vertex(0.75, 0.8,  -0.25),
              new Vertex(0.75, 0.6,  -0.25),
              new Vertex(0.25, 0.6,  -0.25));




      // Create the Color objects.

      colorList.add(Color.GREEN);
      colorList.add(Color.MAGENTA);
      colorList.add(Color.RED);

      // Create the front face line segments
      // (you need to add the Color indices!).

      addPrimitive(new LineSegment(0, 1,0),
              new LineSegment(1, 2,1),
              new LineSegment(2, 3,1),
              new LineSegment(3, 4,2),
              new LineSegment(4, 5,1),
              new LineSegment(5, 6,1),
              new LineSegment(6, 7,1),
              new LineSegment(7, 0,1));

      addPrimitive(new LineSegment( 8,  9,1),
              new LineSegment( 9, 10,1),
              new LineSegment(10, 11,1),
              new LineSegment(11,  8,1));

      // Create the back face line segments.

      addPrimitive(new LineSegment(12, 13,0),
              new LineSegment(13, 14,1),
              new LineSegment(14, 15,1),
              new LineSegment(15, 16,2),
              new LineSegment(16, 17,1),
              new LineSegment(17, 18,1),
              new LineSegment(18, 19,1),
              new LineSegment(19, 12,1));

      addPrimitive(new LineSegment( 20,  21,1),
              new LineSegment( 21, 22,1),
              new LineSegment(22, 23,1),
              new LineSegment(23,  20,1));

      // Create the front face to back face line segments.

      addPrimitive(new LineSegment(0, 12, 0),
              new LineSegment(1, 13, 0),
              new LineSegment(2, 14, 0),
              new LineSegment(3, 15, 1),
              new LineSegment(4, 16, 2),
              new LineSegment(5, 17, 2),
              new LineSegment(6, 18, 1),
              new LineSegment(7, 19, 1),
              new LineSegment(8, 20, 1),
              new LineSegment(9, 21, 1),
              new LineSegment(10, 22, 1),
              new LineSegment(11, 23, 1)
              );

   }
}
