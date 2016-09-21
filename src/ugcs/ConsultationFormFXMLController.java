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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    ComboBox typeChoice;
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
        typeChoice.getItems().addAll("Course Enrolment",
                "Career Guidance",
                "Disciplinary",
                "International Exchange",
                "Attendance/Performance",
                "Advanced Standing" );
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
        
        if (typeChoice.getSelectionModel().getSelectedItem() != null) {
            type = typeChoice.getSelectionModel().getSelectedItem().toString();
            isComplete = true;
        } else {
            System.out.println("TYPE GONE NIGGA");
            isComplete = false;
        }
        //  String firstname = firstnamefield.getText();
        //  String lastname = lastnamefield.getText();
        //  String email = emailfield.getText();  


        if (notesField.getText() != null && isComplete == true) {
            String notes = notesField.getText();
            Consultation c = new Consultation(zid, notes, type, priority, date3, time);
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

    public void Cancel(ActionEvent event) {
        Stage stageedit = (Stage) cancelButton.getScene().getWindow();
        stageedit.close();

    }

        private void gotoHome(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Group root1 = new Group();
        Group root2 = new Group();
        Rectangle rect1 = new Rectangle(1200, 800);
        Label lab1 = new Label("LOADING...");
        rect1.setFill(new ImagePattern(new Image("ugcs/Resources/createS.png")));
        root1.getChildren().addAll(rect1, lab1);
        Rectangle rect2 = new Rectangle(1200, 800);
        Label lab2 = new Label("LOADED!");
        rect2.setFill(new ImagePattern(new Image("ugcs/Resources/homeS.png")));
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
                Parent root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("HomeScreen");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {

            }
        });
        timeline.play();
    }
}
