/*


*/

import renderer.scene.*;
import renderer.scene.primitives.*;

import java.awt.Color;

/**
   A three-dimensional wireframe model of the letter N.
   1
     __   __
    |  \ |  |
    |  _    |
    | | \   |
    |_|  \_ |
   0
*/
public class N extends Model
{
   /**
      The letter N.
   */
   public N()
   {
      super("N");

      // Create the front face vertices.
      addVertex(new Vertex(0,0,0),
              new Vertex(0,1,0),
              new Vertex(.3,1,0),
              new Vertex(0.75,.5,0),
              new Vertex(.75,1,0),
              new Vertex(1,1,0),
              new Vertex(1,0,0),
              new Vertex(.75,0,0),
              new Vertex(.3, .5, 0),
              new Vertex(.3,0,0));

      // Create the back face vertices.
      addVertex(new Vertex(0,0,-0.25),
              new Vertex(0,1,-0.25),
              new Vertex(.25,1,-0.25),
              new Vertex(0.75,.5,-0.25),
              new Vertex(.75,1,-0.25),
              new Vertex(1,1,-0.25),
              new Vertex(1,0,-0.25),
              new Vertex(.75,0,-0.25),
              new Vertex(.25, .5, -0.25),
              new Vertex(.25,0,-0.25));


      // Create the Color objects.

      colorList.add(Color.GREEN);
      colorList.add(Color.MAGENTA);
      colorList.add(Color.RED);

      // Create the front face line segments.

      addPrimitive(new LineSegment(0,1,0),
              new LineSegment(1,2,0),
              new LineSegment(2,3,1),
              new LineSegment(3,4,2),
              new LineSegment(4,5,2),
              new LineSegment(5,6,2),
              new LineSegment(6,7,2),
              new LineSegment(7,8,1),
              new LineSegment(8,9,0),
              new LineSegment(9,0,0));

      // Create the back face line segments.

      addPrimitive(new LineSegment(10,11,0),
              new LineSegment(11,12,0),
              new LineSegment(12,13,1),
              new LineSegment(13,14,2),
              new LineSegment(14,15,2),
              new LineSegment(15,16,2),
              new LineSegment(16,17,2),
              new LineSegment(17,18,1),
              new LineSegment(18,19,0),
              new LineSegment(19,10,0));
//      );

      // Create the front face to back face line segments.

      addPrimitive(new LineSegment(0,10,0),
              new LineSegment(1,11,0),
              new LineSegment(2,12,0),
              new LineSegment(3,13,2),
              new LineSegment(4,14,2),
              new LineSegment(5,15,2),
              new LineSegment(6,16,2),
              new LineSegment(7,17,2),
              new LineSegment(8,18,0),
              new LineSegment(9,19,0));

   }
}
