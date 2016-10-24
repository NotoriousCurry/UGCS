
package ugcs.Database;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Peter 
 *
 */
public class StudentCOOPSetup extends DerbySetup {

    ResultSet rs = null;
    PreparedStatement createStudentCOOPTable = null;

    public static void setupDatabase() {
        StudentCOOPSetup dbs = new StudentCOOPSetup();
        dbs.studentcoopSetup();
    }

    private void studentcoopSetup() {

        openConnection();

        try {

            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "STUDENTCOOP", null);

            if (!rs.next()) {
               
                String sqlText = "CREATE TABLE APP.STUDENT("
                        + "\"ZID\" VARCHAR(8) not null primary key, "
                        + "\"FIRSTNAME\"VARCHAR(50),"
                        + "\"LASTNAME\" VARCHAR(50),"
                        + "\"GENDER\" CHAR,"
                        + "\"ADDRESS\" VARCHAR(100),"
                        + "\"CONTACT\" INT,"                       
                        + "\"EMAIL\" VARCHAR(50),"
                        + "\"WORKEMAIL\" VARCHAR(50),"                                             
                        + "\"NOTES\" VARCHAR(5000),"   
                        + "\"SUBJECT\" VARCHAR(50),"                       
                        + "\"SEMESTERCOMPLETED\" INT,"                     
                        + "\"MARK\" INT,"   
                        + "\"WAM\" INT"
                        
                        + ")";

                System.out.println(sqlText);
                createStudentCOOPTable = conn.prepareStatement(
                        sqlText
                );
                createStudentCOOPTable.execute();
                System.out.println("StudentCOOP table created");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        closeConnection();
    }
}

