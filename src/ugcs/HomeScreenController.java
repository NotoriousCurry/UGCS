/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.scene.control.agenda.Agenda;
import ugcs.Model.Consultation;
import ugcs.Model.Event;
import ugcs.Queries.ConsultationQueries;

/**
 * FXML Controller class
 *
 * @author jenniferpho
 */
public class HomeScreenController implements Initializable {
    //tableview
    @FXML
    TableView consulttable;
    @FXML
    TableColumn idcol;
    @FXML
    TableColumn zidcol;
    @FXML
    TableColumn typecol;
    @FXML
    TableColumn notescol;
    @FXML
    TableColumn prioritycol;
    @FXML
    TableColumn datecol;
    @FXML
    TableColumn timecol;
    
    
    
    //
    @FXML
    Agenda agenda;
    @FXML
    Button create;
    @FXML
    Button createCon;
    @FXML
    DatePicker datepick;
    @FXML
    ComboBox combo;
    @FXML
    Button logOut;
    List<Event> myEvents;
    LocalTime timenow;
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
        ConsultationQueries cq = new ConsultationQueries();
        
        ObservableList<Consultation> consultlist = FXCollections.observableArrayList(cq.getConsultations());
        
        idcol.setCellValueFactory(
                new PropertyValueFactory<Consultation, Integer>("consultationid")
        );
        zidcol.setCellValueFactory(
                new PropertyValueFactory<Consultation, String>("zid")
        );
        typecol.setCellValueFactory(
                new PropertyValueFactory<Consultation, String>("type")
        );
        notescol.setCellValueFactory(
                new PropertyValueFactory<Consultation, String>("notes")
        );
        prioritycol.setCellValueFactory(
                new PropertyValueFactory<Consultation, String>("priority")
        );
        datecol.setCellValueFactory(
                new PropertyValueFactory<Consultation, String>("date1")
        );
        timecol.setCellValueFactory(
                new PropertyValueFactory<Consultation, String>("time1")
        );
        consulttable.setItems(null);
        consulttable.setItems(consultlist);
         // making agenda automatic 
        
        for (Consultation c: consultlist){
          String  localdatenow = c.getDate1();
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
        if (p != null){
                switch(p){
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
                        
        }
        }
        }
    
            
        
        
        
        
        
        logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                lOut(e);
            }
        ;
        }

    );
        createCon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gotoCreate(e);
            }
        });
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
    

    private void lOut(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Group root1 = new Group();
        Group root2 = new Group();
        Rectangle rect1 = new Rectangle(1200, 800);
        Label lab1 = new Label("LOADING...");
        rect1.setFill(new ImagePattern(new Image("ugcs/Resources/homeSs.png")));
        root1.getChildren().addAll(rect1, lab1);
        Rectangle rect2 = new Rectangle(1200, 800);
        Label lab2 = new Label("LOADED!");
        rect2.setFill(new ImagePattern(new Image("ugcs/Resources/loginSs.png")));
        root2.getChildren().addAll(rect2, lab2);

        Scene scene1 = new Scene(root1, 1200, 800);
        stage.setScene(scene1);
        stage.show();

        WritableImage wi = new WritableImage(1200, 800);
        Image img1 = root1.snapshot(new SnapshotParameters(), wi);
        ImageView imgView1 = new ImageView(img1);
        wi = new WritableImage(1200, 800);
        Image img2 = root2.snapshot(new SnapshotParameters(), wi);
        ImageView imgView2 = new ImageView(img2);
        // Create new pane with both images
        imgView1.setTranslateX(0);
        imgView2.setTranslateX(1200);
        StackPane pane = new StackPane(imgView1, imgView2);
        pane.setPrefSize(1200, 800);
        // Replace root1 with new pane
        root1.getChildren().setAll(pane);
        // create transtition
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(imgView2.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("LoginScreen");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {

            }
        });
        timeline.play();
    }

    private void gotoCreate(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ConsultationFormFXML.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Create Consultation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }
    }

}
