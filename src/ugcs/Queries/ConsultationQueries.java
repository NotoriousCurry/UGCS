
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
            deleteConsult.setInt(1, toDelete.getConsultationid());

            deleteConsult.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

    public void updateConsult(Consultation toUpdate) {
        openConnection();
        try {
            updateConsult = conn.prepareStatement("update app.Consultation set notes=?, type=?, priority=?, date1=?, time1=?");
            updateConsult.setString(1, toUpdate.getNotes());
            updateConsult.setString(2, toUpdate.getType());
            updateConsult.setString(3, toUpdate.getPriority());
            updateConsult.setString(4, toUpdate.getDate1());
            updateConsult.setString(5, toUpdate.getTime1());
            

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
                        new Consultation(rs.getInt("consultationid"), rs.getString("Zid"), rs.getString("notes"), rs.getString("type"), rs.getString("priority"), rs.getString("date1"), rs.getString("time1"))
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

            insertConsult = conn.prepareStatement("insert into app.Consultation (zid, notes, type, priority, date1, time1) values (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insertConsult.setString(1, toInsert.getZid());
            insertConsult.setString(2, toInsert.getNotes());
            insertConsult.setString(3, toInsert.getType());
            insertConsult.setString(4, toInsert.getPriority());
            insertConsult.setString(5, toInsert.getDate1());
            insertConsult.setString(6, toInsert.getTime1());
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