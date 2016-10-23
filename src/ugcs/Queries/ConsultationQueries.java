package ugcs.Queries;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    PreparedStatement updateConsult = null;
    ResultSet rs = null;

    public void deleteConsult(Consultation toDelete) {
        openConnection();
        try {
            deleteConsult = conn.prepareStatement("delete from app.Consultation where CONSULTATIONID = ?");
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
            updateConsult = conn.prepareStatement("update app.Consultation set zid=?, notes=?, type=?, priority=?, date1=?, time1=? where consultationid=?");
            updateConsult.setString(1, toUpdate.getZid());
            updateConsult.setString(2, toUpdate.getNotes());
            updateConsult.setString(3, toUpdate.getType());
            updateConsult.setString(4, toUpdate.getPriority());
            updateConsult.setString(5, toUpdate.getDate1());
            updateConsult.setString(6, toUpdate.getTime1());
            updateConsult.setInt(7, toUpdate.getConsultationid());

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
            String key = readOwner();
            getAllConsult = conn.prepareStatement("select * from app.CONSULTATION where owner=?");
            getAllConsult.setString(1, key);
            rs = getAllConsult.executeQuery();
            while (rs.next()) {
                Consultations.add(
                        new Consultation(rs.getInt("consultationid"), rs.getString("Zid"), rs.getString("notes"), rs.getString("type"), rs.getString("priority"), rs.getString("date1"), rs.getString("time1"), rs.getString("owner"))
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

            String key = readOwner();
            insertConsult = conn.prepareStatement("insert into app.Consultation (zid, notes, type, priority, date1, time1, owner) values (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insertConsult.setString(1, toInsert.getZid());
            insertConsult.setString(2, toInsert.getNotes());
            insertConsult.setString(3, toInsert.getType());
            insertConsult.setString(4, toInsert.getPriority());
            insertConsult.setString(5, toInsert.getDate1());
            insertConsult.setString(6, toInsert.getTime1());
            insertConsult.setString(7, key);
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

    private String readOwner() {
        String fName = "temp.txt";
        String line = null;
        String key = "";

        try {
            FileReader fReader = new FileReader(fName);
            BufferedReader bReader = new BufferedReader(fReader);

            line = bReader.readLine();
            String[] sect = line.split(",");
            key = sect[1];

            bReader.close();
            System.out.println(key);

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to find file");
        } catch (IOException ex) {
            System.out.println("Error Reading File");
        }
        return key;
    }
}
