package Gallery;

import java.awt.Polygon;

import Shapes.Circle;

public class Shy extends Sculpture{
    int[] attend;
    
    private boolean visible;
    public Shy(int[][] limites, Polygon salon,Circle a,Boolean visible,int[] attend) {
        super(limites, salon);
        super.setSculpture(a);
        this.visible=visible;
        this.attend = attend;
    }
    public int[] getAttent() {
        return attend;
    }
    public void setAttent(int[] attent) {
        this.attend = attent;
    }

}
