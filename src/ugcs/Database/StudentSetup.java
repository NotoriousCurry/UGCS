
package ugcs.Database;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Peter 
 *
 */
public class StudentSetup extends DerbySetup {

    ResultSet rs = null;
    PreparedStatement createConsultationTable = null;

    public static void setupDatabase() {
        StudentSetup dbs = new StudentSetup();
        dbs.studentSetup();
    }

    private void studentSetup() {

        openConnection();

        try {

            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "STUDENT", null);

            if (!rs.next()) {
               
                String sqlText = "CREATE TABLE APP.STUDENT("
                        + "\"ZID\" VARCHAR(8) not null primary key, "
                        + "\"FIRSTNAME\"VARCHAR(50),"
                        + "\"LASTNAME\" VARCHAR(50),"
                        + "\"COURSE\"VARCHAR(50),"                     
                        + "\"EMAIL\" VARCHAR(100)"                  
                        + ")";

                System.out.println(sqlText);
                createConsultationTable = conn.prepareStatement(
                        sqlText
                );
                createConsultationTable.execute();
                System.out.println("Student table created");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        closeConnection();
    }
}
