package com.example.ecommercemarch;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {

    private VBox bodyPane;
    private VBox product;

    private GridPane loginPage(){
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");

        TextField userName = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Label loginMessage = new Label();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(userNameText, 0, 0);
        gridPane.add(userName, 1, 0);
        gridPane.add(passwordText, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 2);
        gridPane.add(loginMessage, 1, 2);


        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //code functionality.
                String username = userName.getText();
                String password = passwordField.getText();
                try {
                    if(Login.customerLogin(username, password)){
                        //login success
                        loginMessage.setText("Login Successful");
                        bodyPane.getChildren().clear();
                        product = productPageDemo();
                        bodyPane.getChildren().add(product);
                    }
                    else{
                        //login failed
                        loginMessage.setText("Login failed");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        return gridPane;
    }

    private HBox headerBar(){
        TextField searchText = new TextField();
        Button searchButton = new Button("search");
        searchText.setPrefWidth(160);
        HBox headerBar = new HBox(20);
        headerBar.setPadding(new Insets(5));
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(searchText, searchButton);
        return headerBar;
    }

    private HBox footerBar(){
        TextField searchText = new TextField();
        Button buyNowButton = new Button("Buy Now");
        Button addToCart = new Button("Add To Cart");

        searchText.setPrefWidth(160);
        HBox footerBar = new HBox(20);
        footerBar.setPadding(new Insets(5));
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton, addToCart);
        return footerBar;
    }

    private VBox productPageDemo(){
        Text text = new Text("I am product page");
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        VBox product = new VBox();
        product.getChildren().addAll(text);
        return product;
    }

    private BorderPane createContent(){

        BorderPane root = new BorderPane();
        root.setPrefSize(400, 300);

        //header bar
        GridPane loginPage = loginPage();
        HBox headerBar = headerBar();

        bodyPane = new VBox(20);
        bodyPane.setPadding(new Insets(5));
        bodyPane.getChildren().add(loginPage);
        bodyPane.setAlignment(Pos.CENTER);

        headerBar.setPadding(new Insets(5));

        root.setTop(headerBar);
        root.setCenter(bodyPane);

        //footer bar
        HBox footerBar = footerBar();
        root.setBottom(footerBar);

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