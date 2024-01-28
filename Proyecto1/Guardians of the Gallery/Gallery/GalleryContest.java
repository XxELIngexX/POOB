package Gallery;

import java.lang.reflect.Array;
import java.nio.channels.Pipe;
import java.util.*;

/**
 * 
 */

/**
 * @author Amaya Paez
 *
 */


public class GalleryContest {
    private Room salon;
    private Sculpture escultura;
    private Guard guardia;

    /**
     * Devolvería la solución númerica del problema (si se hubiera hecho. :3)
     */
    public float solve(int[][] polygon, int[] guard, int[] sculpture) {
        return 0;
    }
    
    /**
     * este metodo grafica la solucion del ejercicio de la arena
     * @param polygon conjunto de vertices
     * @param guard ubicacion del guardia
     * @param sculpture ubicacion de la escultura
     */
    public void simulate(int[][] polygon, int[] guard, int[] sculpture) {
        Gallery prueba = new Gallery(polygon,guard,sculpture);
        salon = prueba.getRoom("black");
        guardia = salon.getGuard();
        escultura = salon.getSculpture();
        moveVision();
        System.out.println(salon.distanceTraveled());
    }
    
    /**
     * este metodo se usa para encontrar la linea de vision de la escultura fuera de los vertices, calculando el punto por el que pasa,
     * el punto al que llega y la pendiente de la recta que une esos dos puntos
     */
    private void moveVision(){
        ArrayList<int []> vertices = escultura.campoVisual(); // mirar
        int [] puntoExpand = verticeToExpand(vertices); // vertice  por el que pasa la linea
        int[] m1 = deltas(escultura.getPosition(),puntoExpand); 
        int[] puntoVision = expand(puntoExpand,m1); // punto en el limite del poligono
        solucionar(puntoVision, puntoExpand,m1);
    }
    
    /**
     * este metodo encuentra el camino mas corto desde donde esta el guardia, hasta un punto donde pueda ver la escultura
     * @param puntoVision es el punto final de la linea de vision de la escultura 
     *           concatenado con el parametro b de la ecuiacion de la recta correspondiente a la linea de vision
     * @param puntoExpand es el punto inicial de la linea de la escultura
     * @param m1 pendiente de la linea de vision
     */
    private void solucionar(int[] puntoVision, int[] puntoExpand,int[] m1) {
        int[] comparar = {puntoVision[0],puntoVision[1]};
        if(!Arrays.equals(puntoExpand,comparar)) {    //significa que si hay una linea de vision
            if (guardia.canSee(guardia.getPosition(),comparar)&&
                guardia.canSee(guardia.getPosition(),puntoExpand)) {
                int [] m2 = lineaVision(m1);
                int[] point = guardia.getPosition();
                int[] toMove = calculatePoint(m2,point,puntoVision[2],m1);
                if(guardia.canSee(toMove, guardia.getPosition()))
                    guardia.moveGuard(toMove[0], toMove[1]);
                else {
                    guardia.moveGuard(puntoExpand[0], puntoExpand[1]);
                }
                
            }else {
                
                    ArrayList<int[]> point = guardia.verticesCerca(guardia.getPosition());
                    int[] salida = null;
                    for (int[] i: point) {
                        if (escultura.seeSculpture(guardia.getPosition())) {
                            break;
                        }else if(guardia.toMove(guardia.getPosition(),i)) {
                            salida=i;
                            guardia.moveGuard(salida[0], salida[1]);
                            moveVision();
                        }
                    }
            }
        }else {
            guardia.moveGuard(puntoVision[0], puntoVision[1]);
        }
    
                
    }
    
    /**
     * calcula el punto al que el guardia debe llegar para que su rrecorrido sea minimo 
     * @param m2 pendiente de la recta tangente desde el guardia hasta la linea de visoin
     * @param point posicion del guardia
     * @param b1 parametro b de la ecuiacion de la recta correspondiente a la linea de vision
     * @param m1 pendiente de la recta de la linea de vision de la escultura
     * @return coordenada perpendicular desde la ubicacion del guardia a la linea de vision, es decir,
     *         el punto de llegada del guardia
     */
    private int[] calculatePoint(int[] m2, int[] point,int b1,int[]m1) {
        int b2 = b(point,m2);
        int x = puntoX(m1,m2,b1,b2);
        int y = calculateY(x, b2, m2);
        int[] salida = {x,y};
        return salida;
        
        
    }
    /**
     * metodo que calcula el punto x de la interseccion de dos rectas 
     * @param m1 pendiente de la recta 1
     * @param m2 pendiente de la recta 2
     * @param b1 componente b de la ecuacion de la primera recta
     * @param b2 componente b de la ecuacion de la segunda recta
     * @return punto en x de la interseccion de las dos rectas
     */
    private int puntoX(int[]m1,int[]m2,int b1,int b2) {
        b1=b1/m1[0];
        b2=b2/m2[0];
        
        int[] denominador= {m1[1]*m2[0]+(m1[0]*m2[1]*-1),m1[0]*(m2[0])};
        int numerador = b2+(b1*-1);
        int salida = (numerador*denominador[1])/denominador[0];
        return salida;
    }
    
