/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.imageio.ImageIO;
import ugcs.Model.Student;
import ugcs.Queries.StudentQueries;

/**
 * FXML Controller class
 *
 * @author Peterrpancakes
 */
public class VIEWIMAGEController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ImageView imageaa;
    @FXML
    AnchorPane ac;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imageaa.fitWidthProperty().bind(ac.heightProperty());
        imageaa.fitHeightProperty().bind(ac.widthProperty());
        StudentQueries sq = new StudentQueries();
        ObservableList<Student> slist = FXCollections.observableList(sq.getStudents());
        String zidselected = StudentAndConsController.getselected();

        for (Student sw : slist) {
            if (sw.getZID().equals(StudentAndConsController.getselected())) {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:derby:"
                            + System.getProperty("user.dir")
                            + System.getProperty("file.separator")
                            + "UCGDatabase;create=true");

                    ResultSet rs = null;
                    PreparedStatement pstmt = null;
                    String stringzid = sw.getZID();
                    String query = "SELECT * FROM APP.STUDENT WHERE ZID = " + "'" + stringzid + "'";
                    pstmt = conn.prepareStatement(query);
                    rs = pstmt.executeQuery();
                    rs.next();
                    Blob blob2 = rs.getBlob("TRANSCRIPT");
                    byte[] aa = blob2.getBytes(1, (int) blob2.length());
                    // BufferedImage bi = ImageIO.read(blob2.getBinaryStream());
                    //      System.out.println("bufferimage = " + bi);

                    System.out.println("bytes = " + aa);
                    ByteArrayInputStream in = new ByteArrayInputStream(aa);
                    // ImageReader rdr = ImageIO.getImageReadersByFormatName("png").next();
                    //Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(gg);
                    // ImageReader reader = rdr.next();
                    // ImageInputStream imageinput = ImageIO.createImageInputStream(in);
                    //rdr.setInput(imageinput);
                    //BufferedImage bi = rdr.read(0);
                    // in.close();

                    //    System.out.println("please be buffered image = " + bi);
                    // System.out.println("iter = " + rdr);
                    BufferedImage read;
                    try {
                        read = ImageIO.read(in); //returns null

                        System.out.println("setting image");
                        System.out.println(" in = " + in);
                        System.out.println("read = " + ImageIO.read(in));

                        imageaa.setImage(SwingFXUtils.toFXImage(read, null));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(VIEWIMAGEController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
