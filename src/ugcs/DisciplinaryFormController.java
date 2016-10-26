/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import ugcs.Audio.AudioMethods;
import ugcs.Queries.ConsultationQueries;
import ugcs.Model.Consultation;
import ugcs.Model.Student;
import ugcs.Queries.StudentFollowQueries;
import ugcs.Queries.StudentQueries;

/**
 * FXML Controller class
 *
 * @author Peterrpancakes
 */
public class DisciplinaryFormController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private @FXML
    Button upload;
    private @FXML
    Button updatebutton;
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
    Button createButton;
    private @FXML
    Button cancelButton;
    private @FXML
    Button backHome;
    private @FXML
    CheckBox staffcheck;
    private @FXML
    Button rDash;
    private @FXML
    Button rPrev;
    @FXML
    private ImageView imageaa;

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
    /*
     public void staffcheck(ActionEvent event){
     Boolean check = true;
     StudentQueries sq = new StudentQueries();
     ObservableList<Student> slist = FXCollections.observableList(sq.getStudents());
     StudentFollowQueries sfq = new StudentFollowQueries();

     for(Student s : slist){
     if(s.getZID().equals(zId)){
     if (staffcheck.isSelected() == check){
     sfq.insertStudents(s);
     }
     else{
     check = false;
     sfq.deleteStudent(s);
              
     }    
     }
     }
     }
     */

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

                        System.out.println("bytes = " + aa);
                        ByteArrayInputStream in = new ByteArrayInputStream(aa);

                        BufferedImage read = ImageIO.read(in); //returns null
                        System.out.println("setting image");
                        System.out.println(" in = " + in);
                        System.out.println("read = " + ImageIO.read(in));

                        imageaa.setImage(SwingFXUtils.toFXImage(read, null));
                        System.out.println("should be set?");
                        System.out.println("attempting read");

                        data.close();
                        conn.close();

                        rs.close();
                        pstmt.close();
                        conn.close();
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

    public void Viewlabel1(MouseEvent event) {
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
    StudentQueries sq = new StudentQueries();
    ObservableList<Student> slist = FXCollections.observableList(sq.getStudents());
    ConsultationQueries cq = new ConsultationQueries();
    ObservableList<Consultation> clist = FXCollections.observableList(cq.getConsultations());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //IF the staffcheck is PREVIOUSLY checked, then it should be checked again
          StudentFollowQueries sfq = new StudentFollowQueries();

            ObservableList<Student> sflist = FXCollections.observableList(sfq.getStudents());
            for (Student sf : sflist) {
                if (sf.getZID().equals(zId.getText())) {

                    System.out.println("already exists");
                    staffcheck.setSelected(true);
                } else {
staffcheck.setSelected(false);
                  }}
            // initialize page if its opened via an edit button                 
                            
        ConsultationQueries cqq = new ConsultationQueries();
        ObservableList<Consultation> clist2 = FXCollections.observableList(cqq.getConsultations());
        if (StudentAndConsController.getExists() == "true") {
            for (Consultation cw : clist) {
                String i = String.valueOf(StudentAndConsController.getselectedCID());
                if (cw.getConsultationid().toString().equals(i)) {
                    for (Student sw2 : slist) {
                        if (sw2.getZID().equals(cw.getZid())) {
                            String fname1 = sw2.getFName();
                            String lname1 = sw2.getLName();
                            String email1 = sw2.getEMail();
                            String course1 = sw2.getCourse();
                            zId.setText(cw.getZid());
                            fName.setText(fname1);
                            lName.setText(lname1);
                            eMail.setText(email1);
                            course.setText(course1);
                            
                            break;
                        }

                    }
                }
            }
        } else {            
//intialize page if its opened via the create consultation screen.

            for (Student sw : slist) {
                if (sw.getZID().equals(StudentAndConsController.getselected())) {
                    String fname1 = sw.getFName();
                    String lname1 = sw.getLName();
                    String email1 = sw.getEMail();
                    String course1 = sw.getCourse();
                    zId.setText(sw.getZID());
                    fName.setText(fname1);
                    lName.setText(lname1);
                    eMail.setText(email1);
                    course.setText(course1);
                    break;
                }
            }
        }

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
                    if (blob2 != null) {
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
                            conn.close();
                            in.close();
                            rs.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    rs.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(VIEWIMAGEController.class.getName()).log(Level.SEVERE, null, ex);
                }
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

        viewlabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Viewlabel1(e);
            }
        });

        backHome.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                StudentAndConsController.setExists("false");
                handleTransitionButton(e, "asformS.png", "conS.png", "StudentAndCons.fxml", "Create Consultation");

            }
        });

        rDash.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                StudentAndConsController.setExists("false");
                handleTransitionButton(e, "asformS.png", "dashS.png", "Dashboard.fxml", "Create Consultation");

            }
        });

        rPrev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                StudentAndConsController.setExists("false");
                handleTransitionButton(e, "asformS.png", "conS.png", "StudentAndCons.fxml", "Create Consultation");

            }
        });

        if (StudentAndConsController.getExists().equals("true")) {
            for (Consultation cw : clist) {
                String i = String.valueOf(StudentAndConsController.getselectedCID());
                if (cw.getConsultationid().toString().equals(i)) {
                    System.out.println("hello");
                    String d = cw.getDate1();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//formatter = formatter.withLocale(Locale.ENGLISH);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
                    LocalDate date = LocalDate.parse(d, formatter);
                    datePicked.setValue(date);
                    String t = cw.getTime1();
                    timePicked.setValue(t);
                    priorityChoice.getSelectionModel().select(cw.getPriority());
                  //  priorityChoice.setValue(date);
                    notesField.setText(cw.getNotes());
                    updatebutton.setVisible(true);
                    break; //this mother fucking line stops its from freezing during tranisation

                }
            }
        }

    }

    public void UpdateNow(ActionEvent event) {
        ConsultationQueries cqq = new ConsultationQueries();
        ObservableList<Consultation> clist2 = FXCollections.observableList(cqq.getConsultations());
        StudentAndConsController.setExists("false");
 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Changes");
        alert.setHeaderText(null);
        alert.setContentText("Apply changes?");
          ButtonType buttonTypeyes = new ButtonType("Apply");
            ButtonType buttonTypecancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypecancel, buttonTypeyes);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeyes) {
        for (Consultation cw : clist2) {
            String i = String.valueOf(StudentAndConsController.getselectedCID());
            if (cw.getConsultationid().toString().equals(i)) {
                Integer a1 = cw.getConsultationid();
                
                System.out.println("a1 = " + a1);
                String a2 = cw.getZid();
                String a3 = notesField.getText();

                String a5 = priorityChoice.getSelectionModel().getSelectedItem().toString();
                String a4 = cw.getType();
                LocalDate dateupdate = datePicked.getValue();
                Date dateupdate2 = localDateToUtilDate(dateupdate);
                SimpleDateFormat dateformatJava2 = new SimpleDateFormat("dd/MM/yyyy");
                String a6 = dateformatJava2.format(dateupdate2);
                String a7 = timePicked.getValue().toString();
                System.out.println("a7 time = " + cw.getTime1());

                Consultation cupdate = new Consultation(a1, a2, a3, a4, a5, a6, a7);
                cqq.updateConsult(cupdate);
                break;
            }
            
        }

        if (staffcheck.isSelected() == true) {
            System.out.println("true");

            StudentQueries sq = new StudentQueries();
            StudentFollowQueries sfq = new StudentFollowQueries();

            ObservableList<Student> sflist = FXCollections.observableList(sfq.getStudents());
            for (Student sf : sflist) {
                if (sf.getZID().equals(zId.getText())) {

                    System.out.println("already exists");
                } else {

                    for (Student s : slist) {

                        if (s.getZID().equals(zId.getText())) {
                            System.out.println("true2");

                            sfq.insertStudents(s);
                        }

                    }

                }
            }

        }gotoHome(event);
        }else{
            System.out.println("cancel");
        }
         
                
            
    }

    public Date localDateToUtilDate(LocalDate localDate) {
        GregorianCalendar cal = new GregorianCalendar(
                localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        java.util.Date date = cal.getTime();
        return date;
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
            System.out.println("date is " + date3 + "time is " + time);
            Consultation c = new Consultation(zid, notes, "Disciplinary Action", priority, date3, time);
            ConsultationQueries cq = new ConsultationQueries();

            cq.insertConsult(c);
            ObservableList<Consultation> cd = FXCollections.observableArrayList(cq.getConsultations());
            System.out.println(cq.getConsultations());
            //  System.out.println(cd.get(0).getDate1());
            //  System.out.println(cd.get(0).getTime1());
            // System.out.println(cd.get(0).getconsultationid());
            if (staffcheck.isSelected() == true) {
                System.out.println("true");

                StudentQueries sq = new StudentQueries();
                ObservableList<Student> slist = FXCollections.observableList(sq.getStudents());
                StudentFollowQueries sfq = new StudentFollowQueries();

                for (Student s : slist) {

                    if (s.getZID().equals(zId.getText())) {
                        System.out.println("true2");

                        sfq.insertStudents(s);
                    }

                }

            }
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
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {StudentAndConsController.setExists("false");
            Parent root = FXMLLoader.load(getClass().getResource("CalendarView.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Home Screen");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }
    }

    private void deleteTemp() {
        try {
            File file = new File("temp.txt");
            if (file.delete()) {
                System.out.println(file.getName() + " is Deleted");
            } else {
                System.out.println("Delete operation failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAudio(String file) {
        AudioMethods am = new AudioMethods();

        am.playAudio(file);
    }

    private void handleTransitionButton(ActionEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenButton(e, a, b, c, d);

    }

    private void handleTransitionKey(KeyEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenKey(e, a, b, c, d);
    }
}
