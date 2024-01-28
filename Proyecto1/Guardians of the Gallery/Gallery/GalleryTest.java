package Gallery;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.text.DecimalFormat;

/**
 * The test class TextGallery.
 *
 * @author  (Paez - Amaya)
 * @version (a version number or a date)
 */
public class GalleryTest
{
    @Test
    
    public void shouldDisappearIfUnseen(){
        Gallery gall = new Gallery(30, 12);
        int[] sculptLoc = {3, 4};
        gall.buildRoom("Blue", new int[][]{{3,3}, {3,5}, {5,5}, {5,3}});
        gall.arriveGuard("Blue");
        gall.displaySculpture("Shy", "Blue",sculptLoc[0], sculptLoc[1]);
        
    }
    
    @Test
    public void shouldNotHaveAlarm(){
        Gallery gall = new Gallery(30, 12);
        gall.buildRoom("Unprotected", "Blue", new int[][]{{3,3}, {3,5}, {5,5}, {5,3}});
        assertNull(gall.getRoom("Blue").getAlarm());
    }
    
    @Test
    public void shouldNotHaveSculpture(){
        Gallery gall = new Gallery(30, 12);
        gall.buildRoom("Unprotected", "Blue", new int[][]{{3,3}, {3,5}, {5,5}, {5,3}});
        assertNull(gall.getRoom("Blue").getSculpture());
    }
    @Test
    public void shouldNotStealIfHeavy(){
        Gallery gall = new Gallery(30, 12);
        int[] sculptLoc = {3, 4};
        gall.buildRoom("Blue", new int[][]{{3,3}, {3,5}, {5,5}, {5,3}});
        gall.arriveGuard("Blue");
        gall.displaySculpture("Heavy", "Blue",sculptLoc[0], sculptLoc[1]);
        boolean stolen = gall.getRoom("Blue").canSteal();
        assertFalse(stolen);
    }
    
    @Test
    public void shouldArriveGuard(){
        Gallery gall = new Gallery(30, 12);
        int[] sculptLoc = {3, 4};
        gall.buildRoom("Blue", new int[][]{{3,3}, {3,5}, {5,5}, {5,3}});
        gall.arriveGuard("Blue");
        assertTrue(gall.okey);
    }
    
    @Test
    public void shouldDisplaySculpture(){
        Gallery gall = new Gallery(30, 12);
        int[] sculptLoc = {3, 4};
        gall.buildRoom("Blue", new int[][]{{3,3}, {3,5}, {5,5}, {5,3}});
        gall.displaySculpture("Blue",sculptLoc[0], sculptLoc[1]);
        assertTrue(gall.okey);
    }
    
    @Test
    public void shouldCreateGallery(){
        Gallery gall = new Gallery(10,10);
        assertTrue(gall != null);
    }
    
    @Test
    public void testGetRoom(){
        Gallery gall = new Gallery(30, 12);
        gall.buildRoom("Blue", new int[][]{{3,3}, {3,5}, {5,5}, {5,3}});
        assertTrue(gall.okey);
    }
    
    @Test
    public void testRoomsOnAlert(){
        Gallery gall = new Gallery(45,10);
        gall.buildRoom("Blue", new int[][]{{3,3}, {3,5}, {5,5}, {5,3}});
        assertTrue(gall.okey);
    }

    
    @Test
    public void testSteal(){
        Gallery gall = new Gallery(30, 12);
        gall.buildRoom("Blue", new int[][]{{3,3}, {3,5}, {5,5}, {5,3}});
        assertTrue(gall.okey);
    }
    
    @Test
    public void testDistanceTraveled() {
        Gallery gall = new Gallery(50,15);
        gall.buildRoom("Blue", new int[][]{{3,3}, {3,5}, {5,5}, {5,3}});
        gall.arriveGuard("Blue");
        DecimalFormat numberFormat = new DecimalFormat("#.00000");
        int[] pos = gall.guardLocation("Blue");
        float initialDistance = gall.distanceTraveled("Blue");
        gall.moveGuard("Blue", 3, 3);
        float finalDistance = gall.distanceTraveled("Blue");
        assertEquals(numberFormat.format(finalDistance), numberFormat.format(Math.sqrt(Math.pow((3-pos[0]),2)+Math.pow((3-pos[1]),2))));
    }
    
    @Test     
    public void testAlarm(){
        Gallery gall = new Gallery(30, 12);
        gall.buildRoom("Blue", new int[][]{{3,3}, {3,5}, {5,5}, {5,3}});
        assertTrue(gall.okey);
    }
    
    @Test
    public void testMoveGuard(){
        Gallery gall = new Gallery(50,15);
        gall.buildRoom("Blue", new int[][]{{3,3}, {3,5}, {5,5}, {5,3}});
        gall.arriveGuard("Blue");
        int[] guardLoc = {5,5};
        gall.moveGuard("Blue", guardLoc[0], guardLoc[1]);
        int[] pos = gall.guardLocation("Blue");
        assertArrayEquals(guardLoc, pos);
    }
    
    @Test
    public void testRooms(){
        Gallery gall = new Gallery(30,18);
        gall.buildRoom("Red", new int[][]{{1,1}, {3,1}, {3,3}, {1,3}});
        gall.buildRoom("Blue", new int[][]{{4,4}, {4,6}, {6,6}, {6,4}});
        gall.buildRoom("Pink", new int[][]{{8,8}, {8,10}, {10,8}, {10,10}});
        gall.buildRoom("Green", new int[][]{{12,12}, {12,16}, {16,12}, {16,16}});
        gall.buildRoom("Yellow", new int[][]{{20,20}, {20,25}, {25,20}, {25,25}});
        String[] numRooms = gall.rooms();
        assertEquals(5, numRooms.length);
    }
    
    @Test
        public void testBuildRoom(){
        Gallery gall = new Gallery(10,7);
        gall.buildRoom("Red", new int[][]{{1,1}, {1,6}, {5,6}, {5,1}});
        assertTrue(gall.okey);
    }
    
    @Test
    public void testGuardLocation() {
        Gallery gall = new Gallery(10, 8);
        int[] guardLoc = {5, 6};
        gall.buildRoom("Blue", new int[][]{{1,1}, {1,6}, {5,6}, {5,1}});
        gall.arriveGuard("Blue");
        int[] result = gall.guardLocation("Blue");
        assertArrayEquals(guardLoc, result);
    }
    
    @Test
    public void testSculptureLocation(){
        Gallery gall = new Gallery(10, 8);
        int[] sculptLoc = {3, 4};
        gall.buildRoom("Blue", new int[][]{{1,1}, {1,6}, {5,6}, {5,1}});
        gall.displaySculpture("Blue",sculptLoc[0], sculptLoc[1]);
        int[] result = gall.sculptureLocation("Blue");
        assertArrayEquals(sculptLoc, result);
    }
}
