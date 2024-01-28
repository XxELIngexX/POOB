package Gallery;
import Shapes.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;

public class Guard extends Elementos{
    private Rectangle guard;
    public Rectangle getGuard() {
        return guard;
    }

    public void setGuard(Rectangle guard) {
        this.guard = guard;
    }

    private float recorrido = 0f;
    
    public Guard(int[][] polygon, Polygon salon2) {
        super(polygon, salon2);
        
    }

    /**
     * crea y ubica el guardia en la posicion mas al sur y al este
     * @param room color del salon al que será asignado
     * @param salon poligono del salon en el cual será ubicado
     * @param coords coordenadas de los vertices del salon
     * @param ancho longitud de la galeria
     * @param puerta coordenada mas al sur y mas al este
     */
    public void arriveGuard(String room,int ancho,int[] puerta) {
        guard = new Rectangle(puerta[0], puerta[1], room);
        guard.changeSize((ancho*5)/100);
        guard.makeVisible();
    }
    /**
     * mueve al guardia a la posicion especificada
     * @param x coordenada en x donde se moverá el guardia
     * @param y coordenada en y donde se moverá el guardia
     */
    public void moveGuard(int x, int y) {
        int [] array = {x,y};
        while (!Arrays.equals(guard.getPosition(), array)) {
            if(super.canSee(array,guard.getPosition())) {
                int [] past = guard.getPosition();
                guard.changePosition(x,y);
                guard.makeVisible();
                int[] now = guard.getPosition();
                Line rastro = new Line(past[0],past[1],
                                now[0],now[1],"orange"); 
                rastro.makeVisible();
                distance(past,now);
            }else {
                ArrayList<int []> verticeTomove = verticesCerca(guard.getPosition());
                int[] salida = null;
                for (int[] i: verticeTomove) {
                    if(toMove(guard.getPosition(),i)) {
                        salida=i;
                    }
                }
                moveGuard(salida[0],salida[1]);        
            }
        }
     }
    
    /**
    
     * mnetodo que calcula la distancia entre dos puntos
     * @param past la coordenada del punto inical
     * @param now la coordenada del punto final
     */
    private void distance(int[] past, int[]now) {
         
            double salida;
            salida = Math.sqrt(Math.pow((past[0]- now[0]),2)+Math.pow((past[1]-now[1]),2));
            recorrido += salida;
    }
    
    /**
     * este metodo permite consultar cuanto ha recorrido el guardia
     * @return el calculo del recorrido del guardia
     */
    public float distanceTraveled() {
        return recorrido;
    }
    
    
    /**
     * crea e inserta un guardia en la ubicacion especificada del salon
     * @param color color de la habitacion donde será designado
     * @param salon poligono del salon donde será designado
     * @param x coordenada en x donde se insertará
     * @param y coordenada en y donde se insertará
     * @param ancho  longitud de la galria
     * @param coords vertices del salon donde será designado
     */
    public void putGuard(String color, int x,int y,int ancho){
        guard = new Rectangle(x,y,color);
        guard.changeSize((ancho*5)/100);
        guard.makeVisible();
    }
    
    /**
     * medoto que devuleve la psoicion del guardia
     * @return coordenadas donde esta ubicado el guardia
     */
    public int[] getPosition() {
        return guard.getPosition();
    }
    
    /**
     * metodo que hace visible al guardia
     */
    public void makeVisible(){
        guard.makeVisible();
    }
    
    /**
     * metodo que hace invisible al guardia
     */
    public void makeInvisible(){
        guard.makeInvisible();
    }

    

    
    

    
    
    
   
}
