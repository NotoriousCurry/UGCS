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
public class UGC {
    private String zID;
    private String password;
    private String fname;
    private String lname;

    public UGC(String zID, String password, String fname, String lname) {
        this.zID = zID;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
    }

    public String getzID() {
        return zID;
    }

    public void setzID(String zID) {
        this.zID = zID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
    
    
}
