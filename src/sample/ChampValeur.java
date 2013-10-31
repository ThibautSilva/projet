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
    private ObservableList<String> ValeursDesTablesObserves = FXCollections.observableArrayList();
    private String tableselected;
    private String bdselected;
    public ArrayList<Champ> ValeurTable = new ArrayList<Champ>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        valeursdestables.setItems(ValeursDesTablesObserves);
        bt_valid.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public  void handle(ActionEvent e) {
                URL location = getClass().getResource("formulaire.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                Parent root = null;
                try {
                    root = (Parent) fxmlLoader.load(location.openStream());
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                ChampValeur c =fxmlLoader.getController();

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
        for(Champ c  : this.ValeurTable){
            this.ValeursDesTablesObserves.add(c.getNom());
        }
    }
    public void getValeurTable(){     //On recupere les valeurs de la table selectionné
        try {
            //Connexion.setBd(bdselected);         //Je crois que c'est optionnel
            Connexion.connexion();
            Connection con = Connexion.getCon();    //recupere la connexion
            DatabaseMetaData dmd = Base.con.getMetaData();
            ResultSet columns = dmd.getColumns(null, null, tableselected, null);  //On recupere les meta de la table selectionné
            int i = 0;
            while (columns.next())
            {
                Champ c = new Champ(columns.getString("COLUMN_NAME"));                     // On range dans un objet Champ le nom
                Champ.ajoutList(c);                                            //On met chaque objet dans la liste ValeurTable
                System.out.println(columns.getString("COLUMN_NAME"));
                i++;
            }
            setLesValeursDesTables();      // On range la list des noms dans une list observable
        }catch (SQLException E) {
        }
    }

}

