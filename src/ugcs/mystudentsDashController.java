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


/**
 *
 * @author peterrpancakes
 */
public class mystudentsDashController implements Initializable {

    
    @FXML
    Button logout;
    @FXML
    Button rPrev;
    @FXML
    Button managestudents;
    @FXML
    Button manageplacement;
  


    @Override
    public void initialize(URL url, ResourceBundle rb) {

     

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

    }


  

    private void handleTransitionButton(ActionEvent e, String a, String b, String c, String d) {
        AnimationsTransitions at = new AnimationsTransitions();
        at.changeScreenButton(e, a, b, c, d);
    }

 

}
