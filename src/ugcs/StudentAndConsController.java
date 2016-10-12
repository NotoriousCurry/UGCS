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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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

    }

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

    @FXML
    public void ViewTranscript(ActionEvent event) {
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
            ex.printStackTrace();
        }

    }

    @FXML
    public void createConsult(ActionEvent event) {
        Student student1 = studenttable.getSelectionModel().getSelectedItem();
        String studentstring = student1.getZID();
        setselected(studentstring);

        System.out.println("Has value " + getselected());

        if (adbox.isSelected()) {
            handleTransitionButton(event, "calendarS.png", "asformS.png", "AdvanceStandingForm.fxml", "Create Consultation");
        } else if (apbox.isSelected()) {
            handleTransitionButton(event, "calendarS.png", "apformS.png", "AttendancePerformanceForm.fxml", "Create Consultation");
        } else if (cgbox.isSelected()) {
            handleTransitionButton(event, "calendarS.png", "loginS.png", "CareerGuidanceForm.fxml", "Create Consultation");
        } else if (cebox.isSelected()) {
            handleTransitionButton(event, "calendarS.png", "loginS.png", "CourseEnrolmentForm.fxml", "Create Consultation");
        } else if (dpbox.isSelected()) {
            handleTransitionButton(event, "calendarS.png", "loginS.png", "DisciplinaryForm.fxml", "Create Consultation");
        } else if (iebox.isSelected()) {
            handleTransitionButton(event, "calendarS.png", "loginS.png", "InternationalExchangeForm.fxml", "Create Consultation");
        } else {
            System.out.println("Please check box");
        }
    }

    @FXML
    public void addStudent(ActionEvent event) {

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

    @FXML
    public void backhome(ActionEvent event) {
        handleTransitionButton(event, "conS.png", "calendarS.png", "CalendarView.fxml", "Consultations");
    }

    private static String studentst;

    public static String getselected() {
        return studentst;
    }

    public static void setselected(String studentst) {
        StudentAndConsController.studentst = studentst;
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