    /**
     * el metodo calcula la pendiente de la recta tangente a una recta A
     * @param m pendiente de la recta A
     * @return pendiente de la recta B, perpendicular a la recta A
     */
    private int[] lineaVision(int[] m) {
        int [] m2 = {m[1],m[0]*-1};
        return m2;
    }
    
    /**
     * el metodo calcula el el punto final de la linea de vision de la escultura
     * @param puntoExpand punto inicial de la escultura
     * @param delta pendiente de la recta de la linea de vision
     * @return el punto final de la linea de vision
     */
    private int[] expand(int[] puntoExpand,int[] delta) {
        
        int y =puntoExpand[1];
        int x =puntoExpand[0];
        int b = b(puntoExpand,delta);
        int[] salida = {x,y,b};
        int[][] range = salon.getRange();
        int[] rango=rango(range[0]);
        boolean contain = true;
        if (escultura.getPosition()[0]>x){
            for(int i = x ; i > rango[0];i--){
                int new_y = calculateY(i,b,delta);
                contain = escultura.contiene(i, new_y);
                int [] aux = {i,new_y};
                int [] aux1 = {i,new_y,b};
                if (contain && escultura.seeSculpture(aux)) {
                    salida=aux1;
                }
            }
        }else {
            for(int i = x ; i < rango[1];i++){
                int new_y = calculateY(i,b,delta);
                contain = escultura.contiene(i, new_y);
                int [] aux = {i,new_y};
                int [] aux1 = {i,new_y,b};
                if (contain && escultura.seeSculpture(aux)) {
                    salida=aux1;
                }
            }
        }
        return salida;    
    }
     /**
      * calcula el minimo y el maximo de los vertices del eje x de la figura
      * @param a conjunto de coordenadas en el eje x del poligono
      * @return maximo y minimo -> [min,max]
      */
    private int[] rango(int[] a) {
        int max = a[0];
        int min = a[0];
        for (int i = 1; i < a.length; i++) {
                if (a[i] > max) {
                    max = a[i];
                }
                if (a[i] < min) {
                    min = a[i];
                }
        }
        int [] salida = {min,max};
        return salida;
        
    }
    /**
     * metodo que devuelve los delta de un par puntos
     * @param a punto inicial
     * @param b punto final 
     * @return pendiente -> [deltaX,deltaY]
     */
    private int[] deltas(int[]a, int[] b) {
        int[]salida = new int[2];
        salida[0] = b[0]-a[0];
        salida[1] = b[1]-a[1];
        return salida;
    }
    /**
     * calcula el parametro b de la ecuacion de la recta
     * @param point punto inical 
     * @param deltas ->[deltaX,deltaY] pendiente = deltaY/deltaX
     * @return parametro b de la ecuacion de la recta
     */
    private int b(int[] point, int[] deltas) {
        int b = 0;
        if(deltas[1]!=0){
            int y = deltas[0]*point[1];
            int x = -1*deltas[1]*point[0];
            b =(x+y);    
        }
        return b;
    }
    /**
     * metodo que calcula el valor de y de una ecuacion
     * @param x valor de x a remplazar en la formula
     * @param b valor del atributo b de la ecuacin de la recta
     * @param deltas ->[deltaX,deltaY] pendiente = deltaY/deltaX
     * @return
     */
    private int calculateY(int x, int b,int[] deltas) {
        int y = (deltas[1]*x+b)/deltas[0];
        return y;
    }
    
    /**
     * metodo que devuelve el punto por el cual la linea de vision de la escultura debe extenderse
     * @param vertices los vertices cercanos visibles de la escultura
     * @returnel punto por el cual la linea de vision de la escultura debe extenderse
     */
    private int[] verticeToExpand(ArrayList<int []> vertices) {    
        double min = 100000;
        int [] salida = null;
        int [] point = guardia.getPosition(); 
        for(int[] i:vertices) {
            double a = distance(i,point);
            if(a < min) {
                salida = i;
                min = a;
            }
        }
        return salida;
    }
    /**
     * calcula la distancia entre dos puntos
     * @param past punto inicial
     * @param now punto final
     * @return distancia de tipo float
     */
    private double distance(int[] past, int[]now) {
        
        double salida;
        salida = Math.sqrt(Math.pow((past[0]- now[0]),2)+Math.pow((past[1]-now[1]),2));
        return salida;
    }
}


