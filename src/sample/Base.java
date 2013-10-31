package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: pc thomas
 * Date: 30/10/13
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */
public class Base implements Initializable {
    @FXML ListView list_bd;
    @FXML ListView list_table;
    @FXML Button bt_valid;
    private ObservableList<String> lesTablesObserves = FXCollections.observableArrayList();
    private ObservableList<String> lesBdObserves = FXCollections.observableArrayList();
    public ArrayList<String> tablelist = new ArrayList();
    public ArrayList<String> bdlist = new ArrayList();
    public static Connection con = Connexion.getCon();
    public String bdselected;
    public String tableselected;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        list_bd.setItems(lesBdObserves);
        list_table.setItems(lesTablesObserves);
        bt_valid.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public  void handle(ActionEvent e) {
                URL location = getClass().getResource("Champ.fxml");

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

                Parent root = null;
                try {
                    root = (Parent) fxmlLoader.load(location.openStream());
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                //Parent root = FXMLLOADER.load(getClass().getResource("selectBDD.fxml"));
                ChampValeur c =fxmlLoader.getController();
                c.setTableSelected(tableselected);
                c.setBdSelected(bdselected);
                c.getValeurTable();


               /* c.setSelected(selected);
                c.setListeJoueur(listeJoueursObserves);    */


                Stage popUp = new Stage();
                popUp.initModality(Modality.WINDOW_MODAL);
                popUp.initOwner(((Node)e.getSource()).getScene().getWindow());
                popUp.setScene(new Scene(root, 800,600));
                popUp.show();

            }
        });
        list_bd.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                bdselected = (String) newValue;
                System.out.println("OK" + bdselected);
                tablelist = getTable(bdselected);
                setLesTables(tablelist);
                /*setLesJoueurs(); */
                /*
                URL location = getClass().getResource("create_joueur.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                ControllerCreateJoueur c = fxmlLoader.getController();
                c.setSelected(selected);
                */
            }
        });
        list_table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                tableselected = (String) newValue;
                System.out.println("OK" + tableselected);
                /*setLesJoueurs(); */
                /*
                URL location = getClass().getResource("create_joueur.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                ControllerCreateJoueur c = fxmlLoader.getController();
                c.setSelected(selected);
                */
            }
        });
    }


    public static ArrayList listeTable (){
        ArrayList<String> list = new ArrayList<String>();
        try {
            DatabaseMetaData meta = con.getMetaData();
            ResultSet res = meta.getCatalogs();
            while (res.next()) {
                list.add(res.getString("TABLE_CAT"));
            }
            res.close();

            con.close();
        } catch (Exception e) {
            System.err.println("ClassNotFoundException: "
                    +e.getMessage());
        }
        return list;
    }
    public static ArrayList getTable(String bd){
        ArrayList<String> list = new ArrayList();
        Connexion.setBd(bd);
        Connexion.connexion();
        con = Connexion.getCon();
        try {
            //on récupère les métadonnées à partir de la connexion
            DatabaseMetaData dmd = con.getMetaData();
            //récupération des informations
            ResultSet tables = dmd.getTables(con.getCatalog(),null,"%",null);
            //affichage des informations
            while(tables.next()){
                 String tableName = tables.getString("TABLE_NAME" );
                list.add(tableName);
            }
        }catch (Exception e ){
        }
        return list;
    }

    public void setLesBd(ArrayList<String> liste) {
        this.bdlist = liste;
        for(String bd  : bdlist){
            this.lesBdObserves.add(bd);
        }
    }

    public void setLesTables(ArrayList<String> liste) {
        this.tablelist = liste;
        this.lesTablesObserves.clear();
        for(String bd  : tablelist){
            this.lesTablesObserves.add(bd);
        }
    }
}

