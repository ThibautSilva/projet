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
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        Controller.listeTable();
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir le nom de la base :");
        String bd = sc.nextLine();
        Controller.getTable(bd);

    } */
public class Main {
    public static void main(String [] args) {
       // launch(args);
        Controller.listeTable();
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir le nom de la base :");
        String bd = sc.nextLine();
        ArrayList tablelist = Controller.getTable(bd);
        for(int i=0;i<tablelist.size();i++ )
        {

        }
    }
}
