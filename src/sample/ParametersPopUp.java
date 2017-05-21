package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Class which construc pop-up windows asking for programm parameters.
 * @author Michał Treter
 */
public class ParametersPopUp {

    /**
     * ArrayList of parameters in string.
     */
    public ArrayList<String> parameters;

    /**
     * Method which constructs and displays pop-up window.
     */
    public void display()
    {
        Stage popupwindow=new Stage();
        parameters = new ArrayList<String>();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Parametry programu");

        Label heightLabel = new Label("Podaj liczbę pól w osi X:");
        Label widthLabel = new Label("Podaj liczbę pól w osi Y:");
        Label rabbitsLabel = new Label("Podaj liczbę królików:");
        Label peroidLabel = new Label("Podaj okres(k > 99):");

        Button submit = new Button("Zatwierdź!");

        TextField paramX = new TextField();
        TextField paramY = new TextField();
        TextField paramRabbit = new TextField();
        TextField paramPeroid = new TextField();

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        GridPane.setConstraints(heightLabel, 0, 0);
        grid.getChildren().add(heightLabel);
        GridPane.setConstraints(paramX, 1, 0);
        grid.getChildren().add(paramX);
        GridPane.setConstraints(widthLabel, 0, 1);
        grid.getChildren().add(widthLabel);
        GridPane.setConstraints(paramY, 1, 1);
        grid.getChildren().add(paramY);
        GridPane.setConstraints(rabbitsLabel, 0, 2);
        grid.getChildren().add(rabbitsLabel);
        GridPane.setConstraints(paramRabbit, 1, 2);
        grid.getChildren().add(paramRabbit);
        GridPane.setConstraints(peroidLabel, 0, 3);
        grid.getChildren().add(peroidLabel);
        GridPane.setConstraints(paramPeroid, 1, 3);
        grid.getChildren().add(paramPeroid);
        GridPane.setConstraints(submit, 0, 4);
        grid.getChildren().add(submit);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parameters.add(paramX.getCharacters().toString());
                parameters.add(paramY.getCharacters().toString());
                parameters.add(paramRabbit.getCharacters().toString());
                parameters.add(paramPeroid.getCharacters().toString());

                popupwindow.close();
            }
        });

        Scene scene1= new Scene(grid, 350, 180);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }

}
