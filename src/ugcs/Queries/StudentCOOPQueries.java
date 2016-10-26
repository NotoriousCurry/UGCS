/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs.Queries;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ugcs.Model.StudentCOOP;

/**
 *
 * @author sgaheer
 */
public class StudentCOOPQueries extends ugcs.Database.DerbySetup {

    PreparedStatement getAllStudent = null;
    PreparedStatement insertStudent;
    PreparedStatement updateStudent = null;
    PreparedStatement deleteStudent = null;

    ResultSet rs = null;
    
/*String zID, String fName, String lName, String gEnder, String aDdress, 
              Integer cOntact, String eMail, String wOrkemail, String nOtes,
            String sUbject, Integer sEmestercompleted, Integer mArk, Integer wAm */
                    
    public List<StudentCOOP> getStudentCOOP() {
        List<StudentCOOP> studentco = new ArrayList<StudentCOOP>();
        openConnection();
        try {
            getAllStudent = conn.prepareStatement("select * from APP.STUDENTCOOP");
            rs = getAllStudent.executeQuery();
            while (rs.next()) {
                studentco.add(
                        new StudentCOOP(rs.getString("ZID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
                                rs.getString("GENDER"), rs.getString("ADDRESS"), rs.getInt("CONTACT"), rs.getString("EMAIL"),
                        rs.getString("WORKEMAIL"), rs.getString("NOTES"), rs.getString("SUBJECT"), 
                        rs.getInt("SEMESTERCOMPLETED"), rs.getDouble("MARK"), rs.getDouble("WAM"))
                );
            }
            rs.close();
            getAllStudent.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
        return studentco;
    }
   
     public void insertStudentCOOP(StudentCOOP toInsert) {
        openConnection();
        try {
            insertStudent = conn.prepareStatement("insert into APP.STUDENTCOOP (ZID, FIRSTNAME, LASTNAME, "
                    + "GENDER, ADDRESS, CONTACT, EMAIL, WORKEMAIL, NOTES, SUBJECT, SEMESTERCOMPLETED, MARK, WAM) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertStudent.setString(1, toInsert.getZID());
            insertStudent.setString(2, toInsert.getFName());
            insertStudent.setString(3, toInsert.getLName());
            insertStudent.setString(4, toInsert.getGEnder());
            insertStudent.setString(5, toInsert.getADdress());
            insertStudent.setInt(6, toInsert.getCOntact());
            insertStudent.setString(7, toInsert.getEMail());
            insertStudent.setString(8, toInsert.getWOrkemail());
            insertStudent.setString(9, toInsert.getNOtes());
            insertStudent.setString(10, toInsert.getSUbject());
            insertStudent.setInt(11, toInsert.getSEmestercompleted());
            insertStudent.setDouble(12, toInsert.getMArk());
            insertStudent.setDouble(13, toInsert.getWAm());
            
            insertStudent.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }
                                                                                                               
                                                     
    // Assumes we cannot change zID without deleting and recreating user, prevents accidental changes to zID
    public void updateStudentCOOP(StudentCOOP toUpdate) {
        openConnection();
        try {
            updateStudent = conn.prepareStatement("update app.studentcoop set firstname=?, lastname=?, gender=?, address=?,"
                    + " contact=?, email=?, workemail=?, notes=?, subject=?, semestercompleted=?, mark=?, wam=?");
            
          updateStudent.setString(1, toUpdate.getZID());
            updateStudent.setString(1, toUpdate.getFName());
            updateStudent.setString(2, toUpdate.getLName());
            updateStudent.setString(3, toUpdate.getGEnder());
            updateStudent.setString(4, toUpdate.getADdress());
            updateStudent.setInt(5, toUpdate.getCOntact());
            updateStudent.setString(6, toUpdate.getEMail());
            updateStudent.setString(7, toUpdate.getWOrkemail());
            updateStudent.setString(8, toUpdate.getNOtes());
            updateStudent.setString(9, toUpdate.getSUbject());
            updateStudent.setInt(10, toUpdate.getSEmestercompleted());
            updateStudent.setDouble(11, toUpdate.getMArk());
            updateStudent.setDouble(12, toUpdate.getWAm());

            updateStudent.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

    public void deleteStudent(StudentCOOP toDelete) {
        openConnection();
        try {
            deleteStudent = conn.prepareStatement("delete from APP.STUDENTCOOP where ZID = ?");
            deleteStudent.setString(1, toDelete.getZID());

            deleteStudent.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

   
}
