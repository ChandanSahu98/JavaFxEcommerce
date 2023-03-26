package com.example.ecommercemarch;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {

    private TableView<Product> productTableView;

    public VBox getAllProducts(){
       //columns
        TableColumn id = new TableColumn("ID-EComm");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("NAME-EComm");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("PRICE-EComm");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Need data for table
        ObservableList<Product> data = FXCollections.observableArrayList();

        // data.add(new Product(1, "Laptop", 2334)); //temp data
        // data.add(new Product(3, "Phone", 454)); //temp data

        data.clear();
        data = Product.getALlProducts();

        productTableView = new TableView<>();
        productTableView.setItems(data);
        productTableView.getColumns().addAll(id, name, price);

        VBox vBox = new VBox();
        vBox.setPadding((new Insets(5)));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(productTableView);
        return vBox;
    }
    public Product getSelectedProduct(){
        return productTableView.getSelectionModel().getSelectedItem();
    }
}
