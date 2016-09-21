/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import ugcs.Database.DerbySetup;
import ugcs.Queries.UgcQueries;

/**
 * FXML Controller class CTRL + SHIFT + I
 *
 * @author sgaheer
 */
public class LoginPageController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button login;
    @FXML
    private TextField usr;
    @FXML
    private PasswordField pass;
@FXML
private Button exitbutton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (loginCheck()) {
                    System.out.println("FCK YEA LOGIN");
                    animScreen(e);
                } else {
                    System.out.println("FCKED UP SON");
                   // animScreen(e);
                }
            }
        ;
    }

    );
    }
    
     public void Exitkey(ActionEvent event) {
        Stage stage = (Stage) exitbutton.getScene().getWindow();
        stage.close();
       
     }
    
    private Boolean loginCheck() {
        String usrn = usr.getText();
        String ps = pass.getText();
        String check = "";
        Boolean validLogin = false;
        UgcQueries ugcQ = new UgcQueries();
        outerloop:
        for (String s : ugcQ.getUser()) {
            if (s.equals(usrn)) {
                String connPass = ugcQ.getPassword(usrn);
                if (connPass.equals(ps)) {
                    validLogin = true;
                    break outerloop;
                } else {
                }
            } else {
            }
        }
        if (validLogin) {
        } else {
        }
        return validLogin;
    }

    private void gotoHome(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("HomeScreen");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }
    }

    private void animScreen(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Group root1 = new Group();
        Group root2 = new Group();
        Rectangle rect1 = new Rectangle(1200, 800);
        Label lab1 = new Label("LOADING...");
        rect1.setFill(new ImagePattern(new Image("ugcs/Resources/loginSs.png")));
        root1.getChildren().addAll(rect1, lab1);
        Rectangle rect2 = new Rectangle(1200, 800);
        Label lab2 = new Label("LOADED!");
        rect2.setFill(new ImagePattern(new Image("ugcs/Resources/george.png")));
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
