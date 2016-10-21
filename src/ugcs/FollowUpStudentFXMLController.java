package ugcs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ugcs.Model.Student;
import ugcs.Queries.StudentFollowQueries;
import ugcs.Queries.StudentQueries;

/**
 * FXML Controller class
 *
 * @author Peterrpancakes
 */
public class FollowUpStudentFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
    TableView<Student> studentfollowtable;
    @FXML
    TableColumn zidcol1;
    @FXML
    TableColumn fnamecol1;
    @FXML
    TableColumn lnamecol1;
    @FXML
    Button followup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StudentFollowQueries sfq = new StudentFollowQueries();

        ObservableList<Student> studentlist = FXCollections.observableArrayList(sfq.getStudents());
        System.out.println("sss " + studentlist);
        zidcol1.setCellValueFactory(
                new PropertyValueFactory<Student, String>("zID")
        );
        fnamecol1.setCellValueFactory(
                new PropertyValueFactory<Student, String>("fName")
        );
        lnamecol1.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lName")
        );
        ToggleGroup group = new ToggleGroup();
        adbox.setToggleGroup(group);
        apbox.setToggleGroup(group);
        cgbox.setToggleGroup(group);
        cebox.setToggleGroup(group);
        dpbox.setToggleGroup(group);
        iebox.setToggleGroup(group);

        studentfollowtable.setItems(null);
        studentfollowtable.setItems(studentlist);

    }

    public void followupevent(ActionEvent event) {

        Student selected = studentfollowtable.getSelectionModel().getSelectedItem();
        StudentAndConsController.setselected(selected.getZID());
                      StudentFollowQueries sfq = new StudentFollowQueries();
                      sfq.deleteStudent(selected);
        if (adbox.isSelected()) {

            Stage stageedit = (Stage) followup.getScene().getWindow();
            stageedit.close();
            handleTransitionButton(event, "calendarS.png", "asformS.png", "AdvanceStandingForm.fxml", "Create Consultation");
        } else if (apbox.isSelected()) {
            Stage stageedit = (Stage) followup.getScene().getWindow();
            stageedit.close();
            handleTransitionButton(event, "calendarS.png", "apformS.png", "AttendancePerformanceForm.fxml", "Create Consultation");
        } else if (cgbox.isSelected()) {
            Stage stageedit = (Stage) followup.getScene().getWindow();
            stageedit.close();
            handleTransitionButton(event, "calendarS.png", "loginS.png", "CareerGuidanceForm.fxml", "Create Consultation");
        } else if (cebox.isSelected()) {
            Stage stageedit = (Stage) followup.getScene().getWindow();
            stageedit.close();
            handleTransitionButton(event, "calendarS.png", "loginS.png", "CourseEnrolmentForm.fxml", "Create Consultation");
        } else if (dpbox.isSelected()) {
            Stage stageedit = (Stage) followup.getScene().getWindow();
            stageedit.close();
            handleTransitionButton(event, "calendarS.png", "loginS.png", "DisciplinaryForm.fxml", "Create Consultation");
        } else if (iebox.isSelected()) {
            Stage stageedit = (Stage) followup.getScene().getWindow();
            stageedit.close();
            handleTransitionButton(event, "calendarS.png", "loginS.png", "InternationalExchangeForm.fxml", "Create Consultation");
        } else {
            System.out.println("Please check box");
        }

    }

    private void handleTransitionButton(ActionEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenButton(e, a, b, c, d);

    }

    public void returnPrev(ActionEvent event) {
        Stage stageedit = (Stage) followup.getScene().getWindow();
        stageedit.close();
        handleTransitionButton(event, "calendarS.png", "calendarS.png", "CalendarView.fxml", "Create Consulation");
    }
}
