package com.example.ecommercemarch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private GridPane loginPage(){
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");

        TextField userName = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        GridPane gridPane = new GridPane();
        gridPane.add(userNameText, 0, 0);
        gridPane.add(userName, 1, 0);
        gridPane.add(passwordText, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 2);
        return gridPane;
    }

    private HBox headerBar(){
        TextField searchText = new TextField();
        Button searchButton = new Button("search");
        HBox headerBar = new HBox(20);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(searchText, searchButton);
        return headerBar;
    }

    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(400, 300);
        root.setPrefSize(400, 300);
        GridPane loginPage = loginPage();
        loginPage.setTranslateY(40);
        root.getChildren().addAll(headerBar(), loginPage);
        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("ECommerce!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}