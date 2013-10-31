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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Thib'
 * Date: 30/10/13
 * Time: 23:33
 * To change this template use File | Settings | File Templates.
 */
public class ChampValeur implements Initializable {
    @FXML ListView valeursdestables;
    @FXML Button bt_valid;
    private ObservableList<Champ> ValeursDesTablesObserves = FXCollections.observableArrayList();
    private String tableselected;
    private String bdselected;
    private ArrayList<Champ> ValeurTable;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        valeursdestables.setItems(ValeursDesTablesObserves);
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
                /*c.setSelected(selected);
                c.getValeurTable(selected);   */


               /* c.setSelected(selected);
                c.setListeJoueur(listeJoueursObserves);    */


                Stage popUp = new Stage();
                popUp.initModality(Modality.WINDOW_MODAL);
                popUp.initOwner(((Node)e.getSource()).getScene().getWindow());
                popUp.setScene(new Scene(root, 800,600));
                popUp.show();

            }
        });
    }

    public void setTableSelected (String selected){
        this.tableselected = selected;
    }
    public void setBdSelected (String selected){
        this.bdselected = selected;
    }

    public void setLesValeursDesTables() {
        this.ValeurTable = Champ.getValeurTable();
        for(Champ c  : ValeurTable){
            this.ValeursDesTablesObserves.add(c);
        }
    }
    public void getValeurTable(){
        try {
            Connexion.setBd(bdselected);
            Connexion.connexion();
            Connection con = Connexion.getCon();
            DatabaseMetaData dmd = Base.con.getMetaData();


//récupération des informations
       /* ResultSet resultat = dmd.getColumns(Base.con.getCatalog(),null,tableselected, "%"); */

//affichage des informations
        /*ResultSetMetaData rsmd = resultat.getMetaData();
        while(resultat.next()){
            for(int i=0; i<rsmd.getColumnCount(); i++){
                Champ c = new Champ();
                c.setNom(rsmd.getColumnName(i+1));
            }
        }  */
            ResultSet columns = dmd.getColumns(null, null, tableselected, null);
            int i = 0;
            while (columns.next())
            {
                Champ c = new Champ();
                c.setNom(columns.getString("COLUMN_NAME"));
                System.out.println(columns.getString("COLUMN_NAME"));
                i++;
            }
            setLesValeursDesTables();
        }catch (SQLException E) {
        }
    }

}

