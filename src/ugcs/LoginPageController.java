/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import ugcs.Queries.UgcQueries;
import org.jasypt.util.password.BasicPasswordEncryptor;
import ugcs.Audio.AudioMethods;

/**
 * FXML Controller class CTRL + SHIFT + I
 *
 * @author sgaheer
 */
public class LoginPageController implements Initializable {

    @FXML
    private Button login;
    @FXML
    private TextField usr;
    @FXML
    private PasswordField pass;
    @FXML
    private Button exitbutton;
    @FXML
    private Label incPass;

    private PathTransition pathT = new PathTransition();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
          


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                usr.requestFocus();
            }
        });
        
     

        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (loginCheck()) {
                    handleTransitionButton(e, "loginS.png", "dashS.png", "Dashboard.fxml", "Home");
                } else {
                    incPass.setVisible(true);
                    animateLabel(pathT, incPass);
                    handleAudio("beep_short_off.wav");
                }
            }
        ;
        });
        pass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {
                    if (loginCheck()) {
                        handleTransitionKey(e, "loginS.png", "dashS.png", "Dashboard.fxml", "Home");
                    } else {
                        incPass.setVisible(true);
                        animateLabel(pathT, incPass);
                        handleAudio("beep_short_off.wav");

                    }
                }
            }
        });
    }

    public void Exitkey(ActionEvent event) {
        try {
            DriverManager.getConnection(
                    "jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            System.out.println("Closing Derby");
        }
        Stage stage = (Stage) exitbutton.getScene().getWindow();
        stage.close();

    }

    private Boolean loginCheck() {
        String usrn = usr.getText();
        String ps = pass.getText();
        String check = "";
        BasicPasswordEncryptor passEnc = new BasicPasswordEncryptor();
        Boolean validLogin = false;
        UgcQueries ugcQ = new UgcQueries();
        outerloop:
        for (String s : ugcQ.getUser()) {
            if (s.equals(usrn)) {
                String connPass = ugcQ.getPassword(usrn);
                if (passEnc.checkPassword(ps, connPass)) {
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

    private void createLoginDetailsFile() {
        String fName = "temp.txt";
        //String ps = pass.getText();
        String z = usr.getText();
        UgcQueries ugcQ = new UgcQueries();
        String name = ugcQ.getName(z);
        String ps = ugcQ.getPassword(z);
        try {
            FileWriter fileWriter = new FileWriter(fName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(name + "," + ps);

            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing file '" + fName + "'");
            ex.printStackTrace();
        }
    }

    private void handleAudio(String file) {
        AudioMethods am = new AudioMethods();

        am.playAudio(file);
    }

    private void handleTransitionButton(ActionEvent e, String a, String b, String c, String d) {
        createLoginDetailsFile();
        AnimationsTransitions at = new AnimationsTransitions();
        handleAudio("pad_confirm.wav");
        at.changeScreenButton(e, a, b, c, d);

    }
    
    private void animateLabel(PathTransition pt, Label node) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.animateError(pt, node);
    }

    private void handleTransitionKey(KeyEvent e, String a, String b, String c, String d) {
        createLoginDetailsFile();
        AnimationsTransitions at = new AnimationsTransitions();
        handleAudio("pad_confirm.wav");
        at.changeScreenKey(e, a, b, c, d);

    }

}
