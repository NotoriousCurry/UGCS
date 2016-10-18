
package ugcs.Database;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Peter 
 *
 */
public class StudentFollowSetup extends DerbySetup {

    ResultSet rs = null;
    PreparedStatement createConsultationTable = null;

    public static void setupDatabase() {
        StudentFollowSetup dbs = new StudentFollowSetup();
        dbs.studentfollowSetup();
    }

    private void studentfollowSetup() {

        openConnection();

        try {

            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "STUDENTFOLLOW", null);

            if (!rs.next()) {
               
                String sqlText = "CREATE TABLE APP.STUDENTFOLLOW("
                        + "\"ZID\" VARCHAR(8) not null primary key, "
                        + "\"FIRSTNAME\"VARCHAR(50),"
                        + "\"LASTNAME\" VARCHAR(50),"
                        + "\"COURSE\"VARCHAR(50),"                     
                        + "\"EMAIL\" VARCHAR(100),"   
                        + "\"TRANSCRIPT\" BLOB"
                        + ")";

                System.out.println(sqlText);
                createConsultationTable = conn.prepareStatement(
                        sqlText
                );
                createConsultationTable.execute();
                System.out.println("Studentfollow table created");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        closeConnection();
    }
}

