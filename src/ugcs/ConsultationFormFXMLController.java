/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ugcs.Database.ConsultationQueries;
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         comboselected.getItems().addAll("9am", "10am",
                "11am",
                "12pm",
                "1pm",
                "2pm",
                "3pm",
                "4pm");
           prioritybox.getItems().addAll("High", "Medium", "Low");
    }    
    
    @FXML TextField zidfield;
    @FXML TextField firstnamefield;
    @FXML TextField lastnamefield;
    @FXML TextField emailfield;
    @FXML TextArea notesfield;
    @FXML DatePicker datepicked;
    @FXML ComboBox comboselected;
    @FXML ComboBox prioritybox;
    @FXML Button createbutton;
    @FXML Button cancelbutton;
   // @FXML CheckBox highbox;    @FXML CheckBox mediumbox;    @FXML CheckBox lowbox;
     public Date localDateToUtilDate(LocalDate localDate) {
        GregorianCalendar cal = new GregorianCalendar(
                localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        java.util.Date date = cal.getTime();
        return date;
    }

public void Create(ActionEvent event){
    
    String zid = zidfield.getText();
  //  String firstname = firstnamefield.getText();
  //  String lastname = lastnamefield.getText();
  //  String email = emailfield.getText();  
    
    LocalDate date1 = datepicked.getValue();
        Date date2 = localDateToUtilDate(date1);
        SimpleDateFormat dateformatJava2 = new SimpleDateFormat("dd/MM/yyyy");
        String date3 = dateformatJava2.format(date2);
        
    String time = comboselected.getSelectionModel().getSelectedItem().toString();
    String priority = prioritybox.getSelectionModel().getSelectedItem().toString();

    if (notesfield.getText() != null){
    String notes = notesfield.getText();   
    Consultation c = new Consultation(zid, notes, "plagiarism", priority, date3, time)
    ;
    ConsultationQueries cq = new ConsultationQueries();
    cq.insertConsult(c);
    
        System.out.println(cq.getConsultations());
        //POP UP lol
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consultation Created");
        alert.setHeaderText(null);
        alert.setContentText("You have created a new consultation!");

        alert.showAndWait();
    
    }
    else
    {String notes = "...";}
    

  
    
 
    
    
    
}
public void Cancel(ActionEvent event){
    Stage stageedit = (Stage) cancelbutton.getScene().getWindow();
        stageedit.close();

}
}
