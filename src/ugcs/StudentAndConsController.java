/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ugcs.Audio.AudioMethods;
import ugcs.Model.Student;
import ugcs.Queries.StudentQueries;

/**
 *
 * @author sgahe
 */
public class StudentAndConsController implements Initializable {

    @FXML
    private Button logOut;
    @FXML
    private Button viewbutton;
    @FXML
    private Button rPrev;

    private static String studentst;
    private static String f = "False";
    private static Integer i;
    private PathTransition pathT = new PathTransition();

    @FXML
    RadioButton adbox;
    @FXML
    RadioButton apbox;
    @FXML
    RadioButton cgbox;
    @FXML
    RadioButton cebox;
    @FXML
    RadioButton dpbox;
    @FXML
    RadioButton iebox;

    @FXML
    Button backHome;
    @FXML
    Button createButton;
    @FXML
    Button addStudentbutton;
    @FXML
    Label errorLabel;

    @FXML
    TextField zidtex;
    @FXML
    TextField fnametex;
    @FXML
    TextField lnametex;
    @FXML
    TextField coursetex;
    @FXML
    TextField emailtex;
    @FXML
    TableView<Student> studenttable;
    @FXML
    TableColumn zidcol;
    @FXML
    TableColumn fnamecol;
    @FXML
    TableColumn lnamecol;

    @FXML
    TableColumn coursecol;
    @FXML
    TableColumn emailcol;
    Stage stage;
    Parent newroot;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Resources/arrow4.png"))));
        createButton.resize(20, 20);
        StudentQueries ss = new StudentQueries();

        ObservableList<Student> studentlist = FXCollections.observableArrayList(ss.getStudents());

