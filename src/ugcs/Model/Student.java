/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs.Model;

import java.io.BufferedInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import javax.mail.Flags.Flag;

/**
 *
 * @author sgaheer
 */
public class Student {

    private String zID;
    private String fName;
    private String lName;
    private String course;
    private String eMail;
    private Blob tRanscript;

    public Student(String zID, String fName, String lName, String course, String eMail, Blob tRanscript) {
        this.zID = zID;
        this.fName = fName;
        this.lName = lName;
        this.course = course;
        this.eMail = eMail;
    }

    public Blob getTRanscript() throws SQLException {
        return (Blob) tRanscript.getBinaryStream();
    }

    public void setTRanscript(Blob tRanscript) {
        this.tRanscript = tRanscript;
    }

    public String getZID() {
        return zID;
    }

    public void setZID(String zID) {
        this.zID = zID;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

}
