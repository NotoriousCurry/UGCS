/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Peter & Sam
 */
public class UGCSetup extends DerbySetup {

    PreparedStatement createUGCTable = null;
    private Statement stmt = null;
    ResultSet rs = null;

    public static void setupDatabase() {
        UGCSetup dbs = new UGCSetup();
        dbs.ugcSetup();

    }

    private void ugcSetup() {
        boolean requiresdata = false;
        openConnection();
//looks into superclass without need to name the actual class
        
        try {

            
            DatabaseMetaData dbmd = conn.getMetaData();//getting metadata
            rs = dbmd.getTables(null, "APP", "UGC", null);//get all the table where the table names are called app, employee

            if (!rs.next()) {
                requiresdata = true;
               
                String sqlText = "CREATE TABLE APP.UGC ("
                        + "\"ZID\" VARCHAR(8) primary key, "
                        + "\"PASSWORD\" VARCHAR(100),"
                        + "\"FIRSTNAME\" VARCHAR(12),"
                        + "\"LASTNAME\" VARCHAR(12)"
                        + ")";

                System.out.println(sqlText);
                createUGCTable = conn.prepareStatement(
                        sqlText
                );//next column with the data type
                createUGCTable.execute();
                System.out.println("UGC table created");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();//executes when there is an error in the statement
        }

        closeConnection();

        if (requiresdata) {
            insertExistingUGC();
        }
    }

    public void insertExistingUGC() {

        openConnection();
        try {
            System.out.println("Beginning insert");
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO APP.UGC(ZID, PASSWORD, FIRSTNAME, LASTNAME)" + 
                    "VALUES ('z1234567', 'ugc', 'Micheal','Cahalane')");
            System.out.println("UGC inserted.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }
}
