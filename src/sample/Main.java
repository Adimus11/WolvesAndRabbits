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

public class Main extends Application {

    public int[][] map;
    public Random randomGenerator;
    public Canvas drawingCanvas;
    public GraphicsContext gc;
    public ArrayList<Rabbit> rabbits = new ArrayList<Rabbit>();
    public Image grass;
    public Wolf wolf;
    public int sizeX, sizeY;
    public RepaintThread rt;
    public double time;
    private int rabbitNumber;
    private Image rabbitImage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        sizeX = 50;
        sizeY = 30;
        rabbitNumber = 50;
        time = 100;

        ParametersPopUp popUp = new ParametersPopUp();

        popUp.display();

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
        primaryStage.setHeight(20 * (sizeY + 1));
        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(e -> System.exit(0));

        primaryStage.show();

    }

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


    public void endProgram(){
        //showExitMessage();

        System.exit(0);
    }

    public void showExitMessage(){
        Alert programFinished = new Alert(Alert.AlertType.INFORMATION);
        programFinished.setTitle("Program zakończy działanie");
        programFinished.setContentText("Już nie ma żadnych królików na scenia, czas zakończyć działanie");
        programFinished.setHeaderText(null);

        programFinished.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
