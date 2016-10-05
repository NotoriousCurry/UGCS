/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
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
    TableView<Consultation> consulttable;
    @FXML
    TableColumn idcol;
    @FXML
    TableColumn zidcol;
    @FXML
    TableColumn typecol;
   //@FXML
   // TableColumn notescol;
    @FXML
    TableColumn prioritycol;
    @FXML
    TableColumn datecol;
    @FXML
    TableColumn timecol;
    
    //
    @FXML
    TextArea notetextshow; 
    @FXML
    TextField searchBox;
    
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
    @FXML
    Button removebutton;
    @FXML
    Button exitbutton;
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
    @FXML Pane pane;
    @FXML
    ToggleGroup tg;
        
public final void setWrapText(boolean value){}
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
            
                        this.agenda = agenda;
                        		
                        
        }
        }
                
         consulttable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Consultation>() {

            @Override
            public void changed(ObservableValue<? extends Consultation> observable,
                    Consultation oldValue, Consultation newValue) {
                showdeets(newValue);
            }
        });
        
        
        
        }
               
 ToggleGroup group = new ToggleGroup();

tb.setToggleGroup(group);

tb2.setToggleGroup(group);
tb2.setSelected(true);

tb3.setToggleGroup(group);
/*
       ToggleButton ggg = (ToggleButton) group.selectedToggleProperty().getValue();
        if (ggg == tb){agenda.setSkin((new AgendaDaySkin(agenda)));}
        else if (ggg == tb2){agenda.setSkin(new AgendaWeekSkin(agenda));}
        else if (ggg == tb3){System.out.println("not yet");}
        
        
 Calendar c = new GregorianCalendar();
    c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
  
    
        
        displayedCalendarObjectProperty.setValue(c);*/
          
        tb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                agenda.setSkin((new AgendaDaySkin(agenda)));
            }
        } );
        tb2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                agenda.setSkin((new AgendaWeekSkin(agenda)));
            }
        } );
       tb3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e)  {
                agenda.setSkin((new AgendaDaysFromDisplayedSkin(agenda)));
            }
        } );
        
           
        
        logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                deleteTemp();
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
        
     readPass();   
    }

    public void showdeets(Consultation consultation){
        if (consultation == null){
            notetextshow.setText(null);
        
        }
        else {
            ConsultationQueries csss = new ConsultationQueries();
            notetextshow.setText(consultation.getNotes());
        
        }
            
        
    }
    /*

	private final ObjectProperty<Calendar> displayedCalendarObjectProperty = new SimpleObjectProperty<Calendar>(this, "displayedCalendar", Calendar.getInstance())
{
public void set(Calendar value)
{
if (value == null) throw new NullPointerException("Null not allowed");
super.set(value);
}};
    */
    
        
    
    public Date localDateToUtilDate(LocalDate localDate) {
        GregorianCalendar cal = new GregorianCalendar(
                localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        java.util.Date date = cal.getTime();
        return date;
    }
/* this was to create an agenda manually through a 'create' button, however the code also works with 
    the database to automatically create agendas
  */  
    
   public void remove(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove this Consultation?");
        ButtonType buttonTypeyes = new ButtonType("Yes");
        ButtonType buttonTypecancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypecancel, buttonTypeyes);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeyes) {

   Consultation consultselected =  consulttable.getSelectionModel().getSelectedItem();
   ConsultationQueries css = new ConsultationQueries();
   
   css.deleteConsult(consultselected);
   ObservableList<Consultation> listlist = FXCollections.observableArrayList(css.getConsultations());
   
   consulttable.setItems(null);
   consulttable.setItems(listlist);
   
   } else {
            alert.close();    
        }
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
    @FXML Button pdf;
    
    public void PDF(ActionEvent event){
        Document d = new Document();
        
        Consultation chosen = consulttable.getSelectionModel().getSelectedItem();
        ConsultationQueries cq = new ConsultationQueries();
        ObservableList<Consultation> listconsult = FXCollections.observableArrayList(cq.getConsultations());
      Font f1 = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, new BaseColor(0,0,0));
      Font f2 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0,0,0));

        System.out.println(chosen);
        for (Consultation cc : listconsult){
            if (chosen.getZid().equals(cc.getZid())) {
                try{                    
       
                    PdfWriter.getInstance(d, new FileOutputStream("Student Report PDF #" + cc.getConsultationid() + ".pdf"));
                d.open();

                    d.add(new Paragraph ("Student Report #" + cc.getConsultationid(), f1));
                    d.add(Chunk.NEWLINE);
                    PdfPTable table = new PdfPTable(4);
                    
                    String zids = cc.getZid();
                    String types = cc.getType();
                    String dates = cc.getDate1();
                    String times = cc.getTime1();
                    
                    table.addCell(new Phrase("ZID",f2));
                    table.addCell(new Phrase("Type",f2));
                    table.addCell(new Phrase("Date",f2));
                    table.addCell(new Phrase("Time",f2));
                    
                    table.addCell(zids);
                    table.addCell(types);
                    table.addCell(dates);
                    table.addCell(times);
                    d.add(table);
                    d.add(Chunk.NEWLINE);
                    d.add(new Paragraph("Comments:",f1));
                    d.add(new Paragraph(cc.getNotes()));
                    
                    
                    
                    d.close();
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }        
            }
            
        }
        
    
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
            
        }
        catch (FileNotFoundException ex) {
            System.out.println("Unable to find file");
        }
        catch (IOException ex) {
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
            
        }
        catch (FileNotFoundException ex) {
            System.out.println("Unable to find file");
        }
        catch (IOException ex) {
            System.out.println("Error Reading File");
        }
            System.out.println(pass);
        return pass;
    }
        
        private void deleteTemp() {
            try {
                File file = new File("temp.txt");
                if(file.delete()) {
                    System.out.println(file.getName() + " is Deleted");
                } else {
                    System.out.println("Delete operation failed");
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    

    private void lOut(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Group root1 = new Group();
        Group root2 = new Group();
        Rectangle rect1 = new Rectangle(1200, 800);
        Label lab1 = new Label("LOADING...");
        rect1.setFill(new ImagePattern(new Image("ugcs/Resources/homeS.png")));
        root1.getChildren().addAll(rect1, lab1);
        Rectangle rect2 = new Rectangle(1200, 800);
        Label lab2 = new Label("LOADED!");
        rect2.setFill(new ImagePattern(new Image("ugcs/Resources/loginS.png")));
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
    
    /* public void Exitkey(ActionEvent event) {
        Stage stage = (Stage) exitbutton.getScene().getWindow();
        stage.close();
        DerbySetup ds = new DerbySetup();

        ds.closeConnection();
     }
*/
    /* for new student
    StudentSetup ss = new StudentSetup();
   String  zid1 =zidtextfield.getText();
    String fname = fnametextfield.getText();
        String lname = lnametextfield.getText();
    String course = coursetextfield.getText();
    String email = emailtextfield.getText();

    ss.insertStudents(zid1, fname, lname, course, email);
    
    
    
    
    */
    
    

    private void gotoCreate(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Group root1 = new Group();
        Group root2 = new Group();
        Rectangle rect1 = new Rectangle(1200, 800);
        Label lab1 = new Label("LOADING...");
        rect1.setFill(new ImagePattern(new Image("ugcs/Resources/homeS.png")));
        root1.getChildren().addAll(rect1, lab1);
        Rectangle rect2 = new Rectangle(1200, 800);
        Label lab2 = new Label("LOADED!");
        rect2.setFill(new ImagePattern(new Image("ugcs/Resources/createS.png")));
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
                Parent root = FXMLLoader.load(getClass().getResource("ConsultationFormFXML.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Create Consultation");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {

            }
        });
        timeline.play();
    }
}
