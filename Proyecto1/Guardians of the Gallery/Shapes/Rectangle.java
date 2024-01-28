package Shapes;

import java.awt.*;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @version 1.0  (15 July 2000)()
 */


 
public class Rectangle extends Figura{

    public static int EDGES = 4;
    
    private int height;
    

    /**
     * Create a new rectangle at default position with default color.
     */
    public Rectangle(int x, int y, String color){
        super(x,y,color);
        height = 30;
        
        
    }
    
    public int getSize(){
        return height;
    }
   

    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidth must be >=0.
     */
    public void changeSize(int newHeight) {
        erase();
        height = newHeight;
        draw();
    }
    
    

    /*
     * Draw the rectangle with current specifications on screen.
     */

    public void draw() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition-(height), yPosition-(height), 
                                       height, height));
            canvas.wait(10);
        }
    }

    
}

