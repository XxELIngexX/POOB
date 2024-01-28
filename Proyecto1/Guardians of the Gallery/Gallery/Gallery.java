package Gallery;
import Shapes.*;
import java.util.*;
public class Gallery {
    
    private HashMap<String, Room> rooms = new HashMap<String, Room>();
    private String[] salonesAlert = new String[100];
    private int alert = 0;
    private int ancho;
    public boolean okey;
    
    /**
     * Devuele el salón.
     */
    public Room getRoom(String color) {
        return rooms.get(color);
    }
    
    
    /**
     * construye una nueva galeria vacia
     * @param length El parametro length define el largo de la galeria
     * @param width El parametro width define el ancho de la galeria
     */
    public Gallery(int length, int width) {
        ancho=length;
        Canvas.changeLength(length,width);
        Canvas.getCanvas();
    }
    
    /**
     * construye una nueva galeria con un salon negro, un guardia y una escultura
     * @param polygon el parametro polygon es un arreglo de coordenadas, 
     *        estas coordenadas son los vertices del salon
     * @param guard el parametro guard es la coordenada donde va ubicado el guardia
     * @param sculpture el parametro sculpture es la coordenada donde va ubicada la escultura
     * 
     */
    public Gallery(int[][] polygon, int[] guard, int[] sculpture) {
        if (rooms.containsKey("black") != true){
            // crear room
            buildRoom("black",polygon);
            Room salon = rooms.get("black");
            int [] puerta = salon.getPuerta();
            //crear canvas
            ancho=puerta[0]+20;
            Canvas.changeLength(puerta[0]+20,puerta[1]+20);
            Canvas.getCanvas();
            
            //crear guardia
            salon.putGuard(guard[0],guard[1],ancho);
            //crear esciltura
            salon.displaySculpture("black",sculpture[0],sculpture[1],ancho);
            rooms.put("black",salon);
            
            // dibujar room
            salon.makeVisible();
                
        }
    }
    
    
    /**
     * construye un salon dentro de una galeria designando de forma secuencial
     * @param color el parametro color es el color y el identificador del salon
     * @param polygon el parametro polygon es un arreglo de coordenadas, 
     *        estas coordenadas son los vertices del salon
     */
    public void buildRoom(String color, int[][] polygon) {
        if (rooms.containsKey(color) != true){
            Room salon = new Room();
            salon.buildRoom(color,polygon,false);
            rooms.put(color,salon);
            okey = true;
        }
        else{
            okey = false;
            ok();
        }
    }
    
    /**
     * construye un salon determinando el tipo.
     */
    public void buildRoom(String type,String color, int[][] polygon) {
        if (rooms.containsKey(color) != true){
            Room salon = new Room();
            salon.buildRoom(type,color,polygon,false);
            rooms.put(color,salon);
            okey = true;
        }
        else{
            okey = false;
            ok();
        }
    }
    
    /**
     * crea una escultura en el salon indicado, en las coordenadas inticadas designando de forma secuencial
     * @param color es el salon donde se va a ubicar la escultura
     * @param x es la coordenada en x de la ubicacion de la escultua
     * @param y es la coordenada en y de la ubicacion de la escultua
     */
    public void displaySculpture(String color, int x, int y) {
        if (rooms.containsKey(color)){
            Room salon = rooms.get(color);
            salon.displaySculpture(color,x,y,ancho); 
            okey = true;
        }
        else{
            okey = false;
            ok();
        }
    }
    
    public void displaySculpture(String type,String color, int x, int y) {
        if (rooms.containsKey(color)){
            Room salon = rooms.get(color);
            salon.displaySculpture(type,color,x,y,ancho); 
            okey = true;
        }
        else{
            okey = false;
            ok();
        }
    }
    
    /**
     * Permite el robo de la escultura
     * @param Identificador del salón.
     */
    public void steal(String color) {
        if (rooms.containsKey(color)){
            Room salon = rooms.get(color);
            salon.steal(); 
        }
    }
    
