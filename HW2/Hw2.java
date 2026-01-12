/*


*/

import renderer.scene.*;
import renderer.scene.util.DrawSceneGraph;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;

/**
   Create the frames for an animation
   of the letters P, N, and W.
*/
public class Hw2
{
   public static void main(String[] args)
   {
      // Create a FrameBuffer to render our scene into.
      final int width  = 900;
      final int height = 900;
      final FrameBuffer fb = new FrameBuffer(width, height, Color.darkGray);

      final Scene scene = new Scene();

      // Create the Models and give them an initial location.

      P p = new P();
      scene.addPosition(new Position(p, "P", new Vector(-1.5,-0.5,-1.5)));

      N n = new N();
      scene.addPosition(new Position(n,"N", new Vector(-0.5,-0.5,-1.5)));

      W w = new W();
      scene.addPosition(new Position(w,"W", new Vector(0.5,-0.5,-1.5)));


      // If you need to, print the Scene data structure to the console.
      // This can help you check that your models and scene make sense.
    //System.out.println( scene );

      // Use GraphViz to draw a picture of the Scene data structure.
      DrawSceneGraph.draw(scene, "Hw2_SG");
      Rasterize.doClipping = true;
      Rasterize.doGamma = false;
    //scene.debug = true;      // Uncomment this line for debugging output.
    //Rasterize.debug = true;  // Uncomment this line for more debugging output.

      // Create 300 frames of the animation.
      for (int i = 0; i < 300; ++i)
      {

         // Render again.
         fb.clearFB();
         Pipeline.render(scene, fb);
         fb.dumpFB2File(String.format("Hw2_frame%03d.ppm", i));

         // Update each Model's location.

         Position temp;
         Vector tempVector;

         // first 100 frames
         if(i<100){
            if(i<25){
               temp = scene.getPosition(0);
               tempVector = temp.getTranslation();
               scene.setPosition(0, new Position(p, "P",
                       new Vector(tempVector.x,tempVector.y,tempVector.z - .04)));
            }
            if(i >= 25 && i < 75){
               // P moving
               temp = scene.getPosition(0);
               tempVector = temp.getTranslation();
               scene.setPosition(0, new Position(p, "P",
                       new Vector(tempVector.x+.04,tempVector.y,tempVector.z)));
               // N moving
               temp = scene.getPosition(1);
               tempVector = temp.getTranslation();
               scene.setPosition(1, new Position(n, "N",
                       new Vector(tempVector.x-.02,tempVector.y,tempVector.z)));

               // W moving
               temp = scene.getPosition(2);
               tempVector = temp.getTranslation();
               scene.setPosition(2, new Position(w, "W",
                       new Vector(tempVector.x-.02,tempVector.y,tempVector.z)));
            }

            if(i>=75){
               temp = scene.getPosition(0);
               tempVector = temp.getTranslation();
               scene.setPosition(0, new Position(p, "P",
                       new Vector(tempVector.x,tempVector.y,tempVector.z + .04)));
            }

         }
         //2nd 100 frames
         if(i>=100 && i<200){
            if(i<125){
               temp = scene.getPosition(1);
               tempVector = temp.getTranslation();
               scene.setPosition(1, new Position(n, "N",
                       new Vector(tempVector.x,tempVector.y+ .04,tempVector.z)));
            }
            if(i>=125 && i<175){
               temp = scene.getPosition(1);
               tempVector = temp.getTranslation();
               scene.setPosition(1, new Position(n, "N",
                       new Vector(tempVector.x+.04,tempVector.y,tempVector.z)));

               temp = scene.getPosition(0);
               tempVector = temp.getTranslation();
               scene.setPosition(0, new Position(p, "P",
                       new Vector(tempVector.x-.02,tempVector.y,tempVector.z)));

               temp = scene.getPosition(2);
               tempVector = temp.getTranslation();
               scene.setPosition(2, new Position(w, "W",
                       new Vector(tempVector.x-.02,tempVector.y,tempVector.z)));

            }
            if(i>=175){
               temp = scene.getPosition(1);
               tempVector = temp.getTranslation();
               scene.setPosition(1, new Position(n, "N",
                       new Vector(tempVector.x,tempVector.y-.04,tempVector.z)));
            }
         }
         // last 100 frames
         if(i>=200){
            if(i<225){

               temp = scene.getPosition(1);
               tempVector = temp.getTranslation();
               scene.setPosition(1, new Position(n, "N",
                       new Vector(tempVector.x,tempVector.y-.04,tempVector.z)));

               temp = scene.getPosition(0);
               tempVector = temp.getTranslation();
               scene.setPosition(0, new Position(p, "P",
                       new Vector(tempVector.x,tempVector.y-.04,tempVector.z)));
            }
            if(i>=225 && i<250){
               temp = scene.getPosition(0);
               tempVector = temp.getTranslation();
               scene.setPosition(0, new Position(p, "P",
                       new Vector(tempVector.x-.04,tempVector.y,tempVector.z)));

               temp = scene.getPosition(2);
               tempVector = temp.getTranslation();
               scene.setPosition(2, new Position(w, "W",
                       new Vector(tempVector.x+.04,tempVector.y,tempVector.z)));
            }
            if(i>=250 && i<275){
               temp = scene.getPosition(2);
               tempVector = temp.getTranslation();
               scene.setPosition(2, new Position(w, "W",
                       new Vector(tempVector.x+.04,tempVector.y,tempVector.z)));

               temp = scene.getPosition(1);
               tempVector = temp.getTranslation();
               scene.setPosition(1, new Position(n, "N",
                       new Vector(tempVector.x-.04,tempVector.y,tempVector.z)));
            }
            if(i>=275){
               temp = scene.getPosition(1);
               tempVector = temp.getTranslation();
               scene.setPosition(1, new Position(n, "N",
                       new Vector(tempVector.x,tempVector.y+.04,tempVector.z)));

               temp = scene.getPosition(0);
               tempVector = temp.getTranslation();
               scene.setPosition(0, new Position(p, "P",
                       new Vector(tempVector.x,tempVector.y+.04,tempVector.z)));
            }
         }



      }
   }
}
