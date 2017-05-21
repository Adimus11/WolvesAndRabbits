package sample;

import javafx.util.Pair;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class which represents Wolf, extends Thread.
 * @author Michał Treter
 */
public class Wolf extends Thread {

    /**
     * X position of wolf on plane.
     */
    public int posX,

    /**
     * Y position of wolf on plane.
     */
    posY;

    /**
     * Image of the wolf.
     */
    public Image wolfImage;
    private boolean isActive = true;
    private int breakTime;
    private Main mainWindowReference;
    private Rabbit nearestRabbit;
    private boolean rabbitsAvailable = true, rabbitEaten = false;
    private int waitCycle;

    /**
     * Constructor of the class.
     * @param mainReference Reference to main class.
     * @param cycleTime Sleep time for wolf thread.
     */
    public Wolf(Main mainReference, int cycleTime){
        wolfImage = new Image(getClass().getResource("assets/wolf.png").toString());
        this.mainWindowReference = mainReference;

        this.breakTime = cycleTime;

        int tempX, tempY;

        tempX = mainReference.randomGenerator.nextInt(mainReference.sizeY) + 1;
        tempY = mainReference.randomGenerator.nextInt(mainReference.sizeY) + 1;

        while(mainReference.map[tempX][tempY] != 0){
            tempX = mainReference.randomGenerator.nextInt(mainReference.sizeX) + 1;
            tempY = mainReference.randomGenerator.nextInt(mainReference.sizeY) + 1;
        }

        this.posX = tempX;
        this.posY = tempY;

        mainReference.map[tempX][tempY] = 2;

    }

    @Override
    public void run(){
        while(rabbitsAvailable) {
            if(mainWindowReference.rabbits.size() > 0) {
                if(!rabbitEaten) {
                    makeMove();
                }
                else{
                    waitCycle -= 1;
                    if(waitCycle == 0){
                        rabbitEaten = false;
                    }
                }
            }
            else{
                rabbitsAvailable = false;
                mainWindowReference.endProgram();
            }
            //mainWindowReference.drawOnCanvas();
            try {
                sleep(breakTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void findNearestRabbit(){
        ArrayList<Rabbit> nearestRabbits = new ArrayList<Rabbit>();
        nearestRabbit = null;

        double minDistance = Math.sqrt(Math.pow(this.posX - mainWindowReference.rabbits.get(0).posX, 2) + Math.pow(this.posY - mainWindowReference.rabbits.get(0).posY, 2));
        nearestRabbits.add(mainWindowReference.rabbits.get(0));

        for(int i = 1; i < mainWindowReference.rabbits.size(); i++){
            double tempDistance;
            tempDistance = Math.sqrt(Math.pow(this.posX - mainWindowReference.rabbits.get(i).posX, 2) + Math.pow(this.posY - mainWindowReference.rabbits.get(i).posY, 2));

            if( tempDistance < minDistance){
                minDistance = tempDistance;
                nearestRabbits.clear();
                nearestRabbits.add(mainWindowReference.rabbits.get(i));
            }

            if(tempDistance == minDistance){
                nearestRabbits.add(mainWindowReference.rabbits.get(i));
            }
        }

        nearestRabbit = nearestRabbits.get(mainWindowReference.randomGenerator.nextInt(nearestRabbits.size()));


    }

    private synchronized void makeMove(){
        int prevX, prevY;

        prevX = this.posX;
        prevY = this.posY;

        findNearestRabbit();
        if(nearestRabbit != null){
            int diffrenceX, diffrenceY;

            diffrenceX = nearestRabbit.posX - posX;
            diffrenceY = nearestRabbit.posY - posY;

            if(Math.abs(diffrenceX) <= 1 && Math.abs(diffrenceY) <= 1){
                mainWindowReference.rabbits.get(mainWindowReference.rabbits.indexOf(nearestRabbit)).alive = false;
                mainWindowReference.rabbits.remove(nearestRabbit);

                this.rabbitEaten = true;
                this.waitCycle = 5;

                posX += diffrenceX;
                posY += diffrenceY;
            }
            else{
                if(diffrenceX > 0){
                    posX += 1;
                }
                if(diffrenceX < 0){
                    posX -= 1;
                }
                if(diffrenceY > 0){
                    posY += 1;
                }
                if(diffrenceY < 0){
                    posY -= 1;
                }
            }
        }

        mainWindowReference.map[posX][posY] = 2;
        mainWindowReference.map[prevX][prevY] = 0;
    }

}
