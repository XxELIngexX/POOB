package Gallery;
import Shapes.*;

import java.awt.Polygon;
import java.util.ArrayList;

public class Sculpture extends Elementos{
    private Circle sculpture;
    
    /**
     * Retorna la escultura, en este caso tiene forma de circulo.
     */
    public Circle getSculpture() {
        return sculpture;
    }
    
    /**
     * Modifica el atributo circulo de la escultura.
     */
    public void setSculpture(Circle sculpture) {
        this.sculpture = sculpture;
    }
    
    /**
     * Contructor del hijo escultura con padre elementos.
     */
    public Sculpture(int[][] limites, Polygon salon) {
        super(limites, salon);
    }
    /**
     * construye una escultura en un salon asignado
     * @param room color del salon en el cual se va a ubicar la escultura
     * @param x coordenada en x donde se ubica la escultura
     * @param y coordenada en y donde se ubica la escultura
     * @param ancho el largo de la galeria 
     */
    public void displaySculpture(String room,int x, int y,int ancho){
        sculpture = new Circle(x, y, room);        
        sculpture.changeSize((ancho*5)/100);
        sculpture.makeVisible();
    }
    
    /**
     * metodo que devuelve la posicion de la escultuera
     * @return las coordenadas donde se encuentra la escultura
     */
    public int[] getPosition() {
        return sculpture.getPosition();
    }
    
    /**
     * metodo que superpone la escultura a la alarma
     */
    public void alarm(){
        sculpture.makeInvisible();
        sculpture.makeVisible();
    }
    
    /**
     * metodo que hace visible a la escultura
     */
    public void makeVisible(){
        sculpture.makeVisible();
    }
    
    /**
     * metodo que hace visible a la escultura
     */
    public void makeInvisible(){
        sculpture.makeInvisible();
    }
    /**
     * determina si un punto cumple con las condiciones de vista de la escultura
     * @param punto punto que desea saber si puede ver la escultura o no
     * @return true-> si la puedo ver, false-> si no la puedo ver
     */
    public boolean seeSculpture(int[] punto){
        boolean salida = false;
        int cont = 0;
        int[] centro = sculpture.getPosition();
        int radio = sculpture.getSize()/2;
        ArrayList<int[]> cruz = cruz(centro,radio);
        if(canSee(centro,punto)){
            for (int[]i:cruz) {
                if (canSee(i,punto)) {
                    cont++;
                }
            }
        }
        if (cont >= 2)
            salida = true;
        return salida;
    }
    /**
     * metodo que retorna los limites de la escultura, superior, inferior, izquierda y derecha
     * @param centro el punto centro del circulo, la ubicacion de la escultura
     * @param radio radio del circulo
     * @return retorna un arreglo de coordenadas que cooresponde a los limites
     */
    private ArrayList<int[]> cruz(int[]centro,int radio) {
        ArrayList<int[]> salida = new ArrayList<>();
        int []a={centro[0]+ radio,centro[1]};
        int []b={centro[0]- radio,centro[1]};
        int []c={centro[0],centro[1]+radio};
        int []d={centro[0],centro[1]-radio};
        salida.add(a);
        salida.add(b);
        salida.add(c);
        salida.add(d);
        
        return salida;
        
    }

    /**
     * Todos los vertices cercanos que puede ver la escultura.
     * y así mismo se extiende una línea de visión.
     */
    public ArrayList<int[]> campoVisual() {
        // en los condicionales, ver la escultura
        ArrayList<int[]> salida = new ArrayList<>();
        ArrayList<int[]> vertices = verticesCerca(getPosition());
        
        for(int[] i : vertices) {
            int[] m = pendiente(getPosition(), i);
            double pendiente = m[0]/m[1];
            if(pendiente< 0) {
                pendiente*=-1;
                if(getPosition()[0]<i[0] && contiene(i[0]+1, i[1]+pendiente)) {
                    salida.add(i);
                }
                if(getPosition()[0]>i[0] && contiene(i[0]-1, i[1]+pendiente)) {
                    salida.add(i);
                }
            }else {
                if(getPosition()[0]<i[0] && contiene(i[0]+1, i[1]+pendiente)) {
                    salida.add(i);
                }
                if(getPosition()[0]>i[0] && contiene(i[0]-1, i[1]+pendiente)) {
                    salida.add(i);
                }
            }
        }
        return salida;
    }

}
