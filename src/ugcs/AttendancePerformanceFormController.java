/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import ugcs.Queries.ConsultationQueries;
import ugcs.Model.Consultation;
import ugcs.Model.Student;
import ugcs.Queries.StudentQueries;

/**
 * FXML Controller class
 *
 * @author Peterrpancakes
 */
public class AttendancePerformanceFormController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private @FXML
    Label viewlabel;
    private @FXML
    TextField zId;
    private @FXML
    TextField fName;
    private @FXML
    TextField lName;
    private @FXML
    TextField eMail;
    private @FXML
    TextField course;
    private @FXML
    TextArea notesField;
    private @FXML
    DatePicker datePicked;
    private @FXML
    ComboBox timePicked;
    private @FXML
    ComboBox priorityChoice;
    private @FXML
    Button upload;

    private @FXML
    Button createButton;
    private @FXML
    Button cancelButton;
    private @FXML
    Button backHome;
    private @FXML
    CheckBox staffcheck;
    @FXML
    ImageView imageaa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

        viewlabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Viewlabel(e);
            }
        });

        System.out.println(zidselected);
        zId.setText(zidselected);
        for (Student sw : slist) {
            if (sw.getZID().equals(StudentAndConsController.getselected())) {
                String fname1 = sw.getFName();
                String lname1 = sw.getLName();
                String email1 = sw.getEMail();
                String course1 = sw.getCourse();
                fName.setText(fname1);
                lName.setText(lname1);
                eMail.setText(email1);
                course.setText(course1);
                /* 
                Blob blob = sw.getTRanscript();
                System.out.println(blob);
                try {
                    ImageIcon icon = new ImageIcon(
                            blob.getBytes(1, (int)blob.length()));
                    BufferedImage bi = new BufferedImage(
    icon.getIconWidth(),
    icon.getIconHeight(),
    BufferedImage.TYPE_INT_RGB);
Graphics g = bi.createGraphics();
// paint the Icon to the BufferedImage.
icon.paintIcon(null, g, 0,0);
g.dispose();
Image image1 = SwingFXUtils.toFXImage(bi, null);
                    imageaa.setImage(image1);
                } catch (SQLException ex) {
ex.printStackTrace();                }*/
            }

        }

        timePicked.getItems().addAll("9am", "10am",
                "11am",
                "12pm",
                "1pm",
                "2pm",
                "3pm",
                "4pm");

        priorityChoice.getItems().addAll("High", "Medium", "Low");

        backHome.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gotoHome(e);
            }
        });
    }

    public Date localDateToUtilDate(LocalDate localDate) {
        GregorianCalendar cal = new GregorianCalendar(
                localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        java.util.Date date = cal.getTime();
        return date;
    }
    Stage stage;
    Parent newroot;

    public void Viewlabel(MouseEvent event) {
        try {
            stage = new Stage();

            newroot = FXMLLoader.load(getClass().getResource("VIEWIMAGE.fxml"));

            Scene scene = new Scene(newroot);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(viewlabel.getScene().getWindow());

            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void Upload(ActionEvent event) {

        FileChooser file1 = new FileChooser();
        file1.setTitle("Upload Student Transcript");
        file1.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        file1.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        file1.setTitle("Save Image");

        File file = file1.showOpenDialog(stage);
        System.out.println("file is " + file);
        String path = file.getAbsolutePath();
        StudentQueries sq = new StudentQueries();
        ObservableList<Student> slist2 = FXCollections.observableList(sq.getStudents());
        for (Student sww : slist2) {
            if (sww.getZID().equals(StudentAndConsController.getselected())) {

                try {
                    Image image = null;
                    System.out.println("name is " + sww.getFName());

                    InputStream data = new BufferedInputStream(new FileInputStream(file));
//image = ImageIO.read(data);

                    // BufferedImage bufferedImage;
                    //bufferedImage = ImageIO.read(file);
                    //ImageIcon ii = new ImageIcon(bufferedImage);
//Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    //   InputStream inputStream = new FileInputStream(file);
                    //        System.out.println("input stream = " +inputStream);
                    StudentQueries sqq = new StudentQueries();
                    try {

                        Connection conn = DriverManager.getConnection("jdbc:derby:"
                                + System.getProperty("user.dir")
                                + System.getProperty("file.separator")
                                + "UCGDatabase;create=true");

                        PreparedStatement updateStudent = conn.prepareStatement("update APP.STUDENT set FIRSTNAME=?, LASTNAME=?, COURSE=?, EMAIL=? , TRANSCRIPT=? where "
                                + "ZID = " + "'" + sww.getZID() + "'");

                        updateStudent.setString(1, sww.getFName());
                        updateStudent.setString(2, sww.getLName());
                        updateStudent.setString(3, sww.getCourse());
                        updateStudent.setString(4, sww.getEMail());
                        //updateStudent.setBlob(5, sww.getTRanscript());

                        updateStudent.setBinaryStream(5, data, (int) file.length());
                        /*!!!!!!!  Blob blob = conn.createBlob();
         ObjectOutputStream oos;
    oos = new ObjectOutputStream(blob.setBinaryStream(1));
    oos.writeObject(imageion);
    oos.close();
    updateStudent.setBlob(5, blob); !!!!!!*/

                        // updateStudent.setBinaryStream(5, inputStream, file.length());
                        //  ObjectOutputStream oos;
                        //try {
                        //   oos = new ObjectOutputStream(blob.setBinaryStream(1));
                        //  ImageIcon ii = new ImageIcon(bufferedImage);
                        //  oos.writeObject(ii);
                        // oos.close();
                        //} catch (IOException ex) {ex.printStackTrace();                    }
                        //updateStudent.setBlob(5, blob);
                        updateStudent.executeUpdate();
                        // blob.free();
                        System.out.println("1234");
//MOVING TO INITALISE as well.

                        ResultSet rs = null;
                        PreparedStatement pstmt = null;
                        String stringzid = sww.getZID();
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
                        BufferedImage read = ImageIO.read(in); //returns null
                        System.out.println("setting image");
                        System.out.println(" in = " + in);
                        System.out.println("read = " + ImageIO.read(in));

                        imageaa.setImage(SwingFXUtils.toFXImage(read, null));
                        System.out.println("should be set?");
                        System.out.println("attempting read");
                        /* THIS IS TO ACTUALLY OUTPUT THE FILE.
      File imagefile = new File("image.jpg");
            FileOutputStream fos = new FileOutputStream(imagefile);

               
           // InputStream is = rs.getBinaryStream("TRANSCRIPT");

 System.out.println("buffer thingo...");
        fos.write(blob2.getBytes(1, (int)blob2.length()));
            System.out.println("done");
      fos.close();
                         */
                        data.close();
                        conn.close();

//            System.out.println("blob is " + blob2.getBytes(1, (int) blob2.length()) + sqqq.getStudents().get(0).getFName() );
                        /* byte[] byte1 = null;
                byte1 = blob2.getBytes(1, (int) blob2.length());
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(byte1));
                Image image = SwingFXUtils.toFXImage(img, null);
               
                imageaa.setImage(img); */
                        rs.close();
                        pstmt.close();
                        conn.close();
                        //sqq.getstmt().setBinaryStream(6, inputStream, (int)(file.length()));
                        //sqq.updateStudents(sww.getFName(), sww.getLName(), sww.getCourse(), sww.getEMail(), sww.getTRanscript());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(AttendancePerformanceFormController.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println(file);
            }
        }
    }

    public void Create(ActionEvent event) {
        Boolean isComplete = false;
        String zid = "";
        String date3 = "";
        String time = "";
        String priority = "";
        String type = "";

        // TextField Erro Echecking
        if (zId.getText() != null && !zId.getText().isEmpty()) {
            zid = zId.getText();
            isComplete = true;
        } else {
            System.out.println("ZID GONE NIGGA");
            isComplete = false;
        }

        if (datePicked.getValue() != null) {
            LocalDate date1 = datePicked.getValue();
            Date date2 = localDateToUtilDate(date1);
            SimpleDateFormat dateformatJava2 = new SimpleDateFormat("dd/MM/yyyy");
            date3 = dateformatJava2.format(date2);
            isComplete = true;
        } else {
            System.out.println("DATE GONE NIGGA");
            isComplete = false;
        }
        //Comboboxes Error Checks
        if (timePicked.getSelectionModel().getSelectedItem() != null) {
            time = timePicked.getSelectionModel().getSelectedItem().toString();
            isComplete = true;
        } else {
            System.out.println("TIME GONE NIGGA");
            isComplete = false;
        }

        if (priorityChoice.getSelectionModel().getSelectedItem() != null) {
            priority = priorityChoice.getSelectionModel().getSelectedItem().toString();
            isComplete = true;
        } else {
            System.out.println("PRIORITY GONE NIGGA");
            isComplete = false;
        }

        /* if (typeChoice.getSelectionModel().getSelectedItem() != null) {
            type = typeChoice.getSelectionModel().getSelectedItem().toString();
            isComplete = true;
        } else {
            System.out.println("TYPE GONE NIGGA");
            isComplete = false;
        }
         */
        //  String firstname = firstnamefield.getText();
        //  String lastname = lastnamefield.getText();
        //  String email = emailfield.getText();  
        if (notesField.getText() != null && isComplete == true) {
            String notes = notesField.getText();
            Consultation c = new Consultation(zid, notes, "Attendance/Performance", priority, date3, time);
            ConsultationQueries cq = new ConsultationQueries();
            cq.insertConsult(c);
            ObservableList<Consultation> cd = FXCollections.observableArrayList(cq.getConsultations());
            System.out.println(cq.getConsultations());
            //  System.out.println(cd.get(0).getDate1());
            //  System.out.println(cd.get(0).getTime1());
            // System.out.println(cd.get(0).getconsultationid());
            //POP UP lol
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Consultation Created");
            alert.setHeaderText("Confirmation");
            alert.setContentText("You have created a new consultation!");
            ButtonType btnTypeOne = new ButtonType("OK");
            ButtonType btnTypeTwo = new ButtonType("Re-Create", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(btnTypeOne, btnTypeTwo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == btnTypeOne) {
                gotoHome(event);
            } else {

            }

        } else {
            String notes = "...";
            System.out.println("SOMETHING HAPPENED...");
        }
    }

    /* public void Cancel(ActionEvent event) {
        Stage stageedit = (Stage) cancelButton.getScene().getWindow();
        stageedit.close();

    }*/
    private void gotoHome(ActionEvent event) {
        Stage primaryStage = (Stage) backHome.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("calendarview.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Home Screen");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }
    }
}
