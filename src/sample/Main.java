package sample;

import javafx.application.Application;
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

public class Main extends Application {

    public int[][] map;
    public Canvas drawingCanvas;
    public GraphicsContext gc;
    public ArrayList<Rabbit> rabbits = new ArrayList<Rabbit>();
    public Image grass;
    public Wolf wolf = new Wolf();
    private int sizeX, sizeY;

    @Override
    public void start(Stage primaryStage) throws Exception{
        sizeX = 30;
        sizeY = 30;

        grass = new Image(getClass().getResource("assets/grass.jpg").toString());

        initMap();
        showMap();

        Group root = new Group();
        drawingCanvas = new Canvas(20 * sizeX, 20 * sizeY);
        gc = drawingCanvas.getGraphicsContext2D();
        root.getChildren().add(drawingCanvas);

        drawOnCanvas();

        primaryStage.setTitle("Wolves & Rabbits");
        primaryStage.setScene(new Scene(root));

        primaryStage.setWidth(20 * sizeX);
        primaryStage.setHeight(20 * sizeY);
        primaryStage.setResizable(false);

        primaryStage.show();

        while(!rabbits.isEmpty()){

        }

        showExitMessage();

        System.exit(0);

    }

    public void drawOnCanvas(){
        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                gc.drawImage(grass, 20 * i, 20 * j);
            }
        }

        if(wolf != null){

        }

    }

    public void initMap(){
        map = new int[sizeX + 2][sizeY + 2];

        for(int i = 0; i < sizeX + 2; i++){
            map[0][i] = -1;
            map[sizeX + 1][i] = -1;
        }

        for(int i = 0; i < sizeY + 2; i++){
            map[i][0] = -1;
            map[i][sizeY + 1] = -1;
        }

        for(int i = 1; i < sizeX + 1; i++){
            for(int j = 1; j < sizeY + 1; j++){
                map[i][j] = 0;
            }
        }
    }

    public void showMap(){
        for(int i = 0; i < sizeX + 2; i++){
            for(int j = 0; j < sizeY + 2; j++){
                if(map[i][j] != -1){
                    System.out.print(" ");
                }

                System.out.print(map[i][j]);
            }
            System.out.println();
        }
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
