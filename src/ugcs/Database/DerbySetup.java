
package ugcs.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Peter 
 */
public class DerbySetup {

    protected Connection conn;

    public void openConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:derby:"
                        + System.getProperty("user.dir")
                        + System.getProperty("file.separator")
                        + "UCGDatabase;create=true");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException ex) {
            System.out.println(ex);

        }
    }

}
