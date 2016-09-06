
package ugcs.Database;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Peter 
 */
public class UGCSetup extends DerbySetup {

    PreparedStatement createUGCTable = null;
    private Statement stmt = null;
    ResultSet rs = null;

    public static void setupDatabase() {
        UGCSetup dbs = new UGCSetup();
        dbs.ugcSetup();

    }

    // ANY CHANGES HERE MUST BE REFLECTED IN UGC.class and UgcQueries.class
    private void ugcSetup() {
        boolean requiresdata = false;
        openConnection();

        
        try {

            
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "UGC", null);

            if (!rs.next()) {
                requiresdata = true;
               
                String sqlText = "CREATE TABLE APP.UGC ("
                        + "\"ZID\" VARCHAR(8) not null primary key, "
                        + "\"PASSWORD\" VARCHAR(100),"
                        + "\"FIRSTNAME\" VARCHAR(50),"
                        + "\"LASTNAME\" VARCHAR(50),"
                        + "\"EMAIL\" VARCHAR(50)"
                        + ")";

                System.out.println(sqlText);
                createUGCTable = conn.prepareStatement(
                        sqlText
                );//next column with the data type
                createUGCTable.execute();
                System.out.println("UGC table created");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
