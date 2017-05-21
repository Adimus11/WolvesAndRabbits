package sample;

import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class of Rabbit which extends Thread.
 * @author Michał Treter
 */
public class Rabbit extends Thread {

    /**
     * X position of rabbit in plane.
     */
    public int posX,

    /**
     * Y position of rabbit in plane.
     */
    posY;
    private int breakTime;
    private Main mainWindowReference;

    /**
     * Boolean variable which represents if thread is alive.
     */
    public boolean alive = true;

    /**
     * COnstructor of Rabbit class
     * @param mainReference Reference to main class.
     * @param cycleTime Time for thread to sleep.
     */
    public Rabbit(Main mainReference, int cycleTime){
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
        while(alive) {
            makeMove();
            //mainWindowReference.drawOnCanvas();
            try {
                sleep(breakTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized boolean wallAvailable(){
        if(mainWindowReference.map[posX + 1][posY] == -1){
            return true;
        }
        if(mainWindowReference.map[posX - 1][posY] == -1){
            return true;
        }
        if(mainWindowReference.map[posX][posY + 1] == -1){
            return true;
        }
        if(mainWindowReference.map[posX][posY - 1] == -1){
            return true;
        }
        if(mainWindowReference.map[posX + 1][posY + 1] == -1){
            return true;
        }
        if(mainWindowReference.map[posX + 1][posY - 1] == -1){
            return true;
        }
        if(mainWindowReference.map[posX - 1][posY + 1] == -1){
            return true;
        }
        if(mainWindowReference.map[posX -1][posY - 1] == -1){
            return true;
        }
        return false;
    }

    private synchronized ArrayList<Pair2> generatePossibleMoves(){
        ArrayList<Pair2> possibleMoves = new ArrayList<Pair2>();

        if(mainWindowReference.map[posX + 1][posY] == 0){
            possibleMoves.add(new Pair2(1, 0));
        }
        if(mainWindowReference.map[posX - 1][posY] == 0){
            possibleMoves.add(new Pair2(-1, 0));
        }
        if(mainWindowReference.map[posX][posY + 1] == 0){
            possibleMoves.add(new Pair2(0, 1));
        }
        if(mainWindowReference.map[posX][posY - 1] == 0){
            possibleMoves.add(new Pair2(0, -1));
        }
        if(mainWindowReference.map[posX + 1][posY + 1] == 0){
            possibleMoves.add(new Pair2(1, 1));
        }
        if(mainWindowReference.map[posX + 1][posY - 1] == 0){
            possibleMoves.add(new Pair2(1, -1));
        }
        if(mainWindowReference.map[posX - 1][posY + 1] == 0){
            possibleMoves.add(new Pair2(-1, 1));
        }
        if(mainWindowReference.map[posX - 1][posY - 1] == 0){
            possibleMoves.add(new Pair2(-1, -1));
        }

        return possibleMoves;

    }

    private synchronized void makeMove(){
        int prevX, prevY;

        prevX = this.posX;
        prevY = this.posY;

        int newX = 0;
        int newY = 0;

        newX = this.posX - mainWindowReference.wolf.posX;
        newY = this.posY - mainWindowReference.wolf.posY;

        if(newX > 0){
            newX = 1;
        }
        if(newX < 0){
            newX = -1;
        }
        if(newY > 0){
            newY = 1;
        }
        if(newY < 0){
            newY = -1;
        }


        if(mainWindowReference.map[this.posX + newX][this.posY + newY] == 0) {
            this.posX += newX;
            this.posY += newY;


        }
        if(mainWindowReference.map[this.posX + newX][this.posY + newY] != 1) {
            if(wallAvailable()) {
                ArrayList<Pair2> moves = generatePossibleMoves();
                if(moves.size() > 0) {
                    Collections.shuffle(moves);

                    Pair2 move = moves.get(0);

                    newX = move.x;
                    newY = move.y;

                    posX += newX;
                    posY += newY;
                }
            }
        }

        if(mapFree()) {
            mainWindowReference.map[prevX][prevY] = 0;
        }
        else{
            posY = prevY;
            posX = prevX;
        }
    }

    private synchronized boolean mapFree(){
        if(mainWindowReference.map[posX][posY] == 0){
            mainWindowReference.map[posX][posY] = 1;
            return true;
        }

        return false;
    }

}

