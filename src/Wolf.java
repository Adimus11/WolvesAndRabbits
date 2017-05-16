import javafx.util.Pair;
import javafx.scene.image.Image;

/**
 * Created by adimus on 5/16/17.
 */
public class Wolf extends Thread {
    private int posX, posY;
    private Image wolfImage;

    public Wolf(){
        //TODO: Implement constructor
    }

    public void findNearestRabbit(){
        //TODO: apply algorithm to find neearest desired vortex in grid graph
    }

    private void makeMove(){
        //TODO: according to nearestRebbit, make the best possible move
    }

    public Pair getPosition(){
        return new Pair(this.posX, this.posY);
    }

}
