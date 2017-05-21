package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class resposnsible for showing information pop-up window.
 * @author Michał Treter
 */
public class InfoPopUp {

    /**
     * Method which purpose is to display the pop-up window.
     * @param title Title of the window.
     * @param comment Information what this window means.
     */
    public static void display(String title, String comment)
    {
        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle(title);


        Label label1= new Label(comment);


        Button button1= new Button("Zakończ program");


        button1.setOnAction(e -> System.exit(0));



        VBox layout= new VBox(10);


        layout.getChildren().addAll(label1, button1);

        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 410, 75);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }

}
