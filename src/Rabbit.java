import javafx.util.Pair;

/**
 * Created by adimus on 5/16/17.
 */
public class Rabbit extends Thread {
    private int posX, posY;

    public Rabbit(){
        //TODO: Implement constructor
    }

    public Pair getPosition(){
        return new Pair(this.posX, this.posY);
    }
}
