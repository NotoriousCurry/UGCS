/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaysFromDisplayedSkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import ugcs.Audio.AudioMethods;
import ugcs.Model.Consultation;
import ugcs.Model.Event;
import ugcs.Queries.ConsultationQueries;

/**
 *
 * @author sgahe
 */
public class CalendarviewController implements Initializable {

    //tableview
    @FXML
    TableView<Consultation> consulttable;
    @FXML
    TableColumn idcol;
    @FXML
    TableColumn zidcol;
    @FXML
    TableColumn typecol;
    @FXML
    TableColumn prioritycol;
    @FXML
    TableColumn datecol;
    @FXML
    TableColumn timecol;
    @FXML
    TextField searchBox;

    //
    @FXML
    TextArea notetextshow;

    //
    @FXML
    Agenda agenda;
    @FXML
    Button createonpage;
    @FXML
    Button create;
    @FXML
    Button switchTable;
    @FXML
    Button switchCalendar;
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
    Label fName;
    @FXML
    Label searchLabel;
    @FXML
    Button followupbutton;

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
        fName.setText(readName());

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
        /* notescol.setCellValueFactory(
         new PropertyValueFactory<Consultation, String>("notes")
         ); */
        prioritycol.setCellValueFactory(
                new PropertyValueFactory<Consultation, String>("priority")
        );
        datecol.setCellValueFactory(
                new PropertyValueFactory<Consultation, String>("date1")
        );
        timecol.setCellValueFactory(
                new PropertyValueFactory<Consultation, String>("time1")
        );

