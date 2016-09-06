
package ugcs.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ugcs.Database.DerbySetup;
import ugcs.Model.Consultation;

/**
 *
 * @author Peter 
 */
public class ConsultationQueries extends DerbySetup {

    PreparedStatement deleteConsult = null;
    PreparedStatement insertConsult = null;
    PreparedStatement getAllConsult = null;
    PreparedStatement updateConsult= null;
    ResultSet rs = null;

    public void deleteConsult(Consultation toDelete) {
        openConnection();
        try {
            deleteConsult = conn.prepareStatement("delete from app.Consultation where ID = ?");
            deleteConsult.setInt(1, toDelete.getconsultationid());

            deleteConsult.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

    public void updateConsult(Consultation toUpdate) {
        openConnection();
        try {
            updateConsult = conn.prepareStatement("update app.Consultation set notes=?, type=?, priority=?, date=?, time=?");
            updateConsult.setString(1, toUpdate.getnotes());
            updateConsult.setString(2, toUpdate.gettype());
            updateConsult.setString(3, toUpdate.getpriority());
            updateConsult.setString(4, toUpdate.getdate());
            updateConsult.setString(5, toUpdate.gettime());
            

            updateConsult.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

    public List<Consultation> getConsultations() {
        List<Consultation> Consultations = new ArrayList<Consultation>();
        openConnection();
        try {
            getAllConsult = conn.prepareStatement("select * from app.CONSULTATION");
            rs = getAllConsult.executeQuery();
            while (rs.next()) {
                Consultations.add(
                        new Consultation(rs.getString("ZID"), rs.getString("notes"), rs.getString("type"), rs.getString("priority"), rs.getString("date"), rs.getString("time"))
                );
            }
            rs.close();
            getAllConsult.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
        return Consultations;
    }
//
    public void insertConsult(Consultation toInsert) {

        openConnection();
        try {

            insertConsult = conn.prepareStatement("insert into app.Consultation ( zid, notes, type, priority, date, time) values (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insertConsult.setString(1, toInsert.getzid());

            insertConsult.setString(2, toInsert.getnotes());
            insertConsult.setString(3, toInsert.gettype());
            insertConsult.setString(4, toInsert.getpriority());
            insertConsult.setString(5, toInsert.getdate());
            insertConsult.setString(6, toInsert.gettime());
            insertConsult.executeUpdate();

            ResultSet rs = insertConsult.getGeneratedKeys();
            rs.next();

            rs.close();
            insertConsult.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }
}