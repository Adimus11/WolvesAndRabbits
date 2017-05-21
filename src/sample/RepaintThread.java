package sample;

/**
 * Class which repaints the canvas, extends Thread
 * @author Michał Treter
 */
public class RepaintThread extends Thread {

    private Main mainWindowReference;

    /**
     * Constructor of the class
     * @param mainReference Reference to main window.
     */
    public RepaintThread(Main mainReference){
        this.mainWindowReference = mainReference;
    }

    public void run(){
        while(true) {
                mainWindowReference.drawOnCanvas();

            try {
                sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
