package sample;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: pc thomas
 * Date: 30/10/13
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */
public class Base{
    public ArrayList tablelist = new ArrayList();
    public static Connection con = Connexion.getCon();
    public static void listeTable (){
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
}

