package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

//public class Main extends Application {

   /* @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("connexion.fxml"));
            primaryStage.setTitle("Connexion");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    } */
public class Main {
    public static void main(String [] args) {
       // launch(args);
        Connexion.connexion();
        Base.listeTable();
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir le nom de la base :");
        String bd = sc.nextLine();
        ArrayList<String> tablelist = Base.getTable(bd);
        for(String elem: tablelist)
        {
            System.out.println (elem);
        }
    }
}
