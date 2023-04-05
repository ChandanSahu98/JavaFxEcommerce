package com.example.ecommercemarch;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private VBox productPage;
    HBox footerBar;
    HBox headerBar;
    private GridPane loginPage;
    ProductList productList = new ProductList();
    Customer loggedInCustomer;
    Button signInButton;
    Label welcomeLabel;

    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();

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
                    loggedInCustomer = Login.customerLogin(username, password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    if(loggedInCustomer != null){
                        //login success
                        loginMessage.setText("Login Successful");
                        bodyPane.getChildren().clear();
                        productPage = productList.getAllProducts();
                        bodyPane.getChildren().add(productPage);

                        //put a welcome message
                        welcomeLabel.setText("Welcome " + loggedInCustomer.getName());
                        headerBar.getChildren().add(welcomeLabel);
                        footerBar.setVisible(true);
                    }
                    else{
                        //login failed
                        loginMessage.setText("Login failed");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return gridPane;
    }

    private HBox headerBar(){
        TextField searchText = new TextField();
        Button searchButton = new Button("search");
        searchText.setPrefWidth(160);
        signInButton = new Button("Sign In");
        welcomeLabel = new Label("");
        Button cartButton = new Button("Cart");

        HBox headerBar = new HBox(20);
        headerBar.setPadding(new Insets(5));
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(searchText, searchButton, signInButton, cartButton);

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String search = searchText.getText();
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productList.getProductsByName(search));
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage);
                headerBar.getChildren().remove(signInButton);
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedInCustomer == null){
                    showDialog("Please login to see cart items.");
                    return;
                }
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productList.showProductsInCart(itemsInCart));
            }
        });

        return headerBar;
    }

    private HBox footerBar(){
        TextField searchText = new TextField();
        Button buyNowButton = new Button("Buy Now");
        Button addToCart = new Button("Add To Cart");
        Button placeOrderButton = new Button("Place Order");

        searchText.setPrefWidth(160);
        HBox footerBar = new HBox(20);
        footerBar.setPadding(new Insets(5));
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton, addToCart, placeOrderButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product1 = productList.getSelectedProduct();
                int result = 0;
                if(loggedInCustomer == null){
                    showDialog("You need to be logged in first!");
                    return;
                }
                if(product1 == null){
                    showDialog("Please select a product first!");
                }
                result = Order.placeSingleOder(product1, loggedInCustomer);

                if(result >= 1){
                    showDialog("Order Placed Successfully!");
                }
                else{
                    showDialog("Order Failed!");
                }
            }
        });

        addToCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedInCustomer == null){
                    showDialog("Please login first to add item to cart.");
                    return;
                }
                Product product = productList.getSelectedProduct();
                if(product == null){
                    showDialog("Please select a product to add to the cart.");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Product added to cart successfully.");
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedInCustomer == null){
                    showDialog("Please login first to add item to cart.");
                    return;
                }

                if(itemsInCart.size() == 0){
                    showDialog("Please add items to the cart first to place an order!!");
                    return;
                }
                int count = Order.placeMultipleOrder(itemsInCart, loggedInCustomer);
                showDialog("Order placed for "+count+" number of products.");
                itemsInCart.clear();
            }
        });

        return footerBar;
    }

    private void showDialog(String message){
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Message");
        dialog.setContentText(message);
        dialog.setHeaderText(null);
        dialog.showAndWait();
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
        root.setPrefSize(800, 600);

        //header bar

        headerBar = headerBar();
        root.setTop(headerBar);
        BorderPane.setAlignment(headerBar, Pos.CENTER);

        //Body
        bodyPane = new VBox(20);
        bodyPane.setPadding(new Insets(5));
        root.setCenter(bodyPane);
        bodyPane.setAlignment(Pos.CENTER);

        loginPage = loginPage();

        productPage = productList.getAllProducts();
        bodyPane.getChildren().add(productPage);


//        root.setTop(headerBar);


        //footer bar
       footerBar = footerBar();
       root.setBottom(footerBar);
       footerBar.setVisible(false);

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