    /**
     * ubica un guardia en la posicion mas al sur y al este designando de forma secuencial
     * @param room es el salon donde se va a ubicar al guardia
     */
    public void arriveGuard(String room) {
        if (rooms.containsKey(room)){
            Room salon = rooms.get(room);
            salon.arriveGuard(room,ancho); 
            okey = true;
        }
        else{
            okey = false;
            ok();
        }
        
    }
    
    /**
     * este metodo permite consultar cuanto ha recorrido el guardia
     * @param room el salon en el que se va a consultar
     * @return el calculo del recorrido del guardia
     */
    public float distanceTraveled(String room) {
        float salida = 0f;
        if (rooms.containsKey(room)){
            Room salon = rooms.get(room);
            salida=salon.distanceTraveled();
        }
        
        return  salida;
    }
    
    /**
     * mueve al guardia a la coordenada especificada designando de forma secuencial
     * @param room es el salon del que se va a mover el guardia
     * @param x es la coordenada en x donde se va a mover el guardia
     * @param y es la coordenada en y donde se va a mover el guardia
     */
    public void moveGuard(String room, int x, int y) {
        if (rooms.containsKey(room)){
            Room salon = rooms.get(room);
            salon.moveGuard(x,y);   
            okey = true;
        }
        else{
            okey = false;
            ok();
        }
          
    }
    
    /**
     * activa o desactiva la alarma de una escultura designando de forma secuencial
     * @param room es el salon donde se va a activar la alarma
     * @param on indica true -> encendida o false -> apagada
     */
    public void alarm(String room, boolean on) {
        if (rooms.containsKey(room)){
             if(!(rooms.get(room) instanceof roomUnprotected)){
                 Room salon = rooms.get(room);
                salon.alarm(on); 
                
                if (on){
                    salonesAlert[alert] = room;
                    alert++;
                }
                else{
                    alert--;
                    salonesAlert[alert] = null;
                }
                
                okey = true;
             }
             else{
                okey = false;
                ok();
             }
        }
        else{
            okey = false;
            ok();
        }
        
    }

    /**
     * metodo que devuelve los salones de una galeria 
     * @return el arreglo de los salones que hay en la galeria
     */
    public String[] rooms() {    
        okey = true;
        String[] salida = rooms.keySet().toArray(new String[0]);
        return salida;
    }
    
    /**
     * devuelve los salones que tienen la alarma activada
     * @return el arreglo con los salones que tienen las alarmas activadas
     */
    public String[] roomsOnAlert() {
        okey = true;
        return salonesAlert;
    }
    
    /**
     * devulve las coordenadas donde se encuentra la escultura 
     * @param room es el salon del cual se desea la ubicacion de la escultura
     * @return las coordenadas en X y Y donde se encuentra la escultura
     */
    public int[] sculptureLocation(String room) {
        int[] salida = null;
        if (rooms.containsKey(room)){
            Room salon = rooms.get(room);
            salida = salon.sculptureLocation();
            okey = true;
        }
        else{
            okey = false;
            ok();
        }
        return salida;
    }

    /**
     * devulve las coordenadas donde se encuentra el guardia 
     * @param room es el salon del cual se desea la ubicacion del guardia
     * @return las coordenadas en X y Y donde se encuentra el guardia
     */
    public int[] guardLocation(String room) {
        int[] salida = null;
        if (rooms.containsKey(room)){
            Room salon = rooms.get(room);
            salida = salon.guardLocation();
            okey = true;
        }
        else{
            okey = false;
            ok();
        }
        return salida;
    }
    
    /**
     * hace todos los elementos de la galeria visibles
     */
    public void makeVisible() {
        if(!rooms.isEmpty()){
            for (String r : rooms.keySet()){
                if (r!=null){
                    Room salon = rooms.get(r);
                    salon.makeVisible();
                }
            }   
        }
    }
    
    /**
     * hace todos los elementos de la galeria invisibles
     */
    public void makeInvisible() {
        if(!rooms.isEmpty()){
            for (String r : rooms.keySet()){
                if (r!=null){
                    Room salon = rooms.get(r);
                    salon.makeInvisible();
                }
            }   
        }
    }
    
    /**
     *Completar :3
     */
    public void finish() {

    }

    /**
     *Confirma si un método se puede realizar o no.
     */
    public void ok() {
        System.out.println(okey);
    }

}
