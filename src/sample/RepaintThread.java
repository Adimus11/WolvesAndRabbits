package sample;

/**
 * Created by Adimus on 21.05.2017.
 */
public class RepaintThread extends Thread {

    private Main mainWindowReference;

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