        // Search Functionality Code
        FilteredList<Consultation> filteredData = new FilteredList<>(consultlist, p -> true);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Consultation -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (Consultation.getZid().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Consultation.getNotes().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Consultation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(consulttable.comparatorProperty());

        consulttable.setItems(null);
        consulttable.setItems(sortedData);

        consulttable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Consultation>() {

            @Override
            public void changed(ObservableValue<? extends Consultation> observable,
                    Consultation oldValue, Consultation newValue) {
                showdeets(newValue);
            }
        });

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
                                .withDescription(c.getConsultationid().toString())
                                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group2")) // you should use a map of AppointmentGroups
                        );
                        break;
                    case "Medium":
                        agenda.appointments().addAll(
                                new Agenda.AppointmentImplLocal()
                                .withStartLocalDateTime(newevent.getstartlocaldate().atTime(time))
                                .withEndLocalDateTime(newevent.getendlocaldate().atTime(time2))
                                .withDescription(c.getConsultationid().toString())
                                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group8")) // you should use a map of AppointmentGroups
                        );
                        break;
                    case "Low":
                        agenda.appointments().addAll(
                                new Agenda.AppointmentImplLocal()
                                .withStartLocalDateTime(newevent.getstartlocaldate().atTime(time))
                                .withEndLocalDateTime(newevent.getendlocaldate().atTime(time2))
                                .withDescription(c.getConsultationid().toString())
                                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group15")) // you should use a map of AppointmentGroups
                        );

                        this.agenda = agenda;
                }

            }
            //check for change in appointment
            agenda.selectedAppointments().addListener(new ListChangeListener< Appointment>() {
                public void onChanged(Change<? extends Appointment> c1) {
                    System.out.println("onchange start");
                    while (c1.next()) {
                        System.out.println("onchange next");
                        if (c1.wasPermutated()) {
                            System.out.println("on change listening");
                            for (int i = c1.getFrom(); i < c1.getTo(); ++i) {
                                System.out.println("permuated" + c1.getPermutation(i));

                            }
                        } else if (c1.wasUpdated()) {
                            System.out.println("updated");
                            agenda.selectedAppointments().remove(c1);

                            agenda.appointments().addAll(
                                    new Agenda.AppointmentImplLocal()
                                    .withStartLocalDateTime(c1.getList().get(0).getStartLocalDateTime())
                                    .withEndLocalDateTime(c1.getList().get(0).getEndLocalDateTime())
                                    .withDescription(c.getZid())
                                    .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass(c1.getList().get(0).getAppointmentGroup().getStyleClass())) // you should use a map of AppointmentGroups
                            );
                        } else {
                            ConsultationQueries cq = new ConsultationQueries();

        ObservableList<Consultation> consultlist2 = FXCollections.observableArrayList(cq.getConsultations());
                            for (Appointment a : c1.getRemoved()) {
                                if (!agenda.appointments().contains(a)){
                                System.out.println("onchange removed");
                                /*

                                for (Consultation cc : consultlist2) {
                                    System.out.println("description = " + a.getDescription() + " consutlation id = " + cc.getConsultationid().toString());
                                    if (a.getDescription().equals(cc.getConsultationid().toString())) {
                                        System.out.println("gogogo");
                                        ConsultationQueries cqq = new ConsultationQueries();
                                        cqq.deleteConsult(cc);

                                        ObservableList<Consultation> consultlist = FXCollections.observableArrayList(cqq.getConsultations());

                                        FilteredList<Consultation> filteredData = new FilteredList<>(consultlist, p -> true);

                                        SortedList<Consultation> sortedData = new SortedList<>(filteredData);
                                        sortedData.comparatorProperty().bind(consulttable.comparatorProperty());
                                        consulttable.setItems(null);
                                        consulttable.setItems(sortedData);
                                        System.out.println("removed");

                                        break;

                                    }
                                    else {System.out.println("nothing removed");}
                                } */
                                removeappointment(a);
                                break;
                                }
                            }

                            for (Appointment a : c1.getAddedSubList()) {

                                System.out.println("nothing added");

                            }
                        }
                    }
                }

            });
            
            pdf.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Resources/mini3.gif"))));
            pdf.setOnMouseEntered(new EventHandler<MouseEvent>
    () {

        @Override
        public void handle(MouseEvent t) {
           pdf.setStyle("-fx-background-color:#dae7f3;");
        }
    });

    pdf.setOnMouseExited(new EventHandler<MouseEvent>
    () {

        @Override
        public void handle(MouseEvent t) {
           pdf.setStyle("-fx-background-colour:orange");
        }
    });
            
        
       followupbutton.setOnMouseEntered(new EventHandler<MouseEvent>
    () {

        @Override
        public void handle(MouseEvent t) {
           followupbutton.setStyle("-fx-background-color:#dae7f3;");
        }
    });

    followupbutton.setOnMouseExited(new EventHandler<MouseEvent>
    () {

        @Override
        public void handle(MouseEvent t) {
           followupbutton.setStyle("-fx-background-colour:orange");
        }
    });
            
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

        switchTable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
        ConsultationQueries cq = new ConsultationQueries();
        ObservableList<Consultation> consultlist4 = FXCollections.observableArrayList(cq.getConsultations());
        FilteredList<Consultation> filteredData = new FilteredList<>(consultlist4, p -> true);

        SortedList<Consultation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(consulttable.comparatorProperty());

        consulttable.setItems(null);
        consulttable.setItems(sortedData);

                agenda.setVisible(false);
                switchTable.setVisible(false);
                switchCalendar.setVisible(true);
                consulttable.setVisible(true);
                searchBox.setVisible(true);
                searchLabel.setVisible(true);
                notetextshow.setVisible(true);
                pdf.setVisible(true);
                notetextshow.setVisible(true);
                
            }
        });

        switchCalendar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                agenda.setVisible(true);
                switchTable.setVisible(true);
                switchCalendar.setVisible(false);
                consulttable.setVisible(false);
                searchBox.setVisible(false);
                searchLabel.setVisible(false);
                notetextshow.setVisible(false);
                pdf.setVisible(false);
                notetextshow.setVisible(false);
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
public void removeappointment(Appointment aa){   
    ConsultationQueries cqq = new ConsultationQueries();


  ObservableList<Consultation> consultlist = FXCollections.observableArrayList(cqq.getConsultations());


    for (Consultation cc : consultlist) {
                                    System.out.println("description = " + aa.getDescription() + " consutlation id = " + cc.getConsultationid().toString());
                                    if (aa.getDescription().equals(cc.getConsultationid().toString())) {
                                        System.out.println("gogogo");
                                        cqq.deleteConsult(cc);


                                        FilteredList<Consultation> filteredData = new FilteredList<>(consultlist, p -> true);

                                        SortedList<Consultation> sortedData = new SortedList<>(filteredData);
                                        sortedData.comparatorProperty().bind(consulttable.comparatorProperty());
                                        consulttable.setItems(null);
                                        consulttable.setItems(sortedData);
                                        System.out.println("removed");
                                        break;

                                        

}
    else{ System.out.println("nigga please");}
}}
                                    
    public void showdeets(Consultation consultation) {
        if (consultation == null) {
            notetextshow.setText(null);

        } else {
            ConsultationQueries csss = new ConsultationQueries();
            notetextshow.setText(consultation.getNotes());

        }

    }

    @FXML
    public void Follow(ActionEvent event) {
        Parent newroot;
        Stage stage;

        if (event.getSource() == followupbutton) {
            try {
                System.out.println(" attemping to open");

                stage = new Stage();

                newroot = FXMLLoader.load(getClass().getResource("FollowUpStudentFXML.fxml"));

                Scene scene = new Scene(newroot);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(followupbutton.getScene().getWindow());

                stage.showAndWait();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

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
        //removed from addedsublist
        Date date2 = localDateToUtilDate(localdate1);
        SimpleDateFormat dateformatJava2 = new SimpleDateFormat("dd/MM/yyyy");
        String date3 = dateformatJava2.format(date2);
                                    // String dateonly = a.getStartLocalDateTime().toLocalDate().toString();
                                  /* String timeonly = a.getStartLocalDateTime().toLocalTime().toString();

         if (timeonly != null) {
         switch (timeonly) {
         case "09:00":
         timeonly = "9am";
         break;
         case "10:00":
         timeonly = "10am";
         break;
         case "11:00":
         timeonly = "11am";
         break;
         case "12:00":
         timeonly = "12pm";
         break;
         case "13:00":
         timeonly = "1pm";
         break;
         case "14:00":
         timeonly = "2pm";
         break;
         case "15:00":
         timeonly = "3pm";
         break;
         case "16:00":
         timeonly = "4pm";
         break;
         }
         }
         */
        System.out.println("date = " + date3 + " time only = " + q);
        Consultation cnow = new Consultation(null, null, null, "High", date3, q);
        ConsultationQueries cqq = new ConsultationQueries();
        cqq.insertConsult(cnow);
        System.out.println("consult added.");

        // you should use a map of AppointmentGroups
        ConsultationQueries cq = new ConsultationQueries();

        ObservableList<Consultation> consultlist = FXCollections.observableArrayList(cq.getConsultations());

        FilteredList<Consultation> filteredData = new FilteredList<>(consultlist, p -> true);

        SortedList<Consultation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(consulttable.comparatorProperty());
        consulttable.setItems(null);
        consulttable.setItems(sortedData);
        ///

        agenda.appointments().addAll(
                new Agenda.AppointmentImplLocal()
                .withStartLocalDateTime(newevent.getstartlocaldate().atTime(time))
                .withEndLocalDateTime(newevent.getendlocaldate().atTime(time2))
                .withDescription(cqq.getConsultations().get(cqq.getConsultations().size()-1).getConsultationid().toString())
                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")) // you should use a map of AppointmentGroups
        );
        System.out.println("consultation id = " + cqq.getConsultations().get(cqq.getConsultations().size()-1).getConsultationid().toString());
 agenda.refresh();
 
    }
    @FXML
    Button pdf;
    
    //testing
    public void PDFClicked(ActionEvent event) throws Exception {
       Consultation chosen = consulttable.getSelectionModel().getSelectedItem();
    PDFMaker pdf = new PDFMaker();
byte[] bytes = pdf.PDFForm(chosen.getConsultationid(), chosen.getZid(), chosen.getNotes(), chosen.getType(), chosen.getPriority(), chosen.getDate1(), chosen.getTime1());
   
FileChooser fc = new FileChooser();
fc.setTitle("Consultation PDF");
fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
File file = fc.showSaveDialog(null);
FileOutputStream output = new FileOutputStream(file);
if(file != null){
    System.out.println("not chosen");
} 
if(!file.exists()){file.createNewFile();
}
output.write(bytes);
output.flush();
output.close();


    }

    public void PDF(ActionEvent event) {
        // NEED TO UPDATE WITH NEW PDF CODE
        System.out.println("ADD NEW PDF CODE HERE");
        try {
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);
            float scale = 1f;
            PDImageXObject pdImage = PDImageXObject.createFromFile("src/ugcs/Resources/UNSW-LOGO.png", doc);

            PDPageContentStream content = new PDPageContentStream(doc, page);

            Consultation chosen = consulttable.getSelectionModel().getSelectedItem();
            ConsultationQueries cq = new ConsultationQueries();
            ObservableList<Consultation> listconsult = FXCollections.observableArrayList(cq.getConsultations());
            PDFont font = PDType1Font.COURIER_BOLD_OBLIQUE;

            System.out.println(chosen);
            for (Consultation cc : listconsult) {
                if (chosen.getZid().equals(cc.getZid())) {
                    try {

                        String fileName = "Student Report PDF #" + cc.getConsultationid() + ".pdf";

                        content.drawImage(pdImage, 20, 20, pdImage.getWidth() * scale, pdImage.getHeight() * scale);

                        content.beginText();
                        content.setFont(PDType1Font.HELVETICA, 26);
                        content.moveTextPositionByAmount(220, 750);
                        content.drawString("Student Report #" + cc.getConsultationid());
                        content.endText();

                        String zids = cc.getZid();
                        String types = cc.getType();
                        String dates = cc.getDate1();
                        String times = cc.getTime1();

                        content.beginText();
                        content.setFont(PDType1Font.HELVETICA, 16);
                        content.moveTextPositionByAmount(220, 700);
                        content.drawString("Zid: " + zids);
                        content.endText();

                        content.beginText();
                        content.setFont(PDType1Font.HELVETICA, 16);
                        content.moveTextPositionByAmount(220, 650);
                        content.drawString("Type: " + types);
                        content.endText();

                        content.beginText();
                        content.setFont(PDType1Font.HELVETICA, 16);
                        content.moveTextPositionByAmount(220, 600);
                        content.drawString("Date: " + dates);
                        content.endText();

                        content.beginText();
                        content.setFont(PDType1Font.HELVETICA, 16);
                        content.moveTextPositionByAmount(220, 550);
                        content.drawString("Time: " + times);
                        content.endText();

                        content.close();
                        doc.save(fileName);
                        doc.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("error");
        }

    }
    

    private String readName() {
        String fName = "temp.txt";
        String line = null;
        String name = "Welcome, New User";

        try {
            FileReader fReader = new FileReader(fName);
            BufferedReader bReader = new BufferedReader(fReader);

            line = bReader.readLine();
            String[] sect = line.split(",");
            name = "Welcome, " + sect[0];

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
