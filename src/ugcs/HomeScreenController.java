/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import jfxtras.scene.control.agenda.Agenda;
import ugcs.Model.Event;

/**
 * FXML Controller class
 *
 * @author jenniferpho
 */
public class HomeScreenController implements Initializable {
    
    @FXML
    Agenda agenda;
    @FXML
    Button create;
    @FXML
    DatePicker date;
    @FXML
    ComboBox combo;
    List<Event> myEvents;
    LocalTime time;
    String w = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combo.getItems().addAll("9am", "10am",
                "11am",
                "12pm",
                "1pm",
                "2pm",
                "3pm",
                "4pm");
    }

    public Date localDateToUtilDate(LocalDate localDate) {
        GregorianCalendar cal = new GregorianCalendar(
                localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        java.util.Date date = cal.getTime();
        return date;
    }

    public void create(ActionEvent event) {

        LocalDate localdate1 = date.getValue();

        String q = combo.getSelectionModel().getSelectedItem().toString();
        if (q != null) {
            switch (q) {
                case "9am":
                    w = "09";
                    break;
                case "10am":
                    w = "10";
                    break;
                case "11am":
                    w = "11";
                    break;
                case "12pm":
                    w = "12";
                    break;
                case "1pm":
                    w = "13";
                    break;
                case "2pm":
                    w = "14";
                    break;
                case "3pm":
                    w = "15";
                    break;
                case "4pm":
                    w = "16";
                    break;
            }
        }
        LocalTime time = LocalTime.parse(w + ":00:00");

        int w2 = Integer.parseInt(w);
        int w3 = w2 + 1;

        String w4 = Integer.toString(w3);
        LocalTime time2 = LocalTime.parse(w4 + ":00:00");
        Event newevent = new Event();
        newevent.setstartlocaldate(localdate1);
        newevent.setendlocaldate(localdate1);

        time.now();
        System.out.println("Todays time is " + time.now());

        agenda.appointments().addAll(
                new Agenda.AppointmentImplLocal()
                .withStartLocalDateTime(newevent.getstartlocaldate().atTime(time))
                .withEndLocalDateTime(newevent.getendlocaldate().atTime(time2))
                .withDescription("It's time")
                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")) // you should use a map of AppointmentGroups
        );

    }

}