        zidcol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("zID")
        );
        fnamecol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("fName")
        );
        lnamecol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lName")
        );
        coursecol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("course")
        );
        emailcol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("eMail")
        );
        studenttable.setItems(null);
        studenttable.setItems(studentlist);

        ToggleGroup group = new ToggleGroup();
        adbox.setToggleGroup(group);
        apbox.setToggleGroup(group);
        cgbox.setToggleGroup(group);
        cebox.setToggleGroup(group);
        dpbox.setToggleGroup(group);
        iebox.setToggleGroup(group);
        adbox.setSelected(true);
        
        logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                deleteTemp();
                handleTransitionButton(e, "conS.png", "loginS.png", "LoginPage.fxml", "Login");
            }
        });
        
        rPrev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "conS.png", "calendarS.png", "CalendarView.fxml", "Consultations");
            }
        });

      
    }

    @FXML
    public void ViewTranscript(ActionEvent event) {
        if (studenttable.getSelectionModel().isEmpty() == false) {
            Student student1 = studenttable.getSelectionModel().getSelectedItem();
            String studentstring = student1.getZID();
            setselected(studentstring);

            try {
                stage = new Stage();

                newroot = FXMLLoader.load(getClass().getResource("VIEWIMAGE.fxml"));

                Scene scene = new Scene(newroot);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(viewbutton.getScene().getWindow());

                stage.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("No Transcript Exists for Selected Student!");
                alert.setContentText("Please attach a transcript image and retry");

                alert.showAndWait();
                ex.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No Student Selected!");
            alert.setContentText("Please select a student and retry");

            alert.showAndWait();
        }
    }

    @FXML
    public void createConsult(ActionEvent event) {
        if (studenttable.getSelectionModel().isEmpty() == false) {
            Student student1 = studenttable.getSelectionModel().getSelectedItem();
            String studentstring = student1.getZID();
            if (studentstring != null && !studentstring.isEmpty()) {
                setselected(studentstring);

                System.out.println("Has value " + getselected());

                if (adbox.isSelected()) {
                    handleTransitionButton(event, "conS.png", "asformS.png", "AdvanceStandingForm.fxml", "Create Consultation");
                } else if (apbox.isSelected()) {
                    handleTransitionButton(event, "conS.png", "apformS.png", "AttendancePerformanceForm.fxml", "Create Consultation");
                } else if (cgbox.isSelected()) {
                    handleTransitionButton(event, "conS.png", "loginS.png", "CareerGuidanceForm.fxml", "Create Consultation");
                } else if (cebox.isSelected()) {
                    handleTransitionButton(event, "conS.png", "loginS.png", "CourseEnrolmentForm.fxml", "Create Consultation");
                } else if (dpbox.isSelected()) {
                    handleTransitionButton(event, "conS.png", "loginS.png", "DisciplinaryForm.fxml", "Create Consultation");
                } else if (iebox.isSelected()) {
                    handleTransitionButton(event, "conS.png", "loginS.png", "InternationalExchangeForm.fxml", "Create Consultation");
                } else {
                    System.out.println("Please check box");
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No Student Selected!");
            alert.setContentText("Please select a student and retry");

            alert.showAndWait();
        }
    }

    private Boolean errorCheck() {
        Boolean isComplete = false;
        if (zidtex.getText() != null && !zidtex.getText().isEmpty()) {
            isComplete = true;
        } else {
            errorLabel.setText("Please Insert a zID!");
            animateLabel(pathT, errorLabel);
            isComplete = false;
            return isComplete;
        }
        if (zidtex.getLength() == 8) {
            isComplete = true;
        } else {
            errorLabel.setText("zID is incorrect!");
            animateLabel(pathT, errorLabel);
            isComplete = false;
            return isComplete;
        }
        if (fnametex.getText() != null && !fnametex.getText().isEmpty()) {
            isComplete = true;
        } else {
            errorLabel.setText("Please Insert a Firstname!");
            animateLabel(pathT, errorLabel);
            isComplete = false;
            return isComplete;
        }
        if (lnametex.getText() != null && !lnametex.getText().isEmpty()) {
            isComplete = true;
        } else {
            errorLabel.setText("Please Insert a Lastname!");
            animateLabel(pathT, errorLabel);
            isComplete = false;
            return isComplete;
        }
        if (emailtex.getText() != null && !emailtex.getText().isEmpty()) {
            isComplete = true;
        } else {
            errorLabel.setText("Please Insert an Email!");
            animateLabel(pathT, errorLabel);
            isComplete = false;
            return isComplete;
        }
        if (coursetex.getText() != null && !coursetex.getText().isEmpty()) {
            isComplete = true;
        } else {
            errorLabel.setText("Please Insert a Course!");
            animateLabel(pathT, errorLabel);
            isComplete = false;
            return isComplete;
        }

        errorLabel.setVisible(false);
        return isComplete;
    }
    

    @FXML
    public void addStudent(ActionEvent event) {

        if (errorCheck() == true) {

            String a = zidtex.getText();
            String b = fnametex.getText();
            String c = lnametex.getText();
            String d = coursetex.getText();
            String e = emailtex.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Student Details");
            alert.setHeaderText("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are the student details correct?");
            ButtonType buttonTypeyes = new ButtonType("Yes");
            ButtonType buttonTypecancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypecancel, buttonTypeyes);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeyes) {
                StudentQueries sq = new StudentQueries();
                
                Student student = new Student(a, b, c, d, e, null);
                
                    
                sq.insertStudents(student);

                ObservableList<Student> studentlist2 = FXCollections.observableArrayList(sq.getStudents());
                studenttable.setItems(null);
                studenttable.setItems(studentlist2);
                
               
            
                    
            } 
            
            
        }

    }

    @FXML
    public void backhome(ActionEvent event) {
        handleTransitionButton(event, "conS.png", "dashS.png", "Dashboard.fxml", "Dashboard");
    }

    public static String getselected() {
        return studentst;
    }

    public static void setselected(String studentst) {
        StudentAndConsController.studentst = studentst;
    }
    
      public static Integer getselectedCID() {
        return i;
    }

    public static void setselectedCID(Integer i) {
        StudentAndConsController.i = i;
    }
  
  
    

   public static String getExists(){
   return f;
           
           }
   public static void setExists(String f){
       StudentAndConsController.f = f;
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

    private void animateLabel(PathTransition pt, Label node) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.animateError(pt, node);
    }
    
    
}
