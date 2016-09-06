/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import ugcs.Queries.ugcQueries;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (loginCheck()) {
                    System.out.println("FCK YEA LOGIN");
                    gotoHome(e);
                } else {
                    System.out.println("FCKED UP SON");
                }
            }
        ;
    }

    );
    }
    
    private Boolean loginCheck() {
        String usrn = usr.getText();
        String ps = pass.getText();
        String check = "";
        Boolean validLogin = false;
        ugcQueries ugcQ = new ugcQueries();
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

}
