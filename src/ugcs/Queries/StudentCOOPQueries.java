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
                        rs.getInt("SEMESTERCOMPLETED"), rs.getInt("MARK"), rs.getInt("WAM"))
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
            insertStudent = conn.prepareStatement("insert into APP.STUDENT (ZID, FIRSTNAME, LASTNAME, "
                    + "GENDER, ADDRESS, CONTACT, EMAIL, WORKEMAIL, NOTES, SUBJECT, SEMESTERCOMPLETED, MARK, WAM) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertStudent.setString(1, toInsert.getzID());
            insertStudent.setString(2, toInsert.getfName());
            insertStudent.setString(3, toInsert.getlName());
            insertStudent.setString(4, toInsert.getgEnder());
            insertStudent.setString(5, toInsert.getaDdress());
            insertStudent.setInt(6, toInsert.getcOntact());
            insertStudent.setString(7, toInsert.geteMail());
            insertStudent.setString(8, toInsert.getwOrkemail());
            insertStudent.setString(9, toInsert.getnOtes());
            insertStudent.setString(10, toInsert.getsUbject());
            insertStudent.setInt(11, toInsert.getsEmestercompleted());
            insertStudent.setInt(12, toInsert.getmArk());
            insertStudent.setInt(13, toInsert.getwAm());
            
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
            updateStudent = conn.prepareStatement("update APP.STUDENT set FIRSTNAME=?, LASTNAME=?, GENDER=?, ADDRESS=? ,"
                    + " CONTACT=?, EMAIL=?, WORKEMAIL?, NOTES=?, SUBJECT =?, SEMESTERCOMPLETED=?, MARK=?, WAM=?");
            updateStudent.setString(1, toUpdate.getzID());
            updateStudent.setString(2, toUpdate.getfName());
            updateStudent.setString(3, toUpdate.getlName());
            updateStudent.setString(4, toUpdate.getgEnder());
            updateStudent.setString(5, toUpdate.getaDdress());
            updateStudent.setInt(6, toUpdate.getcOntact());
            updateStudent.setString(7, toUpdate.geteMail());
            updateStudent.setString(8, toUpdate.getwOrkemail());
            updateStudent.setString(9, toUpdate.getnOtes());
            updateStudent.setString(10, toUpdate.getsUbject());
            updateStudent.setInt(11, toUpdate.getsEmestercompleted());
            updateStudent.setInt(12, toUpdate.getmArk());
            updateStudent.setInt(13, toUpdate.getwAm());

            updateStudent.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

    public void deleteStudent(StudentCOOP toDelete) {
        openConnection();
        try {
            deleteStudent = conn.prepareStatement("delete from APP.STUDENT where ZID = ?");
            deleteStudent.setString(1, toDelete.getzID());

            deleteStudent.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

   
}
