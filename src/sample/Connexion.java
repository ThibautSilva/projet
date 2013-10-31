package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: pc thomas
 * Date: 30/10/13
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */
public class Connexion implements Initializable {
    @FXML private static TextField loginField;
    @FXML private static TextField mdpField;
    @FXML private Button bt_valider;
    public static String bd = "";
    public static Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){ //
        bt_valider.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public  void handle(ActionEvent e) {         //Quand on clique sur le bouton On ferme connexion et on ouvre base.fxml
                Connexion.connexion();
                Node source = (Node)  e.getSource();
                Stage stage  = (Stage) source.getScene().getWindow();
                stage.close();
                URL location = getClass().getResource("base.fxml");

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

                Parent root = null;
                try {
                    root = (Parent) fxmlLoader.load(location.openStream());
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                Base c =fxmlLoader.getController();
                c.bdlist = Base.listeTable();       // On liste les noms de la base de données dans bdlist
                c.setLesBd(c.bdlist);  //  On récupere bdlist pour le mettre en observable

                Stage popUp = new Stage();
                popUp.initModality(Modality.WINDOW_MODAL);
                popUp.initOwner(((Node)e.getSource()).getScene().getWindow());
                popUp.setScene(new Scene(root, 800,600));
                popUp.show();
            }
        });
    }
    public static Connection getConnection(String login, String mdp, String bd){ // Connexion à la base de données
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
    public static void connexion () {  //Lancement de la connexion avec les champs renseignés dans connexion.fxml
        con = Connexion.getConnection(loginField.getText(), mdpField.getText(), bd);
    }

    public static Connection getCon() { //On recupère la connexion
        return con;
    }

    public static void setBd(String bdnew) {   // On set le nom de la BD
        bd = bdnew;
    }
}
