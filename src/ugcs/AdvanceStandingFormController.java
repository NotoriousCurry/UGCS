/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ugcs.Audio.AudioMethods;
import ugcs.Queries.ConsultationQueries;
import ugcs.Model.Consultation;
import ugcs.Model.Student;
import ugcs.Queries.StudentQueries;

/**
 * FXML Controller class
 *
 * @author Peterrpancakes
 */
public class AdvanceStandingFormController implements Initializable {

    /**
     * Initializes the controller class.
     */
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StudentQueries sq = new StudentQueries();
        ObservableList<Student> slist = FXCollections.observableList(sq.getStudents());
        String zidselected = StudentAndConsController.getselected();
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
                handleTransitionButton(e, "calendarS.png", "loginS.png", "StudentAndCons.fxml", "Create Consultation");

            }
        });
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
            Consultation c = new Consultation(zid, notes, "Advanced Standing", priority, date3, time);
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
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("calendarview.fxml"));
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
