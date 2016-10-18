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
import java.util.logging.Level;
import java.util.logging.Logger;
import ugcs.Model.Student;

/**
 *
 * @author sgaheer
 */
public class StudentFollowQueries extends ugcs.Database.DerbySetup {

    PreparedStatement getAllStudent = null;
    PreparedStatement insertStudent;
    PreparedStatement updateStudent = null;
    PreparedStatement deleteStudent = null;

    ResultSet rs = null;

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<Student>();
        openConnection();
        try {
            getAllStudent = conn.prepareStatement("select * from APP.STUDENTFOLLOW");
            rs = getAllStudent.executeQuery();
            while (rs.next()) {
                students.add(
                        new Student(rs.getString("ZID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
                                rs.getString("COURSE"), rs.getString("EMAIL"), rs.getBlob("TRANSCRIPT"))
                );
            }
            rs.close();
            getAllStudent.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
        return students;
    }
   
     public void insertStudents(Student toInsert) {
        openConnection();
        try {
            insertStudent = conn.prepareStatement("insert into APP.STUDENTFOLLOW (ZID, FIRSTNAME, LASTNAME, COURSE, EMAIL, TRANSCRIPT) "
                    + "values (?, ?, ?, ?, ?, ?)");
            insertStudent.setString(1, toInsert.getZID());
            insertStudent.setString(2, toInsert.getFName());
            insertStudent.setString(3, toInsert.getLName());
            insertStudent.setString(4, toInsert.getCourse());
            insertStudent.setString(5, toInsert.getEMail());
            Blob blob = null;
           insertStudent.setBlob(6, blob);

            
            insertStudent.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

    // Assumes we cannot change zID without deleting and recreating user, prevents accidental changes to zID


    public void deleteStudent(Student toDelete) {
        openConnection();
        try {
            deleteStudent = conn.prepareStatement("delete from APP.STUDENTFOLLOW where ZID = ?");
            deleteStudent.setString(1, toDelete.getZID());

            deleteStudent.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

    public void updateStudents(String fName, String lName, String eMail, String course, Blob tRanscript) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
