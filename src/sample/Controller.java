package sample;

import java.sql.*;
import java.util.*;

public class Controller {
   public ArrayList tablelist = new ArrayList();
   public static void listeTable (){
       Connection con = getConnection();
       try {
           DatabaseMetaData meta = con.getMetaData();
           ResultSet res = meta.getCatalogs();
           System.out.println("Liste des bases de données ");
           while (res.next()) {
               System.out.println("   "
                       +res.getString("TABLE_CAT"));
           }
           res.close();

           con.close();
       } catch (Exception e) {
           System.err.println("ClassNotFoundException: "
                   +e.getMessage());
       }
   }
    public static Connection getConnection(){
        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1/", "root", "");

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return connection;

    }
    public static ArrayList getTable(String bd){
        ArrayList list = new ArrayList();
        try {
            String test = "jdbc:mysql://127.0.0.1/"+bd     ;
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/"+bd,"root","");
//on récupère les métadonnées à partir de la connexion
        DatabaseMetaData dmd = connection.getMetaData();
//récupération des informations
        ResultSet tables = dmd.getTables(connection.getCatalog(),null,"%",null);
//affichage des informations
        while(tables.next()){
            System.out.println("###################################");
            String tableName = tables.getString("TABLE_NAME" );
            list.add(tableName);
        }
        }catch (Exception e ){
        }
        return list;
   }
}



