package com.example.ecommercemarch;
import java.sql.*;

public class DbConnection {
    //dbname - username, password
    //url - db url

    private final String dbUrl = "jdbc:mysql://localhost:3306/ecommerce";
    private final String userName = "root";
    private final String password = "chandan";

    private Statement getStatement(){
        Connection conn;
        try{
            conn = DriverManager.getConnection(dbUrl, userName, password);
            return conn.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getQueryTable(String query){
        Statement statement = getStatement();
        try{
            return statement.executeQuery(query);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public int updateInsertDB(String query){
        Statement statement = getStatement();
        try{
            return statement.executeUpdate(query);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();
        ResultSet rs = dbConnection.getQueryTable("SELECT * FROM customer");
        if(rs != null){
            System.out.println("DB connection successful");
        }
    }
}
