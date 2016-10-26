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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ugcs.Queries.FileQueries;


/**
 *
 * @author peterrpancakes
 */
public class mystudentsDashController implements Initializable {

    
    @FXML
    Button logOut;
    @FXML
    Button rPrev;
    @FXML
    Button managestudents;
    @FXML
    Button manageplacement;
    @FXML
    Label FirstName;
  
    FileQueries fq = new FileQueries();


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        FirstName.setText(fq.readName());
     

        managestudents.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleTransitionButton(e, "coopdashS.png", "manageS.png", "ManageStudent2.fxml", "COOP Students");
            }
        });

        manageplacement.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
              handleTransitionButton(e, "coopdashS.png", "placementS.png", "workplacementDash.fxml", "Placement Dashboard");

            }
        });
        
        rPrev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
              handleTransitionButton(e, "coopdashS.png", "dashS.png", "Dashboard.fxml", "Dashboard");

            }
        });
        logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                fq.deleteTemp();
              handleTransitionButton(e, "coopdashS.png", "loginS.png", "LoginPage.fxml", "Login");

            }
        });

    }


  

    private void handleTransitionButton(ActionEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenButton(e, a, b, c, d);
    }

 

}
