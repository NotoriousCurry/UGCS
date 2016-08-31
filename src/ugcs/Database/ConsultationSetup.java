
package ugcs.Database;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Peter 
 *
 */
public class ConsultationSetup extends DerbySetup {

    ResultSet rs = null;
    PreparedStatement createConsultationTable = null;

    public static void setupDatabase() {
        ConsultationSetup dbs = new ConsultationSetup();
        dbs.consultationSetup();
    }

    private void consultationSetup() {

        openConnection();

        try {

            // Determine if the ITEM table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "CONSULTATION", null);

            if (!rs.next()) {
               
                String sqlText = "CREATE TABLE APP.CONSULTATION("
                        + "\"CONSULTATIONID\" INT not null primary key"   
                        + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "\"ZID\" VARCHAR(8),"
                        + "\"NOTES\"VARCHAR(500),"
                        + "\"TYPE\" VARCHAR(10),"
                        + "\"PRIORITY\"VARCHAR(10),"                     
                        + "\"DATE\" VARCHAR(12),"
                        + "\"TIME\" VARCHAR(12)"
                        + ")";

                System.out.println(sqlText);
                createConsultationTable = conn.prepareStatement(
                        sqlText
                );
                createConsultationTable.execute();
                System.out.println("Consultation table created");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        closeConnection();
    }
}
