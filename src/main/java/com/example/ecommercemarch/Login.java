package com.example.ecommercemarch;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    public static Customer customerLogin(String userName, String password) throws SQLException {
        //create a query.
        String query = "Select * from customer where email = '"+userName+"' And password = '"+password+"' ";
        DbConnection dbConnection = new DbConnection();
        ResultSet rs = dbConnection.getQueryTable(query);
        if(rs != null && rs.next()){
            try {
//                if (rs.getString("name").equals("Angad")) return true;
//                return false;
                return new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("name"), null, null);

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        Login login = new Login();
        System.out.println(login.customerLogin("angad@gmail.com", "abc"));
    }
}
