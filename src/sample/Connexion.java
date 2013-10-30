package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: pc thomas
 * Date: 30/10/13
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */
public class Connexion {
    public static String login = "root";
    public static String mdp = "";
    public static String bd = "";
    public static Connection con;
    public static Connection getConnection(String login, String mdp, String bd){
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
                    .getConnection("jdbc:mysql://127.0.0.1/"+ bd, login, mdp);

        } catch (SQLException e) {
            System.out.println("Erreur mot de passe");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return connection;
    }
    public static void connexion () {
        con = Connexion.getConnection(login, mdp, bd);
    }

    public static Connection getCon() {
        return con;
    }

    public static void setBd(String bdnew) {
        bd = bdnew;
    }
}
