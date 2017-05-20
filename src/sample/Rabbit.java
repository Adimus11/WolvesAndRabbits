package sample;

import javafx.scene.image.Image;
import javafx.util.Pair;

/**
 * Created by adimus on 5/16/17.
 */
public class Rabbit extends Thread {
    private int posX, posY;
    public Image Rabbit;

    public Rabbit(){
        //TODO: Implement constructor
    }

    @Override
    public void run(){

    }

    public Pair getPosition(){
        return new Pair(this.posX, this.posY);
    }
}
