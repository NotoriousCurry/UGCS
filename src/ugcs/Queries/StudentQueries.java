/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ugcs.Model.Student;

/**
 *
 * @author sgaheer
 */
public class StudentQueries extends ugcs.Database.DerbySetup {

    PreparedStatement getAllStudent = null;
    PreparedStatement insertStudent = null;
    PreparedStatement updateStudent = null;
    PreparedStatement deleteStudent = null;

    ResultSet rs = null;

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<Student>();
        openConnection();
        try {
            getAllStudent = conn.prepareStatement("select * from APP.STUDENT");
            rs = getAllStudent.executeQuery();
            while (rs.next()) {
                students.add(
                        new Student(rs.getString("ZID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
                                rs.getString("COURSE"), rs.getString("EMAIL"))
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
            insertStudent = conn.prepareStatement("insert into APP.STUDENT (ZID, FIRSTNAME, LASTNAME, COURSE, EMAIL) "
                    + "values (?, ?, ?, ?, ?)");
            insertStudent.setString(1, toInsert.getzID());
            insertStudent.setString(2, toInsert.getfName());
            insertStudent.setString(3, toInsert.getlName());
            insertStudent.setString(4, toInsert.getCourse());
            insertStudent.setString(5, toInsert.geteMail());
            insertStudent.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

    // Assumes we cannot change zID without deleting and recreating user, prevents accidental changes to zID
    public void updateStudents(Student toUpdate) {
        openConnection();
        try {
            updateStudent = conn.prepareStatement("update APP.STUDENT set FIRSTNAME=?, LASTNAME=?, COURSE=?, EMAIL=?");
            updateStudent.setString(1, toUpdate.getfName());
            updateStudent.setString(2, toUpdate.getlName());
            updateStudent.setString(3, toUpdate.getCourse());
            updateStudent.setString(4, toUpdate.geteMail());

            updateStudent.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

    public void deleteStudent(Student toDelete) {
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
