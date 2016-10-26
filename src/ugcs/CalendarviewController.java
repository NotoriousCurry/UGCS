/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
import javafx.scene.control.TableCell;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaysFromDisplayedSkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import ugcs.Audio.AudioMethods;
import ugcs.Model.Consultation;
import ugcs.Model.Event;
import ugcs.Queries.ConsultationQueries;
import ugcs.Queries.FileQueries;

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
    TableColumn<Consultation, String> datecol;
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
    Button pdfAll;

    @FXML
    ScrollPane sp;

    @FXML
    ToggleButton tb;
    @FXML
    ToggleButton tb2;
    @FXML
    ToggleButton tb3;
    @FXML
    Button editbutton;
    @FXML
    Button addstudentbutton;

    List<Event> myEvents;
    LocalTime timenow;
    String w = null;
    @FXML
    Pane pane;
    @FXML
    ToggleGroup tg;
    @FXML
    ScrollPane sp2;

    FileQueries fq = new FileQueries();
    PDFMaker pdfm = new PDFMaker();

    public final void setWrapText(boolean value) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // followupbutton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Resources/Butt.gif"))));
        //FileQueries fq = new FileQueries();
        notetextshow.setWrapText(true);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        fName.setText("Welcome, " + fq.readName());
        pdf.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Resources/mini3.gif"))));
        pdfAll.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Resources/mini3.gif"))));

        sp2.setVvalue(40);
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
        //  datecol.setCellValueFactory(
        //        new PropertyValueFactory<Consultation, String>("date1")
        //);
        datecol.setCellValueFactory(cellData -> cellData.getValue().dateproperty());
        //displays an exclamation mark when the consultation is today
        datecol.setCellFactory(column -> {
            return new TableCell<Consultation, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//formatter = formatter.withLocale(Locale.ENGLISH);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
                        LocalDate date = LocalDate.parse(item, formatter);
                        System.out.println(" date - " + date);

                        setText(item);
                        LocalDate datenow1 = LocalDate.now();
                        Date dateupdate2 = localDateToUtilDate(datenow1);
                        SimpleDateFormat dateformatJava2 = new SimpleDateFormat("dd/MM/yyyy");
                        String datenow3 = dateformatJava2.format(dateupdate2);
                        LocalDate datenowcompare = LocalDate.parse(datenow3, formatter);
                        LocalDate datetomorrow = LocalDate.now().plusDays(1);

                        System.out.println("now = " + datenowcompare);
                        if (date.equals(datenowcompare)) {
                            System.out.println(" should be working");
                            setTextFill(Color.BLACK);

                            // setStyle("-fx-background-color: lightgreen");
                            HBox box = new HBox();
                            box.setSpacing(10);

                            ImageView imageview = new ImageView();
                            imageview.setImage(new Image(getClass().getResourceAsStream("Resources/excred2.png")));
                            box.getChildren().addAll(imageview);
                            setGraphic(box);

                        } else if (date.equals(datetomorrow)) {
                            System.out.println(" should be working 2");
                            setTextFill(Color.BLACK);

                            // setStyle("-fx-background-color: lightgreen");
                            HBox box = new HBox();
                            box.setSpacing(10);
                            ImageView imageview = new ImageView();
                            imageview.setImage(new Image(getClass().getResourceAsStream("Resources/exc3.png")));
                            box.getChildren().addAll(imageview);
                            setGraphic(box);

                        }
                    }
                }
            };
        });

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

        for (Consultation c : sortedData) {
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
            System.out.println("p is priority = " + p);
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
                        /*
                            agenda.appointments().addAll(
                                    new Agenda.AppointmentImplLocal()
                                    .withStartLocalDateTime(c1.getList().get(0).getStartLocalDateTime())
                                    .withEndLocalDateTime(c1.getList().get(0).getEndLocalDateTime())
                                    .withDescription(c.getZid())
                                    .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass(c1.getList().get(0).getAppointmentGroup().getStyleClass())) // you should use a map of AppointmentGroups
                            );*/
                    } else {
                        ConsultationQueries cq = new ConsultationQueries();

                        ObservableList<Consultation> consultlist2 = FXCollections.observableArrayList(cq.getConsultations());
                        for (Appointment a : c1.getRemoved()) {
                            if (!agenda.appointments().contains(a)) {
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

        pdf.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                pdf.setStyle("-fx-background-color:#dae7f3;");
            }
        });

        pdf.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                pdf.setStyle("-fx-background-colour:orange");
            }
        });

        pdfAll.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                pdfAll.setStyle("-fx-background-color:#dae7f3;");
            }
        });

        pdfAll.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                pdfAll.setStyle("-fx-background-colour:orange");
            }
        });

        followupbutton.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                followupbutton.setStyle("-fx-background-color:#dae7f3;");
            }
        });

        followupbutton.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                followupbutton.setStyle("-fx-background-colour:orange");
            }
        });

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

                agenda.setVisible(false);
                switchTable.setVisible(false);
                switchCalendar.setVisible(true);
                consulttable.setVisible(true);
                searchBox.setVisible(true);
                searchLabel.setVisible(true);
                notetextshow.setVisible(true);
                pdf.setVisible(true);
                pdfAll.setVisible(true);
                notetextshow.setVisible(true);

            }
        });

        switchCalendar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                addstudentbutton.setVisible(false);
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
                fq.deleteTemp();
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
        pdfAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    byte[] bytes = pdfm.createAllPdf();

                    FileChooser fc = new FileChooser();
                    fc.setTitle("Consultation PDF");
                    fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                    File file = fc.showSaveDialog(null);
                    FileOutputStream output = new FileOutputStream(file);
                    if (file != null) {
                        System.out.println("not chosen");
                    }
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    output.write(bytes);
                    output.flush();
                    output.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        ;
        });
        fq.readPass();
    }

    public void removeappointment(Appointment aa) {
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

            } else {
                System.out.println("nigga please");
            }
        }
    }

    public void showdeets(Consultation consultation) {
        if (consultation == null) {
            notetextshow.setText(null);

        } else {
            // ConsultationQueries csss = new ConsultationQueries();
            notetextshow.setText(consultation.getNotes());

        }
        try {
            if (consultation.getZid() == null) {
                addstudentbutton.setVisible(true);
            } else {
                addstudentbutton.setVisible(false);
            }
        } catch (NullPointerException e) {
            System.out.println("no consultation zid added");
        }

    }

    @FXML
    public void EditC(ActionEvent event) {
        Consultation c2 = consulttable.getSelectionModel().getSelectedItem();
        Integer a = c2.getConsultationid();
        String b = c2.getType();
        System.out.println("b = " + b);

        StudentAndConsController.setselectedCID(a);
        if (b.equals("Advanced Standing")) {

            StudentAndConsController.setExists("true");

            System.out.println("ffmpeowfpewmfwe");
            handleTransitionButton(event, "calendarS.png", "asformS.png", "AdvanceStandingForm.fxml", "Create Consultation");
        } else if (b.equals("Attendance/Performance")) {
            StudentAndConsController.setExists("true");
            handleTransitionButton(event, "calendarS.png", "apformS.png", "AttendancePerformanceForm.fxml", "Create Consultation");
        } else if (b.equals("Career Guidance")) {
            StudentAndConsController.setExists("true");
            handleTransitionButton(event, "calendarS.png", "loginS.png", "CareerGuidanceForm.fxml", "Create Consultation");
        } else if (b.equals("Course Enrolment")) {
            StudentAndConsController.setExists("true");
            handleTransitionButton(event, "calendarS.png", "loginS.png", "CourseEnrolmentForm.fxml", "Create Consultation");
        } else if (b.equals("Displinary Action")) {
            StudentAndConsController.setExists("true");
            handleTransitionButton(event, "calendarS.png", "loginS.png", "DisciplinaryForm.fxml", "Create Consultation");
        } else if (b.equals("International Exchange")) {
            StudentAndConsController.setExists("true");
            handleTransitionButton(event, "calendarS.png", "loginS.png", "InternationalExchangeForm.fxml", "Create Consultation");
        } else {
            System.out.println("no type");
        }

    }

    @FXML
    public void Follow(ActionEvent event) {
        Parent newroot;
        Stage stage;

        if (event.getSource() == followupbutton) {
            try {
                stage = (Stage) followupbutton.getScene().getWindow();
                stage.close();
                stage = new Stage();

                Parent root = FXMLLoader.load(getClass().getResource("FollowUpStudentFXML.fxml"));

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(followupbutton.getScene().getWindow());

                stage.showAndWait();

                /*  System.out.println(" attemping to open");

                 stage = new Stage();

                 newroot = FXMLLoader.load(getClass().getResource("FollowUpStudentFXML.fxml"));

                 Scene scene = new Scene(newroot);
                 stage.setScene(scene);
                 stage.initModality(Modality.APPLICATION_MODAL);
                 stage.initOwner(followupbutton.getScene().getWindow());

                 stage.show();
                 */
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
    public void AddStudent(ActionEvent event) {
        Consultation con2 = consulttable.getSelectionModel().getSelectedItem();
        StudentAndConsController.setselectedCID(con2.getConsultationid());
        StudentAndConsController.setExists("true");
        handleTransitionButton(event, "calendarS.png", "conS.png", "StudentAndCons.fxml", "Add a Student to Consultation");

    }

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
                .withDescription(cqq.getConsultations().get(cqq.getConsultations().size() - 1).getConsultationid().toString())
                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group10")) // you should use a map of AppointmentGroups
        );
        System.out.println("consultation id = " + cqq.getConsultations().get(cqq.getConsultations().size() - 1).getConsultationid().toString());
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
        if (file != null) {
            System.out.println("not chosen");
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        output.write(bytes);
        output.flush();
        output.close();

    }

    private void handleAudio(String file) {
        AudioMethods am = new AudioMethods();

        am.playAudio(file);
    }

    public void handleTransitionButton(ActionEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenButton(e, a, b, c, d);

    }

    private void handleTransitionKey(KeyEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenKey(e, a, b, c, d);

    }
}
