package org.example.modul_122322;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        // === First View ===
        Label label = new Label("Welcome to Modul 120/322!");
        TextField textField = new TextField("Type here...");
        Button actionButton = new Button("Click me!");
        Button switchToSecondViewButton = new Button("Go to Second View");

        VBox mainComponents = new VBox(10, label, textField, actionButton);
        mainComponents.setAlignment(Pos.CENTER);

        BorderPane firstView = new BorderPane();
        firstView.setTop(switchToSecondViewButton);
        BorderPane.setAlignment(switchToSecondViewButton, Pos.TOP_LEFT); // Align button to top-left
        firstView.setCenter(mainComponents);

        Scene firstScene = new Scene(firstView, 400, 300);

        // === Second View ===
        Button switchToFirstViewButton = new Button("Back to First View");
        StackPane secondView = new StackPane(switchToFirstViewButton);
        StackPane.setAlignment(switchToFirstViewButton, Pos.TOP_LEFT);
        Scene secondScene = new Scene(secondView, 400, 300);

        // === Button Actions for Scene Switching ===
        switchToSecondViewButton.setOnAction(e -> primaryStage.setScene(secondScene));
        switchToFirstViewButton.setOnAction(e -> primaryStage.setScene(firstScene));


        primaryStage.setTitle("Modul_120/322");
        primaryStage.setScene(firstScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}