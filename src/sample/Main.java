package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

/**
 * Main class of the program which extends JavaFX Application
 * @author Michał Treter
 */
public class Main extends Application {

    /**
     * Variable which contains map of the plane.
     */
    public int[][] map;

    /**
     * Variable which contains random generator.
     */
    public Random randomGenerator;

    /**
     * Variable which contains main canvas.
     */
    public Canvas drawingCanvas;

    /**
     * Variable which contains main graphics context.
     */
    public GraphicsContext gc;

    /**
     * Variable which contains ArrayList of all rabbits.
     */
    public ArrayList<Rabbit> rabbits = new ArrayList<Rabbit>();

    /**
     * Variable which contains image of the grass.
     */
    public Image grass;

    /**
     * Variable which contains wolf object.
     */
    public Wolf wolf;

    /**
     * Variable which contains X size of the plane.
     */
    public int sizeX,

    /**
     * Variable which contains Y size of the plane.
     */
    sizeY;

    /**
     * Variable which contains thread responsible for repainting screen.
     */
    public RepaintThread rt;

    /**
     * Variable which contains "k" argument of the program.
     */
    public double time;
    private int rabbitNumber;
    private Image rabbitImage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        ParametersPopUp popUp = new ParametersPopUp();

        popUp.display();

        try{
            sizeX = Integer.parseInt(popUp.parameters.get(0));
            sizeY = Integer.parseInt(popUp.parameters.get(1));
            rabbitNumber = Integer.parseInt(popUp.parameters.get(2));
            time = Integer.parseInt(popUp.parameters.get(3));

            if(time < 50 || sizeX < 1 || sizeY < 1 || rabbitNumber > ((sizeX * sizeY)/2) || sizeX > 64 || sizeY > 34){
                InfoPopUp.display("Podano złe argument", "Program nie może zostać wykonany poprzez błąd w podanych argumentach.");
            }
        }
        catch(NumberFormatException e){
            InfoPopUp.display("Podano złe argument", "Program nie może zostać wykonany poprzez błąd w podanych argumentach.");;
        }
        catch(IndexOutOfBoundsException e){
            InfoPopUp.display("Podano złe argument", "Program nie może zostać wykonany poprzez błąd w podanych argumentach.");
        }

        rabbitImage = new Image(getClass().getResource("assets/rabbit.png").toString());

        rt = new RepaintThread(this);

        randomGenerator = new Random();

        grass = new Image(getClass().getResource("assets/grass.jpg").toString());

        initMap();

        Group root = new Group();
        drawingCanvas = new Canvas(20 * sizeX, 20 * sizeY);
        gc = drawingCanvas.getGraphicsContext2D();
        root.getChildren().add(drawingCanvas);

        double cycleTime = (randomGenerator.nextDouble() + 0.5) * time;

        wolf = new Wolf(this, (int) cycleTime);

        for(int i = 0; i < rabbitNumber; i++){
            cycleTime = (randomGenerator.nextDouble() + 0.5) * time;
            rabbits.add(new Rabbit(this, (int) cycleTime));
        }

        drawOnCanvas();

        wolf.start();
        for(int i = 0; i < rabbits.size(); i++){
            rabbits.get(i).start();
        }

        rt.setPriority(Thread.MAX_PRIORITY);
        rt.start();

        primaryStage.setTitle("Wolves & Rabbits");
        primaryStage.setScene(new Scene(root));

        primaryStage.setWidth(20 * sizeX);
        primaryStage.setHeight(20 * (sizeY + 1) + 10);
        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(e -> System.exit(0));

        primaryStage.show();

    }

    /**
     * Method which painting on Canvas.
     */
    public void drawOnCanvas(){
        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                gc.drawImage(grass, 20 * i, 20 * j);
            }
        }

        for(int i = 0; i < rabbits.size(); i++){
            gc.drawImage(rabbitImage, (rabbits.get(i).posX - 1) * 20, (rabbits.get(i).posY - 1) * 20);
        }

        if(wolf != null){
            gc.drawImage(wolf.wolfImage, (wolf.posX - 1) * 20, (wolf.posY - 1) * 20);
        }

    }

    /**
     * Method which initializes map of the plane.
     */
    public void initMap(){
        map = new int[sizeX + 2][sizeY + 2];

        for(int i = 0; i < sizeX + 2; i++){
            map[i][0] = -1;
            map[i][sizeY +1] = -1;
        }

        for(int i = 0; i < sizeY + 2; i++){
            map[0][i] = -1;
            map[sizeX + 1][i] = -1;
        }

        for(int i = 1; i < sizeX + 1; i++){
            for(int j = 1; j < sizeY + 1; j++){
                map[i][j] = 0;
            }
        }
    }

    /**
     * Method which exiting program.
     */
    public void endProgram(){
        System.exit(0);
    }

    /**
     * Main method of whole application.
     * @param args Default arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
