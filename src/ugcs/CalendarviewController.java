/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaysFromDisplayedSkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import ugcs.Audio.AudioMethods;
import ugcs.Model.Consultation;
import ugcs.Model.Event;
import ugcs.Queries.ConsultationQueries;

/**
 *
 * @author sgahe
 */
public class CalendarviewController implements Initializable {

    //
    @FXML
    TextArea notetextshow;

    //
    @FXML
    Agenda agenda;

    @FXML
    Button create;
    @FXML
    Button switchTable;
    @FXML
    Button rPrev;
    @FXML
    Button rDash;
    @FXML
    Button logOut;
    @FXML
    Button removebutton;
    @FXML
    Button exitbutton;
    @FXML
    DatePicker datepick;
    @FXML
    Button createCon;
    @FXML
    ComboBox combo;

    @FXML
    ScrollPane sp;

    @FXML
    ToggleButton tb;
    @FXML
    ToggleButton tb2;
    @FXML
    ToggleButton tb3;

    List<Event> myEvents;
    LocalTime timenow;
    String w = null;
    @FXML
    Pane pane;
    @FXML
    ToggleGroup tg;

    public final void setWrapText(boolean value) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        notetextshow.setWrapText(true);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);

        combo.getItems().addAll("9am", "10am",
                "11am",
                "12pm",
                "1pm",
                "2pm",
                "3pm",
                "4pm");
        ConsultationQueries cq = new ConsultationQueries();

        ObservableList<Consultation> consultlist = FXCollections.observableArrayList(cq.getConsultations());

        for (Consultation c : consultlist) {
            String localdatenow = c.getDate1();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            //convert String to LocalDate
            LocalDate localdatenow1 = LocalDate.parse(localdatenow, formatter);

            String t = c.getTime1();
            if (t != null) {
                switch (t) {
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
            newevent.setstartlocaldate(localdatenow1);
            newevent.setendlocaldate(localdatenow1);

            time.now();
            String p = c.getPriority();
            if (p != null) {
                switch (p) {
                    case "High":
                        agenda.appointments().addAll(
                                new Agenda.AppointmentImplLocal()
                                .withStartLocalDateTime(newevent.getstartlocaldate().atTime(time))
                                .withEndLocalDateTime(newevent.getendlocaldate().atTime(time2))
                                .withDescription("High Appointment")
                                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group2")) // you should use a map of AppointmentGroups
                        );
                        break;
                    case "Medium":
                        agenda.appointments().addAll(
                                new Agenda.AppointmentImplLocal()
                                .withStartLocalDateTime(newevent.getstartlocaldate().atTime(time))
                                .withEndLocalDateTime(newevent.getendlocaldate().atTime(time2))
                                .withDescription("Medium Appointment")
                                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group8")) // you should use a map of AppointmentGroups
                        );
                        break;
                    case "Low":
                        agenda.appointments().addAll(
                                new Agenda.AppointmentImplLocal()
                                .withStartLocalDateTime(newevent.getstartlocaldate().atTime(time))
                                .withEndLocalDateTime(newevent.getendlocaldate().atTime(time2))
                                .withDescription("Low Appointment")
                                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group15")) // you should use a map of AppointmentGroups
                        );

                        this.agenda = agenda;

                }
            }

        }

        ToggleGroup group = new ToggleGroup();

        tb.setToggleGroup(group);

        tb2.setToggleGroup(group);
        tb2.setSelected(true);

        tb3.setToggleGroup(group);

        tb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                agenda.setSkin((new AgendaDaySkin(agenda)));
            }
        });
        tb2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                agenda.setSkin((new AgendaWeekSkin(agenda)));
            }
        });
        tb3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                agenda.setSkin((new AgendaDaysFromDisplayedSkin(agenda)));
            }
        });
        createCon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "calendarS.png", "conS.png", "StudentAndCons.fxml", "Create Consultation");

            }
        });

        logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                deleteTemp();
                handleTransitionButton(e, "calendarS.png", "loginS.png", "LoginPage.fxml", "Login");
            }
        ;
        });
        rDash.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "calendarS.png", "dashS.png", "Dashboard.fxml", "Home");
            }
        ;
        });
        rPrev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "calendarS.png", "dashS.png", "Dashboard.fxml", "Home");
            }
        ;
        });
        readPass();
    }

    public void showdeets(Consultation consultation) {
        if (consultation == null) {
            notetextshow.setText(null);

        } else {
            ConsultationQueries csss = new ConsultationQueries();
            notetextshow.setText(consultation.getNotes());

        }

    }

    public Date localDateToUtilDate(LocalDate localDate) {
        GregorianCalendar cal = new GregorianCalendar(
                localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        java.util.Date date = cal.getTime();
        return date;
    }

    /* this was to create an agenda manually through a 'create' button, however the code also works with 
    the database to automatically create agendas
     */
    public void remove(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove this Consultation?");
        ButtonType buttonTypeyes = new ButtonType("Yes");
        ButtonType buttonTypecancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypecancel, buttonTypeyes);

        Optional<ButtonType> result = alert.showAndWait();
    }

    public void create(ActionEvent event) {
        // to create an agenda

        LocalDate localdate1 = datepick.getValue();

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
                .withDescription("Appointment")
                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")) // you should use a map of AppointmentGroups
        );

    }
    @FXML
    Button pdf;

    public void PDF(ActionEvent event) {
        System.out.println("ADD NEW PDF CODE HERE");
    }

    private String readName() {
        String fName = "temp.txt";
        String line = null;
        String name = null;

        try {
            FileReader fReader = new FileReader(fName);
            BufferedReader bReader = new BufferedReader(fReader);

            line = bReader.readLine();
            String[] sect = line.split(",");
            name = sect[0];

            bReader.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to find file");
        } catch (IOException ex) {
            System.out.println("Error Reading File");
        }
        return name;
    }

    private String readPass() {
        String fName = "temp.txt";
        String line = null;
        String pass = null;

        try {
            FileReader fReader = new FileReader(fName);
            BufferedReader bReader = new BufferedReader(fReader);

            line = bReader.readLine();
            String[] sect = line.split(",");
            pass = sect[1];

            bReader.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to find file");
        } catch (IOException ex) {
            System.out.println("Error Reading File");
        }
        System.out.println(pass);
        return pass;
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
