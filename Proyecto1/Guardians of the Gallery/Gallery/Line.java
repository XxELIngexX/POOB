package Gallery;
import Shapes.*;

import java.awt.geom.Line2D;
/**
 * Write a description of class Line here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Line{
    private Line2D line;
    private Boolean isVisible;
    private String color;
    
    /**
     * construye una linea
     * @param X1 coordenada en x del punto incial
     * @param Y1 coordenada en y del punto incial
     * @param X2 coordenada en x del punto final
     * @param Y2 coordenada en y del punto final
     * @param color el color que van a tener las lineas
     */
    public Line(double X1,double Y1,double X2,double Y2,String color){
            line = new Line2D.Double(X1,Y1,X2,Y2);
            this.color = color;
            isVisible = false;
        }
    /**
     * hace todos las lineas visibles
     */
    public void makeVisible(){
        isVisible=true;
        draw();
    }
    
    /**
     * hace todos las lineas visibles
     */
    public void makeInvisible(){
        erase();
        isVisible=false;
        
    }
    
    /**
     * dibuja las lineas en la galeria
     */
    public void draw(){
        if(isVisible){
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this,this.color,line);
        }
    }
    
    /**
     * borra las lineas en la galeria
     */
    public void erase(){
        if(isVisible){
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);   
        }
    }
}
