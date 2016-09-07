/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ugcs.Queries.ConsultationQueries;
import ugcs.Model.Consultation;

/**
 * FXML Controller class
 *
 * @author Peterrpancakes
 */
public class ConsultationFormFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    TextField zId;
    @FXML
    TextField fName;
    @FXML
    TextField lName;
    @FXML
    TextField eMail;
    @FXML
    TextArea notesField;
    @FXML
    DatePicker datePicked;
    @FXML
    ComboBox timePicked;
    @FXML
    ComboBox priorityChoice;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;
    @FXML
    Button backHome;
    // @FXML CheckBox highbox;    @FXML CheckBox mediumbox;    @FXML CheckBox lowbox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    public void Create(ActionEvent event) {

        String zid = zId.getText();
        //  String firstname = firstnamefield.getText();
        //  String lastname = lastnamefield.getText();
        //  String email = emailfield.getText();  

        LocalDate date1 = datePicked.getValue();
        Date date2 = localDateToUtilDate(date1);
        SimpleDateFormat dateformatJava2 = new SimpleDateFormat("dd/MM/yyyy");
        String date3 = dateformatJava2.format(date2);

        String time = timePicked.getSelectionModel().getSelectedItem().toString();
        String priority = priorityChoice.getSelectionModel().getSelectedItem().toString();

        if (notesField.getText() != null) {
            String notes = notesField.getText();
            Consultation c = new Consultation(zid, notes, "plagiarism", priority, date3, time);
            ConsultationQueries cq = new ConsultationQueries();
            cq.insertConsult(c);

            System.out.println(cq.getConsultations());
            //POP UP lol
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Consultation Created");
            alert.setHeaderText(null);
            alert.setContentText("You have created a new consultation!");

            alert.showAndWait();

        } else {
            String notes = "...";
        }
    }

    public void Cancel(ActionEvent event) {
        Stage stageedit = (Stage) cancelButton.getScene().getWindow();
        stageedit.close();

    }
        private void gotoHome(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Home Screen");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }
    }
}