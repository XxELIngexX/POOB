package Gallery;
import Shapes.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Room {
    private int[][] range = new int[2][];
    private int [][] coordenadas;
    private String color;
    private Polygon salon;
    private Sculpture escultura;
    private Alarm alarma;
    private Guard guardia;
    private int [] puerta;
    private ArrayList<Line> lineas = new ArrayList<Line>();
    private boolean isVisible;
    private boolean salida;
    /**
     * construye un salon dentro de una galeria
     * @param color el parametro color es el color y el identificador del salon
     * @param polygon el parametro polygon es un arreglo de coordenadas, 
     *        estas coordenadas son los vertices del salon
     * @param visibility es si el salon es visible o no
     */
    public void buildRoom(String Color, int[][] polygon,boolean visibility) {
        isVisible = visibility;
        color = Color; 
        coordenadas = polygon;
        toPolygon(polygon);
        Elementos escultura = new Sculpture(polygon,salon);
        Elementos guard = new Guard(polygon,salon);
        guardia = (Guard) guard;
        this.escultura = (Sculpture) escultura;
        puerta=puerta();  
    }
    
    /**
     * construye un salon con un tipos especificado.
     * @param color el parametro color es el color y el identificador del salon
     * @param polygon el parametro polygon es un arreglo de coordenadas, 
     *        estas coordenadas son los vertices del salon
     * @param visibility es si el salon es visible o no
     * @param type tipo de la escultura[norma, heavy, shy]
     */
    public void buildRoom(String type,String Color, int[][] polygon,boolean visibility) {
        isVisible = visibility;
        color = Color; 
        coordenadas = polygon;
        toPolygon(polygon);
        Elementos guard = new Guard(polygon,salon);
        guardia = (Guard) guard;
        puerta=puerta();
    }
    
    /**
     * construye un poligono con las especificaciones del salon
     * @param pyligon, son los vertices del salon
     */
    private void toPolygon(int[][] polygon){
        int[] x = new int[polygon.length];
        int[] y = new int[polygon.length];
        for (int i = 0; i< polygon.length;i++){
            x[i]=polygon[i][0];
            y[i]=polygon[i][1];
        }
        range[0]=x;
        range[1]=y;
        salon = new Polygon(x,y,x.length);  
        toLines(x,y);
    }
    
    /**
     * construye las lineas o las paredes del salon
     * @param x son las coordenadas en el eje x de todos los vertices
     * @param y son las coordenadas en el eje y de todos los vertices
     */
    private void toLines(int[] x, int[] y){
        for (int i= 0;i<x.length-1;i++){
            Line paredes = new Line (x[i],y[i],x[i+1],y[i+1],color);
            lineas.add(paredes);
        }
        Line paredes = new Line(x[x.length-1],y[x.length-1],x[0],y[0],color);
        lineas.add(paredes); 
    }
    
    /**
     * este metodo permite consultar cuanto ha recorrido el guardia
     * @return el calculo del recorrido del guardia
     */
    public float distanceTraveled() {
        float salida = 0;
        if (guardia != null) {
            salida = guardia.distanceTraveled();
        }
        return salida;
    }
    /**
     * crea una escultura en el salon, en las coordenadas inticadas
     * @param color es el salon donde se va a ubicar la escultura
     * @param x es la coordenada en x de la ubicacion de la escultua
     * @param y es la coordenada en y de la ubicacion de la escultua
     * @param ancho es el largo del salon, para hacer una equivalencia en cuanto al tamaño de la escultura
     */
    public void displaySculpture(String color, int x, int y,int ancho) {
        if (salon.contains(x,y)){
            escultura.displaySculpture(color,x,y,ancho);
            alarma = new Alarm(x,y,ancho);
        }
    }
    
    /**
     * 
     * @param color el parametro color es el color y el color donde se va a ubicar la escultura.
     * @param polygon el parametro polygon es un arreglo de coordenadas, 
     *        estas coordenadas son los vertices del salon
     * @param visibility es si el salon es visible o no
     */
    public void displaySculpture(String type,String color, int x, int y,int ancho) {
        if (salon.contains(x,y)){
            Circle escultura = new Circle(x, y, color);
            if(type.equals("Shy")) {
                this.escultura = (Sculpture) new Shy(coordenadas, salon, escultura, true,puerta);
                this.escultura.displaySculpture(color,x,y,ancho);
            }else if(type.equals("Heavy")){
                this.escultura = (Sculpture) new Heavy(coordenadas, salon, escultura);
                this.escultura.displaySculpture(color,x,y,ancho);
            }else if (type.equals("normal")){
                this.escultura = (Sculpture) new Normal(coordenadas, salon, escultura);
                this.escultura.displaySculpture(color,x,y,ancho);
            }
            alarma = new Alarm(x,y,ancho);
            makeVisible();
        }
       
    }
    /**
     * Permite robar una escultura si es posible, es decir, 
     * si cumple con los requerimientos del robo de la escultura.
     */
    public void steal() {
        if(!guardia.canSee(guardLocation(), sculptureLocation())&& canSteal()) {
            escultura.getSculpture().erase();
        }
    }
    
    /**
     * Verifica si se puede robar la escultura
     */
    public boolean canSteal() {
        salida = true;
        if(this.escultura instanceof Heavy) {
            salida = false;
        }
        return salida;
    }
    
    /**
     * Devuelve la alarma.
     */
    public Alarm getAlarm(){
        return alarma;
    }
    /**
     * ubica la coordenada mas al sur y mas al este del salon
     * @return la coordenada del vertice mas al sur y mas al este
     */
    private int[] puerta(){
        int xCord = coordenadas[0][0];
        int yCord = coordenadas[0][0];
        for (int i=0;i<coordenadas.length;i++){
            if (coordenadas[i][0]>xCord){
                xCord = coordenadas[i][0];
            }
            
            if (coordenadas[i][1]>yCord){
                yCord = coordenadas[i][1];
            }
        }    
        int [] salida = {xCord,yCord};
        return salida;
    }
    
    /**
     * metodo que devuelve la coordenada mas al sur y al este del salon
     * @return coordenada mas al sur y al este del salon
     */
    public int[] getPuerta(){
        return puerta;
    }
    
   /**
    * ubica al guardia en la coordenada mas al sur y al este del salon, 
    * en caso de que no se le de ubicacion al giardia designando de forma secuencial
    * 
    * @param room salon en el cual será asignado el guardia
    * @param ancho el ancho del salon para hacer su tamaño proporcional en comparacion al salon
    */
    public void arriveGuard(String room,int ancho) {
        guardia.arriveGuard(room,ancho,puerta);
    }
    
    /**
     * mueve al guardia a la coordenada especificada designando de forma secuencial
     * @param x es la coordenada en x donde se va a mover el guardia
     * @param y es la coordenada en y donde se va a mover el guardia
     */
    
    public void moveGuard(int x, int y) {
        if (salon.contains(x,y)){
            guardia.moveGuard(x,y);    
        }
    }
    
    /**
     * ubica al guardia en las coordenadas especificadas, designando de forma secuencial
     * @param x coordenada en X donde se ubicara el guardia
     * @param y coordenada en Y donde se ubicara al guardia
     * @param ancho largo del salon
     */
    public void putGuard(int x, int y,int ancho){
        guardia.putGuard("black",x,y,ancho);
    }
    
    /**
     * enciende o apaga la alarma de la escultura
     * @param on booleano que define si esta prendida o apagada la alarma
     */
    public void alarm(boolean on) {
        alarma.alarm(on);
        escultura.alarm();
    }
    
    /**
     * metodo que devulve la ubicacion de la escultura
     * @return las coordenadas donde esta ubicada la escultura
     */
    public int[] sculptureLocation() {
        return escultura.getPosition();
    }
    
    /**
     * metodo que devulve la ubicacion del guardia 
     * @return las coordenadas donde esta ubicado el guardia
     */
    public int[] guardLocation() {
        return guardia.getPosition();
    }
    /**
     * hace todos los elementos de la galeria visibles
     */
    public void makeVisible() {
        // for rooms
        isVisible=true;
        for (Line L : lineas){
            L.makeVisible();
        }
        if(guardia != null){
            guardia.makeVisible();    
        }
        if (escultura != null){
            escultura.makeVisible();    
        }  
        
        
        
    }
    
    /**
     * hace todos los elementos de la galeria invisibles
     */
    public void makeInvisible() {
        
        for (Line L : lineas){
            L.erase();
        }
        if(guardia.getGuard() != null){
            guardia.makeInvisible();    
        }
        if (escultura.getSculpture() != null){
            escultura.makeInvisible();    
        }
        if(alarma.getState())
            alarma.makeInvisible();
        isVisible = false;
    }
    
    /**
     * Devuelve la figura del guardia
     */
    public Guard getGuard() {
        return guardia;
    }
    
    /**
     *Devuelve el rango de todos los puntos organizados, tanto de x como y
     *ya que después se calculará el máximo y mínimo de cada uno
     */
    public int[][] getRange(){
        return range;
    }
    
    /**
     * Devuelve la escultura.
     */
    public Sculpture getSculpture() {
        return escultura;
    }
    
}
