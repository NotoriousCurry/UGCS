/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs.Model;

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

    public Student(String zID, String fName, String lName, String course, String eMail) {
        this.zID = zID;
        this.fName = fName;
        this.lName = lName;
        this.course = course;
        this.eMail = eMail;
    }

    public String getzID() {
        return zID;
    }

    public void setzID(String zID) {
        this.zID = zID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    
    
    
}
