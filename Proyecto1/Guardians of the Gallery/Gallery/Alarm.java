package Gallery;
import Shapes.*;
public class Alarm{
    private int x;
    private int y;
    private int size;
    private Circle alarma;
    private boolean state = false;
    
    /**
     * construye una alarma en el salon
     * @param xPoint coordenada en el eje x del punto donde se ubicará
     * @param yPoint coordenada en el eje x del punto donde se ubicará
     * @param ancho longitud de la galeria
     */
    public Alarm(int xPoint,int yPoint,int ancho){
        size = (ancho*8)/100;
        alarma = new Circle(xPoint, yPoint, "reed");
        alarma.changePosition(xPoint, yPoint);
        alarma.changeSize(size);
    }
     /**
      * este metodo activa o desactiva la alarma
      * @param on boolean de activado o desactivado
      */
    public void alarm( boolean on) {
        if (on){
            alarma.makeVisible();
            state = true;
        } else{
            alarma.makeInvisible();
            state = false;
        }
    }
    
    /**
     * metodo que retorna el estado de la alarma, si esta activada o desactivada
     * @return estado de la alarma
     */
    public boolean getState(){
        return state;
    }
    public void makeInvisible() {
    	alarma.makeInvisible();
    }
}

