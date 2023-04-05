package com.example.ecommercemarch;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    public static int placeSingleOder(Product product, Customer customer){
        String getMaxGroupOrderId = "Select MAX(group_order_id) + 1 as id From orders";
        DbConnection dbConnection = new DbConnection();
        try{
            ResultSet rs = dbConnection.getQueryTable(getMaxGroupOrderId);
            if(rs.next()){
                String orderQuery = "Insert into orders(group_order_id, customer_id, product_id, status) Values("+rs.getInt(1)+", "+customer.getId()+", "+product.getId()+", 'ORDERED')";
                return dbConnection.updateInsertDB(orderQuery);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static int placeMultipleOrder(ObservableList<Product> productList, Customer customer){
        String getMaxGroupOrderId = "Select MAX(group_order_id) + 1 as id from orders";
        DbConnection dbConnection = new DbConnection();
        try {
            int groupOrderId = 0;
            ResultSet rs = dbConnection.getQueryTable(getMaxGroupOrderId);
            if(rs.next()){
                groupOrderId = rs.getInt("id");
            }
            int count = 0;
            for(Product product : productList){
                String orderQuery = "Insert into orders(group_order_id, customer_id, product_id, status) values(" + groupOrderId+", "+customer.getId()+", "+product.getId()+", 'ORDERED')";
                count += dbConnection.updateInsertDB(orderQuery);
            }
            return count;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
