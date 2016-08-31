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

/**
 *
 * @author sgaheer
 */
public class ugcQueries extends ugcs.Database.DerbySetup {
    PreparedStatement getUsername = null;
    PreparedStatement getPassword = null;
    
    ResultSet rs = null;
    
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
}
