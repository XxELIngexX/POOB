package Gallery;
import Shapes.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Write a description of class Elementos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Elementos
{
   private int[][] limites;
   private Polygon salon;
   
   /**
     * Constructor de elementos del salón. 
     */
   public Elementos(int[][] coords,Polygon room) {
       limites = coords;
       salon = room;
   }
 
   public abstract int [] getPosition();
   
   /**
     * este metodo comprueba si un punto esta dentro del salon o en sus limites
     * @param xPoint coordenada en el eje x del punto a analizar
     * @param yPoint coordenada en el eje y del punto a analizar
     * @return si esta contenido o no
     */
    
   
   public boolean contiene (double xPoint, double yPoint) {
        // El punto es interno                   el punto es una pared, incluye vertices
        return salon.contains(xPoint, yPoint) || isWall(xPoint, yPoint);
    }
    
    /**
     * este metodo comprueba si un punto esta en los limites del salon
     * @param xPoint coordenada en el eje x del punto a analizar
     * @param yPoint coordenada en el eje y del punto a analizar
     * @return si esta contenido o no
     */
    private boolean isWall(double xPoint, double yPoint) {
        for (int i = 0; i<limites.length; i++){
            int vertX = limites[i][0];
            int vertY = limites[i][1];

            if (xPoint == vertX && yPoint == vertY)
                // es un vertice
                return true;
            else {
                int nextVertX;
                int nextVertY;

                if (i == limites.length - 1) {
                    // si es el ultimo vertice, vuelve al punto de origen
                    nextVertX = limites[0][0];
                    nextVertY = limites[0][1];
                } else {
                    nextVertX = limites[i + 1][0];
                    nextVertY = limites[i + 1][1];
                }

                if (vertX == nextVertX) {
                    int factor = 0;
                    // Camina sobre el eje Y
                    if (vertY < nextVertY) {
                        // hacia la arriba
                        for (int j = vertY; j < nextVertY; j++) {
                            if ((j == yPoint) && (vertX == xPoint))
                                return true;
                        }
                    }
                    else {
                        

                        // hacia la abajo
                        for (int j = vertY; j > nextVertY; j--) {
                            if ((j == yPoint) && (vertX == xPoint))
                                return true;
                        }
                    }

                } else if (vertY == nextVertY) {
                    // Camina sobre el eje X
                    if (vertX < nextVertX) {
                    
                        // hacia la derecha
                        for (int j = vertX; j < nextVertX; j++) {
                            if ((j == xPoint) && (vertY == yPoint))
                                return true;
                        }
                    } else {
                        
                        // hacia la izq
                        for (int j = vertX; j > nextVertX; j--) {
                            if ((j == xPoint) && (vertY == yPoint))
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * este metodo evalua si desde la posicion del guardia puede ver un punto
     * @param x coordenada en el eje x del punto a analizar
     * @param y coordenada en el eje y del punto a analizar
     * @return si lo úede ver o no
     */
    public boolean canSee(int[]pointA,int[]pointB) {
        boolean salida = true;
        int[] m = pendiente(pointA, pointB);
        double suma =1;
        if((double)m[0]/(double)m[1]>1 || (double)m[0]/(double)m[1]<-1) {
            suma = 0.25;
            m[1]*=4;
            int MCD = simplificar(m[0],m[1]);
            m[0]/=MCD;
            m[1]/=MCD;
            
        }
                
        // guardia a la derecha del punto 
        if(pointB[0]<pointA[0]){
            int[] puntoSee = {pointB[1],1};
            double cont = pointB[1];
            for(double i = pointB[0]; i<pointA[0];i+=suma) {
                if (salida) {
                    salida = contiene(i,cont);
                    puntoSee = sumar(puntoSee,m); //sumar fracciones
                    cont = (double)puntoSee[0]/(double)puntoSee[1];
                }else {
                    return salida;
                }
                
            }
        }
        if(pointB[0]>pointA[0]){

            int[] puntoSee = {pointA[1],1};
            double cont = pointA[1];
            for(double i = pointA[0]; i<pointB[0];i+=suma) {
                if (salida) {
                    salida = contiene(i,cont);
                    puntoSee = sumar(puntoSee,m); //sumar fracciones
                    cont = (double)puntoSee[0]/(double)puntoSee[1];
                }else {
                    return salida;
                }
            }
        }
        
        return salida;
    }
    /**
     * suma dos fracciones y la retorna en forma simplificada
     * @param a fraccion a
     * @param b fraccion b
     * @return suma de fracciones simplificado
     */
    private int[] sumar(int[]a,int[]b) {
        int denominador = a[1]*b[1];
        int numerador = a[0]*b[1]+a[1]*b[0];
        int[]salida = {numerador,denominador};
        if(salida[0]==1||salida[0]==-1)
            return salida;
        if(salida[1]==1||salida[1]==-1)
            return salida;
        int MCD = simplificar(salida[0],salida[1]);
        salida[0]/=MCD;
        salida[1]/=MCD;
        
        return salida;
    }
    /**
     * este metodo calcula la pendiente de una recta
     * @param x1 coordenada en el eje x del punti inicial
     * @param y1 coordenada en el eje y del punti inicial
     * @param x2 coordenada en el eje x del punti final
     * @param y2 coordenada en el eje y del punti final
     * @return
     */
    public int[] pendiente(int[]a, int[] b) {
        int[]salida = new int[2];
        salida[1] = b[0]-a[0];
        salida[0] = b[1]-a[1];
        if (salida[0]==0 || salida[0]==0) {
            salida[0]=0;
            salida[1]=1;
            return salida;
        }
        int MCD = simplificar(salida[0],salida[1]);
        salida[0]/=MCD;
        salida[1]/=MCD;
        return salida;
    }
    /**
     * calcula el minimo comun multiplo de dos numeros para poder simplificar
     * @param a numero a
     * @param b numero b
     * @return minimo comun multiplo entre a y b
     */
    private int simplificar(int a, int b) {
        int temporal;
        while(b!=0) {
            temporal = b;
            b= a%b;
            a = temporal;            
        }
        return a;
    }

    
    /**
     * este metodo identifica los vertices cercanos 
     * @return el vertice al cual debe moverse el guardia
     */
    public ArrayList<int []> verticesCerca(int[] ubicacion){
        ArrayList<int []> vertices = new ArrayList<int []>();
        for(int i=0;i<limites.length;i++){
            int [] coor = limites[i];            
            if(canSee(coor,ubicacion)){
                int [] coords = new int [2];
                coords = limites[i];
                vertices.add(coords);
            }        
        }
        return vertices;
    }
    
    /**
     * metodo que decide cual es el vertice ideal para el movimiento
     * @param a coordenadas del punto inicial
     * @param b coordenadas del punto final
     * @return true-> si se puede mover o false-> no se puede mover
     */
    public boolean toMove(int[] a, int[] b) {
        
        int[] point= new int[2];
        
        if(a[0]<b[0]){
            point [0] = b[0] +1;
        }else{ 
            point[0] =b[0] -1;
        }
        
        if(a[1]<b[1]) {
            point[1] = b[1] +5;
        }else{
            point[1] = b[1] -5;
        }
             
        return contiene(point[0],point[1]);
    }
}
