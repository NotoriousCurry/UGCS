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
import ugcs.Model.UGC;

/**
 *
 * @author sgaheer
 */
public class UgcQueries extends ugcs.Database.DerbySetup {

    PreparedStatement getUsername = null;
    PreparedStatement getPassword = null;
    PreparedStatement getAllUgc = null;
    PreparedStatement insertUgc = null;
    PreparedStatement updateUgc = null;
    PreparedStatement deleteUgc = null;

    ResultSet rs = null;

    public List<UGC> getUGC() {
        List<UGC> ugcs = new ArrayList<UGC>();
        openConnection();
        try {
            getAllUgc = conn.prepareStatement("select * from APP.UGC");
            rs = getAllUgc.executeQuery();
            while (rs.next()) {
                ugcs.add(
                        new UGC(rs.getString("ZID"), rs.getString("PASSWORD"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
                                rs.getString("EMAIL"))
                );
            }
            rs.close();
            getAllUgc.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
        return ugcs;
    }

    public List<String> getUser() {
        List<String> user = new ArrayList<String>();
        openConnection();
        try {
            getUsername = conn.prepareStatement("SELECT ZID FROM APP.UGC");
            rs = getUsername.executeQuery();
            while (rs.next()) {
                user.add(rs.getString("ZID"));
            }
            rs.close();
            getUsername.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
        return user;
    }

    public String getPassword(String username) {
        String pass = "";
        openConnection();
        try {
            getPassword = conn.prepareStatement("SELECT PASSWORD FROM APP.UGC WHERE ZID=?");
            getPassword.setString(1, username); // To fill int the ? value above
            rs = getPassword.executeQuery();
            while (rs.next()) {
                pass = rs.getString("PASSWORD");
            }
            rs.close();
            getPassword.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
        return pass;
    }
    
        public String getName(String username) {
        String name = "";
        openConnection();
        try {
            getPassword = conn.prepareStatement("SELECT FIRSTNAME FROM APP.UGC WHERE ZID=?");
            getPassword.setString(1, username); // To fill int the ? value above
            rs = getPassword.executeQuery();
            while (rs.next()) {
                name = rs.getString("FIRSTNAME");
            }
            rs.close();
            getPassword.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
        return name;
    }

    public void insertUgc(UGC toInsert) {
        openConnection();
        try {
            insertUgc = conn.prepareStatement("insert into APP.UGC (ZID, PASSWORD, FIRSTNAME, LASTNAME, EMAIL) "
                    + "values (?, ?, ?, ?, ?)");
            insertUgc.setString(1, toInsert.getzID());
            insertUgc.setString(2, toInsert.getPassword());
            insertUgc.setString(3, toInsert.getFname());
            insertUgc.setString(4, toInsert.getLname());
            insertUgc.setString(5, toInsert.geteMail());
            insertUgc.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

    // Assumes we cannot change zID without deleting and recreating user, prevents accidental changes to zID
    public void updateUgc(UGC toUpdate) {
        openConnection();
        try {
            updateUgc = conn.prepareStatement("update APP.UGC set PASSWORD=?, FIRSTNAME=?, LASTNAME=?, EMAIL=?");
            updateUgc.setString(1, toUpdate.getPassword());
            updateUgc.setString(2, toUpdate.getFname());
            updateUgc.setString(3, toUpdate.getLname());
            updateUgc.setString(4, toUpdate.geteMail());

            updateUgc.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }

    public void deleteUgc(UGC toDelete) {
        openConnection();
        try {
            deleteUgc = conn.prepareStatement("delete from APP.UGC where ZID = ?");
            deleteUgc.setString(1, toDelete.getzID());

            deleteUgc.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }
}
