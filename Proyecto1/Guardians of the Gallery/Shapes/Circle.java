package Shapes;

import java.awt.*;
import java.awt.geom.*;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0.  (15 July 2000) 
 */

public class Circle extends Figura{

    public static final double PI=3.1416;
    private int diameter;
    

    public Circle(int x, int y, String color){
        super(x,y,color);
        diameter = 30;
        
    }
    
    /**
     * este metodo devuelve el tamaño del circulo
     * @return diametro del circulo
     */
    public int getSize() {
        return diameter;
    }
    public void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(xPosition-diameter/2, yPosition-diameter/2, 
                diameter, diameter));
            canvas.wait(10);

        }
    }

 
    /**
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter){
        erase();
        diameter = newDiameter;
        draw();
    }

    



}
